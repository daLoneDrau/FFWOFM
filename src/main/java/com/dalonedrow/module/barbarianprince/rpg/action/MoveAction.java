package com.dalonedrow.module.barbarianprince.rpg.action;

import com.dalonedrow.engine.systems.base.Diceroller;
import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.module.barbarianprince.constants.BpScriptConsts;
import com.dalonedrow.module.barbarianprince.graph.Hex;
import com.dalonedrow.module.barbarianprince.graph.HexMap;
import com.dalonedrow.module.barbarianprince.graph.HexType;
import com.dalonedrow.module.barbarianprince.rpg.BpIoGroup;
import com.dalonedrow.module.barbarianprince.rpg.action.param.MoveActionParameters;
import com.dalonedrow.module.barbarianprince.systems.BpController;
import com.dalonedrow.module.barbarianprince.systems.BpScript;
import com.dalonedrow.module.barbarianprince.systems.WebServiceClient;
import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.rpg.base.constants.Dice;
import com.dalonedrow.rpg.base.constants.ScriptConsts;
import com.dalonedrow.rpg.base.flyweights.ErrorMessage;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.systems.Script;
import com.dalonedrow.rpg.graph.WeightedGraphEdge;

/**
 * @author drau
 */
@SuppressWarnings("unchecked")
public final class MoveAction implements TurnAction {
	/** the roll to determine if the party suffers from airborne drift. */
	public static final int		AIRBORNE_DRIFT_ROLL	= 4;
	/** the singleton instance. */
	private static MoveAction	instance;
	/** the maximum # of hexes that can be traveled while airborne. */
	public static final int		MAX_AIRBORNE_TRAVEL	= 3;
	/** the maximum # of hexes that can be traveled while mounted. */
	public static final int		MAX_MOUNTED_TRAVEL	= 2;
	/**
	 * Gets the one and only instance of {@link MoveAction}.
	 * @return {@link MoveAction}
	 */
	public static MoveAction getInstance() {
		if (MoveAction.instance == null) {
			MoveAction.instance = new MoveAction();
		}
		return MoveAction.instance;
	}
	/** flag indicating whether the move was taken while airborne. */
	private boolean	airborne;
	/** debugging flag. used for test cases. */
	private boolean	debug;
	/** the move destination. */
	private int		destination;
	/** flag indicating the party drifts when lost and airborne. */
	private boolean	drift;
	/** the number of hexes traveled this turn. */
	private int		hexesTravelled;
	/** the local guide set for the move. */
	private int		localGuide;
	/** flag indicating the local guide deserts the group. */
	private boolean	localGuideDeserts;
	/** flag indicating the party gets lost. */
	private boolean	lost;
	/** the max number of hexes possibly traveled this turn. */
	private int		maxTravel;
	/** flag indicating the party is rafting. */
	private boolean	rafting;
	/** flag indicating a travel event happens. */
	private boolean	travelEvent;
	/** Creates a new instance of {@link MoveAction}. */
	private MoveAction() {
		localGuide = -1;
		destination = -1;
		hexesTravelled = 0;
		maxTravel = 0;
	}
	/**
	 * Checks to see if the party gets lost attempting to leave a type of
	 * terrain.
	 * @param crossingRiver flag indicating the party is crossing a river
	 * @param terrain the type of terrain
	 */
	private void checkForLost(final boolean crossingRiver,
			final HexType terrain) {
		// r205 - roll 2 dice
		int roll = Diceroller.getInstance().rollXdY(2, Dice.D6);
		// r205a - is there a local guide?
		if (localGuide >= 0) {
			roll--;
		}
		lost = doesPartyGetLost(roll, crossingRiver, terrain);
	}
	/** Clears the reference id for the local guide for the last move. */
	public void clearLocalGuide() {
		localGuide = -1;
	}
	/**
	 * Checks to see if the local guide deserts the party.
	 * @return <tt>true</tt> if the local guide deserts; <tt>false</tt>otherwise
	 */
	private boolean doesLocalGuideDesert() {
		final int four = 4;
		return Diceroller.getInstance().rolldX(Dice.D6) >= four;
	}
	/**
	 * Determines if the party gets lost.
	 * @param roll the die roll, plus modifiers
	 * @param crossingRiver flag indicating the party is crossing a river
	 * @param type the hex terrain being left
	 * @return <tt>true</tt> if the party gets lost; <tt>false</tt> otherwise
	 */
	private boolean doesPartyGetLost(final int roll,
			final boolean crossingRiver, final HexType type) {
		boolean l = false;
		final int five = 5, six = 6, seven = 7, eight = 8, nine = 9, ten = 10;
		final int twelve = 12;
		if (airborne) {
			if (roll >= twelve) {
				l = true;
			}
		} else if (crossingRiver) {
			if (roll >= eight) {
				l = true;
			}
		} else {
			switch (type) {
			case FARM:
				if (roll >= ten) {
					l = true;
				}
				break;
			case COUNTRY:
				if (roll >= nine) {
					l = true;
				}
				break;
			case FOREST:
			case HILL:
				if (roll >= eight) {
					l = true;
				}
				break;
			case MOUNTAIN:
				if (roll >= seven) {
					l = true;
				}
				break;
			case DESERT:
				if (roll >= six) {
					l = true;
				}
			default: // SWAMP
				if (roll >= five) {
					l = true;
				}
				break;
			}
		}
		return l;
	}
	/**
	 * Determines if the party has a travel event.
	 * @param roll the die roll, plus modifiers
	 * @param crossingRiver flag indicating the party is crossing a river
	 * @param onRoad flag indicating the party is following a road
	 * @param type the hex terrain being left
	 * @return <tt>true</tt> if the party has a travel event; <tt>false</tt>
	 *         otherwise
	 */
	private boolean doesTravelEventHappen(final int roll,
			final boolean crossingRiver, final boolean onRoad,
			final HexType type) {
		boolean event = false;
		final int eight = 8, nine = 9, ten = 10;
		if (airborne || crossingRiver || rafting) {
			if (roll >= ten) {
				event = true;
			}
		} else if (onRoad) {
			if (roll >= ten) {
				event = true;
			}
		} else {
			switch (type) {
			case FARM:
				if (roll >= eight) {
					event = true;
				}
				break;
			case COUNTRY:
			case FOREST:
			case MOUNTAIN:
				if (roll >= nine) {
					event = true;
				}
				break;
			default: // HILL, DESERT, or SWAMP
				if (roll >= ten) {
					event = true;
				}
				break;
			}
		}
		return event;
	}
	/** Ends the possibility of any more moves this turn. */
	public void endMoves() {
		hexesTravelled = maxTravel;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute() throws RPGException {
		System.out.println("MA.exec");
		int party = BpController.getInstance().getPlayerParty();
		if (!Interactive.getInstance().hasIO(party)) {
			throw new RPGException(ErrorMessage.ILLEGAL_OPERATION,
					"Cannot move player before game has started");
		}
		BpIoGroup group = (BpIoGroup) Interactive.getInstance().getIO(party);
		if (group.getLocation() == -1) {
			// move party immediately if not on map
			moveInstant(HexMap.getInstance().getHexById(destination), group);
		} else {
			// get path
			WeightedGraphEdge[] path;
			if (rafting) {
				maxTravel = 1;
				path = HexMap.getInstance().getRiverPathList(
						HexMap.getInstance().getHexById(group.getLocation()),
						HexMap.getInstance().getHexById(destination));
			} else {
				if (airborne) {
					path = HexMap.getInstance().getAirPath(
							group.getLocation(), destination);
				} else {
					path = HexMap.getInstance().getLandPath(
							group.getLocation(), destination);
				}
			}
			// move one edge at a time
			if (rafting) {
				Hex from = HexMap.getInstance().getHexById(
						group.getLocation());
				Hex to = HexMap.getInstance().getHexById(destination);
				moveByRaft(from, to, group);
				// check for rafting event
				if (!debug) {
					travelEvent = doesTravelEventHappen(
							Diceroller.getInstance().rollXdY(
									2, Dice.D6),
							false,
							false,
							to.getType());
				}
				if (travelEvent) {
					travelEvent(false, false, to.getType());
				}
				// check for travel event
				rafting = false;
				if (!debug) {
					travelEvent = doesTravelEventHappen(
							Diceroller.getInstance().rollXdY(
									2, Dice.D6),
							false,
							false,
							to.getType());
				}
				if (travelEvent) {
					travelEvent(false, false, to.getType());
				}
			} else {
				// go through all edges, checking for lost along the way
				if (!debug) {
					lost = false;
				}
				// undirected edges - move one step at a time
				int lastDest = group.getLocation();
				for (int i = path.length - 1; i >= 0; i--) {
					WeightedGraphEdge edge = path[i];
					int dest;
					if (edge.getFrom() == lastDest) {
						dest = edge.getTo();
					} else {
						dest = edge.getFrom();
					}
					Hex from = HexMap.getInstance().getHexById(lastDest);
					Hex to = HexMap.getInstance().getHexById(dest);
					lastDest = dest;
					boolean onRoad = !airborne
							&& HexMap.getInstance().hasRoadTo(from, to, 1);
					boolean crossingRiver = !airborne
							&& HexMap.getInstance().hasRiverCrossingTo(
									from, to);
					if (onRoad) { // on road. cannot get lost
						moveByRoad(to, group);
					} else {
						if (!debug) {
							checkForLost(crossingRiver, from.getType());
						}
						if (lost) {
							lost(group, crossingRiver, from, to);
						} else {
							if (crossingRiver) {
								moveByCrossingRiver(from, to, group);
							} else {
								move(to, group);
							}
						}
					}
					if (!debug) {
						if (lost) {
							travelEvent = doesTravelEventHappen(
									Diceroller.getInstance().rollXdY(
											2, Dice.D6),
									crossingRiver,
									onRoad,
									from.getType());
						} else {
							travelEvent = doesTravelEventHappen(
									Diceroller.getInstance().rollXdY(
											2, Dice.D6),
									crossingRiver,
									onRoad,
									to.getType());
						}
					}
					if (travelEvent) {
						if (lost) {
							travelEvent(crossingRiver, onRoad, from.getType());
						} else {
							travelEvent(crossingRiver, onRoad, to.getType());
						}
					}
					edge = null;
					from = null;
					to = null;
					if (lost || travelEvent) {
						// no more traveling, until event is handled
						break;
					}
				}
			}
			path = null;
		}
		group = null;
	}
	/** Adds for the possibility of one more move this turn. */
	public void extraMove() {
		hexesTravelled--;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isResolved() {
		return hexesTravelled >= maxTravel;
	}
	/**
	 * Executes steps when party becomes lost.
	 * @param group the group
	 * @param crossingRiver flag indicating whether the group was trying to
	 *            cross a river
	 * @param from the departure {@link Hex}
	 * @param to the destination {@link Hex}
	 * @throws RPGException if an error occurs
	 */
	private void lost(final BpIoGroup group, final boolean crossingRiver,
			final Hex from, final Hex to) throws RPGException {
		if (airborne) {
			if (!debug) {
				drift =
						Diceroller.getInstance()
								.rolldX(Dice.D6) <= AIRBORNE_DRIFT_ROLL;
			}
			if (drift) {
				lostAirborneDrift(from, group);
			} else {
				lostAirborne(from, group);
			}
		} else if (crossingRiver) {
			lostCrossingRiver(from, to, group);
		} else {
			lostByGround(from, group);
		}
		if (localGuide >= 0) {
			if (!debug) {
				localGuideDeserts = doesLocalGuideDesert();
			}
			if (localGuideDeserts) {
				Script.getInstance().stackSendIOScriptEvent(
						group,
						BpScriptConsts.SM_076_DESERT,
						new Object[] {
								"io_id", localGuide,
								"code", BpScriptConsts.CODE_LOST_GUIDE },
						"move");
			}
		}
	}
	/**
	 * Executes a lost result when traveling airborne.
	 * @param from the departure
	 * @param group the group being moved
	 * @throws RPGException if an error occurs
	 */
	private void lostAirborne(final Hex from, final BpIoGroup group)
			throws RPGException {
		Script.getInstance().stackSendIOScriptEvent(
				group,
				BpScriptConsts.SM_077_LOST,
				new Object[] { "move_departure", from.getIndex(),
						"code", BpScriptConsts.CODE_LOST_AIRBORNE },
				"move");
	}
	/**
	 * Executes a lost, drifting result when traveling airborne.
	 * @param from the departure
	 * @param group the group being moved
	 * @throws RPGException if an error occurs
	 */
	private void lostAirborneDrift(final Hex from, final BpIoGroup group)
			throws RPGException {
		Script.getInstance().stackSendIOScriptEvent(
				group,
				BpScriptConsts.SM_077_LOST,
				new Object[] { "final_destination", destination,
						"move_departure", from.getIndex(),
						"code", BpScriptConsts.CODE_LOST_AIRBORNE_DRIFT },
				"move");
	}
	/**
	 * Executes a lost result when traveling grounded.
	 * @param from the departure
	 * @param group the group being moved
	 * @throws RPGException if an error occurs
	 */
	private void lostByGround(final Hex from, final BpIoGroup group)
			throws RPGException {
		Script.getInstance().stackSendIOScriptEvent(
				group,
				BpScriptConsts.SM_077_LOST,
				new Object[] { "move_departure", from.getIndex(),
						"code", BpScriptConsts.CODE_LOST_FOOT },
				"move");
	}
	/**
	 * Executes a lost result when crossing a river.
	 * @param from the departure
	 * @param to the destination
	 * @param group the group being moved
	 * @throws RPGException if an error occurs
	 */
	private void lostCrossingRiver(final Hex from, final Hex to,
			final BpIoGroup group) throws RPGException {
		Script.getInstance().stackSendIOScriptEvent(
				group,
				BpScriptConsts.SM_077_LOST,
				new Object[] { "move_departure", from.getIndex(),
						"move_target", to.getIndex(),
						"code", BpScriptConsts.CODE_LOST_RIVER_CROSSING },
				"move");
	}
	/**
	 * Executes a move other than by raft or road.
	 * @param to the destination
	 * @param group the group being moved
	 * @throws RPGException if an error occurs
	 */
	private void move(final Hex to, final BpIoGroup group)
			throws RPGException {
		// add move event to stack
		int code = BpScriptConsts.CODE_MOVE_FOOT;
		if (airborne) {
			code = BpScriptConsts.CODE_MOVE_AIRBORNE;
		}
		System.out.println("sending script event:"+destination);
		Script.getInstance().stackSendIOScriptEvent(
				group,
				ScriptConsts.SM_21_MOVE,
				new Object[] { "final_destination", destination,
						"move_target", to.getIndex(),
						"code", code },
				"move");
	}
	/**
	 * Executes a move other than by raft or road.
	 * @param from the departure
	 * @param to the destination
	 * @param group the group being moved
	 * @throws RPGException if an error occurs
	 */
	private void moveByCrossingRiver(final Hex from, final Hex to,
			final BpIoGroup group) throws RPGException {
		// add move event to stack
		Script.getInstance().stackSendIOScriptEvent(
				group,
				ScriptConsts.SM_21_MOVE,
				new Object[] { "final_destination", destination,
						"move_departure", from.getIndex(),
						"move_target", to.getIndex(),
						"code", BpScriptConsts.CODE_MOVE_RIVER_CROSSING },
				"move");
	}
	/**
	 * Executes a move by raft.
	 * @param from the departure
	 * @param to the destination
	 * @param group the group being moved
	 * @throws RPGException if an error occurs
	 */
	private void moveByRaft(final Hex from, final Hex to, final BpIoGroup group)
			throws RPGException {
		Script.getInstance().stackSendIOScriptEvent(
				group,
				ScriptConsts.SM_21_MOVE,
				new Object[] { "final_destination", destination,
						"move_departure", from.getIndex(),
						"move_target", to.getIndex(),
						"code", BpScriptConsts.CODE_MOVE_RAFT,
						"move", 0 },
				"move");
	}
	/**
	 * Executes a move by road.
	 * @param to the destination
	 * @param group the group being moved
	 * @throws RPGException if an error occurs
	 */
	private void moveByRoad(final Hex to, final BpIoGroup group)
			throws RPGException {
		Script.getInstance().stackSendIOScriptEvent(
				group,
				ScriptConsts.SM_21_MOVE,
				new Object[] { "final_destination", destination,
						"move_target", to.getIndex(),
						"code", BpScriptConsts.CODE_MOVE_ROAD },
				"move");
	}
	/**
	 * Executes a move other than by raft or road.
	 * @param to the destination
	 * @param group the group being moved
	 * @throws RPGException if an error occurs
	 */
	private void moveInstant(final Hex to, final BpIoGroup group)
			throws RPGException {
		Script.getInstance().stackSendIOScriptEvent(
				group,
				ScriptConsts.SM_21_MOVE,
				new Object[] { "final_destination", destination,
						"move_target", to.getIndex(),
						"code", BpScriptConsts.CODE_MOVE_INSTANT },
				"move");
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reset() throws RPGException {
		airborne = false;
		destination = -1;
		drift = false;
		hexesTravelled = 0;
		localGuide = -1;
		localGuideDeserts = false;
		lost = false;
		maxTravel = 1;
		BpIoGroup group = (BpIoGroup) Interactive.getInstance().getIO(
				BpController.getInstance().getPlayerParty());
		if (group.canTravelMounted()) {
			maxTravel = MAX_MOUNTED_TRAVEL;
		}
		if (group.canTravelAirborne()) {
			maxTravel = MAX_AIRBORNE_TRAVEL;
		}
		rafting = false;
		travelEvent = false;
		group = null;
	}
	/**
	 * Sets the action's parameters.
	 * @param params the defined {@link MoveActionParameters}
	 */
	public void set(final MoveActionParameters params) {
		airborne = params.isAirborne();
		destination = params.getDestination();
		localGuide = params.getLocalGuide();
		rafting = params.isRafting();
		if (debug) {
			drift = params.isDrifting();
			localGuideDeserts = params.doesLocalGuideDesert();
			lost = params.isLost();
			travelEvent = params.hasTravelEvent();
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setResolved(final boolean val) {
	}
	/**
	 * Sets the value of the airborne flag.
	 * @param val the new value to set
	 */
	public void setAirborne(final boolean val) {
		airborne = val;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setDebug(final boolean val) {
		debug = val;
	}
	/**
	 * Sets the value of the destination.
	 * @param hex the {@link Hex} being traveled to
	 */
	public void setDestination(final Hex hex) {
		setDestination(hex.getIndex());
	}
	/**
	 * Sets the value of the destination.
	 * @param val the new value to set
	 */
	public void setDestination(final int val) {
		destination = val;
	}
	/**
	 * Sets the value of the drift.
	 * @param val the new value to set
	 */
	public void setDriftDebugging(final boolean val) {
		if (debug) {
			drift = val;
		}
	}
	/**
	 * Sets the reference id for the local guide for the last move.
	 * @param ioid the reference id
	 */
	public void setLocalGuide(final int ioid) {
		localGuide = ioid;
	}
	/**
	 * Sets the flag indicating the local guide deserts. Only for debugging.
	 * @param val the new value to set
	 */
	public void setLocalGuideDeserts(final boolean val) {
		if (debug) {
			localGuideDeserts = val;
		}
	}
	/**
	 * Sets the flag indicating the group becomes lost. Only for debugging.
	 * @param val the new value to set
	 */
	public void setLostDebugging(final boolean val) {
		if (debug) {
			lost = val;
		}
	}
	/**
	 * Sets the value of the rafting flag.
	 * @param val the new value to set
	 */
	public void setRafting(final boolean val) {
		rafting = val;
	}
	/**
	 * Sets the flag indicating a travel event happens. Only for debugging.
	 * @param val the new value to set
	 */
	public void setTravelEventDebugging(final boolean val) {
		if (debug) {
			travelEvent = val;
		}
	}
	/** Adds a move taken this turn. */
	public void takeMove() {
		hexesTravelled++;
	}
	/**
	 * Executes steps when party has a travel event.
	 * @param crossingRiver flag indicating the party is crossing a river
	 * @param onRoad flag indicating the party is on a road
	 * @param terrain the type of terrain
	 * @throws RPGException if an error occurs
	 */
	private void travelEvent(final boolean crossingRiver,
			final boolean onRoad, final HexType terrain) throws RPGException {
		final int five = 5, six = 6;
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
			} else if (airborne) {
				roll1 = Diceroller.getInstance().rolldX(five);
				eventData = WebServiceClient.getInstance().getTravelEvent(
						"Airborne", roll1, roll2);
			} else if (rafting) {
				roll1 = Diceroller.getInstance().rolldX(2);
				if (roll2 == six && roll1 == 2) {
					roll2 = five;
				}
				eventData = WebServiceClient.getInstance().getTravelEvent(
						"Rafting", roll1, roll2);
			} else if (crossingRiver) {
				eventData = WebServiceClient.getInstance().getTravelEvent(
						"River Crossing", roll1, roll2);
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
				(Integer) eventData[0],
				new Object[] { "turn_action", BpScriptConsts.ACTION_MOVE },
				(String) eventData[1]);
	}
}
