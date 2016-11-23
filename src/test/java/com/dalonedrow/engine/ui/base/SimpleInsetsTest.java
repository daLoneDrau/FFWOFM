package com.dalonedrow.engine.ui.base;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class SimpleInsetsTest {
	SimpleInsets insets;
	@Before
	public void before() {
		insets = new SimpleInsets(1, 1, 1, 1);
	}
	@Test
	public void canCreateInsets() {
		assertEquals("top inset is 1", insets.top, 1);
	}
	@Test
	public void canGetString() {
		assertEquals("got String", insets.toString(),
				"com.dalonedrow.engine.ui.base.SimpleInsets[top=1, left=1, bottom=1, right=1]");
	}
	@Test
	public void canSetInsets() {
		insets.set(2, 2, 2, 2);
		assertEquals("top inset is 2", insets.top, 2);
	}
}
