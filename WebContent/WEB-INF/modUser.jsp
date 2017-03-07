<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ page import = "javax.servlet.RequestDispatcher" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Modifica utente</title>
	</head>
	
	<body style="background-color:#eeeebb">
		
		<h2>Modifica utente</h2>
		<form action="ServletUser" method="POST">
			<b>Id:</b> ${param.id}	<br><br>
			<input type="hidden" name="id" value="${param.id}"/>
			<b>Nome:</b>  <input type="text" name="name" value="${param.nome}"/><br><br> 
			<b>Cognome:</b>  <input type="text" name="surname" value="${param.cognome}"/><br><br>
			<b>Data di nascita(aaaa-mm-gg):</b>  <input type="text" name="date" value="${param.data}"/><br><br> 
			<input type="hidden" name="action" value="update"/>
			<input type="submit" value="Invia modifica"/>		 	
		
		</form>

	</body>
</html>