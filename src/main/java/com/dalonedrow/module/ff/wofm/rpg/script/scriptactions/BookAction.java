package com.dalonedrow.module.ff.wofm.rpg.script.scriptactions;

import com.dalonedrow.module.ff.rpg.FFScriptable;
import com.dalonedrow.rpg.base.constants.Behaviour;
import com.dalonedrow.rpg.base.constants.IoGlobals;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.flyweights.ScriptAction;

/**
 * @author drau
 */
public final class BookAction extends FFScriptAction {
	private String			action;
	private char mode;
	/**
	 * Creates a new instance of {@link BookAction}.
	 * @param dest the exit destination
	 * @param dir the exit direction
	 * @param room the room where the exit is being created
	 */
	public BookAction() {
	}
	@Override
	public void execute() throws RPGException {
		if (action.equalsIgnoreCase("SET")) {
			switch (mode) {
			case 'A':
				// TODO - set book mode to display Magic
				break;
			case 'E':
				// TODO - set book mode to display Equipment
				break;
			case 'M':
				// TODO - set book mode to display Map
				break;
			}
		} else if (action.equalsIgnoreCase("OPEN")) {
			// ARX_INTERFACE_BookOpenClose(1);
		} else if (action.equalsIgnoreCase("CLOSE")) {
			// ARX_INTERFACE_BookOpenClose(2);
		}
	}
}
