package com.dalonedrow.engine.animation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.dalonedrow.rpg.base.flyweights.RPGException;

/**
 * Test class for {@link AnimationFrameObjectFactoryTest}.
 * @author drau
 *
 */
public final class AnimationFrameObjectFactoryTest {
	/**
	 * Tests that a frame can be added.
	 * @throws RPGException should not happen
	 */
	@Test
	public void canAddFrame() throws RPGException {
		assertNotNull(AnimationFrameObjectFactory.getInstance());
		AnimationFrameObjectFactory.getInstance().addFrame(
				"start", new AnimationFrameObject(0, 1, 2, 3));
		AnimationFrameObjectFactory.getInstance().addFrame(
				"start12", new AnimationFrameObject(12, 1, 2, 5));
		assertEquals(AnimationFrameObjectFactory.getInstance().getFrameById(12)
				.getImageRefId(), 5);
		assertEquals(AnimationFrameObjectFactory.getInstance()
				.getFrameByName("start").getImageRefId(), 3);
		assertEquals(AnimationFrameObjectFactory.getInstance()
				.getFrameRefId("start12"), 12);
		assertTrue(AnimationFrameObjectFactory.getInstance().hasFrame("start"));
		assertFalse(
				AnimationFrameObjectFactory.getInstance().hasFrame("start1"));
		assertEquals(AnimationFrameObjectFactory.getInstance().getNextId(), 0);
		assertEquals(AnimationFrameObjectFactory.getInstance().getNextId(), 1);
		assertEquals(AnimationFrameObjectFactory.getInstance().getNextId(), 2);
	}
	/**
	 * Tests that adding frames with the same name will result in an error.
	 * @throws RPGException expected results
	 */
	@Test(expected = RPGException.class)
	public void willNotAddDuplicateFrameNames() throws RPGException {
		AnimationFrameObjectFactory.getInstance().addFrame(
				"start", new AnimationFrameObject(0, 1, 2, 3));
		AnimationFrameObjectFactory.getInstance().addFrame(
				"start", new AnimationFrameObject(0, 1, 2, 3));
	}
	@Test(expected = RPGException.class)
	public void willNotAddDuplicateFrames() throws RPGException {
		AnimationFrameObjectFactory.getInstance().addFrame(
				"start1", new AnimationFrameObject(0, 1, 2, 3));
		AnimationFrameObjectFactory.getInstance().addFrame(
				"start2", new AnimationFrameObject(0, 1, 2, 3));
	}
	@Test(expected = RPGException.class)
	public void willNotAddNullFrame() throws RPGException {
		AnimationFrameObjectFactory.getInstance().addFrame(
				"start", null);
	}
	@Test(expected = RPGException.class)
	public void willNotAddNullFrameName() throws RPGException {
		AnimationFrameObjectFactory.getInstance().addFrame(
				null, new AnimationFrameObject(0, 1, 2, 3));
	}
	@Test(expected = RPGException.class)
	public void willNotFindMissingFrame() throws RPGException {
		AnimationFrameObjectFactory.getInstance().getFrameById(24);
	}
	@Test(expected = RPGException.class)
	public void willNotGetInvalidFrameId() throws RPGException {
		AnimationFrameObjectFactory.getInstance().getFrameById(-1);
	}
	@Test(expected = RPGException.class)
	public void willNotGetMissingFrameId() throws RPGException {
		AnimationFrameObjectFactory.getInstance().getFrameById(24);
	}
	@Test(expected = RPGException.class)
	public void willNotgetMissingFrameName() throws RPGException {
		AnimationFrameObjectFactory.getInstance().getFrameByName("test");
	}
	@Test(expected = RPGException.class)
	public void willNotGetMissingFrameRefId() throws RPGException {
		AnimationFrameObjectFactory.getInstance().getFrameRefId("test");
	}
}
