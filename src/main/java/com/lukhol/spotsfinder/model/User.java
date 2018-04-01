package com.lukhol.spotsfinder.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@JsonIgnoreProperties({"places"})
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "myNative")
	@Column(name="user_id")
	private Long id;
	@Email
	@Column(unique = true)
	private String email;
	@Column(nullable = true)
	private String facebookId;
	@Column(nullable = true)
	private String googleId;
	private String password;
	@NotEmpty(message="Imie nie mo¿e byæ puste.")
	private String firstname;
	@NotEmpty(message="Nazwisko nie mo¿e byæ puste.")
	private String lastname;
	private boolean isActive;
	private String avatarUrl;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		foreignKey = @ForeignKey(name = "user_in_role"), 
		inverseForeignKey = @ForeignKey(name = "role_assigned_to_user")
	)
	private List<Role> roles;
	
	@JsonIgnore
	@OneToMany(mappedBy="owner")
	private List<Place> places;
}
