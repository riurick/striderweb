package com.strider.follow;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Integer>{

	Optional<Follow> findByUserFollowedIdAndUserFollowingId(Integer idUser, Integer idOther);
	
}
