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

import java.util.Iterator;
import org.biojava3.alignment.template.SubstitutionMatrix;
import org.biojava3.core.sequence.template.AbstractSequence;
import org.biojava3.core.sequence.template.Compound;

/**
 *
 * @author peter
 */
class DPMatrix {

    private final AbstractSequence<Compound> seq1;
    private final AbstractSequence<Compound> seq2;
    private int l1, l2;
    private DPNode[][] mat;
    private BestNodes best;
    private Iterator<DPNode> get;

    DPMatrix(AbstractSequence<Compound> seq1,
            AbstractSequence<Compound> seq2) {

        this.seq1 = seq1;
        this.seq2 = seq2;

        best = new BestNodes();

        // Add a gap at each end to allow starting and ending alignment
        // with gaps.
        l1 = seq1.getLength() + 2;
        l2 = seq2.getLength() + 2;
        System.out.println("Lengths: " + l1 + "   " + l2);

        mat = new DPNode[l1][l2];

        // Biological indices start at 1
        mat[0][0] = new DPNode(0, 0);
        for (int i = 1; i < l1; i++) {
            mat[i][0] = new DPNode(i, 0);
            Compound aa = null;
            if (i < (l1 - 1))
                aa = seq1.getCompoundAt(i);
            mat[i][0].setCompounds(aa, null);
            mat[i][0].findPath(mat[i - 1][0], null, null);
            best.add(mat[i][0]);
        }
        for (int i = 1; i < l2; i++) {
            mat[0][i] = new DPNode(0, i);
            Compound aa = null;
            if (i < (l2 - 1))
                aa = seq2.getCompoundAt(i);
            mat[0][i].setCompounds(null, aa);
            mat[0][i].findPath(null, null, mat[0][i - 1]);
            best.add(mat[0][i]);
        }

        for (int i = 1; i < l1; i++)
            for (int j = 1; j < l2; j++) {
            if ((j == 1) && ((i % 100) == 0))
                System.out.println("Row " + i);

            mat[i][j] = new DPNode(i, j);
            Compound aa = null, ab = null;
            if (i < (l1 - 1))
                aa = seq1.getCompoundAt(i);
            if (j < (l2 - 1))
                ab = seq2.getCompoundAt(j);
            mat[i][j].setCompounds(aa, ab);
            mat[i][j].findPath(mat[i - 1][j], mat[i - 1][j - 1], mat[i][j - 1]);
            best.add(mat[i][j]);
        }
    }

    Alignment getAlignment() {

        if (get == null)
            get = best.getIterator();

        if (!get.hasNext())
            return null;

        DPNode cur = get.next();

        cur.setUsed();
        Alignment align = new Alignment(cur, seq1, seq2);

        // We must be careful to take all the gap steps we need.
        // cur might indicate a left gap of 5 steps, but the
        // previous node to the left might indicate something 
        // different as the best predecessor.
        // This backtrace assumes we have a full matrix, but
        // we would need to change it in a more memory efficient
        // version.
        while (cur.hasPrev()) {
            if (cur.getStatus() == DPNode.gapleft) {
                cur = leftSteps(align, cur);
            } else if (cur.getStatus() == DPNode.gapdown) {
                cur = downSteps(align, cur);
            } else {
                cur = cur.getPrev();
                cur.setUsed();
                align.addPrev(cur);
            }
        }

        // Don't want short ones
        if (align.getLength() < 10)
            return getAlignment();

        align.finish();
        return align;
    }

    private DPNode leftSteps(Alignment align, DPNode cur) {

        // As we go through the left steps, change the 
        // status of each node so that it reflects the left step.
        int nsteps = cur.getLsteps();
        int n = cur.getRow();
        int m = cur.getCol();

        for (int i = 1; i <= nsteps; i++) {
            cur = mat[n - i][m];
            if (i < nsteps)
                cur.setStatus(DPNode.gapleft);
            cur.setUsed();
            align.addPrev(cur);
        }

        return cur;
    }

    private DPNode downSteps(Alignment align, DPNode cur) {

        int nsteps = cur.getDsteps();
        int n = cur.getRow();
        int m = cur.getCol();

        for (int i = 1; i <= nsteps; i++) {
            cur = mat[n][m - i];
            if (i < nsteps)
                cur.setStatus(DPNode.gapdown);
            cur.setUsed();
            align.addPrev(cur);
        }

        return cur;
    }
}
