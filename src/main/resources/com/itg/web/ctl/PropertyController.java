package com.itg.web.ctl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.itg.dao.IPropertyTypeDAO;
import com.itg.dao.PropertyType;


@Controller
public class PropertyController {
	
	private String viewName;
	
	private IPropertyTypeDAO propertyTypeDAO;
	@SuppressWarnings("unchecked")
	@RequestMapping("/property.do")
	public String execute(@RequestParam(value="propertyType") int propertyType, ModelMap map, String keyValue, String keyDate, HttpServletRequest request,HttpServletResponse response){
		
	    PropertyType pt = propertyTypeDAO.findPropertyTypeById(propertyType);
	    map.put("propertyType_id", propertyType);
	    map.put("propertyType_text", pt.getText());
	    map.put("title", pt.getText());
		
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public String getViewName() {
		return viewName;
	}

	public IPropertyTypeDAO getPropertyTypeDAO() {
		return propertyTypeDAO;
	}

	public void setPropertyTypeDAO(IPropertyTypeDAO propertyTypeDAO) {
		this.propertyTypeDAO = propertyTypeDAO;
	}
}
