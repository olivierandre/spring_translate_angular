package fr.oan.translate.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.oan.translate.domain.UserTransfer;
import fr.oan.translate.request.LoginRequest;
import fr.oan.translate.service.UserService;

@RestController
@RequestMapping(value = "api")
public class UserController {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST, produces = "application/json")
	public UserTransfer authenticate(@RequestBody LoginRequest loginRequest, HttpServletRequest request)
			throws BadCredentialsException {
		return userService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
	}

	@RequestMapping(value = "/secure/user", method = RequestMethod.GET, produces = "application/json")
	public UserTransfer checkToken() {
		String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		return userService.getUserByUserName(username);
	}

	@RequestMapping(value = "/secure/user", method = RequestMethod.PUT, produces = "application/json")
	public UserTransfer updateUser(@RequestBody UserTransfer ut) {
		return userService.updateUser(ut);
	}

	@RequestMapping(value = "/secure/user/change-password", method = RequestMethod.POST, produces = "application/json")
	public UserTransfer changePassword(@RequestBody LoginRequest loginRequest, HttpServletRequest request)
			throws Exception {
		return userService.setNewPassword(loginRequest);
	}

}
