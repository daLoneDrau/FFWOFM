package com.dalonedrow.module.barbarianprince.systems;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.module.barbarianprince.log.HeroLog;
import com.dalonedrow.module.barbarianprince.rpg.BpInteractiveObject;
import com.dalonedrow.module.barbarianprince.rpg.action.PreDawnAction;
import com.dalonedrow.module.barbarianprince.turn.TimePhase;
import com.dalonedrow.module.barbarianprince.turn.TimeTrack;
import com.dalonedrow.module.barbarianprince.ui.console.GUI;
import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.rpg.base.consoleui.InputEvent;
import com.dalonedrow.rpg.base.flyweights.RPGException;

@SuppressWarnings("unchecked")
public class BPControllerTest {
	private TestMain t;
	@Before
	public void before() throws RPGException {
		t = new TestMain();
		TimeTrack.getInstance().reset();
	}
	@Test
	public void canGetLocalGuides() throws RPGException, PooledException {
		BpController.getInstance().startGame();
		int[] guides = BpController.getInstance().getLocalGuides(5);
		assertEquals(guides.length, 0);
		BpInteractiveObject io =
				((BpInteractive) Interactive.getInstance()).newMob(4);
		BpController.getInstance().addIOToParty(io.getRefId());
		guides = BpController.getInstance().getLocalGuides(5);
		assertEquals(guides.length, 0);
		io.getNPCData().addLocalGuideHex(5);
		guides = BpController.getInstance().getLocalGuides(5);
		assertEquals(guides.length, 1);
		guides = BpController.getInstance().getLocalGuides(12);
		assertEquals(guides.length, 0);
		io = ((BpInteractive) Interactive.getInstance()).newMob(5);
		BpController.getInstance().addIOToParty(io.getRefId());
		guides = BpController.getInstance().getLocalGuides(12);
		assertEquals(guides.length, 0);
		io.getNPCData().addLocalGuideHex(12);
		guides = BpController.getInstance().getLocalGuides(12);
		assertEquals(guides.length, 1);
	}
	@Test
	public void canGetNewPlayer() throws RPGException, PooledException {
		assertNotNull(BpController.getInstance());
		((BpInteractive) Interactive.getInstance()).newPlayer();
		BpInteractiveObject io =
				(BpInteractiveObject) Interactive.getInstance().getIO(
						((BpInteractive) Interactive.getInstance())
								.getPlayerIO());
		assertNotNull(io);
		Interactive.getInstance().releaseIO(io);
	}
	/*
	 * @Test(expected = RPGException.class) public void willNotMoveBeforeStart()
	 * throws RPGException, PooledException {
	 * BPController.getInstance().movePlayer(0); }
	 */
	@Test
	public void canInitialize() throws RPGException {
		assertNotNull(BpController.getInstance());
	}
	@Test
	public void event002OccursDaily() throws PooledException, RPGException {
		gameStarts();
		for (int i = 35; i >= 0; i--) {
			try {
				InputEvent.getInstance().addAction(
						TimeTrack.getInstance(), // object
						TimeTrack.getInstance().getClass().getMethod(
								"nextPhase", (Class[]) null),
						null);
			} catch (NoSuchMethodException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			t.gameCycle();
		}
		String s = (String) HeroLog.getInstance().getEntry(
				HeroLog.getInstance().getNumberOfEntries() - 1).getValue();
		assertTrue(s.endsWith("Cal Arath's Army encountered a group of the "
				+ "usurpers' hired thugs.<removeLF>"));

	}
	@Test
	public void gameStarts() throws RPGException, PooledException {
		BpController.getInstance().startGame();
		// in phase 0
		assertEquals(TimeTrack.getInstance().getTime(),
				TimePhase.PHASE_00_PRE_ACTION);
		// party has not moved
		assertEquals(BpController.getInstance().getPartyLocation(), -1);
		// add user input event
		try {
			InputEvent.getInstance().addAction(
					PreDawnAction.getInstance(), // object
					PreDawnAction.getInstance().getClass().getMethod(
							"setResolved", new Class[] { boolean.class }),
					new Boolean[] { true });
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// run one game cycle for phase 0
		// - runs event 001 script. script sets phase action as PreDawnAction
		// - updates bpcontroller. no changes
		// - renders e001 message to screen (NOT DURING TESTING)
		// - processes for user input (NOT DURING TESTING)
		t.gameCycle();
		// after game cycle:
		// party has moved
		assertNotEquals(BpController.getInstance().getPartyLocation(), -1);
		// message was sent to render
		int last = GUI.getInstance().getMessages().length - 1;
		assertTrue(GUI.getInstance().getMessages()[last].startsWith(
				"Cal Arath's Army arrives at "));
		// still in phase 0 because controller hasnt updated
		assertEquals(TimeTrack.getInstance().getTime(),
				TimePhase.PHASE_00_PRE_ACTION);
		// run through cycle. resolves action after game update
		// - no scripts run
		// - updates bpcontroller. action was resolved, move to next phase
		// - no rendering change
		// - no user input
		t.gameCycle();
		// now in phase 1
		assertEquals(TimeTrack.getInstance().getTime(),
				TimePhase.PHASE_01_ACTION);
	}
}
