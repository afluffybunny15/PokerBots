package game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import game.Hand.HandList;
import table.Card;
import table.Card.Rank;
import table.Card.Suit;

class HandTest {

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
    void testPair () {
        final ArrayList<Card> community = new ArrayList<Card>();
        community.add( sA );
        community.add( s3 );
        final Hand hand1 = new Hand( community );
        assertEquals( Hand.HandList.HighCard, hand1.getHand() );
        hand1.addCard( cA );
        assertEquals( Hand.HandList.Pair, hand1.getHand() );

        final Hand hand2 = new Hand( community );
        assertEquals( Hand.HandList.HighCard, hand2.getHand() );
        hand2.addCard( c3 );
        assertEquals( Hand.HandList.Pair, hand2.getHand() );
        assertTrue( hand1.compareTo( hand2 ) > 0 );

        hand1.addCard( s4 );
        final Hand hand3 = new Hand( community );
        hand3.addCard( hA );
        hand3.addCard( cK );

        assertTrue( hand3.compareTo( hand1 ) > 0 );

    }

    @Test
    void testTwoPair () {
        final ArrayList<Card> community = new ArrayList<Card>();
        community.add( sA );
        community.add( d3 );
        community.add( s3 );
        final Hand hand1 = new Hand( community );
        assertEquals( Hand.HandList.Pair, hand1.getHand() );
        hand1.addCard( hA );
        assertEquals( Hand.HandList.TwoPair, hand1.getHand() );
        hand1.addCard( h5 );
        assertEquals( Hand.HandList.TwoPair, hand1.getHand() );

        final Hand hand2 = new Hand( community );
        assertEquals( Hand.HandList.Pair, hand2.getHand() );
        hand2.addCard( s5 );
        assertEquals( Hand.HandList.Pair, hand2.getHand() );
        hand2.addCard( c5 );
        assertEquals( Hand.HandList.TwoPair, hand2.getHand() );
        assertTrue( hand1.compareTo( hand2 ) > 0 );

        final Hand hand3 = new Hand( community );
        hand3.addCard( cA );
        hand3.addCard( cK );
        assertTrue( hand3.compareTo( hand2 ) > 0 );

    }

    @Test
    void testThreeOfAKind () {
        final ArrayList<Card> community = new ArrayList<Card>();
        community.add( sA );
        community.add( s3 );
        community.add( d5 );
        final Hand hand1 = new Hand( community );
        assertEquals( Hand.HandList.HighCard, hand1.getHand() );
        hand1.addCard( cA );
        assertEquals( Hand.HandList.Pair, hand1.getHand() );
        hand1.addCard( hA );
        assertEquals( Hand.HandList.ThreeOfAKind, hand1.getHand() );

        final Hand hand2 = new Hand( community );
        assertEquals( Hand.HandList.HighCard, hand2.getHand() );
        hand2.addCard( c3 );
        assertEquals( Hand.HandList.Pair, hand2.getHand() );
        hand2.addCard( h3 );
        assertEquals( Hand.HandList.ThreeOfAKind, hand2.getHand() );
        assertTrue( hand1.compareTo( hand2 ) > 0 );
    }

    @Test
    void testStraightLow () {
        final ArrayList<Card> community = new ArrayList<Card>();
        community.add( sA );
        community.add( c2 );
        community.add( s3 );
        community.add( d5 );
        final Hand hand1 = new Hand( community );
        assertEquals( Hand.HandList.HighCard, hand1.getHand() );
        hand1.addCard( h4 );
        hand1.addCard( h8 );
        assertEquals( Hand.HandList.Straight, hand1.getHand() );

        final Hand hand2 = new Hand( community );
        hand2.addCard( h4 );
        hand2.addCard( h6 );

        assertTrue( hand2.compareTo( hand1 ) > 0 );
    }

    @Test
    void testStraightHigh () {
        final ArrayList<Card> community = new ArrayList<Card>();
        community.add( sT );
        community.add( cK );
        community.add( sQ );
        community.add( dJ );
        final Hand hand1 = new Hand( community );
        assertEquals( Hand.HandList.HighCard, hand1.getHand() );
        hand1.addCard( h9 );
        assertEquals( Hand.HandList.Straight, hand1.getHand() );

        final Hand hand2 = new Hand( community );
        hand2.addCard( hA );

        assertTrue( hand2.compareTo( hand1 ) > 0 );
    }

