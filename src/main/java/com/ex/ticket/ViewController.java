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
		return "index";
	}


	@PostMapping("/token")
	public String token(){
		return "test/token";
	}

	@GetMapping("/login/group")
	public String managerLoginForm(){
		return "group/login";
	}

	@GetMapping("/join/us")
	public String joinForm() { return "user/joinForm"; }


	@GetMapping("/api/mypage")
	public String user(){
		return "test/user";
	}

	@GetMapping("/api/group")
	public String managerHome() { return "group/home"; }

//	@GetMapping("/api/group/{groupId}")
//	public String groupHomeByGroupId(
//			@PathVariable(name = "groupId") String groupId,
//			Model model
//	){
//		System.out.println(groupId);
//		model.addAttribute("groupId", groupId);
//		// TODO 디테일 info 로 변경하기
//		return "group/event/list";
//	}

//	@GetMapping("/api/group/{groupId}/event")
//	public String manageEvent(
//			@PathVariable(name = "groupId") String groupId,
//			Model model
//	){
//		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//		System.out.println(groupId);
//		model.addAttribute("groupId", groupId);
//		return "group/event/list";
//	}


	@GetMapping("/api/group/master")
	public String master(){
		return "test/master";
	}

	@GetMapping("/api/system")
	public String system(){ return "test/admin"; }

	@GetMapping("/login/us")
	public String loginForm(){
		return "user/loginForm";
	}


}
