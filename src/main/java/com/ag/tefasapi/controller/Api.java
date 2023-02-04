package com.ag.tefasapi.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fund")
public class Api {

	public static final String TEFAS_URL_FON_ANALIZ = "https://www.tefas.gov.tr/FonAnaliz.aspx?FonKod=";

	@GetMapping("{fundCode}")
	public String getFundLatestPrice(@PathVariable String fundCode) throws IOException {
		var url = new URL(TEFAS_URL_FON_ANALIZ + fundCode);
		var con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");

		int responseCode = con.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
		String lastPrice = "unknown";
		if(responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			String[] split = response.toString().split("Son Fiyat");
			lastPrice = StringUtils.substringBetween(split[1], "<span>", "</span>");
			System.out.println(lastPrice);
		}
		return lastPrice;
	}

	@GetMapping
	public void getFundLatestPrice() {
		System.out.println("test");
	}
}
