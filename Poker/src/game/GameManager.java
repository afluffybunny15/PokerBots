package game;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

import table.Deck;
import table.Player;
import table.Pot;

public class GameManager {
    private static GameManager      gm;
    private final ArrayList<Player> players;
    private final Pot               pot;
    private Deck                    deck;
    private final int               dealer;
    private double                  ante;

    public GameManager ( final ArrayList<Player> players ) {
        if ( gm == null ) {
            initialize( players );
        }
        this.players = players;
        this.pot = new Pot( players );
        this.deck = new Deck();
        this.dealer = 0;
    }

    public GameManager initialize ( final ArrayList<Player> players ) {
        gm = new GameManager( players );
        return gm;
    }

    public Queue<Player> setupRound () {
        this.deck = new Deck();
        final Queue<Player> order = new PriorityQueue<Player>();
        for ( int i = 0; i < players.size(); i++ ) {
            order.add( players.get( ( i + dealer + 1 ) % players.size() ) );
        }
        while ( players.get( dealer ).getChips() <= ante ) {
            System.out.println(
                    players.get( dealer ).getName() + " is out of money and has been removed from the table." );
            players.remove( dealer );

        }
        while ( players.get( ( dealer + players.size() - 1 ) % players.size() ).getChips() <= ante / 2 ) {
            System.out.println( players.get( ( dealer + players.size() - 1 ) % players.size() ).getName()
                    + " is out of money and has been removed from the table." );
            players.remove( ( dealer + players.size() - 1 ) % players.size() );
        }
        if ( players.size() == 1 ) {
            System.out.println( "The game is over!" );
            return null;
        }
        pot.nextRound();
        pot.bet( players.get( ( dealer + players.size() - 1 ) % players.size() ), ante / 2 );
        pot.bet( players.get( dealer ), ante );
        deck = new Deck();
        for ( final Player p : players ) {
            p.addCardPrivate( deck.deal() );
            p.addCardPrivate( deck.deal() );
        }
        return order;
    }

}
