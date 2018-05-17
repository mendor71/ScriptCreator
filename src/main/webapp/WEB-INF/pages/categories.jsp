<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/resources/webix/codebase/webix.css" rel="stylesheet"/>
    <script src="${pageContext.request.contextPath}/resources/webix/codebase/webix.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/webix/codebase/i18n/ru.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.min.js" type="text/javascript"></script>
    <title>Создайте () категорию своей мечты!</title>
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
                    {width: 25}
                    , {view: "combo", id: "categoriesCombo", label: "Выберите категорию", labelWidth: "175", width: 500, options:[]}
                    , {width: 25}
                    , {view:"checkbox", id: "delCheckbox", label:"Показать удаленные", labelWidth:175, value:0, uncheckValue:0, checkValue:1, on: {
                        onChange: function (newV, oldV) {
                            loadCategories(newV === 0)
                        }
                    }}
                    ,{}
                    , {view: "button", width: 200, value: "Добавить", click: function () {
                        $$("cat_id").setValue("");
                        $$("cat_name").setValue("");
                        $$("index_page").disable();
                        $$("cat_window").show();
                    }}
                    , {view: "button", width: 200, value: "Изменить", click: function () {
                        if ($$("categoriesCombo").getValue() !== "") {
                            $$("cat_id").setValue($$("categoriesCombo").getValue());
                            $$("cat_name").setValue($$("categoriesCombo").getText());
                            $$("index_page").disable();
                            $$("cat_window").show();
                        } else {
                            webix.alert("Выберите категорию для изменения!");
                        }
                    }}, {view: "button", width: 200, value: "Удалить", type: "danger", click: function () {
                        if ($$("categoriesCombo").getValue() !== "") {
                            webix.confirm({text: "Вы уверены?", callback:function (result) {
                                if (result) {
                                    webix.ajax().sync().del("${pageContext.request.contextPath}/categories/" + $$("categoriesCombo").getValue(), null
                                        , {success: function (data, text, request) {
                                            webix.message("Изменения внесены успешно!");
                                            $$("categoriesCombo").getPopup().getList().remove($$("categoriesCombo").getValue());
                                        },error: function (text, data, request) {
                                            webix.alert("Что-то пошло не так... Повторите попытку позже.");
                                        }});
                                }
                            }})
                        } else {
                            webix.alert("Выберите категорию для удаления!");
                        }
                    }}
                    , {width: 25}
                ]
            }
        ]
    });

    webix.i18n.setLocale("ru-RU");
    webix.Date.startOnMonday=true;
    loadCategories(true);
    
    function loadCategories(justActive) {
        webix.ajax().sync().get("${pageContext.request.contextPath}/categories", {justActive: justActive}, {
            success: function (data, text, request) {
                var options = [];
                data = JSON.parse(data);
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
                                    webix.message("Изменения внесены успешно!");
                                    $$("cat_window").hide();
                                    $$("index_page").enable();
                                    $$("categoriesCombo").getPopup().getList().parse({id: JSON.parse(data).catId, value: JSON.parse(data).catName});
                                },error: function (text, data, request) {
                                    webix.alert("Что-то пошло не так... Повторите попытку позже.");
                                }});
                        } else {
                            webix.ajax().headers({"Content-type": "application/json"}).sync().put("${pageContext.request.contextPath}/categories/" + cat.catId, cat
                                , {success: function (data, text, request) {
                                    webix.message("Изменения внесены успешно!");
                                    $$("cat_window").hide();
                                    $$("index_page").enable();
                                    $$("categoriesCombo").getPopup().getList().remove(JSON.parse(data).catId);
                                    $$("categoriesCombo").getPopup().getList().parse({id: JSON.parse(data).catId, value: JSON.parse(data).catName});
                                },error: function (text, data, request) {
                                    webix.alert("Что-то пошло не так... Повторите попытку позже.");
                                }});
                        }
                    }},{}
                ]}
            ]
        }
    })
</script>
