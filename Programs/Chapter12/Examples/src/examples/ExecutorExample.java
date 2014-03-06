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

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author peterg
 */
public class ExecutorExample {

    ExecutorService service;

    ExecutorExample() {
        service = Executors.newFixedThreadPool(10);
    }

    private String heavy(int n) {
        return "That took a long time";
    }

    private String heavy2() {
        return "This one too";
    }

    Future<String> heavyAsync(int n) {

        return service.submit(() -> heavy(n));
    }
    
    Future<String> heavyAsync2(String n) {

        n   = n.toLowerCase();
        return service.submit(() -> n.trim());
    }

    Future<String> heavyAsyncAlt(int n) {

        return service.submit(new Callable<String>() {
            @Override
            public String call() {
                return heavy(n);
            }
        });
    }
    
    Future<String> heavyAsyncAlt2(String n) {

        n = n.toUpperCase();
        return service.submit(new Callable<String>() {
            @Override
            public String call() {
                return n.toLowerCase();
            }
        });
    }

    Future<String> heavy2Async() {

        return service.submit(this::heavy2);
    }
}
