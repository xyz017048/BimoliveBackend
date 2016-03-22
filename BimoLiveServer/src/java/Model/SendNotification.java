/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Chonghuan
 */
public class SendNotification 
{
    private final String username = "bimolive.com";
    private final String password = "the pros";
    private String toMailAddress ="";
    private String typeString = "";
    private String applyStatus ="";
    public enum Type {REGISTER, TEACHERAPPLICATION};
    
    public SendNotification (String toMailAddressString, String type, String applyStatus)
    {
        this.toMailAddress = toMailAddressString;
        this.typeString = type;
        this.applyStatus = applyStatus;
    }
    
    public void send()
    {
        if (typeString.equals( Type.REGISTER.toString()))
            sendRegisterNotify();
        else if (typeString.equals(Type.TEACHERAPPLICATION.toString()))
            sendApplyNotify();
    }
    
    private void sendRegisterNotify()
    {
        sendNotification("Thanks for joining Bimolive.com", "We're excited to have you!\n");
    }
    private void sendApplyNotify()
    {
        sendNotification("Bimolive.com Teacher Application", "Your application to be a teacher at Bimolive.com is " + (applyStatus.equals("approve")?"approved":"declined")+".");
    }
    private void sendNotification (String title, String msg)
    {
        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() 
                {
                    protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username +"@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(toMailAddress));
            message.setSubject(title);
            message.setText(msg);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
