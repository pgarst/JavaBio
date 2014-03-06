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
 * Chapter 4 Simulating a gene The main function instantiates a gene and runs
 * the simulation.
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
        double deltap = 0.2;
        double deltad = 0.2;
        int ngenes = 5;

        for (int i = 0; i < args.length; i++) {
            try {
                switch (args[i]) {
                    case "production":
                        production = Double.parseDouble(args[++i]);
                        break;
                    case "degradation":
                        degradation = Double.parseDouble(args[++i]);
                        break;
                    case "deltap":
                        deltap = Double.parseDouble(args[++i]);
                        break;
                    case "deltad":
                        deltad = Double.parseDouble(args[++i]);
                        break;
                    case "ngenes":
                        ngenes = Integer.parseInt(args[++i]);
                        break;
                    default:    // Includes "help"
                        printHelp();
                }
            }
            catch (NumberFormatException e) {
                printHelp();
            }
        }

        Gene[] geneArray = new Gene[ngenes];
        for (int i = 0; i < ngenes; i++) {
            geneArray[i] = new Gene("gene" + i, production + i * deltap, degradation + i * deltad);
            geneArray[i].setExpressed(true);
        }

        for (int i = 0; i < 20; i++) {
            System.out.print("Concentration at " + geneArray[0].getTime());
            for (int j = 0; j < ngenes; j++) {
                System.out.print("  " + geneArray[j].getConcentration());
                geneArray[j].advance();
            }

            System.out.println();
        }

        for (int i = 0; i < ngenes; i++) {
            geneArray[i].setExpressed(false);
        }

        for (int i = 0; i < 20; i++) {
            System.out.print("Concentration at " + geneArray[0].getTime());
            for (int j = 0; j < ngenes; j++) {
                System.out.print("  " + geneArray[j].getConcentration());
                geneArray[j].advance();
            }

            System.out.println();
        }
    }

    private static void printHelp() {

        System.out.println("Supply 0 or more arguments like these:");
        System.out.println("    production #");
        System.out.println("    degradation #");
        System.out.println("    deltap #");
        System.out.println("    deltad #");
        System.out.println("    ngenes #");
        System.exit(0);
    }
}
