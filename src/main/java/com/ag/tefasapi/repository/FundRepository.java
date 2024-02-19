package com.ag.tefasapi.repository;

import com.ag.tefasapi.repository.model.Fund;
import java.util.HashMap;
import java.util.Map;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public class FundRepository {

	private static final Map<String, Fund> fundRepo = new HashMap<>();

	@CachePut(value = "fund", key = "#result.id")
	public Fund save(Fund fund) {
		System.out.println("Fund saved: " + fund.getId() + " Last price: " + fund.getLastPrice());
		fundRepo.putIfAbsent(fund.getId(), fund);
		return fund;
	}

	@Cacheable(value = "fund", key = "#id", unless="#result == null")
	public Fund get(String id) {
		System.out.println("Get last price of fund: " + id);
		return fundRepo.get(id);
	}
}
