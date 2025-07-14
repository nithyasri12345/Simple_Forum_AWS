<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Post - View</title>
    <link rel="stylesheet" href="<c:url value='/CSS/viewPost.css'/>">
</head>
<body>

<div class="container">

    <div class="post-container">
        <h2>${post.title}</h2>
        <p>${post.content}</p>

        <p><strong>Posted by:</strong> ${post.author.username}</p>

        <p><strong>Status:</strong>
            <c:choose>
                <c:when test="${post.resolved}">
                    ✅ Resolved
                </c:when>
                <c:otherwise>
                    ❌ Not Resolved
                </c:otherwise>
            </c:choose>
        </p>

        <c:if test="${sessionScope.user.id == post.author.id}">
            <form action="/posts/${post.id}/resolve" method="post">
                <button type="submit" class="btn resolve-btn">Mark as Resolved</button>
            </form>
        </c:if>
    </div>

    <hr>

    <!-- Comments -->
    <div class="comment-section">
        <h3>Comments</h3>
        <c:forEach items="${comments}" var="comment">
            <div class="comment-box">
                <p><strong>${comment.commenter.username}:</strong> ${comment.content}</p>
            </div>
        </c:forEach>
    </div>

    <hr>

    <!-- Add Comment Form -->
    <c:if test="${not empty sessionScope.user}">
        <div class="form-container">
            <h4>Add Comment</h4>
            <form action="/posts/${post.id}/comments" method="post">
                <textarea name="content" rows="6" cols="60" placeholder="Write your comment here..." required></textarea><br>
                <button type="submit" class="btn">Add Comment</button>
            </form>
        </div>
    </c:if>

    <c:if test="${empty sessionScope.user}">
        <p><a href="/login">Login</a> to add a comment</p>
    </c:if>

    <p><a href="/home" class="btn back-btn">← Back to Home</a></p>

</div>

</body>
</html>
