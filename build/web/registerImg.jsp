<%-- 
    Document   : registerImg.jsp
    Created on : 06-nov-2020, 8:40:41
    Author     : lokix
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/main.css">
        <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@200;300;400;500;600;700;800&display=swap" rel="stylesheet">
        <title>Unsplash | Subir imagen</title>
    </head>    
    <body class="uploadbody">
        <div class="topnav" id="myTopnav">
            <a href="menu.jsp">Home</a>
            <a href="registerImg.jsp" class="active" >Subir Imágenes</a>
            <a href="listImg.jsp">Todas nuestras imágenes</a>
            <a href="searchImg.jsp">Buscar Imágenes</a>
            <a href="encrypt.jsp">Encriptar ficheros</a>
            <a href="decrypt.jsp">Desencriptar ficheros</a>
            <a href="javascript:void(0);" class="icon" onclick="myFunction()">
                <i class="fas fa-bars"></i>
            </a>
        </div>
        <h1>Sube tus propias imágenes</h1>
        <h2>Rellena el siguiente formulario para subir la imagen</h2>
        <div class="wrapper">
            <div class="form-box">
                <form action="registerImg" method="POST" enctype ="multipart/form-data">
                    <div class="field-box">
                        <label for="title">Título</label>
                        <input type="text" name="title" id="title">
                    </div>
                    <div class="field-box">
                        <label for="description">Descripción</label>
                        <input type="text" name="description" id="description">
                    </div>
                    <div class="field-box">
                        <label for="keywords">Palabras Clave</label>
                        <input type="text" name="keywords" id="keywords">
                    </div>
                    <div class="field-box">
                        <label for="author">Autor</label>
                        <input type="text" name="author" id="author">
                    </div>
                    <div class="field-box">
                        <label for="title">Fecha de creación</label>
                        <input type="date" name="creation_date" id="creation_date" value="" min="2018-01-01" max="">
                    </div>
                    <div class="field-box">
                        <label for="title">Nombre del archivo</label>
                        <input type="text" name="filename" id="filename">
                    </div>
                    <div class="field-box">
                        <label for="title">¿Quieres que encriptemos el fichero?</label>
                        <div>
                            <label for="encrypt_yes">Sí</label>
                            <input type="radio" name="encrypt" id="ecrypt_yes" value="1">
                            <label for="encrypt_no">No</label>
                            <input type="radio" name="encrypt" id="ecrypt_yes" value="0">
                        </div>

                    </div>
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

        <script>
            /* function myFunction() makes the top navbar responsive */
            function myFunction() {
                var x = document.getElementById("myTopnav");
                if (x.className === "topnav") {
                    x.className += " responsive";
                } else {
                    x.className = "topnav";
                }
            }
            
            /* 
                function setDate() writes the current date in the 
                creation date input field in the propper format.
            */
            const setDate = () => {
                let date_label = document.getElementById("creation_date");
                let date = new Date();
                let dd = String(date.getDate()).padStart(2, '0');
                let mm = String(date.getMonth()+1).padStart(2, '0');
                let yyyy = date.getFullYear();
                let format_date = yyyy+'-'+mm+'-'+dd;
                date_label.value = format_date;
                date_label.max = format_date;
            }
            setDate();
        </script>
    </body>
</html>
