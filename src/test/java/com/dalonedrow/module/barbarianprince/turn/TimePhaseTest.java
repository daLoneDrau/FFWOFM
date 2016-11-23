package com.dalonedrow.module.barbarianprince.turn;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.dalonedrow.rpg.base.flyweights.RPGException;

/**
 * Test the time phase track.
 * @author drau
 *
 */
public final class TimePhaseTest {
	/**
	 * Test that a {@link TimePhase} instance can be created and advanced.
	 * @throws RPGException should not happen.
	 */
	@Test
	public void canCreate() throws RPGException {
		TimePhase tp = TimePhase.PHASE_00_PRE_ACTION;
		assertNotNull(tp);
		tp = tp.advance();
		assertEquals(tp, TimePhase.PHASE_01_ACTION);
		tp = tp.advance();
		assertEquals(tp, TimePhase.PHASE_02_POST_ACTION);
		tp = tp.advance();
		assertEquals(tp, TimePhase.PHASE_03_PRE_EVENING);
		tp = tp.advance();
		assertEquals(tp, TimePhase.PHASE_04_EVENING_FOOD);
		tp = tp.advance();
		assertEquals(tp, TimePhase.PHASE_05_EVENING_LODGING);
		tp = tp.advance();
		assertEquals(tp, TimePhase.PHASE_06_POST_EVENING);
		tp = tp.advance();
		assertEquals(tp, TimePhase.PHASE_00_PRE_ACTION);
		assertEquals(tp.getTitle(), "Dawn");
	}
}
