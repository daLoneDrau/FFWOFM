package com.dalonedrow.module.ff.systems.wofm;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.dalonedrow.engine.systems.base.JOGLErrorHandler;
import com.dalonedrow.module.ff.rpg.FFInteractiveObject;
import com.dalonedrow.module.ff.rpg.FFNpc;
import com.dalonedrow.module.ff.rpg.FFRoomData;
import com.dalonedrow.module.ff.systems.wofm.serializers.EquipmentItemModifierSerializer;
import com.dalonedrow.module.ff.systems.wofm.serializers.FFItemSerializer;
import com.dalonedrow.module.ff.systems.wofm.serializers.FFNpcSerializer;
import com.dalonedrow.module.ff.systems.wofm.serializers.FFRoomSerializer;
import com.dalonedrow.module.ff.systems.wofm.serializers.FFScriptSerializer;
import com.dalonedrow.net.RestService;
import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.pooled.PooledStringBuilder;
import com.dalonedrow.pooled.StringBuilderPool;
import com.dalonedrow.rpg.base.flyweights.EquipmentItemModifier;
import com.dalonedrow.rpg.base.flyweights.ScriptAction;
import com.dalonedrow.rpg.base.systems.WebServiceClient;
import com.dalonedrow.utils.ArrayUtilities;

/**
 * @author drau
 */
