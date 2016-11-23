package com.dalonedrow.module.barbarianprince.rpg;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.dalonedrow.module.barbarianprince.rpg.BpInteractiveObject;
import com.dalonedrow.module.barbarianprince.systems.BpController;
import com.dalonedrow.module.barbarianprince.systems.BpInteractive;
import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.rpg.base.constants.IoGlobals;
import com.dalonedrow.rpg.base.flyweights.RPGException;

@SuppressWarnings("unchecked")
public class BPNPCTest {
	@Before
	public void before() throws RPGException {
		BpController.getInstance();
	}
	@Test
	public void canCreate() throws RPGException {
		BpNpc npc = new BpNpc();
		assertNotNull(npc);
	}
	@Test
	public void canMakeLocalGuide() throws RPGException, PooledException {
		BpNpc npc = new BpNpc();
		assertFalse(npc.isLocalGuide(0));
		npc.addLocalGuideHex(0);
		assertTrue(npc.isLocalGuide(0));
		npc.addLocalGuideHex(20);
		assertTrue(npc.isLocalGuide(0));
		assertTrue(npc.isLocalGuide(20));
	}
}
