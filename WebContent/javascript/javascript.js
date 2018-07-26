var divNumber = 0;
var session_id = -1;
var state1 = "starting";
var state2 = "";
var state_result = "";
var state_result1 = "";
var statement_id = -1;
var statement_id1 = -1;
var resultat;
var codeMirrorInstances = new Object();

function enableEditMode() {
    writePop.document.designMode = 'On';
    formulaContent.document.designMode = 'On';
}
function insertText(elemID, text)
{
    var elem = document.getElementById(elemID);
    elem.contentWindow.document.write(" " + text);
}
function  insertText1() {
    var frame = document.getElementById('formulaContent');
    var frame1 = document.getElementById('drag1');
    frame1.contentWindow.document.write(frame.contentDocument.body.innerHTML);
    document.getElementById('drag').value = frame.contentDocument.body.innerHTML;
    document.getElementById('modal-bg').style.display = 'none';
    document.getElementById('modal-content').style.display = 'none';
    document.getElementById('formulaContent').innerHTML = '';
}

function showDiv(elemID, textAreaDiv, text) {
    var elem = document.getElementById(elemID);
    var elemTextArea = document.getElementById(textAreaDiv);
    elem.style.display = "block";
    elemTextArea.value = text;
    var frame = document.getElementById('writePop');
    frame.contentWindow.document.write(text);
}

function closePopWrite() {
    var frame = document.getElementById('formulaContent');
    var frame1 = document.getElementById('writePop');
    var text = document.getElementById('writePop').value;
    text = frame1.contentDocument.body.innerHTML;
    text = " " + text;
    console.log(text);
    text = text.replace("{", "");
    text = text.replace("}", "");

    console.log(text);
    frame.contentWindow.document.write(text);
    document.getElementById('popWrite').style.display = 'none';
    frame1.contentWindow.document.body.innerHTML = "";
}

jQuery(document).ready(function ($) {
    $('.modal-click').click(function () {
        $('#modal-bg, #modal-content').css('display', 'block');
        $('#formulaContent').contents().find("body").html('');
        $('#drag1').contents().find("body").html('');
        $('#drag').contents().find("body").html('');
    });
    $('.modal-close, #modal-bg').click(function () {
        $('#modal-bg, #modal-content').css('display', 'none');
        $('#formulaContent').contents().find("body").html('');

    });

    $('#delete').click(function () {
        $('#drag1').contents().find("body").html('');
        $('#drag').contents().find("body").html('');
    });

    $('#modify').click(function () {
        var toModify = $('#drag1').contents().find("body").html();
        $('#modal-bg, #modal-content').css('display', 'block');
        $('#formulaContent').contents().find("body").html(toModify);
        $('#drag1').contents().find("body").html('');
        $('#drag').contents().find("body").html('');
    });
});


