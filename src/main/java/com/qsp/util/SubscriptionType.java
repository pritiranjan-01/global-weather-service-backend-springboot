package com.qsp.util;

import com.qsp.exception.custom.InvalidSubscriptionException;

public enum SubscriptionType {
	GO, PRO, MAX;

	public static SubscriptionType getUserSubscriptionType(Integer ordinal) {
		switch (ordinal) {
		case 0:
			return GO;
		case 1:
			return PRO;
		case 2:
			return MAX;
		default:
			throw new InvalidSubscriptionException("Invalid Subscription code. Kindly choose between 0(PRO),1(MAX),2(GO).");
		}
	}

	public static String[] getAllTypes() {
		return new String[] { GO.name(), PRO.name(), MAX.name() };
	}
	
	public static String getSubscriptionDetails(SubscriptionType type) {
		return switch (type) {
	        case GO  -> "You will receive daily <strong>local weather updates</strong> every morning at <strong>6 AM</strong>.";
	        case PRO -> "You will receive <strong>local and international weather updates</strong> every morning at <strong>6 AM</strong>.";
	        case MAX -> "You will receive <strong>local and international weather updates twice a day</strong>, at <strong>6 AM and 6 PM</strong>.";
	        default  -> "Your subscription details are currently unavailable.";
	    };
	}
}