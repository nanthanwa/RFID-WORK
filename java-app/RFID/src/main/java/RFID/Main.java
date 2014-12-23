package RFID;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

public class Main{
	public static String RFID = null;
	public static JSONArray array;
	public static Panel form;
	public static ReadRFID readRFID = null;
		
	public static void main(String[] args){
		
		form = new Panel();
		readRFID = new ReadRFID();
		getData();
	
		Runnable task = new Runnable(){
			public void run(){
				readRFID.start();
				while(true){
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					RFID = readRFID.getRFID();
					System.out.println(RFID);
					if (RFID != null) {
						try {
							Thread.sleep(1000);
							Main.CheckRFID();
							readRFID.setRFID(null);
							Thread.sleep(1000);
						} catch (Exception e) {
							System.out.println("Connection Fail");
							e.printStackTrace();
						}
					}
				}
			}
		};
		
		Thread check = new Thread(task);
		check.start();
		
		
	}
	
	public static void getData() {
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet("http://localhost:3000/api/employee");
			//request.addHeader("accept", "application/json");
		
			HttpResponse response = client.execute(request);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
				response.getEntity().getContent())
			);
			StringBuilder sb = new StringBuilder();
			String line = null;

			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			array = new JSONArray(sb.toString());
			//System.out.println(array);

		}catch (Exception e) {
			System.out.println(e);
		}

	}
	
	public static void CheckRFID() {
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost requestpost = new HttpPost("http://localhost:3000/api/employee");
			for (int i = 0; i < array.length(); i++) {
				JSONObject person = array.getJSONObject(i);
				//System.out.println(person.getString("card"));
				if (person.getString("card").equals(RFID)) {
					StringEntity params = new StringEntity(person.toString());
					requestpost.setHeader("Content-type", "application/json");
					requestpost.setEntity(params);
					HttpResponse response = client.execute(requestpost);
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(response.getEntity()
									.getContent()));
					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line);
					}
					JSONArray json = new JSONArray('[' + sb.toString() + ']');

					System.out.println(json.getJSONObject(0).getString("fname"));
					//form.setForm(json.getJSONObject(0).getString("fname"),json.getJSONObject(0).getString("lname"));
						
					// System.out.println(sb.toString());
					//System.out.println(person.getString("fname"));
					break;
				}
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

}