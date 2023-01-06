<%--home page for users without ROLE_ADMIN--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<fmt:setBundle basename="messages.app"/>

<html>

<jsp:include page="fragments/headTag.jsp"/>

<script type="text/javascript" src="resources/js/datatableUtil.js" defer></script>
<script type="text/javascript" src="resources/js/userHomeUtil.js" defer></script>

<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="container">
    <div class="jumbotron">
        <div class="shadow">
            <h3><fmt:message key="common.welcome"/>, ${user.name}!</h3>
            <table class="table" >
                <tr>
                    <td>
                        <a class="btn btn-primary" type="button" onclick="addVote()">
                            <span class="glyphicon glyphicon-plus-sign"></span>
                            <fmt:message key="orders.add"/>
                        </a>
                    </td>
                    <td class="col-sm-1" align="right">
                        <label for="dateTimeFilter"><fmt:message key="common.dateFilter"/></label>
                    </td>
                    <td class="col-sm-2">
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
                        <th><fmt:message key="restaurants.nameAndAddress"/></th>
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

<%-- restaurant modal window--%>
<div class="modal fade" id="selectRestaurant">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title"> <fmt:message key="restaurants.select"/></h2>
            </div>
            <div class="modal-body">
                <table class="table table-hover table-bordered " id="restaurantDT" style="width: 100%">
                    <thead>
                    <tr>
                        <th><fmt:message key="restaurants.name"/></th>
                        <th><fmt:message key="restaurants.address"/></th>
                        <th><fmt:message key="common.select"/></th>
                    </tr>
                    </thead>
                </table>
                <div class="span7 text-center">
                    <a class="btn btn-success" type="button" onclick="$('#selectRestaurant').modal('hide')">
                        <span class="glyphicon glyphicon-remove"></span>
                        <fmt:message key="common.cancel"/>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>

<%--menuList modal select window--%>
<div class="modal fade" id="selectMenuList">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title"><fmt:message key="menuLists.select"/></h2>
                <h3 class="modal-title" id="modalTitleRestaurant"></h3>
            </div>
            <div class="modal-body">
                <table class="table table-hover table-bordered " id="menuListDT" style="width: 100%">
                    <thead>
                    <tr>
                        <th><fmt:message key="menuLists.description"/></th>
                        <th><fmt:message key="dishes.price"/></th>
                    </tr>
                    </thead>
                </table>
                <div class="span7 text-center">
                    <a class="btn btn-success" type="button" onclick="$('#selectMenuList').modal('hide')">
                        <span class="glyphicon glyphicon-remove"></span>
                        <fmt:message key="common.cancel"/>
                    </a>
                    <button type="button" onclick="saveVote()" class="btn btn-primary">
                        <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                        <fmt:message key="common.select"/>
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<%--menuList modal select window--%>
<div class="modal fade" id="showMenuList">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title"><fmt:message key="menuLists.select"/></h2>
                <h3 class="modal-title" id="modalTitleRestaurantShow"></h3>
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
