package bots;

import java.util.ArrayList;

import table.Card;
import table.Pot;

public class NaiveBot extends Bot {

    public NaiveBot ( final String name, final double chips ) {
        super( name, chips );
        // TODO Auto-generated constructor stub
    }

    @Override
    public ActionList getAction ( final ArrayList<Card> community, final Pot pot ) {
        return ActionList.call;
    }

    @Override
    public double getRaise ( final Pot pot ) {
        return pot.getBetToCall();
    }

}
