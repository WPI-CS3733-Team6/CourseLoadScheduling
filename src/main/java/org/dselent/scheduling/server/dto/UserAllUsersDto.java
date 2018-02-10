package org.dselent.scheduling.server.dto;

import javax.annotation.Generated;

/**
 * DTO = Data Transfer Object
 * Used to package/wrap several variables into a single object
 * Uses the Builder pattern for object instantiation
 * 
 * @author dselent
 *
 */
public class UserAllUsersDto
{
	private final Integer userId;
	private final String userName;
	private final String firstName;
	private final String lastName;
	private final String email;
	private final String secondaryEmail;
	private final Long phoneNum;
	private final Float reqCourses;
	
	@Generated("SparkTools")
	private UserAllUsersDto(Builder builder) {
		this.userId = builder.userId;
		this.userName = builder.userName;
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.email = builder.email;
		this.secondaryEmail = builder.secondaryEmail;
		this.phoneNum = builder.phoneNum;
		this.reqCourses = builder.reqCourses;
	}
	/**
	 * Creates builder to build {@link UserInfoDto}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}
	/**
	 * Builder to build {@link UserInfoDto}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private Integer userId;
		private String userName;
		private String firstName;
		private String lastName;
		private String email;
		private String secondaryEmail;
		private Long phoneNum;
		private Float reqCourses;

		private Builder() {
		}

		public Builder withUserId(Integer userId) {
			this.userId = userId;
			return this;
		}

		public Builder withUserName(String userName) {
			this.userName = userName;
			return this;
		}

		public Builder withFirstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public Builder withLastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public Builder withEmail(String email) {
			this.email = email;
			return this;
		}

		public Builder withSecondaryEmail(String secondaryEmail) {
			this.secondaryEmail = secondaryEmail;
			return this;
		}

		public Builder withPhoneNum(Long phoneNum) {
			this.phoneNum = phoneNum;
			return this;
		}

		public Builder withReqCourses(Float reqCourses) {
			this.reqCourses = reqCourses;
			return this;
		}

		public UserAllUsersDto build() {
			return new UserAllUsersDto(this);
		}
	}
	public Integer getUserId() {
		return userId;
	}
	public String getUserName() {
		return userName;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getEmail() {
		return email;
	}
	public String getSecondaryEmail() {
		return secondaryEmail;
	}
	public Long getPhoneNum() {
		return phoneNum;
	}
	public Float getReqCourses() {
		return reqCourses;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((phoneNum == null) ? 0 : phoneNum.hashCode());
		result = prime * result + ((reqCourses == null) ? 0 : reqCourses.hashCode());
		result = prime * result + ((secondaryEmail == null) ? 0 : secondaryEmail.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserAllUsersDto other = (UserAllUsersDto) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (phoneNum == null) {
			if (other.phoneNum != null)
				return false;
		} else if (!phoneNum.equals(other.phoneNum))
			return false;
		if (reqCourses == null) {
			if (other.reqCourses != null)
				return false;
		} else if (!reqCourses.equals(other.reqCourses))
			return false;
		if (secondaryEmail == null) {
			if (other.secondaryEmail != null)
				return false;
		} else if (!secondaryEmail.equals(other.secondaryEmail))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "UserInfoDto [userId=" + userId + ", userName=" + userName + ", firstName=" + firstName + ", lastName="
				+ lastName + ", email=" + email + ", secondaryEmail=" + secondaryEmail + ", phoneNum=" + phoneNum
				+ ", reqCourses=" + reqCourses + "]";
	}
	
}