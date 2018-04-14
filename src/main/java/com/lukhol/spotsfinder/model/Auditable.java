package com.lukhol.spotsfinder.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable<U> {
	
	@CreatedBy
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	protected U createdBy;
	
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition="DATETIME(3)", updatable = false)
	protected Date creationDate;
	
	@LastModifiedBy
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	protected U lastModifiedBy;
	
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition="DATETIME(3)")
    protected Date lastModifiedDate;
}