package com.qsp.util;

import com.qsp.exception.custom.InvalidSubscriptionException;

public enum SubscriptionType {
	GO, PRO, MAX;

	public static SubscriptionType getUserSubscriptionType(Integer ordinal) {
		switch (ordinal) {
		case 0:
			return PRO;
		case 1:
			return MAX;
		case 2:
			return GO;
		default:
			throw new InvalidSubscriptionException("Invalid Subscription code");
		}
	}

	public static String[] getAllTypes() {
		return new String[] { GO.name(), PRO.name(), MAX.name() };
	}
}