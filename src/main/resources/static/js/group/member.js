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

    $('#register-btn').on('click', function(e){
        let formData = $('#register-form').serializeArray();
        let param = {};
        formData.forEach(function(item) {
            param[item.name] = item.value;
        });

        console.log(param)
        $.ajax({
            type: "POST",
            url: `/api/group/master/${$groupId}/invite`,
            contentType: "application/json",
            data: JSON.stringify(param),
            success: function(response){
                alert("매니저 추가가 완료되었습니다.")
                $('#register-modal').modal('hide');
                init();
            },
            error: function(xhr, status, error){
                console.error("에러 내용: ", error);
                alert("매니저 추가에 실패했습니다.\n관리자에게 문의하세요.")
            }
        });
    })

    $('#search-btn').on('click',function(e){
        let searchWindow = window.open('', '_blank', 'width=400,height=200');
        searchWindow.document.body.innerHTML = windowHTML;

        let flag = false;
        searchWindow.document.getElementById('find-btn').addEventListener('click', function() {
            let emailInput = searchWindow.document.querySelector('input[name="email"]').value;
            $.ajax({
                type: "GET",
                url: `/api/group/manage/${$groupId}/invite/users`,
                data: {
                    'email': emailInput
                },
                success: function(res){
                    console.log(res)
                    let resultDiv = searchWindow.document.getElementById('result');

                    let resultText = document.createTextNode(res.userName + " ("+res.email+") ");
                    resultDiv.appendChild(resultText);

                    let newButton = document.createElement('button');
                    newButton.innerHTML = "선택";
                    resultDiv.appendChild(newButton);


                    newButton.addEventListener('click', function(e){
                        if(confirm(res.userName + " ("+res.email+") 을 매니저로 추가하시겠습니까?")){
                            flag = true;
                            const registerForm = document.getElementById('register-form');
                            const emailInput = registerForm.querySelector('input[name="email"]');

                            emailInput.value = res.email;
                            searchWindow.close();
                        }else{
                            flag = false;
                        }
                    });

                },
                error: function(xhr, status, error){
                    console.log(error)
                    alert("존재하지 않는 회원입니다.");
                }
            });
        });

        // searchWindow.document.getElementById('cancel-btn').addEventListener('click', function() {
        //     searchWindow.close();
        // });

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
        '        <div id="result" style="padding-top:3px;"></div>';

})(jQuery);
