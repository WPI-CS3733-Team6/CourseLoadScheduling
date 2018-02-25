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
public class RegisterUserDto
{
	private final String userName;
	private final String firstName;
	private final String lastName;
	private final String email;
	private final Long phoneNum;
	
	@Generated("SparkTools")
	private RegisterUserDto(Builder builder) {
		this.userName = builder.userName;
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.email = builder.email;
		this.phoneNum = builder.phoneNum;
	}
	/**
	 * Creates builder to build {@link RegisterUserDto}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}
	/**
	 * Builder to build {@link RegisterUserDto}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private String userName;
		private String firstName;
		private String lastName;
		private String email;
		private Long phoneNum;

		private Builder() {
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

		public Builder withPhoneNum(Long phoneNum) {
			this.phoneNum = phoneNum;
			return this;
		}

		public RegisterUserDto build() {
			return new RegisterUserDto(this);
		}
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
	public Long getPhoneNum() {
		return phoneNum;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((phoneNum == null) ? 0 : phoneNum.hashCode());
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
		RegisterUserDto other = (RegisterUserDto) obj;
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
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "RegisterUserDto [userName=" + userName + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", phoneNum=" + phoneNum + "]";
	}
	
	
	
	
}
