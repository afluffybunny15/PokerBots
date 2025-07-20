package game;

import java.util.ArrayList;

import table.Card;
import table.Card.Rank;
import table.Player;

public class Hand implements Comparable<Hand> {

    public static enum HandList {
        HighCard, Pair, TwoPair, ThreeOfAKind, Straight, Flush, FullHouse, FourOfAKind, StraightFlush
    }

    private final ArrayList<Card> cardsInHand;
    private HandList              hand;
    private ArrayList<Card>       bestArrangement;

    public Hand () {
        cardsInHand = new ArrayList<Card>();
    }

    public Hand ( final ArrayList<Card> cards ) {
        cardsInHand = new ArrayList<Card>( cards );
        checkHands( cardsInHand );

    }

    public void addCard ( final Card c ) {
        cardsInHand.add( c );
        checkHands( cardsInHand );
    }

    public void addCards ( final ArrayList<Card> cards ) {
        cardsInHand.addAll( cards );
        checkHands( cardsInHand );
    }

    public void checkHands ( final ArrayList<Card> cards ) {
        cards.sort( null );
        ArrayList<Card> tmp = null;
        tmp = hasStraightFlush( cards );
        if ( tmp != null ) {
            bestArrangement = tmp;
            hand = HandList.StraightFlush;
            return;
        }
        tmp = hasFourOfAKind( cards );
        if ( tmp != null ) {
            bestArrangement = tmp;
            bestArrangement.addAll( getKickers() );
            hand = HandList.FourOfAKind;
            return;
        }
        tmp = hasFullHouse( cards );
        if ( tmp != null ) {
            bestArrangement = tmp;
            hand = HandList.FullHouse;
            return;
        }
        tmp = hasFlush( cards );
        if ( tmp != null ) {
            bestArrangement = tmp;
            hand = HandList.Flush;
            return;
        }
        tmp = hasStraight( cards );
        if ( tmp != null ) {
            bestArrangement = tmp;
            hand = HandList.Straight;
            return;
        }
        tmp = hasThreeOfAKind( cards );
        if ( tmp != null ) {
            bestArrangement = tmp;
            bestArrangement.addAll( getKickers() );
            hand = HandList.ThreeOfAKind;
            return;
        }
        tmp = hasTwoPair( cards );
        if ( tmp != null ) {
            bestArrangement = tmp;
            bestArrangement.addAll( getKickers() );
            hand = HandList.TwoPair;
            return;
        }
        tmp = hasPair( cards );
        if ( tmp != null ) {
            bestArrangement = tmp;
            bestArrangement.addAll( getKickers() );
            hand = HandList.Pair;
            return;
        }
        bestArrangement = new ArrayList<Card>();
        bestArrangement.add( cards.getLast() );
        bestArrangement.addAll( getKickers() );
        hand = HandList.HighCard;
        return;
    }

    @Override
    public int compareTo ( final Hand o ) {
        if ( o == null ) {
            return 1;
        }
        if ( this.hand != o.hand ) {
            return this.getHand().ordinal() - o.getHand().ordinal();
        }
        if ( this.hand == HandList.Straight || this.hand == HandList.StraightFlush ) {
            final Rank thisRank = this.getBestArrangement().getFirst().getRank();
            final Rank otherRank = o.getBestArrangement().getFirst().getRank();
            if ( thisRank == Rank.Ace && otherRank != Rank.Ace ) {
                return -1;
            }
            else if ( thisRank != Rank.Ace && otherRank == Rank.Ace ) {
                return 1;
            }
        }

        for ( int i = 0; i < bestArrangement.size(); i++ ) {
            try {
                final int res = this.getBestArrangement().get( i ).compareTo( o.getBestArrangement().get( i ) );
                if ( res != 0 ) {
                    return res;
                }
            }
            catch ( final Exception e ) {
                System.out.println( Card.cardCat( this.getBestArrangement() ) );
                System.out.println( Card.cardCat( o.getBestArrangement() ) );
                throw new IndexOutOfBoundsException();
            }

        }
        return 0;
    }

    /**
     * @return the hand
     */
    public HandList getHand () {
        return hand;
    }

    /**
     * @param hand
     *            the hand to set
     */
    public void setHand ( final HandList hand ) {
        this.hand = hand;
    }

