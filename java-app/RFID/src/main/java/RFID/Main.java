package RFID;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
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
		
		//form = new Panel();
		readRFID = new ReadRFID();
		
	
		(new Thread(new Runnable(){
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
				}
			}
		})).start();
	}
	
	public void run(){
		System.out.println("hello");
	}

//	public static void CheckIn() {
//		try {
//			HttpClient client = HttpClientBuilder.create().build();
//			HttpPost requestpost = new HttpPost("http://localhost:8080/api/check-in");
//			for (int i = 0; i < array.length(); i++) {
//				JSONObject person = array.getJSONObject(i);
//				if (person.getString("RFID").equals(RFID)) {
//					StringEntity params = new StringEntity(person.toString());
//					requestpost.setHeader("Content-type", "application/json");
//					requestpost.setEntity(params);
//					HttpResponse response = client.execute(requestpost);
//					BufferedReader reader = new BufferedReader(
//							new InputStreamReader(response.getEntity()
//									.getContent()));
//					StringBuilder sb = new StringBuilder();
//					String line = null;
//					while ((line = reader.readLine()) != null) {
//						sb.append(line);
//					}
//					JSONArray json = new JSONArray('[' + sb.toString() + ']');
//
//					form.setName(json.getJSONObject(0).getString("Name"));
////					form.setDate(Integer.toString(json.getJSONObject(0).getInt("Hour"))
////							+ ":" + Integer.toString(json.getJSONObject(0).getInt("Min"))
////							+ ":" + Integer.toString(json.getJSONObject(0).getInt("Sec")));
//					if(json.getJSONObject(0).getInt("Hour") > 8)
//						person.put("late", person.getInt("late")+1);
//					//updateStat(person);
//						
//					// System.out.println(sb.toString());
//					System.out.println(person.getString("First_Name"));
//					break;
//				}
//			}
//
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
}
