package com.itg.restful;

import java.util.Date;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
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

import com.itg.dao.ReportMemo;


@Path("/{keyValue}")
@WebService
@Produces("application/json")
@Consumes("application/json")
public interface IReportMemosResource {

	@GET
	public abstract Response getReportMemos(
			@PathParam("keyValue") String reportKey,
			@QueryParam("start") int start, @QueryParam("limit") int limit);

	@POST
	public abstract Response addReportMemo(ReportMemo reportMemo)
			throws JSONException;

	@DELETE
	@Path("/{reportMemoId}")
	@Consumes("application/x-www-form-urlencoded")
	public abstract String deleteReportMemo(
			@PathParam(value = "reportMemoId") Integer reportMemoId)
			throws JSONException;

	@PUT
	@Path("/{reportMemoId}")
	public abstract String updateReportMemo(ReportMemo reportMemo)
			throws JSONException;
	
	@WebResult(name="lastReportMemo")
	public ReportMemo getLastReportMemo(@WebParam(name="keyDate") Date keyDate, @WebParam(name="keyValue")String keyValue);



}