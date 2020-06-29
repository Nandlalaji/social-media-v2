package com.nand.sample.socialmedia.exception;

public class SocialMediaExceptionMessage {
	public static final long SEVERITY_ERROR = 1L;
	public static final int TYPE_USER = 1;
	private long msgSeverity = 0;
	private int msgType = 0;
	private String msgText = null;

	/**
	 * Default constructor.
	 * 
	 * @param msgText
	 *            message text to set.
	 */
	public SocialMediaExceptionMessage(final String msgText) {
		this.msgText = msgText;
	}

	/**
	 * Full constructor.
	 * 
	 * @param msgSeverity
	 *            severity level.
	 * @param msgType
	 *            message type.
	 * @param msgText
	 *            message text.
	 */
	public SocialMediaExceptionMessage(final long msgSeverity, final int msgType, final String msgText) {
		this.msgSeverity = msgSeverity;
		this.msgType = msgType;
		this.msgText = msgText;
	}

	/**
	 * Gets the message text.
	 * 
	 * @return message text to get.
	 */
	public String getMsgText() {
		return msgText;
	}

	/**
	 * Gets the message severity.
	 * 
	 * @return message severity to get.
	 */
	public long getMsgSeverity() {
		return msgSeverity;
	}

	/**
	 * Gets the message type.
	 * 
	 * @return message type to get.
	 */
	public int getMsgType() {
		return msgType;
	}
}
