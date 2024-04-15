document.addEventListener("DOMContentLoaded", function(event) {
    const datatablesSimple = document.getElementById("datatablesSimple");
    if (datatablesSimple) {
        new simpleDatatables.DataTable(datatablesSimple);
    }
});

$(document).ready(function(){

    $('#datetimepicker1').datetimepicker({
        format:'Y-m-d H:i',
        minDate: new Date()
    });
    // 이벤트 등록 함수
    function registerEvent(){
        console.log("event register button click function");

        let param = {
            'name': $('#name').val(),
            'startAt': $('#datetimepicker1').val(),
            'runTime': $('#runTime').val()
        };

        $.ajax({
            type: "POST",
            url: "/api/group/event/register",
            contentType: "application/json",
            data: JSON.stringify(param), // param 객체를 JSON 문자열로 변환하여 전송합니다.
            success: function(response){
                // 요청이 성공했을 때의 동작을 정의합니다.
                console.log("요청이 성공했습니다.");
                console.log("서버로부터의 응답: ", response);
            },
            error: function(xhr, status, error){
                // 요청이 실패했을 때의 동작을 정의합니다.
                console.error("요청이 실패했습니다.");
                console.error("에러 내용: ", error);
            }
        });
    }

    window.registerEvent = registerEvent;

});

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
