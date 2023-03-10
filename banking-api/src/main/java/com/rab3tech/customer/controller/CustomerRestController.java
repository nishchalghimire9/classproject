package com.rab3tech.customer.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rab3tech.customer.service.CustomerService;
import com.rab3tech.customer.service.CustomerTransactionService;
import com.rab3tech.customer.service.LoginService;
import com.rab3tech.utils.PasswordGenerator;
import com.rab3tech.vo.ApplicationResponseVO;
import com.rab3tech.vo.ChangePasswordRequestVO;
import com.rab3tech.vo.CustomerTransactionVO;
import com.rab3tech.vo.CustomerVO;
import com.rab3tech.vo.LoginRequestVO;
import com.rab3tech.vo.LoginVO;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v3")
public class CustomerRestController {
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
    private CustomerTransactionService customerTransactionService;
	
	@Autowired
	private CustomerService customerService;
	
	 @Autowired
	 private BCryptPasswordEncoder bCryptPasswordEncoder;
	 
	 
	@GetMapping("/employee/customerList")
	public List<CustomerVO>  showCustomerList(@RequestParam(required = false) String filter,
			Model model) {
		  List<CustomerVO> customerVOs=null;
		  if(filter!=null && !"all".equalsIgnoreCase(filter)) {
			  customerVOs=customerService.findCustomers(filter);
		  }else {
			  customerVOs=customerService.findCustomers();  
		  }
		   return customerVOs; //Java object will be converted into JSON data
	}
	 
	 
    @GetMapping("/customer/lockunlock")
	public ApplicationResponseVO customerLock(String userid,String status) {
		   customerService.updateCustomerLockStatus(userid, status);
		   ApplicationResponseVO applicationResponseVO=new ApplicationResponseVO();
		   applicationResponseVO.setCode(200);
		   applicationResponseVO.setStatus("success");
		   applicationResponseVO.setMessage("Lock status is updated!");
		   return applicationResponseVO;
	}
		
		
	 
	 @GetMapping("/customer/customerList")
		public  List<CustomerVO> showCustomerList(@RequestParam(required = false) String filter) {
		  List<CustomerVO> customerVOs=null;
		  if(filter!=null) {
			  customerVOs=customerService.findCustomers(filter);
		  }else {
			  customerVOs=customerService.findCustomers();  
		  }
		   return customerVOs;
		}
	 
