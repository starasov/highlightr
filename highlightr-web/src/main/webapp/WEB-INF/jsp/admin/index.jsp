<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<c:url var="streamsUrl" value="/admin/stream"/>
<c:url var="cachesUrl" value="/admin/cache"/>

<h2><a href="${streamsUrl}">Streams Monitoring</a></h2>
<h2><a href="${cachesUrl}">Caches Monitoring</a></h2>