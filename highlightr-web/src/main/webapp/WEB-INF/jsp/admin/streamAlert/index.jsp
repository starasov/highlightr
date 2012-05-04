<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<h2>Stream Alerts</h2>

<table>
    <tr>
        <th align="left">Stream</th>
        <th align="left">Timestamp</th>
    </tr>

    <c:forEach var="alert" items="${alertsPage.content}" varStatus="row">
        <tr>
            <td>${alert.stream.identifier}</td>
            <td>${alert.timestamp}</td>
        </tr>
    </c:forEach>
</table>

<div>
    <c:forEach begin="1" end="${alertsPage.totalPages}" var="i">
        <c:url var="pageUrl" value="/admin/stream-alert?page=${i}"/>
        <span><a href="${pageUrl}">${i}</a></span>
    </c:forEach>
</div>
