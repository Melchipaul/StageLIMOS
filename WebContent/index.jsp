<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>UDAF Optimizer</title>

        <link href="https://fonts.googleapis.com/css?family=Lato"
              rel="stylesheet">

        <link rel="stylesheet" href="css/styles.css">
        <script src="https://code.jquery.com/jquery-3.3.1.js"></script>
        <link rel="stylesheet" href="javascript/librairies/treant-js/Treant.css"
              type="text/css">
        <!--<script src="javascript/librairies/ckeditor/ckeditor.js"></script>-->
        <script src="javascript/librairies/treant-js/vendor/raphael.js"></script>
        <script src="javascript/librairies/treant-js/Treant.js"></script>
        <link rel=stylesheet
              href="javascript/librairies/codemirror/doc/docs.css">
        <link rel="stylesheet"
              href="javascript/librairies/codemirror/theme/cobalt.css">
        <link rel="stylesheet"
              href="javascript/librairies/codemirror/lib/codemirror.css">
        <script src="javascript/librairies/codemirror/lib/codemirror.js"></script>
        <script
        src="javascript/librairies/codemirror/addon/edit/matchbrackets.js"></script>
        <link rel="stylesheet"
              href="javascript/librairies/codemirror/addon/hint/show-hint.css">
        <script src="javascript/librairies/codemirror/addon/hint/show-hint.js"></script>
        <script src="javascript/librairies/codemirror/mode/clike/clike.js"></script>
        <script src="javascript/librairies/codemirror/keymap/sublime.js"></script>
        <link rel="stylesheet"
              href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
        <script
        src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
        <script src="javascript/javascript.js"></script>

    </head>
    <body onload="enableEditMode();" data-spy="scroll" data-target=".navbar"
          data-offset="60">
        <div id="modal-bg"></div>
        <div class="header">
            <div class="container">
                <div class="logo">
                    <img alt="Logo UDAF Optimizer" src="images/logo.png">
                </div>
                <div class="nav">
                    <ul>
                        <li><a href="pagesalphatrees.jsp" target="_blank">UDAF Alpha Trees</a></li>
                        <li><a href="#udfoptimizer">UDAF Optimizer</a></li>
                        <li><a href="#interactiveProgramm">UDAF Interpreter</a></li>
                        <li><a href="#contact">Contact us</a></li>
                        <li><a href="#help">Get Help</a></li>

                    </ul>
                </div>
            </div>
        </div>


        <div class="container">
            <div class="content">

                <section>
                    <center>
                        <div id="udfoptimizer">
                            <h1>UDAF Optimizer</h1>

                            <form>
                                <center>
                                    <div class="form-group">
                                        <div>
                                            <button type="button" class="btn btn-primary modal-click">Fx</button>
                                            <div id="myDrag">
                                                <iframe name="drag1" id="drag1" class="form-control"></iframe>
                                            </div>
                                            <textarea name="drag" id="drag">
       
                                            </textarea>
                                            <input class="btn btn-primary" type="button"
                                                   value="Simple Tree" name="simpleTree"
                                                   class="btn btn-default form-control" id="simpleTree" /> <input
                                                   class="btn btn-primary" type="button"
                                                   value="Terminating Tree" name="terminatingTree"
                                                   class="btn btn-default form-control" id="terminatingTree" />
                                            <input class="btn btn-primary" type="button"
                                                   value="Partial Tree" name="partialTree"
                                                   class="btn btn-default form-control" id="partialTree" /> <input
                                                   class="btn btn-primary" type="button" value="Modify"
                                                   class="btn btn-default form-control" id="modify" /> <input
                                                   class="btn btn-primary" type="button" value="Delete"
                                                   class="btn btn-default form-control" id="delete" /><br /> <span
                                                   id="error"></span>
                                        </div>
                                    </div>
                                </center>
                            </form>
                            <div id="modal-content">
                                <label for="functionname">Function name : </label>
                                <input type="text" name="functionname" class="form-control" id="functionname"/>
                                <iframe id="formulaContent" name="formulaContent"></iframe>
                                <table id="shwoCaractors" class="table table-dark table-striped">
                                    <tbody>
                                        <tr>
                                            <td>Unary Operator :</td>
                                            <td><button type="button" class="btn btn-primary"
                                                        onclick="showDiv('popWrite', 'writePop', '{}x');">{a}x</button></td>
                                            <td><button type="button" class="btn btn-primary"
                                                        onclick="insertText('formulaContent', 'log(x)<sub>2</sub>');">
                                                    log(x)<sub>2</sub>
                                                </button></td>
                                            <td><button type="button" class="btn btn-primary"
                                                        onclick="insertText('formulaContent', 'log(x)<sub>10</sub>');">
                                                    log(x)<sub>10</sub>
                                                </button></td>
                                            <td><button type="button" class="btn btn-primary"
                                                        onclick="insertText('formulaContent', 'ln(x)');">ln(x)</button></td>
                                            <td><button type="button" class="btn btn-primary"
                                                        onclick="showDiv('popWrite', 'writePop', '{}<sup>x</sup>');">
                                                    {a}<sup>x</sup>
                                                </button></td>
                                            <td><button type="button" class="btn btn-primary"
                                                        onclick="showDiv('popWrite', 'writePop', 'x<sup>{}</sup>');">
                                                    x<sup>{a}</sup>
                                                </button></td>
                                            <td><button type="button" class="btn btn-primary"
                                                        onclick="showDiv('popWrite', 'writePop', '{}');">{}</button></td>
                                        </tr>
                                        <tr>
                                            <td>Binary Operators :</td>
                                            <td><button type="button" class="btn btn-primary"
                                                        onclick="insertText('formulaContent', '+');">+</button></td>
                                            <td><button type="button" class="btn btn-primary"
                                                        onclick="insertText('formulaContent', '-');">-</button></td>
                                            <td><button type="button" class="btn btn-primary"
                                                        onclick="insertText('formulaContent', '*');">*</button></td>
                                            <td><button type="button" class="btn btn-primary"
                                                        onclick="insertText('formulaContent', '/');">/</button></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td>N-ary Operators :</td>
                                            <td><button type="button" class="btn btn-primary"
                                                        onclick="insertText('formulaContent', '&sum;');">&sum;</button></td>
                                            <td><button type="button" class="btn btn-primary"
                                                        onclick="insertText('formulaContent', '&prod;');">&prod;</button></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td>Functional composition :</td>
                                            <td><button type="button" class="btn btn-primary"
                                                        onclick="insertText('formulaContent', '&#8728;');">&#8728;</button></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td>Paranthesis :</td>
                                            <td><button type="button" class="btn btn-primary"
                                                        onclick="insertText('formulaContent', '(');">(</button></td>
                                            <td><button type="button" class="btn btn-primary"
                                                        onclick="insertText('formulaContent', ')');">)</button></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                            <td></td>
                                        </tr>
                                    </tbody>
                                </table>
                                <button type="button" class="btn btn-primary"
                                        onclick="insertText1();">Ok</button>
                                <span class="modal-close">X</span>
                            </div>

                            <center>
                                <div id="trees">
                                    <div id="optimizer" class="form-group">
                                        <div name="treenoptimizer" id="treenoptimizer">
                                            <h3>Simple Tree</h3>
                                            <div id="tree-simple" class="chart"></div>
                                        </div>
                                        <div name="treeoptimizer" id="treeoptimizer">
                                            <h3>Decomposed Tree</h3>
                                            <div id="tree-terminating" class="chart"></div>
                                            <div id="tree-partial0" class="chart"></div>
                                            <div id="tree-partial1" class="chart"></div>
                                            <div id="tree-partial2" class="chart"></div>
                                            <div id="tree-partial3" class="chart"></div>
                                            <div id="tree-partial4" class="chart"></div>
                                            <div id="tree-partial5" class="chart"></div>
                                            <div id="tree-partial6" class="chart"></div>
                                            <div id="tree-partial7" class="chart"></div>
                                            <div id="tree-partial8" class="chart"></div>
                                            <div id="tree-partial9" class="chart"></div>
                                            <div id="tree-partial10" class="chart"></div>
                                            <div id="tree-partial11" class="chart"></div>
                                            <div id="tree-partial12" class="chart"></div>
                                            <div id="tree-partial13" class="chart"></div>
                                            <div id="tree-partial14" class="chart"></div>
                                            <div id="tree-partial15" class="chart"></div>
                                            <div id="tree-partial16" class="chart"></div>
                                            <div id="tree-partial17" class="chart"></div>
                                            <div id="tree-partial19" class="chart"></div>
                                            <div id="tree-partial20" class="chart"></div>
                                            <div id="tree-partial21" class="chart"></div>
                                            <div id="tree-partial22" class="chart"></div>
                                            <div id="tree-partial23" class="chart"></div>
                                            <div id="tree-partial24" class="chart"></div>
                                            <div id="tree-partial25" class="chart"></div>
                                            <div id="tree-partial26" class="chart"></div>
                                            <div id="tree-partial27" class="chart"></div>
                                            <div id="tree-partial28" class="chart"></div>
                                            <div id="tree-partial29" class="chart"></div>
                                            <div id="tree-partial30" class="chart"></div>
                                            <div id="tree-partial31" class="chart"></div>
                                            <div id="tree-partial32" class="chart"></div>
                                            <div id="tree-partial33" class="chart"></div>
                                            <div id="tree-partial34" class="chart"></div>
                                            <div id="tree-partial35" class="chart"></div>
                                            <div id="tree-partial36" class="chart"></div>
                                            <div id="tree-partial37" class="chart"></div>
                                            <div id="tree-partial38" class="chart"></div>
                                            <div id="tree-partial39" class="chart"></div>
                                            <div id="tree-partial40" class="chart"></div>
                                            <div id="tree-partial41" class="chart"></div>
                                            <div id="tree-partial42" class="chart"></div>
                                            <div id="tree-partial43" class="chart"></div>
                                            <div id="tree-partial44" class="chart"></div>
                                            <div id="tree-partial45" class="chart"></div>
                                            <div id="tree-partial46" class="chart"></div>
                                            <div id="tree-partial47" class="chart"></div>
                                            <div id="tree-partial48" class="chart"></div>
                                            <div id="tree-partial49" class="chart"></div>
                                            <div id="tree-partial50" class="chart"></div>
                                            <div id="tree-partial51" class="chart"></div>
                                            <div id="tree-partial52" class="chart"></div>
                                            <div id="tree-partial53" class="chart"></div>
                                            <div id="tree-partial54" class="chart"></div>
                                            <div id="tree-partial55" class="chart"></div>
                                            <div id="tree-partial56" class="chart"></div>
                                            <div id="tree-partial57" class="chart"></div>
                                            <div id="tree-partial58" class="chart"></div>
                                            <div id="tree-partial59" class="chart"></div>
                                            <div id="tree-partial60" class="chart"></div>
                                            <div id="tree-partial61" class="chart"></div>
                                            <div id="tree-partial62" class="chart"></div>
                                            <div id="tree-partial63" class="chart"></div>
                                            <div id="tree-partial64" class="chart"></div>
                                            <div id="tree-partial65" class="chart"></div>
                                            <div id="tree-partial66" class="chart"></div>
                                            <div id="tree-partial67" class="chart"></div>
                                            <div id="tree-partial68" class="chart"></div>
                                            <div id="tree-partial69" class="chart"></div>
                                            <div id="tree-partial70" class="chart"></div>
                                            <div id="tree-partial71" class="chart"></div>
                                            <div id="tree-partial72" class="chart"></div>
                                            <div id="tree-partial73" class="chart"></div>
                                            <div id="tree-partial74" class="chart"></div>
                                            <div id="tree-partial75" class="chart"></div>
                                        </div>

                                    </div>
                            </center>
                        </div>
                    </center>
                </section>
                <section>
                    <center>
                        <div id="interactiveProgramm">
                            <h1>UDAF Interpreter</h1>
                            <script>
                                appendDiv();
                            </script>
                        </div>
                    </center>
                </section>

                <section>
                    <center>
                        <div id="contact">
                            <h1>Contact us</h1>
                            <div id="contactForm">
                                <form id="contact-form" method="POST" role="form">
                                    <center>
                                        <div class="form-group">
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <label for="firstname">First Name *</label> <input
                                                        type="text" id="firstname" name="firstname"
                                                        class="form-control" placeholder="Your first name">
                                                    <p class="comments"></p>
                                                </div>

                                                <div class="col-md-6">
                                                    <label for="name">Name *</label> <input type="text"
                                                                                            id="name" name="name" class="form-control"
                                                                                            placeholder="Your name">
                                                    <p class="comments"></p>
                                                </div>

                                                <div class="col-md-6">
                                                    <label for="email">Email *</label> <input type="email"
                                                                                              id="email" name="email" class="form-control"
                                                                                              placeholder="Your email">
                                                    <p class="comments"></p>
                                                </div>

                                                <div class="col-md-6">
                                                    <label for="phone">Phone number</label> <input type="tel"
                                                                                                   id="phone" name="phone" class="form-control"
                                                                                                   placeholder="Your phone number">
                                                    <p class="comments"></p>
                                                </div>

                                                <div class="col-md-12">
                                                    <label for="message">Message *</label>
                                                    <textarea id="message" name="message" class="form-control"
                                                              placeholder="Your message"></textarea>
                                                    <p class="comments"></p>
                                                </div>

                                                <div class="col-md-12">
                                                    <p>
                                                        <strong>* Those informations are required</strong>
                                                    </p>
                                                </div>

                                                <div class="col-md-12">
                                                    <input type="button" onclick="sendMail()" class="btn btn-primary" value="Send">
                                                </div>
                                            </div>


                                        </div>
                                    </center>
                                </form>
                            </div>
                        </div>
                    </center>
                </section>

                <section>
                    <center>
                        <div id="help">
                            <h1>Get Help</h1>

                        </div>
                    </center>
            </div>
        </div>
    <center>
        <footer>
            <a href="#udfoptimizer"> <span
                    class="glyphicon glyphicon-chevron-up"></span>
            </a>
            <h2>
                &copy; 2018-
                <script type="text/javascript">
                    var ladate = new Date();
                    document.write(ladate.getFullYear());
                </script>
                Laboratoire d&apos;Informatique, de Mod&eacute;lisation et
                d&apos;Optimisation des Syst&egrave;mes (LIMOS)
            </h2>
        </footer>
    </center>
    <div id="popWrite">
        <iframe name="writePop" id="writePop"></iframe>
        <button type="button" class="btn btn-primary"
                onclick='closePopWrite()'>Ok</button>
    </div>
</body>
</html>