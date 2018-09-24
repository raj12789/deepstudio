package in.deepstudio.Utils;


import in.deepstudio.pvq.domain.PvqBillInfo;
import in.deepstudio.pvq.service.PvqBillInfoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class PvqBillDeskValidator implements Validator{

static Logger logger = LoggerFactory.getLogger(PvqBillDeskValidator.class);
	
	@Autowired
	private PvqBillInfoService pvqBillInfoService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return PvqBillInfo.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object arg0, Errors arg1) 
	{
		// TODO Auto-generated method stub
		logger.info("Validating ...");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1,"billNumber", "required.custBillNumber"," Bill Number is Required (Default Message)");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1,"billDate", "required.billDate"," Bill Date is Required (Default Message)");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1,"custName", "required.custName"," Customer Name is Required (Default Message)");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1,"custMobno", "required.custMobno"," Customer Mobile Number is Required (Default Message)");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1,"quatationBillAmount", "required.validfinalBillAmount","Quatation Bill Amount can not be 0 (Default Message)");
		PvqBillInfo pvqBillInfo = (PvqBillInfo) arg0;
		
		if(pvqBillInfo.getPvqBillDetailId() == null && pvqBillInfoService.findByBillNumber(pvqBillInfo.getBillNumber()) != null){
			arg1.rejectValue("billNumber", "uniqueConstraintViolation.custBillNumber","Bill Number is Already Created. (Default Message)");
		}
		
		if(pvqBillInfo.getQuatationBillAmount() == null || Integer.valueOf(pvqBillInfo.getQuatationBillAmount()) < 0){
			arg1.rejectValue("quatationBillAmount", "required.validfinalBillAmount","Quatation Bill Amount can not be 0 (Default Message)");
		}
		if(pvqBillInfo.getPvqBillConcernDetails() == null || pvqBillInfo.getPvqBillConcernDetails().size() <= 0){
			arg1.rejectValue("pvqBillConcernDetails","required.minimumOneConcern","Requires Minimum 1 Concern to submit bill (Default Message)");
		}
		if(pvqBillInfo.getPvqBillOrderSummary() == null || pvqBillInfo.getPvqBillOrderSummary().size() <= 0){
			arg1.rejectValue("pvqBillOrderSummary","required.minimumOneOrderSummary","Requires Minimum 1 Order Summary to submit bill  (Default Message)");
		}
		if(pvqBillInfo.getDepositeAmount() == null || Integer.valueOf(pvqBillInfo.getDepositeAmount()) < 0){
			arg1.rejectValue("depositeAmount", "required.validdepositeAmount","Deposite Bill Amount can not be 0 (Default Message)");
		}
		for(int i=0 ; i < pvqBillInfo.getPvqBillConcernDetails().size() ; i++){
			if(pvqBillInfo.getPvqBillConcernDetails().get(i).getPvqConcernRate().getPvqConcernRateId() == -1 ){
				arg1.rejectValue("pvqBillConcernDetails","required.pvqBillConcernDetails","One of Concern Name has not selected any option. (Default Message)");
			}
		}
		for(int i=0 ; i < pvqBillInfo.getPvqBillOrderSummary().size() ; i++){
			if(pvqBillInfo.getPvqBillOrderSummary().get(i).getPvqBillOrderSummaryDate() == null || pvqBillInfo.getPvqBillOrderSummary().get(i).getPvqBillOrderSummaryDate().equals("")
			|| pvqBillInfo.getPvqBillOrderSummary().get(i).getPvqBillOrderSummaryDate() == null || pvqBillInfo.getPvqBillOrderSummary().get(i).getPvqBillOrderSummaryEventType().equals("")
			|| pvqBillInfo.getPvqBillOrderSummary().get(i).getPvqBillOrderSummaryDate() == null || pvqBillInfo.getPvqBillOrderSummary().get(i).getPvqBillOrderSummaryTime().equals("")){
				arg1.rejectValue("pvqBillOrderSummary","required.pvqBillOrderSummary","One of Order Summary can not be blank. (Default Message)");
			}
		}
		
	}
	
	
}
