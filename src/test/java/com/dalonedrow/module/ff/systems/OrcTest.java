package com.dalonedrow.module.ff.systems;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.dalonedrow.module.ff.rpg.FFInteractiveObject;
import com.dalonedrow.module.ff.systems.wofm.FFWebServiceClient;
import com.dalonedrow.module.ff.systems.wofm.FFWoFMController;
import com.dalonedrow.rpg.base.flyweights.RPGException;

/**
 * Class to test creation and reactions to Orc script
 * @author drau
 *
 */
public class OrcTest {
	/** the test object. */
	private FFInteractiveObject orc1;
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
		orc1 = ((FFWebServiceClient)
				FFWebServiceClient.getInstance()).getNPCByName("ORC1");
	}
	@Test
	public void canCreateOrc() {
		assertNotNull(orc1);
	}
}
