package com.lukhol.spotsfinder.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@org.hibernate.annotations.Immutable
@org.hibernate.annotations.Subselect(
	value = "SELECT u.user_id as USERID, count(wpr.wrong_place_report_id) as NUMBEROFREPORTS " +
			"FROM user u INNER JOIN wrong_place_report wpr on u.user_id = wpr.user_user_id"
)
@org.hibernate.annotations.Synchronize({"User", "WrongPlaceReport"})
public class UserReport {

	@Id
	protected Long userId;
	
	protected long numberOfReports;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public long getNumberOfReports() {
		return numberOfReports;
	}

	public void setNumberOfReports(long numberOfReports) {
		this.numberOfReports = numberOfReports;
	}
}
