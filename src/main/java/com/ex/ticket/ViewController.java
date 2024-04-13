package com.ex.ticket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

	@GetMapping("/")
	public String index(){
		return "index";
	}

	@GetMapping("/mypage")
	public String user(){
		return "user";
	}

	@GetMapping("/group/event")
	public String manager(){
		return "manager";
	}

	@GetMapping("/group/master")
	public String master(){
		return "master";
	}

	@GetMapping("/system")
	public String system(){ return "admin"; }

	@GetMapping("/login")
	public String loginForm(){
		return "loginForm";
	}

	@GetMapping("/join")
	public String joinForm() { return "joinForm"; }


}
