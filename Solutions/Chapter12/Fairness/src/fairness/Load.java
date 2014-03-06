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
package fairness;

/**
 *
 * @author peterg
 */
public class Load 
    extends Thread {
    
    private static final int    GOAL    = 100;
    
    private int loops;
    
    // For wasting time.
    // We make total public so that the compiler won't optimize out
    // any calculations we do to compute it.
    public double   total   = 0;
    private int     pos     = 1;
    
    public Load (ThreadGroup group) {
        
        super(group, "oneThread");
    }
    
    public int getLoops () {
        
        return loops;
    }

    @Override
    public void run() {
        
        while (true) { 
            /*
            You can also try passively wasting time
            try {
                sleep(1);
            }
            catch (InterruptedException ex) {
            }*/
            
            // Waste some time actively.
            // We want to use more time than a typical threading slice.
            // Experiment with different amounts of wasted time.
            for (int i = 0; i < 10000; i++, pos++)
                total   += 1.0 / (pos * Math.log(pos));
            
            loops++;
            
            // The first thread to reach the finish line interrups all threads
            if (loops > GOAL)
                getThreadGroup().interrupt();
            
            if (interrupted()) {
                System.out.println("Loops: " + loops);
                break;
            }
        }
    }
    
}
