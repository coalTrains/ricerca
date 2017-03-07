<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "javax.servlet.RequestDispatcher" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Ricerca Db</title>
	</head>
	
	<body style="background-color:#eeeebb">
	<table><tr><td width="600px">
		<div class="search" align="center">
		<h2>Ricerca utenti</h2>
		<br>
		<b><c:out value="${pResp} ${pUsr.id} "/></b> 
		<br><br> 
		<form action="ServletUser" method="GET" ><b>
			 Nome:  <input type="text" name="nome" value=" ${pUsr.name} "/><br><br> 
			 Cognome:  <input type="text" name="cognome" value=" ${pUsr.surname} "/><br><br>
			 Data di nascita (aaaa-mm-gg):  <input type="text" name="data" value=" ${pUsr.date} "/><br><br>
			 <input type="hidden" name="action" value="search"/>
			 <input type="submit" value="Ricerca"/></b>		 
			 <br><br><br><br>	
			 <b><c:out value="${newData}"/></b> 
			 
		</form> 
		</div>	
		</td>
		<td>
		
		<div class="Db-list">
		<h2>Lista utenti</h2>
		
		<table border="1"><tr>  <td><b>ID</b></td>  <td><b>Nome</b></td> <td><b>Cognome</b></td> <td><b>Data di nascita</b></td> </tr>
		
		<c:forEach items="${pUsers}" var="user">
		
			<tr>
				<td><c:out value="${user.id}"/></td>
            	<td><c:out value="${user.name}"/></td>
            	<td><c:out value="${user.surname}"/></td>	
				<td><c:out value="${user.date}"/></td>	
				
				<td>
				<form action="ServletUser" method="GET">
                	<input type="hidden" name="action" value="modUser"/>
                	<input type="hidden" name="id" value="${user.id}"/>
                	<input type="hidden" name="nome" value="${user.name}"/>
                	<input type="hidden" name="cognome" value="${user.surname}"/>
                	<input type="hidden" name="data" value="${user.date}"/>
       	      	    <input type="submit" value="Modifica"/>
                </form></td>
                
                <td><form action="ServletUser" method="POST">
                	<input type="hidden" name="action" value="delete"/>
                	<input type="hidden" name="id" value="${user.id}"/>
                	<input type="hidden" name="nome" value="${user.name}"/>
                	<input type="hidden" name="cognome" value="${user.surname}"/>
                	<input type="hidden" name="data" value="${user.date}"/>
       	      	    <input type="submit" value="Elimina"/>
                </form></td>
			</tr>
		</c:forEach>
		
		<tr>  <td> 
			
				<form action="ServletUser" method="GET">
                	<input type="hidden" name="action" value="addUser"/>
                	<input type="hidden" name="id" />
                	<input type="hidden" name="nome" />
                	<input type="hidden" name="cognome" />
                	<input type="hidden" name="data" />
       	      	    <input type="submit" value="Aggiungi nuovo utente"/>
                </form>
    	</td> </tr>
		</table>		
		</div>
		</td></tr>
		</table>
	</body>

</html>