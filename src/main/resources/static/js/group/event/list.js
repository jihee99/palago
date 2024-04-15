document.addEventListener("DOMContentLoaded", function(event) {
    const datatablesSimple = document.getElementById("datatablesSimple");
    if (datatablesSimple) {
        new simpleDatatables.DataTable(datatablesSimple);
    }
});

// jQuery를 사용하여 datetimepicker 초기화
$(document).ready(function(){
    $('#datetimepicker1').datetimepicker();
});

// 이벤트 등록 함수
function registerEvent(){
    console.log("event register button click function");
}
// $(() => {
//     let $table = $('.listTbl').ajaxDataTable("/sys/company/companyList", data, {
//         columns: [
//             { "data": "rownum"},
//             { "data": "mstrUser" },
//             { "data": "cmpyRegNmbr" },
//             { "data": "cmpyCnt" },
//             { "data": "mstrEmail" },
//             { "data": "postAddr" },
//             { "data": "cmpyNm",
//                 "render": function(data, type, row, meta){
//                     let cmpyId = row.cmpyId;
//                     return '<a href="/sys/company/detail?cmpyId='+cmpyId+'" class="linkStyleNone">' + row.cmpyNm + '</a>';
//                 }
//             },
//         ],
//         columnDefs: [
//             { orderable: false, className: 'listTbl__tbody__td', targets: 0},
//             { orderable: false, className: 'listTbl__tbody__td', targets: 1},
//             { orderable: false, className: 'listTbl__tbody__td', targets: 2},
//             { orderable: false, className: 'listTbl__tbody__td', targets: 3},
//             { orderable: false, className: 'listTbl__tbody__td', targets: 4},
//             { orderable: false, className: 'listTbl__tbody__td', targets: 5},
//             { orderable: false, className: 'listTbl__tbody__td', targets: 6},
//             { orderable: false, className: 'listTbl__tbody__td', targets: 7},
//             { orderable: false, className: 'listTbl__tbody__td', targets: 8},
//             { orderable: false, className: 'listTbl__tbody__td', targets: 9},
//         ],
//     });
// });