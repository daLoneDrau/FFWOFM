package com.dalonedrow.module.barbarianprince.systems;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.dalonedrow.utils.Watchable;
import com.dalonedrow.utils.Watcher;

public final class GameStateTest {
	@Test
	public void watchersWork() {
		final StringBuffer sb = new StringBuffer();
		Watcher w = new Watcher() {
			@Override
			public void watchUpdated(final Watchable data) {
				sb.append("t");
			}
		};
		GameState.getInstance().addWatcher(w);
		GameState.getInstance().addWatcher(new Watcher() {
			@Override
			public void watchUpdated(final Watchable data) {
				sb.append("w");
			}
		});
		assertEquals("current state is 0",
				GameState.getInstance().getCurrentState(), 0);
		GameState.getInstance().nextState();
		assertEquals("current state is 1",
				GameState.getInstance().getCurrentState(), 1);
		assertEquals("watcher was notified", sb.toString(), "tw");
		GameState.getInstance().nextState();
		assertEquals("current state is 2",
				GameState.getInstance().getCurrentState(), 2);
		assertEquals("watcher was notified", sb.toString(), "twtw");
		GameState.getInstance().previousState();
		assertEquals("current state is 1",
				GameState.getInstance().getCurrentState(), 1);
		assertEquals("watcher was notified", sb.toString(), "twtwtw");
		GameState.getInstance().previousState();
		assertEquals("current state is 0",
				GameState.getInstance().getCurrentState(), 0);
		assertEquals("watcher was notified", sb.toString(), "twtwtwtw");
		GameState.getInstance().removeWatcher(w);
		GameState.getInstance().setCurrentState(255);
		assertEquals("current state is 255",
				GameState.getInstance().getCurrentState(), 255);
		assertEquals("watcher was not notified", sb.toString(),
				"twtwtwtww");
	}
}