    /**
     * @return the bestArrangement
     */
    public ArrayList<Card> getBestArrangement () {
        return bestArrangement;
    }

    /**
     * @param bestArrangement
     *            the bestArrangement to set
     */
    public void setBestArrangement ( final ArrayList<Card> bestArrangement ) {
        this.bestArrangement = bestArrangement;
    }

    /**
     * @return the cardsInHand
     */
    public ArrayList<Card> getCardsInHand () {
        return cardsInHand;
    }

    private ArrayList<Card> hasPair ( final ArrayList<Card> cards ) {
        for ( int i = 0; i < cards.size() - 1; i++ ) {
            if ( cards.get( i ).getRank() == cards.get( i + 1 ).getRank() ) {
                final ArrayList<Card> pair = new ArrayList<Card>();
                pair.add( cards.get( i ) );
                pair.add( cards.get( i + 1 ) );
                return pair;
            }
        }
        return null;
    }

    private ArrayList<Card> hasTwoPair ( final ArrayList<Card> cards ) {
        final ArrayList<Card> pair1 = hasPair( cards );
        if ( pair1 == null ) {
            return null;
        }
        cards.removeAll( pair1 );
        final ArrayList<Card> pair2 = hasPair( cards );
        cards.addAll( pair1 );
        cards.sort( null );
        if ( pair2 != null ) {
            pair1.addAll( pair2 );
            return pair1;
        }
        return null;
    }

    private static ArrayList<Card> hasThreeOfAKind ( final ArrayList<Card> cards ) {
        for ( int i = 0; i < cards.size() - 2; i++ ) {
            if ( cards.get( i ).getRank() == cards.get( i + 1 ).getRank()
                    && cards.get( i ).getRank() == cards.get( i + 2 ).getRank() ) {
                final ArrayList<Card> triplets = new ArrayList<Card>();
                triplets.add( cards.get( i ) );
                triplets.add( cards.get( i + 1 ) );
                triplets.add( cards.get( i + 2 ) );
                return triplets;
            }
        }
        return null;
    }

    private static ArrayList<Card> hasStraight ( final ArrayList<Card> cards ) {
        if ( cards == null ) {
            return null;
        }
        final ArrayList<Card> straight = new ArrayList<Card>();
        Rank prevRank = null;
        int count = 0;
        for ( int i = cards.size() - 1; i >= 0; i-- ) {
            if ( prevRank != cards.get( i ).getRank() ) {
                prevRank = cards.get( i ).getRank();
                final Card next = containsPrev( cards.get( i ), cards );
                if ( next != null ) {
                    count++;
                    straight.addFirst( cards.get( i ) );
                }
                else {
                    straight.clear();
                    count = 0;
                }
                if ( count == 4 ) {
                    straight.addFirst( next );
                    return straight;
                }
            }

        }
        return null;
    }

    private static ArrayList<Card> hasFlush ( final ArrayList<Card> cards ) {

        final ArrayList<Card> clubs = new ArrayList<Card>();
        final ArrayList<Card> spades = new ArrayList<Card>();
        final ArrayList<Card> diamonds = new ArrayList<Card>();
        final ArrayList<Card> hearts = new ArrayList<Card>();
        for ( final Card c : cards ) {
            switch ( c.getSuit().name() ) {
                case ( "Hearts" ):
                    hearts.add( c );
                    break;
                case ( "Diamonds" ):
                    diamonds.add( c );
                    break;
                case ( "Spades" ):
                    spades.add( c );
                    break;
                default:
                    clubs.add( c );

            }
        }
        if ( spades.size() >= 5 ) {
            while ( spades.size() > 5 ) {
                spades.removeFirst();
            }
            return spades;
        }
        else if ( clubs.size() >= 5 ) {
            while ( clubs.size() > 5 ) {
                clubs.removeFirst();
            }
            return clubs;
        }
        else if ( hearts.size() >= 5 ) {
            while ( hearts.size() > 5 ) {
                hearts.removeFirst();
            }
            return hearts;
        }
        else if ( diamonds.size() >= 5 ) {
            while ( diamonds.size() > 5 ) {
                diamonds.removeFirst();
            }
            return diamonds;
        }

        return null;
    }

