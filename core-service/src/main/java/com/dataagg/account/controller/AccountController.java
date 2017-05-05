package com.dataagg.account.controller;

import com.dataagg.commons.domain.EAccount;
import com.dataagg.commons.domain.EUser;
import com.dataagg.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
public class AccountController {

	@Autowired
	private AccountService accountService;

	@PreAuthorize("#oauth2.hasScope('server') or #name.equals('demo')")
	@RequestMapping(path = "/{name}", method = RequestMethod.GET)
	public EAccount getAccountByName(@PathVariable String name) {
		return accountService.findByName(name);
	}

	@RequestMapping(path = "/current", method = RequestMethod.GET)
	public EAccount getCurrentAccount(Principal principal) {
		return accountService.findByName(principal.getName());
	}

	@RequestMapping(path = "/current", method = RequestMethod.PUT)
	public void saveCurrentAccount(Principal principal, @Valid @RequestBody EAccount account) {
		accountService.saveAccount(principal.getName(), account);
	}

	@RequestMapping(path = "/", method = RequestMethod.POST)
	public EAccount createNewAccount(@Valid @RequestBody EUser user) {
		return accountService.create(user);
	}
}
