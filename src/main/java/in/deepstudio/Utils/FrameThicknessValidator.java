package in.deepstudio.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import in.deepstudio.framingcentre.domain.FrameThickness;
import in.deepstudio.framingcentre.service.FrameThicknessService;


@Component
public class FrameThicknessValidator implements Validator{

	static Logger logger = LoggerFactory.getLogger(FrameThicknessValidator.class);
	@Autowired
	private FrameThicknessService frameThicknessService;
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return FrameThickness.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object arg0, Errors arg1) 
	{
		// TODO Auto-generated method stub
		logger.info("Validating ...");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1,"frameThicknessSize", "required.frameThicknessSize","FrameThicknessSize is Required (Default Message)");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1,"frameMeasurementsType", "required.frameMeasurementsType","FrameMeasurementsType is Required (Default Message)");
		
		//TODO : Need to update logic because it cause problem at update time
		FrameThickness frameThickness = (FrameThickness) arg0;
		
		if(frameThickness.getFrameThicknessId() == null && frameThicknessService.findByFrameThicknessSizeAndFrameMeasurementsType(frameThickness.getFrameThicknessSize(),frameThickness.getFrameMeasurementsType()) != null){
			arg1.rejectValue("frameThicknessSize", "uniqueConstraintViolation.frameThicknessSizeAndMeasurementsTypeCombination","Frame ThicknessSize & MeasurementsType combination is already exists (Default Message)");
		}
	}
	
	
	
}
