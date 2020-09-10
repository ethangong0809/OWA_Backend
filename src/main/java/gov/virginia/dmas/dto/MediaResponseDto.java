package gov.virginia.dmas.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import gov.virginia.dmas.serializer.DeadlineDeserializer;
import gov.virginia.dmas.serializer.DeadlineSerializer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown=true)
@Getter @Setter @ToString
public class MediaResponseDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Timestamp deadline;
	
	private boolean lessThan24;
	
	private String description;
	
	private RequestorResponseDto requestor;
	
	@JsonSerialize(using = DeadlineSerializer.class)
	public Timestamp getDeadline() {
		return deadline;
	}
	
	@JsonDeserialize(using = DeadlineDeserializer.class)
	public void setDeadline(Timestamp deadline) {
		this.deadline = deadline;
	}

}
