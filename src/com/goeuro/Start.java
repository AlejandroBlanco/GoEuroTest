package com.goeuro;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import org.json.CDL;
import org.json.JSONException;
import org.json.JSONArray;

public class Start {
	private static String ENDPOINT ="http://api.goeuro.com/api/v2/position/suggest/en/";

	private static PrintWriter writeToFile(String param) throws IOException, Exception{
		String filename = "goeuro-" + param;
		int ver = 1;
		File file = new File(filename);
		Thread.sleep(3);
		while(file.exists()){
			ver++;
			filename = "goeuro-" + param + String.valueOf(ver);
			file = new File(filename);
		}
		file.createNewFile();
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(filename), "UTF-8"));
		PrintWriter writer = new PrintWriter(out);
		return writer;
	}
	
	public static void main(String[] args) 
	{

		ParamValidator.initialize(args);
		if(!ParamValidator.isValid()){
			System.err.println(ParamValidator.getError());
			return;
		}

		String url = ENDPOINT + args[0];
		StringBuilder jsonoutput = new StringBuilder();
		JSONArray res = new JSONArray();

		Client client = new Client(url);
		client.parse(jsonoutput);

		try{
			JSONUtils.transform(jsonoutput.toString());
			res = JSONUtils.getResult();
			
			PrintWriter writer = writeToFile(args[0]);
			writer.print(CDL.toString(res));
			writer.close();

		}catch(IOException e){
			System.err.println("An IOException ocurred while writing to file. Ensure there is space in the disk");
			System.err.println(e.getMessage());

		}catch(JSONException e){
			System.err.println(e.getMessage());
		}
		catch(Exception e){
			System.err.println(e.getMessage());
		}
	}
}