<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>DropMusic</title>
    <div align = "left" />
    <h2>Partilhar com outro utilizador</h2>
</head>
<body>
<div align = "left">
    <s:form action = "partilhaUtilizador" method = "post">
        <s:text name = "Nome do utilizador" />
        <s:textfield name = "target"></s:textfield>
        <p><s:submit /></p>
    </s:form>
</div>

</body>
</html>
