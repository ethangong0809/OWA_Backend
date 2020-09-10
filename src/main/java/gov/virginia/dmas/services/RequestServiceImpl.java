package gov.virginia.dmas.services;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.virginia.dmas.dao.ElectedOfficialRequestInternalRepository;
import gov.virginia.dmas.dao.ElectedOfficialRequestRepository;
import gov.virginia.dmas.dao.FormNameRepository;
import gov.virginia.dmas.dao.GeneralRequestRepository;
import gov.virginia.dmas.dao.MediaRequestRepository;
import gov.virginia.dmas.dao.ReportProblemInternalRepository;
import gov.virginia.dmas.dao.ReportProblemRepository;
import gov.virginia.dmas.dao.RequestorInternalRepository;
import gov.virginia.dmas.dao.RequestorRepository;
import gov.virginia.dmas.dto.ElectedOfficialRequestDto;
import gov.virginia.dmas.dto.ElectedOfficialResponseDto;
import gov.virginia.dmas.dto.GeneralResponseDto;
import gov.virginia.dmas.dto.GeneralRequestDto;
import gov.virginia.dmas.dto.MediaResponseDto;
import gov.virginia.dmas.dto.ReasonCodeResponseDto;
import gov.virginia.dmas.dto.MediaRequestDto;
import gov.virginia.dmas.dto.ReportProblemResponseDto;
import gov.virginia.dmas.dto.ReportProblemRequestDto;
import gov.virginia.dmas.dto.RequestorResponseDto;
import gov.virginia.dmas.dto.StatusRequestDto;
import gov.virginia.dmas.dto.StatusResponseDto;
import gov.virginia.dmas.entity.ElectedOfficialRequestEntity;
import gov.virginia.dmas.entity.ElectedOfficialRequestInternalEntity;
import gov.virginia.dmas.entity.FormNameEntity;
import gov.virginia.dmas.entity.GeneralRequestEntity;
import gov.virginia.dmas.entity.MediaRequestEntity;
import gov.virginia.dmas.entity.ReportProblemEntity;
import gov.virginia.dmas.entity.ReportProblemInternalEntity;
import gov.virginia.dmas.entity.RequestorEntity;
import gov.virginia.dmas.entity.RequestorInternalEntity;
import gov.virginia.dmas.exception.MailNotSentException;
import gov.virginia.dmas.exception.ResourceNotFoundException;

@Service
public class RequestServiceImpl implements RequestService {

	final static Logger logger = LogManager.getLogger(RequestServiceImpl.class);

	@Autowired
	FormNameRepository formNameRepository;

	@Autowired
	RequestorRepository requestorRepository;

	@Autowired
	GeneralRequestRepository generalRequestRepository;

	@Autowired
	MediaRequestRepository mediaRequestRepository;

	@Autowired
	ElectedOfficialRequestRepository electedOfficialRequestRepository;

	@Autowired
	ReportProblemRepository reportProblemRepository;

	@Autowired
	RequestorInternalRepository requestorInternalRepository;

	@Autowired
	ElectedOfficialRequestInternalRepository electedOfficialRequestInternalRepository;

	@Autowired
	ReportProblemInternalRepository reportProblemInternalRepository;
	
	@Autowired
	MailService mailService;

	@Transactional
	public GeneralResponseDto saveGeneralRequest(GeneralRequestDto generalRequest) throws ResourceNotFoundException, MailNotSentException {

		logger.info("Inside saveGeneralRequest service");

		GeneralResponseDto savedRequest = new GeneralResponseDto();
		RequestorResponseDto savedRequestor = new RequestorResponseDto();

		GeneralRequestEntity generalRequestEntity = new GeneralRequestEntity();
		RequestorEntity requestorEntity = new RequestorEntity();

		List<RequestorEntity> requestors = new ArrayList<RequestorEntity>();

		String ticketID = getUniqueTicketID();

		FormNameEntity formNameEntity = formNameRepository.findById(generalRequest.getRequestor().getFormID())
				.orElseThrow(() -> new ResourceNotFoundException("Form not found."));

//		Creating General Request entity
		BeanUtils.copyProperties(generalRequest, generalRequestEntity);

//		Creating Requestor entity
		BeanUtils.copyProperties(generalRequest.getRequestor(), requestorEntity);

		requestorEntity.setFormName(formNameEntity);

		requestorEntity.setTicketID(ticketID);

		generalRequestEntity.setRequestorEntity(requestorEntity);

		requestorEntity.setGeneralRequestEntity(generalRequestEntity);

		if (formNameEntity.getRequestors().size() != 0) {
			formNameEntity.getRequestors().add(requestorEntity);
		} else {
			requestors.add(requestorEntity);
			formNameEntity.setRequestors(requestors);
		}

//		Saving and getting the result
		generalRequestEntity = generalRequestRepository.saveAndFlush(generalRequestEntity);
		BeanUtils.copyProperties(generalRequestEntity, savedRequest);
		BeanUtils.copyProperties(generalRequestEntity.getRequestorEntity(), savedRequestor);
		savedRequest.setRequestor(savedRequestor);

//		Sending email notification
//		boolean mailSent = mailService.sendEmailNotification("21sundeep@gmail.com", "E123456");
//		if(!mailSent) {
//			throw new MailNotSentException("Problem in sending Email.");
//		}
		return savedRequest;
	}

