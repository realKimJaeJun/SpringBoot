package kr.co.Sboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class SboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(SboardApplication.class, args);
	}

	@GetMapping(value = {"", "index"})
	public String index() {
		return "user/login";
	}
}
