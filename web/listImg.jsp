<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import = "java.io.*,java.util.*,java.sql.*"%>
<%@ page import = "javax.servlet.http.*,javax.servlet.*" %>
<%@ page import = "javax.servlet.http.*,javax.servlet.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/main.css">
        <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@200;300;400;500;600;700;800&display=swap" rel="stylesheet">
        <title>Unsplash | Todas las imágenes</title>
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
        <h1>Explora todas nuestras Imágenes</h1>
        <%
            Connection connection = null;
            PrintWriter writer = null;
            try {
                connection = DriverManager.getConnection("jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2");
                Statement statement = connection.createStatement();
                statement.setQueryTimeout(30);  // set timeout to 30 sec.
                writer = response.getWriter();
                String query = "SELECT * FROM IMAGE";
                ResultSet rs = statement.executeQuery(query);
                if (!rs.next()) {
                    out.println("<a href='menu.jsp' class='big-btn'>Encriptar</a>");
                } else {
                    out.print("<div class='flex-container'>");
                    do {
                        if (rs.getString("encrypted").equals("1")) {
                            out.println("<div class='section-container no-padding'>Image is  encrypted </div>");
                        } else {
                            out.print("<div class='section-container no-padding'>");
                            out.print("<img src=" + request.getContextPath() + "/" + rs.getString("filename") + ">");
                            out.print("<ul>");
                            out.print("<li> <span> Autor </span>: " + rs.getString("author") + "</li>");
                            out.print("<li> <span> Título </span>: " + rs.getString("title") + "</li>");
                            out.print("</ul>");
                            out.print("</div>");
                        }
                    } while (rs.next());
                    out.print("</div>");

                }
            } catch (SQLException e) {
                if (writer != null) {
                    writer.println("Problems with database connection ");
                    writer.println("<br/> ERROR: " + e.getMessage());
                }
            }
        %>
    </body>
</html>
