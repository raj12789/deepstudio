package in.deepstudio;

import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test {
	
	
	public static void main(String[] args) {
		 
		
		
			String password = "raj12789R";
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode(password);
	 
			System.out.println(hashedPassword);
		
		
//		String host="smtp.gmail.com";//change accordingly  
//		int port=465;
//		String to="raj12789@gmail.com";//change accordingly  
//		final String userName="raj.patel1278@gmail.com";//change accordingly  
//		final String password="daredraj";//change accordingly  
//		Properties properties = System.getProperties();  	
//		properties.put("mail.smtp.auth", "true");
//		properties.put("mail.smtp.starttls.enable","true");
//		properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
//		properties.put("mail.smtp.host", host);
//		properties.put("mail.smtp.socketFactory.port",port);
//		properties.put("mail.smtp.port",port);
//      
//		System.out.println("Properties are Set up");
//		System.out.println("Host-Port before Send mail"+host+" : "+port);
//		Session session;
//		try{
//			session = Session.getDefaultInstance(properties, new Authenticator() {
//	        	 protected PasswordAuthentication getPasswordAuthentication() {
//	        		 return new PasswordAuthentication(userName, password);
//	        	 }
//	         });
//			 Message message = new MimeMessage(session);
//			 message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
//			 System.out.println("To Address :: "+to);
//			 message.setSubject("Deep Studio Quatation");
//			 message.setContent("<h1>sending html mail check</h1>","text/html" ); 
//			 System.out.println("Ready To Send");
//			 Transport.send(message);
//			 System.out.println("message sent...."); 
//		}
//		catch(AuthenticationFailedException authFail){
//			System.out.println("Authentication Failed");	
//			authFail.printStackTrace();
//		}
//		catch(MessagingException msgExc){
//			System.out.println("Exception due to for other failures");
//			msgExc.printStackTrace();
//		}
//		catch(IllegalStateException illexc){	
//			System.out.println("Exception due to service is already connected");
//			illexc.printStackTrace();
//		}
		
	  }
}
