package com.helpme.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import com.helpme.config.HelpMeContants;

public class SMSUtil {
	private static final Logger logger = LogManager.getLogger(SMSUtil.class);
	public static void sendSMS(String senderID, String mobileno, String otp, String smsText, String authKey, String url, String templateID) throws UnsupportedEncodingException {
		RestTemplate restTemplate = new RestTemplate();
		String uri = "https://api.msg91.com/api/v5/otp?authkey="+authKey+"&template_id="+templateID+"&extra_param="+ URLEncoder.encode(smsText, HelpMeContants.UTF8) +"&mobile="+ mobileno + "&invisible=1&otp="+otp;
		logger.debug(uri);
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-type", "application/json");
		HttpEntity<String> entity = new HttpEntity<>("body", headers);
	    String result = restTemplate.postForObject(uri, entity, String.class);
	    logger.info(mobileno + ":" + result);
	}

}
