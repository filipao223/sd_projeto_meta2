<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>DropMusic</title>
    <div align = "left" />
    <h2>Menu Principal</h2>
</head>
<body>
    <div align = "left">
        <s:form action = "gerir" >
            <s:submit value = "Gerir"/>
        </s:form>
        <br>
        <s:form action = "pesquisar_musicas" method = "post">
            <button type="button" onclick="">Pesquisar Músicas</button>
        </s:form>
        <br>
        <s:form action = "detalhes" method = "post">
            <button type="button" onclick="">Consultar detalhes</button>
        </s:form>
        <br>
        <s:form action = "critica" method = "post">
            <button type="button" onclick="">Escrever Crítica</button>
        </s:form>
        <br>
        <s:form action = "editor" method = "post">
            <button type="button" onclick="">Dar privilégios</button>
        </s:form>
        <br>
        <s:form action = "upload" method = "post">
            <button type="button" onclick="">Upload</button>
        </s:form>
        <br>
        <s:form action = "partilhar" method = "post">
            <button type="button" onclick="">Partilhar Música</button>
        </s:form>
        <br>
        <s:form action = "download" method = "post">
            <button type="button" onclick="">Download</button>
        </s:form>
        <br>
        <s:form action = "logout" method = "post">
            <button type="button" onclick="">Logout</button>
        </s:form>

    </div>

</body>
</html>
