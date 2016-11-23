package com.dalonedrow.module.barbarianprince.rpg.event;

import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.utils.ArrayUtilities;

/**
 * 
 * @author drau
 *
 */
public abstract class EventContainer {
	/** Creates a new instance of {@link EventContainer}. */
	protected EventContainer() {
		events = new Event[0];
	}
	/** the list of events. */
	private Event[] events;
	/** the chain of events' name. */
	private char[] name;
	/**
	 * Adds an {@link Event} to be fired.
	 * @param event the {@link Event} being added
	 */
	public final void addEvent(final Event event) {
		events = ArrayUtilities.getInstance().extendArray(event, events);
		event.setParent(this);
	}
	/**
	 * Adds an {@link Event} to be fired to the beginning of the list.
	 * @param event the {@link Event} being added
	 */
	public final void addEventToBeginning(final Event event) {
		events = ArrayUtilities.getInstance().prependArray(event, events);
		event.setParent(this);
	}
	/** Clears all events. */
	public final void clear() {
		events = new Event[0];
	}
	/** Fires all actions until control is returned to the user. 
	 * @throws RPGException if an error occurs
	 */
	public final void fireActions() throws RPGException {
		// CHECK FOR END OF GAME
		if (events.length > 0) {
			// get the first event.
			Event event = events[0];
			// if the event is resolved, remove it, and continue the next action
			if (event.isResolved()) {
				events = ArrayUtilities.getInstance().removeIndex(0, events);
				fireActions();
			} else { // fire the event
				event.fire();
			}
		}
	}
	/**
	 * Determines is the {@link EventContainer} has any events.
	 * @return <tt>true</tt> if some events are left; <tt>false</tt> otherwise
	 */
	public boolean hasActions() {
		return events.length > 0;
	}
	/**
	 * Gets the name.
	 * @return {@link String}
	 */
	public final String getName() {
		return new String(name);
	}
	/**
	 * Sets the value of the name.
	 * @param val the new value to set
	 */
	public final void setName(final char[] val) {
		name = val;
	}
	/**
	 * Sets the value of the name.
	 * @param val the new value to set
	 */
	public final void setName(final String val) {
		if (val != null) {
			name = val.toCharArray();
		}
	}
}
