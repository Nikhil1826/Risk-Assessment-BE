package com.grc.risk.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.grc.risk.dto.RiskDTO;
import com.grc.risk.dto.UserDTO;
import com.grc.risk.entity.User;
import com.grc.risk.exception.ResourceNotFoundException;
import com.grc.risk.exception.UserUnauthorizedException;
import com.grc.risk.repository.UserDao;
import com.grc.risk.service.AdminService;
import com.grc.risk.service.UserService;
import com.grc.risk.util.Constants;

@Service
public class UserServiceImpl implements UserService {
	private final UserDao userDao;
	private final AdminService adminService;

	public UserServiceImpl(UserDao userDao, AdminService adminService) {
		this.userDao = userDao;
		this.adminService = adminService;
	}

	@Override
	public User addUser(UserDTO userDTO) {
		User user = new User();
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setEmail(userDTO.getEmail());
		user.setUsername(userDTO.getLogin());
		user.setPassword(userDTO.getPassword());
		user.setAdmin(userDTO.isAdmin());
		return userDao.save(user);
	}

	@Override
	public Integer getUserCount() {
		return userDao.getUserCount();
	}

	@Override
	public UserDTO authenticateUser(UserDTO userDTO) {
		UserDTO responseUserDTO = null;
		User user = userDao.findByUsernameAndPassword(userDTO.getLogin().trim(), userDTO.getPassword().trim());
		if (user != null) {
			responseUserDTO = new UserDTO();
			responseUserDTO.setId(user.getId());
			responseUserDTO.setFirstName(user.getFirstName());
			responseUserDTO.setLastName(user.getLastName());
			responseUserDTO.setEmail(user.getEmail());
			responseUserDTO.setLogin(user.getUsername());
			responseUserDTO.setAdmin(user.isAdmin());
		}
		return responseUserDTO;
	}

	@Override
	public List<UserDTO> getAllUsers(Long userId) throws UserUnauthorizedException, ResourceNotFoundException {
		boolean isAdmin = adminService.isUserAdmin(userId);
		List<UserDTO> userDTOList = new ArrayList<>();
		if (isAdmin) {
			List<User> userList = userDao.findAll();
			if (!userList.isEmpty()) {
				userList.forEach(user -> {
					UserDTO userDTO = new UserDTO();
					userDTO.setId(user.getId());
					userDTO.setFirstName(user.getFirstName());
					userDTO.setLastName(user.getLastName());
					userDTO.setLogin(user.getUsername());
					userDTOList.add(userDTO);
				});
			} else {
				// No users found
				ResourceNotFoundException resourceNotFoundException = new ResourceNotFoundException();
				resourceNotFoundException.getGenericError().setErrorcode(Constants.GRC_USER_ERR_CODE);
				resourceNotFoundException.getGenericError().setErrormessage(Constants.NO_USER_FOUND_ERR_MSG);
				resourceNotFoundException.getGenericError().setUseraction(Constants.CONTACT_ADMIN_USER_ACTION);
				throw resourceNotFoundException;
			}
		} else {
			// User is not authorized to access users list
			UserUnauthorizedException userUnauthorizedException = new UserUnauthorizedException();
			userUnauthorizedException.getGenericError().setErrorcode(Constants.GRC_USER_ERR_CODE);
			userUnauthorizedException.getGenericError().setErrormessage(Constants.USER_UNAUTHORIZED_ERR_MSG);
			userUnauthorizedException.getGenericError().setUseraction(Constants.CONTACT_ADMIN_USER_ACTION);
			throw userUnauthorizedException;
		}
		return !userDTOList.isEmpty() ? userDTOList : null;
	}

	@Override
	public UserDTO getUserById(Long loggedInUserId, Long userId)
			throws UserUnauthorizedException, ResourceNotFoundException {
		UserDTO response = null;
		boolean isAdmin = adminService.isUserAdmin(loggedInUserId);
		if (isAdmin) {
			Optional<User> userOptional = userDao.findById(userId);
			if (userOptional.isPresent()) {
				response = new UserDTO();
				User user = userOptional.get();
				response.setId(user.getId());
				response.setFirstName(user.getFirstName());
				response.setLastName(user.getLastName());
				response.setEmail(user.getEmail());
				response.setLogin(user.getUsername());
				response.setAdmin(user.isAdmin());
				List<RiskDTO> assignedRisks = new ArrayList<>();
				if (user.getUserRiskMappings() != null && !user.getUserRiskMappings().isEmpty()) {
					user.getUserRiskMappings().forEach(userRiskMapping -> {
						RiskDTO riskDTO = new RiskDTO();
						riskDTO.setId(userRiskMapping.getRisk().getId());
						riskDTO.setName(userRiskMapping.getRisk().getName());
						riskDTO.setDescription(userRiskMapping.getRisk().getDescription());
						assignedRisks.add(riskDTO);
					});
				}
				response.setAssignedRisks(assignedRisks);
			} else {
				// User not found with provided user Id
				ResourceNotFoundException resourceNotFoundException = new ResourceNotFoundException();
				resourceNotFoundException.getGenericError().setErrorcode(Constants.GRC_USER_ERR_CODE);
				resourceNotFoundException.getGenericError().setErrormessage(Constants.USER_NOT_FOUND_ERR_MSG);
				resourceNotFoundException.getGenericError().setUseraction(Constants.USER_NOT_FOUND_USER_ACTION);
				throw resourceNotFoundException;
			}
		} else {
			// User is not an authorized to access users list
			UserUnauthorizedException userUnauthorizedException = new UserUnauthorizedException();
			userUnauthorizedException.getGenericError().setErrorcode(Constants.GRC_USER_ERR_CODE);
			userUnauthorizedException.getGenericError().setErrormessage(Constants.USER_UNAUTHORIZED_ERR_MSG);
			userUnauthorizedException.getGenericError().setUseraction(Constants.CONTACT_ADMIN_USER_ACTION);
			throw userUnauthorizedException;
		}
		return response;
	}

}
