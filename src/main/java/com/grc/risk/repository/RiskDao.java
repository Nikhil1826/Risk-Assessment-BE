package com.grc.risk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.grc.risk.entity.Risk;

@Repository
public interface RiskDao extends JpaRepository<Risk, Long> {

}
