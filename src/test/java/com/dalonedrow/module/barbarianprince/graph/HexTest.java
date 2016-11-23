package com.dalonedrow.module.barbarianprince.graph;

import static org.junit.Assert.*;

import org.junit.Test;

import com.dalonedrow.module.barbarianprince.constants.TurnActions;
import com.dalonedrow.rpg.base.flyweights.RPGException;

public class HexTest {
	@Test
	public void canGetActions() throws RPGException {
		// check Ogon
		Hex hex = HexMap.getInstance().getHex(1, 1);
		assertTrue(hex.canPerformAction(TurnActions.ACTION_REST));
		assertTrue(hex.canPerformAction(TurnActions.ACTION_LAND_TRAVEL));
		assertTrue(hex.canPerformAction(TurnActions.ACTION_RIVER_TRAVEL));
		assertTrue(hex.canPerformAction(TurnActions.ACTION_NEWS));
		assertTrue(hex.canPerformAction(TurnActions.ACTION_HIRE));
		assertTrue(hex.canPerformAction(TurnActions.ACTION_AUDIENCE));
		assertFalse(hex.canPerformAction(TurnActions.ACTION_OFFERING));
		assertFalse(hex.canPerformAction(TurnActions.ACTION_SEARCH_RUINS));
		// check Kabir Desert
		hex = HexMap.getInstance().getHex(15, 10);
		assertTrue(hex.canPerformAction(TurnActions.ACTION_REST));
		assertTrue(hex.canPerformAction(TurnActions.ACTION_LAND_TRAVEL));
		assertFalse(hex.canPerformAction(TurnActions.ACTION_RIVER_TRAVEL));
		assertFalse(hex.canPerformAction(TurnActions.ACTION_NEWS));
		assertFalse(hex.canPerformAction(TurnActions.ACTION_HIRE));
		assertFalse(hex.canPerformAction(TurnActions.ACTION_AUDIENCE));
		assertFalse(hex.canPerformAction(TurnActions.ACTION_OFFERING));
		assertFalse(hex.canPerformAction(TurnActions.ACTION_SEARCH_RUINS));
		// check Jakor's Keep
		hex = HexMap.getInstance().getHex(9, 1);
		assertTrue(hex.canPerformAction(TurnActions.ACTION_REST));
		assertTrue(hex.canPerformAction(TurnActions.ACTION_LAND_TRAVEL));
		assertTrue(hex.canPerformAction(TurnActions.ACTION_RIVER_TRAVEL));
		assertFalse(hex.canPerformAction(TurnActions.ACTION_NEWS));
		assertFalse(hex.canPerformAction(TurnActions.ACTION_HIRE));
		assertFalse(hex.canPerformAction(TurnActions.ACTION_AUDIENCE));
		assertFalse(hex.canPerformAction(TurnActions.ACTION_OFFERING));
		assertTrue(hex.canPerformAction(TurnActions.ACTION_SEARCH_RUINS));
		// check Donat's Temple
		hex = HexMap.getInstance().getHex(13, 9);
		assertTrue(hex.canPerformAction(TurnActions.ACTION_REST));
		assertTrue(hex.canPerformAction(TurnActions.ACTION_LAND_TRAVEL));
		assertFalse(hex.canPerformAction(TurnActions.ACTION_RIVER_TRAVEL));
		assertTrue(hex.canPerformAction(TurnActions.ACTION_NEWS));
		assertFalse(hex.canPerformAction(TurnActions.ACTION_HIRE));
		assertTrue(hex.canPerformAction(TurnActions.ACTION_AUDIENCE));
		assertTrue(hex.canPerformAction(TurnActions.ACTION_OFFERING));
		assertFalse(hex.canPerformAction(TurnActions.ACTION_SEARCH_RUINS));
		// check Huldra Castle
		hex = HexMap.getInstance().getHex(12, 12);
		assertTrue(hex.canPerformAction(TurnActions.ACTION_REST));
		assertTrue(hex.canPerformAction(TurnActions.ACTION_LAND_TRAVEL));
		assertFalse(hex.canPerformAction(TurnActions.ACTION_RIVER_TRAVEL));
		assertTrue(hex.canPerformAction(TurnActions.ACTION_NEWS));
		assertTrue(hex.canPerformAction(TurnActions.ACTION_HIRE));
		assertTrue(hex.canPerformAction(TurnActions.ACTION_AUDIENCE));
		assertFalse(hex.canPerformAction(TurnActions.ACTION_OFFERING));
		assertFalse(hex.canPerformAction(TurnActions.ACTION_SEARCH_RUINS));
	}
}
