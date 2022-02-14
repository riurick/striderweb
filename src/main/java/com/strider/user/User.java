package com.strider.user;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull
	@Size(max = 14)
	private String username;
	
	@NotNull
	private Date joinedPosterr;
	
	@NotNull
	private Integer nFollowers;
	
	@NotNull
	private Integer nFollowing;
	
	@NotNull
	private Integer nPosts;
	
	
}
