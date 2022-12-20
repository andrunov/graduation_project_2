<%@page isErrorPage="true" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>

<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="container">
    <div class="jumbotron">
        <br>
        <h4>Application error: </h4>
        <h2>${exception.message}</h2>
        <!--
<c:forEach items="${exception.stackTrace}" var="stackTrace">
    ${stackTrace}
</c:forEach>
-->
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>