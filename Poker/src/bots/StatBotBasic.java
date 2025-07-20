package bots;

import java.util.ArrayList;

import game.Hand;
import table.Card;
import table.Pot;

public class StatBotBasic extends Bot {
    private final double bias;
    private final double raiseThreshold;
    private double       odds;

    public StatBotBasic ( final String name, final double chips, final double bias, final double raiseThreshold ) {
        super( name, chips );
        this.bias = bias;
        this.raiseThreshold = raiseThreshold;
    }

    @Override
    public ActionList getAction ( final ArrayList<Card> community, final Pot pot ) {
        ArrayList<Hand> bestHands = null;
        if ( community == null ) {
            bestHands = HandRankingSystem.getBestHands();
        }
        else {
            bestHands = HandRankingSystem.getBestHands( community );
        }
        odds = HandRankingSystem.percentBest( bestHands, getHand() ) + bias;
        // System.out.println( "Odds: " + odds );
        final double random = Math.random();
        if ( odds < random ) {
            if ( pot.getBetToCall() != 0 ) {
                return ActionList.fold;
            }
            else {
                return ActionList.call;
            }
        }
        else if ( random < odds * raiseThreshold ) {
            return ActionList.raise;
        }
        else {
            return ActionList.call;
        }

    }

    @Override
    public double getRaise ( final Pot pot ) {
        return Math.min( Math.exp( 7 * ( ( odds - bias ) - 1 ) ) * getChips() + pot.getBetToCall(),
                Math.floor( getChips() + this.getMoneyInPot() ) );
    }

}
