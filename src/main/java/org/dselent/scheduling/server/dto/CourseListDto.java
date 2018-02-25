package org.dselent.scheduling.server.dto;

import java.util.ArrayList;
import javax.annotation.Generated;

public class CourseListDto {
	
	private final ArrayList<Integer> id;
	private final ArrayList<String> course_name;
	private final ArrayList<String> course_num;
	private final ArrayList<String> course_description;
	private final ArrayList<String> type;
	private final ArrayList<Boolean> level;
	private final ArrayList<Integer> instanceNo;
	
	@Generated("SparkTools")
	private CourseListDto(Builder builder) {
		this.id = builder.id;
		this.course_name = builder.course_name;
		this.course_num = builder.course_num;
		this.course_description = builder.course_description;
		this.type = builder.type;
		this.level = builder.level;
		this.instanceNo = builder.instanceNo;
	}
	/**
	 * Creates builder to build {@link CourseListDto}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}
	/**
	 * Builder to build {@link CourseListDto}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private ArrayList<Integer> id;
		private ArrayList<String> course_name;
		private ArrayList<String> course_num;
		private ArrayList<String> course_description;
		private ArrayList<String> type;
		private ArrayList<Boolean> level;
		private ArrayList<Integer> instanceNo;

		private Builder() {
		}

		public Builder withId(ArrayList<Integer> id) {
			this.id = id;
			return this;
		}

		public Builder withCourse_name(ArrayList<String> course_name) {
			this.course_name = course_name;
			return this;
		}

		public Builder withCourse_num(ArrayList<String> course_num) {
			this.course_num = course_num;
			return this;
		}

		public Builder withCourse_description(ArrayList<String> course_description) {
			this.course_description = course_description;
			return this;
		}

		public Builder withType(ArrayList<String> type) {
			this.type = type;
			return this;
		}

		public Builder withLevel(ArrayList<Boolean> level) {
			this.level = level;
			return this;
		}

		public Builder withInstanceNo(ArrayList<Integer> instanceNo) {
			this.instanceNo = instanceNo;
			return this;
		}

		public CourseListDto build() {
			return new CourseListDto(this);
		}
	}
	public ArrayList<Integer> getId() {
		return id;
	}
	public ArrayList<String> getCourse_name() {
		return course_name;
	}
	public ArrayList<String> getCourse_num() {
		return course_num;
	}
	public ArrayList<String> getCourse_description() {
		return course_description;
	}
	public ArrayList<String> getType() {
		return type;
	}
	public ArrayList<Boolean> getLevel() {
		return level;
	}
	public ArrayList<Integer> getInstanceNo() {
		return instanceNo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((course_description == null) ? 0 : course_description.hashCode());
		result = prime * result + ((course_name == null) ? 0 : course_name.hashCode());
		result = prime * result + ((course_num == null) ? 0 : course_num.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((instanceNo == null) ? 0 : instanceNo.hashCode());
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
		CourseListDto other = (CourseListDto) obj;
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
		if (instanceNo == null) {
			if (other.instanceNo != null)
				return false;
		} else if (!instanceNo.equals(other.instanceNo))
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
				+ ", course_description=" + course_description + ", type=" + type + ", level=" + level + ", instanceNo="
				+ instanceNo + "]";
	}
}