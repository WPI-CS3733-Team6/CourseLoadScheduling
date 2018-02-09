package org.dselent.scheduling.server.dto;

import javax.annotation.Generated;

	public class UserInfoUpdateDto
	{
		private final Integer userId;
		private final String currentPassword;
		private final String newPassword;
		private final String newPasswordConfirmed;
		private final String preferredEmail;
		private final Long phoneNum;
		
		@Generated("SparkTools")
		private UserInfoUpdateDto(Builder builder) {
			this.userId = builder.userId;
			this.currentPassword = builder.currentPassword;
			this.newPassword = builder.newPassword;
			this.newPasswordConfirmed = builder.newPasswordConfirmed;
			this.preferredEmail = builder.preferredEmail;
			this.phoneNum = builder.phoneNum;
		}
		/**
		 * Creates builder to build {@link UserInfoUpdateDto}.
		 * @return created builder
		 */
		@Generated("SparkTools")
		public static Builder builder() {
			return new Builder();
		}
		/**
		 * Builder to build {@link UserInfoUpdateDto}.
		 */
		@Generated("SparkTools")
		public static final class Builder {
			private Integer userId;
			private String currentPassword;
			private String newPassword;
			private String newPasswordConfirmed;
			private String preferredEmail;
			private Long phoneNum;

			private Builder() {
			}

			public Builder withUserId(Integer userId) {
				this.userId = userId;
				return this;
			}

			public Builder withCurrentPassword(String currentPassword) {
				this.currentPassword = currentPassword;
				return this;
			}

			public Builder withNewPassword(String newPassword) {
				this.newPassword = newPassword;
				return this;
			}

			public Builder withNewPasswordConfirmed(String newPasswordConfirmed) {
				this.newPasswordConfirmed = newPasswordConfirmed;
				return this;
			}

			public Builder withPreferredEmail(String preferredEmail) {
				this.preferredEmail = preferredEmail;
				return this;
			}

			public Builder withPhoneNum(Long phoneNum) {
				this.phoneNum = phoneNum;
				return this;
			}

			public UserInfoUpdateDto build() {
				return new UserInfoUpdateDto(this);
			}
		}
		public Integer getUserId() {
			return userId;
		}
		public String getCurrentPassword() {
			return currentPassword;
		}
		public String getNewPassword() {
			return newPassword;
		}
		public String getNewPasswordConfirmed() {
			return newPasswordConfirmed;
		}
		public String getPreferredEmail() {
			return preferredEmail;
		}
		public Long getPhoneNum() {
			return phoneNum;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((currentPassword == null) ? 0 : currentPassword.hashCode());
			result = prime * result + ((newPassword == null) ? 0 : newPassword.hashCode());
			result = prime * result + ((newPasswordConfirmed == null) ? 0 : newPasswordConfirmed.hashCode());
			result = prime * result + ((phoneNum == null) ? 0 : phoneNum.hashCode());
			result = prime * result + ((preferredEmail == null) ? 0 : preferredEmail.hashCode());
			result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
			UserInfoUpdateDto other = (UserInfoUpdateDto) obj;
			if (currentPassword == null) {
				if (other.currentPassword != null)
					return false;
			} else if (!currentPassword.equals(other.currentPassword))
				return false;
			if (newPassword == null) {
				if (other.newPassword != null)
					return false;
			} else if (!newPassword.equals(other.newPassword))
				return false;
			if (newPasswordConfirmed == null) {
				if (other.newPasswordConfirmed != null)
					return false;
			} else if (!newPasswordConfirmed.equals(other.newPasswordConfirmed))
				return false;
			if (phoneNum == null) {
				if (other.phoneNum != null)
					return false;
			} else if (!phoneNum.equals(other.phoneNum))
				return false;
			if (preferredEmail == null) {
				if (other.preferredEmail != null)
					return false;
			} else if (!preferredEmail.equals(other.preferredEmail))
				return false;
			if (userId == null) {
				if (other.userId != null)
					return false;
			} else if (!userId.equals(other.userId))
				return false;
			return true;
		}
		@Override
		public String toString() {
			return "UserInfoUpdateDto [userId=" + userId + ", currentPassword=" + currentPassword + ", newPassword="
					+ newPassword + ", newPasswordConfirmed=" + newPasswordConfirmed + ", preferredEmail="
					+ preferredEmail + ", phoneNum=" + phoneNum + "]";
		}
	}