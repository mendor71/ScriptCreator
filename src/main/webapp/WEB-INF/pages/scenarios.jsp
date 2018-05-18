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
    var current_obj_type = "req";
    var current_element_id = -1;

    var next_items = [];
    var prev_items = [];

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
                                if (id !== "") {
                                    loadScenarioKernelRequests(id);
                                    $$("scenario_content_form").enable();
                                }
                            }
                        }}
                        , {width: 25}
                        , {view:"checkbox", id: "delCheckbox", label:"Показать удаленные", labelWidth:175, value:0, uncheckValue:0, checkValue:1, on: {
                            onChange: function (newV, oldV) {
                                loadScenarios(newV === 0);
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
            ,{id: "scenario_content_form", disabled: true, view: "form", elements: [
                {cols: [
                    {width: 200, rows: [
                        {height: 40}
                        ,{view: "button", value: "Добавить элемент", click: function () {
                            if (current_element_id === -1) {
                                if ($$("scenarioCombo").getValue() !== "") {
                                    setRequestEditWindow("", "", $$("scenarioCombo").getValue(), "");
                                } else {
                                    webix.alert("Выберите сценарий для изменения!");
                                }
                            } else {
                                if (current_obj_type === "req")
                                    setRequestEditWindow("", current_element_id, $$("scenarioCombo").getValue(), "");
                                else
                                    setResponseEditWindow("", current_element_id, $$("scenarioCombo").getValue(), "");
                            }
                        }}
                        ,{view: "button", value: "Изменить элемент"}
                        ,{view: "button", value: "Удалить элемент"}

                    ]}
                    ,{width: 25}
                    ,{rows: [
                        {view: "label", id: "list_label", align: "center", label: "Корневые вопросы", css: "header_label"}
                        , {view: "list", height: 250, id: "data_list", select: true, template: "#id#. #text#", on: {
                            onItemClick: function (id) {
                                saveCurrentContent(prev_items);
                                current_element_id = id;

                                var newLabel = current_obj_type === "req" ? "Ответы на: " : "Вопросы на: ";
                                newLabel += "\"" + $$("data_list").getItem(id).text + "\"";
                                $$("list_label").config.label = newLabel;
                                $$("list_label").refresh();
                                if (current_obj_type === "req")
                                    loadChildResponses(id);
                                else
                                    loadChildRequests(id);
                                current_obj_type = current_obj_type === "req" ? "resp" : "req";
                            }, onItemDblClick: function (id) {

                            }
                        }}
                        ,{height: 25}
                        ,{cols: [
                            {},{view: "button", label: "Шаг назад", width: 150, click: function () {
                                switchDataListContent(prev_items, next_items);
                            }}
                            ,{width: 25}
                            ,{view: "button", label: "К корневым", width: 150, click: function () {

                            }}
                            ,{width: 25}
                            ,{view: "button", label: "Шаг вперед", width: 150, click: function () {
                                switchDataListContent(next_items, prev_items);
                            }},{}
                        ]}
                    ]}
                ]}
            ]}
        ]
    });

    webix.i18n.setLocale("ru-RU");
    webix.Date.startOnMonday=true;

    loadScenarios(true);

    function switchDataListContent(extractFrom, appendIn) {
        var switch_to = extractFrom.pop();
        if (switch_to !== undefined) {
            saveCurrentContent(appendIn);
            current_element_id = switch_to.current_element_id;
            current_obj_type = switch_to.type;
            $$("list_label").config.label = switch_to.label;
            $$("list_label").refresh();
            $$("data_list").clearAll();
            for (var i = 0; i < switch_to.list.length; i++) {
                $$("data_list").add({id: switch_to.list[i].id, text: switch_to.list[i].text});
            }
        }
    }

    function saveCurrentContent(saveTo) {
        var prevlistContent = [];
        $$("data_list").data.each(function (obj) {
            prevlistContent.push({id: obj.id, text: obj.text});
        });
        var currentContent = {type: current_obj_type, label: $$("list_label").config.label, list: prevlistContent, current_element_id: current_element_id};
        saveTo.push(currentContent);
    }

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
        $$("data_list").clearAll();
        webix.ajax().sync().get("${pageContext.request.contextPath}/requests/scenario/" + scId, {kernel: true}, {
            success: function (data, text, request) {
                data = JSON.parse(data);
                for (var i = 0; i < data.length; i++)
                    $$("data_list").add({id: data[i].reqId, text: data[i].reqText});
            }, error: function (data, text, request) {
                webix.alert("Что-то пошло не так... Повторите попытку позже.");
            }
        })
    }

    function loadChildResponses(reqId) {
        $$("data_list").clearAll();
        webix.ajax().sync().get("${pageContext.request.contextPath}/responses/parent_request/" + reqId, null, {
            success: function (data, text, request) {
                data = JSON.parse(data);
                for (var i = 0; i < data.length; i++)
                    $$("data_list").add({id: data[i].respId, text: data[i].respText});
            }, error: function (data, text, request) {
                webix.alert("Что-то пошло не так... Повторите попытку позже.");
            }
        });
    }

    function loadParentResponses(reqId) {
        $$("data_list").clearAll();
        webix.ajax().sync().get("${pageContext.request.contextPath}/responses/parent_request/" + reqId, null, {
            success: function (data, text, request) {
                data = JSON.parse(data);
                for (var i = 0; i < data.length; i++)
                    $$("data_list").add({id: data[i].respId, text: data[i].respText});
            }, error: function (data, text, request) {
                webix.alert("Что-то пошло не так... Повторите попытку позже.");
            }
        });
    }

    function loadChildRequests(respId) {
        $$("data_list").clearAll();
        webix.ajax().sync().get("${pageContext.request.contextPath}/requests/parent_response/" + respId, null, {
            success: function (data, text, request) {
                data = JSON.parse(data);
                for (var i = 0; i < data.length; i++)
                    $$("data_list").add({id: data[i].reqId, text: data[i].reqText});
            }, error: function (data, text, request) {
                webix.alert("Что-то пошло не так... Повторите попытку позже.");
            }
        });
    }

    function loadParentRequests(respId) {
        $$("data_list").clearAll();
        webix.ajax().sync().get("${pageContext.request.contextPath}/requests/child_response/" + respId, null, {
            success: function (data, text, request) {
                data = JSON.parse(data);
                for (var i = 0; i < data.length; i++)
                    $$("data_list").add({id: data[i].reqId, text: data[i].reqText});
            }, error: function (data, text, request) {
                webix.alert("Что-то пошло не так... Повторите попытку позже.");
            }
        });
    }

    function addChildResponse(requestId, response) {
        webix.ajax().headers({"Content-type": "application/json"}).sync()
            .post("${pageContext.request.contextPath}/requests/" + requestId + "/child_response/", response
                , {success: function (data, text, request) {
                    data = JSON.parse(data);
                    webix.message("Изменения внесены успешно!");
                    $$("data_list").add({id: data.respId, text: data.respText});
                },error: function (text, data, request) {
                    webix.alert("Что-то пошло не так... Повторите попытку позже.");
            }});
    }

    function addChildRequest(responseId, request) {
        webix.ajax().headers({"Content-type": "application/json"}).sync()
            .post("${pageContext.request.contextPath}/responses/" + responseId + "/child_request/", request
                , {success: function (data, text, request) {
                    data = JSON.parse(data);
                    webix.message("Изменения внесены успешно!");
                    $$("data_list").add({id: data.reqId, text: data.reqText});
                },error: function (text, data, request) {
                    webix.alert("Что-то пошло не так... Повторите попытку позже.");
            }});
    }

    function addKernelRequest(request) {
        webix.ajax().headers({"Content-type": "application/json"}).sync().post("${pageContext.request.contextPath}/requests/", request
            , {success: function (data, text, request) {
                data = JSON.parse(data);
                webix.message("Изменения внесены успешно!");
                $$("data_list").add({id: data.reqId, text: data.reqText});
            },error: function (text, data, request) {
                webix.alert("Что-то пошло не так... Повторите попытку позже.");
            }});
    }

    function saveRequestChanges(reqId, reqScId, reqParentRespId, reqText) {
        var request = {reqScenario: {scId: reqScId}, reqText: reqText};
        if (reqId === "") {
            if (reqParentRespId !== "") {
                addChildRequest(reqParentRespId, request);
            } else {
                addKernelRequest(request);
            }
        } else {
            /* TODO update request */
        }

        $$("index_page").enable();
        $$("req_edit_window").hide();
    }

    function saveResponseChanges(respId, respScId, respParentReqId, respText) {
        var response = {respScenario: {scId: respScId}, respText: respText};
        if (respId === "") {
            addChildResponse(respParentReqId, response);
        } else {
            /* TODO update response */
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
