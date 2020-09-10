package gov.virginia.dmas.services;

import gov.virginia.dmas.dto.ElectedOfficialRequestDto;
import gov.virginia.dmas.dto.ElectedOfficialResponseDto;
import gov.virginia.dmas.dto.GeneralResponseDto;
import gov.virginia.dmas.dto.GeneralRequestDto;
import gov.virginia.dmas.dto.MediaResponseDto;
import gov.virginia.dmas.dto.MediaRequestDto;
import gov.virginia.dmas.dto.ReportProblemResponseDto;
import gov.virginia.dmas.dto.StatusRequestDto;
import gov.virginia.dmas.dto.ReportProblemRequestDto;
import gov.virginia.dmas.dto.StatusResponseDto;
import gov.virginia.dmas.exception.MailNotSentException;
import gov.virginia.dmas.exception.ResourceNotFoundException;

public interface RequestService {
	
	public GeneralResponseDto saveGeneralRequest(GeneralRequestDto generalRequest) throws ResourceNotFoundException, MailNotSentException;
	
	public MediaResponseDto saveMediaRequest(MediaRequestDto mediaRequest) throws ResourceNotFoundException;
	
	public ElectedOfficialResponseDto saveElectedOfficialRequest(ElectedOfficialRequestDto electedOfficialRequest) throws ResourceNotFoundException;
	
	public ReportProblemResponseDto saveReportProblemRequest(ReportProblemRequestDto reportProblemRequest) throws ResourceNotFoundException;

	public StatusResponseDto getStatus(StatusRequestDto statusRequest) throws ResourceNotFoundException;
}
