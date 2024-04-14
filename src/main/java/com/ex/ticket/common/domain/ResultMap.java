package com.ex.ticket.common.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class ResultMap extends HashMap<String, Object> {
	
	private static final long serialVersionUID = 1L;

	public void setSuccess() {
		this.put(Constants.KEY_RESULT, Constants.VALUE_RESULT_SUCCESS);
	}
	
	public void setFailure() {
		this.put(Constants.KEY_RESULT, Constants.VALUE_RESULT_FAILURE);
	}

	public void setWrongAccess() {
		this.put(Constants.KEY_RESULT, Constants.VALUE_RESULT_ACCESS_DENIED);
		this.put(Constants.KEY_MSG, Constants.VALUE_MSG_WRONG_ACCESS);
	}
	
	public void setParamMissing(String param) {
		this.put(Constants.KEY_RESULT, Constants.VALUE_RESULT_PARAM_MISSING);
		this.put(Constants.KEY_PARAM, param);
	}
	
	public void setList(List<?> list) {
		this.put(Constants.KEY_LIST, list);
	}
	
	public void setPaging(int totalCount, int page, int pageRow) {
		this.put(Constants.KEY_TOTAL, totalCount);
		this.put(Constants.KEY_PAGE, page);
		this.put(Constants.KEY_PAGE_ROW, pageRow);
	}
	
	public void setInfo(Map<?, ?> info) {
		this.put(Constants.KEY_INFO, info);
	}
	
	public void setData(Map<?, ?> data) {
		this.put(Constants.KEY_DATA, data);
	}
	
	public void setMessage(String message) {
		this.put(Constants.KEY_MSG, message);
	}
	
	public void reset() {
		this.clear();
	}


	public String toJSONString() {
		JSONObject jsonObj = new JSONObject(this);
		return jsonObj.toString();
	}

	public static ResultMap getWrongAccessResult() {
		ResultMap result = new ResultMap();
		result.setWrongAccess();

		return result;
	}


}
