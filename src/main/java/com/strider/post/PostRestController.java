package com.strider.post;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.strider.exception.RegraNegocioException;
import com.strider.response.ServiceMessage;
import com.strider.response.ServiceResponse;
import com.strider.service.MessagesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/post")
@Api(value = "Post")
public class PostRestController {
	
	private static final String CREATED = "post.created";
	
	@Autowired
	private PostService service;
	
	@Autowired
	private MessagesService messages;
	
	@PostMapping
	@ApiOperation(value = "Create post")
	public ResponseEntity<ServiceResponse<Post>> save(@RequestBody @Valid Post post) throws RegraNegocioException {

		post = service.save(post);

		HttpHeaders headers = new HttpHeaders();

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId())
				.toUri();
		headers.setLocation(location);

		ServiceMessage message = new ServiceMessage(messages.get(CREATED));

		return new ResponseEntity<>(new ServiceResponse<>(post, message), headers, HttpStatus.CREATED);
	}
}
