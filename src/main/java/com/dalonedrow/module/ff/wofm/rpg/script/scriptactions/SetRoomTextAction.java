package com.dalonedrow.module.ff.wofm.rpg.script.scriptactions;

import com.dalonedrow.module.ff.systems.wofm.FFWoFMController;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.flyweights.ScriptAction;

/**
 * @author drau
 */
public final class SetRoomTextAction extends FFScriptAction {
	/** the room where the text is being changed. */
	private final int		roomCode;
	/** the text id. */
	private final String	textId;
	/**
	 * Creates a new instance of {@link SetRoomTextAction}.
	 * @param code the room where the text is being changed
	 * @param text the text id
	 */
	public SetRoomTextAction(final int code, final String text) {
		roomCode = code;
		textId = text;
	}
	@Override
	public void execute() throws RPGException {
		FFWoFMController.getInstance().getRoomIOByCode(
				roomCode).getRoomData().setTextid(textId);
	}
}
