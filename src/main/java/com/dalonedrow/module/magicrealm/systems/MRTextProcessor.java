package com.dalonedrow.module.magicrealm.systems;

import com.dalonedrow.pooled.PooledStringBuilder;
import com.dalonedrow.pooled.StringBuilderPool;
import com.dalonedrow.rpg.base.consoleui.ConsoleLogger;
import com.dalonedrow.rpg.base.consoleui.MagicRealmInteractiveObject;
import com.dalonedrow.rpg.base.consoleui.TextProcessor;
import com.dalonedrow.utils.TextLoader;

public class MRTextProcessor extends TextProcessor {

	/**
	 * Gets the text to display when a sound is revealed.
	 * @param site the sound
	 * @param tileType the type of tile
	 * @param partyLeaderIO the party leader's IO
	 * @throws Exception if an error occurs
	 * @return {@link String
	 */
	public String getSiteTextString(final int site, final int tileType,
			final MagicRealmInteractiveObject partyLeaderIO) throws Exception {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		sb.append("site_");
		switch (site) {
		case MagicRealmConsts.SITE_10_ALTAR_1:
			sb.append("altar");
			break;
		case MagicRealmConsts.SITE_11_CAIRNS_5:
			sb.append("cairns");
			break;
		case MagicRealmConsts.SITE_12_HOARD_6:
			sb.append("hoard");
			break;
		case MagicRealmConsts.SITE_13_LAIR_3:
			sb.append("lair");
			break;
		case MagicRealmConsts.SITE_14_POOL_6:
			sb.append("pool");
			break;
		case MagicRealmConsts.SITE_15_SHRINE_4:
			sb.append("shrine");
			break;
		case MagicRealmConsts.SITE_16_STATUE_2:
			sb.append("statue");
			break;
		case MagicRealmConsts.SITE_17_VAULT_3:
			sb.append("vault");
			break;
		case MagicRealmConsts.SITE_18_LOST_CITY:
			sb.append("lost_city");
			break;
		case MagicRealmConsts.SITE_19_LOST_CASTLE:
			sb.append("lost_castle");
			break;
		default:
			throw new Exception("invalid site " + site);
		}
		String text =
				TextProcessor.getInstance().processText(
						partyLeaderIO,
						null,
						(String[]) null,
						TextLoader.getInstance().loadText("warnings.txt",
								sb.toString()));
		ConsoleLogger.getInstance().info(sb.toString());
		sb.returnToPool();
		sb = null;
		text =
				TextProcessor.getInstance().wrapText(text, defaultDlgWidth,
						false, false);
		text =
				TextProcessor.getInstance().processText(
						null,
						null,
						text,
						TextLoader.getInstance().loadText("warnings.txt",
								"discovery_dialog"));
		return text;
	}
	/**
	 * Gets the text to display when a sound is revealed.
	 * @param sound the sound
	 * @param tileType the type of tile
	 * @param partyLeaderIO the party leader's IO
	 * @throws Exception if an error occurs
	 * @return {@link String
	 */
	public String getSoundTextString(final int sound, final int tileType,
			final MagicRealmInteractiveObject partyLeaderIO) throws Exception {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		sb.append("sound_");
		switch (sound) {
		case MagicRealmConsts.SOUND_00_FLUTTER_1:
		case MagicRealmConsts.SOUND_05_FLUTTER_2:
			sb.append("flutter_");
			break;
		case MagicRealmConsts.SOUND_01_HOWL_4:
		case MagicRealmConsts.SOUND_06_HOWL_5:
			sb.append("howl_");
			break;
		case MagicRealmConsts.SOUND_02_PATTER_2:
		case MagicRealmConsts.SOUND_07_PATTER_5:
			sb.append("patter_");
			break;
		case MagicRealmConsts.SOUND_03_ROAR_4:
		case MagicRealmConsts.SOUND_08_ROAR_6:
			sb.append("roar_");
			break;
		case MagicRealmConsts.SOUND_04_SLITHER_3:
		case MagicRealmConsts.SOUND_09_SLITHER_6:
			sb.append("slither_");
			break;
		default:
			throw new Exception("invalid sound " + sound);
		}
		switch (tileType) {
		case MagicRealmConsts.TILE_TYPE_CAVE:
			sb.append('c');
			break;
		case MagicRealmConsts.TILE_TYPE_MOUNTAIN:
			sb.append('m');
			break;
		case MagicRealmConsts.TILE_TYPE_VALLEY:
			sb.append('v');
			break;
		case MagicRealmConsts.TILE_TYPE_WOODS:
			sb.append('w');
			break;
		default:
			throw new Exception("invalid tile type " + tileType);
		}
		String text =
				TextProcessor.getInstance().processText(
						partyLeaderIO,
						null,
						(String[]) null,
						TextLoader.getInstance().loadText("warnings.txt",
								sb.toString()));
		ConsoleLogger.getInstance().info(sb.toString());
		sb.returnToPool();
		sb = null;
		text =
				TextProcessor.getInstance().wrapText(text, defaultDlgWidth,
						false, false);
		text =
				TextProcessor.getInstance().processText(
						null,
						null,
						text,
						TextLoader.getInstance().loadText("warnings.txt",
								"discovery_dialog"));
		return text;
	}
	/**
	 * Gets the text to display when a warning is revealed.
	 * @param warning the warning
	 * @param tileType the type of tile
	 * @param partyLeaderIO the party leader's IO
	 * @throws Exception if an error occurs
	 * @return {@link String
	 */
	public String getWarningTextString(final int warning, final int tileType,
			final MagicRealmInteractiveObject partyLeaderIO) throws Exception {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		sb.append("warning_");
		switch (warning) {
		case MagicRealmConsts.WARNING_BONES:
			sb.append("bones_");
			break;
		case MagicRealmConsts.WARNING_DANK:
			sb.append("dank_");
			break;
		case MagicRealmConsts.WARNING_RUINS:
			sb.append("ruins_");
			break;
		case MagicRealmConsts.WARNING_SMOKE:
			sb.append("smoke_");
			break;
		case MagicRealmConsts.WARNING_STINK:
			sb.append("stink_");
			break;
		default:
			throw new Exception("invalid warning " + warning);
		}
		switch (tileType) {
		case MagicRealmConsts.TILE_TYPE_CAVE:
			sb.append('c');
			break;
		case MagicRealmConsts.TILE_TYPE_MOUNTAIN:
			sb.append('m');
			break;
		case MagicRealmConsts.TILE_TYPE_VALLEY:
			sb.append('v');
			break;
		case MagicRealmConsts.TILE_TYPE_WOODS:
			sb.append('w');
			break;
		default:
			throw new Exception("invalid tile type " + tileType);
		}
		String text =
				TextProcessor.getInstance().processText(
						partyLeaderIO,
						null,
						(String[]) null,
						TextLoader.getInstance().loadText("warnings.txt",
								sb.toString()));
		ConsoleLogger.getInstance().info(sb.toString());
		sb.returnToPool();
		sb = null;
		text =
				TextProcessor.getInstance().wrapText(text, defaultDlgWidth,
						false, false);
		text =
				TextProcessor.getInstance().processText(
						null,
						null,
						text,
						TextLoader.getInstance().loadText("warnings.txt",
								"discovery_dialog"));
		return text;
	}
}
