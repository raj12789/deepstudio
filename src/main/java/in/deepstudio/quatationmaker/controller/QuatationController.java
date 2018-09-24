package in.deepstudio.quatationmaker.controller;

import java.util.Properties;

import in.deepstudio.pvq.domain.PvqBillInfo;
import in.deepstudio.pvq.service.PvqBillInfoService;
import in.deepstudio.pvq.service.PvqConcernRateService;
import in.deepstudio.pvq.service.PvqConcernTypeService;
import in.deepstudio.quatationmaker.service.QuatationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Controller
@RequestMapping("/quatation")
public class QuatationController {

	@Autowired
	private PvqBillInfoService pvqBillInfoService;
	@Autowired
	private PvqConcernTypeService pvqConcernTypeService;
	@Autowired
	private PvqConcernRateService pvqConcernRateService;
	@Autowired
	private QuatationService quatationService;
	@Autowired
	private TemplateEngine templateEngine;
	
	@RequestMapping("/previewQuataion")
	public ModelAndView previewQuatation(@RequestParam("custBillingId") Long id){
		ModelAndView mv = new ModelAndView("mail/quatation");
		PvqBillInfo pvqBillInfo =  pvqBillInfoService.findOne(id);
		mv.addObject("concernTypes", pvqConcernTypeService.findAll());
		mv.addObject("concernRates", pvqConcernRateService.findAll());
		mv.addObject("objPvq", pvqBillInfo);
		return mv;
	}
	
	
	@RequestMapping(value="/sendMail",method = RequestMethod.GET,headers ="Accept=*/*")
	public @ResponseBody String sendQuatation(@RequestParam("custBillingId") Long id,@RequestParam("to") String toAddress,
				@RequestParam("mailBody") String mailBody){
		
		String result="";
		String Msg = "";
		String subject="DeepStudio Order Quatation";
		//String mailBody="<h4>Please Find Attached Order Quatation Sheet.</h4> <br/><br/><br/>Thanks and Regards,<br/>Deep Studio.";
		//String to="raj12789@gmail.com";//change accordingly
		
		String host="smtp.gmail.com";//change accordingly  
		int port=465;
		final String userName="raj.patel1278@gmail.com";//change accordingly  
		final String password="daredraj";//change accordingly  
		Properties properties = System.getProperties();  	
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable","true");
		properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.socketFactory.port",port);
		properties.put("mail.smtp.port",port);
      
		System.out.println("Properties are Set up");
		System.out.println("Host-Port before Send mail"+host+" : "+port);
		
		PvqBillInfo pvqBillInfo =  pvqBillInfoService.findOne(id);
		final Context ctx = new Context();
		ctx.setVariable("objPvq", pvqBillInfo);
		final String htmlContent = this.templateEngine.process("mail/quatationMailTemplate", ctx);
		 
		Msg = quatationService.sendMail(userName, password, host, port, properties, toAddress, subject, mailBody, htmlContent);
		
		System.out.println("Send Mail Status :"+Msg);
		result = "{\"Msg\":\""+Msg+"\"}";
    	return result;
	}
}
