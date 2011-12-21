package com.itg.dao;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class FilterItemOpt {

	public static FilterItem makeFilter(String type, String comparison, String field, String[] values){
		FilterItem fi = new FilterItem();
		fi.setType(type);
		fi.setComparison(comparison);
		fi.setField(field);			
		
		
		List<String> valueList = new ArrayList();
		
		for(String v : values){
			valueList.add(v);
		}
		fi.setValues(valueList);
		return fi;
		
	}
	
	
	public static List<FilterItem> getFilterFromRequest(HttpServletRequest request){
		
		List<FilterItem> l = new ArrayList<FilterItem>();
		
		int i = 0;
		
		while(null!=request.getParameter("filter["+String.valueOf(i)+"][field]")){
			FilterItem fi = new FilterItem();
			fi.setType(request.getParameter("filter["+String.valueOf(i)+"][data][type]"));
			fi.setComparison(request.getParameter("filter["+String.valueOf(i)+"][data][comparison]"));
			fi.setField(request.getParameter("filter["+String.valueOf(i)+"][field]"));			
			
			String[] values = request.getParameterValues("filter["+String.valueOf(i)+"][data][value]");
			List<String> valueList = new ArrayList<String>();
			
			for(String v : values){
				valueList.add(v);
			}
			fi.setValues(valueList);
			l.add(fi);
			
			i++;
		}
		
		return l;
		
	}
}
