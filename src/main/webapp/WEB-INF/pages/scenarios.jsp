<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <link href="${pageContext.request.contextPath}/resources/webix/codebase/webix.css" rel="stylesheet"/>
    <script src="${pageContext.request.contextPath}/resources/webix/codebase/webix.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/webix/codebase/i18n/ru.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.min.js" type="text/javascript"></script>
    <title>Создайте (или удалите) сценарий своей мечты!</title>
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
    .header_label {
        font-size: 20px;
    }
</style>
<script>
    var current_req_id = -1;
    var current_resp_id = -1;

    webix.ui({
        id: "index_page"
        , container: "page_content"
        , rows: [
            {type: "toolbar", cols:[
                {view: "button", width: 100, type: "prev", icon: "home", align: "center", label: "В меню", click: function () {
                    webix.send("${pageContext.request.contextPath}/", null, "GET");
                }},{view: "label", label: "Раздел работы со сценариями", align: "center"}
            ]}
            ,{height: 25}
            , {view: "form", elements: [
                {
                    cols: [
                        {width: 25}
                        , {view: "combo", id: "scenarioCombo", label: "Выберите сценарий", labelWidth: "175", width: 500, options:[], on:{
                            onChange: function (id) {
                                if (id !== "")
                                    loadScenarioKernelRequests(id);
                            }
                        }}
                        , {width: 25}
                        , {view:"checkbox", id: "delCheckbox", label:"Показать удаленные", labelWidth:175, value:0, uncheckValue:0, checkValue:1, on: {
                            onChange: function (newV, oldV) {
                                loadScenarios(newV === 0)
                            }
                        }}
                        ,{}
                        , {view: "button", width: 200, value: "Добавить", click: function () {
                            setScenarioEditWindow("","");
                        }}
                        , {view: "button", width: 200, value: "Изменить", click: function () {
                            if ($$("scenarioCombo").getValue() !== "") {
                                setScenarioEditWindow($$("scenarioCombo").getValue(), $$("scenarioCombo").getText());
                            } else {
                                webix.alert("Выберите сценарий для изменения!");
                            }
                        }}, {view: "button", width: 200, value: "Удалить", type: "danger", click: function () {
                            if ($$("scenarioCombo").getValue() !== "") {
                                webix.confirm({text: "Вы уверены?", callback:function (result) {
                                    if (result) {
                                        webix.ajax().sync().del("${pageContext.request.contextPath}/scenarios/" + $$("scenarioCombo").getValue(), null
                                            , {success: function (data, text, request) {
                                                webix.message("Изменения внесены успешно!");
                                                if ($$("delCheckbox").getValue() === 0) {
                                                    $$("scenarioCombo").getPopup().getList().remove($$("scenarioCombo").getValue());
                                                }
                                            },error: function (text, data, request) {
                                                webix.alert("Что-то пошло не так... Повторите попытку позже.");
                                            }});
                                    }
                                }})
                            } else {
                                webix.alert("Выберите сценарий для удаления!");
                            }
                        }}
                        , {width: 25}
                    ]
                }
            ]}
            ,{height: 25}
            ,{view: "form", elements: [
                {cols:[
                    {rows: [
                        {cols: [
                            {view: "label", css: "header_label", label: "Вопросы", height: 50}
                            ,{view: "button", type: "form", label: "Добавить корневой", click: function () {
                                if ($$("scenarioCombo").getValue() !== "") {
                                    setRequestEditWindow("", "", $$("scenarioCombo").getValue(), "");
                                } else {
                                    webix.alert({type: "confirm-warning", text: "Для начала выберите сценарий!"});
                                }
                            }}
                            ,{view: "button", label: "Добавить дочерный ответ", click: function () {
                                if ($$("req_list").getSelectedId() !== "") {
                                    setResponseEditWindow("",$$("req_list").getSelectedId(),$$("scenarioCombo").getValue(),"");
                                } else {
                                    webix.alert({type: "confirm-warning", text: "Для начала выберите один из вопросов!"});
                                }
                            }}
                            ,{view: "button", type: "danger", label: "Удалить дочерный ответ", click: function () {

                            }}
                            ,{view: "button", label: "Родительские Ответы", click: function () {
                                if ($$("req_list").getSelectedId() !== "") {
                                    loadParentResponses($$("req_list").getSelectedId());
                                } else {
                                    webix.alert({type: "confirm-warning", text: "Для начала выберите один из вопросов!"});
                                }
                            }}
                        ]}
                        , {height: 15}
                        , {view: "list", id: "req_list", select: true, template: "#id#. #text#", on: {
                            onItemClick: function (id) {
                                loadChildResponses(id);
                            }, onItemDblClick: function (id) {
                                /*TODO update item*/
                            }
                        }}
                    ]}
                    ,{width: 50}
                    ,{rows: [
                        {cols: [
                            {view: "label", css: "header_label", label: "Ответы", height: 50}
                            ,{view: "button", label: "Добавить дочерный вопрос"}
                            ,{view: "button", type: "danger", label: "Удалить дочерный ответ"}
                            ,{view: "button", label: "Родительские Вопросы", click: function () {
                                if ($$("resp_list").getSelectedId() !== "") {
                                    loadParentRequests($$("resp_list").getSelectedId());
                                } else {
                                    webix.alert({type: "confirm-warning", text: "Для начала выберите один из ответов!"});
                                }
                            }}
                        ]}
                        , {height: 15}
                        ,{view: "list", id: "resp_list", select: true, template: "#id#. #text#", on: {
                            onItemClick: function (id) {
                                loadChildRequests(id)
                            }, onItemDblClick: function (id) {
                                /*TODO update item*/
                            }
                        }}
                    ]}

                ]}
            ]}
        ]
    });

    webix.i18n.setLocale("ru-RU");
    webix.Date.startOnMonday=true;

    loadScenarios(true);

    function setScenarioEditWindow(scId, scName) {
        $$("sc_id").setValue(scId);
        $$("sc_name").setValue(scName);
        $$("index_page").disable();
        $$("sc_window").show();
        loadCategories(true);
    }

    function setRequestEditWindow(reqId, reqParentRespId, reqScId, reqText) {
        $$("req_id").setValue(reqId);
        $$("req_parent_resp_id").setValue(reqParentRespId);
        $$("req_sc_id").setValue(reqScId);
        $$("req_text").setValue(reqText);
        $$("index_page").disable();
        $$("req_edit_window").show();
    }

    function setResponseEditWindow(respId, respParentReqId, respScId, respText) {
        $$("resp_id").setValue(respId);
        $$("resp_parent_req_id").setValue(respParentReqId);
        $$("resp_sc_id").setValue(respScId);
        $$("resp_text").setValue(respText);
        $$("index_page").disable();
        $$("resp_edit_window").show();
    }

    function loadScenarios(justActive) {
        webix.ajax().sync().get("${pageContext.request.contextPath}/scenarios", {justActive: justActive}, {
            success: function (data, text, request) {
                var options = [];
                data = JSON.parse(data);
                for (var i = 0; i < data.length; i++) {
                    options.push({id: data[i].scId, value: data[i].scName});
                }
                $$("scenarioCombo").getPopup().getList().clearAll();
                $$("scenarioCombo").getPopup().getList().parse(options);

            }, error: function (data, text, request) {
                webix.alert("Что-то пошло не так... Повторите попытку позже.");
            }
        })
    }

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

    function loadScenarioKernelRequests(scId) {
        $$("req_list").clearAll();
        webix.ajax().sync().get("${pageContext.request.contextPath}/requests/scenario/" + scId, {kernel: true}, {
            success: function (data, text, request) {
                data = JSON.parse(data);
                for (var i = 0; i < data.length; i++)
                    $$("req_list").add({id: data[i].reqId, text: data[i].reqText});
            }, error: function (data, text, request) {
                webix.alert("Что-то пошло не так... Повторите попытку позже.");
            }
        })
    }

    function loadChildResponses(reqId) {
        $$("resp_list").clearAll();
        webix.ajax().sync().get("${pageContext.request.contextPath}/responses/parent_request/" + reqId, null, {
            success: function (data, text, request) {
                data = JSON.parse(data);
                for (var i = 0; i < data.length; i++)
                    $$("resp_list").add({id: data[i].respId, text: data[i].respText});
            }, error: function (data, text, request) {
                webix.alert("Что-то пошло не так... Повторите попытку позже.");
            }
        });
    }

    function loadParentResponses(reqId) {
        $$("resp_list").clearAll();
        webix.ajax().sync().get("${pageContext.request.contextPath}/responses/parent_request/" + reqId, null, {
            success: function (data, text, request) {
                data = JSON.parse(data);
                for (var i = 0; i < data.length; i++)
                    $$("resp_list").add({id: data[i].respId, text: data[i].respText});
            }, error: function (data, text, request) {
                webix.alert("Что-то пошло не так... Повторите попытку позже.");
            }
        });
    }

    function loadChildRequests(respId) {
        $$("req_list").clearAll();
        webix.ajax().sync().get("${pageContext.request.contextPath}/requests/parent_response/" + respId, null, {
            success: function (data, text, request) {
                data = JSON.parse(data);
                for (var i = 0; i < data.length; i++)
                    $$("req_list").add({id: data[i].reqId, text: data[i].reqText});
            }, error: function (data, text, request) {
                webix.alert("Что-то пошло не так... Повторите попытку позже.");
            }
        });
    }

    function loadParentRequests(respId) {
        $$("req_list").clearAll();
        webix.ajax().sync().get("${pageContext.request.contextPath}/requests/child_response/" + respId, null, {
            success: function (data, text, request) {
                data = JSON.parse(data);
                for (var i = 0; i < data.length; i++)
                    $$("req_list").add({id: data[i].reqId, text: data[i].reqText});
            }, error: function (data, text, request) {
                webix.alert("Что-то пошло не так... Повторите попытку позже.");
            }
        });
    }

    function saveRequestChanges(reqId, reqScId, reqParentRespId, reqText) {
        var request;
        if (reqParentRespId !== "") {

        } else {
            request = {reqScenario: {scId: reqScId}, reqText: reqText};
            if (reqId === "") {
                webix.ajax().headers({"Content-type": "application/json"}).sync().post("${pageContext.request.contextPath}/requests", request
                    , {success: function (data, text, request) {
                        data = JSON.parse(data);
                        webix.message("Изменения внесены успешно!");
                        $$("req_list").add({id: data.reqId, text: data.reqText});
                    },error: function (text, data, request) {
                        webix.alert("Что-то пошло не так... Повторите попытку позже.");
                    }});


            } else {

            }
        }
        $$("index_page").enable();
        $$("req_edit_window").hide();
    }

    function saveResponseChanges(respId, respScId, respParentReqId, respText) {
        var response = {respScenario: {scId: respScId}, respText: respText};
        if (respId === "") {
            webix.ajax().headers({"Content-type": "application/json"}).sync().post("${pageContext.request.contextPath}/requests/" + respParentReqId + "/child_response", response
                , {success: function (data, text, request) {
                    data = JSON.parse(data);
                    webix.message("Изменения внесены успешно!");
                    $$("resp_list").add({id: data.respId, text: data.respText});
                },error: function (text, data, request) {
                    webix.alert("Что-то пошло не так... Повторите попытку позже.");
                }});
        }
        $$("index_page").enable();
        $$("resp_edit_window").hide();
    }

    webix.ui({
        view: "window"
        , id: "sc_window"
        , width: 800
        , height: 450
        , position: "center"
        , head: {
            view: "toolbar", cols: [
                {view: "label", label: "Редактирование сценариев"}
                ,{view: "button", value: "Закрыть", width: 100, click: function () {
                    $$("sc_window").hide();
                    $$("index_page").enable();
                }}
            ]
        }
        , body: {
            view: "form"
            , elements: [
                {view: "text", id: "sc_id", label: "ID сценария", labelWidth: 195, readonly: true}
                ,{view: "text", id: "sc_name", label: "Наименование сценария", labelWidth: 195}
                ,{view: "combo", id: "categoriesCombo", label: "Выберите категорию", labelWidth: "195", width: 500, options:[]}
                ,{cols: [
                    {},{view: "button", width: 150, value: "Сохранить", click: function() {
                        var sc;
                        if ($$("sc_id").getValue() === "") {sc = {scName: $$("sc_name").getValue(), scCatId: {catId: $$("categoriesCombo").getValue()}}}
                        else {sc = {scId: $$("sc_id").getValue(), scName: $$("sc_name").getValue(), scCatId: {catId: $$("categoriesCombo").getValue()}}}

                        if ($$("sc_id").getValue() === "") {
                            webix.ajax().headers({"Content-type": "application/json"}).sync().post("${pageContext.request.contextPath}/scenarios", sc
                                , {success: function (data, text, request) {
                                    webix.message("Изменения внесены успешно!");
                                    $$("sc_window").hide();
                                    $$("index_page").enable();
                                    $$("scenarioCombo").getPopup().getList().parse({id: JSON.parse(data).scId, value: JSON.parse(data).scName});
                                },error: function (text, data, request) {
                                    webix.alert("Что-то пошло не так... Повторите попытку позже.");
                                }});
                        } else {
                            webix.ajax().headers({"Content-type": "application/json"}).sync().put("${pageContext.request.contextPath}/scenarios/" + sc.scId, sc
                                , {success: function (data, text, request) {
                                    webix.message("Изменения внесены успешно!");
                                    $$("sc_window").hide();
                                    $$("index_page").enable();
                                    $$("scenarioCombo").getPopup().getList().remove(JSON.parse(data).scId);
                                    $$("scenarioCombo").getPopup().getList().parse({id: JSON.parse(data).scId, value: JSON.parse(data).scName});
                                },error: function (text, data, request) {
                                    webix.alert("Что-то пошло не так... Повторите попытку позже.");
                                }});
                        }
                    }},{}
                ]}
            ]
        }
    });
    
    webix.ui({
        view: "window"
        , id: "req_edit_window"
        , width: 800
        , height: 450
        , position: "center"
        , head: {
            view: "toolbar", cols: [
                {view: "label", label: "Редактировать вопрос"}
                ,{view: "button", value: "Закрыть", width: 100, click: function () {
                    $$("req_edit_window").hide();
                    $$("index_page").enable();
                }}
            ]
        }, body: {
            view: "form"
            , elements: [
                {view: "text", id: "req_id", label: "ID вопроса", labelWidth: 195, readonly: true}
                ,{view: "text", id: "req_sc_id", label: "ID сценария", labelWidth: 195, readonly: true}
                ,{view: "text", id: "req_parent_resp_id", label: "Родительский ответ", labelWidth: 195, readonly: true}
                ,{view: "text", id: "req_text", label: "Текст вопроса", labelWidth: 195}
                ,{cols: [
                    {},{view: "button", type: "form", value: "Сохранить", click: function () {
                        saveRequestChanges($$("req_id").getValue(), $$("req_sc_id").getValue(), $$("req_parent_resp_id").getValue(), $$("req_text").getValue())
                    }}
                ]}
            ]
        }
    });

    webix.ui({
        view: "window"
        , id: "resp_edit_window"
        , width: 800
        , height: 450
        , position: "center"
        , head: {
            view: "toolbar", cols: [
                {view: "label", label: "Редактировать ответ"}
                ,{view: "button", value: "Закрыть", width: 100, click: function () {
                    $$("resp_edit_window").hide();
                    $$("index_page").enable();
                }}
            ]
        }, body: {
            view: "form"
            , elements: [
                {view: "text", id: "resp_id", label: "ID ответа", labelWidth: 195, readonly: true}
                ,{view: "text", id: "resp_sc_id", label: "ID сценария", labelWidth: 195, readonly: true}
                ,{view: "text", id: "resp_parent_req_id", label: "Родительский вопрос", labelWidth: 195, readonly: true}
                ,{view: "text", id: "resp_text", label: "Текст ответа", labelWidth: 195}
                ,{cols: [
                    {},{view: "button", type: "form", value: "Сохранить", click: function () {
                        saveResponseChanges($$("resp_id").getValue(), $$("resp_sc_id").getValue(), $$("resp_parent_req_id").getValue(), $$("resp_text").getValue())
                    }}
                ]}
            ]
        }
    });
</script>
