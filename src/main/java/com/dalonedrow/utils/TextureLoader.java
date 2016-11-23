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
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.dalonedrow.pooled.PooledStringBuilder;
import com.dalonedrow.pooled.StringBuilderPool;

/**
 * @author Based on the work of Andrew Davison from <u>Killer Game Programming
 *         in Java</u>
 */
public final class TextureLoader {
	/** the one and only instance of the <code>TextureLoader</code> class. */
	private static TextureLoader instance;
	/**
	 * Gives access to the singleton instance of {@link TextureLoader}.
	 * @return {@link TextureLoader}
	 */
	public static TextureLoader getInstance() {
		if (TextureLoader.instance == null) {
			TextureLoader.instance = new TextureLoader();
		}
		return TextureLoader.instance;
	}
	/**
	 * Creates a new singleton instance of {@link TextureLoader}, with all
	 * assets loaded from the "data" folder.
	 * @param fileList the name of the file containing the list of images to
	 *            load
	 * @return {@link TextureLoader}
	 */
	public static TextureLoader getInstance(final String fileList) {
		return getInstance("data", fileList);
	}
	/**
	 * Creates a new singleton instance of {@link TextureLoader}.
	 * @param assetsPath the folder containing all image assets
	 * @param fileList the name of the file containing the list of images to
	 *            load
	 * @return {@link TextureLoader}
	 */
	public static TextureLoader getInstance(final String assetsPath,
			final String fileList) {
		try {
			TextureLoader.instance = new TextureLoader(assetsPath, fileList);
		} catch (Exception ex) {
			JOGLErrorHandler.getInstance().fatalError(ex);
		}
		return TextureLoader.instance;
	}
	/** the number 5. */
	private final int						five	= 5;
	/** the number 4. */
	private final int						four	= 4;
	/**
	 * the <code>HashMap</code> containing all textures indexed by their name.
	 */
	private final HashMap<String, Integer>	intMap;
	/** the folder containing the image library. */
	private String							libraryFolder;
	/**
	 * the <code>HashMap</code> containing all textures indexed by their name.
	 */
	private final HashMap<Integer, Texture>	textureMap;
	/** the number 3. */
	private final int						three	= 3;
	/** Creates a new instance of <code>TextureLoader</code>. */
	private TextureLoader() {
		textureMap = new HashMap<Integer, Texture>();
		intMap = new HashMap<String, Integer>();
		TextureLoader.instance = this;
	}
	/**
	 * Creates a new instance of {@link TextureLoader}.
	 * @param assetsPath the path to the assets folder
	 * @param fileList the name of the file containing the list of image assets
	 * @throws Exception if an error occurs
	 */
	private TextureLoader(final String assetsPath, final String fileList)
			throws Exception {
		libraryFolder = assetsPath;
		textureMap = new HashMap<Integer, Texture>();
		intMap = new HashMap<String, Integer>();
		loadImagesFile(fileList);
		TextureLoader.instance = this;
	}
	/**
	 * Processes a line of text and loads the image file as a blocked number of
	 * images.
	 * @param line the line of text to process
	 * @throws Exception if the line of text has an invalid string
	 */
	private void getBlockedImages(final String line) throws Exception {
		// logger.debug("getBlockedImages(" + line + ")");
		final int minValues = 4;
		// b filename xx,yy #
		String[] splitText = null; // String array to hold each line's elements
		splitText = line.split(" "); // split the line by its spacing
		// if there are more than two elements, do not process
		if (splitText.length < minValues) {
			throw new Exception("ImagesLoader getBlockedImages() "
					+ "invalid line: " + line);
		} else {
			loadBlockImage(splitText);
		}
	}
	/**
	 * Processes a line of text and loads the single image file named in it.
	 * Input should be in this format: o < fnm >
	 * @param line the line of text to process
	 * @throws Exception if the line of text has an invalid string
	 */
	private void getFileNameImage(final String line) throws Exception {
		String[] splitText = null; // String array to hold each line's elements
		splitText = line.split(" "); // split the line by its spacing
		// if there are more than two elements, do not process
		if (splitText.length != 2) {
			throw new Exception("TextureLoader getFileNameImage() "
					+ "invalid filename: " + line);
		} else {
			loadSingleImage(splitText[1]);
		} // end else there are 2 elements in the line
		splitText = null;
	}
	/**
	 * Processes a line of text and loads the image file as a
	 * {@link FontsheetObject}.
	 * @param line the line of text to process
	 * @throws Exception if the line of text has an invalid string
	 */
	private void getFontImages(final String line) throws Exception {
		final int minValues = 4;
		String[] splitText = null; // String array to hold each line's elements
		splitText = line.split(" "); // split the line by its spacing
		// if there are more than two elements, do not process
		if (splitText.length < minValues) {
			throw new Exception("ImagesLoader getFontImages() "
					+ "invalid line: " + line);
		} else {
			loadFontImage(splitText);
		}
	}
	/**
	 * Takes a String file name, and returns the name of the file without its
	 * extension. for example, getPrefix( ball.png) would just return ball.
	 * @param name the file name
	 * @return <code>String</code> the file name without its extension
	 * @throws Exception if there are too many '.'s in the file name
	 */
	private String getPrefix(final String name) throws Exception {
		String[] split = null;
		String prefix = null;
		split = name.split("\\."); // split the name at the literal dot
		// if there are more or less than 2 elements, display an error
		if (split.length != 2) {
			throw new Exception("TextureLoader getPrefix() "
					+ "invalid file name: " + name);
		}
		// check for /'s
		String[] split2 = split[0].split("/");
		if (split2.length > 0) {
			prefix = split2[split2.length - 1];
		} else {
			prefix = split[0];
		}
		// return the file name without its extension
		return prefix;
	}
	/**
	 * Processes a line of text and loads the image file as a multiple number of
	 * images.
	 * @param line the line of text to process
	 * @throws Exception if the line of text has an invalid string
	 */
	private void getSheetImages(final String line) throws Exception {
		final int minValues = 4;
		String[] splitText = null; // String array to hold each line's elements
		splitText = line.split(" "); // split the line by its spacing
		// if there are more than two elements, do not process
		if (splitText.length < minValues) {
			throw new Exception("ImagesLoader getMultipleImages() "
					+ "invalid line: " + line);
		}
		loadSheetImage(splitText);
		splitText = null;
	}
	/**
	 * Returns the first image associated with the image name in the
	 * <code>HashMap</code>.
	 * @param id the reference id
	 * @return <code>Texture</code> the first image in the ArrayList
	 * @throws Exception if name is not a key in the imagesMap
	 */
	public Texture getTexture(final int id) throws Exception {
		Texture t = null;
		if (textureMap.containsKey(id)) {
			t = textureMap.get(id);
		}
		if (t == null) {
			throw new Exception("TextureLoader Error "
					+ "- textureMap does not contain key " + id);
		}
		return t;
	}
	/**
	 * Returns the first image associated with the image name in the
	 * <code>HashMap</code>.
	 * @param name the texture name
	 * @return <code>Texture</code> the first image in the ArrayList
	 * @throws Exception if name is not a key in the imagesMap
	 */
	public Texture getTexture(final String name) throws Exception {
		if (!intMap.containsKey(name)) {
			throw new Exception("TextureLoader getTexture() "
					+ "textureMap " + name + " was never loaded.");
		}
		Integer id = intMap.get(name);
		if (id == null) {
			throw new Exception("TextureLoader getTexture() "
					+ "textureMap " + name + " does not exist.");
		}
		return this.getTexture(id);
	}
	/**
	 * Loads a "block" image file. The images in this file are "blocked" out in
	 * equal dimensions.
	 * @param imageInfo the multiple image information
	 * @return true if the file was successfully loaded; false otherwise
	 * @throws Exception if there was an error during loading
	 */
	private boolean loadBlockImage(final String[] imageInfo)
			throws Exception {
		Texture texture = null;
		if (intMap.containsKey(imageInfo[1])) {
			texture = this.getTexture(imageInfo[1]);
		} else {
			// call loadImage to create a texture
			texture = loadImage(imageInfo[1]);
		}
		if (SpriteImageObjectFactory.getInstance().hasImage(imageInfo[1])) {
			PooledStringBuilder sb =
					StringBuilderPool.getInstance().getStringBuilder();
			sb.append("TextureLoader loadBlockImage() - ");
			sb.append("duplicate sprite image ");
			sb.append(imageInfo[1]);
			Exception ex = new Exception(sb.toString());
			sb.returnToPool();
			throw ex;
		}

		// if the Texture was created, add it to the hashmap
		if (texture == null) {
			throw new Exception("TextureLoader loadSheetImage() "
					+ "texture " + imageInfo[1] + " was not loaded.");
		}
		textureMap.put(
				texture.getTextureObjectHandle(),
				texture);
		intMap.put(imageInfo[1], texture.getTextureObjectHandle());
		int width = Integer.parseInt(imageInfo[2].split(",")[0]);
		int height = Integer.parseInt(imageInfo[2].split(",")[1]);
		int numImages = Integer.parseInt(imageInfo[three]);
		int x = 0, y = 0;
		for (int i = 0; i < numImages; i++) {
			SpriteImageObject image = new SpriteImageObject(
					x, // left coordinates
					y, // top coordinates
					width, // width
					height, // height
					new SimpleDimension(texture.getWidth(),
							texture.getHeight()),
					texture.getTextureObjectHandle(),
					// texture id
					SpriteImageObjectFactory.getInstance().getNextId());
			PooledStringBuilder sb =
					StringBuilderPool.getInstance().getStringBuilder();
			sb.append(getPrefix(imageInfo[1]));
			sb.append(i);
			SpriteImageObjectFactory.getInstance().addImage(sb.toString(),
					image);
			sb.returnToPool();
			x += width;
			if (x >= texture.getWidth()) {
				x = 0;
				y += height;
			}
		}
		texture = null;
		return true;
	}
	/**
	 * Loads a font image file.
	 * @param imageInfo the multiple image information
	 * @return true if the file was successfully loaded; false otherwise
	 * @throws Exception if there was an error during loading
	 */
	private boolean loadFontImage(final String[] imageInfo)
			throws Exception {
		Texture texture = null;
		if (intMap.containsKey(imageInfo[1])) {
			texture = getTexture(intMap.get(imageInfo[1]));
		} else {
			texture = loadImage(imageInfo[1]);
			if (texture != null) {
				textureMap.put(
						texture.getTextureObjectHandle(),
						texture);
				intMap.put(imageInfo[1], texture.getTextureObjectHandle());
			}
		}
		if (texture == null) {
			PooledStringBuilder msg =
					StringBuilderPool.getInstance().getStringBuilder();
			msg.append("TextureLoader loadFontImage() ");
			msg.append("texture ");
			msg.append(imageInfo[1]);
			msg.append(" was not loaded.");
			Exception ex = new Exception(msg.toString());
			msg.returnToPool();
			throw ex;
		}

		String fontName = imageInfo[2];
		int pt = Integer.parseInt(imageInfo[three]);
		int base = Integer.parseInt(imageInfo[four]);
		String[] split =
				imageInfo[five].split(",");
		if (split.length == 2) { // irregularly spaced font
			int xOffset = Integer.parseInt(split[0]);
			int yOffset = Integer.parseInt(split[1]);
			PooledStringBuilder sb =
					StringBuilderPool.getInstance().getStringBuilder();
			sb.append(fontName);
			sb.append("_");
			sb.append(pt);
			System.out.println("load font sheet " + sb.toString());
			FontsheetObject sheet = new FontsheetObject(
					sb.toString(), // font name
					FontProperties.getInstance().getCharacterMap(fontName, pt),
					new SimpleDimension(texture.getWidth(),
							texture.getHeight()),
					texture.getTextureObjectHandle(),
					base,
					xOffset,
					yOffset
			// texture id
			);
			sb.returnToPool();
			GameFont.getInstance().addFont(fontName, pt, sheet);
		} else {
			if (split.length == four) {
				// monospaced font
				int xOffset = Integer.parseInt(split[0]);
				int yOffset = Integer.parseInt(split[1]);
				int charWidth = Integer.parseInt(split[2]);
				int charHeight = Integer.parseInt(split[three]);

				PooledStringBuilder sb =
						StringBuilderPool.getInstance().getStringBuilder();
				sb.append(fontName);
				sb.append("_");
				sb.append(pt);
				FontsheetObject sheet = new FontsheetObject(
						sb.toString(),
						charWidth, charHeight,
						new SimpleDimension(texture.getWidth(),
								texture.getHeight()),
						texture.getTextureObjectHandle(),
						base,
						xOffset, yOffset);
				// texture id);
				sb.returnToPool();
				GameFont.getInstance().addFont(fontName, pt, sheet);
			} else {
				PooledStringBuilder msg =
						StringBuilderPool.getInstance().getStringBuilder();
				msg.append("TextureLoader loadFontImage() ");
				msg.append("invalid coordinate data in line \"");
				for (int i = 0; i < imageInfo.length; i++) {
					msg.append(imageInfo[i]);
				}
				msg.append("\".");
				Exception ex = new Exception(msg.toString());
				msg.returnToPool();
				throw ex;
			}
		}
		return true;
	}
	/**
	 * Creates a <code>Texture</code> based upon the file name provided.
	 * @param name the file name
	 * @return <code>Texture</code> the image loaded by the file name
	 * @throws Exception if there is a problem loading the file
	 */
	private Texture loadImage(final String name) throws Exception {
		Texture image = null;
		PooledStringBuilder fileName =
				StringBuilderPool.getInstance().getStringBuilder();
		fileName.append(libraryFolder);
		fileName.append("/");
		fileName.append(name);
		try {
			System.out.println("loading texture " + fileName.toString());
			image = new Texture(Gdx.files.internal(fileName.toString()));
			image.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			image.setWrap(TextureWrap.ClampToEdge, TextureWrap.ClampToEdge);
		} catch (Exception exception) {
			throw exception;
		} // end try-catch block
		return image;
	}
	/**
	 * Loads an images file and populate the hashmaps.
	 * @param fileList the name of the file list to load
	 * @throws Exception if there are problems loading the supplied file
	 */
	public void loadImagesFile(final String fileList) throws Exception {
		if (libraryFolder == null) {
			throw new Exception("libraryFolder is null");
		}
		PooledStringBuilder fileName =
				StringBuilderPool.getInstance().getStringBuilder();
		fileName.append(libraryFolder);
		fileName.append("/");
		fileName.append(fileList);
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
					// read the line prefix character and convert to lower case
					char prefix = Character.toLowerCase(inText.charAt(0));
					switch (prefix) {
					case 'o':
						// logger.info("o line - " + inText);
						// DONE
						getFileNameImage(inText);
						break;
					case 's':
						// logger.info("s line - " + inText);
						// DONE
						getSheetImages(inText);
						break;
					case 'f':
						// logger.info("f line - " + inText);
						// DONE
						getFontImages(inText);
						break;
					case 'b':
						// logger.debug("b line - " + inText);
						getBlockedImages(inText);
						break;
					default:
						throw new Exception("Undefined line marker: "
								+ prefix + " at " + inText);
					}
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
	/**
	 * Loads a sheet of images.
	 * @param imageInfo the multiple image information
	 * @return true if the file was successfully loaded; false otherwise
	 * @throws Exception if there was an error during loading
	 */
	private boolean loadSheetImage(final String[] imageInfo)
			throws Exception {
		Texture texture = null;
		if (intMap.containsKey(imageInfo[1])) {
			texture = this.getTexture(imageInfo[1]);
		} else {
			// call loadImage to create a texture
			texture = loadImage(imageInfo[1]);
		}
		// if the Texture was created, add it to the hashmap
		if (texture == null) {
			throw new Exception("TextureLoader loadSheetImage() "
					+ "texture " + imageInfo[1] + " was not loaded.");
		}
		textureMap.put(
				texture.getTextureObjectHandle(),
				texture);
		intMap.put(imageInfo[1], texture.getTextureObjectHandle());
		for (int i = 2; i < imageInfo.length;) {
			String imageName = imageInfo[i];
			if (SpriteImageObjectFactory.getInstance().hasImage(imageName)) {
				PooledStringBuilder sb =
						StringBuilderPool.getInstance().getStringBuilder();
				sb.append("TextureLoader loadSheetImage() - ");
				sb.append("duplicate sprite image ");
				sb.append(imageName);
				Exception ex = new Exception(sb.toString());
				sb.returnToPool();
				throw ex;
			}
			String coordinates = imageInfo[i + 1];
			// logger.info(imageName + "::" + coordinates);
			String[] values = coordinates.split(",");
			final int len = 4;
			if (values.length != len) {
				throw new Exception("TextureLoader "
						+ "loadSheetImage() "
						+ "invalid coordinates: " + coordinates);
			}
			int sx1 = Integer.parseInt(values[0]);
			int sy1 = Integer.parseInt(values[1]);
			int sx2 = Integer.parseInt(values[2]);
			int sy2 = Integer.parseInt(values[three]);

			SpriteImageObject image = new SpriteImageObject(
					sx1, // left coordinates
					sy1, // top coordinates
					sx2, // width
					sy2, // height
					new SimpleDimension(texture.getWidth(),
							texture.getHeight()),
					texture.getTextureObjectHandle(),
					// texture id
					SpriteImageObjectFactory.getInstance().getNextId());
			SpriteImageObjectFactory.getInstance().addImage(imageName, image);
			i += 2;
			imageName = null;
			coordinates = null;
		}
		texture = null;
		return true;
	}
	/**
	 * Loads a single image by name and adds it to the
	 * <code>TextureLoader</code> hashmap.
	 * @param name the name of the image
	 * @return boolean indication success or failure
	 * @throws Exception if an image of the same name has already been loaded
	 */
	public boolean loadSingleImage(final String name) throws Exception {
		// logger.info("loadSingleImage(" + name + ")");
		String imageName = getPrefix(name);
		// make sure the image name isn't already in the
		// imagesMap. there cant be two images called
		// 'bouncing ball'
		if (intMap.containsKey(imageName)) {
			throw new Exception("TextureLoader loadSingleImage() "
					+ "textureMap duplicate texture: " + imageName);
		}
		if (SpriteImageObjectFactory.getInstance().hasImage(imageName)) {
			PooledStringBuilder sb =
					StringBuilderPool.getInstance().getStringBuilder();
			sb.append("TextureLoader loadSingleImage() - ");
			sb.append("duplicate sprite image ");
			sb.append(imageName);
			Exception ex = new Exception(sb.toString());
			sb.returnToPool();
			throw ex;
		}

		// call loadImage to create a texture
		Texture texture = loadImage(name);
		// if the Texture was created, add it to the hashmap
		if (texture != null) {
			textureMap.put(
					texture.getTextureObjectHandle(),
					texture);
			intMap.put(imageName, texture.getTextureObjectHandle());
			// logger.info("loaded single image: " + imageName);

			SpriteImageObject image = new SpriteImageObject(
					0, // left coordinates
					0, // top coordinates
					texture.getWidth(), // width
					texture.getHeight(), // height
					new SimpleDimension(texture.getWidth(),
							texture.getHeight()),
					texture.getTextureObjectHandle(),
					// texture id
					SpriteImageObjectFactory.getInstance().getNextId());
			SpriteImageObjectFactory.getInstance().addImage(imageName, image);
		} else {
			PooledStringBuilder sb =
					StringBuilderPool.getInstance().getStringBuilder();
			sb.append("TextureLoader loadSingleImage() - ");
			sb.append("texture ");
			sb.append(name);
			sb.append(" could not be loaded.");
			Exception ex = new Exception(sb.toString());
			sb.returnToPool();
			throw ex;
		}
		imageName = null;
		texture = null;
		return true;
	}
}
