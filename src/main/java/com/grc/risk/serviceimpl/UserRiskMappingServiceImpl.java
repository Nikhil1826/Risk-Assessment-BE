package com.grc.risk.serviceimpl;

import org.springframework.stereotype.Service;
import com.grc.risk.entity.UserRiskMapping;
import com.grc.risk.repository.UserRiskMappingDao;
import com.grc.risk.service.UserRiskMappingService;

@Service
public class UserRiskMappingServiceImpl implements UserRiskMappingService {
	private final UserRiskMappingDao userRiskMappingDao;

	public UserRiskMappingServiceImpl(UserRiskMappingDao userRiskMappingDao) {
		this.userRiskMappingDao = userRiskMappingDao;
	}

	@Override
	public void addUserRiskMapping(UserRiskMapping userRiskMapping) {
		userRiskMappingDao.save(userRiskMapping);
	}

}
