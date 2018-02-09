package org.dselent.scheduling.server.dto;

import javax.annotation.Generated;

/**
 * DTO = Data Transfer Object
 * Used to package/wrap several variables into a single object
 * Uses the Builder pattern for object instantiation
 * 
 * @author dselent
 *
 */
public class InboxMessageDto
{
	private final Integer messageId;
	private final Integer senderId;
	private final String subject;
	private final String content;
	private final Boolean status;
	
	
	public Integer getMessageId() {
		return messageId;
	}
	public Integer getSenderId() {
		return senderId;
	}
	public String getSubject() {
		return subject;
	}
	public String getContent() {
		return content;
	}
	public Boolean getStatus() {
		return status;
	}
	
	@Override
	public String toString() {
		return "InboxMessageDto [messageId=" + messageId + ", senderId=" + senderId + ", subject=" + subject
				+ ", content=" + content + ", status=" + status + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((messageId == null) ? 0 : messageId.hashCode());
		result = prime * result + ((senderId == null) ? 0 : senderId.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
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
		InboxMessageDto other = (InboxMessageDto) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (messageId == null) {
			if (other.messageId != null)
				return false;
		} else if (!messageId.equals(other.messageId))
			return false;
		if (senderId == null) {
			if (other.senderId != null)
				return false;
		} else if (!senderId.equals(other.senderId))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		return true;
	}
	
	@Generated("SparkTools")
	private InboxMessageDto(Builder builder) {
		this.messageId = builder.messageId;
		this.senderId = builder.senderId;
		this.subject = builder.subject;
		this.content = builder.content;
		this.status = builder.status;
	}
	
	/**
	 * Creates builder to build {@link InboxMessageDto}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}
	
	/**
	 * Builder to build {@link InboxMessageDto}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private Integer messageId;
		private Integer senderId;
		private String subject;
		private String content;
		private Boolean status;

		private Builder() {
		}

		public Builder withMessageId(Integer messageId) {
			this.messageId = messageId;
			return this;
		}

		public Builder withSenderId(Integer senderId) {
			this.senderId = senderId;
			return this;
		}

		public Builder withSubject(String subject) {
			this.subject = subject;
			return this;
		}

		public Builder withContent(String content) {
			this.content = content;
			return this;
		}

		public Builder withStatus(Boolean status) {
			this.status = status;
			return this;
		}

		public InboxMessageDto build() {
			return new InboxMessageDto(this);
		}
	}
	
}