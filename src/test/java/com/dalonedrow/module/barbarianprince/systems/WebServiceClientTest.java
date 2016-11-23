package com.dalonedrow.module.barbarianprince.systems;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.module.barbarianprince.graph.Hex;
import com.dalonedrow.module.barbarianprince.rpg.BpInteractiveObject;
import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.rpg.base.flyweights.RPGException;

public class WebServiceClientTest {
	@Before
	public void before() throws RPGException {
		BpController.getInstance();
	}
	@Test
	public void canGetTreasure() throws PooledException {
		String[] l = WebServiceClient.getInstance().getTreasure("25", 1);
		assertTrue(l[0].equalsIgnoreCase("20"));
		assertTrue(l[1].equalsIgnoreCase("roll|A"));
	}
	@Test
	public void canLoadCharacters() {
		Object[] pc = WebServiceClient.getInstance().getCharacter(0);
		assertTrue("Cal Arath".equalsIgnoreCase((String) pc[7]));
	}
	@Test
	public void canLoadHexes() {
		Hex[] l = WebServiceClient.getInstance().loadHexes();
		for (int i = 0, len = l.length; i < len; i++) {
			System.out.println(l[i].getIndex());
		}
	}
	@Test
	public void canLoadMob() throws RPGException {
		BpInteractiveObject npc =
				((BpInteractive) Interactive.getInstance()).newMob(75);
		assertTrue(npc.isInGroup("CANNOT_MOUNT"));
		assertEquals("Giant", new String(npc.getNPCData().getName()));
		assertEquals((int) npc.getNPCData().getFullAttributeScore("EN"), 8);
		assertEquals((int) npc.getNPCData().getFullAttributeScore("WE"), 10);
		assertEquals((int) npc.getNPCData().getFullAttributeScore("CS"), 9);
	}
	@Test
	public void canLoadRiverCrossings() {
		WebServiceClient.getInstance().loadRiverCrossings();
		assertEquals(WebServiceClient.getInstance().getRiverCrossingName(0, 1),
				"Tragoth River");
	}
	@Test
	public void canLoadRoads() {
		int[][] l = WebServiceClient.getInstance().loadRoads();
		for (int i = 0, len = l.length; i < len; i++) {
			int[] a = l[i];
			System.out.println(a[0] + "," + a[1]);
		}
	}
	@Test
	public void canLoadText() {
		assertTrue("<tioName> follows the road to <genText>.".equalsIgnoreCase(
				WebServiceClient.getInstance().loadText("move_msg_road")));
	}
}
