package bots;

import java.util.ArrayList;

import game.Hand;
import table.Card;
import table.Deck;

public class HandRankingSystem {
    public static ArrayList<Hand> getBestHands ( final ArrayList<Card> community ) {
        final ArrayList<Hand> retArr = new ArrayList<Hand>();
        final Deck d1 = new Deck();
        for ( final Card c1 : d1.getCards() ) {
            final Deck d2 = new Deck();
            for ( final Card c2 : d2.getCards() ) {
                if ( !c1.equals( c2 ) && !community.contains( c1 ) && !community.contains( c2 ) ) {
                    final ArrayList<Card> allCards = new ArrayList<Card>();
                    allCards.addAll( community );
                    allCards.add( c1 );
                    allCards.add( c2 );
                    final Hand newHand = new Hand( allCards );
                    retArr.add( newHand );
                }
            }
        }
        retArr.sort( null );
        return retArr;
    }

    public static ArrayList<Hand> getBestHands () {
        final ArrayList<Hand> retArr = new ArrayList<Hand>();
        final Deck d1 = new Deck();
        for ( final Card c1 : d1.getCards() ) {
            final Deck d2 = new Deck();
            for ( final Card c2 : d2.getCards() ) {
                if ( !c1.equals( c2 ) ) {
                    final ArrayList<Card> allCards = new ArrayList<Card>();
                    allCards.add( c1 );
                    allCards.add( c2 );
                    final Hand newHand = new Hand( allCards );
                    retArr.add( newHand );
                }
            }
        }
        retArr.sort( null );
        return retArr;
    }

    public static double percentBest ( final ArrayList<Hand> hands, final Hand hand ) {
        int idx = 0;
        while ( hand.compareTo( hands.get( idx ) ) > 0 && idx < hands.size() - 1 ) {
            idx++;
        }
        return ( (double) idx + 1 ) / hands.size();
    }
}
