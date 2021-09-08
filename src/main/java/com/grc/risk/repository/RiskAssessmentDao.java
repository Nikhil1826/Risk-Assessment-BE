package com.grc.risk.repository;

import org.springframework.data.repository.CrudRepository;
import com.grc.risk.entity.RiskAssessment;

public interface RiskAssessmentDao extends CrudRepository<RiskAssessment, Long> {
}
