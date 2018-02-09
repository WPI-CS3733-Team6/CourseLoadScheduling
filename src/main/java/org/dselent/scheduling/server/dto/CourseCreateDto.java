package org.dselent.scheduling.server.dto;

import org.dselent.scheduling.server.dto.RegisterUserDto.Builder;

public class CourseCreateDto {
	
	private final String user_id;
	private final String course_name;
	private final String course_num;
	private final String course_description;
	private final String type;
	private final Boolean level;
	
	private CourseCreateDto(Builder builder) {
		
		this.user_id = builder.user_id;
		this.course_name = builder.course_name;
		this.course_num = builder.course_num;
		this.course_description = builder.course_description;
		this.type = builder.type;
		this.level = builder.level;
		
		
		if(this.user_id == null)
		{
			throw new IllegalStateException("user_id cannot be null");
		}
		if(this.course_name == null)
		{
			throw new IllegalStateException("course_name cannot be null");
		}
		if(this.course_num == null)
		{
			throw new IllegalStateException("course_num cannot be null");
		}
		if(this.course_description == null)
		{
			throw new IllegalStateException("course_description cannot be null");
		}
		if(this.type == null)
		{
			throw new IllegalStateException("type cannot be null");
		}
		if(this.level == null)
		{
			throw new IllegalStateException("level cannot be null");
		}
		
	}

	
	
	public String getUser_id() {
		return user_id;
	}



	public String getCourse_name() {
		return course_name;
	}



	public String getCourse_num() {
		return course_num;
	}



	public String getCourse_description() {
		return course_description;
	}



	public String getType() {
		return type;
	}



	public Boolean getLevel() {
		return level;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((course_description == null) ? 0 : course_description.hashCode());
		result = prime * result + ((course_name == null) ? 0 : course_name.hashCode());
		result = prime * result + ((course_num == null) ? 0 : course_num.hashCode());
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((user_id == null) ? 0 : user_id.hashCode());
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
		CourseCreateDto other = (CourseCreateDto) obj;
		if (course_description == null) {
			if (other.course_description != null)
				return false;
		} else if (!course_description.equals(other.course_description))
			return false;
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
		if (level == null) {
			if (other.level != null)
				return false;
		} else if (!level.equals(other.level))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (user_id == null) {
			if (other.user_id != null)
				return false;
		} else if (!user_id.equals(other.user_id))
			return false;
		return true;
	}

	

	@Override
	public String toString() {
		return "CourseCreateDto [user_id=" + user_id + ", course_name=" + course_name + ", course_num=" + course_num
				+ ", course_description=" + course_description + ", type=" + type + ", level=" + level + "]";
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
		
		private String user_id;
		private String course_name;
		private String course_num;
		private String course_description;
		private String type;
		private Boolean level;
		
		private Builder() 
		{
		}
		
		public Builder withUserId(String user_id)
		{
			this.user_id = user_id;
			return this;
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
		
		public Builder withCourseDescription(String course_description)
		{
			this.course_description = course_description;
			return this;
		}
		
		public Builder withType(String type)
		{
			this.type = type;
			return this;
		}
		
		public Builder withLevel(Boolean level)
		{
			this.level = level;
			return this;
		}
		
		public CourseCreateDto build() {
			return new CourseCreateDto(this);
		}
		
	}
	
}
