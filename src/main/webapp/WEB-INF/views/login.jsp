<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Login - Forum</title>
    <link rel="stylesheet" href="<c:url value='/CSS/login.css'/>">
    
</head>
<body>
 <form action="/login" method="post">
   
    <h2>Login</h2>

    <c:if test="${not empty error}">
        <p style="color: red;">${error}</p>
    </c:if>

    
        <label>Username:</label>
        <input type="text" name="username" required />

        <label>Password:</label>
        <input type="password" name="password" required />

        <button type="submit">Login</button>
    

    <p>Don't have an account? <a href="/signup">Sign up</a></p>
    </form>
</body>
</html>
