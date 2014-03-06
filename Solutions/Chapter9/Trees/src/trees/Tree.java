/*
 * Copyright 2014 Peter Garst.
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
package trees;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * There are many ways to implement a tree class. This tree has a T object at
 * each node, internal and leaf.
 *
 * @author peterg
 */
public class Tree<T> {

    private T nodeobj = null;
    private Tree<T> parent;
    private ArrayList<Tree<T>> children;

    public Tree(T tobj) {

        nodeobj = tobj;
        children = new ArrayList<>();
    }
    
    @Override
    public String toString () {
        
        // There are easier ways to handle this using stream operations
        // we will study later in this chapter. 
        // Try rewriting this function using those methods.
        String  fpart   = (nodeobj == null)? "null" : nodeobj.toString()
                + System.lineSeparator();
        
        StringBuilder   sb  = new StringBuilder(fpart);
        for (Tree<T> ch : children)
            sb.append(indent("  ", ch.toString()));
        
        return sb.toString();
    }
    
    // Add indentation to each line.
    private String indent (String ind, String inp) {
        
        String lines[] = inp.split("\\r?\\n");
        
        StringBuilder   sb  = new StringBuilder();
        for (String ln : lines) {
            sb.append(ind + ln + System.lineSeparator());
        }
        
        return sb.toString();
    }

    public T get() {

        return nodeobj;
    }

    public int countChildren() {

        return children.size();
    }

    public Tree<T> getChild(int n) {

        return children.get(n);
    }

    public Tree<T> getParent() {

        return parent;
    }

    public List<Tree<T>> getChildren() {

        return children;
    }

    public Iterator<T> depthFirst() {

        return new DepthIterator(this);
    }

    public Iterator<T> breadthFirst() {

        return new BreadthIterator(this);
    }

    public void add(Tree<T> ch) {

        ch.parent = this;
        children.add(ch);
    }

    public void remove(Tree<T> ch) {

        if (children.contains(ch)) {
            ch.parent = null;
            children.remove(ch);
        }
    }

    /*
     * These iterators return the contents of the nodes; we could
     * instead return the nodes themselves.
     */
    // Not efficient - can you do better?
    private class BreadthIterator
            implements Iterator<T> {

        private ArrayList<Tree<T>> pending;
        private int position;

        BreadthIterator(Tree<T> top) {

            pending = new ArrayList<>();
            pending.add(top);
            position = 0;
        }

        @Override
        public boolean hasNext() {

            return (position < pending.size());
        }

        @Override
        public T next() {

            if (position >= pending.size())
                throw new NoSuchElementException();

            T ret = pending.get(position).get();

            position++;
            if (position >= pending.size()) {
                ArrayList<Tree<T>> nlevel = new ArrayList<>();
                for (Tree<T> elem : pending)
                    nlevel.addAll(elem.children);

                position = 0;
                pending = nlevel;
            }

            return ret;
        }

    }

    private class DepthIterator
            implements Iterator<T> {

        private Tree<T> top;
        private Tree<T> waitnode;
        private int chposition;
        private DepthIterator sub;

        DepthIterator(Tree<T> node) {

            top = node;
            waitnode = node;
        }

        @Override
        public boolean hasNext() {

            if (waitnode != null)
                return true;

            if (chposition >= top.countChildren())
                return false;
            if (chposition < (top.countChildren() - 1))
                return true;

            // We are on the last subtree
            return ((sub == null) || sub.hasNext());
        }

        @Override
        public T next() {

            if (waitnode != null) {
                T temp = waitnode.get();
                waitnode = null;
                chposition = 0;
                return temp;
            }

            if (chposition >= top.countChildren())
                throw new NoSuchElementException();

            if (sub == null)
                sub = new DepthIterator(top.getChild(chposition));
            if (!sub.hasNext()) {
                chposition++;
                sub = null;
                return next();
            }

            return sub.next();
        }
    }
}
