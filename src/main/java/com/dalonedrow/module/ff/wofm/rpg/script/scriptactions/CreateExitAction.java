package com.dalonedrow.module.ff.wofm.rpg.script.scriptactions;

import com.dalonedrow.module.ff.rpg.Direction;
import com.dalonedrow.module.ff.systems.wofm.FFWoFMController;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.flyweights.ScriptAction;

/**
 * @author drau
 */
public final class CreateExitAction extends FFScriptAction {
	/** the exit destination. */
	private final int		destination;
	/** the exit direction. */
	private final Direction	direction;
	/** the room where the exit is being created. */
	private final int		roomCode;
	/**
	 * Creates a new instance of {@link CreateExitAction}.
	 * @param dest the exit destination
	 * @param dir the exit direction
	 * @param room the room where the exit is being created
	 */
	public CreateExitAction(final int dest, final Direction dir,
			final int room) {
		destination = dest;
		direction = dir;
		roomCode = room;
	}
	@Override
	public void execute() throws RPGException {
		FFWoFMController.getInstance().getRoomIOByCode(
				roomCode).getRoomData().addExit(
						direction.getValue(), destination);
	}
}
