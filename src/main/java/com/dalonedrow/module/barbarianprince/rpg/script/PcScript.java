package com.dalonedrow.module.barbarianprince.rpg.script;

import com.dalonedrow.engine.systems.base.Diceroller;
import com.dalonedrow.module.barbarianprince.rpg.BpCharacter;
import com.dalonedrow.module.barbarianprince.rpg.BpScriptable;
import com.dalonedrow.module.barbarianprince.systems.WebServiceClient;
import com.dalonedrow.rpg.base.constants.Dice;
import com.dalonedrow.rpg.base.flyweights.RPGException;

/**
 * @author drau
 */
public final class PcScript extends BpScriptable {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int onInit() throws RPGException {
		final int five = 5;
		BpCharacter pc = super.getIO().getPCData();
		pc.setBaseAttributeScore("CS",
				super.getLocalIntVariableValue("combat_skill"));
		pc.setBaseAttributeScore("EN",
				super.getLocalIntVariableValue("endurance"));
		pc.setBaseAttributeScore("WO", 0);
		pc.setBaseAttributeScore("PW", 0);
		pc.setName(super.getLocalStringVariableValue("name"));
		pc.setBaseAttributeScore("WE",
				super.getLocalIntVariableValue("wealth"));
		pc.setBaseAttributeScore("WI",
				Diceroller.getInstance().rolldXPlusY(five, 1));
		// set wealth
		String[] treasure = WebServiceClient.getInstance().getTreasure(
				Integer.toString((int) pc.getFullAttributeScore("WE")),
				Diceroller.getInstance().rolldX(Dice.D6));
		pc.adjustGold(Integer.parseInt(treasure[0]));
		return super.onInit();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int onInitEnd() throws RPGException {
		// TODO Auto-generated method stub
		return super.onInitEnd();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int onRest() throws RPGException {
		BpCharacter pc = super.getIO().getPCData();
		if (pc.getFullAttributeScore("WO") > 0) {
			pc.adjustAttributeModifier("WO", -1);
			// TODO add message that resting happened
		}
		pc = null;
		return super.onRest();
	}
}
