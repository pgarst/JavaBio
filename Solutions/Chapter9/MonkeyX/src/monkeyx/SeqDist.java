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
package monkeyx;

import seqtools.CharGenerator;
import seqtools.Collect;
import seqtools.DataSource;
import seqtools.ProteinSource;

/**
 * This is the solution to section 9.7.2.1, problem 2.
 *
 * @author peterg
 */
public class SeqDist {

    private static boolean istext = true;

    /**
     * Take two file names, and find the cross entropy distance between their
     * ngram distributions. The user can specify text, protein or dna. You could
     * make this fancier by making the program detect the data type.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("Usage: seqdist [-p] file1 file2");
            System.out.println("   The files can be text, or protein with -p.");
            System.exit(0);
        }

        int pos = 0;
        if (args[0].equals("-p")) {
            pos++;
            istext = false;
        }

        CharGenerator gen1 = getCharGen(args[pos]);
        CharGenerator gen2 = getCharGen(args[pos + 1]);

        // Do this for ngrams 1, 2 and 3
        for (int i = 1; i <= 3; i++) {
            Collect col1 = new Collect();
            Collect col2 = new Collect();

            col1.setNgram(i);
            col1.setAlphabet(gen1.getAlphabet());
            col2.setNgram(i);
            col2.setAlphabet(gen2.getAlphabet());

            gen1.init();
            col1.useSource(gen1);
            col1.finish();

            gen2.init();
            col2.useSource(gen2);
            col2.finish();

            System.out.println("Ngram " + i + " cross entropy " + col1.crossEntropy(col2));
        }
    }

    /**
     * Read protein, or dna, or text.
     *
     * @param string
     * @return
     */
    private static CharGenerator getCharGen(String fname) {

        if (istext) {
            String alphabet = "_abcdefghijklmnopqrstuvwxyz";
            return new DataSource(fname, Collect.stringToAlphabet(alphabet));
        }

        return new ProteinSource(fname);
    }
}
