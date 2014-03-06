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

import java.io.PrintStream;
import java.util.ArrayList;
import org.biojava3.core.sequence.template.AbstractSequence;
import org.biojava3.core.sequence.template.Compound;

/**
 *
 * @author peter
 */
class Alignment {

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

    @Override
    public String toString() {

        if (rev)
            reverseit();

        // First do some summary information
        String res = summary();

        String line1 = getSymbols(0);
        String mstr = getMatch();
        String line2 = getSymbols(1);

        // Want a number of lines, one protein above the other.	
        while (true) {
            if ((line1 == null) || (line1.length() == 0))
                break;
            String p1, p2, pm;
            if (line1.length() > 80) {
                p1 = line1.substring(0, 80);
                line1 = line1.substring(80);
                p2 = line2.substring(0, 80);
                line2 = line2.substring(80);
                pm = mstr.substring(0, 80);
                mstr = mstr.substring(80);
            } else {
                p1 = line1;
                p2 = line2;
                pm = mstr;
                line1 = line2 = null;
            }

            // Add a pair for p1, p2
            res += p1 + "\n" + pm + "\n" + p2 + "\n\n";
        }

        return res;
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

    private String summary() {

        String res = s1.getOriginalHeader() + "  length " + s1.getLength() + "\n";
        res += s2.getOriginalHeader() + "  length " + s2.getLength() + "\n";

        int alen = alist.size();
        int nmatch = 0;
        int ngap = 0;
        for (DPNode nd : alist) {
            if (nd.getStatus() == DPNode.match)
                nmatch++;
            else if (nd.getStatus() != DPNode.mis)
                ngap++;
        }

        // Get last node
        DPNode nd;
        if (rev)
            nd = alist.get(0);
        else
            nd = alist.get(alist.size() - 1);

        res += "Alignment length " + alen
                + "   matches " + nmatch + "  gaps " + ngap
                + "   score  " + nd.totalCost() + "\n\n";

        return res;
    }
    
    double getAlignPerc () {
        
        int len     = (s1.getLength() + s2.getLength()) / 2;
        int nmatch  = 0;
        for (DPNode nd : alist) {
            if (nd.getStatus() == DPNode.match)
                nmatch++;
        }
        
        return ((100.0 * nmatch) / len);
    }

    void finish() {

        reverseit();
    }

    DPNode getNode(int n) {

        return alist.get(n);
    }

}
