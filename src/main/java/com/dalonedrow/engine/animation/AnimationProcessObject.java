package com.dalonedrow.engine.animation;

/**
 * The animation process class, which holds references to the id of the current 
 * animation and the number of the current frame being displayed.  Each 
 * animation process is associated with a specific interactive object being 
 * drawn
 * @author DaLoneDrau
 */
public final class AnimationProcessObject {
	/** flag indicating the animation repeats itself from the beginning. */
	int	ANIM_REPEATS_FROM_BEGINNING	= 1;
	/** flag indicating the animation repeats itself from the key frame. */
	int ANIM_REPEATS_FROM_STARTKEYFRAME = 2;
	/** flag indicating the animation repeats itself from the key frame. */
	int ANIM_REPEATS_FROM_ENDKEYFRAME = 4;
	/** flag indicating that the frame's speed has been modified. */
	int ANIM_FRAME_SPEED_MODIFIED = 8;
	/** flag indicating that the sequence's speed has been modified. */
	int ANIM_SEQUENCE_SPEED_MODIFIED = 16;
	/** flag indicating the animation reverses its order. */
	int	ANIM_REVERSES_ORDER	= 32;
	/** 
	 * flag indicating the animation should display its start 
	 * frame when stopped. 
	 */
	int ANIM_GOTO_START_FRAME = 64;
	/** 
	 * flag indicating the animation should display its end 
	 * frame when stopped. 
	 */
	int ANIM_GOTO_END_FRAME = 128;
	/** 
	 * flag indicating the IO's base sprite should change to its end 
	 * frame when stopped. 
	 */
	int ANIM_CHANGE_SPRITE_TO_END_FRAME = 256;
	/** flag indicating that an animation has not started playing. */
	int ANIM_NOT_STARTED = -1;

	/** the current animation sequence. */
	private AnimationSequenceObject		currentAnimationSequence;
	/** the current frame being displayed. */
	private int		currentFrame	= 0;
	/** any flags applied to the current animation. */
	private long	flags			= 0;
	/** the time that the current animation frame started playing. */
	private long	frameStart		= ANIM_NOT_STARTED;
	/**
	 * the flag indicating whether the {@link AnimationProcessObject} is in use.
	 */
	private boolean	inUse			= false;
	/**
	 * the reference id of the next animation played after the current finishes.
	 */
	private int		nextAnimation;
	/** any flags applied to the next animation. */
	private long	nextFlags;
	/**
	 * the reference id of the {@link BaseInteractiveObject} the animation is
	 * associated with.
	 */
	private int		refId;
	/** Creates a new instance of {@link AnimationProcessObject}. */
	public AnimationProcessObject() {
		refId = -1;
		inUse = false;
	}
	/**
	 * Gets the reference id of the current animation.
	 * @return {@link AnimationSequenceObject}
	 */
	public AnimationSequenceObject getCurrentAnimationSequence() {
		return currentAnimationSequence;
	}
	/**
	 * Gets the current frame being displayed.
	 * @return int
	 */
	public int getCurrentFrame() {
		return currentFrame;
	}
	/**
	 * Gets the time that the current animation frame started playing.
	 * @return long
	 */
	public long getFrameStart() {
		return frameStart;
	}
	/**
	 * Gets the reference id of the {@link BaseInteractiveObject} the animation
	 * is associated with.
	 * @return int
	 */
	public int getRefId() {
		return refId;
	}
	/**
	 * Gets the flag indicating whether the {@link AnimationProcessObject} is in
	 * use.
	 * @return true if the {@link AnimationProcessObject} is being used; false
	 *         otherwise
	 */
	public boolean isInUse() {
		return inUse;
	}
	/**
	 * Sets the {@link AnimationSequenceObject} of the current animation.
	 * @param sequence the {@link AnimationSequenceObject} to set
	 */
	public void setCurrentAnimationSequence(
			final AnimationSequenceObject sequence) {
		currentAnimationSequence = sequence;
	}
	/**
	 * Sets the current frame being displayed.
	 * @param index the index to set
	 */
	public void setCurrentFrame(final int index) {
		currentFrame = index;
	}
	/**
	 * Sets the time that the current animation frame started playing.
	 * @param time the time to set
	 */
	public void setFrameStart(final long time) {
		frameStart = time;
	}
	/**
	 * Sets the flag indicating whether the {@link AnimationProcessObject} is in
	 * use.
	 * @param val the flag
	 */
	public void setInUse(final boolean val) {
		inUse = val;
	}
	/**
	 * Sets the reference id of the {@link BaseInteractiveObject} the animation
	 * is associated with.
	 * @param val the id to set
	 */
	public void setRefId(final int val) {
		refId = val;
	}
}
