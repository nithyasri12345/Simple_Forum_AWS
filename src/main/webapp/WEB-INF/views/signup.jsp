<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Signup - Forum</title>
    <link rel="stylesheet" href="<c:url value='/CSS/signup.css'/>">
</head>
<body>

    <div class="form-container">
        <h2>Signup</h2>

        <c:if test="${not empty error}">
            <p style="color: red; text-align: center;">${error}</p>
        </c:if>

        <form action="/signup" method="post">
            <label>Username:</label>
            <input type="text" name="username" required />

            <label>Email:</label>
            <input type="email" name="email" required />

            <label>Password:</label>
            <input type="password" name="password" required />

            <button type="submit">Sign Up</button>
        </form>

        <p style="text-align: center; margin-top: 15px;">
            Already have an account? <a href="/login">Login</a>
        </p>
    </div>

</body>
</html>
