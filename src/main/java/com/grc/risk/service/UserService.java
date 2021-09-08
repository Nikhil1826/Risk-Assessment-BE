package com.grc.risk.service;

import java.util.List;
import com.grc.risk.dto.UserDTO;
import com.grc.risk.entity.User;
import com.grc.risk.exception.ResourceNotFoundException;
import com.grc.risk.exception.UserUnauthorizedException;

public interface UserService {

	User addUser(UserDTO userDTO);

	Integer getUserCount();

	UserDTO authenticateUser(UserDTO userDTO);

	List<UserDTO> getAllUsers(Long userId) throws UserUnauthorizedException, ResourceNotFoundException;

	UserDTO getUserById(Long loggedInUserId, Long userId) throws UserUnauthorizedException, ResourceNotFoundException;
}
