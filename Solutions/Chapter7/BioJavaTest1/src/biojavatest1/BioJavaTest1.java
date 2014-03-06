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
package biojavatest1;

import java.io.File;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import org.biojava3.alignment.Alignments;
import org.biojava3.alignment.Alignments.PairwiseSequenceAlignerType;
import org.biojava3.alignment.SimpleGapPenalty;
import org.biojava3.alignment.SimpleSubstitutionMatrix;
import org.biojava3.alignment.template.SequencePair;
import org.biojava3.alignment.template.SubstitutionMatrix;
import org.biojava3.core.sequence.ProteinSequence;
import org.biojava3.core.sequence.compound.AminoAcidCompound;
import org.biojava3.core.sequence.io.FastaReaderHelper;

/**
 *
 * @author peterg
 */
public class BioJavaTest1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("Usage: [align] file1 file2");
            System.exit(0);
        }
        
        try {
            ProteinSequence s1  = getSequenceForFile(args[0]);
            ProteinSequence s2  = getSequenceForFile(args[1]);
            
            if ((s1 == null) || (s2 == null)) {
                System.out.println("Unable to read input files");
            } else {
                doAlignment(s1, s2);
            }
        } catch (Exception e) {
            System.out.println("Exception reading input: " + e);
        }
    }

    private static void alignPairGlobal(String id1, String id2)
            throws Exception {
        ProteinSequence s1 = getSequenceForId(id1),
                s2 = getSequenceForId(id2);
        
        doAlignment(s1, s2);
    }
    
    private static void doAlignment (ProteinSequence s1, ProteinSequence s2) {
        
        SubstitutionMatrix<AminoAcidCompound> matrix
                = new SimpleSubstitutionMatrix<>();
        SequencePair<ProteinSequence, AminoAcidCompound> pair
                = Alignments.getPairwiseAlignment(s1, s2,
                PairwiseSequenceAlignerType.GLOBAL,
                new SimpleGapPenalty(), matrix);
        System.out.printf("%n%s vs %s%n%s",
                pair.getQuery().getAccession(),
                pair.getTarget().getAccession(), pair);
    }

    private static ProteinSequence getSequenceForId(String uniProtId)
            throws Exception {
        URL uniprotFasta = new URL(String.format(
                "http://www.uniprot.org/uniprot/%s.fasta", uniProtId));
        ProteinSequence seq = FastaReaderHelper.
                readFastaProteinSequence(uniprotFasta.openStream()).
                get(uniProtId);
        System.out.printf("id : %s %s%n%s%n",
                uniProtId, seq, seq.getOriginalHeader());
        return seq;
    }

    private static ProteinSequence getSequenceForFile(String fname)
            throws Exception {

        LinkedHashMap<String, ProteinSequence> map
                = FastaReaderHelper.readFastaProteinSequence(new File(fname));

        // We will just take the first one
        ProteinSequence seq = null;

        // Getting an entry when we don't know the key is a little awkward
        Set<String> keys = map.keySet();
        Iterator<String> keyiterator = keys.iterator();
        if (keyiterator.hasNext())
            seq = map.get(keyiterator.next());

        return seq;
    }

}
