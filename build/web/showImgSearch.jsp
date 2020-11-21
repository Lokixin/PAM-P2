<%-- 
    Document   : showImgSearch
    Created on : 11-nov-2020, 11:19:19
    Author     : lokix
--%>

<%@page import="javax.crypto.spec.IvParameterSpec"%>
<%@page import="utilities.CryptoTools"%>
<%@page import="java.io.File"%>
<%@page import="java.io.InputStream"%>
<%@page import="javax.crypto.SecretKey"%>
<%@page import="javax.crypto.CipherInputStream"%>
<%@page import="javax.crypto.Cipher"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Enumeration"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/main.css">
        <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@200;300;400;500;600;700;800&display=swap" rel="stylesheet">
        <title>Unsplash | Imágenes Encontradas</title>
    </head>
    <body>
        <div class="topnav" id="myTopnav">
            <a href="menu.jsp">Home</a>
            <a href="registerImg.jsp">Subir Imágenes</a>
            <a href="listImg.jsp">Todas nuestras imágenes</a>
            <a href="searchImg.jsp" class="active" >Buscar Imágenes</a>
            <a href="encrypt.jsp">Encriptar ficheros</a>
            <a href="decrypt.jsp">Desencriptar ficheros</a>
            <a href="javascript:void(0);" class="icon" onclick="myFunction()">
                <i class="fas fa-bars"></i>
            </a>
        </div>
        <h1>Imágenes relacionadas con tu búsqueda</h1>
        <%
            if (request.getAttribute("images") == null) {
                out.println("<h3>Lo sentimos, no hemos encontrado imágenes relacionadas con tu búsqueda</h3>");
        %>
        <div class="wrapper">
            <h4>Puedes realizar una nueva búsqueda</h4>
            <div class="form-box">
                <form action="searchImg" method="GET">
                    <div class="field-box">
                        <label for="title">Título</label>
                        <input type="text" name="title" id="title">
                    </div>
                    <div class="field-box">
                        <label for="author">Autor</label>
                        <input type="text" name="author" id="author">
                    </div>
                    <div class="field-box">
                        <label for="keywords">Palabras Clave</label>
                        <input type="text" name="keywords" id="keywords">
                    </div>
                    <div class="field-box">
                        <label for="title">¿Quieres que mostremos las imágenes encriptadas?</label>
                        <div>
                            <label for="encrypt_yes">Sí</label>
                            <input type="radio" name="encrypt" id="ecrypt_yes" value="1">
                            <label for="encrypt_no" >No</label>
                            <input type="radio" name="encrypt" id="ecrypt_yes" value="0" checked>
                        </div>
                    </div>
                    <div class="field-box">
                        <input type="submit" value="Enviar">
                    </div>

                </form>
            </div>
            <h4>O volver a la página principal</h4>
            <a href="menu.jsp" class="big-btn" style="text-align: center">Volver al menú</a>
        </div>
        
        <%
            } else {
                Cipher cipher = null;
                SecretKey key;
                String pathToFile;
                String decrypt = (String) request.getAttribute("encrypt");
                ArrayList<Object> img_list = (ArrayList<Object>) request.getAttribute("images");

                if (decrypt.equals("1")) {
                    CryptoTools ct = new CryptoTools("keystore.keystore", "C:\\Users\\lokix\\Documents\\NetBeansProjects\\WebApplication1\\pimpampum");
                    key = ct.loadFromKeyStore();
                    cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                    cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(new byte[16]));
                    System.out.println("[MESSAGE] -> The key is" + key);
                }

                out.print("<div class='flex-container'>");

                for (Object image : img_list) {
                    HashMap<String, String> img = (HashMap<String, String>) image;
                    if (img.get("encrypt").equals("1")) {
                        if (decrypt.equals("1")) {
                            pathToFile = request.getServletContext().getRealPath("") + File.separator + img.get("filename");
                            String outputPath = request.getServletContext().getRealPath("") + File.separator + "media" + File.separator + img.get("filename");
                            String pathTmp = CryptoTools.decryptImg(cipher, pathToFile, outputPath);
                            out.print("<div class='section-container no-padding'>");
                            //out.print("<img src="+pathTmp+">");
                            out.print("<img src=" + request.getContextPath() + "/media/" + pathTmp + ">");
                            out.print("<ul>");
                            out.print("<li> <span> Autor </span>: " + img.get("author") + "</li>");
                            out.print("<li> <span> Título </span>: " + img.get("title") + "</li>");
                            out.print("</ul>");
                            out.print("</div>");
                        } else if (decrypt.equals("0")) {
                            out.print("<div class='section-container no-padding'>This image is encrypted</div>");
                        }
                    } else {
                        out.print("<div class='section-container no-padding'>");
                        out.print("<img src=" + request.getContextPath() + "/" + img.get("filename") + ">");
                        out.print("<ul>");
                        out.print("<li> <span> Autor </span>: " + img.get("author") + "</li>");
                        out.print("<li> <span> Título </span>: " + img.get("title") + "</li>");
                        out.print("</ul>");
                        out.print("</div>");
                    }
                }
                out.print("</div>");
            }

        %>


    </body>
</html>
