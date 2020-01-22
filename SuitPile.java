package edu.fau.COT4930;

import java.util.Stack;


public class SuitPile {
    
    private Stack <Card> pile;
    private Card card;
    private int suit;
    private int xLoc;
    private int yLoc;
    
    /**
     * Each suit card will be initialized with its own stack (pile),
     * a location on the board with location (xLoc, yLoc)
     * and a suit value given by an int 0-3 (s)
     * @param x
     * @param y
     * @param s 
     */
    public SuitPile(int x, int y, int s)
    {
        pile = new Stack<>();
        suit = s;
        xLoc = x;
        yLoc = y;
    }
    
    /**
     * Checks suit and value of the top card of SuitPile
     * Returns true if:
     * Input card is in the same suit
     * and input card is the next logical value to the stack
     * @param c
     * @return 
     */
    public boolean canAdd(Card c)
    {
        int val;
        int v;
        if(c.getSuit() != suit)
            return false;
        
        v = c.getValue();
        if(pile.isEmpty() && v == 12)
            return true;
        
        if(!pile.isEmpty())
        {
            card = pile.peek();
            val = card.getValue();
            if (val == 12)
                val = -1;
            if(v == (val+1))
                return true;
        }
        return false;
    }
    
    /**
     * returns true if all all suit cards A-K are in stack
     * @return 
     */
    public boolean completed()
    {
        if(pile.size() == 13)
            return true;
        return false;
    }
    /**
     * Pushes a card to the top of the suit stack
     * @param c 
     */
    public void addCardToPile(Card c)
    {
        pile.push(c);
    }
    
    /**
     * Shows the top card of the stack without altering it
     * @return 
     */
    public Card showCard()
    {
        return pile.peek();
    }
    
    /**
     * returns the size of the suit pile
     * @return 
     */
    public int getSize()
    {
        return pile.size();
    }
    
    /**
     * returns the x coordinate of suit pile
     * @return 
     */
    public int getX()
    {
        return xLoc;
    }
    
    /**
     * returns the y coordinate of the suit pile
     * @return 
     */
    public int getY()
    {
        return yLoc;
    }
}
