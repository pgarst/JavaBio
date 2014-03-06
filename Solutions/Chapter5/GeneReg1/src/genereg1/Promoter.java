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

    private Regulator[] regulators;
    private int nreg = 0;
    private boolean andfunc = false;

    Promoter() {

        // Allow enough room
        regulators = new Regulator[100];
    }

    void setAnd(boolean val) {

        andfunc = val;
    }

    void activate(Gene gene, double val) {

        regulators[nreg] = new Regulator(gene, true, val);
    }

    double expressionLevel() {

        if (nreg == 0)
            return 1;

        /*
         * For an and combination, we use the minimum expression
         * value; for an or combination, the maximum.
         * This is really an empirical question, though.
         */
        double  result  = andfunc? 1.0 : 0.0;
        for (int i = 0; i < nreg; i++) {
            double expressit = regulators[i].expressionLevel();
            if (andfunc)
                result  = Math.min(result, expressit);
            else
                result  = Math.max(result, expressit);
        }

        return result;
    }

}
