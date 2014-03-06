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
package genereg;

/**
 * Chapter 4 Simulating a gene
 * The main function instantiates a gene and runs the simulation.
 * 
 * @author Peter
 */
public class GeneReg {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        double production = 2.0;
        double degradation = 1.0;

        if (args.length > 0) {
            try {
                production = Double.parseDouble(args[0]);
                if (args.length > 1)
                    degradation = Double.parseDouble(args[1]);
            }
            catch (NumberFormatException e) {
                System.out.println("Bad arguments: " + args);
                System.exit(0);
            }
        }

        Gene gene1 = new Gene("gene1", production, degradation);
        gene1.setExpressed(true);

        for (int i = 0; i < 20; i++) {
            System.out.println("Concentration at "
                    + gene1.getTime() + "  " + gene1.getConcentration());
            gene1.advance();
        }

        gene1.setExpressed(false);

        for (int i = 0; i < 20; i++) {
            System.out.println("Concentration at "
                    + gene1.getTime() + "  " + gene1.getConcentration());
            gene1.advance();
        }

    }
}
