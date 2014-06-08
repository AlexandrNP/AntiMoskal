package com.example.antimoskal_v001;

import java.util.regex.*;

import android.util.Log;

public class StringParser {

	public static String getResultValue(String text, int len){
		String result = "", from_regex="";
		Pattern pat = Pattern.compile("</td><td><input value=\".*\" name=\"information\"");
		Matcher m = pat.matcher(text);
		Log.d("TAGG", " "+pat.toString());
		
		if(m.find()) {
			from_regex = m.toMatchResult().group(0);
		}
//		
		result = from_regex.substring(127 + len,from_regex.length()-20);
		
		return result;
	}
}