    private ArrayList<Card> hasFullHouse ( final ArrayList<Card> cards ) {
        ArrayList<Card> toak = hasThreeOfAKind( cards );
        if ( toak == null ) {
            return null;
        }
        cards.removeAll( toak );
        if ( null != hasThreeOfAKind( cards ) ) {
            final ArrayList<Card> temp = hasThreeOfAKind( cards );
            cards.addAll( toak );
            cards.sort( null );
            toak = temp;
            cards.removeAll( toak );
        }
        final ArrayList<Card> pair = hasPair( cards );
        if ( pair == null ) {
            cards.addAll( toak );
            cards.sort( null );
            return null;
        }
        cards.addAll( toak );
        cards.sort( null );
        toak.addAll( pair );
        return toak;
    }

    private ArrayList<Card> hasFourOfAKind ( final ArrayList<Card> cards ) {
        for ( int i = 0; i < cards.size() - 3; i++ ) {
            if ( cards.get( i ).getRank() == cards.get( i + 1 ).getRank()
                    && cards.get( i ).getRank() == cards.get( i + 2 ).getRank()
                    && cards.get( i ).getRank() == cards.get( i + 3 ).getRank() ) {
                final ArrayList<Card> foak = new ArrayList<Card>();
                foak.add( cards.get( i ) );
                foak.add( cards.get( i + 1 ) );
                foak.add( cards.get( i + 2 ) );
                foak.add( cards.get( i + 3 ) );
                return foak;
            }
        }
        return null;
    }

    private ArrayList<Card> hasStraightFlush ( final ArrayList<Card> cards ) {
        final ArrayList<Card> flush = hasFlushFull( cards );
        return hasStraight( flush );
    }

    private static ArrayList<Card> hasFlushFull ( final ArrayList<Card> cards ) {

        final ArrayList<Card> clubs = new ArrayList<Card>();
        final ArrayList<Card> spades = new ArrayList<Card>();
        final ArrayList<Card> diamonds = new ArrayList<Card>();
        final ArrayList<Card> hearts = new ArrayList<Card>();
        for ( final Card c : cards ) {
            switch ( c.getSuit().name() ) {
                case ( "Hearts" ):
                    hearts.add( c );
                    break;
                case ( "Diamonds" ):
                    diamonds.add( c );
                    break;
                case ( "Spades" ):
                    spades.add( c );
                    break;
                default:
                    clubs.add( c );

            }
        }
        if ( spades.size() >= 5 ) {

            return spades;
        }
        else if ( clubs.size() >= 5 ) {

            return clubs;
        }
        else if ( hearts.size() >= 5 ) {

            return hearts;
        }
        else if ( diamonds.size() >= 5 ) {

            return diamonds;
        }

        return null;
    }

    private static Card containsPrev ( final Card card, final ArrayList<Card> cards ) {
        for ( final Card c : cards ) {
            if ( card.getRank().ordinal() - 1 == c.getRank().ordinal() ) {
                return c;
            }
            if ( card.getRank() == Rank.Two && cards.getLast().getRank() == Rank.Ace ) {
                return cards.getLast();
            }
        }
        return null;
    }

    private ArrayList<Card> getKickers () {
        cardsInHand.removeAll( bestArrangement );
        final ArrayList<Card> kickers = new ArrayList<Card>();
        for ( int i = 0; kickers.size() + bestArrangement.size() < 5
                && cardsInHand.size() - kickers.size() != 0; i++ ) {
            kickers.add( cardsInHand.get( cardsInHand.size() - 1 - i ) );
        }
        cardsInHand.addAll( bestArrangement );
        cardsInHand.sort( null );
        return kickers;

    }

    public static ArrayList<Player> getBestHand ( final ArrayList<Player> players ) {
        final ArrayList<Player> bestPlayers = new ArrayList<Player>();
        Hand bestHand = null;
        for ( int i = 0; i < players.size(); i++ ) {
            if ( players.get( i ).getInTheGame() ) {
                final Hand contender = players.get( i ).getHand();
                if ( contender.compareTo( bestHand ) > 0 ) {
                    bestPlayers.clear();
                    bestPlayers.add( players.get( i ) );
                    bestHand = contender;
                }
                else if ( contender.compareTo( bestHand ) == 0 ) {
                    bestPlayers.add( players.get( i ) );
                }
            }

        }
        return bestPlayers;
    }

}
