package com.utils;

import java.util.Arrays;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import com.model.Email;

@Component
public class EmailHandlerUtils {

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private Environment environment;

	private static final Logger logger = Logger.getLogger(EmailHandlerUtils.class);
	private static final String CLASS_NAME = EmailHandlerUtils.class.getSimpleName();

	public String sendEmail(Email email) {

		String emailStatus = "EXCEPTION";

		List<String> toEmailIDlist = Arrays.asList(email.getToEmailIDs().split(","));
		String[] toEmailIDStrArr = new String[toEmailIDlist.size()];
		toEmailIDStrArr = toEmailIDlist.toArray(toEmailIDStrArr);
		final String[] toEmailIDStrArrFinal = toEmailIDStrArr;

		String[] ccEmailIDStrArr = null;
		if (email.getCcEmailIDs() != null) {
			List<String> ccEmailIDlist = Arrays.asList(email.getCcEmailIDs().split(","));
			ccEmailIDStrArr = new String[ccEmailIDlist.size()];
			ccEmailIDStrArr = ccEmailIDlist.toArray(ccEmailIDStrArr);
		}

		final String[] ccEmailIDStrArrFinal = ccEmailIDStrArr;

		final String subject = email.getSubject();
		final String msgtxt = email.getMessage();

		logger.debug(CLASS_NAME + " sendEmail() " + " emailTo: " + toEmailIDlist);

		try {
			if (mailSender != null) {
				logger.debug(CLASS_NAME + " sendEmail() - Mail Sender Created Successfully. ");
				mailSender.send(new MimeMessagePreparator() {

					public void prepare(MimeMessage mimeMessage) throws MessagingException {

						MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
						String fromMailID = environment.getProperty("email.Username");
						messageHelper.setFrom(fromMailID);
						messageHelper.setTo(toEmailIDStrArrFinal);
						if (ccEmailIDStrArrFinal != null) {
							messageHelper.setCc(ccEmailIDStrArrFinal);
						}
						messageHelper.setSubject(subject);
						messageHelper.setText(msgtxt);
					}
				});
				emailStatus = "SUCCESS";
			}
		} catch (MailException mailException) {
			emailStatus = "MAILEXCPTION";
			logger.error(CLASS_NAME + " Mail Exception " + mailException.getLocalizedMessage());
		} catch (Exception exception) {
			emailStatus = "EXCEPTION";
			logger.error(CLASS_NAME + " Exception " + exception.getLocalizedMessage());
		}

		logger.debug(CLASS_NAME + " sendEmail() - Returning " + emailStatus);

		return emailStatus;
	}
}
