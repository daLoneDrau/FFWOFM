package com.dalonedrow.module.ff.wofm.rpg.script;

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
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.flyweights.ScriptAction;
import com.dalonedrow.rpg.base.systems.WebServiceClient;

/**
 * 
 * @author drau
 *
 */
public class RoomScript extends FFScriptable {
	/**
	 * Creates a new instance of {@link RoomScript}.
	 * @param io the IO associated with this script
	 */
	public RoomScript(final FFInteractiveObject io) {
		super(io);
	}
	/* (non-Javadoc)
	 * @see com.dalonedrow.module.ff.rpg.FFScriptable#onEnterRoom()
	 */
	@Override
	public int onEnterRoom() {
		try {
			System.out.println(super.getIO().getRoomData().getCode()
					+ " onEnterRoom");
			ScriptAction[] actions = super.getEventActions(
					FFScriptConsts.SM_303_ENTER_ROOM);
			for (int i = 0, len = actions.length; i < len; i++) {
				actions[i].execute();
			}
		} catch (RPGException e) {
			JOGLErrorHandler.getInstance().fatalError(e);
		}
		return super.onEnterRoom();
	}

	@Override
	public int onExitRoom() {
		try {
			FFInteractiveObject io = super.getIO();
			FFRoomData room = io.getRoomData();
			int dir = super.getLocalIntVariableValue("direction");
			PooledStringBuilder sb =
					StringBuilderPool.getInstance().getStringBuilder();
			if (room.hasExit(dir)) {
				FFWoFMController.getInstance().setCurrentRoom(
						room.getExit(dir));
				sb.append(room.getCode());
				sb.append("_SECONDARY");
				String text = 
						WebServiceClient.getInstance().getTextByName(
								sb.toString());
				if (text != null) {
					room.setTextid(sb.toString());
				}
				sb.setLength(0);
			}
			sb.append(room.getCode());
			sb.append('_');
			sb.append(Direction.valueOf(dir));
			String text = 
					WebServiceClient.getInstance().getTextByName(sb.toString());
			sb.returnToPool();
			sb = null;
			if (text == null) {
				ConsoleInterface.getInstance().getCurrentView().addErrorMessage(
						WebServiceClient.getInstance().getTextByName(
								"invalid_exit"));
			} else {
				if (room.hasExit(dir)) {
					ConsoleInterface.getInstance().getCurrentView()
					.addMessage(text);
					ScriptAction[] actions = super.getEventActions(
							FFScriptConsts.SM_302_EXIT_ROOM);
					for (int i = 0, len = actions.length; i < len; i++) {
						actions[i].execute();
					}
				} else {
					ConsoleInterface.getInstance().getCurrentView()
					.addErrorMessage(text);
				}
			}
		} catch (RPGException | PooledException e) {
			JOGLErrorHandler.getInstance().fatalError(e);
		}
		return super.onExitRoom();
	}
}
