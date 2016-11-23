/**
 *
 */
package com.dalonedrow.engine.sprite.base;

import com.dalonedrow.engine.ui.base.SimpleInsets;

/**
 * @author Donald
 */
public interface SpriteBase {
	/**
	 * flag indicating the {@link SpriteBase}'s alpha channel has been modified.
	 */
	long	FLAG_SPRITE_CHANGE_ALPHA			= 1;
	/**
	 * flag indicating the {@link SpriteBase}'s blue channel has been modified.
	 */
	long	FLAG_SPRITE_CHANGE_BLUE				= 1 << 1;
	/**
	 * flag indicating the {@link SpriteBase}'s green channel has been modified.
	 */
	long	FLAG_SPRITE_CHANGE_GREEN			= 1 << 2;
	/**
	 * flag indicating the {@link SpriteBase}'s red channel has been modified.
	 */
	long	FLAG_SPRITE_CHANGE_RED				= 8;
	/**
	 * flag indicating the {@link SpriteBase} should not be rendered.
	 */
	long	FLAG_SPRITE_DO_NOT_RENDER			= 2048;
	/**
	 * flag indicating the {@link SpriteBase} should have a left to right
	 * horizontal gradient applied when rendering.
	 */
	long	FLAG_SPRITE_GRADIENT_HORIZONTAL_L2R	= 128;
	/**
	 * flag indicating the {@link SpriteBase} should have a right to left
	 * horizontal gradient applied when rendering.
	 */
	long	FLAG_SPRITE_GRADIENT_HORIZONTAL_R2L	= 256;
	/**
	 * flag indicating the {@link SpriteBase} should have a bottom to top
	 * vertical gradient applied when rendering.
	 */
	long	FLAG_SPRITE_GRADIENT_VERTICAL_B2T	= 1024;
	/**
	 * flag indicating the {@link SpriteBase} should have a top to bottom
	 * vertical gradient applied when rendering.
	 */
	long	FLAG_SPRITE_GRADIENT_VERTICAL_T2B	= 512;
	/** flag indicating the {@link SpriteBase} is only rendered partially. */
	long	FLAG_SPRITE_RENDERED_PARTIALLY		= 16;
	/** flag indicating the {@link SpriteBase} has had its position shifted. */
	long	FLAG_SPRITE_SHIFT_POSITION			= 32;
	/** flag indicating the {@link SpriteBase} has had its size changed. */
	long	FLAG_SPRITE_SIZE_CHANGED			= 64;
	/** the {@link SpriteBase} is a simple rendered image. */
	int		TYPE_00_BASIC_IMAGE					= 0;
	/** the {@link SpriteBase} represents a grouping of rendered images. */
	int		TYPE_01_GROUP_IMAGE					= 1;
	/** the {@link SpriteBase} represents a grouping of rendered text. */
	int		TYPE_02_GROUP_LINES					= 2;
	/** the {@link SpriteBase} represents a series of lines. */
	int		TYPE_03_GROUP_TEXT					= 3;
	/** this {@link SpriteBase} is rendered as a lines. */
	int		TYPE_04_LINE						= 4;
	/** this {@link SpriteBase} is rendered as a line strip. */
	int		TYPE_05_LINE_STRIP					= 5;
	/** this {@link SpriteBase} is rendered as a series of quads. */
	int		TYPE_06_QUADS						= 6;
	/** this {@link SpriteBase} is rendered as a triangle strip. */
	int		TYPE_07_TRIANGLE_STRIP				= 7;
	/** this {@link SpriteBase} is rendered as a series of triangles. */
	int		TYPE_08_TRIANGLES					= 8;
	/** this {@link SpriteBase} represents a tile map. */
	int		TYPE_09_TILE_MAP					= 9;
	/** the {@link SpriteBase} is a particle image. */
	int		TYPE_10_PARTICLE					= 10;
	/**
	 * Assigns a flag.
	 * @param flag the flag
	 */
	void assignFlag(long flag);
	/** Clears all flags that were assigned. */
	void clearFlags();
	/** Clears out the rendering storage array. */
	void clearFloatStore();
	/**
	 * Gets the unmodified value of the alpha color channel.
	 * @return <code>float</code>
	 */
	float getAlpha();
	/**
	 * Gets the unmodified value of the blue color channel.
	 * @return <code>float</code>
	 */
	float getBlue();
	/**
	 * Gets the storage array for rendering values.
	 * @return <code>float</code>[]
	 */
	float[] getFloatStore();
	/**
	 * Gets the number of values in the storage array for rendering values.
	 * @return <code>int</code>
	 */
	int getFloatStoreNumber();
	/**
	 * Gets the full value of the alpha color channel.
	 * @return <code>float</code>
	 */
	float getFullAlpha();
	/**
	 * Gets the full value of the blue color channel.
	 * @return <code>float</code>
	 */
	float getFullBlue();
	/**
	 * Gets the full value of the green color channel.
	 * @return <code>float</code>
	 */
	float getFullGreen();
	/**
	 * Gets the full Height.
	 * @return <code>int</code>
	 */
	int getFullHeight();
	/**
	 * Gets the full value of the red color channel.
	 * @return <code>float</code>
	 */
	float getFullRed();
	/**
	 * Gets the full value of the {@link SpriteBase}'s texture's bottom
	 * coordinates.
	 * @return <code>float</code>
	 */
	float getFullTCBottom();
	/**
	 * Gets the full value of the {@link SpriteBase}'s texture's left
	 * coordinates.
	 * @return <code>float</code>
	 */
	float getFullTCLeft();
	/**
	 * Gets the full value of the {@link SpriteBase}'s texture's right
	 * coordinates.
	 * @return <code>float</code>
	 */
	float getFullTCRight();
	/**
	 * Gets the full value of the {@link SpriteBase}'s texture's top
	 * coordinates.
	 * @return <code>float</code>
	 */
	float getFullTCTop();
	/**
	 * Gets the full Width.
	 * @return <code>int</code>
	 */
	int getFullWidth();
	/**
	 * Gets the full value of the x-coordinate of the {@link SpriteBase}'s first
	 * vertex. Only used for triangle primitives.
	 * @return <code>float</code>
	 */
	float getFullX1();
	/**
	 * Gets the full value of the x-coordinate of the {@link SpriteBase}'s
	 * second vertex. Only used for triangle primitives.
	 * @return <code>float</code>
	 */
	float getFullX2();
	/**
	 * Gets the full value of the x-coordinate of the {@link SpriteBase}'s third
	 * vertex. Only used for triangle primitives.
	 * @return <code>float</code>
	 */
	float getFullX3();
	/**
	 * Gets the full value of the {@link SpriteBase}'s Xl position.
	 * @return <code>int</code>
	 */
	float getFullXl();
	/**
	 * Gets the full value of the {@link SpriteBase}'s Xr position.
	 * @return <code>int</code>
	 */
	float getFullXr();
	/**
	 * Gets the full value of the y-coordinate of the {@link SpriteBase}'s first
	 * vertex. Only used for triangle primitives.
	 * @return <code>float</code>
	 */
	float getFullY1();
	/**
	 * Gets the full value of the y-coordinate of the {@link SpriteBase}'s
	 * second vertex. Only used for triangle primitives.
	 * @return <code>float</code>
	 */
	float getFullY2();
	/**
	 * Gets the full value of the y-coordinate of the {@link SpriteBase}'s third
	 * vertex. Only used for triangle primitives.
	 * @return <code>float</code>
	 */
	float getFullY3();
	/**
	 * Gets the full value of the {@link SpriteBase}'s Yb position.
	 * @return <code>int</code>
	 */
	float getFullYb();
	/**
	 * Gets the full value of the {@link SpriteBase}'s Yt position.
	 * @return <code>int</code>
	 */
	float getFullYt();
	/**
	 * Gets the unmodified value of the green color channel.
	 * @return <code>float</code>
	 */
	float getGreen();
	/**
	 * Gets the unmodified Height.
	 * @return <code>int</code>
	 */
	int getHeight();
	/**
	 * Gets the sprite's image reference id.
	 * @return <code>int</code>
	 */
	int getImageRefId();
	/**
	 * Gets the value of all modifications applied to the alpha color channel.
	 * @return <code>float</code>
	 */
	float getModAlpha();
	/**
	 * Gets the value of all modifications applied to the blue color channel.
	 * @return <code>float</code>
	 */
	float getModBlue();
	/**
	 * Gets the value of all modifications applied to the green color channel.
	 * @return <code>float</code>
	 */
	float getModGreen();
	/**
	 * Gets the value of all modifications applied to the Height.
	 * @return <code>int</code>
	 */
	int getModHeight();
	/**
	 * Gets the value of all modifications applied to the red color channel.
	 * @return <code>float</code>
	 */
	float getModRed();
	/**
	 * Gets the value of all modifications applied to the {@link SpriteBase}'s
	 * texture's bottom coordinates.
	 * @return <code>float</code>
	 */
	float getModTCBottom();
	/**
	 * Gets the value of all modifications applied to the {@link SpriteBase}'s
	 * texture's left coordinates.
	 * @return <code>float</code>
	 */
	float getModTCLeft();
	/**
	 * Gets the value of all modifications applied to the {@link SpriteBase}'s
	 * texture's right coordinates.
	 * @return <code>float</code>
	 */
	float getModTCRight();
	/**
	 * Gets the value of all modifications applied to the {@link SpriteBase}'s
	 * texture's top coordinates.
	 * @return <code>float</code>
	 */
	float getModTCTop();
	/**
	 * Gets the value of all modifications applied to the Width.
	 * @return <code>int</code>
	 */
	int getModWidth();
	/**
	 * Gets the value of all modifications applied to the {@link SpriteBase}'s
	 * left-side x position.
	 * @return <code>int</code>
	 */
	float getModXl();
	/**
	 * Gets the value of all modifications applied to the {@link SpriteBase}'s
	 * Xr position.
	 * @return <code>int</code>
	 */
	float getModXr();
	/**
	 * Gets the value of all modifications applied to the {@link SpriteBase}'s
	 * Yb position.
	 * @return <code>int</code>
	 */
	float getModYb();
	/**
	 * Gets the value of all modifications applied to the {@link SpriteBase}'s
	 * Yt position.
	 * @return <code>int</code>
	 */
	float getModYt();
	/**
	 * Gets the time the {@link SpriteBase}'s move lasts.
	 * @return <code>long</code>
	 */
	long getMoveDuration();
	/**
	 * Gets the time the {@link SpriteBase}'s move started.
	 * @return <code>long</code>
	 */
	long getMoveStart();
	/**
	 * Gets the parent container's reference id.
	 * @return <code>int</code>
	 */
	int getParentRefId();
	/**
	 * Gets the {@link SpriteBase}'s partial offsets.
	 * @return {@link SimpleInsets}
	 */
	SimpleInsets getPartialOffsets();
	/**
	 * Gets the {@link SpriteBase}'s position.
	 * @return {@link SimpleVector3}
	 */
	SimpleVector3 getPosition();
	/**
	 * Gets the unmodified value of the red color channel.
	 * @return <code>float</code>
	 */
	float getRed();
	/**
	 * Gets the scaling factor applied to the sprite's height.
	 * @return <code>float</code>
	 */
	float getScaleFactorHeight();
	/**
	 * Gets the scaling factor applied to the sprite's width.
	 * @return <code>float</code>
	 */
	float getScaleFactorWidth();
	/**
	 * Gets the unmodified value of the {@link SpriteBase}'s texture's bottom
	 * coordinates.
	 * @return <code>float</code>
	 */
	float getTCBottom();
	/**
	 * Gets the unmodified value of the {@link SpriteBase}'s texture's left
	 * coordinates.
	 * @return <code>float</code>
	 */
	float getTCLeft();
	/**
	 * Gets the unmodified value of the {@link SpriteBase}'s texture's right
	 * coordinates.
	 * @return <code>float</code>
	 */
	float getTCRight();
	/**
	 * Gets the unmodified value of the {@link SpriteBase}'s texture's top
	 * coordinates.
	 * @return <code>float</code>
	 */
	float getTCTop();
	/**
	 * Gets the type of {@link SpriteBase}.
	 * @return <code>int</code>
	 */
	int getType();
	/**
	 * Gets the {@link SpriteBase}'s velocity.
	 * @return {@link SimpleVector3}
	 */
	SimpleVector3 getVelocity();
	/**
	 * Gets the unmodified Width.
	 * @return <code>int</code>
	 */
	int getWidth();
	/**
	 * Gets the value of the x-coordinate of the {@link SpriteBase}'s first
	 * vertex. Only used for triangle primitives.
	 * @return <code>float</code>
	 */
	float getX1();
	/**
	 * Gets the value of the x-coordinate of the {@link SpriteBase}'s second
	 * vertex. Only used for triangle primitives.
	 * @return <code>float</code>
	 */
	float getX2();
	/**
	 * Gets the value of the x-coordinate of the {@link SpriteBase}'s third
	 * vertex. Only used for triangle primitives.
	 * @return <code>float</code>
	 */
	float getX3();
	/**
	 * Gets the value of the y-coordinate of the {@link SpriteBase}'s first
	 * vertex. Only used for triangle primitives.
	 * @return <code>float</code>
	 */
	float getY1();
	/**
	 * Gets the value of the y-coordinate of the {@link SpriteBase}'s second
	 * vertex. Only used for triangle primitives.
	 * @return <code>float</code>
	 */
	float getY2();
	/**
	 * Gets the value of the y-coordinate of the {@link SpriteBase}'s third
	 * vertex. Only used for triangle primitives.
	 * @return <code>float</code>
	 */
	float getY3();
	/**
	 * Determines if the {@link SpriteBase} has a specific flag set.
	 * @param flag the flag
	 * @return true if the flag was set; false otherwise
	 */
	boolean hasFlag(long flag);
	/**
	 * Gets the debugging output flag.
	 * @return <code>boolean</code>
	 */
	boolean isDebug();
	/**
	 * Removes a flag.
	 * @param flag the flag
	 */
	void removeFlag(long flag);
	/**
	 * Sets the unmodified value of the alpha color channel.
	 * @param a a value between 0.0 - 1.0
	 */
	void setAlpha(float a);
	/**
	 * Sets the unmodified value of the blue color channel.
	 * @param b a value between 0.0 - 1.0
	 */
	void setBlue(float b);
	/**
	 * Sets the sprite's color based on the setting of a 3 or 4-value array.
	 * @param color the new color
	 * @throws Exception if the array is less than 3 values, or more than 4
	 */
	void setColor(float[] color) throws Exception;
	/**
	 * Sets the debugging output flag.
	 * @param val the flag to set
	 */
	void setDebug(boolean val);
	/**
	 * Sets the number of values in the storage array for rendering values.
	 * @param val the number of values
	 */
	void setFloatStoreNumber(int val);
	/**
	 * Sets the full value of the alpha color channel.
	 * @param a a value between 0.0 - 1.0
	 */
	void setFullAlpha(float a);
	/**
	 * Sets the full value of the blue color channel.
	 * @param b a value between 0.0 - 1.0
	 */
	void setFullBlue(float b);
	/**
	 * Sets the full value of the green color channel.
	 * @param g a value between 0.0 - 1.0
	 */
	void setFullGreen(float g);
	/**
	 * Sets the full value of the Height.
	 * @param h the new value
	 */
	void setFullHeight(int h);
	/**
	 * Sets the full value of the red color channel.
	 * @param r a value between 0.0 - 1.0
	 */
	void setFullRed(float r);
	/**
	 * Sets the full value of the {@link SpriteBase}'s texture's bottom
	 * coordinates.
	 * @param val the new value
	 */
	void setFullTCBottom(float val);
	/**
	 * Sets the full value of the {@link SpriteBase}'s texture's left
	 * coordinates.
	 * @param val the new value
	 */
	void setFullTCLeft(float val);
	/**
	 * Sets the full value of the {@link SpriteBase}'s texture's right
	 * coordinates.
	 * @param val the new value
	 */
	void setFullTCRight(float val);
	/**
	 * Sets the full value of the {@link SpriteBase}'s texture's top
	 * coordinates.
	 * @param val the new value
	 */
	void setFullTCTop(float val);
	/**
	 * Sets the full value of the Width.
	 * @param w the new value
	 */
	void setFullWidth(int w);
	/**
	 * Sets the full value of the x-coordinate of the {@link SpriteBase}'s first
	 * vertex. Only used for triangle primitives.
	 * @param val the new value
	 */
	void setFullX1(float val);
	/**
	 * Sets the full value of the x-coordinate of the {@link SpriteBase}'s
	 * second vertex. Only used for triangle primitives.
	 * @param val the new value
	 */
	void setFullX2(float val);
	/**
	 * Sets the full value of the x-coordinate of the {@link SpriteBase}'s third
	 * vertex. Only used for triangle primitives.
	 * @param val the new value
	 */
	void setFullX3(float val);
	/**
	 * Sets the full value of the {@link SpriteBase}'s x-left position.
	 * @param val the new value
	 */
	void setFullXl(float val);
	/**
	 * Sets the full value of the {@link SpriteBase}'s Xr position.
	 * @param val the new value
	 */
	void setFullXr(float val);
	/**
	 * Sets the full value of the y-coordinate of the {@link SpriteBase}'s first
	 * vertex. Only used for triangle primitives.
	 * @param val the new value
	 */
	void setFullY1(float val);
	/**
	 * Sets the full value of the y-coordinate of the {@link SpriteBase}'s
	 * second vertex. Only used for triangle primitives.
	 * @param val the new value
	 */
	void setFullY2(float val);
	/**
	 * Sets the full value of the y-coordinate of the {@link SpriteBase}'s third
	 * vertex. Only used for triangle primitives.
	 * @param val the new value
	 */
	void setFullY3(float val);
	/**
	 * Sets the full value of the {@link SpriteBase}'s y-bottom position.
	 * @param val the new value
	 */
	void setFullYb(float val);
	/**
	 * Sets the full value of the {@link SpriteBase}'s Yt position.
	 * @param val the new value
	 */
	void setFullYt(float val);
	/**
	 * Sets the unmodified value of the green color channel.
	 * @param g a value between 0.0 - 1.0
	 */
	void setGreen(float g);
	/**
	 * Sets the unmodified image height.
	 * @param h the height
	 */
	void setHeight(int h);
	/**
	 * Sets the sprite's image reference id.
	 * @param refId the reference id
	 */
	void setImageRefId(int refId);
	/**
	 * Sets the value of all modifications applied to the alpha color channel.
	 * @param a the modification, as a percentage
	 */
	void setModAlpha(float a);
	/**
	 * Sets the value of all modifications applied to the blue color channel.
	 * @param b the modification, as a percentage
	 */
	void setModBlue(float b);
	/**
	 * Sets the value of all modifications applied to the green color channel.
	 * @param g the modification, as a percentage
	 */
	void setModGreen(float g);
	/**
	 * Sets the value of all modifications applied to the Height.
	 * @param h the new value
	 */
	void setModHeight(int h);
	/**
	 * Sets the value of all modifications applied to the red color channel.
	 * @param r the modification, as a percentage
	 */
	void setModRed(float r);
	/**
	 * Sets the value of all modifications applied to the {@link SpriteBase}'s
	 * texture's bottom coordinates.
	 * @param val the new value
	 */
	void setModTCBottom(float val);
	/**
	 * Sets the value of all modifications applied to the {@link SpriteBase}'s
	 * texture's left coordinates.
	 * @param val the new value
	 */
	void setModTCLeft(float val);
	/**
	 * Sets the value of all modifications applied to the {@link SpriteBase}'s
	 * texture's right coordinates.
	 * @param val the new value
	 */
	void setModTCRight(float val);
	/**
	 * Sets the value of all modifications applied to the {@link SpriteBase}'s
	 * texture's top coordinates.
	 * @param val the new value
	 */
	void setModTCTop(float val);
	/**
	 * Sets the value of all modifications applied to the Width.
	 * @param w the new value
	 */
	void setModWidth(int w);
	/**
	 * Sets the value of all modifications applied to the {@link SpriteBase}'s
	 * Xl position.
	 * @param val the new value
	 */
	void setModXl(float val);
	/**
	 * Sets the value of all modifications applied to the {@link SpriteBase}'s
	 * Xr position.
	 * @param val the new value
	 */
	void setModXr(float val);
	/**
	 * Sets the value of all modifications applied to the {@link SpriteBase}'s
	 * Yb position.
	 * @param val the new value
	 */
	void setModYb(float val);
	/**
	 * Sets the value of all modifications applied to the {@link SpriteBase}'s
	 * Yt position.
	 * @param val the new value
	 */
	void setModYt(float val);
	/**
	 * Sets the time the {@link SpriteBase}'s move started.
	 * @param time the time in nanoseconds
	 */
	void setMoveStart(long time);
	/**
	 * Sets the time the {@link SpriteBase}'s move lasts.
	 * @param time the time in nanoseconds
	 */
	void setMoveTimer(long time);
	/**
	 * Sets the parent container's reference id.
	 * @param val the value to set
	 */
	void setParentRefId(int val);
	/**
	 * Sets the position.
	 * @param x the x position
	 * @param y the y position
	 * @param z the z position
	 */
	void setPosition(double x, double y, double z);
	/**
	 * Sets the position.
	 * @param v the position
	 */
	void setPosition(SimpleVector3 v);
	/**
	 * Sets the unmodified value of the red color channel.
	 * @param r a value between 0.0 - 1.0
	 */
	void setRed(float r);
	/**
	 * Sets the scaling factor applied to the sprite's height.
	 * @param val the factor applied
	 */
	void setScaleFactorHeight(float val);
	/**
	 * Sets the scaling factor applied to the sprite's width.
	 * @param val the factor applied
	 */
	void setScaleFactorWidth(float val);
	/**
	 * Sets the unmodified value of the {@link SpriteBase}'s texture's bottom
	 * coordinates.
	 * @param val the new value
	 */
	void setTCBottom(float val);
	/**
	 * Sets the unmodified value of the {@link SpriteBase}'s texture's left
	 * coordinates.
	 * @param val the new value
	 */
	void setTCLeft(float val);
	/**
	 * Sets the unmodified value of the {@link SpriteBase}'s texture's right
	 * coordinates.
	 * @param val the new value
	 */
	void setTCRight(float val);
	/**
	 * Sets the unmodified value of the {@link SpriteBase}'s texture's top
	 * coordinates.
	 * @param val the new value
	 */
	void setTCTop(float val);
	/**
	 * Sets the velocity.
	 * @param x the x velocity
	 * @param y the y velocity
	 */
	void setVelocity(double x, double y);
	/**
	 * Sets the velocity.
	 * @param x the x velocity
	 * @param y the y velocity
	 * @param z the z velocity
	 */
	void setVelocity(double x, double y, double z);
	/**
	 * Sets the velocity.
	 * @param v the velocity
	 */
	void setVelocity(SimpleVector3 v);
	/**
	 * Sets the unmodified image width.
	 * @param w the width
	 */
	void setWidth(int w);
	/**
	 * Sets the value of the x-coordinate of the {@link SpriteBase}'s first
	 * vertex. Only used for triangle primitives.
	 * @param val the new value
	 */
	void setX1(float val);
	/**
	 * Sets the value of the x-coordinate of the {@link SpriteBase}'s second
	 * vertex. Only used for triangle primitives.
	 * @param val the new value
	 */
	void setX2(float val);
	/**
	 * Sets the value of the x-coordinate of the {@link SpriteBase}'s third
	 * vertex. Only used for triangle primitives.
	 * @param val the new value
	 */
	void setX3(float val);
	/**
	 * Sets the value of the y-coordinate of the {@link SpriteBase}'s first
	 * vertex. Only used for triangle primitives.
	 * @param val the new value
	 */
	void setY1(float val);
	/**
	 * Sets the value of the y-coordinate of the {@link SpriteBase}'s second
	 * vertex. Only used for triangle primitives.
	 * @param val the new value
	 */
	void setY2(float val);
	/**
	 * Sets the value of the y-coordinate of the {@link SpriteBase}'s third
	 * vertex. Only used for triangle primitives.
	 * @param val the new value
	 */
	void setY3(float val);
}
