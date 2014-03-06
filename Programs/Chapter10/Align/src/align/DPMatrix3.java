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
public class DPMatrix3
        extends DPMatrix {

    private int l1, l2;
    private DPNode[] currow, prevrow;

    private int[] bestRowScore;

    DPMatrix3(AbstractSequence<Compound> seq1,
            AbstractSequence<Compound> seq2) {

        super(seq1, seq2);

        // Add a gap at each end to allow starting and ending alignment
        // with gaps.
        l1 = seq1.getLength() + 2;
        l2 = seq2.getLength() + 2;
        System.out.println("Lengths: " + l1 + "   " + l2);

        prevrow = new DPNode[l1];
        currow = new DPNode[l1];

        bestRowScore = new int[l2];

        // Biological indices start at 1.
        // Do the x axis as the previous row.
        prevrow[0] = new DPNode(0);
        for (int col = 1; col < l1; col++) {
            prevrow[col] = new DPNode(0);
            Compound aa = null;
            if (col < (l1 - 1))
                aa = seq1.getCompoundAt(col);
            prevrow[col].setCompounds(aa, null);
            prevrow[col].findPath(prevrow[col - 1], null, null);
        }

        for (int row = 1; row < l2; row++) {
            if ((row % 100) == 0)
                System.out.println("Row " + row);

            for (int col = 0; col < l1; col++) {
                currow[col] = new DPNode(row);
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

                // We use negative penalties, so the biggest cost is bestRowScore.
                int cost = currow[col].totalCost();
                if ((col == 0) || (cost > bestRowScore[row]))
                    bestRowScore[row] = cost;
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
        align.finish();

        double perRowDiff = 0.0;
        int[] biggestRowDifference = new int[l2];

        for (int i = 0; i < align.getLength(); i++) {
            DPNode n = align.getNode(i);
            int r = n.getRow();
            if (r < 200)
                continue;

            int bestv = bestRowScore[r];

            // This should be smallish and nonnegative - gap between bestRowScore
            // row score and this path point.
            int nodeDifference = bestv - n.totalCost();
            if (nodeDifference > biggestRowDifference[r])
                biggestRowDifference[r] = nodeDifference;
        }

        // Get the per-row perRowDiff gap
        if (l2 > 200) {
            perRowDiff = ((double ) biggestRowDifference[200]) / 200;
            for (int i = 201; i < l2; i++) {
                double  temp    = ((double ) biggestRowDifference[i]) / i;
                if (temp > perRowDiff)
                    perRowDiff   = temp;
            }
        }

        System.out.println("Worst cost factor " + perRowDiff);
        // dumpRows(bestRowScore, biggestRowDifference, rowfact);

        return align;
    }

    private void dumpRows(int[] best, int[] rowsc, double[] rowfact) {

        for (int i = 200; i < l2; i++) {
            System.out.println("Row " + i + " best " + best[i] + "    best on path " + rowsc[i]);
        }
    }
}
