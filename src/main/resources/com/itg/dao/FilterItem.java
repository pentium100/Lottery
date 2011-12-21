package com.itg.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FilterItem {

	private String comparison;
	private String type;
	private List<String> values;
	private String field;
	private String label;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getComparison() {

		if (getType().equals("list")) {
			return "in";
		}
		if (getType().equals("boolean")) {
			return "=";
		}

		if (getType().equals("string") && (getValues().get(0).equals("null"))) {
			return "is";
		}

		if (getType().equals("string")) {
			return "like";
		}

		if (comparison.equals("gt") || comparison.equals("greaterThan")) {
			return ">";
		}
		if (comparison.equals("lt") || comparison.equals("lessThan")) {
			return "<";
		}

		if (comparison.equals("ge") || comparison.equals("greaterOrEqual")) {
			return ">=";
		}
		if (comparison.equals("le") || comparison.equals("lessOrEqual")) {
			return "<=";
		}

		if (comparison.equals("eq") || comparison.equals("equal")) {
			return "=";
		}
		if (comparison.equals("notEqual")) {
			return "!=";
		}

		return comparison;
	}

	public void setComparison(String comparison) {

		this.comparison = comparison;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getSqlValues() {

		StringBuffer result = new StringBuffer();
		if (getType().equals("date")) {

			try {
				SimpleDateFormat s = new SimpleDateFormat("MM/dd/yyyy");
				Date today = s.parse(getValues().get(0));

				// if (getComparison().equals("=")) {
				//
				//
				// today.setTime((today.getTime() - 1000*60*60*12));
				//
				//
				// }

				result.append("'"
						+ new SimpleDateFormat("yyyy/MM/dd").format(today)
						+ "'");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		if (getType().equals("datetime")) {

			// SimpleDateFormat s = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			// Date today = s.parse(getValues().get(0));

			// if (getComparison().equals("=")) {
			//
			//
			// today.setTime((today.getTime() - 1000*60*60*12));
			//
			//
			// }

			result.append("'" + getValues().get(0) + "'");

		}

		if (getType().equals("string")) {

			if (getValues().get(0).equals("null")) {

				result.append(" null ");
			} else {

				result.append("'" + getValues().get(0) + "'");
			}
		}

		if (getType().equals("list")) {
			result.append("(");
			List<String> l = getValues();
			for (int i = 0; i < l.size(); i++) {
				if (l.get(i) == null) {
					continue;
				}
				if (i > 0) {
					result.append(",");
				}
				result.append("'" + l.get(i) + "'");
			}
			result.append(")");
			;

		}

		if (getType().equals("numeric")) {
			result.append(getValues().get(0));

		}

		if (getType().equals("boolean")) {
			result.append(getValues().get(0).equals("true") ? 1 : 0);

		}

		return result.toString();
	}

	private String getTodayCondition() throws ParseException {

		StringBuffer s = new StringBuffer();

		Calendar c = Calendar.getInstance();
		SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");
		Date today = f.parse(getValues().get(0));
		c.setTime(today);

		c.add(c.DATE, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);

		// if(c.get(Calendar.HOUR_OF_DAY)<=12){
		//
		// c.set(Calendar.HOUR_OF_DAY, 0);
		// c.set(Calendar.MINUTE, 0);
		// c.set(Calendar.SECOND, 0);
		// }
		// else{
		// c.add(c.DATE, 1);
		// c.set(Calendar.HOUR_OF_DAY, 0);
		// c.set(Calendar.MINUTE, 0);
		// c.set(Calendar.SECOND, 0);
		//
		// }

		s.append(" convert(float, " + getField() + "-'" + f.format(c.getTime())
				+ "')  >= -0.5 ");
		s.append(" AND convert(float, " + getField() + "-'"
				+ f.format(c.getTime()) + "')  <= 0.5 ");

		return s.toString();

	}

	public String getSqlFieldLabel() {
		return getLabel();
	}

	public String getSqlField() {

		if (getType().equals("date")) {

			return "convert(VARCHAR(10), " + getField() + ", 111)";

		}

		return getField();

	}

	public String getSql() throws ParseException {
		StringBuffer result = new StringBuffer();

		if (isTodayCondition()) {
			return getTodayCondition();
		}

		result.append(getSqlField());
		result.append(" ");
		result.append(getComparison());
		result.append(" ");
		result.append(getSqlValues());
		result.append(" ");
		return result.toString();
	}

	public String getLabelSql() throws ParseException {
		StringBuffer result = new StringBuffer();

		if (isTodayCondition()) {
			return getTodayCondition();
		}

		result.append(getSqlFieldLabel());
		result.append(" ");
		result.append(getComparison());
		result.append(" ");
		result.append(getSqlValues());
		result.append(" ");
		return result.toString();
	}

	private boolean isTodayCondition() {

		SimpleDateFormat s = new SimpleDateFormat("MM/dd/yyyy");
		Date today = new Date();
		// && s.format(today).equals(getValues().get(0))
		if ((getType().equals("date")) && getComparison().equals("=")) {

			return true;
		}

		return false;

	}

}
