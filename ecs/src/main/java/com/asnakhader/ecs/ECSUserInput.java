package com.asnakhader.ecs;

public class ECSUserInput {
	private long requestNumber;
	
	private String request;

	public long getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(long requestNumber) {
		this.requestNumber = requestNumber;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}
	
	public ECSUserInput(long id, String content) {
		this.requestNumber = id;
		this.request = content;
	}
	
}
