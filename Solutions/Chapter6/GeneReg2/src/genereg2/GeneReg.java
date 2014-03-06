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

/**
 * This project is a solution to section 6.9, problem 1.
 * Subclass SimEvent to simplify the structure.
 * @author Peter
 */
public class GeneReg
    {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
        {

        if (args.length != 1)
            {
            System.out.println("Usage: java -jar GeneReg2.jar configfile");
            System.exit(0);
            }

        // Set up the cell
        CellState cs = new CellState(args[0]);

        // Run the simulation
        while (true)
            {
            // Print first to pick up the initial state
            System.out.println(cs);

            if (!cs.advance())
                break;
            }
        }
    }