	@Transactional
	public MediaResponseDto saveMediaRequest(MediaRequestDto mediaRequest) throws ResourceNotFoundException {

		logger.info("Inside saveMediaRequest service");

		MediaResponseDto savedRequest = new MediaResponseDto();
		RequestorResponseDto savedRequestor = new RequestorResponseDto();

		MediaRequestEntity mediaRequestEntity = new MediaRequestEntity();
		RequestorEntity requestorEntity = new RequestorEntity();

		List<RequestorEntity> requestors = new ArrayList<RequestorEntity>();

		String ticketID = getUniqueTicketID();

		FormNameEntity formNameEntity = formNameRepository.findById(mediaRequest.getRequestor().getFormID())
				.orElseThrow(() -> new ResourceNotFoundException("Form not found."));

//		Creating Media Request Entity
		BeanUtils.copyProperties(mediaRequest, mediaRequestEntity);

//		Creating Requestor entity
		BeanUtils.copyProperties(mediaRequest.getRequestor(), requestorEntity);

		requestorEntity.setFormName(formNameEntity);

		requestorEntity.setTicketID(ticketID);

		mediaRequestEntity.setRequestorEntity(requestorEntity);

		requestorEntity.setMediaRequestEntity(mediaRequestEntity);

		if (formNameEntity.getRequestors().size() != 0) {
			formNameEntity.getRequestors().add(requestorEntity);
		} else {
			requestors.add(requestorEntity);
			formNameEntity.setRequestors(requestors);
		}

//		Saving and getting the result
		mediaRequestEntity = mediaRequestRepository.saveAndFlush(mediaRequestEntity);
		BeanUtils.copyProperties(mediaRequestEntity, savedRequest);
		BeanUtils.copyProperties(mediaRequestEntity.getRequestorEntity(), savedRequestor);
		savedRequest.setRequestor(savedRequestor);

		return savedRequest;
	}

	@Transactional
	public ElectedOfficialResponseDto saveElectedOfficialRequest(ElectedOfficialRequestDto electedOfficialRequest)
			throws ResourceNotFoundException {

		logger.info("Inside saveElectedOffRequest service");

		ElectedOfficialResponseDto savedRequest = new ElectedOfficialResponseDto();
		RequestorResponseDto savedRequestor = new RequestorResponseDto();

		ElectedOfficialRequestEntity electedOfficialEntity = new ElectedOfficialRequestEntity();
		RequestorEntity requestorEntity = new RequestorEntity();

		List<RequestorEntity> requestors = new ArrayList<RequestorEntity>();

		String ticketID = getUniqueTicketID();

		FormNameEntity formNameEntity = formNameRepository.findById(electedOfficialRequest.getRequestor().getFormID())
				.orElseThrow(() -> new ResourceNotFoundException("Form not found."));

//		Creating Elected Official Entity
		BeanUtils.copyProperties(electedOfficialRequest, electedOfficialEntity);

//		Creating Requestor Entity
		BeanUtils.copyProperties(electedOfficialRequest.getRequestor(), requestorEntity);

		requestorEntity.setFormName(formNameEntity);

		requestorEntity.setTicketID(ticketID);

		electedOfficialEntity.setRequestorEntity(requestorEntity);

		requestorEntity.setElectedOfficialRequestEntity(electedOfficialEntity);

		if (formNameEntity.getRequestors().size() != 0) {
			formNameEntity.getRequestors().add(requestorEntity);
		} else {
			requestors.add(requestorEntity);
			formNameEntity.setRequestors(requestors);
		}

//		Saving and getting the result
		electedOfficialEntity = electedOfficialRequestRepository.saveAndFlush(electedOfficialEntity);
		BeanUtils.copyProperties(electedOfficialEntity, savedRequest);
		BeanUtils.copyProperties(electedOfficialEntity.getRequestorEntity(), savedRequestor);
		savedRequest.setRequestor(savedRequestor);
		return savedRequest;
	}

