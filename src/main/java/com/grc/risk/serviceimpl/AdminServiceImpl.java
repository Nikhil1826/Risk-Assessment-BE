package com.grc.risk.serviceimpl;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.grc.risk.entity.User;
import com.grc.risk.repository.UserDao;
import com.grc.risk.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {
	private final UserDao userDao;

	public AdminServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * This method checks whether the user is admin or not
	 * 
	 */
	@Override
	public boolean isUserAdmin(Long userId) {
		boolean isAdmin = false;
		Optional<User> userOptional = userDao.findById(userId);
		if (userOptional.isPresent() && userOptional.get().isAdmin()) {
			isAdmin = true;
		}
		return isAdmin;
	}

}
