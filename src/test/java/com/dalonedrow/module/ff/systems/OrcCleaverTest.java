package com.dalonedrow.module.ff.systems;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.module.ff.rpg.FFInteractiveObject;
import com.dalonedrow.module.ff.systems.wofm.FFWebServiceClient;
import com.dalonedrow.module.ff.systems.wofm.FFWoFMController;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.systems.Script;

/**
 * Class to test creation and reactions to Orc script
 * @author drau
 *
 */
public class OrcCleaverTest {
	/** the test object. */
	private FFInteractiveObject cleaver;
	/**
	 * Run before all tests.<br>
	 * - set debugging flags where needed<br>
	 * - reset the time track<br>
	 * - start the game
	 * @throws RPGException if an error occurs
	 */
	@Before
	public void before() throws RPGException {
		FFWoFMController.getInstance().startGame();
		cleaver = ((FFWebServiceClient)
				FFWebServiceClient.getInstance()).getItemByName("Orcish cleaver");
	}
	/**
	 * Test that the IO can be created.
	 * @throws RPGException if an error occurs
	 */
	@Test
	public void canCreateCleaver() throws RPGException {
		assertNotNull(cleaver);
		assertEquals("none",
				cleaver.getScript().getLocalStringVariableValue("reagent"));
		assertEquals(1,
				cleaver.getScript().getLocalIntVariableValue("poisonable"));
	}
	/**
	 * Test that the IO can be broken through scripted events.
	 * @throws RPGException if an error occurs
	 */
	@Test
	public void canBreak() throws RPGException {
		Script.getInstance().sendIOScriptEvent(cleaver, 0, null, "break");
		assertFalse(Interactive.getInstance().hasIO(cleaver.getRefId()));
		assertFalse(Interactive.getInstance().hasIO(cleaver));
	}
}
