package com.grc.risk.serviceimpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.grc.risk.dto.AssessmentRequest;
import com.grc.risk.dto.RiskAssessmentDTO;
import com.grc.risk.dto.RiskDTO;
import com.grc.risk.dto.UserAssignedRisksResponse;
import com.grc.risk.entity.Risk;
import com.grc.risk.entity.RiskAssessment;
import com.grc.risk.entity.User;
import com.grc.risk.entity.UserRiskMapping;
import com.grc.risk.exception.BadRequestException;
import com.grc.risk.exception.ResourceNotFoundException;
import com.grc.risk.exception.UserUnauthorizedException;
import com.grc.risk.repository.RiskAssessmentDao;
import com.grc.risk.repository.RiskDao;
import com.grc.risk.repository.UserDao;
import com.grc.risk.repository.UserRiskMappingDao;
import com.grc.risk.service.AdminService;
import com.grc.risk.service.RiskService;
import com.grc.risk.util.Constants;
import com.grc.risk.util.DateUtil;

@Service
public class RiskServiceImpl implements RiskService {
	private final Logger logger = LoggerFactory.getLogger(RiskServiceImpl.class);

	private final RiskDao riskDao;
	private final UserDao userDao;
	private final UserRiskMappingDao userRiskMappingDao;
	private final RiskAssessmentDao riskAssessmentDao;
	private final AdminService adminService;

	public RiskServiceImpl(RiskDao riskDao, UserDao userDao, UserRiskMappingDao userRiskMappingDao,
			RiskAssessmentDao riskAssessmentDao, AdminService adminService) {
		this.riskDao = riskDao;
		this.userDao = userDao;
		this.userRiskMappingDao = userRiskMappingDao;
		this.riskAssessmentDao = riskAssessmentDao;
		this.adminService = adminService;
	}

	@Override
	public Risk addRisk(RiskDTO riskDTO) {
		logger.info("RiskServiceImpl: inside addRisk() method...");
		Risk risk = new Risk();
		risk.setName(riskDTO.getName());
		risk.setDescription(riskDTO.getDescription());
		return riskDao.save(risk);
	}

