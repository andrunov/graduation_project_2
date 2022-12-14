<%--shows menuLists of specify restaurant--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<fmt:setBundle basename="messages.app"/>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<script type="text/javascript" src="resources/js/datatableUtil.js" defer></script>
<script type="text/javascript" src="resources/js/menuItemsUtil.js" defer></script>

<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="container">
    <div class="jumbotron">
        <div class="shadow">
            <h3>${restaurant.name}, ${restaurant.address}</h3>
            <h2>${menuListTo.date}</h2>
            <p style="color: darkgreen"><fmt:message key="menuLists.list"/></p>
            <div class="view-box">
                <a class="btn btn-primary" type="button" onclick="add()">
                    <span class="glyphicon glyphicon-plus-sign"></span>
                    <fmt:message key="dishes.add"/>
                </a>
                <table class="table table-hover table-bordered " id="datatable">
                    <thead>
                    <tr>
                        <th><fmt:message key="dishes.description"/></th>
                        <th><fmt:message key="dishes.price"/></th>
                        <th><fmt:message key="common.update"/></th>
                        <th><fmt:message key="common.delete"/></th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>

<div class="modal fade" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <div class="span7 text-center">
                    <h2 class="modal-title" id="modalTitle"></h2>
                </div>

            </div>
            <div class="modal-body">
                <form class="form-horizontal" method="post" id="detailsForm">
                    <input type="text" hidden="hidden" id="id" name="id">
                    <input type="text" hidden="hidden" id="dishId" name="dishId">

                    <div class="form-group">
                        <label for="menuItemName" class="control-label col-xs-3"><fmt:message
                                key="dishes.description"/></label>

                        <div class="col-xs-9">
                            <input class="form-control" id="menuItemName" name="name" size="15" readonly="readonly"
                                   onclick="openSelectDishWindow()" placeholder="<fmt:message key="dishes.description"/>">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="menuItemPrice" class="control-label col-xs-3"><fmt:message
                                key="dishes.price"/></label>

                        <div class="col-xs-9">
                            <input class="form-control" id="menuItemPrice" name="price"
                                   placeholder="<fmt:message key="dishes.price"/>">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="span7 text-center">
                            <button class="btn btn-success" type="button" onclick="$('#editRow').modal('hide')">
                                <span class="glyphicon glyphicon-remove"></span>
                                <fmt:message key="common.cancel"/>
                            </button>
                            <a class="btn btn-primary" type="button" onclick="openSelectDishWindow()">
                                <span class="glyphicon glyphicon-time"></span>
                                <fmt:message key="dishes.select"/>
                            </a>
                            <button type="button" onclick="save()" class="btn btn-primary">
                                <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                                <fmt:message key="common.complete"/>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="selectDish">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <div class="span7 text-center">
                    <h2 class="modal-title" id="modalTitle2"></h2>
                </div>

            </div>
            <div class="modal-body">
                <form class="form-horizontal" method="post" id="detailsForm2">
                    <table width="100%" class="table table-hover table-bordered " id="dishdatatable">
                        <thead>
                        <tr>
                            <th width="90%"><fmt:message key="dishes.description"/></th>
                            <th width="10%"><fmt:message key="common.select"/></th>
                        </tr>
                        </thead>
                    </table>
                </form>
            </div>
        </div>
    </div>
</div>

</body>
</html>



