<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%String username = (String)session.getAttribute("username"); %>
<%String userid = (String)session.getAttribute("userinfoid"); %>
<%String password = (String)session.getAttribute("password"); %>
    Username:   <%= username %>
    Userid:     <%= userid %>
    Password:   <%= password %>
</body>
</html>