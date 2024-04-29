$(document).ready(function(){

    const $groupId = document.getElementById('groupIdContainer').getAttribute('data-group-id');
    const $eventId = document.getElementById('eventIdContainer').getAttribute('data-event-id');
    const ajaxURL = `/api/group/event/${$eventId}/ticketItems`;

    console.log($groupId, $eventId)

    init();

    if ($('#freeTicket').is(':checked')) {
        $('input[name="approveType"][value="APPROVAL"]').prop('checked', true).prop('disabled', true);
        $('#price').val(0).prop('disabled', true);
    }
    $('input[name="approveType"]').prop('disabled', true);

    $('#datetimepicker1').datetimepicker({
        format: 'Y.m.d H:i',
        minDate: new Date(),
    });

    $('input[name="payType"]').change(function() {
        if ($('#freeTicket').is(':checked')) {
            $('input[name="approveType"]').prop('disabled', true);
            $('input[name="approveType"][value="APPROVAL"]').prop('checked', true).prop('disabled', true);
            $('#price').val(0).prop('disabled', true);
        } else {
            console.log("else")
            $('input[name="approveType"]').prop('disabled', false);
            $('#price').prop('disabled', false);
        }
    });

    /* 이벤트 수정 함수 */
    function modifyEvent(){
        console.log("event modify button click function");

        let formData = $('#modify-form').serializeArray();
        let param = {};
        formData.forEach(function(item) {
            param[item.name] = item.value;
        });

        let api1 = $.ajax({
            type: "POST",
            url: `/api/group/event/${$eventId}/basic`,
            contentType: "application/json",
            data: JSON.stringify(param),
            success: function(response){
                console.log("요청이 성공했습니다.");
                console.log("서버로부터의 응답: ", response);
            },
            error: function(xhr, status, error) {
                console.error("요청이 실패했습니다.");
                console.error("에러 내용: ", error);
            }
        });

        let api2 = $.ajax({
            type: "POST",
            url: `/api/group/event/${$eventId}/details`,
            contentType: "application/json",
            data: JSON.stringify(param),
            success: function(response){
                console.log("요청이 성공했습니다.");
                console.log("서버로부터의 응답: ", response);
            },
            error: function(xhr, status, error) {
                console.error("요청이 실패했습니다.");
                console.error("에러 내용: ", error);
            }
        });

        $.when(api1, api2)
            .then(function(response1, response2) {

            console.log("모든 요청이 성공적으로 완료되었습니다.");
            console.log("첫 번째 요청 결과: ", response1);
            console.log("두 번째 요청 결과: ", response2);

            $('#modify-form')[0].reset();
            alert("success");
            init();

            }).fail(function(xhr, status, error){
                // 하나 이상의 AJAX 요청이 실패한 경우에 대한 처리
                console.error("하나 이상의 요청이 실패했습니다.");
                console.error("에러 내용: ", error);

                let errorResponse = JSON.parse(xhr.responseText);
                let errorMessage = errorResponse.errors[0].defaultMessage;

                alert(errorMessage);
            });

    }


    function registerTicket(){
        console.log("ticket register button click function");

        let formData = $('#register-form').serializeArray();
        let param = {};

        formData.forEach(function(item) {
            param[item.name] = item.value;
        });

        param['approveType'] = $('input[name="approveType"]:checked').val();
        param['price'] = $('input[name="price"]').val();

        // if ($('#freeTicket').is(':checked')) {
        //     param['approveType'] = '승인';
        //     param['price'] = 0;
        // } else {
        //     param['approveType'] = $('input[name="approveType"]:checked').val();
        //     param['price'] = $('input[name="approveType"]:checked').val();
        // }

        // let formData = new FormData($('#register-form')[0]);
        // let data = {};
        //
        // // FormData 요소를 반복하면서 객체에 추가
        // for (let [name, value] of formData.entries()) {
        //     console.log(name, value)
        //     data[name] = value;
        // }
        // console.log(data)

        console.log(JSON.stringify(param))
        $.ajax({
            type: "POST",
            url: `/api/group/event/${$eventId}/ticketItems`,
            contentType: "application/json",
            data: JSON.stringify(param), // param 객체를 JSON 문자열로 변환하여 전송
            success: function(response){
                console.log("요청이 성공했습니다.");
                console.log("서버로부터의 응답: ", response);

                $('#register-modal').modal('hide');
                $('#register-form')[0].reset();
                alert("요청이 성공했습니다.");
                init();
            },
            error: function(xhr, status, error){
                // 요청이 실패했을 때의 동작을 정의합니다.
                console.error("요청이 실패했습니다.");
                console.error("에러 내용: ", error);
            }
        });
    }


    function setEventInfo() {
        $.ajax({
            type: "GET",
            url: `/api/event/${$groupId}/${$eventId}`,
            contentType: "application/json",
            success: function(res){
                console.log("set Evnet Info",res)
                $('#modify-form input[name="name"]').val(res.name);
                if (res.content!=null) {
                    $('#modify-form textarea[name="content"]').val(res.content);
                }
                if(res.address != null){
                    $('#modify-form input[name="address"]').val(res.address);
                }
                $('#modify-form input[name="startAt"]').val(formatDate(res.startAt, false));  // Assuming you have a function to format date/time
                $('#modify-form input[name="runTime"]').val(res.runTime);
                // $('#modify-form select[name="status"]').val(res.status);
                $('#modify-form select[name="status"] option').filter(function() {
                    return $(this).text() === res.status;
                }).prop('selected', true);

            },
            error: function(xhr, status, error){
                console.error("요청이 실패했습니다.");
                console.error("에러 내용: ", error);
            }
        });
    }

    function setTicketItemTable(){
        let table = new Tabulator('#ticket-div', {
            layout: "fitDataStretch",
            placeholder: "발급 티켓이 존재하지 않습니다.",
            pagination: "local",
            paginationSize: 10,
            index: "eventId",
            ajaxURL: `/api/group/event/${$eventId}/ticketItems/admin`,
            ajaxResponse: function(url, params, response) {
                // Log the response data to the console
                console.log("티켓 테이블 세팅")
                console.log('AJAX Response:', response);
                return response.ticketItems;
            },
            columns: [
                {title: "No.", field: "ticketItemId"},
                {title: "티켓명", field: "ticketName", minWidth: 250},
                {title: "설명", field: "description", minWidth: 300},
                {title: "지불타입", field: "payType", minWidth: 150},
                {title: "가격", field: "price", minWidth: 150},
                {title: "승인타입", field: "approveType", minWidth: 150},
                {title: "제한수량", field: "purchaseLimit", minWidth: 95},
                {title: "공급량", field: "supplyCount", minWidth: 95},
                {title: "재고", field: "quantity", minWidth: 95},
                {title: "삭제", minWidth:70, formatter: (cell, formatterParams, onRendered) => {
                        const rowData = cell.getRow().getData();

                        let button = document.createElement("button");
                        button.textContent = "삭제";
                        button.className = "detail-button btn btn-sm btn-danger";

                        // TODO
                        // rowData.quantity > 0 || rowData.isSold == true 일 때 버튼 disable
                        if (rowData.isSold) {
                            button.disabled = true;
                        }

                        button.addEventListener("click", function() {
                            console.log("delete button clicked for row:", rowData);

                            // 재고가 감소하지 않은 티켓만 삭제할 수 있다.
                            if(!rowData.isSold){
                                $.ajax({
                                    type: "POST",
                                    url: `/api/group/event/${$eventId}/ticketItems/${rowData.ticketItemId}`,
                                    success: function(response){
                                        alert("티켓이 삭제되었습니다.");
                                        console.log("요청이 성공했습니다.");
                                        console.log("서버로부터의 응답: ", response);
                                        setTicketItemTable();
                                    },
                                    error: function(xhr, status, error){
                                        console.error("요청이 실패했습니다.");
                                        console.error("에러 내용: ", error);
                                    }
                                })
                            }else{

                            }
                        });
                        // Return the button element
                        return button;
                    }}
            ]
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

    function init(){
        setEventInfo();
        setTicketItemTable();
    }

    window.registerTicket = registerTicket;
    window.modifyEvent = modifyEvent;

});

