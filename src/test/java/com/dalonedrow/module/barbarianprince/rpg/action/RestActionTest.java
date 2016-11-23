package com.dalonedrow.module.barbarianprince.rpg.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.module.barbarianprince.graph.Hex;
import com.dalonedrow.module.barbarianprince.graph.HexMap;
import com.dalonedrow.module.barbarianprince.rpg.BpInteractiveObject;
import com.dalonedrow.module.barbarianprince.rpg.BpIoGroup;
import com.dalonedrow.module.barbarianprince.systems.BpController;
import com.dalonedrow.module.barbarianprince.systems.BpInteractive;
import com.dalonedrow.module.barbarianprince.systems.TestMain;
import com.dalonedrow.module.barbarianprince.turn.TimePhase;
import com.dalonedrow.module.barbarianprince.turn.TimeTrack;
import com.dalonedrow.module.barbarianprince.ui.console.GUI;
import com.dalonedrow.rpg.base.consoleui.InputEvent;
import com.dalonedrow.rpg.base.flyweights.ErrorMessage;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.graph.HexCoordinateSystem;

/**
 * 
 * @author drau
 *
 */
public final class RestActionTest {
	/** game cycle test object. */
	private TestMain t;
	/**
	 * Execute before all tests.
	 * @throws RPGException if an error occurs
	 */
	@Before
	public void before() throws RPGException {
		t = new TestMain();
		BpController.getInstance().startGame();
		RestAction.getInstance().reset();
		RestAction.getInstance().setDebug(true);
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
		// run through cycle. resolves action after game update
		// - no scripts run
		// - updates bpcontroller. action was resolved, move to next phase
		// - no rendering change
		// - no user input
		t.gameCycle();
		// now in phase 1
	}
	/**
	 * Tests that a rest action will advance the time track and heal a
	 * wounded character.
	 * @throws RPGException if an error occurs
	 */
	@Test
	public void canRestWithoutEvent() throws RPGException {
		try {
			assertEquals(TimeTrack.getInstance().getTime(),
					TimePhase.PHASE_01_ACTION);
			// give io 2 wounds
			BpInteractiveObject io =
					((BpInteractive) Interactive.getInstance()).newMob(5);
			BpController.getInstance().addIOToParty(io.getRefId());
			io.getNPCData().adjustAttributeModifier("WO", 2);
			// give io2 4 wounds
			BpInteractiveObject io2 =
					((BpInteractive) Interactive.getInstance()).newMob(5);
			BpController.getInstance().addIOToParty(io2.getRefId());
			io2.getNPCData().adjustAttributeModifier("WO", 4);
			// give io3 4 wounds
			BpInteractiveObject io3 =
					((BpInteractive) Interactive.getInstance()).newMob(5);
			io3.getNPCData().adjustAttributeModifier("WO", 1);
			assertEquals((int) io.getNPCData().getFullAttributeScore("WO"), 2);
			assertEquals((int) io2.getNPCData().getFullAttributeScore("WO"), 4);
			assertEquals((int) io3.getNPCData().getFullAttributeScore("WO"), 1);
			RestAction.getInstance().reset();
			BpController.getInstance().setTurnAction(RestAction.getInstance());
			InputEvent.getInstance().addAction(
					BpController.getInstance(), // object
					BpController.getInstance().getClass().getMethod(
							"executeTurnAction", (Class[]) null),
					null);
			t.gameCycle();
			RestAction.getInstance().setResolved(true);
			t.gameCycle();
			assertEquals(TimeTrack.getInstance().getTime(),
					TimePhase.PHASE_02_POST_ACTION);
			// the two ios in the group healed
			assertEquals((int) io.getNPCData().getFullAttributeScore("WO"), 1);
			assertEquals((int) io2.getNPCData().getFullAttributeScore("WO"), 3);
			// io3 was not in the group and did not heal
			assertEquals((int) io3.getNPCData().getFullAttributeScore("WO"), 1);
			int last = GUI.getInstance().getMessages().length - 1;
			assertEquals(GUI.getInstance().getMessages()[last],
					"Cal Arath's Army rests for the day.");
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT, e);
		}
	}
	/**
	 * Tests that a rest action will advance the time track and heal a
	 * wounded character.
	 * @throws RPGException if an error occurs
	 */
	@Test
	public void cannotRestWithEvent() throws RPGException {
		try {
			assertEquals(TimeTrack.getInstance().getTime(),
					TimePhase.PHASE_01_ACTION);
			// give io 2 wounds
			BpInteractiveObject io =
					((BpInteractive) Interactive.getInstance()).newMob(5);
			BpController.getInstance().addIOToParty(io.getRefId());
			io.getNPCData().adjustAttributeModifier("WO", 2);
			// give io2 4 wounds
			BpInteractiveObject io2 =
					((BpInteractive) Interactive.getInstance()).newMob(5);
			BpController.getInstance().addIOToParty(io2.getRefId());
			io2.getNPCData().adjustAttributeModifier("WO", 4);
			RestAction.getInstance().reset();
			RestAction.getInstance().setEventDebugging(true);
			BpController.getInstance().setTurnAction(RestAction.getInstance());
			InputEvent.getInstance().addAction(
					BpController.getInstance(), // object
					BpController.getInstance().getClass().getMethod(
							"executeTurnAction", (Class[]) null),
					null);
			t.gameCycle();
			System.out.println("setting resolved");
			RestAction.getInstance().setResolved(true);
			t.gameCycle();
			assertEquals(TimeTrack.getInstance().getTime(),
					TimePhase.PHASE_02_POST_ACTION);
			// the two ios in the group did not heal
			assertEquals((int) io.getNPCData().getFullAttributeScore("WO"), 2);
			assertEquals((int) io2.getNPCData().getFullAttributeScore("WO"), 4);
			int last = GUI.getInstance().getMessages().length - 1;
			assertEquals(GUI.getInstance().getMessages()[last],
					"Cal Arath's Army's rest was interrupted!");
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT, e);
		}
	}
	/**
	 * Tests that a rest action will not heal a poisoned character.
	 * @throws RPGException if an error occurs
	 */
	@Test
	public void restWillNotHealPoison() throws RPGException {
		try {
			assertEquals(TimeTrack.getInstance().getTime(),
					TimePhase.PHASE_01_ACTION);
			// give io 2 wounds
			BpInteractiveObject io =
					((BpInteractive) Interactive.getInstance()).newMob(5);
			BpController.getInstance().addIOToParty(io.getRefId());
			io.getNPCData().adjustAttributeModifier("PW", 2);
			// give io2 4 wounds
			BpInteractiveObject io2 =
					((BpInteractive) Interactive.getInstance()).newMob(5);
			BpController.getInstance().addIOToParty(io2.getRefId());
			io2.getNPCData().adjustAttributeModifier("PW", 4);
			// give io3 4 wounds
			BpInteractiveObject io3 =
					((BpInteractive) Interactive.getInstance()).newMob(5);
			io3.getNPCData().adjustAttributeModifier("PW", 1);
			assertEquals((int) io.getNPCData().getFullAttributeScore("PW"), 2);
			assertEquals((int) io2.getNPCData().getFullAttributeScore("PW"), 4);
			assertEquals((int) io3.getNPCData().getFullAttributeScore("PW"), 1);
			RestAction.getInstance().reset();
			BpController.getInstance().setTurnAction(RestAction.getInstance());
			InputEvent.getInstance().addAction(
					BpController.getInstance(), // object
					BpController.getInstance().getClass().getMethod(
							"executeTurnAction", (Class[]) null),
					null);
			t.gameCycle();
			RestAction.getInstance().setResolved(true);
			t.gameCycle();
			assertEquals(TimeTrack.getInstance().getTime(),
					TimePhase.PHASE_02_POST_ACTION);
			// the two ios in the group healed
			assertEquals((int) io.getNPCData().getFullAttributeScore("PW"), 2);
			assertEquals((int) io2.getNPCData().getFullAttributeScore("PW"), 4);
			// io3 was not in the group and did not heal
			assertEquals((int) io3.getNPCData().getFullAttributeScore("PW"), 1);
			int last = GUI.getInstance().getMessages().length - 1;
			assertEquals(GUI.getInstance().getMessages()[last],
					"Cal Arath's Army rests for the day.");
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT, e);
		}
	}
	private void moveToLocation(final int dest) throws RPGException {
		try {
			MoveAction.getInstance().reset();
			MoveAction.getInstance().setDestination(dest);
			BpController.getInstance().setTurnAction(MoveAction.getInstance());
			InputEvent.getInstance().addAction(
					BpController.getInstance(), // object
					BpController.getInstance().getClass().getMethod(
							"executeTurnAction", (Class[]) null),
					null);
			assertFalse(MoveAction.getInstance().isResolved());
			System.out.println("fire");
			t.gameCycle();
			assertFalse(MoveAction.getInstance().isResolved());
			t.gameCycle();
			int last = GUI.getInstance().getMessages().length - 1;
			System.out.println(GUI.getInstance().getMessages()[last]);
			assertEquals(BpController.getInstance().getPartyLocation(), dest);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT, e);
		}
	}
}
