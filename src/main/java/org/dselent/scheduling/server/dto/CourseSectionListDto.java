package org.dselent.scheduling.server.dto;

import java.util.ArrayList;

import javax.annotation.Generated;

public class CourseSectionListDto {
	private final ArrayList<Integer> id;
	private final ArrayList<Integer> instance_id;
	private final ArrayList<Integer> expected_pop;
	
	@Generated("SparkTools")
	private CourseSectionListDto(Builder builder) {
		this.id = builder.id;
		this.instance_id = builder.instance_id;
		this.expected_pop = builder.expected_pop;
	}
	/**
	 * Creates builder to build {@link CourseSectionListDto}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}
	/**
	 * Builder to build {@link CourseSectionListDto}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private ArrayList<Integer> id;
		private ArrayList<Integer> instance_id;
		private ArrayList<Integer> expected_pop;

		private Builder() {
		}

		public Builder withId(ArrayList<Integer> id) {
			this.id = id;
			return this;
		}

		public Builder withInstance_id(ArrayList<Integer> instance_id) {
			this.instance_id = instance_id;
			return this;
		}

		public Builder withExpected_pop(ArrayList<Integer> expected_pop) {
			this.expected_pop = expected_pop;
			return this;
		}

		public CourseSectionListDto build() {
			return new CourseSectionListDto(this);
		}
	}
	public ArrayList<Integer> getId() {
		return id;
	}
	public ArrayList<Integer> getInstance_id() {
		return instance_id;
	}
	public ArrayList<Integer> getExpected_pop() {
		return expected_pop;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expected_pop == null) ? 0 : expected_pop.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((instance_id == null) ? 0 : instance_id.hashCode());
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
		CourseSectionListDto other = (CourseSectionListDto) obj;
		if (expected_pop == null) {
			if (other.expected_pop != null)
				return false;
		} else if (!expected_pop.equals(other.expected_pop))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (instance_id == null) {
			if (other.instance_id != null)
				return false;
		} else if (!instance_id.equals(other.instance_id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "CourseSectionListDto [id=" + id + ", instance_id=" + instance_id + ", expected_pop=" + expected_pop
				+ "]";
	}
}
