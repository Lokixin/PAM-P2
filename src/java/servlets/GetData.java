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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author lokix
 */
@WebServlet(name = "GetData", urlPatterns = {"/getdata"})
public class GetData extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Connection connection = null;
        try (PrintWriter out = response.getWriter()) {
            
            //DB CONNECTION
            String query;
            PreparedStatement statement;
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2");
            if(connection != null){
                out.println("Connection successful");
            }
            /* TODO output your page here. You may use following sample code. */
            String titulo = request.getParameter("titulo");
            String autor = request.getParameter("autor");
            if(titulo!=null && autor!=null){
                query = String.format("select * from image where title='%s' and author='%s'", titulo, autor);
                statement = connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery();
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet GetData</title>");            
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Servlet GetData at " + request.getContextPath() + "</h1>");
                while(rs.next()){
                    out.println("<ul>");
                    out.println("<li>Título: " + rs.getString("title") + "</li>");
                    out.println("<li>Autor: " + rs.getString("author") + "</li>");
                    out.println("<li>Fecha de creación: " + rs.getString("creation_date") + "</li>");
                    out.println("<li>Keeywords: " + rs.getString("keywords") + "</li>");
                    out.println("<li>Nombre del archivo: " + rs.getString("filename") + "</li>");
                    out.println("</ul>");
                }
                out.println("</body>");
                out.println("</html>");
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GetData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GetData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
