package com.goeuro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Client {

	String url;
	final private String charset = "UTF-8";

	Client(String url){
		this.url = url;
	}

	void parse(StringBuilder output){

		try{
			java.net.URLConnection connection = new URL(url).openConnection();
			connection.setRequestProperty("Accept-Charset", charset);

			if ( !(connection instanceof HttpURLConnection))
			{
				throw new Exception("HTTP connection couldn't be established");
			}
			HttpURLConnection httpConnection = (HttpURLConnection) connection;
			if(httpConnection.getResponseCode() != 200){
				System.err.println("HTTP connection failed with code: " + httpConnection.getResponseCode() +
						". " + httpConnection.getResponseMessage());
				return;
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), charset));

			String line;
			while((line = reader.readLine()) != null) {
				output.append(line);
			}
			output.insert(0, "{\"response\":");
			output.append("}");
		}catch(IOException e){
			System.err.println("An IOException ocurred while parsing the HTTP response. Connectivity problem.");
		}
		catch(Exception e){
			System.err.println(e.getMessage());
		}
	}
}
