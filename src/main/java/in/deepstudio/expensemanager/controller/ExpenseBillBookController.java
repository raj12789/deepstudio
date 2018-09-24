package in.deepstudio.expensemanager.controller;

import in.deepstudio.Utils.Constants;
import in.deepstudio.Utils.ExpenseBillBookValidator;
import in.deepstudio.expensemanager.domain.ExpenseBillBook;
import in.deepstudio.expensemanager.service.ExpenseBillBookService;
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
@RequestMapping(value="/expenseManager/expenseBillBook")
public class ExpenseBillBookController {

	static Logger logger = LoggerFactory.getLogger(ExpenseBillBookController.class);
	@Autowired
	private ExpenseBillBookService expenseBillBookService;
	@Autowired
	private ExpenseTypeService expenseTypeService;
	@Autowired
	private ExpenseBillBookValidator validator;
	
	@RequestMapping  
    public ModelAndView home(){
		ModelAndView mv = new ModelAndView("expenseManager/expenseBillBook");
		mv.addObject("expenseList", expenseBillBookService.findAll());
		mv.addObject("objExpense", new ExpenseBillBook());
		mv.addObject("expenseTypes", expenseTypeService.findAll());
		mv.addObject("expenseType", "new");
		return mv;
    }
	
	@RequestMapping(value = {"/", "/viewAllExpenseDetails"},method=RequestMethod.GET)
	public ModelAndView viewAllDetails(@ModelAttribute("objExpense") ExpenseBillBook objExpense,
			@ModelAttribute("expenseType") String expenseType){
		logger.info("PRG Redirection to View....");
		ModelAndView mv = new ModelAndView("expenseManager/expenseBillBook");
		List<ExpenseBillBook> expenseList = expenseBillBookService.findAll();
		if(expenseList !=null){
			mv.addObject("expenseList", expenseList);
			mv.addObject("objExpense", objExpense);
			mv.addObject("expenseTypes", expenseTypeService.findAll());
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
			@ModelAttribute("objExpense") ExpenseBillBook objExpense,
			BindingResult result ,
			HttpServletRequest request,
			final RedirectAttributes redirectAttributes,
			ModelMap model){
		validator.validate(objExpense, result);
		if(result.hasErrors()){
			logger.info("Something Goes Wrong..");
			model.addAttribute("expenseType", "new");
			model.addAttribute("expenseList", expenseBillBookService.findAll());
			model.addAttribute("expenseTypes", expenseTypeService.findAll());
			return "expenseManager/expenseBillBook";
		}
		redirectAttributes.addFlashAttribute("expenseType", "new");
		if(objExpense.getExpenseBillBookId() == null){
			logger.info("Creating New Record...");
			objExpense.setCreatedDate(new Date());
		}else{
			logger.info("Updating Existing Record...");
			ExpenseBillBook eInfo =  expenseBillBookService.findOne(objExpense.getExpenseBillBookId());
			objExpense.setCreatedDate(eInfo.getCreatedDate());
		}
		objExpense.setUpdatedDate(new Date());
		objExpense.setIpAddress(request.getRemoteAddr());
		System.out.println("Expense Date ::: "+objExpense.getExpenseDate());
		expenseBillBookService.save(objExpense);
		
		redirectAttributes.addFlashAttribute("objExpense", new ExpenseBillBook());
		
		return "redirect:/expenseManager/expenseBillBook/viewAllExpenseDetails";
	}
	
	//Delete Expense_ConvernType
	@RequestMapping(value= Constants.DELETE_REQ_MAP,method=RequestMethod.GET)
	public String deleteExpenseInfo(@RequestParam("expenseId") Long id,
			@ModelAttribute("objExpense") ExpenseBillBook objExpense,
			HttpServletRequest request,
			final RedirectAttributes redirectAttributes){
		logger.info("Deleting Existing Record...");
		objExpense.setUpdatedDate(new Date());
		objExpense.setIpAddress(request.getRemoteAddr());
		expenseBillBookService.delete(id);
		redirectAttributes.addFlashAttribute("objExpense", new ExpenseBillBook());
		redirectAttributes.addFlashAttribute("expenseType", "new");
		return "redirect:/expenseManager/expenseBillBook/viewAllExpenseDetails";
	}
	
	//Edit Expense_ConvernType
	@RequestMapping(value=Constants.EDIT_REQ_MAP,method=RequestMethod.GET)
	public String editExpenseInfo(@RequestParam("expenseId") Long id,
			final RedirectAttributes redirectAttributes){
		logger.info("Editing Existing Record...");
		ExpenseBillBook eInfo =  expenseBillBookService.findOne(id);
		redirectAttributes.addFlashAttribute("objExpense", eInfo);
		redirectAttributes.addFlashAttribute("expenseType", "update");
		return "redirect:/expenseManager/expenseBillBook/viewAllExpenseDetails";
	}
	
}
