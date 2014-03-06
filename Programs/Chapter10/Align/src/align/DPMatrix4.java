/*
 * Copyright 2013 Peter Garst.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package align;

import org.biojava3.core.sequence.template.AbstractSequence;
import org.biojava3.core.sequence.template.Compound;

/**
 *
 * @author Peter
 */
public class DPMatrix4 
    extends DPMatrix {

    private int l1, l2;
    private DPNode[] currow, prevrow;
    private static final int SKIP = 500;
    private static final double PRDIFF = 0.05;

    // Want to know the range of valid nodes in the previous row.
    private int startvalid, endvalid;
    private int lastpos;

    DPMatrix4(AbstractSequence<Compound> seq1,
            AbstractSequence<Compound> seq2) {

        super(seq1, seq2);

        // Add a gap at each end to allow starting and ending alignment
        // with gaps.
        l1 = seq1.getLength() + 2;
        l2 = seq2.getLength() + 2;
        System.out.println("Lengths: " + l1 + "   " + l2);

        prevrow = new DPNode[l1];
        currow = new DPNode[l1];

        // Biological indices start at 1.
        // Do the x axis as the previous row.
        prevrow[0] = new DPNode();
        for (int col = 1; col < l1; col++) {
            prevrow[col] = new DPNode();
            Compound aa = null;
            if (col < (l1 - 1))
                aa = seq1.getCompoundAt(col);
            prevrow[col].setCompounds(aa, null);
            prevrow[col].findPath(prevrow[col - 1], null, null);
        }

        startvalid = 0;
        endvalid = l1;

        for (int row = 1; row < l2; row++) {
            if ((row % 100) == 0)
                System.out.println("Row " + row);

            boolean prune = (row > SKIP);
            int best = -1;
            int bestpos = -1;
            
            int margin  = (int ) (PRDIFF * row);

            // Don't have to start earlier than the previous row.
            boolean started = false;
            for (int col = startvalid; col < l1; col++) {
                makeNode(row, col);
                if (!prune)
                    continue;
                if (currow[col] == null) {
                    if (col >= endvalid)
                        break;
                    continue;
                }

                // Do some pruning as we go
                int cost = currow[col].totalCost();
                if (!started || (cost > best)) {
                    started = true;
                    best = cost;
                    bestpos = col;
                    lastpos = col;
                } else if (cost < (best - margin)) {
                    // If we get null past the end of the previous row,
                    // then we cannot get any more non-null nodes.
                    currow[col] = null;
                    if (col >= endvalid) {
                        break;
                    }
                } else
                    lastpos = col;
            }

            if (prune)
                finishPruning(bestpos, best, margin);

            // Switch the rows
            DPNode[] temp = currow;
            currow = prevrow;
            prevrow = temp;
            if (startvalid > 0) {
                prevrow[startvalid - 1] = null;
                
            assert startvalid <= endvalid;
            }
        }
    }

    /**
     * Finish the pruning in one row. We have the column where we found the best
     * cost, so we need to check for prunable nodes in columns from startvalid
     * to that position. We also want to set the valid range for this row.
     */
    private void finishPruning(int bestpos, int best, int margin) {

        // Check earlier columns
        boolean started = false;
        for (int i = startvalid; i < bestpos; i++) {
            DPNode nd = currow[i];
            if (nd == null)
                continue;
            if ((best - margin) > nd.totalCost())
                currow[i] = null;
            else if (!started) {
                started = true;
                startvalid = i;
            }
        }

        endvalid = lastpos + 1;
    }

    private void makeNode(int row, int col) {

        DPNode cprev = null;
        DPNode pprev = null;
        if (col > 0) {
            cprev = currow[col - 1];
            pprev = prevrow[col - 1];
        }

        if ((cprev == null) && (pprev == null) && (prevrow[col] == null)) {
            currow[col] = null;
            return;
        }

        currow[col] = new DPNode();
        Compound aa = null, ab = null;
        if ((col > 0) && (col < (l1 - 1)))
            aa = seq1.getCompoundAt(col);
        if (row < (l2 - 1))
            ab = seq2.getCompoundAt(row);
        currow[col].setCompounds(aa, ab);
        currow[col].findPath(cprev, pprev, prevrow[col]);
    }

    Alignment getAlignment() {

        DPNode cur = prevrow[l1 - 1];
        Alignment align = new Alignment(cur, seq1, seq2);

        while (cur.hasPrev()) {
            cur = cur.getPrev();
            align.addPrev(cur);
        }

        return align;
    }
}
