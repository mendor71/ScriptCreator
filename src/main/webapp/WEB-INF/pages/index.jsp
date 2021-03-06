<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
  <link href="${pageContext.request.contextPath}/resources/webix/codebase/webix.css" rel="stylesheet"/>
  <script src="${pageContext.request.contextPath}/resources/webix/codebase/webix.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/resources/webix/codebase/i18n/ru.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.min.js" type="text/javascript"></script>
  <title>Основное меню сервиса</title>
</head>
<body>
<div class="container" id="page_content" style="width: 100%; height: 100%;">
  <sec:authorize access="!isAuthenticated()">
    <c:redirect url="/login"/>">
  </sec:authorize>
  <div id="profile_container"></div>
</div>
</body>
</html>
<style>

</style>
<script>
    webix.ui({
        id: "index_page"
        , container: "page_content"
        , rows: [
            {type: "toolbar", cols:[
                {}
                ,{view: "label", label: "Вас приветствует сервис %ServiceName%!", align: "center"}
                ,{}
            ]}
            ,{height: 25}
            ,{
                cols: [
                    {}
                    ,{rows: [
                        {view: "button", value: "Работа со сценариями", click: function () {
                          webix.send("${pageContext.request.contextPath}/menu/scenarios", null, "GET");
                        }},
                        {view: "button", value: "Работа с категориями", click: function () {
                            webix.send("${pageContext.request.contextPath}/menu/categories", null, "GET");
                        }}
                        /*,{view: "button", value: "Администрирование пользлователей", click: function () {
                            webix.send("${pageContext.request.contextPath}/menu/user_admin", null, "GET");
                        }}*/
                        ,{view: "button", value: "Выйти", click: function () {
                            webix.send("${pageContext.request.contextPath}/logout", null, "GET");
                        }}
                    ]}
                    ,{}
                ]
            }
        ]
    });

    webix.i18n.setLocale("ru-RU");
    webix.Date.startOnMonday=true;
</script>
