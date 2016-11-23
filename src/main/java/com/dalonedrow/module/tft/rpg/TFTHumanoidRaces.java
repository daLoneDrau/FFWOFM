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
public final class TFTHumanoidRaces implements Serializable {
	/** the criteria parser. */
	private static CriteriaParser		criteriaParser;
	/** the list of spells. */
	private static TFTHumanoidRaces[]	list;
	/** the human race. */
	public static final int				RACE_00_HUMAN		= 0;
	/** the orc race. */
	public static final int				RACE_01_ORC			= 1;
	/** the elf race. */
	public static final int				RACE_02_ELF			= 2;
	/** the dwarf race. */
	public static final int				RACE_03_DWARF		= 3;
	/** the goblin race. */
	public static final int				RACE_04_GOBLIN		= 4;
	/** the hobgoblin race. */
	public static final int				RACE_05_HOBGOBLIN	= 5;
	/** the halfling race. */
	public static final int				RACE_06_HALFLING	= 6;
	/** the prootwaddle race. */
	public static final int				RACE_07_PROOTWADDLE	= 7;
	/** the centaur race. */
	public static final int				RACE_08_CENTAUR		= 8;
	/** the giant race. */
	public static final int				RACE_09_GIANT		= 9;
	/** the gargoyle race. */
	public static final int				RACE_10_GARGOYLE	= 10;
	/** the reptile men race. */
	public static final int				RACE_11_REPTILE_MEN	= 11;
	/** no race. */
	public static final int				RACE_UNDEFINED		= -1;
	/**
	 * Adds an entry to the list of humanoid races.
	 * @param entry the {@link TFTHumanoidRaces} to be added
	 */
	public static void add(final TFTHumanoidRaces entry) {
		if (!contains(new String(entry.shortName))) {
			list = ArrayUtilities.getInstance().extendArray(entry, list);
		}
	}
	/**
	 * Determines if the list of humanoid races has an entry by the specified
	 * name.
	 * @param name the entry name
	 * @return <tt>true</tt> if the entry was found; <tt>false</tt> otherwise
	 */
	public static boolean contains(final String name) {
		if (list == null) {
			init();
		}
		boolean found = false;
		for (int i = list.length - 1; i >= 0; i--) {
			if (new String(list[i].shortName).equalsIgnoreCase(name)) {
				found = true;
				break;
			}
		}
		return found;
	}
	/**
	 * Gets all humanoid races.
	 * @return {@link TFTHumanoidRaces}[]
	 */
	public static TFTHumanoidRaces[] get() {
		if (list == null) {
			init();
		}
		return list;
	}
	/**
	 * Gets a specific set of humanoid races based on the parameters provided.
	 * @param params the filters and sorts applied to the set of spells returned
	 * @return {@link TFTHumanoidRaces}[]
	 * @throws RPGException if the params were invalid
	 */
	@SuppressWarnings("unchecked")
	public static TFTHumanoidRaces[] get(final String params)
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
		TFTHumanoidRaces[] values = new TFTHumanoidRaces[0];
		if (criteriaParser.getFilters() != null
				&& !criteriaParser.getFilters().isEmpty()) {
			values =
					criteriaParser.applyFilters(list, TFTHumanoidRaces.class,
							values);
		}
		if (criteriaParser.getSorts() != null
				&& !criteriaParser.getSorts().isEmpty()) {
			Arrays.sort(values, criteriaParser);
		}
		return values;
	}
	/** Initializes values and calls the web service. */
	private static void init() {
		list = new TFTHumanoidRaces[0];
		WebServiceClient.getInstance().loadHumanoidRaces();
	}
	/**
	 * Gets an entry by the given id.
	 * @param id the entry id
	 * @return {@link TFTHumanoidRaces}
	 */
	public static TFTHumanoidRaces valueOf(final int id) {
		if (list == null) {
			init();
		}
		TFTHumanoidRaces found = null;
		for (int i = list.length - 1; i >= 0; i--) {
			if (list[i].id == id) {
				found = list[i];
				break;
			}
		}
		return found;
	}
	/**
	 * Gets the list of races.
	 * @return {@link TFTHumanoidRaces}[]
	 */
	public static TFTHumanoidRaces[] values() {
		if (list == null) {
			init();
		}
		return list;
	}
	/** the race description. */
	private char[]	description;
	/** the race's full name. */
	private char[]	fullName;
	/** the race's id. */
	private int		id;
	/** the race's short name. */
	private char[]	shortName;
	/** the race's starting DX. */
	private int		startingDx;
	/** the race's starting EP. */
	private int		startingEp;
	/** the race's starting IQ. */
	private int		startingIq;
	/** the race's starting ST. */
	private int		startingSt;
	/** Hidden constructor. */
	private TFTHumanoidRaces() {
		super();
		// TODO Auto-generated constructor stub
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
	 * Gets the value for the shortName.
	 * @return {@link char[]}
	 */
	public char[] getShortName() {
		return shortName;
	}
	/**
	 * Gets the value for the startingDx.
	 * @return {@link int}
	 */
	public int getStartingDx() {
		return startingDx;
	}
	/**
	 * Gets the value for the startingEp.
	 * @return {@link int}
	 */
	public int getStartingEp() {
		return startingEp;
	}
	/**
	 * Gets the value for the startingIq.
	 * @return {@link int}
	 */
	public int getStartingIq() {
		return startingIq;
	}
	/**
	 * Gets the value for the startingSt.
	 * @return {@link int}
	 */
	public int getStartingSt() {
		return startingSt;
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
		shortName = jsonData.getString("short_name").toCharArray();
		startingDx = jsonData.getInt("starting_dx");
		if (jsonData.has("starting_ep")) {
			startingEp = jsonData.getInt("starting_ep");
		}
		startingIq = jsonData.getInt("starting_iq");
		startingSt = jsonData.getInt("starting_st");
	}
	/**
	 * Sets the value of the description.
	 * @param val the new value to set
	 */
	public void setDescription(final char[] val) {
		description = val;
	}
	/**
	 * Sets the value of the fullName.
	 * @param val the new value to set
	 */
	public void setFullName(final char[] val) {
		fullName = val;
	}
	/**
	 * Sets the value of the id.
	 * @param val the new value to set
	 */
	public void setId(final int val) {
		id = val;
	}
	/**
	 * Sets the value of the shortName.
	 * @param val the new value to set
	 */
	public void setShortName(final char[] val) {
		shortName = val;
	}
	/**
	 * Sets the value of the startingDx.
	 * @param val the new value to set
	 */
	public void setStartingDx(final int val) {
		startingDx = val;
	}
	/**
	 * Sets the value of the startingEp.
	 * @param val the new value to set
	 */
	public void setStartingEp(final int val) {
		startingEp = val;
	}
	/**
	 * Sets the value of the startingIq.
	 * @param val the new value to set
	 */
	public void setStartingIq(final int val) {
		startingIq = val;
	}
	/**
	 * Sets the value of the startingSt.
	 * @param val the new value to set
	 */
	public void setStartingSt(final int val) {
		startingSt = val;
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
		sb.append(startingSt);
		sb.append("|");
		sb.append(startingDx);
		sb.append("|");
		sb.append(startingIq);
		sb.append("|");
		sb.append(startingEp);
		return sb.toString();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void write(final Json json) {
		// TODO Auto-generated method stub}
	}
}
