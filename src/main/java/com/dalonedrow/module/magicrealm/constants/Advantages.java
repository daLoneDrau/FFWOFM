/**
 *
 */
package com.dalonedrow.module.magicrealm.constants;

/**
 * @author drau
 */
interface Advantages {
	/**
	 * The character subtracts one from each die roll whenever they roll on the
	 * Missile Table to attack with a missile weapon.
	 */
	int	ADVANTAGE_AIM					= 1 << 0;
	/**
	 * The character rolls one die Instead of two whenever they roll on the
	 * Missile Table to make an attack with a bow or crossbow.
	 */
	int	ADVANTAGE_ARCHER				= 1 << 1;
	/** The character can record and do an extra Spell phase each turn. */
	int	ADVANTAGE_AURA_OF_POWER			= 1 << 2;
	/**
	 * The character rolls one die instead of two whenever they use the Meeting
	 * Table during a Trade activity. <b><i>Note:</i> They get this advantage
	 * only during the Trade activity. They do not get it during the Hire
	 * activity or when they roll for battling natives.</b>
	 */
	int	ADVANTAGE_BARTER				= 1 << 3;
	/**
	 * <p>
	 * The character can play their Berserk chit to increase their vulnerability
	 * to Tremendous for the rest of the day. Once they play it, it takes
	 * Tremendous harm to kill them. At Midnight they revert to normal.
	 * </p>
	 * <i>Note</i>: For purposes of fatiguing, the Berserk chit counts as a
	 * Fight chit. It cannot be used as a Fight chit in any other way.
	 */
	int	ADVANTAGE_BERSERK				= 1 << 4;
	/**
	 * The character rolls one die instead of two whenever they use the Hide
	 * table, the Meeting Table, or any Search table when they are in a cave
	 * clearing. This gives them some powerful advantages in the caves, somewhat
	 * offsetting their short legs. Obviously, the character prefers to spend as
	 * much time as possible in the caves.
	 */
	int	ADVANTAGE_CAVE_KNOWLEDGE		= 1 << 5;
	/**
	 * <p>
	 * Instead of taking their turn when their Attention chit is picked, the
	 * character chooses when they will take their turn.
	 * </p>
	 * <p>
	 * At Sunrise they keep their Attention chit instead of mixing it in with
	 * the others, and each time a new Attention chit is about to be picked
	 * during Daylight their can preempt and take their turn at that point. They
	 * can preempt only once per day (they get only one turn per day), they
	 * cannot interrupt another character's turn once that other character's
	 * chit has been picked, and if they have not taken their turn when all of
	 * the Attention chits have been picked they must take their turn at that
	 * point.
	 * </p>
	 * <p>
	 * The ability to preempt applies only during Daylight. It does not work
	 * when chits are picked during other periods of the day.
	 * </p>
	 * <p>
	 * If several characters have the ability to preempt (due to spells or
	 * duplicate characters in the game), they can preempt or pass in turn,
	 * starting with the last character to take a turn and going to the left,
	 * skipping any characters who do not have the ability to preempt. When no
	 * chits remain to be picked, any characters who have not yet taken their
	 * turns cannot pass.
	 * </p>
	 */
	int	ADVANTAGE_CLEVER				= 1 << 6;
	/**
	 * The character rolls one die instead of two each time they make a Hide die
	 * roll.
	 */
	int	ADVANTAGE_CONCEALMENT			= 1 << 7;
	/** The character must use Magic Sight. */
	int	ADVANTAGE_DISEMBODIED			= 1 << 8;
	/** The character can record and do an extra Hide phase each day. */
	int	ADVANTAGE_ELUSIVENESS			= 1 << 9;
	/**
	 * The character in the Magic Realm. At the start of the game they cross all
	 * of the hidden paths and secret passages off of their Discoveries list.
	 * They can use them all.
	 */
	int	ADVANTAGE_EXPERIENCE			= 1 << 10;
	/**
	 * <p>
	 * The character has an invisible companion that can move around the map
	 * separately and discover things for them.
	 * </p>
	 * <p>
	 * They use an extra game piece to represent this "familiar". Each day they
	 * record a separate turn for the familiar: it gets the same basic and
	 * sunlight phases as do the characters, and it can do only the Move,
	 * Follow, and Peer activities (the only clearing it can search is the
	 * clearing it is in). It takes its turn just before the character takes
	 * their turn, when their Attention chit is picked. The familiar cannot
	 * block or be blocked, it does not summon denizens nor cause monsters to
	 * move, and it cannot take part In combat.
	 * </p>
	 * <p>
	 * The familiar can follow and spy like a character. When it follows the
	 * character, they can carry it like an item with Negligible weight, even
	 * when they fly. The familiar cannot be followed or spied on.
	 * </p>
	 * <p>
	 * The familiar cannot carry belongings or recorded Gold.
	 * </p>
	 * <p>
	 * The character and their familiar share the same Discoveries list.
	 * Anything either of them discovers can be used by both of them. <b>If the
	 * familiar discovers Hidden Enemies, the character can only see them if
	 * they are in the same clearing with the Familiar.</b>
	 * </p>
	 */
	int	ADVANTAGE_FAMILIAR				= 1 << 11;
	/**
	 * Whenever the character rolls on the Meeting Table they roll one die
	 * instead of two. Their deadly reputation makes it easier for them to trade
	 * and hire natives, and it makes their enemies think twice before blocking
	 * or battling them.
	 */
	int	ADVANTAGE_FEAR					= 1 << 12;
	/**
	 * The Demon, Winged Demon and Imp cannot block the character and they
	 * cannot be assigned to attack them: the character cannot lure them into
	 * attacking, and <b>they cannot be assigned to the character randomly.</b>
	 * The character can block and attack them normally. The character's
	 * hirelings are not protected and can lure and be assigned Demons and Imps.
	 */
	int	ADVANTAGE_HEAVENLY_PROTECTION	= 1 << 13;
	/**
	 * The character subtracts one from each die they roll whenever they roll on
	 * the Meeting Table; this includes all rolls they make during trading,
	 * hiring and rolling to see if the natives will battle the character. Their
	 * noble accomplishments and reputation make even their enemies less likely
	 * to attack the character, and all of the native groups are likely to give
	 * the character a little price break when the character deals with them.
	 */
	int	ADVANTAGE_HONOR					= 1 << 14;
	/**
	 * The character subtracts one from each die they roll when they use the
	 * Reading Runes table.
	 */
	int	ADVANTAGE_KNOWLEDGE				= 1 << 15;
	/**
	 * The character rolls one die instead of two each time they use the Reading
	 * Runes table.
	 */
	int	ADVANTAGE_LORE					= 1 << 16;
	/**
	 * The character can record and do an extra Alert phase each day. This
	 * reflects the effects of the magical implements they are carrying; the
	 * phase is best used to alert Magic chits.
	 */
	int	ADVANTAGE_MAGICAL_PARAPHERNALIA	= 1 << 17;
	/**
	 * <p>
	 * When the character ends their turn, the Warning and Sound chits in their
	 * tile do not summon monsters. Individuals following the character will
	 * summon monsters normally.
	 * </p>
	 * <p>
	 * If the map chits in their tile are face down they reveal them normally,
	 * but they turn the Warning and Sound chits face down again to show they
	 * have not summoned monsters yet (chits are turned face up only if they
	 * have had the opportunity to summon monsters). The chits react normally
	 * when anyone else ends his turn in the tile.
	 * </p>
	 * <p>
	 * Peace With Nature does not affect Dwellings, Site chits and Site cards,
	 * and it does not affect the Dragon Essence Treasure card. When the
	 * character ends their turn in a tile that contains one of these pieces, it
	 * summons denizens normally.
	 * </p>
	 */
	int	ADVANTAGE_PEACE_WITH_NATURE		= 1 << 18;
	/**
	 * The character can record and do an extra phase each day they are at a
	 * Dwelling (including a campfire). They must be at the Dwelling when they
	 * start to do the phase, not when they record it. They can use the extra
	 * phase to do any normal activity.
	 */
	int	ADVANTAGE_REPUTATION			= 1 << 19;
	/** The character can record and do an extra Rest phase each day. */
	int	ADVANTAGE_ROBUST				= 1 << 20;
	/**
	 * <p>
	 * This "advantage" is a mixture of advantages and disadvantages:
	 * </p>
	 * <p>
	 * The character can never use sunlight phases - they can only use basic
	 * phases (plus any extra phases due to belongings or spells). <b>They can
	 * Follow characters normally, even if they are using sunlight phases.
	 * <i>Note:</i> When using the optional Seasons/Weather rules, the character
	 * can also use Sheltered phases.</b>
	 * </p>
	 * <p>
	 * The character can rest an extra effort asterisk each time they do a Rest
	 * activity.
	 * </p>
	 * <p>
	 * The character can use their Duck chit as a special Move chit. They can
	 * play it only to do the "Duck" maneuver during the Melee Step. They cannot
	 * use it for any other purpose (except as a T chit for looting): they
	 * cannot use it to carry items, to charge or run away during the Encounter
	 * Step, and they cannot use it to do any maneuver except "Duck". For
	 * purposes of fatigue, it counts as a Move chit. <b><i>Note</i>: In the
	 * Development Game, they can use the Duck chit even as a Youngster, before
	 * they receive the Short Legs special advantage.</b>
	 * </p>
	 */
	int	ADVANTAGE_SHORT_LEGS			= 1 << 21;
	/**
	 * The character can record and do an extra Move phase each turn. They get
	 * this bonus even when they are riding a horse - their stamina includes
	 * being an excellent horseman/woman.
	 */
	int	ADVANTAGE_STAMINA				= 1 << 22;
	/**
	 * The character rolls one die instead of two whenever they use the Hide
	 * table, the Meeting table or any Search table while they are in one of the
	 * six tiles labeled �Woods� (specifically, the Deep Woods, Linden Woods,
	 * Maple Woods, Nut Woods, Oak Woods and Pine Woods). They do not get this
	 * advantage in other tiles, even when they are in woods clearings in those
	 * tiles.
	 */
	int	ADVANTAGE_TRACKING_SKILLS		= 1 << 23;
}
