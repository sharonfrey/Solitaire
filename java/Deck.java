package edu.fau.COT4930;

import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.util.Stack;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import javax.imageio.ImageIO;



public class Deck {
    
    private Stack <Card> deck;
    private Image javaImage;
    private Image imageBack;
    
    private final Component compCopy;
    private BufferedImage im;
    private ImageFilter crop;
    
    private Card temp;
    private final int width = 71;
    private final int height = 96;
    private int x, y;
    
    /**
     * comp is a Component that is used to create images
     * fileCards is the file path of the image of the cards
     * creates a new deck and initializes it
     * @param comp
     * @param fileCards 
     */
    public Deck(Component comp, String fileCards)
    {
       //copy comp value for resetDeck function to use
       compCopy = comp;
       //create a new deck of cards as a stack
       deck = new Stack<>();
       //open image file and initialize bufferedImage
       try {
           File f = new File(fileCards);
           im = ImageIO.read(f);
           //create the deck calling the resetDeck helper function
           resetDeck();
       }catch (IOException e) {System.out.println(e);}
       
       
    }
    
    /**
     * Helper function to restore all cards to deck and randomly shuffle deck
     * int values x and y are position from original image that crop starts
     * int values height and width are dimensions of card image being cropped
     */
    public void resetDeck()
    {
        deck.clear();
        crop = new CropImageFilter(355, 384, width, height);
        imageBack = compCopy.createImage(new FilteredImageSource(im.getSource(), crop));
       //start y coordinate at 0
        y = 0;
        //2 for loops for each coloumn and row of given image
        for (int i = 0; i < 4; i++)
        {
            //start x coordinate at 0 for each new row
            x = 0;
            for (int j = 0; j < 13; j++)
            {
                //crop image
                crop = new CropImageFilter(x, y, width, height);
                javaImage = compCopy.createImage(new FilteredImageSource(im.getSource(), crop));
                //create new card with cropped image and card values
                temp = new Card(i,j,javaImage, imageBack);
                //push new card onto stack
                deck.push(temp);
                //move x coordinate to the next card
                x += 71;
            }
            //move y coordinate to the next card
            y += 96;
        }
    }
    
    /**
     * Method to randomly shuffle cards
     */
    void shuffle ()
    {
        Collections.shuffle(deck);
    }
    /**
     * Method which returns the next card in the deck
     * @return 
     */
    Card popCard()
    { 
        //use stack function pop to return the next card in deck
        return deck.pop();
    }
    public void addCardToPile(Card c)
    {
        if(c.getFace() == 1)
            c.flipCard();
        deck.push(c);
    }
    /**
     * Method to return the number of cards left in the deck
     * @return 
     */
    int getSize()
    {
        //use stack function size to return the size of the deck
        return deck.size();
    }
}
