<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
  <%--<link href="${pageContext.request.contextPath}/resources/css/bootstrap.css" rel="stylesheet"/>--%>

  <title>Login Page</title>
</head>
<body>
  <div class="container" style="width: 300px;">
    <c:url value="/j_spring_security_check" var="loginUrl"/>
    <form action="${loginUrl}" method="post">
      <h2 class="form-control-static">Please sign in</h2>
      ${errText}
      <input type="text" class="form-control" name="j_username" placeholder="login" required autofocus value="">
      <br>
      <input type="password" class="form-control" name="j_password" placeholder="password" required="" value="" >
      <br>
      <button class="btn btn-lg btn-block" type="submit">Sign IN</button>
    </form>
  </div>
</body>
</html>
