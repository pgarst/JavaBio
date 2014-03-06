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
import java.util.PriorityQueue;

/**
 * In the local alignment case we may get not one global alignment but
 * multiple smaller aligned sections. This class provides an iterator
 * over those sections, each one represented by the last DPNode in
 * the section.
 * @author pgarst
 */
public class BestNodes
        implements Iterator<DPNode> {

    private PriorityQueue<DPNode> list = new PriorityQueue<DPNode>();
    private static final int target = 500;

    private DPNode[] elem;
    private int pos;

    void add(DPNode node) {

        if (node.totalCost() <= 0)
            return;

        if (list.size() < target) {
            list.add(node);
            return;
        }

        DPNode worst = list.peek();
        if (node.totalCost() < worst.totalCost())
            return;

        list.poll();
        list.add(node);
    }

    Iterator<DPNode> getIterator() {

        elem = new DPNode[list.size()];
        for (int i = list.size() - 1; i >= 0; i--)
            elem[i] = list.poll();
        pos = 0;

        return this;
    }

    /*
     * Return true if it leads into a used section.
     * We should really break at a local minimum, not necessarily 0.
     */
    private boolean merges(DPNode nd) {

        if (nd.isUsed())
            return true;
        if (!nd.hasPrev())
            return false;

        DPNode prev = nd.getPrev();
        if (prev.totalCost() <= 0)
            return false;

        return merges(prev);
    }

    public boolean hasNext() {

        // Don't return anything already used.
        // Don't return it if it merges with a previous path.
        for (; pos < elem.length; pos++) {
            if (merges(elem[pos])) {
                elem[pos].setUsed();
                continue;
            }

            return true;
        }

        return false;
    }

    public DPNode next() {

        return elem[pos++];
    }

    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
