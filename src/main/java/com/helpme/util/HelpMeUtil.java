package com.helpme.util;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.helpme.config.HelpMeContants;
import com.helpme.model.LoginBean;
import com.helpme.model.OTPBean;

public class HelpMeUtil {

	public static String generateOTP() {
		Random random = new Random();
		String otp = "" + random.nextInt(HelpMeContants.OTP_SIZE);
		int otpLength = (""+HelpMeContants.OTP_SIZE).length();
		//System.out.println(otp);
		while(otp.length() < otpLength) {
			otp = "0" + otp;
		}
		return otp;
	}

	public static void sendOTP(LoginBean loginBean, String COMPANY_NAME, String PARAM3, String authKey, String url, String templateID) throws UnsupportedEncodingException, JsonProcessingException {
		String smsText = new OTPBean(loginBean.getOtp(), COMPANY_NAME, PARAM3).toJson();
		SMSUtil.sendSMS(HelpMeContants.senderID, loginBean.getMobileno(), loginBean.getOtp(), smsText, authKey, url, templateID);
	}

	public static void main(String[] args) {
		for(int i = 0; i < 1000; i++) {
			System.out.println(generateOTP());
			System.out.println();
		}
	}
}
