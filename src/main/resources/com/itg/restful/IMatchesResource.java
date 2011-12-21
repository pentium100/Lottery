package com.itg.restful;

import java.util.Date;

import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;

import com.itg.dao.Match;
import com.itg.dao.Property;


@Path("/")
@Produces("application/json")
@Consumes("application/json")
public interface IMatchesResource {
	
	@GET
	@Path("/")
	public abstract Response getMatches(
			@Context HttpServletRequest request, 
			@QueryParam("start") int start, @QueryParam("limit") int limit,
			@QueryParam("sort") String sort, @QueryParam("dir") String dir
			);

	@POST
	public abstract Response addMatch(Match m)
			throws JSONException;

	@DELETE
	@Path("/{matchId}")
	@Consumes("application/x-www-form-urlencoded")
	public abstract Response deleteMatch(
			@PathParam(value = "matchId") Integer matchId)
			throws JSONException;

	@PUT
	@Path("/{matchId}")
	public abstract Response updateMatch(Match m)
			throws JSONException;


}
