package model;


import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class Mail
{
	
	
	//set up session
	Session newSession = null;
	MimeMessage mimeMessage = null;
	
	//send
	public void send(String receipient, String emailSubject, String emailBody) throws AddressException, MessagingException, IOException
	{
		System.out.println("Try to send email");
		Mail mail = new Mail();
		mail.setupServerProperties();
		mail.draftEmail(receipient, emailSubject, emailBody);
		mail.sendEmail();
	}

	//send email final step
	private void sendEmail() throws MessagingException {
		String fromUser = "caribou.inn.denali@gmail.com";  //Enter sender email id
		String fromUserPassword = "lvwkjnhmpntounya";  //Enter sender gmail password , this will be authenticated by gmail smtp server
		String emailHost = "smtp.gmail.com";
		Transport transport = newSession.getTransport("smtp");
		transport.connect(emailHost, fromUser, fromUserPassword);
		transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
		transport.close();
		System.out.println("Email successfully sent!!!");
	}
	
	//draft email
	private MimeMessage draftEmail(String receipient, String emailSubject, String emailBody) throws AddressException, MessagingException, IOException {
		
		mimeMessage = new MimeMessage(newSession);
		
		
		mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(receipient));
		
		mimeMessage.setSubject(emailSubject);
	   
      // CREATE MIMEMESSAGE 
	    // CREATE MESSAGE BODY PARTS 
	    // CREATE MESSAGE MULTIPART 
	    // ADD MESSAGE BODY PARTS ----> MULTIPART 
	    // FINALLY ADD MULTIPART TO MESSAGECONTENT i.e. mimeMessage object 
	    
	    
		 MimeBodyPart bodyPart = new MimeBodyPart();
		 bodyPart.setText(emailBody);
		 MimeMultipart multiPart = new MimeMultipart();
		 multiPart.addBodyPart(bodyPart);
		 mimeMessage.setContent(multiPart);
		 
		 System.out.println("drafted");
		 return mimeMessage;
	}

	//set up server properties
	private void setupServerProperties() {
		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "caribou.inn.denali@gmail.com");
		properties.put("mail.smtp.starttls.enable", "true");
		newSession = Session.getDefaultInstance(properties,null);
		System.out.println("server set up");
	}
	
}