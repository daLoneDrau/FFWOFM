package com.dalonedrow.module.ff.wofm.rpg.script.scriptactions;

import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.module.ff.rpg.FFInteractiveObject;
import com.dalonedrow.rpg.base.constants.IoGlobals;
import com.dalonedrow.rpg.base.constants.ScriptConsts;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.systems.Script;

/**
 * @author drau
 */
public final class SendEventAction extends FFScriptAction {
	private String action;
	private String target;
	private String recipients;
	private String eventname;
	/**
	 * Creates a new instance of {@link SendEventAction}.
	 */
	public SendEventAction() {
		super();
	}
	@SuppressWarnings("unchecked")
	@Override
	public void execute() throws RPGException {
		String evt = null;
		String temp1;
		String temp2;
		String temp3;
		String zonename;
		boolean radius = false;
		boolean zone = false;
		boolean group = false;
		String groupname = null;
		final int SEND_NPC=	1;
		final int  SEND_ITEM=	2;
		final int  SEND_FIX=	4;
		long sendto = SEND_NPC;
		if (recipients != null) {
			if (recipients.contains("G")) {
				group = true;
				groupname = target;
			}
			if (recipients.contains("F")) {
				sendto = SEND_FIX;
			}
			if (recipients.contains("I")) {
				if (sendto == SEND_NPC) {
					sendto = SEND_ITEM;
				} else {
					sendto |= SEND_ITEM;
				}
			}
			if (recipients.contains("N")) {
				sendto |= SEND_NPC;
			}
			if (recipients.contains("R")) {
				radius = true;
			}
			if (recipients.contains("Z")) {
				zone = true;
			}
		}
		float rad = 0;

		if (group
				&& !zone
				&& !radius) {
		} else {
			if (zone) {
				zonename = target;
			}

			if (radius) {
				rad = Float.parseFloat(target);
			}
		}

		int i = 0;

		//while (i < ScriptConsts.SM_75_MAXCMD) {
			//if (!strcmp(temp1, AS_EVENT[i].name + 3)) {
			//	break;
			//}

			//i++;
		//}

		//if (i >= SM_MAXCMD) {
		//	evt = temp1;
		//} else {
		//	evt = AS_EVENT[i].name + 3;
		//}

		FFInteractiveObject oes =
				(FFInteractiveObject) Script.getInstance().getEventSender();
		Script.getInstance().setEventSender(super.getMaster().getIO());

		if (radius) {   // SEND EVENT TO ALL OBJECTS IN A RADIUS
			//EERIE_3D _pos, _pos2;
			//int l = Interactive.getInstance().getMaxIORefId();
			//for (; l >= 0; l--) {
			//	if (Interactive.getInstance().hasIO(l)) {
			//		FFInteractiveObject ffIO = (FFInteractiveObject) Interactive.getInstance().getIO(l);
			//		if (ffIO != null
			//		        && !ffIO.equals(super.getMaster().getIO())
			//		        &&	!ffIO.hasIOFlag(IoGlobals.IO_CAMERA)
			//		        &&	!ffIO.hasIOFlag(IoGlobals.IO_MARKER)
			//		        && (!group
			//		        		|| Script.getInstance().isIOInGroup(
			//		        				ffIO, groupname))) {
			//			if (((sendto & SEND_NPC) && (inter.iobj[l]->ioflags & IO_NPC))
			//			        ||	((sendto & SEND_FIX) && (inter.iobj[l]->ioflags & IO_FIX))
			//			        ||	((sendto & SEND_ITEM) && (inter.iobj[l]->ioflags & IO_ITEM)))
			//			{
			//				GetItemWorldPosition(inter.iobj[l], &_pos);
			//				GetItemWorldPosition(io, &_pos2);
	
			//				if (EEDistance3D(&_pos, &_pos2) <= rad)
			//				{
			//					io->stat_sent++;
			//					Stack_SendIOScriptEvent(inter.iobj[l], 0, temp3, evt);
			//				}
			//			}
			//		}
			//	}
			//}
		} else if (zone) { // SEND EVENT TO ALL OBJECTS IN A ZONE
			//ARX_PATH * ap = ARX_PATH_GetAddressByName(zonename);
//
			//if (ap != null) {
			//	EERIE_3D _pos;
//
			//	for (long l = 0; l < inter.nbmax; l++) {
			//		if ((inter.iobj[l])
			//		        &&	!(inter.iobj[l]->ioflags & IO_CAMERA)
			//		        &&	!(inter.iobj[l]->ioflags & IO_MARKER)
			//		        &&	((!group) || (IsIOGroup(inter.iobj[l], groupname)))
			//		   ) {
			//			if (((sendto & SEND_NPC) && (inter.iobj[l]->ioflags & IO_NPC))
			//			        ||	((sendto & SEND_FIX) && (inter.iobj[l]->ioflags & IO_FIX))
			//			        ||	((sendto & SEND_ITEM) && (inter.iobj[l]->ioflags & IO_ITEM))) {
			//				GetItemWorldPosition(inter.iobj[l], &_pos);
//
			//				if (ARX_PATH_IsPosInZone(ap, _pos.x, _pos.y, _pos.z))
			//				{
			//					io->stat_sent++;
			//					Stack_SendIOScriptEvent(inter.iobj[l], 0, temp3, evt);
			//				}
			//			}
			//		}
			//	}
			//}
		} else if (group) { // sends an event to all members of a group
			int l = Interactive.getInstance().getMaxIORefId();
			for (; l >= 0; l--) {
				if (Interactive.getInstance().hasIO(l)) {
					FFInteractiveObject ffIO = (FFInteractiveObject) Interactive.getInstance().getIO(l);
					if (ffIO != null
					        && !ffIO.equals(super.getMaster().getIO())
					        &&Script.getInstance().isIOInGroup(ffIO, groupname)) {
						super.getMaster().getIO().setStatSent(
								super.getMaster().getIO().getStatSent() + 1);
						Script.getInstance().stackSendIOScriptEvent(
								ffIO,
								0,
								new Object[] { "target", target },
								eventname);
					}
				}
			}
		} else { // SINGLE OBJECT EVENT
		//	long t = GetTargetByNameTarget(temp2);

		//	if (t == -2) t = GetInterNum(io);



		//	if (ValidIONum(t)) {
		//		io->stat_sent++;
		//		Stack_SendIOScriptEvent(inter.iobj[t], 0, temp3, evt);
		//	}
		}

		Script.getInstance().setEventSender(oes);
	}
}
