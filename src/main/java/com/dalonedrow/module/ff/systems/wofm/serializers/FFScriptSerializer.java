package com.dalonedrow.module.ff.systems.wofm.serializers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializer;
import com.badlogic.gdx.utils.JsonValue;
import com.dalonedrow.module.ff.rpg.Direction;
import com.dalonedrow.module.ff.systems.wofm.FFWebServiceClient;
import com.dalonedrow.module.ff.wofm.rpg.script.scriptactions.CreateExitAction;
import com.dalonedrow.module.ff.wofm.rpg.script.scriptactions.DmgPlayerAction;
import com.dalonedrow.module.ff.wofm.rpg.script.scriptactions.GoToCombatAction;
import com.dalonedrow.module.ff.wofm.rpg.script.scriptactions.MovePlayerAction;
import com.dalonedrow.module.ff.wofm.rpg.script.scriptactions.PermalockAction;
import com.dalonedrow.module.ff.wofm.rpg.script.scriptactions.ScriptActionType;
import com.dalonedrow.module.ff.wofm.rpg.script.scriptactions.SetRoomTextAction;
import com.dalonedrow.module.ff.wofm.rpg.script.scriptactions.ShowTextAction;
import com.dalonedrow.module.ff.wofm.rpg.script.scriptactions.
TestYourLuckAction;
import com.dalonedrow.rpg.base.flyweights.ScriptAction;
import com.dalonedrow.rpg.base.systems.WebServiceClient;

/**
 * @author drau
 */
@SuppressWarnings("rawtypes")
public final class FFScriptSerializer implements Serializer<ScriptAction> {
	@Override
	public ScriptAction read(final Json json, final JsonValue jsonData,
			final Class type) {
		ScriptAction script = null;
		if (jsonData.has("type")) {
			ScriptActionType scriptType =
					ScriptActionType.valueOf(jsonData.get("type").asString());
			switch (scriptType) {
			case CREATE_EXIT:
				script = new CreateExitAction(
						jsonData.get("destination").asInt(),
						Direction.valueOf(jsonData.get("direction").asString()),
						jsonData.get("origin").asInt());
				break;
			case DMG_PLAYER:
				script = new DmgPlayerAction(
						jsonData.get("attribute").asString(),
						jsonData.get("amount").asInt());
				break;
			case GO_TO_COMBAT:
				List<String> strList = new ArrayList<String>();
				JsonValue list = jsonData.get("mobs");
				System.out.println(list);
				for (JsonValue entry = list.child;
						entry != null; entry = entry.next) {
					strList.add(entry.toString());
				}
				String textId = null;
				if (jsonData.has("text_name")) {
					textId = jsonData.get("text_name").asString();
				}				
				script = new GoToCombatAction(strList, textId);
				strList = null;
				list = null;
				textId = null;
				break;
			case MOVE_PLAYER:
				script = new MovePlayerAction(
						jsonData.get("room_code").asInt());
				break;
			case PERMALOCK:
				script = new PermalockAction(
						jsonData.get("door_name").asString());
				break;
			case SET_ROOM_TEXT:
				script = new SetRoomTextAction(
						jsonData.get("room_code").asInt(),
						jsonData.get("text_name").asString());
				break;
			case SHOW_TEXT:
				script = new ShowTextAction(
						jsonData.get("error").asBoolean(),
						jsonData.get("text_name").asString());
				break;
			case TEST_YOUR_LUCK:
				script = new TestYourLuckAction(
						((FFWebServiceClient) WebServiceClient.getInstance())
								.getScriptBundleByName(
										jsonData.get("pass_scripts")
												.asString()),
						((FFWebServiceClient) WebServiceClient.getInstance())
								.getScriptBundleByName(
										jsonData.get("fail_scripts")
												.asString()));
				break;
			default:
				System.err.println("Undefined script type " + scriptType);
				System.exit(1);
			}
		}
		return script;
	}
	@Override
	public void write(final Json json, final ScriptAction object,
			final Class knownType) {
		// TODO Auto-generated method stub

	}
}
