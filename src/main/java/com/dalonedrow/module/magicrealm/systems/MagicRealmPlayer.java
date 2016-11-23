/**
 *
 */
package com.dalonedrow.module.magicrealm.systems;

import java.util.Arrays;

import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.module.magicrealm.bus.pc.BasePlayerPartyScript;
import com.dalonedrow.module.magicrealm.constants.MagicRealmConsts;
import com.dalonedrow.module.magicrealm.graph.MagicRealmGraph;
import com.dalonedrow.module.magicrealm.graph.TileClearing;
import com.dalonedrow.module.magicrealm.rpg.MagicRealmCharacter;
import com.dalonedrow.module.magicrealm.rpg.MagicRealmInteractiveObject;
import com.dalonedrow.module.magicrealm.rpg.MagicRealmPartyData;
import com.dalonedrow.module.magicrealm.rpg.MagicRealmScriptable;
import com.dalonedrow.module.magicrealm.rpg.action.ActionChit;
import com.dalonedrow.pooled.PooledStringBuilder;
import com.dalonedrow.pooled.StringBuilderPool;
import com.dalonedrow.rpg.base.systems.Player;
import com.dalonedrow.rpg.base.systems.Script;
import com.dalonedrow.utils.ArrayUtilities;
import com.dalonedrow.utils.ConsoleLogger;
import com.dalonedrow.utils.TextLoader;

/**
 * @author drau
 */
