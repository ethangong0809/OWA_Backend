package gov.virginia.dmas.controller;

import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gov.virginia.dmas.dto.ElectedOfficialRequestDto;
import gov.virginia.dmas.dto.ElectedOfficialResponseDto;
import gov.virginia.dmas.dto.GeneralResponseDto;
import gov.virginia.dmas.dto.GeneralRequestDto;
import gov.virginia.dmas.dto.MediaResponseDto;
import gov.virginia.dmas.dto.MediaRequestDto;
import gov.virginia.dmas.dto.ReportProblemRequestDto;
import gov.virginia.dmas.dto.ReportProblemResponseDto;
import gov.virginia.dmas.dto.StatusRequestDto;
import gov.virginia.dmas.dto.StatusResponseDto;
import gov.virginia.dmas.exception.MailNotSentException;
import gov.virginia.dmas.exception.ResourceNotFoundException;
import gov.virginia.dmas.services.RequestService;


@RestController
@CrossOrigin(origins="*")
public class RequestController {
	
	@Autowired
	RequestService requestService;
	
	final static Logger logger = LogManager.getLogger(RequestController.class);
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public @ResponseBody String welcomeMessage()
	{
		return "Welcome to OCLA Web Application";
	}
	
	@RequestMapping(value="/saveGeneralRequest", method=RequestMethod.POST)
	public @ResponseBody GeneralResponseDto saveGeneralRequest(@Valid @RequestBody GeneralRequestDto generalRequest) throws ResourceNotFoundException, MailNotSentException {
		
		logger.info("Saving the General Request to the list of requests.");

		GeneralResponseDto savedRequest = requestService.saveGeneralRequest(generalRequest);
		return savedRequest;
	}
	
	@RequestMapping(value="/saveMediaRequest", method=RequestMethod.POST)
	public @ResponseBody MediaResponseDto saveMediaRequest(@Valid @RequestBody MediaRequestDto mediaRequest) throws ResourceNotFoundException {
		
		logger.info("Saving the Media request to the list of requests.");

		MediaResponseDto savedRequest = requestService.saveMediaRequest(mediaRequest);
		return savedRequest;
	}
	
	@RequestMapping(value="/saveElectedOfficialRequest", method=RequestMethod.POST)
	public @ResponseBody ElectedOfficialResponseDto saveElectedOffRequest(@Valid @RequestBody ElectedOfficialRequestDto electedOfficialRequest) throws ResourceNotFoundException {
		
		logger.info("Saving the Elected Official request to the list of requests.");

		ElectedOfficialResponseDto savedRequest = requestService.saveElectedOfficialRequest(electedOfficialRequest);
		return savedRequest;
	}
	
	@RequestMapping(value="/saveReportProblemRequest", method=RequestMethod.POST)
	public @ResponseBody ReportProblemResponseDto saveRepProbRequest(@Valid @RequestBody ReportProblemRequestDto reportProblemRequest) throws ResourceNotFoundException {

		logger.info("Saving the Report a Problem request to the list of requests.");
		
		ReportProblemResponseDto savedRequest = requestService.saveReportProblemRequest(reportProblemRequest);
		return savedRequest;
	}
	
	@RequestMapping(value="/getStatus", method = RequestMethod.POST)
	public @ResponseBody StatusResponseDto getStatus(@Valid @RequestBody StatusRequestDto statusRequest) throws ResourceNotFoundException {
		
		logger.info("Getting the request status based on the Ticket Id and Email address.");
		
		StatusResponseDto status = requestService.getStatus(statusRequest);
		return status;
	}
}
