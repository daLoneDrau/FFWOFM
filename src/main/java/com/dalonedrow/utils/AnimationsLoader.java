/**
 * TextureLoader takes the name of an images list file, and loads all images
 * from that list. images loaded can be of four different types: o - a single
 * image n - a numbered list of images s - a number of images loaded as a
 * graphics strip - such as a gif with several images in it g - a group of
 * images each with different names
 * @author Owner
 */
package com.dalonedrow.utils;

import java.io.BufferedReader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.dalonedrow.pooled.PooledStringBuilder;
import com.dalonedrow.pooled.StringBuilderPool;

/**
 * @author Based on the work of Andrew Davison from <u>Killer Game Programming
 *         in Java</u>
 */
public final class AnimationsLoader {
	/** the one and only instance of the <code>SpriteLibrary</code> class. */
	private static AnimationsLoader instance;
	/**
	 * Gives access to the singleton instance of {@link AnimationsLoader}.
	 * @return {@link AnimationsLoader}
	 */
	public static AnimationsLoader getInstance() {
		return AnimationsLoader.instance;
	}
	/**
	 * Gives access to the singleton instance of {@link AnimationsLoader}.
	 * @param name the name of the animations file to load
	 * @param folder the directory containing the image source files and listing
	 * @return {@link AnimationsLoader}
	 */
	public static AnimationsLoader getInstance(final String name,
			final String folder)
					throws Exception {
		instance = new AnimationsLoader(name, folder);
		return AnimationsLoader.instance;
	}
	/** //logger for this class and subclasses. */
	// private final Log //logger = LogFactory.getLog(this.getClass());
	/** the folder containing the image library. */
	private final String libraryFolder;
	/**
	 * Creates a new instance of <code>TextureLoader</code>.
	 * @param name the name of the image file to load
	 * @param folder the directory containing the image source files and listing
	 * @throws Exception if there are problems loading the supplied file
	 */
	public AnimationsLoader(final String name, final String folder)
			throws Exception {
		// logger.info("TextureLoader(" + name + ")");
		libraryFolder = folder;
		try {
			loadFramesFile(name);
		} catch (Exception exception) {
			throw exception;
		}
		AnimationsLoader.instance = this;
	}
	/**
	 * Loads an images file and populate the hashmaps.
	 * @param name the name of the image file to load
	 * @throws Exception if there are problems loading the supplied file
	 */
	public void loadFramesFile(final String name) throws Exception {
		// logger.info("loadImagesFile(" + name + ")");
		if (libraryFolder == null) {
			throw new Exception("libraryFolder is null");
		}
		PooledStringBuilder fileName =
				StringBuilderPool.getInstance().getStringBuilder();
		fileName.append(libraryFolder);
		fileName.append("/");
		fileName.append(name);
		FileHandle file = Gdx.files.internal(fileName.toString());
		try {
			final int defaultCharBufferSize = 8192;
			BufferedReader input = file.reader(defaultCharBufferSize);
			String inText = ""; // String object to read
			// in each line of the file
			inText = input.readLine(); // priming read
			// as long as lines are being read from file
			while (inText != null) {
				if (inText.length() == 0
						|| inText.startsWith("//")) {
					inText = input.readLine(); // read next line
					continue;
				} else {
					// read each line
					String[] split = inText.split(" ");
					AnimationSequenceObject sequence =
							new AnimationSequenceObject(
									AnimationSequenceObjectFactory.getInstance()
											.getNextId());
					String animName = split[0];
					int frameOrder = 0;
					for (int i = 1; i < split.length;) {
						if (split[i].length() != 1) {
							PooledStringBuilder sb =
									StringBuilderPool.getInstance()
											.getStringBuilder();
							sb.append("AnimationsLoader loadFramesFile() - ");
							sb.append("undefined line marker '");
							sb.append(split[i]);
							sb.append("' found on line '");
							sb.append(inText);
							sb.append("'.");
							Exception ex = new Exception(sb.toString());
							fileName.returnToPool();
							sb.returnToPool();
							throw ex;
						}
						char c = split[i].charAt(0);
						switch (c) {
						case 'i': // image frame to add
							String frameName = split[i + 1];
							int frameDuration = Integer.parseInt(split[i + 2]);
							AnimationFrameObject frame =
									new AnimationFrameObject(
											AnimationFrameObjectFactory
													.getInstance()
													.getNextId(),
											UtilityMethods.getInstance()
													.convertMilliToNano(
															frameDuration),
											frameOrder,
											SpriteImageObjectFactory
													.getInstance()
													.getImageRefId(
															frameName));
							try {
								AnimationFrameObjectFactory.getInstance()
										.addFrame(frameName, frame);
							} catch (AnimationFramelLoadedException afle) {
								frame =
										AnimationFrameObjectFactory
												.getInstance()
												.getFrameByName(frameName);
							}
							sequence.addFrame(frameOrder++, frame.getRefId());
							i += 3;
							break;
						case 'f': // animation flag to add
							sequence.assignFlag(Long.parseLong(split[i + 1]));
							i += 2;
							break;
						}
					}
					AnimationSequenceObjectFactory.getInstance().addSequence(
							animName,
							sequence);
				}
				inText = input.readLine(); // read next line
			}
			input.close(); // not necessary, but still good practice
			fileName.returnToPool();
		} catch (Exception exception) {
			fileName.returnToPool();
			throw exception;
		}
	}
}
