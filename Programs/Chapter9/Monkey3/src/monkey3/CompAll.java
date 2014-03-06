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
package monkey3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import seqtools.CharGenerator;
import seqtools.DataSource;
import seqtools.NGramModel;
import seqtools.ProteinSource;

/**
 *
 * @author pgarst
 */
public class CompAll {

    private static ArrayList<NGramModel> models = new ArrayList<>();
    private static ArrayList<CharGenerator> data = new ArrayList<>();

    private static void readModels(StringTokenizer tok) {

        while (tok.hasMoreTokens()) {
            String mname = tok.nextToken();
            NGramModel mod = NGramModel.read(mname);
            if (mod == null)
                System.out.println("Unable to read model " + mname);
            else
                models.add(mod);
        }
    }

    private static void readText(StringTokenizer tok) {

        if (models.isEmpty())
            return;

        char[] alphabet = models.get(0).getAlphabet();
        while (tok.hasMoreTokens()) {
            DataSource src = new DataSource(tok.nextToken(), alphabet);
            data.add(src);
        }
    }

    private static void readProtein(StringTokenizer tok) {

        while (tok.hasMoreTokens()) {
            ProteinSource src = new ProteinSource(tok.nextToken());
            data.add(src);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Usage: CompAll config");
            System.out.println("Valid config lines:");
            System.out.println("    model modname ...");
            System.out.println("    protein file1 ...");
            System.out.println("    text file1 ...");
            return;
        }

        try {
            BufferedReader rd = new BufferedReader(new FileReader(args[0]));

            String line;
            while ((line = rd.readLine()) != null) {
                StringTokenizer tok = new StringTokenizer(line);
                if (!tok.hasMoreTokens())
                    continue;

                String tag = tok.nextToken();
                if (tag.equals("model"))
                    readModels(tok);
                else if (tag.equals("text"))
                    readText(tok);
                else if (tag.equals("protein"))
                    readProtein(tok);
            }

            rd.close();
        }
        catch (IOException e) {
            System.out.println("Unable to read " + args[0]);
            System.exit(0);
        }

        Comparison comp = new Comparison(models, data);
    }

}
