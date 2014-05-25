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
package sequences;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;

/**
 *
 * @author peterg
 */
public class Sequences {

    /**
     * Solution to section 6.9, problem 2. The main program runs some simple
     * tests on the sequence library. We'll see later how to create unit tests
     * in a situation like this. This program takes a command line argument
     * containing sequences, one per line. It creates a sequence set, then sorts
     * it by length. Then we print the sorted set.
     *
     * @param args The name of a file of sequences
     */
    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("Usage: sequences (dna | protein) filename");
            System.exit(0);
        }
        
        SequenceSet sset = null;       
        BufferedReader rd = null;
        
        try {
            rd  = new BufferedReader(new FileReader(args[1]));
        }
        catch (FileNotFoundException ex) {
            System.out.println("Unable to read file " + args[1]);
            System.exit(0);
        }
        
        if (args[0].equals("dna"))
            sset    = doDna(rd);
        else if (args[0].equals("protein"))
            sset    = doProtein(rd);
        else {
            System.out.println("Usage: sequences (dna | protein) filename");
            System.exit(0);
        }

        Collections.sort(sset);

        // We can do this in an untyped way here, or we could do
        // something like for (DNA dna : sset) if we know the type
        // parameter
        for (Object s : sset)
            System.out.println(s);
    }
    
    private static SequenceSet<DNA> doDna (BufferedReader rd) {
        
        SequenceSet<DNA>    sset    = new SequenceSet<>();   
        String line;
        
        try {
            while ((line = rd.readLine()) != null) {
                DNA dna = new DNA(line);
                sset.add(dna);
            }
        }
        catch (IOException ex) {
            System.out.println("Read error");
        }
        catch (SequenceException ex) {
            System.out.println("Invalid dna sequence " + ex);
        }
        
        return sset;
    }
     
    private static SequenceSet<Protein> doProtein (BufferedReader rd) {
          
        SequenceSet<Protein>    sset    = new SequenceSet<>();   
        String line;
        
        try {
            while ((line = rd.readLine()) != null) {
                Protein p = new Protein(line);
                sset.add(p);
            }
        }
        catch (IOException ex) {
            System.out.println("Read error");
        }
        catch (SequenceException ex) {
            System.out.println("Invalid protein sequence " + ex);
        }
        
        return sset; 
    }
    
    private static SequenceSet<? extends Sequence<? extends Compound>> doSequence (BufferedReader rd) {
        
        return new SequenceSet<DNA>();
    }

}
