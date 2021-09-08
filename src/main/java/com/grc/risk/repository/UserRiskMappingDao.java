package com.grc.risk.repository;

import org.springframework.data.repository.CrudRepository;
import com.grc.risk.entity.UserRiskMapping;

public interface UserRiskMappingDao extends CrudRepository<UserRiskMapping, Long> {

}
