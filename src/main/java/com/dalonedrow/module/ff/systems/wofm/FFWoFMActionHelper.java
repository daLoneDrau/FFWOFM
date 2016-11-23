package com.dalonedrow.module.ff.systems.wofm;

import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.module.ff.constants.FFScriptConsts;
import com.dalonedrow.module.ff.rpg.UserAction;
import com.dalonedrow.module.ff.rpg.Direction;
import com.dalonedrow.module.ff.rpg.FFInteractiveObject;
import com.dalonedrow.module.ff.rpg.FFRoomData;
import com.dalonedrow.module.ff.systems.ConsoleInterface;
import com.dalonedrow.module.ff.wofm.ui.console.CombatScreen;
import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.systems.Script;
import com.dalonedrow.rpg.base.systems.WebServiceClient;
import com.dalonedrow.utils.ArrayUtilities;

/**
 * @author drau
 */
@SuppressWarnings("unchecked")
public final class FFWoFMActionHelper {
	/** the one and only instance of the <code>WelcomeScreen</code> class. */
	private static FFWoFMActionHelper instance;
	/**
	 * Gives access to the singleton instance of {@link FFWoFMActionHelper}.
	 * @return {@link FFWoFMActionHelper}
	 */
	public static FFWoFMActionHelper getInstance() {
		if (FFWoFMActionHelper.instance == null) {
			FFWoFMActionHelper.instance = new FFWoFMActionHelper();
		}
		return FFWoFMActionHelper.instance;
	}
	/** Hidden constructor. */
	private FFWoFMActionHelper() {
		super();
	}
	/**
	 * Gets the list of possible actions for a room.
	 * @param room the room
	 * @return {@link String}[]
	 */
	public String[] getPossibleActions(final FFRoomData room) {
		String[] list = new String[0];
		String[] exits = room.getExitDirections();
		for (int i = 0, len = exits.length; i < len; i++) {
			list = ArrayUtilities.getInstance().extendArray(exits[i], list);
		}
		// TODO - add other actions
		if (room.hasLockedDoors()) {
			// are any of the doors bashable?
			if (room.canBashDoorsInRoom()) {
				list = ArrayUtilities.getInstance().extendArray("BASH", list);
			}
		}
		return list;
	}
	/**
	 * Moves the player from one exit.
	 * @param exitIO the room IO being exited
	 * @param direction the direction being exited
	 * @throws RPGException if an error occurs
	 */
	private void moveAction(final FFInteractiveObject exitIO,
			final Direction direction) throws RPGException {
		Script.getInstance().stackSendIOScriptEvent(
				exitIO, // target
				FFScriptConsts.SM_302_EXIT_ROOM, // message
				new Object[] { "direction",
						direction.getValue() }, // params
				"exit_room"); // event name
		// the room being entered.
		FFInteractiveObject enterIO =
				FFWoFMController.getInstance().getRoomIOByCode(
						exitIO.getRoomData().getExit(direction.getValue()));
		if (enterIO != null) {
			Script.getInstance().stackSendIOScriptEvent(
					enterIO, // target
					FFScriptConsts.SM_303_ENTER_ROOM, // message
					null, // params
					"enter_room"); // event name
			enterIO = null;
		}
	}
	/**
	 * Handles an attack action.
	 * @throws PooledException if an error occurs
	 * @throws RPGException if an error occurs
	 */
	private void attackAction()
			throws PooledException, RPGException {
		CombatScreen.getInstance().attackFirstMob(false);
	}
	public void processInput(final String input) throws PooledException, RPGException {
		// handle menu options
		if (input.length() == 0) {
			ConsoleInterface.getInstance().getCurrentView().addErrorMessage(
					WebServiceClient.getInstance().getTextByName(
							"invalid_input"));
		} else {
			UserAction action = null;
			if (input.length() == 1) {
				if (input.equalsIgnoreCase("e")) {
					action = UserAction.EAS;
				} else if (input.equalsIgnoreCase("w")) {
					action = UserAction.WES;
				} else if (input.equalsIgnoreCase("n")) {
					action = UserAction.NOR;
				} else if (input.equalsIgnoreCase("s")) {
					action = UserAction.SOU;
				}
			} else if (input.length() >= 3) {
				action = UserAction.valueOf(input.substring(0, 3));
			}
			if (action == null) {
				ConsoleInterface.getInstance().getCurrentView().addErrorMessage(
						WebServiceClient.getInstance().getTextByName(
								"invalid_input"));
			} else if (GameState.getInstance().getCurrentState()
					== GameState.STATE_01_IN_PLAY) {
				FFInteractiveObject exitIO =
						(FFInteractiveObject) Interactive.getInstance().getIO(
								FFWoFMController.getInstance()
										.getCurrentRoom());
				FFRoomData roomData = exitIO.getRoomData();
				switch (action) {
				case EAS:
					moveAction(exitIO, Direction.EAST);
					break;
				case WES:
					moveAction(exitIO, Direction.WEST);
					break;
				case NOR:
					moveAction(exitIO, Direction.NORTH);
					break;
				case SOU:
					moveAction(exitIO, Direction.SOUTH);
					break;
				case BAS:
					// how many doors are there?
					if (roomData.getNumLockedDoors() == 0) {
						// no doors - display error
						ConsoleInterface.getInstance().getCurrentView()
								.addErrorMessage(WebServiceClient.getInstance()
										.getTextByName("no_doors_to_bash"));
					} else if (roomData.getNumLockedDoors() == 1) {
						if (roomData.canBashDoorsInRoom()) {
							// get the door
							FFInteractiveObject doorIO = (FFInteractiveObject)
									Interactive.getInstance().getIO(
											roomData.getLockedDoors()[0]);
							Script.getInstance().stackSendIOScriptEvent(
									doorIO, // target
									FFScriptConsts.SM_304_BASH_DOOR, // message
									new Object[] { "room_code",
											roomData.getCode() }, // params
									"bash_door"); // event name
						} else {
							// cannot bash this door - display error
							ConsoleInterface.getInstance().getCurrentView()
							.addErrorMessage(WebServiceClient.getInstance()
									.getTextByName("cant_bash_permalock"));
						}
					} else {

					}
					break;
				}
			} else if (GameState.getInstance().getCurrentState()
					== GameState.STATE_02_IN_COMBAT) {
				FFInteractiveObject io =
						CombatScreen.getInstance().getFirstMob();
				switch (action) {
				case ATT:
					attackAction();
					break;
				default:
					break;
				}
			}
		}
	}
}
