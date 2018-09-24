package in.deepstudio.quatationmaker.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import in.deepstudio.quatationmaker.service.QuatationService;

@Component("quatationService")
@Transactional
public class QuatationServiceImpl implements QuatationService{

	@Override
	public String sendMail(final String userName, final String password, String host,int port, Properties properties , String to , 
			String subject , String mailBody, String htmlContent) 
	{
		String Msg = "";
		Session session;
		try{
			session = Session.getDefaultInstance(properties, new Authenticator() {
	        	 protected PasswordAuthentication getPasswordAuthentication() {
	        		 return new PasswordAuthentication(userName, password);
	        	 }
	         });
			 MimeMessage message = new MimeMessage(session);
			 message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
			 message.setSubject(subject);
			 System.out.println("To Address :: "+to);
			 mailBody = "<h4>Please Find Attached Order Quatation Sheet.</h4><br/>"+mailBody+"<br/><br/>Thanks and Regards,<br/>Deep Studio."; 
			 BodyPart messageBodyPart = new MimeBodyPart();
	       	 messageBodyPart.setContent(mailBody, "text/html"); 
	       	 Multipart multipart = new MimeMultipart();
			 multipart.addBodyPart(messageBodyPart); // Mail Body Part Added to multipart
			 BodyPart attachmentBodyPart=  new MimeBodyPart();
			 
			 File file = new File("generatedQuatationTemplate.html");
				
			 try(FileOutputStream fop = new FileOutputStream(file)) {
		 
					// if file doesn't exists, then create it
					if (!file.exists()) {
						file.createNewFile();
					}
		 
					// get the content in bytes
					byte[] contentInBytes = htmlContent.getBytes();
		 
					fop.write(contentInBytes);
					fop.flush();
					fop.close();
		 
					System.out.println("Done");
		 
				} catch (IOException e) {
					e.printStackTrace();
				}
			 
			/*
			 File file = new File("C:/Users/Raj-Macbook/Desktop/Deep Studio/temp/testpdf.pdf");
			try{
				Document document = new Document(PageSize.LETTER);
				PdfWriter pdfWriter = PdfWriter.getInstance
				           (document, new FileOutputStream(file));
				document.open();
				document.addSubject("Deep Studio Order Quatation");
				XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
				worker.parseXHtml(pdfWriter, document, new StringReader(htmlContent));
				document.close();
			    System.out.println("Done.");
			}catch (Exception e) {
			      e.printStackTrace();
			    }*/
			 DataSource source =  new FileDataSource(file);
			 attachmentBodyPart.setDataHandler(new DataHandler(source));
			 attachmentBodyPart.setFileName("quatation.html");
			 multipart.addBodyPart(attachmentBodyPart);
        	 message.setContent(multipart);
			 
			 System.out.println("Ready To Send");
			 Transport.send(message);
			 Msg = "Mail Sent.";
		}
		catch(AuthenticationFailedException authFail){
			System.out.println("Authentication Failed");
			Msg = "Authentication Failed.";
			authFail.printStackTrace();
		}
		catch(MessagingException msgExc){
			System.out.println("Exception due to for other failures");
			Msg = "Exception due to for other failures.";
			msgExc.printStackTrace();
		}
		catch(IllegalStateException illexc){	
			System.out.println("Exception due to service is already connected");
			Msg = "Exception due to service is already connected.";
			illexc.printStackTrace();
		}
		
		return Msg;
	}
	
}
