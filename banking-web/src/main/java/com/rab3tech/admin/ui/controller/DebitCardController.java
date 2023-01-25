package com.rab3tech.admin.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rab3tech.admin.service.DebitCardTypeService;
import com.rab3tech.vo.DebitCardTypeVO;

@Controller
@RequestMapping("/admin")
public class DebitCardController {
	
	@Autowired 
	private DebitCardTypeService debitCardTypeService;
	
	@GetMapping("/debit/card") // this below code will display the thyme leaf in browser.
	public String showDebitCardtype(Model model) {
		DebitCardTypeVO debitCardTypeVO = new DebitCardTypeVO();
		model.addAttribute("debitCardTypeVO", debitCardTypeVO);
		return "admin/addDebitCard";

	}
	
	@PostMapping("/debit/card")										// this object come from thymeleaf
	public String postDebitCardtype(@ModelAttribute DebitCardTypeVO debitCardTypeVO , Model model ) { 
		// it will bring loaded data from the 	thyme to post into the database. 
	
		debitCardTypeService.save(debitCardTypeVO);
		DebitCardTypeVO ddebitCardTypeVO = new DebitCardTypeVO();
		model.addAttribute("debitCardTypeVO", ddebitCardTypeVO);
		model.addAttribute("message" , "Information is submited  sucessfully");
		return "admin/addDebitCard";
	}
	
	@GetMapping("/debit/cards") 
	public String DebitCardtypeview(Model model) {
		model.addAttribute("debitCardTypelistVO",debitCardTypeService.showDebitCardTypeList());
		return "admin/listOFDebitCard";

	}

}