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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import utilities.CryptoTools;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.apache.xml.security.encryption.XMLEncryptionException;


/**
 *
 * @author lokix
 */
@WebServlet(name = "uploadXML", urlPatterns = {"/uploadXML"})
@MultipartConfig
public class uploadXML extends HttpServlet {

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            String appPath = request.getServletContext().getRealPath("");
            String SAVE_DIR = "upload";
            // constructs path of the directory to save uploaded file
            String saveDir = appPath + File.separator + SAVE_DIR;
            
            // creates the save directory if it does not exists
            File fileSaveDir = new File(saveDir);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdir();
            }
            
            // Get filename from the file selected by user
            // Get parameter from form
            Part filePart = request.getPart("myfile");
            
            // Extract filename from multi-part
            String fileName = getFileName(filePart);
            
            // Get only the filename using Path class (after Java 1.7)
            Path p = Paths.get(fileName);
            fileName = p.getFileName().toString();
            String savePath = saveDir + File.separator + fileName;
            
            // Create file inside Web Application
            FileOutputStream out = new FileOutputStream(new File(savePath));
            InputStream filecontent = filePart.getInputStream();
            
            final byte[] bytes = new byte[1024];
            
            /*
            If the users asks to encrypt the image, it is encrypted using
            the Cipher with the AES algorithm and the SecretKey stored
            in the KeyStore.
            */
            int read;
            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            
            CryptoTools ct = new CryptoTools("keystore.keystore", "C:\\Users\\lokix\\Documents\\NetBeansProjects\\WebApplication1\\pimpampum");
            SecretKey key = ct.loadFromKeyStore();
            XMLCipher xmlCipher = XMLCipher.getInstance(XMLCipher.AES_128);
            xmlCipher.init(XMLCipher.ENCRYPT_MODE, key);
            CryptoTools.encryptXML(xmlCipher, key, saveDir);
            response.sendRedirect("menu.jsp");
            } catch (IOException ex) {
            Logger.getLogger(uploadXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServletException ex) {
            Logger.getLogger(uploadXML.class.getName()).log(Level.SEVERE, null, ex);
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
