package com.polibuda.pbl.model.listeners;

import java.util.Calendar;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.polibuda.pbl.model.Place;

public class PlaceEntityListener {
	@PreUpdate
	@PrePersist
	public void updateVersion(Place place){
		Calendar calendar = Calendar.getInstance();
		place.setVersion(calendar.getTimeInMillis());
	}
}
