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

import org.biojava3.alignment.SubstitutionMatrixHelper;
import org.biojava3.alignment.template.SubstitutionMatrix;
import org.biojava3.core.sequence.compound.AminoAcidCompound;
import org.biojava3.core.sequence.compound.NucleotideCompound;
import org.biojava3.core.sequence.template.Compound;

/**
 *
 * @author peter
 */
class DPNode
        implements Comparable<DPNode> {

    public static final int gapdown = 1;
    public static final int gapleft = 2;
    public static final int mis = 3;
    public static final int match = 4;

    private static final int BIG = -1000000000;
    private static final int gopen = -20;
    private static final int gextend = -2;

    private int status;
    private int total;

    private int row, col;
    private boolean used = false;

    private DPNode prev;
    private int leftpred = BIG, downpred = BIG;
    private int leftsteps = 0, downsteps = 0;

    private Compound c1, c2;

    // Construct the starting node.
    DPNode() {

        total = 0;
        // left	= diag	= down	= null;
        prev = null;

        status = match;
    }

    DPNode(int row, int col) {

        this();
        this.row = row;
        this.col = col;
    }

    int getRow() {

        return row;
    }

    int getCol() {

        return col;
    }

    boolean hasPrev() {

        return (prev != null) && (prev.total > 0);
    }

    DPNode getPrev() {

        return prev;
    }

    String symbol(int n) {

        if (n == 0) {
            if ((status == gapdown) || (c1 == null))
                return "_";
            return c1.getShortName();
        }

        if ((status == gapleft) || (c2 == null))
            return "_";
        return c2.getShortName();
    }

    int totalCost() {

        return total;
    }

    int getStatus() {

        return status;
    }

    void setStatus(int status) {

        this.status = status;
    }

    void setCompounds(Compound c1,
            Compound c2) {

        this.c1 = c1;
        this.c2 = c2;
    }

    void findPath(DPNode left, DPNode diag, DPNode down) {

        // Final link cost.
        // Left link matches node in seq 1 to gap;
        // down matches node in seq 2 to gap.
        // Need the local score for a diagonal move.
        int diagc = BIG;
        if (diag != null)
            diagc = diag.totalCost() + linkCost(c1, c2);

        // For affine approximation use affine(gap1, left.getStatus()), etc
        leftpred = swLeft(left);
        downpred = swDown(down);

        if ((leftpred >= diagc) && (leftpred >= downpred)) {
            prev = left;
            total = leftpred;
            status = gapleft;
        } else if ((downpred >= diagc) && (downpred >= leftpred)) {
            prev = down;
            total = downpred;
            status = gapdown;
        } else {
            prev = diag;
            total = diagc;
            if ((c1 == null) || (c2 == null) || !c1.equals(c2))
                status = mis;
            else
                status = match;
        }

        // Make it local
        total = Math.max(0, total);
    }

    // Not very efficient
    private int linkCost(Compound c1, Compound c2) {

        if ((c1 == null) || (c2 == null))
            return 0;

        int val;
        if (c1 instanceof AminoAcidCompound)
            val = SubstitutionMatrixHelper.getBlosum62()
                    .getValue((AminoAcidCompound) c1, (AminoAcidCompound) c2);
        else
            val = SubstitutionMatrixHelper.getNuc4_2()
                    .getValue((NucleotideCompound) c1, (NucleotideCompound) c2);

        return val;
    }

    /*
     * Full Smith Waterman affine gap penalty.
     * We are at node (i,j) and want to find the
     * max over all k > 0 of c(i-k,j) + g(k), where
     * 
     * g(k) is the gap penalty gopen + k*gextend
     * c(i-k,j) is the score at node (i-k, j).
     * 
     * Call the node at (i-k,j) where we achieve the 
     * maximum the best left predecessor to (i,j).
     * 
     * Suppose the node at (a,j) is the best left
     * predecessor to (i-1,j). Then the best
     * left predecessor to (i,j) must be (i-1,j) or (a,j).
     * If you look at a different possible left predecessor
     * (b,j), and compare the cost of (a,j), because (a,j)
     * is at least as good as (b,j) at (i-1,j), and at (i,j)
     * we just add one gap extension penalty to each, (a,j)
     * is still optimal at (i,j).
     * 
     * Using this lets us keep the n^2 algorithm, but with
     * bigger nodes.
     */
    private int swLeft(DPNode nd) {

        if (nd == null)
            return BIG;

        int immed = nd.totalCost() + gopen + gextend;
        int prevc = nd.bestLeft() + gextend;

        if ((immed <= 0) && (prevc <= 0))
            return BIG;

        if (immed > prevc) {
            leftsteps = 1;
            return immed;
        }

        leftsteps = nd.getLsteps() + 1;
        return prevc;
    }

    private int swDown(DPNode nd) {
        if (nd == null)
            return BIG;

        int immed = nd.totalCost() + gopen + gextend;
        int prevc = nd.bestDown() + gextend;

        if ((immed <= 0) && (prevc <= 0))
            return BIG;

        if (immed > prevc) {
            downsteps = 1;
            return immed;
        }

        downsteps = nd.getDsteps() + 1;
        return prevc;
    }

    int bestLeft() {

        return leftpred;
    }

    int bestDown() {

        return downpred;
    }

    void setUsed() {

        used = true;
    }

    boolean isUsed() {

        return used;
    }

    @Override
    public int compareTo(DPNode t) {

        int diff = total - t.totalCost();
        return diff;
    }

    int getLsteps() {

        return leftsteps;
    }

    int getDsteps() {

        return downsteps;
    }

}
