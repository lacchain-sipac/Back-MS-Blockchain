package com.invest.honduras.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.invest.honduras.request.RequestDocumentStep;
import com.invest.honduras.request.RequestHasRoleUser;
import com.invest.honduras.request.RequestHasRoleUserProject;
import com.invest.honduras.request.RequestStep;
import com.invest.honduras.request.RequestUpdateUser;
import com.invest.honduras.request.RequestUser;
import com.invest.honduras.request.RequestUserCap;
import com.invest.honduras.request.RequestUserRoleProject;
import com.invest.honduras.response.ResponseGeneral;
import com.invest.honduras.response.ResponseGeneralBool;
import com.invest.honduras.response.ResponseGeneralDocument;
import com.invest.honduras.response.ResponseGeneralProject;
import com.invest.honduras.service.DigitalIdentityService;
import com.invest.honduras.service.StepManagerService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController()
@RequestMapping("/api/v1/blockchain")
@Slf4j
public class BlockchainController implements ApiBlockchainController {

	@Autowired
	StepManagerService service;

	@Autowired
	DigitalIdentityService digitalService;

	@PostMapping(value = "/project", produces = { MediaType.APPLICATION_STREAM_JSON_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	@Override
	public Mono<ResponseEntity<ResponseGeneral>> initProject(@RequestBody RequestStep request) {

		return service.initProject(request).map(data -> ResponseEntity.ok(data));
	}

	@Override
	@GetMapping(value = "/project", produces = { MediaType.APPLICATION_STREAM_JSON_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public Mono<ResponseEntity<ResponseGeneralProject>> getProject(@RequestParam String hashProject) {

		return service.getProject(hashProject).map(data -> ResponseEntity.ok(data));

	}

	@Override
	@GetMapping(value = "/comment", produces = { MediaType.APPLICATION_STREAM_JSON_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public Mono<ResponseEntity<ResponseGeneralDocument>> getDocument(@RequestParam String hashProject,
			@RequestParam String typeDocument, @RequestParam String hash) {

		return service.getDocument(hashProject, typeDocument, hash).map(data -> ResponseEntity.ok(data));

	}

	@Override
	@GetMapping(value = "/document", produces = { MediaType.APPLICATION_STREAM_JSON_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public Mono<ResponseEntity<ResponseGeneralDocument>> getComment(@RequestParam String hashProject,
			@RequestParam String typeDocument, @RequestParam String hash) {

		return service.getComment(hashProject, typeDocument, hash).map(data -> ResponseEntity.ok(data));

	}

	@Override
	@PutMapping("/project")
	public Mono<ResponseEntity<ResponseGeneral>> changeStep(@RequestBody RequestStep request) {

		return service.changeStep(request).map(data -> ResponseEntity.ok(data));

	}

	@Override
	@PostMapping(value = "/document", produces = { MediaType.APPLICATION_STREAM_JSON_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public Mono<ResponseEntity<ResponseGeneral>> addDocumentStep(@RequestBody RequestDocumentStep request) {

		return service.addDocumentStep(request).map(data -> ResponseEntity.ok(data));

	}

	@Override
	@PostMapping(value = "/user", produces = { MediaType.APPLICATION_STREAM_JSON_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public Mono<ResponseEntity<ResponseGeneral>> addUser(@RequestBody RequestUser userRequest) {
		log.info("addUser :{}" , new Gson().toJson(userRequest));
		return digitalService.addUser(userRequest).map(data -> ResponseEntity.ok(data));

	}

	@Override
	@PutMapping("/user")
	public Mono<ResponseEntity<ResponseGeneral>> updateUser(@RequestBody RequestUpdateUser userRequest) {
		log.info("updateUser :{}" , new Gson().toJson(userRequest));
		return digitalService.updateUser(userRequest).map(data -> ResponseEntity.ok(data));
	}

	@Override
	@PutMapping("/user-cap")
	public Mono<ResponseEntity<ResponseGeneral>> setCap(@RequestBody RequestUserCap userRequest) {

		return digitalService.setCap(userRequest).map(data -> ResponseEntity.ok(data));

	}

	@Override
	@PostMapping(value = "/user-cap", produces = { MediaType.APPLICATION_STREAM_JSON_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public Mono<ResponseEntity<ResponseGeneralBool>> checkCap(@RequestBody RequestUserCap userRequest) {

		return digitalService.checkCap(userRequest).map(data -> ResponseEntity.ok(data));

	}

	@Override
	@GetMapping(value = "/user-role", produces = { MediaType.APPLICATION_STREAM_JSON_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public Mono<ResponseEntity<ResponseGeneralBool>> hasRoleUser(@RequestParam String role, @RequestParam String proxy) {
		RequestHasRoleUser request = new RequestHasRoleUser();
		request.setRole(role);
		request.setUserProxyAddress(proxy);
		return digitalService.hasRoleUser(request).map(data -> ResponseEntity.ok(data));

	}

	@Override
	@GetMapping(value = "/user-role-project", produces = { MediaType.APPLICATION_STREAM_JSON_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public Mono<ResponseEntity<ResponseGeneralBool>> hasRoleUserProject(@RequestParam String role,
			@RequestParam String proxy, @RequestParam String project) {
		RequestHasRoleUserProject request = new RequestHasRoleUserProject();
		request.setRole(role);
		request.setUserProxyAddress(proxy);
		request.setProjectCodeHash(project);
		return digitalService.hasRoleUserProject(request).map(data -> ResponseEntity.ok(data));

	}

	@Override
	@PostMapping(value = "/role-project", produces = { MediaType.APPLICATION_STREAM_JSON_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public Mono<ResponseEntity<ResponseGeneral>> addUserRoleProject(@RequestBody RequestUserRoleProject request) {

		return digitalService.setRoleUserProject(request).map(data -> ResponseEntity.ok(data));

	}

	@Override
	@PutMapping(value = "/role-project")
	public Mono<ResponseEntity<ResponseGeneral>> removeUserRoleProject(@RequestBody RequestUserRoleProject request) {

		return digitalService.removeRoleUserProject(request).map(data -> ResponseEntity.ok(data));

	}
}
