package com.dalonedrow.module.shin_sam.rpg.base.flyweights;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.dalonedrow.rpg.base.flyweights.Attribute;
import com.dalonedrow.rpg.base.flyweights.RPGException;

public class ShinSamCharacterTest {
	/** test character. */
	private ShinSamPC test;
	@Before
	public void before() throws RPGException {
		test = new ShinSamPC();
		test.setBaseAttributeScore("AB", 1);
		test.setBaseAttributeScore("AC", 10);
		test.setBaseAttributeScore("END", 9);
		test.setBaseAttributeScore("HP", 6);
		test.setBaseAttributeScore("HPR", 1);
		test.setBaseAttributeScore("KI", 2);
		test.setBaseAttributeScore("MHP", 6);
		test.setBaseAttributeScore("MIT", 1);
		test.setBaseAttributeScore("MKI", 3);
		test.setBaseAttributeScore("MR", 6);
		test.setBaseAttributeScore("PIE", 6);
		test.setBaseAttributeScore("SPD", 2);
		test.setBaseAttributeScore("SPR", 10);
		test.setBaseAttributeScore("ST", 3);
	}
	@Test
	public void canAdjustLife() {
		assertEquals(6f, test.getBaseAttributeScore("HP"), .01f);
	}
}
