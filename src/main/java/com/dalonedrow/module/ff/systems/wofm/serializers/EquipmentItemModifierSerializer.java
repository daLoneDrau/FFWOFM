package com.dalonedrow.module.ff.systems.wofm.serializers;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializer;
import com.badlogic.gdx.utils.JsonValue;
import com.dalonedrow.rpg.base.flyweights.EquipmentItemModifier;

/**
 * @author drau
 */
@SuppressWarnings("rawtypes")
public class EquipmentItemModifierSerializer
		implements Serializer<EquipmentItemModifier> {
	@Override
	public EquipmentItemModifier read(final Json json, final JsonValue jsonData,
			final Class type) {
		EquipmentItemModifier modifier = new EquipmentItemModifier();
		// set percent
		boolean percent = false;
		if (jsonData.has("percent")) {
			percent = jsonData.getBoolean("percent");
		}
		modifier.setPercentage(percent);
		// set special
		int special = 0;
		if (jsonData.has("special")) {
			special = jsonData.getInt("special");
		}
		modifier.setSpecial(special);
		// set value
		float value = 0;
		if (jsonData.has("value")) {
			value = jsonData.getFloat("value");
		}
		modifier.setValue(value);
		return modifier;
	}
	@Override
	public void write(final Json json, final EquipmentItemModifier object,
			final Class knownType) {
		// TODO Auto-generated method stub

	}
}
