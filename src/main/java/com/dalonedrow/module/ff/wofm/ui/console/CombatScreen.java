package com.dalonedrow.module.ff.wofm.ui.console;

import java.util.ArrayList;
import java.util.List;

import com.dalonedrow.engine.systems.base.Diceroller;
import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.engine.systems.base.JOGLErrorHandler;
import com.dalonedrow.module.ff.constants.FFIo;
import com.dalonedrow.module.ff.rpg.FFInteractiveObject;
import com.dalonedrow.module.ff.systems.wofm.FFWoFMActionHelper;
import com.dalonedrow.module.ff.systems.wofm.FFWoFMController;
import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.pooled.PooledStringBuilder;
import com.dalonedrow.pooled.StringBuilderPool;
import com.dalonedrow.rpg.base.consoleui.ConsoleView;
import com.dalonedrow.rpg.base.consoleui.InputProcessor;
import com.dalonedrow.rpg.base.consoleui.OutputEvent;
import com.dalonedrow.rpg.base.consoleui.Panel;
import com.dalonedrow.rpg.base.consoleui.TextProcessor;
import com.dalonedrow.rpg.base.constants.Dice;
import com.dalonedrow.rpg.base.constants.EquipmentGlobals;
import com.dalonedrow.rpg.base.constants.IoGlobals;
import com.dalonedrow.rpg.base.flyweights.ErrorMessage;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.systems.WebServiceClient;
import com.dalonedrow.utils.ArrayUtilities;
import com.dalonedrow.utils.Watchable;
import com.dalonedrow.utils.Watcher;

/**
 * @author drau
 */
