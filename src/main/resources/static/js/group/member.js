(function($) {

    const $groupId = document.getElementById('groupIdContainer').getAttribute('data-group-id');

    $(document).ready(function() {
        init();
    });


    $('#update-btn').on('click',function(e){
        let formData = $('#modify-form').serializeArray();
        let param = {};
        formData.forEach(function(item) {
            param[item.name] = item.value;
        });

        $.ajax({
            type: "POST",
            url: `/api/group/master/${$groupId}/role`,
            contentType: "application/json",
            data: JSON.stringify(param),
            success: function(response){
                alert("매니저 권한 수정이 완료되었습니다.")
                init();
                $('#manager-modal').modal('hide');
            },
            error: function(xhr, status, error){
                console.error("에러 내용: ", error);
                alert("매니저 권한 수정에 실패했습니다.\n관리자에게 문의하세요.")
            }
        });
    });


    $('#search-btn').on('click',function(e){
        let searchWindow = window.open('', '_blank', 'width=400,height=200');

        searchWindow.document.body.innerHTML = windowHTML;

        // // 새로운 창이 로드되었을 때 이벤트를 처리합니다.
        // searchWindow.onload = function() {
        //     // 새로운 창의 DOM에 접근하여 검색 결과를 처리합니다.
        //     var searchForm = searchWindow.document.getElementById('search-form');
        //     searchForm.addEventListener('submit', function(event) {
        //         event.preventDefault();
        //         var searchResult = searchForm.querySelector('input[name="searchResult"]').value;
        //         // 검색 결과를 원하는 방식으로 처리합니다.
        //         document.getElementById('email').value = searchResult;
        //         // 새로운 창을 닫습니다.
        //         searchWindow.close();
        //     });
        // };

        searchWindow.document.getElementById('find-btn').addEventListener('click', function() {
            let emailInput = searchWindow.document.querySelector('input[name="email"]').value;
            console.log(emailInput)
            // 검색 결과를 표시합니다.


            $.ajax({
                type: "GET",
                url: `/api/group/manage/${$groupId}/invite/users`,
                data: {
                    'email': emailInput
                },
                success: function(response){
                    searchWindow.document.getElementById('result').innerHTML = '검색 결과: ' + emailInput;
                },
                error: function(xhr, status, error){
                    alert("존재하지 않는 회원입니다.");
                }
            })

        });

        searchWindow.document.getElementById('cancel-btn').addEventListener('click', function() {
            searchWindow.close();
        });

        searchWindow.document.getElementById('confirm-btn').addEventListener('click', function() {
            // 여기에 확인 버튼을 클릭했을 때 수행할 동작을 추가하세요.
        });

    })

    function init(){
        $.ajax({
            type: "GET",
            url: `/api/group/manage/${$groupId}/list`,
            success: function(response){
                console.log("매니저리스트", response);
                generateTable(response.groupUsers);
            },
            error: function(xhr, status, error){
                console.error("에러 내용: ", error);
            }
        })
    }

    function generateTable(data){
        let table = new Tabulator("#table", {
            layout: "fitDataStretch",
            placeholder: "데이터가 존재하지 않습니다.",
            pagination: "local",
            paginationSize: 10,
            ajaxURL: `/api/group/manage/${$groupId}/list`,
            ajaxResponse: function(url, params, response){
                console.log('AJAX Response:', response);
                return response.groupUsers;
            },
            columns: [
                {title: "role", field: "role", minWidth: 110},
                {title: "email", field: "email", minWidth: 200},
                {title: "name", field: "userName", minWidth: 200},
                {title: "phone", field: "phoneNumber", minWidth: 200},
                {title: "active status", field: "active", minWidth: 100},
                {title: "createdAt", field: "createdAt", minWidth: 190, formatter: function(cell, formatterParams, onRendered) {
                        let date = new Date(cell.getValue());
                        return date.getFullYear() + '-' +
                            ('0' + (date.getMonth() + 1)).slice(-2) + '-' +
                            ('0' + date.getDate()).slice(-2) + ' ' +
                            ('0' + date.getHours()).slice(-2) + ':' +
                            ('0' + date.getMinutes()).slice(-2) + ':' +
                            ('0' + date.getSeconds()).slice(-2);
                }},
                {title: "update", formatter:(cell, formatterParams, onRendered) => {
                        const rowData = cell.getRow().getData();
                        let button = document.createElement("button");
                        button.textContent = "권한변경";
                        button.className = "detail-button btn btn-sm btn-secondary";

                        button.addEventListener("click", function() {
                            console.log(rowData);

                            $('#userId').val(rowData.userId);
                            $('#email').val(rowData.email);

                            $('#role').val($('#role option').filter(function() {
                                return $(this).text() === rowData.role;
                            }).val());


                            $('#manager-modal').modal('show');
                        });

                        return button;
                }}
            ]
        })
    }

    const windowHTML = '<h4>회원찾기</h4>\n' +
        '        <div>\n' +
        '            <input type="text" name="email">\n' +
        '            <button id="find-btn">찾기</button>\n' +
        '        </div>\n' +
        '        <div id="result" style="padding-top:5px;"></div>\n' +
        '        <div style="padding-top:5px;">\n' +
        '            <button>취소</button>\n' +
        '            <button>확인</button>\n' +
        '        </div>'

})(jQuery);
