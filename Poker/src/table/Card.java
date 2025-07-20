package table;

import java.util.ArrayList;

public class Card implements Comparable<Card> {
    public static enum Suit {
        Spades, Hearts, Clubs, Diamonds
    }

    public static enum Rank {
        Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King, Ace
    }

    private final Suit suit;
    private final Rank rank;

    public Card ( final Suit suit, final Rank rank ) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit () {
        return suit;
    }

    public Rank getRank () {
        return rank;
    }

    private String suitConvert ( final Suit suit ) {
        switch ( suit.ordinal() ) {
            case 0:
                return "♠";
            case 1:
                return "♥";
            case 2:
                return "♣";
            case 3:
                return "♦";
        }
        return null;
    }

    @Override
    public String toString () {
        String retStr = " ____ \n";
        retStr += "|    |\n";
        if ( rank.ordinal() >= 0 && rank.ordinal() < 8 ) {
            retStr += String.format( "| %d  |\n", rank.ordinal() + 2 );
        }
        else if ( rank.ordinal() != 8 ) {
            retStr += String.format( "| %c  |\n", rank.name().charAt( 0 ) );
        }
        else {
            retStr += String.format( "| 10 |\n", rank.name().charAt( 0 ) );
        }

        retStr += String.format( "| %s  |\n", suitConvert( suit ) );
        retStr += "|____|\n";
        return retStr;
    }

    @Override
    public int compareTo ( final Card o ) {
        if ( this.rank == Rank.Ace ) {
            if ( o.getRank() != Rank.Ace ) {
                return 1;
            }
            else {
                return 0;
            }
        }
        return this.rank.compareTo( o.rank );
    }

    public static String cardCat ( final String set1, final String set2 ) {
        final String[] lines1 = set1.split( "\n" );
        final String[] lines2 = set2.split( "\n" );
        String retStr = "";
        for ( int i = 0; i < lines1.length; i++ ) {
            retStr += lines1[i] + lines2[i] + "\n";
        }
        return retStr;
    }

    public static String cardCat ( final ArrayList<Card> cards ) {
        String retStr = cards.get( 0 ).toString();

        for ( int i = 1; i < cards.size(); i++ ) {
            retStr = Card.cardCat( retStr, cards.get( i ).toString() );
        }
        return retStr;
    }

    @Override
    public boolean equals ( final Object o ) {
        if ( o.getClass() != Card.class ) {
            return false;
        }
        return ( (Card) o ).getRank() == this.getRank() && ( (Card) o ).getSuit() == this.getSuit();
    }

}
