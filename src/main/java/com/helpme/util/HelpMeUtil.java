package com.helpme.util;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.helpme.config.HelpMeContants;
import com.helpme.model.LoginBean;
import com.helpme.model.OTPBean;

public class HelpMeUtil {

	private static final double EARTH_RADIUS = 6371 * 1000; //metres
	
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
	
	
	/**
	 * Haversine Algo
	 * @param lat1
	 * @param long1
	 * @param lat2
	 * @param long2
	 * @return
	 */
	public static double getDistance(double lat1, double long1,
			double lat2, double long2)
	{

		//		var φ1 = lat1.toRadians();
		//		var φ2 = lat2.toRadians();
		double gamma1 = Math.toRadians(lat1);
		double gamma2 = Math.toRadians(lat2);

		//		var Δφ = (lat2-lat1).toRadians();
		//		var Δλ = (lon2-lon1).toRadians();
		double theta  = Math.toRadians(lat2-lat1);
		double lambda = Math.toRadians(long2-long1);

		//var a = Math.sin(Δφ/2) * Math.sin(Δφ/2) +
		//      Math.cos(φ1) * Math.cos(φ2) *
		//      Math.sin(Δλ/2) * Math.sin(Δλ/2);

		double a =	Math.sin(theta/2) * Math.sin(theta/2) +
				Math.cos(gamma1) * Math.cos(gamma2) *
				Math.sin(lambda/2) * Math.sin(lambda/2);

		//var c  = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

		//var d = R * c;
		double d = EARTH_RADIUS * c;
		return d;
	}
}
