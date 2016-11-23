package com.dalonedrow.module.ff.wofm.rpg.script;

import com.dalonedrow.engine.systems.base.Diceroller;
import com.dalonedrow.engine.systems.base.JOGLErrorHandler;
import com.dalonedrow.module.ff.rpg.FFCharacter;
import com.dalonedrow.module.ff.rpg.FFInteractiveObject;
import com.dalonedrow.module.ff.rpg.FFScriptable;
import com.dalonedrow.module.ff.systems.ConsoleInterface;
import com.dalonedrow.module.ff.systems.wofm.FFWebServiceClient;
import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.pooled.PooledStringBuilder;
import com.dalonedrow.pooled.StringBuilderPool;
import com.dalonedrow.rpg.base.consoleui.TextProcessor;
import com.dalonedrow.rpg.base.constants.Dice;
import com.dalonedrow.rpg.base.flyweights.ErrorMessage;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.systems.WebServiceClient;

/**
 * @author drau
 */
public final class PcScript extends FFScriptable {
	/**
	 * Creates a new instance of {@link PcScript}.
	 * @param io the IO associated with this script
	 */
	public PcScript(final FFInteractiveObject io) {
		super(io);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int onInit() throws RPGException {
		FFCharacter pc = super.getIO().getPCData();
		// skill 1d6 + 6
		pc.setBaseAttributeScore("SK", 
				Diceroller.getInstance().rolldXPlusY(Dice.D6, Dice.D6));
		pc.setBaseAttributeScore("MSK", 
				pc.getBaseAttributeScore("SK"));
		// stamina 2d6 + 12
		pc.setBaseAttributeScore("ST", 
				Diceroller.getInstance().rollXdY(2, Dice.D6) + 2 * Dice.D6);
		pc.setBaseAttributeScore("MST", 
				pc.getBaseAttributeScore("ST"));
		// luck 1d6 + 6
		pc.setBaseAttributeScore("LK", 
				Diceroller.getInstance().rolldXPlusY(Dice.D6, Dice.D6));
		pc.setBaseAttributeScore("MLK", 
				pc.getBaseAttributeScore("LK"));
		// equip iron sword
		FFInteractiveObject swordIO = ((FFWebServiceClient)
				WebServiceClient.getInstance()).getItemByName("Iron sword");
		try {
			swordIO.getItemData().ARX_EQUIPMENT_Equip(super.getIO());
		} catch (PooledException e) {
			throw new RPGException(ErrorMessage.INTERNAL_ERROR, e);
		}
		return super.onInit();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int onHit() {
		try {
			FFCharacter pc = super.getIO().getPCData();
			String attr = super.getLocalStringVariableValue("hit_attribute");
			int dmg = super.getLocalIntVariableValue("hit_amount");
			float val = pc.getBaseAttributeScore(attr);
			val -= dmg;
			// adjust attribute score
			pc.setBaseAttributeScore(attr, val);
			// send message
			PooledStringBuilder sb =
					StringBuilderPool.getInstance().getStringBuilder();
			sb.append(dmg);
			sb.append(" point");
			if (dmg > 1) {
				sb.append('s');
			}
			ConsoleInterface.getInstance().getCurrentView().addErrorMessage(
					TextProcessor.getInstance().processText(
							null, // player io
							null, // target io
							new String[] { Integer.toString(dmg),
									pc.getAttributeName(attr), sb.toString() },
							WebServiceClient.getInstance().getTextByName(
									"player_hit")));
			sb.returnToPool();
			sb = null;
			pc = null;
			attr = null;
		} catch (RPGException | PooledException e) {
			JOGLErrorHandler.getInstance().fatalError(e);
		}
		return super.onHit();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int onInitEnd() throws RPGException {
		// TODO Auto-generated method stub
		return super.onInitEnd();
	}
}
