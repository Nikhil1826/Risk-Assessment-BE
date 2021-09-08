package com.grc.risk.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grc.risk.dto.AssessmentRequest;
import com.grc.risk.dto.MalformedRequestErrorDetails;
import com.grc.risk.dto.Response;
import com.grc.risk.dto.RiskDTO;
import com.grc.risk.dto.UserAssignedRisksResponse;
import com.grc.risk.entity.Risk;
import com.grc.risk.entity.RiskAssessment;
import com.grc.risk.enums.ModeType;
import com.grc.risk.exception.BadRequestException;
import com.grc.risk.exception.InternalServerException;
import com.grc.risk.exception.MalformedRequestBodyException;
import com.grc.risk.exception.ResourceNotFoundException;
import com.grc.risk.exception.UserUnauthorizedException;
import com.grc.risk.service.RiskService;
import com.grc.risk.util.Constants;
import com.grc.risk.util.Validator;

@RestController
@RequestMapping("/risk")
public class RiskController {
	private final Logger logger = LoggerFactory.getLogger(RiskController.class);

	private final RiskService riskService;

	public RiskController(RiskService riskService) {
		this.riskService = riskService;
	}

	@GetMapping("/getRisks/{userId}")
	public ResponseEntity<List<UserAssignedRisksResponse>> getRisksAssignedToUser(
			@PathVariable(value = "userId", required = true) Long userId)
			throws BadRequestException, ResourceNotFoundException, InternalServerException {
		logger.info("RiskController: inside getRisksAssignedToUser() method...");
		List<UserAssignedRisksResponse> responseList = riskService.getRisksAssignedToUser(userId);
		if (responseList == null) {
			// Something went wrong
			logger.error("Unable to retrieve risks assigned to risk manager having Id: {}", userId);
			InternalServerException internalServerException = new InternalServerException();
			internalServerException.getGenericError().setErrorcode(Constants.GRC_SOMETHING_WENT_WRONG_ERR_CODE);
			internalServerException.getGenericError().setErrormessage(Constants.SOMETHING_WENT_WRONG_ERR_MSG);
			internalServerException.getGenericError().setUseraction(Constants.SOMETHING_WENT_WRONG_USER_ACTION);
			throw internalServerException;
		}
		return ResponseEntity.ok().body(responseList);
	}

	@PostMapping("/assess")
	public ResponseEntity<Response> assessRisk(@RequestBody AssessmentRequest assessmentRequest)
			throws BadRequestException, MalformedRequestBodyException {
		logger.info("RiskController: inside assessRisk() method...");
		List<MalformedRequestErrorDetails> errorList = Validator.validateRiskAssessmentRequest(assessmentRequest);
		if (!errorList.isEmpty()) {
			throw new MalformedRequestBodyException(errorList);
		}
		RiskAssessment riskAssessment = riskService.assessRisk(assessmentRequest);
		if (riskAssessment == null) {
			// Throw error
		}
		Response response = new Response();
		response.setStatus(HttpStatus.OK.value());
		response.setMessage(Constants.RISK_ASSESSEMENT_SUCCESS);
		return ResponseEntity.ok().body(response);
	}

	/**
	 * API to add risk
	 * 
	 * @param riskDTO
	 * @param userId
	 * @return
	 * @throws MalformedRequestBodyException
	 * @throws UserUnauthorizedException
	 * @throws InternalServerException
	 */
	@PostMapping("/add/{userId}")
	public ResponseEntity<Response> addRisk(@RequestBody RiskDTO riskDTO, @PathVariable("userId") Long userId)
			throws MalformedRequestBodyException, UserUnauthorizedException, InternalServerException {
		logger.info("RiskController: inside addRisk() method...");
		List<MalformedRequestErrorDetails> errorList = Validator.validateRiskRequest(riskDTO,
				ModeType.ADD.getModeType());
		if (!errorList.isEmpty()) {
			logger.error("RiskController-addRisk(): Request body not appropriate");
			throw new MalformedRequestBodyException(errorList);
		}
		Risk risk = riskService.validateAdminAndAddRisk(riskDTO, userId);
		if (risk == null) {
			logger.error("RiskController-addRisk(): Something went wrong while adding risk");
			InternalServerException internalServerException = new InternalServerException();
			internalServerException.getGenericError().setErrorcode(Constants.GRC_SOMETHING_WENT_WRONG_ERR_CODE);
			internalServerException.getGenericError().setErrormessage(Constants.SOMETHING_WENT_WRONG_ERR_MSG);
			internalServerException.getGenericError().setUseraction(Constants.SOMETHING_WENT_WRONG_USER_ACTION);
			throw internalServerException;
		}
		Response response = new Response();
		response.setStatus(HttpStatus.OK.value());
		response.setMessage(Constants.RISK_ADD_SUCCESS);
		return ResponseEntity.ok().body(response);
	}

