package com.dalonedrow.module.barbarianprince.rpg.event;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.dalonedrow.rpg.base.constants.ScriptConsts;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.systems.Script;

/**
 * 
 * @author drau
 *
 */
@SuppressWarnings("rawtypes")
public final class EventFactory implements Json.Serializer<EventContainer> {
	/** the singleton instance. */
	private static EventFactory instance;
	/**
	 * Gets the one and only instance of {@link EventFactory}.
	 * @return {@link EventFactory}
	 * @throws RPGException if an error occurs
	 */
	public static EventFactory getInstance() throws RPGException {
		if (EventFactory.instance == null) {
			EventFactory.instance = new EventFactory();
		}
		return EventFactory.instance;
	}
	/** Hidden constructor. */
	private EventFactory() {
		super();
	}
	/**
	 * Creates a delayed event from JSON.
	 * @param subevent the JSON being parsed
	 * @return {@link DelayedEvent}
	 */
	private DelayedEvent createDelayedEvent(final JsonValue subevent) {
		DelayedEvent e = new DelayedEvent();
		JsonValue rolls = subevent.get("eventRolls");
		JsonValue roll = rolls.child;
		for (; roll != null; roll = roll.next) {
			e.setEventId(roll.getInt("outcome"));
		}
		JsonValue turnPhases = subevent.get("turn_phase");
		JsonValue tpChild = turnPhases.child;
		for (; tpChild != null; tpChild = tpChild.next) {
			e.setTurnPhase(tpChild.getInt("id"));
		}
		return e;
	}
	/**
	 * Creates a mob spawn event from JSON.
	 * @param subevent the JSON being parsed
	 * @return {@link MobSpawnEvent}
	 */
	private MobSpawnEvent createMobSpawnEvent(final JsonValue subevent) {
		MobSpawnEvent e = new MobSpawnEvent();
		JsonValue rolls = subevent.get("eventRolls");
		JsonValue roll = rolls.child;
		for (; roll != null; roll = roll.next) {
			e.addRandomSpawnNumber(roll.getInt("outcome"));
		}
		JsonValue characters = subevent.get("characters");
		JsonValue character = characters.child;
		e.setMobId(character.getInt("id"));
		return e;
	}
	/**
	 * Creates a random teleport event from JSON.
	 * @param subevent the JSON being parsed
	 * @return {@link RandomTeleportEvent}
	 */
	private RandomTeleportEvent createRandomTeleportEvent(
			final JsonValue subevent) {
		RandomTeleportEvent e = new RandomTeleportEvent();
		JsonValue rolls = subevent.get("eventRolls");
		JsonValue roll = rolls.child;
		for (; roll != null; roll = roll.next) {
			e.addDestination(roll.getInt("outcome"));
		}
		return e;
	}
	/**
	 * Reads a value directly from the JSON, to avoid libgdx' parse errors.
	 * @param jsonData the json being read
	 * @return {@link EventContainer}
	 */
	public EventContainer readFromJsonValue(final JsonValue jsonData) {
		EventContainer ec = new EventContainer() {
			
		};
		ec.setName(jsonData.getString("name"));
		if (jsonData.has("subevents")) {
			JsonValue subevent = jsonData.get("subevents").child;
			for (; subevent != null; subevent = subevent.next) {
				JsonValue type = subevent.get("type");
				

				Script.getInstance().stackSendIOScriptEvent(
						group,
						ScriptConsts.SM_21_MOVE,
						new Object[] { "final_destination", destination,
								"move_target", to.getIndex(),
								"code", code },
						"move");
				int m
				if (subevent.has("type")) {
					eventType = subevent.getInt("type");
				}
				Event e = null;
				switch (eventType) {
				case EventType.RANDOM_TELEPORT_EVENT:
					e = createRandomTeleportEvent(subevent);
					break;
				case EventType.DELAYED_EVENT:
					e = createDelayedEvent(subevent);
					break;
				case EventType.MOB_ENCOUNTER_EVENT:
					e = createMobSpawnEvent(subevent);
					break;
					default:
						System.err.println("INVALID TYPE " + eventType);
				}
				e.setName(subevent.getString("name"));
				if (subevent.has("conditions")) {
					Json json2 = new Json();
					try {
						json2.setSerializer(Condition.class, 
								ConditionFactory.getInstance());
					} catch (RPGException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (subevent.get("conditions").size > 1) {
						LinkedCondition lc = new LinkedCondition();
						JsonValue condition = subevent.get("conditions").child;
						for (; condition != null; condition = condition.next) {
							lc.addChild(json2.fromJson(Condition.class, 
									condition.toString()));
						}
						e.setCondition(lc);
						lc = null;
						condition = null;
					} else {
						e.setCondition(json2.fromJson(Condition.class, 
								subevent.get("conditions").child.toString()));
					}
					json2 = null;
				}
				ec.addEvent(e);
				e = null;
			}
			subevent = null;
		}
		return ec;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public EventContainer read(final Json json, final JsonValue jsonData, 
			final Class type) {
		EventContainer ec = new EventContainer() {
			
		};
		ec.setName(jsonData.getString("name"));
		if (jsonData.has("subevents")) {
			JsonValue subevent = jsonData.get("subevents").child;
			for (; subevent != null; subevent = subevent.next) {
				int eventType = 0;
				if (subevent.has("type")) {
					eventType = subevent.getInt("type");
				}
				Event e = null;
				switch (eventType) {
				case EventType.RANDOM_TELEPORT_EVENT:
					e = createRandomTeleportEvent(subevent);
					break;
				case EventType.DELAYED_EVENT:
					e = createDelayedEvent(subevent);
					break;
				case EventType.MOB_ENCOUNTER_EVENT:
					e = createMobSpawnEvent(subevent);
					break;
					default:
						System.err.println("INVALID TYPE " + eventType);
				}
				e.setName(subevent.getString("name"));
				if (subevent.has("conditions")) {
					Json json2 = new Json();
					try {
						json2.setSerializer(Condition.class, 
								ConditionFactory.getInstance());
					} catch (RPGException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (subevent.get("conditions").size > 1) {
						LinkedCondition lc = new LinkedCondition();
						JsonValue condition = subevent.get("conditions").child;
						for (; condition != null; condition = condition.next) {
							lc.addChild(json2.fromJson(Condition.class, 
									condition.toString()));
						}
						e.setCondition(lc);
						lc = null;
						condition = null;
					} else {
						e.setCondition(json2.fromJson(Condition.class, 
								subevent.get("conditions").child.toString()));
					}
					json2 = null;
				}
				ec.addEvent(e);
				e = null;
			}
			subevent = null;
		}
		return ec;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(final Json json, final EventContainer object,
			final Class knownType) {
		// TODO Auto-generated method stub
		
	}
}
