package com.dalonedrow.module.ff.wofm.rpg.script;

import com.dalonedrow.engine.systems.base.Diceroller;
import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.engine.systems.base.JOGLErrorHandler;
import com.dalonedrow.module.ff.constants.FFScriptConsts;
import com.dalonedrow.module.ff.rpg.Direction;
import com.dalonedrow.module.ff.rpg.FFInteractiveObject;
import com.dalonedrow.module.ff.rpg.FFRoomData;
import com.dalonedrow.module.ff.rpg.FFScriptable;
import com.dalonedrow.module.ff.systems.ConsoleInterface;
import com.dalonedrow.module.ff.systems.wofm.FFWoFMController;
import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.pooled.PooledStringBuilder;
import com.dalonedrow.pooled.StringBuilderPool;
import com.dalonedrow.rpg.base.constants.Dice;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.flyweights.ScriptAction;
import com.dalonedrow.rpg.base.systems.Script;
import com.dalonedrow.rpg.base.systems.WebServiceClient;

/**
 * 
 * @author drau
 *
 */
@SuppressWarnings("unchecked")
public class DoorScript extends FFScriptable {
	/**
	 * Creates a new instance of {@link DoorScript}.
	 * @param io the IO associated with this script
	 */
	public DoorScript(final FFInteractiveObject io) {
		super(io);
	}
	/* (non-Javadoc)
	 * @see com.dalonedrow.module.ff.rpg.FFScriptable#onBashDoor()
	 */
	@Override
	public int onBashDoor() {
		if (super.hasLocalVariable("attribute_test")) {
			try {
				String attr =
						super.getLocalStringVariableValue("attribute_test");
				int numDice =
						super.getLocalIntVariableValue("num_dice_roll");
				FFInteractiveObject playerIO = (FFInteractiveObject)
						Interactive.getInstance().getIO(
								FFWoFMController.getInstance().getPlayer());
				playerIO.getPCData().doComputeFullStats();
				int score = (int) playerIO.getPCData().getFullAttributeScore(
						attr);
				int roll = Diceroller.getInstance().rollXdY(numDice, Dice.D6);
				ScriptAction[] actions = null;
				//if (roll <= score) {
				if (true) {
					actions = super.getEventActions(
							FFScriptConsts.SM_305_BASH_DOOR_SUCCESS);
				} else {
					actions = super.getEventActions(
							FFScriptConsts.SM_306_BASH_DOOR_FAILURE);
				}
				for (int i = 0, len = actions.length; i < len; i++) {
					actions[i].execute();
				}
				attr = null;
				playerIO = null;
				actions = null;
			} catch (RPGException e) {
				JOGLErrorHandler.getInstance().fatalError(e);
			}
		}
		return super.onBashDoor();
	}
}
