package com.dalonedrow.module.ff.systems.wofm.serializers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializer;
import com.badlogic.gdx.utils.JsonValue;
import com.dalonedrow.engine.systems.base.Interactive;
import com.dalonedrow.engine.systems.base.JOGLErrorHandler;
import com.dalonedrow.module.ff.constants.FFScriptConsts;
import com.dalonedrow.module.ff.rpg.Event;
import com.dalonedrow.module.ff.rpg.FFInteractiveObject;
import com.dalonedrow.module.ff.rpg.FFNpc;
import com.dalonedrow.module.ff.rpg.FFScriptable;
import com.dalonedrow.module.ff.systems.wofm.FFInteractive;
import com.dalonedrow.module.ff.systems.wofm.FFWebServiceClient;
import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.pooled.PooledStringBuilder;
import com.dalonedrow.pooled.StringBuilderPool;
import com.dalonedrow.rpg.base.constants.ScriptConsts;
import com.dalonedrow.rpg.base.flyweights.ErrorMessage;
import com.dalonedrow.rpg.base.flyweights.RPGException;
import com.dalonedrow.rpg.base.flyweights.ScriptAction;
import com.dalonedrow.rpg.base.systems.Script;
import com.dalonedrow.rpg.base.systems.WebServiceClient;

/**
 * 
 * @author drau
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class FFNpcSerializer implements Serializer<FFInteractiveObject> {
	@Override
	public FFInteractiveObject read(final Json json, final JsonValue jsonData,
			final Class type) {
		FFInteractiveObject npcIO = null;
		try {
			FFNpc npc = new FFNpc();
			// set behavior
			if (jsonData.has("behavior")) {
				npc.addBehavior(jsonData.getInt("behavior"));
			} else {
				npc.addBehavior(0);
			}
			// set gender
			int val = 0;
			if (jsonData.get("gender").has("id")) {
				val = jsonData.get("gender").getInt("id");
			}
			npc.setGender(val);
			// set name and title
			npc.setName(jsonData.getString("name"));
			npc.setTitle(jsonData.getString("title"));
			npcIO = ((FFInteractive) Interactive.getInstance()).newMob(npc);
			// add initial attribute scores
			if (jsonData.has("attributes")) {
				JsonValue jsonValue = jsonData.get("attributes");
				JsonValue child = jsonValue.child;
				PooledStringBuilder sb =
						StringBuilderPool.getInstance().getStringBuilder();
				do {
					sb.append("INIT_");
					sb.append(child.name());
					npcIO.getScript().setLocalVariable(
							sb.toString(), child.asInt());
					// System.out.println(sb.toString()+"::"+child.asInt());
					sb.setLength(0);
					child = child.next();
				} while (child != null);
				jsonValue = null;
				child = null;
				sb.returnToPool();
				sb = null;
			}
			// set script
			if (jsonData.has("internal_script")) {
				PooledStringBuilder sb =
						StringBuilderPool.getInstance().getStringBuilder();
				try {
					sb.append("com.dalonedrow.module.ff.wofm");
					sb.append(".rpg.script.npcs.");
					sb.append(jsonData.getString("internal_script"));
					sb.append("Script");
					Class clazz = Class.forName(sb.toString());
					Constructor constructor = clazz.getConstructor(
							new Class[] { FFInteractiveObject.class });
					sb.returnToPool();
					sb = null;
					npcIO.setScript(
							(FFScriptable) constructor.newInstance(npcIO));
					clazz = null;
					constructor = null;
				} catch (PooledException | ClassNotFoundException
						| InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException
						| NoSuchMethodException | SecurityException e) {
					throw new RPGException(ErrorMessage.INTERNAL_ERROR, e);
				}
			}
			Script.getInstance().sendInitScriptEvent(npcIO);
			Script.getInstance().sendIOScriptEvent(
					npcIO, ScriptConsts.SM_033_INITEND, null, "");
		} catch (RPGException | PooledException e) {
			JOGLErrorHandler.getInstance().fatalError(e);
		}
		return npcIO;
	}
	@Override
	public void write(final Json json, final FFInteractiveObject object,
			final Class knownType) {
		// TODO Auto-generated method stub

	}

}
