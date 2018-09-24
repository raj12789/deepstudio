package in.deepstudio.quatationmaker.service;

import java.util.Properties;

public interface QuatationService {

	String sendMail(final String userName, final String passsword, String host,int port, Properties props , String to , String subject , String mailBody, String htmlContent);
	
}
