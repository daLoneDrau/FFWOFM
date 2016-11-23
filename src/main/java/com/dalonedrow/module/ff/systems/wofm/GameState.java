package com.dalonedrow.module.ff.systems.wofm;

import java.util.ArrayList;

import com.dalonedrow.utils.Watchable;
import com.dalonedrow.utils.Watcher;

/**
 * 
 * @author drau
 *
 */
public final class GameState implements Watchable {
	/** the one and only instance of the <code>GameState</code> class. */
	private static GameState	instance;
	/** game initializing. */
	public static final int		STATE_00_INITIALIZING		= 0;
	/** game in-play. */
	public static final int		STATE_01_IN_PLAY			= 1;
	/** game in-combat. */
	public static final int		STATE_02_IN_COMBAT			= 2;
	/**
	 * Gives access to the singleton instance of {@link GameState}.
	 * @return {@link GameState}
	 */
	public static GameState getInstance() {
		if (GameState.instance == null) {
			GameState.instance = new GameState();
		}
		return GameState.instance;
	}
	/** This data member will store the current state. */
	private int					currentState;
	/** the list of watchers. */
	private ArrayList<Watcher>	watchers;
	/** Hidden constructor. */
	private GameState() {
		super();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addWatcher(final Watcher watcher) {
		final int max = 100;
		if (watchers == null) {
			watchers = new ArrayList<Watcher>(max);
		}
		watchers.add(watcher);
	}
	/**
	 * Gets the current state.
	 * @return {@link int}
	 */
	public int getCurrentState() {
		return currentState;
	}
	/** Goes to the next game state. */
	public void nextState() {
		currentState++;
		notifyWatchers();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void notifyWatchers() {
		if (watchers != null) {
			for (int i = 0; i < watchers.size(); i++) {
				watchers.get(i).watchUpdated(this);
			}
		}
	}
	/** Goes to the previous state. */
	public void previousState() {
		currentState--;
		notifyWatchers();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeWatcher(final Watcher watcher) {
		if (watchers != null) {
			watchers.remove(watcher);
		}
	}
	/**
	 * Sets the current state.
	 * @param value the value to set
	 */
	public void setCurrentState(final int value) {
		currentState = value;
		notifyWatchers();
	}
}

