package com.itg.bot;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.itg.dao.HistoryResult;

import com.itg.dao.IHistoryResultDAO;
import com.itg.dao.IMatchDAO;
import com.itg.dao.IMatchMouthDAO;
import com.itg.dao.IOddPredictDAO;
import com.itg.dao.IPropertyDAO;
import com.itg.dao.IWarningFilterDAO;
import com.itg.dao.Match;
import com.itg.dao.MatchMouth;
import com.itg.dao.OddPredict;
import com.itg.dao.Property;
import com.itg.dao.WarningFilter;
import com.itg.strategy.IStrategy;

public class LotteryBot implements Callable<String> {

	private final static String splitDomain = "\\$";
	private final static String splitRecord = "\\;";
	private final static String splitColumn = "\\,";

	// private final static String oddUrl =
	// "http://61.143.225.74/xml/odds.aspx?companyID=,4,14,9,8,19,1,12,33,";
	private final static String oddUrl = "http://61.143.225.173:88/xml/odds.aspx?companyID=,4,14,9,8,19,1,12,33,";
	private final static String resultUrl = "http://1x2.bet007.com/bet007history.aspx";
	private final static String infoUrl = "http://info.bet007.com/info/index.htm";
	private ExecutorService es = Executors.newFixedThreadPool(10);
	private String[] GoalCn = { "0", "0/0.5", "0.5", "0.5/1", "1", "1/1.5",
			"1.5", "1.5/2", "2", "2/2.5", "2.5", "2.5/3", "3", "3/3.5", "3.5",
			"3.5/4", "4", "4/4.5", "4.5", "4.5/5", "5", "5/5.5", "5.5",
			"5.5/6", "6", "6/6.5", "6.5", "6.5/7", "7", "7/7.5", "7.5",
			"7.5/8", "8", "8/8.5", "8.5", "8.5/9", "9", "9/9.5", "9.5",
			"9.5/10", "10", "10/10.5", "10.5", "10.5/11", "11", "11/11.5",
			"11.5", "11.5/12", "12", "12/12.5", "12.5", "12.5/13", "13",
			"13/13.5", "13.5", "13.5/14", "14" };
	private String[] GoalCn2 = { "0", "0/-0.5", "-0.5", "-0.5/-1", "-1",
			"-1/-1.5", "-1.5", "-1.5/-2", "-2", "-2/-2.5", "-2.5", "-2.5/-3",
			"-3", "-3/-3.5", "-3.5", "-3.5/-4", "-4", "-4/-4.5", "-4.5",
			"-4.5/-5", "-5", "-5/-5.5", "-5.5", "-5.5/-6", "-6", "-6/-6.5",
			"-6.5", "-6.5/-7", "-7", "-7/-7.5", "-7.5", "-7.5/-8", "-8",
			"-8/-8.5", "-8.5", "-8.5/-9", "-9", "-9/-9.5", "-9.5", "-9.5/-10",
			"-10" };

	private String proxy_address;
	private int proxy_port;

	private String smtp = "mail.itg.com.cn";

	private Float euroWarningPct = new Float(150);
	private Float asiaWarningPct = new Float(70);
	private int euroWarningCount = 30;

	private int asiaBigWarningCount = 10;
	private int asiaUpWarningCount = 10;
	private int asiaSmallWarningCount = 10;
	private int asiaDownWarningCount = 10;
	private int warningBeforeMatchTime = 40;
	private int summaryHistoryDays = 30;

	private NamedParameterJdbcTemplate jdbcTemplate;

	public NamedParameterJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public String getSmtp() {
		return smtp;
	}

	public void setSmtp(String smtp) {
		this.smtp = smtp;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getCopyto() {
		return copyto;
	}

	public void setCopyto(String copyto) {
		this.copyto = copyto;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private String from;
	private String to;
	private String copyto;
	private String subject;
	private String username;
	private String password;

	private IPropertyDAO propertyDAO;
	private IMatchDAO matchDAO;
	private IMatchMouthDAO matchMouthDAO;
	private IHistoryResultDAO historyResultDAO;
	private IWarningFilterDAO warningFilterDAO;
	private IOddPredictDAO oddPredictDAO;

	private int sleepTime;
	private static Log logger = LogFactory.getLog(LotteryBot.class);

	private List<IStrategy> strategyList;
	private Map<String, IStrategy> strategyMap;

	public IHistoryResultDAO getHistoryResultDAO() {
		return historyResultDAO;
	}

	public void setHistoryResultDAO(IHistoryResultDAO historyResultDAO) {
		this.historyResultDAO = historyResultDAO;
	}

	public IWarningFilterDAO getWarningFilterDAO() {
		return warningFilterDAO;
	}

	public void setWarningFilterDAO(IWarningFilterDAO warningFilterDAO) {
		this.warningFilterDAO = warningFilterDAO;
	}

	private List<Map<MatchMouth, Date>> promptList = new ArrayList<Map<MatchMouth, Date>>();

	private String goal2GoalCn(Double goal) {

		int iGoal = (int) (goal * 4);
		if (goal >= 0) {
			if (iGoal >= GoalCn.length) {
				logger.error("error asia big/small mouth:" + goal);
				return "0";
			}
			return GoalCn[(iGoal)];
		} else
			return GoalCn2[Math.abs((iGoal))];

	}

	private String inputStream2String(InputStream in) throws IOException {
		StringBuffer out = new StringBuffer();

		BufferedReader reader = new BufferedReader(new InputStreamReader(in));

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				out.append(line + "\n");
			}
		} catch (IOException e) {
			logger.error(e);
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				logger.error(e);
			}
		}

