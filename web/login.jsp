<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>DropMusic</title>
    <div align = "center" />
    <p>[DropMusic]</p>
</div>

</head>
<body>
<div align = "center">
    <h2>Login</h2>
    <c:choose>
        <c:when test = "${session.loggedin == 0}">
            <p>Username ${session.username} não existe!</p>
            <s:form action = "login" method = "post">
                <s:text name = "Username" />
                <s:textfield name = "username" label = "Username" />
                <s:text name = "Password" />
                <s:password name = "password" label = "Password" />
                <p><s:submit value = "Login"/></p>
            </s:form>
        </c:when>
        <c:when test = "${session.loggedin == 2}">
            <p>O utilizador ${session.username} já está logged in!</p>
            <s:form action = "login" method = "post">
                <s:text name = "Username" />
                <s:textfield name = "username" label = "Username" />
                <s:text name = "Password" />
                <s:password name = "password" label = "Password" />
                <p><s:submit value = "Login"/></p>
            </s:form>
        </c:when>
            <c:otherwise>
                <s:form action = "login" method = "post">
                    <s:text name = "Username" />
                    <s:textfield name = "username" label = "Username" />
                    <s:text name = "Password" />
                    <s:password name = "password" label = "Password" />
                    <p><s:submit value = "Login"/></p>
                </s:form>
            </c:otherwise>
    </c:choose>

    <h2>Registar</h2>
    <c:choose>
        <c:when test = "${session.registar == false}">
            <p>Username ${session.username} já utilizado!</p>
            <s:form action = "registar" method = "post">
                <s:text name = "Username" />
                <s:textfield name = "username" label = "Username" />
                <s:text name = "Password" />
                <s:password name = "password" label = "Password" />
                <p><s:submit value = "Registar"/></p>
            </s:form>
        </c:when>
        <c:when test = "${session.registar == true}">
            <p>Registado com sucesso</p>
            <s:form action = "registar" method = "post">
                <s:text name = "Username" />
                <s:textfield name = "username" label = "Username" />
                <s:text name = "Password" />
                <s:password name = "password" label = "Password" />
                <p><s:submit value = "Registar"/></p>
            </s:form>
        </c:when>
        <c:otherwise>
            <s:form action = "registar" method = "post">
                <s:text name = "Username" />
                <s:textfield name = "username" label = "Username" />
                <s:text name = "Password" />
                <s:password name = "password" label = "Password" />
                <p><s:submit value = "Registar"/></p>
            </s:form>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>