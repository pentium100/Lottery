package com.itg.web.ctl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.itg.dao.PropertyType;


@Controller
public class MatchController {
	
	private String viewName;
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/match")
	public String execute(ModelMap map, String keyValue, String keyDate, HttpServletRequest request,HttpServletResponse response){
		

		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public String getViewName() {
		return viewName;
	}

	
}
