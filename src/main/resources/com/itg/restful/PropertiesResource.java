package com.itg.restful;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.itg.dao.IPropertyDAO;
import com.itg.dao.Property;
import com.itg.dao.PropertyDAO;
import com.itg.dao.ReportMemo;
import com.itg.dao.ResponseProperty;
import com.itg.dao.ResponseReportMemo;

public class PropertiesResource implements IPropertiesResource {
	
    private IPropertyDAO propertyDAO;
    
	public Response addProperty(Property p) throws JSONException {
		
		ResponseProperty r = new ResponseProperty();
		try {

			propertyDAO.insertProperty(p);
			r.setSuccess(true);
			r.setMessage("记录已新建!");
			List l = new ArrayList();
			l.add(p);
			r.setProperties(l);

		} catch (Exception e) {

			r.setSuccess(false);
			r.setMessage("记录创建失败:" + e.getMessage());
			List l = new ArrayList();
			l.add(p);
			r.setProperties(l);
			System.out.println(e);

		}
		// return r;
		return Response.ok(r).build();
		
		
	}

	public Response deleteReportMemo(Integer propertyId) throws JSONException {
		
		
		ResponseProperty r = new ResponseProperty();
		try {

			Property p = propertyDAO.findPropertyById(propertyId);
			propertyDAO.deleteProperty(p);

			r.setSuccess(true);
			r.setMessage("记录已删除!");

		} catch (Exception e) {

			r.setSuccess(false);
			r.setMessage("记录删除失败:" + e.getMessage());
			System.out.println(e);

		}
		// return r;
		return Response.ok(r).build();

		
	}

	public Response getProperties(int propertyType) {
		
		
		ResponseProperty r = new ResponseProperty();
		try {

			List<Property> l = propertyDAO.getProperties(propertyType);
			//if (l.size()==1){
			//	l.add(new Property());
			//}
			r.setSuccess(true);
			r.setMessage("记录已读取!");
			r.setProperties(l);

		} catch (Exception e) {

			r.setSuccess(false);
			r.setMessage("记录读取失败:" + e.getMessage());
			System.out.println(e);

		}
		// return r;
		return Response.ok(r).build();
		
	}

	public Response updateProperty(Property p) throws JSONException {
		
		ResponseProperty r = new ResponseProperty();
		try {

			propertyDAO.modifyProperty(p);
			r.setSuccess(true);
			r.setMessage("记录已更新!");
			List l = new ArrayList();
			l.add(p);
			r.setProperties(l);

		} catch (Exception e) {

			r.setSuccess(false);
			r.setMessage("记录更新失败:" + e.getMessage());
			System.out.println(e);

		}
		// return r;
		return Response.ok(r).build();

		
		
	}

	public IPropertyDAO getPropertyDAO() {
		return propertyDAO;
	}

	public void setPropertyDAO(IPropertyDAO propertyDAO) {
		this.propertyDAO = propertyDAO;
	}

}
