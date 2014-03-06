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
package randalign;

import org.biojava3.core.sequence.template.AbstractSequence;
import org.biojava3.core.sequence.template.Compound;

/**
 *
 * @author Peter
 */
public class DPMatrix2 
    extends DPMatrix {

    private int l1, l2;
    private DPNode[] currow, prevrow;

    DPMatrix2(AbstractSequence<Compound> seq1,
            AbstractSequence<Compound> seq2) {

        super(seq1, seq2);

        // Add a gap at each end to allow starting and ending alignment
        // with gaps.
        l1 = seq1.getLength() + 2;
        l2 = seq2.getLength() + 2;
        // System.out.println("Lengths: " + l1 + "   " + l2);

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

        for (int row = 1; row < l2; row++) {

            for (int col = 0; col < l1; col++) {
                currow[col] = new DPNode();
                Compound aa = null, ab = null;
                if ((col > 0) && (col < (l1 - 1)))
                    aa = seq1.getCompoundAt(col);
                if (row < (l2 - 1))
                    ab = seq2.getCompoundAt(row);
                currow[col].setCompounds(aa, ab);

                DPNode cprev = null;
                DPNode pprev = null;
                if (col > 0) {
                    cprev = currow[col - 1];
                    pprev = prevrow[col - 1];
                }

                currow[col].findPath(cprev, pprev, prevrow[col]);
            }

            // Switch the rows
            DPNode[] temp = currow;
            currow = prevrow;
            prevrow = temp;
        }
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
