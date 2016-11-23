package com.dalonedrow.engine.animation;

/**
 * @author drau
 */
public final class AnimationFrameObject {
	/** the length in nanoseconds that this frame should play. */
	private long		duration;
	/** any flags applied to the frame. */
	private long		flags;
	/** the order that this frame appears in in the animation sequence. */
	private int			frameNumber;
	/**
	 * the reference id for the {@link SpriteImageObject} this frame displays.
	 */
	private int			imageRefId;
	private float		modSpeed;
	/** the frame's reference id. */
	private final int	refId;
	/**
	 * Creates a new instance of {@link AnimationFrameObject}.
	 * @param id the frame's reference id
	 * @param time the length in nanoseconds that this frame should play
	 * @param order the order that this frame appears in in the animation
	 *            sequence
	 * @param imgId the reference id for the {@link SpriteImageObject} this
	 *            frame displays
	 */
	public AnimationFrameObject(final int id, final long time,
			final int order, final int imgId) {
		refId = id;
		duration = time;
		frameNumber = order;
		imageRefId = imgId;
	}
	/**
	 * Assigns a flag.
	 * @param flag the flag
	 */
	public void assignFlag(final long flag) {
		flags |= flag;
	}
	/** Clears all flags that were assigned. */
	public void clearFlags() {
		flags = 0;
	}
	/**
	 * Gets the length in nanoseconds that this frame should play.
	 * @return long
	 */
	public long getDuration() {
		return duration;
	}
	/**
	 * Gets the order that this frame appears in in the animation sequence.
	 * @return {@link int}
	 */
	public int getFrameNumber() {
		return frameNumber;
	}
	/**
	 * Gets the reference id for the {@link SpriteImageObject} this frame
	 * displays.
	 * @return {@link int}
	 */
	public int getImageRefId() {
		return imageRefId;
	}
	public final float getModSpeed() {
		return modSpeed;
	}
	/**
	 * Gets the frame's reference id.
	 * @return {@link int}
	 */
	public int getRefId() {
		return refId;
	}
	/**
	 * Determines if the {@link AnimationFrameObject} has a specific flag set.
	 * @param flag the flag
	 * @return <tt>true</tt> if the flag was set; <tt>false</tt> otherwise
	 */
	public boolean hasFlag(final long flag) {
		return (flags & flag) == flag;
	}
	/**
	 * Removes a flag.
	 * @param flag the flag
	 */
	public void removeFlag(final long flag) {
		flags &= ~flag;
	}
	/**
	 * Sets the length in nanoseconds that this frame should play.
	 * @param val the length
	 */
	public void setDuration(final long val) {
		duration = val;
	}
	/**
	 * Sets the order that this frame appears in in the animation sequence.
	 * @param val the new value to set
	 */
	public void setFrameNumber(final int val) {
		frameNumber = val;
	}
	/**
	 * Sets the reference id for the {@link SpriteImageObject} this frame
	 * displays.
	 * @param id the reference id
	 */
	public void setImageRefId(final int id) {
		imageRefId = id;
	}
	public final void setModSpeed(final float val) {
		modSpeed = val;
	}
}
