package gov.virginia.dmas.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.virginia.dmas.entity.ReportProblemInternalEntity;

public interface ReportProblemInternalRepository extends JpaRepository<ReportProblemInternalEntity, Long>{

	public ReportProblemInternalEntity findByIdAndMemEmail(Long id, String email);
}
