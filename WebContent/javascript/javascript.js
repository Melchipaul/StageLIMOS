var divNumber = 0;
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
function appendDiv(identifiant, response) {
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
        $.ajax({
            type: 'POST',
            dataType:'json',
            url: 'http://localhost:8998/',
            data: {kind: 'spark',
                   code: CodeMirrorString},
            success: function (resultat) {


                document.getElementById(response).innerHTML = resultat;


            }

        });
    }
    divNumber++;







    //A chercher

}