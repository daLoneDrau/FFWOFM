package com.dalonedrow.module.barbarianprince.systems;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dalonedrow.engine.systems.base.ProjectConstants;
import com.dalonedrow.engine.systems.base.Time;
import com.dalonedrow.module.barbarianprince.graph.HexMap;
import com.dalonedrow.module.barbarianprince.rpg.action.FoodAction;
import com.dalonedrow.module.barbarianprince.rpg.action.LodgingAction;
import com.dalonedrow.module.barbarianprince.rpg.action.MoveAction;
import com.dalonedrow.module.barbarianprince.rpg.action.PostDailyActionAction;
import com.dalonedrow.module.barbarianprince.rpg.action.PreDawnAction;
import com.dalonedrow.module.barbarianprince.rpg.action.PreEveningAction;
import com.dalonedrow.module.barbarianprince.rpg.action.PostEveningAction;
import com.dalonedrow.module.barbarianprince.rpg.action.RestAction;
import com.dalonedrow.module.barbarianprince.rpg.action.TurnAction;
import com.dalonedrow.module.barbarianprince.rpg.action.param.MoveActionParameters;
import com.dalonedrow.module.barbarianprince.turn.Day;
import com.dalonedrow.module.barbarianprince.turn.TimePhase;
import com.dalonedrow.module.barbarianprince.turn.TimeTrack;
import com.dalonedrow.module.barbarianprince.turn.Week;
import com.dalonedrow.module.barbarianprince.ui.console.GUI;
import com.dalonedrow.rpg.base.consoleui.InputEvent;
import com.dalonedrow.rpg.base.consoleui.InputProcessor;
import com.dalonedrow.rpg.base.consoleui.OutputEvent;
import com.dalonedrow.rpg.base.flyweights.ErrorMessage;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.systems.Script;
import com.dalonedrow.rpg.graph.HexCoordinateSystem;

/**
 * The main tester for the game.
 * @author drau
 */