		return out.toString();
	}

	private String getOddContent() throws ClientProtocolException, IOException {

		HttpClient httpclient = new DefaultHttpClient();
		if (!proxy_address.equals("")) {
			final HttpHost proxy = new HttpHost(proxy_address, proxy_port,
					"http");
			httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
					proxy);
		}

		HttpGet httpget = new HttpGet(oddUrl);
		httpget.setHeader(
				"User-Agent",
				"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1) ; .NET CLR 2.0.50727; .NET CLR 3.0.04506.648; .NET CLR 3.5.21022; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		String s = null;

		s = EntityUtils.toString(entity, "UTF-8");
		return s;
	}

	private List<NameValuePair> makeFormParams(Date matchDate, String viewState) {

		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		if (!viewState.equals("")) {
			formparams.add(new BasicNameValuePair("__VIEWSTATE", viewState));
		}

		for (int i = 0; i < 400; i++) {
			formparams.add(new BasicNameValuePair("checkbox", (Integer
					.valueOf(i)).toString()));
		}

		formparams.add(new BasicNameValuePair("key", ""));
		formparams.add(new BasicNameValuePair("matchdate",
				new SimpleDateFormat("yyyy-MM-dd").format(matchDate)));

		return formparams;

	}

	private List<String> getResultContent() throws ClientProtocolException,
			IOException, URISyntaxException {

		HttpClient httpclient = new DefaultHttpClient();
		if (!proxy_address.equals("")) {
			final HttpHost proxy = new HttpHost(proxy_address, proxy_port,
					"http");
			httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
					proxy);
		}

		HttpPost httpPost = new HttpPost(resultUrl);

		List<NameValuePair> formparams;
		UrlEncodedFormEntity entity;
		HttpResponse response;
		HttpEntity responseEntity;
		List<String> s = new ArrayList<String>();
		String viewState = "";
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		for (int i = 0; i < 3; i++) {
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, -i);
			Date matchDate = c.getTime();
			// URI uri = new
			// URI(resultUrl.concat("?matchdate=").concat(sdf.format(matchDate)));
			httpPost.setURI(new URI(resultUrl));
			formparams = makeFormParams(matchDate, viewState);
			entity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httpPost.setEntity(entity);
			httpPost.setHeader(
					"User-Agent",
					"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1) ; .NET CLR 2.0.50727; .NET CLR 3.0.04506.648; .NET CLR 3.5.21022; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
			response = httpclient.execute(httpPost);
			responseEntity = response.getEntity();
			String r = EntityUtils.toString(responseEntity);
			Pattern p = Pattern
					.compile("<input type=\"hidden\" name=\"__VIEWSTATE\" value=\"(.*)\" />");

			Matcher m = p.matcher(r);
			if (m.find()) {
				viewState = m.group(1);
			}

			if (responseEntity != null) {
				s.add(r);
			}

		}

		return s;
	}

	private String getInfo() throws ClientProtocolException, IOException {

		HttpClient httpclient = new DefaultHttpClient();
		if (!proxy_address.equals("")) {
			final HttpHost proxy = new HttpHost(proxy_address, proxy_port,
					"http");
			httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
					proxy);
		}

		HttpGet httpGet = new HttpGet(infoUrl);

		HttpResponse response;
		HttpEntity responseEntity;

		response = httpclient.execute(httpGet);
		responseEntity = response.getEntity();
		String r = EntityUtils.toString(responseEntity, "UTF-8");

		return r;
	}

	private void analyzeChampionshipInfo(String info) {

		Pattern p = Pattern
				.compile(
						"<a class=\"n_outer\" href=\"javascript:\" onmouseover=\"zq_\\d{1,6}.style.display='';\" onmouseout=\"zq_\\d{1,6}.style.display='none';\"><span>&nbsp;(\\S*)\\s.+?</td></tr></table></span></a></div></div></td></tr></table></div>",
						Pattern.DOTALL);

		Matcher m = p.matcher(info);
		while (m.find()) {

			// Country c = countryDAO.findByName();
			Property c = propertyDAO.findPropertyByText(m.group(1), 6);
			if (c == null) {
				c = new Property();
				c.setText(m.group(1));
				c.setPropertyType_id(6);
				propertyDAO.insertProperty(c);
			}

			String champs = m.group(0);
			p = Pattern
					.compile(
							"<a class='n_inner n_second' href=\"javascript:\"><span  onmouseover=\"CheckPosition\\('tab1_(\\d{1,6})_\\d{1,6}'\\)\">(\\S*)\\s",
							Pattern.DOTALL);

			Matcher m1 = p.matcher(champs);

			while (m1.find()) {

				Property p2 = propertyDAO.findPropertyById2(
						Integer.parseInt(m1.group(1)), 1);
				if (p2 == null) {
					p2 = new Property();
					p2.setText(m1.group(2));
					p2.setPropertyType_id(1);
					p2.setId2(Integer.parseInt(m1.group(1)));
				}

				p2.setCountry(c);
				propertyDAO.insertPropertyIfNoExists(p2);
			}

		}

	}

	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "applicationContext.xml", "Lottery-dao.xml",
						"Lottery-bot.xml" });

		// an ApplicationContext is also a BeanFactory (via inheritance)
		// BeanFactory factory = context;

		LotteryBot lbot = (LotteryBot) context.getBean("lotteryBot");

		LotteryThreadFactory threadFactory = new LotteryThreadFactory();

		// ScheduledExecutorService executor =
		// Executors.newScheduledThreadPool(2);

		while (true) {

			// executor.invokeAll(Arrays.asList(lbot), 8, TimeUnit.MINUTES);
			// Future<String> f = executor.invokeAny(tasks, timeout, unit)
			// executor.shutdown();
			// Future<String> f = executor.submit(lbot);

			try {
				// f.get(5, TimeUnit.SECONDS);
				ExecutorService executor = Executors
						.newSingleThreadExecutor(threadFactory);
				List<Future<String>> f = executor.invokeAll(Arrays.asList(lbot), 8, TimeUnit.MINUTES);
				
				f.get(0).get();

				executor.shutdown();
				executor.shutdownNow();
				if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
					threadFactory.getThread().stop();
					logger.error("stop thread error!");
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				threadFactory.getThread().stop();
				logger.error("stop thread error!");
			}

			// executor.shutdown();

		}

		// private void sendWarningFilterMail(List<MatchMouth> mms) {
		// StringBuffer c = new StringBuffer();
		// for (MatchMouth mm : mms) {
		// List<WarningFilter> l = warningFilterDAO.findWarningFilter(mm);
		// if (l.size() > 0) {
		// c.append(getMatchMouthHTML(mm));
		// }
		// }
		// if (!c.toString().equals("")) {
		// StringBuffer content = getMatchMouthHTMLHeader("盘口提醒-符合查询条件");
		// content.append(c).append("</table>");
		// SendMail.sendAndCc(smtp, from, to, copyto, "盘口提醒-符合查询条件", content
		// .toString(), username, password);
		// }
		//
		// }
	}

	private StringBuffer getMatchMouthHTMLHeader(String subject) {

		StringBuffer content = new StringBuffer();

		content.append("<p>" + subject + "</p>");
		content.append("<table border=\"1\" cellpadding=\"1\" cellspacing=\"0\" width=\"600px\" style=\"width: 1200px;font-size: 11px\">");
		content.append("	<tbody >");
		content.append("		<tr background=\"\" lang=\"\">");
		content.append("			<td style=\"text-align: center\">");
		content.append("				时间</td>");
		content.append("			<td style=\"text-align: center\">");
		content.append("				赛事</td>");
		content.append("			<td style=\"text-align: center\">");
		content.append("				主队</td>");
		content.append("			<td style=\"text-align: center\">");
		content.append("				客队</td>");
		content.append("			<td style=\"text-align: center\">");
		content.append("				公司</td>");
		content.append("			<td style=\"text-align: center\">");
		content.append("				欧赔胜</td>");
		content.append("			<td style=\"text-align: center\">");
		content.append("				欧赔平</td>");
		content.append("			<td style=\"text-align: center\">");
		content.append("				欧赔负</td>");
		content.append("			<td style=\"text-align: center\">");
		content.append("				亚赔上</td>");
		content.append("			<td style=\"text-align: center\">");
		content.append("				亚赔盘</td>");
		content.append("			<td style=\"text-align: center\">");
		content.append("				亚赔下</td>");
		content.append("			<td style=\"text-align: center\">");
		content.append("				大小大</td>");
		content.append("			<td style=\"text-align: center\">");
		content.append("				大小盘</td>");
		content.append("			<td style=\"text-align: center\">");
		content.append("				大小小</td>");
		content.append("		</tr>");

		return content;

	}

	private StringBuffer getMatchMouthHTML(MatchMouth mm) {

		StringBuffer content = new StringBuffer();

		String prompt = "";
		prompt += "<td>%1$tY-%1$tm-%1$te %1$tH:%1$tM</td><td>%2$s</td><td>(%3$s)</td><td>(%4$s)</td><td>(%5$s)</td>"
				+ "<td><font color="
				+ mm.isChangedField("euro_final_win")
				+ ">%6$6.3f</font></td><td><font color="
				+ mm.isChangedField("euro_final_standoff")
				+ ">%7$6.3f</font></td><td><font color="
				+ mm.isChangedField("euro_final_loss")
				+ ">%8$6.3f</td>\n\r"
				+ "<td>%9$6.3f</td><td><font color="
				+ mm.isChangedField("asia_final_ud_mouth")
				+ ">%10$s</font></td><td>%11$6.3f</td>\n\r"
				+ "<td>%12$6.3f</td><td><font color="
				+ mm.isChangedField("asia_final_bs_mouth")
				+ ">%13$s</font></td><td>%14$6.3f</td>\n\r";
		prompt = "<tr>" + prompt + "</tr>";

		Property p;

		Match m = matchDAO.findMatchById2(mm.getMatchId());
		p = propertyDAO.findPropertyById(m.getChampionship());
		String championship;
		if (p != null) {
			championship = p.getText();
		} else {
			championship = "&nbsp;";
		}

		p = propertyDAO.findPropertyById(m.getHomeTeam());
		String homeTeam = p.getText();

		p = propertyDAO.findPropertyById(m.getAwayTeam());
		String awayTeam = p.getText();

		p = propertyDAO.findPropertyById(mm.getCompany());
		String company = p.getText();

		p = propertyDAO.findPropertyById(mm.getAsia_final_bs_mouth());
		String bsMouth;
		// (p != null) ? bsMouth = p.getText() : bsMouth = "&nbsp;";

		if (p == null) {
			bsMouth = "&nbsp;";
		} else {
			bsMouth = p.getText();

		}

		p = propertyDAO.findPropertyById(mm.getAsia_final_ud_mouth());

		String udMouth;

		if (p == null) {
			udMouth = "&nbsp;";
		} else {
			udMouth = p.getText();

		}

		content.append(String.format(prompt, m.getDate(), championship,
				homeTeam, awayTeam, company, mm.getEuro_final_win(),
				mm.getEuro_final_standoff(), mm.getEuro_final_loss(),
				mm.getAsia_final_up(), udMouth, mm.getAsia_final_down(),
				mm.getAsia_final_big(), bsMouth, mm.getAsia_final_small()));

		// content.append("</table>");

		return content;

	}

	private void sendMouthChangedWarningMail(MatchMouth mm)
			throws MessagingException {

		if (mm.isChanged()) {
			int count = 0;
			List<String> strategyNames = oddPredictDAO
					.findStrategyByMatchMouthId(mm.getMatchMouthId());

			for (String strategyName : strategyNames) {

				IStrategy s = strategyMap.get(strategyName);
				if (s == null) {
					continue;
				}
				String c = getMatchMouthHTMLHeader("盘口变化提醒")
						.append(getMatchMouthHTML(mm)).append("</table>")
						.toString();

				SendMailThread st = new SendMailThread(smtp, from,
						s.getMailTo(), s.getCcTo(), "盘口变化提醒" + "-"
								+ s.getStrategyName(), c, username, password);

				count = s.matchMouthChanged(mm);
				es.submit(st);

			}

			// for (IStrategy s : getStrategyList()) {
			// count = s.matchMouthChanged(mm);
			//
			// if (count > 0) {
			//
			// String c = getMatchMouthHTMLHeader("盘口变化提醒").append(
			// getMatchMouthHTML(mm)).append("</table>")
			// .toString();
			//
			// SendMailThread st = new SendMailThread(smtp, from, s
			// .getMailTo(), s.getCcTo(), "盘口变化提醒" + "-"
			// + s.getStrategyName(), c, username, password);
			//
			// es.submit(st);
			//
			// }
			//
			// }

			// List<HistoryResult> r = historyResultDAO
			// .findHistoryResultByMatchId(mm.getMatchId(), mm
			// .getCompany());
			// if ((r == null) || (r.size() == 0)) {
			// return;
			// }
			// removeMatchMouthIndexInList(mm);
			// for (HistoryResult h : r) {
			// historyResultDAO.deleteHistoryResult(h);
			// }

		}

	}

	private void sendPromptMail(StringBuffer mailContent)
			throws MessagingException {

		if (mailContent == null || mailContent.toString().equals("")) {
			return;
		}

		StringBuffer content = new StringBuffer();

		content.append("<table border=\"1\" cellpadding=\"1\" cellspacing=\"0\" width=\"1200px\" style=\"width: 1200px;font-size: 11px\">");
		content.append("	<tbody >");
		content.append("		<tr background=\"\" lang=\"\">");
		content.append("			<td style=\"text-align: center\">");
		content.append("				时间</td>");
		content.append("			<td style=\"text-align: center\">");
		content.append("				赛事</td>");
		content.append("			<td style=\"text-align: center\">");
		content.append("				主队</td>");
		content.append("			<td style=\"text-align: center\">");
		content.append("				客队</td>");
		content.append("			<td style=\"text-align: center\">");
		content.append("				公司</td>");
		content.append("			<td style=\"text-align: center\">");
		content.append("				比分</td>");
		content.append("			<td style=\"text-align: center\">");
		content.append("				结果</td>");
		content.append("			<td style=\"text-align: center\">");
		content.append("				总场次</td>");
		content.append("			<td style=\"text-align: center\">");
		content.append("				准确</td>");
		content.append("			<td style=\"text-align: center\">");
		content.append("				预计</td>");
		content.append("			<td style=\"text-align: center\">");
		content.append("				比例</td>");
		content.append("			<td style=\"text-align: center\">");
		content.append("				欧赔胜</td>");
		content.append("			<td style=\"text-align: center\">");
		content.append("				欧赔平</td>");
		content.append("			<td style=\"text-align: center\">");
		content.append("				欧赔负</td>");
		content.append("			<td style=\"text-align: center\">");
		content.append("				亚赔上</td>");
		content.append("			<td style=\"text-align: center\">");
		content.append("				盘口</td>");
		content.append("			<td style=\"text-align: center\">");
		content.append("				亚赔下</td>");
		content.append("			<td style=\"text-align: center\">");
		content.append("				大小大</td>");
		content.append("			<td style=\"text-align: center\">");
		content.append("				盘口</td>");
		content.append("			<td style=\"text-align: center\">");
		content.append("				大小小</td>");
		content.append("		</tr>");
		content.append(mailContent);
		content.append("</table>");

		SendMailThread st = new SendMailThread(smtp, from, to, copyto, subject,
				content.toString(), username, password);

		es.submit(st);

	}

	private void analyzeMatchResult(List<String> contents) {

		Pattern p = Pattern
				.compile("<td rowspan=\"2\"><font color=red>(\\d{1,2}-\\d{1,2})<br><font color=.*?\\x0d\\x0a\\x09\\x09<td rowspan=\"2\" class=\"gocheck\" align=\"left\"><a href='Oddslist/(\\d{5,8})\\.htm' target=\"_blank\">");

		for (int i = 0; i < contents.size(); i++) {
			Matcher m = p.matcher(contents.get(i));

			while (m.find()) {
				Match match = matchDAO.findMatchById2(Integer.parseInt(m
						.group(2)));
				if (null != match) {

					if (m.group(0).contains("腰斩") || m.group(0).contains("上")
							|| m.group(0).contains("中")
							|| m.group(0).contains("下")
							|| m.group(0).contains("推迟")
							|| m.group(0).contains("待定")
							|| m.group(0).contains("延迟")) {
						match.setScore(null);
					} else {
						match.setScore(m.group(1).replace("-", ":"));
					}

					matchDAO.modifyMatch(match);

				}

			}

		}

	}

	private void deleteUnuseMatchMouth() {

		matchMouthDAO.deleteUnuseMatchMouth();
	}

	private void analyzeAsiaBigSmallMouth(String infoStr)
			throws MessagingException {
		String[] odds = infoStr.split(splitRecord);
		for (int i = 0; i < odds.length; i++) {
			String[] odd = odds[i].split(splitColumn);
			Property p = propertyDAO.findPropertyById2(
					Integer.parseInt(odd[1]), 5);

			if (null == p) {
				// logger.error(odd[1] + "type:5");
				continue;
			}

			MatchMouth mm = matchMouthDAO.findMatchMouthByMatchId(
					Integer.parseInt(odd[0]), p.getId());
			
			//matchMouthDAO.lock(mm);
			if (mm == null) {
				mm = new MatchMouth();
				mm.setMatchId(Integer.parseInt(odd[0]));
				mm.setCompany(p.getId());
			}
			String asiaMouth = goal2GoalCn(Double.parseDouble(odd[2]));
			p = propertyDAO.findPropertyByText(asiaMouth, 4);

			if (p == null) {
				p = new Property();
				p.setPropertyType_id(4);
				p.setText(asiaMouth);
				propertyDAO.insertProperty(p);
			}

			mm.setAsia_early_bs_mouth(p.getId());
			mm.setAsia_early_big(Double.parseDouble(odd[3]));
			mm.setAsia_early_small(Double.parseDouble(odd[4]));

			asiaMouth = goal2GoalCn(Double.parseDouble(odd[5]));
			p = propertyDAO.findPropertyByText(asiaMouth, 4);

			if (p == null) {
				p = new Property();
				p.setPropertyType_id(4);
				p.setText(asiaMouth);
				propertyDAO.insertProperty(p);
			}

			mm.setAsia_final_bs_mouth(p.getId());
			mm.setAsia_final_big(Double.parseDouble(odd[6]));
			mm.setAsia_final_small(Double.parseDouble(odd[7]));

			matchMouthDAO.modifyMatchMouth(mm);
			sendMouthChangedWarningMail(mm);

		}

	}

	private int removeMatchMouthIndexInList(MatchMouth mm) {

		int result = 0;
		for (int i = 0; i < promptList.size() && result == 0; i++) {

			for (MatchMouth o : promptList.get(i).keySet()) {

				if (o.getMatchMouthId() == (mm.getMatchMouthId())) {

					promptList.remove(i);
					result = 1;
					break;
				}
			}

		}

		return result;

	}

	private int findMatchMouthIndexInList(MatchMouth mm) {

		for (int i = 0; i < promptList.size(); i++) {

			for (MatchMouth o : promptList.get(i).keySet()) {

				if (o.getMatchMouthId() == mm.getMatchMouthId()) {

					return i;
				}
			}

		}

		return -1;

	}

	private List<OddPredict> analyzeEuroWSL(String infoStr) throws Exception {
		String[] odds = infoStr.split(splitRecord);
		// List<MatchMouth> l = new ArrayList<MatchMouth>();
		List<OddPredict> result = new ArrayList<OddPredict>();

		for (int i = 0; i < odds.length; i++) {
			List<OddPredict> oddPredicts = new ArrayList<OddPredict>();
			String[] odd = odds[i].split(splitColumn);

			Property p = propertyDAO.findPropertyById2(
					Integer.parseInt(odd[1]), 5);
			if (null == p) {
				// logger.error(odd[1] + "type:5");
				continue;
			}

			MatchMouth mm = matchMouthDAO.findMatchMouthByMatchId(
					Integer.parseInt(odd[0]), p.getId());
			if (mm == null) {
				mm = new MatchMouth();
				mm.setMatchId(Integer.parseInt(odd[0]));
				mm.setCompany(p.getId());
			}
			if (mm.getOfferTime() == null) {
				mm.setOfferTime(new Date());

			}

			mm.setEuro_early_win(Double.parseDouble(odd[2]));
			mm.setEuro_early_standoff(Double.parseDouble(odd[3]));
			mm.setEuro_early_loss(Double.parseDouble(odd[4]));

			if (mm.isChangedField("euro_early_win").equals("red")
					|| mm.isChangedField("euro_early_loss").equals("red")) {
				setMatchMouthInterval(mm);
			}

			mm.setEuro_final_win(Double.parseDouble(odd[5]));
			mm.setEuro_final_standoff(Double.parseDouble(odd[6]));
			mm.setEuro_final_loss(Double.parseDouble(odd[7]));
			boolean setted = false;

			if (mm.getAsia_final_up() == null) {
				// 主(上) 平手 2.30-2.60 平半 1.90-2.30 半球 1.70-1.90 半一 1.60-1.70
				// 一球 1.45-1.60 一球球半 1.35-1.45 球半 1.25-1.35 球半二球 1.15-1.25

				if (mm.getEuro_final_win() > 2.3
						&& mm.getEuro_final_win() <= 2.6) {

					if (mm.getEuro_final_win() > mm.getEuro_final_loss()) {
						mm.setAsia_final_ud_mouth(propertyDAO
								.findPropertyByText("-0", 3).getId());

					} else {
						mm.setAsia_final_ud_mouth(propertyDAO
								.findPropertyByText("+0", 3).getId());
					}
					setted = true;

				}

				if (mm.getEuro_final_win() > 1.9
						&& mm.getEuro_final_win() <= 2.3) {
					mm.setAsia_final_ud_mouth(propertyDAO.findPropertyByText(
							"0/0.5", 3).getId());
					setted = true;
				}

				if (mm.getEuro_final_win() > 1.7
						&& mm.getEuro_final_win() <= 1.9) {
					mm.setAsia_final_ud_mouth(propertyDAO.findPropertyByText(
							"0.5", 3).getId());
					setted = true;
				}

				if (mm.getEuro_final_win() > 1.6
						&& mm.getEuro_final_win() <= 1.7) {
					mm.setAsia_final_ud_mouth(propertyDAO.findPropertyByText(
							"0.5/1", 3).getId());
					setted = true;
				}

				if (mm.getEuro_final_win() > 1.45
						&& mm.getEuro_final_win() <= 1.6) {
					mm.setAsia_final_ud_mouth(propertyDAO.findPropertyByText(
							"1", 3).getId());
					setted = true;
				}
				if (mm.getEuro_final_win() > 1.35
						&& mm.getEuro_final_win() <= 1.45) {
					mm.setAsia_final_ud_mouth(propertyDAO.findPropertyByText(
							"1/1.5", 3).getId());
					setted = true;
				}
				if (mm.getEuro_final_win() > 1.25
						&& mm.getEuro_final_win() <= 1.35) {
					mm.setAsia_final_ud_mouth(propertyDAO.findPropertyByText(
							"1.5", 3).getId());
					setted = true;
				}
				if (mm.getEuro_final_win() > 1.15
						&& mm.getEuro_final_win() <= 1.25) {
					mm.setAsia_final_ud_mouth(propertyDAO.findPropertyByText(
							"1.5/2", 3).getId());
					setted = true;
				}

				// 主(上) 平手 2.30-2.60 平半 1.90-2.30 半球 1.70-1.90 半一 1.60-1.70
				// 一球 1.45-1.60 一球球半 1.35-1.45 球半 1.25-1.35 球半二球 1.15-1.25
				if (!setted) {
					if ((mm.getEuro_final_loss() > 2.3 && mm
							.getEuro_final_loss() <= 2.6)
							&& (!(mm.getEuro_final_win() > 2.3 && mm
									.getEuro_final_win() <= 2.6))) {
						if (mm.getEuro_final_win() > mm.getEuro_final_loss()) {
							mm.setAsia_final_ud_mouth(propertyDAO
									.findPropertyByText("-0", 3).getId());
						} else {
							mm.setAsia_final_ud_mouth(propertyDAO
									.findPropertyByText("+0", 3).getId());
						}

					}

					if (mm.getEuro_final_loss() > 1.9
							&& mm.getEuro_final_loss() <= 2.3) {
						mm.setAsia_final_ud_mouth(propertyDAO
								.findPropertyByText("0/-0.5", 3).getId());
					}

					if (mm.getEuro_final_loss() > 1.7
							&& mm.getEuro_final_loss() <= 1.9) {
						mm.setAsia_final_ud_mouth(propertyDAO
								.findPropertyByText("-0.5", 3).getId());
					}
					if (mm.getEuro_final_loss() > 1.6
							&& mm.getEuro_final_loss() <= 1.7) {
						mm.setAsia_final_ud_mouth(propertyDAO
								.findPropertyByText("-0.5/-1", 3).getId());
					}
					if (mm.getEuro_final_loss() > 1.45
							&& mm.getEuro_final_loss() <= 1.60) {
						mm.setAsia_final_ud_mouth(propertyDAO
								.findPropertyByText("-1", 3).getId());
					}
					if (mm.getEuro_final_loss() > 1.35
							&& mm.getEuro_final_loss() <= 1.45) {
						mm.setAsia_final_ud_mouth(propertyDAO
								.findPropertyByText("-1/-1.5", 3).getId());
					}
					if (mm.getEuro_final_loss() > 1.25
							&& mm.getEuro_final_loss() <= 1.35) {
						mm.setAsia_final_ud_mouth(propertyDAO
								.findPropertyByText("-1.5", 3).getId());
					}

					if (mm.getEuro_final_loss() > 1.25
							&& mm.getEuro_final_loss() <= 1.35) {
						mm.setAsia_final_ud_mouth(propertyDAO
								.findPropertyByText("-1.5", 3).getId());
					}
					if (mm.getEuro_final_loss() > 1.15
							&& mm.getEuro_final_loss() <= 1.25) {
						mm.setAsia_final_ud_mouth(propertyDAO
								.findPropertyByText("-1.5/-2", 3).getId());
					}
				}

			}

			if (mm.getAsia_final_ud_mouth() != null) {
				p = propertyDAO.findPropertyById(mm.getAsia_final_ud_mouth());

				if (p.getText().equals("0") || p.getText().equals("-0")
						|| p.getText().equals("+0")) {

					if (mm.getEuro_final_win() > mm.getEuro_final_loss()) {
						p = propertyDAO.findPropertyByText("-0", 3);
						if (p == null) {
							p = new Property();
							p.setPropertyType_id(3);
							p.setText("-0");
							propertyDAO.insertProperty(p);
						}
						mm.setAsia_final_ud_mouth(p.getId());
					}

					if (mm.getEuro_final_win() <= mm.getEuro_final_loss()) {
						p = propertyDAO.findPropertyByText("+0", 3);
						if (p == null) {
							p = new Property();
							p.setPropertyType_id(3);
							p.setText("+0");
							propertyDAO.insertProperty(p);
						}
						mm.setAsia_final_ud_mouth(p.getId());
					}

				}

			}

			matchMouthDAO.modifyMatchMouth(mm);
			sendMouthChangedWarningMail(mm);
			// l.add(mm);

			Match m = matchDAO.findMatchById2(mm.getMatchId());
			p = propertyDAO.findPropertyById(m.getChampionship());

			Long restTime = ((m.getDate().getTime()) - (new Date()).getTime());
			if ((findMatchMouthIndexInList(mm) < 0)) {

				// Property country = p.getCountry();
				// if(country!=null){

				// List<HistoryResult> l = matchMouthDAO.checkMatchMouthHistory(
				// mm, 0, country.getId(), 365);

				// }

				// List<HistoryResult> l = matchMouthDAO.checkMatchMouthHistory(
				// mm, m.getChampionship(), 0, 365);
				// List<HistoryResult> l2 =
				// matchMouthDAO.checkMatchMouthHistory(
				// mm, 0, 0, 365);
				List<HistoryResult> l = new ArrayList<HistoryResult>();
				if (p.getCountry() != null) {
					List<HistoryResult> l3 = matchMouthDAO
							.checkMatchMouthHistory(mm, 0, p.getCountry()
									.getId(), 365);
					if (l3.size() > 0) {
						l.add(l3.get(0));
					}

				}

				// if (l2.size() > 0) {
				// l.add(l2.get(0));
				// }
				for (IStrategy s : getStrategyList()) {

					oddPredicts = (s.predict(m, mm, l));

					if (((restTime <= 10 * 1000 * 60))
							&& (restTime > (-90 * 1000 * 60))
							&& oddPredicts.size() > 0) {
						sendOddsMail(oddPredicts);
					} else {
						result.addAll(oddPredicts);

					}

				}

				// StringBuffer s = getPrompt(l, true);
				//
				// if (s != null) {
				//
				// if (((restTime <= 10 * 1000 * 60))
				// && (restTime > (-30 * 1000 * 60))) {
				// sendPromptMail(s);
				// } else {
				// mailContent.append(s);
				// }
				// }

			}

		}

		return result;
		// sendWarningFilterMail(l);

	}


	
	private void setMatchMouthInterval(MatchMouth mm) {

		if (mm.getEuro_early_loss() == null || mm.getEuro_early_win() == null) {
			return;
		}

		Double level = mm.getEuro_early_loss();
		if (level > mm.getEuro_early_win()) {
			level = mm.getEuro_early_win();
		}
		// level = Math.floor(level * 100) / 100;
		String sql = "select * " + "  from euro_interval"
				+ "  where low <= :level" + "  order by low desc";

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("level", level);

		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, params);
		if (rs.first()) {
			mm.setWater_level(rs.getString("water_level"));
			mm.setInterval(rs.getString("interval"));
		}

	}

	private void sendOddsMail(List<OddPredict> oddPredicts) throws Exception {

		getOddPredictText(oddPredicts);
		Map<String, List<OddPredict>> oddMaps = new HashMap<String, List<OddPredict>>();

		for (OddPredict op : oddPredicts) {

			IStrategy s = op.getStrategy();

			if (s == null) {

				for (IStrategy s2 : strategyList) {
					if (s2.getStrategyName().equals(
							op.getPk().getStrategyName())) {
						s = s2;
						op.setStrategy(s);
						break;
					}
				}
				if (s == null) {
					continue;
				}

			}
			List<OddPredict> oddList = oddMaps.get(s.getStrategyName());
			if (oddList == null) {
				oddList = new ArrayList<OddPredict>();
				oddMaps.put(s.getStrategyName(), oddList);
			}

			oddList.add(op);
		}

		for (String s : oddMaps.keySet()) {

			List<OddPredict> oddList = oddMaps.get(s);
			OddPredict odd = oddList.get(0);
			IStrategy st2 = odd.getStrategy();

			String content = formatOddPredicts(oddList);

			SendMailThread st = new SendMailThread(smtp, from, st2.getMailTo(),
					st2.getCcTo(), subject.concat("-" + st2.getStrategyName()),
					content, username, password);
			es.submit(st);

		}

		for (OddPredict o : oddPredicts) {
			oddPredictDAO.modifyOddPredict(o);
		}

	}

	private void getOddPredictText(List<OddPredict> oddPredicts) {

		// Iterator<OddPredict> it = oddPredicts.iterator();
		for (Iterator<OddPredict> it = oddPredicts.iterator(); it.hasNext();) {
			// for (OddPredict op : oddPredicts) {

			OddPredict op = it.next();
			Match m = matchDAO.findMatchById2(op.getMatchId());
			MatchMouth mm = matchMouthDAO.findMatchMouthByMatchId(
					op.getMatchId(), op.getCompany());

			if (mm == null) {
				it.remove();
				oddPredicts.remove(op);
				oddPredictDAO.deleteOddPredict(op);
				continue;
			}
			op.setIsInHome(m.getIsInHome());
			op.setLevel(m.getLevel());
			op.setMatchDate(m.getDate());

			Property p;
			p = propertyDAO.findPropertyById(op.getAsia_final_bs_mouth());
			if (p != null) {
				op.setBsMouth(p.getText());
			}

			p = propertyDAO.findPropertyById(op.getAsia_final_ud_mouth());
			if (p != null) {
				op.setUdMouth(p.getText());
			}

			p = propertyDAO.findPropertyById(m.getChampionship());
			if (p != null) {
				op.setChampionship(p.getText());

				Property p2 = p.getCountry();
				if (p2 != null) {
					op.setCountry(p2.getText());
				}

			}

			p = propertyDAO.findPropertyById(op.getCompany());
			if (p != null) {
				op.setCompany_text(p.getText());
			}

			p = propertyDAO.findPropertyById(m.getAwayTeam());
			if (p != null) {
				op.setAwayTeam(p.getText());
			}

			p = propertyDAO.findPropertyById(m.getHomeTeam());
			if (p != null) {
				op.setHomeTeam(p.getText());
			}

			if (m.getScore() != null) {

				op.setScore(m.getScore());

				op.setProfit(-1.00);
				if (op.getPk().getPredict().contains("欧赔")) {
					op.setActual(mm.getWin_standoff_loss());
					if (op.getPk().getPredict().substring(2, 3)
							.equals(mm.getWin_standoff_loss())) {
						// op.setProfit(mm.getEuroWin(op.getPk().getPredict()));
						op.setProfit(op.getEuroWin() - 1);
					}
				}
				if (op.getPk().getPredict().contains("亚赔")) {
					op.setActual(mm.getUp_down());
					if (op.getPk().getPredict().substring(2, 3)
							.equals(mm.getUp_down())) {
						// op.setProfit(mm.getUdWin(op.getPk().getPredict(), op
						// .getUdMouth(), op.getScore()));

						String udMouth = op.getUdMouth();
						if (udMouth == null) {
							udMouth = op.convetEuroToAsia();
							op.setUdMouth(udMouth);
						}

						op.setProfit(op.getUdWin(udMouth));
					}

				}
				if (op.getPk().getPredict().contains("大小")) {

					op.setActual(mm.getBig_small());
					if (op.getPk().getPredict().substring(2, 3)
							.equals(mm.getBig_small())) {
						// op.setProfit(mm.getBsWin(op.getPk().getPredict(), op
						// .getBsMouth(), op.getScore()));
						op.setProfit(op.getBsWin(op.getBsMouth()));
					}

				}
			}

		}

	}

	private String formatOddPredicts(List<OddPredict> oddPredicts)
			throws Exception {

		// Reader reader = new InputStreamReader(getClass().getClassLoader()
		// .getResourceAsStream("OddPredict.vm"));
		VelocityContext context = new VelocityContext();

		context.put("oddPredicts", oddPredicts);
		context.put("date", new org.apache.velocity.tools.generic.DateTool());
		context.put("number",
				new org.apache.velocity.tools.generic.NumberTool());

		Properties p = new Properties();
		p.put(Velocity.OUTPUT_ENCODING, "UTF-8");
		p.put(Velocity.INPUT_ENCODING, "UTF-8");

		p.put(Velocity.ENCODING_DEFAULT, "UTF-8");
		p.setProperty(Velocity.RESOURCE_LOADER, "class");
		p.setProperty("class.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

		Velocity.init(p);

		StringWriter writer = new StringWriter();
		Template template = Velocity.getTemplate("OddPredict.vm", "UTF-8");
		template.merge(context, writer);
		writer.flush();

		// System.out.println(sw.toString());

		// Velocity.evaluate(context, writer, "", reader);
		return writer.toString();

	}

	private void analyzeAsiaUpDownMouth(String infoStr)
			throws MessagingException {

		String[] odds = infoStr.split(splitRecord);
		for (int i = 0; i < odds.length; i++) {
			String[] odd = odds[i].split(splitColumn);

			Property p = propertyDAO.findPropertyById2(
					Integer.parseInt(odd[1]), 5);

			if (null == p) {
				// logger.error(odd[1] + "type:5");
				continue;
			}

			MatchMouth m = matchMouthDAO.findMatchMouthByMatchId(
					Integer.parseInt(odd[0]), p.getId());
			
			//matchMouthDAO.lock(m);

			if (m == null) {
				m = new MatchMouth();
				m.setMatchId(Integer.parseInt(odd[0]));
				m.setCompany(p.getId());
			}
			m.setAsia_early_up(Double.parseDouble(odd[3]));
			m.setAsia_early_down(Double.parseDouble(odd[4]));

			String asiaMouth = goal2GoalCn(Double.parseDouble(odd[2]));
			p = propertyDAO.findPropertyByText(asiaMouth, 3);

			if (p == null) {
				p = new Property();
				p.setPropertyType_id(3);
				p.setText(asiaMouth);
				propertyDAO.insertProperty(p);
			}

			Property early_mouth = getRealAsiaMouth(p, m.getEuro_early_win(),
					m.getEuro_early_loss());

			m.setAsia_early_ud_mouth(early_mouth.getId());

			m.setAsia_final_up(Double.parseDouble(odd[6]));
			m.setAsia_final_down(Double.parseDouble(odd[7]));

			asiaMouth = goal2GoalCn(Double.parseDouble(odd[5]));
			p = propertyDAO.findPropertyByText(asiaMouth, 3);

			if (p == null) {
				p = new Property();
				p.setPropertyType_id(3);
				p.setText(asiaMouth);
				propertyDAO.insertProperty(p);
			}

			// m.setAsia_final_ud_mouth(p.getId());
			// if(p!=null){
			// p = propertyDAO.findPropertyById(m.getAsia_final_ud_mouth());

			Property final_mouth = getRealAsiaMouth(p, m.getEuro_final_win(),
					m.getEuro_final_loss());
			m.setAsia_final_ud_mouth(final_mouth.getId());

			// }

			m.setAsiaOddChangeVol(early_mouth.getId2() - final_mouth.getId2());
			if (m.getAsiaOddChangeVol() < 0) {
				m.setAsiaOddChange("1");
			}
			if (m.getAsiaOddChangeVol() == 0) {
				m.setAsiaOddChange("2");
			}
			if (m.getAsiaOddChangeVol() > 0) {
				m.setAsiaOddChange("3");
			}

			m.setAsiaLevelChangeVol(m.getAsia_early_up()
					- m.getAsia_final_down());

			if (m.getAsiaLevelChangeVol() < 0) {
				m.setAsiaLevelChange("1");
			}
			if (m.getAsiaLevelChangeVol() == 0) {
				m.setAsiaLevelChange("2");
			}
			if (m.getAsiaLevelChangeVol() > 0) {
				m.setAsiaLevelChange("3");
			}

			// try {
			matchMouthDAO.modifyMatchMouth(m);
			sendMouthChangedWarningMail(m);
			// } catch (Exception e) {
			// System.out.println(e);
			// }

		}

	}

	private Property getRealAsiaMouth(Property oldAsiaMouth, Double upLevel,
			Double downLevel) {

		Property p = null;
		if (oldAsiaMouth.getText().equals("0") && upLevel != null
				&& downLevel != null) {

			if (upLevel > downLevel) {
				p = propertyDAO.findPropertyByText("-0", 3);
				if (p == null) {
					p = new Property();
					p.setPropertyType_id(3);
					p.setText("-0");
					propertyDAO.insertProperty(p);
				}

			}

			if (upLevel <= downLevel) {
				p = propertyDAO.findPropertyByText("+0", 3);
				if (p == null) {
					p = new Property();
					p.setPropertyType_id(3);
					p.setText("+0");
					propertyDAO.insertProperty(p);
				}

			}

		}

		if (p != null) {
			return p;
		} else {
			return oldAsiaMouth;
		}

	}

	private void anzlyzeMatch(String infoStr) {

		String[] matches = infoStr.split(splitRecord);
		for (int i = 0; i < matches.length; i++) {
			String[] match = matches[i].split(splitColumn);
			Match m = matchDAO.findMatchById2(Integer.parseInt(match[0]));
			if (m == null) {
				m = new Match();
				m.setId2(Integer.parseInt(match[0]));
			}
			m.setLevel(Integer.parseInt(match[19]));
			m.setIsInHome(!Boolean.parseBoolean(match[18]));
			Property p = propertyDAO.findPropertyById2(
					Integer.parseInt(match[1]), 1);
			if (null == p) {
				logger.error("Could not fine championship id " + match[1]);
			}
			m.setChampionship(p.getId());

			Date matchDate = new Date(Long.parseLong(match[2]));

			if (m.getDate() != null && (!matchDate.equals(m.getDate()))) {

				List<HistoryResult> hl = historyResultDAO
						.findHistoryResultByMatchId(m.getId2(), -1);
				for (HistoryResult h : hl) {
					historyResultDAO.deleteHistoryResult(h);
				}
				List<MatchMouth> ml = matchMouthDAO.findMatchMouthByMatchId(m
						.getId2());
				for (MatchMouth m2 : ml) {
					removeMatchMouthIndexInList(m2);
				}

			}

			m.setDate(matchDate);
			if (match[8].matches("\\d{1,5}")) {
				m.setHomeTeamPosition(Integer.valueOf(match[8]));
			} else {
				m.setHomeTeamPosition(null);
			}

			if (match[13].matches("\\d{1,5}")) {
				m.setAwayTeamPosition(Integer.valueOf(match[13]));
			} else {
				m.setAwayTeamPosition(null);
			}

			if ((m.getAwayTeamPosition() != null)
					&& (m.getHomeTeamPosition() != null)) {
				m.setRankDiff(m.getAwayTeamPosition() - m.getHomeTeamPosition());
			}

			// m.setCompany(Integer.parseInt(match[]));
			// m.setScore(match[15] + ":" + match[16]);

			p = propertyDAO.findPropertyById2(Integer.parseInt(match[4]), 2);
			m.setHomeTeam(p.getId());
			p = propertyDAO.findPropertyById2(Integer.parseInt(match[9]), 2);
			m.setAwayTeam(p.getId());

			matchDAO.modifyMatch(m);

		}

	}

	private void analyzeTeam(String infoStr) {

		String[] teams = infoStr.split(splitRecord);
		for (int i = 0; i < teams.length; i++) {
			String[] team = teams[i].split(splitColumn);
			Property p = propertyDAO.findPropertyById2(
					Integer.parseInt(team[4]), 2);

			if (p == null) {
				Property p1 = new Property();
				p1.setText(team[6]);
				p1.setPropertyType_id(2);
				p1.setId2(Integer.parseInt(team[4]));
				propertyDAO.insertProperty(p1);

			} else {
				// p.setText(team[6]);
				// propertyDAO.modifyProperty(p);
			}

			p = propertyDAO.findPropertyById2(Integer.parseInt(team[9]), 2);

			if (p == null) {
				Property p2 = new Property();
				p2.setId2(Integer.parseInt(team[9]));
				p2.setText(team[11]);
				p2.setPropertyType_id(2);
				propertyDAO.insertProperty(p2);
			} else {
				p.setText(team[11]);
				propertyDAO.modifyProperty(p);

			}

		}

	}

	private void analyzeChampionShip(String infoStr) {

		String[] championShips = infoStr.split(splitRecord);
		for (int i = 0; i < championShips.length; i++) {
			String[] championShip = championShips[i].split(splitColumn);

			Property p = propertyDAO.findPropertyById2(
					Integer.parseInt(championShip[0]), 1);
			if (p == null) {
				p = new Property();
				p.setId2(Integer.parseInt(championShip[0]));
				p.setText(championShip[4]);
				p.setPropertyType_id(1);
				propertyDAO.insertProperty(p);
			} else {
				// p.setText(championShip[4]);
				// propertyDAO.modifyProperty(p);
			}

		}

	}

	public IPropertyDAO getPropertyDAO() {
		return propertyDAO;
	}

	public void setPropertyDAO(IPropertyDAO propertyDAO) {
		this.propertyDAO = propertyDAO;
	}

	public IMatchDAO getMatchDAO() {
		return matchDAO;
	}

	public void setMatchDAO(IMatchDAO matchDAO) {
		this.matchDAO = matchDAO;
	}

	public IMatchMouthDAO getMatchMouthDAO() {
		return matchMouthDAO;
	}

	public void setMatchMouthDAO(IMatchMouthDAO matchMouthDAO) {
		this.matchMouthDAO = matchMouthDAO;
	}

	public void clearPromptList(int hours) {

		for (IStrategy s : getStrategyList()) {
			s.removeExpiredMouth(hours);
		}

		// Date now = new Date();
		// Long diff;
		// for (int i = promptList.size() - 1; i >= 0; i--) {
		//
		// for (MatchMouth o : promptList.get(i).keySet()) {
		// diff = now.getTime() - promptList.get(i).get(o).getTime();
		// if ((diff) >= 1000 * 60 * 60 * hours) {
		//
		// promptList.remove(i);
		// break;
		//
		// }
		// }
		//
		// }

	}

	private void sendSumrizedResult() throws Exception {

		Calendar c = Calendar.getInstance();

		if (c.get(Calendar.HOUR_OF_DAY) < 17) {
			return;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		File iniFile = new File("LotteryBot.ini");
		iniFile.createNewFile();

		org.ini4j.Ini ini = new org.ini4j.Ini(iniFile);

		String s = ini.get("common", "LastSummaryDate");

		if ((s != null) && (s.equals(sdf.format(c.getTime())))) {
			return;
		}

		ini.add("common", "LastSummaryDate", sdf.format(c.getTime()));

		ini.store();

		// c.set(Calendar.MONTH, Calendar.JANUARY);
		// c.set(Calendar.DAY_OF_MONTH, 24);
		// c.add(Calendar.HOUR_OF_DAY, -7);

		c.set(Calendar.HOUR_OF_DAY, 10);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);

		// List<HistoryResult> l =
		// historyResultDAO.findHistoryResult(c.getTime(),
		// summaryHistoryDays);
		// StringBuffer prompt = getPrompt(l, false);
		// sendPromptMail(prompt);
		//
		// for (HistoryResult h : l) {
		//
		// h.setSent(true);
		// historyResultDAO.modifyHistoryResult(h);
		// }

		List<OddPredict> oddPredicts = oddPredictDAO.getOddPredicts(
				c.getTime(), summaryHistoryDays);
		sendOddsMail(oddPredicts);

		String info = getInfo();
		analyzeChampionshipInfo(info);

	}

	public String getProxy_address() {
		return proxy_address;
	}

	public void setProxy_address(String proxy_address) {
		this.proxy_address = proxy_address;
	}

	public int getProxy_port() {
		return proxy_port;
	}

	public void setProxy_port(int proxy_port) {
		this.proxy_port = proxy_port;
	}

	public int getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}

	public Float getEuroWarningPct() {
		return euroWarningPct;
	}

	public void setEuroWarningPct(Float euroWarningPct) {
		this.euroWarningPct = euroWarningPct;
	}

	public Float getAsiaWarningPct() {
		return asiaWarningPct;
	}

	public void setAsiaWarningPct(Float asiaWarningPct) {
		this.asiaWarningPct = asiaWarningPct;
	}

	public int getEuroWarningCount() {
		return euroWarningCount;
	}

	public void setEuroWarningCount(int euroWarningCount) {
		this.euroWarningCount = euroWarningCount;
	}

	private int getSleepTimeByHours() {
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		int result = 0;
		if (hour >= 20 && hour <= 5) {
			result = getSleepTime() / 2;
		} else {
			result = getSleepTime();
		}
		return result;
	}

	public int getAsiaBigWarningCount() {
		return asiaBigWarningCount;
	}

	public void setAsiaBigWarningCount(int asiaBigWarningCount) {
		this.asiaBigWarningCount = asiaBigWarningCount;
	}

	public int getAsiaUpWarningCount() {
		return asiaUpWarningCount;
	}

	public void setAsiaUpWarningCount(int asiaUpWarningCount) {
		this.asiaUpWarningCount = asiaUpWarningCount;
	}

	public int getAsiaSmallWarningCount() {
		return asiaSmallWarningCount;
	}

	public void setAsiaSmallWarningCount(int asiaSmallWarningCount) {
		this.asiaSmallWarningCount = asiaSmallWarningCount;
	}

	public int getAsiaDownWarningCount() {
		return asiaDownWarningCount;
	}

	public void setAsiaDownWarningCount(int asiaDownWarningCount) {
		this.asiaDownWarningCount = asiaDownWarningCount;
	}

	public int getWarningBeforeMatchTime() {
		return warningBeforeMatchTime;
	}

	public void setWarningBeforeMatchTime(int warningBeforeMatchTime) {
		this.warningBeforeMatchTime = warningBeforeMatchTime;
	}

	public int getSummaryHistoryDays() {
		return summaryHistoryDays;
	}

	public void setSummaryHistoryDays(int summaryHistoryDays) {
		this.summaryHistoryDays = summaryHistoryDays;
	}

	public List<IStrategy> getStrategyList() {
		return strategyList;
	}

	public void setStrategyList(List<IStrategy> strategyList) {
		this.strategyList = strategyList;
	}

	public IOddPredictDAO getOddPredictDAO() {
		return oddPredictDAO;
	}

	public void setOddPredictDAO(IOddPredictDAO oddPredictDAO) {
		this.oddPredictDAO = oddPredictDAO;
	}

	public Map<String, IStrategy> getStrategyMap() {
		return strategyMap;
	}

	public void setStrategyMap(Map<String, IStrategy> strategyMap) {
		this.strategyMap = strategyMap;
	}

	@Override
	public String call() throws Exception {

		// try {
		// 以半分钟， 30秒为一个SLEEPTIME
		// lbot.getResultContent();
		// Thread.sleep(1000 * 30 * lbot.getSleepTimeByHours());
		Date from = new Date();
		logger.info("start from " + from);
		String content;

		content = getOddContent();

		// System.out.println(content);
		String[] domains = content.split(splitDomain);
		StringBuffer mailContent = new StringBuffer();

		analyzeChampionShip(domains[0]);
		analyzeTeam(domains[1]);
		anzlyzeMatch(domains[1]);
		analyzeAsiaUpDownMouth(domains[2]);
		analyzeAsiaBigSmallMouth(domains[4]);

		List<String> results = null;
		// if (i % 1 == 0) {
		results = getResultContent();
		analyzeMatchResult(results);
		deleteUnuseMatchMouth();
		// i = 0;
		// }

		List<OddPredict> ods = analyzeEuroWSL(domains[3]);
		// lbot.sendPromptMail(mailContent);
		sendOddsMail(ods);

		// }

		sendSumrizedResult();
		clearPromptList(3);
		Date to = new Date();

		Long useMinutes = (to.getTime() - from.getTime()) / 1000 / 60;
		logger.info("end in " + to);

		logger.info("use " + useMinutes + " min.");
		// } catch (Exception e) {

		// StringWriter sw = new StringWriter();
		// PrintWriter pw = new PrintWriter(sw);
		// e.printStackTrace(pw);

		// logger.error(sw.toString());

		// }
		return null;

	}

}
