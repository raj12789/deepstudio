package in.deepstudio.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import in.deepstudio.framingcentre.domain.FrameSize;
import in.deepstudio.framingcentre.service.FrameSizeService;


@Component
public class FrameSizeValidator implements Validator{

	static Logger logger = LoggerFactory.getLogger(FrameSizeValidator.class);
	
	@Autowired
	private FrameSizeService frameSizeService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return FrameSize.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object arg0, Errors arg1) 
	{
		// TODO Auto-generated method stub
		logger.info("Validating ...");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1,"frameSizeWidth", "required.frameSizeWidth"," Frame Width is Required (Default Message)");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1,"frameSizeHeight", "required.frameSizeHeight"," Frame Height is Required (Default Message)");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1,"frameMeasurementsType", "required.frameMeasurementsType"," Frame MeasurementType is Required (Default Message)");
		FrameSize fSize = (FrameSize) arg0;
		if(fSize.getFrameSizeWidth() <= 0){
			arg1.rejectValue("frameSizeWidth", "required.validframeSizeWidth","Please enter proper frame Width (Default Message)");
		}
		if(fSize.getFrameSizeHeight() <= 0){
			arg1.rejectValue("frameSizeHeight", "required.validframeSizeHeight","Please enter proper frame Height (Default Message)");
		}
		
		//TODO : Need to update this logic because it cause problem at update time
		if(fSize.getFrameSizeId() == null && frameSizeService.findByFrameSizeWidthAndFrameSizeHeightAndFrameMeasurementsType(fSize.getFrameSizeWidth(),fSize.getFrameSizeHeight(),fSize.getFrameMeasurementsType()) != null){
			arg1.rejectValue("frameSizeWidth", "uniqueConstraintViolation.frameWidth_Height_MeasureTypeCombination","Frame Width & Height & MeasurementType is already exists (Default Message)");
		}
	}
	
	
	
}
