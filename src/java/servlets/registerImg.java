/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import javax.crypto.*;
import utilities.CryptoTools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.spec.IvParameterSpec;
import javax.servlet.RequestDispatcher;

/**
 *
 * @author lokix
 */
@WebServlet(name = "registerImg", urlPatterns = {"/registerImg"})
@MultipartConfig
public class registerImg extends HttpServlet {

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection = null;
        PrintWriter writer = null;
        OutputStream out = null;
        InputStream filecontent = null;

        Part filePart;
        File fileSaveDir;

        String appPath, saveDir, fileName, savePath;
        String title, description, keywords, author, crea_date, stor_date, encrypt;

        int read;

        response.setContentType("text/html;charset=UTF-8");

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            // create a database connection
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            writer = response.getWriter();

            title = request.getParameter("title");
            description = request.getParameter("description");
            keywords = request.getParameter("keywords");
            author = request.getParameter("author");
            encrypt = request.getParameter("encrypt");
            System.out.println("El valor de encrypt es: " + encrypt);

            crea_date = request.getParameter("creation_date");
            crea_date = crea_date.replace('-', '/');

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
            stor_date = dateFormat.format(date);

            // Check NULL values from form is missing
            // Create path components to save the file 
            // Directory inside web application under /uploadFiles
            // Images are accessible afterwards
            appPath = request.getServletContext().getRealPath("");
            String SAVE_DIR = "";
            // constructs path of the directory to save uploaded file
            saveDir = appPath + File.separator + SAVE_DIR;

            // creates the save directory if it does not exists
            fileSaveDir = new File(saveDir);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdir();
            }

            // Get filename from the file selected by user
            // Get parameter from form
            filePart = request.getPart("myfile");

            // Extract filename from multi-part
            fileName = getFileName(filePart);

            // Get only the filename using Path class (after Java 1.7)
            Path p = Paths.get(fileName);
            fileName = p.getFileName().toString();
            savePath = saveDir + File.separator + fileName;

            // Create file inside Web Application
            out = new FileOutputStream(new File(savePath));
            filecontent = filePart.getInputStream();

            final byte[] bytes = new byte[1024];
            
            /* 
                If the users asks to encrypt the image, it is encrypted using
                the Cipher with the AES algorithm and the SecretKey stored 
                in the KeyStore. 
            */
            if (encrypt.equals("1")) {
                try {
                    CryptoTools ct = new CryptoTools("keystore.keystore", "C:\\Users\\lokix\\Documents\\NetBeansProjects\\WebApplication1\\pimpampum");
                    SecretKey key = ct.loadFromKeyStore();
                    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                    cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(new byte[16]));
                    CryptoTools.encryptImg(filecontent, cipher, savePath);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(registerImg.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchPaddingException ex) {
                    Logger.getLogger(registerImg.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvalidKeyException ex) {
                    Logger.getLogger(registerImg.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvalidAlgorithmParameterException ex) {
                    Logger.getLogger(registerImg.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                while ((read = filecontent.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
            }

            System.out.println("File " + fileName + " being uploaded to " + saveDir);

            String query = "select max(id) from image";
            ResultSet rs = statement.executeQuery(query);

            int id = 0;
            if (rs.next()) {
                id = rs.getInt(1) + 1;
            }

            query = "insert into image values(?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement prepstatement;
            prepstatement = connection.prepareStatement(query);
            prepstatement.setInt(1, id);
            prepstatement.setString(2, title);
            prepstatement.setString(3, description);
            prepstatement.setString(4, keywords);
            prepstatement.setString(5, author);
            prepstatement.setString(6, crea_date);
            prepstatement.setString(7, stor_date);
            prepstatement.setString(8, fileName);
            prepstatement.setInt(9, Integer.decode(encrypt));
            prepstatement.executeUpdate();

            /**
             * menu.jsp must exist in your web application *
             */
            /**
             * if it is missing, redirection fails *
             */
            response.sendRedirect("menu.jsp");

        } catch (FileNotFoundException fne) {

            // Catch exceptions on file management
            if (writer != null) {
                writer.println("You either did not specify a file to upload or are "
                        + "trying to upload a file to a protected or nonexistent "
                        + "location.");
                writer.println("<br/> ERROR: " + fne.getMessage());
            }
            System.out.println("Problems during file upload in disk. Error: " + fne.getMessage());
            request.setAttribute("error", "El archivo no ha sido encontrado o su acceso ha sido denegado");
            request.setAttribute("from", "registerImg.jsp");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("error.jsp");
            requestDispatcher.forward(request, response);

        } catch (ClassNotFoundException | SQLException e) {
            // Catch exceptions on file management
            if (writer != null) {
                writer.println("Problems with database connection ");
                writer.println("<br/> ERROR: " + e.getMessage());
            }
            System.out.println("Problems during file upload DB. Error: " + e.getMessage());
            
        } finally {

            // Close opened files
            if (out != null) {
                out.close();
            }
            if (filecontent != null) {
                filecontent.close();
            }
            if (writer != null) {
                writer.close();
            }

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }

    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}

/**
 * Returns a short description of the servlet.
 *
 * @return a String containing servlet description
 */
