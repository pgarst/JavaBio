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
package genereg2;

import java.io.IOException;
import java.io.PrintStream;

/**
 * Solution to some of the problems in section 5.4.3. Most of the relevant
 * changes are in the CellState class.
 *
 * @author Peter
 */
public class GeneReg {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        PrintStream outf = System.out;
        String config = null;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-o")) {
                String outn = args[++i];
                try {                  
                    outf = new PrintStream(outn);
                }
                catch (IOException e) {
                    System.out.println("Unable to write to " + outn);
                }
            } else {
                config = args[i];
            }
        }

        if (config == null) {
            System.out.println("Usage: java -jar genereg2.jar [-o outfile] config");
            System.exit(0);
        }

        // Set up the cell
        CellState cs = new CellState(config);

        // Run the simulation
        while (true) {
            // Print first to pick up the initial state
            outf.println(cs);

            if (!cs.advance())
                break;
        }
    }
}
