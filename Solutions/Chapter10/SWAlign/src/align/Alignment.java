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

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import org.biojava3.core.sequence.template.AbstractSequence;
import org.biojava3.core.sequence.template.Compound;

/**
 * To solve problem 1 in the exercises, we want to allow iteration
 * over an alignment, as in "for (DPNode nd : align) ...".
 * We should do this by just making Alignment extend ArrayList;
 * but we can also do it, as here, by letting Alignment implement
 * Iterable. The iterator method at the bottom is the one required
 * by Iterable.
 * @author peter
 */
class Alignment 
    implements Iterable<DPNode> {

    private ArrayList<DPNode> alist;
    private boolean rev = true;
    private AbstractSequence<Compound> s1, s2;

    public static final String nl = System.getProperty("line.separator");
    public static char[] nla = null;
    public static int nllen;

    Alignment(DPNode cur, AbstractSequence<Compound> s1, AbstractSequence<Compound> s2) {

        alist = new ArrayList<DPNode>();
        alist.add(cur);

        this.s1 = s1;
        this.s2 = s2;

        // For convenience get an array of chars representing the newline
        nllen = nl.length();
        nla = new char[nllen];
        nl.getChars(0, nllen, nla, 0);
    }

    int getLength() {

        return alist.size();
    }

    void printAlignment(PrintStream ps) {

        if (rev)
            reverseit();

        // print scores
        /*
         for (DPNode p : alist)
         ps.print("  " + p.totalCost());
         ps.println();
         */
        ps.println(summary());

        int len = alist.size();
        int seg = 80;
        int totlen = (seg + nl.length()) * 3;
        char[] buf = new char[totlen];

        for (int pos = 0; pos < len; pos += seg) {
            int slen = getSeg(buf, pos, seg, len);
            if (slen == totlen)
                ps.println(buf);
            else
                ps.println(new String(buf, 0, slen));
        }
    }

    private int getSeg(char[] buf, int pos, int seg, int len) {

        int uselen = Math.min(seg, len - pos);
        int np = getSymbols(0, buf, 0, pos, uselen);
        np = getMatch(buf, np, pos, uselen);
        np = getSymbols(1, buf, np, pos, uselen);

        return np;
    }

    void addPrev(DPNode cur) {

        alist.add(cur);
    }

    private void reverseit() {

        if (!rev)
            return;

        ArrayList<DPNode> blist = new ArrayList<DPNode>();

        while (!alist.isEmpty()) {
            DPNode n = alist.remove(alist.size() - 1);
            blist.add(n);
        }

        alist = blist;

        rev = false;
    }

    // Get the first or second sequence, with gaps
    private String getSymbols(int n) {

        String res = new String();

        for (DPNode nd : alist)
            res += nd.symbol(n);

        return res;
    }

    // Return a string with | at match locations
    private String getMatch() {

        String res = new String();
        for (DPNode nd : alist) {
            if (nd.getStatus() == DPNode.match)
                res += "|";
            else
                res += " ";
        }

        return res;
    }

    private int getSymbols(int n, char[] buf, int bufpos, int pos, int uselen) {

        for (int i = pos; i < pos + uselen; i++) {
            DPNode nd = alist.get(i);
            buf[bufpos++] = nd.symbol(n).charAt(0);
        }

        // Add newline
        for (int i = 0; i < nllen; i++)
            buf[bufpos++] = nla[i];

        return bufpos;
    }

    private int getMatch(char[] buf, int bufpos, int pos, int uselen) {

        for (int i = pos; i < pos + uselen; i++) {
            DPNode nd = alist.get(i);
            if (nd.getStatus() == DPNode.match)
                buf[bufpos++] = '|';
            else
                buf[bufpos++] = ' ';
        }

        // Add newline
        for (int i = 0; i < nllen; i++)
            buf[bufpos++] = nla[i];

        return bufpos;
    }

    private String summary() {

        String res = s1.getOriginalHeader() + "  length " + s1.getLength() + "\n";
        res += s2.getOriginalHeader() + "  length " + s2.getLength() + "\n";

        DPNode beg = alist.get(0);
        DPNode end = alist.get(alist.size() - 1);
        res += "Row range " + beg.getRow() + " to " + end.getRow() + "\n";

        int alen = alist.size();
        int nmatch = 0;
        int ngap = 0;
        for (DPNode nd : alist) {
            if (nd.getStatus() == DPNode.match)
                nmatch++;
            else if (nd.getStatus() != DPNode.mis)
                ngap++;
        }

        res += "Alignment length " + alen
                + "   matches " + nmatch + "  gaps " + ngap + "\n\n";

        return res;
    }

    void finish() {

        reverseit();

        assert checkGaps();
    }

    DPNode getNode(int n) {

        return alist.get(n);
    }

    /*
     * For consistency checking - make sure nodes in alignment
     * represent single steps in the matrix
     */
    private boolean checkGaps() {

        DPNode start = alist.get(0);
        int crow = start.getRow();
        int ccol = start.getCol();

        for (int i = 1; i < alist.size(); i++) {
            DPNode next = alist.get(i);
            int rdiff = next.getRow() - crow;
            int cdiff = next.getCol() - ccol;

            if ((rdiff < 0) || (rdiff > 1))
                return false;
            if ((cdiff < 0) || (cdiff > 1))
                return false;
            if ((rdiff == 0) && (cdiff == 0))
                return false;

            crow = next.getRow();
            ccol = next.getCol();
        }

        return false;
    }

    /**
     * This is the implementation of Iterable, which allows us to
     * iterate over this object.
     * @return 
     */
    public Iterator<DPNode> iterator() {
        
        return alist.iterator();
    }

}
