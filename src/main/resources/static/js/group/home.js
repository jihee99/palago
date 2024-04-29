(function($) {

    const $groupId = document.getElementById('groupIdContainer').getAttribute('data-group-id');

    $(document).ready(function() {
        init();
    });


    $('#save').on('click', (e) => {
        console.log()

        let formData = $('#group-form').serializeArray();
        let param = {};
        formData.forEach(function(item) {
            param[item.name] = item.value;
        });

        $.ajax({
            type: "POST",
            url: `/api/group/manage/${$groupId}/profile`,
            contentType: "application/json",
            data: JSON.stringify(param),
            success: function(response){
                console.log("요청이 성공했습니다.");
                console.log("서버로부터의 응답: ", response);
                alert("그룹 정보 수정에 성공했습니다.");
                init();
            },
            error: function(xhr, status, error) {
                console.error("요청이 실패했습니다.");
                console.error("에러 내용: ", error);
                alert("그룹 정보 수정에 실패했습니다.")
            }

        })

    });

    function init(){
        $.ajax({
            type: "GET",
            url: `/api/group/manage/${$groupId}`,
            success: function(response){
                console.log("요청이 성공했습니다.");
                console.log("서버로부터의 응답: ", response);

                $('#name').val(response.name);
                $('#introduce').val(response.introduce);
                $('#contactEmail').val(response.contactEmail);
                $('#contactNumber').val(response.contactNumber);
                $('#email').val(response.masterUser.email);
            },
            error: function(xhr, status, error){
                console.error("요청이 실패했습니다.");
                console.error("에러 내용: ", error);
                alert("전시 상태를 확인하세요");
            }
        })
    }

})(jQuery);
