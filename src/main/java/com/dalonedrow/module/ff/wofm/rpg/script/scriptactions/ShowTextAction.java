package com.dalonedrow.module.ff.wofm.rpg.script.scriptactions;

import com.dalonedrow.module.ff.systems.ConsoleInterface;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.flyweights.ScriptAction;
import com.dalonedrow.rpg.base.systems.WebServiceClient;

/**
 * @author drau
 */
public final class ShowTextAction extends FFScriptAction {
	/** flag indicating whether the text is an error message or not. */
	private final boolean	error;
	/** the text to display. */
	private final String	textId;
	/**
	 * Creates a new instance of {@link ShowTextAction}.
	 * @param e flag indicating whether the text is an error message or not
	 * @param id the reference id of the text to display
	 */
	public ShowTextAction(final boolean e, final String id) {
		super();
		error = e;
		textId = id;
	}
	@Override
	public void execute() throws RPGException {
		if (error) {
			ConsoleInterface.getInstance().getCurrentView().addErrorMessage(
					WebServiceClient.getInstance().getTextByName(textId));
		} else {
			ConsoleInterface.getInstance().getCurrentView().addMessage(
					WebServiceClient.getInstance().getTextByName(textId));
		}
	}
}