@SuppressWarnings("unchecked")
public final class CombatScreen extends ConsoleView implements Watcher {
	/** the one and only instance of the <code>CombatScreen</code> class. */
	private static CombatScreen	instance;
	/** the damage from a lucky attack. */
	private static final int	LUCKY_ATTACK	= 2;
	/** the damage from a lucky defense. */
	private static final int	LUCKY_DEFENSE	= -1;
	/** display 10 lines at most for the action. */
	private static final int	MAX_LINES		= 10;
	/** the damage from an unlucky attack. */
	private static final int	UNLUCKY_ATTACK	= -1;
	/** the damage from an unlucky defense. */
	private static final int	UNLUCKY_DEFENSE	= 1;
	/**
	 * Gives access to the singleton instance of {@link CombatScreen}.
	 * @return {@link CombatScreen}
	 */
	public static CombatScreen getInstance() {
		if (CombatScreen.instance == null) {
			CombatScreen.instance = new CombatScreen();
		}
		return CombatScreen.instance;
	}
	/** the panel displaying the room view. */
	private final Panel						actionPanel;
	/** the panel displaying the enemy stats. */
	private final Panel						enmyStatsPanel;
	/** the list of mobs attacking. */
	private final List<FFInteractiveObject>	mobs;
	/** the panel displaying the actions. */
	private final Panel						optionsPanel;
	/** the panel displaying the player stats. */
	private final Panel						plyrStatsPanel;
	/** the current round. */
	private int								round;
	/** the width of the stats table, with borders. */
	private final int						statsTableWidth;
	/** the combat text. */
	private String[]						text;
	/** Hidden constructor. */
	private CombatScreen() {
		final int plrStatsHt = 5, optionsHt = 6;
		statsTableWidth = "Stamina: 99/99".length()
				+ FFUIGlobals.BORDER_PADDING;
		actionPanel = new Panel(
				FFUIGlobals.SCREEN_WIDTH - statsTableWidth, // width
				false, // borders
				MAX_LINES); // height
		plyrStatsPanel = new Panel(
				statsTableWidth, // width
				true, // borders
				plrStatsHt, // height
				"", // content
				"STATUS"); // title
		enmyStatsPanel = new Panel(
				statsTableWidth, // width
				true, // borders
				MAX_LINES, // height
				"", // content
				"ENEMIES"); // title
		optionsPanel = new Panel(
				FFUIGlobals.SCREEN_WIDTH - statsTableWidth - 1, // width
				true, // borders
				optionsHt, // height
				"", // content
				"COMBAT"); // title
		text = new String[0];
		mobs = new ArrayList<FFInteractiveObject>();
	}
	/**
	 * Processes user input to go to the next screen.
	 * @param s not used
	 */
	public void actionProcessInput(final String s) {
		try {
			FFWoFMActionHelper.getInstance().processInput(s);
		} catch (Exception e) {
			JOGLErrorHandler.getInstance().fatalError(e);
		}
	}
	@Override
	public void addErrorMessage(final String msg) {
		try {
			text = ArrayUtilities.getInstance().extendArray(
					TextProcessor.getInstance().wrapText(
							msg, // text
							FFUIGlobals.SCREEN_WIDTH - statsTableWidth - 1,
							false, // bordered
							true), // starred
					text);
			text = ArrayUtilities.getInstance().extendArray("\n", text);
		} catch (RPGException e) {
			JOGLErrorHandler.getInstance().fatalError(e);
		}
	}
	@Override
	public void addMessage(final String msg) {
		try {
			text = ArrayUtilities.getInstance().extendArray(
					TextProcessor.getInstance().wrapText(
							msg,
							FFUIGlobals.SCREEN_WIDTH - statsTableWidth - 1),
					text);
			text = ArrayUtilities.getInstance().extendArray("\n", text);
		} catch (RPGException e) {
			JOGLErrorHandler.getInstance().fatalError(e);
		}
	}
	/**
	 * Adds a mob to the combat.
	 * @param npc the npc
	 */
	public void addMob(final FFInteractiveObject npc) {
		mobs.add(npc);
	}
	/**
	 * 
	 * @param targetIO
	 * @param dmg
	 * @param sourceIO
	 * @param flags
	 * @return
	 * @throws RPGException
	 * @throws PooledException
	 */
	public float ARX_DAMAGES_DealDamages(final FFInteractiveObject targetIO,
			final float dmg, final FFInteractiveObject sourceIO,
			final int flags) throws RPGException, PooledException {
		if (!Interactive.getInstance().hasIO(targetIO)) {
			throw new RPGException(ErrorMessage.BAD_PARAMETERS,
					"Invalid target object");
		}
		if (!Interactive.getInstance().hasIO(sourceIO)) {
			throw new RPGException(ErrorMessage.BAD_PARAMETERS,
					"Invalid source object");
		}
		float damagesdone = 0;
		if (targetIO.hasIOFlag(IoGlobals.IO_01_PC)) {
			// check poison
			if (damageIsPoison(flags)) {
				// TODO - figure out poison resistance
				damagesdone = dmg;
			} else if (damageIsManaDrain(flags)) {
				damagesdone = dmg;
			} else {
				// no need to damage equipment in this game
				// ARX_DAMAGES_DamagePlayerEquipment(dmg);
				damagesdone = targetIO.getPCData().ARX_DAMAGES_DamagePlayer(
						dmg, flags, sourceIO.getRefId());
			}
			damagesdone = damageByType(sourceIO, damagesdone, flags);
		} else if (targetIO.hasIOFlag(IoGlobals.IO_03_NPC)) {
			if (damageIsPoison(flags)) {
				// TODO - figure out poison resistance
				damagesdone = dmg;
			} else {
				if (damageIsFire(flags)) {
					// no fire damage (yet!)
					// ARX_DAMAGES_IgnitIO(io_target, damagesdone);
					damagesdone = dmg;
				}
				if (damageIsManaDrain(flags)) {
					// ARX_DAMAGES_HealManaInter(io_source,
					damagesdone = dmg;
				} else {
					damagesdone = targetIO.getNPCData().damageNPC(
							dmg, sourceIO.getRefId(), false);
				}
			}
			damagesdone = damageByType(sourceIO, damagesdone, flags);
		}
		return damagesdone;
	}
	public float ARX_DAMAGES_DealDamages(final int target, final float dmg,
			final int source, final int flags)
					throws RPGException, PooledException {
		if (!Interactive.getInstance().hasIO(target)) {
			throw new RPGException(ErrorMessage.BAD_PARAMETERS,
					"Invalid target object reference");
		}
		if (!Interactive.getInstance().hasIO(source)) {
			throw new RPGException(ErrorMessage.BAD_PARAMETERS,
					"Invalid source object reference");
		}
		return ARX_DAMAGES_DealDamages(
				(FFInteractiveObject) Interactive.getInstance().getIO(target),
				dmg,
				(FFInteractiveObject) Interactive.getInstance().getIO(source),
				flags);
	}
	/**
	 * Gets the damage from an IO's weapon.  The minimum damage is 1.
	 * @param io the IO
	 * @return {@link int}
	 * @throws PooledException if an error occurs
	 * @throws RPGException if an error occurs
	 */
	private int getIOWeaponDamage(FFInteractiveObject io) throws PooledException, RPGException {
		int dmg = 1;
		int wpnId = -1;
		if (io.hasIOFlag(IoGlobals.IO_01_PC)) {
			wpnId = io.getPCData().getEquippedItem(
					EquipmentGlobals.EQUIP_SLOT_WEAPON);
		} else if (io.hasIOFlag(IoGlobals.IO_03_NPC)) {
			wpnId = io.getNPCData().getWeaponInHand();
		}
		if (Interactive.getInstance().hasIO(wpnId)) {
			FFInteractiveObject wpnIO = (FFInteractiveObject)
					Interactive.getInstance().getIO(wpnId);
			dmg = (int) wpnIO.getItemData().getEquipitem().getElement(
					FFIo.getElementIndex("IO_EQUIPITEM_ELEMENT_DAMAGES"))
					.getValue();
		}
		return dmg;
	}
	/**
	 * Attacks the first mob.
	 * @param withLuck if <tt>true</tt>, luck is being used
	 * @throws PooledException if an error occurs
	 * @throws RPGException if an error occurs
	 */
	public void attackFirstMob(final boolean withLuck)
			throws PooledException, RPGException {
		FFInteractiveObject playerIO =
				FFWoFMController.getInstance().getPlayerIO();
		playerIO.getPCData().doComputeFullStats();
		FFInteractiveObject mobIO = mobs.get(0);
		mobIO.getNPCData().doComputeFullStats();
		int mobAttackStrength =
				(int) (Diceroller.getInstance().rollXdY(2, Dice.D6)
						+ mobIO.getNPCData().getFullAttributeScore("SK"));
		int playerAttackStrength =
				(int) (Diceroller.getInstance().rollXdY(2, Dice.D6)
						+ playerIO.getPCData().getFullAttributeScore("SK"));
		if (playerAttackStrength > mobAttackStrength) {
			int dmg = getIOWeaponDamage(playerIO);
			if (withLuck) {
				if (playerIO.getPCData().testYourLuck(true)) {
					ARX_DAMAGES_DealDamages(mobIO, dmg + LUCKY_ATTACK,
							playerIO, 0);
					addMessage("Lucky hit!");
				} else {
					ARX_DAMAGES_DealDamages(mobIO, dmg + UNLUCKY_ATTACK,
							playerIO, 0);
					addMessage("Unlucky hit.");
				}
			} else {
				ARX_DAMAGES_DealDamages(mobIO, dmg, playerIO, 0);
				addMessage("Hit!");
			}
		} else if (mobAttackStrength > playerAttackStrength) {
			int dmg = getIOWeaponDamage(mobIO);
			if (withLuck) {
				if (playerIO.getPCData().testYourLuck(true)) {
					ARX_DAMAGES_DealDamages(playerIO, dmg + LUCKY_DEFENSE,
							mobIO, 0);
					addMessage("Lucky block!");
				} else {
					ARX_DAMAGES_DealDamages(playerIO, dmg + UNLUCKY_DEFENSE,
							mobIO, 0);
					addMessage("Unlucky block!");
				}
			} else {
				ARX_DAMAGES_DealDamages(playerIO, dmg, mobIO, 0);
				addMessage("Block!");
			}
		} else {
			// TODO - parry!
			addMessage("Parry!");
		}
	}
	/**
	 * perform additional calculations for damage by type.
	 * @param sourceIO the source of the damage
	 * @param damagesdone the amount of damages done
	 * @param flags any flags applied to the damage type
	 * @return {@link float}
	 */
	private float damageByType(final FFInteractiveObject sourceIO,
			final float damagesdone, final int flags) {
		float dmg = damagesdone;
		if (damageIsFire(flags)) {
			// no fire damage (yet!)
			// ARX_DAMAGES_IgnitIO(io_target, damagesdone);
		}
		if ((flags & EquipmentGlobals.DAMAGE_TYPE_DRAIN_LIFE)
				== EquipmentGlobals.DAMAGE_TYPE_DRAIN_LIFE) {
			if (sourceIO.hasIOFlag(IoGlobals.IO_03_NPC)) {
				sourceIO.getNPCData().healNPC(damagesdone);
			} else if (sourceIO.hasIOFlag(IoGlobals.IO_01_PC)) {
				sourceIO.getPCData().ARX_DAMAGES_HealPlayer(damagesdone);
			}
		}
		if ((flags & EquipmentGlobals.DAMAGE_TYPE_DRAIN_MANA)
				== EquipmentGlobals.DAMAGE_TYPE_DRAIN_MANA) {
			if (sourceIO.hasIOFlag(IoGlobals.IO_01_PC)) {
				sourceIO.getPCData().ARX_DAMAGES_HealManaPlayer(damagesdone);
			} else if (sourceIO.hasIOFlag(IoGlobals.IO_03_NPC)) {
				sourceIO.getNPCData().ARX_DAMAGES_HealManaInter(damagesdone);
			}
		}
		if ((flags
				& EquipmentGlobals.DAMAGE_TYPE_PUSH) == EquipmentGlobals.DAMAGE_TYPE_PUSH) {
			// ARX_DAMAGES_PushIO(io_target, source, damagesdone *
			// DIV2);
		}
		if ((flags
				& EquipmentGlobals.DAMAGE_TYPE_MAGICAL) == EquipmentGlobals.DAMAGE_TYPE_MAGICAL
				&& (flags
						& EquipmentGlobals.DAMAGE_TYPE_FIRE) != EquipmentGlobals.DAMAGE_TYPE_FIRE
				&& (flags
						& EquipmentGlobals.DAMAGE_TYPE_COLD) != EquipmentGlobals.DAMAGE_TYPE_COLD) {
			// no need to do magical damages (yet)
			// damagesdone -= player.Full_resist_magic * DIV100 *
			// damagesdone;
			// damagesdone = __max(0, damagesdone);
		}
		return dmg;
	}
	/**
	 * Determines if the damage type includes fire.
	 * @param damageTypeFlags the damage type flags
	 * @return <tt>true</tt> if the damage types include fire; <tt>false</tt>
	 *         otherwise
	 */
	private boolean damageIsFire(final int damageTypeFlags) {
		return (damageTypeFlags
				& EquipmentGlobals.DAMAGE_TYPE_FIRE) == EquipmentGlobals.DAMAGE_TYPE_FIRE;
	}
	/**
	 * Determines if the damage type includes mana drain.
	 * @param damageTypeFlags the damage type flags
	 * @return <tt>true</tt> if the damage types include mana drain;
	 *         <tt>false</tt> otherwise
	 */
	private boolean damageIsManaDrain(final int damageTypeFlags) {
		return (damageTypeFlags
				& EquipmentGlobals.DAMAGE_TYPE_DRAIN_MANA) == EquipmentGlobals.DAMAGE_TYPE_DRAIN_MANA;
	}
	/**
	 * Determines if the damage type includes poison.
	 * @param damageTypeFlags the damage type flags
	 * @return <tt>true</tt> if the damage types include poison; <tt>false</tt>
	 *         otherwise
	 */
	private boolean damageIsPoison(final int damageTypeFlags) {
		return (damageTypeFlags
				& EquipmentGlobals.DAMAGE_TYPE_POISON) == EquipmentGlobals.DAMAGE_TYPE_POISON;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getErrorMessage() {
		return null;
	}
	public FFInteractiveObject getFirstMob() {
		FFInteractiveObject io = null;
		if (!mobs.isEmpty()) {
			io = mobs.get(0);
		}
		return io;
	}
	/*
	 * (non-Javadoc)
	 * @see com.dalonedrow.rpg.base.consoleui.ConsoleView#getMessage()
	 */
	@Override
	public String getMessage() {
		return null;
	}
	/**
	 * Renders the top half of the screen, which is the room description.
	 * @throws PooledException if an error occurs
	 * @throws RPGException if an error occurs
	 */
	private void processActionView() {
		try {
			PooledStringBuilder sb =
					StringBuilderPool.getInstance().getStringBuilder();
			sb.append("** ROUND ");
			sb.append(round);
			sb.append("\n\n");
			boolean fits = false;
			do {
				int lines = 0;
				for (int i = 0, len = text.length; i < len; i++) {
					lines += text[i].split("\n").length;
				}
				if (lines > MAX_LINES + 2) {
					text = ArrayUtilities.getInstance().removeIndex(0, text);
				} else {
					fits = true;
				}
			} while (!fits);
			for (int i = 0, len = text.length; i < len; i++) {
				sb.append(text[i]);
			}
			actionPanel.setContent(sb.toString());
			sb.returnToPool();
			sb = null;
		} catch (SecurityException | PooledException e) {
			JOGLErrorHandler.getInstance().fatalError(e);
		}
	}
	/**
	 * Renders the enemy status screen.
	 * @throws PooledException if an error occurs
	 * @throws RPGException if an error occurs
	 */
	private void processEnemyStatsView() throws PooledException, RPGException {
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		for (int i = 0, len = mobs.size(); i < len; i++) {
			for (int j =
					statsTableWidth - FFUIGlobals.BORDER_PADDING - mobs.get(i)
							.getNPCData().getTitle().length; j > 0; j--) {
				sb.append(' ');
			}
			sb.append(mobs.get(i).getNPCData().getTitle());
			sb.append("\n");
			sb.append(FFWoFMController.getInstance().getNPCStatusMarkup(
					mobs.get(i)));
			sb.append("\n\n");
		}
		enmyStatsPanel.setContent(sb.toString());
		sb.returnToPool();
		sb = null;
	}
	/**
	 * Renders the top half of the screen, which is the room description.
	 * @throws PooledException if an error occurs
	 * @throws RPGException if an error occurs
	 */
	private void processOptionsView() throws PooledException, RPGException {
		final int numColumns = 4;
		final int panelWidth = FFUIGlobals.SCREEN_WIDTH
				- FFUIGlobals.BORDER_PADDING + 1 - statsTableWidth;
		String[] actions = new String[] { "ATTACK" };
		String t = TextProcessor.getInstance().getSelectionsAsColumns(
				Math.min(actions.length, numColumns), actions, "  ");
		// FIX MENU ITEMS WIDTH
		String[] split = t.split("\n");
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		sb.append(split[0]);
		int i = panelWidth - split[0].length();
		for (; i > 0; i--) {
			sb.append(' ');
		}
		i = 1;
		for (int len = split.length; i < len; i++) {
			sb.append("\n");
			sb.append(split[i]);
		}
		optionsPanel.setContent(sb.toString());
		sb.returnToPool();
		sb = null;
		actions = null;
		t = null;
	}
	/**
	 * Renders the top half of the screen, which is the room description.
	 * @throws PooledException if an error occurs
	 * @throws RPGException if an error occurs
	 */
	private void processStatsView() throws PooledException, RPGException {
		plyrStatsPanel.setContent(
				FFWoFMController.getInstance().getPartyStatusTable());
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render() throws RPGException {
		try {
			processActionView();
			processOptionsView();
			processStatsView();
			processEnemyStatsView();
			// options panel to left of plyr stats panel
			optionsPanel.join(plyrStatsPanel, Panel.LEFT, Panel.TOP);
			// view panel to left of enmy stats panel
			actionPanel.join(enmyStatsPanel, Panel.LEFT, Panel.TOP);
			// view panel above options panel
			actionPanel.join(optionsPanel, Panel.TOP, Panel.CENTER);
			OutputEvent.getInstance().print(actionPanel.getDisplayText(), this);
			OutputEvent.getInstance().print(
					WebServiceClient.getInstance().getTextByName("choice"),
					this);
			InputProcessor.getInstance().setInputAction(
					this, // object
					getClass().getMethod("actionProcessInput",
							new Class[] { String.class }), // method
					null); // arguments to be read from system.in
		} catch (NoSuchMethodException | PooledException
				| SecurityException e) {
			JOGLErrorHandler.getInstance().fatalError(e);
		}
	}
	/** Resets the screen. */
	public void reset() {
		mobs.clear();
		text = new String[0];
		round = 1;
	}
	@Override
	public void watchUpdated(final Watchable data) {
		// TODO
	}
}
