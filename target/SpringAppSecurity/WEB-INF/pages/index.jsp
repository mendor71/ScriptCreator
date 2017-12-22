<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
  <%--<link href="${pageContext.request.contextPath}/resources/css/bootstrap.css" rel="stylesheet"/>--%>

  <title>Index Page</title>
</head>
<body>
${text}
<div class="container">
  <h1>Spring Security test</h1>
  <sec:authorize access="!isAuthenticated()">
    <p><a class="btn btn-lg btn-success" href="<c:redirect url="/login"/>">ВОЙТИ</a></p>
    <%--<p><a class="btn btn-lg btn-success" href="<c:url value="/login"/>">ВОЙТИ</a></p>--%>
  </sec:authorize>
  <sec:authorize access="isAuthenticated()">
    <sec:authorize access="hasAnyRole('ADMIN', 'USER')">
      <p>Добро пожаловать: <sec:authentication property="principal"/>!</p>
      <p><a class="btn btn-lg btn-default" href="<c:url value="/users/"/>">Просмотр пользователей</a></p>
      <p><a class="btn btn-lg btn-danger" href="<c:url value="/logout"/>">ВЫЙТИ</a></p>
    </sec:authorize>
    <sec:authorize access="hasRole('PRE_AUTH')">
      <div class="container" style="width: 300px;">
        <c:url value="/j_spring_security_check" var="loginUrl"/>
        <form action="${loginUrl}" method="post">
          <h2 class="form-control-static">Please give up device uid and pin code</h2>
          <input type="text" class="form-control" name="j_username" placeholder="login" autofocus value="<sec:authentication property="principal"/>">
          <input type="password" class="form-control" name="j_password" placeholder="pin code" required="" value="" >
          <button class="btn btn-lg btn-block" type="submit">Sign IN</button>
        </form>
      </div>
    </sec:authorize>
  </sec:authorize>
</div>
</body>
</html>
