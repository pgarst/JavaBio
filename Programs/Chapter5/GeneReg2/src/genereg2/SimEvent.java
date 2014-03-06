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
package genereg2;

/**
 *
 * @author Peter
 */
class SimEvent
    {

    public static final int SIGNAL = 1;
    public static final int LEVEL = 2;
    public static final int END = 3;
    private Gene gene;
    private int time;
    private int evtype;
    private boolean signaled;
    private double level;

    SimEvent(int evtype, Gene gene, boolean signaled, int time)
        {

        this(evtype, time);
        this.gene = gene;
        this.signaled = signaled;
        }

    SimEvent(int evtype, Gene gene, double level, int time)
        {

        this(evtype, time);
        this.gene = gene;
        this.level = level;
        }

    SimEvent(int evtype, int time)
        {

        this.time = time;
        this.evtype = evtype;
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
                gene.setConcentration(level);
                break;
            default:
                System.out.println("Unknown event type");
            }
        }
    }
