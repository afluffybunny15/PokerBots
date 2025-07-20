package table;

import java.util.ArrayList;

public class Pot {
    private double                   total;
    private double                   betToCall;
    private double                   currentRoundMoney;
    private final ArrayList<Player>  players;
    private final ArrayList<SidePot> sidePots;

    public class SidePot {
        private final ArrayList<Player> playersInPot;
        private final double            maxBet;

        public SidePot ( final ArrayList<Player> playersInPot, final double maxBet ) {
            this.playersInPot = playersInPot;
            this.maxBet = maxBet;
        }

        public ArrayList<Player> getPlayersInPot () {
            return playersInPot;
        }

        public double getMaxBet () {
            return maxBet;
        }
    }

    public ArrayList<SidePot> getSidePots () {
        return sidePots;
    }

    public Pot ( final ArrayList<Player> players ) {
        this.total = 0;
        this.betToCall = 0;
        this.players = players;
        sidePots = new ArrayList<SidePot>();
    }

    public boolean bet ( final Player player, final double bet ) {
        if ( betToCall > bet + player.getMoneyInPot() && betToCall > 0 ) {
            throw new IllegalArgumentException( "Bet must match money in the pot" );
        }
        player.setChips( player.getChips() - bet );
        player.setMoneyInPot( player.getMoneyInPot() + bet );
        player.setTotalMoneyIn( player.getTotalMoneyIn() + bet );
        setCurrentRoundMoney( getCurrentRoundMoney() + bet );
        if ( player.getMoneyInPot() > betToCall ) {
            setBetToCall( player.getMoneyInPot() );
        }
        return true;
    }

    public boolean raise ( final Player player, final double raise ) {
        if ( betToCall > raise && betToCall > 0 ) {
            throw new IllegalArgumentException( "Raise must match money in the pot" );
        }

        player.setChips( player.getChips() - raise + player.getMoneyInPot() );
        setCurrentRoundMoney( getCurrentRoundMoney() - player.getMoneyInPot() + raise );
        player.setTotalMoneyIn( player.getTotalMoneyIn() - player.getMoneyInPot() + raise );
        player.setMoneyInPot( raise );

        if ( player.getMoneyInPot() > betToCall ) {
            setBetToCall( player.getMoneyInPot() );
        }
        return true;
    }

    public void nextRound () {
        this.betToCall = 0;
        this.total += currentRoundMoney;
        currentRoundMoney = 0;
        for ( final Player p : players ) {
            p.setMoneyInPot( 0 );
        }
    }

    /**
     * @return the total
     */
    public double getTotal () {
        return total;
    }

    /**
     * @param total
     *            the total to set
     */
    public void setTotal ( final double total ) {
        this.total = total;
    }

    /**
     * @return the bet
     */
    public double getBetToCall () {
        return betToCall;
    }

    /**
     * @param bet
     *            the bet to set
     */
    public void setBetToCall ( final double betToCall ) {
        this.betToCall = betToCall;
    }

    public double getCurrentRoundMoney () {
        return currentRoundMoney;
    }

    public void setCurrentRoundMoney ( final double currentRoundMoney ) {
        this.currentRoundMoney = currentRoundMoney;
    }

    @Override
    public String toString () {
        String retStr = String.format( "|  Player  |  Stake  |   Bet   |  Total  |\n" );
        for ( final Player p : players ) {
            if ( p.getInTheGame() ) {
                retStr += String.format( "|%10s| %7.2f | %7.2f | %7.2f |\n", p.getName(), p.getChips(),
                        p.getMoneyInPot(), p.getTotalMoneyIn() );
            }
        }
        return retStr;
    }

}