    @Test
    void testStraightGame () {
        final ArrayList<Card> dealt = new ArrayList<Card>();
        dealt.add( s7 );
        dealt.add( hT );
        final Hand h1 = new Hand( dealt );
        h1.addCard( s8 );
        h1.addCard( d9 );
        h1.addCard( h9 );
        h1.addCard( d8 );
        h1.addCard( d6 );
        assertEquals( h1.getHand(), HandList.Straight );
    }

    @Test
    void testFlush () {
        final ArrayList<Card> community = new ArrayList<Card>();
        community.add( sA );
        community.add( s3 );
        community.add( s5 );
        community.add( s7 );

        final Hand hand1 = new Hand( community );
        assertEquals( Hand.HandList.HighCard, hand1.getHand() );
        hand1.addCard( s9 );
        assertEquals( Hand.HandList.Flush, hand1.getHand() );

        final Hand hand2 = new Hand( community );
        assertEquals( Hand.HandList.HighCard, hand2.getHand() );
        hand2.addCard( s8 );
        assertEquals( Hand.HandList.Flush, hand2.getHand() );

        assertTrue( hand1.compareTo( hand2 ) > 0 );
    }

    @Test
    void testFullHouse () {
        final ArrayList<Card> community = new ArrayList<Card>();
        community.add( sA );
        community.add( s5 );
        community.add( d5 );

        final Hand hand1 = new Hand( community );
        assertEquals( Hand.HandList.Pair, hand1.getHand() );
        hand1.addCard( cA );
        assertEquals( Hand.HandList.TwoPair, hand1.getHand() );
        hand1.addCard( hA );
        assertEquals( Hand.HandList.FullHouse, hand1.getHand() );

        final Hand hand2 = new Hand( community );
        assertEquals( Hand.HandList.Pair, hand2.getHand() );
        hand2.addCard( dA );
        assertEquals( Hand.HandList.TwoPair, hand2.getHand() );
        hand2.addCard( c5 );
        assertEquals( Hand.HandList.FullHouse, hand2.getHand() );

        assertTrue( hand1.compareTo( hand2 ) > 0 );
    }

    @Test
    void testFourOfAKind () {
        final ArrayList<Card> community = new ArrayList<Card>();
        community.add( sA );
        community.add( dA );
        community.add( sK );
        community.add( dK );

        final Hand hand1 = new Hand( community );
        assertEquals( Hand.HandList.TwoPair, hand1.getHand() );
        hand1.addCard( cA );
        assertEquals( Hand.HandList.FullHouse, hand1.getHand() );
        hand1.addCard( hA );
        assertEquals( Hand.HandList.FourOfAKind, hand1.getHand() );

        final Hand hand2 = new Hand( community );
        assertEquals( Hand.HandList.TwoPair, hand2.getHand() );
        hand2.addCard( cK );
        assertEquals( Hand.HandList.FullHouse, hand2.getHand() );
        hand2.addCard( hK );
        assertEquals( Hand.HandList.FourOfAKind, hand2.getHand() );

        assertTrue( hand1.compareTo( hand2 ) > 0 );
    }

    @Test
    void testStraightFlushLow () {
        final ArrayList<Card> community = new ArrayList<Card>();
        community.add( sA );
        community.add( s2 );
        community.add( s3 );
        community.add( s5 );
        final Hand hand1 = new Hand( community );
        assertEquals( Hand.HandList.HighCard, hand1.getHand() );
        hand1.addCard( s4 );
        assertEquals( Hand.HandList.StraightFlush, hand1.getHand() );

        final Hand hand2 = new Hand( community );
        hand2.addCard( s4 );
        hand2.addCard( s6 );
        assertEquals( Hand.HandList.StraightFlush, hand1.getHand() );
        assertTrue( hand2.compareTo( hand1 ) > 0 );
    }

    @Test
    void testStraightFlushHigh () {
        final ArrayList<Card> community = new ArrayList<Card>();
        community.add( sT );
        community.add( sK );
        community.add( sQ );
        community.add( sJ );
        final Hand hand1 = new Hand( community );
        assertEquals( Hand.HandList.HighCard, hand1.getHand() );
        hand1.addCard( s9 );
        assertEquals( Hand.HandList.StraightFlush, hand1.getHand() );

        final Hand hand2 = new Hand( community );
        hand2.addCard( sA );
        assertEquals( Hand.HandList.StraightFlush, hand2.getHand() );

        assertTrue( hand2.compareTo( hand1 ) > 0 );

    }

}
