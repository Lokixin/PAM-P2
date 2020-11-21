<%-- 
    Document   : menu.jsp
    Created on : 06-nov-2020, 8:31:12
    Author     : lokix
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/main.css">
        <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@200;300;400;500;600;700;800&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css" integrity="sha512-+4zCK9k+qNFUR5X+cKL9EIR+ZOhtIloNl9GIKS57V1MyNsYpYcUrUeQc9vNfzsWfV28IaLL3i96P9sdNyeRssA==" crossorigin="anonymous" />
        <title>Unsplash | Navegación</title>
    </head>
    <body>
        <div class="topnav" id="myTopnav">
            <a href="" class="active">Home</a>
            <a href="registerImg.jsp">Subir Imágenes</a>
            <a href="listImg.jsp">Todas nuestras imágenes</a>
            <a href="searchImg.jsp">Buscar Imágenes</a>
            <a href="encrypt.jsp">Encriptar ficheros</a>
            <a href="decrypt.jsp">Desencriptar ficheros</a>
            <a href="javascript:void(0);" class="icon" onclick="myFunction()">
                <i class="fas fa-bars"></i>
            </a>
        </div>


        <header class="showcase">
            <div class="txt-container">
                <h1>Bienvenidos a Unsplash</h1>
                <a href="#inicio" class="big-btn">Empezar</a>
            </div>
        </header>

        <main class="main-container">

            <h2> ¿Qué puede ofrecerte Unsplash?</h2>
            <div class="flex-container">
                <section class="section-container">
                    <i class="far fa-images fa-4x"></i>
                    <h3>Stocks de fotografía sin copyright</h3>
                    <p>
                        Utiliza las fotografías publicadas en esta web libremenete
                        sin preocuparte por el copyright. Lo único que te pediremos
                        es, siempre y cuando sea posible, dale crédito al autor.
                    </p>
                    <a href="listImg.jsp" class="big-btn">Buscar</a>
                </section>

                <section class="section-container">
                    <i class="fas fa-camera fa-4x"></i>
                    <h3>Sube tus propias fotografías</h3>
                    <p>
                        ¿Eres un fotógrafo? ¡Entonces publica aquí tus fotografías!
                        No importa si eres amateur o todo un profesional, aquí
                        todo el mundo tiene cabida. Súbelas para tener un gran 
                        reconocimiento entre nuestros usuarios. 
                    </p>
                    <a href="registerImg.jsp" class="big-btn">Upload</a>
                </section>

                <section id="inicio" class="section-container">
                    <i class="fas fa-lock fa-4x"></i>
                    <h3>Encriptación y desencriptación de archivos</h3>
                    <p>
                        Recuerda, la seguridad es lo primero. Por eso en Unsplash
                        contamos con un sistema de encriptación y desencriptación
                        para que tus archivos estén seguros.
                    </p>
                    <a href="#inicio" class="big-btn">Encriptar</a>
                </section>
            </div>
        </main>
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
