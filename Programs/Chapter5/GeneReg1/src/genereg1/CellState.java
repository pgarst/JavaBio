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
 * A model of the overall state.
 *
 * @author Peter
 */
public class CellState
    {

    private Gene[] genes;
    private SimEvent[] events;
    private int time = 0;
    private int nextevent = 0;

    public CellState()
        {

        // To initialize, we need:
        // List of genes, with rates.
        // Regulatory relationships
        // Map of signals over time

        genes = new Gene[3];
        genes[0] = new Gene("X", 4.0, 2.0);
        genes[1] = new Gene("Y", 4.0, 2.0);
        genes[2] = new Gene("Z", 4.0, 2.0);

        genes[1].getPromoter().activate(genes[0], 1.0);
        genes[2].getPromoter().activate(genes[0], 1.0);
        genes[2].getPromoter().activate(genes[1], 1.0);

        genes[2].getPromoter().setAnd(true);

        events = new SimEvent[100]; // This should be big enough
        int evpos = 0;

        events[evpos++] = new SimEvent(SimEvent.SIGNAL, genes[0], true, 0);
        events[evpos++] = new SimEvent(SimEvent.SIGNAL, genes[1], true, 0);
        events[evpos++] = new SimEvent(SimEvent.LEVEL, genes[0], 2.0, 0);
        events[evpos++] = new SimEvent(SimEvent.SIGNAL, genes[0], false, 20);
        events[evpos++] = new SimEvent(SimEvent.END, 40);
        }

    @Override
    public String toString()
        {

        String state = "Time " + time + "   ";

        for (int i = 0; i < 3; i++)
            {
            String fmt  = String.format("   %5.2f", genes[i].getConcentration());
            state += fmt;
            }

        return state;
        }

    /*
     *
     */
    boolean advance()
        {

        // Handle any events at this time
        while (events[nextevent] != null)
            {
            SimEvent ev = events[nextevent];
            if (ev.getTime() > time)
                break;

            if (ev.getEvent() == SimEvent.END)
                return false;

            ev.doEvent();
            nextevent++;
            }

        // Now advance all the genes
        for (int i = 0; i < 3; i++)
            {
            genes[i].advance();
            }

        time++;
        return true;
        }
    }
