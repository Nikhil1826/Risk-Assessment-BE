package com.grc.risk.service;

import java.util.List;
import com.grc.risk.dto.AssessmentRequest;
import com.grc.risk.dto.RiskDTO;
import com.grc.risk.dto.UserAssignedRisksResponse;
import com.grc.risk.entity.Risk;
import com.grc.risk.entity.RiskAssessment;
import com.grc.risk.exception.BadRequestException;
import com.grc.risk.exception.ResourceNotFoundException;
import com.grc.risk.exception.UserUnauthorizedException;

public interface RiskService {

	Risk addRisk(RiskDTO riskDTO);

	List<UserAssignedRisksResponse> getRisksAssignedToUser(Long userId)
			throws BadRequestException, ResourceNotFoundException;

	RiskAssessment assessRisk(AssessmentRequest assessmentRequest) throws BadRequestException;

	Risk validateAdminAndAddRisk(RiskDTO riskDTO, Long userId) throws UserUnauthorizedException;

	List<RiskDTO> getAllRisks(Long userId) throws ResourceNotFoundException, UserUnauthorizedException;

	Risk updateRisk(RiskDTO riskDTO, Long userId) throws BadRequestException, UserUnauthorizedException;
	
	void deleteRisk(Long riskId, Long userId) throws BadRequestException, UserUnauthorizedException;
}
