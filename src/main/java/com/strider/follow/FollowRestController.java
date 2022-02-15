package com.strider.follow;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.strider.exception.RegraNegocioException;
import com.strider.response.ServiceMessage;
import com.strider.response.ServiceResponse;
import com.strider.service.MessagesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/follow")
@Api(value = "Follow")
public class FollowRestController {
	
	private static final String CREATED = "global.success";
	private static final String DELETED = "global.deleted";

	@Autowired
	private FollowService service;
	
	@Autowired
	private MessagesService messages;
	
	@PostMapping
	@ApiOperation(value = "Following user")
	public ResponseEntity<ServiceResponse<Follow>> save(@RequestBody @Valid Follow follow) throws RegraNegocioException {
		
		follow = service.save(follow);
		HttpHeaders headers = new HttpHeaders();
		ServiceMessage message = new ServiceMessage(messages.get(CREATED));

		return new ResponseEntity<>(new ServiceResponse<>(follow, message), headers, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Unfollowing user")
	public ResponseEntity<ServiceResponse<Void>> delete(@PathVariable Integer id) {
		service.unfollowing(id);
		ServiceMessage message = new ServiceMessage(messages.get(DELETED));

		return new ResponseEntity<>(new ServiceResponse<>(message), HttpStatus.OK);
	}

	
}