	@Transactional
	public ReportProblemResponseDto saveReportProblemRequest(ReportProblemRequestDto reportProblemRequest)
			throws ResourceNotFoundException {

		logger.info("Inside saveReportProblem service");

		ReportProblemResponseDto savedRequest = new ReportProblemResponseDto();
		RequestorResponseDto savedRequestor = new RequestorResponseDto();

		ReportProblemEntity reportProblemEntity = new ReportProblemEntity();
		RequestorEntity requestorEntity = new RequestorEntity();

		List<RequestorEntity> requestors = new ArrayList<RequestorEntity>();

		String ticketID = getUniqueTicketID();

		FormNameEntity formNameEntity = formNameRepository.findById(reportProblemRequest.getRequestor().getFormID())
				.orElseThrow(() -> new ResourceNotFoundException("Form not found."));

//		Creating Report Problem Entity
		BeanUtils.copyProperties(reportProblemRequest, reportProblemEntity);

//		Creating Requestor Entity
		BeanUtils.copyProperties(reportProblemRequest.getRequestor(), requestorEntity);

		requestorEntity.setFormName(formNameEntity);

		requestorEntity.setTicketID(ticketID);

		reportProblemEntity.setRequestorEntity(requestorEntity);

		requestorEntity.setReportProblemEntity(reportProblemEntity);

		if (formNameEntity.getRequestors().size() != 0) {
			formNameEntity.getRequestors().add(requestorEntity);
		} else {
			requestors.add(requestorEntity);
			formNameEntity.setRequestors(requestors);
		}

//		Saving and getting the result
		reportProblemEntity = reportProblemRepository.saveAndFlush(reportProblemEntity);
		BeanUtils.copyProperties(reportProblemEntity, savedRequest);
		BeanUtils.copyProperties(reportProblemEntity.getRequestorEntity(), savedRequestor);
		savedRequest.setRequestor(savedRequestor);

		return savedRequest;
	}

	@Transactional
	public StatusResponseDto getStatus(StatusRequestDto statusRequest) throws ResourceNotFoundException {

		logger.info("Inside getStatusById service");

		StatusResponseDto status = new StatusResponseDto();
		ReasonCodeResponseDto reason = new ReasonCodeResponseDto();
		RequestorInternalEntity requestorInternalEntity = new RequestorInternalEntity();
		ElectedOfficialRequestInternalEntity electedOfficialRequestInternalEntity = new ElectedOfficialRequestInternalEntity();
		ReportProblemInternalEntity reportProblemInternalEntity = new ReportProblemInternalEntity();

//		Finding the details in the database for the status		
		requestorInternalEntity = requestorInternalRepository.findByTicketIDAndEmail(statusRequest.getTicketID(),
				statusRequest.getEmail());
		if (requestorInternalEntity == null) {
			requestorInternalEntity = requestorInternalRepository.findByTicketID(statusRequest.getTicketID());
			if (requestorInternalEntity != null) {
				electedOfficialRequestInternalEntity = electedOfficialRequestInternalRepository
						.findByIdAndConstEmail(requestorInternalEntity.getId(), statusRequest.getEmail());
				reportProblemInternalEntity = reportProblemInternalRepository
						.findByIdAndMemEmail(requestorInternalEntity.getId(), statusRequest.getEmail());

				if (electedOfficialRequestInternalEntity != null) {
					BeanUtils.copyProperties(requestorInternalEntity, status);
					if(requestorInternalEntity.getReason()==null) {
						reason.setReasonCode((long) 101);
						reason.setDescription("The request is under process.");
						reason.setStatusType("Open");
					}
					else {
						BeanUtils.copyProperties(requestorInternalEntity.getReason(), reason);
					}
					status.setReason(reason);
					return status;
				}
				else if (reportProblemInternalEntity != null) {
					BeanUtils.copyProperties(requestorInternalEntity, status);
					if(requestorInternalEntity.getReason()==null) {
						reason.setReasonCode((long) 101);
						reason.setDescription("The request is under process.");
						reason.setStatusType("Open");
					}
					else {
						BeanUtils.copyProperties(requestorInternalEntity.getReason(), reason);
					}
					status.setReason(reason);
					return status;
				}
				else {
					throw new ResourceNotFoundException("Requestor Not Found.");
				}
			} else {
				throw new ResourceNotFoundException("Requestor Not Found.");
			}

		}
		else {
			BeanUtils.copyProperties(requestorInternalEntity, status);
			if(requestorInternalEntity.getReason()==null) {
				reason.setReasonCode((long) 101);
				reason.setDescription("The request is under process.");
				reason.setStatusType("Open");
			}
			else {
				BeanUtils.copyProperties(requestorInternalEntity.getReason(), reason);
			}
			status.setReason(reason);
			return status;
		}
	}

	public String getUniqueTicketID() {
		SecureRandom rnd = new SecureRandom();
		boolean value = true;
		String ticketID = null;

		List<String> ticketIDs = requestorRepository.getTicketID();
		try {
			while (value) {

				int n = rnd.nextInt(1000000);
				String ticketNumber = String.format("%06d", n);
				ticketID = "E" + ticketNumber;
				if (ticketIDs.contains(ticketID)) {
					continue;
				} else {
					value = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ticketID;
	}
}
