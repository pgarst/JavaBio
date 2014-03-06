/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genereg;

/**
 *
 * @author peterg
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
                System.out.println("Bad arguments: " + e);
                System.exit(0);
            }
        }

        Gene gene1 = new Gene("gene1", production, degradation);
        gene1.setExpressed(true);

        System.out.println(gene1 + " expressed:");
        for (int i = 0; i < 20; i++) {
            System.out.println("Concentration at "
                    + gene1.getTime() + ": " + gene1.getConcentration());
            gene1.advance();
        }

        gene1.setExpressed(false);
        System.out.println(gene1 + " not expressed:");
        for (int i = 0; i < 20; i++) {
            System.out.println("Concentration at "
                    + gene1.getTime() + ": " + gene1.getConcentration());
            gene1.advance();
        }
    }
}
