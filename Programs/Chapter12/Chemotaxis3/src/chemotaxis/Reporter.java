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
package chemotaxis;

import javax.swing.JLabel;

/**
 *
 * @author pgarst
 */
public class Reporter
    extends Thread
    {
    private Pond    pond;
    private JLabel  popValue;

    public Reporter(Pond pond, JLabel popValue)
        {
        
        this.pond       = pond;
        this.popValue   = popValue;
        }
    
    @Override
    public void run ()
        {
        
        while (true)
            {
            popValue.setText(
                    "Position " + pond.getPosition() +
                    "    Population " + pond.getPopulation()
                    );
            
            try
                {
                sleep(1000);
                }
            catch (InterruptedException ex)
                {
                }
            }
        }
    }
