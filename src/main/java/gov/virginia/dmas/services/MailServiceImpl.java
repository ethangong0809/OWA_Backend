package gov.virginia.dmas.services;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService{

	final static Logger logger = LogManager.getLogger(MailServiceImpl.class);
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	private String userName;

	@Override
	public boolean sendEmailNotification(String recepient, String ticketID) {

		logger.info("Inside sendEmailNotification service");
		try {
			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setFrom(userName);
			mail.setTo(recepient);
			mail.setSubject("DMAS Request");
			mail.setText("Hi,\n We have received your request. Below is your ticket ID.\n"
					+ "Ticket ID: "+ticketID);
		
			javaMailSender.send(mail);
			return true;
		}
		catch (MailException e) {
			e.printStackTrace();
		}
		return false;
	}

	
	
}
