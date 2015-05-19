package com.frank.myfirstapp.utils;

import java.util.List;


public class Result{
		private SK sk; /*当前实况天气*/
		private Today today;
//		private Future future;
		private List<Future> future;
		
		public void setSk(SK sk){
			this.sk = sk;
		}
		public void setToday(Today today){
			this.today = today;
		}
		public void setFuture(List<Future> future){
			this.future = future;
		}
		
		public SK getSk(){
			return this.sk;
		}
		public Today getToday(){
			return this.today;
		}
		public List<Future> getFuture(){
			return this.future;
		}
		
}
