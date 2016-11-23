package com.dalonedrow.module.ff.wofm.rpg.script.scriptactions;

import com.dalonedrow.module.ff.rpg.FFInteractiveObject;
import com.dalonedrow.rpg.base.constants.Behaviour;
import com.dalonedrow.rpg.base.constants.IoGlobals;
import com.dalonedrow.rpg.base.flyweights.RPGException;

/**
 * @author drau
 */
public final class NPCBehaviorAction extends FFScriptAction {
	/** the action taken - set, stack, unstack, etc... */
	private String			action;
	private Behaviour		affectedBehavior;
	private int				parameter;
	private boolean			resetTactics;
	/**
	 * Creates a new instance of {@link NPCBehaviorAction}.
	 * @param dest the exit destination
	 * @param dir the exit direction
	 * @param room the room where the exit is being created
	 */
	public NPCBehaviorAction() {
	}
	@Override
	public void execute() throws RPGException {
		if (affectedBehavior == null) {
			affectedBehavior = Behaviour.BEHAVIOUR_NONE;
		}
		FFInteractiveObject io = super.getMaster().getIO();
		if (io != null
				&& io.hasIOFlag(IoGlobals.IO_03_NPC)) {
			if (action.equalsIgnoreCase("STACK")) {
				io.getNPCData().ARX_NPC_Behaviour_Stack();
			} else if (action.equalsIgnoreCase("UNSTACK")) {
				io.getNPCData().ARX_NPC_Behaviour_UnStack();
			} else if (action.equalsIgnoreCase("UNSTACKALL")) {
				io.getNPCData().resetBehavior();
			} else {
				if (action.equalsIgnoreCase("SET")) {
					if (resetTactics) {
						io.getNPCData().setTactics(0);
					}
				}
				if (action.equalsIgnoreCase("GO_HOME")) {
					// TODO - figure out something
				} else if (action.equalsIgnoreCase("FRIENDLY")) {
					io.getNPCData().setMovemode(IoGlobals.NOMOVEMODE);
				} else if (action.equalsIgnoreCase("MOVE_TO")
						|| action.equalsIgnoreCase("LOOK_FOR")
						|| action.equalsIgnoreCase("HIDE")
						|| action.equalsIgnoreCase("WANDER_AROUND")) {
					io.getNPCData().setMovemode(IoGlobals.WALKMODE);
				} else if (action.equalsIgnoreCase("FLEE")) {
					io.getNPCData().setMovemode(IoGlobals.RUNMODE);
				} else if (action.equalsIgnoreCase("GUARD")) {
					io.setTargetinfo(-2);
					io.getNPCData().setMovemode(IoGlobals.NOMOVEMODE);
				}
				io.getNPCData().ARX_NPC_Behaviour_Change(
						affectedBehavior.getFlag(), parameter);
			}
		}
		io = null;
	}
}
