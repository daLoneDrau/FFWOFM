package com.dalonedrow.module.barbarianprince.systems;

import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.engine.systems.base.JOGLErrorHandler;
import com.dalonedrow.engine.systems.base.ProjectConstants;
import com.dalonedrow.engine.systems.base.Time;
import com.dalonedrow.module.barbarianprince.constants.BpScriptConsts;
import com.dalonedrow.module.barbarianprince.graph.HexMap;
import com.dalonedrow.module.barbarianprince.rpg.BpInteractiveObject;
import com.dalonedrow.module.barbarianprince.rpg.BpIoGroup;
import com.dalonedrow.module.barbarianprince.rpg.action.TurnAction;
import com.dalonedrow.module.barbarianprince.rpg.event.EventContainer;
import com.dalonedrow.module.barbarianprince.turn.TimePhase;
import com.dalonedrow.module.barbarianprince.turn.TimeTrack;
import com.dalonedrow.module.barbarianprince.turn.Turn;
import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.rpg.base.constants.IoGlobals;
import com.dalonedrow.rpg.base.flyweights.ErrorMessage;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.systems.Script;
import com.dalonedrow.utils.ArrayUtilities;

/**
 * The main class for running the game.
 * @author drau
 */
@SuppressWarnings("unchecked")
public final class BpController extends ProjectConstants<BpInteractiveObject> {
	/** the singleton instance. */
	private static BpController instance;
	/**
	 * Gets the one and only instance of {@link BpController}.
	 * @return {@link BpController}
	 * @throws RPGException if an error occurs
	 */
	public static BpController getInstance() throws RPGException {
		if (BpController.instance == null) {
			BpController.instance = new BpController();
		}
		return BpController.instance;
	}
    public void goToState(final int state) {
        GameState.getInstance().setCurrentState(state);
    }
	/** the current turn. */
	private Turn		currentTurn;
	/** the encounter group's reference id. */
	private int			encounterGroup;
	private boolean		gameOver;
	/** the next turn. */
	private Turn		nextTurn;
	/** the player's party. */
	private int			party	= -1;
	private TurnAction	turnAction;
	/**
	 * Hidden constructor.
	 * @throws RPGException if an error occurs
	 */
	private BpController() throws RPGException {
		super.setInstance(this);
		new BpInteractive();
		HexMap.getInstance();
		new BpScript();
		Time.getInstance().init();
		encounterGroup = -1;
	}
	/**
	 * Adds an event to the current phase.
	 * @param event the event
	 */
	public void addEventToTurn(final EventContainer event) {
		currentTurn.addEvent(event);
	}
	/**
	 * Adds an event to a specific turn phase. If the turn phase is a phase that
	 * occurs before the current turn phase, the event is added to the next day.
	 * @param event the event
	 * @param tp the turn phase
	 */
	public void addEventToTurn(final EventContainer event, final TimePhase tp) {
		if (tp.ordinal() > currentTurn.getCurrentPhase().ordinal()) {
			currentTurn.addEvent(event, tp);
		} else {
			nextTurn.addEvent(event, tp);
		}
	}
	/**
	 * Adds an event to the beginning of a specific turn phase. If the turn
	 * phase is a phase that occurs before the current turn phase, the event is
	 * added to the next day.
	 * @param event the event
	 * @param tp the turn phase
	 */
	public void addEventToTurnBeginning(final EventContainer event,
			final TimePhase tp) {
		if (tp.ordinal() >= currentTurn.getCurrentPhase().ordinal()) {
			currentTurn.addEventToBeginning(event, tp);
		} else {
			nextTurn.addEventToBeginning(event, tp);
		}
	}
	/**
	 * Adds an IO to the party.
	 * @param ioid the IO's reference id
	 * @throws RPGException if an error occurs
	 */
	public void addIOToParty(final int ioid) throws RPGException {
		BpIoGroup group = (BpIoGroup) Interactive.getInstance().getIO(party);
		group.addMember(ioid);
		group = null;
	}
	/** Clears the encounter group's reference id. */
	public void clearEncounterGroup() {
		encounterGroup = -1;
	}
	/**
	 * Completes the turn phase.
	 * @throws RPGException
	 */
	public void completeTurnPhase() throws RPGException {
		currentTurn.nextPhase();
		if (currentTurn.getCurrentPhase() == TimePhase.PHASE_00_PRE_ACTION) {
			Turn d = currentTurn;
			currentTurn = nextTurn;
			nextTurn = d;
			d = null;
			TimeTrack.getInstance().nextDay();
		}
	}
	/**
	 * Executes the selected turn action.
	 * @throws RPGException if an error occurs
	 */
	public void executeTurnAction() throws RPGException {
		if (turnAction == null) {
			throw new RPGException(
					ErrorMessage.INVALID_OPERATION, "No action was set");
		}
		turnAction.execute();
	}
	public Turn getCurrentTurn() {
		return currentTurn;
	}
	/**
	 * Gets the value for the encounterGroup.
	 * @return {@link int}
	 */
	public int getEncounterGroup() {
		return encounterGroup;
	}
	/**
	 * Gets all local guides in the party for a specific hex.
	 * @param hexId the hex id
	 * @return {@link int}[]
	 * @throws RPGException if an error occurs
	 */
	public int[] getLocalGuides(final int hexId) throws RPGException {
		int[] guides = new int[0];
		BpIoGroup group = (BpIoGroup) Interactive.getInstance().getIO(party);
		for (int i = group.size() - 1; i >= 0; i--) {
			BpInteractiveObject io =
					(BpInteractiveObject) Interactive.getInstance()
							.getIO(group.getMember(i));
			if (io.hasIOFlag(IoGlobals.IO_14_PARTY)) {
				for (int j = ((BpIoGroup) io).size() - 1; j >= 0; j--) {
					BpInteractiveObject ioj =
							(BpInteractiveObject) Interactive.getInstance()
									.getIO(
											((BpIoGroup) io).getMember(j));
					if (ioj.hasIOFlag(IoGlobals.IO_03_NPC)
							&& ioj.getNPCData().isLocalGuide(hexId)) {
						guides = ArrayUtilities.getInstance().extendArray(
								ioj.getRefId(), guides);
					}
					ioj = null;
				}
			} else if (io.hasIOFlag(IoGlobals.IO_03_NPC)
					&& io.getNPCData().isLocalGuide(hexId)) {
				guides = ArrayUtilities.getInstance().extendArray(
						io.getRefId(), guides);
			}
			io = null;
		}
		group = null;
		return guides;
	}
	/**
	 * Gets the party's current location.
	 * @return {@link int}
	 * @throws RPGException if an error occurs
	 */
	public int getPartyLocation() throws RPGException {
		BpIoGroup group = (BpIoGroup) Interactive.getInstance().getIO(party);
		int loc = group.getLocation();
		group = null;
		return loc;
	}
	/**
	 * Gets the reference id of the player's party.
	 * @return {@link int}
	 */
	public int getPlayerParty() {
		return party;
	}
	/**
	 * Gets the value for the turnAction.
	 * @return {@link TurnAction}
	 */
	public TurnAction getTurnAction() {
		return turnAction;
	}
	/**
	 * Gets the gameOver.
	 * @return <code>boolean</code>
	 */
	public boolean isGameOver() {
		return gameOver;
	}
	/**
	 * Determines if the turn action was resolved.
	 * @return <tt>true</tt> if the turn action was resolved; <tt>false</tt>
	 *         otherwise
	 */
	public boolean isTurnActionResolved() {
		boolean resolved = false;
		if (turnAction != null) {
			resolved = turnAction.isResolved();
		}
		return resolved;
	}
	/**
	 * Removes an IO from the party.
	 * @param ioid the IO's reference id
	 * @throws RPGException if an error occurs
	 */
	public void removeIOFromParty(final int ioid) throws RPGException {
		BpIoGroup group = (BpIoGroup) Interactive.getInstance().getIO(party);
		group.removeMember(ioid);
		// TODO remove all gold, mount, etc...
		group = null;
	}
	/**
	 * Sets the encounter group's reference id.
	 * @param ioid the new ioid to set
	 */
	public void setEncounterGroup(final int ioid) {
		encounterGroup = ioid;
	}
	/**
	 * Sets the value for the gameOver.
	 * @param val the value to set
	 */
	public void setGameOver(final boolean val) {
		gameOver = val;
	}
	/**
	 * Sets the value of the turn's action.
	 * @param action the new action to set
	 */
	public void setTurnAction(final TurnAction action) {
		turnAction = action;
	}
	public void startGame() throws RPGException {
		BpIoGroup group =
				(BpIoGroup) ((BpInteractive) Interactive.getInstance())
						.newPlayerParty();
		((BpInteractive) Interactive.getInstance()).newPlayer();
		group.addMember(
				((BpInteractive) Interactive.getInstance()).getPlayerIO());
		party = group.getRefId();
		group = null;
		currentTurn = new Turn();
		nextTurn = new Turn();
		TimeTrack.getInstance().reset();
		Script.getInstance().eventStackClear();
		Script.getInstance().stackSendIOScriptEvent(
				((BpScript) Script.getInstance()).getMasterScript(),
				BpScriptConsts.SM_301_E001,
				null,
				"startgame");
	}
	@Override
	public void update() {
		if (isTurnActionResolved()) {
			// TODO did control go to an event screen?
			
			try {
				System.out.println("advancing timetrack");
				turnAction = null;
				TimeTrack.getInstance().nextPhase();
			} catch (RPGException e) {
				e.printStackTrace();
				JOGLErrorHandler.getInstance().fatalError(e);
			}
		}
	}
}
