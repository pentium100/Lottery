package com.itg.restful;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;

import com.itg.dao.Property;


@Path("/{propertyType}")
@Produces("application/json")
@Consumes("application/json")
public interface IPropertiesResource {
	
	@GET
	public abstract Response getProperties(
			@PathParam("propertyType") int propertyType
			);

	@POST
	public abstract Response addProperty(Property p)
			throws JSONException;

	@DELETE
	@Path("/{propertyId}")
	@Consumes("application/x-www-form-urlencoded")
	public abstract Response deleteReportMemo(
			@PathParam(value = "propertyId") Integer propertyId)
			throws JSONException;

	@PUT
	@Path("/{propertyId}")
	public abstract Response updateProperty(Property p)
			throws JSONException;


}
