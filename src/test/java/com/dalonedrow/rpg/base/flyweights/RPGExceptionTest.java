package com.dalonedrow.rpg.base.flyweights;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RPGExceptionTest {
	@Test
	public void canCreate() {
		RPGException ex =
				new RPGException(ErrorMessage.BAD_PARAMETERS,
						new Exception("msg"));
		assertEquals(ex.getDeveloperMessage(), "msg");
		assertEquals(ex.getErrorMessage(), ErrorMessage.BAD_PARAMETERS);
	}
}
