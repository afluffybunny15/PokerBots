package table;

import java.util.ArrayList;

import game.Hand;

public class Player {
    private String                name;
    private double                chips;
    private double                moneyInPot;
    private double                totalMoneyIn;
    private boolean               inTheGame = false;
    private Hand                  hand;
    private final ArrayList<Card> dealt;

    public ArrayList<Card> getDealt () {
        return dealt;
    }

    public void addCardPublic ( final Card card ) {
        hand.addCard( card );
    }

    public void addCardPrivate ( final Card card ) {
        dealt.add( card );
        hand.addCard( card );
    }

    public Hand getHand () {
        return hand;
    }

    public void clearCards () {
        hand = new Hand();
        dealt.clear();
    }

    public void setTotalMoneyIn ( final double totalMoneyIn ) {
        this.totalMoneyIn = totalMoneyIn;
    }

    public double getTotalMoneyIn () {
        return totalMoneyIn;
    }

    /**
     * @return the name
     */
    public String getName () {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName ( final String name ) {
        this.name = name;
    }

    public boolean getInTheGame () {
        return inTheGame;
    }

    public void setInTheGame ( final boolean inTheGame ) {
        this.inTheGame = inTheGame;
    }

    /**
     * @return the chips
     */
    public double getChips () {
        return chips;
    }

    /**
     * @param chips
     *            the chips to set
     */
    public void setChips ( final double chips ) {
        if ( chips < 0 ) {
            throw new IllegalArgumentException( "Player does not have enough money" );
        }
        this.chips = chips;
    }

    /**
     * @return the bet
     */
    public double getMoneyInPot () {
        return moneyInPot;
    }

    /**
     * @param bet
     *            the bet to set
     */
    public void setMoneyInPot ( final double moneyInPot ) {
        this.moneyInPot = moneyInPot;
    }

    public Player ( final String name, final double chips ) {
        super();
        this.name = name;
        this.chips = chips;
        this.moneyInPot = 0;
        this.totalMoneyIn = 0;
        hand = new Hand();
        dealt = new ArrayList<Card>();
    }

}
