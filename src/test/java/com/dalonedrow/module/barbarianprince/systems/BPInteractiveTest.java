package com.dalonedrow.module.barbarianprince.systems;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.module.barbarianprince.rpg.BpInteractiveObject;
import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.rpg.base.constants.IoGlobals;
import com.dalonedrow.rpg.base.flyweights.RPGException;

@SuppressWarnings("unchecked")
public class BPInteractiveTest {
	@Before
	public void before() throws RPGException {
		BpController.getInstance();
	}
	@Test
	public void canCreatePlayer() throws RPGException, PooledException {
		((BpInteractive) Interactive.getInstance()).newPlayer();
		BpInteractiveObject io =
				(BpInteractiveObject) Interactive.getInstance().getIO(
						((BpInteractive) Interactive.getInstance())
								.getPlayerIO());
		assertTrue(io.hasIOFlag(IoGlobals.IO_01_PC));
	}
	@Test
	public void canGetNewIO() throws RPGException {
		for (int i =
				Interactive.getInstance().getNumberIOs() - 1; i >= 0; i--) {
			Interactive.getInstance().releaseIO(i);
		}
		// the master script IO is left - it can never be removed
		assertEquals(Interactive.getInstance().getNumberIOs(), 1);
		assertTrue(Interactive.getInstance().hasIO(0));
		BpInteractiveObject io =
				((BpInteractive) Interactive.getInstance()).newMob(4);
		assertEquals(Interactive.getInstance().getNumberIOs(), 2);
		assertNotNull(io);
		assertTrue(Interactive.getInstance().hasIO(io.getRefId()));
		assertFalse(Interactive.getInstance().isSameObject(
				io, Interactive.getInstance().getIO(io.getRefId())));
		assertEquals(io, Interactive.getInstance().getIO(io.getRefId()));
		Interactive.getInstance().releaseIO(io.getRefId());
		assertFalse(Interactive.getInstance().hasIO(io.getRefId()));
		assertNotNull(io);
		io = ((BpInteractive) Interactive.getInstance()).newMob(4);
		int id = io.getRefId();
		Interactive.getInstance().releaseIO(io);
		assertFalse(Interactive.getInstance().hasIO(id));
	}
}
