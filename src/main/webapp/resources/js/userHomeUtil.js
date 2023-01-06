/**
 * Class serves user_home.jsp
 * works with orders of specify user
 */

/*url for exchange JSON data between main form DataTable
 *represents orders and server*/
var ajaxUrl = 'ajax/votes/';

/*url for exchange JSON data between restaurant modal window DataTable (id="restaurantDT") and server*/
var ajaxRestaurantUrl = 'ajax/restaurants/';

/*url for exchange JSON data between menuList modal window DataTable (id="menuListDT") and server*/
var ajaxMenuItemsByRestaurantUrl = 'ajax/menuItems/currentByRestaurant/';

/*url for exchange JSON data between menuList modal window DataTable (id="menuListDT") and server*/
var ajaxMenuItemsByMenuListUrl = 'ajax/menuItems/byMenuList/';

/*variable links to main form DataTable represents orders in orders.jsp*/
var datatableApi;

/*variable links to orders.edit resource bundle */
var editTitleKey ="orders.edit";

/*variable links to orders.add resource bundle */
var addTitleKey ="orders.add";

/*function to update DataTable by data from server*/
function updateTableWithDate() {
    var date = $('#dateTimeFilter').val();
    $.get(ajaxUrl + "?dateKey=" + date, updateTableByData);
}

/*function to update DataTable by data from server*/
function updateTable() {
    $.get(ajaxUrl, updateTableByData);
}

/*function to update DataTable by data from server
 * with filter by date of orders*/
function updateTableDateFilter(date) {
    $.get(ajaxUrl + "?dateKey=" + date, updateTableByData);
}

/*DataTable represents orders in main form initialization*/
function votesDataTableInit() {
    datatableApi = $('#votesDT').DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": false,
        "searching": false,
        "info": true,
        "columns": [
            /*add column with image depending of Status*/
            {
                "orderable": false,
                "data": "dateTime",
                "render": renderVoteImage
            },
            {
                "data": "dateTime",
                "render": function (date, type, row) {
                    if (type === 'display') {
                        return formatDate(date);
                    }
                    return date;
                }
            },
            {
                "data": "restaurant",
                "render": function (date, type, row) {
                    return (date.name +', '+ date.address);
                }
            },
            {
                "orderable": false,
                "defaultContent": "",
                "className": "dt-center",
                "render": linkBtn
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
                1,
                "desc"
            ]
        ],
        "createdRow": "",
        "initComplete": makeEditable
    });
}

function renderVoteImage (data, type, row) {
    voteDateTime = new Date(data);
    now = new Date();
    startOfDay = new Date().setHours(0);
    elevenAM = new Date().setHours(11);
    thirteenAM = new Date().setHours(13);
    if (type === 'display') {
        if (voteDateTime >= startOfDay) {
            if (now < elevenAM) {
                return '<img  src="resources/pictures/accepted.png" />';
            } else if (now >= elevenAM && now < thirteenAM) {
                return '<img  src="resources/pictures/preparing.png" />';
            } else {
                return '<img  src="resources/pictures/ready.png" />';
            }
        } else {
            return '<img  src="resources/pictures/finished.png" />';
        }
    }
    return null;
}

/*DataTable represents restaurants in modal window initialization*/
function restaurantDataTableInit() {
    $('#restaurantDT').DataTable({
        "ajax": {
            "url": ajaxRestaurantUrl,
            "dataSrc": ""
        },
        "paging": false,
        "searching": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "address"
            },
            {
                "orderable": false,
                "defaultContent": "",
                "className": "dt-center",
                "render": selectRestaurantBtn
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ],
        "createdRow": ""
    });
}

