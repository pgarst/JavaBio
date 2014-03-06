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
package chemotaxis;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JLabel;

/**
 * This is the solution to section 12.9.7.4. It has the same function as the
 * Reporter class, but uses a ScheduledExecutorService instead.
 * Notice that this is much simpler than the thread based version.
 *
 * @author peterg
 */
public class AltReporter 
    implements Runnable {

    private Pond pond;
    private JLabel popValue;

    public AltReporter(Pond pond, JLabel popValue) {

        this.pond = pond;
        this.popValue = popValue;
        
        // The Executors class can return scheduled executors in several ways.
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        
        // Now we can schdule a Runnable to run at regular intervals.
        scheduler.scheduleAtFixedRate(this, 1, 1, TimeUnit.SECONDS);
    }
    
    public void run () {
        
        popValue.setText(pond.getReport());
    }
}
