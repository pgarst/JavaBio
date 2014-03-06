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
package examples;

import java.io.File;
import java.io.Serializable;
import java.util.concurrent.Future;
import java.util.function.Predicate;

/**
 *
 * @author peterg
 */
public class Examples {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ExecutorExample eg = new ExecutorExample();
        Future<String> f1 = eg.heavyAsync(2);
        Future<String> f2 = eg.heavyAsyncAlt(2);
        Future<String> f3 = eg.heavy2Async();
        try {
            System.out.println(f1.get());
            System.out.println(f2.get());
            System.out.println(f3.get());
        }
        catch (Exception ex) {
        }

        Predicate<File> ftest1 = (File f) -> f.getName().endsWith(".fasta");
        // Predicate<String> stest1 = f -> f.getName().endsWith(".fasta");
        Predicate<File> ftest2 = makeTest(".fasta");
        Predicate<File> ftest3 = (Predicate<File> & Serializable) (f) -> f.getName().endsWith(".fasta");

    }

    static private Predicate<File> makeTest(String ext) {

        return (f) -> (f.getName().endsWith(ext));
    }

    static private Predicate<File> makeTest2(String ext) {

        return new Predicate<File> () {
            @Override
            public boolean test (File f) {
                return (f.getName().endsWith(ext));
            }
        };
    }
    
    static private Predicate<Integer> makeTest(int ext) {

        return (f) -> (f > ext);
    }

    static private Predicate<Integer> makeTest2(int ext) {

        return new Predicate<Integer> () {
            @Override
            public boolean test (Integer f) {
                return (f > ext);
            }
        };
    }
}
