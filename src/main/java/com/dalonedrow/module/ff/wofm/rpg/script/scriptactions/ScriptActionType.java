package com.dalonedrow.module.ff.wofm.rpg.script.scriptactions;

/**
 * 
 * @author drau
 *
 */
public enum ScriptActionType {
	/** creates an exit in a room. */
	CREATE_EXIT,
	/** action to damage the player. */
	DMG_PLAYER,
	/** action to go to combat. */
	GO_TO_COMBAT,
	/** moves the player to a new room. */
	MOVE_PLAYER,
	/** permanently locks a door. */
	PERMALOCK,
	/** sets text on a room. */
	SET_ROOM_TEXT,
	/** displays text. */
	SHOW_TEXT,
	/** displays text. */
	TEST_YOUR_LUCK;
}
