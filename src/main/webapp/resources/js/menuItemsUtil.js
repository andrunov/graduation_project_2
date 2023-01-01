/**
 * Class serves menuList.jsp
 * works with menuLists of specific restaurant
 */

/*url for exchange JSON data between DataTable and server*/
var ajaxUrl = 'ajax/menuItems/';

var ajaxDishUrl = 'ajax/dishes/';

/*url for exchange JSON data between main form DataTable
 *represents menu lists, and server, using filter by status*/
var ajaxUrlWithFilter = '/ajax/menuLists/filterByEnabled/';

/*url for link to order_by_dish.jsp*/
var goMenuItems = '/menuItems/';

/*variable links to DataTable represents menu lists in menuList.jsp*/
var datatableApi;

/*variable links to menuLists.edit resource bundle */
var editTitleKey ="dishes.edit";

/*variable links to menuLists.add resource bundle */
var addTitleKey ="menuLists.add";

var selectDishKey ="dishes.select";

/*variable for save current filter value*/
var currentFilterValue = "ALL";

/*function to update DataTable by data from server*/
function updateTable() {
     $.get(ajaxUrl, updateTableByData);
}

/*document.ready function*/
$(function () {
    datatableApi = $('#datatable').DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": false,
        "searching": false,
        "info": true,
        "columns": [
            /*add column with image depending of Enabled*/
            {
                "data": "name",
            },
            {
                "data": "price",
            },
            {
                "orderable": false,
                "defaultContent": "",
                "className": "dt-center",
                "render": renderEditMenuItemBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "className": "dt-center",
                "render": renderDeleteBtn
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ],
        /*customize row style depending of Enabled*/
        "createdRow": "",
        "initComplete": makeEditable
    });
    dishSelectApi = $('#dishdatatable').DataTable({
        "ajax": {
            "url": ajaxDishUrl,
            "dataSrc": ""
        },
        "paging": false,
        "searching": false,
        "info": true,
        "columns": [
            /*add column with image depending of Enabled*/
            {
                "data": "name",
            },
            {
                "orderable": false,
                "defaultContent": "",
                "className": "dt-center",
                "render": renderDeleteBtnWithFilter
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ],
    });
});

/*function for link to dishes.jsp*/
function linkBtn(data, type, row) {
    if (type == 'display') {
        return '<a class="btn btn-primary" onclick=location.href="'+ goMenuItems + row.id +'">' +
            '<span class="glyphicon glyphicon-list-alt"></span></a>';
    }
}

/*render function draw button for update row*/
function renderEditMenuItemBtn(data, type, row) {
    if (type == 'display') {
        return '<a class="btn btn-primary" onclick="updateRow(' + row.id + ');">' +
            '<span class="glyphicon glyphicon-time"></span></a>';
    }
}

/*method to update row in tables */
function updateRow(id) {
    $('#modalTitle').html(i18n[editTitleKey]);
    $.get(ajaxUrl + id, function (data) {
        $.each(data, function (key, value) {
             form.find("input[name='" + key + "']").val(value);
        });
        $('#editRow').modal();
    });
}

/*method to add new several entities in several forms*/
function selectDish() {
    $('#modalTitle2').html(i18n[selectDishKey]);
    $.get(ajaxDishUrl, function (data) {
        $.each(data, function (key, value) {
            form.find("input[name='" + key + "']").val(value);
        });
        $('#selectDish').modal();
    });
}
