/**
 *
 */
package com.dalonedrow.engine.ui.base;

import com.dalonedrow.pooled.PooledException;
import com.dalonedrow.pooled.PooledStringBuilder;
import com.dalonedrow.pooled.StringBuilderPool;

/**
 * Simple implementation of java.awt.Insets.
 * @author DaLoneDrow
 */
public final class SimpleInsets {
	/**
	 * The inset from the bottom. This value is subtracted from the Bottom of
	 * the rectangle to yield a new location for the Bottom.
	 */
	public int	bottom;
	/**
	 * The inset from the left. This value is added to the Left of the rectangle
	 * to yield a new location for the Left edge.
	 */
	public int	left;
	/**
	 * The inset from the right. This value is subtracted from the Right of the
	 * rectangle to yield a new location for the Right edge.
	 */
	public int	right;
	/**
	 * The inset from the top. This value is added to the Top of the rectangle
	 * to yield a new location for the Top.
	 */
	public int	top;
	/**
	 * Creates and initializes a new <code>Insets</code> object with the
	 * specified top, left, bottom, and right insets.
	 * @param t the inset from the top.
	 * @param l the inset from the left.
	 * @param b the inset from the bottom.
	 * @param r the inset from the right.
	 */
	public SimpleInsets(final int t, final int l, final int b, final int r) {
		top = t;
		left = l;
		bottom = b;
		right = r;
	}
	/**
	 * Set top, left, bottom, and right to the specified values.
	 * @param t the inset from the top.
	 * @param l the inset from the left.
	 * @param b the inset from the bottom.
	 * @param r the inset from the right.
	 */
	public void set(final int t, final int l, final int b, final int r) {
		top = t;
		left = l;
		bottom = b;
		right = r;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		String s = null;
		PooledStringBuilder text =
				StringBuilderPool.getInstance().getStringBuilder();
		try {
			text.append(getClass().getName());
			text.append("[top=");
			text.append(top);
			text.append(", left=");
			text.append(left);
			text.append(", bottom=");
			text.append(bottom);
			text.append(", right=");
			text.append(right);
			text.append("]");
		} catch (PooledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		s = text.toString();
		text.returnToPool();
		return s;
	}
}
