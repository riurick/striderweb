package com.strider.post;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.strider.exception.RegraNegocioException;
import com.strider.service.MessagesService;

@Service
public class PostService {

	private static final int MAX_DAILY = 4;
	private static final String NOT_ALLOWED = "post.notAllowed";
	
	@Autowired
	private MessagesService messages;
	
	@Autowired
	private PostRepository repository;
	
	
	public Post save (@Valid Post post) throws RegraNegocioException {
		List<Post> list = repository.findByUserIdAndPubliDate(post.getUser().getId(), post.getPubliDate());
		if(list.size() > MAX_DAILY) {
			throw new RegraNegocioException(messages.get(NOT_ALLOWED));
		}
		return repository.save(post);
	}
	
	public List<Post> all() {
		return repository.findAll();
	}
	
	public List<Post> listByFollowing(Integer userId) {
		return repository.findFollowingPostsByUserId(userId);
	}
}
