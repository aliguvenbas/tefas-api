package com.ag.tefasapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.commons.lang3.StringUtils;

@SpringBootApplication
public class TefasApiApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(TefasApiApplication.class, args);

		var fonCode ="GSP";
		var url = new URL("https://www.tefas.gov.tr/FonAnaliz.aspx?FonKod="+fonCode);
		var con = (HttpURLConnection)url.openConnection();
		con.setRequestMethod("GET");

		int responseCode = con.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			String[] split = response.toString().split("Son Fiyat");
			String lastPrice = StringUtils.substringBetween(split[1], "<span>", "</span>");
			System.out.println(lastPrice);
		}
	}

}
