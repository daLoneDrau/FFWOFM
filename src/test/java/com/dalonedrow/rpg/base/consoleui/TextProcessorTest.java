package com.dalonedrow.rpg.base.consoleui;

import org.junit.Assert;
import org.junit.Test;

import com.dalonedrow.module.ff.systems.wofm.FFWebServiceClient;
import com.dalonedrow.rpg.base.flyweights.RPGException;

public class TextProcessorTest {
	@Test
	public void createTable() {
		try {
			System.out.println(TextProcessor.getInstance().processText(
			        null,
			        null,
			        new String[] { "title", "lorem ipsum\nlorem" },
			        "<table><title><genText></title><genText></table>"));
		} catch (RPGException ex) {
			ex.printStackTrace();
			Assert.fail(ex.getMessage());
		}
	}
}
