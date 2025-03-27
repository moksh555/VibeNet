package com.social.models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity 
@Table(name="story")
@Data
public class Story {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
		
	private String image;
	
	private String caption;
	
	private LocalDateTime timeStamp;
	
	@ManyToOne
	private User user;
}
