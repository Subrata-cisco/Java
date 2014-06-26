package com.basic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExceptionHandling {

	public static void main(String[] args) {
		System.out.println("****** Subrata -> "+test());
		List<Integer> list = new ArrayList<>();
		Collections.addAll(list,1,2,3,4,5,6,7,8,9);
	}
	
	private static int test(){
		int k = -1;
		try{
			throw new Exception();
			//k = 4;
			//return 1;
		}catch(Exception ex){
			//k = 2;
			return 2;
		}finally{
			//k = 3;
			return 3;
		}
	}
}
