package model;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Mail {
    public static void main(String[] args) {
        // Put recipient�s address
        String to = "phoebeevans14@gmail.com";

        // Put sender�s address
        String from = "caribou.inn.denali@gmail.com";
        final String username = "6ce782621e4ff2";//username generated by Mailtrap
        final String password = "84d4dd939a7641";//password generated by Mailtrap
       
        // Paste host address from the SMTP settings tab in your Mailtrap Inbox
        String host = "sandbox.smtp.mailtrap.io";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");//it�s optional in Mailtrap
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "2525");// use one of the options in the SMTP settings tab in your Mailtrap Inbox

        // Get the Session object.
        Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field
            message.setFrom(new InternetAddress(from));

            // Set To: header field
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject("My first message with JavaMail");

            // Put the content of your message
            message.setText("Hi there, this is my first message sent with JavaMail");

            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}