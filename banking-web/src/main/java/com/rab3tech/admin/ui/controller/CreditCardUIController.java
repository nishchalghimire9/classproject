package com.rab3tech.admin.ui.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rab3tech.admin.dao.repository.CreditCardTypeRepository;
import com.rab3tech.admin.service.CreditCardTypeService;
import com.rab3tech.dao.entity.CreditCardTypeEntity;
import com.rab3tech.vo.CreditCardTypeVO;

@Controller
@RequestMapping("/admin") //this is prefix
public class CreditCardUIController {
	
	@Autowired
	//private CreditCardTypeRepository creditCardTypeRepository;
	private CreditCardTypeService creditCardTypeService;
	@GetMapping("/credit/card")
	public String showAddCreditCardType( Model model) {  
		CreditCardTypeVO creditCardTypeVO = new CreditCardTypeVO();
		model.addAttribute("creditCardTypeVO" ,creditCardTypeVO);
		return "admin/addCreditcard";	
	}
	
	@PostMapping("/credit/card") // after we fill the data we can get in the request model.ALL THE DATA COME HERE
	public String AddCreditCardType( @ModelAttribute CreditCardTypeVO creditCardTypeVO , Model model ) {   
		// Transferring data from view to entity
//		CreditCardTypeEntity creditCardTypeEntity = new CreditCardTypeEntity(); // here we create object of entity to copy from Vo
//		BeanUtils.copyProperties
//		
//		
//		(creditCardTypeVO, creditCardTypeEntity); //
//		creditCardTypeRepository.save(creditCardTypeEntity);
		creditCardTypeService.save(creditCardTypeVO);
		CreditCardTypeVO ccreditCardTypeVO = new CreditCardTypeVO();  // create an  carry the data.
		model.addAttribute("creditCardTypeVO",ccreditCardTypeVO); //  // this line is used to carry the blank data means to clean the field and show empty field
		model.addAttribute("message" ,"Entry is created sucessfuly");
		return "admin/addCreditcard";
	}	
	
	
	
	@GetMapping("/credit/cards")
	public String showAddCreditCardTypes( Model model) {  
		model.addAttribute("creditCardTypeVOList" ,creditCardTypeService.showCreditCardTypeList());
		return "admin/listCreditcard";	
	}
	
	
	

}
