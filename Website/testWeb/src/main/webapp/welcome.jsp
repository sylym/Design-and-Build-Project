<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<%String username = (String)session.getAttribute("username"); %>
    welcome   <%= username %>
<form method="post" action="./wantchangepswrd"> 
	Enter 1 to change your password ; Enter 2 to check cars by userid;Enter 3 to check your information: <input type="text" name="userchoice"/><br/>
	<input type="SUBMIT" name="submit" value="Submit">
</form>

</html>