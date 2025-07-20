package game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import bots.Bot;
import bots.Bot.ActionList;
import bots.StatBotBasic;
import table.Card;
import table.Deck;
import table.Player;
import table.Pot;

public class main {

    public static int numHands = 100;

    /**
     * Runs the poker game. 1. Adds Players 2. Initializes the Pot 3. Sets up
     * the betting/dealing order 4. Players do a round of betting 5. Flop is
     * dealt 6. Players do a round of betting 7. Turn is dealt 8. Players do a
     * round of betting 9. River is dealt 10. Players do a round of betting 11.
     * Winner takes the pot
     *
     * @param args
     *            CMD-line args
     */
    public static void main ( final String[] args ) {
        final Player player1 = new Player( "Andrew", 1000 );
        // final Player player2 = new Player( "Ava", 100 );
        // final Player player3 = new Player( "Holden", 100 );
        // final Player bot1 = new RandomBot( "Random Bot", 1000 );
        // final Player bot2 = new NaiveBot( "Naive Bot", 1000 );
        final Player bot3 = new StatBotBasic( "Stat Bot", 1000, 0.1, 0.5 );

        final ArrayList<Player> players = new ArrayList<Player>();
        players.add( player1 );
        // players.add( player2 );
        // players.add( player3 );
        // players.add( bot1 );
        // players.add( bot2 );
        players.add( bot3 );

        int dealerIdx = 0;
        final Scanner input = new Scanner( System.in );
        for ( int i = 0; i < numHands && players.size() > 1; i++ ) {
            final Deck deck = new Deck();
            final Pot pot = initRound( dealerIdx, players, 10, deck );
            final Queue<Player> order = new LinkedList<Player>();
            for ( int j = 0; j < players.size(); j++ ) {
                order.add( players.get( ( j + dealerIdx ) % players.size() ) );
            }
            betting( players, order, pot, input, deck );
            final ArrayList<Card> flop = deck.flop();
            for ( final Card c : flop ) {
                for ( final Player p : players ) {
                    p.addCardPublic( c );
                }
            }
            order.clear();
            for ( int j = 0; j < players.size(); j++ ) {
                if ( players.get( ( j + dealerIdx ) % players.size() ).getInTheGame() ) {
                    order.add( players.get( ( j + dealerIdx ) % players.size() ) );
                }
            }
            if ( order.size() != 1 ) {
                betting( players, order, pot, input, deck );
            }
            final Card turn = deck.turn();
            for ( final Player p : players ) {
                p.addCardPublic( turn );
            }
            order.clear();
            for ( int j = 0; j < players.size(); j++ ) {
                if ( players.get( ( j + dealerIdx ) % players.size() ).getInTheGame() ) {
                    order.add( players.get( ( j + dealerIdx ) % players.size() ) );
                }
            }
            if ( order.size() != 1 ) {
                betting( players, order, pot, input, deck );
            }
            final Card river = deck.river();
            for ( final Player p : players ) {
                p.addCardPublic( river );
            }
            order.clear();
            for ( int j = 0; j < players.size(); j++ ) {
                if ( players.get( ( j + dealerIdx ) % players.size() ).getInTheGame() ) {
                    order.add( players.get( ( j + dealerIdx ) % players.size() ) );
                }
            }
            if ( order.size() != 1 ) {
                betting( players, order, pot, input, deck );
            }
            final ArrayList<Player> winners = Hand.getBestHand( players );
            for ( final Player winner : winners ) {
                final double earnings = pot.getTotal() / winners.size();
                winner.setChips( winner.getChips() + earnings );
                System.out.println( "Congratulations to " + winner.getName() + " for winning "
                        + ( earnings - winner.getMoneyInPot() ) + " with a " + winner.getHand().getHand().toString()
                        + "!" );
                System.out.println( Card.cardCat( winner.getDealt() ) );
                System.out.println( Card.cardCat( deck.getCommunityCards() ) );
            }
            for ( final Player p : players ) {
                p.setTotalMoneyIn( 0 );
            }
            try {
                Thread.sleep( 2000 );
            }
            catch ( final InterruptedException e ) {
                e.printStackTrace();
            }
            dealerIdx++;
        }
        for ( final Player p : players ) {
            System.out.println( p.getName() + " ends the game with " + p.getChips() );
        }

    }

