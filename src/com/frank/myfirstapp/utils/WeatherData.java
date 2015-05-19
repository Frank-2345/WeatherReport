package com.frank.myfirstapp.utils;

//import java.util.List;


public class WeatherData {
	private int resultcode;
	private String reason;
	private Result result;
	private int error_code;
	
	public void setResultcode(int resultcode){
		this.resultcode = resultcode;
	}
	public void setReason(String reason){
		this.reason = reason;
	}
	public void setResult(Result result){
		this.result = result;
	}
	public void setError_code(int error_code){
		this.error_code = error_code;
	}
	
	public int getResultcode(){
		return this.resultcode;
	}
	public String getReason(){
		return this.reason;
	}
	public Result getResult(){
		return this.result;
	}
	public int getError_code(){
		return this.error_code;
	}
}
