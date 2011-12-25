package com.itg.web.ctl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpEntity;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.itg.dao.FilterItem;
import com.itg.dao.FilterItemOpt;
import com.itg.dao.IMatchDAO;
import com.itg.dao.IMatchMouthDAO;
import com.itg.dao.IPropertyTypeDAO;
import com.itg.dao.Match;
import com.itg.dao.MatchMouth;
import com.itg.dao.PropertyType;

@Controller
public class AnalyzeController1 {

	private String viewName;
	private String analyze2View;
	private String onlyResultView;
	private IMatchDAO matchDAO;
	private IMatchMouthDAO matchMouthDAO;

	private String analyze3View;

	public IMatchMouthDAO getMatchMouthDAO() {
		return matchMouthDAO;
	}

	public void setMatchMouthDAO(IMatchMouthDAO matchMouthDAO) {
		this.matchMouthDAO = matchMouthDAO;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {

		
		SimpleDateFormat dateFormat2 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm");
		dateFormat2.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat2, false));

		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, false));


	
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/analyze1")
	public String execute(ModelMap map, String keyValue, String keyDate,
			HttpServletRequest request, HttpServletResponse response) {
		return viewName;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/analyze2")
	public String execute2(ModelMap map, String keyValue, String keyDate,
			HttpServletRequest request, HttpServletResponse response) {
		return analyze2View;
	}

	@RequestMapping("/getAnalyzeResult1")
	public String getResult1(@RequestParam("fromDate") Date fromDate,
			@RequestParam("toDate") Date toDate,
			@RequestParam("company") int company,
			@RequestParam("euro_mouth") double euro_mouth,
			@RequestParam("asia_big_small_mouth") int asia_big_small_mouth,

			ModelMap map, String keyValue, String keyDate,
			HttpServletRequest request, HttpServletResponse response) {

		List l = matchDAO.analyzeEuroMouthMix(fromDate, toDate, company,
				euro_mouth, asia_big_small_mouth);
		// List l = matchDAO.test(fromDate, toDate, company, euro_mouth,
		// asia_big_small_mouth);

		map.put("result", JSONArray.fromObject(l));
		map.put("dataRoot", "root");

		return onlyResultView;
	}

	@RequestMapping("/getAnalyzeResult2")
	public String getResult2(@RequestParam("fromDate") Date fromDate,
			@RequestParam("toDate") Date toDate,
			@RequestParam("company") int company,
			@RequestParam("euro_mouth") double euro_mouth,

			ModelMap map, String keyValue, String keyDate,
			HttpServletRequest request, HttpServletResponse response) {

		List l = matchDAO.analyzeEuroMouth(fromDate, toDate, company,
				euro_mouth);
		// List l = matchDAO.test(fromDate, toDate, company, euro_mouth,
		// asia_big_small_mouth);

		map.put("result", JSONArray.fromObject(l));
		map.put("dataRoot", "root");

		return onlyResultView;
	}

	@RequestMapping("/getAnalyzeResult3")
	public String getResult3(@RequestParam("championship") int championship,
			@RequestParam("country") int country,
			@RequestParam("company") int company,
			@RequestParam("euro_final_win") double euro_final_win,
			@RequestParam("euro_final_standoff") double euro_final_standoff,
			@RequestParam("euro_final_loss") double euro_final_loss,
			@RequestParam("asia_final_bs_mouth") int asia_final_bs_mouth,
			@RequestParam("asia_final_ud_mouth") int asia_final_ud_mouth,

			ModelMap map, String keyValue, String keyDate,
			HttpServletRequest request, HttpServletResponse response) {

		MatchMouth mm = new MatchMouth();

		mm.setCompany(company);
		mm.setEuro_final_win(euro_final_win);
		mm.setEuro_final_standoff(euro_final_standoff);
		mm.setEuro_final_loss(euro_final_loss);
		mm.setAsia_final_bs_mouth(asia_final_bs_mouth);
		mm.setAsia_final_ud_mouth(asia_final_ud_mouth);

		List l = matchMouthDAO.checkMatchMouthHistory(mm, championship, 0,
				3650);
		List l2 = matchMouthDAO.checkMatchMouthHistory(mm, 0, 0, 3650);
		
		if (l2.size() > 0) {
			l.add(l2.get(0));
		}

		List l3 = matchMouthDAO.checkMatchMouthHistory(mm, 0, country,
				3650);

		if (l3.size() > 0) {
			l.add(l3.get(0));
		}

		map.put("result", JSONArray.fromObject(l));
		map.put("dataRoot", "root");

		return onlyResultView;
	}

	@RequestMapping("/analyze4")
	public String execute4(ModelMap map, String keyValue, String keyDate,
			HttpServletRequest request, HttpServletResponse response) {

		return "matchAnalyze4";
	}

	

	
	@RequestMapping(value="/getAnalyzeResult4/{matchMouthId}", method=RequestMethod.PUT)
	public String updateMatchMouth(
			@PathVariable("matchMouthId") Integer matchMouthId,  
			ModelMap map, @RequestBody Match match,
			HttpServletRequest request, HttpServletResponse response
			) throws ParseException
			{
		
		
		
		Match m = matchDAO.findMatchById2(match.getId2());
		m.setHome_power(match.getHome_power());
		m.setAway_power(match.getAway_power());
		m.setRemark(match.getRemark());
		

		m.setChampionship(match.getChampionship());
		m.setDate(match.getDate());
		m.setAwayTeam(match.getAwayTeam());
		m.setHomeTeam(match.getHomeTeam());
		m.setHomeTeamPosition(match.getHomeTeamPosition());
		m.setAwayTeamPosition(match.getAwayTeamPosition());
		m.setIsInHome(match.getIsInHome());
		m.setScore(match.getScore());
		
		
		matchDAO.modifyMatch(m);
		
		
		MatchMouth mm = matchMouthDAO.findMatchMouthById(match.getId());
		mm.setCompany(match.getCompany());
		mm.setPan(match.getPan());
		mm.setFen(match.getFen());
		mm.setAsia_early_big(match.getAsia_early_big());
		mm.setAsia_early_bs_mouth(match.getAsia_early_bs_mouth());
		mm.setAsia_early_down(match.getAsia_early_down());
		mm.setAsia_early_small(match.getAsia_early_small());
		mm.setAsia_early_ud_mouth(match.getAsia_early_ud_mouth());
		mm.setAsia_early_up(match.getAsia_early_up());
		mm.setAsia_final_big(match.getAsia_final_big());
		mm.setAsia_final_bs_mouth(match.getAsia_final_bs_mouth());
		mm.setAsia_final_down(match.getAsia_final_down());
		mm.setAsia_final_small(match.getAsia_final_small());
		mm.setAsia_final_ud_mouth(match.getAsia_final_ud_mouth());
		mm.setAsia_final_up(match.getAsia_final_up());
		mm.setEuro_early_loss(match.getEuro_early_loss());
		mm.setEuro_early_standoff(match.getEuro_early_standoff());
		mm.setEuro_early_win(match.getEuro_early_win());
		mm.setEuro_final_loss(match.getEuro_final_loss());
		mm.setEuro_final_standoff(match.getEuro_final_standoff());
		mm.setEuro_final_win(match.getEuro_final_win());
		matchMouthDAO.setResult(mm, m.getScore());
		matchMouthDAO.setMatchMouthInterval(mm);
		
		
		matchMouthDAO.modifyMatchMouth(mm);
		
		map.put("result", new ArrayList());
		
           
		map.put("success", true);
		map.put("total", 1);
		map.put("message", "更新完成");

		return "resultOnly_JSON";
		
	}
	
	@RequestMapping(value="/getAnalyzeResult4", method=RequestMethod.GET)
	public String getResult4(@RequestParam("start") int start,
			@RequestParam("limit") int limit,
			@RequestParam("sort") String sort, @RequestParam("dir") String dir,
			ModelMap map, String keyValue, String keyDate,
			HttpServletRequest request, HttpServletResponse response)
			throws NumberFormatException, ParseException {

		String paramName = "company";
		int i = 1;
		List<Match> l = null;

		String[] idList = null;

		String[] companyList = new String[4];
		if (request.getParameter(paramName.concat(Integer.toString(i))) != null) {
			while (i <= 3
					&& (!request.getParameter(
							paramName.concat(Integer.toString(i))).equals(""))) {
				l = matchDAO.getMatchMouthByCondition(
						Integer.parseInt(request.getParameter("company" + i)), 
						Double.parseDouble(request.getParameter("euroWinFrom" + i)),
						Double.parseDouble(request.getParameter("euroWinTo" + i)), 
						Double.parseDouble(request.getParameter("euroStandFrom" + i)),
						Double.parseDouble(request.getParameter("euroStandTo"+ i)), 
						Double.parseDouble(request.getParameter("euroLossFrom" + i)), 
						Double.parseDouble(request.getParameter("euroLossTo"+ i)),
						// Integer.parseInt(request.getParameter("asiaMouth"+i)),
						idList);

				companyList[i - 1] = request.getParameter("company" + i);

				idList = new String[l.size()];
				int j = 0;
				for (Match m : l) {
					idList[j] = String.valueOf(m.getId2());
					j++;

				}
				i++;
			}

		}
		List<FilterItem> fl = new ArrayList<FilterItem>();
		fl = FilterItemOpt.getFilterFromRequest(request);
		if (companyList.length > 0&&companyList[0]!=null) {
			fl.add(FilterItemOpt.makeFilter("list", "in", "company",
					companyList));
		}
		
		
		if (idList != null && idList.length > 0) {
			fl.add(FilterItemOpt.makeFilter("list", "in", "id2", idList));
		}
		
		if ((idList == null || idList.length == 0)&&(companyList[0]!=null)) {
			idList = new String[]{"-100"};
			fl.add(FilterItemOpt.makeFilter("list", "in", "id2", idList));
		}
		
		
		
		
		l = matchDAO.getMatchMouth(fl, start, limit, sort, dir);

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,
				new DateJsonValueProcessor(null));

		map.put("result", JSONArray.fromObject(l, jsonConfig));
		

		map.put("success", true);
		map.put("message", "");
		map.put("total", matchDAO.getMatchMouthCount(fl));

		return "resultOnly_JSON";
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public String getViewName() {
		return viewName;
	}

	public String getOnlyResultView() {
		return onlyResultView;
	}

	public void setOnlyResultView(String onlyResultView) {
		this.onlyResultView = onlyResultView;
	}

	public IMatchDAO getMatchDAO() {
		return matchDAO;
	}

	public void setMatchDAO(IMatchDAO matchDAO) {
		this.matchDAO = matchDAO;
	}

	public String getAnalyze2View() {
		return analyze2View;
	}

	public void setAnalyze2View(String analyze2View) {
		this.analyze2View = analyze2View;
	}

	public String getAnalyze3View() {
		return analyze3View;
	}

	public void setAnalyze3View(String analyze3View) {
		this.analyze3View = analyze3View;
	}

}
