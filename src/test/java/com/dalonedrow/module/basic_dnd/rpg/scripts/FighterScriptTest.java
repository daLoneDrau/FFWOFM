/**
 * 
 */
package com.dalonedrow.module.basic_dnd.rpg.scripts;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.module.basic_dnd.rpg.systems.BDDController;
import com.dalonedrow.module.basic_dnd.rpg.systems.BDDInteractive;

/**
 * @author 588648
 *
 */
public class FighterScriptTest {
	private FighterScript script;
	@Before
	public void before() {
		new BDDController();
		script = new FighterScript();
		script.setIO(
				((BDDInteractive) Interactive.getInstance()).getNewPlayer());
		script.getIO().setLevel(1);
	}
	@Test
	public void canGetTHAC() {
		assertEquals("at level 1, need to roll 14 to hit AC 5",
				14, script.getTHAC(5));
		assertEquals("at level 1, need to roll 20 to hit AC -6",
				20, script.getTHAC(-6));
		assertEquals("at level 1, need to roll 9 to hit AC 10",
				9, script.getTHAC(10));
		script.getIO().setLevel(3);
		assertEquals("at level 3, need to roll 18 to hit AC 0",
				18, script.getTHAC(0));
		assertEquals("at level 3, need to roll 15 to hit AC 3",
				15, script.getTHAC(3));
		assertEquals("at level 3, need to roll 12 to hit AC 6",
				12, script.getTHAC(6));
		script.getIO().setLevel(4);
		assertEquals("at level 4, need to roll 17 to hit AC 0",
				17, script.getTHAC(0));
		assertEquals("at level 4, need to roll 14 to hit AC 3",
				14, script.getTHAC(3));
		script.getIO().setLevel(5);
		assertEquals("at level 5, need to roll 11 to hit AC 5",
				11, script.getTHAC(5));
		assertEquals("at level 5, need to roll 20 to hit AC -6",
				20, script.getTHAC(-6));
		script.getIO().setLevel(6);
		assertEquals("at level 6, need to roll 15 to hit AC 0",
				15, script.getTHAC(0));
		assertEquals("at level 6, need to roll 12 to hit AC 3",
				12, script.getTHAC(3));
		script.getIO().setLevel(8);
		assertEquals("at level 8, need to roll 9 to hit AC 5",
				9, script.getTHAC(5));
		assertEquals("at level 8, need to roll 20 to hit AC -6",
				20, script.getTHAC(-6));
		script.getIO().setLevel(9);
		assertEquals("at level 9, need to roll 13 to hit AC 0",
				13, script.getTHAC(0));
		assertEquals("at level 9, need to roll 10 to hit AC 3",
				10, script.getTHAC(3));
		script.getIO().setLevel(10);
		assertEquals("at level 10, need to roll 7 to hit AC 5",
				7, script.getTHAC(5));
		assertEquals("at level 10, need to roll 18 to hit AC -6",
				18, script.getTHAC(-6));
		script.getIO().setLevel(11);
		assertEquals("at level 11, need to roll 12 to hit AC 0",
				12, script.getTHAC(0));
		assertEquals("at level 11, need to roll 9 to hit AC 3",
				9, script.getTHAC(3));
		script.getIO().setLevel(12);
		assertEquals("at level 12, need to roll 6 to hit AC 5",
				6, script.getTHAC(5));
		assertEquals("at level 12, need to roll 17 to hit AC -6",
				17, script.getTHAC(-6));
		script.getIO().setLevel(13);
		assertEquals("at level 13, need to roll 10 to hit AC 0",
				10, script.getTHAC(0));
		assertEquals("at level 13, need to roll 7 to hit AC 3",
				7, script.getTHAC(3));
		script.getIO().setLevel(14);
		assertEquals("at level 14, need to roll 4 to hit AC 5",
				4, script.getTHAC(5));
		assertEquals("at level 14, need to roll 15 to hit AC -6",
				15, script.getTHAC(-6));
		script.getIO().setLevel(15);
		assertEquals("at level 15, need to roll 8 to hit AC 0",
				8, script.getTHAC(0));
		assertEquals("at level 15, need to roll 5 to hit AC 3",
				5, script.getTHAC(3));
		script.getIO().setLevel(16);
		assertEquals("at level 16, need to roll 2 to hit AC 5",
				2, script.getTHAC(5));
		assertEquals("at level 16, need to roll 13 to hit AC -6",
				13, script.getTHAC(-6));
		script.getIO().setLevel(17);
		assertEquals("at level 17, need to roll 6 to hit AC 0",
				6, script.getTHAC(0));
		assertEquals("at level 17, need to roll 3 to hit AC 3",
				3, script.getTHAC(3));
		script.getIO().setLevel(18);
		assertEquals("at level 18, need to roll 2 to hit AC 5",
				2, script.getTHAC(5));
		assertEquals("at level 18, need to roll 11 to hit AC -6",
				11, script.getTHAC(-6));
		script.getIO().setLevel(19);
		assertEquals("at level 19, need to roll 4 to hit AC 0",
				4, script.getTHAC(0));
		assertEquals("at level 19, need to roll 2 to hit AC 3",
				2, script.getTHAC(3));
		script.getIO().setLevel(20);
		assertEquals("at level 20, need to roll 2 to hit AC 5",
				2, script.getTHAC(5));
		assertEquals("at level 20, need to roll 10 to hit AC -6",
				10, script.getTHAC(-6));
	}
}
