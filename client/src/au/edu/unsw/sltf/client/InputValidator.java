package au.edu.unsw.sltf.client;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

public class InputValidator {
	
	
	
	public static boolean checkYear(String year){
		
		boolean b = Pattern.matches("[1-2][0-9]{3}", year);
		if (b)
			return true;
		return false;
	}
	
	// TODO: COMPLETE
	public static String convertMillSecToThreeDigits(String milli){
		
		int milliseconds = Integer.parseInt(milli);		
			
		return milli;
	}

	

}
