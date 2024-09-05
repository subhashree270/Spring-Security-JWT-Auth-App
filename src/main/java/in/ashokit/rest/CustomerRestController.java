package in.ashokit.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.ashokit.entity.Customer;
import in.ashokit.service.JwtService;
import in.ashokit.service.MyUserDetailsService;

@RestController
@RequestMapping("/api")
public class CustomerRestController {
	@Autowired
	private MyUserDetailsService service;
	@Autowired
	private PasswordEncoder pwdEncoder;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private AuthenticationManager authManager;
	
	@PostMapping("/register")
	public String registerUser(@RequestBody Customer c) {
		c.setPwd(pwdEncoder.encode(c.getPwd()));
		boolean saveCustomer = service.saveCustomer(c);
		if(saveCustomer)
			return "User Registered";
		else
			return" Registration Failed";	
	}
	@PostMapping("/login")
	public ResponseEntity<String> loginCheck(@RequestBody Customer c){
		
		UsernamePasswordAuthenticationToken token = 
				new UsernamePasswordAuthenticationToken(c.getUname(),c.getPwd());
		
		try {
			Authentication authenticate = authManager.authenticate(token);
			if(authenticate.isAuthenticated()) {
				String jwtToken = jwtService.generateToken(c.getUname());
				return new ResponseEntity<>(jwtToken,HttpStatus.OK);
			}	
		}catch(Exception e) {
		//logger
	}
	return new ResponseEntity<>("Invalid Credentials",HttpStatus.BAD_REQUEST);
}
	@GetMapping("/welcome")
	public String welcomeMsg() {
		return "Welcome to our portal";
	}
	@GetMapping("/greet")
	public String greetMsg() {
		return "All the best for your upcoming plan...";
	}
}