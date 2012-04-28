<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<html>
<head>
    <title><tiles:getAsString name="title"/></title>
</head>
<body>

<div>
    <h1>Highlight Administration</h1>
    <tiles:insertAttribute name="body"/>
</div>

</body>
</html>