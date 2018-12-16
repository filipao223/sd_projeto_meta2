<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>DropMusic</title>
    <div align = "left" />
    <h2>consulta</h2>
</head>
<body>
<div align = "left">
    <s:form action = "consulta" method = "post">
        <s:text name = "Nome do Album" />
        <s:textfield name = "nomeAlbum"></s:textfield>
        <s:text name = "Nome do artista" />
        <s:textfield name = "nomeArtista"></s:textfield>
        <p><s:submit /></p>
        <s:text name="O Resultado vai aparecer aqui">${info}</s:text>

    </s:form>
</div>

</body>
</html>
