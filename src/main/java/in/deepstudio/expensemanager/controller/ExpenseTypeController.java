package in.deepstudio.expensemanager.controller;

import in.deepstudio.Utils.Constants;
import in.deepstudio.Utils.ExpenseTypeValidator;
import in.deepstudio.expensemanager.domain.ExpenseType;
import in.deepstudio.expensemanager.service.ExpenseTypeService;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value="/expenseManager/expenseType")
public class ExpenseTypeController {

	static Logger logger = LoggerFactory.getLogger(ExpenseTypeController.class);
	@Autowired
	private ExpenseTypeService expenseTypeService;
	@Autowired
	private ExpenseTypeValidator validator;
	
	@RequestMapping  
    public ModelAndView home(){
		ModelAndView mv = new ModelAndView("expenseManager/expenseType");
		mv.addObject("expenseList", expenseTypeService.findAll());
		mv.addObject("objExpense", new ExpenseType());
		mv.addObject("expenseType", "new");
		return mv;
    }
	
	@RequestMapping(value = {"/", "/viewAllExpenseDetails"},method=RequestMethod.GET)
	public ModelAndView viewAllDetails(@ModelAttribute("objExpense") ExpenseType objExpense,
			@ModelAttribute("expenseType") String expenseType){
		logger.info("PRG Redirection to View....");
		ModelAndView mv = new ModelAndView("expenseManager/expenseType");
		List<ExpenseType> expenseList = expenseTypeService.findAll();
		if(expenseList !=null){
			mv.addObject("expenseList", expenseList);
			mv.addObject("objExpense", objExpense);
			if(!expenseType.equalsIgnoreCase("")){
				mv.addObject("expenseType", expenseType);
			}else{
				mv.addObject("expenseType", "new");
			}
		}
		return mv;
	}
	
	//Save Expense_ConvernType
	@RequestMapping(value= Constants.CREATE_UPDATE_REQ_MAP ,method=RequestMethod.POST)
	public String saveExpenseInfo(
			@ModelAttribute("objExpense") ExpenseType objExpense,
			BindingResult result ,
			HttpServletRequest request,
			final RedirectAttributes redirectAttributes,
			ModelMap model){
		validator.validate(objExpense, result);
		if(result.hasErrors()){
			logger.info("Something Goes Wrong..");
			model.addAttribute("expenseType", "new");
			model.addAttribute("expenseList", expenseTypeService.findAll());
			return "expenseManager/expenseType";
		}
		redirectAttributes.addFlashAttribute("expenseType", "new");
		if(objExpense.getExpenseTypeId() == null){
			logger.info("Creating New Record...");
			objExpense.setCreatedDate(new Date());
		}else{
			logger.info("Updating Existing Record...");
			ExpenseType eInfo =  expenseTypeService.findOne(objExpense.getExpenseTypeId());
			objExpense.setCreatedDate(eInfo.getCreatedDate());
		}
		objExpense.setUpdatedDate(new Date());
		objExpense.setIpAddress(request.getRemoteAddr());
		
		expenseTypeService.save(objExpense);
		
		redirectAttributes.addFlashAttribute("objExpense", new ExpenseType());
		
		return "redirect:/expenseManager/expenseType/viewAllExpenseDetails";
	}
	
	//Delete Expense_ConvernType
	@RequestMapping(value= Constants.DELETE_REQ_MAP,method=RequestMethod.GET)
	public String deleteExpenseInfo(@RequestParam("expenseId") Long id,
			@ModelAttribute("objExpense") ExpenseType objExpense,
			HttpServletRequest request,
			final RedirectAttributes redirectAttributes){
		logger.info("Deleting Existing Record...");
		objExpense.setUpdatedDate(new Date());
		objExpense.setIpAddress(request.getRemoteAddr());
		expenseTypeService.delete(id);
		redirectAttributes.addFlashAttribute("objExpense", new ExpenseType());
		redirectAttributes.addFlashAttribute("expenseType", "new");
		return "redirect:/expenseManager/expenseType/viewAllExpenseDetails";
	}
	
	//Edit Expense_ConvernType
	@RequestMapping(value=Constants.EDIT_REQ_MAP,method=RequestMethod.GET)
	public String editExpenseInfo(@RequestParam("expenseId") Long id,
			final RedirectAttributes redirectAttributes){
		logger.info("Editing Existing Record...");
		ExpenseType eInfo =  expenseTypeService.findOne(id);
		redirectAttributes.addFlashAttribute("objExpense", eInfo);
		redirectAttributes.addFlashAttribute("expenseType", "update");
		return "redirect:/expenseManager/expenseType/viewAllExpenseDetails";
	}
	
}
