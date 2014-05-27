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

import java.util.HashMap;

/**
 * See the comments on DNABase for information about the structure.
 * @author peterg
 */
public class AminoAcid 
    extends Compound {
      
    static final HashMap<Character, AminoAcid>   aatable;
    
    static {
        
        aatable   = new HashMap<>();
        
        aatable.put('A', new AminoAcid('A', "Alanine"));
        aatable.put('C', new AminoAcid('C', "Cysteine"));
        aatable.put('D', new AminoAcid('D', "Aspartic acid"));
        aatable.put('E', new AminoAcid('E', "Glutamic acid"));
        aatable.put('F', new AminoAcid('F', "Phenylalanine"));
        aatable.put('G', new AminoAcid('G', "Glycine"));
        aatable.put('H', new AminoAcid('H', "Histidine"));
        aatable.put('I', new AminoAcid('I', "Isoleucine"));
        aatable.put('K', new AminoAcid('K', "Lysine"));
        aatable.put('L', new AminoAcid('L', "Leucine"));
        aatable.put('M', new AminoAcid('M', "Methionine"));
        aatable.put('N', new AminoAcid('N', "Asparagine"));
        aatable.put('P', new AminoAcid('P', "Proline"));
        aatable.put('Q', new AminoAcid('Q', "Glutamine"));
        aatable.put('R', new AminoAcid('R', "Arginine"));
        aatable.put('S', new AminoAcid('S', "Serine"));
        aatable.put('T', new AminoAcid('T', "Threonine"));
        aatable.put('V', new AminoAcid('V', "Valine"));
        aatable.put('W', new AminoAcid('W', "Tryptophan"));
        aatable.put('Y', new AminoAcid('Y', "Tyrosine"));
    }
    
    private AminoAcid (char code, String name) {
        
        super(code, name);
    }
    
    public static AminoAcid getAA (char code) {
        
        return aatable.get(code);
    }
    
}
