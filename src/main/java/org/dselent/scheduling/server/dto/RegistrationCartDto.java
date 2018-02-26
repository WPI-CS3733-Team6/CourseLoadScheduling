package org.dselent.scheduling.server.dto;

import javax.annotation.Generated;

public class RegistrationCartDto {
	
	private final String course_name;
	private final String course_num;
	private final Integer instanceId;
	private final String term;
	@Generated("SparkTools")
	private RegistrationCartDto(Builder builder) {
		this.course_name = builder.course_name;
		this.course_num = builder.course_num;
		this.instanceId = builder.instanceId;
		this.term = builder.term;
	}
	/**
	 * Creates builder to build {@link RegistrationCartDto}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}
	/**
	 * Builder to build {@link RegistrationCartDto}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private String course_name;
		private String course_num;
		private Integer instanceId;
		private String term;

		private Builder() {
		}

		public Builder withCourse_name(String course_name) {
			this.course_name = course_name;
			return this;
		}

		public Builder withCourse_num(String course_num) {
			this.course_num = course_num;
			return this;
		}

		public Builder withInstanceId(Integer instanceId) {
			this.instanceId = instanceId;
			return this;
		}

		public Builder withTerm(String term) {
			this.term = term;
			return this;
		}

		public RegistrationCartDto build() {
			return new RegistrationCartDto(this);
		}
	}
	public String getCourse_name() {
		return course_name;
	}
	public String getCourse_num() {
		return course_num;
	}
	public Integer getInstanceId() {
		return instanceId;
	}
	public String getTerm() {
		return term;
	}
	@Override
	public String toString() {
		return "RegistrationCartDto [course_name=" + course_name + ", course_num=" + course_num + ", instanceId="
				+ instanceId + ", term=" + term + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((course_name == null) ? 0 : course_name.hashCode());
		result = prime * result + ((course_num == null) ? 0 : course_num.hashCode());
		result = prime * result + ((instanceId == null) ? 0 : instanceId.hashCode());
		result = prime * result + ((term == null) ? 0 : term.hashCode());
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
		RegistrationCartDto other = (RegistrationCartDto) obj;
		if (course_name == null) {
			if (other.course_name != null)
				return false;
		} else if (!course_name.equals(other.course_name))
			return false;
		if (course_num == null) {
			if (other.course_num != null)
				return false;
		} else if (!course_num.equals(other.course_num))
			return false;
		if (instanceId == null) {
			if (other.instanceId != null)
				return false;
		} else if (!instanceId.equals(other.instanceId))
			return false;
		if (term == null) {
			if (other.term != null)
				return false;
		} else if (!term.equals(other.term))
			return false;
		return true;
	}
	
	
	
}
