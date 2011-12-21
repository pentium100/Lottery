package com.itg.web.ctl;

import java.util.List;
import java.util.Map;

public interface IReportFilter {
	
	public abstract String renderFilter(String userName, String prompt, String componentName, String componentID, String defaultValue, boolean isMulti, boolean allowBlank);
	public abstract String getLabel();	
	public abstract String getMasterName();

    
	abstract List<String> checkAuth(String userName, String authObject, List<String> authValues);
    
    
	
    
}
