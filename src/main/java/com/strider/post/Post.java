package com.strider.post;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.strider.user.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Post {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Size(max = 777)
	private String text;
	
	@Null
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="post_id")
	@Fetch(FetchMode.JOIN)
	private Post post;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	@Fetch(FetchMode.JOIN)
	private User user;
	
	@NotNull
	private Date publiDate;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private TypeEnum typePost;
	
	
	
}
