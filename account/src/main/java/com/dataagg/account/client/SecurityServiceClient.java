package com.dataagg.account.client;

import com.dataagg.account.domain.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "security", url = "/security")
public interface SecurityServiceClient {
	@RequestMapping(method = RequestMethod.POST, value = "/users", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	void createUser(User user);
}
