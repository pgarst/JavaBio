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
package randalign;

import java.util.Random;
import org.biojava3.core.sequence.BasicSequence;
import org.biojava3.core.sequence.compound.AminoAcidCompoundSet;
import org.biojava3.core.sequence.compound.DNACompoundSet;
import org.biojava3.core.sequence.template.AbstractSequence;
import org.biojava3.core.sequence.template.Compound;

/**
 * This is a solution to section section 10.5.4.1: generate alignments 
 * between random sequences.
 * @author peter
 */
public class RandAlign {
    
    private static Random   rand = new Random();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // We'll do 10 random matches for DNA and 10 for protein
        tryRandomProtein();
        tryRandomDNA();
    }  
    
    /**
     * Inheritance is tricky when we are using generics.
     * We really should return a DNASequence object, but we need a common
     * super class for the DNA and protein cases for the alignment routine.
     * @return A random DNA sequence of length 1000.
     */
    private static AbstractSequence<Compound> randomDNA () {
        
        char[]  bases   = {'A', 'C', 'G', 'T'};
        StringBuilder   sb  = new StringBuilder();
        
        for (int i = 0; i < 1000; i++)
            sb.append(bases[rand.nextInt(4)]);
        
        return new BasicSequence(sb.toString(), DNACompoundSet.getDNACompoundSet());      
    }
    
    private static AbstractSequence<Compound> randomProtein () {
        
        char[]  aa   = 
            {'A', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'Y'};
        StringBuilder   sb  = new StringBuilder();
        
        for (int i = 0; i < 1000; i++)
            sb.append(aa[rand.nextInt(20)]);
        
        return new BasicSequence(sb.toString(), AminoAcidCompoundSet.getAminoAcidCompoundSet());   
    }

    private static void tryRandomDNA() {

        double  alignperc   = 0;
        for (int i = 0; i < 10; i++) {
            AbstractSequence<Compound> seq1 = randomDNA();
            AbstractSequence<Compound> seq2 = randomDNA();
            AlignSeq as = new AlignSeq(seq1, seq2);
            Alignment al = as.getAlign();
            alignperc   += al.getAlignPerc();
        }
        
        String  perc    = String.format("%4.1f", alignperc / 10);
        System.out.println("Random DNA match rate: " + perc);
    }

    private static void tryRandomProtein() {
        
        double  alignperc   = 0;
        for (int i = 0; i < 10; i++) {
            AbstractSequence<Compound> seq1 = randomProtein();
            AbstractSequence<Compound> seq2 = randomProtein();
            AlignSeq as = new AlignSeq(seq1, seq2);
            Alignment al = as.getAlign();
            alignperc   += al.getAlignPerc();
        }
        
        String  perc    = String.format("%4.1f", alignperc / 10);
        System.out.println("Random protein match rate: " + perc);
    }

}
