package com.dalonedrow.rpg.base.consoleui;

import static org.junit.Assert.*;

import org.junit.Test;

import com.dalonedrow.rpg.base.flyweights.RPGException;

/**
 * Test for {@link Panel} class.
 * @author drau
 *
 */
public final class PanelTest {
	/** Test creating several panels. */
	@Test
	public void canCreate() {
		Panel p = new Panel(5, false, 5);
		assertNotNull(p);
		p = new Panel(5, false, 5, new char[] { 't', 'e', 's', 't' },
				new char[] { 't', 'i', 't', 'l', 'e' });
		assertNotNull(p);
		p = new Panel(5, false, 5, "test2");
		assertNotNull(p);
		p = new Panel(5, false, 5, new char[] { 't', 'e', 's', 't' });
		assertNotNull(p);
		p = new Panel(5, false, 5, "test2", "title2");
		assertNotNull(p);
	}
	/**
	 * Test writing several panels. 
	 * @throws RPGException if an error occurs
	 */
	@Test
	public void canWrite() throws RPGException {
		Panel p = new Panel(5, false, 5);
		p.setContent("one\ntwo\nthree\nfour\nfive");
		assertEquals(p.getDisplayText(), "one  \ntwo  \nthree\nfour \nfive \n");
		p = new Panel(7, true, 5);
		p.setContent("one\ntwo\ntwo");
		assertEquals(p.getDisplayText(), "|-----|\n| one |\n| two |"
				+ "\n| two |\n|-----|\n");
		p = new Panel(5, false, 5, new char[] { 't', 'e', 's', 't' },
				new char[] { 't', 'i', 't', 'l', 'e' });
		assertEquals(p.getDisplayText(), "test \n     \n     \n     \n     \n");
		p = new Panel(5, false, 5, "test2");
		assertEquals(p.getDisplayText(), "test2\n     \n     \n     \n     \n");
		p = new Panel(7, true, 5, new char[] { 'o', 'n', 'e' });
		assertEquals(p.getDisplayText(), "|-----|\n| one |\n|     |"
				+ "\n|     |\n|-----|\n");
		p = new Panel(9, true, 3, "test", "title");
		assertEquals(p.getDisplayText(), "|-title-|\n| test  |\n|-------|\n");
	}
	/**
	 * Test left joining several panels. 
	 * @throws RPGException if an error occurs
	 */
	@Test
	public void canJoinLeftNoBorder() throws RPGException {
		// join two panels, panel 1 left of panel 2, top aligned
		Panel p1 = new Panel(5, false, 5);
		p1.setContent("one\ntwo\nthree\nfour\nfive");
		assertEquals(p1.getDisplayText(),
				"one  \ntwo  \nthree\nfour \nfive \n");
		Panel p2 = new Panel(7, false, 3);
		p2.setContent("six\nseven\neight");
		assertEquals(p2.getDisplayText(), "six    \nseven  \neight  \n");
		p1.join(p2, Panel.LEFT, Panel.TOP);
		assertEquals(p1.getDisplayText(),
				"one  six    \ntwo  seven  \nthreeeight  \nfour"
				+ "        \nfive        \n");
		
		// join two panels, panel 2 left of panel 1 top aligned
		p1 = new Panel(5, false, 5);
		p1.setContent("one\ntwo\nthree\nfour\nfive");
		p2.join(p1, Panel.LEFT, Panel.TOP);
		assertEquals(p2.getDisplayText(),
				"six    one  \nseven  two  \neight  three\n       "
				+ "four \n       five \n");
		
		// join two panels, panel 2 left of panel 1 center aligned
		p2 = new Panel(7, false, 3);
		p2.setContent("six\nseven\neight");
		p1.join(p2, Panel.LEFT, Panel.CENTER);
		assertEquals(p1.getDisplayText(),
				"one         \ntwo  six    \nthreeseven  \nfour eight  \n"
				+ "five        \n");
		
		// join two panels, panel 2 left of panel 1 center aligned
		p1 = new Panel(5, false, 5);
		p1.setContent("one\ntwo\nthree\nfour\nfive");
		p2.join(p1, Panel.LEFT, Panel.CENTER);
		assertEquals(p2.getDisplayText(),
				"       one  \nsix    two  \nseven  three\neight  four "
				+ "\n       five \n");
		
		// join two panels, panel 1 left of panel 2, bottom aligned
		p2 = new Panel(7, false, 3);
		p2.setContent("six\nseven\neight");
		p1.join(p2, Panel.LEFT, Panel.BOTTOM);
		assertEquals(p1.getDisplayText(),
				"one         \ntwo         \nthreesix    \nfour seven  "
				+ "\nfive eight  \n");
		
		// join two panels, panel 2 left of panel 1, bottom aligned
		p1 = new Panel(5, false, 5);
		p1.setContent("one\ntwo\nthree\nfour\nfive");
		p2.join(p1, Panel.LEFT, Panel.BOTTOM);
		assertEquals(p2.getDisplayText(),
				"       one  \n       two  \nsix    three\nseven  four "
				+ "\neight  five \n");
		
		// join two panels, panel 1 left of panel 2, bottom aligned
		p2 = new Panel(9, true, 3);
		p2.setContent("six");
		p1.join(p2, Panel.LEFT, Panel.BOTTOM);
		assertEquals(p1.getDisplayText(),
				"one           \ntwo           \nthree|-------|\n"
				+ "four | six   |\nfive |-------|\n");
		
		// join two panels, panel 1 left of panel 2, bottom aligned
		p1 = new Panel(5, false, 5);
		p1.setContent("one\ntwo\nthree\nfour\nfive");
		p1.join(p2, Panel.LEFT, Panel.TOP);
		assertEquals(p1.getDisplayText(),
				"one  |-------|\ntwo  | six   |\nthree|-------|\n"
				+ "four          \nfive          \n");
	}
	/**
	 * Test left joining several panels. 
	 * @throws RPGException if an error occurs
	 */
	@Test
	public void canJoinTopNoBorder() throws RPGException {
		// join two panels, panel 1 left of panel 2, top aligned
		Panel p1 = new Panel(5, false, 5);
		p1.setContent("one\ntwo\nthree\nfour\nfive");
		assertEquals(p1.getDisplayText(),
				"one  \ntwo  \nthree\nfour \nfive \n");
		Panel p2 = new Panel(7, false, 3);
		p2.setContent("six\nsevennn\neight");
		assertEquals(p2.getDisplayText(), "six    \nsevennn\neight  \n");
		p1.join(p2, Panel.TOP, Panel.LEFT);
		assertEquals(p1.getDisplayText(),
				"one    \n"
				+ "two    \n"
				+ "three  \n"
				+ "four   \n"
				+ "five   \n"
				+ "six    \n"
				+ "sevennn\n"
				+ "eight  \n");
		
		// join two panels, panel 2 left of panel 1 top aligned
		p1 = new Panel(5, false, 5);
		p1.setContent("one\ntwo\nthree\nfour\nfive");
		p2.join(p1, Panel.TOP, Panel.LEFT);
		assertEquals(p2.getDisplayText(),
						"six    \n"
						+ "sevennn\n"
						+ "eight  \n"
						+ "one  \n"
						+ "two  \n"
						+ "three\n"
						+ "four \n"
						+ "five \n");
		
		// join two panels, panel 2 left of panel 1 center aligned
		p2 = new Panel(7, false, 3);
		p2.setContent("six\nsevennn\neight");
		p1.join(p2, Panel.TOP, Panel.CENTER);
		assertEquals(p1.getDisplayText(),
				" one   \n"
				+ " two   \n"
				+ " three \n"
				+ " four  \n"
				+ " five  \n"
				+ "six    \n"
				+ "sevennn\n"
				+ "eight  \n");
		
		// join two panels, panel 2 left of panel 1 center aligned
		p1 = new Panel(5, false, 5);
		p1.setContent("one\ntwo\nthree\nfour\nfive");
		p2.join(p1, Panel.TOP, Panel.CENTER);
		assertEquals(p2.getDisplayText(),
				"six    \n"
				+ "sevennn\n"
				+ "eight  \n"
				+ " one   \n"
				+ " two   \n"
				+ " three \n"
				+ " four  \n"
				+ " five  \n");
		
		// join two panels, panel 1 left of panel 2, bottom aligned
		p2 = new Panel(7, false, 3);
		p2.setContent("six\nsevennn\neight");
		p1.join(p2, Panel.TOP, Panel.RIGHT);
		assertEquals(p1.getDisplayText(),
				"  one  \n"
				+ "  two  \n"
				+ "  three\n"
				+ "  four \n"
				+ "  five \n"
				+ "six    \n"
				+ "sevennn\n"
				+ "eight  \n");
		
		// join two panels, panel 2 left of panel 1, bottom aligned
		p1 = new Panel(5, false, 5);
		p1.setContent("one\ntwo\nthree\nfour\nfive");
		p2.join(p1, Panel.TOP, Panel.RIGHT);
		assertEquals(p2.getDisplayText(),
				"six    \n"
				+ "sevennn\n"
				+ "eight  \n"
				+ "  one  \n"
				+ "  two  \n"
				+ "  three\n"
				+ "  four \n"
				+ "  five \n");
		
		// join two panels, panel 1 left of panel 2, bottom aligned
		p2 = new Panel(9, true, 3);
		p2.setContent("sixxx");
		p1.join(p2, Panel.TOP, Panel.RIGHT);
		assertEquals(p1.getDisplayText(),
				"    one  \n"
				+ "    two  \n"
				+ "    three\n"
				+ "    four \n"
				+ "    five \n"
				+ "|-------|\n"
				+ "| sixxx |\n"
				+ "|-------|\n");
		
		// join two panels, panel 1 left of panel 2, bottom aligned
		p1 = new Panel(5, false, 5);
		p1.setContent("one\ntwo\nthree\nfour\nfive");
		p1.join(p2, Panel.TOP, Panel.LEFT);
		assertEquals(p1.getDisplayText(),
				"one      \n"
				+ "two      \n"
				+ "three    \n"
				+ "four     \n"
				+ "five     \n"
				+ "|-------|\n"
				+ "| sixxx |\n"
				+ "|-------|\n");
	}
	/**
	 * Test bottom joining several panels. 
	 * @throws RPGException if an error occurs
	 */
	@Test
	public void canJoinBottomNoBorder() throws RPGException {
		// join two panels, panel 1 left of panel 2, top aligned
		Panel p1 = new Panel(5, false, 5);
		p1.setContent("one\ntwo\nthree\nfour\nfive");
		assertEquals(p1.getDisplayText(),
				"one  \ntwo  \nthree\nfour \nfive \n");
		Panel p2 = new Panel(7, false, 3);
		p2.setContent("six\nsevennn\neight");
		assertEquals(p2.getDisplayText(), "six    \nsevennn\neight  \n");
		p1.join(p2, Panel.BOTTOM, Panel.LEFT);
		assertEquals("panel 1-5 is below 6-8, both lined up left",
				"six    \n"
				+ "sevennn\n"
				+ "eight  \n"
				+ "one    \n"
				+ "two    \n"
				+ "three  \n"
				+ "four   \n"
				+ "five   \n",
				p1.getDisplayText());
		
		// join two panels, panel 2 left of panel 1 top aligned
		p1 = new Panel(5, false, 5);
		p1.setContent("one\ntwo\nthree\nfour\nfive");
		p2.join(p1, Panel.BOTTOM, Panel.LEFT);
		assertEquals(p2.getDisplayText(),
				"one  \n"
				+ "two  \n"
				+ "three\n"
				+ "four \n"
				+ "five \n"
						+ "six    \n"
						+ "sevennn\n"
						+ "eight  \n");
		
		// join two panels, panel 2 left of panel 1 center aligned
		p2 = new Panel(7, false, 3);
		p2.setContent("six\nsevennn\neight");
		p1.join(p2, Panel.BOTTOM, Panel.CENTER);
		assertEquals(p1.getDisplayText(),
				"six    \n"
				+ "sevennn\n"
				+ "eight  \n"
				+ " one   \n"
				+ " two   \n"
				+ " three \n"
				+ " four  \n"
				+ " five  \n");
		
		// join two panels, panel 2 left of panel 1 center aligned
		p1 = new Panel(5, false, 5);
		p1.setContent("one\ntwo\nthree\nfour\nfive");
		p2.join(p1, Panel.BOTTOM, Panel.CENTER);
		assertEquals(p2.getDisplayText(),
				" one   \n"
				+ " two   \n"
				+ " three \n"
				+ " four  \n"
				+ " five  \n"
				+ "six    \n"
				+ "sevennn\n"
				+ "eight  \n");
		
		// join two panels, panel 1 left of panel 2, bottom aligned
		p2 = new Panel(7, false, 3);
		p2.setContent("six\nsevennn\neight");
		p1.join(p2, Panel.BOTTOM, Panel.RIGHT);
		assertEquals(p1.getDisplayText(),
				"six    \n"
				+ "sevennn\n"
				+ "eight  \n"
				+ "  one  \n"
				+ "  two  \n"
				+ "  three\n"
				+ "  four \n"
				+ "  five \n");
		
		// join two panels, panel 2 left of panel 1, bottom aligned
		p1 = new Panel(5, false, 5);
		p1.setContent("one\ntwo\nthree\nfour\nfive");
		p2.join(p1, Panel.BOTTOM, Panel.RIGHT);
		assertEquals(p2.getDisplayText(),
				"  one  \n"
				+ "  two  \n"
				+ "  three\n"
				+ "  four \n"
				+ "  five \n"
				+ "six    \n"
				+ "sevennn\n"
				+ "eight  \n");
		
		// join two panels, panel 1 left of panel 2, bottom aligned
		p2 = new Panel(9, true, 3);
		p2.setContent("sixxx");
		p1.join(p2, Panel.BOTTOM, Panel.RIGHT);
		assertEquals(p1.getDisplayText(),
				"|-------|\n"
				+ "| sixxx |\n"
				+ "|-------|\n"
				+ "    one  \n"
				+ "    two  \n"
				+ "    three\n"
				+ "    four \n"
				+ "    five \n");
		
		// join two panels, panel 1 left of panel 2, bottom aligned
		p1 = new Panel(5, false, 5);
		p1.setContent("one\ntwo\nthree\nfour\nfive");
		p1.join(p2, Panel.BOTTOM, Panel.LEFT);
		assertEquals(p1.getDisplayText(),
				"|-------|\n"
				+ "| sixxx |\n"
				+ "|-------|\n"
				+ "one      \n"
				+ "two      \n"
				+ "three    \n"
				+ "four     \n"
				+ "five     \n");
	}
	/**
	 * Test left joining several panels. 
	 * @throws RPGException if an error occurs
	 */
	@Test
	public void canJoinLeftBordered() throws RPGException {
		// join two panels, panel 1 left of panel 2, top aligned
		Panel p1 = new Panel(9, true, 7);
		p1.setContent("one\ntwo\nthree\nfour\nfive");
		assertEquals(p1.getDisplayText(),
				"|-------|\n| one   |\n| two   |\n| three |\n| four  |\n"
				+ "| five  |\n|-------|\n");
		Panel p2 = new Panel(9, true, 5);
		p2.setContent("six\nseven\neight");
		assertEquals(p2.getDisplayText(), "|-------|\n| six   |\n| seven |\n"
				+ "| eight |\n|-------|\n");
		p1.join(p2, Panel.LEFT, Panel.TOP);
		assertEquals("panel 1-5 is left of 6-8, both lined up at top",
				p1.getDisplayText(),
				"|-------|-------|\n"
				+ "| one   | six   |\n"
				+ "| two   | seven |\n"
				+ "| three | eight |\n"
				+ "| four  |       |\n"
				+ "| five  |       |\n"
				+ "|-------|-------|\n");
		
		// join two panels, panel 2 left of panel 1 top aligned
		p1 = new Panel(9, true, 7);
		p1.setContent("one\ntwo\nthree\nfour\nfive");
		p2.join(p1, Panel.LEFT, Panel.TOP);
		assertEquals("panel 6-8 is left of 1-5, both lined up at top",
				p2.getDisplayText(),
				"|-------|-------|\n"
				+ "| six   | one   |\n"
				+ "| seven | two   |\n"
				+ "| eight | three |\n"
				+ "|       | four  |\n"
				+ "|       | five  |\n"
				+ "|-------|-------|\n");
		
		// join two panels, panel 2 left of panel 1 center aligned
		p2 = new Panel(9, true, 5);
		p2.setContent("six\nseven\neight");
		p1.join(p2, Panel.LEFT, Panel.CENTER);
		assertEquals("panel 1-5 is left of 6-8, both lined center",
				p1.getDisplayText(),
				"|-------|-------|\n"
				+ "| one   |       |\n"
				+ "| two   | six   |\n"
				+ "| three | seven |\n"
				+ "| four  | eight |\n"
				+ "| five  |       |\n"
				+ "|-------|-------|\n");
		
		// join two panels, panel 2 left of panel 1 center aligned
		p1 = new Panel(9, true, 7);
		p1.setContent("one\ntwo\nthree\nfour\nfive");
		p2.join(p1, Panel.LEFT, Panel.CENTER);
		assertEquals("panel 6-8 is left of 1-5, both lined up center",
				"|-------|-------|\n"
				+ "|       | one   |\n"
				+ "| six   | two   |\n"
				+ "| seven | three |\n"
				+ "| eight | four  |\n"
				+ "|       | five  |\n"
				+ "|-------|-------|\n",
				p2.getDisplayText());
		
		// join two panels, panel 1 left of panel 2, bottom aligned
		p2 = new Panel(9, true, 5);
		p2.setContent("six\nseven\neight");
		p1.join(p2, Panel.LEFT, Panel.BOTTOM);
		assertEquals("panel 1-5 is left of 6-8, both lined bottom",
				"|-------|-------|\n"
				+ "| one   |       |\n"
				+ "| two   |       |\n"
				+ "| three | six   |\n"
				+ "| four  | seven |\n"
				+ "| five  | eight |\n"
				+ "|-------|-------|\n",
				p1.getDisplayText());
		
		// join two panels, panel 2 left of panel 1, bottom aligned
		p1 = new Panel(9, true, 7);
		p1.setContent("one\ntwo\nthree\nfour\nfive");
		p2.join(p1, Panel.LEFT, Panel.BOTTOM);
		assertEquals("panel 6-8 is left of 1-5, both lined up bottom",
				"|-------|-------|\n"
				+ "|       | one   |\n"
				+ "|       | two   |\n"
				+ "| six   | three |\n"
				+ "| seven | four  |\n"
				+ "| eight | five  |\n"
				+ "|-------|-------|\n",
				p2.getDisplayText());
	}
	/**
	 * Test top joining several panels. 
	 * @throws RPGException if an error occurs
	 */
	@Test
	public void canJoinTopBordered() throws RPGException {
		// join two panels, panel 1 left of panel 2, top aligned
		Panel p1 = new Panel(9, true, 7);
		p1.setContent("one\ntwo\nthree\nfour\nfive");
		assertEquals(p1.getDisplayText(),
				"|-------|\n| one   |\n| two   |\n| three |\n| four  |\n"
				+ "| five  |\n|-------|\n");
		Panel p2 = new Panel(5, true, 5);
		p2.setContent("6\n7\n8");
		assertEquals(
				p2.getDisplayText(), "|---|\n| 6 |\n| 7 |\n| 8 |\n|---|\n");
		p1.join(p2, Panel.TOP, Panel.LEFT);
		assertEquals("panel 1-5 is above 6-8, both lined up left",
				"|-------|\n"
				+ "| one   |\n"
				+ "| two   |\n"
				+ "| three |\n"
				+ "| four  |\n"
				+ "| five  |\n"
				+ "|-------|\n"
				+ "| 6     |\n"
				+ "| 7     |\n"
				+ "| 8     |\n"
				+ "|-------|\n",
				p1.getDisplayText());
		
		// join two panels, panel 2 left of panel 1 top aligned
		p1 = new Panel(9, true, 7);
		p1.setContent("one\ntwo\nthree\nfour\nfive");
		p2.join(p1, Panel.TOP, Panel.LEFT);
		assertEquals("panel 6-8 is above 1-5, both lined up left",
				"|-------|\n"
				+ "| 6     |\n"
				+ "| 7     |\n"
				+ "| 8     |\n"
				+ "|-------|\n"
				+ "| one   |\n"
				+ "| two   |\n"
				+ "| three |\n"
				+ "| four  |\n"
				+ "| five  |\n"
				+ "|-------|\n",
				p2.getDisplayText());
		
		// join two panels, panel 2 left of panel 1 center aligned
		p2 = new Panel(5, true, 5);
		p2.setContent("6\n7\n8");
		p1.join(p2, Panel.TOP, Panel.CENTER);
		assertEquals("panel 1-5 is above 6-8, both lined center",
				"|-------|\n"
				+ "|  one  |\n"
				+ "|  two  |\n"
				+ "| three |\n"
				+ "| four  |\n"
				+ "| five  |\n"
				+ "|-------|\n"
				+ "|   6   |\n"
				+ "|   7   |\n"
				+ "|   8   |\n"
				+ "|-------|\n",
				p1.getDisplayText());
		
		// join two panels, panel 2 left of panel 1 center aligned
		p1 = new Panel(9, true, 7);
		p1.setContent("one\ntwo\nthree\nfour\nfive");
		p2.join(p1, Panel.TOP, Panel.CENTER);
		assertEquals("panel 6-8 is above 1-5, both lined up center",
				"|-------|\n"
				+ "|   6   |\n"
				+ "|   7   |\n"
				+ "|   8   |\n"
				+ "|-------|\n"
				+ "|  one  |\n"
				+ "|  two  |\n"
				+ "| three |\n"
				+ "| four  |\n"
				+ "| five  |\n"
				+ "|-------|\n",
				p2.getDisplayText());
		
		// join two panels, panel 1 left of panel 2, bottom aligned
		p2 = new Panel(5, true, 5);
		p2.setContent("6\n7\n8");
		p1.join(p2, Panel.TOP, Panel.RIGHT);
		assertEquals("panel 1-5 is abovef 6-8, both lined right",
				"|-------|\n"
				+ "|   one |\n"
				+ "|   two |\n"
				+ "| three |\n"
				+ "|  four |\n"
				+ "|  five |\n"
				+ "|-------|\n"
				+ "|     6 |\n"
				+ "|     7 |\n"
				+ "|     8 |\n"
				+ "|-------|\n",
				p1.getDisplayText());
		
		// join two panels, panel 2 left of panel 1, bottom aligned
		p1 = new Panel(9, true, 7);
		p1.setContent("one\ntwo\nthree\nfour\nfive");
		p2.join(p1, Panel.TOP, Panel.RIGHT);
		assertEquals("panel 6-8 is above 1-5, both lined right",
				"|-------|\n"
				+ "|     6 |\n"
				+ "|     7 |\n"
				+ "|     8 |\n"
				+ "|-------|\n"
				+ "|   one |\n"
				+ "|   two |\n"
				+ "| three |\n"
				+ "|  four |\n"
				+ "|  five |\n"
				+ "|-------|\n",
				p2.getDisplayText());
	}
	/**
	 * Test top joining several panels. 
	 * @throws RPGException if an error occurs
	 */
	@Test
	public void canJoinBottomBordered() throws RPGException {
		// join two panels, panel 1 left of panel 2, top aligned
		Panel p1 = new Panel(9, true, 7);
		p1.setContent("one\ntwo\nthree\nfour\nfive");
		assertEquals(p1.getDisplayText(),
				"|-------|\n| one   |\n| two   |\n| three |\n| four  |\n"
				+ "| five  |\n|-------|\n");
		Panel p2 = new Panel(5, true, 5);
		p2.setContent("6\n7\n8");
		assertEquals(
				p2.getDisplayText(), "|---|\n| 6 |\n| 7 |\n| 8 |\n|---|\n");
		p1.join(p2, Panel.BOTTOM, Panel.LEFT);
		assertEquals("panel 1-5 is above 6-8, both lined up left",
				"|-------|\n"
				+ "| 6     |\n"
				+ "| 7     |\n"
				+ "| 8     |\n"
				+ "|-------|\n"
				+ "| one   |\n"
				+ "| two   |\n"
				+ "| three |\n"
				+ "| four  |\n"
				+ "| five  |\n"
				+ "|-------|\n",
				p1.getDisplayText());
		
		// join two panels, panel 2 left of panel 1 top aligned
		p1 = new Panel(9, true, 7);
		p1.setContent("one\ntwo\nthree\nfour\nfive");
		p2.join(p1, Panel.BOTTOM, Panel.LEFT);
		assertEquals("panel 6-8 is above 1-5, both lined up left",
				"|-------|\n"
				+ "| one   |\n"
				+ "| two   |\n"
				+ "| three |\n"
				+ "| four  |\n"
				+ "| five  |\n"
				+ "|-------|\n"
				+ "| 6     |\n"
				+ "| 7     |\n"
				+ "| 8     |\n"
				+ "|-------|\n",
				p2.getDisplayText());
		
		// join two panels, panel 2 left of panel 1 center aligned
		p2 = new Panel(5, true, 5);
		p2.setContent("6\n7\n8");
		p1.join(p2, Panel.BOTTOM, Panel.CENTER);
		assertEquals("panel 1-5 is above 6-8, both lined center",
				"|-------|\n"
				+ "|   6   |\n"
				+ "|   7   |\n"
				+ "|   8   |\n"
				+ "|-------|\n"
				+ "|  one  |\n"
				+ "|  two  |\n"
				+ "| three |\n"
				+ "| four  |\n"
				+ "| five  |\n"
				+ "|-------|\n",
				p1.getDisplayText());
		
		// join two panels, panel 2 left of panel 1 center aligned
		p1 = new Panel(9, true, 7);
		p1.setContent("one\ntwo\nthree\nfour\nfive");
		p2.join(p1, Panel.BOTTOM, Panel.CENTER);
		assertEquals("panel 6-8 is above 1-5, both lined up center",
				"|-------|\n"
				+ "|  one  |\n"
				+ "|  two  |\n"
				+ "| three |\n"
				+ "| four  |\n"
				+ "| five  |\n"
				+ "|-------|\n"
				+ "|   6   |\n"
				+ "|   7   |\n"
				+ "|   8   |\n"
				+ "|-------|\n",
				p2.getDisplayText());
		
		// join two panels, panel 1 left of panel 2, bottom aligned
		p2 = new Panel(5, true, 5);
		p2.setContent("6\n7\n8");
		p1.join(p2, Panel.BOTTOM, Panel.RIGHT);
		assertEquals("panel 1-5 is abovef 6-8, both lined right",
				"|-------|\n"
				+ "|     6 |\n"
				+ "|     7 |\n"
				+ "|     8 |\n"
				+ "|-------|\n"
				+ "|   one |\n"
				+ "|   two |\n"
				+ "| three |\n"
				+ "|  four |\n"
				+ "|  five |\n"
				+ "|-------|\n",
				p1.getDisplayText());
		
		// join two panels, panel 2 left of panel 1, bottom aligned
		p1 = new Panel(9, true, 7);
		p1.setContent("one\ntwo\nthree\nfour\nfive");
		p2.join(p1, Panel.BOTTOM, Panel.RIGHT);
		assertEquals("panel 6-8 is above 1-5, both lined right",
				"|-------|\n"
				+ "|   one |\n"
				+ "|   two |\n"
				+ "| three |\n"
				+ "|  four |\n"
				+ "|  five |\n"
				+ "|-------|\n"
				+ "|     6 |\n"
				+ "|     7 |\n"
				+ "|     8 |\n"
				+ "|-------|\n",
				p2.getDisplayText());
	}
	/**
	 * Test right joining several panels. 
	 * @throws RPGException if an error occurs
	 */
	@Test
	public void canJoinRightBordered() throws RPGException {
		// join two panels, panel 1 left of panel 2, top aligned
		Panel p1 = new Panel(9, true, 7);
		p1.setContent("one\ntwo\nthree\nfour\nfive");
		assertEquals(p1.getDisplayText(),
				"|-------|\n| one   |\n| two   |\n| three |\n| four  |\n"
				+ "| five  |\n|-------|\n");
		Panel p2 = new Panel(9, true, 5);
		p2.setContent("six\nseven\neight");
		assertEquals(p2.getDisplayText(), "|-------|\n| six   |\n| seven |\n"
				+ "| eight |\n|-------|\n");
		p1.join(p2, Panel.RIGHT, Panel.TOP);
		assertEquals("panel 1-5 is right of 6-8, both lined up at top",
				p1.getDisplayText(),
				"|-------|-------|\n"
				+ "| six   | one   |\n"
				+ "| seven | two   |\n"
				+ "| eight | three |\n"
				+ "|       | four  |\n"
				+ "|       | five  |\n"
				+ "|-------|-------|\n");
		
		// join two panels, panel 2 left of panel 1 top aligned
		p1 = new Panel(9, true, 7);
		p1.setContent("one\ntwo\nthree\nfour\nfive");
		p2.join(p1, Panel.RIGHT, Panel.TOP);
		assertEquals("panel 6-8 is left of 1-5, both lined up at top",
				p2.getDisplayText(),
				"|-------|-------|\n"
				+ "| one   | six   |\n"
				+ "| two   | seven |\n"
				+ "| three | eight |\n"
				+ "| four  |       |\n"
				+ "| five  |       |\n"
				+ "|-------|-------|\n");
		
		// join two panels, panel 2 left of panel 1 center aligned
		p2 = new Panel(9, true, 5);
		p2.setContent("six\nseven\neight");
		p1.join(p2, Panel.RIGHT, Panel.CENTER);
		assertEquals("panel 1-5 is left of 6-8, both lined center",
				"|-------|-------|\n"
				+ "|       | one   |\n"
				+ "| six   | two   |\n"
				+ "| seven | three |\n"
				+ "| eight | four  |\n"
				+ "|       | five  |\n"
				+ "|-------|-------|\n",
				p1.getDisplayText());
		
		// join two panels, panel 2 left of panel 1 center aligned
		p1 = new Panel(9, true, 7);
		p1.setContent("one\ntwo\nthree\nfour\nfive");
		p2.join(p1, Panel.RIGHT, Panel.CENTER);
		assertEquals("panel 6-8 is left of 1-5, both lined up center",
				"|-------|-------|\n"
				+ "| one   |       |\n"
				+ "| two   | six   |\n"
				+ "| three | seven |\n"
				+ "| four  | eight |\n"
				+ "| five  |       |\n"
				+ "|-------|-------|\n",
				p2.getDisplayText());
		
		// join two panels, panel 1 left of panel 2, bottom aligned
		p2 = new Panel(9, true, 5);
		p2.setContent("six\nseven\neight");
		p1.join(p2, Panel.RIGHT, Panel.BOTTOM);
		assertEquals("panel 1-5 is left of 6-8, both lined bottom",
				"|-------|-------|\n"
				+ "|       | one   |\n"
				+ "|       | two   |\n"
				+ "| six   | three |\n"
				+ "| seven | four  |\n"
				+ "| eight | five  |\n"
				+ "|-------|-------|\n",
				p1.getDisplayText());
		
		// join two panels, panel 2 left of panel 1, bottom aligned
		p1 = new Panel(9, true, 7);
		p1.setContent("one\ntwo\nthree\nfour\nfive");
		p2.join(p1, Panel.RIGHT, Panel.BOTTOM);
		assertEquals("panel 6-8 is left of 1-5, both lined up bottom",
				"|-------|-------|\n"
				+ "| one   |       |\n"
				+ "| two   |       |\n"
				+ "| three | six   |\n"
				+ "| four  | seven |\n"
				+ "| five  | eight |\n"
				+ "|-------|-------|\n",
				p2.getDisplayText());
	}
	/**
	 * Test right joining several panels. 
	 * @throws RPGException if an error occurs
	 */
	@Test
	public void canJoinRightNoBorder() throws RPGException {
		// join two panels, panel 1 left of panel 2, top aligned
		Panel p1 = new Panel(5, false, 5);
		p1.setContent("one\ntwo\nthree\nfour\nfive");
		assertEquals(p1.getDisplayText(),
				"one  \ntwo  \nthree\nfour \nfive \n");
		Panel p2 = new Panel(7, false, 3);
		p2.setContent("six\nseven\neight");
		assertEquals(p2.getDisplayText(), "six    \nseven  \neight  \n");
		p1.join(p2, Panel.RIGHT, Panel.TOP);
		assertEquals(p1.getDisplayText(),
				"six    one  \nseven  two  \neight  three\n       four "
				+ "\n       five \n");
		
		// join two panels, panel 2 left of panel 1 top aligned
		p1 = new Panel(5, false, 5);
		p1.setContent("one\ntwo\nthree\nfour\nfive");
		p2.join(p1, Panel.RIGHT, Panel.TOP);
		assertEquals(p2.getDisplayText(),
				"one  six    \ntwo  seven  \nthreeeight  \nfour        \n"
				+ "five        \n");
		
		// join two panels, panel 2 left of panel 1 center aligned
		p2 = new Panel(7, false, 3);
		p2.setContent("six\nseven\neight");
		p1.join(p2, Panel.RIGHT, Panel.CENTER);
		assertEquals(p1.getDisplayText(),
				"       one  \nsix    two  \nseven  three\neight  four "
				+ "\n       five \n");
		
		// join two panels, panel 2 left of panel 1 center aligned
		p1 = new Panel(5, false, 5);
		p1.setContent("one\ntwo\nthree\nfour\nfive");
		p2.join(p1, Panel.RIGHT, Panel.CENTER);
		assertEquals(p2.getDisplayText(),
				"one         \ntwo  six    \nthreeseven  \nfour eight  \n"
				+ "five        \n");
		
		// join two panels, panel 1 left of panel 2, bottom aligned
		p2 = new Panel(7, false, 3);
		p2.setContent("six\nseven\neight");
		p1.join(p2, Panel.RIGHT, Panel.BOTTOM);
		assertEquals(p1.getDisplayText(),
				"       one  \n       two  \nsix    three\nseven  four "
				+ "\neight  five \n");
		
		// join two panels, panel 2 left of panel 1, bottom aligned
		p1 = new Panel(5, false, 5);
		p1.setContent("one\ntwo\nthree\nfour\nfive");
		p2.join(p1, Panel.RIGHT, Panel.BOTTOM);
		assertEquals(p2.getDisplayText(),
				"one         \ntwo         \nthreesix    \nfour seven  \n"
				+ "five eight  \n");
		
		// join two panels, panel 1 left of panel 2, bottom aligned
		p2 = new Panel(9, true, 3);
		p2.setContent("six");
		p1.join(p2, Panel.RIGHT, Panel.BOTTOM);
		assertEquals(p1.getDisplayText(),
				"         one  \n         two  \n|-------|three\n"
				+ "| six   |four \n|-------|five \n");
		
		// join two panels, panel 1 left of panel 2, bottom aligned
		p1 = new Panel(5, false, 5);
		p1.setContent("one\ntwo\nthree\nfour\nfive");
		p1.join(p2, Panel.RIGHT, Panel.TOP);
		assertEquals(p1.getDisplayText(),
				"|-------|one  \n| six   |two  \n|-------|three"
				+ "\n         four \n         five \n");
	}
}
