package com.itg.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class OddPredictPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8822656563226396816L;
	@Column(length = 6)
	private String predict;
	private Integer matchMouthId; // 盘口id
	
	private Integer scope;
	private String strategyName;
	
	
	
	public int hashCode() {
		return (int) predict.hashCode() + matchMouthId.hashCode() + scope.hashCode();
	}

	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof OddPredictPK))
			return false;
		if (obj == null)
			return false;
		OddPredictPK pk = (OddPredictPK) obj;
		return pk.predict.equals(predict)
				&& pk.matchMouthId.equals(matchMouthId)
				&& pk.scope.equals(scope);
	}

	public String getPredict() {
		return predict;
	}

	public void setPredict(String predict) {
		this.predict = predict;
	}

	public Integer getMatchMouthId() {
		return matchMouthId;
	}

	public void setMatchMouthId(Integer matchMouthId) {
		this.matchMouthId = matchMouthId;
	}

	

	public String getStrategyName() {
		return strategyName;
	}

	public void setStrategyName(String strategyName) {
		this.strategyName = strategyName;
	}

	public Integer getScope() {
		return scope;
	}

	public void setScope(Integer scope) {
		this.scope = scope;
	}



}
