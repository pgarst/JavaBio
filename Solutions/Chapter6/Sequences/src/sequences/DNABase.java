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
 * This class represents one DNA base.
 * Notice how we address the problem of requiring exactly four known
 * bases:
 * We make the constructor private, so that we can only build new DNA bases 
 * inside this class. 
 * We have a static initializer - that is, a piece of 
 * code that runs once, when we first load the class - that constructs
 * the four bases.
 * We put the four bases in a hash table so that we can retrieve them 
 * using their code letters.
 * Finally, we provide a static method for getting the base for a
 * code letter.
 * 
 * @author peterg
 */
public class DNABase 
    extends Compound {
    
    static final HashMap<Character, DNABase>   basetable;
    
    static {
        
        basetable   = new HashMap<>();
        basetable.put('A', new DNABase('A', "adenine"));
        basetable.put('C', new DNABase('C', "cytosine"));
        basetable.put('G', new DNABase('G', "guanine"));
        basetable.put('T', new DNABase('T', "thymine"));
    }
    
    private DNABase (char code, String name) {
        
        super(code, name);
    }
    
    public static DNABase getBase (char code) {
        
        return basetable.get(code);
    }
}
