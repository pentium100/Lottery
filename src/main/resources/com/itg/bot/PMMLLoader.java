package com.itg.bot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.itg.dao.FilterItem;
import com.itg.dao.FilterItemOpt;
import com.itg.dao.Property;
import com.itg.dao.PropertyDAO;

public class PMMLLoader {

	/**
	 * @param args
	 * @throws Exception
	 */

	public List<LinkedList<FilterItem>> result = new ArrayList<LinkedList<FilterItem>>();
	private Element schama;
	private StringBuffer script = new StringBuffer();

	private PropertyDAO propertyDAO;

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		/*
		 * 0031 威廉 0032 韦德 0033 立博 0036 澳门 0037 InterW 0038 易胜 0043 BET365
		 */

		PMMLLoader loader = new PMMLLoader();
		
		String combineFunction = "function check(ASIALEVELCHANGE,ASIALEVELCHANGEVOL,ASIAODDCHANGE,ASIAODDCHANGEVOL,ASIA_EARLY_DOWN,ASIA_EARLY_UD_MOUTH,ASIA_EARLY_UP,ASIA_FINAL_DOWN,ASIA_FINAL_UD_MOUTH,ASIA_FINAL_UP,CHAMPIONSHIP,COUNTRY,EURO_EARLY_LOSS,EURO_EARLY_LOSS2,EURO_EARLY_STANDOFF,EURO_EARLY_STANDOFF2,EURO_EARLY_WIN,EURO_EARLY_WIN2,EURO_FINAL_LOSS,EURO_FINAL_LOSS2,EURO_FINAL_STANDOFF,EURO_FINAL_STANDOFF2,EURO_FINAL_WIN,EURO_FINAL_WIN2,UP_DOWN, COMPANY){";
		String singleFunction = "function check(ASIALEVELCHANGE,ASIALEVELCHANGEVOL,ASIAODDCHANGE,ASIAODDCHANGEVOL,ASIA_EARLY_DOWN,ASIA_EARLY_UD_MOUTH,ASIA_EARLY_UP,ASIA_FINAL_DOWN,ASIA_FINAL_UD_MOUTH,ASIA_FINAL_UP,CHAMPIONSHIP,COUNTRY,EURO_EARLY_LOSS,EURO_EARLY_STANDOFF,EURO_EARLY_WIN,EURO_FINAL_LOSS,EURO_FINAL_STANDOFF,EURO_FINAL_WIN,UP_DOWN, COMPANY){";

		System.out.println("31-威廉-InterW");
		loader.filterPMMLTree("/com/itg/bot/CODDS_31.xml", "c37_31.js", 31, "2", combineFunction);
		System.out.println("32-韦德-InterW");
		loader.filterPMMLTree("/com/itg/bot/CODDS_32.xml", "c37_32.js", 32, "2", combineFunction);
		System.out.println("33-立博-InterW");
		loader.filterPMMLTree("/com/itg/bot/CODDS_33.xml", "c37_33.js", 33, "2", combineFunction);
		System.out.println("36-澳门-InterW");
		loader.filterPMMLTree("/com/itg/bot/CODDS_36.xml", "c37_36.js", 36, "2", combineFunction);
		System.out.println("38-易胜-InterW");
		loader.filterPMMLTree("/com/itg/bot/CODDS_38.xml", "c37_38.js", 38, "2", combineFunction);
		System.out.println("43-BET365-InterW");
		loader.filterPMMLTree("/com/itg/bot/CODDS_43.xml", "c37_43.js", 43, "2", combineFunction);
		
