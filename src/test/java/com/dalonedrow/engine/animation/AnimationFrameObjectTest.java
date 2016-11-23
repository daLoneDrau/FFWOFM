package com.dalonedrow.engine.animation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AnimationFrameObjectTest {
	@Test
	public void canCreate() {
		AnimationFrameObject afo = new AnimationFrameObject(0, 1, 2, 3);
		assertNotNull(afo);
		assertEquals(afo.getRefId(), 0);

		assertEquals(afo.getDuration(), 1);
		afo.setDuration(2);
		assertEquals(afo.getDuration(), 2);

		assertEquals(afo.getFrameNumber(), 2);
		afo.setFrameNumber(3);
		assertEquals(afo.getFrameNumber(), 3);

		assertEquals(afo.getImageRefId(), 3);
		afo.setImageRefId(4);
		assertEquals(afo.getImageRefId(), 4);

		assertFalse(afo.hasFlag(1));
		assertFalse(afo.hasFlag(16));
		afo.assignFlag(1);
		assertTrue(afo.hasFlag(1));
		assertFalse(afo.hasFlag(16));
		afo.assignFlag(16);
		assertTrue(afo.hasFlag(1));
		assertTrue(afo.hasFlag(16));
		afo.removeFlag(1);
		assertFalse(afo.hasFlag(1));
		assertTrue(afo.hasFlag(16));
		afo.clearFlags();
		assertFalse(afo.hasFlag(1));
		assertFalse(afo.hasFlag(16));

		assertEquals(afo.getModSpeed(), 0, .0001f);
		afo.setModSpeed(5.5f);
		assertEquals(afo.getModSpeed(), 5.5f, .0001f);
	}
}
