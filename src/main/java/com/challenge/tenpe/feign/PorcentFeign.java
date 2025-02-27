package com.challenge.tenpe.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.challenge.tenpe.dto.PercentDto;

@FeignClient(name = "percent", url = "${percent.url}")
public interface PorcentFeign {
	
	@GetMapping("${percent.endpoint}")
	PercentDto getPercent();
}
