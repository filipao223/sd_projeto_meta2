<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DropMusic</title>
<div align = "left" />
<h2>Login/Registo</h2>
</div>

</head>
<body>
<div align = "left">
    <h2>Login</h2>
    <s:form action = "login" method = "post">
        <s:text name = "Username" />
        <s:textfield name = "username" label = "Username" />
        <s:text name = "Password" />
        <s:password name = "password" label = "Password" />
        <p><s:submit value = "Login"/></p>
    </s:form>

    <h2>Registar</h2>
    <s:form action = "registar" method = "post">
        <s:text name = "Username" />
        <s:textfield name = "username" label = "Username" />
        <s:text name = "Password" />
        <s:password name = "password" label = "Password" />
        <p><s:submit value = "Registar"/></p>
    </s:form>
</div>
</body>
</html>