<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.min.js" type="text/javascript"></script>
    <title>Основное меню сервиса</title>
</head>
<body>
    <div id="main_block">

    </div>
</body>
</html>

<style>
    .main_tbl {
        border: 1px solid #ffffff;
        border-collapse: collapse;
    }
    .main_tbl td {
        border: 1px solid #000000;
    }
    .main_tbl th {
        border: 1px solid #000000;
    }
</style>


<script>
    load_data();

    function load_data() {
        $.ajax({
            type: "get"
            , url: "api/description"
            , success: function (data) {
                var body = document.getElementsByTagName('body')[0];

                for (var i = 0; i < data.length; i++) {
                    var table = document.createElement("table");

                    table.style.width = "1300px";
                    table.setAttribute("class", "main_tbl");

                    var tbody = document.createElement("tdoby");
                    var table_caption = document.createElement("caption");
                    var h2_caption = document.createElement("h2");
                    h2_caption.appendChild(document.createTextNode(data[i].controllerName));
                    table_caption.appendChild(h2_caption);

                    var tr_head = document.createElement("tr");

                    var th_url = document.createElement("th");
                    th_url.setAttribute("width","300px");
                    th_url.appendChild(document.createTextNode("url"));

                    var th_actionName = document.createElement("th");
                    th_actionName.setAttribute("width","300px");
                    th_actionName.appendChild(document.createTextNode("action"));

                    var th_method = document.createElement("th");
                    th_method.setAttribute("width","100px");
                    th_method.appendChild(document.createTextNode("method"));

                    var th_params = document.createElement("th");
                    th_params.setAttribute("width","600px");
                    th_params.appendChild(document.createTextNode("params"));

                    tr_head.appendChild(th_url);
                    tr_head.appendChild(th_actionName);
                    tr_head.appendChild(th_method);
                    tr_head.appendChild(th_params);

                    tbody.appendChild(tr_head);

                    var methods = data[i].methodList;
                    methods.sort(function (c_obj, o_obj) {
                        if (c_obj.method > o_obj.method) return 1;
                        if (c_obj.method < o_obj.method) return -1;
                        if (c_obj.method === o_obj.method) return 0;
                    });

                    for (var n = 0; n < methods.length; n++) {
                        var tr = document.createElement("tr");

                        var td_url = document.createElement("td");
                        td_url.appendChild(document.createTextNode(data[i].methods[n].url));

                        var td_action = document.createElement("td");
                        td_action.appendChild(document.createTextNode(data[i].methods[n].actionName));

                        var td_method = document.createElement("td");
                        td_method.appendChild(document.createTextNode(data[i].methods[n].method));

                        var td_params = document.createElement("td");
                        td_params.appendChild(document.createTextNode(data[i].methods[n].params));

                        tr.appendChild(td_url);
                        tr.appendChild(td_action);
                        tr.appendChild(td_method);
                        tr.appendChild(td_params);

                        tbody.appendChild(tr);
                    }

                    table.appendChild(table_caption);
                    table.appendChild(tbody);
                    body.appendChild(table);
                }
            }
        })
    }
</script>