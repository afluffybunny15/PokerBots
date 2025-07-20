package table;

import java.util.ArrayList;

import table.Card.Rank;
import table.Card.Suit;

public class Deck {
    ArrayList<Card> cards;
    ArrayList<Card> communityCards;

    public Deck () {
        cards = initDeck();
        communityCards = new ArrayList<Card>();
    }

    public ArrayList<Card> getCards () {
        return cards;
    }

    public ArrayList<Card> flop () {
        communityCards.add( deal() );
        communityCards.add( deal() );
        communityCards.add( deal() );
        return communityCards;
    }

    public Card turn () {
        final Card turn = deal();
        communityCards.add( turn );
        return turn;
    }

    public Card river () {
        final Card river = deal();
        communityCards.add( river );
        return river;
    }

    public Card deal () {
        return cards.removeLast();
    }

    public ArrayList<Card> getCommunityCards () {
        return communityCards;
    }

    private ArrayList<Card> initDeck () {
        ArrayList<Card> cards = new ArrayList<Card>();
        for ( final Suit suit : Card.Suit.values() ) {
            for ( final Rank rank : Card.Rank.values() ) {
                cards.add( new Card( suit, rank ) );
            }
        }
        cards = shuffle( cards );
        return cards;
    }

    private ArrayList<Card> shuffle ( final ArrayList<Card> cards ) {
        final ArrayList<Card> shuffled = new ArrayList<Card>();
        for ( final Card card : cards ) {
            shuffled.add( (int) ( Math.random() * ( shuffled.size() + 1 ) ), card );
        }
        return shuffled;
    }

}
