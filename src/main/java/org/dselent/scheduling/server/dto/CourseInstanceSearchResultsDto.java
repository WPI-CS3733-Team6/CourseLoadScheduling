package org.dselent.scheduling.server.dto;

import java.util.ArrayList;
import javax.annotation.Generated;

public class CourseInstanceSearchResultsDto {
	private final ArrayList<Integer> id;
	private final ArrayList<String> course_num;
	private final ArrayList<String> term;
	private final ArrayList<Integer> sectionNo;
	private final ArrayList<String> subject;
	private final ArrayList<String> level;
	
	@Override
	public String toString() {
		return "CourseInstanceSearchResultsDto [id=" + id + ", course_num=" + course_num + ", term=" + term
				+ ", sectionNo=" + sectionNo + ", subject=" + subject + ", level=" + level + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((course_num == null) ? 0 : course_num.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		result = prime * result + ((sectionNo == null) ? 0 : sectionNo.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
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
		CourseInstanceSearchResultsDto other = (CourseInstanceSearchResultsDto) obj;
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
		if (sectionNo == null) {
			if (other.sectionNo != null)
				return false;
		} else if (!sectionNo.equals(other.sectionNo))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		if (term == null) {
			if (other.term != null)
				return false;
		} else if (!term.equals(other.term))
			return false;
		return true;
	}
	public ArrayList<Integer> getId() {
		return id;
	}
	public ArrayList<String> getCourse_num() {
		return course_num;
	}
	public ArrayList<String> getTerm() {
		return term;
	}
	public ArrayList<Integer> getSectionNo() {
		return sectionNo;
	}
	public ArrayList<String> getSubject() {
		return subject;
	}
	public ArrayList<String> getLevel() {
		return level;
	}
	@Generated("SparkTools")
	private CourseInstanceSearchResultsDto(Builder builder) {
		this.id = builder.id;
		this.course_num = builder.course_num;
		this.term = builder.term;
		this.sectionNo = builder.sectionNo;
		this.subject = builder.subject;
		this.level = builder.level;
	}
	/**
	 * Creates builder to build {@link CourseInstanceSearchResultsDto}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}
	/**
	 * Builder to build {@link CourseInstanceSearchResultsDto}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private ArrayList<Integer> id;
		private ArrayList<String> course_num;
		private ArrayList<String> term;
		private ArrayList<Integer> sectionNo;
		private ArrayList<String> subject;
		private ArrayList<String> level;

		private Builder() {
		}

		public Builder withId(ArrayList<Integer> id) {
			this.id = id;
			return this;
		}

		public Builder withCourse_num(ArrayList<String> course_num) {
			this.course_num = course_num;
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

		public Builder withSubject(ArrayList<String> subject) {
			this.subject = subject;
			return this;
		}

		public Builder withLevel(ArrayList<String> level) {
			this.level = level;
			return this;
		}

		public CourseInstanceSearchResultsDto build() {
			return new CourseInstanceSearchResultsDto(this);
		}
	}
}
