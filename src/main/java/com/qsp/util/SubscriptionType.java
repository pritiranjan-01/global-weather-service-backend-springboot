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
}