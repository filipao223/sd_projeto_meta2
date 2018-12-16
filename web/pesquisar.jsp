<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>DropMusic</title>
    <div align = "left" />
    <h2>Pesquisar</h2>
</head>
<body>
    <div align = "left">
        <s:form action = "pesquisarArtista">
            <s:submit value = "Por Artista" />
        </s:form>
        <br>
        <s:form action = "pesquisarAlbum">
            <s:submit value = "Por Album" />
        </s:form>
        <br>
    </div>

</body>
</html>
