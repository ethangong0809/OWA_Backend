package gov.virginia.dmas.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.virginia.dmas.entity.ElectedOfficialRequestInternalEntity;

public interface ElectedOfficialRequestInternalRepository  extends JpaRepository<ElectedOfficialRequestInternalEntity, Long>{

	public ElectedOfficialRequestInternalEntity findByIdAndConstEmail(Long id, String email);
}
