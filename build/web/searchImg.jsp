<%-- 
    Document   : searchImg
    Created on : 11-nov-2020, 10:10:27
    Author     : lokix
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/main.css">
        <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@200;300;400;500;600;700;800&display=swap" rel="stylesheet">
        <title>Unsplash | Search Images</title>
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
        <h1>Encuentra las imágenes que buscas</h1>
        <div class="wrapper">
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
        </div>
        <script>
            function myFunction() {
                var x = document.getElementById("myTopnav");
                if (x.className === "topnav") {
                    x.className += " responsive";
                } else {
                    x.className = "topnav";
                }
            }
        </script>
    </body>
</html>
