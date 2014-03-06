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

/**
 * These are the chemotaxis parameters we can adjust to optimize the bugs.
 * 
 * @author pgarst
 */
public enum Genes {
   
    SPEED,          // Speed
    SLOPE,          // How sensitive to local changes
    CONSUMPTION,    // Food consumption
    REVERT,         // Adaptation speed
    TPROB;          // Steady state tumbling probability
    
    private double  minv, maxv, delta, init;

    public double getDelta()
        {
        return delta;
        }

    public double getInit()
        {
        return init;
        }

    public double getMaxv()
        {
        return maxv;
        }
      
    public double getMinv () {
        
        return minv;
    }  
    
    void setValues (double minv, double maxv, double delta, double init) {
      
        this.minv     = minv;
        this.maxv     = maxv;
        this.delta    = delta;
        this.init     = init;
    }  
    
}
