<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%String username = (String)session.getAttribute("username"); %>
<%String userid = (String)session.getAttribute("userinfoid"); %>
<%String password = (String)session.getAttribute("password"); %>
    yourusername= <%= username %><br/>
    youruserid=   <%= userid %><br/>
    yourpassword=   <%= password %><br/>
</body>
</html>