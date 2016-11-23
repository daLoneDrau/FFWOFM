package com.dalonedrow.module.barbarianprince.turn;

import com.dalonedrow.module.barbarianprince.constants.BpScriptConsts;
import com.dalonedrow.module.barbarianprince.constants.UiSettings;
import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.pooled.PooledStringBuilder;
import com.dalonedrow.pooled.StringBuilderPool;
import com.dalonedrow.rpg.base.flyweights.ErrorMessage;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.systems.Script;

/**
 * @author drau
 */
public final class TimeTrack {
	/** the singleton instance. */
	private static TimeTrack instance;
	/**
	 * Gets the one and only instance of {@link TimeTrack}.
	 * @return {@link TimeTrack}
	 * @throws RPGException if an error occurs
	 */
	public static TimeTrack getInstance() throws RPGException {
		if (TimeTrack.instance == null) {
			TimeTrack.instance = new TimeTrack();
		}
		return TimeTrack.instance;
	}
	/** the current day. */
	private Day			day;
	/** the current time phase. */
	private TimePhase	time;
	/** the current week. */
	private Week		week;
	/** Hidden constructor. */
	private TimeTrack() {
		time = TimePhase.PHASE_00_PRE_ACTION;
		day = Day.One;
		week = Week.One;
	}
	/**
	 * Gets the current {@link Day}.
	 * @return {@link Day}
	 */
	public Day getDay() {
		return day;
	}
	/**
	 * Gets the current time phase.
	 * @return {@link TimePhase}
	 */
	public TimePhase getTime() {
		return time;
	}
	/**
	 * Gets the current {@link Week}.
	 * @return {@link Week}
	 */
	public Week getWeek() {
		return week;
	}
	/** Advances the day by one. */
	public void nextDay() {
		int current = day.getValue() + 1;
		if (current > Day.values().length) {
			current = 1;
			nextWeek();
		}
		for (int i = 0; i < Day.values().length; i++) {
			if (Day.values()[i].getValue() == current) {
				day = Day.values()[i];
				break;
			}
		}
	}
	/**
	 * Advances the time by one period. 
	 * @throws RPGException if an error occurs
	 */
	public void nextPhase() throws RPGException {
		time = time.advance();
		if (time == TimePhase.PHASE_00_PRE_ACTION) {
			nextDay();
		}
		Script.getInstance().sendMsgToAllIO(
				BpScriptConsts.SM_300_TIME_CHANGE, null);
	}
	/** Advances the week by one. */
	private void nextWeek() {
		int current = week.getValue() + 1;
		for (int i = 0; i < Week.values().length; i++) {
			if (Week.values()[i].getValue() == current) {
				week = Week.values()[i];
				break;
			}
		}
		if (current > Week.Ten.getValue()) {
			// do something to end the game
			// BPController.stopGame();
		}
	}
	/** Resets the {@link TimeTrack}. */
	public void reset() {
		time = TimePhase.PHASE_00_PRE_ACTION;
		day = Day.One;
		week = Week.One;
	}
	/**
	 * Gets the current time converted for display to the UI.
	 * @return {@link String}
	 * @throws RPGException 
	 */
	public String toUiString() throws RPGException {
		String s = "";
		try {
			PooledStringBuilder sb =
					StringBuilderPool.getInstance().getStringBuilder();
			sb.append("Day ");
			sb.append(day.getValue());
			sb.append(" Week ");
			sb.append(week.getValue());
			sb.append(" - ");
			sb.append(time.getTitle());
			s = sb.toString();
			sb.returnToPool();
			sb = null;
		} catch (PooledException e) {
			throw new RPGException(ErrorMessage.INTERNAL_ERROR, e);
		}
		return s;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		String s = "";
		try {
			PooledStringBuilder sb =
					StringBuilderPool.getInstance().getStringBuilder();
			sb.append(day.getAdjective());
			sb.append(" Day ");
			sb.append(week.getAdjective());
			sb.append(" Week ");
			sb.append(" - ");
			sb.append(time.getTitle());
			s = sb.toString();
			sb.returnToPool();
			sb = null;
		} catch (PooledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
}
