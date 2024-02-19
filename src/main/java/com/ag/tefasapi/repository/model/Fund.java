package com.ag.tefasapi.repository.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class Fund implements Serializable {
	private final String id;
	private final String lastPrice;
	private final String name; // original name
	private final String portfolio;
}
