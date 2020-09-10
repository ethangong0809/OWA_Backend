package gov.virginia.dmas.services;

public interface MailService {
	
	public boolean sendEmailNotification(String recepient, String ticketID);

}
