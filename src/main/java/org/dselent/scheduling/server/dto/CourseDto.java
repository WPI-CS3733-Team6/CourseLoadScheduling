package org.dselent.scheduling.server.dto;

import java.util.ArrayList;

import javax.annotation.Generated;

public class CourseDto {
	
	private final Integer id;
	private final String course_name;
	private final String course_num;
	private final String course_description;
	private final String type;
	private final Boolean level;
	private final ArrayList<CourseInstanceDto> courseInstance;
	
	@Generated("SparkTools")
	private CourseDto(Builder builder) {
		this.id = builder.id;
		this.course_name = builder.course_name;
		this.course_num = builder.course_num;
		this.course_description = builder.course_description;
		this.type = builder.type;
		this.level = builder.level;
		this.courseInstance = builder.courseInstance;
	}
	/**
	 * Creates builder to build {@link CourseDto}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}
	/**
	 * Builder to build {@link CourseDto}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private Integer id;
		private String course_name;
		private String course_num;
		private String course_description;
		private String type;
		private Boolean level;
		private ArrayList<CourseInstanceDto> courseInstance;

		private Builder() {
		}

		public Builder withId(Integer id) {
			this.id = id;
			return this;
		}

		public Builder withCourse_name(String course_name) {
			this.course_name = course_name;
			return this;
		}

		public Builder withCourse_num(String course_num) {
			this.course_num = course_num;
			return this;
		}

		public Builder withCourse_description(String course_description) {
			this.course_description = course_description;
			return this;
		}

		public Builder withType(String type) {
			this.type = type;
			return this;
		}

		public Builder withLevel(Boolean level) {
			this.level = level;
			return this;
		}

		public Builder withCourseInstance(ArrayList<CourseInstanceDto> courseInstance) {
			this.courseInstance = courseInstance;
			return this;
		}

		public CourseDto build() {
			return new CourseDto(this);
		}
	}
	public Integer getId() {
		return id;
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
	public ArrayList<CourseInstanceDto> getCourseInstance() {
		return courseInstance;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((courseInstance == null) ? 0 : courseInstance.hashCode());
		result = prime * result + ((course_description == null) ? 0 : course_description.hashCode());
		result = prime * result + ((course_name == null) ? 0 : course_name.hashCode());
		result = prime * result + ((course_num == null) ? 0 : course_num.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		CourseDto other = (CourseDto) obj;
		if (courseInstance == null) {
			if (other.courseInstance != null)
				return false;
		} else if (!courseInstance.equals(other.courseInstance))
			return false;
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
		return true;
	}
	
	@Override
	public String toString() {
		return "CourseDto [id=" + id + ", course_name=" + course_name + ", course_num=" + course_num
				+ ", course_description=" + course_description + ", type=" + type + ", level=" + level
				+ ", courseInstance=" + courseInstance + "]";
	}
	
	
	
}
