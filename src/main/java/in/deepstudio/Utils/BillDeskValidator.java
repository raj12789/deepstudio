package in.deepstudio.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import in.deepstudio.framingcentre.domain.FrameCustBillingInfo;
import in.deepstudio.framingcentre.service.FrameCustBillingInfoService;


@Component
public class BillDeskValidator implements Validator{

	static Logger logger = LoggerFactory.getLogger(BillDeskValidator.class);
	
	@Autowired
	private FrameCustBillingInfoService frameCustBillingInfoService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return FrameCustBillingInfo.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object arg0, Errors arg1) 
	{
		// TODO Auto-generated method stub
		logger.info("Validating ...");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1,"custBillNumber", "required.custBillNumber"," Bill Number is Required (Default Message)");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1,"billDate", "required.billDate"," Bill Date is Required (Default Message)");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1,"custName", "required.custName"," Customer Name is Required (Default Message)");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1,"custMobno", "required.custMobno"," Customer Mobile Number is Required (Default Message)");
		FrameCustBillingInfo frameCustBillingInfo = (FrameCustBillingInfo) arg0;
		
		if(frameCustBillingInfo.getCustBillingId() == null && frameCustBillingInfoService.findByCustBillNumber(frameCustBillingInfo.getCustBillNumber()) != null){
			arg1.rejectValue("custBillNumber", "uniqueConstraintViolation.custBillNumber","Bill Number is Already Created. (Default Message)");
		}
		
		if(frameCustBillingInfo.getFinalBillAmount() == null || frameCustBillingInfo.getFinalBillAmount() <= 0){
			arg1.rejectValue("finalBillAmount", "required.validfinalBillAmount","Final Bill Amount can not be 0 (Default Message)");
		}
		if(frameCustBillingInfo.getDepositeAmount() == null || Integer.valueOf(frameCustBillingInfo.getDepositeAmount()) < 0){
			arg1.rejectValue("depositeAmount", "required.validdepositeAmount","Deposite Bill Amount can not be 0 (Default Message)");
		}
		if(frameCustBillingInfo.getFrameCustBillingInfoDetail() == null || frameCustBillingInfo.getFrameCustBillingInfoDetail().size() <= 0){
			arg1.rejectValue("frameCustBillingInfoDetail","required.minimumOneConcern","Requires Minimum 1 Concern to submit bill (Default Message)");
		}
		for(int i=0 ; i < frameCustBillingInfo.getFrameCustBillingInfoDetail().size() ; i++){
			if(frameCustBillingInfo.getFrameCustBillingInfoDetail().get(i).getFrameDetails().getFrameQualifiedId() == null ){
				arg1.rejectValue("frameCustBillingInfoDetail","required.frameDetails","Frame Details Can not be unselected (Default Message)");
			}
			if(frameCustBillingInfo.getFrameCustBillingInfoDetail().get(i).getFrameClientType().getClientTypeId() == -1){
				arg1.rejectValue("frameCustBillingInfoDetail","required.frameClientType","Frame ClientType Can not be unselected (Default Message)");
			}
		}
	}
}

