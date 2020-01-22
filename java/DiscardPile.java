/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fau.COT4930;


import java.util.Stack;

public class DiscardPile {
    private Stack <Card> pile;
    private int xLoc; //set up loc later
    private int yLoc;
    
    /**
     * Given input coordinates x and y, assigns xLoc and yLoc of card pile
     * creates a new stack for pile 
     * @param x
     * @param y 
     */
    public DiscardPile(int x, int y)
    {
        pile = new Stack<>();
        xLoc = x;
        yLoc = y;
    }
    /**
     * given card c, function pushes card to top of the discard pile
     * @param c 
     */
    public void addCardToPile(Card c)
    {
        if(c.getFace() == 0)
            c.flipCard();
        pile.push(c);
    }
    /**
     * pops the top card from the stack and returns card
     * @return 
     */
    public Card popCard()
    {
        return pile.pop();
    }
    /**
     * shows the top card of the stack without altering it
     * @return 
     */
    public Card showCard()
    {
        return pile.peek();
    }
    
    /**
     * returns the number of cards in the stack
     * @return 
     */
    public int getSize()
    {
        return pile.size();
    }
    
    /**
     * returns the x coordinate of the discard pile
     * @return 
     */
    public int getX()
    {
        return xLoc;
    }
    
    /**
     * returns the y coordinate of the discard pile
     * @return 
     */
    public int getY()
    {
        return yLoc;
    }
}
