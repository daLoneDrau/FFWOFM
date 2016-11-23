package com.dalonedrow.module.tft.systems;

import static com.dalonedrow.module.tft.rpg.TFTHumanoidRaces.RACE_00_HUMAN;
import static com.dalonedrow.module.tft.rpg.TFTHumanoidRaces.RACE_01_ORC;
import static com.dalonedrow.module.tft.rpg.TFTHumanoidRaces.RACE_02_ELF;
import static com.dalonedrow.module.tft.rpg.TFTHumanoidRaces.RACE_03_DWARF;
import static com.dalonedrow.module.tft.rpg.TFTHumanoidRaces.RACE_04_GOBLIN;
import static com.dalonedrow.module.tft.rpg.TFTHumanoidRaces.RACE_06_HALFLING;
import static com.dalonedrow.module.tft.rpg.TFTHumanoidRaces.RACE_09_GIANT;
import static com.dalonedrow.module.tft.rpg.TFTHumanoidRaces.RACE_10_GARGOYLE;
import static com.dalonedrow.module.tft.rpg.TFTHumanoidRaces.RACE_11_REPTILE_MEN;

import com.dalonedrow.engine.systems.base.Diceroller;
import com.dalonedrow.module.tft.rpg.TFTCharacter;
import com.dalonedrow.module.tft.rpg.TFTCharacterArchetype;
import com.dalonedrow.module.tft.rpg.TFTHumanoidRaces;
import com.dalonedrow.module.tft.rpg.TFTSpell;
import com.dalonedrow.module.tft.rpg.TFTTalent;
import com.dalonedrow.module.tft.rpg.TFTTalentPrerequisite;
import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.pooled.PooledStringBuilder;
import com.dalonedrow.pooled.StringBuilderPool;
import com.dalonedrow.rpg.base.constants.Gender;
import com.dalonedrow.rpg.base.flyweights.ErrorMessage;
import com.dalonedrow.rpg.base.flyweights.RPGException;

/**
 * The character creation wizard.
 * @author drau
 */
