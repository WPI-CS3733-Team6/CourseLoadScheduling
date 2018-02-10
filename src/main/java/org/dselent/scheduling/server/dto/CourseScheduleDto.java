package org.dselent.scheduling.server.dto;


import javax.annotation.Generated;


public class CourseScheduleDto {
	private final Integer id;
	private final Integer section_id;
	private final String lecture_type;
	private final String meeting_days;
	private final Integer time_start;
	private final Integer time_end;
	
	@Generated("SparkTools")
	private CourseScheduleDto(Builder builder) {
		this.id = builder.id;
		this.section_id = builder.section_id;
		this.lecture_type = builder.lecture_type;
		this.meeting_days = builder.meeting_days;
		this.time_start = builder.time_start;
		this.time_end = builder.time_end;
	}
	/**
	 * Creates builder to build {@link CourseScheduleDto}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}
	/**
	 * Builder to build {@link CourseScheduleDto}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private Integer id;
		private Integer section_id;
		private String lecture_type;
		private String meeting_days;
		private Integer time_start;
		private Integer time_end;

		private Builder() {
		}

		public Builder withId(Integer id) {
			this.id = id;
			return this;
		}

		public Builder withSection_id(Integer section_id) {
			this.section_id = section_id;
			return this;
		}

		public Builder withLecture_type(String lecture_type) {
			this.lecture_type = lecture_type;
			return this;
		}

		public Builder withMeeting_days(String meeting_days) {
			this.meeting_days = meeting_days;
			return this;
		}

		public Builder withTime_start(Integer time_start) {
			this.time_start = time_start;
			return this;
		}

		public Builder withTime_end(Integer time_end) {
			this.time_end = time_end;
			return this;
		}

		public CourseScheduleDto build() {
			return new CourseScheduleDto(this);
		}
	}
	public Integer getId() {
		return id;
	}
	public Integer getSection_id() {
		return section_id;
	}
	public String getLecture_type() {
		return lecture_type;
	}
	public String getMeeting_days() {
		return meeting_days;
	}
	public Integer getTime_start() {
		return time_start;
	}
	public Integer getTime_end() {
		return time_end;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lecture_type == null) ? 0 : lecture_type.hashCode());
		result = prime * result + ((meeting_days == null) ? 0 : meeting_days.hashCode());
		result = prime * result + ((section_id == null) ? 0 : section_id.hashCode());
		result = prime * result + ((time_end == null) ? 0 : time_end.hashCode());
		result = prime * result + ((time_start == null) ? 0 : time_start.hashCode());
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
		CourseScheduleDto other = (CourseScheduleDto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lecture_type == null) {
			if (other.lecture_type != null)
				return false;
		} else if (!lecture_type.equals(other.lecture_type))
			return false;
		if (meeting_days == null) {
			if (other.meeting_days != null)
				return false;
		} else if (!meeting_days.equals(other.meeting_days))
			return false;
		if (section_id == null) {
			if (other.section_id != null)
				return false;
		} else if (!section_id.equals(other.section_id))
			return false;
		if (time_end == null) {
			if (other.time_end != null)
				return false;
		} else if (!time_end.equals(other.time_end))
			return false;
		if (time_start == null) {
			if (other.time_start != null)
				return false;
		} else if (!time_start.equals(other.time_start))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "CourseScheduleDto [id=" + id + ", section_id=" + section_id + ", lecture_type=" + lecture_type
				+ ", meeting_days=" + meeting_days + ", time_start=" + time_start + ", time_end=" + time_end + "]";
	}
	
	
}
