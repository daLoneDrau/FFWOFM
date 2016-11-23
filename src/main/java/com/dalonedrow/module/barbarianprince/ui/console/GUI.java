package com.dalonedrow.module.barbarianprince.ui.console;

import com.dalonedrow.module.barbarianprince.systems.WebServiceClient;
import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.pooled.PooledStringBuilder;
import com.dalonedrow.pooled.StringBuilderPool;
import com.dalonedrow.rpg.base.consoleui.OutputEvent;
import com.dalonedrow.rpg.base.consoleui.TextProcessor;
import com.dalonedrow.rpg.base.flyweights.ErrorMessage;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.utils.ArrayUtilities;

public class GUI {
    /** the one and only instance of the <code>GUI</code> class. */
    private static GUI          instance;
    public static final int     LAYOUT_CHARACTER_SCREEN    = 1;
    public static final int     LAYOUT_DAYLIGHT_ACTIVITIES = 0;
    public static final int    LAYOUT_INVENTORY_SCREEN    = 2;
    private static final char[] MENU_ITEM_BUFFER           = { ' ', ' ', ' ',
            ' '                                           };
    public static final int     MESSAGE_ERROR              = 0;
    public static final int     MESSAGE_INFO               = 4;
    public static final int     MESSAGE_POSITIVE           = 3;
    public static final int     MESSAGE_STANDARD           = 1;
    public static final int     MESSAGE_WARNING            = 2;
    private static final int    SCREEN_WIDTH               = 120;
    private static final int MAX_MESSAGES = 8;
    /**
     * Gives access to the singleton instance of {@link GUI}.
     * @return {@link GUI}
     */
    public static GUI getInstance() {
        if (GUI.instance == null) {
            GUI.instance = new GUI();
        }
        return GUI.instance;
    }
    private String   art;
    private String   characterActions;
    private String   characterInventoryItem;
    private String   characterActionsHeader;
    private String   characterInfo;
    private boolean  clearMessagesAfterRender;
    private String   condition;
    private String[] errorMessages;
    private int      layout;
    private String   location;
    private String   menuTable;
    private String[] messages;
    private int[]    messageTypes;
    private String   time;
    private String   victoryPoints;
    private String characterInventoryItemHeader;
    private String characterInventory;
    /** flag to display debugging. */
    private boolean debug = false;
    /** Hidden constructor. */
    private GUI() {
        messages = new String[0];
        errorMessages = new String[0];
        messageTypes = new int[0];
    }
    /**
     * Adds a message to be displayed in the GUI.
     * @param type the type of message
     * @param message the message text
     */
    public void addMessage(final int type, final String message) {
    	switch (type) {
    	case GUI.MESSAGE_ERROR:
            errorMessages = ArrayUtilities.getInstance().extendArray(
            		message, errorMessages);
    		break;
    		default:
                messageTypes = ArrayUtilities.getInstance().extendArray(
                		type, messageTypes);
                messages = ArrayUtilities.getInstance().extendArray(
                		message, messages);    			
    	}
    }
    /** Clears all messages. */
    public void clearMessages() {
        messages = new String[0];
        messageTypes = new int[0];
    }
    /** Clears all messages beyond max. */
    public void clearExcessMessages() {
        while (messages.length > MAX_MESSAGES) {
            messages = (String[]) 
                    ArrayUtilities.getInstance().removeIndex(0, messages);
            messageTypes = ArrayUtilities.getInstance().removeIndex(
                    0, messageTypes);
        }
    }
    /** Clears all messages after the next render. */
    public void clearMessagesAfterRender() {
        clearMessagesAfterRender = true;
    }
    private String createTextWindow(final String windowHeader, 
            String windowText, final int minHeight, final int width, 
            final boolean includeSpeedList) 
                    throws Exception {
        final int four = 4, six = 6, eight = 8;
        PooledStringBuilder sb =
                StringBuilderPool.getInstance().getStringBuilder();
        if (windowHeader.equalsIgnoreCase("advantages")) {
            // wrap each line
            windowText =
                    TextProcessor.getInstance().wrapText(windowText,
                            width - four, false, false);
            String[] split = windowText.split("\n");
            int minimumLines = Math.max(minHeight, split.length);
            // pad with extra lines at the bottom
            for (int i = minimumLines - split.length; i > 0; i--) {
                split =
                        (String[]) ArrayUtilities.getInstance().extendArray(
                                " ", split);
            }
            for (int i = 0, lenI = split.length; i < lenI; i++) {
                sb.append(split[i]);
                for (int l = width - four - split[i].length(); l > 0; l--) {
                    sb.append(' ');
                }
                sb.append('\n');
            }
        } else {
            String columnSeparator = "    ";
            // equalize the left column
            String[] leftColumn = windowText.split("\n");
            if (!includeSpeedList) {
                int minimumLines =
                        Math.max(minHeight, leftColumn.length);
                for (int i = minimumLines - leftColumn.length; i > 0; i--) {
                    leftColumn =
                            (String[]) ArrayUtilities.getInstance().extendArray(
                                    " ", leftColumn);
                }
                for (int i = 0, lenI = leftColumn.length; i < lenI; i++) {
                    sb.append(leftColumn[i]);
                    for (int l = width - 4 - leftColumn[i].length(); l > 0; l--) {
                        sb.append(' ');
                    }
                    leftColumn[i] = sb.toString();
                    sb.setLength(0);
                }
                // put the two columns together
                for (int i = 0; i < minimumLines; i++) {
                    sb.append(leftColumn[i]);
                    sb.append('\n');
                }
            } else {
                String[] rightColumn = getSpeedList().split("\n");
                int minimumLines =
                        Math.max(minHeight, Math.max(leftColumn.length,
                                rightColumn.length));
                int leftColumnWidth = 0;
                for (int i = 0, lenI = leftColumn.length; i < lenI; i++) {
                    leftColumnWidth =
                            Math.max(leftColumnWidth, leftColumn[i].length());
                }
                for (int i = minimumLines - leftColumn.length; i > 0; i--) {
                    leftColumn =
                            (String[]) ArrayUtilities.getInstance().extendArray(
                                    " ", leftColumn);
                }
                for (int i = 0, lenI = leftColumn.length; i < lenI; i++) {
                    sb.append(leftColumn[i]);
                    for (int l = leftColumnWidth - leftColumn[i].length(); l > 0; l--) {
                        sb.append(' ');
                    }
                    leftColumn[i] = sb.toString();
                    sb.setLength(0);
                }
                // equalize the right column
                int rightColumnWidth = 0;
                for (int i = minimumLines - rightColumn.length; i > 0; i--) {
                    rightColumn =
                            (String[]) ArrayUtilities.getInstance().extendArray(
                                    " ", rightColumn);
                }
                rightColumnWidth =
                        width - 4 - columnSeparator.length() - leftColumnWidth;
                for (int i = 0, lenI = rightColumn.length; i < lenI; i++) {
                    sb.append(rightColumn[i]);
                    for (int l = rightColumnWidth - rightColumn[i].length(); l > 0; l--) {
                        sb.append(' ');
                    }
                    rightColumn[i] = sb.toString();
                    sb.setLength(0);
                }
                // put the two columns together
                for (int i = 0; i < minimumLines; i++) {
                    sb.append(leftColumn[i]);
                    sb.append(columnSeparator);
                    sb.append(rightColumn[i]);
                    sb.append('\n');
                }
            }
        }
        String text =
                TextProcessor.getInstance().processText(
                        null,
                        null,
                        new String[] { windowHeader, sb.toString() },
                        TextLoader.getInstance().loadText(
                                "daylight_dialog.txt", "table"));
        sb.returnToPool();
        sb = null;
        return text;
    }
    /**
	 * Gets the value for the messages.
	 * @return {@link String[]}
	 */
	public String[] getMessages() {
		return messages;
	}
	private String createCharacterInventoryItemColumn(final int minHeight,
            final int width) throws Exception {
        final int four = 4, six = 6, eight = 8;
        PooledStringBuilder sb =
                StringBuilderPool.getInstance().getStringBuilder();
        String columnSeparator = "    ";
        // equalize the left column
        String[] leftColumn = characterInventoryItem.split("\n");
        String[] rightColumn = getSpeedList().split("\n");
        int minimumLines =
                Math.max(minHeight, Math.max(leftColumn.length,
                        rightColumn.length));
        int leftColumnWidth = 0;
        for (int i = 0, lenI = leftColumn.length; i < lenI; i++) {
            leftColumnWidth =
                    Math.max(leftColumnWidth, leftColumn[i].length());
        }
        for (int i = minimumLines - leftColumn.length; i > 0; i--) {
            leftColumn =
                    (String[]) ArrayUtilities.getInstance().extendArray(
                            " ", leftColumn);
        }
        for (int i = 0, lenI = leftColumn.length; i < lenI; i++) {
            sb.append(leftColumn[i]);
            for (int l = leftColumnWidth - leftColumn[i].length(); l > 0; l--) {
                sb.append(' ');
            }
            leftColumn[i] = sb.toString();
            sb.setLength(0);
        }
        // equalize the right column
        int rightColumnWidth = 0;
        for (int i = minimumLines - rightColumn.length; i > 0; i--) {
            rightColumn =
                    (String[]) ArrayUtilities.getInstance().extendArray(
                            " ", rightColumn);
        }
        rightColumnWidth =
                width - 4 - columnSeparator.length() - leftColumnWidth;
        for (int i = 0, lenI = rightColumn.length; i < lenI; i++) {
            sb.append(rightColumn[i]);
            for (int l = rightColumnWidth - rightColumn[i].length(); l > 0; l--) {
                sb.append(' ');
            }
            rightColumn[i] = sb.toString();
            sb.setLength(0);
        }
        // put the two columns together
        for (int i = 0; i < minimumLines; i++) {
            sb.append(leftColumn[i]);
            sb.append(columnSeparator);
            sb.append(rightColumn[i]);
            sb.append('\n');
        }
        String text =
                TextProcessor.getInstance().processText(
                        null,
                        null,
                        new String[] { characterInventoryItemHeader, 
                                sb.toString() },
                                TextLoader.getInstance().loadText(
                                        "daylight_dialog.txt", "table"));
        sb.returnToPool();
        sb = null;
        return text;
    }
    /**
     * Creates the error messages column.
     * @return {@link String}
     * @throws Exception if an error occurs
     */
    private String createErrorMessagesColumn(final int width) throws Exception {
        final int four = 4, six = 6, eight = 8;
        PooledStringBuilder sb =
                StringBuilderPool.getInstance().getStringBuilder();
        if (errorMessages.length == 0) {
            sb.append(" ");
            for (int l = width - four - sb.length(); l > 0; l--) {
                sb.append(' ');
            }
            sb.append('\n');
        } else {
            for (int i = 0, lenI = errorMessages.length; i < lenI; i++) {
                String[] split = errorMessages[i].split("\n");
                for (int j = 0, lenJ = split.length; j < lenJ; j++) {
                    int maxWidth = width - four;
                    maxWidth -= four;
                    String[] wrapped =
                            TextProcessor.getInstance().wrapText(split[j],
                                    maxWidth, false, false).split("\n");
                    for (int k = 0, lenK = wrapped.length; k < lenK; k++) {
                        sb.append("* ");
                        sb.append(wrapped[k]);
                        for (int l = width - eight - wrapped[k].length(); l > 0; l--) {
                            sb.append(' ');
                        }
                        sb.append(" *");
                        sb.append('\n');
                    }
                    wrapped = null;
                }
                split = null;
                if (i + 1 < lenI) {
                    sb.append('\n');
                }
            }
        }
        String[] mSplit = sb.toString().split("\n");
        sb.setLength(0);
        for (int i = 0, len = mSplit.length; i < len; i++) {
            sb.append(mSplit[i]);
            for (int k = width - four - mSplit[i].length(); k > 0; k--) {
                sb.append(' ');
            }
            sb.append('\n');
        }
        String text =
                TextProcessor.getInstance().processText(
                        null,
                        null,
                        new String[] { sb.toString() },
                        TextLoader.getInstance().loadText(
                                "daylight_dialog.txt", "table_no_title"));
        sb.returnToPool();
        sb = null;
        return text;
    }
    /**
     * Creates the messages column.
     * @param minHeight the minimum number of lines the column needs to have
     * @param width the column width
     * @return {@link String}
     * @throws Exception if an error occurs
     */
    private String createMessagesColumn(final int minHeight, final int width)
            throws Exception {
        if (debug) {
            System.out.println("createMessagesColumn(minHeight=" + minHeight 
                    + ", width=" + width + ")");
        }
        final int four = 4, six = 6, eight = 8;
        PooledStringBuilder sb =
                StringBuilderPool.getInstance().getStringBuilder();
        for (int i = 0, lenI = messages.length; i < lenI; i++) {
            String[] split = messages[i].split("\n");
            for (int j = 0, lenJ = split.length; j < lenJ; j++) {
                int maxWidth = width - four;
                switch (messageTypes[i]) {
                    case MESSAGE_STANDARD:
                        break;
                    case MESSAGE_WARNING:
                    case MESSAGE_POSITIVE:
                    case MESSAGE_INFO:
                    case MESSAGE_ERROR:
                        maxWidth -= four;
                        break;
                    default:
                        throw new Exception("Invalid message type "
                                + messageTypes[i]);
                }
                String[] wrapped =
                        TextProcessor.getInstance().wrapText(split[j],
                                maxWidth, false, false).split("\n");
                for (int k = 0, lenK = wrapped.length; k < lenK; k++) {
                    switch (messageTypes[i]) {
                        case MESSAGE_ERROR:
                            sb.append("* ");
                            break;
                        case MESSAGE_STANDARD:
                            break;
                        case MESSAGE_INFO:
                            sb.append("# ");
                            break;
                        case MESSAGE_WARNING:
                            sb.append("! ");
                            break;
                        case MESSAGE_POSITIVE:
                            sb.append("+ ");
                            break;
                        default:
                            throw new Exception("Invalid message type "
                                    + messageTypes[i]);
                    }
                    sb.append(wrapped[k]);
                    for (int l = width - eight - wrapped[k].length();
                            l > 0; l--) {
                        sb.append(' ');
                    }
                    switch (messageTypes[i]) {
                        case MESSAGE_ERROR:
                            sb.append(" *");
                            break;
                        case MESSAGE_STANDARD:
                            break;
                        case MESSAGE_WARNING:
                            sb.append(" !");
                            break;
                        case MESSAGE_POSITIVE:
                            sb.append(" +");
                            break;
                        case MESSAGE_INFO:
                            sb.append(" #");
                            break;
                        default:
                            throw new Exception("Invalid message type "
                                    + messageTypes[i]);
                    }
                    sb.append('\n');
                }
                wrapped = null;
            }
            split = null;
            if (i + 1 < lenI) {
                sb.append('\n');
            }
        }
        String[] mSplit = sb.toString().split("\n");
        for (int i = minHeight - mSplit.length; i > 0; i--) {
            // pad messages array with blank lines at top
            mSplit =
                    (String[]) ArrayUtilities.getInstance().prependArray(" ",
                            mSplit);
        }
        sb.setLength(0);
        for (int i = 0, len = mSplit.length; i < len; i++) {
            sb.append(mSplit[i]);
            for (int k = width - four - mSplit[i].length(); k > 0; k--) {
                sb.append(' ');
            }
            sb.append('\n');
        }
        String text =
                TextProcessor.getInstance().processText(
                        null,
                        null,
                        new String[] { sb.toString() },
                        TextLoader.getInstance().loadText(
                                "daylight_dialog.txt", "table_no_title"));
        sb.setLength(0);
        sb.returnToPool();
        sb = null;
        if (debug) {
            System.out.println("end createMessagesColumn()");
        }
        return text;
    }
    /**
     * Creates the statistics column displayed on the right-side of the basic
     * activities interface.
     * @return {@link String}
     * @throws Exception if an error occurs
     */
    private String createStatsColumn() throws Exception {
        MagicRealmInteractiveObject io =
                ((MagicRealmController) ProjectConstants.getInstance())
                        .getPCObject();
        PooledStringBuilder sb =
                StringBuilderPool.getInstance().getStringBuilder();
        String victoryTable, timeTable, locationTable, conditionTable;
        String[] vSplit =
                MagicRealmPlayer.getInstance().victoryPointsToString2(
                        (MagicRealmInteractiveObject) ProjectConstants
                                .getInstance().getPCObject()).split("\n");
        String[] tSplit =
                MagicRealmTime.getInstance().toTimeString().split("\n");
        setLocation(io);
        setCondition(io);
        String[] lSplit = location.split("\n");
        String[] cSplit = condition.split("\n");
        // equalize all lines
        int col2Len = 0;
        for (int i = vSplit.length - 1; i >= 0; i--) {
            col2Len = Math.max(col2Len, vSplit[i].length());
        }
        for (int i = tSplit.length - 1; i >= 0; i--) {
            col2Len = Math.max(col2Len, tSplit[i].length());
        }
        for (int i = lSplit.length - 1; i >= 0; i--) {
            col2Len = Math.max(col2Len, lSplit[i].length());
        }
        for (int i = cSplit.length - 1; i >= 0; i--) {
            col2Len = Math.max(col2Len, cSplit[i].length());
        }
        // create victory table
        for (int i = 0, len = vSplit.length; i < len; i++) {
            sb.append(vSplit[i]);
            for (int j = col2Len - vSplit[i].length(); j > 0; j--) {
                sb.append(' ');
            }
            sb.append('\n');
        }
        victoryTable =
                TextProcessor.getInstance().processText(
                        null,
                        null,
                        new String[] { "VICTORY POINTS", sb.toString() },
                        TextLoader.getInstance().loadText(
                                "daylight_dialog.txt", "table"));
        sb.setLength(0);
        // time table
        for (int i = 0, len = tSplit.length; i < len; i++) {
            sb.append(tSplit[i]);
            for (int j = col2Len - tSplit[i].length(); j > 0; j--) {
                sb.append(' ');
            }
            sb.append('\n');
        }
        timeTable =
                TextProcessor.getInstance().processText(
                        null,
                        null,
                        new String[] { "TIME", sb.toString() },
                        TextLoader.getInstance().loadText(
                                "daylight_dialog.txt", "table"));
        sb.setLength(0);
        // location table
        for (int i = 0, len = lSplit.length; i < len; i++) {
            sb.append(lSplit[i]);
            for (int j = col2Len - lSplit[i].length(); j > 0; j--) {
                sb.append(' ');
            }
            sb.append('\n');
        }
        locationTable =
                TextProcessor.getInstance().processText(
                        null,
                        null,
                        new String[] { "LOCATION", sb.toString() },
                        TextLoader.getInstance().loadText(
                                "daylight_dialog.txt", "table"));
        sb.setLength(0);
        // condition table
        for (int i = 0, len = cSplit.length; i < len; i++) {
            sb.append(cSplit[i]);
            for (int j = col2Len - cSplit[i].length(); j > 0; j--) {
                sb.append(' ');
            }
            sb.append('\n');
        }
        conditionTable =
                TextProcessor.getInstance().processText(
                        null,
                        null,
                        new String[] { new String(io.getPCData().getName()),
                                sb.toString() },
                        TextLoader.getInstance().loadText(
                                "daylight_dialog.txt", "table"));
        sb.setLength(0);

        vSplit = victoryTable.split("\n");
        tSplit = timeTable.split("\n");
        lSplit = locationTable.split("\n");
        cSplit = conditionTable.split("\n");
        for (int i = 0, len = vSplit.length; i < len; i++) {
            sb.append(vSplit[i]);
            sb.append('\n');
        }
        sb.append('\n');
        for (int i = 0, len = tSplit.length; i < len; i++) {
            sb.append(tSplit[i]);
            sb.append('\n');
        }
        sb.append('\n');
        for (int i = 0, len = lSplit.length; i < len; i++) {
            sb.append(lSplit[i]);
            sb.append('\n');
        }
        sb.append('\n');
        for (int i = 0, len = cSplit.length; i < len; i++) {
            sb.append(cSplit[i]);
            sb.append('\n');
        }
        String text = sb.toString();
        sb.returnToPool();
        sb = null;
        return text;
    }
    /**
     * Gets the list of speeds.
     * @return {@link String}
     */
    private String getSpeedList() {
        String[] list = new String[0];
        list =
                (String[]) ArrayUtilities.getInstance().extendArray("fastest",
                        list);
        list = (String[]) ArrayUtilities.getInstance().extendArray(" ", list);
        for (int i = 0, len = MagicRealmConsts.ACTION_SPEEDS.length; i < len; i++) {
            if (i == 0) {
                list =
                        (String[]) ArrayUtilities.getInstance().extendArray(
                                "   ^   ", list);
            } else if (i + 1 == len) {
                list =
                        (String[]) ArrayUtilities.getInstance().extendArray(
                                "   v   ", list);
            } else {
                list =
                        (String[]) ArrayUtilities.getInstance().extendArray(
                                "   |   ", list);
            }
            list =
                    (String[]) ArrayUtilities.getInstance()
                            .extendArray(
                                    new String(
                                            MagicRealmConsts.ACTION_SPEEDS[i]),
                                    list);
        }
        list =
                (String[]) ArrayUtilities.getInstance().extendArray("slowest",
                        list);
        list = (String[]) ArrayUtilities.getInstance().extendArray(" ", list);
        String text =
                TextProcessor.getInstance().getSelectionsAsColumns(2, list,
                        "  ");
        list = null;
        return text;
    }
    /**
     * Renders the GUI.
     * @throws Exception if an error occurs
     */
    public void render() throws Exception {
        if (debug) {
            System.out.println("GUI.render()");
        }
        OutputEvent.getInstance().println("", this);
        switch (layout) {
            case LAYOUT_DAYLIGHT_ACTIVITIES:
                renderDaylight();
                clearExcessMessages();
                break;
            case LAYOUT_CHARACTER_SCREEN:
                renderCharacter();
                break;
            case LAYOUT_INVENTORY_SCREEN:
                renderInventory();
                break;
            default:
                throw new Exception("Invalid layout " + layout);
        }
        errorMessages = new String[0];
        if (clearMessagesAfterRender) {
            clearMessages();
        }
    }
    /**
     * Renders the character screen.
     * @throws Exception if an error occurs
     */
    private void renderCharacter() throws Exception {
        PooledStringBuilder sb =
                StringBuilderPool.getInstance().getStringBuilder();
        String[] leftColumnSplit = characterInfo.split("\n");
        int leftWidth = 0;
        for (int i = leftColumnSplit.length - 1; i >= 0; i--) {
            leftWidth = Math.max(leftWidth, leftColumnSplit[i].length());
        }
        String[] errColumnSplit =
                createErrorMessagesColumn(GUI.SCREEN_WIDTH - leftWidth).split(
                        "\n");
        int maxHeight = leftColumnSplit.length;
        String msgCol =
                createTextWindow(characterActionsHeader, // header
                        characterActions, // text
                        maxHeight - 2 - errColumnSplit.length, // height
                        GUI.SCREEN_WIDTH - leftWidth,
                        true); // width
        String[] mSplit = msgCol.split("\n");
        maxHeight = Math.max(maxHeight, mSplit.length + errColumnSplit.length);
        if (maxHeight > leftColumnSplit.length) {
            // center art in the middle
            int extra = maxHeight - leftColumnSplit.length;
            int top = extra / 2, bottom = extra - top;
            sb.setLength(0);
            for (int i = leftWidth; i > 0; i--) {
                sb.append(' ');
            }
            String blank = sb.toString();
            sb.setLength(0);
            for (int i = top; i > 0; i--) {
                leftColumnSplit =
                        (String[]) ArrayUtilities.getInstance().prependArray(
                                blank, leftColumnSplit);
            }
            for (int i = bottom; i > 0; i--) {
                leftColumnSplit =
                        (String[]) ArrayUtilities.getInstance().extendArray(
                                blank, leftColumnSplit);
            }
        }
        // create right column
        String[] rightColumnSplit =
                new String[mSplit.length + errColumnSplit.length];
        System.arraycopy(mSplit, 0, rightColumnSplit, 0, mSplit.length);
        System.arraycopy(errColumnSplit, 0, rightColumnSplit, mSplit.length,
                errColumnSplit.length);
        for (int i = 0; i < maxHeight; i++) {
            sb.append(leftColumnSplit[i]);
            for (int j = leftWidth - leftColumnSplit[i].length(); j > 0; j--) {
                sb.append(' ');
            }
            sb.append(rightColumnSplit[i]);
            OutputEvent.getInstance().println(sb.toString(), this);
            sb.setLength(0);
        }
        if (menuTable != null) {
            OutputEvent.getInstance().print(menuTable, this);
        }
        menuTable = null;
        int i = 0;
        while (i < messages.length) {
            if (messageTypes[i] == GUI.MESSAGE_ERROR) {
                messageTypes =
                        ArrayUtilities.getInstance().removeIndex(i,
                                messageTypes);
                messages =
                        (String[]) ArrayUtilities.getInstance().removeIndex(i,
                                messages);
            } else {
                i++;
            }
        }
    }
    /**
     * Renders the Daylight basic activities screen.
     * @throws Exception if an error occurs
     */
    private void renderDaylight() throws Exception {
        if (debug) {
            System.out.println("GUI.renderDaylight()");
        }
        PooledStringBuilder sb =
                StringBuilderPool.getInstance().getStringBuilder();
        String statsCol = createStatsColumn();
        if (art != null) {
            String[] aSplit = art.split("\n");
            int artWidth = 0;
            for (int i = aSplit.length - 1; i >= 0; i--) {
                artWidth = Math.max(artWidth, aSplit[i].length());
            }
            String[] sSplit = statsCol.split("\n");
            int statsWidth = 0;
            for (int i = sSplit.length - 1; i >= 0; i--) {
                statsWidth = Math.max(statsWidth, sSplit[i].length());
            }
            int maxHeight = aSplit.length;
            maxHeight = Math.max(maxHeight, sSplit.length);
            String[] errColumnSplit =
                    createErrorMessagesColumn(
                            GUI.SCREEN_WIDTH - artWidth - statsWidth).split(
                            "\n");
            String msgCol = createMessagesColumn(
                    maxHeight - 2 - errColumnSplit.length, // height
                    GUI.SCREEN_WIDTH - artWidth - statsWidth); // width
            String[] mSplit = msgCol.split("\n");
            // create middle column
            String[] middleColumnSplit =
                    new String[mSplit.length + errColumnSplit.length];
            System.arraycopy(mSplit, 0, middleColumnSplit, 0, mSplit.length);
            System.arraycopy(errColumnSplit, 0, middleColumnSplit,
                    mSplit.length, errColumnSplit.length);
            maxHeight = Math.max(maxHeight, middleColumnSplit.length);
            if (maxHeight > sSplit.length) {
                // add extra blank lines to stats
                for (int i = maxHeight - sSplit.length; i > 0; i--) {
                    sSplit =
                            (String[]) ArrayUtilities.getInstance()
                                    .extendArray(" ", sSplit);
                }
            }
            if (maxHeight > aSplit.length) {
                // center art in the middle
                int extra = maxHeight - aSplit.length;
                int top = extra / 2, bottom = extra - top;
                sb.setLength(0);
                for (int i = artWidth; i > 0; i--) {
                    sb.append(' ');
                }
                String blank = sb.toString();
                sb.setLength(0);
                for (int i = top; i > 0; i--) {
                    aSplit =
                            (String[]) ArrayUtilities.getInstance()
                                    .prependArray(blank, aSplit);
                }
                for (int i = bottom; i > 0; i--) {
                    aSplit =
                            (String[]) ArrayUtilities.getInstance()
                                    .extendArray(blank, aSplit);
                }
            }
            for (int i = 0; i < maxHeight; i++) {
                sb.append(aSplit[i]);
                for (int j = artWidth - aSplit[i].length(); j > 0; j--) {
                    sb.append(' ');
                }
                sb.append(middleColumnSplit[i]);
                sb.append(sSplit[i]);
                OutputEvent.getInstance().println(sb.toString(), this);
                sb.setLength(0);
            }
        } else {

        }
        if (menuTable != null) {
            OutputEvent.getInstance().print(menuTable, this);
        }
        if (debug) {
            System.out.println("end GUI.renderDaylight()");
        }
        menuTable = null;
    }
    private void renderInventory() throws Exception {
        PooledStringBuilder sb =
                StringBuilderPool.getInstance().getStringBuilder();
        String[] leftColumnSplit = art.split("\n");
        int leftWidth = 0;
        for (int i = leftColumnSplit.length - 1; i >= 0; i--) {
            leftWidth = Math.max(leftWidth, leftColumnSplit[i].length());
        }
        String[] errColumnSplit =
                createErrorMessagesColumn(GUI.SCREEN_WIDTH - leftWidth).split(
                        "\n");
        int maxHeight = leftColumnSplit.length;
        String msgCol = createTextWindow(characterInventoryItemHeader,
                characterInventoryItem,
                maxHeight - 2 - errColumnSplit.length, // height
                GUI.SCREEN_WIDTH - leftWidth,
                false); // width
        String[] mSplit = msgCol.split("\n");
        maxHeight = Math.max(maxHeight, mSplit.length + errColumnSplit.length);
        if (maxHeight > leftColumnSplit.length) {
            // center art in the middle
            int extra = maxHeight - leftColumnSplit.length;
            int top = extra / 2, bottom = extra - top;
            sb.setLength(0);
            for (int i = leftWidth; i > 0; i--) {
                sb.append(' ');
            }
            String blank = sb.toString();
            sb.setLength(0);
            for (int i = top; i > 0; i--) {
                leftColumnSplit =
                        (String[]) ArrayUtilities.getInstance().prependArray(
                                blank, leftColumnSplit);
            }
            for (int i = bottom; i > 0; i--) {
                leftColumnSplit =
                        (String[]) ArrayUtilities.getInstance().extendArray(
                                blank, leftColumnSplit);
            }
        }
        // create right column
        String[] rightColumnSplit =
                new String[mSplit.length + errColumnSplit.length];
        System.arraycopy(mSplit, 0, rightColumnSplit, 0, mSplit.length);
        System.arraycopy(errColumnSplit, 0, rightColumnSplit, mSplit.length,
                errColumnSplit.length);
        for (int i = 0; i < maxHeight; i++) {
            sb.append(leftColumnSplit[i]);
            for (int j = leftWidth - leftColumnSplit[i].length(); j > 0; j--) {
                sb.append(' ');
            }
            sb.append(rightColumnSplit[i]);
            OutputEvent.getInstance().println(sb.toString(), this);
            sb.setLength(0);
        }
        if (menuTable != null) {
            OutputEvent.getInstance().print(menuTable, this);
        }
        menuTable = null;
    }
    /**
     * Sets the art displayed in the GUI.
     * @param val the ASCII art
     */
    public void setArt(final String val) {
        if (debug) {
            System.out.println("setArt(\n" + val);
        }
        art = val;
    }
    /**
     * Sets the character's actions section text.
     * @param header the section header
     * @param text the section text
     */
    public void setCharacterActions(final String header, final String text) {
        characterActionsHeader = header;
        characterActions = text;
    }
    /**
     * Sets the character inventory item.
     * @param header the panel header
     * @param text the panel text
     */
    public void setCharacterInventoryItem(final String header,
            final String text) {
        characterInventoryItemHeader = header;
        characterInventoryItem = text;
    }
    /**
     * Sets the text of the basic character info panel.
     * @param val the panel text.
     */
    public void setCharacterBasicInfo(final String val) {
        characterInfo = val;
    }
    /**
     * Sets the condition table.
     * @param io the player IO
     * @throws Exception if an error occurs
     */
    private void setCondition(final MagicRealmInteractiveObject io)
            throws Exception {
        PooledStringBuilder sb =
                StringBuilderPool.getInstance().getStringBuilder();
        if (io.getPCData().isHidden()) {
            sb.append("HIDDEN");
        } else {
            sb.append("VISIBLE");
        }
        condition = sb.toString();
        sb.returnToPool();
        sb = null;
    }
    /**
     * Sets the value of the layout.
     * @param val the value to set
     */
    public void setLayout(final int val) {
        layout = val;
    }
    /**
     * Sets the location table.
     * @param io the player IO
     * @throws Exception if an error occurs
     */
    private void setLocation(final MagicRealmInteractiveObject io)
            throws Exception {
        PooledStringBuilder sb =
                StringBuilderPool.getInstance().getStringBuilder();
        int ioloc = MagicRealmMap.getInstance().getClearingForIO(io.getRefId());
        HexTile tile = MagicRealmMap.getInstance().getHexTileForClearing(ioloc);
        sb.append(tile.getName());
        sb.append(", ");
        sb.append(MagicRealmMap.getInstance().getClearing(ioloc).getName());
        sb.append(" - ");
        sb.append(MagicRealmConsts.CLEARING_TYPES[MagicRealmMap.getInstance()
                .getClearingTypeForIO(io.getRefId())]);
        sb.append("\n");
        sb.append("The clearing contains:\n");
        String[] list = new String[0];
        String[] fetch =
                MagicRealmMap.getInstance().getDwellingNamesForClearing(ioloc);
        for (int i = 0, len = fetch.length; i < len; i++) {
            list =
                    (String[]) ArrayUtilities.getInstance().extendArray("  ",
                            list);
            list =
                    (String[]) ArrayUtilities.getInstance().extendArray(
                            fetch[i], list);
        }
        fetch = MagicRealmMap.getInstance().getMonsterNamesForClearing(ioloc);
        for (int i = 0, len = fetch.length; i < len; i++) {
            list =
                    (String[]) ArrayUtilities.getInstance().extendArray("  ",
                            list);
            list =
                    (String[]) ArrayUtilities.getInstance().extendArray(
                            fetch[i], list);
        }
        fetch = MagicRealmMap.getInstance().getNativeNamesForClearing(ioloc);
        for (int i = 0, len = fetch.length; i < len; i++) {
            list =
                    (String[]) ArrayUtilities.getInstance().extendArray("  ",
                            list);
            list =
                    (String[]) ArrayUtilities.getInstance().extendArray(
                            fetch[i], list);
        }
        fetch = MagicRealmMap.getInstance().getPcNamesForClearing(ioloc);
        for (int i = 0, len = fetch.length; i < len; i++) {
            list =
                    (String[]) ArrayUtilities.getInstance().extendArray("  ",
                            list);
            list =
                    (String[]) ArrayUtilities.getInstance().extendArray(
                            fetch[i], list);
        }
        fetch = MagicRealmMap.getInstance().getItemNamesForClearing(ioloc);
        for (int i = 0, len = fetch.length; i < len; i++) {
            list =
                    (String[]) ArrayUtilities.getInstance().extendArray("  ",
                            list);
            list =
                    (String[]) ArrayUtilities.getInstance().extendArray(
                            fetch[i], list);
        }
        if (list.length == 0) {
            list =
                    (String[]) ArrayUtilities.getInstance().extendArray("  ",
                            list);
            list =
                    (String[]) ArrayUtilities.getInstance().extendArray(
                            "-nothing-", list);
        }
        sb.append(TextProcessor.getInstance().getSelectionsAsColumns(2, list,
                "  "));
        sb.append('\n');
        sb.append(MagicRealmMap.getInstance().consolePrintClearingAdjacencies(
                io, tile));
        location = sb.toString();
        sb.returnToPool();
        sb = null;
    }
    /**
     * Sets the menu displayed in the GUI.
     * @param title the menu title
     * @param header the menu header
     * @param options the list of menu options
     * @param prompt the text displayed before the cursor prompt
     * @throws RPGException if an error occurs
     */
    public void setMenuMultipleColumns(final String title, final String header,
            final String[] options, final String prompt) throws RPGException {
        PooledStringBuilder sb =
                StringBuilderPool.getInstance().getStringBuilder();
        final int four = 4;
        // find the max width in the options
        int maxLen = 0;
        for (int i = options.length - 1; i >= 0; i--) {
            maxLen = Math.max(maxLen, options[i].length());
        }
        int maxWidth = GUI.SCREEN_WIDTH - four;
        String[] hSplit = header.split("\n");
        for (int i = 0, len = hSplit.length; i < len; i++) {
            if (hSplit[i].length() > maxWidth) {
                hSplit[i] = TextProcessor.getInstance().wrapText(
                        hSplit[i], maxWidth);
            }
            sb.append(hSplit[i]);
            for (int j = maxWidth - hSplit[i].length(); j > 0; j--) {
                sb.append(' ');
            }
            sb.append('\n');
        }
        String headerText = sb.toString();
        sb.setLength(0);
        int numberOfColumns = 1;
        while (true) {
            int currCol = 0;
            int[] columnLengths = new int[numberOfColumns];
            // measure all columns to see if you can fit X number across the
            // screen
            for (int i = 0, len = options.length; i < len; i++) {
                columnLengths[currCol] =
                        Math.max(columnLengths[currCol], options[i].length());
                currCol++;
                if (currCol >= numberOfColumns) {
                    currCol = 0;
                }
            }
            // test all columns
            int testLen = 0;
            for (int i = columnLengths.length - 1; i >= 0; i--) {
                testLen += columnLengths[i];
                if (i > 0) {
                    testLen += GUI.MENU_ITEM_BUFFER.length;
                }
            }
            if (testLen <= maxWidth) {
                if (numberOfColumns == options.length) {
                    break;
                }
                numberOfColumns++;
            } else {
                numberOfColumns--;
                break;
            }
        }
        if (numberOfColumns < 1) {
            throw new RPGException(ErrorMessage.INVALID_OPERATION,
            		"Invalid # of columns in menu!");
        }
        // measure each column
        int currentColumn = 0;
        int[] columnLengths = new int[numberOfColumns];
        // measure all columns to see if you can fit X number across the
        // screen
        for (int i = 0, len = options.length; i < len; i++) {
            columnLengths[currentColumn] =
                    Math.max(columnLengths[currentColumn], options[i].length());
            currentColumn++;
            if (currentColumn >= numberOfColumns) {
                currentColumn = 0;
            }
        }
        // calculate left over space to add to each column
        int extra = 0, tot = 0;
        for (int i = columnLengths.length - 1; i >= 0; i--) {
            tot += columnLengths[i];
            if (i > 0) {
                tot += GUI.MENU_ITEM_BUFFER.length;
            }
        }
        extra = maxWidth - tot;
        // add left over space to each column
        while (extra > 0) {
            int minIndex = 0, minLen = Integer.MAX_VALUE;
            for (int i = columnLengths.length - 1; i >= 0; i--) {
                if (minLen > columnLengths[i]) {
                    minLen = columnLengths[i];
                    minIndex = i;
                }
            }
            columnLengths[minIndex] = columnLengths[minIndex] + 1;
            extra--;
        }
        // put all options into columns
        currentColumn = 0;
        int lineLength = 0;
        for (int i = 0, len = options.length; i < len; i++) {
            sb.append(options[i]);
            lineLength += options[i].length();
            for (int j = columnLengths[currentColumn] - options[i].length(); 
                    j > 0; j--) {
                sb.append(' ');
                lineLength++;
            }
            if (currentColumn + 1 < numberOfColumns) {
                sb.append(GUI.MENU_ITEM_BUFFER);
                lineLength += GUI.MENU_ITEM_BUFFER.length;
                currentColumn++;
            } else {
                for (int j = maxWidth - lineLength; j > 0; j--) {
                    sb.append(' ');
                }
                sb.append('\n');
                currentColumn = 0;
                lineLength = 0;
            }
        }
        // put it all into a table
        menuTable = WebServiceClient.getInstance().loadText("menu_table");
        menuTable = TextProcessor.getInstance().processText(
                        null,
                        null,
                        new String[] { title, headerText, sb.toString(), 
                                prompt },
                        menuTable);
    }
    /**
     * Sets the menu displayed in the GUI.
     * @param title the menu title
     * @param header the menu header
     * @param options the list of menu options
     * @param prompt the text displayed before the cursor prompt
     * @throws RPGException if an error occurs
     */
    public void setMenuSingleColumn(final String title, final String header,
            final String[] options, final String prompt) throws RPGException {
        PooledStringBuilder sb =
                StringBuilderPool.getInstance().getStringBuilder();
        String headerText = null;
        try {
        final int four = 4;
        // find the max width in the options
        int maxLen = 0;
        for (int i = options.length - 1; i >= 0; i--) {
            maxLen = Math.max(maxLen, options[i].length());
        }
        int maxWidth = GUI.SCREEN_WIDTH - four;
        String[] hSplit = header.split("\n");
        for (int i = 0, len = hSplit.length; i < len; i++) {
            if (hSplit[i].length() > maxWidth) {
                hSplit[i] = TextProcessor.getInstance().wrapText(
                        hSplit[i], maxWidth);
            }
				sb.append(hSplit[i]);
	            for (int j = maxWidth - hSplit[i].length(); j > 0; j--) {
	                sb.append(' ');
	            }
	            if (i + 1 < len) {
	                sb.append('\n');
	            }
        }
        headerText = sb.toString();
        sb.setLength(0);
        for (int i = 0, len = options.length; i < len; i++) {
            sb.append(TextProcessor.TAB);
            sb.append(options[i]);
            for (int j = maxWidth - sb.length(); j > 0; j--) {
                sb.append(' ');
            }
            if (i + 1 < len) {
                sb.append('\n');
            }
        }
		} catch (PooledException e) {
			throw new RPGException(ErrorMessage.INTERNAL_ERROR, e);
		}
        String menuItemText = sb.toString();
        sb.setLength(0);
        // put it all into a table
        menuTable = WebServiceClient.getInstance().loadText("menu_table");
        menuTable = TextProcessor.getInstance().processText(
                        null,
                        null,
                        new String[] { title, headerText, menuItemText, 
                                prompt },
                        menuTable);
    }
}
