(function($) {

    const $groupId = document.getElementById('groupIdContainer').getAttribute('data-group-id');

    $(document).ready(function() {
        $.ajax({
            type: "GET",
            url: `/api/group/manage/${$groupId}`,
            success: function(response){
                console.log("요청이 성공했습니다.");
                console.log("서버로부터의 응답: ", response);

                // 받은 응답을 사용하여 폼 요소에 값을 설정합니다.
                $('#name').val(response.name);
                $('#introduce').val(response.introduce);
                $('#contactEmail').val(response.contactEmail);
                $('#contactNumber').val(response.contactNumber);
                $('#email').val(response.email);
                $('#email').val(response.masterUser.email);
            },
            error: function(xhr, status, error){
                console.error("요청이 실패했습니다.");
                console.error("에러 내용: ", error);
                alert("전시 상태를 확인하세요");
            }
        })
    });
})(jQuery);
