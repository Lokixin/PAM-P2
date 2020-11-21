<%-- 
    Document   : encryptxml
    Created on : 19-nov-2020, 12:51:06
    Author     : lokix
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/main.css">
        <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@200;300;400;500;600;700;800&display=swap" rel="stylesheet">
        <title>Upload and Encrypt XML files</title>
    </head>
    <body>
        <div class="topnav" id="myTopnav">
            <a href="menu.jsp">Home</a>
            <a href="registerImg.jsp">Subir Imágenes</a>
            <a href="listImg.jsp" class="active" >Todas nuestras imágenes</a>
            <a href="searchImg.jsp">Buscar Imágenes</a>
            <a href="encrypt.jsp">Encriptar ficheros</a>
            <a href="decrypt.jsp">Desencriptar ficheros</a>
            <a href="javascript:void(0);" class="icon" onclick="myFunction()">
                <i class="fas fa-bars"></i>
            </a>
        </div>

        <h1>Sube tu fichero XML para encriptarlo</h1>
        <div class="wrapper">
            <div class="form-box">
                <form action="uploadXML" method="POST" enctype ="multipart/form-data">

                    <div class="field-box">
                        <label for="myfile" id="inputfile"></label>
                        <input type="file" id="myfile" name="myfile">
                    </div>
                    <div class="field-box">
                        <input type="submit" value="Enviar">
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
