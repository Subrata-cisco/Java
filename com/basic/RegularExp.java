package com.basic;

import java.util.regex.Pattern;


public class RegularExp {

	
	public static void main(String[] args) {
        Pattern regex = Pattern.compile("\\d*(\\D*)");
        
        
        String input1 = "10 euro";
        String input2 = "10 euro";
        String intValue = input1.replaceAll("[a-zA-Z]", ""); 
        String stringValue = input2.replaceAll("[0-9]", ""); 
        System.out.println("*************** Subrata intValue ::"+intValue+" stringValue ::"+stringValue);
        
        /*Matcher matcher = regex.matcher(input);

        if (matcher.matches() && matcher.groupCount() == 1) {
            String digitStr = matcher.group(1);
            Integer digit;
			try {
				digit = Integer.parseInt(digitStr);
				System.out.println(digit);       
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
                 
        }

        System.out.println("done.");*/
    }
}
