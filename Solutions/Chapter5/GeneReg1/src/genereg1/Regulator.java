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
package genereg1;

/**
 *
 * @author peterg
 */
public class Regulator {
    
    private Gene    regulator;
    private boolean activator;
    private double  thresh;

    Regulator(Gene gene, boolean activator, double thresh) {
        
        regulator       = gene;
        this.activator  = activator;
        this.thresh     = thresh;
    }   
    
    private static final int    hilln   = 2;    // Try values 1 to 4
    
    /**
     * A Hill function for the expression level of a gene
     * @return an expression level between 0 and 1
     */
    double expressionLevel () {

        double  value   = 0;
        if (regulator.isSignaled()) {
            double  cval    = regulator.getConcentration();
            double  p1      = Math.pow(cval, hilln);
            double  p2      = Math.pow(thresh, hilln);
            value   = p1 / (p1 + p2);
        }
        
        if (!activator)
            value   = 1 - value;
        return value;
    }
    
}