	 @GetMapping("/customer/customerTransaction/{username}")
	 public List<CustomerTransactionVO> showCustomerTransaction(@RequestParam(required=false) String sort,@PathVariable String username) {
			//Here we have to write logic to fetch data
			//This is coming from session
			List<CustomerTransactionVO>  customerTransactionVOs=customerTransactionService.findCustomerTransaction(username);
			
			if(sort==null){
				Collections.sort(customerTransactionVOs,(t1,t2)->t2.getDot().compareTo(t1.getDot()));
			} 	
			else{
				if("desc".equals(sort)) {
					Collections.sort(customerTransactionVOs,(t1,t2)->(int)(t2.getAmount()-t1.getAmount()));
				}else {
					Collections.sort(customerTransactionVOs,(t1,t2)->(int)(t1.getAmount()-t2.getAmount()));
				}
				
			}
			return customerTransactionVOs; // thyme leaf
		}
	
	
	@GetMapping("/amount/words") 
	public String convertAmountInWords(int amount) {
		if(amount==100){
			return "HUNDRED";
		}
		if(amount==1000){
			return "THOUSAND";
		}
		if(amount==5000){
			return "5 THOUSAND";
		}
		return "NA";
	}
	
	
	@GetMapping("/scustomers")
	public List<CustomerVO> searchCustomers(@RequestParam String searchText){
		CustomerVO customerVO=customerService.searchCustomer(searchText);
		return null;
	}
	
	
	//{   "loginid":"nagen@gmail.com",
	 //      "passcode":"2938939",
	  //      "newpassword":"*(#$*$*$*$$&"
	//
     // } @RequestBody // it takes JSON data from request body and converts into Java Object
	@PostMapping("/customer/change/password")
	public ApplicationResponseVO updateCustomerPassword(@RequestBody ChangePasswordRequestVO changePasswordRequestVO) {

		String status=loginService.updatePassword(changePasswordRequestVO);
		ApplicationResponseVO applicationResponseVO=new ApplicationResponseVO();
		if("success".equalsIgnoreCase(status)) {
			applicationResponseVO.setCode(200);
			applicationResponseVO.setStatus("success");
			applicationResponseVO.setMessage("Your password is updated successfully.");
			return applicationResponseVO;
		}else {
			applicationResponseVO.setCode(0);
			applicationResponseVO.setStatus("fail");
			applicationResponseVO.setMessage(status);
			return applicationResponseVO;
		}
	}
	
	
	@GetMapping("/customer/passcode")
	// var promise= fetch("v3/customer/passcode?usernameOrEmail="+usernameEmail); 	
	public ApplicationResponseVO sendPassCode(@RequestParam("usernameOrEmail") 
	String usernameOrEmail) {
		String email=usernameOrEmail;
		
		String passCode=PasswordGenerator.generateRandomPassword(8);
		String result=loginService.updatePassCode(email,passCode);
		if("notexist".equalsIgnoreCase(result)) {
			ApplicationResponseVO applicationResponseVO=new ApplicationResponseVO();
			applicationResponseVO.setCode(0);
			applicationResponseVO.setStatus("fail");
			applicationResponseVO.setMessage("Sorry, this username or email does not exist , "+email);
			return applicationResponseVO;
		}
		try {
			 //below line will send the email
			 SimpleMailMessage mailMessage = new SimpleMailMessage();
	         mailMessage.setTo(email);
		     mailMessage.setSubject("Regarding your passcode to change password");
	         mailMessage.setText("Hey! "+result+" ,   your password code is  = "+passCode);
		     mailMessage.setFrom("javahunk100@gmail.com");
		     System.out.println("Your passcode is  = "+passCode);
	         javaMailSender.send(mailMessage);
	         
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Your passcode is  = "+passCode);
		}
		ApplicationResponseVO applicationResponseVO=new ApplicationResponseVO();
		applicationResponseVO.setCode(200);
		applicationResponseVO.setStatus("success");
		applicationResponseVO.setMessage("Passcode has been sent on your email "+email);
		return applicationResponseVO;
	}
	
	
	@PostMapping("/user/login")
	public ApplicationResponseVO authUser(@RequestBody LoginRequestVO loginRequestVO) {
		//{
		 //  "username":"javahunk100@gmail.com",
		  // "password":"test@1231"
		//}
		//this loginvo contains password
		Optional<LoginVO>  optional=loginService.findUserByUsername(loginRequestVO.getUsername());
		ApplicationResponseVO applicationResponseVO=new ApplicationResponseVO();
		if(optional.isPresent()) {
			//test@123
			String password=loginRequestVO.getPassword();
			//$2a$10$hMLCz4az4cSHIN9ADIF5F.RobD4wxS49Q0DrEYHbWf0cutfcA9tZ.
			String encodedPassword=optional.get().getPassword();
			boolean b=bCryptPasswordEncoder.matches(password, encodedPassword);
			//compare the password
			//"test@123"
			if(b==false){
				applicationResponseVO.setMessage("password is not correct");
			}else{
				applicationResponseVO.setMessage("Userid is correct");
			}
			applicationResponseVO.setCode(200);
			applicationResponseVO.setStatus("success");
		
		}else {
			applicationResponseVO.setCode(400);
			applicationResponseVO.setStatus("fail");
			applicationResponseVO.setMessage("Userid is not correct");
		}
		return applicationResponseVO;
	}
}
