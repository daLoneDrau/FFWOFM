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
 * @author drau
 */
public final class TFTTalent implements Serializable {
	/** the criteria parser. */
	private static CriteriaParser	criteriaParser;
	/** the list of talents. */
	private static TFTTalent[]		list;
	private static final char[][]	TALENT_CATEGORY	= {
															{ 'C', 'o', 'm',
																	'b', 'a',
																	't' },
															{ 'P', 'e', 'r',
																	's', 'o',
																	'n', 'a',
																	'l' },
															{ 'P', 'r', 'o',
																	'f', 'e',
																	's', 's',
																	'i', 'o',
																	'n', 'a',
																	'l' }
													};
	/**
	 * Adds an entry to the list of talents.
	 * @param entry the {@link TFTTalent} to be added
	 */
	public static void add(final TFTTalent entry) {
		list = ArrayUtilities.getInstance().extendArray(entry, list);
	}
	/**
	 * Determines if the list of talents has an entry by the specified name.
	 * @param name the entry name
	 * @return <tt>true</tt> if the entry was found; <tt>false</tt> otherwise
	 */
	public static boolean contains(final String name) {
		if (list == null) {
			init();
		}
		boolean found = false;
		if (list == null) {
			list = new TFTTalent[0];
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
	 * Gets all talents.
	 * @return {@link TFTTalent}[]
	 */
	public static TFTTalent[] get() {
		if (list == null) {
			init();
		}
		return list;
	}
	/**
	 * Gets a specific set of talents based on the parameters provided.
	 * @param params the filters and sorts applied to the set of talents
	 *            returned
	 * @return {@link TFTTalent}[]
	 * @throws RPGException if the params were invalid
	 */
	@SuppressWarnings("unchecked")
	public static TFTTalent[] get(final String params) throws RPGException {
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
		TFTTalent[] values = new TFTTalent[0];
		if (criteriaParser.getFilters() != null
				&& !criteriaParser.getFilters().isEmpty()) {
			values = criteriaParser.applyFilters(list, TFTTalent.class, values);
		}
		if (criteriaParser.getSorts() != null
				&& !criteriaParser.getSorts().isEmpty()) {
			Arrays.sort(values, criteriaParser);
		}
		return values;
	}
	/** Initializes values and calls the web service. */
	private static void init() {
		list = new TFTTalent[0];
		WebServiceClient.getInstance().loadTalents();
	}
	/**
	 * Gets an entry by the short name.
	 * @param name the entry name
	 * @return {@link TFTTalent}
	 */
	public static TFTTalent valueOf(final char[] name) {
		return valueOf(new String(name));
	}
	/**
	 * Gets an entry by the short name.
	 * @param name the entry name
	 * @return {@link TFTTalent}
	 */
	public static TFTTalent valueOf(final String name) {
		if (list == null) {
			init();
		}
		TFTTalent found = null;
		for (int i = list.length - 1; i >= 0; i--) {
			if (new String(list[i].shortName).equalsIgnoreCase(name)) {
				found = list[i];
				break;
			}
		}
		return found;
	}
	/**
	 * Gets an entry by id.
	 * @param refId the id
	 * @return {@link TFTTalent}
	 */
	public static TFTTalent valueOf(final int refId) {
		if (list == null) {
			init();
		}
		TFTTalent found = null;
		for (int i = list.length - 1; i >= 0; i--) {
			if (list[i].id == refId) {
				found = list[i];
				break;
			}
		}
		return found;
	}
	/**
	 * Gets the list of talents.
	 * @return {@link TFTTalent}[]
	 */
	public static TFTTalent[] values() {
		if (list == null) {
			init();
		}
		return list;
	}
	/** the talent category. */
	private int		category;
	/** the talent's description. */
	private char[]	description;
	/** the talent's full name. */
	private char[]	fullName;
	/** the talent's id. */
	private int		id;
	/** the talent's IQ cost. */
	private int		iqCost;
	/** the talent's IQ level. */
	private int		iqLevel;
	/** the talent's prerequisites. */
	private int[]	prerequisites;
	/** flag indicating pretty printing is needed for String serialization. */
	private boolean	prettyPrinting;
	/** the talent's short name. */
	private char[]	shortName;
	/** flag indicating wizard's pay a penalty to learn this talent. */
	private boolean	wizardPenalty;
	/**
	 * Gets the talent category.
	 * @return {@link int}
	 */
	public int getCategory() {
		return category;
	}
	/**
	 * Gets the talent's description.
	 * @return {@link char[]}
	 */
	public char[] getDescription() {
		return description;
	}
	/**
	 * Gets the talent's full name.
	 * @return {@link char[]}
	 */
	public char[] getFullName() {
		return fullName;
	}
	/**
	 * Gets the talent's id.
	 * @return {@link int}
	 */
	public int getId() {
		return id;
	}
	/**
	 * Gets the talent's IQ cost.
	 * @return {@link int}
	 */
	public int getIqCost() {
		return iqCost;
	}
	/**
	 * Gets the talent's IQ level.
	 * @return {@link int}
	 */
	public int getIqLevel() {
		return iqLevel;
	}
	/**
	 * Gets the talent's prerequisites.
	 * @return {@link Prerequisite[]}
	 */
	public TFTTalentPrerequisite[] getPrerequisites() {
		TFTTalentPrerequisite[] results = new TFTTalentPrerequisite[0];
		if (prerequisites != null
				&& prerequisites.length > 0) {
			try {
				PooledStringBuilder sb =
						StringBuilderPool.getInstance().getStringBuilder();
				sb.append("sort=-required|filters=");
				for (int i = prerequisites.length - 1; i >= 0; i--) {
					sb.append("id==");
					sb.append(prerequisites[i]);
					if (i - 1 >= 0) {
						sb.append(',');
					}
				}
				results = TFTTalentPrerequisite.get(sb.toString());
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
	 * Gets the talent's short name.
	 * @return {@link char[]}
	 */
	public char[] getShortName() {
		return shortName;
	}
	/**
	 * Gets the talent's wizard penalty flag.
	 * @return {@link boolean}
	 */
	public boolean hasWizardPenalty() {
		return wizardPenalty;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void read(final Json json, final JsonValue jsonData) {
		JsonValue entry = jsonData.get("category");
		if (entry.has("id")) {
			category = entry.getInt("id");
		}
		entry = null;
		description = jsonData.getString("description").toCharArray();
		fullName = jsonData.getString("full_name").toCharArray();
		if (jsonData.has("id")) {
			id = jsonData.getInt("id");
		}
		iqCost = jsonData.getInt("iq_cost");
		iqLevel = jsonData.getInt("iq_level");
		prerequisites = new int[0];
		if (jsonData.has("requirements")) {
			entry = jsonData.get("requirements");
			for (int i = 0, len = entry.size; i < len; i++) {
				if (entry.get(i).has("id")) {
					prerequisites = ArrayUtilities.getInstance().extendArray(
							entry.get(i).getInt("id"), prerequisites);
				} else {
					prerequisites = ArrayUtilities.getInstance().extendArray(
							0, prerequisites);
				}
				TFTTalentPrerequisite.add(json.fromJson(
						TFTTalentPrerequisite.class, entry.get(i).toString()));
			}
		}
		entry = null;
		shortName = jsonData.getString("short_name").toCharArray();
		wizardPenalty = jsonData.getBoolean("wizard_penalty");
	}
	/**
	 * Sets the flag indicating pretty printing is needed for String
	 * serialization.
	 * @param flag the new value to set
	 */
	public void setPrettyPrinting(final boolean flag) {
		prettyPrinting = flag;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		String s = null;
		try {
			PooledStringBuilder sb =
					StringBuilderPool.getInstance().getStringBuilder();
			if (prettyPrinting) {
				sb.append(new String(fullName));
				sb.append('\n');
				sb.append(new String(TALENT_CATEGORY[category]));
				sb.append('\n');
				sb.append("IQ Level: ");
				sb.append(iqLevel);
				sb.append("  ");
				sb.append("IQ Cost: ");
				sb.append(iqCost);
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
				sb.append(category);
				sb.append("|");
				sb.append(iqLevel);
				sb.append("|");
				sb.append(iqCost);
				sb.append("|");
				TFTTalentPrerequisite[] pList = getPrerequisites();
				if (pList != null) {
					for (int i = 0, len = pList.length; i < len; i++) {
						sb.append(pList[i]);
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
