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
package genereg1;

/**
 *
 * @author Peter
 */
public class SimEvent
    {

    public static final int SIGNAL = 1;
    public static final int LEVEL = 2;
    public static final int END = 3;
    private int evtype;
    private int time;
    private Gene gene;
    private boolean signaled;
    private double val;

    SimEvent(int ev, Gene gene, boolean signaled, int time)
        {

        this(ev, time);
        this.gene = gene;
        this.signaled = signaled;
        }

    SimEvent(int ev, Gene gene, double val, int time)
        {

        this(ev, time);
        this.gene = gene;
        this.val = val;
        }

    SimEvent(int ev, int time)
        {

        this.time = time;
        this.evtype = ev;
        }

    int getTime()
        {

        return time;
        }

    int getEvent()
        {

        return evtype;
        }

    void doEvent()
        {

        switch (evtype)
            {
            case SIGNAL:
                gene.setSignaled(signaled);
                break;
            case LEVEL:
                gene.setConcentration(val);
                break;
            default:
                System.out.println("Unknown event type");
            }
        }
    }
