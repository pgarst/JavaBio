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
public class Gene
    {

    private double concentration;
    private double production;
    private double degradation;
    private boolean expressed;
    private boolean signaled;
    private Promoter promoter;
    private String name;

    Gene(String name, double prod, double degrade)
        {

        this.name = name;

        concentration = 0;
        promoter = new Promoter();
        production = prod;
        degradation = degrade;
        }

    double getConcentration()
        {

        return concentration;
        }

    void advance()
        {

        double exppart = production * promoter.expressionLevel();
        concentration += (exppart
            - degradation * concentration) * 0.1;
        }

    @Override
    public String toString()
        {

        return name + ": " + concentration;
        }

    Promoter getPromoter()
        {

        return promoter;
        }

    void setConcentration(double val)
        {

        concentration = val;
        }

    void setSignaled(boolean signaled)
        {

        this.signaled = signaled;
        }

    boolean isSignaled()
        {
        return signaled;
        }
    }
