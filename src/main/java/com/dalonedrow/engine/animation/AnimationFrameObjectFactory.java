package com.dalonedrow.engine.animation;

import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.pooled.PooledStringBuilder;
import com.dalonedrow.pooled.StringBuilderPool;
import com.dalonedrow.rpg.base.flyweights.ErrorMessage;
import com.dalonedrow.rpg.base.flyweights.RPGException;

/**
 * @author drau
 */
public final class AnimationFrameObjectFactory {
	/** the initial capacity. */
	private static final int					INITIAL_CAPACITY	= 10;
	/**
	 * the one and only instance of the <code>AnimationFrameObjectFactory</code>
	 * class.
	 */
	private static AnimationFrameObjectFactory	instance;
	/**
	 * Gives access to the singleton instance of
	 * {@link AnimationFrameObjectFactory}.
	 * @return {@link AnimationFrameObjectFactory}
	 */
	public static AnimationFrameObjectFactory getInstance() {
		if (AnimationFrameObjectFactory.instance == null) {
			AnimationFrameObjectFactory.instance =
					new AnimationFrameObjectFactory();
		}
		return AnimationFrameObjectFactory.instance;
	}
	/** the list of images. */
	private AnimationFrameObject[]	frames;
	/** the list of image names used. */
	private String[]				names;
	/** the next available image id. */
	private int						nextId;
	/** Hidden constructor. */
	private AnimationFrameObjectFactory() {
		frames = new AnimationFrameObject[INITIAL_CAPACITY];
		names = new String[INITIAL_CAPACITY];
		nextId = 0;
	}
	/**
	 * Attempts to add a frame to the {@link AnimationFrameObjectFactory}.
	 * @param frameName the frame's name
	 * @param frame the {@link AnimationFrameObject}
	 * @throws RPGException if the frame could not be loaded
	 */
	public void addFrame(final String frameName,
			final AnimationFrameObject frame) throws RPGException {
		try {
			if (frameName == null
					|| frame == null) {
				PooledStringBuilder sb = StringBuilderPool.getInstance()
						.getStringBuilder();
				sb.append("ERROR! AnimationFrameObjectFactory.addFrame() - ");
				sb.append("null value sent in parameters");
				RPGException ex = new RPGException(
						ErrorMessage.BAD_PARAMETERS, sb.toString());
				sb.returnToPool();
				sb = null;
				throw ex;
			}
			if (hasFrame(frameName)) {
				PooledStringBuilder sb = StringBuilderPool.getInstance()
						.getStringBuilder();
				sb.append("ERROR! AnimationFrameObjectFactory.addFrame() - ");
				sb.append("frame '");
				sb.append(frameName);
				sb.append("' already loaded");
				RPGException ex = new RPGException(
						ErrorMessage.BAD_PARAMETERS, sb.toString());
				sb.returnToPool();
				sb = null;
				throw ex;
			}
			if (frame.getRefId() < frames.length
					&& frames[frame.getRefId()] != null) {
				PooledStringBuilder sb = StringBuilderPool.getInstance()
						.getStringBuilder();
				sb.append("ERROR! AnimationFrameObjectFactory.addFrame() - ");
				sb.append("frame '");
				sb.append(frameName);
				sb.append("' assigned invalid refId.  RefId already in use.");
				RPGException ex = new RPGException(
						ErrorMessage.BAD_PARAMETERS, sb.toString());
				sb.returnToPool();
				sb = null;
				throw ex;
			}
			if (frame.getRefId() >= frames.length) {
				// extend the names array
				String[] tempStr = new String[frame.getRefId() + 1];
				System.arraycopy(names, 0, tempStr, 0, names.length);
				names = tempStr;
				// extend the images array
				AnimationFrameObject[] tempImg =
						new AnimationFrameObject[frame.getRefId() + 1];
				System.arraycopy(frames, 0, tempImg, 0, frames.length);
				frames = tempImg;
			}
			names[frame.getRefId()] = frameName;
			frames[frame.getRefId()] = frame;
		} catch (PooledException e) {
			throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT, e);
		}
	}
	/**
	 * Gets a {@link AnimationFrameObject} by its reference id.
	 * @param id the reference id
	 * @return {@link AnimationFrameObject}
	 * @throws RPGException if the frame does not exist
	 */
	public AnimationFrameObject getFrameById(final int id) throws RPGException {
		AnimationFrameObject frame = null;
		if (id >= 0
				&& id < frames.length) {
			frame = frames[id];
		}
		if (frame == null) {
			try {
				PooledStringBuilder sb = StringBuilderPool.getInstance()
						.getStringBuilder();
				sb.append(
						"ERROR! AnimationFrameObjectFactory.getFrameById() - ");
				sb.append("invalid refId. frame with refId ");
				sb.append(id);
				sb.append(" was never loaded.");
				RPGException ex = new RPGException(
						ErrorMessage.INTERNAL_BAD_ARGUMENT, sb.toString());
				sb.returnToPool();
				sb = null;
				throw ex;
			} catch (PooledException e) {
				throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT, e);
			}
		}
		return frame;
	}
	/**
	 * Gets a {@link AnimationFrameObject} by its name.
	 * @param frameName the frame's name
	 * @return {@link AnimationFrameObject}
	 * @throws RPGException if the frame does not exist
	 */
	public AnimationFrameObject getFrameByName(final String frameName)
			throws RPGException {
		AnimationFrameObject frame = null;
		int id = -1;
		for (int i = 0; i < names.length; i++) {
			if (names[i] != null
					&& names[i].equals(frameName)) {
				id = i;
				break;
			}
		}
		if (id >= 0
				&& id < frames.length) {
			frame = frames[id];
		}
		if (frame == null) {
			PooledStringBuilder sb = StringBuilderPool.getInstance()
					.getStringBuilder();
			try {
				sb.append("ERROR! AnimationFrameObjectFactory");
				sb.append(".getFrameByName() - invalid refId. frame '");
				sb.append(frameName);
				sb.append("' was never loaded.");
				RPGException ex = new RPGException(
						ErrorMessage.INTERNAL_BAD_ARGUMENT, sb.toString());
				sb.returnToPool();
				sb = null;
				throw ex;
			} catch (PooledException e) {
				throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT, e);
			}
		}
		return frame;
	}
	/**
	 * Gets the reference id for a specific frame.
	 * @param frameName the frame's name
	 * @return int
	 * @throws RPGException if the frame was not loaded
	 */
	public int getFrameRefId(final String frameName) throws RPGException {
		int id = -1;
		for (int i = 0; i < names.length; i++) {
			if (names[i] != null
					&& names[i].equals(frameName)) {
				id = i;
				break;
			}
		}
		if (id == -1) {
			try {
				PooledStringBuilder sb =
						StringBuilderPool.getInstance().getStringBuilder();
				sb.append("ERROR! AnimationFrameObjectFactory");
				sb.append(".getFrameRefId() - invalid name: '");
				sb.append(frameName);
				sb.append("'");
				RPGException ex = new RPGException(
						ErrorMessage.INTERNAL_BAD_ARGUMENT, sb.toString());
				sb.returnToPool();
				sb = null;
				throw ex;
			} catch (PooledException e) {
				throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT, e);
			}
		}
		return id;
	}
	/**
	 * Gets the next available frame id.
	 * @return int
	 */
	public int getNextId() {
		return nextId++;
	}
	/**
	 * Determines if the {@link AnimationFrameObjectFactory} has an frame by a
	 * specific name.
	 * @param frameName the frame's name
	 * @return true if an frame by that name has been stored already; false
	 *         otherwise
	 */
	public boolean hasFrame(final String frameName) {
		boolean has = false;
		for (int i = 0; i < names.length; i++) {
			if (frameName != null
					&& names[i] != null
					&& frameName.equals(names[i])) {
				has = true;
				break;
			}
		}
		return has;
	}
}
