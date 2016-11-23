package com.dalonedrow.module.tft.systems;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.SerializationException;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Constructor;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.module.barbarianprince.graph.Hex;
import com.dalonedrow.module.barbarianprince.graph.HexFeature;
import com.dalonedrow.module.barbarianprince.graph.RiverCrossing;
import com.dalonedrow.module.barbarianprince.rpg.BpCharacter;
import com.dalonedrow.module.barbarianprince.rpg.BpInteractiveObject;
import com.dalonedrow.module.barbarianprince.rpg.BpNpc;
import com.dalonedrow.module.barbarianprince.rpg.event.EventContainer;
import com.dalonedrow.module.barbarianprince.rpg.event.EventFactory;
import com.dalonedrow.module.tft.rpg.TFTCharacterArchetype;
import com.dalonedrow.module.tft.rpg.TFTHumanoidRaces;
import com.dalonedrow.module.tft.rpg.TFTSpell;
import com.dalonedrow.module.tft.rpg.TFTTalent;
import com.dalonedrow.net.RestService;
import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.pooled.PooledStringBuilder;
import com.dalonedrow.pooled.StringBuilderPool;
import com.dalonedrow.rpg.base.constants.IoGlobals;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.utils.ArrayUtilities;

/**
 * @author drau
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public final class WebServiceClient {
	/** the singleton instance. */
	private static WebServiceClient instance;
	/**
	 * Gets the one and only instance of {@link WebServiceClient}.
	 * @return {@link WebServiceClient}
	 */
	public static WebServiceClient getInstance() {
		if (WebServiceClient.instance == null) {
			WebServiceClient.instance = new WebServiceClient();
		}
		return WebServiceClient.instance;
	}
	/** the api properties. */
	private final Properties	apiProperties;
	/** Hidden constructor. */
	private WebServiceClient() {
		apiProperties = new Properties();
		try {
			InputStream is = getClass().getClassLoader().getResourceAsStream(
					"the_fantasy_trip_api.properties");
			if (is != null) {
				apiProperties.load(is);
			} else {
				System.out.println("Cannot load resource");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/** Loads the list of humanoid races with a call to the web service. */
	public void loadHumanoidRaces() {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		try {
			sb.append(apiProperties.getProperty("endpoint"));
			sb.append(apiProperties.getProperty("humanoidRaceApi"));
		} catch (PooledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RestService.getInstance().callService(sb.toString(), (String[]) null);
		sb.returnToPool();
		sb = null;
		JsonValue root = new JsonReader().parse(
				RestService.getInstance().getResponseString());
		Json json = new Json();
		for (JsonValue entry = root.child; entry != null; entry = entry.next) {
			TFTHumanoidRaces.add(json.fromJson(
					TFTHumanoidRaces.class, entry.toString()));
		}
	}
	/** Loads the list of spells with a call to the web service. */
	public void loadSpells() {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		try {
			sb.append(apiProperties.getProperty("endpoint"));
			sb.append(apiProperties.getProperty("spellApi"));
		} catch (PooledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RestService.getInstance().callService(sb.toString(), (String[]) null);
		sb.returnToPool();
		sb = null;
		JsonValue root = new JsonReader().parse(
				RestService.getInstance().getResponseString());
		Json json = new Json();
		for (JsonValue entry = root.child; entry != null; entry = entry.next) {
			TFTSpell.add(json.fromJson(TFTSpell.class, entry.toString()));
		}
	}
	/** Loads the list of talents with a call to the web service. */
	public void loadTalents() {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		try {
			sb.append(apiProperties.getProperty("endpoint"));
			sb.append(apiProperties.getProperty("talentApi"));
		} catch (PooledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RestService.getInstance().callService(sb.toString(), (String[]) null);
		sb.returnToPool();
		sb = null;
		JsonValue root = new JsonReader().parse(
				RestService.getInstance().getResponseString());
		Json json = new Json();
		for (JsonValue entry = root.child; entry != null; entry = entry.next) {
			TFTTalent.add(json.fromJson(TFTTalent.class, entry.toString()));
		}
	}
	/** Loads the list of archetypes with a call to the web service. */
	public void loadArchetypes() {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		try {
			sb.append(apiProperties.getProperty("endpoint"));
			sb.append(apiProperties.getProperty("archetypeApi"));
		} catch (PooledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RestService.getInstance().callService(sb.toString(), (String[]) null);
		sb.returnToPool();
		sb = null;
		JsonValue root = new JsonReader().parse(
				RestService.getInstance().getResponseString());
		Json json = new Json();
		for (JsonValue entry = root.child; entry != null; entry = entry.next) {
			TFTCharacterArchetype.add(json.fromJson(
					TFTCharacterArchetype.class, entry.toString()));
		}
	}
	/**
	 * Creates a new instance of a class.
	 * @param type the class
	 * @return {@link Object}
	 */
	private Object newInstance(Class type) {
		try {
			return ClassReflection.newInstance(type);
		} catch (Exception ex) {
			try {
				// Try a private constructor.
				Constructor constructor =
						ClassReflection.getDeclaredConstructor(type);
				constructor.setAccessible(true);
				return constructor.newInstance();
			} catch (SecurityException ignored) {
			} catch (ReflectionException ignored) {
				if (ClassReflection.isAssignableFrom(Enum.class, type)) {
					if (type.getEnumConstants() == null) {
						type = type.getSuperclass();
					}
					return type.getEnumConstants()[0];
				}
				if (type.isArray()) {
					throw new SerializationException(
							"Encountered JSON object when "
									+ "expected array of type: "
									+ type.getName(),
							ex);
				} else if (ClassReflection.isMemberClass(type)
						&& !ClassReflection.isStaticClass(type)) {
					throw new SerializationException(
							"Class cannot be created "
									+ "(non-static member class): "
									+ type.getName(),
							ex);
				} else {
					throw new SerializationException(
							"Class cannot be created "
									+ "(missing no-arg constructor): "
									+ type.getName(),
							ex);
				}
			} catch (Exception privateConstructorException) {
				ex = privateConstructorException;
			}
			throw new SerializationException(
					"Error constructing instance of class: "
							+ type.getName(),
					ex);
		}
	}
}
