package com.dalonedrow.module.barbarianprince.log;

import com.dalonedrow.module.barbarianprince.graph.HexMap;
import com.dalonedrow.module.barbarianprince.turn.Day;
import com.dalonedrow.module.barbarianprince.turn.TimePhase;
import com.dalonedrow.module.barbarianprince.turn.Week;
import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.pooled.PooledStringBuilder;
import com.dalonedrow.pooled.StringBuilderPool;
import com.dalonedrow.rpg.base.flyweights.RPGException;

/**
 * @author drau
 */
public final class LogEntry implements Comparable<LogEntry> {
	/** the entry value. */
	private final Object	value;
	/** the entry code. */
	private final int	code;
	/** the entry time. */
	private final Time		time;
	/**
	 * Creates a new instance of {@link LogEntry}.
	 * @param p the {@link TimePhase} the entry takes place
	 * @param d the {@link Day} the entry takes place
	 * @param w the {@link Week} the entry takes place
	 * @param desc the entry text
	 */
	public LogEntry(final TimePhase p, final Day d, final Week w, final int c,
			final Object v) {
		time = new Time(p, d, w);
		code = c;
		value = v;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		try {
			sb.append(time);
			sb.append(" - ");
			switch (code) {
			case HeroLog.LOG_CODE_MESSAGE:
			case HeroLog.LOG_CODE_ENCOUNTER:
				sb.append((String) value);
				break;
			case HeroLog.LOG_CODE_MOVE:
				sb.append("Traveled to ");
				sb.append(HexMap.getInstance().getHexById(
						(Integer) value).toMsgString());
				break;
			case HeroLog.LOG_CODE_DESERTER:
				sb.append((String) value);
				sb.append(" left the party.");
				break;
			case HeroLog.LOG_CODE_LOST:
				sb.append("You became lost trying to leave ");
				sb.append(HexMap.getInstance().getHexById(
						(Integer) value).toMsgString());
				break;
			}
		} catch (PooledException | RPGException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s = sb.toString();
		sb.returnToPool();
		sb = null;
		return s;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(final LogEntry arg0) {
		return arg0.time.compareTo(time);
	}
	/**
	 * Gets the value for the value.
	 * @return {@link Object}
	 */
	public Object getValue() {
		return value;
	}
	/**
	 * Gets the value for the code.
	 * @return {@link int}
	 */
	public int getCode() {
		return code;
	}
	/**
	 * Gets the value for the time.
	 * @return {@link Time}
	 */
	public Time getTime() {
		return time;
	}
}
/**
 * 
 * @author drau
 *
 */
class Time implements Comparable<Time> {
	/** the day. */
	private final Day		day;
	/** the week. */
	private final TimePhase	phase;
	/** the week. */
	private final Week		week;
	/**
	 * Creates a new instance of {@link Time}.
	 * @param p the {@link TimePhase}
	 * @param d the {@link Day}
	 * @param w the {@link Week}
	 */
	Time(final TimePhase p, final Day d, final Week w) {
		phase = p;
		day = d;
		week = w;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(final Time arg0) {
		int compare = ((Time) arg0).week.compareTo(week);
		if (compare == 0) {
			compare = ((Time) arg0).day.compareTo(day);
		}
		if (compare == 0) {
			compare = ((Time) arg0).phase.compareTo(phase);
		}
		return compare;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		try {
			sb.append(phase.getTitle());
			sb.append(", ");
			sb.append(day.getAdjective());
			sb.append(" day of the ");
			sb.append(week.getAdjective());
			sb.append(" week");
		} catch (PooledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s = sb.toString();
		sb.returnToPool();
		sb = null;
		return s;
	}
}
