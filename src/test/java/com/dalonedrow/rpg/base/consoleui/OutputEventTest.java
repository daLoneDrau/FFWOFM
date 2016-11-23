package com.dalonedrow.rpg.base.consoleui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dalonedrow.rpg.base.flyweights.RPGException;

/**
 * @author drau
 */
public final class OutputEventTest {
	/** error content. */
	private ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	/** out content. */
	private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	/** the old output stream. */
	private PrintStream outOld;
	/** the old error stream. */
	private PrintStream errOld;
	/** Removes the output streams. */
	@After
	public void after() {
		System.setOut(outOld);
		System.setErr(errOld);
	}
	/** Sets up the output streams. */
	@Before
	public void before() {
		outOld = System.out;
		errOld = System.err;
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));
	}
	/**
	 * Test that error printing w/locking works.
	 * @throws RPGException if an error occurs
	 */
	@Test
	public void canErrprintWithLock() throws RPGException {
		OutputEvent.getInstance().lock(this);
		OutputEvent.getInstance().errprintln("error", this);
		OutputEvent.getInstance().render();
		assertEquals("error printing with lock",
				"** error **\n", outContent.toString());
		OutputEvent.getInstance().unlock(this);
	}
	/**
	 * Test that error printing w/o locking works.
	 * @throws RPGException if an error occurs
	 */
	@Test
	public void canErrprintWithoutLock() throws RPGException {
		OutputEvent.getInstance().errprintln("error", this);
		OutputEvent.getInstance().render();
		assertEquals("error printing without lock",
				"** error **\n", outContent.toString());
	}
	/**
	 * Test that printing w/locking works.
	 * @throws RPGException if an error occurs
	 */
	@Test
	public void canPrintCharArrayWithLock() throws RPGException {
		OutputEvent.getInstance().lock(this);
		OutputEvent.getInstance().print(
				new char[] { 't', 'e', 's', 't' }, this);
		OutputEvent.getInstance().render();
		assertEquals("printing char array with lock",
				"test", outContent.toString());
		OutputEvent.getInstance().unlock(this);
	}
	/**
	 * Test that printing w/o locking works.
	 * @throws RPGException if an error occurs
	 */
	@Test
	public void canPrintCharArrayWithoutLock() throws RPGException {
		OutputEvent.getInstance().print(
				new char[] { 't', 'e', 's', 't' }, this);
		OutputEvent.getInstance().render();
		assertEquals("printing char array without lock",
				"test", outContent.toString());
	}
	/**
	 * Test that printing w/locking works.
	 * @throws RPGException if an error occurs
	 */
	@Test
	public void canPrintCharWithLock() throws RPGException {
		OutputEvent.getInstance().lock(this);
		OutputEvent.getInstance().print('i', this);
		OutputEvent.getInstance().render();
		assertEquals("printing char with lock",
				"i", outContent.toString());
		OutputEvent.getInstance().unlock(this);
	}
	/**
	 * Test that printing w/o locking works.
	 * @throws RPGException if an error occurs
	 */
	@Test
	public void canPrintCharWithoutLock() throws RPGException {
		OutputEvent.getInstance().print('i', this);
		OutputEvent.getInstance().render();
		assertEquals("printing char without lock",
				"i", outContent.toString());
	}
	/**
	 * Test that printing w/locking works.
	 * @throws RPGException if an error occurs
	 */
	@Test
	public void canPrintIntWithLock() throws RPGException {
		OutputEvent.getInstance().lock(this);
		OutputEvent.getInstance().print(1, this);
		OutputEvent.getInstance().render();
		assertEquals("printing int with lock",
				"1", outContent.toString());
		OutputEvent.getInstance().unlock(this);
	}
	/**
	 * Test that printing w/o locking works.
	 * @throws RPGException if an error occurs
	 */
	@Test
	public void canPrintIntWithoutLock() throws RPGException {
		OutputEvent.getInstance().print(1, this);
		OutputEvent.getInstance().render();
		assertEquals("printing int without lock",
				"1", outContent.toString());
	}
	/**
	 * Test that printing w/locking works.
	 * @throws RPGException if an error occurs
	 */
	@Test
	public void canPrintlnCharArrayWithLock() throws RPGException {
		OutputEvent.getInstance().lock(this);
		OutputEvent.getInstance().println(
				new char[] { 't', 'e', 's', 't' }, this);
		OutputEvent.getInstance().render();
		assertEquals("printing char array with lock",
				"test\n", outContent.toString());
		OutputEvent.getInstance().unlock(this);
	}
	/**
	 * Test that printing w/o locking works.
	 * @throws RPGException if an error occurs
	 */
	@Test
	public void canPrintlnCharArrayWithoutLock() throws RPGException {
		OutputEvent.getInstance().println(
				new char[] { 't', 'e', 's', 't' }, this);
		OutputEvent.getInstance().render();
		assertEquals("printing char array without lock",
				"test\n", outContent.toString());
	}
	/**
	 * Test that printing w/locking works.
	 * @throws RPGException if an error occurs
	 */
	@Test
	public void canPrintlnStringWithLock() throws RPGException {
		OutputEvent.getInstance().lock(this);
		OutputEvent.getInstance().println("test", this);
		OutputEvent.getInstance().render();
		assertEquals("printing string with lock",
				"test\n", outContent.toString());
		OutputEvent.getInstance().unlock(this);
	}
	/**
	 * Test that printing w/o locking works.
	 * @throws RPGException if an error occurs
	 */
	@Test
	public void canPrintlnStringWithoutLock() throws RPGException {
		OutputEvent.getInstance().println("test", this);
		OutputEvent.getInstance().render();
		assertEquals("printing string without lock",
				"test\n", outContent.toString());
	}
	/**
	 * Test that printing w/locking works.
	 * @throws RPGException if an error occurs
	 */
	@Test
	public void canPrintStringWithLock() throws RPGException {
		OutputEvent.getInstance().lock(this);
		OutputEvent.getInstance().print("test", this);
		OutputEvent.getInstance().render();
		assertEquals("printing string with lock",
				"test", outContent.toString());
		OutputEvent.getInstance().unlock(this);
	}
	/**
	 * Test that printing w/o locking works.
	 * @throws RPGException if an error occurs
	 */
	@Test
	public void canPrintStringWithoutLock() throws RPGException {
		OutputEvent.getInstance().print("test", this);
		OutputEvent.getInstance().render();
		assertEquals("printing string without lock",
				"test", outContent.toString());
	}
	/**
	 * Test that error printing w/locking doesn't work with wrong lock.
	 * @throws RPGException if an error occurs
	 */
	@Test
	public void willNotErrprintWithWrongLock() throws RPGException {
		OutputEvent.getInstance().lock(this);
		OutputEvent.getInstance().errprintln("error", new Object());
		OutputEvent.getInstance().render();
		assertEquals("error printing with wrong lock",
				"", outContent.toString());
		OutputEvent.getInstance().unlock(this);
	}
	/**
	 * Test that once locked, the output can't be re-locked.
	 * @throws RPGException if an error occurs
	 */
	@Test
	public void willNotLockTwice() throws RPGException {
		OutputEvent.getInstance().lock(this);
		boolean le = false;
		try {
			OutputEvent.getInstance().lock(this);
		} catch (RPGException e) {
			le = true;
		}
		assertTrue("Double lock threw exception", le);
		OutputEvent.getInstance().unlock(this);
	}
	/**
	 * Test that printing w/locking doesn't work with wrong lock.
	 * @throws RPGException if an error occurs
	 */
	@Test
	public void willNotPrintCharArrayWithWrongLock() throws RPGException {
		OutputEvent.getInstance().lock(this);
		OutputEvent.getInstance().print(
				new char[] { 't', 'e', 's', 't' }, new Object());
		OutputEvent.getInstance().render();
		assertEquals("won't print char array with wrong lock",
				"", outContent.toString());
		OutputEvent.getInstance().unlock(this);
	}
	/**
	 * Test that printing w/locking doesn't work with wrong lock.
	 * @throws RPGException if an error occurs
	 */
	@Test
	public void willNotPrintCharWithWrongLock() throws RPGException {
		OutputEvent.getInstance().lock(this);
		OutputEvent.getInstance().print('i', new Object());
		OutputEvent.getInstance().render();
		assertEquals("won't print char with wrong lock",
				"", outContent.toString());
		OutputEvent.getInstance().unlock(this);
	}
	/**
	 * Test that printing w/locking doesn't work with wrong lock.
	 * @throws RPGException if an error occurs
	 */
	@Test
	public void willNotPrintIntWithWrongLock() throws RPGException {
		OutputEvent.getInstance().lock(this);
		OutputEvent.getInstance().print(1, new Object());
		OutputEvent.getInstance().render();
		assertEquals("won't print int with wrong lock",
				"", outContent.toString());
		OutputEvent.getInstance().unlock(this);
	}
	/**
	 * Test that printing w/locking doesn't work with wrong lock.
	 * @throws RPGException if an error occurs
	 */
	@Test
	public void willNotPrintlnCharArrayWithWrongLock() throws RPGException {
		OutputEvent.getInstance().lock(this);
		OutputEvent.getInstance().println(
				new char[] { 't', 'e', 's', 't' }, new Object());
		OutputEvent.getInstance().render();
		assertEquals("won't print char array with wrong lock",
				"", outContent.toString());
		OutputEvent.getInstance().unlock(this);
	}
	/**
	 * Test that printing w/locking doesn't work with wrong lock.
	 * @throws RPGException if an error occurs
	 */
	@Test
	public void willNotPrintlnStringWithWrongLock() throws RPGException {
		OutputEvent.getInstance().lock(this);
		OutputEvent.getInstance().println("test", new Object());
		OutputEvent.getInstance().render();
		assertEquals("won't print string with wrong lock",
				"", outContent.toString());
		OutputEvent.getInstance().unlock(this);
	}
	/**
	 * Test that printing w/locking doesn't work with wrong lock.
	 * @throws RPGException if an error occurs
	 */
	@Test
	public void willNotPrintStringWithWrongLock() throws RPGException {
		OutputEvent.getInstance().lock(this);
		OutputEvent.getInstance().print("test", new Object());
		OutputEvent.getInstance().render();
		assertEquals("won't print string with wrong lock",
				"", outContent.toString());
		OutputEvent.getInstance().unlock(this);
	}
	/**
	 * Test that printing w/locking doesn't work with wrong lock.
	 * @throws RPGException if an error occurs
	 */
	@Test
	public void willNotUnlockWithWrongObject() throws RPGException {
		OutputEvent.getInstance().lock(this);
		OutputEvent.getInstance().unlock(new Object());
		OutputEvent.getInstance().println("test", new Object());
		OutputEvent.getInstance().render();
		assertEquals("won't print string with wrong unlock",
				"", outContent.toString());
		OutputEvent.getInstance().unlock(this);
	}
}
