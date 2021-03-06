package com.nand.sample.socialmedia.exception;

public class SocialMediaRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Default no argument constructor.
	 */
	public SocialMediaRuntimeException() {
		super();
	}

	/**
	 * Constructor with msg and cause to propagate.
	 * 
	 * @param message
	 *            message to propagate.
	 * @param cause
	 *            cause to propagate.
	 */
	public SocialMediaRuntimeException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Full constructor.
	 * 
	 * @param msg
	 *            msg to chain.
	 * @param className
	 *            fqcn.
	 * @param methodName
	 *            method name to propagate.
	 * @param cause
	 *            cause to propagate.
	 */
	public SocialMediaRuntimeException(final SocialMediaExceptionMessage msg, final String className, final String methodName, final Throwable cause) {
		super(msg.getMsgText(), cause);
	}

	/**
	 * Constructor with message to propagate.
	 * 
	 * @param message
	 *            msg to propagate.
	 */
	public SocialMediaRuntimeException(final String message) {
		super(message);
	}

	/**
	 * Constructor with cause to propagate.
	 * 
	 * @param cause
	 *            cause to chain.
	 */
	public SocialMediaRuntimeException(final Throwable cause) {
		super(cause);
	}
}
