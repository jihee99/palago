(function($) {

    const $groupId = document.getElementById('groupIdContainer').getAttribute('data-group-id');

    $(document).ready(function() {
        init();
    });



    function init(){
        $.ajax({
            type: "GET",
            url: `/api/group/manage/${$groupId}/list`,
            success: function(response){
                console.log("요청이 성공했습니다.");
                console.log("서버로부터의 응답: ", response);

            },
            error: function(xhr, status, error){
                console.error("요청이 실패했습니다.");
                console.error("에러 내용: ", error);
                alert("전시 상태를 확인하세요");
            }
        })
    }

})(jQuery);