		System.out.println("31-威廉");
		loader.filterPMMLTree("/com/itg/bot/31.xml", "s31.js",31, "", singleFunction);
		System.out.println("32-韦德");
		loader.filterPMMLTree("/com/itg/bot/32.xml", "s32.js",32, "", singleFunction);
		System.out.println("33-立博");
		loader.filterPMMLTree("/com/itg/bot/33.xml", "s33.js",33, "", singleFunction);
		System.out.println("36-澳门");
		loader.filterPMMLTree("/com/itg/bot/31.xml", "s36.js",36, "", singleFunction);
		System.out.println("37-ITW");
		loader.filterPMMLTree("/com/itg/bot/37.xml", "s37.js",37, "", singleFunction);
		System.out.println("38-易胜");
		loader.filterPMMLTree("/com/itg/bot/38.xml", "s38.js",38, "", singleFunction);
		System.out.println("43-BET365");
		loader.filterPMMLTree("/com/itg/bot/43.xml", "s43.js",43, "", singleFunction);


		System.out.println("32-韦德-威廉");
		loader.filterPMMLTree("/com/itg/bot/CODDS31_32.xml", "c31_32.js", 32, "2", combineFunction);
		System.out.println("33-立博-威廉");
		loader.filterPMMLTree("/com/itg/bot/CODDS31_33.xml", "c31_33.js", 33, "2", combineFunction);
		System.out.println("36-澳门-威廉");
		loader.filterPMMLTree("/com/itg/bot/CODDS31_36.xml", "c31_36.js", 36, "2", combineFunction);
		System.out.println("37-InterW-威廉");
		loader.filterPMMLTree("/com/itg/bot/CODDS31_37.xml", "c31_37.js", 37, "2", combineFunction);
		System.out.println("38-易胜-威廉");
		loader.filterPMMLTree("/com/itg/bot/CODDS31_38.xml", "c31_38.js", 38, "2", combineFunction);
		System.out.println("43-BET365-威廉");
		loader.filterPMMLTree("/com/itg/bot/CODDS31_43.xml", "c31_43.js", 43, "2", combineFunction);

	
	
	}

	public PMMLLoader() {

		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "applicationContext.xml", "Lottery-dao.xml" });

		propertyDAO = (PropertyDAO) context.getBean("propertyDAO");
	}

	public void filterPMMLTree(String fileName, String scriptFileName,
			Integer company, String validString, String functionHeader) throws IOException {

		InputStream in = PMMLLoader.class.getResourceAsStream(fileName);

		StringBuffer sb = new StringBuffer();
		BufferedReader bf = new BufferedReader(new InputStreamReader(in));
		String line1;
		Pattern p = Pattern.compile("value=\"(\\d+.\\d+)-\"");

		while ((line1 = bf.readLine()) != null) {

			Matcher m = p.matcher(line1);
			if (m.find()) {
				line1 = m.replaceAll("value=\"-" + m.group(1) + "\"");
			}

			sb.append(line1 + "\n");
		}

		// InputStream in2 = new StringBufferInputStream(sb.toString());

		InputStream in2 = new ByteArrayInputStream(sb.toString().getBytes());

		try {
			SAXReader saxReader = new SAXReader();
			Document doc = (Document) saxReader.read(in2);

			List<Element> l = doc.selectNodes("/PMML//Node");
			schama = (Element) doc.selectSingleNode("/PMML/DataDictionary");
			Element root = l.get(0);
			LinkedList<FilterItem> cond = new LinkedList<FilterItem>();

			analyzePMMLTree(cond, root, validString);

			// FileWriter fstream2 = new FileWriter(scriptFileName+".label");
			// BufferedWriter out = new BufferedWriter(fstream2);

			OutputStreamWriter out = new OutputStreamWriter(
					new FileOutputStream(scriptFileName), "UTF-8");

//			out.write("function check(");
//
//			List<Element> fieldList = schama.selectNodes("DataField");
//			boolean isFirst = true;
//			for (Element e : fieldList) {
//				if (!isFirst) {
//					out.write(",");
//				}
//				isFirst = false;
//
//				out.write(e.attributeValue("name"));
//
//			}
//			out.write(", COMPANY");
//
//			out.write("){" );
			
			out.write(functionHeader);
			
			
			
			out.write("\n");
			out.write("if(COMPANY!=" + company + "){return '0';}\n");
			out.write(script.toString() + "}\n");
			// Close the output stream
			out.flush();
			out.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		script.setLength(0);

	}

	public void analyzePMMLTree(LinkedList<FilterItem> cond, Element n, String validString) {

		Double ttl = Double.parseDouble(n.attributeValue("recordCount"));

		if (ttl < 20) {
			return;
		}

		Element sp = (Element) n.selectSingleNode("SimplePredicate");

		if (sp != null) {
			String value = sp.attributeValue("value");
			String optType = ((Element) schama
					.selectSingleNode("DataField[@name='"
							+ sp.attributeValue("field") + "']"))
					.attributeValue("optype");
			if (optType.equals("categorical")) {
				value = value.replaceAll("^0+", "");
			}

			cond.addLast(FilterItemOpt.makeFilter("numeric",
					sp.attributeValue("operator"), sp.attributeValue("field"),
					new String[] { value }));
			FilterItem fi = cond.getLast();
			fi.setLabel(((Element) schama.selectSingleNode("DataField[@name='"
					+ sp.attributeValue("field") + "']"))
					.attributeValue("displayName"));

		}

		List<Element> l = n.selectNodes("ScoreDistribution");
		for (Element e : l) {

			Double recordCount = Double.parseDouble(e
					.attributeValue("recordCount"));

			if ((recordCount / ttl >= 0.75d) && (recordCount > 20)) {
				String value = e.attributeValue("value");
				generateScript(cond, recordCount, ttl, value, validString);
			}

		}

		List<Element> child = n.selectNodes("Node");

		for (Element e : child) {

			analyzePMMLTree(cond, e, validString);
		}
		if (!cond.isEmpty()) {
			cond.removeLast();
		}

	}

	private void generateScript(LinkedList<FilterItem> cond,
			Double recordCount, Double ttl, String value, String validString) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		// sb.append(" ( 1==1 ) ");

		StringBuffer labelSb = new StringBuffer();
		// labelSb.append(" ( 1==1 ) ");

		boolean isFirst = true;
		boolean isValid = false;
		for (FilterItem fi : cond) {

			try {
				String temp = fi.getSql();
				if (fi.getComparison().equals("=")) {
					temp = temp.replaceAll("=", "==");
				}
				StringBuffer labelSql = new StringBuffer();

				if (!isFirst) {
					sb.append(" && ");
					labelSql.append(" and ");
				}
				isFirst = false;
				sb.append("(" + temp + ")");

				labelSql.append("(");
				labelSql.append(fi.getSqlFieldLabel());
				if (fi.getSqlFieldLabel().contains(validString)) {
					isValid = true;
				}
				// labelSql.append(" ");
				labelSql.append(fi.getComparison());
				// labelSql.append(" ");

				String optType = ((Element) schama
						.selectSingleNode("DataField[@name='" + fi.getField()
								+ "']")).attributeValue("optype");
				if (optType.equals("categorical")) {
					Property p = propertyDAO.findPropertyById(Integer
							.valueOf(fi.getSqlValues()));
					if (p != null) {
						labelSql.append(p.getText());
					} else {
						labelSql.append(fi.getSqlValues());
					}
				} else {

					labelSql.append(fi.getSqlValues());
				}

				labelSql.append(")");

				labelSb.append(labelSql.toString());

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		String remark = labelSb + "result: " + value + " ttl:" + ttl
				+ " record: " + recordCount + " confident: "
				+ Math.round(recordCount / ttl * 100) + "%";

		String s2 = "if " + "(" + sb.toString() + "){return '" + remark
				+ "';}\n";

		String s3 = "if " + "(" + labelSb.toString() + "){return '" + value
				+ "';}" + "//ttl:" + ttl + " record: " + recordCount
				+ " value=" + value + "\n";

		if (isValid) {
			script.insert(0, s2);

			System.out.println(labelSb.toString() + "ttl:" + ttl + " record: "
					+ recordCount + " value=" + value);
		}
	}
}
