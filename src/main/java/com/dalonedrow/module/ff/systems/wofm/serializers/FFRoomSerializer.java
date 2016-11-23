package com.dalonedrow.module.ff.systems.wofm.serializers;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializer;
import com.badlogic.gdx.utils.JsonValue;
import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.engine.systems.base.JOGLErrorHandler;
import com.dalonedrow.module.ff.constants.FFScriptConsts;
import com.dalonedrow.module.ff.rpg.Direction;
import com.dalonedrow.module.ff.rpg.Event;
import com.dalonedrow.module.ff.rpg.FFDoorData;
import com.dalonedrow.module.ff.rpg.FFInteractiveObject;
import com.dalonedrow.module.ff.rpg.FFRoomData;
import com.dalonedrow.module.ff.systems.wofm.FFInteractive;
import com.dalonedrow.module.ff.systems.wofm.FFWebServiceClient;
import com.dalonedrow.rpg.base.flyweights.ErrorMessage;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.flyweights.ScriptAction;
import com.dalonedrow.rpg.base.systems.WebServiceClient;

/**
 * @author drau
 */
@SuppressWarnings("rawtypes")
public final class FFRoomSerializer implements Serializer<FFInteractiveObject> {
	/**
	 * Creates a new door based on the json data supplied.
	 * @param jsonData the json data
	 * @return {@link FFInteractiveObject}
	 * @throws RPGException if an error occurs
	 */
	private FFInteractiveObject createDoor(final JsonValue jsonData)
			throws RPGException {
		FFDoorData door = new FFDoorData();
		door.setName(jsonData.get("name").asString());
		door.setTitle(jsonData.get("title").asString());
		door.setLocked(jsonData.get("locked").asBoolean());
		FFInteractiveObject newDoor =
				((FFInteractive) Interactive.getInstance()).newDoor(door);
		newDoor.getScript().setLocalVariable("perma_locked", 0);
		if (jsonData.has("attribute_test")) {
			newDoor.getScript().setLocalVariable("attribute_test",
					jsonData.get("attribute_test").asString());
		}
		if (jsonData.has("num_dice_roll")) {
			newDoor.getScript().setLocalVariable("num_dice_roll",
					jsonData.get("num_dice_roll").asInt());
		}

		// load scripts
		if (jsonData.has("scripted_events")) {
			JsonValue jsonValue = jsonData.get("scripted_events");
			for (int i = Event.values().length - 1; i >= 0; i--) {
				Event event = Event.values()[i];
				if (jsonValue.has(event.toString())) {
					String bundle = jsonValue.get(event.toString()).asString();
					// load bundle for event
					ScriptAction[] actions =
							((FFWebServiceClient) WebServiceClient
									.getInstance())
											.getScriptBundleByName(bundle);
					int eventID = 0;
					switch (event) {
					case OnEnterRoom:
						eventID = FFScriptConsts.SM_303_ENTER_ROOM;
						break;
					case OnExitRoom:
						eventID = FFScriptConsts.SM_302_EXIT_ROOM;
						break;
					case OnBashDoorFailure:
						eventID = FFScriptConsts.SM_306_BASH_DOOR_FAILURE;
						break;
					case OnBashDoorSuccess:
						eventID = FFScriptConsts.SM_305_BASH_DOOR_SUCCESS;
						break;
					default:
						throw new RPGException(ErrorMessage.MISSING_ID,
								"Unknown event - " + event);
					}
					for (int j = 0, len = actions.length; j < len; j++) {
						System.out.println("door " + door.getName()
								+ " adding "
								+ actions[j].getClass().getSimpleName()
								+ " for event " + event);
						newDoor.getScript().addScriptAction(
								eventID, actions[j]);
					}
					bundle = null;
					actions = null;
				}
				event = null;
			}
		}
		return newDoor;
	}
	@Override
	public FFInteractiveObject read(final Json json, final JsonValue jsonData,
			final Class type) {
		FFInteractiveObject roomIO = null;
		FFRoomData room = new FFRoomData();
		// set code
		room.setCode(Integer.parseInt(jsonData.getString("code")));
		// add doors
		if (jsonData.has("doors")) {
			JsonValue jsonValue = jsonData.get("doors");
			JsonValue child = jsonValue.child;
			do {
				try {
					room.addDoor(createDoor(child));
					child = child.next();
				} catch (RPGException e) {
					JOGLErrorHandler.getInstance().fatalError(e);
				}
			} while (child != null);
			jsonValue = null;
			child = null;
		}
		// set exits
		if (jsonData.has("exits")) {
			JsonValue jsonValue = jsonData.get("exits");
			for (int i = Direction.values().length - 1; i >= 0; i--) {
				Direction dir = Direction.valueOf(i);
				if (jsonValue.has(dir.getName())) {
					room.addExit(dir.getValue(),
							jsonValue.getInt(dir.getName()));
				}
				dir = null;
			}
			jsonValue = null;
		}
		// set text
		room.setTextid(jsonData.get("text").get("name").asString());
		try {
			roomIO = ((FFInteractive) Interactive.getInstance()).newRoom(room);
		} catch (RPGException e) {
			JOGLErrorHandler.getInstance().fatalError(e);
		}
		// set scripts
		if (jsonData.has("scripted_events")) {
			JsonValue jsonValue = jsonData.get("scripted_events");
			for (int i = Event.values().length - 1; i >= 0; i--) {
				Event event = Event.values()[i];
				if (jsonValue.has(event.toString())) {
					String bundle = jsonValue.get(event.toString()).asString();
					// load bundle for event
					ScriptAction[] actions =
							((FFWebServiceClient) WebServiceClient
									.getInstance())
											.getScriptBundleByName(bundle);
					int eventID = 0;
					switch (event) {
					case OnEnterRoom:
						eventID = FFScriptConsts.SM_303_ENTER_ROOM;
						break;
					case OnExitRoom:
						eventID = FFScriptConsts.SM_302_EXIT_ROOM;
						break;
					case OnBashDoorFailure:
						eventID = FFScriptConsts.SM_306_BASH_DOOR_FAILURE;
						break;
					case OnBashDoorSuccess:
						eventID = FFScriptConsts.SM_305_BASH_DOOR_SUCCESS;
						break;
					default:
						JOGLErrorHandler.getInstance().fatalError(
								new RPGException(ErrorMessage.MISSING_ID,
										"Unknown event - " + event));
					}
					for (int j = 0, len = actions.length; j < len; j++) {
						System.out.println("room " + room.getCode()
						+ " adding "
						+ actions[j].getClass().getSimpleName()
						+ " for event " + event);
						roomIO.getScript().addScriptAction(
								eventID, actions[j]);
					}
					bundle = null;
					actions = null;
				}
				event = null;
			}
		}
		return roomIO;
	}
	@Override
	public void write(final Json json, final FFInteractiveObject object,
			final Class knownType) {
		// TODO Auto-generated method stub

	}
}
