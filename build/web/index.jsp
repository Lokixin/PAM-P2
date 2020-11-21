<%-- 
    Document   : index.jsp
    Created on : 30-oct-2020, 8:40:54
    Author     : lokix
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>PAM - Práctica 1</h1>
        <% out.println("<h2>First practice web applications jsp</h2>"); %>
        <ul>
          <% for(int i = 0; i<5; i++){
            out.println("<li>Elemento núnero "+i+"</li>");
        };%>
        </ul>
    </body>
</html>
