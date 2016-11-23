package com.dalonedrow.module.tft.rpg;

import java.util.Arrays;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.dalonedrow.module.tft.systems.WebServiceClient;
import com.dalonedrow.net.RestService;
import com.dalonedrow.rpg.base.flyweights.ErrorMessage;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.utils.ArrayUtilities;
import com.dalonedrow.utils.CriteriaParser;

public class TFTTalentPrerequisite implements Serializable {
	/** flag indicating the prerequisite is for an attribute. */
	public static final int					ATTRIBUTE_PREREQUSITE	= 1;
	/** the criteria parser. */
	private static CriteriaParser			criteriaParser;
	/** the list of talent prerequisites. */
	private static TFTTalentPrerequisite[]	list;
	/** flag indicating the prerequisite is for a spell. */
	public static final int					SPELL_PREREQUSITE		= 2;
	/** flag indicating the prerequisite is for a talent. */
	public static final int					TALENT_PREREQUSITE		= 0;
	/**
	 * Adds an entry to the list of talent prerequisites.
	 * @param entry the {@link TFTTalentPrerequisite} to be added
	 */
	public static void add(final TFTTalentPrerequisite entry) {
		if (list == null) {
			init();
		}
		list = ArrayUtilities.getInstance().extendArray(entry, list);
	}
	/**
	 * Determines if the list of talent prerequisites has an entry by the
	 * specified name.
	 * @param name the entry name
	 * @return <tt>true</tt> if the entry was found; <tt>false</tt> otherwise
	 */
	public static boolean contains(final String name) {
		if (list == null) {
			init();
		}
		boolean found = false;
		for (int i = list.length - 1; i >= 0; i--) {
			if (new String(list[i].name).equalsIgnoreCase(name)) {
				found = true;
				break;
			}
		}
		return found;
	}
	/**
	 * Gets all talent prerequisites.
	 * @return {@link TFTTalentPrerequisite}[]
	 */
	public static TFTTalentPrerequisite[] get() {
		if (list == null) {
			init();
		}
		return list;
	}
	/**
	 * Gets a specific set of talent prerequisites based on the parameters
	 * provided.
	 * @param params the filters and sorts applied to the set of spells returned
	 * @return {@link TFTTalentPrerequisite}[]
	 * @throws RPGException if the params were invalid
	 */
	@SuppressWarnings("unchecked")
	public static TFTTalentPrerequisite[] get(final String params)
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
		TFTTalentPrerequisite[] values = new TFTTalentPrerequisite[0];
		if (criteriaParser.getFilters() != null
				&& !criteriaParser.getFilters().isEmpty()) {
			values = criteriaParser.applyFilters(list,
					TFTTalentPrerequisite.class, values);
		}
		if (criteriaParser.getSorts() != null
				&& !criteriaParser.getSorts().isEmpty()) {
			Arrays.sort(values, criteriaParser);
		}
		return values;
	}
	/** Initializes values and calls the web service. */
	private static void init() {
		list = new TFTTalentPrerequisite[0];
	}
	/**
	 * Gets an entry by the given name.
	 * @param name the entry name
	 * @return {@link TFTTalentPrerequisite}
	 */
	public static TFTTalentPrerequisite valueOf(final String name) {
		if (list == null) {
			init();
		}
		TFTTalentPrerequisite found = null;
		for (int i = list.length - 1; i >= 0; i--) {
			if (new String(list[i].name).equalsIgnoreCase(name)) {
				found = list[i];
				break;
			}
		}
		return found;
	}
	/**
	 * Gets an entry by its id.
	 * @param refId the id
	 * @return {@link TFTTalentPrerequisite}
	 */
	public static TFTTalentPrerequisite valueOf(final int refId) {
		if (list == null) {
			init();
		}
		TFTTalentPrerequisite found = null;
		for (int i = list.length - 1; i >= 0; i--) {
			if (list[i].id == refId) {
				found = list[i];
				break;
			}
		}
		return found;
	}
	/** the minimum attribute value. */
	private int		attributeMin;
	/** the name of the required attribute. */
	private char[]	attributeName;
	/** the id. */
	private int	id;
	/** the name. */
	private char[] name;
	/**
	 * flag indicating the prerequisite is required, needing an AND when
	 * evaluating all talent prerequisites.
	 */
	private boolean	required;
	/** the required spell's id. */
	private int	spell;
	/** the required talent's id. */
	private int	talent;
	/** the type. */
	private int		type;
	private TFTTalentPrerequisite() {
		super();
	}
	/**
	 * Gets the minimum attribute value.
	 * @return {@link int}
	 */
	public int getAttributeMin() {
		return attributeMin;
	}
	/**
	 * Gets the name of the required attribute.
	 * @return {@link char[]}
	 */
	public char[] getAttributeName() {
		return attributeName;
	}
	/**
	 * Gets the id.
	 * @return {@link int}
	 */
	public int getId() {
		return id;
	}
	/**
	 * Gets the name.
	 * @return {@link char}[]
	 */
	public char[] getName() {
		return name;
	}
	/**
	 * Gets the required spell.
	 * @return {@link TFTSpell}
	 */
	public TFTSpell getSpell() {
		return TFTSpell.valueOf(spell);
	}
	/**
	 * Gets the required talent.
	 * @return {@link TFTTalent}
	 */
	public TFTTalent getTalent() {
		return TFTTalent.valueOf(talent);
	}
	/**
	 * Gets the type.
	 * @return {@link int}
	 */
	public int getType() {
		return type;
	}
	/**
	 * Gets the flag indicating the prerequisite is required, needing an AND
	 * when evaluating all talent prerequisites.
	 * @return {@link boolean}
	 */
	public boolean isRequired() {
		return required;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void read(final Json json, final JsonValue jsonData) {
		if (jsonData.has("attribute_min")) {
			attributeMin = jsonData.getInt("attribute_min");
		}
		if (jsonData.has("attribute")) {
			JsonValue entry = jsonData.get("attribute");
			attributeName = entry.getString("name").toCharArray();
			entry = null;
		}
		if (jsonData.has("id")) {
			id = jsonData.getInt("id");
		}
		name = jsonData.getString("name").toCharArray();
		required = jsonData.getBoolean("required");
		if (jsonData.has("spell")
				&& jsonData.get("spell").has("id")) {
			spell = jsonData.get("spell").getInt("id");
		}
		if (jsonData.has("talent")
				&& jsonData.get("talent").has("id")) {
			talent = jsonData.get("talent").getInt("id");
		}
		if (jsonData.get("type").has("id")) {
			type = jsonData.get("type").getInt("id");
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
		sb.append(":");
		sb.append(new String(name));
		sb.append("]:");
		switch (type) {
		case ATTRIBUTE_PREREQUSITE:
			sb.append(new String(attributeName));
			sb.append("|");
			sb.append(attributeMin);
			break;
		case SPELL_PREREQUSITE:
			sb.append(TFTSpell.valueOf(spell));
			break;
		default:
			sb.append(TFTTalent.valueOf(talent));
			break;
		}
		sb.append("|");
		sb.append(required);
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
