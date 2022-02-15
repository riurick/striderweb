package com.strider.follow;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.strider.exception.RegraNegocioException;
import com.strider.service.MessagesService;

@Service
public class FollowService {
	
	private static final String FOLLOW_NOT_ALLOWED = "follow.notAllowed";

	@Autowired
	private MessagesService messages;
	
	@Autowired 
	private FollowRepository repository;
	
	public Follow save (@Valid Follow follow) throws RegraNegocioException {
		if(follow.getUserFollowed().equals(follow.getUserFollowing())) {
			throw new RegraNegocioException(messages.get(FOLLOW_NOT_ALLOWED));
		}
		return repository.save(follow);
	}
	
	public void unfollowing(Integer id) {
		repository.deleteById(id);
	}

	public Boolean isFollowing(Integer idUser, Integer idOther) {
		Optional<Follow> op = repository.findByUserFollowedIdAndUserFollowingId(idUser, idOther);
		return op.isPresent();
	}
}
