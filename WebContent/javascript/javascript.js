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
    var functionName = document.getElementById('functionname').value;
    var expression = functionName + " = " + frame.contentDocument.body.innerHTML;
    frame1.contentWindow.document.write(expression);
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
        var functionName = $('#functionname').val();
        if (isEmpty(functionName)) {
            error = "Error : The function name is empty !";
            $('#error').html(error);
            $('#error').css("display", "inline-block");
            return false;
        } else if (isEmpty(drag)) {
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
                data: {drag: drag,
                    functionName: functionName},
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
//Pour l'affichage du Terminating Tree
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
        var functionName = $('#functionname').val();
        if (isEmpty(functionName)) {
            error = "Error : The function name is empty !";
            $('#error').html(error);
            $('#error').css("display", "inline-block");
            return false;
        } else if (isEmpty(drag)) {
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
//Pour affichage des arbres partiels
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
        var functionName = $('#functionname').val();
        if (isEmpty(functionName)) {
            error = "Error : The function name is empty !";
            $('#error').html(error);
            $('#error').css("display", "inline-block");
            return false;
        } else if (isEmpty(drag)) {
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

//Pour l'éditeur de code 
function appendDiv(identifiant, reponse, errorCode) {
    $("#interactiveProgramm").append('<div id=interactivForm class=interactive><input type=image src=images/execute.png class=execute onclick=appendDiv("MyInterpreter' + divNumber + '","MyInterpreterResponse' + divNumber + '","errorCode' + divNumber + '"); ><textarea  id="MyInterpreter' + divNumber + '" class="MyInterpreter form-control"></textarea><span id="errorCode' + divNumber + '"> </span><p id="MyInterpreterResponse' + divNumber + '"></p></div>');
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
    function isEmpty(myString) {
        return (myString.length === 0 || !myString.trim());
    }
    if (editor !== undefined) {

        var CodeMirrorString = editor.getValue();
        console.log(CodeMirrorString);
        if (isEmpty(CodeMirrorString)) {
            var error = "Error : Your code expression is empty !";
            $('#' + errorCode).html(error);
            $('#' + errorCode).css("display", "inline-block");
        } else {
            $('#' + errorCode).html("");
            $('#' + errorCode).css("display", "none");
            error = "";
            $.ajax({
                type: 'POST',
                url: 'ApacheLivyInteractiveApi',
                async: false,
                data: {myCode: CodeMirrorString,
                    session_id: session_id},
                success: function (resultat) {
                    var resultatOutput = JSON.parse(resultat);
                    document.getElementById(reponse).innerHTML = JSON.stringify(resultatOutput['output']);
                    console.log(JSON.stringify(resultatOutput['output']));
                    session_id = JSON.stringify(resultatOutput['session_id']);
                    console.log(session_id);

                }

            });
        }

    }
    divNumber++;

}

//Pour envoyer un email
function sendMail() {
    $('.comments').empty();
    var postdata = $('#contact-form').serialize();
    $.ajax({
        type: 'POST',
        url: 'SendMailServlet',
        data: postdata,
        dataType: 'json',
        success: function (result) {

            if (result.isSuccess)
            {

                $("#contact-form").append("<p class='thank-you'>Votre message a bien été envoyé. Merci de nous avoir contacté</p>");
                $("#contact-form")[0].reset();

            } else
            {
                $("#firstname + .comments").html(result.firstnameError);
                $("#name + .comments").html(result.nameError);
                $("#email + .comments").html(result.emailError);
                $("#phone + .comments").html(result.phoneError);
                $("#message + .comments").html(result.messageError);

            }

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("Status: " + textStatus);
            console.log("Error: " + errorThrown);
        }

    });
}
    