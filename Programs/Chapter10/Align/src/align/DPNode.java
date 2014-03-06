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

import org.biojava3.core.sequence.template.Compound;

/**
 *
 * @author peter
 */
class DPNode {

    public static final int gap0 = 1;
    public static final int gap1 = 2;
    public static final int mis = 3;
    public static final int match = 4;

    private static final int BIG = -1000000000;
    private static final int GAP = -3;
    private static final int MISMATCH = -1;
    private static final int MATCH = 1;

    private int status;
    private int total;

    private int row;

    // private DPNode	left = null, diag = null, down = null;
    private DPNode prev;

    private Compound c1, c2;

    // Construct the starting node.
    DPNode() {

        total = 0;
        // left	= diag	= down	= null;
        prev = null;

        status = match;
    }

    DPNode(int row) {

        this();
        this.row = row;
    }

    int getRow() {

        return row;
    }

    boolean hasPrev() {

        return (prev != null);
    }

    DPNode getPrev() {

        return prev;
    }

    String symbol(int n) {

        if (n == 0) {
            if ((status == gap0) || (c1 == null))
                return "_";
            return c1.getShortName();
        }

        if ((status == gap1) || (c2 == null))
            return "_";
        return c2.getShortName();
    }
    
    String name (int n) {
        
        if (n == 0) {
            if (c1 == null)
                return "_";
            return c1.getShortName();
        }

        if (c2 == null)
            return "_";
        return c2.getShortName();
    }
    
    @Override
    public String toString () {
        
        return name(0) + name(1) + "  cost " + total;
    }

    int totalCost() {

        return total;
    }

    int getStatus() {

        return status;
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
        int leftc = BIG, diagc = BIG, downc = BIG;
        if (left != null)
            leftc = left.totalCost() + GAP;
        if (diag != null)
            diagc = diag.totalCost() + linkCost(c1, c2);
        if (down != null)
            downc = down.totalCost() + GAP;

        if ((leftc >= diagc) && (leftc >= downc)) {
            prev = left;
            total = leftc;
            status = gap1;
        } else if ((downc >= diagc) && (downc >= leftc)) {
            prev = down;
            total = downc;
            status = gap0;
        } else {
            prev = diag;
            total = diagc;
            if (linkCost(c1, c2) == MATCH)
                status = match;
            else
                status = mis;
        }
    }

    private int linkCost(Compound c1,
            Compound c2) {

        // Return 0 for gap against gap
        if ((c1 == null) && (c2 == null))
            return 0;
        if ((c1 == null) || (c2 == null))
            return GAP;
        if (c1.equals(c2))
            return MATCH;
        return MISMATCH;
    }

}
