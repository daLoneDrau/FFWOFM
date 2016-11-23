package com.dalonedrow.module.barbarianprince.ui.console;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dalonedrow.module.barbarianprince.systems.BpController;
import com.dalonedrow.module.barbarianprince.systems.TestMain;
import com.dalonedrow.module.barbarianprince.systems.GameState;
import com.dalonedrow.rpg.base.flyweights.RPGException;

/**
 * test the welcome screen.
 * @author drau
 *
 */
public final class WelcomeScreenTest {
	/** error content. */
	private ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	/** out content. */
	private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	/** the old output stream. */
	private PrintStream outOld;
	/** the old error stream. */
	private PrintStream errOld;
	/** the old input stream. */
	private InputStream inOld;
	/** the game cycle instance. */
	private TestMain t;
	/** Removes the output streams. */
	@After
	public void after() {
		System.setOut(outOld);
		System.setErr(errOld);
		System.setIn(inOld);
	}
	/**
	 * Run before all tests.
	 * @throws RPGException if an error occurs
	 */
	@Before
	public void before() throws RPGException {
		t = new TestMain();
		t.setInputOn(true);
		BpController.getInstance();
		inOld = System.in;
		outOld = System.out;
		errOld = System.err;
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
		System.setIn(new ByteArrayInputStream("e".getBytes()));
	}
	@Test
	public void canRender() throws RPGException {
		/*
		t.gameCycle();
		assertTrue("welcome was printed", outContent.toString().contains(
				"Welcome to BARBARIAN PRINCE."));
		assertEquals("input was processed, and game state is in play", 
				GameState.getInstance().getCurrentState(), 
				GameState.STATE_01_IN_PLAY);
				*/
	}
}
