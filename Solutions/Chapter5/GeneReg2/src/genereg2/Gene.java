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
 *
 * @author Peter
 */
class Gene
    {

    private Promoter promoter;
    private double concentration;
    private double production;
    private double degradation;
    private String name;
    private boolean signaled = false;

    Gene(String name, double production, double degradation)
        {

        this.name = name;
        this.production = production;
        this.degradation = degradation;

        promoter = new Promoter();
        }

    double getConcentration()
        {

        return concentration;
        }

    Promoter getPromoter()
        {

        return promoter;
        }

    String getName()
        {

        return name;
        }

    void setSignaled(boolean val)
        {

        signaled = val;
        }

    boolean isSignaled()
        {

        return signaled;
        }

    void advance()
        {

        double exppart = 0.0;
        if (promoter.isExpressed())
            exppart = production;
        concentration += (exppart - degradation * concentration) * 0.1;
        }

    void setConcentration(double level)
        {

        concentration = level;
        }
    }
