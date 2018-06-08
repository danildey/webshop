package com.epam.preprod.entity;

public class User {
	private int id;

	private String password;

	private String firstName;

	private String lastName;

	private boolean isSignUp;

	private String email;

	private String userAvatarExtension;

	public String getUserAvatarExtension() {
		return userAvatarExtension;
	}

	public void setUserAvatarExtension(String userAvatarExtension) {
		this.userAvatarExtension = userAvatarExtension;
	}

	public boolean isSignUp() {
		return isSignUp;
	}

	public void setSignUp(boolean signUp) {
		isSignUp = signUp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", password='" + password + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", isSignUp=" + isSignUp +
				", email='" + email + '\'' +
				", userAvatarExtension='" + userAvatarExtension + '\'' +
				'}';
	}
}