public final class TestMain {
	/** flag indicating user input is on. */
	private boolean	inputOn;
	private int		numDaysToTest;
	/**
	 * Run before all tests.<br>
	 * - set debugging flags where needed<br>
	 * - reset the time track<br>
	 * - start the game
	 * @throws RPGException if an error occurs
	 */
	@Before
	public void before() throws RPGException {
		MoveAction.getInstance().setDebug(true);
		RestAction.getInstance().setDebug(true);
		TimeTrack.getInstance().reset();
		BpController.getInstance().startGame();
	}
	/**
	 * Runs through one game cycle.<br>
	 * - start the time for the frame<br>
	 * - update scripts<br>
	 * - update the game controller/advances the time track<br>
	 * - render the screens to the output buffer<br>
	 * - prepare the console for rendering<br>
	 * - write and flush the output<br>
	 * - wait for user input<br>
	 * - process user input
	 * @throws RPGException if an error occurs
	 */
	private void gameCycle() throws RPGException {
		System.out.println("---g-a-m-e---c-y-c-l-e---");
		// mark the start of the frame
		Time.getInstance().startFrame();
		// update scripts
		System.out.println("**event stack execute**");
		Script.getInstance().eventStackExecuteAll();
		// update game controller and possibly advance time track
		System.out.println("**update controller/advance time track**");
		ProjectConstants.getInstance().update();
		// render screens to the output buffer
		System.out.println("**prepare for rendering**");
		ConsoleInterface.getInstance().prepareForRendering();
		// write and flush output
		System.out.println("**render**");
		OutputEvent.getInstance().render();
		// wait for input
		if (inputOn) {
			System.out.println("**accept input**");
			InputProcessor.getInstance().acceptInput();
		}
		// handle user input
		System.out.println("**process input actions**");
		InputEvent.getInstance().processActions();
		System.out.println("---c-y-c-l-e---o-v-e-r---");
	}
	/**
	 * Sets the value of the inputOn flag.
	 * @param flag the new value to set
	 */
	public void setInputOn(final boolean flag) {
		inputOn = flag;
	}
	/**
	 * Sets the value of the numDaysToTest.
	 * @param val the new value to set
	 */
	public void setNumDaysToTest(final int val) {
		this.numDaysToTest = val;
	}
	/**
	 * Test method.
	 */
	@Test
	public void testBP() {
		try {
			BpController.getInstance();
			do {
				gameCycle();
				((BpController) ProjectConstants.getInstance())
						.setGameOver(true);
			} while (!((BpController) ProjectConstants.getInstance())
					.isGameOver());
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail(ex.getMessage());
		}
	}
	/**
	 * Runs two game cycles to execute and resolve all actions in the Pre-Action
	 * phase.
	 * @throws RPGException if an error occurs
	 */
	private void resolvePreActionPhase() throws RPGException {
		if (TimeTrack.getInstance().getTime()
				== TimePhase.PHASE_00_PRE_ACTION) {
			// add user input event
			try {
				InputEvent.getInstance().addAction(
						PreDawnAction.getInstance(), // object
						PreDawnAction.getInstance().getClass().getMethod(
								"setResolved", new Class[] { boolean.class }),
						new Boolean[] { true });
			} catch (NoSuchMethodException | SecurityException e) {
				throw new RPGException(ErrorMessage.INTERNAL_ERROR, e);
			}
			// run two game cycles
			gameCycle();
			gameCycle();
		}
	}
	/**
	 * Runs one game cycle to execute actions in the Action phase.
	 * @throws RPGException if an error occurs
	 */
	private void executeActionPhase() throws RPGException {
		if (TimeTrack.getInstance().getTime() == TimePhase.PHASE_01_ACTION) {
			// add user input event
			try {
				InputEvent.getInstance().addAction(
						BpController.getInstance(), // object
						BpController.getInstance().getClass().getMethod(
								"executeTurnAction", (Class[]) null),
						null);
			} catch (NoSuchMethodException | SecurityException e) {
				throw new RPGException(ErrorMessage.INTERNAL_ERROR, e);
			}
			// run one game cycle
			gameCycle();
		}
	}
	/**
	 * Runs one game cycle to execute actions in the Post-Action phase.
	 * @throws RPGException if an error occurs
	 */
	private void executePostActionPhase() throws RPGException {
		if (TimeTrack.getInstance().getTime()
				== TimePhase.PHASE_02_POST_ACTION) {
			// add user input event
			try {
				InputEvent.getInstance().addAction(
						BpController.getInstance(), // object
						BpController.getInstance().getClass().getMethod(
								"executeTurnAction", (Class[]) null),
						null);
			} catch (NoSuchMethodException | SecurityException e) {
				throw new RPGException(ErrorMessage.INTERNAL_ERROR, e);
			}
			// run one game cycle
			gameCycle();
		}
	}
	/**
	 * Runs one game cycle to execute actions in the Post-Action phase.
	 * @throws RPGException if an error occurs
	 */
	private void executePreEveningPhase() throws RPGException {
		if (TimeTrack.getInstance().getTime()
				== TimePhase.PHASE_03_PRE_EVENING) {
			// add user input event
			try {
				InputEvent.getInstance().addAction(
						BpController.getInstance(), // object
						BpController.getInstance().getClass().getMethod(
								"executeTurnAction", (Class[]) null),
						null);
			} catch (NoSuchMethodException | SecurityException e) {
				throw new RPGException(ErrorMessage.INTERNAL_ERROR, e);
			}
			// run one game cycle
			gameCycle();
		}
	}
	/**
	 * Runs one game cycle to execute actions in the Evening-Food phase.
	 * @throws RPGException if an error occurs
	 */
	private void executeEveningFoodPhase() throws RPGException {
		if (TimeTrack.getInstance().getTime()
				== TimePhase.PHASE_04_EVENING_FOOD) {
			// add user input event
			try {
				InputEvent.getInstance().addAction(
						BpController.getInstance(), // object
						BpController.getInstance().getClass().getMethod(
								"executeTurnAction", (Class[]) null),
						null);
			} catch (NoSuchMethodException | SecurityException e) {
				throw new RPGException(ErrorMessage.INTERNAL_ERROR, e);
			}
			// run one game cycle
			gameCycle();
		}
	}
	/**
	 * Runs one game cycle to execute actions in the Evening-Lodging phase.
	 * @throws RPGException if an error occurs
	 */
	private void executeEveningLodgingPhase() throws RPGException {
		if (TimeTrack.getInstance().getTime()
				== TimePhase.PHASE_05_EVENING_LODGING) {
			// add user input event
			try {
				InputEvent.getInstance().addAction(
						BpController.getInstance(), // object
						BpController.getInstance().getClass().getMethod(
								"executeTurnAction", (Class[]) null),
						null);
			} catch (NoSuchMethodException | SecurityException e) {
				throw new RPGException(ErrorMessage.INTERNAL_ERROR, e);
			}
			// run one game cycle
			gameCycle();
		}
	}
	/**
	 * Runs one game cycle to execute actions in the Post-Evening phase.
	 * @throws RPGException if an error occurs
	 */
	private void executePostEveningPhase() throws RPGException {
		if (TimeTrack.getInstance().getTime()
				== TimePhase.PHASE_06_POST_EVENING) {
			// add user input event
			try {
				InputEvent.getInstance().addAction(
						BpController.getInstance(), // object
						BpController.getInstance().getClass().getMethod(
								"executeTurnAction", (Class[]) null),
						null);
			} catch (NoSuchMethodException | SecurityException e) {
				throw new RPGException(ErrorMessage.INTERNAL_ERROR, e);
			}
			// run one game cycle
			gameCycle();
		}
	}
	/**
	 * Runs one game cycle to resolve actions in the Action phase.
	 * @param action the selected {@link TurnAction}
	 * @throws RPGException if an error occurs
	 */
	private void resolveActionPhase(final TurnAction action)
			throws RPGException {
		if (TimeTrack.getInstance().getTime() == TimePhase.PHASE_01_ACTION) {
			action.setResolved(true);
			gameCycle();
		}
	}
	/**
	 * Runs one game cycle to resolve actions in the Post-Action phase.
	 * @param action the selected {@link TurnAction}
	 * @throws RPGException if an error occurs
	 */
	private void resolvePostActionPhase(final TurnAction action)
			throws RPGException {
		if (TimeTrack.getInstance().getTime()
				== TimePhase.PHASE_02_POST_ACTION) {
			action.setResolved(true);
			gameCycle();
		}
	}
	/**
	 * Runs one game cycle to resolve actions in the Pre-Evening phase.
	 * @param action the selected {@link TurnAction}
	 * @throws RPGException if an error occurs
	 */
	private void resolvePreEveningPhase(final TurnAction action)
			throws RPGException {
		if (TimeTrack.getInstance().getTime()
				== TimePhase.PHASE_03_PRE_EVENING) {
			action.setResolved(true);
			gameCycle();
		}
	}
	/**
	 * Runs one game cycle to resolve actions in the Evening-Food phase.
	 * @param action the selected {@link TurnAction}
	 * @throws RPGException if an error occurs
	 */
	private void resolveEveningFoodPhase(final TurnAction action)
			throws RPGException {
		if (TimeTrack.getInstance().getTime()
				== TimePhase.PHASE_04_EVENING_FOOD) {
			action.setResolved(true);
			gameCycle();
		}
	}
	/**
	 * Runs one game cycle to resolve actions in the Evening-Lodging phase.
	 * @param action the selected {@link TurnAction}
	 * @throws RPGException if an error occurs
	 */
	private void resolveEveningLodgingPhase(final TurnAction action)
			throws RPGException {
		if (TimeTrack.getInstance().getTime()
				== TimePhase.PHASE_05_EVENING_LODGING) {
			action.setResolved(true);
			gameCycle();
		}
	}
	/**
	 * Runs one game cycle to resolve actions in the Post-Evening phase.
	 * @param action the selected {@link TurnAction}
	 * @throws RPGException if an error occurs
	 */
	private void resolvePostEveningPhase(final TurnAction action)
			throws RPGException {
		if (TimeTrack.getInstance().getTime()
				== TimePhase.PHASE_06_POST_EVENING) {
			action.setResolved(true);
			gameCycle();
		}
	}
	/**
	 * Finishes a day with no expected activities/events occurring after the
	 * chosen action.
	 * @throws RPGException if an error occurs
	 */
	private void finishDayWithNoActions() throws RPGException {
		PostDailyActionAction.getInstance().reset();
		BpController.getInstance().setTurnAction(
				PostDailyActionAction.getInstance());
		executePostActionPhase();
		resolvePostActionPhase(PostDailyActionAction.getInstance());
		PreEveningAction.getInstance().reset();
		BpController.getInstance().setTurnAction(
				PreEveningAction.getInstance());
		executePreEveningPhase();
		resolvePreEveningPhase(PreEveningAction.getInstance());
		FoodAction.getInstance().reset();
		BpController.getInstance().setTurnAction(
				FoodAction.getInstance());
		executeEveningFoodPhase();
		resolveEveningFoodPhase(FoodAction.getInstance());
		LodgingAction.getInstance().reset();
		BpController.getInstance().setTurnAction(
				LodgingAction.getInstance());
		executeEveningLodgingPhase();
		resolveEveningLodgingPhase(LodgingAction.getInstance());
		PostEveningAction.getInstance().reset();
		BpController.getInstance().setTurnAction(
				PostEveningAction.getInstance());
		executePostEveningPhase();
		resolvePostEveningPhase(PostEveningAction.getInstance());
	}
	/**
	 * Test that the game can cycle through one day with no activity/events.
	 * @throws RPGException if an error occurs
	 */
	@Test
	public void testMoving1Hex() throws RPGException {
		resolvePreActionPhase();
		assertEquals(TimeTrack.getInstance().getTime(),
				TimePhase.PHASE_01_ACTION);
		int currentLoc = BpController.getInstance().getPartyLocation();
		int dest = HexMap.getInstance().getNeighbor(currentLoc,
				HexCoordinateSystem.DIRECTION_S);
		System.out.println(HexMap.getInstance().getHexById(currentLoc));
		System.out.println(HexMap.getInstance().getHexById(dest));
		assertNotEquals(currentLoc, -1);
		// move south
		MoveAction.getInstance().reset();
		BpController.getInstance().setTurnAction(
				MoveAction.getInstance());
		MoveAction.getInstance().set(new MoveActionParameters(
				false, // not airborne
				dest, // the destination
				false, // no drifting
				-1, // no local guide
				false, // the guide doesn't desert
				false, // no getting lost
				false, // not rafting
				false)); // not travel event
		executeActionPhase();
		currentLoc = BpController.getInstance().getPartyLocation();
		assertEquals(currentLoc, dest);
		assertEquals(TimeTrack.getInstance().getTime(),
				TimePhase.PHASE_01_ACTION);
		resolveActionPhase(RestAction.getInstance());
		assertEquals(TimeTrack.getInstance().getTime(),
				TimePhase.PHASE_02_POST_ACTION);
		// finish the day
		finishDayWithNoActions();
	}
	/**
	 * Test that the game can cycle through one day with no activity/events.
	 * @throws RPGException if an error occurs
	 */
	@Test
	public void testOneDay() throws RPGException {
		// in phase 0
		assertEquals(TimeTrack.getInstance().getTime(),
				TimePhase.PHASE_00_PRE_ACTION);
		// party has not moved
		assertEquals(BpController.getInstance().getPartyLocation(), -1);
		resolvePreActionPhase();
		// after game cycle - party has moved
		assertNotEquals(BpController.getInstance().getPartyLocation(), -1);
		// message was sent to render
		int last = GUI.getInstance().getMessages().length - 1;
		assertTrue(GUI.getInstance().getMessages()[last].startsWith(
				"Cal Arath's Army arrives at "));
		// now in phase 1
		assertEquals(TimeTrack.getInstance().getTime(),
				TimePhase.PHASE_01_ACTION);
		// add action to rest
		RestAction.getInstance().reset();
		BpController.getInstance().setTurnAction(RestAction.getInstance());
		executeActionPhase();
		// still in phase 1
		assertEquals(TimeTrack.getInstance().getTime(),
				TimePhase.PHASE_01_ACTION);
		// resolve action
		resolveActionPhase(RestAction.getInstance());
		// now in phase 2
		assertEquals(TimeTrack.getInstance().getTime(),
				TimePhase.PHASE_02_POST_ACTION);
		// add action to go through phase
		PostDailyActionAction.getInstance().reset();
		BpController.getInstance().setTurnAction(
				PostDailyActionAction.getInstance());
		executePostActionPhase();
		// still in phase 2
		assertEquals(TimeTrack.getInstance().getTime(),
				TimePhase.PHASE_02_POST_ACTION);
		resolvePostActionPhase(PostDailyActionAction.getInstance());
		// now in phase 3
		assertEquals(TimeTrack.getInstance().getTime(),
				TimePhase.PHASE_03_PRE_EVENING);
		// add action to go through phase
		PreEveningAction.getInstance().reset();
		BpController.getInstance().setTurnAction(
				PreEveningAction.getInstance());
		executePreEveningPhase();
		// still in phase 3
		assertEquals(TimeTrack.getInstance().getTime(),
				TimePhase.PHASE_03_PRE_EVENING);
		// resolve last action
		resolvePreEveningPhase(PreEveningAction.getInstance());
		// now in phase 4
		assertEquals(TimeTrack.getInstance().getTime(),
				TimePhase.PHASE_04_EVENING_FOOD);
		// add action to go through phase
		FoodAction.getInstance().reset();
		BpController.getInstance().setTurnAction(
				FoodAction.getInstance());
		// resolve last action
		executeEveningFoodPhase();
		// still in phase 4
		assertEquals(TimeTrack.getInstance().getTime(),
				TimePhase.PHASE_04_EVENING_FOOD);
		// resolve last action
		resolveEveningFoodPhase(FoodAction.getInstance());
		// now in phase 5
		assertEquals(TimeTrack.getInstance().getTime(),
				TimePhase.PHASE_05_EVENING_LODGING);
		// add action to go through phase
		LodgingAction.getInstance().reset();
		BpController.getInstance().setTurnAction(
				LodgingAction.getInstance());
		executeEveningLodgingPhase();
		// still in phase 5
		assertEquals(TimeTrack.getInstance().getTime(),
				TimePhase.PHASE_05_EVENING_LODGING);
		// resolve last action
		resolveEveningLodgingPhase(LodgingAction.getInstance());
		// now in phase 6
		assertEquals(TimeTrack.getInstance().getTime(),
				TimePhase.PHASE_06_POST_EVENING);
		// add action to go through phase
		PostEveningAction.getInstance().reset();
		BpController.getInstance().setTurnAction(
				PostEveningAction.getInstance());
		executePostEveningPhase();
		// still in phase 6
		assertEquals(TimeTrack.getInstance().getTime(),
				TimePhase.PHASE_06_POST_EVENING);
		// resolve last action
		resolvePostEveningPhase(PostEveningAction.getInstance());
		// now in phase 0 Week One, Day Two
		assertEquals(TimeTrack.getInstance().getTime(),
				TimePhase.PHASE_00_PRE_ACTION);
		assertEquals(TimeTrack.getInstance().getDay(), Day.Two);
		assertEquals(TimeTrack.getInstance().getWeek(), Week.One);
	}
}
