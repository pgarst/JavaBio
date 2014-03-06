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
package problem4;

/**
 * Solution to section 4.9, problem 4.
 * @author peterg
 */
public class Sequence {
    
    private boolean dnaseq, rnaseq;
    private String dnaval, rnaval;
    
    public Sequence (String seq) {
        
        seq = seq.toUpperCase();
        
        /*
         * We use a regular expression to test if the string characters
         * match the DNA bases or the RNA bases (or both).
         * See documentation on the Pattern class.
         * You could also do this with a loop over the characters in the
         * string, using seq.charAt(n) to get the character at position n
         * and then seeing if it matches a DNA or RNA base.
         */
        dnaseq  = seq.matches("[ACGT]*");
        rnaseq  = seq.matches("[ACGU]*");
        
        // Check for badly formed input
        if (!dnaseq && !rnaseq) {
            dnaval  = "";
            rnaval  = "";
            return;
        }
        
        if (dnaseq) {
            dnaval  = seq;
            rnaval  = seq.replace('T', 'U');
        } else {
            rnaval  = seq;
            dnaval  = seq.replace('U', 'T');
        }
    }

    boolean isDNA() {
        
        return dnaseq;
    }

    boolean isRNA() {
        
        return rnaseq;
    }

    /*
     * Given a section of one strand of DNA, this finds the corresponding
     * section on the other strand. The direction is reversed, and we swap
     * A and T, and G and C. 
     * RNA is not double stranded like DNA, but for an RNA molecule this
     * returns another RNA molecule which corresponds to the other DNA
     * strand.
     *
     * There are many ways to do this. We use the StringBuilder class,
     * which has some convenient functions
     */
    String getRevComplement() {
        
        StringBuilder  val  = new StringBuilder();
        String  src;
        if (dnaseq)
            src = dnaval;
        else if (rnaseq)
            src = rnaval;
        else
            return "";
        
        for (int i = src.length() - 1; i >= 0; i--)
            val.append(baseComp(src.charAt(i)));
        
        // StringBuilder has a reverse method - you could also do this
        // by putting the original into a string builder, reversing it,
        // and then mapping the characters.
        // Instead of using a switch statement, you could also map the
        // characters using a series of string replace calls - try this also.

        return val.toString();
    }

    String getDNA() {
        
        return dnaval;
    }

    String getRNA() {
        
        return rnaval;
    }

    private char baseComp(char ch) {
        
        switch (ch) {
            case 'A':
                if (rnaseq)
                    return 'U';
                return 'T';
            case 'C':
                return 'G';
            case 'G':
                return 'C';
            case 'T':
                return 'A';
            case 'U':
                return 'A';
                
            // We should never get here
            default:
                return 'X';
        }
    }
}
