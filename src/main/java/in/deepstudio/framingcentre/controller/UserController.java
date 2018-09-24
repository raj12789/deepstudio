package in.deepstudio.framingcentre.controller;

import in.deepstudio.Utils.UserCreateFormValidator;
import in.deepstudio.framingcentre.domain.UserCreateForm;
import in.deepstudio.framingcentre.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserController.class);
	private final UserService userService;
	private final UserCreateFormValidator userCreateFormValidator;

	@Autowired
	public UserController(UserService userService,
			UserCreateFormValidator userCreateFormValidator) {
		this.userService = userService;
		this.userCreateFormValidator = userCreateFormValidator;
	}

	@InitBinder("form")
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(userCreateFormValidator);
	}

	@PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
	@RequestMapping("/user")
	public ModelAndView getUserPage(@RequestParam("userId") Long id) {
		LOGGER.debug("Getting user page for user={}", id);
		return new ModelAndView("user", "user", userService.getUserById(id));
	}
	
	@PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
	@RequestMapping("/users")
	public ModelAndView getUsersPage() {		
		return new ModelAndView("users","users",userService.getAllUsers());
	}

	@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
	@RequestMapping(value = "/user/create", method = RequestMethod.GET)
	public ModelAndView getUserCreatePage() {
		LOGGER.debug("Getting user create form");
		return new ModelAndView("user_create", "form", new UserCreateForm());
	}

	@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
	@RequestMapping(value = "/user/create", method = RequestMethod.POST)
	public String handleUserCreateForm(
			@ModelAttribute("form") UserCreateForm form,
			BindingResult bindingResult) {
		LOGGER.debug("Processing user create form={}, bindingResult={}", form,
				bindingResult);
		ObjectError error = null;
		if(!(form.getPassword().equals(form.getPasswordRepeated()))){
			error = new ObjectError("password.mismatch","Password Mismatch.");
			bindingResult.addError(error);
		}
		//LOGGER.info("Get Email : "+userService.getUserByEmail(form.getEmail()));
		if(!(form.getId() >= 0) && (userService.getUserByEmail(form.getEmail()) != null)){
			error = new ObjectError("email.exists","Email already exists");
			bindingResult.addError(error);
		}
		if (bindingResult.hasErrors()) {
			// failed validation
			return "user_create";
		}
		try {
			userService.create(form);
		} catch (DataIntegrityViolationException e) {
			// probably email already exists - very rare case when multiple
			// admins are adding same user
			// at the same time and form validation has passed for more than one
			// of them.
			LOGGER.warn(
					"Exception occurred when trying to save the user, assuming duplicate email",
					e);
				bindingResult.reject("email.exists", "Email already exists");
			return "user_create";
		}
		// ok, redirect
		return "redirect:/users";
	}
	
	@PreAuthorize("hasAuthority('SUPER_ADMIN')")
	@RequestMapping(value = "/user/edit", method = RequestMethod.GET)
	public ModelAndView handleEditUserBySuperAdmin(@RequestParam("userId") Long id){
		LOGGER.info("Editing Existing Record...");
		UserCreateForm userform =  userService.findOne(id);
		return new ModelAndView("user_create", "form", userform);
	}
	
	@PreAuthorize("hasAuthority('SUPER_ADMIN')")
	@RequestMapping(value = "/user/delete", method = RequestMethod.GET)
	public String handleDeleteUserBySuperAdmin(@RequestParam("userId") Long id){
		LOGGER.info("Deleting Existing Record...");
		userService.delete(id);
		return "redirect:/users";
	}
}
