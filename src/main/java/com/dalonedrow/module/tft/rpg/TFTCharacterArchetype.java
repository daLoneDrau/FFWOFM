package com.dalonedrow.module.tft.rpg;

import java.util.Arrays;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;
import com.dalonedrow.module.tft.systems.WebServiceClient;
import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.pooled.PooledStringBuilder;
import com.dalonedrow.pooled.StringBuilderPool;
import com.dalonedrow.rpg.base.flyweights.ErrorMessage;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.utils.ArrayUtilities;
import com.dalonedrow.utils.CriteriaParser;

/**
 * 
 * @author drau
 *
 */
public final class TFTCharacterArchetype implements Serializable {
	/** the criteria parser. */
	private static CriteriaParser			criteriaParser;
	/** archetype Fighter - TANK. */
	public static final int					HERO_00_TANK			= 0;
	/** archetype Fighter - BARBARIAN. */
	public static final int					HERO_01_BARBARIAN		= 1;
	/** archetype Fighter - LEADER. */
	public static final int					HERO_02_LEADER			= 2;
	/** archetype Fighter - AMAZON. */
	public static final int					HERO_03_AMAZON			= 3;
	/** archetype Fighter - MERCENARY. */
	public static final int					HERO_04_MERCENARY		= 4;
	/** archetype Fighter - BLADEMASTER. */
	public static final int					HERO_05_BLADEMASTER		= 5;
	/** archetype Adventurer - THIEF. */
	public static final int					HERO_06_THIEF			= 6;
	/** archetype Adventurer - GADGETEER. */
	public static final int					HERO_07_GADGETEER		= 7;
	/** archetype Adventurer - PRIEST. */
	public static final int					HERO_08_PRIEST			= 8;
	/** archetype Adventurer - SCHOLAR. */
	public static final int					HERO_09_SCHOLAR			= 9;
	/** archetype Adventurer - WOODSMAN. */
	public static final int					HERO_10_WOODSMAN		= 10;
	/** archetype Adventurer - BARD. */
	public static final int					HERO_11_BARD			= 11;
	/** archetype Wizard - SOLIDUS_MAGUS. */
	public static final int					HERO_12_SOLIDUS_MAGUS	= 12;
	/** archetype Wizard - ADEPT. */
	public static final int					HERO_13_ADEPT			= 13;
	/** archetype Wizard - CLEPTES_MAGUS. */
	public static final int					HERO_14_CLEPTES_MAGUS	= 14;
	/** the list of archetypes. */
	private static TFTCharacterArchetype[]	list;
	/** the list of character types. */
	public static final char[][]			TYPES					= {
			"Fighter".toCharArray(),
			"Adventurer".toCharArray(),
			"Wizard".toCharArray()
	};
	/**
	 * Adds an entry to the list of archetypes.
	 * @param entry the {@link TFTCharacterArchetype} to be added
	 */
	public static void add(final TFTCharacterArchetype entry) {
		list = ArrayUtilities.getInstance().extendArray(entry, list);
	}
	/**
	 * Determines if the list of archetypes has an entry by the specified name.
	 * @param name the entry name
	 * @return <tt>true</tt> if the entry was found; <tt>false</tt> otherwise
	 */
	public static boolean contains(final String name) {
		if (list == null) {
			init();
		}
		boolean found = false;
		if (list == null) {
			list = new TFTCharacterArchetype[0];
		}
		for (int i = list.length - 1; i >= 0; i--) {
			if (new String(list[i].shortName).equalsIgnoreCase(name)) {
				found = true;
				break;
			}
		}
		return found;
	}
	/**
	 * Gets all archetypes.
	 * @return {@link TFTCharacterArchetype}[]
	 */
	public static TFTCharacterArchetype[] get() {
		if (list == null) {
			init();
		}
		return list;
	}
	/**
	 * Gets a specific set of archetypes based on the parameters provided.
	 * @param params the filters and sorts applied to the set of archetypes
	 *            returned
	 * @return {@link TFTCharacterArchetype}[]
	 * @throws RPGException if the params were invalid
	 */
	@SuppressWarnings("unchecked")
	public static TFTCharacterArchetype[] get(final String params)
			throws RPGException {
		if (list == null) {
			init();
		}
		if (criteriaParser == null) {
			criteriaParser = new CriteriaParser();
		}
		criteriaParser.setFilters(null);
		criteriaParser.setSorts(null);
		String[] paramList = params.split("\\|");
		for (int i = paramList.length - 1; i >= 0; i--) {
			int eq = paramList[i].indexOf('=');
			if (eq < 0) {
				throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT,
						"get params were invalid - " + params);
			}
			String key = paramList[i].substring(0, eq);
			String value = paramList[i].substring(eq + 1);
			if (key.equalsIgnoreCase("sort")) {
				criteriaParser.parseSorts(value);
			} else if (key.equalsIgnoreCase("filters")) {
				criteriaParser.parseFilters(value);
			}
		}
		TFTCharacterArchetype[] values = new TFTCharacterArchetype[0];
		if (criteriaParser.getFilters() != null
				&& !criteriaParser.getFilters().isEmpty()) {
			values = criteriaParser.applyFilters(
					list, TFTCharacterArchetype.class, values);
		}
		if (criteriaParser.getSorts() != null
				&& !criteriaParser.getSorts().isEmpty()) {
			Arrays.sort(values, criteriaParser);
		}
		return values;
	}
	/** Initializes values and calls the web service. */
	private static void init() {
		list = new TFTCharacterArchetype[0];
		WebServiceClient.getInstance().loadArchetypes();
	}
	/**
	 * Gets an entry by the profession id.
	 * @param refId the id
	 * @return {@link TFTCharacterArchetype}
	 */
	public static TFTCharacterArchetype valueOf(final int refId) {
		if (list == null) {
			init();
		}
		TFTCharacterArchetype found = null;
		for (int i = list.length - 1; i >= 0; i--) {
			if (list[i].id == refId) {
				found = list[i];
				break;
			}
		}
		return found;
	}
	/**
	 * Gets an entry by the short name.
	 * @param name the entry name
	 * @return {@link TFTCharacterArchetype}
	 */
	public static TFTCharacterArchetype valueOf(final String name) {
		if (list == null) {
			init();
		}
		TFTCharacterArchetype found = null;
		for (int i = list.length - 1; i >= 0; i--) {
			if (new String(list[i].shortName).equalsIgnoreCase(name)) {
				found = list[i];
				break;
			}
		}
		return found;
	}
	/**
	 * Gets the list of races.
	 * @return {@link TFTCharacterArchetype}[]
	 */
	public static TFTCharacterArchetype[] values() {
		if (list == null) {
			init();
		}
		return list;
	}
	/** the archetype category. */
	private int		characterType;
	/** the archetype's description. */
	private char[]	description;
	/** the archetype's full name. */
	private char[]	fullName;
	/** the archetype's id. */
	private int		id;
	/** the archetype's minimum DX. */
	private int		minimumDx;
	/** the archetype's minimum IQ. */
	private int		minimumIq;
	/** the archetype's minimum ST. */
	private int		minimumSt;
	/** flag indicating pretty printing has been turned on. */
	private boolean	prettyPrinting;
	/** the archetype's short name. */
	private char[]	shortName;
	/** the archetype's base spells. */
	private int[]	spells;
	/** the archetype's base talents. */
	private int[]	talents;
	/** Hidden constructor. */
	private TFTCharacterArchetype() {
		spells = new int[0];
		talents = new int[0];
	}
	/**
	 * Gets the value for the characterType.
	 * @return {@link int}
	 */
	public int getCharacterType() {
		return characterType;
	}
	/**
	 * Gets the value for the description.
	 * @return {@link char[]}
	 */
	public char[] getDescription() {
		return description;
	}
	/**
	 * Gets the value for the fullName.
	 * @return {@link char[]}
	 */
	public char[] getFullName() {
		return fullName;
	}
	/**
	 * Gets the value for the id.
	 * @return {@link int}
	 */
	public int getId() {
		return id;
	}
	/**
	 * Gets the value for the minimumDx.
	 * @return {@link int}
	 */
	public int getMinimumDx() {
		return minimumDx;
	}
	/**
	 * Gets the value for the minimumIq.
	 * @return {@link int}
	 */
	public int getMinimumIq() {
		return minimumIq;
	}
	/**
	 * Gets the value for the minimumSt.
	 * @return {@link int}
	 */
	public int getMinimumSt() {
		return minimumSt;
	}
	/**
	 * Gets the value for the shortName.
	 * @return {@link char[]}
	 */
	public char[] getShortName() {
		return shortName;
	}
	/**
	 * Gets the value for the spells.
	 * @return {@link TFTSpell[]}
	 */
	public TFTSpell[] getSpells() {
		TFTSpell[] results = null;
		if (spells != null
				&& spells.length > 0) {
			try {
				PooledStringBuilder sb =
						StringBuilderPool.getInstance().getStringBuilder();
				sb.append("sort=-iqLevel|filters=");
				for (int i = spells.length - 1; i >= 0; i--) {
					sb.append("id==");
					sb.append(spells[i]);
					if (i - 1 >= 0) {
						sb.append(',');
					}
				}
				results = TFTSpell.get(sb.toString());
				sb.returnToPool();
				sb = null;
			} catch (PooledException e) {
				e.printStackTrace();
			} catch (RPGException e) {
				e.printStackTrace();
			}
		}
		return results;
	}
	/**
	 * Gets the value for the archetypes.
	 * @return {@link TFTTalent[]}
	 */
	public TFTTalent[] getTalents() {
		TFTTalent[] results = null;
		if (talents != null
				&& talents.length > 0) {
			try {
				PooledStringBuilder sb =
						StringBuilderPool.getInstance().getStringBuilder();
				sb.append("sort=-iqLevel|filters=");
				for (int i = talents.length - 1; i >= 0; i--) {
					sb.append("id==");
					sb.append(talents[i]);
					if (i - 1 >= 0) {
						sb.append(',');
					}
				}
				results = TFTTalent.get(sb.toString());
				sb.returnToPool();
				sb = null;
			} catch (PooledException e) {
				e.printStackTrace();
			} catch (RPGException e) {
				e.printStackTrace();
			}
		}
		return results;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void read(final Json json, final JsonValue jsonData) {
		if (jsonData.get("type").has("id")) {
			characterType = jsonData.get("type").getInt("id");
		}
		description = jsonData.getString("description").toCharArray();
		fullName = jsonData.getString("full_name").toCharArray();
		if (jsonData.has("id")) {
			id = jsonData.getInt("id");
		}
		if (jsonData.has("minimum_dx")) {
			minimumDx = jsonData.getInt("minimum_dx");
		}
		if (jsonData.has("minimum_iq")) {
			minimumIq = jsonData.getInt("minimum_iq");
		}
		if (jsonData.has("minimum_st")) {
			minimumSt = jsonData.getInt("minimum_st");
		}
		shortName = jsonData.getString("short_name").toCharArray();
		if (jsonData.has("base_spells")) {
			JsonValue entry = jsonData.get("base_spells");
			for (int i = 0, len = entry.size; i < len; i++) {
				if (entry.get(i).has("id")) {
					spells = ArrayUtilities.getInstance().extendArray(
							entry.get(i).getInt("id"), spells);
				}
			}
		}
		if (jsonData.has("base_talents")) {
			JsonValue entry = jsonData.get("base_talents");
			for (int i = 0, len = entry.size; i < len; i++) {
				if (entry.get(i).has("id")) {
					talents = ArrayUtilities.getInstance().extendArray(
							entry.get(i).getInt("id"), talents);
				}
			}
		}
	}
	/**
	 * Sets the value of the flag indicating pretty printing has been turned on.
	 * @param flag the new value to set
	 */
	public void setPrettyPrinting(final boolean flag) {
		prettyPrinting = flag;
	}
	/**
	 * {@inheritDoc}
	 * @param prettyPrinting
	 */
	@Override
	public String toString() {
		String s = null;
		try {
			PooledStringBuilder sb =
					StringBuilderPool.getInstance().getStringBuilder();
			if (prettyPrinting) {
				sb.append(new String(TYPES[characterType]));
				sb.append(" (");
				sb.append(new String(fullName));
				sb.append(')');
				sb.append('\n');
				prettyPrinting = false;
			} else {
				sb.append("[");
				sb.append(id);
				sb.append("]:");
				sb.append(new String(shortName));
				sb.append("|");
				sb.append(new String(fullName));
				sb.append("|");
				sb.append(new String(description));
				sb.append("|");
				sb.append(minimumSt);
				sb.append("|");
				sb.append(minimumDx);
				sb.append("|");
				sb.append(minimumIq);
				sb.append("|");
				TFTSpell[] sList = getSpells();
				if (sList != null) {
					for (int i = 0, len = sList.length; i < len; i++) {
						sb.append(sList[i]);
						if (i + 1 < len) {
							sb.append('\n');
						}
					}
				}
				sb.append("|");
				TFTTalent[] tList = getTalents();
				if (tList != null) {
					for (int i = 0, len = tList.length; i < len; i++) {
						sb.append(tList[i]);
						if (i + 1 < len) {
							sb.append('\n');
						}
					}
				}
			}
			s = sb.toString();
			sb.returnToPool();
			sb = null;
		} catch (PooledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	@Override
	public void write(final Json json) {
		// TODO Auto-generated method stub

	}
}
