package gov.virginia.dmas.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.ToString;

import lombok.Setter;

import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown=true)
@Getter @Setter @ToString
public class StatusResponseDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String ticketID;
	
	private String status;
	
	private ReasonCodeResponseDto reason;

}
