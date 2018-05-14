<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/resources/webix/codebase/webix.css" rel="stylesheet"/>
    <script src="${pageContext.request.contextPath}/resources/webix/codebase/webix.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/webix/codebase/i18n/ru.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.min.js" type="text/javascript"></script>
    <title>Welcome to MoneyHelper!</title>
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
                ,{view: "label", label: "Раздел работы с категориями", align: "center"}
                ,{}
            ]}
            ,{height: 25}
            ,{
                cols: [
                    {width: 15}
                    , {view: "combo", id: "categoriesCombo", label: "Категории", options:[]}
                    , {width: 15}
                ]
            }
        ]
    });

    webix.i18n.setLocale("ru-RU");
    webix.Date.startOnMonday=true;
    loadCategories();
    
    function loadCategories() {
        webix.ajax().sync().get("${pageContext.request.contextPath}/categories/", null, {
            success: function (data, text, request) {
                var options = [];
                data =JSON.parse(data);
                for (var i = 0; i < data.length; i++) {
                    options.push({id: data[i].catId, value: data[i].catName});
                }
                $$("categoriesCombo").getPopup().getList().clearAll();
                $$("categoriesCombo").getPopup().getList().parse(options);

            }, error: function (data, text, request) {
                webix.alert("Что-то пошло не так... Повторите попытку позже.");
            }
        })
    }
</script>
