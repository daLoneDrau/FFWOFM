package com.dalonedrow.module.barbarianprince.log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dalonedrow.rpg.base.flyweights.RPGException;

/**
 * 
 * @author drau
 *
 */
public final class HeroLog {
	public static final int LOG_CODE_MESSAGE = 0;
	public static final int LOG_CODE_MOVE = 1;
	public static final int LOG_CODE_ENCOUNTER = 2;
	public static final int LOG_CODE_DESERTER = 3;
	public static final int LOG_CODE_LOST = 4;
	public static final int LOG_CODE_REST = 5;
	/** the singleton instance. */
	private static HeroLog instance;
	/**
	 * Gets the one and only instance of {@link FFWoFMController}.
	 * @return {@link FFWoFMController}
	 */
	public static HeroLog getInstance() {
		if (HeroLog.instance == null) {
			HeroLog.instance = new HeroLog();
		}
		return HeroLog.instance;
	}
	private HeroLog() {
		entries = new ArrayList<LogEntry>();
	}
	public int getNumberOfEntries() {
		return entries.size();
	}
	public LogEntry getEntry(final int entry) {
		return entries.get(entry);
	}
	public void addEntry(final LogEntry entry) {
		entries.add(entry);
		Collections.sort(entries);
	}
	private List<LogEntry> entries;
}
