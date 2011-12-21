package com.itg.restful;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;

import com.itg.dao.FilterItem;
import com.itg.dao.FilterItemOpt;
import com.itg.dao.IMatchDAO;
import com.itg.dao.IMatchMouthDAO;
import com.itg.dao.Match;
import com.itg.dao.MatchMouth;
import com.itg.dao.Property;
import com.itg.dao.ResponseMatch;
import com.itg.dao.ResponseProperty;

public class MatchesResource implements IMatchesResource {
	
	private IMatchDAO matchDAO;
	private IMatchMouthDAO matchMouthDAO;

	public IMatchMouthDAO getMatchMouthDAO() {
		return matchMouthDAO;
	}

	public void setMatchMouthDAO(IMatchMouthDAO matchMouthDAO) {
		this.matchMouthDAO = matchMouthDAO;
	}

	public Response addMatch(Match m) throws JSONException {
		
		ResponseMatch r = new ResponseMatch();
		try {

			//matchDAO.insertMatch(m);
			
			//r.setSuccess(true);
			//r.setMessage("记录已新建!");
			
			r.setSuccess(false);
			r.setMessage("记录新建功能已经关闭!");
			
			List l = new ArrayList();
			l.add(m);
			r.setMatches(l);

		} catch (Exception e) {

			r.setSuccess(false);
			r.setMessage("记录创建失败:" + e.getMessage());
			List l = new ArrayList();
			l.add(m);
			r.setMatches(l);
			System.out.println(e);

		}
		// return r;
		return Response.ok(r).build();
		

		
	}

	public Response deleteMatch(Integer matchId) throws JSONException {
		ResponseMatch r = new ResponseMatch();
		try {

			
			MatchMouth mm = matchMouthDAO.findMatchMouthById(matchId);
			
			matchMouthDAO.deleteMatchMouth(mm);
			
			Match m = matchDAO.findMatchById(mm.getMatchId());
			Long recCount = matchMouthDAO.getOddCounts(m.getId2());
			
			if(recCount==0){
				matchDAO.deleteMatch(m);
			}
			

			r.setSuccess(true);
			r.setMessage("记录已删除!");

		} catch (Exception e) {

			r.setSuccess(false);
			r.setMessage("记录删除失败:" + e.getMessage());
			e.printStackTrace();
			//System.out.println(e);

		}
		// return r;
		return Response.ok(r).build();

	}

	public Response getMatches(HttpServletRequest request, int start , int limit, String sort, String dir) {
		
		ResponseMatch r = new ResponseMatch();
		try {
			
			List<FilterItem> fl = FilterItemOpt.getFilterFromRequest(request);
			

			List<Match> l = matchDAO.getMatchMouth(fl, start, limit, sort, dir);
   
			r.setSuccess(true);
			r.setMessage("记录已读取!");
			r.setMatches(l);
			r.setTotal((matchDAO.getMatchMouthCount(fl)));

		} catch (Exception e) {

			r.setSuccess(false);
			r.setMessage("记录读取失败:" + e.getMessage());
			System.out.println(e);

		}
		// return r;
		return Response.ok(r).build();


	}

	public Response updateMatch(Match m) throws JSONException {
		
		ResponseMatch r = new ResponseMatch();
		try {

			
			MatchMouth mm = matchMouthDAO.findMatchMouthById(m.getId());
			mm.setCompany(m.getCompany());
			mm.setEuro_final_win(m.getEuro_final_win());
			mm.setEuro_final_standoff(m.getEuro_final_standoff());
			mm.setEuro_final_loss(m.getEuro_final_loss());
			
			mm.setEuro_early_win(m.getEuro_early_win());
			mm.setEuro_early_standoff(m.getEuro_early_standoff());
			mm.setEuro_early_loss(m.getEuro_early_loss());

			mm.setAsia_final_up(m.getAsia_final_up());
			mm.setAsia_final_ud_mouth(m.getAsia_final_ud_mouth());
			mm.setAsia_final_down(m.getAsia_final_down());
			
			mm.setAsia_early_up(m.getAsia_early_up());
			mm.setAsia_early_ud_mouth(m.getAsia_early_ud_mouth());
			mm.setAsia_early_down(m.getAsia_early_down());

			mm.setAsia_final_big(m.getAsia_final_big());
			mm.setAsia_final_bs_mouth(m.getAsia_final_bs_mouth());
			mm.setAsia_final_small(m.getAsia_final_small());
			
			mm.setAsia_early_big(m.getAsia_early_big());
			mm.setAsia_early_bs_mouth(m.getAsia_early_bs_mouth());
			mm.setAsia_early_small(m.getAsia_early_small());
			
			matchMouthDAO.modifyMatchMouth(mm);
			Match m2 = matchDAO.findMatchById2(mm.getMatchId());
			
			m2.setDate(m.getDate());
			m2.setChampionship(m.getChampionship());
			m2.setScore(m.getScore());
			m2.setHomeTeam(m.getHomeTeam());
			m2.setAwayTeam(m.getAwayTeam());
			m2.setHomeTeamPosition(m.getHomeTeamPosition());
			m2.setAwayTeamPosition(m.getAwayTeamPosition());
			
			matchDAO.modifyMatch(m2);
			m2.setId(mm.getMatchMouthId());
			r.setSuccess(true);
			r.setMessage("记录已更新!");
			List l = new ArrayList();
			l.add(m2);
			r.setMatches(l);

		} catch (Exception e) {

			r.setSuccess(false);
			r.setMessage("记录更新失败:" + e.getMessage());
			System.out.println(e);

		}
		// return r;
		return Response.ok(r).build();

		
	}

	public IMatchDAO getMatchDAO() {
		return matchDAO;
	}

	public void setMatchDAO(IMatchDAO matchDAO) {
		this.matchDAO = matchDAO;
	}


}
