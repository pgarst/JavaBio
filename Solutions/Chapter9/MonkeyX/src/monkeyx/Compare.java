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
package monkeyx;

import seqtools.LogProb;
import seqtools.NGramModel;


/**
 *
 * @author Peter
 */
public class Compare {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("Usage: compare model data ...");
            return;
        }

        NGramModel mod = NGramModel.read(args[0]);
        if (mod == null) {
            System.out.println("Unable to read model " + args[0]);
            return;
        }

        LogProb lprob = new LogProb(mod);
        for (int i = 1; i < args.length; i++) {
            System.out.print(args[i] + "   ");
            lprob.add(args[i]);
        }

        System.out.println("    " + lprob);
    }
}
