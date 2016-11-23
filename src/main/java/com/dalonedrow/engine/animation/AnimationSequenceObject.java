package com.dalonedrow.engine.animation;

import com.dalonedrow.pooled.PooledStringBuilder;
import com.dalonedrow.pooled.StringBuilderPool;

/**
 * Each animation sequence is made up of the sequence's total length in
 * nanoseconds, the ids of all images that make up the animation, and the
 * indices of any key frames.
 * @author DaLoneDrau
 */
public class AnimationSequenceObject {
	/** the total animation time for the sequence. */
	private long	animationTime;
	/** the frame that defines the starting point of the transition. */
	private int		endKeyFrame;
	/** any flags applied to the animation. */
	private long	flags;
	/** the ids of all frames in the sequence. */
	private int[]	frames;
	/** the factor by which the animation speed has been modified. */
	private float	modSpeed;
	/** the number of frames in the animation sequence. */
	private int		numFrames;
	/** the sequence's reference id. */
	private int		refId;
	/** the frame that defines the starting point of the transition. */
	private int		startKeyFrame;
	/**
	 * Creates a new instance of {@link AnimationSequenceObject}.
	 * @param id the sequence's reference id
	 */
	public AnimationSequenceObject(final int id) {
		refId = id;
		final int initialCapacity = 10;
		frames = new int[initialCapacity];
		for (int i = 0; i < frames.length; i++) {
			frames[i] = -1;
		}
	}
	/**
	 * Adds a frame to the sequence.
	 * @param frame the {@link AnimationFrameObject}
	 * @throws Exception if an error occurs
	 */
	public void addFrame(final AnimationFrameObject frame) throws Exception {
		int index = frame.getFrameNumber();
		if (index < 0) {
			PooledStringBuilder sb = StringBuilderPool.getInstance()
					.getStringBuilder();
			sb.append("ERROR! AnimationSequenceObject.addFrame() - ");
			sb.append("frame created with invalid frame number ");
			sb.append(index);
			Exception ex = new Exception(sb.toString());
			sb.returnToPool();
			throw ex;
		}
		this.addFrame(index, frame.getRefId());
	}
	/**
	 * Adds a frame to the sequence.
	 * @param index the index at which the frame is played
	 * @param frameRefId the frame's reference id
	 */
	public void addFrame(final int index, final int frameRefId) {
		if (index >= frames.length) {
			int[] dest = new int[index + 1];
			System.arraycopy(frames, 0, dest, 0, frames.length);
			frames = dest;
			dest = null;
		}
		frames[index] = frameRefId;
		numFrames++;
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
	 * {@inheritDoc}
	 */
	@Override
	protected AnimationSequenceObject clone()
			throws CloneNotSupportedException {
		AnimationSequenceObject clone = new AnimationSequenceObject(refId);
		clone.animationTime = animationTime;
		clone.endKeyFrame = endKeyFrame;
		clone.flags = flags;
		clone.frames = new int[frames.length];
		System.arraycopy(frames, 0, clone.frames, 0, frames.length);
		clone.modSpeed = modSpeed;
		clone.numFrames = numFrames;
		clone.refId = refId;
		clone.startKeyFrame = startKeyFrame;
		return clone;
	}
	/**
	 * Gets the total animation time for the sequence.
	 * @return <code>long</code>
	 */
	public long getAnimationTime() {
		return animationTime;
	}
	/**
	 * Gets the endKeyFrame.
	 * @return <code>int</code>
	 */
	public int getEndKeyFrame() {
		return endKeyFrame;
	}
	/**
	 * Gets the frame at a specific index.
	 * @param index the index
	 * @return {@link AnimationFrameObject}
	 * @throws Exception if the index is invalid
	 */
	public AnimationFrameObject getFrame(final int index) throws Exception {
		AnimationFrameObject frame = null;
		if (index >= 0
				&& index < frames.length) {
			frame =
					AnimationFrameObjectFactory.getInstance().getFrameById(
							frames[index]);
		}
		if (frame == null) {
			PooledStringBuilder sb = StringBuilderPool.getInstance()
					.getStringBuilder();
			sb.append("ERROR! AnimationSequenceObject.getFrame() - ");
			sb.append("invalid index - ");
			sb.append(index);
			Exception ex = new Exception(sb.toString());
			sb.returnToPool();
			throw ex;
		}
		return frame;
	}
	/**
	 * Gets the factor by which the animation speed has been modified.
	 * @return <code>float</code>
	 */
	public float getModSpeed() {
		return modSpeed;
	}
	/**
	 * Gets the total number of frames in the sequence.
	 * @return <code>int</code>
	 */
	public int getNumFrames() {
		return numFrames;
	}
	/**
	 * Gets the sequence's reference id.
	 * @return <code>int</code>
	 */
	public int getRefId() {
		return refId;
	}
	/**
	 * Gets the startKeyFrame.
	 * @return <code>int</code>
	 */
	public int getStartKeyFrame() {
		return startKeyFrame;
	}
	/**
	 * Determines if the {@link AnimationSequenceObject} has a specific flag
	 * set.
	 * @param flag the flag
	 * @return true if the flag was set; false otherwise
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
	/** Reverses the frames' order. */
	public void reverseFrames() {
		int[] dest = new int[numFrames];
		for (int i = 0; i < numFrames; i++) {
			dest[numFrames - 1 - i] = frames[i];
		}
		frames = dest;
		dest = null;
	}
	/**
	 * Sets the total animation time for the sequence.
	 * @param val the time to set
	 */
	public void setAnimationTime(final long val) {
		animationTime = val;
	}
	/**
	 * Sets the frame that defines the starting point of the transition.
	 * @param val the frame id to set
	 */
	public void setEndKeyFrame(final int val) {
		endKeyFrame = val;
	}
	/**
	 * Sets the factor by which the animation speed has been modified.
	 * @param val the factor
	 */
	public void setModSpeed(final float val) {
		modSpeed = val;
	}
	/**
	 * Sets the frame that defines the starting point of the transition.
	 * @param val the frame id to set
	 */
	public void setStartKeyFrame(final int val) {
		startKeyFrame = val;
	}

}
