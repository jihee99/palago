function submitForm() {

    let form = document.getElementById("loginForm");

    let username = form.elements["username"].value;
    let password = form.elements["password"].value;

    if (username.trim() === "" || password.trim() === "") {
        alert("이메일과 비밀번호를 모두 입력하세요.");
        return;
    }


    let formData = new FormData(form);

    let param = {};
    formData.forEach(function(value, key){
        param[key] = value;
    });

    // JSON 데이터를 문자열로 변환
    let jsonData = JSON.stringify(param);

    // AJAX 요청 생성
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/login");
    xhr.setRequestHeader("Content-Type", "application/json");

    // 요청 완료 후 실행될 콜백 함수 설정
    xhr.onload = function() {
        if (xhr.status === 200) {
            alert("로그인 성공!");
            // window.location.href = "/home";
            window.location.href = "/api/group";
        } else {
            alert("로그인 실패!");
        }
    };

    // 요청 전송
    xhr.send(jsonData);
}
