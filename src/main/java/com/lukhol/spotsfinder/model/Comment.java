package com.lukhol.spotsfinder.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends Auditable<User> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myNative")
	private Long id;
	
	@Column(nullable = false, length = 65535, columnDefinition = "TEXT")
	private String content;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(foreignKey = @ForeignKey(name = "comment_to_place"))
	private Place place;
	
	private Boolean deleted;
}