@SuppressWarnings({ "rawtypes" })
public final class FFWebServiceClient extends WebServiceClient {
	/** the api properties. */
	private final Properties apiProperties;
	/** Hidden constructor. */
	FFWebServiceClient() {
		super.setInstance(this);
		apiProperties = new Properties();
		try {
			InputStream is = getClass().getClassLoader().getResourceAsStream(
					"ff.properties");
			if (is != null) {
				apiProperties.load(is);
			} else {
				System.out.println("not found");
			}
		} catch (IOException e) {
			JOGLErrorHandler.getInstance().fatalError(e);
		}
	}
	/**
	 * Gets a room by its id code.
	 * @param code the room's id code
	 * @return {@link FFRoomData}
	 */
	public FFRoomData getRoomByCode(final int code) {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		try {
			sb.append(apiProperties.getProperty("endpoint"));
			sb.append(apiProperties.getProperty("roomApi"));
			sb.append("code/");
			sb.append(code);
		} catch (PooledException e) {
			JOGLErrorHandler.getInstance().fatalError(e);
		}
		RestService.getInstance().callService(sb.toString());
		sb.returnToPool();
		sb = null;
		FFRoomData[] list = new FFRoomData[0];
		JsonValue root = new JsonReader().parse(
				RestService.getInstance().getResponseString());
		Json json = new Json();
		json.setSerializer(FFRoomData.class,
				new Json.Serializer<FFRoomData>() {
					@Override
					public FFRoomData read(final Json json,
							final JsonValue jsonData,
							final Class type) {
						FFRoomData room = new FFRoomData();
						room.setCode(
								Integer.parseInt(jsonData.getString("code")));
						JsonValue text = jsonData.get("text");
						room.setTextid(text.get("name").asString());
						return room;
					}
					@Override
					public void write(final Json json, final FFRoomData object,
							final Class knownType) {
						// TODO Auto-generated method stub
					}
				});
		for (JsonValue entry = root.child; entry != null; entry = entry.next) {
			list = ArrayUtilities.getInstance().extendArray(
					json.fromJson(FFRoomData.class, entry.toString()), list);
		}
		return list[0];
	}
	/**
	 * Gets a NPC with a call to the web service.
	 * @param name the NPC's name
	 * @return {@link FFInteractiveObject}
	 */
	public FFInteractiveObject getNPCByName(final String name) {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		try {
			sb.append(apiProperties.getProperty("endpoint"));
			sb.append(apiProperties.getProperty("npcApi"));
			sb.append("name/");
			sb.append(name);
		} catch (PooledException e) {
			JOGLErrorHandler.getInstance().fatalError(e);
		}
		RestService.getInstance().callService(sb.toString());
		sb.returnToPool();
		sb = null;
		FFInteractiveObject[] list = new FFInteractiveObject[0];
		JsonValue root = new JsonReader().parse(
				RestService.getInstance().getResponseString());
		Json json = new Json();
		json.setSerializer(FFInteractiveObject.class, new FFNpcSerializer());
		for (JsonValue entry = root.child; entry != null; entry = entry.next) {
			list = ArrayUtilities.getInstance().extendArray(
					json.fromJson(FFInteractiveObject.class, entry.toString()),
					list);
		}
		return list[0];
	}
	/**
	 * Gets an item with a call to the web service.
	 * @param name the NPC's name
	 * @return {@link FFInteractiveObject}
	 */
	public FFInteractiveObject getItemByName(final String name) {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		try {
			sb.append(apiProperties.getProperty("endpoint"));
			sb.append(apiProperties.getProperty("itemApi"));
			sb.append("item_name/");
			sb.append(name);
		} catch (PooledException e) {
			JOGLErrorHandler.getInstance().fatalError(e);
		}
		RestService.getInstance().callService(sb.toString());
		sb.returnToPool();
		sb = null;
		FFInteractiveObject[] list = new FFInteractiveObject[0];
		JsonValue root = new JsonReader().parse(
				RestService.getInstance().getResponseString());
		Json json = new Json();
		json.setSerializer(FFInteractiveObject.class, new FFItemSerializer());
		for (JsonValue entry = root.child; entry != null; entry = entry.next) {
			list = ArrayUtilities.getInstance().extendArray(
					json.fromJson(FFInteractiveObject.class, entry.toString()),
					list);
		}
		return list[0];
	}
	/**
	 * Gets an {@link EquipmentItemModifier} with a call to the web service.
	 * @param code the modifier's code
	 * @return {@link EquipmentItemModifier}
	 */
	public EquipmentItemModifier getEquipmentItemModifierByCode(
			final String code) {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		try {
			sb.append(apiProperties.getProperty("endpoint"));
			sb.append(apiProperties.getProperty("equipModifersApi"));
			sb.append("code/");
			sb.append(code);
		} catch (PooledException e) {
			JOGLErrorHandler.getInstance().fatalError(e);
		}
		RestService.getInstance().callService(sb.toString());
		sb.returnToPool();
		sb = null;
		EquipmentItemModifier[] list = new EquipmentItemModifier[0];
		JsonValue root = new JsonReader().parse(
				RestService.getInstance().getResponseString());
		Json json = new Json();
		json.setSerializer(EquipmentItemModifier.class,
				new EquipmentItemModifierSerializer());
		for (JsonValue entry = root.child; entry != null; entry = entry.next) {
			list = ArrayUtilities.getInstance().extendArray(
					json.fromJson(EquipmentItemModifier.class,
							entry.toString()),
					list);
		}
		return list[0];
	}
	/**
	 * Gets a list of scripted actions with a call to the web service.
	 * @param name the name of the script bundle
	 * @return {@link ScriptAction}[]
	 */
	public ScriptAction[] getScriptBundleByName(final String name) {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		try {
			sb.append(apiProperties.getProperty("endpoint"));
			sb.append(apiProperties.getProperty("scriptBundleApi"));
			sb.append("name/");
			sb.append(name);
		} catch (PooledException e) {
			JOGLErrorHandler.getInstance().fatalError(e);
		}
		RestService.getInstance().callService(sb.toString());
		sb.returnToPool();
		sb = null;
		ScriptAction[] list = new ScriptAction[0];
		JsonValue root = new JsonReader().parse(
				RestService.getInstance().getResponseString());
		Json json = new Json();
		json.setSerializer(ScriptAction.class, new FFScriptSerializer());
		for (JsonValue entry = root.child; entry != null; entry = entry.next) {
			if (entry.hasChild("scripts")) {
				JsonValue scripts = entry.get("scripts");
				for (JsonValue script = scripts.child; script != null; script =
						script.next) {
					list = ArrayUtilities.getInstance().extendArray(
							json.fromJson(ScriptAction.class,
									script.toString()),
							list);
				}
			}
		}
		return list;
	}
	/**
	 * Gets a result from the treasure table with a call to the web service.
	 * @param name the name of the text section
	 * @return {@link String}[]
	 */
	@Override
	public String getTextByName(final String name) {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		try {
			sb.append(apiProperties.getProperty("endpoint"));
			sb.append(apiProperties.getProperty("textApi"));
			sb.append("name/");
			sb.append(name);
		} catch (PooledException e) {
			JOGLErrorHandler.getInstance().fatalError(e);
		}
		RestService.getInstance().callService(sb.toString());
		sb.returnToPool();
		sb = null;
		String[] list = new String[0];
		JsonValue root = new JsonReader().parse(
				RestService.getInstance().getResponseString());
		Json json = new Json();
		json.setSerializer(String.class,
				new Json.Serializer<String>() {
					@Override
					public String read(final Json json,
							final JsonValue jsonData,
							final Class type) {
						return jsonData.getString("text");
					}
					@Override
					public void write(final Json json, final String object,
							final Class knownType) {
						// TODO Auto-generated method stub
					}
				});
		for (JsonValue entry = root.child; entry != null; entry = entry.next) {
			list = ArrayUtilities.getInstance().extendArray(
					json.fromJson(String.class, entry.toString()), list);
		}
		String s = null;
		if (list.length > 0) {
			s = list[0];
		}
		list = null;
		return s;
	}
	/** Loads all rooms. */
	public void loadAllRooms() {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		try {
			sb.append(apiProperties.getProperty("endpoint"));
			sb.append(apiProperties.getProperty("roomApi"));
		} catch (PooledException e) {
			JOGLErrorHandler.getInstance().fatalError(e);
		}
		RestService.getInstance().callService(sb.toString());
		sb.returnToPool();
		sb = null;
		JsonValue root = new JsonReader().parse(
				RestService.getInstance().getResponseString());
		Json json = new Json();
		json.setSerializer(FFInteractiveObject.class, new FFRoomSerializer());
		for (JsonValue entry = root.child; entry != null; entry = entry.next) {
			json.fromJson(FFInteractiveObject.class, entry.toString());
		}
	}
}
