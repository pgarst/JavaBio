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
package streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.Random;
import java.util.stream.Stream;

/**
 * Solutions to streams related problems.
 * @author peterg
 */
public class Streams {
    
    /**
     * Solution to section 9.6.2.2.
     */
    private static void tryInfinite () {
        
        Stream<Integer> positive = Stream.iterate(1, ((x) -> (x+1)));
        System.out.println(positive.count());
    }
    
    /**
     * Solution to section 9.6.5.1, problems 1 and 2.
     * This takes a stream as input and produces a stream representing
     * a random subset as output.
     * @param data
     * @return 
     */
    private static Stream<Integer> getHeads (Stream<Integer> data) {
        
        Random  r   = new Random();
        PrimitiveIterator.OfInt coin    = r.ints(0, 2).iterator();
        return data.filter(x -> (coin.nextInt() == 0));
    }
    
    /**
     * Solution to section 9.6.5.1, problem 3.
     * The last method showed how to get a random subset of a stream,
     * in the form of another stream.
     * This one shows how to split a (finite sized) stream into two 
     * collections. There is no way to split it into two streams.
     * @param data 
     */
    private static void partition (Stream<Integer> data) {
        
        Random  r   = new Random();
        PrimitiveIterator.OfInt coin    = r.ints(0, 2).iterator();
        ArrayList<Integer>  heads   = new ArrayList<>();
        ArrayList<Integer>  tails   = new ArrayList<>();
        
        data.forEach((x) -> {if (coin.nextInt() == 0) heads.add(x); else tails.add(x);});
    }
    
    /**
     * Solution to section 9.6.5.1, problem 4.
     * Two ways to convert a finite stream into a list.
     * @param data 
     */
    private static ArrayList<Integer> transform (Stream<Integer> data) {
        
        ArrayList<Integer>  list1   = new ArrayList<>();
        
        // One way to convert a stream into a list
        data.forEach((x) -> list1.add(x));
        
        // Another way to convert it.
        // WARNING: this code is just for illustration. We have already used
        // up the input stream. You can apply either approach to the input
        // stream, but not both.
        List<Integer>  list2   = Arrays.asList((Integer[] ) data.toArray());
        
        return list1;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
    }
    
}
