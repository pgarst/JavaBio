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

import java.net.URL;
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

        // String[] ids = {"Q21691", "Q21495"};
        String[] ids = {"Q21691", "O28951"};
        
        // This is brca2 for human and mouse:
        // String[] ids = {"P51587", "P97929"};
        try {
            alignPairGlobal(ids[0], ids[1]);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void alignPairGlobal(String id1, String id2)
            throws Exception {
        ProteinSequence s1 = getSequenceForId(id1),
                s2 = getSequenceForId(id2);
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

}
