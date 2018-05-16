<%--
  Created by IntelliJ IDEA.
  User: mendor71
  Date: 30.10.17
  Time: 14:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
  <script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.min.js" type="text/javascript"></script>
</head>
<body>
<p><input type="button" value="Create USER" id="user_create"/></p>

</body>
</html>
<script>
  $("#user_create").click(function () {
    $.ajax({
      type: 'post'
      , url: 'users/'
      , async: false
      , contentType: "application/json"
      , charset: "utf-8"
      , data: JSON.stringify({"userLogin":"tester","userPassword":"password", "userFirstName":"Тестер"})
      , 'response': 'json'
      , success: function(data) {
        alert(data)
      } , error: function(a, b, c) {
        alert(a + ' ' + b + ' ' + c)
      }
    });
  });
</script>
