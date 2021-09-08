package com.grc.risk.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.grc.risk.dto.RiskDTO;
import com.grc.risk.dto.UserDTO;
import com.grc.risk.entity.Risk;
import com.grc.risk.entity.User;
import com.grc.risk.entity.UserRiskMapping;
import com.grc.risk.service.RiskService;
import com.grc.risk.service.UserRiskMappingService;
import com.grc.risk.service.UserService;

@Component
public class DataLoader implements CommandLineRunner {
	private final Logger logger = LoggerFactory.getLogger(DataLoader.class);

	private final UserService userService;
	private final RiskService riskService;
	private final UserRiskMappingService userRiskMappingService;

	public DataLoader(UserService userService, RiskService riskService, UserRiskMappingService userRiskMappingService) {
		this.userService = userService;
		this.riskService = riskService;
		this.userRiskMappingService = userRiskMappingService;
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("Bootstraping data...");
		if (userService.getUserCount() == 0) {
			logger.info("---Adding User 1---");
			UserDTO userDTO1 = new UserDTO();
			userDTO1.setEmail("risk.manager1@mshackathon.in");
			userDTO1.setFirstName("Risk");
			userDTO1.setLastName("Manager1");
			userDTO1.setLogin("risk.manager1");
			userDTO1.setPassword("password1");
			userDTO1.setAdmin(false);
			User user1 = userService.addUser(userDTO1);

			logger.info("---Adding User 2---");
			UserDTO userDTO2 = new UserDTO();
			userDTO2.setEmail("risk.manager2@mshackathon.in");
			userDTO2.setFirstName("Risk");
			userDTO2.setLastName("Manager2");
			userDTO2.setLogin("risk.manager2");
			userDTO2.setPassword("password2");
			userDTO2.setAdmin(false);
			User user2 = userService.addUser(userDTO2);

			logger.info("---Adding User 3---");
			UserDTO userDTO3 = new UserDTO();
			userDTO3.setEmail("risk.manager3@mshackathon.in");
			userDTO3.setFirstName("Risk");
			userDTO3.setLastName("Manager3");
			userDTO3.setLogin("risk.manager3");
			userDTO3.setPassword("password3");
			userDTO3.setAdmin(false);
			User user3 = userService.addUser(userDTO3);

			logger.info("---Adding User 4---");
			UserDTO userDTO4 = new UserDTO();
			userDTO4.setEmail("risk.manager4@mshackathon.in");
			userDTO4.setFirstName("Risk");
			userDTO4.setLastName("Manager4");
			userDTO4.setLogin("risk.manager4");
			userDTO4.setPassword("password4");
			userDTO4.setAdmin(false);
			User user4 = userService.addUser(userDTO4);

			logger.info("---Adding User 5---");
			UserDTO userDTO5 = new UserDTO();
			userDTO5.setEmail("risk.manager5@mshackathon.in");
			userDTO5.setFirstName("Risk");
			userDTO5.setLastName("Manager5");
			userDTO5.setLogin("risk.manager5");
			userDTO5.setPassword("password5");
			userDTO5.setAdmin(false);
			User user5 = userService.addUser(userDTO5);

			logger.info("---Adding User 6---");
			UserDTO userDTO6 = new UserDTO();
			userDTO6.setEmail("risk.manager6@mshackathon.in");
			userDTO6.setFirstName("Risk");
			userDTO6.setLastName("Manager6");
			userDTO6.setLogin("risk.manager6");
			userDTO6.setPassword("password6");
			userDTO6.setAdmin(false);
			User user6 = userService.addUser(userDTO6);

			logger.info("---Adding User 7---");
			UserDTO userDTO7 = new UserDTO();
			userDTO7.setEmail("admin@mshackathon.in");
			userDTO7.setFirstName("Admin");
			userDTO7.setLastName("User");
			userDTO7.setLogin("admin.user");
			userDTO7.setPassword("admin1");
			userDTO7.setAdmin(true);
			User user7 = userService.addUser(userDTO7);

			logger.info("---Users loaded successfully---");

			logger.info("---Adding Risk 1---");
			RiskDTO riskDTO1 = new RiskDTO();
			riskDTO1.setName("Attrition Risk");
			riskDTO1.setDescription("Risk of losing employees within the company");
			Risk risk1 = riskService.addRisk(riskDTO1);

			logger.info("---Adding Risk 2---");
			RiskDTO riskDTO2 = new RiskDTO();
			riskDTO2.setName("Loan Defaulting Risk");
			riskDTO2.setDescription("Risk of borrowers defaulting on the loan");
			Risk risk2 = riskService.addRisk(riskDTO2);

			logger.info("---Adding Risk 3---");
			RiskDTO riskDTO3 = new RiskDTO();
			riskDTO3.setName("Security Risk");
			riskDTO3.setDescription("Risk of applications hacked");
			Risk risk3 = riskService.addRisk(riskDTO3);

			logger.info("---Adding Risk 4---");
			RiskDTO riskDTO4 = new RiskDTO();
			riskDTO4.setName("Compliance Risk");
			riskDTO4.setDescription("Risk of employees not following internal processes");
			Risk risk4 = riskService.addRisk(riskDTO4);

			logger.info("---Adding Risk 5---");
			RiskDTO riskDTO5 = new RiskDTO();
			riskDTO5.setName("Vendor Risk");
			riskDTO5.setDescription("Risk of vendors not delivering their services");
			Risk risk5 = riskService.addRisk(riskDTO5);

			logger.info("---Risks loaded successfully---");

			UserRiskMapping userRiskMapping1 = new UserRiskMapping();
			userRiskMapping1.setUser(user1);
			userRiskMapping1.setRisk(risk1);
			userRiskMappingService.addUserRiskMapping(userRiskMapping1);

			UserRiskMapping userRiskMapping2 = new UserRiskMapping();
			userRiskMapping2.setUser(user2);
			userRiskMapping2.setRisk(risk1);
			userRiskMappingService.addUserRiskMapping(userRiskMapping2);

			UserRiskMapping userRiskMapping3 = new UserRiskMapping();
			userRiskMapping3.setUser(user5);
			userRiskMapping3.setRisk(risk2);
			userRiskMappingService.addUserRiskMapping(userRiskMapping3);

			UserRiskMapping userRiskMapping4 = new UserRiskMapping();
			userRiskMapping4.setUser(user1);
			userRiskMapping4.setRisk(risk3);
			userRiskMappingService.addUserRiskMapping(userRiskMapping4);

			UserRiskMapping userRiskMapping5 = new UserRiskMapping();
			userRiskMapping5.setUser(user2);
			userRiskMapping5.setRisk(risk3);
			userRiskMappingService.addUserRiskMapping(userRiskMapping5);

			UserRiskMapping userRiskMapping6 = new UserRiskMapping();
			userRiskMapping6.setUser(user3);
			userRiskMapping6.setRisk(risk3);
			userRiskMappingService.addUserRiskMapping(userRiskMapping6);

			UserRiskMapping userRiskMapping7 = new UserRiskMapping();
			userRiskMapping7.setUser(user3);
			userRiskMapping7.setRisk(risk4);
			userRiskMappingService.addUserRiskMapping(userRiskMapping7);

			UserRiskMapping userRiskMapping8 = new UserRiskMapping();
			userRiskMapping8.setUser(user4);
			userRiskMapping8.setRisk(risk4);
			userRiskMappingService.addUserRiskMapping(userRiskMapping8);

			UserRiskMapping userRiskMapping9 = new UserRiskMapping();
			userRiskMapping9.setUser(user1);
			userRiskMapping9.setRisk(risk5);
			userRiskMappingService.addUserRiskMapping(userRiskMapping9);

			UserRiskMapping userRiskMapping10 = new UserRiskMapping();
			userRiskMapping10.setUser(user4);
			userRiskMapping10.setRisk(risk5);
			userRiskMappingService.addUserRiskMapping(userRiskMapping10);

			UserRiskMapping userRiskMapping11 = new UserRiskMapping();
			userRiskMapping11.setUser(user2);
			userRiskMapping11.setRisk(risk4);
			userRiskMappingService.addUserRiskMapping(userRiskMapping11);

		}
	}

}
