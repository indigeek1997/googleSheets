package com.satyam.interview;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

public class UserController {
	@Controller
	public class StudentController {

	   @RequestMapping(value = "/user", method = RequestMethod.GET)
	   public ModelAndView user() {
	      return new ModelAndView("form", "command", new User());
	   }
	   
	   @RequestMapping(value = "/addUser", method = RequestMethod.POST)
	   public void addUser(@ModelAttribute("SpringWeb")User user, 
	      ModelMap model) {
	      model.addAttribute("name", user.getName());
	      model.addAttribute("phone", user.getPhone());
	      
	      GoogleAuthorizeUtil ob = new GoogleAuthorizeUtil();
	      try {
			ob.execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }
	}
}
