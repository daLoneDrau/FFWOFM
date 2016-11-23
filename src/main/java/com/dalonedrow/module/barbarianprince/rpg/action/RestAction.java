package com.dalonedrow.module.barbarianprince.rpg.action;

import com.dalonedrow.engine.systems.base.Diceroller;
import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.module.barbarianprince.constants.BpScriptConsts;
import com.dalonedrow.module.barbarianprince.graph.HexMap;
import com.dalonedrow.module.barbarianprince.graph.HexType;
import com.dalonedrow.module.barbarianprince.log.HeroLog;
import com.dalonedrow.module.barbarianprince.log.LogEntry;
import com.dalonedrow.module.barbarianprince.rpg.BpIoGroup;
import com.dalonedrow.module.barbarianprince.systems.BpController;
import com.dalonedrow.module.barbarianprince.systems.BpScript;
import com.dalonedrow.module.barbarianprince.systems.WebServiceClient;
import com.dalonedrow.module.barbarianprince.turn.TimeTrack;
import com.dalonedrow.module.barbarianprince.ui.console.GUI;
import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.rpg.base.consoleui.TextProcessor;
import com.dalonedrow.rpg.base.constants.Dice;
import com.dalonedrow.rpg.base.flyweights.ErrorMessage;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.systems.Script;

/**
 * @author drau
 */
@SuppressWarnings("unchecked")
public final class RestAction implements TurnAction {
	/** the singleton instance. */
	private static RestAction instance;
	/**
	 * Gets the one and only instance of {@link RestAction}.
	 * @return {@link RestAction}
	 */
	public static RestAction getInstance() {
		if (RestAction.instance == null) {
			RestAction.instance = new RestAction();
		}
		return RestAction.instance;
	}
	/** debugging flag. used for test cases. */
	private boolean	debug;
	/** flag indicating an event happens. */
	private boolean	event;
	/** flag indicating the action was resolved. */
	private boolean	resolved;
	/**
	 * Determines if the party has an event.
	 * @param roll the die roll, plus modifiers
	 * @param onRoad flag indicating the party is following a road
	 * @param type the hex terrain being left
	 * @return <tt>true</tt> if the party has a travel event; <tt>false</tt>
	 *         otherwise
	 */
	private boolean doesEventHappen(final int roll, final boolean onRoad,
			final HexType type) {
		boolean does = false;
		final int eight = 8, nine = 9, ten = 10;
		if (onRoad) {
			if (roll >= ten) {
				does = true;
			}
		} else {
			switch (type) {
			case FARM:
				if (roll >= eight) {
					does = true;
				}
				break;
			case COUNTRY:
			case FOREST:
			case MOUNTAIN:
				if (roll >= nine) {
					does = true;
				}
				break;
			default: // HILL, DESERT, or SWAMP
				if (roll >= ten) {
					does = true;
				}
				break;
			}
		}
		return does;
	}
	/**
	 * Executes steps when party has a travel event.
	 * @param onRoad flag indicating the party is on a road
	 * @param terrain the type of terrain
	 * @throws RPGException if an error occurs
	 */
	private void event(final boolean onRoad, final HexType terrain)
			throws RPGException {
		// party has an event.
		// get the event
		// no more travel today
		int roll1 = Diceroller.getInstance().rolldX(Dice.D6);
		int roll2 = Diceroller.getInstance().rolldX(Dice.D6);
		Object[] eventData;
		try {
			if (onRoad) {
				eventData = WebServiceClient.getInstance().getTravelEvent(
						"Road", roll1, roll2);
			} else {
				eventData = WebServiceClient.getInstance().getTravelEvent(
						terrain.getTitle(), roll1, roll2);
			}
		} catch (PooledException e) {
			throw new RPGException(ErrorMessage.INTERNAL_ERROR, e);
		}
		// add event to phase
		((BpScript) Script.getInstance()).stackSendPostScriptEvent(
				((BpScript) Script.getInstance()).getMasterScript(),
				(Integer) eventData[0], // event code
				new Object[] { "turn_action", BpScriptConsts.ACTION_REST },
				(String) eventData[1]); // event name
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() throws RPGException {
		int party = BpController.getInstance().getPlayerParty();
		if (!Interactive.getInstance().hasIO(party)) {
			throw new RPGException(ErrorMessage.ILLEGAL_OPERATION,
					"Cannot move player before game has started");
		}
		BpIoGroup group = (BpIoGroup) Interactive.getInstance().getIO(party);
		boolean onRoad = HexMap.getInstance().isOnRoad(group.getLocation());
		if (!debug) {
			event = doesEventHappen(
					Diceroller.getInstance().rollXdY(
							2, Dice.D6),
					onRoad,
					HexMap.getInstance().getHexById(
							group.getLocation()).getType());
		}
		if (event) {
			String s = WebServiceClient.getInstance().loadText("rest_msg_fail");
			s = TextProcessor.getInstance().processText(
					null,
					group,
					(String[]) null,
					s);
			GUI.getInstance().addMessage(GUI.MESSAGE_INFO, s);
			HeroLog.getInstance().addEntry(new LogEntry(
					TimeTrack.getInstance().getTime(),
					TimeTrack.getInstance().getDay(),
					TimeTrack.getInstance().getWeek(),
					HeroLog.LOG_CODE_REST,
					s));
			s = null;
			event(onRoad, HexMap.getInstance().getHexById(
					group.getLocation()).getType());
		} else {
			Script.getInstance().stackSendIOScriptEvent(
					group,
					BpScriptConsts.SM_078_REST,
					null,
					"rest");
		}
		group = null;
		System.out.println("rest action complete");
	}
	@Override
	public boolean isResolved() {
		return resolved;
	}
	@Override
	public void reset() throws RPGException {
		event = false;
		resolved = false;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setDebug(final boolean val) {
		debug = val;
	}
	/**
	 * Sets the flag indicating a travel event happens. Only for debugging.
	 * @param val the new value to set
	 */
	public void setEventDebugging(final boolean val) {
		if (debug) {
			event = val;
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setResolved(final boolean val) {
		resolved = val;
	}
}
