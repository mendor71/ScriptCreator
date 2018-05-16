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
                ,{view: "label", label: "Раздел работы с категориями", width: 450, align: "center"}
                ,{}
            ]}
            ,{height: 25}
            ,{
                cols: [
                    {width: 50}
                    , {view: "combo", id: "categoriesCombo", label: "Категории", options:[]}
                    , {}
                    , {view: "button", width: 200, value: "Добавить", click: function () {
                        $$("cat_id").setValue("");
                        $$("cat_name").setValue("");
                        $$("index_page").disable();
                        $$("cat_window").show();
                    }}
                    , {view: "button", width: 200, value: "Изменить", click: function () {
                        $$("cat_id").setValue($$("categoriesCombo").getValue());
                        $$("cat_name").setValue($$("categoriesCombo").getText());
                        $$("index_page").disable();
                        $$("cat_window").show();
                    }}
                    , {width: 50}
                ]
            }
        ]
    });

    webix.i18n.setLocale("ru-RU");
    webix.Date.startOnMonday=true;
    loadCategories();
    
    function loadCategories() {
        webix.ajax().sync().get("${pageContext.request.contextPath}/categories", null, {
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

    webix.ui({
        view: "window"
        , id: "cat_window"
        , width: 800
        , height: 450
        , position: "center"
        , head: {
            view: "toolbar", cols: [
                {view: "label", label: "Редактирование категории"}
                ,{view: "button", value: "Закрыть", width: 100, click: function () {
                    $$("cat_window").hide();
                    $$("index_page").enable();
                }}
            ]
        }
        , body: {
            view: "form"
            , elements: [
                {view: "text", id: "cat_id", label: "ID категории", labelWidth: 195, readonly: true}
                ,{view: "text", id: "cat_name", label: "Наименование категории", labelWidth: 195}
                ,{cols: [
                    {},{view: "button", width: 150, value: "Сохранить", click: function() {
                        var cat;
                        if ($$("cat_id").getValue() === "") {cat = {catName: $$("cat_name").getValue()}}
                        else {cat = {catId: $$("cat_id").getValue(), catName: $$("cat_name").getValue()}}

                        if ($$("cat_id").getValue() === "") {
                            webix.ajax().headers({"Content-type": "application/json"}).sync().post("${pageContext.request.contextPath}/categories", cat
                                , {success: function (data, text, request) {
                                    webix.message(data.message);
                                },error: function (data, text, request) {
                                    webix.alert("Что-то пошло не так... Повторите попытку позже.");
                                }});
                        } else {
                            webix.ajax().headers({"Content-type": "application/json"}).sync().put("${pageContext.request.contextPath}/categories", cat
                                , {success: function (data, text, request) {
                                    webix.message(data.message);
                                },error: function (data, text, request) {
                                    webix.alert("Что-то пошло не так... Повторите попытку позже.");
                                }});
                        }
                    }},{}
                ]}
            ]
        }
    })
</script>
