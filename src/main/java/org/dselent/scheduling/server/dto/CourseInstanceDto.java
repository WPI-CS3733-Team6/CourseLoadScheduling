package org.dselent.scheduling.server.dto;

import java.util.ArrayList;

import javax.annotation.Generated;

public class CourseInstanceDto {
	
	private final Integer id;
	private final Integer course_id;
	private final String term;
	private final ArrayList<CourseSectionDto> sections;
	
	@Generated("SparkTools")
	private CourseInstanceDto(Builder builder) {
		this.id = builder.id;
		this.course_id = builder.course_id;
		this.term = builder.term;
		this.sections = builder.sections;
	}
	/**
	 * Creates builder to build {@link CourseInstanceDto}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}
	/**
	 * Builder to build {@link CourseInstanceDto}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private Integer id;
		private Integer course_id;
		private String term;
		private ArrayList<CourseSectionDto> sections;

		private Builder() {
		}

		public Builder withId(Integer id) {
			this.id = id;
			return this;
		}

		public Builder withCourse_id(Integer course_id) {
			this.course_id = course_id;
			return this;
		}

		public Builder withTerm(String term) {
			this.term = term;
			return this;
		}

		public Builder withSections(ArrayList<CourseSectionDto> sections) {
			this.sections = sections;
			return this;
		}

		public CourseInstanceDto build() {
			return new CourseInstanceDto(this);
		}
	}
	public Integer getId() {
		return id;
	}
	public Integer getCourse_id() {
		return course_id;
	}
	public String getTerm() {
		return term;
	}
	public ArrayList<CourseSectionDto> getSections() {
		return sections;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((course_id == null) ? 0 : course_id.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((sections == null) ? 0 : sections.hashCode());
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
		CourseInstanceDto other = (CourseInstanceDto) obj;
		if (course_id == null) {
			if (other.course_id != null)
				return false;
		} else if (!course_id.equals(other.course_id))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (sections == null) {
			if (other.sections != null)
				return false;
		} else if (!sections.equals(other.sections))
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
		return "CourseInstanceDto [id=" + id + ", course_id=" + course_id + ", term=" + term + ", sections=" + sections
				+ "]";
	}

	
}
