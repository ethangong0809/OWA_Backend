package gov.virginia.dmas.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import gov.virginia.dmas.validation.ExtendedEmailValidator;
import gov.virginia.dmas.validation.FieldsEquality;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown=true)
@Getter @Setter @ToString
@FieldsEquality(firstFieldName = "email", secondFieldName = "confirmEmail")
public class ElectedOfficialRequestorDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull(message="Please provide a FormID.")
	private Long formID;
	
	@NotEmpty(message="Please tell us about your Virginia residency.")
	private String vaResident;
	
	@NotEmpty(message="Please enter a FirstName.")
	private String firstName;
	
	@NotEmpty(message="Please enter a LastName.")
	private String lastName;

	@NotNull(message="Please enter a phone number.")
	@Pattern(regexp="[\\d]{10}", message="Phone number must be 10 digits.")
	private String phone;
	
	@NotEmpty(message="Please enter a email address.")
	@ExtendedEmailValidator
	private String email;
	
	@NotEmpty(message="Please enter a email address to confirm.")
	@ExtendedEmailValidator
	private String confirmEmail;
	
	private String preferredContact;
	
	private String requestorType;
	
	@NotEmpty
	private String address1;
	
	private String address2;
	
	@NotEmpty
	private String city;
	
	@NotEmpty
	private String state;
	
	@NotNull(message="Please enter a zipcode.")
	@Pattern(regexp="[\\d]{5,9}", message="Please enter a valid zipcode.")
	private String zipcode;
	
	@Pattern(regexp="[\\d]{10}", message="Fax number must be 10 digits.")
	private String fax;
	
	@NotEmpty(message="Please tell us about the status of the request.")
	private String status;
	
	private String createdBy;
}
