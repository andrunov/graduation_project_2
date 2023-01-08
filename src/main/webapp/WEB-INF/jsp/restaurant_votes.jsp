<%--shows restaurants--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<fmt:setBundle basename="messages.app"/>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<script type="text/javascript" src="resources/js/datatableUtil.js" defer></script>
<script type="text/javascript" src="resources/js/restaurantVotesUtil.js" defer></script>

<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<%--restaurants datatable--%>
<div class="container">
    <div class="jumbotron">
        <div class="shadow">
            <h3><fmt:message key="orders.ofRestaurant"/>: ${restaurant.name}</h3>
            <table class="table" >
                <tr>
                    <td width="70%"></td>
                    <td width="10%" class="col-sm-1" align="right">
                        <label for="dateTimeFilter"><fmt:message key="common.dateFilter"/></label>
                    </td>
                    <td width="20%" class="col-sm-2">
                        <input class="form-control" type="text" id="dateTimeFilter" onchange="updateTableWithDate()"
                               placeholder="<fmt:message key="common.dateFilter"/>">
                    </td>
                </tr>
            </table>
            <div class="view-box">
                <table class="table table-hover table-bordered " id="votesDT">
                    <thead>
                    <tr>
                        <th></th>
                        <th><fmt:message key="common.dateTime"/></th>
                        <th><fmt:message key="users.name"/></th>
                        <th><fmt:message key="orders.content"/></th>
                        <th><fmt:message key="common.delete"/></th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>

<%--menuList modal show window--%>
<div class="modal fade" id="showMenuList">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3 class="modal-title"><fmt:message key="menuLists.content"/></h3>
                <h4 class="modal-title" id="modalTitleRestaurantShow"></h4>
                <h4 class="modal-title" id="modalTitleUserShow"></h4>
                <h5 class="modal-title" id="modalTitleDateTime"></h5>
            </div>
            <div class="modal-body">
                <table class="table table-hover table-bordered " id="menuListDTShow" style="width: 100%">
                    <thead>
                    <tr>
                        <th><fmt:message key="menuLists.description"/></th>
                        <th><fmt:message key="dishes.price"/></th>
                    </tr>
                    </thead>
                </table>
                <div class="span7 text-center">
                    <a class="btn btn-success" type="button" onclick="$('#showMenuList').modal('hide')">
                        <span class="glyphicon glyphicon-ok"></span>
                        <fmt:message key="common.ok"/>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>