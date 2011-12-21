package com.itg.strategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import com.itg.dao.FilterItem;
import com.itg.dao.FilterItemOpt;
import com.itg.dao.HistoryResult;
import com.itg.dao.IMatchDAO;
import com.itg.dao.IMatchMouthDAO;
import com.itg.dao.IPropertyDAO;
import com.itg.dao.Match;
import com.itg.dao.MatchMouth;
import com.itg.dao.OddPredict;
import com.itg.dao.Property;

public class ScriptStrategy2 extends BaseStrategy {

	// private int company1;
	// private int company2;
	//
	// private Double euro_final_win1;
	// private Double euro_final_win2;
	//
	// private Double euro_final_standoff1;
	// private Double euro_final_standoff2;
	//
	// private Double euro_final_loss1;
	// private Double euro_final_loss2;

	private IMatchDAO matchDAO;
	private IPropertyDAO propertyDAO;
	private IMatchMouthDAO matchMouthDAO;

	public IMatchMouthDAO getMatchMouthDAO() {
		return matchMouthDAO;
	}

	public void setMatchMouthDAO(IMatchMouthDAO matchMouthDAO) {
		this.matchMouthDAO = matchMouthDAO;
	}

	private Pattern resultPattern = Pattern
			.compile("(.*)result:\\s?(\\d+)\\s*ttl:\\s*(\\d*\\.\\d*)\\s*record:\\s*(\\d*\\.\\d*)\\s*confident:\\s?(\\d+)%");

	private List<ScriptEngine> engines = new ArrayList<ScriptEngine>();
	private List<ScriptEngine> combEngines = new ArrayList<ScriptEngine>();

