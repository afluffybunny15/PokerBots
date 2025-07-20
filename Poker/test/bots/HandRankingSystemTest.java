package bots;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import game.Hand;
import game.Hand.HandList;
import table.Card;
import table.Card.Rank;
import table.Card.Suit;

class HandRankingSystemTest {

    private final Card s2 = new Card( Suit.Spades, Rank.Two );
    private final Card h2 = new Card( Suit.Hearts, Rank.Two );
    private final Card c2 = new Card( Suit.Clubs, Rank.Two );
    private final Card d2 = new Card( Suit.Diamonds, Rank.Two );

    private final Card sA = new Card( Suit.Spades, Rank.Ace );
    private final Card hA = new Card( Suit.Hearts, Rank.Ace );
    private final Card cA = new Card( Suit.Clubs, Rank.Ace );
    private final Card dA = new Card( Suit.Diamonds, Rank.Ace );

    private final Card s3 = new Card( Suit.Spades, Rank.Three );
    private final Card h3 = new Card( Suit.Hearts, Rank.Three );
    private final Card c3 = new Card( Suit.Clubs, Rank.Three );
    private final Card d3 = new Card( Suit.Diamonds, Rank.Three );

    private final Card s4 = new Card( Suit.Spades, Rank.Four );
    private final Card h4 = new Card( Suit.Hearts, Rank.Four );
    private final Card c4 = new Card( Suit.Clubs, Rank.Four );
    private final Card d4 = new Card( Suit.Diamonds, Rank.Four );

    private final Card s5 = new Card( Suit.Spades, Rank.Five );
    private final Card h5 = new Card( Suit.Hearts, Rank.Five );
    private final Card c5 = new Card( Suit.Clubs, Rank.Five );
    private final Card d5 = new Card( Suit.Diamonds, Rank.Five );

    private final Card s6 = new Card( Suit.Spades, Rank.Six );
    private final Card h6 = new Card( Suit.Hearts, Rank.Six );
    private final Card c6 = new Card( Suit.Clubs, Rank.Six );
    private final Card d6 = new Card( Suit.Diamonds, Rank.Six );

    private final Card s7 = new Card( Suit.Spades, Rank.Seven );
    private final Card h7 = new Card( Suit.Hearts, Rank.Seven );
    private final Card c7 = new Card( Suit.Clubs, Rank.Seven );
    private final Card d7 = new Card( Suit.Diamonds, Rank.Seven );

    private final Card s8 = new Card( Suit.Spades, Rank.Eight );
    private final Card h8 = new Card( Suit.Hearts, Rank.Eight );
    private final Card c8 = new Card( Suit.Clubs, Rank.Eight );
    private final Card d8 = new Card( Suit.Diamonds, Rank.Eight );

    private final Card s9 = new Card( Suit.Spades, Rank.Nine );
    private final Card h9 = new Card( Suit.Hearts, Rank.Nine );
    private final Card c9 = new Card( Suit.Clubs, Rank.Nine );
    private final Card d9 = new Card( Suit.Diamonds, Rank.Nine );

    private final Card sT = new Card( Suit.Spades, Rank.Ten );
    private final Card hT = new Card( Suit.Hearts, Rank.Ten );
    private final Card cT = new Card( Suit.Clubs, Rank.Ten );
    private final Card dT = new Card( Suit.Diamonds, Rank.Ten );

    private final Card sJ = new Card( Suit.Spades, Rank.Jack );
    private final Card hJ = new Card( Suit.Hearts, Rank.Jack );
    private final Card cJ = new Card( Suit.Clubs, Rank.Jack );
    private final Card dJ = new Card( Suit.Diamonds, Rank.Jack );

    private final Card sQ = new Card( Suit.Spades, Rank.Queen );
    private final Card hQ = new Card( Suit.Hearts, Rank.Queen );
    private final Card cQ = new Card( Suit.Clubs, Rank.Queen );
    private final Card dQ = new Card( Suit.Diamonds, Rank.Queen );

    private final Card sK = new Card( Suit.Spades, Rank.King );
    private final Card hK = new Card( Suit.Hearts, Rank.King );
    private final Card cK = new Card( Suit.Clubs, Rank.King );
    private final Card dK = new Card( Suit.Diamonds, Rank.King );

    @Test
    void testSimpleAce () {
        ArrayList<Hand> bestHands = HandRankingSystem.getBestHands();
        assertTrue( bestHands.getLast().getHand() == HandList.Pair
                && bestHands.getLast().getBestArrangement().get( 0 ).getRank() == Rank.Ace );

        final ArrayList<Card> community = new ArrayList<Card>();
        community.add( sA );
        bestHands = HandRankingSystem.getBestHands( community );
        assertTrue( bestHands.getLast().getHand() == HandList.ThreeOfAKind
                && bestHands.getLast().getBestArrangement().get( 0 ).getRank() == Rank.Ace );

        community.add( sK );
        bestHands = HandRankingSystem.getBestHands( community );
        assertEquals( bestHands.getLast().getHand(), HandList.ThreeOfAKind );
        assertEquals( bestHands.getLast().getBestArrangement().get( 0 ).getRank(), Rank.Ace );

        community.add( sQ );
        bestHands = HandRankingSystem.getBestHands( community );
        assertEquals( bestHands.getLast().getHand(), HandList.StraightFlush );
        assertEquals( bestHands.getLast().getBestArrangement().get( 4 ).getRank(), Rank.Ace );

    }

}
