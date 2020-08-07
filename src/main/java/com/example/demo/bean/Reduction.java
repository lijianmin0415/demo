package com.example.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
@AllArgsConstructor
public class Reduction implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5230039234195939194L;

    private String ip;

    private String port;

    private String usename;

    private String password;


}