@SuppressWarnings("unchecked")
public final class MagicRealmPlayer implements
Player<MagicRealmInteractiveObject, MagicRealmCharacter> {
    /** the singLeton instance of {@link MagicRealmPlayer}. */
    private static MagicRealmPlayer instance;
    /**
     * Gets the one and only instance of {@link MagicRealmPlayer}.
     * @return {@link MagicRealmPlayer}
     */
    public static MagicRealmPlayer getInstance() {
        if (MagicRealmPlayer.instance == null) {
            MagicRealmPlayer.instance = new MagicRealmPlayer();
        }
        return MagicRealmPlayer.instance;
    }
    /*
     * (non-Javadoc)
     * 
     * @see com.dalonedrow.rpg.base.systems.Player#addGold(java.lang.Object,
     * float)
     */
    @Override
    public void addGold(final MagicRealmInteractiveObject playerIO,
            final float val) {
        playerIO.getPCData().setGold(playerIO.getPCData().getGold() + val);
        if (playerIO.getPCData().getGold() < 0) {
            playerIO.getPCData().setGold(0);
        }
    }
    /**
     * Adds a certain amount of notoriety to a player.
     * @param playerIO the player
     * @param val the amount of notoriety
     */
    public void addNotoriety(final MagicRealmInteractiveObject playerIO,
            final float val) {
        playerIO.getPCData().adjustNotoriety(val);
    }
    /**
     * Adds an entry to the player's knowledge. They found a dwelling on the
     * map.
     * @param playerIO the player IO
     * @param dwellingIO the dwelling IO
     * @param clearingId the clearing where the dwelling was found
     */
    public void addPlayerKnowledgeDwellingOnMap(
            final MagicRealmInteractiveObject playerIO,
            final MagicRealmInteractiveObject dwellingIO, final int clearingId) {
        // add to knowledge
        int[][] dom = playerIO.getPCData().getDwellingOnMap();
        boolean foundClearing = false;
        for (int o = dom.length - 1; o >= 0; o--) {
            int[] list = dom[o];
            if (list[0] == clearingId) {
                foundClearing = true;
                boolean foundDwelling = false;
                for (int i = list.length - 1; i >= 1; i--) {
                    if (list[i] == dwellingIO.getRefId()) {
                        foundDwelling = true;
                        break;
                    }
                }
                if (!foundDwelling) {
                    dom[o] =
                            ArrayUtilities.getInstance().extendArray(
                                    dwellingIO.getRefId(), list);
                }
                list = null;
                break;
            }
        }
        if (!foundClearing) {
            dom =
                    (int[][]) ArrayUtilities.getInstance().extendArray(
                            new int[] { clearingId, dwellingIO.getRefId() },
                            dom);
        }
        playerIO.getPCData().setDwellingOnMap(dom);
    }
    /**
     * Adds an entry to the player's knowledge. They found a native on the map.
     * @param playerIO the player IO
     * @param nativeIO the native IO
     * @param clearingId the clearing where the native was found
     */
    public void addPlayerKnowledgeNativeOnMap(
            final MagicRealmInteractiveObject playerIO,
            final MagicRealmInteractiveObject nativeIO, final int clearingId) {
        removeFromPlayerKnowledgeNativeOnMap(playerIO, nativeIO);
        // add to knowledge
        int[][] nom = playerIO.getPCData().getNativeOnMap();
        boolean foundClearing = false;
        for (int o = nom.length - 1; o >= 0; o--) {
            int[] list = nom[o];
            if (list[0] == clearingId) {
                foundClearing = true;
                boolean foundNatives = false;
                for (int i = list.length - 1; i >= 1; i--) {
                    if (list[i] == nativeIO.getRefId()) {
                        foundNatives = true;
                        break;
                    }
                }
                if (!foundNatives) {
                    nom[o] =
                            ArrayUtilities.getInstance().extendArray(
                                    nativeIO.getRefId(), list);
                }
                list = null;
                break;
            }
        }
        if (!foundClearing) {
            nom =
                    (int[][]) ArrayUtilities.getInstance().extendArray(
                            new int[] { clearingId, nativeIO.getRefId() }, nom);
        }
        playerIO.getPCData().setNativeOnMap(nom);
    }
    /**
     * Adds an entry to the player's knowledge. They found a sound on the map.
     * @param playerIO the player IO
     * @param siteOrSound the sound
     * @param clearingId the clearing where the dwelling was found
     */
    public void addPlayerKnowledgeSitesFoundOnMap(
            final MagicRealmInteractiveObject playerIO, final int site,
            final int clearingId) {
        // add to knowledge
        int[][] sfom = playerIO.getPCData().getSiteFoundOnMap();
        boolean foundClearing = false;
        for (int o = sfom.length - 1; o >= 0; o--) {
            int[] list = sfom[o];
            if (list[0] == clearingId) {
                foundClearing = true;
                boolean foundSite = false;
                for (int i = list.length - 1; i >= 1; i--) {
                    if (list[i] == site) {
                        foundSite = true;
                        break;
                    }
                }
                if (!foundSite) {
                    sfom[o] =
                            ArrayUtilities.getInstance()
                                    .extendArray(site, list);
                }
                list = null;
                break;
            }
        }
        if (!foundClearing) {
            sfom =
                    (int[][]) ArrayUtilities.getInstance().extendArray(
                            new int[] { clearingId, site }, sfom);
        }
        playerIO.getPCData().setSiteFoundOnMap(sfom);
    }
    /**
     * Adds an entry to the player's knowledge. They found a sound on the map.
     * @param playerIO the player IO
     * @param siteOrSound the sound
     * @param clearingId the clearing where the dwelling was found
     */
    public void addPlayerKnowledgeSoundOnMap(
            final MagicRealmInteractiveObject playerIO, final int siteOrSound,
            final int clearingId) {
        // add to knowledge
        int[][] som = playerIO.getPCData().getSoundOnMap();
        boolean foundClearing = false;
        for (int o = som.length - 1; o >= 0; o--) {
            int[] list = som[o];
            if (list[0] == clearingId) {
                foundClearing = true;
                boolean foundSound = false;
                for (int i = list.length - 1; i >= 1; i--) {
                    if (list[i] == siteOrSound) {
                        foundSound = true;
                        break;
                    }
                }
                if (!foundSound) {
                    som[o] =
                            ArrayUtilities.getInstance().extendArray(
                                    siteOrSound, list);
                }
                list = null;
                break;
            }
        }
        if (!foundClearing) {
            som =
                    (int[][]) ArrayUtilities.getInstance().extendArray(
                            new int[] { clearingId, siteOrSound }, som);
        }
        playerIO.getPCData().setSoundOnMap(som);
    }
    /**
     * Adds an entry to the player's knowledge. They found a treasure on the
     * map.
     * @param playerIO the player IO
     * @param treasureIO the treasure IO
     * @param clearingId the clearing where the treasure was found
     */
    public void addPlayerKnowledgeTreasureOnMap(
            final MagicRealmInteractiveObject playerIO,
            final MagicRealmInteractiveObject treasureIO, final int clearingId) {
        removeFromPlayerKnowledgeTreasureOnMap(playerIO, treasureIO);
        removeFromPlayerKnowledgeTreasureWithMe(playerIO, treasureIO);
        removeFromPlayerKnowledgeTreasureWithNatives(playerIO, treasureIO);
        removeFromPlayerKnowledgeTreasureWithPlayers(playerIO, treasureIO);
        // add to knowledge
        int[][] tom = playerIO.getPCData().getTreasureOnMap();
        boolean found = false;
        for (int i = tom.length - 1; i >= 0; i--) {
            int[] list = tom[i];
            if (list[0] == clearingId) {
                tom[i] =
                        ArrayUtilities.getInstance().extendArray(
                                treasureIO.getRefId(), list);
                list = null;
                found = true;
                break;
            }
        }
        if (!found) {
            tom =
                    (int[][]) ArrayUtilities.getInstance().extendArray(
                            new int[] { clearingId, treasureIO.getRefId() },
                            tom);
        }
        playerIO.getPCData().setTreasureOnMap(tom);
    }
    /**
     * Adds an entry to the player's knowledge. They picked up a treasure.
     * @param playerIO the player IO
     * @param treasureIO the treasure IO
     */
    public void addPlayerKnowledgeTreasureWithMe(
            final MagicRealmInteractiveObject playerIO,
            final MagicRealmInteractiveObject treasureIO) {
        removeFromPlayerKnowledgeTreasureOnMap(playerIO, treasureIO);
        removeFromPlayerKnowledgeTreasureWithMe(playerIO, treasureIO);
        removeFromPlayerKnowledgeTreasureWithNatives(playerIO, treasureIO);
        removeFromPlayerKnowledgeTreasureWithPlayers(playerIO, treasureIO);
        playerIO.getPCData().setTreasureWithMe(
                ArrayUtilities.getInstance().extendArray(treasureIO.getRefId(),
                        playerIO.getPCData().getTreasureWithMe()));
    }
    /**
     * Adds an entry to the player's knowledge. They found a treasure with a
     * native.
     * @param playerIO the player IO
     * @param treasureIO the treasure IO
     * @param nativeIO the native IO
     */
    public void addPlayerKnowledgeTreasureWithNative(
            final MagicRealmInteractiveObject playerIO,
            final MagicRealmInteractiveObject treasureIO,
            final MagicRealmInteractiveObject nativeIO) {
        removeFromPlayerKnowledgeTreasureOnMap(playerIO, treasureIO);
        removeFromPlayerKnowledgeTreasureWithMe(playerIO, treasureIO);
        removeFromPlayerKnowledgeTreasureWithNatives(playerIO, treasureIO);
        removeFromPlayerKnowledgeTreasureWithPlayers(playerIO, treasureIO);
        // add to knowledge
        int[][] twn = playerIO.getPCData().getTreasureWithNatives();
        boolean found = false;
        for (int i = twn.length - 1; i >= 0; i--) {
            int[] list = twn[i];
            if (list[0] == nativeIO.getRefId()) {
                twn[i] =
                        ArrayUtilities.getInstance().extendArray(
                                treasureIO.getRefId(), list);
                list = null;
                found = true;
                break;
            }
        }
        if (!found) {
            twn =
                    (int[][]) ArrayUtilities.getInstance().extendArray(
                            new int[] { nativeIO.getRefId(),
                                    treasureIO.getRefId() }, twn);
        }
        playerIO.getPCData().setTreasureWithNatives(twn);
    }
    /**
     * Adds an entry to the player's knowledge. They found a treasure with
     * another player.
     * @param playerIO the player IO
     * @param treasureIO the treasure IO
     * @param opponentIO the opponent IO
     */
    public void addPlayerKnowledgeTreasureWithPlayer(
            final MagicRealmInteractiveObject playerIO,
            final MagicRealmInteractiveObject treasureIO,
            final MagicRealmInteractiveObject opponentIO) {
        removeFromPlayerKnowledgeTreasureOnMap(playerIO, treasureIO);
        removeFromPlayerKnowledgeTreasureWithMe(playerIO, treasureIO);
        removeFromPlayerKnowledgeTreasureWithNatives(playerIO, treasureIO);
        removeFromPlayerKnowledgeTreasureWithPlayers(playerIO, treasureIO);
        // add to knowledge
        int[][] twp = playerIO.getPCData().getTreasureWithPlayer();
        boolean found = false;
        for (int i = twp.length - 1; i >= 0; i--) {
            int[] list = twp[i];
            if (list[0] == opponentIO.getRefId()) {
                twp[i] =
                        ArrayUtilities.getInstance().extendArray(
                                treasureIO.getRefId(), list);
                list = null;
                found = true;
                break;
            }
        }
        if (!found) {
            twp =
                    (int[][]) ArrayUtilities.getInstance().extendArray(
                            new int[] { opponentIO.getRefId(),
                                    treasureIO.getRefId() }, twp);
        }
        playerIO.getPCData().setTreasureWithPlayer(twp);
    }
    /**
     * Adds an entry to the player's knowledge. They found a warning on the map.
     * @param playerIO the player IO
     * @param warning the warning
     * @param clearingId the clearing where the warning was found
     */
    public void addPlayerKnowledgeWarningOnMap(
            final MagicRealmInteractiveObject playerIO, final int warning,
            final int clearingId) {
        // add to knowledge
        int[][] wom = playerIO.getPCData().getWarningOnMap();
        boolean foundClearing = false;
        for (int o = wom.length - 1; o >= 0; o--) {
            int[] list = wom[o];
            if (list[0] == clearingId) {
                foundClearing = true;
                boolean foundWarning = false;
                for (int i = list.length - 1; i >= 1; i--) {
                    if (list[i] == warning) {
                        foundWarning = true;
                        break;
                    }
                }
                if (!foundWarning) {
                    wom[o] =
                            ArrayUtilities.getInstance().extendArray(warning,
                                    list);
                }
                list = null;
                break;
            }
        }
        if (!foundClearing) {
            wom =
                    (int[][]) ArrayUtilities.getInstance().extendArray(
                            new int[] { clearingId, warning }, wom);
        }
        playerIO.getPCData().setWarningOnMap(wom);
    }
    /**
     * Gets the character's advantages in a console-friendly {@link String}.
     * @param pc the {@link MagicRealmCharacter} instance
     * @return {@link String}
     */
    private String advantagesToConsoleString(final MagicRealmCharacter pc) {
        PooledStringBuilder sb =
                StringBuilderPool.getInstance().getStringBuilder();
        sb.append("Advantages:\n");
        String[] list = new String[] { "  " };
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_AIM)) {
            list = (String[]) ArrayUtilities.getInstance().extendArray(
                    "AIM", list);
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_ARCHER)) {
            list = (String[]) ArrayUtilities.getInstance().extendArray(
                    "ARCHER", list);
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_AURA_OF_POWER)) {
            list = (String[]) ArrayUtilities.getInstance().extendArray(
                    "AURA OF POWER", list);
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_BARTER)) {
            list = (String[]) ArrayUtilities.getInstance().extendArray(
                    "BARTER", list);
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_BERSERK)) {
            list = (String[]) ArrayUtilities.getInstance().extendArray(
                    "BERSERK", list);
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_CAVE_KNOWLEDGE)) {
            list = (String[]) ArrayUtilities.getInstance().extendArray(
                    "CAVE KNOWLEDGE", list);
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_CLEVER)) {
            list = (String[]) ArrayUtilities.getInstance().extendArray(
                    "CLEVER", list);
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_CONCEALMENT)) {
            list = (String[]) ArrayUtilities.getInstance().extendArray(
                    "CONCEALMENT", list);
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_DISEMBODIED)) {
            list = (String[]) ArrayUtilities.getInstance().extendArray(
                    "DISEMBODIED", list);
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_ELUSIVENESS)) {
            list = (String[]) ArrayUtilities.getInstance().extendArray(
                    "ELUSIVENESS", list);
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_EXPERIENCE)) {
            list = (String[]) ArrayUtilities.getInstance().extendArray(
                    "EXPERIENCE", list);
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_FAMILIAR)) {
            list = (String[]) ArrayUtilities.getInstance().extendArray(
                    "FAMILIAR", list);
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_FEAR)) {
            list = (String[]) ArrayUtilities.getInstance().extendArray(
                    "FEAR", list);
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_HEAVENLY_PROTECTION)) {
            list = (String[]) ArrayUtilities.getInstance().extendArray(
                    "HEAVENLY PROTECTION", list);
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_HONOR)) {
            list = (String[]) ArrayUtilities.getInstance().extendArray(
                    "HONOR", list);
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_KNOWLEDGE)) {
            list = (String[]) ArrayUtilities.getInstance().extendArray(
                    "KNOWLEDGE", list);
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_LORE)) {
            list = (String[]) ArrayUtilities.getInstance().extendArray(
                    "LORE", list);
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_MAGICAL_PARAPHERNALIA)) {
            list = (String[]) ArrayUtilities.getInstance().extendArray(
                    "MAGICAL PARAPHERNALIA", list);
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_PEACE_WITH_NATURE)) {
            list = (String[]) ArrayUtilities.getInstance().extendArray(
                    "PEACE WITH NATURE", list);
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_REPUTATION)) {
            list = (String[]) ArrayUtilities.getInstance().extendArray(
                    "REPUTATION", list);
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_ROBUST)) {
            list = (String[]) ArrayUtilities.getInstance().extendArray(
                    "ROBUST", list);
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_SHORT_LEGS)) {
            list = (String[]) ArrayUtilities.getInstance().extendArray(
                    "SHORT LEGS", list);
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_STAMINA)) {
            list = (String[]) ArrayUtilities.getInstance().extendArray(
                    "STAMINA", list);
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_TRACKING_SKILLS)) {
            list = (String[]) ArrayUtilities.getInstance().extendArray(
                    "TRACKING SKILLS", list);
        }
        sb.append(TextProcessor.getInstance().getSelectionsAsColumns(
                3, list, "  "));
        sb.append('\n');
        String s = sb.toString();
        sb.returnToPool();
        sb = null;
        return s;
    }
    /**
     * Gets the character's advantages in a console-friendly {@link String}.
     * @param pc the {@link MagicRealmCharacter} instance
     * @return {@link String}
     * @throws Exception 
     */
    public String advantagesDetailsToConsoleString(
            final MagicRealmInteractiveObject io) throws Exception {
        PooledStringBuilder sb =
                StringBuilderPool.getInstance().getStringBuilder();
        MagicRealmCharacter pc = io.getPCData();
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_AIM)) {
            sb.append("AIM\n");
            sb.append(TextProcessor.getInstance().processText(
                    io, 
                    null, 
                    (String[]) null, 
                    TextLoader.getInstance().loadText("advantages.txt", "aim"))
                    );
            sb.append('\n');
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_ARCHER)) {
            sb.append("ARCHER\n");
            sb.append(TextProcessor.getInstance().processText(
                    io, 
                    null, 
                    (String[]) null, 
                    TextLoader.getInstance().loadText("advantages.txt", "archer"))
                    );
            sb.append('\n');
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_AURA_OF_POWER)) {
            sb.append("AURA OF POWER\n");
            sb.append(TextProcessor.getInstance().processText(
                    io, 
                    null, 
                    (String[]) null, 
                    TextLoader.getInstance().loadText(
                            "advantages.txt", "aura_of_power")));
            sb.append('\n');
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_BARTER)) {
            sb.append("BARTER\n");
            sb.append(TextProcessor.getInstance().processText(
                    io, 
                    null, 
                    (String[]) null, 
                    TextLoader.getInstance().loadText(
                            "advantages.txt", "barter")));
            sb.append('\n');
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_BERSERK)) {
            sb.append("BERSERK\n");
            sb.append(TextProcessor.getInstance().processText(
                    io, 
                    null, 
                    (String[]) null, 
                    TextLoader.getInstance().loadText(
                            "advantages.txt", "berserk")));
            sb.append('\n');
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_CAVE_KNOWLEDGE)) {
            sb.append("CAVE KNOWLEDGE\n");
            sb.append(TextProcessor.getInstance().processText(
                    io, 
                    null, 
                    (String[]) null, 
                    TextLoader.getInstance().loadText(
                            "advantages.txt", "cave_knowledge")));
            sb.append('\n');
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_CLEVER)) {
            sb.append("CLEVER\n");
            sb.append(TextProcessor.getInstance().processText(
                    io, 
                    null, 
                    (String[]) null, 
                    TextLoader.getInstance().loadText(
                            "advantages.txt", "clever")));
            sb.append('\n');
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_CONCEALMENT)) {
            sb.append("CONCEALMENT\n");
            sb.append(TextProcessor.getInstance().processText(
                    io, 
                    null, 
                    (String[]) null, 
                    TextLoader.getInstance().loadText(
                            "advantages.txt", "concealment")));
            sb.append('\n');
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_DISEMBODIED)) {
            sb.append("DISEMBODIED\n");
            sb.append(TextProcessor.getInstance().processText(
                    io, 
                    null, 
                    (String[]) null, 
                    TextLoader.getInstance().loadText(
                            "advantages.txt", "disembodied")));
            sb.append('\n');
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_ELUSIVENESS)) {
            sb.append("ELUSIVENESS\n");
            sb.append(TextProcessor.getInstance().processText(
                    io, 
                    null, 
                    (String[]) null, 
                    TextLoader.getInstance().loadText(
                            "advantages.txt", "elusiveness")));
            sb.append('\n');
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_EXPERIENCE)) {
            sb.append("EXPERIENCE\n");
            sb.append(TextProcessor.getInstance().processText(
                    io, 
                    null, 
                    (String[]) null, 
                    TextLoader.getInstance().loadText(
                            "advantages.txt", "experience")));
            sb.append('\n');
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_FAMILIAR)) {
            sb.append("FAMILIAR\n");
            sb.append(TextProcessor.getInstance().processText(
                    io, 
                    null, 
                    (String[]) null, 
                    TextLoader.getInstance().loadText(
                            "advantages.txt", "familiar")));
            sb.append('\n');
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_FEAR)) {
            sb.append("FEAR\n");
            sb.append(TextProcessor.getInstance().processText(
                    io, 
                    null, 
                    (String[]) null, 
                    TextLoader.getInstance().loadText("advantages.txt", "fear"))
                    );
            sb.append('\n');
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_HEAVENLY_PROTECTION)) {
            sb.append("HEAVENLY PROTECTION\n");
            sb.append(TextProcessor.getInstance().processText(
                    io, 
                    null, 
                    (String[]) null, 
                    TextLoader.getInstance().loadText(
                            "advantages.txt", "heavenly_protection")));
            sb.append('\n');
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_HONOR)) {
            sb.append("HONOR\n");
            sb.append(TextProcessor.getInstance().processText(
                    io, 
                    null, 
                    (String[]) null, 
                    TextLoader.getInstance().loadText(
                            "advantages.txt", "honor")));
            sb.append('\n');
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_KNOWLEDGE)) {
            sb.append("KNOWLEDGE\n");
            sb.append(TextProcessor.getInstance().processText(
                    io, 
                    null, 
                    (String[]) null, 
                    TextLoader.getInstance().loadText(
                            "advantages.txt", "knowledge")));
            sb.append('\n');
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_LORE)) {
            sb.append("LORE\n");
            sb.append(TextProcessor.getInstance().processText(
                    io, 
                    null, 
                    (String[]) null, 
                    TextLoader.getInstance().loadText("advantages.txt", "lore"))
                    );
            sb.append('\n');
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_MAGICAL_PARAPHERNALIA)) {
            sb.append("MAGICAL PARAPHERNALIA\n");
            sb.append(TextProcessor.getInstance().processText(
                    io, 
                    null, 
                    (String[]) null, 
                    TextLoader.getInstance().loadText(
                            "advantages.txt", "magical_paraphenalia")));
            sb.append('\n');
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_PEACE_WITH_NATURE)) {
            sb.append("PEACE WITH NATURE\n");
            sb.append(TextProcessor.getInstance().processText(
                    io, 
                    null, 
                    (String[]) null, 
                    TextLoader.getInstance().loadText(
                            "advantages.txt", "peace_with_nature")));
            sb.append('\n');
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_REPUTATION)) {
            sb.append("REPUTATION\n");
            sb.append(TextProcessor.getInstance().processText(
                    io, 
                    null, 
                    (String[]) null, 
                    TextLoader.getInstance().loadText(
                            "advantages.txt", "reputation")));
            sb.append('\n');
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_ROBUST)) {
            sb.append("ROBUST\n");
            sb.append(TextProcessor.getInstance().processText(
                    io, 
                    null, 
                    (String[]) null, 
                    TextLoader.getInstance().loadText(
                            "advantages.txt", "robust")));
            sb.append('\n');
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_SHORT_LEGS)) {
            sb.append("SHORT LEGS\n");
            sb.append(TextProcessor.getInstance().processText(
                    io, 
                    null, 
                    (String[]) null, 
                    TextLoader.getInstance().loadText(
                            "advantages.txt", "short_legs")));
            sb.append('\n');
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_STAMINA)) {
            sb.append("STAMINA\n");
            sb.append(TextProcessor.getInstance().processText(
                    io, 
                    null, 
                    (String[]) null, 
                    TextLoader.getInstance().loadText(
                            "advantages.txt", "stamina")));
            sb.append('\n');
        }
        if (pc.hasAdvantage(MagicRealmConsts.ADVANTAGE_TRACKING_SKILLS)) {
            sb.append("TRACKING SKILLS\n");
            sb.append(TextProcessor.getInstance().processText(
                    io, 
                    null, 
                    (String[]) null, 
                    TextLoader.getInstance().loadText(
                            "advantages.txt", "tracking_skills")));
            sb.append('\n');
        }
        String s = sb.toString();
        sb.returnToPool();
        sb = null;
        return s;
    }
    /**
     * Determines if an IO can carry their inventory (see section 3.3 of the 1st
     * encounter, 2nd edition).
     * @param playerIO the player IO
     * @return <tt>true</tt> if the IO can carry its inventory; <tt>false</tt>
     *         otherwise
     * @throws Exception if an error occurs
     */
    public boolean canCarryInventory(final MagicRealmInteractiveObject playerIO)
            throws Exception {
        ConsoleLogger.getInstance().debug("canCarryInventory(playerIO=" 
            + playerIO.getRefId());
        boolean canCarry = false;
        int carried = getInventoryWeight(playerIO);
        if (carried == MagicRealmConsts.WT_NEGLIGIBLE) {
            canCarry = true;
        } else {
            int numActions = playerIO.getPCData().getNumberOfActions();
            // check actions first
            for (int i = numActions - 1; i >= 0; i--) {
                if (playerIO.getPCData().getActionChit(i).getAction() 
                        == MagicRealmConsts.ACTION_MOVE
                        && playerIO.getPCData().getActionChit(i)
                                .getWeightOrMagic() >= carried) {
                    canCarry = true;
                    break;
                }
            }
            // if no action available to carry, check horses
            int len = playerIO.getInventory().getNumInventorySlots();
            for (int i = len - 1; i >= 0; i--) {
                MagicRealmInteractiveObject io =
                        playerIO.getInventory().getSlot(i).getIo();
                if (io != null) {
                    if (io.hasIOFlag(MagicRealmConsts.IO_04_HORSE)
                            && io.getHorseData().getActionChit(0)
                                    .getWeightOrMagic() >= carried) {
                        canCarry = true;
                        io = null;
                        break;
                    }
                    io = null;
                }
            }
        }
        return canCarry;
    }
    /**
     * Clears all treasures that were previously discovered in a clearing.
     * @param io the player io
     * @param clearingId the clearing's reference id
     */
    public void clearPlayerKnowledgeTreasureOnMap(
            final MagicRealmInteractiveObject io, final int clearingId) {
        int[][] tom = io.getPCData().getTreasureOnMap();
        for (int outer = tom.length - 1; outer >= 0; outer--) {
            int[] list = tom[outer];
            if (list[0] == clearingId) {
                list = new int[] { clearingId };
                tom[outer] = list;
                break;
            }
        }
        io.getPCData().setTreasureOnMap(tom);
        tom = null;
    }
    /*
     * (non-Javadoc)
     *
     * @see
     * com.dalonedrow.rpg.base.systems.Player#computePlayerFullStats(java.lang
     * .Object)
     */
    @Override
    public void computePlayerFullStats(final MagicRealmInteractiveObject io)
            throws Exception {
        if (io != null && io.hasIOFlag(MagicRealmConsts.IO_01_PC)) {
            MagicRealmCharacter pc = io.getPCData();
            pc.clearModAbilityScores();
            pc.getVulnerability().setFull(
                    pc.getVulnerability().getBase()
                    + pc.getVulnerability().getModifier());
        }
        // TODO Auto-generated method stub

    }
    /**
     * Gets a fresh array to store knowledge.
     * @param arr the original array
     * @param baseRef the base reference id for the array length
     * @return <code>int</code>[]
     */
    private int[] getFreshKnowledgeArray(final int[] arr, final int baseRef) {
        int[] twp = arr;
        if (twp == null) {
            twp =
                    new int[Math.max(baseRef, Interactive.getInstance()
                            .getNumberIOs())];
            Arrays.fill(twp, -1);
        }
        if (twp.length < Math.max(baseRef, Interactive.getInstance()
                .getNumberIOs())) {
            int[] dest =
                    new int[Math.max(baseRef, Interactive.getInstance()
                            .getNumberIOs())];
            Arrays.fill(dest, -1);
            System.arraycopy(twp, 0, dest, 0, twp.length);
            twp = dest;
            dest = null;
        }
        return twp;
    }
    /**
     * Gets the weight of the player's inventory.
     * @param playerIO the {@link MagicRealmInteractiveObject} player instance
     * @return <code>int</code>
     * @throws Exception if an error occurs
     */
    public int getInventoryWeight(final MagicRealmInteractiveObject playerIO)
            throws Exception {
        int weight = MagicRealmConsts.WT_NEGLIGIBLE;
        if (playerIO != null) {
            MagicRealmCharacter pc = playerIO.getPCData();
            // weigh all equipped items
            for (int i = MagicRealmConsts.MAX_EQUIPED - 1; i >= 0; i--) {
                int item = pc.getEquippedItem(i);
                weight =
                        Math.max(weight, MagicRealmEquipment.getInstance()
                                .getItemWeight(item));
            }
            // weigh the carried inventory
            int len = playerIO.getInventory().getNumInventorySlots();
            for (int i = len - 1; i >= 0; i--) {
                MagicRealmInteractiveObject io =
                        playerIO.getInventory().getSlot(i).getIo();
                if (io != null) {
                    if (io.hasIOFlag(MagicRealmConsts.IO_04_HORSE)) {
                        // skip horses
                        io = null;
                        continue;
                    }
                    weight =
                            Math.max(weight, MagicRealmEquipment.getInstance()
                                    .getItemWeight(io));
                    io = null;
                }
            }
        }
        return weight;
    }
    /**
     * Gets the least number of Move activities needed to reach any adjacent
     * clearing.
     * @param io the player IO
     * @return <code>int</code>
     * @throws Exception if an error occurs
     */
    public int getLeastMovesNeededToReachAdjacentClearings(
            final MagicRealmInteractiveObject io) throws Exception {
        int clid = MagicRealmMap.getInstance().getClearingForIO(io.getRefId());
        int[] adj =
                MagicRealmPathfinding.getInstance().getAdjacentClearings(io,
                        clid);
        int moveCost = 2;
        for (int i = adj.length - 1; i >= 0; i--) {
            TileClearing cl = MagicRealmMap.getInstance().getClearing(adj[i]);
            if (cl.getType() == MagicRealmConsts.CLEARING_TYPE_MOUNTAIN) {
                moveCost = Math.min(moveCost, 2);
            } else {
                moveCost = Math.min(moveCost, 1);
            }
            cl = null;
        }
        adj = null;
        return moveCost;
    }
    /**
     * Gets a new character.
     * @param script the character's script
     * @return {@link MagicRealmInteractiveObject}
     * @throws Exception if an error occurs
     */
    public MagicRealmInteractiveObject getNewCharacter(
            final MagicRealmScriptable script) throws Exception {
        MagicRealmInteractiveObject io =
                (MagicRealmInteractiveObject) Interactive.getInstance()
                        .getNewIO();
        io.setPCData(new MagicRealmCharacter());
        io.setScript(script);
        script.setIO(io);
        Script.getInstance().sendInitScriptEvent(io);
        // create IO's copy of all recorded paths
        MagicRealmGraph g = MagicRealmMap.getInstance().getGraph();
        for (int outer = g.getNumberOfVertices() - 1; outer >= 0; outer--) {
            int[] adj = g.getAdjacencies(outer);
            for (int inner = adj.length - 1; inner >= 0; inner--) {
                io.getPCData().addPath(outer, adj[inner]);
            }
        }
        g = null;
        // give player 10 gold to start
        addGold(io, MagicRealmConsts.STARTING_GOLD);
        return io;
    }
    /**
     * Gets a new party.
     * @param members all the IOs in the party
     * @return {@link MagicRealmInteractiveObject}
     * @throws Exception if an error occurs
     */
    @SuppressWarnings("unchecked")
    public MagicRealmInteractiveObject getNewParty(
            final MagicRealmInteractiveObject... members) throws Exception {
        MagicRealmInteractiveObject io =
                (MagicRealmInteractiveObject) Interactive.getInstance()
                        .getNewIO();
        io.addIOFlag(MagicRealmConsts.IO_16_PC_PARTY);
        MagicRealmScriptable script = new BasePlayerPartyScript();
        io.setScript(script);
        script.setIO(io);
        io.setPartyData(new MagicRealmPartyData());
        for (int i = 0, len = members.length; i < len; i++) {
            io.getPartyData().addMember(members[i].getRefId());
        }
        return io;
    }
    /**
     * Gets the party leader.
     * @param partyIO the party IO
     * @return {@link MagicRealmInteractiveObject}
     * @throws Exception if an error occurs
     */
    public MagicRealmInteractiveObject getPartyLeader(
            final MagicRealmInteractiveObject partyIO) throws Exception {
        MagicRealmInteractiveObject leader = null;
        if (partyIO != null
                && partyIO.hasIOFlag(MagicRealmConsts.IO_16_PC_PARTY)) {
            int[] members = partyIO.getPartyData().getMembers();
            for (int i = members.length - 1; i >= 0; i--) {
                if (Interactive.getInstance().hasIO(members[i])) {
                    MagicRealmInteractiveObject io =
                            (MagicRealmInteractiveObject) Interactive
                                    .getInstance().getIO(members[i]);
                    if (Script.getInstance().isIOInGroup(io, "PARTY_LEADER")) {
                        leader = io;
                        break;
                    }
                }
            }
        }
        return leader;
    }
    /**
     * Lowers a character's trading relationship with a group.
     * @param player the character
     * @param group the group's id
     */
    public void lowerRelationship(final MagicRealmCharacter player,
            final int group) {
        if (player.isAlly(group)) {
            player.removeAlly(group);
            player.assignFriendly(group);
        } else if (player.isFriendly(group)) {
            player.removeFriendly(group);
        } else if (player.isUnfriendly(group)) {
            player.removeUnfriendly(group);
            player.assignEnemy(group);
        } else if (player.isEnemy(group)) {
            // do nothing
        } else { // was Neutral
            player.assignUnfriendly(group);
        }
    }
    /**
     * Determines if a noun needs to end in ' or 's.
     * @param noun the noun
     * @return <tt>true</tt> if the needs 's, false otherwise
     */
    private boolean needApostropheS(final char[] noun) {
        boolean needs = true;
        if (noun[noun.length - 1] == 's' || noun[noun.length - 1] == 'S'
                || noun[noun.length - 1] == 'x' || noun[noun.length - 1] == 'X'
                || noun[noun.length - 1] == 'z' || noun[noun.length - 1] == 'Z') {
            needs = false;
        }
        return needs;
    }
    /**
     * Prints a row of magic action chits to the console.
     * @param col1 the 1st column of magic chits
     * @param col1t the title of the 1st column
     * @param col2 the 2nd column of magic chits
     * @param col2t the title of the 2nd column
     * @param pc the character being printed to the console
     * @return {@link String}
     */
    private String printMagicRowToConsole(final int[] col1, final char[] col1t,
            final int[] col2, final char[] col2t, final MagicRealmCharacter pc) {
        PooledStringBuilder sb =
                StringBuilderPool.getInstance().getStringBuilder();
        final int twelve = 12, fortyone = 41;
        int len = 0;
        if (col1 != null) {
            len = col1.length;
        }
        if (col2 != null) {
            len = Math.max(len, col2.length);
        }
        for (int i = 0; i < len; i++) {
            if (col1 != null && i < col1.length) {
                if (i == 0) {
                    sb.append(new String(col1t));
                    for (int inner = twelve, lenInner = col1t.length; inner > lenInner; inner--) {
                        sb.append(' ');
                    }
                } else {
                    for (int j = twelve; j > 0; j--) {
                        sb.append(' ');
                    }
                }
                sb.append(pc.getActionChit(col1[i]));
                for (int j = fortyone, lenJ =
                        pc.getActionChit(col1[i]).toString().length(); j > lenJ; j--) {
                    sb.append(' ');
                }
            } else {
                for (int j = twelve + fortyone; j > 0; j--) {
                    sb.append(' ');
                }
            }
            if (col2 != null && i < col2.length) {
                if (i == 0) {
                    sb.append(new String(col2t));
                    for (int inner = twelve, lenInner = col2t.length; inner > lenInner; inner--) {
                        sb.append(' ');
                    }
                } else {
                    for (int j = twelve; j > 0; j--) {
                        sb.append(' ');
                    }
                }
                sb.append(pc.getActionChit(col2[i]));
                sb.append('\n');
            } else {
                sb.append('\n');
            }
        }
        sb.append('\n');
        String s = sb.toString();
        sb.returnToPool();
        sb = null;
        return s;
    }
    public String printToConsole(final int ioid) throws Exception {
        String s = null;
        if (Interactive.getInstance().hasIO(ioid)) {
            s =
                    printToConsole((MagicRealmInteractiveObject) Interactive
                            .getInstance().getIO(ioid));
        }
        return s;
    }
    public String printBasicInfoToConsole(final MagicRealmInteractiveObject io)
            throws Exception {
        PooledStringBuilder sb =
                StringBuilderPool.getInstance().getStringBuilder();
        if (io != null && io.hasIOFlag(MagicRealmConsts.IO_01_PC)) {
            computePlayerFullStats(io);
            MagicRealmCharacter pc = io.getPCData();
            sb.append(new String(pc.getName()));
            sb.append('\n');
            sb.append("Weight Class: ");
            sb.append(new String(MagicRealmConsts.WEIGHT_CLASSES[(int) pc
                    .getVulnerability().getFull()]));
            sb.append('\n');
            sb.append(advantagesToConsoleString(pc));
            sb.append(printTradingRelationsToConsole(pc));
        }
        String text = sb.toString();
        sb.returnToPool();
        sb = null;
        return text;
    }
    public String printToConsole(final MagicRealmInteractiveObject io)
            throws Exception {
        PooledStringBuilder sb =
                StringBuilderPool.getInstance().getStringBuilder();
        if (io != null && io.hasIOFlag(MagicRealmConsts.IO_01_PC)) {
            computePlayerFullStats(io);
            MagicRealmCharacter pc = io.getPCData();
            sb.append(new String(pc.getName()));
            sb.append('\n');
            sb.append("Weight Class: ");
            sb.append(new String(MagicRealmConsts.WEIGHT_CLASSES[(int) pc
                    .getVulnerability().getFull()]));
            sb.append('\n');
            sb.append(advantagesToConsoleString(pc));
            sb.append(printTradingRelationsToConsole(pc));
            // sort actions
            int[] offensiveLt = new int[0], offensiveMd = new int[0];
            int[] offensiveHv = new int[0], offensiveTr = new int[0];
            int[] defensiveLt = new int[0], defensiveMd = new int[0];
            int[] defensiveHv = new int[0], defensiveTr = new int[0];
            int[] magici = new int[0], magicii = new int[0];
            int[] magiciii = new int[0], magiciv = new int[0];
            int[] magicv = new int[0], magicvi = new int[0];
            int[] magicvii = new int[0], magicviii = new int[0];
            for (int i = 0, len = pc.getNumberOfActions(); i < len; i++) {
                ActionChit ac = pc.getActionChit(i);
                switch (ac.getAction()) {
                    case MagicRealmConsts.ACTION_BERSERK:
                    case MagicRealmConsts.ACTION_FIGHT:
                        switch (ac.getWeightOrMagic()) {
                            case MagicRealmConsts.WT_LIGHT:
                                offensiveLt =
                                ArrayUtilities.getInstance()
                                .extendArray(i, offensiveLt);
                                break;
                            case MagicRealmConsts.WT_02_MEDIUM:
                                offensiveMd =
                                ArrayUtilities.getInstance()
                                .extendArray(i, offensiveMd);
                                break;
                            case MagicRealmConsts.WT_03_HEAVY:
                                offensiveHv =
                                ArrayUtilities.getInstance()
                                .extendArray(i, offensiveHv);
                                break;
                            case MagicRealmConsts.WT_04_TREMENDOUS:
                                offensiveTr =
                                ArrayUtilities.getInstance()
                                .extendArray(i, offensiveTr);
                                break;
                            default:
                                throw new Exception("Invalid weight "
                                        + ac.getWeightOrMagic());
                        }
                        break;
                    case MagicRealmConsts.ACTION_DUCK:
                    case MagicRealmConsts.ACTION_MOVE:
                        switch (ac.getWeightOrMagic()) {
                            case MagicRealmConsts.WT_LIGHT:
                                defensiveLt =
                                ArrayUtilities.getInstance()
                                .extendArray(i, defensiveLt);
                                break;
                            case MagicRealmConsts.WT_02_MEDIUM:
                                defensiveMd =
                                ArrayUtilities.getInstance()
                                .extendArray(i, defensiveMd);
                                break;
                            case MagicRealmConsts.WT_03_HEAVY:
                                defensiveHv =
                                ArrayUtilities.getInstance()
                                .extendArray(i, defensiveHv);
                                break;
                            case MagicRealmConsts.WT_04_TREMENDOUS:
                                defensiveTr =
                                ArrayUtilities.getInstance()
                                .extendArray(i, defensiveTr);
                                break;
                            default:
                                throw new Exception("Invalid weight "
                                        + ac.getWeightOrMagic());
                        }
                        break;
                    case MagicRealmConsts.ACTION_MAGIC:
                        switch (ac.getWeightOrMagic()) {
                            case MagicRealmConsts.MAGIC_TYPE_I:
                                magici =
                                ArrayUtilities.getInstance()
                                .extendArray(i, magici);
                                break;
                            case MagicRealmConsts.MAGIC_TYPE_II:
                                magicii =
                                ArrayUtilities.getInstance()
                                .extendArray(i, magicii);
                                break;
                            case MagicRealmConsts.MAGIC_TYPE_III:
                                magiciii =
                                ArrayUtilities.getInstance()
                                .extendArray(i, magiciii);
                                break;
                            case MagicRealmConsts.MAGIC_TYPE_IV:
                                magiciv =
                                ArrayUtilities.getInstance()
                                .extendArray(i, magiciv);
                                break;
                            case MagicRealmConsts.MAGIC_TYPE_V:
                                magicv =
                                ArrayUtilities.getInstance()
                                .extendArray(i, magicv);
                                break;
                            case MagicRealmConsts.MAGIC_TYPE_VI:
                                magicvi =
                                ArrayUtilities.getInstance()
                                .extendArray(i, magicvi);
                                break;
                            case MagicRealmConsts.MAGIC_TYPE_VII:
                                magicvii =
                                ArrayUtilities.getInstance()
                                .extendArray(i, magicvii);
                                break;
                            case MagicRealmConsts.MAGIC_TYPE_VIII:
                                magicviii =
                                ArrayUtilities.getInstance()
                                .extendArray(i, magicviii);
                                break;
                            default:
                                throw new Exception("Invalid magic "
                                        + ac.getWeightOrMagic());
                        }
                        break;
                }
            }
            sb.append("---------------Actions-------------------\n");
            sb.append('\n');
            if (offensiveLt.length > 0 || offensiveMd.length > 0
                    || offensiveHv.length > 0 || offensiveTr.length > 0
                    || defensiveLt.length > 0 || defensiveMd.length > 0
                    || defensiveHv.length > 0 || defensiveTr.length > 0) {
                sb.append("              OFFENSIVE                     ");
                sb.append("                        DEFENSIVE");
                sb.append('\n');
                sb.append("---------------------------------------------");
                sb.append("---------------------------------------------");
                sb.append("---------");
                sb.append('\n');
                for (int i = 0, len =
                        Math.max(offensiveLt.length, defensiveLt.length); i < len; i++) {
                    if (i < offensiveLt.length) {
                        if (i == 0) {
                            sb.append("LIGHT WEAPONS      ");
                        } else {
                            sb.append("                   ");
                        }
                        sb.append(pc.getActionChit(offensiveLt[i]));
                        for (int j = 38, lenJ =
                                pc.getActionChit(offensiveLt[i]).toString()
                                        .length(); j > lenJ; j--) {
                            sb.append(' ');
                        }
                    } else {
                        for (int j = 57; j > 0; j--) {
                            sb.append(' ');
                        }
                    }
                    if (i < defensiveLt.length) {
                        if (i == 0) {
                            sb.append("LIGHT ARMOR      ");
                        } else {
                            sb.append("                 ");
                        }
                        sb.append(pc.getActionChit(defensiveLt[i]));
                        sb.append('\n');
                    } else {
                        sb.append('\n');
                    }
                }
                if (offensiveLt.length > 0 || defensiveLt.length > 0) {
                    sb.append('\n');
                }
                for (int i = 0, len =
                        Math.max(offensiveMd.length, defensiveMd.length); i < len; i++) {
                    if (i < offensiveMd.length) {
                        if (i == 0) {
                            sb.append("MEDIUM WEAPONS     ");
                        } else {
                            sb.append("                   ");
                        }
                        sb.append(pc.getActionChit(offensiveMd[i]));
                        for (int j = 38, lenJ =
                                pc.getActionChit(offensiveMd[i]).toString()
                                        .length(); j > lenJ; j--) {
                            sb.append(' ');
                        }
                    } else {
                        for (int j = 57; j > 0; j--) {
                            sb.append(' ');
                        }
                    }
                    if (i < defensiveMd.length) {
                        if (i == 0) {
                            sb.append("MEDIUM ARMOR     ");
                        } else {
                            sb.append("                 ");
                        }
                        sb.append(pc.getActionChit(defensiveMd[i]));
                        sb.append('\n');
                    } else {
                        sb.append('\n');
                    }
                }
                if (offensiveMd.length > 0 || defensiveMd.length > 0) {
                    sb.append('\n');
                }
                for (int i = 0, len =
                        Math.max(offensiveHv.length, defensiveHv.length); i < len; i++) {
                    if (i < offensiveHv.length) {
                        if (i == 0) {
                            sb.append("HEAVY WEAPONS      ");
                        } else {
                            sb.append("                   ");
                        }
                        sb.append(pc.getActionChit(offensiveHv[i]));
                        for (int j = 38, lenJ =
                                pc.getActionChit(offensiveHv[i]).toString()
                                        .length(); j > lenJ; j--) {
                            sb.append(' ');
                        }
                    } else {
                        for (int j = 57; j > 0; j--) {
                            sb.append(' ');
                        }
                    }
                    if (i < defensiveHv.length) {
                        if (i == 0) {
                            sb.append("HEAVY ARMOR      ");
                        } else {
                            sb.append("                 ");
                        }
                        sb.append(pc.getActionChit(defensiveHv[i]));
                        sb.append('\n');
                    } else {
                        sb.append('\n');
                    }
                }
                if (offensiveHv.length > 0 || defensiveHv.length > 0) {
                    sb.append('\n');
                }
                for (int i = 0, len =
                        Math.max(offensiveTr.length, defensiveTr.length); i < len; i++) {
                    if (i < offensiveTr.length) {
                        if (i == 0) {
                            sb.append("TREMENDOUS WEAPONS ");
                        } else {
                            sb.append("                   ");
                        }
                        sb.append(pc.getActionChit(offensiveTr[i]));
                        for (int j = 38, lenJ =
                                pc.getActionChit(offensiveTr[i]).toString()
                                        .length(); j > lenJ; j--) {
                            sb.append(' ');
                        }
                    } else {
                        for (int j = 57; j > 0; j--) {
                            sb.append(' ');
                        }
                    }
                    if (i < defensiveTr.length) {
                        if (i == 0) {
                            sb.append("TREMENDOUS ARMOR ");
                        } else {
                            sb.append("                 ");
                        }
                        sb.append(pc.getActionChit(defensiveTr[i]));
                        sb.append('\n');
                    } else {
                        sb.append('\n');
                    }
                }
                if (offensiveTr.length > 0 || defensiveTr.length > 0) {
                    sb.append('\n');
                }
            }
            int spellColumns = 0;
            if (magici.length > 0) {
                spellColumns++;
            }
            if (magicii.length > 0) {
                spellColumns++;
            }
            if (magiciii.length > 0) {
                spellColumns++;
            }
            if (magiciv.length > 0) {
                spellColumns++;
            }
            if (magicv.length > 0) {
                spellColumns++;
            }
            if (magicvi.length > 0) {
                spellColumns++;
            }
            if (magicvii.length > 0) {
                spellColumns++;
            }
            if (magicviii.length > 0) {
                spellColumns++;
            }
            int spellRows = (int) Math.ceil(spellColumns / 2f);
            if (spellColumns > 1) {
                spellColumns = 2;
                sb.append("                                          ");
                sb.append("           MAGIC");
                sb.append('\n');
                sb.append("-------------------------------------------");
                sb.append("-------------------------------------------");
                sb.append("------------------------");
                sb.append('\n');
            } else if (spellColumns == 1) {
                sb.append("                      MAGIC");
                sb.append('\n');
                sb.append("-------------------------------------------");
                sb.append("-----------");
                sb.append('\n');
            }
            for (int i = spellRows; i > 0; i--) {
                int[] col1 = null, col2 = null;
                char[] col1t = null, col2t = null;
                if (magici != null && magici.length > 0) {
                    // magic I needs to be printed
                    col1 = Arrays.copyOf(magici, magici.length);
                    col1t = MagicRealmConsts.MAGIC_TYPES[0];
                    magici = null;
                }
                if (col1 == null || spellColumns == 2 && col2 == null) {
                    if (magicii != null && magicii.length > 0) {
                        if (col1 == null) {
                            col1 = Arrays.copyOf(magicii, magicii.length);
                            col1t = MagicRealmConsts.MAGIC_TYPES[1];
                        } else {
                            col2 = Arrays.copyOf(magicii, magicii.length);
                            col2t = MagicRealmConsts.MAGIC_TYPES[1];
                        }
                        magicii = null;
                    }
                }
                if (col1 == null || spellColumns == 2 && col2 == null) {
                    if (magiciii != null && magiciii.length > 0) {
                        if (col1 == null) {
                            col1 = Arrays.copyOf(magiciii, magiciii.length);
                            col1t = MagicRealmConsts.MAGIC_TYPES[2];
                        } else {
                            col2 = Arrays.copyOf(magiciii, magiciii.length);
                            col2t = MagicRealmConsts.MAGIC_TYPES[2];
                        }
                        magiciii = null;
                    }
                }
                if (col1 == null || spellColumns == 2 && col2 == null) {
                    if (magiciv != null && magiciv.length > 0) {
                        if (col1 == null) {
                            col1 = Arrays.copyOf(magiciv, magiciv.length);
                            col1t = MagicRealmConsts.MAGIC_TYPES[3];
                        } else {
                            col2 = Arrays.copyOf(magiciv, magiciv.length);
                            col2t = MagicRealmConsts.MAGIC_TYPES[3];
                        }
                        magiciv = null;
                    }
                }
                if (col1 == null || spellColumns == 2 && col2 == null) {
                    if (magicv != null && magicv.length > 0) {
                        if (col1 == null) {
                            col1 = Arrays.copyOf(magicv, magicv.length);
                            col1t = MagicRealmConsts.MAGIC_TYPES[4];
                        } else {
                            col2 = Arrays.copyOf(magicv, magicv.length);
                            col2t = MagicRealmConsts.MAGIC_TYPES[4];
                        }
                        magicv = null;
                    }
                }
                if (col1 == null || spellColumns == 2 && col2 == null) {
                    if (magicvi != null && magicvi.length > 0) {
                        if (col1 == null) {
                            col1 = Arrays.copyOf(magicvi, magicvi.length);
                            col1t = MagicRealmConsts.MAGIC_TYPES[5];
                        } else {
                            col2 = Arrays.copyOf(magicvi, magicvi.length);
                            col2t = MagicRealmConsts.MAGIC_TYPES[5];
                        }
                        magicvi = null;
                    }
                }
                if (col1 == null || spellColumns == 2 && col2 == null) {
                    if (magicvii != null && magicvii.length > 0) {
                        if (col1 == null) {
                            col1 = Arrays.copyOf(magicvii, magicvii.length);
                            col1t = MagicRealmConsts.MAGIC_TYPES[6];
                        } else {
                            col2 = Arrays.copyOf(magicvii, magicvii.length);
                            col2t = MagicRealmConsts.MAGIC_TYPES[6];
                        }
                        magicvii = null;
                    }
                }
                if (col1 == null || spellColumns == 2 && col2 == null) {
                    if (magicviii != null && magicviii.length > 0) {
                        if (col1 == null) {
                            col1 = Arrays.copyOf(magicviii, magicviii.length);
                            col1t = MagicRealmConsts.MAGIC_TYPES[7];
                        } else {
                            col2 = Arrays.copyOf(magicviii, magicviii.length);
                            col2t = MagicRealmConsts.MAGIC_TYPES[7];
                        }
                        magicviii = null;
                    }
                }
                // print columns
                printMagicRowToConsole(col1, col1t, col2, col2t, pc);
            }
        }
        String s = sb.toString();
        sb.returnToPool();
        sb = null;
        return s;
    }
    /**
     * Prints the trading relationships to the console.
     * @param pc the {@link MagicRealmCharacter} instance
     * @return {@link String}
     */
    private String printTradingRelationsToConsole(
            final MagicRealmCharacter pc) {
        PooledStringBuilder sb =
                StringBuilderPool.getInstance().getStringBuilder();
        boolean needsComma = false, addedOne = false;
        sb.append("Trading Relationships:\n");
        String[] list = new String[] { "  " };
        // allies
        list = (String[]) ArrayUtilities.getInstance().extendArray(
                "ALLY", list);
        for (int i = 0, len = MagicRealmConsts.TRADE_GROUPS.length; 
                i < len; i++) {
            if (pc.isAlly(MagicRealmConsts.TRADE_GROUPS[i])) {
                if (needsComma) {
                    list = (String[]) ArrayUtilities.getInstance().extendArray(
                            "  ", list);
                    list = (String[]) ArrayUtilities.getInstance().extendArray(
                            " ", list);
                }
                list = (String[]) ArrayUtilities.getInstance().extendArray(
                        MagicRealmConsts.TRADE_GROUP_NAMES[i], list);
                needsComma = true;
                addedOne = true;
            }
        }
        if (!addedOne) {
            list = (String[]) ArrayUtilities.getInstance().extendArray(
                    " ", list);
        }
        needsComma = false;
        addedOne = false;
        // friendlies
        list = (String[]) ArrayUtilities.getInstance().extendArray(
                "  ", list);
        list = (String[]) ArrayUtilities.getInstance().extendArray(
                "FRIENDLY", list);
        for (int i = 0, len = MagicRealmConsts.TRADE_GROUPS.length; 
                i < len; i++) {
            if (pc.isFriendly(MagicRealmConsts.TRADE_GROUPS[i])) {
                if (needsComma) {
                    list = (String[]) ArrayUtilities.getInstance().extendArray(
                            "  ", list);
                    list = (String[]) ArrayUtilities.getInstance().extendArray(
                            " ", list);
                }
                list = (String[]) ArrayUtilities.getInstance().extendArray(
                        MagicRealmConsts.TRADE_GROUP_NAMES[i], list);
                needsComma = true;
                addedOne = true;
            }
        }
        if (!addedOne) {
            list = (String[]) ArrayUtilities.getInstance().extendArray(
                    " ", list);
        }
        needsComma = false;
        addedOne = false;
        // unfriendlies
        list = (String[]) ArrayUtilities.getInstance().extendArray(
                "  ", list);
        list = (String[]) ArrayUtilities.getInstance().extendArray(
                "UNFRIENDLY", list);
        for (int i = 0, len = MagicRealmConsts.TRADE_GROUPS.length; 
                i < len; i++) {
            if (pc.isUnfriendly(MagicRealmConsts.TRADE_GROUPS[i])) {
                if (needsComma) {
                    list = (String[]) ArrayUtilities.getInstance().extendArray(
                            "  ", list);
                    list = (String[]) ArrayUtilities.getInstance().extendArray(
                            " ", list);
                }
                list = (String[]) ArrayUtilities.getInstance().extendArray(
                        MagicRealmConsts.TRADE_GROUP_NAMES[i], list);
                needsComma = true;
                addedOne = true;
            }
        }
        if (!addedOne) {
            list = (String[]) ArrayUtilities.getInstance().extendArray(
                    " ", list);
        }
        needsComma = false;
        addedOne = false;
        // enemies
        list = (String[]) ArrayUtilities.getInstance().extendArray(
                "  ", list);
        list = (String[]) ArrayUtilities.getInstance().extendArray(
                "ENEMY", list);
        for (int i = 0, len = MagicRealmConsts.TRADE_GROUPS.length; 
                i < len; i++) {
            if (pc.isEnemy(MagicRealmConsts.TRADE_GROUPS[i])) {
                if (needsComma) {
                    list = (String[]) ArrayUtilities.getInstance().extendArray(
                            "  ", list);
                    list = (String[]) ArrayUtilities.getInstance().extendArray(
                            " ", list);
                }
                list = (String[]) ArrayUtilities.getInstance().extendArray(
                        MagicRealmConsts.TRADE_GROUP_NAMES[i], list);
                needsComma = true;
                addedOne = true;
            }
        }
        if (!addedOne) {
            list = (String[]) ArrayUtilities.getInstance().extendArray(
                    " ", list);
        }
        
        sb.append(TextProcessor.getInstance().getSelectionsAsColumns(
                3, list, "  "));
        sb.append('\n');
        String s = sb.toString();
        sb.returnToPool();
        sb = null;
        return s;
    }
    /**
     * Raises a character's trading relationship with a group.
     * @param player the character
     * @param group the group's id
     */
    public void raiseRelationship(final MagicRealmCharacter player,
            final int group) {
        if (player.isEnemy(group)) {
            player.removeEnemy(group);
            player.assignUnfriendly(group);
        } else if (player.isUnfriendly(group)) {
            player.removeUnfriendly(group);
            // relationship is now neutral
        } else if (player.isFriendly(group)) {
            player.removeFriendly(group);
            player.assignAlly(group);
        } else if (player.isAlly(group)) {
            // do nothing
        } else { // was Neutral
            player.assignFriendly(group);
        }
    }
    /**
     * Removes a native from the player's knowledge base of natives on the map.
     * @param playerIO the player IO
     * @param nativeIO the native IO
     */
    public void removeFromPlayerKnowledgeNativeOnMap(
            final MagicRealmInteractiveObject playerIO,
            final MagicRealmInteractiveObject nativeIO) {
        int[][] nom = playerIO.getPCData().getNativeOnMap();
        // does the player have this on the map already?
        int prevClearing = -1;
        for (int outer = nom.length - 1; outer >= 0; outer--) {
            int[] list = nom[outer];
            for (int inner = list.length - 1; outer > 0; outer--) {
                if (list[inner] == nativeIO.getRefId()) {
                    prevClearing = list[0];
                    break;
                }
            }
            if (prevClearing >= 0) {
                break;
            }
        }
        if (prevClearing >= 0) { // added to list previously
            // must remove it from list
            for (int outer = nom.length - 1; outer >= 0; outer--) {
                int[] list = nom[outer];
                if (list[0] == prevClearing) {
                    // remove from here
                    int index = -1;
                    for (int inner = list.length - 1; outer > 0; outer--) {
                        if (list[inner] == nativeIO.getRefId()) {
                            index = inner;
                            break;
                        }
                    }
                    // REMOVE AT INDEX LOCATION
                    if (index > 0) {
                        list =
                                ArrayUtilities.getInstance().removeIndex(index,
                                        list);
                        nom[outer] = list;
                        list = null;
                        break;
                    }
                }
                list = null;
            }
        }
        playerIO.getPCData().setNativeOnMap(nom);
        nom = null;
    }
    /**
     * Removes a treasure from the player's knowledge base of items on the map.
     * @param playerIO the player IO
     * @param treasureIO the treasure IO
     */
    public void removeFromPlayerKnowledgeTreasureOnMap(
            final MagicRealmInteractiveObject playerIO,
            final MagicRealmInteractiveObject treasureIO) {
        int[][] tom = playerIO.getPCData().getTreasureOnMap();
        // does the player have this on the map already?
        int prevClearing = -1;
        for (int outer = tom.length - 1; outer >= 0; outer--) {
            int[] list = tom[outer];
            for (int inner = list.length - 1; outer > 0; outer--) {
                if (list[inner] == treasureIO.getRefId()) {
                    prevClearing = list[0];
                    break;
                }
            }
            if (prevClearing >= 0) {
                break;
            }
        }
        if (prevClearing >= 0) { // added to list previously
            // must remove it from list
            for (int outer = tom.length - 1; outer >= 0; outer--) {
                int[] list = tom[outer];
                if (list[0] == prevClearing) {
                    // remove from here
                    int index = -1;
                    for (int inner = list.length - 1; outer > 0; outer--) {
                        if (list[inner] == treasureIO.getRefId()) {
                            index = inner;
                            break;
                        }
                    }
                    // REMOVE AT INDEX LOCATION
                    if (index > 0) {
                        list =
                                ArrayUtilities.getInstance().removeIndex(index,
                                        list);
                        tom[outer] = list;
                        list = null;
                        break;
                    }
                }
                list = null;
            }
        }
        playerIO.getPCData().setTreasureOnMap(tom);
        tom = null;
    }
    /**
     * Removes a treasure from the player's knowledge base of items on the map.
     * @param playerIO the player IO
     * @param treasureIO the treasure IO
     */
    public void removeFromPlayerKnowledgeTreasureWithMe(
            final MagicRealmInteractiveObject playerIO,
            final MagicRealmInteractiveObject treasureIO) {
        int[] twm = playerIO.getPCData().getTreasureWithMe();
        // does the player have this?
        int index = -1;
        for (int i = twm.length - 1; i >= 0; i--) {
            if (twm[i] == treasureIO.getRefId()) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            playerIO.getPCData().setTreasureWithMe(
                    ArrayUtilities.getInstance().removeIndex(index, twm));
        }
        twm = null;
    }
    /**
     * Removes a treasure from the player's knowledge base of items sold by
     * natives.
     * @param playerIO the player IO
     * @param treasureIO the treasure IO
     */
    public void removeFromPlayerKnowledgeTreasureWithNatives(
            final MagicRealmInteractiveObject playerIO,
            final MagicRealmInteractiveObject treasureIO) {
        int[][] twn = playerIO.getPCData().getTreasureWithNatives();
        // does the player have this with natives already?
        int prevNatives = -1;
        for (int outer = twn.length - 1; outer >= 0; outer--) {
            int[] list = twn[outer];
            for (int inner = list.length - 1; outer > 0; outer--) {
                if (list[inner] == treasureIO.getRefId()) {
                    prevNatives = list[0];
                    break;
                }
            }
            if (prevNatives >= 0) {
                break;
            }
        }
        if (prevNatives >= 0) { // added to list previously
            // must remove it from list
            for (int outer = twn.length - 1; outer >= 0; outer--) {
                int[] list = twn[outer];
                if (list[0] == prevNatives) {
                    // remove from here
                    int index = -1;
                    for (int inner = list.length - 1; outer > 0; outer--) {
                        if (list[inner] == treasureIO.getRefId()) {
                            index = inner;
                            break;
                        }
                    }
                    // REMOVE AT INDEX LOCATION
                    if (index > 0) {
                        list =
                                ArrayUtilities.getInstance().removeIndex(index,
                                        list);
                        twn[outer] = list;
                        list = null;
                        break;
                    }
                }
                list = null;
            }
        }
        playerIO.getPCData().setTreasureWithNatives(twn);
        twn = null;
    }
    /**
     * Removes a treasure from the player's knowledge base of items owned by
     * other players.
     * @param playerIO the player IO
     * @param treasureIO the treasure IO
     */
    public void removeFromPlayerKnowledgeTreasureWithPlayers(
            final MagicRealmInteractiveObject playerIO,
            final MagicRealmInteractiveObject treasureIO) {
        int[][] twp = playerIO.getPCData().getTreasureWithPlayer();
        // does the player have this with natives already?
        int prevPlayer = -1;
        for (int outer = twp.length - 1; outer >= 0; outer--) {
            int[] list = twp[outer];
            for (int inner = list.length - 1; outer > 0; outer--) {
                if (list[inner] == treasureIO.getRefId()) {
                    prevPlayer = list[0];
                    break;
                }
            }
            if (prevPlayer >= 0) {
                break;
            }
        }
        if (prevPlayer >= 0) { // added to list previously
            // must remove it from list
            for (int outer = twp.length - 1; outer >= 0; outer--) {
                int[] list = twp[outer];
                if (list[0] == prevPlayer) {
                    // remove from here
                    int index = -1;
                    for (int inner = list.length - 1; outer > 0; outer--) {
                        if (list[inner] == treasureIO.getRefId()) {
                            index = inner;
                            break;
                        }
                    }
                    // REMOVE AT INDEX LOCATION
                    if (index > 0) {
                        list =
                                ArrayUtilities.getInstance().removeIndex(index,
                                        list);
                        twp[outer] = list;
                        list = null;
                        break;
                    }
                }
                list = null;
            }
        }
        playerIO.getPCData().setTreasureWithPlayer(twp);
        twp = null;
    }
    public String toBannerString(final MagicRealmInteractiveObject io) {
        String s = null;
        if (io != null && io.hasIOFlag(MagicRealmConsts.IO_01_PC)) {
            MagicRealmCharacter pc = io.getPCData();
            PooledStringBuilder sb =
                    StringBuilderPool.getInstance().getStringBuilder();
            sb.append("Fame: ");
            sb.append((int) pc.getFame());
            sb.append("\tNotoriety: ");
            sb.append((int) pc.getNotoriety());
            sb.append("\tGold: ");
            sb.append((int) pc.getGold());
            s = sb.toString();
            sb.returnToPool();
            sb = null;
        }
        return s;
    }
    public String victoryPointsToString2(
            final MagicRealmInteractiveObject playerIO) throws Exception {
        PooledStringBuilder sb =
                StringBuilderPool.getInstance().getStringBuilder();
        String[] headers =
                TextProcessor.getInstance().processText(
                        null,
                        null,
                        (String[]) null,
                        TextLoader.getInstance().loadText(
                                "character_selection_dialog.txt",
                                "victory_pt_headers")).split(",");
        int hLen = 0;
        for (int i = headers.length - 1; i >= 0; i--) {
            hLen = Math.max(hLen, headers[i].length());
        }
        // count great treasures
        int trCount = 0;
        MagicRealmInteractiveObject[] items =
                MagicRealmInventory.getInstance().getAllItemsInInventory(
                        playerIO);
        for (int i = items.length - 1; i >= 0; i--) {
            int itemid = items[i].getRefId();
            if (Interactive.getInstance().hasIO(itemid)) {
                MagicRealmInteractiveObject itemIO = items[i];
                if (itemIO.hasIOFlag(MagicRealmConsts.IO_12_TREASURE)
                        && (itemIO.getTreasureData().getType() 
                                == MagicRealmConsts.TREASURE_GREAT 
                                || itemIO.getTreasureData().getType() 
                                == MagicRealmConsts.TREASURE_LARGE_AND_GREAT)) {
                    trCount++;
                }
                itemIO = null;
            }
        }
        items = null;
        sb.append(headers[0]);
        for (int i = hLen - headers[0].length(); i > 0; i--) {
            sb.append(' ');
        }
        sb.append("  ");
        sb.append(trCount);
        sb.append('/');
        sb.append(playerIO.getPCData().getVictoryPtsTreasure());
        sb.append('\n');
        // count spells
        sb.append(headers[1]);
        for (int i = hLen - headers[1].length(); i > 0; i--) {
            sb.append(' ');
        }
        sb.append("  ");
        sb.append(0);
        sb.append('/');
        sb.append(0);
        sb.append('\n');
        // count fame
        sb.append(headers[2]);
        for (int i = hLen - headers[2].length(); i > 0; i--) {
            sb.append(' ');
        }
        sb.append("  ");
        sb.append((int) playerIO.getPCData().getFame());
        sb.append('/');
        sb.append(playerIO.getPCData().getVictoryPtsFame() * 10);
        sb.append('\n');
        // count notoriety
        sb.append(headers[3]);
        for (int i = hLen - headers[3].length(); i > 0; i--) {
            sb.append(' ');
        }
        sb.append("  ");
        sb.append((int) playerIO.getPCData().getNotoriety());
        sb.append('/');
        sb.append(playerIO.getPCData().getVictoryPtsNotoriety() * 20);
        sb.append('\n');
        // count gold
        sb.append(headers[4]);
        for (int i = hLen - headers[4].length(); i > 0; i--) {
            sb.append(' ');
        }
        sb.append("  ");
        sb.append((int) playerIO.getPCData().getGold());
        sb.append('/');
        sb.append(playerIO.getPCData().getVictoryPtsGold() * 30);
        sb.append('\n');
        String text = sb.toString();
        headers = null;
        sb.returnToPool();
        sb = null;
        return text;
    }
    public String victoryPointsToString(
            final MagicRealmInteractiveObject playerIO) throws Exception {
        PooledStringBuilder sb =
                StringBuilderPool.getInstance().getStringBuilder();
        String[] headers =
                TextProcessor.getInstance().processText(
                        null,
                        null,
                        (String[]) null,
                        TextLoader.getInstance().loadText(
                                "character_selection_dialog.txt",
                                "victory_pt_headers")).split(",");

        String[][] columns = new String[5][1];
        int col = 0;
        // count great treasures
        int trCount = 0;
        MagicRealmInteractiveObject[] items =
                MagicRealmInventory.getInstance().getAllItemsInInventory(
                        playerIO);
        for (int i = items.length - 1; i >= 0; i--) {
            int itemid = items[i].getRefId();
            if (Interactive.getInstance().hasIO(itemid)) {
                MagicRealmInteractiveObject itemIO = items[i];
                if (itemIO.hasIOFlag(MagicRealmConsts.IO_12_TREASURE)
                        && (itemIO.getTreasureData().getType() == MagicRealmConsts.TREASURE_GREAT || itemIO
                                .getTreasureData().getType() == MagicRealmConsts.TREASURE_LARGE_AND_GREAT)) {
                    trCount++;
                }
                itemIO = null;
            }
        }
        items = null;
        sb.append(trCount);
        sb.append('/');
        sb.append(playerIO.getPCData().getVictoryPtsTreasure());
        columns[col++][0] = sb.toString();
        sb.setLength(0);
        // count spells
        sb.append(0);
        sb.append('/');
        sb.append(0);
        columns[col++][0] = sb.toString();
        sb.setLength(0);
        // count fame
        sb.append((int) playerIO.getPCData().getFame());
        sb.append('/');
        sb.append(playerIO.getPCData().getVictoryPtsFame() * 10);
        columns[col++][0] = sb.toString();
        sb.setLength(0);
        // count notoriety
        sb.append((int) playerIO.getPCData().getNotoriety());
        sb.append('/');
        sb.append(playerIO.getPCData().getVictoryPtsNotoriety() * 20);
        columns[col++][0] = sb.toString();
        sb.setLength(0);
        // count gold
        sb.append((int) playerIO.getPCData().getGold());
        sb.append('/');
        sb.append(playerIO.getPCData().getVictoryPtsGold() * 30);
        columns[col++][0] = sb.toString();
        sb.setLength(0);
        String table =
                TextProcessor.getInstance().columnsToString(columns, headers,
                        "    ", false);
        String text =
                TextProcessor.getInstance().processText(
                        null,
                        null,
                        table,
                        TextLoader.getInstance().loadText(
                                "character_selection_dialog.txt",
                                "victory_pts_table"));
        headers = null;
        columns = null;
        table = null;
        sb.returnToPool();
        sb = null;
        return text;
    }
}
