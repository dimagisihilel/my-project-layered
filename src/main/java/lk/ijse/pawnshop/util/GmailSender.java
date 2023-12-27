package lk.ijse.pawnshop.util;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class GmailSender {

    public void sendEmailWithAttachment(String toEmail, String subject, String body, String filePath) {

        // SMTP server configuration for Gmail
        String host = "smtp.gmail.com";
        String port = "587";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", port);
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        // Get the default Session object
        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("ranjanpawnbrokers@gmail.com", "nartjhuapoadfbcq");
            }
        });

        try {
            // Create a default MimeMessage object
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header
            message.setFrom(new InternetAddress("ranjanpawnbrokers@gmail.com"));

            // Set To: header field of the header
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

            // Set Subject: header field
            message.setSubject(subject);

            // Create a multipart message
            Multipart multipart = new MimeMultipart();

            // Create the text part
            BodyPart textPart = new MimeBodyPart();
            textPart.setText(body);

            // Create the attachment part
            BodyPart attachmentPart = new MimeBodyPart();
            ((MimeBodyPart) attachmentPart).attachFile(filePath);
           // attachmentPart.setContent(new File(filePath), "application/pdf");

            // Add the parts to the multipart
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(attachmentPart);

            // Set the multipart message to the email
            message.setContent(multipart);

            // Send message
            Transport.send(message);
            System.out.println("Sent email with attachment successfully.");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}