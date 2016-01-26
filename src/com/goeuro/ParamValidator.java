package com.goeuro;

public class ParamValidator {
	
	static private boolean valid;
	static private String error;
	private static ParamValidator instance = null;

    public static ParamValidator initialize(String[] args) {
       if(instance == null) {
          instance = new ParamValidator(args);
       }
       return instance;
    }
	
	private ParamValidator(String[] args){
		if(hasOneParam(args) && isAllASCII(args))
			valid = true;
		else
			valid = false;
	}
	
    private static boolean isAllASCII(String[] input) {
        boolean isASCII = true;
        for (int i = 0; i < input[0].length(); i++) {
            int c = input[0].charAt(i);
            if (c > 0x7F) {
                isASCII = false;
                error = "Characters in http query are not valid. Use of ASCII mandatory."
          		  		+ " RFC 3986. http://tools.ietf.org/html/rfc3986#section-2";
                break;
            }
        }
        return isASCII;
    }
    
    private static boolean hasOneParam(String[] input) {
        if(input.length!=1){
        	error = "GoEuroTest.jar use only one parameter.";
        	return false;
        }
        else
        	return true;
    }
    
    static boolean isValid(){
    	return valid;
    }
    
    static String getError(){
    	return error;
    }
}
