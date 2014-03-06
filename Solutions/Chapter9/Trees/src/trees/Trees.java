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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author peterg
 */
public class Trees {

    /**
     * This is a solution for section 9.4.2.7, problems 3 and 4. The Tree class
     * provides a general tree structure, and we use it to solve problem 3.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // Read a file of strings, one per line, and construct a tree.
        if (args.length != 1) {
            System.out.println("Usage: trees filename");
            System.exit(0);
        }

        Tree<String> t = makeStringTree(args[0]);
        
        // Print the tree:
        System.out.println(t);

        // Print them depth first
        System.out.println();
        System.out.println("Depth first:");
        Iterator<String> it = t.depthFirst();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
        
        // Now print them breadth first:
        System.out.println();
        System.out.println("Breadth first:");
        it = t.breadthFirst();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    private static Tree<String> makeStringTree(String fname) {

        Tree<String> root = new Tree<>("");

        try (BufferedReader rd = new BufferedReader(new FileReader(fname))) {
            while (true) {
                String line = rd.readLine();
                if (line == null)
                    break;

                Tree<String> parent = findParent(root, line);

                // Watch for duplicate strings
                if (parent.get().equals(line))
                    continue;

                Tree<String> node = new Tree<>(line);

                // Children of parent may now be children of the new node.
                // We don't want to modify the list of children inside a for
                // loop, so we keep a list of nodes to move.
                // We could also use an iterator and remove items during the
                // iteration.
                ArrayList<Tree<String>> move    = new ArrayList<>();
                for (Tree<String> ch : parent.getChildren()) {
                    if (ch.get().contains(line))
                        move.add(ch);
                }
                
                for (Tree<String> ch : move) {
                    parent.remove(ch);
                    node.add(ch);
                }

                parent.add(node);
            }
        }
        catch (IOException e) {
            System.out.println("File read error: " + e);
        }

        return root;
    }

    // We know node is an ancestor in the tree - try to find lower nodes
    // which are.
    private static Tree<String> findParent(Tree<String> node, String line) {
        
        for (Tree<String> nd : node.getChildren()) {
            if (line.contains(nd.get())) 
                return findParent(nd, line);
        }
        
        return node;
    }

}
