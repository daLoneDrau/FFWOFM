package com.dalonedrow.module.ff.systems;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringBufferInputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.engine.systems.base.ProjectConstants;
import com.dalonedrow.engine.systems.base.Time;
import com.dalonedrow.module.ff.rpg.FFCharacter;
import com.dalonedrow.module.ff.rpg.FFInteractiveObject;
import com.dalonedrow.module.ff.rpg.FFIoGroup;
import com.dalonedrow.module.ff.systems.wofm.FFWoFMController;
import com.dalonedrow.module.ff.systems.wofm.GameState;
import com.dalonedrow.module.ff.wofm.ui.console.RoomScreen;
import com.dalonedrow.rpg.base.consoleui.InputEvent;
import com.dalonedrow.rpg.base.consoleui.InputProcessor;
import com.dalonedrow.rpg.base.consoleui.OutputEvent;
import com.dalonedrow.rpg.base.flyweights.ErrorMessage;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.systems.Script;
import com.dalonedrow.rpg.base.systems.WebServiceClient;

/**
 * The main tester for the game.
 * @author drau
 */
public class TestMain {
	/** flag indicating user input is on. */
	private boolean	inputOn = true;
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
		System.out.println("**update controller**");
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
		// check timers
		System.out.println("**check timers**");
		Script.getInstance().timerCheck();
		System.out.println("---c-y-c-l-e---o-v-e-r---");
	}
	@Test
	public final void testGameStart() {
		try {
			System.out.println(FFWoFMController.getInstance().getPlayerParty());
			Object o = Interactive.getInstance().getIO(
					FFWoFMController.getInstance().getPlayerParty());
			System.out.println(o);
			for (int i = ((FFIoGroup) o).getNumberOfMembers() - 1;
					i >= 0; i--) {
				int id = ((FFIoGroup) o).getMember(i);
				Object o1 = Interactive.getInstance().getIO(id);
				System.out.println(o1);
				FFCharacter pc = ((FFInteractiveObject) o1).getPCData();
				pc.setPrettyPrinting(true);
				System.out.println(pc);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail(ex.getMessage());
		}
	}
	/**
	 * Test method.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public final void testWelcomeScreen() {
		try {
			FFWoFMController.getInstance();
			do {
				gameCycle();
			} while (GameState.getInstance().getCurrentState()
					!= GameState.STATE_01_IN_PLAY);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail(ex.getMessage());
		}
	}
	/**
	 * Test method.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public final void testRoom1() {
		try {
			gameCycle(); // render 1st page
			gameCycle(); // render 2nd page
			assertTrue(RoomScreen.getInstance().getErrorMessage().contains(
					WebServiceClient.getInstance().getTextByName(
							"invalid_input")));
			gameCycle(); // process NOR input
			gameCycle(); // display NOR error/ process SOU input
			gameCycle(); // display SOU input/ process E input
			gameCycle(); // display E input/ process BAS input
			gameCycle(); // display BAS input/ process W input
			gameCycle(); // display W input/ process W input
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail(ex.getMessage());
		}
	}
	/**
	 * Test method.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public final void testRoom71() {
		try {
			gameCycle(); // display W input/ process [Enter] input
			gameCycle(); // display [Enter] input/ process ATT input
			gameCycle(); // display ATT input/ process ATT input
			gameCycle(); // display ATT input/ process ATT input
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail(ex.getMessage());
		}
	}
	/**
	 * Enters input for testing the prolog.
	 * @param sb the string buffer
	 */
	private void prologInput(final StringBuffer sb) {
		// 3 [Enter]s for welcome screen
		sb.append(System.lineSeparator());
		sb.append(System.lineSeparator());
		sb.append(System.lineSeparator());
		// 2 [Enter]s for room 1
		sb.append(System.lineSeparator());
		sb.append(System.lineSeparator());
	}
	/**
	 * Enters input for testing from room 1 to 278 and back.
	 * @param sb the string buffer
	 */
	private void room1To278TestInput(final StringBuffer sb) {
		// try to go north from room 1
		sb.append("NOR\n");
		// try to go south from room 1
		sb.append("SOU\n");
		// try to go from room 1 -> 278
		sb.append("E\n");
		// try to bash door in room 278 -> 343
		sb.append("BAS\n");
		// try to go from room 343 -> 278
		sb.append("W\n");
		// try to go from room 278 -> 1
		sb.append("W\n");
	}
	/**
	 * Enters input for testing from room 1 to 278 and back.
	 * @param sb the string buffer
	 */
	private void room1To71CombatTestInput(final StringBuffer sb) {
		// try to go west from room 1
		sb.append("W\n");
		// [Enter] to go to combat
		sb.append(System.lineSeparator());
		// attack
		sb.append("ATT\n");
		// attack again
		sb.append("ATT\n");
	}
	@Test
	public final void testMain() {
		try {
			// grab old input
			InputStream old = System.in;
			StringBuffer sb = new StringBuffer();
			prologInput(sb);
			room1To278TestInput(sb);
			room1To71CombatTestInput(sb);
			// one more enter to avoid error at end of last gamecycle()
			sb.append(System.lineSeparator());
			System.setIn(new StringBufferInputStream(sb.toString()));
			testWelcomeScreen();
			testRoom1();
			testRoom71();
			// return input to old stream
			System.setIn(old);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail(ex.getMessage());
		}
	}
}
