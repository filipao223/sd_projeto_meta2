<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>DropMusic</title>
    <div align = "left" />
    <h2>Alterar Artista</h2>
</head>
<body>
<div align = "left">
    <s:form action = "alterarAlbum" method = "post">
        <s:text name = "Nome do Album" />
        <s:textfield name = "nome"></s:textfield>
        <s:text name = "Alterar nome" />
        <s:textfield name = "alterarNome"></s:textfield>
        <s:text name = "Alterar ano de criacao" />
        <s:textfield name = "alterarAno"></s:textfield>
        <s:text name = "Alterar tipo" />
        <s:textfield name = "alterarTipo"></s:textfield>
        <s:text name = "Alterar artista" />
        <s:textfield name = "alterarArtista"></s:textfield>
        <s:text name = "Alterar descricao" />
        <s:textfield name = "alterarDescricao"></s:textfield>
        <p><s:submit/></p>
    </s:form>
</div>

</body>
</html>
