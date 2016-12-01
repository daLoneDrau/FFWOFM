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
public class MageScriptTest {
	private MageScript script;
	@Before
	public void before() {
		new BDDController();
		script = new MageScript();
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
		assertEquals("at level 3, need to roll 19 to hit AC 0",
				19, script.getTHAC(0));
		assertEquals("at level 3, need to roll 16 to hit AC 3",
				16, script.getTHAC(3));
		assertEquals("at level 3, need to roll 13 to hit AC 6",
				13, script.getTHAC(6));
		script.getIO().setLevel(4);
		assertEquals("at level 4, need to roll 18 to hit AC 0",
				18, script.getTHAC(0));
		assertEquals("at level 4, need to roll 15 to hit AC 3",
				15, script.getTHAC(3));
		script.getIO().setLevel(7);
		assertEquals("at level 7, need to roll 13 to hit AC 5",
				13, script.getTHAC(5));
		assertEquals("at level 7, need to roll 20 to hit AC -6",
				20, script.getTHAC(-6));
		script.getIO().setLevel(8);
		assertEquals("at level 8, need to roll 17 to hit AC 0",
				17, script.getTHAC(0));
		assertEquals("at level 8, need to roll 14 to hit AC 3",
				14, script.getTHAC(3));
		script.getIO().setLevel(10);
		assertEquals("at level 10, need to roll 12 to hit AC 5",
				12, script.getTHAC(5));
		assertEquals("at level 10, need to roll 20 to hit AC -6",
				20, script.getTHAC(-6));
		script.getIO().setLevel(11);
		assertEquals("at level 11, need to roll 16 to hit AC 0",
				16, script.getTHAC(0));
		assertEquals("at level 11, need to roll 13 to hit AC 3",
				13, script.getTHAC(3));
		script.getIO().setLevel(12);
		assertEquals("at level 12, need to roll 11 to hit AC 5",
				11, script.getTHAC(5));
		assertEquals("at level 12, need to roll 20 to hit AC -6",
				20, script.getTHAC(-6));
		script.getIO().setLevel(13);
		assertEquals("at level 13, need to roll 15 to hit AC 0",
				15, script.getTHAC(0));
		assertEquals("at level 13, need to roll 12 to hit AC 3",
				12, script.getTHAC(3));
		script.getIO().setLevel(14);
		assertEquals("at level 14, need to roll 9 to hit AC 5",
				9, script.getTHAC(5));
		assertEquals("at level 14, need to roll 20 to hit AC -6",
				20, script.getTHAC(-6));
		script.getIO().setLevel(16);
		assertEquals("at level 16, need to roll 13 to hit AC 0",
				13, script.getTHAC(0));
		assertEquals("at level 16, need to roll 10 to hit AC 3",
				10, script.getTHAC(3));
		script.getIO().setLevel(18);
		assertEquals("at level 18, need to roll 8 to hit AC 5",
				8, script.getTHAC(5));
		assertEquals("at level 18, need to roll 19 to hit AC -6",
				19, script.getTHAC(-6));
		script.getIO().setLevel(19);
		assertEquals("at level 19, need to roll 12 to hit AC 0",
				12, script.getTHAC(0));
		assertEquals("at level 19, need to roll 9 to hit AC 3",
				9, script.getTHAC(3));
		script.getIO().setLevel(20);
		assertEquals("at level 20, need to roll 7 to hit AC 5",
				7, script.getTHAC(5));
		assertEquals("at level 20, need to roll 18 to hit AC -6",
				18, script.getTHAC(-6));
		script.getIO().setLevel(21);
		assertEquals("at level 21, need to roll 11 to hit AC 0",
				11, script.getTHAC(0));
		assertEquals("at level 21, need to roll 8 to hit AC 3",
				8, script.getTHAC(3));
		script.getIO().setLevel(23);
		assertEquals("at level 23, need to roll 6 to hit AC 5",
				6, script.getTHAC(5));
		assertEquals("at level 23, need to roll 17 to hit AC -6",
				17, script.getTHAC(-6));
		script.getIO().setLevel(24);
		assertEquals("at level 24, need to roll 10 to hit AC 0",
				10, script.getTHAC(0));
		assertEquals("at level 24, need to roll 7 to hit AC 3",
				7, script.getTHAC(3));
		script.getIO().setLevel(25);
		assertEquals("at level 25, need to roll 5 to hit AC 5",
				5, script.getTHAC(5));
		assertEquals("at level 25, need to roll 16 to hit AC -6",
				16, script.getTHAC(-6));
	}
}
