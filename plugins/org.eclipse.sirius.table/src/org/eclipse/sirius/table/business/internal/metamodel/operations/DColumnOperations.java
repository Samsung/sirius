/*******************************************************************************
 * Copyright (c) 2007, 2015 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.table.business.internal.metamodel.operations;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.eclipse.sirius.table.metamodel.table.DCell;
import org.eclipse.sirius.table.metamodel.table.DColumn;
import org.eclipse.sirius.table.metamodel.table.DLine;
import org.eclipse.sirius.table.metamodel.table.LineContainer;
import org.eclipse.sirius.table.tools.internal.Messages;

import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;

/**
 * Common operations for DColumn subclasses.
 *
 * @author cbrun
 */
public final class DColumnOperations {

    private DColumnOperations() {

    }

    /**
     * Sort the column cells considering their index and return them.
     *
     * @param column
     *            column.
     * @return a sorted set of cells.
     */
    public static Collection<DCell> getOrderedCells(final DColumn column) {
        final Map<DLine, Integer> lineIndices = Maps.newHashMap();
        fillIndices(column.getTable(), lineIndices, 0);
        Ordering<DCell> ordering = Ordering.from(new Comparator<DCell>() {
            @Override
            public int compare(DCell a, DCell b) {
                int result = 0;
                DLine lineA = a.getLine();
                DLine lineB = b.getLine();
                if (lineA == null) {
                    result = -1;
                } else if (lineB == null) {
                    result = 1;
                } else {
                    Integer aIndex = lineIndices.get(lineA);
                    Integer bIndex = lineIndices.get(lineB);
                    if (aIndex == null || bIndex == null) {
                        throw new RuntimeException(Messages.Table_UnexpectedExceptionMessage);
                    }
                    return aIndex - bIndex;
                }
                return result;
            }
        });
        List<DCell> result = ordering.sortedCopy(column.getCells());
        return result;
    }

    private static int fillIndices(LineContainer container, final Map<DLine, Integer> lineIndices, int i) {
        int j = i;
        for (DLine line : container.getLines()) {
            if (line.getContainer() != null) {
                lineIndices.put(line, j);
            } else {
                lineIndices.put(line, -1);
            }
            j += 1;
            j = fillIndices(line, lineIndices, j);
        }
        return j;
    }
}
