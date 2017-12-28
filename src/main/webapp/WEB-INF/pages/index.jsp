<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>

  <script src="${pageContext.request.contextPath}/resources/jquery-3.2.1.min.js" type="text/javascript"></script>

  <title>Index Page</title>
</head>
<body>
<div class="container">
  <h1>Script Creator Service</h1>
  <sec:authorize access="!isAuthenticated()">
    <p><a class="btn btn-lg btn-success" href="<c:redirect url="/login"/>">ВОЙТИ</a></p>
  </sec:authorize>
  <sec:authorize access="isAuthenticated()">
    <sec:authorize access="hasAnyRole('ADMIN', 'USER')">
      <p>Добро пожаловать: <sec:authentication property="principal"/>!</p>
      <div class="ur button"><a href="<c:url value="/users/"/>">Просмотр пользователей</a></div>
      <p><a class="btn btn-lg btn-danger" href="<c:url value="/logout"/>">ВЫЙТИ</a></p>
    </sec:authorize>
  </sec:authorize>
</div>
</body>
</html>
