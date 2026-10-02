package com.hms.hospital.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping({ "/", "/login" })
	public String login() {
		return "login";

	}

	@GetMapping("/dashboard")
	public String dashboard(Authentication authentication) {

		String role = authentication.getAuthorities().iterator().next().getAuthority();

		if (role.equals("ROLE_ADMIN")) {
			return "redirect:/admin/dashboard";
		}

		if (role.equals("ROLE_DOCTOR")) {
			return "redirect:/doctor/dashboard";
		}

		return "redirect:/patient/dashboard";
	}
}