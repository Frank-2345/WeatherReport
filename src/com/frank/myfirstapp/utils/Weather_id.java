package com.frank.myfirstapp.utils;

public class Weather_id {    /*天气唯一标识*/
	
	private String fa;/*天气标识00：晴*/
	private String fb;/*天气标识53：霾 如果fa不等于fb，说明是组合天气*/
	
	public void setFa(String fa){
		this.fa = fa;
	}
	public void setFb(String fb){
		this.fb = fb;
	}
	
	public String getFa(){
		return this.fa;
	}
	public String getFb(){
		return this.fb;
	}

}
