package org.salephoto.models;

import java.io.Serializable;


public class Location implements Serializable {
	private static final long serialVersionUID = 1L;

	private double latitude;
	private double longitude;

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(final double newLatitude) {
		latitude = newLatitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(final double newLongitude) {
		longitude = newLongitude;
	}

}
