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
public class Fairness {

    /**
     * Solution to section 12.4.3.3.
     * Run a bunch of threads, and see if resources are allocated fairly
     * among them.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // This isn't strictly necessary, but we use a ThreadGroup to
        // organize the threads. This allows us to call one interrupt
        // method to interrupt all the threads.
        ThreadGroup group   = new ThreadGroup("Fairness");
        
        Load[]  tests   = new Load[10];
        for (int i = 0; i < 10; i++)
            tests[i]    = new Load(group);
        
        // Start them all.
        // They will detect the end condition internally.
        for (int i = 0; i < 10; i++)
            tests[i].start();
    }
    
}