// Ajax pour Simple Tree et Tree optimized
$(document).ready(function () {
    $('#simpleTree').click(function () {

        function isEmpty(myString) {
            return (myString.length === 0 || !myString.trim());
        }
        function wellParenthesed(myString) {
            var stack = [];
            for (i = 0; i < myString.length; i++) {
                if (myString[i] === "(") {
                    stack.push("(");
                } else if (myString[i] === ")" && myString.length === 0) {
                    return false;
                } else if (myString[i] === ")") {
                    stack.pop();
                }

            }

            if (stack.length === 0) {
                return true;
            } else {
                return false;
            }
        }
        ;
        var drag = $('#drag').val();
        var error = "";
        if (isEmpty(drag)) {
            error = "Error : Your expression is empty !";
            $('#error').html(error);
            $('#error').css("display", "inline-block");
            return false;
        } else if (!drag.includes("∑") && !drag.includes("∏")) {
            error = "Error : Your expression must contain a N-ary operator !";
            $('#error').html(error);
            $('#error').css("display", "inline-block");
            return false;
        } else if (!wellParenthesed(drag)) {
            error = "Error : Your expression is not well parenthesed !";
            $('#error').html(error);
            $('#error').css("display", "inline-block");
            return false;
        } else {
            $('#error').html("");
            $('#error').css("display", "none");
            $.ajax({
                type: 'POST',
                url: 'TraitementsServlet',
                data: {drag: drag},
                success: function (resultat) {
                    $('#myJson').html(resultat);
                    myTreeJSON = resultat;
                    console.log(myTreeJSON);
                    var pourParser = JSON.parse(resultat);
                    var simple_chart_config = pourParser;
                    console.log(simple_chart_config);
                    new Treant(simple_chart_config);

                }

            });
        }
    });

    $('#terminatingTree').click(function () {

        function isEmpty(myString) {
            return (myString.length === 0 || !myString.trim());
        }
        function wellParenthesed(myString) {
            var stack = [];
            for (i = 0; i < myString.length; i++) {
                if (myString[i] === "(") {
                    stack.push("(");
                } else if (myString[i] === ")" && myString.length === 0) {
                    return false;
                } else if (myString[i] === ")") {
                    stack.pop();
                }

            }

            if (stack.length === 0) {
                return true;
            } else {
                return false;
            }
        }
        ;
        var drag = $('#drag').val();
        var error = "";
        if (isEmpty(drag)) {
            error = "Error : Your expression is empty !";
            $('#error').html(error);
            $('#error').css("display", "inline-block");
            return false;
        } else if (!drag.includes("∑") && !drag.includes("∏")) {
            error = "Error : Your expression must contain a N-ary operator !";
            $('#error').html(error);
            $('#error').css("display", "inline-block");
            return false;
        } else if (!wellParenthesed(drag)) {
            error = "Error : Your expression is not well parenthesed !";
            $('#error').html(error);
            $('#error').css("display", "inline-block");
            return false;
        } else {
            $('#error').html("");
            $('#error').css("display", "none");
            $.ajax({
                type: 'POST',
                url: 'TraitementsServletTerminating',
                data: {drag: drag},
                success: function (resultat) {
                    $('#myJson').html(resultat);
                    myTreeJSON = resultat;
                    console.log(myTreeJSON);
                    var pourParser = JSON.parse(resultat);
                    var simple_chart_config = pourParser;
                    console.log(simple_chart_config);
                    new Treant(simple_chart_config);

                }

            });
        }
    });

    $('#partialTree').click(function () {

        function isEmpty(myString) {
            return (myString.length === 0 || !myString.trim());
        }
        function wellParenthesed(myString) {
            var stack = [];
            for (i = 0; i < myString.length; i++) {
                if (myString[i] === "(") {
                    stack.push("(");
                } else if (myString[i] === ")" && myString.length === 0) {
                    return false;
                } else if (myString[i] === ")") {
                    stack.pop();
                }

            }

            if (stack.length === 0) {
                return true;
            } else {
                return false;
            }
        }
        ;
        var drag = $('#drag').val();
        var error = "";
        if (isEmpty(drag)) {
            error = "Error : Your expression is empty !";
            $('#error').html(error);
            $('#error').css("display", "inline-block");
            return false;
        } else if (!drag.includes("∑") && !drag.includes("∏")) {
            error = "Error : Your expression must contain a N-ary operator !";
            $('#error').html(error);
            $('#error').css("display", "inline-block");
            return false;
        } else if (!wellParenthesed(drag)) {
            error = "Error : Your expression is not well parenthesed !";
            $('#error').html(error);
            $('#error').css("display", "inline-block");
            return false;
        } else {
            $('#error').html("");
            $('#error').css("display", "none");
            $.ajax({
                type: 'POST',
                url: 'TraitementsServletPartialAgre',
                data: {drag: drag},
                success: function (resultat) {

                    var myTrees = resultat.split("|");

                    for (var myTree in myTrees) {
                        var myTree = JSON.parse(myTrees[myTree]);
                        new Treant(myTree);
                    }



                }

            });
        }
    });
});

//function dedent(text) {
//    var re_whitespace = /^([ \t]*)(.*)\n/gm;
//    var l, m, i;
//
//    while ((m = re_whitespace.exec(text)) !== null) {
//        if (!m[2]) continue;
//
//        if (l === m[1].length) {
//            i = (i !== undefined) ? Math.min(i, l) : l;
//        } else break;
//    }
//
//    if (i)
//        text = text.replace(new RegExp('^[ \t]{' + i + '}(.*\n)', 'gm'), '$1');
//
//    return text;
//}
function submit_code(session_id, code) {
    var settings = {
        "async": false,
        "crossDomain": true,
        "url": "http://Livy.net/sessions/" + session_id + "/statements",
        "method": "POST",
        "headers": {
            "Content-Type": "application/json",
            "X-Requested-By": "user",
            "Cache-Control": "no-cache"
        },
        "processData": false,
        "data": "{\"code\": \"" + code + "\"}"
    };

    $.ajax(settings).done(function (response2) {
        console.log("Je suis dans le done de submit");
        console.log(response2);
        var my_json_response2 = eval('(' + JSON.stringify(response2) + ')');
        statement_id1 = my_json_response2['id'];
        console.log(my_json_response2);
        console.log(statement_id1);
        console.log(state1);
        console.log(session_id);

    });
    console.log(statement_id1);
    return statement_id1;
}
function get_result(session_id, statement_id) {
    console.log(session_id);
    console.log(statement_id);
    var settings = {
        "async": false,
        "crossDomain": true,
        "url": "http://Livy.net/sessions/" + session_id + "/statements/" + statement_id + "",
        "method": "GET",
        "headers": {
            "Content-Type": "application/json",
            "X-Requested-By": "user",
            "Cache-Control": "no-cache"
        }
    };

    $.ajax(settings).done(function (response3) {
        console.log("Je suis dans le done de get_result");
        console.log(response3['state']);
        state_result = response3['state'];
        resultat = eval('(' + JSON.stringify(response3) + ')');
        console.log("Je quitte le done de get_result");
    });
    console.log(state_result);
    return state_result;
}
function get_idle(session_id) {

    var settings = {
        "async": false,
        "crossDomain": true,
        "url": "http://Livy.net/sessions/" + session_id + "",
        "method": "GET",
        "headers": {
            "Content-Type": "application/json",
            "X-Requested-By": "user",
            "Cache-Control": "no-cache"
        }
    };

    $.ajax(settings).done(function (response1) {
        console.log("Je suis dans le done de get_idle");
        console.log(response1);
        var my_json_response1 = eval('(' + JSON.stringify(response1) + ')');
        state2 = my_json_response1['state'];
        console.log("Je suis dans la fonction " + state2);

    });
    return state2;
}


