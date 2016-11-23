package com.dalonedrow.module.citadelofchaos.systems;

import com.dalonedrow.engine.systems.base.Diceroller;
import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.module.citadelofchaos.rpg.CitadelInteractiveObject;
import com.dalonedrow.module.citadelofchaos.rpg.CitadelNPC;
import com.dalonedrow.module.citadelofchaos.rpg.CitadelPC;
import com.dalonedrow.rpg.base.constants.Dice;
import com.dalonedrow.rpg.base.flyweights.ErrorMessage;
import com.dalonedrow.rpg.base.flyweights.RPGException;

public class BattleWizard {
	/** the npc combatant's reference id. */
	private int	npcRefId;
	/** the pc combatant's reference id. */
	private int	pcRefId;
	public void sequence() {
		CitadelPC pc =
				((CitadelInteractiveObject) Interactive.getInstance()
						.getIO(pcRefId)).getPCData();
		CitadelNPC npc =
				((CitadelInteractiveObject) Interactive.getInstance()
						.getIO(npcRefId)).getNPCData();
		int creatureAttackStrength = (int) npc.getBaseAttributeScore("SK");
		creatureAttackStrength += Diceroller.getInstance().rollXdY(2, Dice.D6);

		int pcAttackStrength = (int) npc.getBaseAttributeScore("SK");
		pcAttackStrength += Diceroller.getInstance().rollXdY(2, Dice.D6);

		if (creatureAttackStrength > pcAttackStrength) {

		} else if (pcAttackStrength > creatureAttackStrength) {

		} else {

		}
	}
	/**
	 * Sets the reference id for the npc combatant.
	 * @param ioid the new value to set
	 * @throws RPGException if the reference id is invalid
	 */
	public void setNpcCombatant(final int ioid) throws RPGException {
		if (!Interactive.getInstance().hasIO(ioid)) {
			throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT,
					"Invalid IO ref ID " + ioid);
		}
		npcRefId = ioid;
	}
	/**
	 * Sets the reference id for the pc combatant.
	 * @param ioid the new value to set
	 * @throws RPGException if the reference id is invalid
	 */
	public void setPcCombatant(final int ioid) throws RPGException {
		if (!Interactive.getInstance().hasIO(ioid)) {
			throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT,
					"Invalid IO ref ID " + ioid);
		}
		pcRefId = ioid;
	}
}
