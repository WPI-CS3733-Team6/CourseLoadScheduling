package org.dselent.scheduling.server.dto;

import java.util.ArrayList;
import javax.annotation.Generated;

public class CourseInstanceListDto {

	private final ArrayList<Integer> id;
	private final ArrayList<Integer> course_id;
	private final ArrayList<String> term;
	private final ArrayList<Integer> sectionNo;
	
	@Generated("SparkTools")
	private CourseInstanceListDto(Builder builder) {
		this.id = builder.id;
		this.course_id = builder.course_id;
		this.term = builder.term;
		this.sectionNo = builder.sectionNo;
	}
	/**
	 * Creates builder to build {@link CourseInstanceListDto}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}
	/**
	 * Builder to build {@link CourseInstanceListDto}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private ArrayList<Integer> id;
		private ArrayList<Integer> course_id;
		private ArrayList<String> term;
		private ArrayList<Integer> sectionNo;

		private Builder() {
		}

		public Builder withId(ArrayList<Integer> id) {
			this.id = id;
			return this;
		}

		public Builder withCourse_id(ArrayList<Integer> course_id) {
			this.course_id = course_id;
			return this;
		}

		public Builder withTerm(ArrayList<String> term) {
			this.term = term;
			return this;
		}

		public Builder withSectionNo(ArrayList<Integer> sectionNo) {
			this.sectionNo = sectionNo;
			return this;
		}

		public CourseInstanceListDto build() {
			return new CourseInstanceListDto(this);
		}
	}
	public ArrayList<Integer> getId() {
		return id;
	}
	public ArrayList<Integer> getCourse_id() {
		return course_id;
	}
	public ArrayList<String> getTerm() {
		return term;
	}
	public ArrayList<Integer> getSectionNo() {
		return sectionNo;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((course_id == null) ? 0 : course_id.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((sectionNo == null) ? 0 : sectionNo.hashCode());
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
		CourseInstanceListDto other = (CourseInstanceListDto) obj;
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
		if (sectionNo == null) {
			if (other.sectionNo != null)
				return false;
		} else if (!sectionNo.equals(other.sectionNo))
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
		return "CourseInstanceListDto [id=" + id + ", course_id=" + course_id + ", term=" + term + ", sectionNo="
				+ sectionNo + "]";
	}
}
