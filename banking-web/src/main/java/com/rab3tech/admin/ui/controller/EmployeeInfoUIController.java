package com.rab3tech.admin.ui.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rab3tech.admin.service.EmployeePersonalService;
import com.rab3tech.vo.EmployeeVO;

@Controller
@RequestMapping("/admin")
public class EmployeeInfoUIController {
	@Autowired
	private EmployeePersonalService employeePersonalService;
	
	//THIS CODE WILL DISPLAY THE  FORM TO ENTER INPUT
	
	@GetMapping("/employee/info")
	public String displayinfopage(Model model ) {
		EmployeeVO employeeVO = new EmployeeVO();
		model.addAttribute("employeeVO" ,employeeVO);
		return "admin/employeeInfo";		
	}
	//THIS CODE IS USED TO SEND THE DATA TO DATA BASE FROM AND REFRESH THE PAGE
	
	@PostMapping("/employee/info")
	public String postEmployeeInfo(@ModelAttribute EmployeeVO employeeVO , Model model) {
		employeePersonalService.save(employeeVO);
		EmployeeVO eemployeeVO = new EmployeeVO();
		model.addAttribute("employeeVO",eemployeeVO);
		model.addAttribute("message", "Employee information is entered sucessfully.");
		return "admin/employeeInfo";	
	}
	// THIS CODE WILL DELETE THE EMPLOYEE FROM THE LIST .
	
	@GetMapping("/deleteEmployeeInfo")
	public String deleteEmployeeById(@RequestParam int id, Model model ) {
		employeePersonalService.deleteById(id);
		List<EmployeeVO> employeeVO = employeePersonalService.findAll();
		model.addAttribute("employeeVO" ,employeeVO);
		return "redirect:/admin/employee/infolist";		
	}
	// THIS CODE WILL DISPLAY THE LIST OF EMPLOYEE BY FETCHING FROM THE DATA BASE.
	
	@GetMapping("/employee/infolist")
	public String displayListofEmpInfo(Model model ) {
		model.addAttribute("employeelist", employeePersonalService.findAll()); // we call service layer method here 
		return "admin/employeeList";
	}	
	//	THIS CODE IS FEATCHING THE DATA BY ID TO UPDATE THE RECORD.
	@GetMapping("/editEmployeeInfo")
	public String showupdateEmployeeById(@RequestParam int id, Model model ) {
		EmployeeVO employeeVO=	employeePersonalService.findById(id);
	// THIS HAS  PREPOPULATED DATA
		model.addAttribute("employeeVO" ,employeeVO);
		return "admin/employeeInfo";
}
//	THIS CODE IS POSTING  THE DATA BY ID AFTER THE  RECODRD IS UPDATED .
	@PostMapping("/editEmployeeInfo")
	public String updateEmployeeById(@ModelAttribute EmployeeVO employeeVO, Model model ) {
		employeePersonalService.updateById(employeeVO);
		model.addAttribute("employeeVO" ,employeeVO);
		return "admin/employeeList";
			//return "redirect:/admin/employee/infolist"; // we can use this as well with nodoubt.
}
	
}