function appendDiv(identifiant, reponse) {
    $("#interactiveProgramm").append('<div id=interactivForm class=interactive><input type=image src=images/execute.png class=execute onclick=appendDiv("MyInterpreter' + divNumber + '","MyInterpreterResponse' + divNumber + '"); ><textarea  id="MyInterpreter' + divNumber + '" class="MyInterpreter form-control"></textarea><p id="MyInterpreterResponse' + divNumber + '"></p></div>');
    var javaEditor = CodeMirror.fromTextArea(document.getElementsByClassName("MyInterpreter")[divNumber], {
        lineNumbers: true,
        matchBrackets: true,
        mode: "text/x-scala",
        keyMap: "sublime",
        autoCloseBrackets: true,
        showCursorWhenSelecting: true,
        theme: "cobalt",
        tabSize: 2
    });
    codeMirrorInstances["MyInterpreter" + divNumber] = javaEditor;
    var editor = codeMirrorInstances[identifiant];
    if (editor !== undefined) {

        var CodeMirrorString = editor.getValue();
        console.log(CodeMirrorString);
//         
//       var  host = 'http://localhost:8998';
//      var data = {'kind': 'spark'};
//  var headers = 'json';
//     var r = $.post(host, data);
//     document.getElementById(response).innerHTML = r;
//     console.log(r);

//        $.ajax({
//            type: 'POST',
//            contentType: "application/json; charset=utf-8",
//            dataType: 'json',
//            url: 'http://localhost:8998',
//            data: {kind: 'spark'},
//            success: function (resultat) {
//                document.getElementById(response).innerHTML = resultat;
//                console.log(resultat);
//
//            }
//
//        });



        if (session_id === -1) {

            var settings = {
                "async": false,
                "crossDomain": true,
                "url": "http://Livy.net/sessions",
                "method": "POST",
                "headers": {
                    "Content-Type": "application/json",
                    "X-Requested-By": "user",
                    "Cache-Control": "no-cache"
                },
                "processData": false,
                "data": "{\"kind\": \"spark\"}"
            };
            $.ajax(settings).done(function (response) {
                console.log("Je suis dans le done de main");
                var my_json_response = eval('(' + JSON.stringify(response) + ')');
                session_id = my_json_response['id'];


            });
            console.log("Voyons cette session ici!");
            console.log(session_id);
            const state = setInterval(function () {
                state1 = get_idle(session_id);
                if (state1 === "idle") {
                    statement_id = submit_code(session_id, CodeMirrorString);
                    clearInterval(state);
                    if (state1 === "idle" && session_id !== -1 && statement_id !== -1) {
                        console.log("J'entre ici???");
                        const state_result_waite = setInterval(function () {
                            state_result1 = get_result(session_id, statement_id);
                            if (state_result1 === "available") {
                                console.log("Je suis entré ici aussi");
                                console.log(state_result1);
                                console.log(resultat['output']);
                                document.getElementById(reponse).innerHTML = JSON.stringify(resultat['output']);
                                clearInterval(state_result_waite);
                            }
                        }, 1000);

                    }

                }


            },
                    1000);

        } else {
            console.log("Voyons cette session ici!");
            console.log(session_id);
            const state = setInterval(function () {
                state1 = get_idle(session_id);
                if (state1 === "idle") {
                    statement_id = submit_code(session_id, CodeMirrorString);
                    clearInterval(state);
                    if (state1 === "idle" && session_id !== -1 && statement_id !== -1) {
                        console.log("J'entre ici???");
                        const state_result_waite = setInterval(function () {
                            state_result1 = get_result(session_id, statement_id);
                            if (state_result1 === "available") {
                                console.log("Je suis entré ici aussi");
                                console.log(state_result1);
                                console.log(resultat['output']);
                                document.getElementById(reponse).innerHTML = JSON.stringify(resultat['output']);
                                clearInterval(state_result_waite);
                            }
                        }, 1000);

                    }

                }


            },
                    1000);
        }

    }
    divNumber++;








    //A chercher

}