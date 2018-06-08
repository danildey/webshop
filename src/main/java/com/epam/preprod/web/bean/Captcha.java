package com.epam.preprod.web.bean;

import java.util.Date;
import java.util.UUID;

public class Captcha {
	private UUID id;
	private String value;
	private long lifetime;

	public Captcha(UUID id, String value, long lifetime) {
		this.id = id;
		this.value = value;
		this.lifetime = new Date().getTime() + lifetime;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public long getLifetime() {
		return lifetime;
	}

	public void setLifetime(long lifetime) {
		this.lifetime = lifetime;
	}

	@Override
	public String toString() {
		return "Captcha{" +
				"id=" + id +
				", value='" + value + '\'' +
				", lifetime=" + lifetime +
				'}';
	}
}
