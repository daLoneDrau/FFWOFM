package com.dalonedrow.module.barbarianprince.ui.console;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dalonedrow.module.barbarianprince.rpg.action.MoveAction;
import com.dalonedrow.module.barbarianprince.rpg.action.PreDawnAction;
import com.dalonedrow.module.barbarianprince.systems.BpController;
import com.dalonedrow.module.barbarianprince.systems.TestMain;
import com.dalonedrow.module.barbarianprince.systems.GameState;
import com.dalonedrow.rpg.base.consoleui.InputEvent;
import com.dalonedrow.rpg.base.flyweights.RPGException;

/**
 * test the welcome screen.
 * @author drau
 *
 */
public final class PreDawnScreenTest {
	/** error content. */
	private ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	/** out content. */
	private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	/** the old output stream. */
	private PrintStream outOld;
	/** the old error stream. */
	private PrintStream errOld;
	/** the old input stream. */
	private InputStream stdin = System.in;
	/** the game cycle instance. */
	private TestMain t;
	/** Removes the output streams. */
	@After
	public void after() {
		//System.setOut(outOld);
		//System.setErr(errOld);
		System.setIn(stdin);
	}
	/**
	 * Run before all tests.
	 * @throws RPGException if an error occurs
	 */
	@Before
	public void before() throws RPGException {
		t = new TestMain();
		//t.setInputOn(true);
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
		outOld = System.out;
		errOld = System.err;
		//System.setOut(new PrintStream(outContent));
		//System.setErr(new PrintStream(errContent));
		System.setIn(new ByteArrayInputStream("e".getBytes()));
	}
	@Test
	public void canRender() throws RPGException {
		PreDawnScreen.getInstance().render();
		System.out.println(BpController.getInstance().getPartyLocation());
		t.gameCycle();
		System.out.println(BpController.getInstance().getPartyLocation());
		assertTrue("welcome was printed", outContent.toString().contains(
				"Welcome to BARBARIAN PRINCE."));
		assertEquals("input was processed, and game state is in play", 
				GameState.getInstance().getCurrentState(), 
				GameState.STATE_01_IN_PLAY);
	}
}
