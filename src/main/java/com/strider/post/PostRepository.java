package com.strider.post;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Integer>{

	@Query("Select p from Post p where p.user.id = :id and p.publiDate = :publiDate ")
	List<Post> findByUserIdAndPubliDate(Integer id, Date publiDate);
	
	@Query("Select p from Post p "
			+ " join Follow f on f.userFollowing.id = p.user.id "
			+ " where p.user.id = :idUser ")
	List<Post> findFollowingPostsByUserId(Integer idUser);

	List<Post> findByUserId(Integer id);
}
