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

	public static void main(String[] args)  {
		SpringApplication.run(TefasApiApplication.class, args);
	}

}
