package com.ex.ticket;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class ViewController {

	@GetMapping("/home")
	public String index(Model model, Principal principal){
		System.out.println("home url 요청 중");
		if (principal != null) {
			String username = principal.getName(); // 현재 인증된 사용자의 이름을 가져옴
			System.out.println(username);
			model.addAttribute("username", username); // 모델에 사용자 이름을 추가하여 화면에 전달
		}
		return "test/index";
	}

	@PostMapping("/token")
	public String token(){
		return "test/token";
	}

	@GetMapping("/api/mypage")
	public String user(){
		return "test/user";
	}

	@GetMapping("/api/group/event")
	public String manager(){
		return "test/manager";
	}

	@GetMapping("/api/group/master")
	public String master(){
		return "test/master";
	}

	@GetMapping("/api/system")
	public String system(){ return "test/admin"; }

	@GetMapping("/login/us")
	public String loginForm(){
		return "loginForm";
	}

	@GetMapping("/join/us")
	public String joinForm() { return "joinForm"; }


}