    private static void betting ( final ArrayList<Player> players, final Queue<Player> order, final Pot pot,
            final Scanner input, final Deck deck ) {
        if ( order.size() == 1 ) {
            return;
        }
        while ( !order.isEmpty() ) {
            final Player player = order.remove();
            if ( player.getClass().getPackageName().equals( "bots" ) ) {
                handleBot( (Bot) player, pot, order, players, deck.getCommunityCards() );
                System.out.println( pot );

                System.out.print( "\033[H\033[2J" );
                System.out.flush();
            }
            else {
                System.out.print( "\033[2J" );
                System.out.flush();
                boolean done = false;
                while ( !done ) {
                    final String hand = Card.cardCat( player.getDealt().get( 0 ).toString(),
                            player.getDealt().get( 1 ).toString() );
                    System.out.println( "Hello " + player.getName() + ", you have:" );
                    System.out.println( hand );
                    if ( !deck.getCommunityCards().isEmpty() ) {
                        System.out.println( "The community cards are: " );
                        String comm = "";
                        for ( final Card c : deck.getCommunityCards() ) {
                            if ( comm.isBlank() ) {
                                comm = c.toString();
                            }
                            else {
                                comm = Card.cardCat( comm, c.toString() );
                            }
                        }
                        System.out.println( comm );
                    }
                    System.out.println( pot );
                    System.out.println( "OPTIONS-----" );
                    System.out.println( "   * Fold  - Forfeit the hand. No more action comes to you." );
                    if ( pot.getBetToCall() == player.getMoneyInPot() ) {
                        System.out.println( "   * Check - Only available if nobody has made a bet. Passes your turn." );
                        System.out.println( "   * Bet   - Allows you to input a new bet." );
                    }
                    else {
                        System.out.println( "   * Call  - Matches the highest bet." );
                        System.out.println( "   * Raise - Allows you to input a new highest bet." );
                    }

                    final String option = input.next().trim().toLowerCase();
                    if ( option.equals( "fold" ) ) {
                        player.setInTheGame( false );
                        done = true;
                    }
                    else if ( pot.getBetToCall() - player.getMoneyInPot() == 0 && option.equals( "check" ) ) {
                        done = true;
                    }
                    else if ( option.equals( "call" ) ) {
                        try {
                            pot.bet( player, pot.getBetToCall() - player.getMoneyInPot() );
                            done = true;
                        }
                        catch ( final Exception e ) {
                            System.out.println(
                                    "You do not have enough money to call. A side pot will be created for you" );
                        }
                    }
                    else if ( option.equals( "raise" ) || option.equals( "bet" ) ) {
                        System.out.println( "How much would you like to raise to?" );
                        try {
                            final double raise = Double.parseDouble( input.next() );
                            try {
                                pot.raise( player, raise );
                                order.clear();
                                for ( int i = 1; i < players.size(); i++ ) {
                                    if ( players.get( ( i + players.indexOf( player ) ) % players.size() )
                                            .getInTheGame() ) {
                                        order.add( players.get( ( i + players.indexOf( player ) ) % players.size() ) );
                                    }

                                }
                                done = true;
                            }
                            catch ( final Exception e ) {
                                System.out.println( e.getMessage() );
                            }
                        }
                        catch ( final Exception e ) {
                            System.out.println( "Could not read. Please restart." );
                        }

                    }
                }

            }
        }
        pot.nextRound();

    }

    private static void handleBot ( final Bot bot, final Pot pot, final Queue<Player> order,
            final ArrayList<Player> players, final ArrayList<Card> community ) {
        final ActionList action = bot.getAction( community, pot );
        switch ( action ) {
            case ActionList.fold:
                bot.setInTheGame( false );
                System.out.println( bot.getName() + " folds" );
                break;
            case ActionList.call:
                System.out.println( bot.getName() + " calls" );
                try {
                    pot.bet( bot, pot.getBetToCall() - bot.getMoneyInPot() );
                }
                catch ( final Exception e ) {
                    pot.bet( bot, bot.getChips() );
                    System.out.println( bot.getName() + " goes all in" );
                }
                break;
            case ActionList.raise:
                System.out.println( bot.getName() + " raises" );
                try {
                    final double raise = bot.getRaise( pot );
                    System.out.println( "Amount: " + raise );
                    pot.raise( bot, raise );
                    order.clear();
                    for ( int i = 1; i < players.size(); i++ ) {
                        if ( players.get( ( i + players.indexOf( bot ) ) % players.size() ).getInTheGame() ) {
                            order.add( players.get( ( i + players.indexOf( bot ) ) % players.size() ) );
                        }

                    }
                }
                catch ( final Exception e ) {
                    bot.setInTheGame( false );
                    System.out.println( bot.getName() + " folds" );
                }
                break;
            default:
                // Not Yet Implemented
                break;
        }

    }

    private static Pot initRound ( final int dealerIdx, final ArrayList<Player> players, final double bigBlind,
            final Deck deck ) {
        for ( final Player player : players ) {
            player.clearCards();
        }
        final Pot pot = new Pot( players );
        boolean littleGood = false;
        boolean bigGood = false;
        int seatMod = -1;
        while ( !littleGood && !bigGood && players.size() > 1 ) {
            seatMod++;
            littleGood = false;
            bigGood = false;
            final Player little = players.get( ( dealerIdx - 2 + players.size() - seatMod ) % players.size() );
            final Player big = players.get( ( dealerIdx - 1 + players.size() - seatMod ) % players.size() );
            if ( little.getChips() < bigBlind / 2 ) {
                players.remove( little );
                System.out.println( little.getName() + " does not have enough money to continue." );
            }
            else {
                littleGood = true;
            }
            if ( big.getChips() >= bigBlind ) {
                if ( littleGood ) {
                    pot.bet( players.get( ( dealerIdx - 2 + players.size() ) % players.size() ), bigBlind / 2 );
                    pot.bet( players.get( ( dealerIdx - 1 + players.size() ) % players.size() ), bigBlind );
                    bigGood = true;
                }
            }
            else {
                players.remove( big );
                System.out.println( big.getName() + " does not have enough money to continue." );
            }

        }
        for ( final Player player : players ) {
            player.setInTheGame( true );
        }

        for ( int i = 0; i < 2; i++ ) {
            for ( int j = 0; j < players.size(); j++ ) {
                final Player player = players.get( ( j + dealerIdx ) % players.size() );
                player.addCardPrivate( deck.deal() );
            }
        }
        return pot;
    }

}
