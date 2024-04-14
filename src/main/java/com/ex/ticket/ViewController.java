package com.ex.ticket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ViewController {

	@GetMapping("/home")
	public String index(){
		return "index";
	}

	@PostMapping("/token")
	public String token(){
		return "token";
	}

	@GetMapping("/api/mypage")
	public String user(){
		return "user";
	}

	@GetMapping("/api/group/event")
	public String manager(){
		return "manager";
	}

	@GetMapping("/api/group/master")
	public String master(){
		return "master";
	}

	@GetMapping("/api/system")
	public String system(){ return "admin"; }

	@GetMapping("/login")
	public String loginForm(){
		return "loginForm";
	}

	@GetMapping("/join")
	public String joinForm() { return "joinForm"; }


}
