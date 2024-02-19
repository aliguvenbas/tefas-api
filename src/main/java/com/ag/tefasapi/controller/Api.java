package com.ag.tefasapi.controller;

import com.ag.tefasapi.controller.dto.FundDto;
import com.ag.tefasapi.repository.FundRepository;
import com.ag.tefasapi.repository.model.Fund;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fund")
public class Api {

	public static final String TEFAS_URL_FON_ANALIZ = "https://www.tefas.gov.tr/FonAnaliz.aspx?FonKod=";
	@Autowired
	private FundRepository fundRepository;

	@GetMapping("{fundCode}")
	public FundDto getFundLatestPrice(@PathVariable String fundCode) throws IOException {
		var url = new URL(TEFAS_URL_FON_ANALIZ + fundCode);
		var con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");

		String lastPrice = "unknown";

		Fund fund1 = fundRepository.get(fundCode);
		System.out.println("fund1:" + fund1);
		if(fund1 != null && !fund1.equals("unknown")) {
			System.out.println("fetched from db");
		} else {
			int responseCode = con.getResponseCode();
			System.out.println("GET Response Code :: " + responseCode);
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

			fund1 = new Fund(fundCode, lastPrice, fundCode, "Azimut Portfoy");
			fundRepository.save(fund1);
		}

		return new FundDto(fund1.getLastPrice());
	}

	@GetMapping
	public void getFundLatestPrice() {
		System.out.println("test");
	}
}
