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
package chemotaxis;

import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author pgarst
 */
public class Genotype 
    extends HashMap<Genes, Double> {
    
    /**
     * In the constructor we set up default values.
     */
    public Genotype () {
        
        for (Genes g : Genes.values()) 
            put(g, g.getInit());
    }
    
    /**
     * Get a nearby parameter set.
     * @return mutated set
     */
    public Genotype mutate (Random rand) {
        
        Genotype    other   = new Genotype();
        for (Genes g : Genes.values()) {
           double   nval    = get(g) + rand.nextGaussian() * g.getDelta();
           nval = Math.max(nval, g.getMinv());
           nval = Math.min(nval, g.getMaxv());
           other.put(g, nval);
        }
        
        return other;
    }
    
}