	public ScriptStrategy2() throws FileNotFoundException, ScriptException {
		super();

		FileReader fr;

		/*
		 * 0031 威廉 0032 韦德 0033 立博 0036 澳门 0037 InterW 0038 易胜 0043 BET365
		 */

		// fr = new FileReader("31.js");
		// CompiledScript script = compEngine.compile(fr);
		// script.eval();
		// engines.add(engine1);
		//
		// compEngine = (Compilable) engine2;
		// fr = new FileReader("32.js");
		// script = compEngine.compile(fr);
		// script.eval();
		// engines.add(engine2);
		//
		// compEngine = (Compilable) engine3;
		// fr = new FileReader("33.js");
		// script = compEngine.compile(fr);
		// script.eval();
		// engines.add(engine3);
		//
		//
		// compEngine = (Compilable) engine4;
		// fr = new FileReader("36.js");
		// script = compEngine.compile(fr);
		// script.eval();
		// engines.add(engine4);
		//
		//
		// compEngine = (Compilable) engine5;
		// fr = new FileReader("37.js");
		// script = compEngine.compile(fr);
		// script.eval();
		// engines.add(engine5);
		//
		//
		// compEngine = (Compilable) engine6;
		// fr = new FileReader("38.js");
		// script = compEngine.compile(fr);
		// script.eval();
		// engines.add(engine6);
		//
		// compEngine = (Compilable) engine7;
		// fr = new FileReader("43.js");
		// script = compEngine.compile(fr);
		// script.eval();
		// engines.add(engine7);

		File dir = new File(".");
		FilenameFilter filter2 = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.startsWith("s31") && name.contains(".js");

			}
		};

		String[] children = dir.list(filter2);
		for (String jsFileName : children) {

			ScriptEngine engine = new ScriptEngineManager()
					.getEngineByName("javascript");
			Compilable compEngine = (Compilable) engine;
			CompiledScript script;
			fr = new FileReader(jsFileName);
			script = compEngine.compile(fr);
			script.eval();
			engines.add(engine);

		}

		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.startsWith("c31") && name.contains(".js");

			}
		};

		children = dir.list(filter);
		for (String jsFileName : children) {

			ScriptEngine engine = new ScriptEngineManager()
					.getEngineByName("javascript");
			Compilable compEngine = (Compilable) engine;
			CompiledScript script;
			fr = new FileReader(jsFileName);
			script = compEngine.compile(fr);
			script.eval();
			combEngines.add(engine);

		}

	}

	@Override
	public List<OddPredict> predict2(Match m, MatchMouth mm,
			List<HistoryResult> hl) {

		List<OddPredict> result = new ArrayList<OddPredict>();

		//Long after8Min = m.getDate().getTime();
		//after8Min = after8Min - (8 * 60 * 60 * 1000) - 1000;
		//after8Min = new Date().getTime() + (8 * 60 * 60 * 1000);

		MatchMouth itw = matchMouthDAO.findMatchMouthByMatchId(m.getId2(), 37);
		// if (itw == null) {
		// itw = new MatchMouth();
		// }

		//if ((new Date(after8Min)).before(new Date())) {
		//if (m.getDate().before(new Date(after8Min))) 
		{

			for (ScriptEngine engine : engines) {
				try {
					Invocable invoke = (Invocable) engine;
					// function
					// check(ASIALEVELCHANGE,ASIALEVELCHANGEVOL,ASIAODDCHANGE,ASIAODDCHANGEVOL,ASIA_EARLY_DOWN,ASIA_EARLY_UD_MOUTH,ASIA_EARLY_UP,ASIA_FINAL_DOWN,
					// ASIA_FINAL_UD_MOUTH,ASIA_FINAL_UP,CHAMPIONSHIP,EURO_EARLY_LOSS,EURO_EARLY_STANDOFF,EURO_EARLY_WIN,EURO_FINAL_LOSS,
					// EURO_FINAL_STANDOFF,EURO_FINAL_WIN,UP_DOWN,
					//function check(ASIALEVELCHANGE,ASIALEVELCHANGEVOL,ASIAODDCHANGE,ASIAODDCHANGEVOL,ASIA_EARLY_DOWN,ASIA_EARLY_UD_MOUTH,ASIA_EARLY_UP,
					//		ASIA_FINAL_DOWN,ASIA_FINAL_UD_MOUTH,ASIA_FINAL_UP,CHAMPIONSHIP,COUNTRY,EURO_EARLY_LOSS,EURO_EARLY_STANDOFF,EURO_EARLY_WIN,
					//		EURO_FINAL_LOSS,EURO_FINAL_STANDOFF,EURO_FINAL_WIN,UP_DOWN, COMPANY){
					String remark = (String) invoke.invokeFunction("check",
							mm.getAsiaLevelChange(),
							mm.getAsiaLevelChangeVol(), 
							mm.getAsiaOddChange(),
							mm.getAsiaOddChangeVol(), 
							mm.getAsia_early_down(),
							mm.getAsia_early_ud_mouth(), mm.getAsia_early_up(),
							mm.getAsia_final_down(),
							mm.getAsia_final_ud_mouth(), mm.getAsia_final_up(),
							m.getChampionship(),m.getCountry(), mm.getEuro_early_loss(),
							mm.getEuro_early_standoff(),
							mm.getEuro_early_win(), mm.getEuro_final_loss(),
							mm.getEuro_final_standoff(),
							mm.getEuro_final_win(), mm.getUp_down(),
							mm.getCompany()

					);

					if (remark != null) {
						Matcher matcher = resultPattern.matcher(remark);
						if (matcher.find()) {
							OddPredict op = null;
							if (matcher.group(2).equals("01")) {
								op = createOddPredict(m, mm, null, "亚赔上");
							}

							if (matcher.group(2).equals("03")) {
								op = createOddPredict(m, mm, null, "亚赔下");
							}
							if (op != null) {
								op.setMatchCount(Math.round(Float
										.parseFloat(matcher.group(3))));
								op.setPossibility(Float.parseFloat(matcher
										.group(5)));

								op.setRemark(new String(matcher.group(1)
										.getBytes("UTF-8")));
								// op.setRemark(matcher.group(1));

								result.add(op);

							}

						}

					}

				} catch (ScriptException scrEx) {
					scrEx.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (itw != null) {
				for (ScriptEngine engine : combEngines) {
					try {
						Invocable invoke = (Invocable) engine;
						
						//function check(ASIALEVELCHANGE,ASIALEVELCHANGEVOL,ASIAODDCHANGE,ASIAODDCHANGEVOL,ASIA_EARLY_DOWN,ASIA_EARLY_UD_MOUTH,
						//		ASIA_EARLY_UP,ASIA_FINAL_DOWN,ASIA_FINAL_UD_MOUTH,ASIA_FINAL_UP,CHAMPIONSHIP,COUNTRY,EURO_EARLY_LOSS,
						//		EURO_EARLY_LOSS2,EURO_EARLY_STANDOFF,EURO_EARLY_STANDOFF2,EURO_EARLY_WIN,EURO_EARLY_WIN2,EURO_FINAL_LOSS,
						//		EURO_FINAL_LOSS2,EURO_FINAL_STANDOFF,EURO_FINAL_STANDOFF2,EURO_FINAL_WIN,EURO_FINAL_WIN2,UP_DOWN, COMPANY)
						
						String remark = (String) invoke.invokeFunction("check",
								mm.getAsiaLevelChange(),
								mm.getAsiaLevelChangeVol(),
								mm.getAsiaOddChange(),
								mm.getAsiaOddChangeVol(),
								
								mm.getAsia_early_down(),
								mm.getAsia_early_ud_mouth(),
								mm.getAsia_early_up(),
								mm.getAsia_final_down(),
								mm.getAsia_final_ud_mouth(),
								mm.getAsia_final_up(),
							    m.getChampionship(),
							    m.getCountry(),
							    
								mm.getEuro_early_loss(),
								itw.getEuro_early_loss(),
								mm.getEuro_early_standoff(),
								itw.getEuro_early_standoff(),
								mm.getEuro_early_win(),
								itw.getEuro_early_win(),
								mm.getEuro_final_loss(),
								itw.getEuro_final_loss(),
								mm.getEuro_final_standoff(),
								itw.getEuro_final_standoff(),
								mm.getEuro_final_win(),
								itw.getEuro_final_win(), mm.getUp_down(),
								mm.getCompany()

						);

						if (remark != null) {
							Matcher matcher = resultPattern.matcher(remark);
							if (matcher.find()) {
								OddPredict op = null;
								if (matcher.group(2).equals("01")) {
									op = createOddPredict(m, mm, null, "亚赔上");
								}

								if (matcher.group(2).equals("03")) {
									op = createOddPredict(m, mm, null, "亚赔下");
								}
								if (op != null) {
									op.setMatchCount(Math.round(Float
											.parseFloat(matcher.group(3))));
									op.setPossibility(Float.parseFloat(matcher
											.group(5)));

									op.setRemark(new String(matcher.group(1)
											.getBytes("UTF-8")));
									// op.setRemark(matcher.group(1));

									result.add(op);

								}

							}

						}

					} catch (ScriptException scrEx) {
						scrEx.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}
		return result;

	}

	public IMatchDAO getMatchDAO() {
		return matchDAO;
	}

	public void setMatchDAO(IMatchDAO matchDAO) {
		this.matchDAO = matchDAO;
	}

	public IPropertyDAO getPropertyDAO() {
		return propertyDAO;
	}

	public void setPropertyDAO(IPropertyDAO propertyDAO) {
		this.propertyDAO = propertyDAO;
	}

}
