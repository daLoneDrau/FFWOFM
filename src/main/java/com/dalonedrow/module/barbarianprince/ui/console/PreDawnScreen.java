package com.dalonedrow.module.barbarianprince.ui.console;

import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.engine.systems.base.JOGLErrorHandler;
import com.dalonedrow.engine.systems.base.ProjectConstants;
import com.dalonedrow.module.barbarianprince.constants.UiSettings;
import com.dalonedrow.module.barbarianprince.graph.HexMap;
import com.dalonedrow.module.barbarianprince.rpg.BpIoGroup;
import com.dalonedrow.module.barbarianprince.systems.BpController;
import com.dalonedrow.module.barbarianprince.systems.BpInteractive;
import com.dalonedrow.module.barbarianprince.systems.GameState;
import com.dalonedrow.module.barbarianprince.systems.WebServiceClient;
import com.dalonedrow.module.barbarianprince.turn.TimeTrack;
import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.pooled.PooledStringBuilder;
import com.dalonedrow.pooled.StringBuilderPool;
import com.dalonedrow.rpg.base.consoleui.ConsoleView;
import com.dalonedrow.rpg.base.consoleui.InputProcessor;
import com.dalonedrow.rpg.base.consoleui.OutputEvent;
import com.dalonedrow.rpg.base.consoleui.Panel;
import com.dalonedrow.rpg.base.consoleui.TextProcessor;
import com.dalonedrow.rpg.base.flyweights.ErrorMessage;
import com.dalonedrow.rpg.base.flyweights.RPGException;

/**
 * 
 * @author drau
 *
 */
public final class PreDawnScreen extends ConsoleView {
	/** the one and only instance of the <code>PreDawnScreen</code> class. */
	private static PreDawnScreen instance;
	/**
	 * Gives access to the singleton instance of {@link PreDawnScreen}.
	 * @return {@link PreDawnScreen}
	 */
	public static PreDawnScreen getInstance() {
		if (PreDawnScreen.instance == null) {
			PreDawnScreen.instance = new PreDawnScreen();
		}
		return PreDawnScreen.instance;
	}
	/** the text to display. */
	private char[] text;
	private String buildRightColumn() throws RPGException {
		final int colHt = 20, r1Ht = 6;
		PooledStringBuilder sb =
				StringBuilderPool.getInstance().getStringBuilder();
		try {
			sb.append(TimeTrack.getInstance().toUiString());
			sb.append("\n\n");
			sb.append(HexMap.getInstance().toUITableString(
					BpController.getInstance().getPartyLocation()));
			sb.append("\n \n");
			String s = new Panel(UiSettings.RIGHT_COLUMN_WIDTH,
					true,
					sb.toString().split("\n").length + 2, // 6 lines
					sb.toString()).getConsoleText();
			sb.setLength(0);
			String[] split = s.split("\n");
			for (int i = 0, len = split.length - 1; i < len; i++) {
				sb.append(split[i]);
				sb.append('\n');
			}
			BpIoGroup group = (BpIoGroup)
					((BpInteractive) Interactive.getInstance()).getIO(
							BpController.getInstance().getPlayerParty());
			sb.append(new Panel(UiSettings.RIGHT_COLUMN_WIDTH,
					true,
					colHt - r1Ht,
					group.getUiTablePartyList(),
					"ARMY"
					).getConsoleText());
		} catch (PooledException e) {
			throw new RPGException(ErrorMessage.INTERNAL_ERROR, e);
		}
		String s = sb.toString();
		sb.returnToPool();
		sb = null;
		return s;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render() throws RPGException {
		TimeTrack.getInstance().toString();
		OutputEvent.getInstance().print(buildRightColumn(), this);
	}
}
