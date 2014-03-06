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
package driftutils;

import java.util.Random;

/**
 *
 * @author Peter
 */
public class Generation {

    private Random rand;
    private int size;
    private int[] population = new int[Genotype.values().length];

    Generation(Random rand, int size) {

        this.rand = rand;
        this.size = size;

        population[Genotype.AA.ordinal()] = size / 4;
        population[Genotype.AB.ordinal()] = size / 2;
        population[Genotype.BB.ordinal()] = size
                - population[Genotype.AA.ordinal()] - population[Genotype.AB.ordinal()];
    }

    int total() {

        int tot = 0;
        for (Genotype g : Genotype.values()) {
            tot += population[g.ordinal()];
        }

        return tot;
    }

    // Return the proportion of A genes
    double totalA() {

        double tota = 2 * population[Genotype.AA.ordinal()] + population[Genotype.AB.ordinal()];

        return tota / (2 * total());
    }

    // Populate this generation by randomly mating members of the previous
    // generation.
    // Select two members at random, and generate four random offspring.
    void breed(Generation current) {

        // Zero out the current population
        for (Genotype g : Genotype.values()) {
            population[g.ordinal()] = 0;
        }

        while (current.hasTwo()) {
            Genotype g1 = current.draw();
            Genotype g2 = current.draw();

            for (int i = 0; i < 4; i++) {
                population[makeChild(g1, g2).ordinal()]++;
            }
        }
    }

    void survive() {

        // Change the current population distribution by survivability
        for (Genotype g : Genotype.values()) {
            survivors(g);
        }

        // Now normalize the size of the population, approximately
        // We still have roundoff error
        double factor = ((double) size) / total();
        for (Genotype g : Genotype.values()) {
            population[g.ordinal()] = (int) Math.round(factor * population[g.ordinal()]);
        }
    }

    boolean hasTwo() {

        return (total() > 1);
    }

    private Genotype draw() {

        int sz = total();

        // Draw a random member of the population.
        // Start with a random integer between 0 and the total size.
        int choice = rand.nextInt(sz);

        // Go through the genotypes until the total population gets up
        // to our random choice.
        int sofar = 0;
        for (Genotype x : Genotype.values()) {
            sofar += population[x.ordinal()];
            if (sofar > choice) {
                // Remove one from the population
                population[x.ordinal()]--;
                return x;
            }
        }

        // We should never get here
        return null;
    }

    /**
     * Pick a random gene; return 0 for A, 1 for B.
     */
    private int randomGene(Genotype m1) {

        switch (m1) {
            case AA:
                return 0;
            case AB:
                if (rand.nextBoolean())
                return 1;
                return 0;
            case BB:
                return 1;
        }

        // Should never get here, but keep Netbeans quiet
        return 0;
    }

    /**
     * Find a random genotype for a child. Take a random gene from each to make
     * the new genotype.
     */
    private Genotype makeChild(Genotype m1, Genotype m2) {

        int r1 = randomGene(m1);
        int r2 = randomGene(m2);

        switch (r1 + r2) {
            case 0:
                return Genotype.AA;
            case 1:
                return Genotype.AB;
            case 2:
                return Genotype.BB;
        }

        // Should never get here
        return null;
    }

    /**
     * Change the population for this genotype according to the survival rate.
     * Nominally for each individual we flip a coin with the specified survival
     * probability, but for larger populations we use a normal approximation.
     *
     * @param g
     */
    private void survivors(Genotype g) {

        int now = population[g.ordinal()];
        double rate = g.getSurvival();

        int remaining = 0;
        if (now < 6) {
            for (int i = 0; i < now; i++)
                if (flip(rate))
                remaining++;

            population[g.ordinal()] = remaining;
            return;
        }

        // This is logically the same, but faster
        double mean = rate * now;
        double stddev = Math.sqrt(now * rate * (1.0 - rate));
        double temp = mean + stddev * rand.nextGaussian();
        remaining = (int) Math.round(temp);
        remaining = Math.max(0, remaining);
        remaining = Math.min(now, remaining);

        population[g.ordinal()] = remaining;
    }

    private boolean flip(double rate) {

        double temp = rand.nextDouble();
        return (temp <= rate);
    }
}
