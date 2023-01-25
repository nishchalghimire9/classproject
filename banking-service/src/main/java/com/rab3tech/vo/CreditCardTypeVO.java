package com.rab3tech.vo;

import lombok.Data;

@Data //  if we put notation we do not need to put getter and setter. but need to place dependency in the pom file.
public class CreditCardTypeVO {

	private int cid;
	private String name;
	private String code;
	private String type;
	private String description;
	private String url;
	
}