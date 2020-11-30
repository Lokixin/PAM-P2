<%-- 
    Document   : error
    Created on : 26-nov-2020, 20:43:08
    Author     : lokix
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/main.css">
        <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@200;300;400;500;600;700;800&display=swap" rel="stylesheet">
        <title>Ha ocurrido un ERROR</title>
    </head>
    <body>
        <h1>ERROR: Lo sentimos ha ocurrido un error</h1>
        <div class="main-container">
            <div class="txt-container">
                <h4>
                    Descripción del error: <%= (String) request.getAttribute("error")%>
                </h4>
                <a href=<%= request.getAttribute("from")%> class="big-btn" >
                    Volver a la página anterior
                </a>   
            </div>
        </div>

    </body>
</html>
