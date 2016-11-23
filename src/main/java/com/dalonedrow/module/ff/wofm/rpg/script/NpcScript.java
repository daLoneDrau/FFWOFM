package com.dalonedrow.module.ff.wofm.rpg.script;

import com.dalonedrow.module.ff.rpg.FFInteractiveObject;
import com.dalonedrow.module.ff.rpg.FFNpc;
import com.dalonedrow.module.ff.rpg.FFScriptable;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.flyweights.ScriptConstants;

/**
 * 
 * @author drau
 *
 */
public final class NpcScript extends FFScriptable {
	/**
	 * Creates a new instance of {@link NpcScript}.
	 * @param io the IO associated with this script
	 */
	public NpcScript(final FFInteractiveObject io) {
		super(io);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int onInit() throws RPGException {
		FFInteractiveObject npcIO = super.getIO();
		FFNpc npc = npcIO.getNPCData();
		System.out.println("onInit " + new String(npc.getName()));
		if (super.hasLocalVariable("group")) {
			String[] var = super.getLocalStringArrayVariableValue("group");
			for (int i = var.length - 1; i >= 0; i--) {
				super.getIO().addGroup(var[i]);
			}
		}
		// initialize ability scores
		npc.setBaseAttributeScore(
				"ST", super.getLocalIntVariableValue("INIT_ST"));
		npc.setBaseAttributeScore(
				"MST", super.getLocalIntVariableValue("INIT_ST"));
		npc.setBaseAttributeScore(
				"SK", super.getLocalIntVariableValue("INIT_SK"));
		npc.setBaseAttributeScore(
				"MSK", super.getLocalIntVariableValue("INIT_SK"));

		npc = null;
		npcIO = null;
		return super.onInit();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int onMovement() throws RPGException {
		// TODO do something
		return ScriptConstants.ACCEPT;
	}
}
