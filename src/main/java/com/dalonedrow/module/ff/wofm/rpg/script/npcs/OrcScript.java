package com.dalonedrow.module.ff.wofm.rpg.script.npcs;

import com.dalonedrow.engine.systems.base.Diceroller;
import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.engine.systems.base.Time;
import com.dalonedrow.module.ff.rpg.FFInteractiveObject;
import com.dalonedrow.module.ff.rpg.FFScriptable;
import com.dalonedrow.module.ff.wofm.rpg.script.items.OrcCleaverScript;
import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.rpg.base.constants.Behaviour;
import com.dalonedrow.rpg.base.constants.IoGlobals;
import com.dalonedrow.rpg.base.constants.ScriptConsts;
import com.dalonedrow.rpg.base.flyweights.BaseInteractiveObject;
import com.dalonedrow.rpg.base.flyweights.ErrorMessage;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.flyweights.ScriptTimer;
import com.dalonedrow.rpg.base.flyweights.ScriptTimerAction;
import com.dalonedrow.rpg.base.flyweights.ScriptTimerInitializationParameters;
import com.dalonedrow.rpg.base.systems.Script;

/**
 * $: GLOBAL TEXT £: LOCAL TEXT #: GLOBAL LONG §: LOCAL LONG &: GLOBAL FLOAT @:
 * LOCAL FLOAT
 * 
 * based on goblin_base.asl
 * @author drau
 */
