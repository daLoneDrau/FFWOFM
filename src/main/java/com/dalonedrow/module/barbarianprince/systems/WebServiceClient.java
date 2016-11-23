package com.dalonedrow.module.barbarianprince.systems;

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
	private Hex					hex;
	private int					hl;
	/** Hidden constructor. */
	private WebServiceClient() {
		apiProperties = new Properties();
		try {
			InputStream is = getClass().getClassLoader().getResourceAsStream(
					"dwarfstar_barbarian_prince_api.properties");
			if (is != null) {
				apiProperties.load(is);
			} else {
				System.out.println("not found");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Loads the character's starting statistics with a call to the web service.
	 * @param id the character's id
	 * @return {@link Object}[]
	 */
	public Object[] getCharacter(final int id) {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		try {
			sb.append(apiProperties.getProperty("endpoint"));
			sb.append(apiProperties.getProperty("characterApi"));
			sb.append(id);
		} catch (PooledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RestService.getInstance().callService(sb.toString(), (String[]) null);
		sb.returnToPool();
		sb = null;
		Object[] vars = null;
		JsonValue root = new JsonReader().parse(
				RestService.getInstance().getResponseString());
		Json json = new Json();
		json.setSerializer(Object[].class,
				new Json.Serializer<Object[]>() {
					@Override
					public Object[] read(final Json json,
							final JsonValue jsonData,
							final Class type) {
						int cs = 0;
						if (jsonData.has("combat_skill")) {
							cs = jsonData.getInt("combat_skill");
						}
						int wealth = 0;
						if (jsonData.has("wealth")) {
							wealth = jsonData.getInt("wealth");
						}
						Object[] vars = {
								"combat_skill", cs,
								"endurance", jsonData.getInt("endurance"),
								"wealth", wealth,
								"name", jsonData.get("name").asString()
								};
						if (jsonData.has("group")) {
							JsonValue group = jsonData.get("group");
							JsonValue entry = group.child;
							String[] g = new String[0];
							for (; entry != null; entry = entry.next) {
								g = ArrayUtilities.getInstance().extendArray(
										entry.getString("name"), g);
							}
							vars = ArrayUtilities.getInstance().extendArray(
									"group", vars);
							vars = ArrayUtilities.getInstance().extendArray(
									g, vars);
							group = null;
							entry = null;
							g = null;
						}
						return vars;
					}
					@Override
					public void write(final Json json, final Object[] object,
							final Class knownType) {
						// TODO Auto-generated method stub
					}
				});
		vars = json.fromJson(Object[].class, root.toString());
		return vars;
	}
	/**
	 * Gets a result from the event table with a call to the web service.
	 * @param id the event's id
	 * @return {@link EventContainer}
	 * @throws PooledException should never happen
	 * @throws RPGException if an error occurs
	 */
	public EventContainer getEvent(final int id)
			throws PooledException, RPGException {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		try {
			sb.append(apiProperties.getProperty("endpoint"));
			sb.append(apiProperties.getProperty("eventApi"));
			sb.append(id);
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
		json.setSerializer(EventContainer.class, EventFactory.getInstance());
		EventContainer ec =
				json.fromJson(EventContainer.class, root.toString());
		return ec;
	}
	/**
	 * Loads the river crossings name with a call to the web service.
	 * @param from the hex being traveled from
	 * @param to the hex being traveled to
	 * @return {@link String}
	 */
	public String getRiverCrossingName(final long from, final long to) {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		try {
			sb.append(apiProperties.getProperty("endpoint"));
			sb.append(apiProperties.getProperty("riverCrossingApi"));
			sb.append("to_from/");
		} catch (PooledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RestService.getInstance().callService(sb.toString(),
				new String[] { "from", Long.toString(from),
						"to", Long.toString(to) });
		sb.returnToPool();
		sb = null;
		String n = "";
		JsonValue root = new JsonReader().parse(
				RestService.getInstance().getResponseString());
		Json json = new Json();
		json.setSerializer(String.class, new Json.Serializer<String>() {
			@Override
			public String read(final Json json, final JsonValue jsonData,
					final Class type) {
				return jsonData.getString("name");
			}
			@Override
			public void write(final Json json, final String object,
					final Class knownType) {
				// TODO Auto-generated method stub
			}
		});
		n = json.fromJson(String.class, root.toString());
		root = null;
		json = null;
		return n;
	}
	/**
	 * Gets a result from the travel event table with a call to the web service.
	 * @param terrain the terrain
	 * @param roll1 the first roll
	 * @param roll2 the second roll
	 * @return {@link Object}[]
	 * @throws PooledException should never happen
	 * @throws RPGException if an error occurs
	 */
	public Object[] getTravelEvent(final String terrain, final int roll1,
			final int roll2) throws PooledException, RPGException {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		try {
			sb.append(apiProperties.getProperty("endpoint"));
			sb.append(apiProperties.getProperty("travelEventApi"));
		} catch (PooledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RestService.getInstance().callService(sb.toString(), new String[] {
				"terrain_name", terrain, "roll_1", Integer.toString(roll1),
				"roll_2", Integer.toString(roll2)
		});
		sb.returnToPool();
		sb = null;
		JsonValue root = new JsonReader().parse(
				RestService.getInstance().getResponseString());
		Json json = new Json();
		json.setSerializer(Object[].class,
				new Json.Serializer<Object[]>() {
					@Override
					public Object[] read(final Json json,
							final JsonValue jsonData,
							final Class type) {
						JsonValue e = jsonData.get("event");
						Object[] o = new Object[] {
								e.getInt("code"), e.getString("name") };
						e = null;
						return o;
					}
					@Override
					public void write(final Json json, final Object[] object,
							final Class knownType) {
						// TODO Auto-generated method stub
					}
				});
		//root = null;
		return json.fromJson(Object[].class, root.toString());
	}
	/**
	 * Gets a result from the treasure table with a call to the web service.
	 * @param wealth the wealth code
	 * @param roll the die roll
	 * @return {@link String}[]
	 */
	public String[] getTreasure(final String wealth, final int roll) {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		try {
			sb.append(apiProperties.getProperty("endpoint"));
			sb.append(apiProperties.getProperty("treasureApi"));
		} catch (PooledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RestService.getInstance().callService(sb.toString(),
				new String[] { "wealth", wealth,
						"roll", Integer.toString(roll) });
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
						return jsonData.getString("roll_result");
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
		return list;
	}
	/**
	 * Loads the hex map with a call to the web service.
	 * @return {@link Hex}[]
	 */
	public Hex[] loadHexes() {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		try {
			sb.append(apiProperties.getProperty("endpoint"));
			sb.append(apiProperties.getProperty("hexApi"));
		} catch (PooledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RestService.getInstance().callService(sb.toString(), (String[]) null);
		sb.returnToPool();
		sb = null;
		Hex[] list = new Hex[0];
		JsonValue root = new JsonReader().parse(
				RestService.getInstance().getResponseString());
		Json json = new Json();
		json.setSerializer(Hex.class, new Json.Serializer<Hex>() {
			@Override
			public Hex read(final Json json, final JsonValue jsonData,
					final Class type) {
				Hex hex = (Hex) newInstance(type);
				if (jsonData.has("name")) {
					hex.setName(jsonData.get("name").asString());
				}
				if (jsonData.has("features")) {
					JsonValue arrF = jsonData.get("features");
					JsonValue entry = arrF.child;
					for (; entry != null; entry = entry.next) {
						hex.addFeature(HexFeature.typeOf(entry.getInt("flag")));
					}
					arrF = null;
					entry = null;
				}
				if (jsonData.has("id")) {
					hex.setIndex(jsonData.getInt("id"));
				}
				try {
					if (jsonData.has("type")) {
						hex.setType(jsonData.getInt("type"));
					} else {
						hex.setType(0);
					}
				} catch (RPGException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				hex.setLocation(jsonData.getInt("x"), jsonData.getInt("y"));
				return hex;
			}
			@Override
			public void write(final Json json, final Hex object,
					final Class knownType) {
				// TODO Auto-generated method stub
			}
		});
		for (JsonValue entry = root.child; entry != null; entry = entry.next) {
			list = ArrayUtilities.getInstance().extendArray(
					json.fromJson(Hex.class, entry.toString()), list);
		}
		hex = (Hex) newInstance(Hex.class);
		hex.setName("No Man's Land");
		hex.setLocation(14, 0);
		hex.setIndex(list.length);
		list = ArrayUtilities.getInstance().extendArray(hex, list);
		for (int i = list.length - 1; i >= 0; i--) {
			if (list[i].getLocation().equals(14, 1)) {
				hl = list[i].getIndex();
			}
		}
		return list;
	}
	/**
	 * Loads the river crossings map with a call to the web service.
	 * @return {@link int}[][]
	 */
	public RiverCrossing[] loadRiverCrossings() {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		try {
			sb.append(apiProperties.getProperty("endpoint"));
			sb.append(apiProperties.getProperty("riverCrossingApi"));
		} catch (PooledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RestService.getInstance().callService(sb.toString(), (String[]) null);
		sb.returnToPool();
		sb = null;
		RiverCrossing[] list = new RiverCrossing[0];
		JsonValue root = new JsonReader().parse(
				RestService.getInstance().getResponseString());
		Json json = new Json();
		json.setSerializer(RiverCrossing.class,
				new Json.Serializer<RiverCrossing>() {
					@Override
					public RiverCrossing read(final Json json,
							final JsonValue jsonData,
							final Class type) {
						RiverCrossing r = new RiverCrossing();
						if (jsonData.has("from")) {
							r.setFrom(jsonData.getInt("from"));
						}
						if (jsonData.has("to")) {
							r.setTo(jsonData.getInt("to"));
						}
						r.setRiverName(jsonData.getString("name"));
						return r;
					}
					@Override
					public void write(final Json json,
							final RiverCrossing object,
							final Class knownType) {
						// TODO Auto-generated method stub
					}
				});
		for (JsonValue entry = root.child; entry != null; entry = entry.next) {
			list = ArrayUtilities.getInstance().extendArray(json.fromJson(
					RiverCrossing.class, entry.toString()), list);
		}
		RiverCrossing r = new RiverCrossing();
		r.setFrom(hex.getIndex());
		r.setTo(hl);
		r.setRiverName("Tragoth River");
		list = ArrayUtilities.getInstance().extendArray(r, list);
		r = null;
		return list;
	}
	/**
	 * Loads the road map with a call to the web service.
	 * @return {@link int}[][]
	 */
	public int[][] loadRoads() {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		try {
			sb.append(apiProperties.getProperty("endpoint"));
			sb.append(apiProperties.getProperty("roadApi"));
		} catch (PooledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RestService.getInstance().callService(sb.toString(), (String[]) null);
		sb.returnToPool();
		sb = null;
		int[][] list = new int[0][];
		JsonValue root = new JsonReader().parse(
				RestService.getInstance().getResponseString());
		Json json = new Json();
		json.setSerializer(int[].class, new Json.Serializer<int[]>() {
			@Override
			public int[] read(final Json json, final JsonValue jsonData,
					final Class type) {
				int[] arr = new int[2];
				arr[0] = jsonData.getInt("from");
				arr[1] = jsonData.getInt("to");
				return arr;
			}
			@Override
			public void write(final Json json, final int[] object,
					final Class knownType) {
				// TODO Auto-generated method stub
			}
		});
		for (JsonValue entry = root.child; entry != null; entry = entry.next) {
			list = ArrayUtilities.getInstance().extendArray(
					json.fromJson(int[].class, entry.toString()), list);
		}
		return list;
	}
	/**
	 * Loads the game text with a call to the web service.
	 * @param section the text section
	 * @return {@link String}
	 */
	public String loadText(final String section) {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		try {
			sb.append(apiProperties.getProperty("endpoint"));
			sb.append(apiProperties.getProperty("textApi"));
			sb.append(section);
		} catch (PooledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RestService.getInstance().callService(sb.toString(), (String[]) null);
		sb.returnToPool();
		sb = null;
		JsonValue root = new JsonReader().parse(
				RestService.getInstance().getResponseString());
		String s = null;
		if (root.size >= 0
				&& root.child.has("text")) {
			s = root.child.getString("text");
		}
		root = null;
		return s;
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
