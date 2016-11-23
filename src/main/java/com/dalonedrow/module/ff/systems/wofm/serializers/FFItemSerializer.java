package com.dalonedrow.module.ff.systems.wofm.serializers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializer;
import com.badlogic.gdx.utils.JsonValue;
import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.engine.systems.base.JOGLErrorHandler;
import com.dalonedrow.module.ff.constants.FFIo;
import com.dalonedrow.module.ff.rpg.FFInteractiveObject;
import com.dalonedrow.module.ff.rpg.FFItem;
import com.dalonedrow.module.ff.rpg.FFScriptable;
import com.dalonedrow.module.ff.systems.wofm.FFInteractive;
import com.dalonedrow.module.ff.systems.wofm.FFWebServiceClient;
import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.pooled.PooledStringBuilder;
import com.dalonedrow.pooled.StringBuilderPool;
import com.dalonedrow.rpg.base.constants.IoGlobals;
import com.dalonedrow.rpg.base.constants.ScriptConsts;
import com.dalonedrow.rpg.base.flyweights.EquipmentItemModifier;
import com.dalonedrow.rpg.base.flyweights.ErrorMessage;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.systems.Script;
import com.dalonedrow.rpg.base.systems.WebServiceClient;

/**
 * @author drau
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public final class FFItemSerializer implements Serializer<FFInteractiveObject> {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public FFInteractiveObject read(final Json json, final JsonValue jsonData,
			final Class type) {
		FFInteractiveObject itemIO = null;
		try {
			FFItem item = new FFItem();
			itemIO = ((FFInteractive) Interactive.getInstance()).newItem(item);
			itemIO.addIOFlag(IoGlobals.IO_02_ITEM);
			// SET ITEM NAME
			if (jsonData.has("item_name")) {
				item.setItemName(jsonData.getString("item_name"));
			}
			// SET DESCRIPTION
			if (jsonData.has("description")) {
				item.setDescription(jsonData.getString("description"));
			}
			// ASSIGN GROUPS
			if (jsonData.has("groups")) {
				JsonValue jsonValue = jsonData.get("groups");
				JsonValue child = jsonValue.child;
				do {
					Script.getInstance().addToGroup(itemIO, child.asString());
					child = child.next();
				} while (child != null);
				jsonValue = null;
				child = null;
			}
			// SET PRICE
			if (jsonData.has("price")) {
				item.setPrice(jsonData.getInt("price"));
			}
			// SET STACK SIZE
			if (jsonData.has("stack_size")) {
				item.setStackSize(jsonData.getInt("stack_size"));
			}
			// SET OBJECT TYPES
			if (jsonData.has("types")) {
				JsonValue jsonValue = jsonData.get("types");
				JsonValue child = jsonValue.child;
				do {
					item.ARX_EQUIPMENT_SetObjectType(
							child.getInt("flag"), true);
					child = child.next();
				} while (child != null);
				jsonValue = null;
				child = null;
			}
			// SET EQUIPMENT ELEMENT MODIFIERS
			if (jsonData.has("modifiers")) {
				JsonValue jsonValue = jsonData.get("modifiers");
				JsonValue child = jsonValue.child;
				do {
					int elemIndex = FFIo.getElementIndex(child.name());
					item.getEquipitem().getElement(elemIndex).clearData();
					EquipmentItemModifier modifier =
							((FFWebServiceClient) WebServiceClient
									.getInstance())
											.getEquipmentItemModifierByCode(
													child.asString());
					item.getEquipitem().getElement(elemIndex).setPercentage(
							modifier.isPercentage());
					item.getEquipitem().getElement(elemIndex).setSpecial(
							modifier.getSpecial());
					item.getEquipitem().getElement(elemIndex).setValue(
							modifier.getValue());
					modifier = null;
					child = child.next();
				} while (child != null);
				jsonValue = null;
				child = null;
			}
			
			// SET MINOR FIELDS NOT USED IN THE GAME
			// set class restrictions - NOT USED IN THIS GAME
			if (jsonData.has("class_restrictions")) {
				item.addClassRestriction(jsonData.getInt("class_restrictions"));
			} else {
				item.clearClassRestrictions();
			}
			// set count
			if (jsonData.has("count")) {
				item.setCount(jsonData.getInt("count"));
			}
			// set food_value - NOT USED IN THIS GAME
			if (jsonData.has("food_value")) {
				item.setFoodValue((char) jsonData.getInt("food_value"));
			}
			// set light_value - NOT USED IN THIS GAME
			if (jsonData.has("light_value")) {
				item.setLightValue(jsonData.getInt("light_value"));
			}
			// set max_owned - NOT USED IN THIS GAME
			if (jsonData.has("max_owned")) {
				item.setMaxOwned(jsonData.getInt("max_owned"));
			}
			// set race restrictions - NOT USED IN THIS GAME
			if (jsonData.has("race_restrictions")) {
				item.addRaceRestriction(jsonData.getInt("race_restrictions"));
			} else {
				item.clearRaceRestrictions();
			}
			// set steal_value - NOT USED IN THIS GAME
			if (jsonData.has("steal_value")) {
				item.setStealvalue((char) jsonData.getInt("steal_value"));
			}
			// set weight - NOT USED IN THIS GAME
			if (jsonData.has("weight")) {
				item.setWeight(jsonData.getInt("weight"));
			}
			if (jsonData.has("internal_script")) {
				PooledStringBuilder sb =
						StringBuilderPool.getInstance().getStringBuilder();
				try {
					sb.append("com.dalonedrow.module.ff.wofm");
					sb.append(".rpg.script.items.");
					sb.append(jsonData.getString("internal_script"));
					sb.append("Script");
					Class clazz = Class.forName(sb.toString());
					Constructor constructor = clazz.getConstructor(
							new Class[] { FFInteractiveObject.class });
					sb.returnToPool();
					sb = null;
					itemIO.setScript(
							(FFScriptable) constructor.newInstance(itemIO));
					clazz = null;
					constructor = null;
				} catch (PooledException | ClassNotFoundException
						| InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException
						| NoSuchMethodException | SecurityException e) {
					throw new RPGException(ErrorMessage.INTERNAL_ERROR, e);
				}
			}
			Script.getInstance().sendInitScriptEvent(itemIO);
			Script.getInstance().sendIOScriptEvent(
					itemIO, ScriptConsts.SM_033_INITEND, null, null);
		} catch (RPGException e) {
			JOGLErrorHandler.getInstance().fatalError(e);
		}
		return itemIO;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(final Json json, final FFInteractiveObject object,
			final Class knownType) {
		// TODO Auto-generated method stub

	}
}
