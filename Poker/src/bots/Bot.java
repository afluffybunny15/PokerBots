package bots;

import java.util.ArrayList;

import table.Card;
import table.Player;
import table.Pot;

public abstract class Bot extends Player {

    public enum ActionList {
        fold, call, raise, check, bet
    }

    public Bot ( final String name, final double chips ) {
        super( name, chips );
        // TODO Auto-generated constructor stub
    }

    public abstract ActionList getAction ( ArrayList<Card> community, Pot pot );

    public abstract double getRaise ( Pot pot );

}
