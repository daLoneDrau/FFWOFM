package com.dalonedrow.module.barbarianprince.rpg.script;

import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.module.barbarianprince.constants.BpIo;
import com.dalonedrow.module.barbarianprince.constants.BpScriptConsts;
import com.dalonedrow.module.barbarianprince.graph.Hex;
import com.dalonedrow.module.barbarianprince.graph.HexFeature;
import com.dalonedrow.module.barbarianprince.graph.HexMap;
import com.dalonedrow.module.barbarianprince.rpg.BpCharacter;
import com.dalonedrow.module.barbarianprince.rpg.BpInteractiveObject;
import com.dalonedrow.module.barbarianprince.rpg.BpNpc;
import com.dalonedrow.module.barbarianprince.rpg.BpScriptable;
import com.dalonedrow.module.barbarianprince.systems.BpController;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.flyweights.ScriptConstants;
import com.dalonedrow.rpg.base.systems.Script;

@SuppressWarnings("unchecked")
public final class NpcScript extends BpScriptable {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int onRest() throws RPGException {
		BpNpc npc = super.getIO().getNPCData();
		if (npc.getFullAttributeScore("WO") > 0) {
			npc.adjustAttributeModifier("WO", -1);
			// TODO add message that resting happened
		}
		npc = null;
		return super.onRest();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int onInit() throws RPGException {
		BpNpc npc = super.getIO().getNPCData();
		npc.setBaseAttributeScore("CS",
				super.getLocalIntVariableValue("combat_skill"));
		npc.setBaseAttributeScore("EN",
				super.getLocalIntVariableValue("endurance"));
		npc.setBaseAttributeScore("WO", 0);
		npc.setBaseAttributeScore("PW", 0);
		npc.setName(super.getLocalStringVariableValue("name"));
		npc.setBaseAttributeScore("WE",
				super.getLocalIntVariableValue("wealth"));
		if (super.hasLocalVariable("group")) {
			String[] var = super.getLocalStringArrayVariableValue("group");
			for (int i = var.length - 1; i >= 0; i--) {
				super.getIO().addGroup(var[i]);
			}
		}
		return super.onInit();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int onMovement() throws RPGException {
		BpInteractiveObject io = super.getIO();
		if (io.hasBehaviorFlag(BpIo.BEHAVIOR_FUGITIVE)
				|| io.hasBehaviorFlag(BpIo.BEHAVIOR_ESCAPEE)) {
			int loc = super.getLocalIntVariableValue("move_target");
			Hex hex = HexMap.getInstance().getHexById(loc);
			if (hex.hasFeature(HexFeature.CASTLE)
					|| hex.hasFeature(HexFeature.TEMPLE)
					|| hex.hasFeature(HexFeature.TOWN)) {
				// fugitive deserts
				Script.getInstance().sendIOScriptEvent(
						(BpInteractiveObject) Interactive.getInstance().getIO(
								BpController.getInstance().getPlayerParty()),
						BpScriptConsts.SM_076_DESERT,
						new Object[] { "deserter", io.getRefId() },
						"deserter");
			}
		}
		return ScriptConstants.ACCEPT;
	}
}
