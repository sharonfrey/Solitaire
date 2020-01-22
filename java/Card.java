package edu.fau.COT4930;

import java.awt.Image;

public class Card {
    
    private int face;
    private int x;
    private int y;
    private final int s;
    private final int v;
    private final Image im;
    private final Image back;
    
    /**
     * Create a new card with values s, v, and im
     * s is the suit number (0-3 for Spade, Heart, Club, Diamond respectively)
     * v is the value of the card (0-12, in order 2,3,4,5,6,7,8,9,J,Q,K,A)
     * im is the image given to the card
     * @param s
     * @param v
     * @param im 
     * @param back
     */
    public Card(int s, int v, Image im, Image back)
    {
        this.s = s;
        this.v = v;
        this.im = im;
        this.face = 0; //0 is the back of the card
        this.back = back;
    }
    /**
     * returns image
     * @return 
     */
    public Image getImage()
    {
        if (face == 0)
            return back;
        return im;
    }
    /**
     * returns value of the card
     * @return 
     */
    public int getValue()
    {
        return v;
    }
    /**
     * returns the suit value of the card
     * @return 
     */
    public int getSuit()
    {
        return s;
    }
    
    public int getFace()
    {
        return face;
    }
    
    public Image getBack()
    {
        return back;
    }
    
    public void flipCard()
    {
        this.face = face ^ 1;
    }
    
    public boolean inArea(int x, int y)
    {
        
        if((x >= this.x && x <= (this.x + 71)) &&
                (y >= this.y && y<= (this.y + 91)))
            return true;
        return false;
    }
    
    public void setLoc(int x, int y)
    {
        this.x = x + 10;
        this.y = y + 55;
    }
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    
}