	@Override
	public List<UserAssignedRisksResponse> getRisksAssignedToUser(Long userId)
			throws BadRequestException, ResourceNotFoundException {
		logger.info("RiskServiceImpl: inside getRisksAssignedToUser() method...");
		List<UserAssignedRisksResponse> responseList = new ArrayList<>();
		Optional<User> userOptional = userDao.findById(userId);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			List<UserRiskMapping> userRiskMappingList = user.getUserRiskMappings();
			if (userRiskMappingList != null && !userRiskMappingList.isEmpty()) {
				userRiskMappingList.forEach(userRiskMapping -> {
					UserAssignedRisksResponse userAssignedRisk = new UserAssignedRisksResponse();
					userAssignedRisk.setMappingId(userRiskMapping.getMappingId());
					userAssignedRisk.setRiskName(userRiskMapping.getRisk().getName());
					userAssignedRisk.setRiskDescription(userRiskMapping.getRisk().getDescription());
					userAssignedRisk.setAssessmentAvailable(true);
					List<RiskAssessmentDTO> riskAssessmentDTOList = new ArrayList<>();
					if (userRiskMapping.getRiskAssessmentList() != null
							&& !userRiskMapping.getRiskAssessmentList().isEmpty()) {
						Timestamp latestAssessmentTimestamp = userRiskMapping.getRiskAssessmentList().get(0)
								.getAssessedDate();
						for (RiskAssessment riskAssessment : userRiskMapping.getRiskAssessmentList()) {
							RiskAssessmentDTO riskAssessmentDTO = new RiskAssessmentDTO();
							riskAssessmentDTO.setId(riskAssessment.getId());
							riskAssessmentDTO.setAssessmentScore(riskAssessment.getAssessmentScore());
							riskAssessmentDTO.setComment(riskAssessment.getComment());
							riskAssessmentDTO.setAssessedDate(riskAssessment.getAssessedDate());
							riskAssessmentDTOList.add(riskAssessmentDTO);
							int result = latestAssessmentTimestamp.compareTo(riskAssessment.getAssessedDate());
							if (result < 0) {
								latestAssessmentTimestamp = riskAssessment.getAssessedDate();
							}
						}

						if (DateUtil.getCurrentUTCTimestamp().getTime()
								- latestAssessmentTimestamp.getTime() < Constants.SEVEN_DAYS_MILLISECONDS) {
							userAssignedRisk.setAssessmentAvailable(false);
						}
					}
					userAssignedRisk.setAssessments(riskAssessmentDTOList);
					responseList.add(userAssignedRisk);
				});
			} else {
				// No risks assigned to the risk manager
				logger.error("RiskServiceImpl-getRisksAssignedToUser: No risk assigned to risk manager: {}",
						user.getUsername());
				ResourceNotFoundException resourceNotFoundException = new ResourceNotFoundException();
				resourceNotFoundException.getGenericError().setErrorcode(Constants.GRC_RISK_ERR_CODE);
				resourceNotFoundException.getGenericError().setErrormessage(Constants.NO_RISKS_ASSIGNED_ERR_MSG);
				resourceNotFoundException.getGenericError().setUseraction(Constants.NO_RISKS_ASSIGNED_USER_ACTION);
				throw resourceNotFoundException;

			}
		} else {
			// No user available with the provided user Id
			logger.error("RiskServiceImpl-getRisksAssignedToUser: No user found with Id: {}", userId);
			BadRequestException badRequestException = new BadRequestException();
			badRequestException.getGenericError().setErrorcode(Constants.GRC_USER_ERR_CODE);
			badRequestException.getGenericError().setErrormessage(Constants.USER_NOT_FOUND_ERR_MSG);
			badRequestException.getGenericError().setUseraction(Constants.USER_NOT_FOUND_USER_ACTION);
			throw badRequestException;
		}
		return !responseList.isEmpty() ? responseList : null;
	}

	@Override
	public RiskAssessment assessRisk(AssessmentRequest assessmentRequest) throws BadRequestException {
		logger.info("RiskServiceImpl: inside assessRisk() method...");
		RiskAssessment responseObj = null;
		Optional<UserRiskMapping> userRiskMappingOptional = userRiskMappingDao
				.findById(assessmentRequest.getMappingId());
		if (userRiskMappingOptional.isPresent()) {
			UserRiskMapping userRiskMapping = userRiskMappingOptional.get();
			RiskAssessment riskAssessment = new RiskAssessment();
			riskAssessment.setUserRiskMapping(userRiskMapping);
			riskAssessment.setAssessmentScore(assessmentRequest.getAssessmentScore());
			riskAssessment.setComment(assessmentRequest.getComment());
			riskAssessment.setAssessedDate(DateUtil.getCurrentUTCTimestamp());
			responseObj = riskAssessmentDao.save(riskAssessment);
		} else {
			// No user-risk mapping found with provided mapping Id
			logger.error("RiskServiceImpl-assessRisk: No user-risk mapping found with Id: {}",
					assessmentRequest.getMappingId());
			BadRequestException badRequestException = new BadRequestException();
			badRequestException.getGenericError().setErrorcode(Constants.GRC_USER_RISK_ERR_CODE);
			badRequestException.getGenericError().setErrormessage(Constants.USER_RISK_MAPPING_NOT_FOUND_ERR_MSG);
			badRequestException.getGenericError().setUseraction(Constants.USER_RISK_MAPPING_NOT_FOUND_USER_ACTION);
			throw badRequestException;
		}
		return responseObj;
	}

	@Override
	public Risk validateAdminAndAddRisk(RiskDTO riskDTO, Long userId) throws UserUnauthorizedException {
		logger.info("RiskServiceImpl: inside validateAdminAndAddRisk() method...");
		Risk savedRisk = null;
		boolean isAdmin = adminService.isUserAdmin(userId);
		if (isAdmin) {
			savedRisk = addRisk(riskDTO);
		} else {
			// User is not authorized to add risk
			logger.error("User with user Id {}, not authorized to add risk", userId);
			UserUnauthorizedException userUnauthorizedException = new UserUnauthorizedException();
			userUnauthorizedException.getGenericError().setErrorcode(Constants.GRC_USER_ERR_CODE);
			userUnauthorizedException.getGenericError().setErrormessage(Constants.USER_UNAUTHORIZED_ERR_MSG);
			userUnauthorizedException.getGenericError().setUseraction(Constants.CONTACT_ADMIN_USER_ACTION);
			throw userUnauthorizedException;
		}
		return savedRisk;
	}

	@Override
	public List<RiskDTO> getAllRisks(Long userId) throws ResourceNotFoundException, UserUnauthorizedException {
		logger.info("RiskServiceImpl: inside getAllRisks() method...");
		List<RiskDTO> responseList = new ArrayList<>();
		boolean isAdmin = adminService.isUserAdmin(userId);
		if (isAdmin) {
			List<Risk> risks = riskDao.findAll();
			if (!risks.isEmpty()) {
				risks.forEach(risk -> {
					RiskDTO riskDTO = new RiskDTO();
					riskDTO.setId(risk.getId());
					riskDTO.setName(risk.getName());
					riskDTO.setDescription(risk.getDescription());
					responseList.add(riskDTO);
				});
			} else {
				// No risks found
				ResourceNotFoundException resourceNotFoundException = new ResourceNotFoundException();
				resourceNotFoundException.getGenericError().setErrorcode(Constants.GRC_RISK_ERR_CODE);
				resourceNotFoundException.getGenericError().setErrormessage(Constants.NO_RISK_FOUND_ERR_MSG);
				resourceNotFoundException.getGenericError().setUseraction(Constants.CONTACT_ADMIN_USER_ACTION);
				throw resourceNotFoundException;
			}
		} else {
			// User is not authorized to access risks list
			logger.error("User with user Id {}, not authorized to access risks", userId);
			UserUnauthorizedException userUnauthorizedException = new UserUnauthorizedException();
			userUnauthorizedException.getGenericError().setErrorcode(Constants.GRC_USER_ERR_CODE);
			userUnauthorizedException.getGenericError().setErrormessage(Constants.USER_UNAUTHORIZED_ERR_MSG);
			userUnauthorizedException.getGenericError().setUseraction(Constants.CONTACT_ADMIN_USER_ACTION);
			throw userUnauthorizedException;
		}
		return !responseList.isEmpty() ? responseList : null;
	}

	@Override
	public Risk updateRisk(RiskDTO riskDTO, Long userId) throws BadRequestException, UserUnauthorizedException {
		logger.info("RiskServiceImpl: inside updateRisk() method...");
		Risk risk = null;
		boolean isAdmin = adminService.isUserAdmin(userId);
		if (isAdmin) {
			Optional<Risk> riskOptional = riskDao.findById(riskDTO.getId());
			if (riskOptional.isPresent()) {
				risk = riskOptional.get();
				risk.setName(riskDTO.getName());
				risk.setDescription(riskDTO.getDescription());
				riskDao.save(risk);
			} else {
				// Risk not found for the provided risk Id
				logger.error("Risk not found for the provided risk Id: {}", riskDTO.getId());
				BadRequestException badRequestException = new BadRequestException();
				badRequestException.getGenericError().setErrorcode(Constants.GRC_RISK_ERR_CODE);
				badRequestException.getGenericError().setErrormessage(Constants.RISK_NOT_FOUND);
				badRequestException.getGenericError().setUseraction(Constants.RISK_NOT_FOUND_USER_ACTION);
				throw badRequestException;
			}
		} else {
			// User is not authorized to update risk
			logger.error("User with user Id {}, not authorized to update risk", userId);
			UserUnauthorizedException userUnauthorizedException = new UserUnauthorizedException();
			userUnauthorizedException.getGenericError().setErrorcode(Constants.GRC_USER_ERR_CODE);
			userUnauthorizedException.getGenericError().setErrormessage(Constants.USER_UNAUTHORIZED_ERR_MSG);
			userUnauthorizedException.getGenericError().setUseraction(Constants.CONTACT_ADMIN_USER_ACTION);
			throw userUnauthorizedException;
		}
		return risk;
	}

	@Override
	public void deleteRisk(Long riskId, Long userId) throws BadRequestException, UserUnauthorizedException {
		logger.info("RiskServiceImpl: inside deleteRisk() method...");
		boolean isAdmin = adminService.isUserAdmin(userId);
		if (isAdmin) {
			Optional<Risk> riskOptional = riskDao.findById(riskId);
			if (riskOptional.isPresent()) {
				riskDao.delete(riskOptional.get());
			} else {
				// Risk not found for the provided risk Id
				logger.error("Risk not found for the provided risk Id: {}", riskId);
				BadRequestException badRequestException = new BadRequestException();
				badRequestException.getGenericError().setErrorcode(Constants.GRC_RISK_ERR_CODE);
				badRequestException.getGenericError().setErrormessage(Constants.RISK_NOT_FOUND);
				badRequestException.getGenericError().setUseraction(Constants.RISK_NOT_FOUND_USER_ACTION);
				throw badRequestException;
			}
		} else {
			// User is not authorized to delete risk
			logger.error("User with user Id {}, not authorized to delete risk", userId);
			UserUnauthorizedException userUnauthorizedException = new UserUnauthorizedException();
			userUnauthorizedException.getGenericError().setErrorcode(Constants.GRC_USER_ERR_CODE);
			userUnauthorizedException.getGenericError().setErrormessage(Constants.USER_UNAUTHORIZED_ERR_MSG);
			userUnauthorizedException.getGenericError().setUseraction(Constants.CONTACT_ADMIN_USER_ACTION);
			throw userUnauthorizedException;
		}
	}

}
