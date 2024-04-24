
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
    let ajaxURL = `/api/event/${$groupId}/list`;

    let table = new Tabulator('#event-div', {
        layout: "fitDataStretch",
        placeholder: "데이터가 존재하지 않습니다.",
        pagination: "local",
        paginationSize: 10,
        index: "eventId",
        ajaxURL: ajaxURL,
        ajaxResponse: function(url, params, response) {
            // Log the response data to the console
            console.log('AJAX Response:', response);
            return response;
        },
        columns: [
            {title: "No.", field: "eventId"},
            {title: "이벤트명", field: "name", minWidth: 220},
            {title: "시작일시", field: "startAt", minWidth: 200, formatter: dateTimeFormatter},
            {title: "종료일시", field: "endAt", minWidth: 200, formatter: dateTimeFormatter},
            {title: "진행시간", field: "runTime", minWidth: 200},
            {title: "오픈여부", field: "status", minWidth: 180},
            {title: "", minWidth: 100, maxWidth: 120,  formatter:(cell, formatterParams, onRendered) => {
                const rowData = cell.getRow().getData();
                let button = document.createElement("button");
                button.textContent = "오픈하기";
                button.className = "detail-button btn btn-sm btn-primary";

                if(rowData.status != '준비중' || rowData.content == null){
                    button.disabled = true
                }

                button.addEventListener("click", function() {
                    $.ajax({
                        type: "GET",
                        url: `/api/group/event/${rowData.eventId}/open`,
                        contentType: "application/json",
                        success: function(response){
                            console.log("요청이 성공했습니다.");
                            console.log("서버로부터의 응답: ", response);
                        },
                        error: function(xhr, status, error){
                            console.log(xhr)
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
                    const rowData = cell.getRow().getData();
                    console.log("Detail button clicked for row:", rowData);

                    window.location.href = `/api/group/${$groupId}/event/${rowData.eventId}/detail`;
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
                    $.ajax({
                        type: "GET",
                        url: `/api/group/event/${rowData.eventId}/delete`,
                        success: function(response){
                            console.log("요청이 성공했습니다.");
                            console.log("서버로부터의 응답: ", response);
                            reloadData();
                        },
                        error: function(xhr, status, error){
                            console.error("요청이 실패했습니다.");
                            console.error("에러 내용: ", error);
                        }
                    })
                });
                // Return the button element
                return button;
            }}
        ]
    });

    $('#datetimepicker1').datetimepicker({
        format: 'Y.m.d H:i',
        minDate: new Date(),
    });

    $('#modify-datepicker1').datetimepicker({
        format: 'Y.m.d H:i',
        minDate: new Date(),
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
            data: JSON.stringify(param), // param 객체를 JSON 문자열로 변환하여 전송
            success: function(response){
                console.log("요청이 성공했습니다.");
                console.log("서버로부터의 응답: ", response);

                $('#register-modal').modal('hide');
                $('#register-form')[0].reset();
                reloadData();
            },
            error: function(xhr, status, error){
                // 요청이 실패했을 때의 동작을 정의합니다.
                console.error("요청이 실패했습니다.");
                console.error("에러 내용: ", error);
            }
        });
    }

    /* 이벤트 수정 함수 */
    function modifyEvent(){
        console.log("event modify button click function");

        const eventId = $('#modify-modal').data('eventId');

        let formData = $('#modify-form').serializeArray();
        let param = {};
        formData.forEach(function(item) {
            param[item.name] = item.value;
        });

        console.log(param);

        let api1 = $.ajax({
            type: "POST",
            url: `/api/group/event/${eventId}/basic`,
            contentType: "application/json",
            data: JSON.stringify(param),
            success: function(response){
                // 성공했을 때의 동작을 정의합니다.
                console.log("요청이 성공했습니다.");
                console.log("서버로부터의 응답: ", response);
            },
            error: function(xhr, status, error) {
                // 실패했을 때의 동작을 정의합니다.
                console.error("요청이 실패했습니다.");
                console.error("에러 내용: ", error);
            }
        });

        let api2 = $.ajax({
            type: "POST",
            url: `/api/group/event/${eventId}/details`,
            contentType: "application/json",
            data: JSON.stringify(param),
            success: function(response){
                // 성공했을 때의 동작을 정의합니다.
                console.log("요청이 성공했습니다.");
                console.log("서버로부터의 응답: ", response);
            },
            error: function(xhr, status, error) {
                // 실패했을 때의 동작을 정의합니다.
                console.error("요청이 실패했습니다.");
                console.error("에러 내용: ", error);
            }
        });

        $.when(api1, api2)
            .then(function(response1, response2) {

            // 첫 번째 AJAX 요청의 결과(response1)와 두 번째 AJAX 요청의 결과(response2)를 사용하여 작업 수행
            console.log("모든 요청이 성공적으로 완료되었습니다.");
            console.log("첫 번째 요청 결과: ", response1);
            console.log("두 번째 요청 결과: ", response2);

            $('#modify-modal').modal('hide');
            $('#modify-form')[0].reset();
            alert("success")
            reloadData();

            }).fail(function(xhr, status, error){
                // 하나 이상의 AJAX 요청이 실패한 경우에 대한 처리
                console.error("하나 이상의 요청이 실패했습니다.");
                console.error("에러 내용: ", error);

                console.log(xhr.responseText);
                let errorResponse = JSON.parse(xhr.responseText);
                let errorMessage = errorResponse.errors[0].defaultMessage;
                console.log(errorMessage);

                alert(errorMessage);

            });

    }

    function reloadData() {
        // AJAX 요청을 보내어 새로운 데이터를 가져옵니다.
        $.ajax({
            type: "GET",
            url: ajaxURL,
            success: function(response) {
                // 가져온 데이터를 테이블에 적용합니다.
                table.setData(response);
            },
            error: function(xhr, status, error) {
                console.error("요청이 실패했습니다.");
                console.error("에러 내용: ", error);

            }
        });
    }

    // function detailButtonFormatter(cell, formatterParams, onRendered) {
    //     let button = document.createElement("button");
    //     button.textContent = "오픈";
    //     button.className = "detail-button btn btn-sm btn-primary";
    //
    //     button.addEventListener("click", function() {
    //         const row = cell.getRow();
    //         const rowData = cell.getRow().getData();
    //         console.log("row:", row);
    //         console.log("Detail button clicked for row:", rowData);
    //
    //         $('#modify-form input[name="name"]').val(rowData.name);
    //         $('#modify-form input[name="content"]').val(rowData.content);
    //         $('#modify-form input[name="startAt"]').val(formatDate(rowData.startAt, true));  // Assuming you have a function to format date/time
    //         $('#modify-form input[name="endAt"]').val(formatDate(rowData.endAt, true)); // Assuming you have a function to format date/time
    //         $('#modify-form input[name="runTime"]').val(rowData.runTime);
    //         $('#modify-form select[name="status"]').val(rowData.status);
    //
    //         $('#modify-modal').modal('show');
    //
    //     });
    //
    //     return button;
    // }

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

        let formattedDate = year + "." + month + "." + day + " " + hours + ":" + minutes;
        if (includeSeconds) {
            formattedDate += ":" + seconds;
        }
        return formattedDate;
    }


    window.registerEvent = registerEvent;
    window.modifyEvent = modifyEvent;

});

