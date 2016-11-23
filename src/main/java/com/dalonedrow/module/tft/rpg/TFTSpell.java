package com.dalonedrow.module.tft.rpg;

import java.util.Arrays;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;
import com.dalonedrow.module.tft.systems.WebServiceClient;
import com.dalonedrow.rpg.base.flyweights.ErrorMessage;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.utils.ArrayUtilities;
import com.dalonedrow.utils.CriteriaParser;

/**
 * @author drau
 */
public final class TFTSpell implements Serializable {
	/** the criteria parser. */
	private static CriteriaParser	criteriaParser;
	/** the list of spells. */
	private static TFTSpell[]		list;
	/**
	 * Adds an entry to the list of spells.
	 * @param entry the {@link TFTSpell} to be added
	 */
	public static void add(final TFTSpell entry) {
		list = ArrayUtilities.getInstance().extendArray(entry, list);
	}
	/**
	 * Determines if the list of spells has an entry by the specified name.
	 * @param name the entry name
	 * @return <tt>true</tt> if the entry was found; <tt>false</tt> otherwise
	 */
	public static boolean contains(final String name) {
		if (list == null) {
			init();
		}
		boolean found = false;
		if (list == null) {
			list = new TFTSpell[0];
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
	 * Gets all spells.
	 * @return {@link TFTSpell}[]
	 */
	public static TFTSpell[] get() {
		if (list == null) {
			init();
		}
		return list;
	}
	/**
	 * Gets a specific set of spells based on the parameters provided.
	 * @param params the filters and sorts applied to the set of spells returned
	 * @return {@link TFTSpell}[]
	 * @throws RPGException if the params were invalid
	 */
	@SuppressWarnings("unchecked")
	public static TFTSpell[] get(final String params) throws RPGException {
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
		TFTSpell[] values = new TFTSpell[0];
		if (criteriaParser.getFilters() != null
				&& !criteriaParser.getFilters().isEmpty()) {
			values = criteriaParser.applyFilters(list, TFTSpell.class, values);
		}
		if (criteriaParser.getSorts() != null
				&& !criteriaParser.getSorts().isEmpty()) {
			Arrays.sort(values, criteriaParser);
		}
		return values;
	}
	/** Initializes values and calls the web service. */
	private static void init() {
		list = new TFTSpell[0];
		WebServiceClient.getInstance().loadSpells();
	}
	/**
	 * Gets an entry by the short name.
	 * @param name the entry name
	 * @return {@link TFTSpell}
	 */
	public static TFTSpell valueOf(final char[] name) {
		return valueOf(new String(name));
	}
	/**
	 * Gets an entry by the id.
	 * @param refId the entry name
	 * @return {@link TFTSpell}
	 */
	public static TFTSpell valueOf(final int refId) {
		if (list == null) {
			init();
		}
		TFTSpell found = null;
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
	 * @param name the short name
	 * @return {@link TFTSpell}
	 */
	public static TFTSpell valueOf(final String name) {
		if (list == null) {
			init();
		}
		TFTSpell found = null;
		for (int i = list.length - 1; i >= 0; i--) {
			if (new String(list[i].shortName).equalsIgnoreCase(name)) {
				found = list[i];
				break;
			}
		}
		return found;
	}
	/** the spell description. */
	private char[]	description;
	/** the spell's full name. */
	private char[]	fullName;
	/** the spell's id. */
	private int		id;
	/** the spell's IQ Level. */
	private int		iqLevel;
	/** the spell's maintenance cost. */
	private int		maintenanceCost;
	/** the spell's short name. */
	private char[]	shortName;
	/** the spell's duration. */
	private int		spellDuration;
	/** the spell's type. */
	private int		spellType;
	/** the spell's ST cost. */
	private int		stCost;
	/** Hidden constructor. */
	private TFTSpell() {
		super();
	}
	/**
	 * Gets the value for the description.
	 * @return {@link char}[]
	 */
	public char[] getDescription() {
		return description;
	}
	/**
	 * Gets the value for the fullName.
	 * @return {@link char}[]
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
	 * Gets the value for the iqLevel.
	 * @return {@link int}
	 */
	public int getIqLevel() {
		return iqLevel;
	}
	/**
	 * Gets the value for the maintenanceCost.
	 * @return {@link int}
	 */
	public int getMaintenanceCost() {
		return maintenanceCost;
	}
	/**
	 * Gets the value for the shortName.
	 * @return {@link char}[]
	 */
	public char[] getShortName() {
		return shortName;
	}
	/**
	 * Gets the value for the spellDuration.
	 * @return {@link int}
	 */
	public int getSpellDuration() {
		return spellDuration;
	}
	/**
	 * Gets the value for the spellType.
	 * @return {@link int}
	 */
	public int getSpellType() {
		return spellType;
	}
	/**
	 * Gets the value for the stCost.
	 * @return {@link int}
	 */
	public int getStCost() {
		return stCost;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void read(final Json json, final JsonValue jsonData) {
		description = jsonData.getString("description").toCharArray();
		fullName = jsonData.getString("full_name").toCharArray();
		if (jsonData.has("id")) {
			id = jsonData.getInt("id");
		}
		iqLevel = jsonData.getInt("iq_level");
		if (jsonData.has("maintenance_cost")) {
			maintenanceCost = jsonData.getInt("maintenance_cost");
		}
		shortName = jsonData.getString("short_name").toCharArray();
		if (jsonData.has("spell_duration")) {
			spellDuration = jsonData.getInt("spell_duration");
		}
		JsonValue entry = jsonData.get("type");
		if (entry.has("id")) {
			spellType = entry.getInt("id");
		}
		entry = null;
		if (jsonData.has("st_cost")) {
			stCost = jsonData.getInt("st_cost");
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append(id);
		sb.append("]:");
		sb.append(new String(shortName));
		sb.append("|");
		sb.append(new String(fullName));
		sb.append("|");
		sb.append(new String(description));
		sb.append("|");
		sb.append(spellType);
		sb.append("|");
		sb.append(iqLevel);
		sb.append("|");
		sb.append(stCost);
		return sb.toString();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(final Json json) {
		// TODO Auto-generated method stub

	}
}
