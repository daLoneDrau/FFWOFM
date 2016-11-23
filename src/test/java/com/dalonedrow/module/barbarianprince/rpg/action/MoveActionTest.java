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
import com.dalonedrow.module.barbarianprince.ui.console.GUI;
import com.dalonedrow.rpg.base.consoleui.InputEvent;
import com.dalonedrow.rpg.base.flyweights.ErrorMessage;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.graph.HexCoordinateSystem;

public class MoveActionTest {
	/** the game cycle instance. */
	private TestMain t;
	/** Run before all tests. */
	@Before
	public void before() throws RPGException {
		t = new TestMain();
		BpController.getInstance().startGame();
		MoveAction.getInstance().reset();
		MoveAction.getInstance().setDebug(true);
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
	@Test
	public void canMove1SpaceAirborne() throws RPGException {
		try {
			int dest = HexMap.getInstance().getNeighbor(
					BpController.getInstance().getPartyLocation(),
					HexCoordinateSystem.DIRECTION_S);
			MoveAction.getInstance().reset();
			MoveAction.getInstance().setAirborne(true);
			MoveAction.getInstance().setDestination(dest);
			BpController.getInstance().setTurnAction(MoveAction.getInstance());
			InputEvent.getInstance().addAction(
					BpController.getInstance(), // object
					BpController.getInstance().getClass().getMethod(
							"executeTurnAction", (Class[]) null),
					null);
			t.gameCycle();
			t.gameCycle();
			assertEquals(BpController.getInstance().getPartyLocation(), dest);
			int last = GUI.getInstance().getMessages().length - 1;
			assertTrue(GUI.getInstance().getMessages()[last].startsWith(
					"Cal Arath's Army lands at "));
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT, e);
		}

		// move 1 space airborne to hex 1,3
		try {
			int dest = HexMap.getInstance().getNeighbor(
					BpController.getInstance().getPartyLocation(),
					HexCoordinateSystem.DIRECTION_S);
			MoveAction.getInstance().reset();
			MoveAction.getInstance().setDestination(dest);
			MoveAction.getInstance().setAirborne(true);
			MoveAction.getInstance().setRafting(false);
			InputEvent.getInstance().addAction(
					MoveAction.getInstance(), // object
					MoveAction.getInstance().getClass().getMethod(
							"execute", (Class[]) null),
					null);
			t.gameCycle();
			assertNotEquals(
					BpController.getInstance().getPartyLocation(), dest);
			t.gameCycle();
			assertEquals(BpController.getInstance().getPartyLocation(), dest);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT, e);
		}
	}
	@Test
	public void canMove1SpaceAirborneWithEvent() throws RPGException {
		try {
			int dest = HexMap.getInstance().getNeighbor(
					BpController.getInstance().getPartyLocation(),
					HexCoordinateSystem.DIRECTION_S);
			MoveAction.getInstance().reset();
			MoveAction.getInstance().setAirborne(true);
			MoveAction.getInstance().setDestination(dest);
			MoveAction.getInstance().setTravelEventDebugging(true);
			BpController.getInstance().setTurnAction(MoveAction.getInstance());
			InputEvent.getInstance().addAction(
					BpController.getInstance(), // object
					BpController.getInstance().getClass().getMethod(
							"executeTurnAction", (Class[]) null),
					null);
			t.gameCycle();
			t.gameCycle();
			assertEquals(BpController.getInstance().getPartyLocation(), dest);
			int last = GUI.getInstance().getMessages().length - 1;
			assertTrue(GUI.getInstance().getMessages()[last].startsWith(
					"Cal Arath's Army lands at "));
			t.gameCycle();
			t.gameCycle();
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT, e);
		}
	}
	@Test
	public void canMove1SpaceByGround() throws RPGException {
		try {
			moveToLocation(HexMap.getInstance().getHex(1, 2).getIndex());
			int dest = HexMap.getInstance().getNeighbor(
					BpController.getInstance().getPartyLocation(),
					HexCoordinateSystem.DIRECTION_S);
			MoveAction.getInstance().reset();
			MoveAction.getInstance().setDestination(dest);
			BpController.getInstance().setTurnAction(MoveAction.getInstance());
			InputEvent.getInstance().addAction(
					BpController.getInstance(), // object
					BpController.getInstance().getClass().getMethod(
							"executeTurnAction", (Class[]) null),
					null);
			assertFalse(MoveAction.getInstance().isResolved());
			t.gameCycle();
			assertFalse(MoveAction.getInstance().isResolved());
			t.gameCycle();
			assertEquals(BpController.getInstance().getPartyLocation(), dest);
			int last = GUI.getInstance().getMessages().length - 1;
			System.out.println(GUI.getInstance().getMessages()[last]);
			assertEquals(GUI.getInstance().getMessages()[last],
					"Cal Arath's Army travels to Mountains (1, 3).");
			assertTrue(MoveAction.getInstance().isResolved());
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT, e);
		}
	}
	@Test
	public void canMove1SpaceByGroundWithEvent() throws RPGException {
		try {
			assertNotEquals(BpController.getInstance().getPartyLocation(), -1);
			int last = GUI.getInstance().getMessages().length - 1;
			assertTrue(GUI.getInstance().getMessages()[last].startsWith(
					"Cal Arath's Army arrives at "));
			int dest = HexMap.getInstance().getNeighbor(
					BpController.getInstance().getPartyLocation(),
					HexCoordinateSystem.DIRECTION_S);
			MoveAction.getInstance().reset();
			MoveAction.getInstance().setDestination(dest);
			MoveAction.getInstance().setTravelEventDebugging(true);
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
			assertEquals(BpController.getInstance().getPartyLocation(), dest);
			last = GUI.getInstance().getMessages().length - 1;
			System.out.println(GUI.getInstance().getMessages()[last]);
			assertTrue(GUI.getInstance().getMessages()[last].startsWith(
					"Cal Arath's Army crosses the Tragoth River and reaches "));
			assertTrue(MoveAction.getInstance().isResolved());
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT, e);
		}
	}
	@Test
	public void canMove1SpaceByRoad() throws RPGException {
		try {
			moveToLocation(HexMap.getInstance().getHex(1, 9).getIndex());
			MoveAction.getInstance().reset();
			MoveAction.getInstance().setDestination(
					HexMap.getInstance().getHex(2, 8));
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
			assertEquals(BpController.getInstance().getPartyLocation(), 30);
			assertEquals(GUI.getInstance().getMessages()[last],
					"Cal Arath's Army follows the road to Countryside "
							+ "(2, 8).");
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT, e);
		}
	}
	@Test
	public void canMove1SpaceByRoadWithEvent() throws RPGException {
		try {
			moveToLocation(HexMap.getInstance().getHex(1, 9).getIndex());
			MoveAction.getInstance().reset();
			MoveAction.getInstance().setTravelEventDebugging(true);
			MoveAction.getInstance().setDestination(
					HexMap.getInstance().getHex(2, 8));
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
			assertEquals(BpController.getInstance().getPartyLocation(), 30);
			assertEquals(GUI.getInstance().getMessages()[last],
					"Cal Arath's Army follows the road to Countryside "
							+ "(2, 8).");
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT, e);
		}
	}
	@Test
	public void canMove2SpacesAirborne() throws RPGException {
		try {
			int dest = HexMap.getInstance().getNeighbor(
					BpController.getInstance().getPartyLocation(),
					HexCoordinateSystem.DIRECTION_S);
			dest = HexMap.getInstance().getNeighbor(
					dest,
					HexCoordinateSystem.DIRECTION_S);
			MoveAction.getInstance().reset();
			MoveAction.getInstance().setAirborne(true);
			MoveAction.getInstance().setDestination(dest);
			BpController.getInstance().setTurnAction(MoveAction.getInstance());
			InputEvent.getInstance().addAction(
					BpController.getInstance(), // object
					BpController.getInstance().getClass().getMethod(
							"executeTurnAction", (Class[]) null),
					null);
			t.gameCycle();
			t.gameCycle();
			assertEquals(BpController.getInstance().getPartyLocation(), dest);
			int last = GUI.getInstance().getMessages().length - 1;
			assertTrue(GUI.getInstance().getMessages()[last].startsWith(
					"Cal Arath's Army lands at "));
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT, e);
		}
	}
	@Test
	public void canMove2SpacesByGround() throws RPGException {
		moveToLocation(1);
		try {
			MoveAction.getInstance().reset();
			MoveAction.getInstance().setDestination(
					HexMap.getInstance().getHex(3, 3));
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
			assertEquals(BpController.getInstance().getPartyLocation(), 48);
			int last = GUI.getInstance().getMessages().length - 1;
			System.out.println(GUI.getInstance().getMessages()[last]);
			assertEquals(GUI.getInstance().getMessages()[last],
					"Cal Arath's Army travels to Forest (3, 3).");
			assertTrue(MoveAction.getInstance().isResolved());
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT, e);
		}
	}
	@Test
	public void canMove2SpacesByRoad() throws RPGException {
		moveToLocation(8);
		try {
			MoveAction.getInstance().reset();
			MoveAction.getInstance().setDestination(
					HexMap.getInstance().getHex(2, 7));
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
			assertEquals(BpController.getInstance().getPartyLocation(), 29);
			int last = GUI.getInstance().getMessages().length - 1;
			System.out.println(GUI.getInstance().getMessages()[last]);
			assertEquals(GUI.getInstance().getMessages()[last],
					"Cal Arath's Army follows the road to Badlands "
							+ "(2, 7).");
			assertTrue(MoveAction.getInstance().isResolved());
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT, e);
		}
	}
	@Test
	public void canMove3SpacesAirborne() throws RPGException {
		moveToLocation(1);
		try {
			MoveAction.getInstance().reset();
			MoveAction.getInstance().setDestination(
					HexMap.getInstance().getHex(2, 4));
			MoveAction.getInstance().setAirborne(true);
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
			assertEquals(BpController.getInstance().getPartyLocation(), 26);
			int last = GUI.getInstance().getMessages().length - 1;
			System.out.println(GUI.getInstance().getMessages()[last]);
			assertEquals(GUI.getInstance().getMessages()[last],
					"Cal Arath's Army lands at Badlands (2, 4).");
			assertTrue(MoveAction.getInstance().isResolved());
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT, e);
		}
	}
	@Test
	public void canClearLocalGuide() {
		MoveAction.getInstance().setLocalGuide(2);
		MoveAction.getInstance().clearLocalGuide();
	}
	@Test
	public void canMoveAcrossRiver() throws RPGException {
		moveToLocation(HexMap.getInstance().getHex(1, 2).getIndex());
		try {
			MoveAction.getInstance().reset();
			MoveAction.getInstance().setDestination(
					HexMap.getInstance().getHex(2, 1));
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
			assertEquals(BpController.getInstance().getPartyLocation(),
					HexMap.getInstance().getHex(2, 1).getIndex());
			assertEquals(GUI.getInstance().getMessages()[last],
					"Cal Arath's Army crosses the Tragoth River and "
							+ "reaches Mountains (2, 1).");
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT, e);
		}
	}
	@Test
	public void canMoveAcrossRiverWithEvent() throws RPGException {
		moveToLocation(1);
		try {
			MoveAction.getInstance().reset();
			MoveAction.getInstance().setDestination(
					HexMap.getInstance().getHex(2, 1));
			MoveAction.getInstance().setTravelEventDebugging(true);
			BpController.getInstance().setTurnAction(MoveAction.getInstance());
			InputEvent.getInstance().addAction(
					BpController.getInstance(), // object
					BpController.getInstance().getClass().getMethod(
							"executeTurnAction", (Class[]) null),
					null);
			assertFalse(MoveAction.getInstance().isResolved());
			t.gameCycle();
			assertFalse(MoveAction.getInstance().isResolved());
			t.gameCycle();
			assertEquals(BpController.getInstance().getPartyLocation(),
					HexMap.getInstance().getHex(2, 1).getIndex());
			int last = GUI.getInstance().getMessages().length - 1;
			assertEquals(GUI.getInstance().getMessages()[last],
					"Cal Arath's Army crosses the Tragoth River and "
							+ "reaches Mountains (2, 1).");
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT, e);
		}
	}
	@Test
	public void canMoveByRaft() throws RPGException {
		moveToLocation(14);
		try {
			MoveAction.getInstance().reset();
			MoveAction.getInstance().setDestination(
					HexMap.getInstance().getHex(2, 13));
			MoveAction.getInstance().setRafting(true);
			BpController.getInstance().setTurnAction(MoveAction.getInstance());
			InputEvent.getInstance().addAction(
					BpController.getInstance(), // object
					BpController.getInstance().getClass().getMethod(
							"executeTurnAction", (Class[]) null),
					null);
			assertFalse(MoveAction.getInstance().isResolved());
			t.gameCycle();
			assertFalse(MoveAction.getInstance().isResolved());
			t.gameCycle();
			assertEquals(BpController.getInstance().getPartyLocation(),
					HexMap.getInstance().getHex(2, 13).getIndex());
			int last = GUI.getInstance().getMessages().length - 1;
			assertEquals(GUI.getInstance().getMessages()[last],
					"Cal Arath's Army rafts the Nesser River to Llewyla Moor "
							+ "(2, 13).");
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT, e);
		}
	}
	@Test
	public void canMoveByRaftWithEvent() throws RPGException {
		moveToLocation(HexMap.getInstance().getHex(1, 15).getIndex());
		assertEquals(BpController.getInstance().getPartyLocation(), 14);
		try {
			MoveAction.getInstance().reset();
			MoveAction.getInstance().setRafting(true);
			MoveAction.getInstance().setTravelEventDebugging(true);
			MoveAction.getInstance().setDestination(
					HexMap.getInstance().getHex(2, 13));
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
			assertEquals(BpController.getInstance().getPartyLocation(), 35);
			assertEquals(GUI.getInstance().getMessages()[last],
					"Cal Arath's Army rafts the Nesser River to Llewyla Moor "
							+ "(2, 13).");
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT, e);
		}
	}
	@Test
	public void gettingLostCreatesDesertionEvent() throws RPGException {
		moveToLocation(0);
		try {
			MoveAction.getInstance().reset();
			BpInteractiveObject io =
					((BpInteractive) Interactive.getInstance()).newMob(5);
			BpController.getInstance().addIOToParty(io.getRefId());
			MoveAction.getInstance().setLostDebugging(true);
			MoveAction.getInstance().setLocalGuideDeserts(true);
			MoveAction.getInstance().setLocalGuide(io.getRefId());
			MoveAction.getInstance().setDestination(1);
			BpController.getInstance().setTurnAction(MoveAction.getInstance());
			InputEvent.getInstance().addAction(
					BpController.getInstance(), // object
					BpController.getInstance().getClass().getMethod(
							"executeTurnAction", (Class[]) null),
					null);
			assertFalse(MoveAction.getInstance().isResolved());
			t.gameCycle();
			assertFalse(MoveAction.getInstance().isResolved());
			t.gameCycle();
			int last = GUI.getInstance().getMessages().length - 1;
			assertEquals(BpController.getInstance().getPartyLocation(), 0);
			assertEquals(GUI.getInstance().getMessages()[last],
					"Amazon abandons the party out of shame after getting "
							+ "hopelessly lost.");
			assertFalse(((BpIoGroup) Interactive.getInstance().getIO(
					BpController.getInstance().getPlayerParty())).hasMember(
							io.getRefId()));
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
	@Test
	public void willNotMove2SpaceAirborneWithEvent() throws RPGException {
		moveToLocation(1);
		try {
			MoveAction.getInstance().reset();
			MoveAction.getInstance().setDestination(
					HexMap.getInstance().getHex(1, 4).getIndex());
			MoveAction.getInstance().setAirborne(true);
			MoveAction.getInstance().setTravelEventDebugging(true);
			BpController.getInstance().setTurnAction(MoveAction.getInstance());
			InputEvent.getInstance().addAction(
					BpController.getInstance(), // object
					BpController.getInstance().getClass().getMethod(
							"executeTurnAction", (Class[]) null),
					null);
			assertFalse(MoveAction.getInstance().isResolved());
			t.gameCycle();
			assertFalse(MoveAction.getInstance().isResolved());
			t.gameCycle();
			assertEquals(BpController.getInstance().getPartyLocation(), 2);
			int last = GUI.getInstance().getMessages().length - 1;
			assertEquals(GUI.getInstance().getMessages()[last],
					"Cal Arath's Army flies over Mountains (1, 3).");
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT, e);
		}
	}
	@Test
	public void willNotMove2SpacesByGroundWithEvent() throws RPGException {
		moveToLocation(1);
		try {
			MoveAction.getInstance().reset();
			MoveAction.getInstance().setDestination(
					HexMap.getInstance().getHex(3, 3));
			MoveAction.getInstance().setTravelEventDebugging(true);
			BpController.getInstance().setTurnAction(MoveAction.getInstance());
			InputEvent.getInstance().addAction(
					BpController.getInstance(), // object
					BpController.getInstance().getClass().getMethod(
							"executeTurnAction", (Class[]) null),
					null);
			assertFalse(MoveAction.getInstance().isResolved());
			t.gameCycle();
			assertFalse(MoveAction.getInstance().isResolved());
			t.gameCycle();
			assertNotEquals(BpController.getInstance().getPartyLocation(),
					HexMap.getInstance().getHex(3, 3).getIndex());
			int last = GUI.getInstance().getMessages().length - 1;
			assertEquals(GUI.getInstance().getMessages()[last],
					"Cal Arath's Army travels to Forest (2, 2).");
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT, e);
		}
	}
	@Test
	public void willNotMove2SpacesByRoadWithEvent() throws RPGException {
		moveToLocation(8);
		try {
			MoveAction.getInstance().reset();
			MoveAction.getInstance().setDestination(
					HexMap.getInstance().getHex(2, 7));
			MoveAction.getInstance().setTravelEventDebugging(true);
			BpController.getInstance().setTurnAction(MoveAction.getInstance());
			InputEvent.getInstance().addAction(
					BpController.getInstance(), // object
					BpController.getInstance().getClass().getMethod(
							"executeTurnAction", (Class[]) null),
					null);
			assertFalse(MoveAction.getInstance().isResolved());
			t.gameCycle();
			assertFalse(MoveAction.getInstance().isResolved());
			t.gameCycle();
			assertNotEquals(BpController.getInstance().getPartyLocation(),
					HexMap.getInstance().getHex(2, 7).getIndex());
			int last = GUI.getInstance().getMessages().length - 1;
			assertEquals(GUI.getInstance().getMessages()[last],
					"Cal Arath's Army follows the road to Countryside "
							+ "(2, 8).");
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT, e);
		}
	}
	@Test
	public void willNotMove3SpaceAirborneWithEvent() throws RPGException {
		moveToLocation(1);
		try {
			MoveAction.getInstance().reset();
			MoveAction.getInstance().setDestination(
					HexMap.getInstance().getHex(2, 4).getIndex());
			MoveAction.getInstance().setAirborne(true);
			MoveAction.getInstance().setTravelEventDebugging(true);
			BpController.getInstance().setTurnAction(MoveAction.getInstance());
			InputEvent.getInstance().addAction(
					BpController.getInstance(), // object
					BpController.getInstance().getClass().getMethod(
							"executeTurnAction", (Class[]) null),
					null);
			assertFalse(MoveAction.getInstance().isResolved());
			t.gameCycle();
			assertFalse(MoveAction.getInstance().isResolved());
			t.gameCycle();
			assertEquals(BpController.getInstance().getPartyLocation(), 2);
			int last = GUI.getInstance().getMessages().length - 1;
			assertEquals(GUI.getInstance().getMessages()[last],
					"Cal Arath's Army flies over Mountains (1, 3).");
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT, e);
		}
	}
	@Test
	public void willNotMoveWhenLostAirborne() throws RPGException {
		moveToLocation(0);
		try {
			MoveAction.getInstance().reset();
			MoveAction.getInstance().setAirborne(true);
			MoveAction.getInstance().setLostDebugging(true);
			MoveAction.getInstance().setDestination(1);
			BpController.getInstance().setTurnAction(MoveAction.getInstance());
			InputEvent.getInstance().addAction(
					BpController.getInstance(), // object
					BpController.getInstance().getClass().getMethod(
							"executeTurnAction", (Class[]) null),
					null);
			assertFalse(MoveAction.getInstance().isResolved());
			t.gameCycle();
			assertFalse(MoveAction.getInstance().isResolved());
			t.gameCycle();
			int last = GUI.getInstance().getMessages().length - 1;
			assertEquals(BpController.getInstance().getPartyLocation(), 0);
			assertEquals(GUI.getInstance().getMessages()[last],
					"Cal Arath's Army tries to fly, but the cloud cover is too "
							+ "thick.");
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT, e);
		}
	}
	@Test
	public void willNotMoveWhenLostAirborneDrift() throws RPGException {
		moveToLocation(0);
		try {
			MoveAction.getInstance().reset();
			MoveAction.getInstance().setAirborne(true);
			MoveAction.getInstance().setDriftDebugging(true);
			MoveAction.getInstance().setLostDebugging(true);
			MoveAction.getInstance().setDestination(1);
			BpController.getInstance().setTurnAction(MoveAction.getInstance());
			InputEvent.getInstance().addAction(
					BpController.getInstance(), // object
					BpController.getInstance().getClass().getMethod(
							"executeTurnAction", (Class[]) null),
					null);
			assertFalse(MoveAction.getInstance().isResolved());
			t.gameCycle();
			assertFalse(MoveAction.getInstance().isResolved());
			t.gameCycle();
			int last = GUI.getInstance().getMessages().length - 1;
			assertNotEquals(BpController.getInstance().getPartyLocation(), 0);
			assertNotEquals(BpController.getInstance().getPartyLocation(), 1);
			assertTrue(GUI.getInstance().getMessages()[last].contains(
					"takes off in flight, but strong winds blow them off "
							+ "course, forcing them to land"));
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT, e);
		}
	}
	@Test
	public void willNotMoveWhenLostByGround() throws RPGException {
		moveToLocation(HexMap.getInstance().getHex(1, 2).getIndex());
		try {
			MoveAction.getInstance().reset();
			MoveAction.getInstance().setDestination(
					HexMap.getInstance().getHex(1, 3).getIndex());
			MoveAction.getInstance().setLostDebugging(true);
			BpController.getInstance().setTurnAction(MoveAction.getInstance());
			InputEvent.getInstance().addAction(
					BpController.getInstance(), // object
					BpController.getInstance().getClass().getMethod(
							"executeTurnAction", (Class[]) null),
					null);
			assertFalse(MoveAction.getInstance().isResolved());
			t.gameCycle();
			assertFalse(MoveAction.getInstance().isResolved());
			t.gameCycle();
			assertEquals(BpController.getInstance().getPartyLocation(),
					HexMap.getInstance().getHex(1, 2).getIndex());
			int last = GUI.getInstance().getMessages().length - 1;
			assertEquals(GUI.getInstance().getMessages()[last],
					"Cal Arath's Army attempts to leave Countryside (1, 2), "
							+ "but becomes lost.");
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT, e);
		}
	}
	@Test
	public void willNotMoveWhenLostCrossingRiver() throws RPGException {
		moveToLocation(HexMap.getInstance().getHex(1, 2).getIndex());
		try {
			MoveAction.getInstance().reset();
			MoveAction.getInstance().setDestination(
					HexMap.getInstance().getHex(1, 1).getIndex());
			MoveAction.getInstance().setLostDebugging(true);
			BpController.getInstance().setTurnAction(MoveAction.getInstance());
			InputEvent.getInstance().addAction(
					BpController.getInstance(), // object
					BpController.getInstance().getClass().getMethod(
							"executeTurnAction", (Class[]) null),
					null);
			assertFalse(MoveAction.getInstance().isResolved());
			t.gameCycle();
			assertFalse(MoveAction.getInstance().isResolved());
			t.gameCycle();
			assertEquals(BpController.getInstance().getPartyLocation(),
					HexMap.getInstance().getHex(1, 2).getIndex());
			int last = GUI.getInstance().getMessages().length - 1;
			assertEquals(GUI.getInstance().getMessages()[last],
					"Cal Arath's Army attempts to cross the Tragoth River, "
							+ "but cannot find a suitable place to ford.");
		} catch (NoSuchMethodException | SecurityException e) {
			throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT, e);
		}
	}
}
