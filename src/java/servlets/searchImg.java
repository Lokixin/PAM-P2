/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author lokix
 */
@WebServlet(name = "searchImg", urlPatterns = {"/searchImg"})
public class searchImg extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection = null;
        PrintWriter writer = null;
        try {
            // DB connection
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            writer = response.getWriter();

            // Obtain data from the form
            String title = request.getParameter("title");
            String keywords = request.getParameter("keywords");
            String author = request.getParameter("author");
            String encrypt = request.getParameter("encrypt");

            //query the DB to search images with the title and author required.
            //String query = "SELECT * FROM IMAGE WHERE AUTHOR LIKE '%" + author + "%' AND TITLE LIKE '%" + title + "%'";
            String query = generateQuery(author, title, keywords);
            ResultSet rs = statement.executeQuery(query);

            /*
                Form every image we create a HasMap, containing the 
                title, the author, the encrypt, and the filename. 
                Every HashMap is added to an ArrayList. The ArrayList 
                is passed into the request Attributes.
             */
            if (!rs.next()) {
                System.out.println("No data");
            } else {
                ArrayList<Object> arr = new ArrayList<Object>();
                do {
                    Map<String, String> map = new HashMap<>();
                    map.put("title", rs.getString("title"));
                    map.put("author", rs.getString("author"));
                    map.put("filename", rs.getString("filename"));
                    map.put("encrypt", String.valueOf(rs.getInt("encrypted")));
                    arr.add(map);
                } while (rs.next());
                request.setAttribute("images", arr);
                request.setAttribute("encrypt", encrypt);
            }

            // The request is forwarded to showImgSearch.jsp to show results.
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("showImgSearch.jsp");
            requestDispatcher.forward(request, response);

        } catch (ClassNotFoundException | SQLException e) {
            if (writer != null) {
                writer.println("Problems with database connection ");
                writer.println("<br/> ERROR: " + e.getMessage());
            }
            // Error handling. An error message and the return page is added to 
            // the request body. They are useful to identify the error 
            // and return to the previous page.
            request.setAttribute("error", "Hemos tenido un error con la base de datos, disculpa las molestias");
            request.setAttribute("encrypt", "searchImg.jsp");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("error.jsp");
            requestDispatcher.forward(request, response);
            System.out.println("Problems during file upload DB. Error: " + e.getMessage());
        }
    }

    /**
     *
     * Generates the query according to the parameters required by the user.
     * It search for any coincidence (just one field needs to be the same).
     * More concrete search can easily be implemented by replacing ORs for ANDs.
     * 
     * @param author
     * @param title
     * @param keywords
     * @return String
     */
    public String generateQuery(String author, String title, String keywords) {
        String query = "SELECT DISTINCT title, author, filename, encrypted FROM IMAGE WHERE(";
        if (keywords.contains(",")) {
            int index = 0;
            int length = keywords.split(",").length - 1;
            for (String kw : keywords.split(",")) {
                if (index < length) {
                    query += "keywords LIKE '%" + kw + "%' OR ";
                } else {
                    query += "keywords LIKE '%" + kw + "%'";
                }
                index += 1;
            }
        } else {
            query += "keywords LIKE '%" + keywords + "%'";
        }
        query += ") ";
        query += "OR (author like '%" + author + "%' OR title like '%" + title + "%')";
        return query;
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
