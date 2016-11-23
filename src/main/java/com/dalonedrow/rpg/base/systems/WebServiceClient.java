package com.dalonedrow.rpg.base.systems;

/**
 * 
 * @author drau
 *
 */
public abstract class WebServiceClient {
	/** the one and only instance of the <code>Interactive</code> class. */
	@SuppressWarnings("rawtypes")
	private static WebServiceClient instance;
	/**
	 * Gives access to the singleton instance of {@link Interactive}.
	 * @return {@link Interactive}
	 */
	@SuppressWarnings("rawtypes")
	public static WebServiceClient getInstance() {
		return WebServiceClient.instance;
	}
	/**
	 * Sets the singleton instance.
	 * @param i the instance to set
	 */
	@SuppressWarnings("rawtypes")
	protected static void setInstance(final WebServiceClient i) {
		WebServiceClient.instance = i;
	}
	/**
	 * Gets a result from the treasure table with a call to the web service.
	 * @param name the name of the text section
	 * @return {@link String}[]
	 */
	public abstract String getTextByName(final String name);
}
