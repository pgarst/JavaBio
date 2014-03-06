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
 * @author peter
 */
class DPMatrix1
        extends DPMatrix {

    private int len1, len2;

    private DPNode[][] mat;

    DPMatrix1(AbstractSequence<Compound> seq1,
            AbstractSequence<Compound> seq2) {

        super(seq1, seq2);

        // Add a gap at each end to allow starting and ending alignment
        // with gaps.
        len1 = seq1.getLength() + 2;
        len2 = seq2.getLength() + 2;
        System.out.println("Lengths: " + len1 + "   " + len2);

        mat = new DPNode[len1][len2];

        // Biological indices start at 1
        mat[0][0] = new DPNode();
        for (int i = 1; i < len1; i++) {
            mat[i][0] = new DPNode();
            Compound aa = null;
            if (i < (len1 - 1))
                aa = seq1.getCompoundAt(i);
            mat[i][0].setCompounds(aa, null);
            mat[i][0].findPath(mat[i - 1][0], null, null);
        }
        for (int i = 1; i < len2; i++) {
            mat[0][i] = new DPNode();
            Compound aa = null;
            if (i < (len2 - 1))
                aa = seq2.getCompoundAt(i);
            mat[0][i].setCompounds(null, aa);
            mat[0][i].findPath(null, null, mat[0][i - 1]);
        }

        for (int i = 1; i < len1; i++)
            for (int j = 1; j < len2; j++) {
            if ((j == 1) && ((i % 100) == 0))
                System.out.println("Row " + i);

            mat[i][j] = new DPNode();
            Compound aa = null, ab = null;
            if (i < (len1 - 1))
                aa = seq1.getCompoundAt(i);
            if (j < (len2 - 1))
                ab = seq2.getCompoundAt(j);
            mat[i][j].setCompounds(aa, ab);
            mat[i][j].findPath(mat[i - 1][j], mat[i - 1][j - 1], mat[i][j - 1]);
        }
    }

    Alignment getAlignment() {

        DPNode cur = mat[len1 - 1][len2 - 1];
        Alignment align = new Alignment(cur, seq1, seq2);

        while (cur.hasPrev()) {
            cur = cur.getPrev();
            align.addPrev(cur);
        }

        return align;
    }

}
