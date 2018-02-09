package org.dselent.scheduling.server.dto;

public class RegistrationCartDto {
	
	private final String course_name;
	private final String course_num;
	private final Integer status;
	private final String term;
	private final String dept_name;
	
	private RegistrationCartDto(Builder builder) {
		
		this.course_name = builder.course_name;
		this.course_num = builder.course_num;
		this.status = builder.status;
		this.term = builder.term;
		this.dept_name = builder.dept_name;
		
		if(this.course_name == null)
		{
			throw new IllegalStateException("course_name cannot be null");
		}
		if(this.course_num == null)
		{
			throw new IllegalStateException("course_num cannot be null");
		}
		if(this.status == null)
		{
			throw new IllegalStateException("status cannot be null");
		}
		if(this.term == null)
		{
			throw new IllegalStateException("term cannot be null");
		}
		if(this.dept_name == null)
		{
			throw new IllegalStateException("dept_name cannot be null");
		}
		
	}

	public String getCourse_name() {
		return course_name;
	}

	public String getCourse_num() {
		return course_num;
	}

	public Integer getStatus() {
		return status;
	}

	public String getTerm() {
		return term;
	}

	public String getDept_name() {
		return dept_name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((course_name == null) ? 0 : course_name.hashCode());
		result = prime * result + ((course_num == null) ? 0 : course_num.hashCode());
		result = prime * result + ((dept_name == null) ? 0 : dept_name.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		if (dept_name == null) {
			if (other.dept_name != null)
				return false;
		} else if (!dept_name.equals(other.dept_name))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (term == null) {
			if (other.term != null)
				return false;
		} else if (!term.equals(other.term))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RegistrationCartDto [course_name=" + course_name + ", course_num=" + course_num + ", status=" + status
				+ ", term=" + term + ", dept_name=" + dept_name + "]";
	}
	
	/**
	 * Creates builder to build {@link RegisterUserDto}.
	 * @return created builder
	 */

	public static Builder builder()
	{
		return new Builder();
	}
	
	/**
	 * Builder to build {@link RegisterUserDto}.
	 */
	public static final class Builder {
		
		private String course_name;
		private String course_num;
		private Integer status;
		private String term;
		private String dept_name;
		
		private Builder() {
		}
		
		public Builder withCourseName(String course_name)
		{
			this.course_name = course_name;
			return this;
		}
		
		public Builder withCourseNum(String course_num)
		{
			this.course_num = course_num;
			return this;
		}
		
		public Builder withStatus(Integer status)
		{
			this.status = status;
			return this;
		}
		
		public Builder withTerm(String term)
		{
			this.term = term;
			return this;
		}
		
		public Builder withDeptName(String dept_name)
		{
			this.dept_name = dept_name;
			return this;
		}
		
		public RegistrationCartDto build()
		{
			return new RegistrationCartDto(this);
		}
		
	}
	
}
