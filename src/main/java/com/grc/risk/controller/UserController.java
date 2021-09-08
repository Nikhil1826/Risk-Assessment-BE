package com.grc.risk.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.grc.risk.dto.UserDTO;
import com.grc.risk.exception.ResourceNotFoundException;
import com.grc.risk.exception.UserUnauthorizedException;
import com.grc.risk.service.UserService;
import com.grc.risk.util.Constants;

@RestController
@RequestMapping("/user")
public class UserController {
	private final Logger logger = LoggerFactory.getLogger(UserController.class);

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/authenticate")
	public ResponseEntity<UserDTO> authenticateUser(@RequestBody UserDTO userDTO) throws UserUnauthorizedException {
		logger.info("UserController-authenticateUser: Inside authenticateUser method...");
		UserDTO responseUserDTO = userService.authenticateUser(userDTO);
		if (responseUserDTO == null) {
			logger.info("UserController-authenticateUser: Either user does not exist or invalid user credentials");
			UserUnauthorizedException userUnauthorizedException = new UserUnauthorizedException();
			userUnauthorizedException.getGenericError().setErrorcode(Constants.GRC_USER_ERR_CODE);
			userUnauthorizedException.getGenericError().setErrormessage(Constants.LOGIN_FAILED_ERR_MSG);
			userUnauthorizedException.getGenericError().setUseraction(Constants.LOGIN_FAILED_USER_ACTION);
			throw userUnauthorizedException;
		}
		return ResponseEntity.ok().body(responseUserDTO);
	}

	@GetMapping("/getAll/{userId}")
	public ResponseEntity<List<UserDTO>> getAllUsers(@PathVariable("userId") Long userId)
			throws UserUnauthorizedException, ResourceNotFoundException {
		List<UserDTO> responseList = userService.getAllUsers(userId);
		if (responseList == null) {
			// Throw error
		}
		return ResponseEntity.ok().body(responseList);
	}

	@GetMapping("/get")
	public ResponseEntity<UserDTO> getUserById(@RequestParam(value = "loggedInUserId") Long loggedInUserId,
			@RequestParam(value = "userId") Long userId) throws UserUnauthorizedException, ResourceNotFoundException {
		UserDTO response = userService.getUserById(loggedInUserId, userId);
		return ResponseEntity.ok().body(response);
	}

}