public final class CharacterCreationWizard {
	public static final int	DX	= 1;
	public static final int	IQ	= 2;
	public static final int	ST	= 0;
	/** the number of extra attribute points remaining. */
	private int				attributePoints;
	/** the character being created by the wizard. */
	private TFTCharacter	newCharacter;
	public CharacterCreationWizard() {
		TFTHumanoidRaces.get();
		TFTSpell.get();
		TFTTalent.get();
		TFTTalentPrerequisite.get();
		TFTCharacterArchetype.get();
	}
	/**
	 * Adjusts a character's attribute score.
	 * @param attr the attribute value
	 * @param pos flag indicating the adjustment is positive
	 * @throws PooledException should not happen
	 * @throws RPGException if an error occurs
	 */
	public void adjustAttributeScore(final int attr, final boolean pos)
			throws PooledException, RPGException {
		if (newCharacter == null
				|| newCharacter.getRace() == -1) {
			PooledStringBuilder sb =
					StringBuilderPool.getInstance().getStringBuilder();
			sb.append("Cannot adjust scores before setting race");
			RPGException ex = new RPGException(
					ErrorMessage.INVALID_OPERATION, sb.toString());
			sb.returnToPool();
			sb = null;
			throw ex;
		}
		TFTHumanoidRaces raceObj =
				TFTHumanoidRaces.valueOf(newCharacter.getRace());
		String abbr = null;
		switch (attr) {
		case ST:
			abbr = "ST";
			break;
		case DX:
			abbr = "DX";
			break;
		case IQ:
			abbr = "IQ";
			break;
		default:
			PooledStringBuilder sb =
					StringBuilderPool.getInstance().getStringBuilder();
			sb.append("Invalid attribute - ");
			sb.append(attr);
			RPGException ex = new RPGException(
					ErrorMessage.INVALID_OPERATION, sb.toString());
			sb.returnToPool();
			sb = null;
			throw ex;
		}
		float attrBase = newCharacter.getBaseAttributeScore(abbr);
		if (pos) {
			if (attributePoints == 0) {
				PooledStringBuilder sb =
						StringBuilderPool.getInstance().getStringBuilder();
				sb.append("No more extra points to distribute.");
				RPGException ex = new RPGException(
						ErrorMessage.INVALID_OPERATION, sb.toString());
				sb.returnToPool();
				sb = null;
				throw ex;
			}
			attrBase++;
		} else {
			if ((int) attrBase == raceObj.getStartingSt()) {
				PooledStringBuilder sb =
						StringBuilderPool.getInstance().getStringBuilder();
				sb.append("Cannot decrease ST below racial minimum.");
				RPGException ex = new RPGException(
						ErrorMessage.INVALID_OPERATION, sb.toString());
				sb.returnToPool();
				sb = null;
				throw ex;
			}
			attrBase--;
		}
		/*
		 * not used in wizard // check for racial constraints switch
		 * (newCharacter.getRace()) { case TFTHumanoidRaces.RACE_07_PROOTWADDLE:
		 * if (attr == IQ && pos) { PooledStringBuilder sb =
		 * StringBuilderPool.getInstance().getStringBuilder(); sb.append(
		 * "The Prootwaddle race can never increase their IQ."); RPGException ex
		 * = new RPGException( ErrorMessage.INVALID_OPERATION, sb.toString());
		 * sb.returnToPool(); sb = null; throw ex; } break; case
		 * TFTHumanoidRaces.RACE_09_GIANT: if ((attr == IQ || attr == DX) && pos
		 * && (int) attrBase > 10) { PooledStringBuilder sb =
		 * StringBuilderPool.getInstance().getStringBuilder(); sb.append(
		 * "The Giant race can never increase their IQ or DX "); sb.append(
		 * "beyond 10 other than through magical means."); RPGException ex = new
		 * RPGException( ErrorMessage.INVALID_OPERATION, sb.toString());
		 * sb.returnToPool(); sb = null; throw ex; } break; case
		 * TFTHumanoidRaces.RACE_10_GARGOYLE: if (attr == IQ && pos && (int)
		 * attrBase > 10) { PooledStringBuilder sb =
		 * StringBuilderPool.getInstance().getStringBuilder(); sb.append(
		 * "The Gargoyle race can never increase their IQ "); sb.append(
		 * "beyond 10 other than through magical means."); RPGException ex = new
		 * RPGException( ErrorMessage.INVALID_OPERATION, sb.toString());
		 * sb.returnToPool(); sb = null; throw ex; } break; }
		 */
		newCharacter.setBaseAttributeScore(abbr, attrBase);
		if (pos) {
			attributePoints--;
		} else {
			attributePoints++;
		}
	}
	/**
	 * Adjusts a character's attribute score.
	 * @param attr the attribute value
	 * @param pos flag indicating the adjustment is positive
	 * @throws PooledException should not happen
	 * @throws RPGException if an error occurs
	 */
	public void adjustAttributeScoreGodMode(final int attr, final boolean pos)
			throws PooledException, RPGException {
		if (newCharacter == null
				|| newCharacter.getRace() == -1) {
			PooledStringBuilder sb =
					StringBuilderPool.getInstance().getStringBuilder();
			sb.append("Cannot adjust scores before setting race");
			RPGException ex = new RPGException(
					ErrorMessage.INVALID_OPERATION, sb.toString());
			sb.returnToPool();
			sb = null;
			throw ex;
		}
		TFTHumanoidRaces raceObj =
				TFTHumanoidRaces.valueOf(newCharacter.getRace());
		String abbr = null;
		switch (attr) {
		case ST:
			abbr = "ST";
			break;
		case DX:
			abbr = "DX";
			break;
		case IQ:
			abbr = "IQ";
			break;
		default:
			PooledStringBuilder sb =
					StringBuilderPool.getInstance().getStringBuilder();
			sb.append("Invalid attribute - ");
			sb.append(attr);
			RPGException ex = new RPGException(
					ErrorMessage.INVALID_OPERATION, sb.toString());
			sb.returnToPool();
			sb = null;
			throw ex;
		}
		float attrBase = newCharacter.getBaseAttributeScore(abbr);
		if (pos) {
			attrBase++;
		} else {
			attrBase--;
		}
		newCharacter.setBaseAttributeScore(abbr, attrBase);
	}
	/**
	 * Assigns a random race.
	 * @throws PooledException if one occurs
	 * @throws RPGException if an invalid race is assigned
	 */
	public void assignRandomRace() throws PooledException, RPGException {
		final int three = 3, six = 6;
		final int giant = 3;
		final int elfMin = 4, elfMax = 5;
		final int goblinMin = 6, goblinMax = 7;
		final int dwarfMin = 8, dwarfMax = 9;
		final int humanMin = 10, humanMax = 11, humanMax2 = 12;
		final int orcMin = 13, orcMax = 14;
		final int halflinfMin = 15, halflingMax = 16;
		final int gargoyle = 17;
		switch (Diceroller.getInstance().rollXdY(three, six)) {
		case giant:
			setRace(RACE_09_GIANT);
			break;
		case elfMin:
		case elfMax:
			setRace(RACE_02_ELF);
			break;
		case goblinMin:
		case goblinMax:
			setRace(RACE_04_GOBLIN);
			break;
		case dwarfMin:
		case dwarfMax:
			setRace(RACE_03_DWARF);
			break;
		case humanMin:
		case humanMax:
		case humanMax2:
			setRace(RACE_00_HUMAN);
			break;
		case orcMin:
		case orcMax:
			setRace(RACE_01_ORC);
			break;
		case halflinfMin:
		case halflingMax:
			setRace(RACE_06_HALFLING);
			break;
		case gargoyle:
			setRace(RACE_10_GARGOYLE);
			break;
		default: // reptile man
			setRace(RACE_11_REPTILE_MEN);
		}
	}
	/**
	 * Assigns a talent to a character.
	 * @param spell the {@link TFTTalent}
	 * @throws RPGException if the character isn't eligible for the talent
	 */
	public void assignSpell(final TFTSpell spell) throws RPGException {
		if (newCharacter.getBaseAttributeScore("IQ") < spell.getIqLevel()) {
			throw new RPGException(ErrorMessage.INVALID_OPERATION,
					"IQ Level is too low for spell "
							+ new String(spell.getShortName()));
		}
		int spellCost = 1;
		if (!newCharacter.isWizard()) {
			spellCost *= TFTCharacter.HERO_SPELL_PENALTY;
		}
		if (newCharacter.getBaseAttributeScore("IQ")
				- newCharacter.getTotalSpellIQCost() < spellCost) {
			throw new RPGException(ErrorMessage.INVALID_OPERATION,
					"IQ Cost is too high for spell "
							+ new String(spell.getShortName())
							+ ", need to unlearn some spells");
		}
		newCharacter.learnSpell(spell.getShortName());
	}
	/**
	 * Assigns a talent to a character.
	 * @param talent the {@link TFTTalent}
	 * @throws RPGException if the character isn't eligible for the talent
	 */
	public void assignTalent(final TFTTalent talent) throws RPGException {
		if (newCharacter.getBaseAttributeScore("IQ") < talent.getIqLevel()) {
			throw new RPGException(ErrorMessage.INVALID_OPERATION,
					"IQ Level is too low for talent "
							+ new String(talent.getShortName()));
		}
		if (newCharacter.getBaseAttributeScore("IQ")
				- newCharacter.getTotalTalentIQCost() < talent.getIqCost()) {
			throw new RPGException(ErrorMessage.INVALID_OPERATION,
					"IQ Cost is too high for talent "
							+ new String(talent.getShortName())
							+ ", need to unlearn some talents");
		}
		if (!newCharacter.meetsTalentPrerequisites(talent)) {
			throw new RPGException(ErrorMessage.INVALID_OPERATION,
					"Character does not meet prerequirements for "
							+ new String(talent.getShortName()));
		}
		newCharacter.addTalent(talent.getShortName());
	}
	/**
	 * Creates a blank, empty character.
	 * @throws RPGException if an error occurs
	 */
	public void createNewCharacter() throws RPGException {
		if (newCharacter == null) {
			newCharacter = new TFTCharacter();
		}
		resetCharacter();
	}
	/**
	 * Gets the value for the extraPoints.
	 * @return {@link int}
	 */
	public int getExtraPoints() {
		return attributePoints;
	}
	/**
	 * Gets the value for the newCharacter.
	 * @return {@link TFTCharacter}
	 */
	public TFTCharacter getNewCharacter() {
		return newCharacter;
	}
	/**
	 * Removes a spell the character knows.
	 * @param spell the {@link TFTSpell}
	 */
	public void removeSpell(final TFTSpell spell) {
		newCharacter.unlearnSpell(spell.getShortName());
	}
	/**
	 * Removes a talent the character knows.
	 * @param talent the {@link TFTTalent}
	 * @throws RPGException if the talent cannot be removed because it is a
	 *             prerequisite for another
	 */
	public void removeTalent(final TFTTalent talent) throws RPGException {
		newCharacter.removeTalent(talent.getShortName());
	}
	/**
	 * Removes a talent the character knows and any talents that rely on it.
	 * @param talent the {@link TFTTalent}
	 */
	public void removeTalentCascade(final TFTTalent talent) {
		newCharacter.removeTalentCascade(talent.getShortName());
	}
	/** Resets the character. */
	private void resetCharacter() {
		newCharacter.setBaseAttributeScore("ST", 0);
		newCharacter.setBaseAttributeScore("DX", 0);
		newCharacter.setBaseAttributeScore("IQ", 0);
		newCharacter.clearAttributeModifier("ST");
		newCharacter.clearAttributeModifier("DX");
		newCharacter.clearAttributeModifier("IQ");
		newCharacter.setGender(-1);
		newCharacter.setProfession(-1);
		newCharacter.setRace(-1);
		newCharacter.clearTalents();
		attributePoints = 0;
	}
	/**
	 * Sets the character's archetype.
	 * @param archetype the archetype id
	 * @throws PooledException should never occur
	 * @throws RPGException if the archetype is invalid, or an error occurs
	 */
	public void setArchetype(final int archetype)
			throws PooledException, RPGException {
		if (newCharacter == null
				|| newCharacter.getGender() == -1) {
			PooledStringBuilder sb =
					StringBuilderPool.getInstance().getStringBuilder();
			sb.append("Cannot adjust scores before setting gender");
			RPGException ex = new RPGException(
					ErrorMessage.INVALID_OPERATION, sb.toString());
			sb.returnToPool();
			sb = null;
			throw ex;
		}
		if (newCharacter.getRace() == -1) {
			PooledStringBuilder sb =
					StringBuilderPool.getInstance().getStringBuilder();
			sb.append("Cannot adjust scores before setting race");
			RPGException ex = new RPGException(
					ErrorMessage.INVALID_OPERATION, sb.toString());
			sb.returnToPool();
			sb = null;
			throw ex;
		}
		if (archetype != newCharacter.getProfession()) {
			// get archetype
			TFTCharacterArchetype archetypeObj = null;
			switch (archetype) {
			case TFTCharacterArchetype.HERO_00_TANK:
			case TFTCharacterArchetype.HERO_01_BARBARIAN:
			case TFTCharacterArchetype.HERO_02_LEADER:
			case TFTCharacterArchetype.HERO_04_MERCENARY:
			case TFTCharacterArchetype.HERO_05_BLADEMASTER:
			case TFTCharacterArchetype.HERO_06_THIEF:
			case TFTCharacterArchetype.HERO_07_GADGETEER:
			case TFTCharacterArchetype.HERO_08_PRIEST:
			case TFTCharacterArchetype.HERO_09_SCHOLAR:
			case TFTCharacterArchetype.HERO_10_WOODSMAN:
			case TFTCharacterArchetype.HERO_11_BARD:
			case TFTCharacterArchetype.HERO_12_SOLIDUS_MAGUS:
			case TFTCharacterArchetype.HERO_13_ADEPT:
			case TFTCharacterArchetype.HERO_14_CLEPTES_MAGUS:
				archetypeObj = TFTCharacterArchetype.valueOf(archetype);
				break;
			case TFTCharacterArchetype.HERO_03_AMAZON:
				if (newCharacter.getGender() != Gender.GENDER_FEMALE) {
					throw new RPGException(ErrorMessage.INVALID_OPERATION,
							"Must be female to be an Amazon");
				}
				archetypeObj = TFTCharacterArchetype.valueOf(archetype);
				break;
			default:
				throw new RPGException(ErrorMessage.INVALID_OPERATION,
						"Invalid archetype - " + archetype);

			}
			// determine if the character is eligible for the archetype
			TFTHumanoidRaces pcRace =
					TFTHumanoidRaces.valueOf(newCharacter.getRace());
			int ptsNeeded = 0;
			if (archetypeObj.getMinimumSt() != 0
					&& archetypeObj.getMinimumSt()
							- pcRace.getStartingSt() > 0) {
				ptsNeeded =
						archetypeObj.getMinimumSt() - pcRace.getStartingSt();
			}
			if (archetypeObj.getMinimumDx() != 0
					&& archetypeObj.getMinimumDx()
							- pcRace.getStartingDx() > 0) {
				ptsNeeded +=
						archetypeObj.getMinimumDx() - pcRace.getStartingDx();
			}
			if (archetypeObj.getMinimumIq() != 0
					&& archetypeObj.getMinimumIq()
							- pcRace.getStartingIq() > 0) {
				ptsNeeded +=
						archetypeObj.getMinimumIq() - pcRace.getStartingIq();
			}
			if (ptsNeeded <= pcRace.getStartingEp()) {
				// reset the character
				int gender = newCharacter.getGender();
				int race = newCharacter.getRace();
				resetCharacter();
				setGender(gender);
				setRace(race);
				// adjust the starting attributes
				newCharacter.setProfession(archetype);
				while (newCharacter.getBaseAttributeScore("ST") < archetypeObj
						.getMinimumSt()) {
					adjustAttributeScore(ST, true);
				}
				while (newCharacter.getBaseAttributeScore("DX") < archetypeObj
						.getMinimumDx()) {
					adjustAttributeScore(DX, true);
				}
				while (newCharacter.getBaseAttributeScore("IQ") < archetypeObj
						.getMinimumIq()) {
					adjustAttributeScore(IQ, true);
				}
				// set starting spells
				TFTSpell[] spells = archetypeObj.getSpells();
				if (spells != null
						&& spells.length > 0) {

				}
				// set starting talents
				TFTTalent[] talents = archetypeObj.getTalents();
				if (talents != null
						&& talents.length > 0) {
					for (int i = 0, len = talents.length; i < len; i++) {
						assignTalent(talents[i]);
					}
				}
			} else {
				PooledStringBuilder sb =
						StringBuilderPool.getInstance().getStringBuilder();
				sb.append("Archetype ");
				sb.append(new String(archetypeObj.getShortName()));
				sb.append(" requires ST  ");
				sb.append(archetypeObj.getMinimumSt());
				sb.append(", DX  ");
				sb.append(archetypeObj.getMinimumDx());
				sb.append(", IQ  ");
				sb.append(archetypeObj.getMinimumIq());
				sb.append(".  Character needs ");
				sb.append(ptsNeeded);
				sb.append(" attribute points, only has ");
				sb.append(pcRace.getStartingEp());
				sb.append(".");
				String s = sb.toString();
				sb.returnToPool();
				sb = null;
				throw new RPGException(ErrorMessage.INVALID_OPERATION, s);
			}
		}
	}
	/**
	 * Sets the character's gender.
	 * @param gender the new gender
	 * @throws PooledException should never occur
	 * @throws RPGException if the gender is invalid, or an error occurs
	 */
	public void setGender(final int gender)
			throws PooledException, RPGException {
		if (newCharacter == null) {
			createNewCharacter();
		}
		if (gender != Gender.GENDER_FEMALE
				&& gender != Gender.GENDER_MALE) {
			PooledStringBuilder sb =
					StringBuilderPool.getInstance().getStringBuilder();
			sb.append("Invalid gender - ");
			sb.append(gender);
			sb.append(".");
			RPGException ex = new RPGException(ErrorMessage.INVALID_PARAM,
					sb.toString());
			sb.returnToPool();
			sb = null;
			throw ex;
		}
		if (gender != newCharacter.getGender()) {
			newCharacter.setGender(gender);
		}
	}
	/**
	 * Sets the character's race.
	 * @param race the race to set
	 * @throws PooledException if one occurs
	 * @throws RPGException if an invalid value is passed for race
	 */
	public void setRace(final int race) throws PooledException, RPGException {
		if (newCharacter == null
				|| newCharacter.getGender() == -1) {
			PooledStringBuilder sb =
					StringBuilderPool.getInstance().getStringBuilder();
			sb.append("Cannot set race before setting gender");
			RPGException ex = new RPGException(
					ErrorMessage.INVALID_OPERATION, sb.toString());
			sb.returnToPool();
			sb = null;
			throw ex;
		}
		if (race != newCharacter.getRace()) {
			int gender = newCharacter.getGender();
			resetCharacter();
			setGender(gender);
			newCharacter.setRace(race);
			// adjust scores
			TFTHumanoidRaces raceObj = TFTHumanoidRaces.valueOf(race);
			if (raceObj == null) {
				PooledStringBuilder sb =
						StringBuilderPool.getInstance().getStringBuilder();
				sb.append("Invalid race - ");
				sb.append(race);
				sb.append(".");
				RPGException ex = new RPGException(ErrorMessage.INVALID_PARAM,
						sb.toString());
				sb.returnToPool();
				throw ex;
			}
			newCharacter.setBaseAttributeScore("ST", raceObj.getStartingSt());
			newCharacter.setBaseAttributeScore("DX", raceObj.getStartingDx());
			newCharacter.setBaseAttributeScore("IQ", raceObj.getStartingIq());
			attributePoints = raceObj.getStartingEp();
		}
	}
}
