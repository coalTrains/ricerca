<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "javax.servlet.RequestDispatcher" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
		<title>Aggiungi nuovo utente</title>
	</head>
	
	<body style="background-color:#eeeebb">
	
		<h2>Aggiungi nuovo utente</h2>
		
		<form action="ServletUser" method="POST">
			
			Nome:  <input type="text" name="nome"/><br><br> 
			Cognome:  <input type="text" name="cognome"/><br><br>
			Data di nascita(aaaa-mm-gg):  <input type="text" name="data"/><br><br>
			<input type="hidden" name="action" value="add"/>
			<input type="submit" value="Aggiungi"/>		 	
		
		</form>  
		
	</body>
</html>
