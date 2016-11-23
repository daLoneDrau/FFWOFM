package com.dalonedrow.module.barbarianprince.turn;

import static org.junit.Assert.*;

import org.junit.Test;

import com.dalonedrow.engine.sprite.base.SimplePoint;
import com.dalonedrow.rpg.base.flyweights.RPGException;

public class TimeTrackTest {
	@Test
	public void canCreate() throws RPGException {
		TimeTrack.getInstance().reset();
		assertNotNull(TimeTrack.getInstance());
		assertTrue(TimeTrack.getInstance().getDay() == Day.One);
		assertTrue(TimeTrack.getInstance().getWeek() == Week.One);
		TimeTrack.getInstance().nextDay();
		assertTrue(TimeTrack.getInstance().getDay() == Day.Two);
		assertTrue(TimeTrack.getInstance().getWeek() == Week.One);
		TimeTrack.getInstance().nextDay();
		TimeTrack.getInstance().nextDay();
		TimeTrack.getInstance().nextDay();
		TimeTrack.getInstance().nextDay();
		TimeTrack.getInstance().nextDay();
		TimeTrack.getInstance().nextDay();
		assertTrue(TimeTrack.getInstance().getDay() == Day.One);
		assertTrue(TimeTrack.getInstance().getWeek() == Week.Two);
		assertEquals(TimeTrack.getInstance().toString(), 
				"1st Day 2nd Week  - Dawn");
	}
}
