package com.helpme.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

public class NotificationUtil {
	private static final Logger logger = LogManager.getLogger(NotificationUtil.class);

	public static void sendNotification(String title, String text, String type, String url, List<String> deviceTokens) {
		String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";
		//String FIREBASE_SERVER_KEY = "AIzaSyAVKS3gwj7SXup_fTJJHwDj5SzL3BDM5Xc";
		String FIREBASE_SERVER_KEY = "AAAAzEzQ-xs:APA91bG7IB9EPKGp5rIIcVunwsav3IQgCTcAuLpnW7L3mL2Csk704ut1A00K01HL5UrKz1o51WGZ_qODxRj0_e1NyB2d1qKQIFWzpLaMdQEOt-UnKNRsRqxgZB0Uo6RGinpN1WnCJTDI";
		RestTemplate restTemplate = new RestTemplate();
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		JSONObject notification = new JSONObject();
		JSONArray arr = new JSONArray();

		arr.addAll(deviceTokens); 
		logger.debug(arr.toJSONString());
		try {
			data.put("time", new Date());	
			data.put("type", type);
			data.put("url", url);
			notification.put("body", text); //"Visitor scheduled for your society"
			notification.put("title", title); //"Schedule Notification"
			json.put("registration_ids",arr); //json.put("to",toStr);
			json.put("data", data);
			json.put("notification", notification);
			//json.put("android_channel_id", "SPQueue");
			logger.debug(json.toString());
			HttpHeaders headers = new HttpHeaders();
			String contentTypeHeader = MediaType.APPLICATION_JSON_VALUE;
			String acceptHeader = MediaType.APPLICATION_JSON_VALUE;
			headers.add("Content-Type", contentTypeHeader);
			headers.add("Accept", acceptHeader);
			headers.add("Authorization", "key=" + FIREBASE_SERVER_KEY);
			HttpEntity<String> entity = new HttpEntity<String>(json.toString(), headers);
			ResponseEntity<String> response = restTemplate.postForEntity(FIREBASE_API_URL, entity , String.class);
			logger.debug(response.toString());

		}catch (Exception e) {
			logger.error("Exception", e);
		}
	}
	
	public static void main(String[] args) {
		List<String> deviceTokens = new ArrayList<String>();
		deviceTokens.add("eCv8RBQzQh-ETH-EwIFTOr:APA91bE9dN6PWlJVZhgN7_cjDMrPr1svSWFiyN5dnMHaNBCOIlIh_1FVXdJpXbPMU74QrKZXoG_1sItIGqMntEqMWFQF1LV6Z-HixOsT1p-yYkmk1q_68I2tjbJ4E-RBb9Y10s9QUe-H");
		sendNotification("Title", "Sample Notification", "ServiceProvider", "https://app.helpmeplease.in/myrequest", deviceTokens);
	}
}
