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
 * This is a simple gene class.
 * It has production and degradation rates, and tracks the 
 * concentration of the gene product over time.
 * @author Peter
 */
public class Gene {

    private int time;
    private double concentration;
    private double production;
    private double degradation;
    private boolean expressed;
    private String  geneName;

    public Gene(String name, double production, double degrade) {

        geneName   = name;
        this.production = production;
        this.degradation = degrade;
    }

    void setExpressed(boolean b) {

        expressed = b;
    }

    double getConcentration() {

        return concentration;
    }

    int getTime() {

        return time;
    }

    void advance() {

        double exppart = 0.0;
        if (expressed)
            exppart = production;
        concentration += (exppart - degradation * concentration) * 0.1;
        time++;
    }
    
    @Override
    public String toString () {

        return geneName;
    }
}
