
// document.addEventListener("DOMContentLoaded", function(event) {
//     // const datatablesSimple = document.getElementById("datatablesSimple");
//     // if (datatablesSimple) {
//     //     new simpleDatatables.DataTable(datatablesSimple);
//     // }
//     let table = new Tabulator('#event-table', {
//         layout:"fitDataStretch",
//         placeholder:"데이터가 존재하지 않습니다.",
//         pagination:"local",
//         paginationSize: 10,
//         index: "eventId",
//         ajaxURL:"/api/group/"+$groupId+"/list",
//         columns: [
//             {title: "No.", field: "eventId"},
//             {title: "이벤트명", field: "name", minWidth: 250 },
//             {title: "시작일시", field: "startAt", minWidth: 250},
//             {title: "진행시간", field: "runTime", minWidth: 200},
//             {title: "오픈여부",field: "status", minWidth: 200},
//             {title: "등록일", field: "createdAt", minWidth: 200},
//             {title: "상세정보", minWidth: 100, maxWidth: 120}
//         ]
//     })
//
// });

$(document).ready(function(){

    const $groupId = document.getElementById('groupIdContainer').getAttribute('data-group-id');
    let ajaxURL = `/api/group/manage/${$groupId}/events`;

    let table = new Tabulator('#event-div', {
        layout: "fitDataStretch",
        placeholder: "데이터가 존재하지 않습니다.",
        pagination: "local",
        paginationSize: 10,
        index: "eventId",
        ajaxURL: `/api/group/manage/${$groupId}/events`,
        ajaxResponse: function(url, params, response) {
            // Log the response data to the console
            console.log('AJAX Response:', response);
            return response;
        },
        rowClick: function(e, row) {
            const rowData = row.getData();
            console.log("Row clicked:", rowData);

            // Populate the form fields with the retrieved data
            $('#modifyForm input[name="name"]').val(rowData.name);
            $('#modifyForm input[name="content"]').val(rowData.content);
            $('#modifyForm input[name="startAt"]').val(formatDate(rowData.startAt, true));  // Assuming you have a function to format date/time
            $('#modifyForm input[name="endAt"]').val(formatDate(rowData.endAt, true)); // Assuming you have a function to format date/time
            $('#modifyForm input[name="runTime"]').val(rowData.runTime);
            $('#modifyForm select[name="status"]').val(rowData.status);

            $('#modify-modal').modal('show');
        },
        columns: [
            {title: "No.", field: "eventId"},
            {title: "이벤트명", field: "name", minWidth: 220},
            {title: "시작일시", field: "startAt", minWidth: 200, formatter: dateTimeFormatter},
            {title: "종료일시", field: "endAt", minWidth: 200, formatter: dateTimeFormatter},
            {title: "진행시간", field: "runTime", minWidth: 200},
            {title: "오픈여부", field: "status", minWidth: 200},
            {title: "", minWidth: 100, maxWidth: 120,  formatter:(cell, formatterParams, onRendered) => {
                const rowData = cell.getRow().getData();

                let button = document.createElement("button");
                button.textContent = "오픈하기";
                button.className = "detail-button btn btn-sm btn-primary";

                if(rowData.status != '준비중'){
                    button.disabled = true
                }

                button.addEventListener("click", function() {
                    $.ajax({
                        type: "GET",
                        url: `/api/group/event/${rowData.eventId}/open`,
                        contentType: "application/json",
                        success: function(response){
                            // 요청이 성공했을 때의 동작을 정의합니다.
                            console.log("요청이 성공했습니다.");
                            console.log("서버로부터의 응답: ", response);
                            table.setData();
                        },
                        error: function(xhr, status, error){
                            // 요청이 실패했을 때의 동작을 정의합니다.
                            console.error("요청이 실패했습니다.");
                            console.error("에러 내용: ", error);
                        }
                    });
                });

                return button;
            }},
            {title: "수정", minWidth:60, maxWidth:65, formatter: (cell, formatterParams, onRendered) =>  {
                let button = document.createElement("button");
                button.textContent = "수정";
                button.className = "detail-button btn btn-sm btn-secondary";

                button.addEventListener("click", function() {
                    const row = cell.getRow();
                    const rowData = cell.getRow().getData();
                    console.log("row:", row);
                    console.log("Detail button clicked for row:", rowData);

                    $('#modifyForm input[name="name"]').val(rowData.name);
                    $('#modifyForm input[name="content"]').val(rowData.content);
                    $('#modifyForm input[name="startAt"]').val(formatDate(rowData.startAt, true));  // Assuming you have a function to format date/time
                    $('#modifyForm input[name="endAt"]').val(formatDate(rowData.endAt, true)); // Assuming you have a function to format date/time
                    $('#modifyForm input[name="runTime"]').val(rowData.runTime);
                    $('#modifyForm select[name="status"]').val(rowData.status);

                    $('#modify-modal').modal('show');

                });
                return button;
            }},
            {title: "삭제", minWidth:60, maxWidth:65, formatter: (cell, formatterParams, onRendered) => {
                let button = document.createElement("button");
                button.textContent = "삭제";
                button.className = "detail-button btn btn-sm btn-danger";

                button.addEventListener("click", function() {
                    const rowData = cell.getRow().getData();

                    console.log("delete button clicked for row:", rowData);
                    //TODO /api/group/{groupId}/event/{eventId}/delete ajax 요청하기
                });
                // Return the button element
                return button;
            }}
        ]
    });

    $('#datetimepicker1').datetimepicker({
        format:'Y-m-d H:i',
        minDate: new Date()
    });
    $('#datetimepicker2').datetimepicker({
        format:'Y-m-d H:i',
        minDate: new Date()
    });

    // 이벤트 등록 함수
    function registerEvent(){
        console.log("event register button click function");

        let param = {
            'name': $('#name').val(),
            'startAt': $('#datetimepicker1').val(),
            'endAt': $('#datetimepicker2').val(),
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

    function detailButtonFormatter(cell, formatterParams, onRendered) {
        let button = document.createElement("button");
        button.textContent = "오픈";
        button.className = "detail-button btn btn-sm btn-primary";

        button.addEventListener("click", function() {
            const row = cell.getRow();
            const rowData = cell.getRow().getData();
            console.log("row:", row);
            console.log("Detail button clicked for row:", rowData);

            $('#modifyForm input[name="name"]').val(rowData.name);
            $('#modifyForm input[name="content"]').val(rowData.content);
            $('#modifyForm input[name="startAt"]').val(formatDate(rowData.startAt, true));  // Assuming you have a function to format date/time
            $('#modifyForm input[name="endAt"]').val(formatDate(rowData.endAt, true)); // Assuming you have a function to format date/time
            $('#modifyForm input[name="runTime"]').val(rowData.runTime);
            $('#modifyForm select[name="status"]').val(rowData.status);

            $('#modify-modal').modal('show');

        });

        return button;
    }

    function dateTimeFormatter(cell, formatterParams, onRendered) {
        return formatDate(cell.getValue(), true);
    }

    function formatDate(dateTimeString, includeSeconds) {
        let date = new Date(dateTimeString);

        let year = date.getFullYear();
        let month = ('0' + (date.getMonth() + 1)).slice(-2);
        let day = ('0' + date.getDate()).slice(-2);
        let hours = ('0' + date.getHours()).slice(-2);
        let minutes = ('0' + date.getMinutes()).slice(-2);
        let seconds = ('0' + date.getSeconds()).slice(-2);

        let formattedDate = year + "-" + month + "-" + day + " " + hours + ":" + minutes;
        if (includeSeconds) {
            formattedDate += ":" + seconds;
        }
        return formattedDate;
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
