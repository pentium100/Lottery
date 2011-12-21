package com.itg.dao;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="responseReportMemo")
public class ResponseReportMemo {
	private Long total;
	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	private boolean success;
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private String message;
	private List<ReportMemo> reportMemo;
	public List<ReportMemo> getReportMemo() {
		return reportMemo;
	}

	public void setReportMemo(List<ReportMemo> reportMemo) {
		this.reportMemo = reportMemo;
	}



}
