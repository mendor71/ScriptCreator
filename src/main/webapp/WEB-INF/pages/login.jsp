<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
  <link href="${pageContext.request.contextPath}/resources/webix/codebase/webix.css" rel="stylesheet"/>
  <script src="${pageContext.request.contextPath}/resources/webix/codebase/webix.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.min.js" type="text/javascript"></script>
  <title>Login Page</title>
</head>
<body>
</body>
</html>
<style>
  .my_style {
    font-size: 25px;
  }
  .login_error {
    font-size: 18px;
    margin-bottom: 5px;
    color: #b70f20;
  }
</style>

<script>
    webix.ui({
        rows: [
            {height: 50}
            , {cols: [
                {}
                ,{rows: [
                    {view: "label", label: "Добро пожаловать!", align: "center", css: "my_style"}
                    ,{view: "label", label: "${error}", align: "center", css: "login_error"}
                    ,{view: "form", id:"loginForm", width: 500, elements: [
                        {view: "text", id: "j_username", name: "j_username", label: "Введите логин", labelWidth: 150}
                        ,{view: "text", id: "j_password", name: "j_password", type: "password", label: "Введите пароль", labelWidth: 150 }
                        ,{cols: [
                            {}
                            ,{view: "button", value: "Войти в систему", type: "form", click: function () {
                                if ($$("loginForm").validate()) {
                                    webix.send("${pageContext.request.contextPath}/j_spring_security_check", $$("loginForm").getValues());
                                } else {
                                    webix.alert({text: "Логин или пароль не указаны!"});
                                }
                            }}
                        ]}
                    ],rules:{
                        j_username:webix.rules.isNotEmpty,
                        j_password:webix.rules.isNotEmpty
                    }}
                    ,{}
                ]}
                ,{}
            ]}
        ]
    });
</script>
