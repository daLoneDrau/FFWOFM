package com.dalonedrow.module.ff.wofm.rpg.script.scriptactions;

import com.dalonedrow.module.ff.rpg.FFScriptable;
import com.dalonedrow.rpg.base.flyweights.ScriptAction;

/**
 * 
 * @author drau
 *
 */
public abstract class FFScriptAction implements ScriptAction<FFScriptable> {
	/** the master script for this {@link ScriptAction}. */
	private FFScriptable	master;
	/**
	 * Gets the master script for this {@link ScriptAction}.
	 * @return {@link FFScriptable}
	 */
	protected final FFScriptable getMaster() {
		return master;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void setScript(final FFScriptable script) {
		master = script;
	}
}
