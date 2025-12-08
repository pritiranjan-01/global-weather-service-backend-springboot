package com.qsp.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientSubscriptionUpdateEvent {
	private String name;
	private String email;
	private Integer subscriptionCode;
}