@SuppressWarnings("unchecked")
public class OrcScript extends FFScriptable {
	/**
	 * Creates a new instance of {@link OrcCleaverScript}.
	 * @param io the IO associated with the script
	 */
	public OrcScript(final FFInteractiveObject io) {
		super(io);
	}
	private void attackPlayerAfterOuch() throws RPGException {
		super.setLocalVariable("ignorefailure", 0);
		if (Script.getInstance()
				.getGlobalIntVariableValue("PLAYERSPELL_INVISIBILITY") == 1) {
			lookForSuite();
		} else {
			// turn off the 'lookfor' timer
			Script.getInstance().timerClearByNameAndIO("lookfor",
					super.getIO());
			// turn off the 'heard' timer
			Script.getInstance().timerClearByNameAndIO("heard", super.getIO());
			super.setLocalVariable("panicmode", 1);
			super.setLocalVariable("looking_for", 0);
			super.setLocalVariable("enemy", 1);
			// turn off hearing - io is in combat
			super.assignDisallowedEvent(ScriptConsts.DISABLE_HEAR);
			if (super.getLocalIntVariableValue("noise_heard") < 2) {
				super.setLocalVariable("noise_heard", 2);
			}
			callForHelp();
			if (super.getLocalIntVariableValue("player_enemy_send") == 0) {
				super.setLocalVariable("player_enemy_send", 1);
				// send event to all members of group that the player attacked
				Script.getInstance().stackSendGroupScriptEvent(
						super.getLocalStringVariableValue("friend"),
						0, null, "onPlayerEnemy");
				System.out.println("PLAYER_ENEMY sent");
			}
			// kill all local timers
			Script.getInstance().timerClearAllLocalsForIO(super.getIO());
			// clear the quiet timer
			Script.getInstance().timerClearByNameAndIO("quiet", super.getIO());
			// SET_NPC_STAT BACKSTAB 0
			if (super.getIO().getNPCData()
					.getBaseLife() < super.getLocalIntVariableValue(
							"cowardice")) {
				// flee();
			} else if (super.getLocalIntVariableValue("fighting_mode") == 2) {
				// running away
			} else {
				if (super.getLocalIntVariableValue("fighting_mode") == 1) {
					super.setLocalVariable("reflection_mode", 2);
				} else {
					if (super.getLocalStringVariableValue(
							"attached_object") != "NONE") {
						// DETACH ~£attached_object~ SELF
						// OBJECT_HIDE ~£attached_object~ ON
						// SET £attached_object "NONE"
						super.setLocalVariable("attached_object", "NONE");
					}
					saveBehavior();
					// reset misc reflection timer
					Script.getInstance().stackSendIOScriptEvent(super.getIO(),
							0, null, "onMiscReflection");
					if (super.getLocalIntVariableValue("fighting_mode") == 3
							&& super.getIO().getNPCData()
									.getBaseLife() < super.getLocalIntVariableValue(
											"cowardice")) {
						// flee();
					} else if (super.getLocalIntVariableValue("tactic") == 2) {
						// flee(); // coward
					} else {
						if (super.getLocalIntVariableValue("spotted") == 0) {
							super.setLocalVariable("spotted", 1);
							// hail aggressively
							// SPEAK -a ~£hail~ NOP
						}
						super.setLocalVariable("reflection_mode", 2);
						super.setLocalVariable("fighting_mode", 1);
						if (super.getLocalIntVariableValue("tactic") == 0) {
							super.getIO().getNPCData().ARX_NPC_Behaviour_Change(
									Behaviour.BEHAVIOUR_FIGHT.getFlag()
											+ Behaviour.BEHAVIOUR_MOVE_TO
													.getFlag(),
									0);
						} else if (super.getLocalIntVariableValue(
								"tactic") == 1) {
							super.getIO().getNPCData().ARX_NPC_Behaviour_Change(
									Behaviour.BEHAVIOUR_FIGHT.getFlag()
											+ Behaviour.BEHAVIOUR_SNEAK
													.getFlag()
											+ Behaviour.BEHAVIOUR_MOVE_TO
													.getFlag(),
									0);
						} else if (super.getLocalIntVariableValue(
								"tactic") == 3) {
							super.getIO().getNPCData().ARX_NPC_Behaviour_Change(
									Behaviour.BEHAVIOUR_MAGIC.getFlag()
											+ Behaviour.BEHAVIOUR_MOVE_TO
													.getFlag(),
									0);
						}
						// set pathfinding to target player
						// SETTARGET -a PLAYER
						System.out.println("WEAPON ON");
						// set weapon in hand
						// TODO - set weapon
						super.getIO().getNPCData()
								.setMovemode(IoGlobals.RUNMODE);
					}
				}
			}
		}
	}
	private void callForHelp() throws RPGException {
		if (!super.getLocalStringVariableValue("friend")
				.equalsIgnoreCase("NONE")) {
			if (super.getLocalIntVariableValue("controls_off") == 0) {
				long tmp = Script.getInstance().getGlobalLongVariableValue(
						"GAMESECONDS");
				try {
					tmp -= super.getLocalLongVariableValue("last_call_help");
				} catch (PooledException e) {
					throw new RPGException(ErrorMessage.INTERNAL_ERROR, e);
				}
				if (tmp > 4) {
					// don't call for help too often...
					System.out.println("CALL FOR HELP !!!");
					if (super.getLocalIntVariableValue("fighting_mode") == 2) {
						Script.getInstance().stackSendGroupScriptEvent(
								super.getLocalStringVariableValue("friend"),
								0, null, "callHelp");
						// TODO -
						// also send call to everyone within 1200 unit radius
					} else {
						Script.getInstance().stackSendGroupScriptEvent(
								super.getLocalStringVariableValue("friend"),
								0, null, "callHelp");
						// TODO -
						// also send call to everyone within 600 unit radius
					}
					super.setLocalVariable("last_call_help",
							Script.getInstance().getGlobalLongVariableValue(
									"GAMESECONDS"));
				}
			}
		}
	}
	private void flee() throws RPGException, PooledException {
		if (super.getLocalIntVariableValue("fighting_mode") != 2) {
			// change to fleeing
			super.setLocalVariable("fighting_mode", 2);
			System.out.println("Fleeing");
			// kill local timers
			Script.getInstance().timerClearAllLocalsForIO(super.getIO());
			// turn off hearing and collisions - io is fleeing
			super.assignDisallowedEvent(ScriptConsts.DISABLE_HEAR);
			super.assignDisallowedEvent(ScriptConsts.DISABLE_COLLIDE_NPC);
			super.setLocalVariable("reflection_mode", 0);
			if (super.getLocalStringVariableValue("helping_buddy")
					.equalsIgnoreCase("NOBUDDY")) {
				super.getIO().getNPCData().ARX_NPC_Behaviour_Change(
						Behaviour.BEHAVIOUR_FLEE.getFlag(), 1000);
				// set pathfinding to target player
				// SETTARGET PLAYER
				super.getIO().getNPCData().setMovemode(IoGlobals.RUNMODE);
				if (Script.getInstance().isIOInGroup(super.getIO(), "UNDEAD")) {
					super.getIO().getNPCData().setMovemode(IoGlobals.WALKMODE);
				}
			} else {
				Script.getInstance().stackSendIOScriptEvent(super.getIO(),
						0,
						new Object[] { "flee_marker",
								super.getLocalStringVariableValue(
										"flee_marker") },
						"Panic");
				super.getIO().getNPCData().ARX_NPC_Behaviour_Change(
						Behaviour.BEHAVIOUR_MOVE_TO.getFlag(), 0);
				// set pathfinding to target helping buddy
				// SETTARGET -a £helping_buddy
				super.getIO().getNPCData().setMovemode(IoGlobals.RUNMODE);
				if (Script.getInstance().isIOInGroup(super.getIO(), "UNDEAD")) {
					super.getIO().getNPCData().setMovemode(IoGlobals.WALKMODE);
				}
			}
			callForHelp();
			ScriptTimerInitializationParameters<FFInteractiveObject> timerParams =
					new ScriptTimerInitializationParameters<FFInteractiveObject>();
			timerParams.setName("coward");
			timerParams.setScript(this);
			timerParams.setIo(super.getIO());
			timerParams.setMilliseconds(2000);
			timerParams.setStartTime(Time.getInstance().getGameTime());
			timerParams.setRepeatTimes(1);
			timerParams.setObj(Script.getInstance());
			try {
				timerParams.setMethod(
						Script.class.getMethod("stackSendIOScriptEvent",
								new Class[] { BaseInteractiveObject.class,
										int.class, Object[].class,
										String.class }));
			} catch (NoSuchMethodException | SecurityException e) {
				throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT, e);
			}
			timerParams.setArgs(new Object[] {
					super.getIO(), 0, new Object[] {
							"speak_duration", 5,
							"speak_tone", "aggressive",
							"speak_text",
							super.getLocalStringVariableValue(
									"help")
					},
					"speakNoRepeat"
			});
			Script.getInstance().startTimer(timerParams);
			timerParams.clear();

			timerParams.setName("home");
			timerParams.setScript(this);
			timerParams.setIo(super.getIO());
			timerParams.setMilliseconds(30000);
			timerParams.setStartTime(Time.getInstance().getGameTime());
			timerParams.setRepeatTimes(1);
			timerParams.setObj(this);
			try {
				timerParams.setMethod(getClass().getMethod("goHome"));
			} catch (NoSuchMethodException | SecurityException e) {
				throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT, e);
			}
			timerParams.setRepeatTimes(1);
			Script.getInstance().startTimer(timerParams);
			timerParams = null;
		}
	}
	private void lookFor() throws RPGException {
		if (super.getLocalIntVariableValue("controls_off") == 0) {
			Script.getInstance().timerClearByNameAndIO("lookfor",
					super.getIO());
			if (super.getLocalIntVariableValue("confused") == 1
					|| Script.getInstance().getGlobalLongVariableValue(
							"PLAYERSPELL_INVISIBILITY") == 1) {
				lookForSuite();
			} else if (Script.getInstance().getGlobalLongVariableValue(
					"DIST_PLAYER") < 500) {
				playerDetected();
			}
		}
	}
	private void lookForSuite() throws RPGException {
		if (super.getLocalIntVariableValue("controls_off") == 0) {
			if (super.getLocalIntVariableValue("looking_for") > 2) {
				playerDetected();
			} else {
				if (super.getLocalIntVariableValue("fighting_mode") <= 1) {
					super.getIO().getNPCData().ARX_NPC_Behaviour_Change(
							Behaviour.BEHAVIOUR_LOOK_FOR.getFlag(), 500);
					// SETTARGET -a PLAYER
					super.getIO().getNPCData().setMovemode(IoGlobals.WALKMODE);
					super.setLocalVariable("looking_for", 2);
					super.setLocalVariable("fighting_mode", 0);
					super.removeDisallowedEvent(ScriptConsts.DISABLE_HEAR);
					super.setLocalVariable("reflection_mode", 3);
					int timerNum = Script.getInstance().timerGetFree();
					if (timerNum >= 0) {
						// after 30 seconds, go home
						ScriptTimer timer =
								Script.getInstance().getScriptTimer(timerNum);
						timer.setScript(this);
						timer.setExists(true);
						timer.setIo(super.getIO());
						timer.setCycleLength(18000);
						timer.setName("home");
						// invoke method go home
						try {
							timer.setAction(new ScriptTimerAction(this, // object
									getClass().getMethod("goHome"), // method
									null));
						} catch (NoSuchMethodException | SecurityException e) {
							// TODO Auto-generated catch block
							throw new RPGException(ErrorMessage.INTERNAL_ERROR,
									e);
						}
						timer.setRepeatTimes(1);
						timer.clearFlags();
					}
				}
			}
		}
	}
	public int miscReflection() throws RPGException {
		if (super.getIO().getPoisonLevel() > 0) {
			if (super.getLocalIntVariableValue("enemy") != 1) {
				super.setLocalVariable("enemy", 1);
				ouchSuite();
			} else if (super.getLocalIntVariableValue("reflection_mode") > 0
					&& Script.getInstance()
							.getGlobalIntVariableValue("SHUT_UP") != 1) {
				int tmp;
				if (super.getLocalIntVariableValue("reflection_mode") == 2) {
					// in fighting mode -> more reflections - roll 1d10 + 3
					tmp = Diceroller.getInstance().rolldXPlusY(10, 3);
				} else {
					// in fighting mode -> more reflections - roll 1d32 + 5
					tmp = Diceroller.getInstance().rolldXPlusY(32, 5);
				}
				if (super.getLocalStringVariableValue("type")
						.contains("undead")) {
					tmp /= 2;
				}
				// set next reflection timer
				ScriptTimerInitializationParameters<FFInteractiveObject> timerParams =
						new ScriptTimerInitializationParameters<FFInteractiveObject>();
				timerParams.setName("misc_reflection");
				timerParams.setScript(this);
				timerParams.setFlagValues(1);
				timerParams.setIo(super.getIO());
				timerParams.setMilliseconds(tmp);
				timerParams.setStartTime(Time.getInstance().getGameTime());
				timerParams.setRepeatTimes(0);
				timerParams.setObj(Script.getInstance());
				try {
					timerParams.setMethod(
							Script.class.getMethod("stackSendIOScriptEvent",
									new Class[] { BaseInteractiveObject.class,
											int.class, Object[].class,
											String.class }));
				} catch (NoSuchMethodException | SecurityException e) {
					throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT,
							e);
				}
				timerParams.setArgs(new Object[] {
						super.getIO(), 0, null,
						"miscReflection"
				});
				Script.getInstance().startTimer(timerParams);
				timerParams = null;

				if (super.getLocalIntVariableValue("reflection_mode") == 1) {
					if (super.getLocalStringVariableValue("misc") != null) {
						if (super.getLocalIntVariableValue(
								"short_reflections") == 1) {
							if (Diceroller.getInstance().rolldX(2) == 1) {
								// SENDEVENT SPEAK_NO_REPEAT SELF "6 N
								// [Human_male_misc_short]" ACCEPT
							}
						} else {
							// SENDEVENT SPEAK_NO_REPEAT SELF "10 N £misc"
						}
					}
				} else if (super.getLocalIntVariableValue(
						"reflection_mode") == 2
						&& super.getLocalStringVariableValue(
								"threat") != null) {
					// SENDEVENT SPEAK_NO_REPEAT SELF "3 A £threat"
				} else if (super.getLocalStringVariableValue(
						"search") != null) {
					// SENDEVENT SPEAK_NO_REPEAT SELF "3 N £search"
				}
			}
		}
		return ScriptConsts.ACCEPT;
	}
	/*
	 * (non-Javadoc)
	 * @see com.dalonedrow.rpg.base.flyweights.Scriptable#onInit()
	 */
	@Override
	public int onInit() throws RPGException {
		super.setLocalVariable("voice", "");
		super.assignDisallowedEvent(ScriptConsts.DISABLE_HEAR);
		// 1 : PLAYER_ENEMY event already sent by this NPC
		super.setLocalVariable("player_enemy_send", 0);
		// to avoid to many CALL_FOR_HELP events
		super.setLocalVariable("last_call_help", 0);
		// name of attached object (if one)
		super.setLocalVariable("attached_object", "NONE");
		// if 1 : must have a SPECIAL_ATTACK in
		// the code (ratmen & mummies for instance)
		super.setLocalVariable("special_attack", 0);
		// meynier... tu dors...
		super.setLocalVariable("sleeping", 0);
		// when = 1, attack mice
		super.setLocalVariable("care_about_mice", 0);

		// in order to restore the main behavior after a look_for or a help
		super.setLocalVariable("main_behavior_stacked", 0);
		// 0: nothing, 1: normal, 2: threat, 3: search
		super.setLocalVariable("reflection_mode", 0);
		// used for various reasons,
		// 1 indicates that the NPC currently sees he player.
		super.setLocalVariable("player_in_sight", 0);

		// if a npc hears a sound more than 3 times, he detects the player
		super.setLocalVariable("noise_heard", 0);
		// 1 = the NPC is about to look for the player 2=looking for him
		super.setLocalVariable("looking_for", 0);
		// 0 = NO 1 = Fighting 2 = Fleeing
		super.setLocalVariable("fighting_mode", 0);
		super.setLocalVariable("last_heard", "NOHEAR");
		super.setLocalVariable("snd_tim", 0);
		super.setLocalVariable("ouch_tim", 0);
		// used for chats to save current reflection_mode
		super.setLocalVariable("saved_reflection", 0);
		// to stop looping anims if ATTACK_PLAYER called
		super.setLocalVariable("frozen", 0);
		// defines if the NPC has already said "I'll get you" to the player
		super.setLocalVariable("spotted", 0);
		// defines the current dialogue position
		super.setLocalVariable("chatpos", 0);
		// current target
		super.setLocalVariable("targ", 0);
		// this stores the name of the current attacked mice, so that the NPC
		// doesn't attack another one until this one is dead.
		super.setLocalVariable("targeted_mice", "NOMOUSE");
		// this stores the name of the current NPC that his being helped.
		super.setLocalVariable("helping_target", "NOFRIEND");
		// this might change, but it currently defines the ONLY
		// key that the NPC carries with them
		super.setLocalVariable("key_carried", "NOKEY");
		// this is used to check what door is dealing the npc with right now.
		super.setLocalVariable("targeted_door", "NODOOR");
		// set backstab stat to 1
		// SET_NPC_STAT BACKSTAB 1
		// only for spell casters
		super.setLocalVariable("spell_ready", 1);
		// friend to run to in case of trouble
		super.setLocalVariable("helping_buddy", "NOBUDDY");
		// last time someone spoke
		super.setLocalVariable("last_reflection", 0);
		// go back to this marker if combat finished
		super.setLocalVariable("init_marker", "NONE");
		super.setLocalVariable("friend", "NONE");
		// set detection value, from -1 (off) to 100
		// SETDETECT 40
		// the number of attempt at passing a locked door
		super.setLocalVariable("door_locked_attempt", 0);
		// set the radius for physics
		// PHYSICAL RADIUS 30
		// set material
		// SET_MATERIAL FLESH
		// SET_ARMOR_MATERIAL LEATHER
		// SET_STEP_MATERIAL Foot_bare
		// SET_BLOOD 0.9 0.1 0.1
		// inventory created as part of IO
		// SETIRCOLOR 0.8 0.0 0.0
		// stats are set during serialization
		// SET_NPC_STAT RESISTMAGIC 10
		// SET_NPC_STAT RESISTPOISON 10
		// SET_NPC_STAT RESISTFIRE 1
		// defines if the NPC is enemy to the player at the moment
		super.setLocalVariable("enemy", 0);
		// when = 0, the NPC is not sure if he saw the
		// player "did that thing move over there ?"
		super.setLocalVariable("panicmode", 0);
		// 0 = normal 1 = sneak 2 = rabit 3 = caster
		super.setLocalVariable("tactic", 0);
		// used to restore previous tactic after a repel undead
		super.setLocalVariable("current_tactic", 0);
		// if life < cowardice, NPC flees
		super.setLocalVariable("cowardice", 8);
		// level of magic needed to confuse this npc
		super.setLocalVariable("confusability", 3);
		// if damage < pain , no hit anim
		super.setLocalVariable("pain", 1);
		// new set the value for the npc heals himself
		super.setLocalVariable("low_life_alert", 1);
		super.setLocalVariable("friend", "goblin");
		super.setLocalVariable("type", "goblin_base");

		return super.onInit();
	}
	/*
	 * (non-Javadoc)
	 * @see com.dalonedrow.rpg.base.flyweights.Scriptable#onInitEnd()
	 */
	@Override
	public int onInitEnd() throws RPGException {
		System.out.println("onInitEnd OrcScript");
		if (super.getLocalIntVariableValue("enemy") == 1) {
			// turn hearing back on
			super.removeDisallowedEvent(ScriptConsts.DISABLE_HEAR);
		}
		if (!"none".equalsIgnoreCase(
				super.getLocalStringVariableValue("friend"))) {
			Script.getInstance().addToGroup(super.getIO(),
					super.getLocalStringVariableValue("friend"));
		}
		int scale = 95;
		scale += Diceroller.getInstance().rolldX(10);
		super.setLocalVariable("scale", scale);
		// SETSCALE §scale
		if (super.getLocalIntVariableValue("care_about_mice") == 1) {
			Script.getInstance().addToGroup(super.getIO(), "MICECARE");
		}
		if ("goblin_base".equalsIgnoreCase(
				super.getLocalStringVariableValue("type"))) {
			Interactive.getInstance().prepareSetWeapon(getIO(), "Orc Cleaver");
			// TODO set additional stats

			// TODO load animations

			// TODO set reflection texts

			// set reflection timer
			ScriptTimerInitializationParameters<FFInteractiveObject> timerParams =
					new ScriptTimerInitializationParameters<FFInteractiveObject>();
			timerParams.setName("misc_reflection");
			timerParams.setScript(this);
			timerParams.setFlagValues(1);
			timerParams.setIo(super.getIO());
			timerParams.setMilliseconds(10000);
			timerParams.setStartTime(Time.getInstance().getGameTime());
			timerParams.setRepeatTimes(0);
			timerParams.setObj(Script.getInstance());
			try {
				timerParams.setMethod(
						Script.class.getMethod("stackSendIOScriptEvent",
								new Class[] { BaseInteractiveObject.class,
										int.class, Object[].class,
										String.class }));
			} catch (NoSuchMethodException | SecurityException e) {
				throw new RPGException(ErrorMessage.INTERNAL_BAD_ARGUMENT, e);
			}
			timerParams.setArgs(new Object[] {
					super.getIO(), 0, null,
					"miscReflection"
			});
			Script.getInstance().startTimer(timerParams);
			timerParams = null;
		}
		return super.onInitEnd();
	}
	/*
	 * (non-Javadoc)
	 * @see com.dalonedrow.rpg.base.flyweights.Scriptable#onOuch()
	 */
	@Override
	public int onOuch() throws RPGException {
		try {
			ouchStart();
			ouchSuite();
		} catch (PooledException e) {
			throw new RPGException(ErrorMessage.INTERNAL_ERROR, e);
		}
		return ScriptConsts.ACCEPT;
	}
	/*
	 * (non-Javadoc)
	 * @see com.dalonedrow.rpg.base.flyweights.Scriptable#onInit()
	 */
	@Override
	public int onSpellcast() throws RPGException {
		// SHUT_UP global var means a cinematic is playing - no spells cast
		// if caster is not PC - no spells cast
		if (Script.getInstance().getGlobalIntVariableValue("SHUT_UP") != 0
				&& Script.getInstance().getEventSender()
						.hasIOFlag(IoGlobals.IO_01_PC)) {
			/*
			 * IF (§casting_lvl != 0) { IF (^$PARAM1 == NEGATE_MAGIC) { SET
			 * #NEGATE ^#PARAM2 ACCEPT } } IF ( SELF ISGROUP UNDEAD ) { IF
			 * (^$PARAM1 == REPEL_UNDEAD) { SET #REPEL ^#PARAM2 IF (£type ==
			 * "undead_lich") { IF (^#PARAM2 < 6) { HERO_SAY -d
			 * "pas assez fort, mon fils" ACCEPT } } GOTO REPEL } } IF (^$PARAM1
			 * == CONFUSE) { IF (^#PARAM2 < §confusability) ACCEPT SENDEVENT
			 * UNDETECTPLAYER SELF "" SET §confused 1 ACCEPT } IF (§enemy == 0)
			 * ACCEPT IF (£type == "human_ylside") ACCEPT IF (£type ==
			 * "undead_lich") ACCEPT IF (^$PARAM1 == HARM) { IF (^PLAYER_LIFE <
			 * 20) ACCEPT GOTO NO_PAIN_REPEL } IF (^$PARAM1 == LIFE_DRAIN) { IF
			 * (^PLAYER_LIFE < 20) ACCEPT GOTO NO_PAIN_REPEL } IF (^$PARAM1 ==
			 * MANA_DRAIN) { IF (§casting_lvl == 0) ACCEPT IF (^PLAYER_LIFE <
			 * 20) ACCEPT GOTO NO_PAIN_REPEL } ACCEPT
			 */
		}
		return super.onSpellcast();
	}
	/**
	 * Starts the ouch event.
	 * @throws PooledException if an error occurs
	 * @throws RPGException if an error occurs
	 */
	private void ouchStart() throws PooledException, RPGException {
		System.out.print("OUCH ");
		float ouchDmg = super.getLocalFloatVariableValue("SUMMONED_OUCH")
				+ super.getLocalFloatVariableValue("OUCH");
		System.out.print(ouchDmg);
		int painThreshold = super.getLocalIntVariableValue("pain");
		System.out.print(" PAIN THRESHOLD ");
		System.out.println(painThreshold);
		if (ouchDmg < painThreshold) {
			if (Script.getInstance()
					.getGlobalIntVariableValue("PLAYERCASTING") == 0) {
				// Script.getInstance().forceAnimation(HIT_SHORT);
			}
			if (super.getLocalIntVariableValue("enemy") == 0) {
				// clear all speech
			}
		} else { // damage is above pain threshold
			long tmp = Script.getInstance().getGlobalLongVariableValue(
					"GAMESECONDS");
			tmp -= super.getLocalLongVariableValue("ouch_time");
			if (tmp > 4) {
				// been more than 4 seconds since last recorded ouch?
				// force hit animation
				// Script.getInstance().forceAnimation(HIT);
				// set current time as last ouch
				super.setLocalVariable("ouch_time",
						Script.getInstance().getGlobalLongVariableValue(
								"GAMESECONDS"));
			}
			tmp = painThreshold;
			tmp *= 3;
			if (ouchDmg >= tmp) {
				if (Diceroller.getInstance().rolldX(2) == 2) {
					// speak angrily "ouch_strong"
				}
			} else {
				tmp = painThreshold;
				tmp *= 2;
				if (ouchDmg >= tmp) {
					if (Diceroller.getInstance().rolldX(2) == 2) {
						// speak angrily "ouch_medium"
					}
				} else {
					if (Diceroller.getInstance().rolldX(2) == 2) {
						// speak angrily "ouch"
					}
				}
			}
		}
	}
	private void ouchSuite() throws RPGException {
		if (super.getLocalIntVariableValue("controls_off") != 0) {
			// don't react to aggression
		} else {
			if (Script.getInstance().getEventSender().hasIOFlag(
					IoGlobals.IO_01_PC)) {
				if (super.getLocalIntVariableValue("player_in_sight") == 0) {
					// player not in sight
					super.setLocalVariable("enemy", 1);
					// LOOK FOR ATTACKER
				}
				// turn off aggression - io is in combat
				super.assignDisallowedEvent(ScriptConsts.DISABLE_AGGRESSION);
				super.setLocalVariable("spotted", 1);
				attackPlayerAfterOuch();
			}
		}
	}
	public int playerDetected() throws RPGException {
		// if player is invisible ignore the event
		// IF (^PLAYERSPELL_INVISIBILITY == 1) ACCEPT

		// if IO is confused, ignore the event
		if (super.getLocalIntVariableValue("confused") != 1) {
			super.setLocalVariable("player_in_sight", 1);
			if (super.getLocalIntVariableValue("controls_off") == 0) {
				// SET_NPC_STAT BACKSTAB 0
				if (super.getLocalIntVariableValue("enemy") != 0
						|| super.getLocalIntVariableValue("fighting_mode") != 2
						|| super.getLocalIntVariableValue("sleeping") != 1) {
					if (super.getLocalIntVariableValue("panicmode") > 0) {
						// attackPlayer();
					} // ELSE IF (^DIST_PLAYER < 600) GOTO ATTACK_PLAYER
					else {
						// set reflection timer
						ScriptTimerInitializationParameters<FFInteractiveObject> timerParams =
								new ScriptTimerInitializationParameters<FFInteractiveObject>();
						timerParams.setName("doubting");
						timerParams.setScript(this);
						timerParams.setFlagValues(0);
						timerParams.setIo(super.getIO());
						timerParams.setMilliseconds(3000);
						timerParams
								.setStartTime(Time.getInstance().getGameTime());
						timerParams.setRepeatTimes(1);
						timerParams.setObj(Script.getInstance());
						try {
							timerParams.setMethod(
									Script.class.getMethod(
											"stackSendIOScriptEvent",
											new Class[] {
													BaseInteractiveObject.class,
													int.class, Object[].class,
													String.class }));
						} catch (NoSuchMethodException | SecurityException e) {
							throw new RPGException(
									ErrorMessage.INTERNAL_BAD_ARGUMENT, e);
						}
						timerParams.setArgs(new Object[] {
								super.getIO(), 0, null,
								"attackPlayer"
						});
						Script.getInstance().startTimer(timerParams);
						timerParams = null;
						// set panic mode to 2 to start doubting
						super.setLocalVariable("panicmode", 2);
						super.setLocalVariable("noise_heard", 2);
						super.setLocalVariable("looking_for", 0);
						// speak who goes there
						// SPEAK -a ~£whogoesthere~ NOP
						super.setLocalVariable("reflection_mode", 0);
						Script.getInstance().timerClearByNameAndIO("quiet",
								super.getIO());
						saveBehavior();
						super.getIO().getNPCData()
								.addBehavior(Behaviour.BEHAVIOUR_MOVE_TO);
						// SETTARGET PLAYER
						super.getIO().getNPCData()
								.setMovemode(IoGlobals.WALKMODE);
					}
				}
			}
		}
		return ScriptConsts.ACCEPT;
	}
	private void saveBehavior() throws RPGException {
		Script.getInstance().timerClearByNameAndIO("colplayer", super.getIO());
		if (super.getLocalIntVariableValue("main_behavior_stacked") == 0) {
			if (super.getLocalIntVariableValue("frozen") == 1) {
				// frozen anim -> wake up !
				super.setLocalVariable("frozen", 0);
				// PLAYANIM NONE
				// PLAYANIM -2 NONE
				// PHYSICAL ON
				// COLLISION ON
				super.getIO().getNPCData().ARX_NPC_Behaviour_Change(
						Behaviour.BEHAVIOUR_FRIENDLY.getFlag(), 0);
				// SETTARGET PLAYER
			}
			super.setLocalVariable("main_behavior_stacked", 1);
			System.out.println("stack");
			super.getIO().getNPCData().ARX_NPC_Behaviour_Stack();
		} else {
			// CLEAR_MICE_TARGET
		}
	}

}
