package com.dalonedrow.module.ff.wofm.rpg.script.scriptactions;

import com.dalonedrow.rpg.base.constants.IoGlobals;
import com.dalonedrow.rpg.base.flyweights.RPGException;

/**
 * @author drau
 */
public final class SoundAmbienceAction extends FFScriptAction {
	private String action;
	private float parameter;
	/**
	 * Creates a new instance of {@link SoundAmbienceAction}.
	 */
	public SoundAmbienceAction() {
		super();
	}
	@Override
	public void execute() throws RPGException {
		float volume = 1.0f;
		if ("SET_VOLUME".equalsIgnoreCase(action)) {
			volume = parameter;
			// ARX_SOUND_PlayScriptAmbiance(piece, ARX_SOUND_PLAY_LOOPED,
			// volume * DIV100);
		} else if ("NEW_SOUND".equalsIgnoreCase(action)) {
			// ARX_SOUND_PlayScriptAmbiance(piece, ARX_SOUND_PLAY_ONCE);
		} else if ("MUTE".equalsIgnoreCase(action)) {
			// ARX_SOUND_SetAmbianceTrackStatus(temp, temp2, 1); //1 = Mute
		} else if ("UNMUTE".equalsIgnoreCase(action)) {
			// ARX_SOUND_SetAmbianceTrackStatus(temp, temp2, 0); //0 = Unmute
		} else if ("KILL".equalsIgnoreCase(action)) {
			// ARX_SOUND_KillAmbiances();
		} else {
			// ARX_SOUND_PlayScriptAmbiance(temp);
		}
	}
}
