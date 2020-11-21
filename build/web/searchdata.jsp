<%-- 
    Document   : searchdata
    Created on : 02-nov-2020, 12:15:31
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
        <h1>Aquí puedes buscar datos en la BDD</h1>
        <form type="GET" action="/WebApplication1/getdata">
            <label>Título: </label>
            <input type="text" name="titulo">
            <label>Autor: </label>
            <input type="text" name="autor">
            <input type="submit" name="Enviar">
        </form>
    </body>
</html>
