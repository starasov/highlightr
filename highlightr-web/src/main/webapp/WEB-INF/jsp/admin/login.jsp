<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Highlightr - Please Log In to Your Account</title>

    <link rel="stylesheet" type="text/css" media="all" href="/css/reset.css"/>
    <link rel="stylesheet" type="text/css" media="all" href="/css/text.css"/>
    <link rel="stylesheet" type="text/css" media="all" href="/css/960.css"/>
    <link rel="stylesheet" type="text/css" media="all" href="/css/ui-lightness/jquery-ui-1.8.19.custom.css"/>

    <script type="text/javascript" src="/js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="/js/jquery-ui-1.8.19.custom.min.js"></script>

    <script>
        $(function () {
            $("input:submit, button").button();
        });
    </script>
</head>
<body>
<div class="container_12">
    <div class="grid_12">
        <h2 style="color: #357ae8;">Highlightr - Administration</h2>
    </div>

    <div class="grid_12">
        <p>Please Log In to Your Account</p>

        <form action="j_spring_security_check" method="post">
            <fieldset>
                <label for="j_username" style="display: block;">Login</label>
                <input id="j_username" name="j_username" size="20" maxlength="50" type="text" class="text ui-widget-content ui-corner-all"/>
                <label for="j_password" style="display: block;">Password</label>
                <input id="j_password" name="j_password" size="20" maxlength="50" type="password" class="text ui-widget-content ui-corner-all"/>
            </fieldset>

            <input type="submit" value="Login"/>
        </form>
    </div>
</div>
</body>
</html>