/*DataTable represents MenuLists in modal window initialization*/
function menuItemsDataTableByRestaurant(id) {
    $('#menuListDT').DataTable({
        "ajax": {
            "url": ajaxMenuItemsByRestaurantUrl + id,
            "dataSrc": ""
        },
        "destroy": true,
        "paging": false,
        "searching": false,
        "info": true,
        "columns": [
            {
                "data": "dish",
                "render": function (date, type, row) {
                    return (date.name);
                }
            },
            {
                "data": "price",
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ],
        "createdRow": ""
    });
}

/*DataTable represents MenuLists in modal window initialization*/
function menuItemsDataTableByMenuList(id) {
    $('#menuListDTShow').DataTable({
        "ajax": {
            "url": ajaxMenuItemsByMenuListUrl + id,
            "dataSrc": ""
        },
        "destroy": true,
        "paging": false,
        "searching": false,
        "info": true,
        "columns": [
            {
                "data": "dish",
                "render": function (date, type, row) {
                    return (date.name);
                }
            },
            {
                "data": "price",
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ],
        "createdRow": ""
    });
}

/*document.ready function*/
$(function () {

    /*set cross-page variable "ordersDishesPostRedirectUrl" as 'home' for return
     * to page user_home.jsp after call POST-method in orders_dishes.jsp */
    localStorage.setItem("ordersDishesPostRedirectUrl",'home');

    /*dataTables initialization*/
    votesDataTableInit();
    restaurantDataTableInit();


    /*adjust Datetimepicker*/
    $.datetimepicker.setLocale(localeCode);
    $('#dateTime').datetimepicker({
        format: 'Y-m-d H:i'
    });
    $('#dateTimeFilter').datetimepicker({
        closeOnDateSelect: true,
        format: 'Y-m-d',
        timepicker: false,
        onChangeDateTime:function(dp,$input){
            updateTableDateFilter($input.val())
        }
    });
    /*add clear button to input field*/
    $('#dateTimeFilter').addClear({
        symbolClass: 'glyphicon glyphicon-remove',
        returnFocus: false,
        onClear: function () {
            updateTableDateFilter('');
        }
    });
});

/*function for link to orders_dishes.jsp*/
function linkBtn(data, type, row) {
    if (type == 'display') {
        restaurantTitle = row.restaurant.name+", "+row.restaurant.address;
        return '<a class="btn btn-primary" onclick="openMenuItemsByMenuList(' + row.menuList.id +',\''+ restaurantTitle +'\');">' +
            '<span class="glyphicon glyphicon-list-alt"></span></a>';
    }
}

/*function for begin procedure of order addition
 * 1-st step: open modal window of restaurant select*/
function addVote() {
    $('#selectRestaurant').modal();
}

/*render function draw button for restaurant selection
 * and finish 1-st step of order addition procedure*/
function selectRestaurantBtn(data, type, row) {
    if (type == 'display') {
        restaurantTitle = row.name+", "+row.address;
        return '<a class="btn btn-primary" onclick="openMenuItemsByRestaurant(' + row.id +',\''+ restaurantTitle +'\');">' +
            '<span class="glyphicon glyphicon-ok"></span></a>';
    }
}

/*function of 2-nd step of order addition
 * get restaurant by id from server and to memory it in server
 * open modal window for menu list selection
 * hide modal window of restaurant select*/
function openMenuItemsByRestaurant(id, restaurantTitle) {

    /*set html titles*/
    $('#modalTitleRestaurant').html(restaurantTitle);

    /*dataTable initialization*/
    menuItemsDataTableByRestaurant(id);

    /*open modal window for menu list selection
     * hide modal window of restaurant select*/
    $('#selectMenuList').modal();
    $('#selectRestaurant').modal('hide');
}

/*function of 2-nd step of order addition
 * get restaurant by id from server and to memory it in server
 * open modal window for menu list selection
 * hide modal window of restaurant select*/
function openMenuItemsByMenuList(id, restaurantTitle) {

    /*set html titles*/
    $('#modalTitleRestaurantShow').html(restaurantTitle);

    /*dataTable initialization*/
    menuItemsDataTableByMenuList(id);

    /*open modal window for menu list selection
     * hide modal window of restaurant select*/
    $('#showMenuList').modal();
}

/*render function draw button for delete row*/
function renderDeleteBtn(data, type, row) {
    if (type == 'display') {
        return '<a class="btn btn-danger" onclick="deleteRow(' + row.id +');">'+
            '<span class="glyphicon glyphicon-remove-circle"></span></a>';
    }
}

/*method to delete row
 * use in all forms*/
function deleteRow(id) {
    $.ajax({
        url: ajaxUrl + id ,
        type: 'DELETE',
        success: function () {
            updateTableWithDate();
        }
    });
}

function saveVote() {
    var form = $('#detailsForm');
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize(),
        success: function () {
            $('#selectMenuList').modal('hide');
            $('#selectRestaurant').modal('hide');
            updateTable();
        }
    });
}
