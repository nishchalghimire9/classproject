package com.rab3tech.admin.ui.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rab3tech.admin.service.AccountStatusService;
import com.rab3tech.admin.service.impl.RoleService;
import com.rab3tech.vo.AccountStatusVO;

@Controller
@RequestMapping("/admin")
//http:localhost:4055
//htttp://www.kuebikobank.com/admin/security/questions
@Scope("singleton")
public class AdminUIController {

	@Autowired
	private RoleService roleService;
	@Autowired
	private AccountStatusService accountStatusService;
	
	
	@PostMapping("/editAccountStatus")
	public String posteditAccountStatus(@ModelAttribute AccountStatusVO accountStatusVO) {
		accountStatusService.updateById(accountStatusVO);
	
		//model.addAttribute("accountStatusVO" ,accountStatusVO);
		return "redirect:/admin/account/status";
	}
	// code to edit account status.
	@GetMapping("/editAccountStatus")
	public String editAccountStatus(@RequestParam int id, Model model) {
		AccountStatusVO accountStatusVO = accountStatusService.findById(id);
		//this has prepopulated data 
		model.addAttribute("accountStatusVO" ,accountStatusVO);
		return "admin/editAccountStatus";
	
	}
	
	@GetMapping("/deleteAccountStatus")
	public String deleteAccountStatus(@RequestParam int id, Model model) {
		accountStatusService.deleteById(id);
		List<AccountStatusVO> accountStatusVos = accountStatusService.findAll();
		model.addAttribute("accountStatusVos", accountStatusVos);
		return "redirect:/admin/account/status"; // we can use  redirect because we are going to the same page after delete 
	}										// the code is below with action with account status. 

	@GetMapping("/account/status")
	public String accountstatus(Model model) {
		List<AccountStatusVO> accountStatusVos = accountStatusService.findAll();
		model.addAttribute("accountStatusVos", accountStatusVos);
		return "admin/accountStatus";
	}

	@GetMapping("/roles")
	public String showRoles(Model model) {
		model.addAttribute("roles", roleService.findRoles());
		return "admin/roles";
	}

	@GetMapping("/deleteRole")
	public String deleteRole(int rid, Model model) {
		boolean deleted = roleService.delete(rid);
		if (!deleted) {
			model.addAttribute("message", "This role is associated with customer");
		} else {
			model.addAttribute("message", "This role is deleted success");
		}
		model.addAttribute("roles", roleService.findRoles());
		return "admin/roles";
	}

}
