package bots;

import java.util.ArrayList;

import table.Card;
import table.Pot;

public class RandomBot extends Bot {

    public RandomBot ( final String name, final double chips ) {
        super( name, chips );
        // TODO Auto-generated constructor stub
    }

    @Override
    public ActionList getAction ( final ArrayList<Card> community, final Pot pot ) {
        final int actOrdinal = (int) ( ( 3 + 1 ) * Math.random() );
        if ( pot.getBetToCall() == 0 && ActionList.values()[actOrdinal] == ActionList.fold ) {
            return ActionList.call;
        }
        return ActionList.values()[actOrdinal];
    }

    @Override
    public double getRaise ( final Pot pot ) {
        return Math.max( ( Math.random() * getChips() + pot.getBetToCall() ), pot.getBetToCall() );
    }
}
