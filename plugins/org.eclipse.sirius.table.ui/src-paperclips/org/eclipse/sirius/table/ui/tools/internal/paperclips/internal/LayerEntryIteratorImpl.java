package org.eclipse.sirius.table.ui.tools.internal.paperclips.internal;

import org.eclipse.sirius.table.ui.tools.internal.paperclips.LayerEntry;
import org.eclipse.sirius.table.ui.tools.internal.paperclips.LayerEntryIterator;
import org.eclipse.sirius.table.ui.tools.internal.paperclips.PrintIterator;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;

public class LayerEntryIteratorImpl implements LayerEntryIterator {
	final PrintIterator target;
	final int alignment;

	public LayerEntryIteratorImpl(LayerEntry entry, Device device, GC gc) {
		this.target = entry.getTarget().iterator(device, gc);
		this.alignment = entry.getHorizontalAlignment();
	}

	public LayerEntryIteratorImpl(LayerEntryIterator that) {
		this.target = that.getTarget().copy();
		this.alignment = that.getAlignment();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.sirius.ext.paperclips.core.internal.LayerEntryIterator#getTarget
	 * ()
	 */
	public PrintIterator getTarget() {
		return target;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.sirius.ext.paperclips.core.internal.LayerEntryIterator#getAlignment
	 * ()
	 */
	public int getAlignment() {
		return alignment;
	}

	public LayerEntryIterator copy() {
		return new LayerEntryIteratorImpl(this);
	}
}