package com.codecool.shop.email;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailUtil {
    public static void sendEmail (String recipient) throws Exception {
        System.out.println("Sending Email...");
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myEmail = "runtime.error.team.404@gmail.com";
        String myPassword = "asd123.?!";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail, myPassword);
            }
        });

        Message message = prepareMessage(session, myEmail, recipient);
        Transport.send(message);
        System.out.println("Email sent.");
    }

    private static Message prepareMessage(Session session, String myEmail, String recipient) throws Exception {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(myEmail));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        message.setSubject("Order Confirmation");
        message.setText("lmao xd szkruu szkruu");
        // TODO: This needs to work with HTML file
        // message.setContent("PLACE OF HTML CODE", text/html)
        return message;
    }
}
