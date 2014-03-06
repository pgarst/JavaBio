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
package sequences;

import java.util.Collections;

/**
 *
 * @author peterg
 */
public class Sequences {

    /**
     * Solution to section 6.9, problem 2. The main program runs some simple
     * tests on the sequence library. We'll see later how to create unit tests
     * in a situation like this. This program takes a command line argument
     * containing sequences, one per line. It creates a sequence set, then sorts
     * it by length. Then we print the sorted set.
     *
     * @param args The name of a file of sequences
     */
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Usage: sequences filename");
            System.exit(0);
        }

        SequenceSet sset = null;

        // See if it is a valid file of DNA sequences
        try {
            DNASet dset = new DNASet(args[0]);
            sset = dset;
        }
        catch (SequenceException e) {
            // Just try the next molecule type
        }
        if (sset == null) {
            try {
                ProteinSet dset = new ProteinSet(args[0]);
                sset = dset;
            }
            catch (SequenceException e) {
                System.out.println("Not a valid protein or dna file");
                System.exit(0);
            }
        }

        Collections.sort(sset);

        for (Object s : sset)
            System.out.println(s);
    }

}
