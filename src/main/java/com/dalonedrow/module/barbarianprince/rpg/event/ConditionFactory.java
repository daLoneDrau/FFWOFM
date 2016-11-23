package com.dalonedrow.module.barbarianprince.rpg.event;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonValue.ValueType;
import com.dalonedrow.module.barbarianprince.graph.HexMap;
import com.dalonedrow.rpg.base.flyweights.RPGException;

/**
 * 
 * @author drau
 *
 */
@SuppressWarnings("rawtypes")
public final class ConditionFactory implements Json.Serializer<Condition> {
	/** the singleton instance. */
	private static ConditionFactory instance;
	/**
	 * Gets the one and only instance of {@link ConditionFactory}.
	 * @return {@link ConditionFactory}
	 * @throws RPGException if an error occurs
	 */
	public static ConditionFactory getInstance() throws RPGException {
		if (ConditionFactory.instance == null) {
			ConditionFactory.instance = new ConditionFactory();
		}
		return ConditionFactory.instance;
	}
	/** Hidden constructor. */
	private ConditionFactory() {
		super();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Condition read(final Json json, final JsonValue jsonData, 
			final Class type) {
		int conditionType = 0;
		if (jsonData.has("type")) {
			conditionType = jsonData.getInt("type");
		}
		Condition c = null;
		switch (conditionType) {
		case ConditionType.LOCATION_CONDITION:
			c = new LocationCondition();
			c.setName(jsonData.getString("name"));
			JsonValue location = jsonData.get("locations").child;
			for (; location != null; location = location.next) {
				int x = location.getInt("x");
				int y = location.getInt("y");
				try {
					((LocationCondition) c).addLocation(
							HexMap.getInstance().getHex(x, y).getIndex());
				} catch (RPGException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			location = null;
			break;
		case ConditionType.ROLL_CONDITION:
			c = new RollCondition();
			c.setName(jsonData.getString("name"));
			JsonValue roll = jsonData.get("rolls").child;
			((RollCondition) c).setNumD6(roll.getInt("num_d6"));
			((RollCondition) c).setRollMin(roll.getInt("roll_min"));
			if (roll.has("roll_modifier")) {
				((RollCondition) c).setRollModifier(roll.getInt(
						"roll_modifier"));
			}
			if (roll.has("condition_modifier")) {
				((RollCondition) c).setConditionModifier(roll.getInt(
						"condition_modifier"));
			}
			if (roll.has("conditions")) {
				Json json2 = new Json();
				try {
					json2.setSerializer(Condition.class, 
							ConditionFactory.getInstance());
				} catch (RPGException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				((RollCondition) c).setCondition(json2.fromJson(Condition.class,
						roll.get("conditions").child.toString()));
				json2 = null;
			}
			roll = null;
			break;
		}
		return c;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(final Json json, final Condition object, 
			final Class knownType) {
		// TODO Auto-generated method stub
		
	}
}
