(function($) {
    $(document).ready(function() {
        // 페이지가 로드되면 실행할 코드를 여기에 작성합니다.
        // 예를 들어, 서버에 AJAX 요청을 보내는 코드:
        $.ajax({
            url: '/api/group/manage',
            method: 'GET',
            success: function(response) {
                console.log(response)
                console.log('요청이 성공적으로 완료되었습니다.');

                let innerHtml = '';
                response.forEach(function(item) {
                    console.log(item)
                    innerHtml += `
                        <div class="card mb-4">
                            <div class="card-body">
                                <p class="mb-0">
                                    <h5 class="fw-bold" value="${item.groupId}">${item.name}</h5>
                                    <span>${item.introduce}</span>
                                </p>
                                <a href="/api/group/${item.groupId}">바로가기</a>
                            </div>
                        </div>`;
                });
                $('#list').html(innerHtml)
                // 요소 만들어서 넣기
            },
            error: function(xhr, status, error) {
                console.error('요청이 실패했습니다:', status, error);
            }
        });
    });
})(jQuery);
