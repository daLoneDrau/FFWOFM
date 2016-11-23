package com.dalonedrow.module.ff.wofm.rpg.script.scriptactions;

import com.dalonedrow.module.ff.constants.FFScriptConsts;
import com.dalonedrow.module.ff.rpg.FFInteractiveObject;
import com.dalonedrow.module.ff.systems.wofm.FFWoFMController;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.flyweights.ScriptAction;
import com.dalonedrow.rpg.base.systems.Script;

/**
 * @author drau
 */
@SuppressWarnings("unchecked")
public final class MovePlayerAction extends FFScriptAction {
	/** the code for the room the player is being moved to. */
	private final int roomCode;
	/**
	 * Creates a new instance of {@link MovePlayerAction}.
	 * @param code the code for the room the player is being moved to
	 */
	public MovePlayerAction(final int code) {
		roomCode = code;
	}
	@Override
	public void execute() throws RPGException {
		// MOVE PLAYER INTO NEXT ROOM
		FFWoFMController.getInstance().setCurrentRoom(roomCode);
		// GENERATE ONENTER EVENT FOR NEW ROOM
		FFInteractiveObject enterIO =
				FFWoFMController.getInstance().getRoomIOByCode(
						roomCode);
		Script.getInstance().stackSendIOScriptEvent(
				enterIO, // target
				FFScriptConsts.SM_303_ENTER_ROOM, // message
				null, // params
				"enter_room"); // event name
		enterIO = null;
	}
}
