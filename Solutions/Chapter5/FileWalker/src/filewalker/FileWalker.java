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
package filewalker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.stream.Stream;

/**
 *
 * @author peterg
 */
public class FileWalker {

    /**
     * Part of the solution to section 5.4.3, problem 3.
     * Given a starting point in the file system, list all files under it.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String  startp  = ".";
        if (args.length > 0) {
            startp  = args[0];
        }
        
        // The Files walker functions start with a path
        Path    start   = (new File(startp)).toPath();
        
        /*
         * This uses streams, which we will study in chapter 9.
         * You can also do this using the isDirectory and 
         * listFiles methods from the File class.
         */
        try {
            Stream<Path>    paths       = Files.walk(start);
            Iterator<Path>  iterator    = paths.iterator();
            while (iterator.hasNext()) {
                Path    p   = iterator.next();
                System.out.println(p);
            }             
        } catch (IOException e) {
            System.out.println("IO exception: " + e);
        }
    }
    
}