	/**
	 * API to get all risks
	 * 
	 * @param userId
	 * @return
	 * @throws ResourceNotFoundException
	 * @throws UserUnauthorizedException
	 * @throws InternalServerException
	 */
	@GetMapping("/getAll/{userId}")
	public ResponseEntity<List<RiskDTO>> getAllRisks(@PathVariable("userId") Long userId)
			throws ResourceNotFoundException, UserUnauthorizedException, InternalServerException {
		logger.info("RiskController: inside getAllRisks() method...");
		List<RiskDTO> riskDTOList = riskService.getAllRisks(userId);
		if (riskDTOList == null) {
			logger.error("RiskController-getAllRisks(): Something went wrong while retrieving risks");
			InternalServerException internalServerException = new InternalServerException();
			internalServerException.getGenericError().setErrorcode(Constants.GRC_SOMETHING_WENT_WRONG_ERR_CODE);
			internalServerException.getGenericError().setErrormessage(Constants.SOMETHING_WENT_WRONG_ERR_MSG);
			internalServerException.getGenericError().setUseraction(Constants.SOMETHING_WENT_WRONG_USER_ACTION);
			throw internalServerException;
		}
		return ResponseEntity.ok().body(riskDTOList);
	}

	/**
	 * API to update risk details
	 * 
	 * @param riskDTO
	 * @param userId
	 * @return
	 * @throws MalformedRequestBodyException
	 * @throws UserUnauthorizedException
	 * @throws InternalServerException
	 * @throws BadRequestException
	 */
	@PutMapping("/update/{userId}")
	public ResponseEntity<Response> updateRisk(@RequestBody RiskDTO riskDTO, @PathVariable("userId") Long userId)
			throws MalformedRequestBodyException, UserUnauthorizedException, InternalServerException,
			BadRequestException {
		logger.info("RiskController: inside updateRisk() method...");
		List<MalformedRequestErrorDetails> errorList = Validator.validateRiskRequest(riskDTO,
				ModeType.UPDATE.getModeType());
		if (!errorList.isEmpty()) {
			logger.error("RiskController-updateRisk(): Request body not appropriate");
			throw new MalformedRequestBodyException(errorList);
		}
		Risk risk = riskService.updateRisk(riskDTO, userId);
		if (risk == null) {
			logger.error("RiskController-updateRisk(): Something went wrong while adding risk");
			InternalServerException internalServerException = new InternalServerException();
			internalServerException.getGenericError().setErrorcode(Constants.GRC_SOMETHING_WENT_WRONG_ERR_CODE);
			internalServerException.getGenericError().setErrormessage(Constants.SOMETHING_WENT_WRONG_ERR_MSG);
			internalServerException.getGenericError().setUseraction(Constants.SOMETHING_WENT_WRONG_USER_ACTION);
			throw internalServerException;
		}
		Response response = new Response();
		response.setStatus(HttpStatus.OK.value());
		response.setMessage(Constants.RISK_UPDATE_SUCCESS);
		return ResponseEntity.ok().body(response);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<Response> deleteRisk(@RequestParam("riskId") Long riskId, @RequestParam("userId") Long userId)
			throws BadRequestException, UserUnauthorizedException {
		logger.info("RiskController: inside deleteRisk() method...");
		riskService.deleteRisk(riskId, userId);
		Response response = new Response();
		response.setStatus(HttpStatus.OK.value());
		response.setMessage(Constants.RISK_DELETE_SUCCESS);
		return ResponseEntity.ok().body(response);
	}

}
