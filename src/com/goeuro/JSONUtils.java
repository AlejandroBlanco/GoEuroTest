package com.goeuro;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtils {

	private static JSONUtils instance = null;
	private static String input;
	private static JSONArray output;
	private static String error;

	public static JSONUtils transform(String args) {
		if(instance == null) {
			instance = new JSONUtils(args);
		}
		return instance;
	}

	private JSONUtils(String string){
		input = string;
		output = new JSONArray();
		error = "";
		parse(input);
	}

	private void parse(String input){
		try{
			JSONObject json = new JSONObject(input);
			JSONArray jsona = json.getJSONArray("response");
			Iterator<Object> it = jsona.iterator();
			while(it.hasNext()){
				JSONObject jsonc = (JSONObject)it.next();
				JSONObject resc = new JSONObject();
				resc.put("_id", jsonc.getInt("_id"));
				resc.put("name", jsonc.getString("name"));
				resc.put("type", jsonc.getString("type"));
				resc.put("latitude", jsonc.getJSONObject("geo_position").getDouble("latitude"));
				resc.put("longitude", jsonc.getJSONObject("geo_position").getDouble("longitude"));
				output.put(resc);
			}
		}
		catch(JSONException e){
			error = "A JSONException ocurred while parsing the JSON Object. JSON Object is malformed";
		}catch(Exception e){
			error = e.getMessage();
		}
	}

	static JSONArray getResult() throws JSONException{
		if (error.equals(""))
			return output;
		else 
			throw new JSONException(error);
	}
}
