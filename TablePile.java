/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fau.COT4930;

import java.util.Stack;


public class TablePile {
    
    private Stack <Card> pile;
    private Stack <Card> parse;
    private Card temp;
    private int xLoc;
    private int yLoc;
    
    public TablePile(int x, int y)
    {
        pile = new Stack<>();
        parse = new Stack<>();
        
        xLoc = x;
        yLoc = y;
    }
    
    /**
     * given an x and y coordinate
     * function parses through table pile and searches for card clicked on by user
     * the index i of clicked card is return to the user
     * if the clicked element is not in the stack, then -1 is returned
     * @param x
     * @param y
     * @return 
     */
    public int inStack(int x, int y)
    {
        for(int i = pile.size()-1; i>=0; i--)
        {
            temp = pile.get(i);
            if(temp.inArea(x, y))
                return i;
        }
               
        return -1;
    }
    
    /**
     * given an index location on the stack i
     * function removes cards in stack up to index i
     * removed cards are returned to the user to push onto a different table stack
     * @param ind
     * @return 
     */
    public Stack getSubStack(int i)
    {
        
        int lim = pile.size();
        for(; i < lim; i++ )
        {
            parse.push(pile.pop());
            yLoc -= 30;
        }
        
        return parse;
    }
    
    /**
     * returns card at index i without altering the stack
     * @param ind
     * @return 
     */
    public Card showCard(int i)
    {
        return pile.get(i);
    }
    
    /**
     * pops the top card off the stack and returns it
     * @return 
     */
    public Card popCard()
    {
        return pile.pop();
    }
    
    /**
     * Takes a user stack cards and pushes stack on to table pile
     * before card is pushed, a new x and y location is assigned to it to represent its position in the stack
     * @param cards 
     */
    public void addStackToPile(Stack <Card> cards)
    {
        while(!cards.isEmpty())
        {
            temp = cards.pop();
            temp.setLoc(xLoc, yLoc -30);
            pile.push(temp);
            yLoc += 30;
        }
       
    }
    /**
     * Pushes a new card onto the table stack
     * assigns new y location to where last card is added
     * @param newCard 
     */
    public void addCardToPile(Card newCard)
    {
        pile.push(newCard);
        yLoc = newCard.getY();
    }
    
    /**
     * given a card c, function tests if card (and resulting pile) can be added to stack
     * Returns true if:
     * input card is a different color than top card of the table stack
     * and the input card is the next logical value to to the stack
     * @param c
     * @return 
     */
    public boolean canAdd(Card c)
    {
        int color; //top card of stack color
        int val; //top card of stack value
        int s = c.getSuit(); //input suit
        int v = c.getValue(); //input value
        if(c.getFace() == 0) //check if card is facing up or not
            return false;
        if(pile.isEmpty() && v == 11) //king can be added to an empty pile
            return true;
        
        if(pile.isEmpty()) //no other card can be added to an empty pile
            return false;
        
        temp = pile.peek();
        val = temp.getValue(); 
        color = temp.getSuit() % 2; //convert suit to color: 0 black & 1 red
        s %= 2;
        if (v == 12)
            v = -1;
        if(color != s && v == (val-1)) //check conditions
            return true;
        return false;
    }
    
    /**
     * checks the top card of the stack
     * if the top card has its back facing then function flips card
     */
    public void topCardFaceUp()
    {
        if(pile.isEmpty())
            return;
        temp = pile.pop();
        if (temp.getFace() == 0)
            temp.flipCard();
        pile.push(temp);
    }
    
    /**
     * return size of the stack
     * @return 
     */
    public int getSize()
    {
        return pile.size();
    }
            
}
