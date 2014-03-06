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
package monkey4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;
import org.biojava3.core.sequence.ProteinSequence;
import org.biojava3.core.sequence.compound.AminoAcidCompound;
import org.biojava3.core.sequence.io.FastaReaderHelper;
import seqtools.Collect;
import seqtools.NGramModel;
import seqtools.UnigramModel;

/**
 * This class is a trainer based on Java 8 bulk data tools. It takes a list of
 * file system locations and finds all the fasta files under those spots,
 * getting a stream of Files using the new Files.find method. It turns that
 * stream into a stream of ngrams, and then collects those to get the basic
 * input to the training material. We've stripped some of the generality - this
 * is protein only.
 *
 * @author Peter
 */
public class Train {

    private static int ngrams = 3;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("Train configfile outfile");
            System.exit(0);
        }

        Collect collect = new Collect();
        ArrayList<String> locations = new ArrayList<>();
        Stream<File> fl = locations.stream().map(File::new);
        Stream<File> dirs = fl.filter(File::isDirectory);

        try {
            BufferedReader rd = new BufferedReader(new FileReader(args[0]));

            String line;
            while ((line = rd.readLine()) != null) {
                StringTokenizer tok = new StringTokenizer(line);
                if (!tok.hasMoreTokens())
                    continue;

                String tag = tok.nextToken();
                if (tag.equals("ngram")) {
                    ngrams = Integer.parseInt(tok.nextToken());
                    collect.setNgram(ngrams);
                } else if (tag.equals("protein")) {
                    locations.add(tok.nextToken());
                }
            }
            rd.close();
        }
        catch (IOException e) {
        }

        Function<String, Path> nameToPath = s -> (new File(s)).toPath();
        Stream<String> ngramstream = 
                locations
                    .parallelStream()
                    .map(nameToPath)
                    .flatMap(Train::doFind)
                    .flatMap(Train::readSeq)
                    .flatMap(Train::getNgrams)
                    .sequential();
        
        ngramstream.forEach(collect::addKey);
        collect.finish();

        if (collect.getNgram() == 1) {
            UnigramModel model = new UnigramModel(collect);
            model.write(args[1]);
        } else {
            NGramModel model = new NGramModel(collect);
            model.write(args[1]);
        }
    }

    static Stream<String> getNgrams(ProteinSequence ps) {

        // Could go through a stream of AminoAcidCompound, but that's overkill
        StringBuilder   builder = new StringBuilder();
        for (AminoAcidCompound aa : ps)
            builder.append(aa.getShortName());

        Stream.Builder<String>  sbuild  = Stream<String>.builder();
        for (int i = 0; i < builder.length() - ngrams + 1; i++)
            sbuild.accept(builder.substring(i, ngrams));
        return sbuild.build();
    }

    static Stream<ProteinSequence> readSeq(Path p) {

        try {
            LinkedHashMap<String, ProteinSequence> pmap = FastaReaderHelper.readFastaProteinSequence(p.toFile());
            return pmap.values().parallelStream();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static Stream<Path> doFind(Path p) {

        BiPredicate<Path, BasicFileAttributes> matcher = (path, a) -> (path.endsWith(".fasta"));
        try {
            return Files.find(p, 100, matcher, FileVisitOption.FOLLOW_LINKS);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
