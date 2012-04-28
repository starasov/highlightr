<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<h2>Streams Monitoring</h2>

<table>
    <tr>
        <th align="left">Stream</th>
        <th align="left">Statistics</th>
    </tr>
    <c:forEach var="s" items="${statistics}" varStatus="row">
        <c:set var="stream" value="${s.stream}"/>
        <c:set var="stats" value="${s.statistics}"/>
        <fmt:formatNumber var="statsAverage" value="${stats.avg}" minFractionDigits="2" maxFractionDigits="2"/>
        <fmt:formatNumber var="statsMin" value="${stats.min}" minFractionDigits="2" maxFractionDigits="2"/>
        <fmt:formatNumber var="statsMax" value="${stats.max}" minFractionDigits="2" maxFractionDigits="2"/>

        <tr>
            <td>${stream.identifier}</td>
            <td>avg: ${statsAverage}, min: ${statsMin}, max: ${statsMax}</td>
        </tr>
    </c:forEach>
</table>
