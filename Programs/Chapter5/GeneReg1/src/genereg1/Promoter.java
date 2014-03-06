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
package genereg1;

/**
 *
 * @author Peter
 */
public class Promoter {

    private Gene[] regulators;
    private boolean[] activator;
    private double[] thresh;
    private int nreg = 0;
    private boolean andfunc = false;

    Promoter() {

        // Allow enough room
        regulators = new Gene[100];
        activator = new boolean[100];
        thresh = new double[100];
    }

    void setAnd(boolean val) {

        andfunc = val;
    }

    void activate(Gene gene, double val) {

        regulators[nreg] = gene;
        thresh[nreg] = val;
        activator[nreg++] = true;
    }

    boolean isExpressed() {

        if (nreg == 0)
            return true;

        int nbound = 0;
        for (int i = 0; i < nreg; i++) {
            boolean expressit = shouldExpress(i);
            if (andfunc && !expressit)
                return false;
            if (!andfunc && expressit)
                return true;
        }
    
        return andfunc;
    }

    private boolean shouldExpress (int n) {

        double cval = regulators[n].getConcentration();
        if (activator[n]) {
            return (regulators[n].isSignaled() && (cval >= thresh[n]));
        } else {
            return (!regulators[n].isSignaled() || (cval < thresh[n]));
        }
    }
}
