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

import java.util.ArrayList;

/**
 *
 * @author Peter
 */
public class Promoter
    {

    private ArrayList<Regulator> regulators;
    private boolean andfunc = false;

    public Promoter()
        {
       
        regulators = new ArrayList<Regulator>();
        }

    void repress(Gene gene, double val)
        {

        regulators.add(new Regulator(gene, val, false));
        }

    void activate(Gene gene, double val)
        {

        regulators.add(new Regulator(gene, val, true));
        }
    
      void setAnd(boolean b)
        {

        andfunc = b;
        }

    boolean isExpressed()
        {

        if (regulators.size() == 0)
            return true;

        for (Regulator r : regulators)
            {
            boolean expressit = r.shouldExpress();
            if (andfunc && !expressit)
                return false;
            if (!andfunc && expressit)
                return true;
            }

        return andfunc;
        }

    }
