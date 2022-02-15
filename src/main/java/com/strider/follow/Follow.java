package com.strider.follow;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.strider.user.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Follow {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id")
	@Fetch(FetchMode.JOIN)
	private User userFollowed;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id")
	@Fetch(FetchMode.JOIN)
	private User userFollowing;
	
}
