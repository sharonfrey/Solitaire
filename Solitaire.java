package edu.fau.COT4930;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JFrame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Stack;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Solitaire implements MouseMotionListener
{
    
    //frame elements
    final private int FRAMEWIDTH = 800;
    final private int FRAMEHEIGHT = 700;
    JPanel master;
    
    
    //game element coordinates
    private int xLoc;
    private int yLoc;
    final private int DECKX = 20;
    final private int TOPROWY = 0;
    final private int DISCARDX = 120;
    final private int SUIT1X = 410;
    final private int SUIT2X = 510;
    final private int SUIT3X = 610;
    final private int SUIT4X = 710;
    private int TABLE1X;
    private int TABLE2X;
    private int TABLE3X;
    private int TABLE4X;
    private int TABLE5X;
    private int TABLE6X;
    private int TABLE7X;
    final private int TABLEY = 100;
    
    
    //game elements
    private Deck deck;
    private DiscardPile discard;
    private SuitPile spade;
    private SuitPile heart;
    private SuitPile club;
    private SuitPile dimond;
    private TablePile pile1;
    private TablePile pile2;
    private TablePile pile3;
    private TablePile pile4;
    private TablePile pile5;
    private TablePile pile6;
    private TablePile pile7;
    
    //temporary helper variables
    private Stack <Card> temp;
    private Card card;
    private int ind;
    
    //enable on click listener
    private int enabled = 0;
    
    //graphics variable
    private WriteGraphics d;
    
    //wins and losses counts
    
        //call solitaire game
	public static void main(String[] args) 
	{
            Solitaire md = new Solitaire();
  	}
	public Solitaire()
	{
            
            //frame for solitaire game
            JFrame f = new JFrame("Solitaire");
            
            //panel for game
            JPanel big = new JPanel();
            big.setBackground(Color.WHITE);
            
            //game graphics
            d = new WriteGraphics(FRAMEWIDTH, FRAMEHEIGHT);
            d.setPreferredSize(new Dimension(FRAMEWIDTH, FRAMEHEIGHT));
           
            //panel for start page
            JPanel homePage = new JPanel();
            homePage.setBackground(Color.WHITE);
            homePage.setLayout(new BoxLayout(homePage, BoxLayout.Y_AXIS));
            
            //panel for win page
            JPanel winPage = new JPanel();
            winPage.setBackground(Color.WHITE);
            winPage.setLayout(new BoxLayout(winPage, BoxLayout.Y_AXIS));
            
            //card layout to switch between home page and game page
            master = new JPanel(new CardLayout());
            
            //create the menu bar
            JMenuBar menuBar = new JMenuBar();
            JMenu menu = new JMenu("Options");
            menu.getAccessibleContext().setAccessibleDescription("Solitaire game settings");
            menuBar.add(menu);
            
            //help menu item
            JMenu help = new JMenu("Help");
            help.getAccessibleContext().setAccessibleDescription("Help with gameplay");
            //help description
            JMenuItem description1 = new JMenuItem("In order to win the game, stack all cards into piles of their own Suit.");
            description1.getAccessibleContext().setAccessibleDescription("game details");
            JMenuItem description2 = new JMenuItem("Suit cards will pile in the order: A, 2, 3, 4, 5, 6, 7, 8, 9, 10, J, Q, K");
            description2.getAccessibleContext().setAccessibleDescription("game details");
            JMenuItem description3 = new JMenuItem("Click on Deck (top left) and move table piles for more cards. ");
            description3.getAccessibleContext().setAccessibleDescription("game details");
            JMenuItem description4 = new JMenuItem("Cards stacked on table piles must be every other color and stack in decending order.");
            description4.getAccessibleContext().setAccessibleDescription("game details");
            
            //game reset menu item
            JMenuItem menuItem = new JMenuItem("New Game");
            menuItem.getAccessibleContext().setAccessibleDescription("Reset the game");
        	
            menuItem.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
		{
                    d.addLoss();
                    deck = new Deck(big, "cards.gif");
                    deck.shuffle();
                    newGame();
                    d.changeStatus();
                    d.repaint();
                    returnCardsFromGraphics();
                }
            });
            menu.add(menuItem);
            help.add(description1);
            help.add(description2);
            help.add(description3);
            help.add(description4);
            menu.add(help);
            
            //start page
            
            //title
            float font = (float)FRAMEWIDTH;   
            JLabel title = new JLabel();		
            title.setText("SOLITAIRE");
            title.setFont(title.getFont().deriveFont(font/6));
            title.setAlignmentX(Component.CENTER_ALIGNMENT);
                
            //start button
            JButton start = new JButton("Start");
            start.setAlignmentX(Component.CENTER_ALIGNMENT);
            start.addActionListener(new
   		ActionListener()
   		{
      			public void actionPerformed(ActionEvent event)
      			{
                            
                            CardLayout c = (CardLayout) master.getLayout();
                            c.show(master, "GAMEPAGE");
                            enabled = 1;
                            
     			}
   		});
            
            
            //win page  
            JLabel win = new JLabel();		
            win.setText("YOU WON!!!");
            win.setFont(win.getFont().deriveFont(font/6));
            win.setAlignmentX(Component.CENTER_ALIGNMENT);
                
            //new game button
            JButton newGame = new JButton("New Game");
            newGame.setAlignmentX(Component.CENTER_ALIGNMENT);
            newGame.addActionListener(new
   		ActionListener()
   		{
      			public void actionPerformed(ActionEvent event)
      			{
                            //reset and shuffle the deck
                            deck = new Deck(big, "cards.gif");
                            deck.shuffle();
                            //re inintialize all game piles
                            newGame();
                            //draw game and return new stacks
                            d.changeStatus();
                            d.repaint();
                            returnCardsFromGraphics();
                            
                            //switch cards
                            CardLayout c = (CardLayout) master.getLayout();
                            c.show(master, "GAMEPAGE");
                            //turn mouse tracker on
                            enabled = 1;
                            
     			}
   		});
                
            
            

            
            //game
            //create and shuffle new deck
            deck = new Deck(big, "cards.gif");
            deck.shuffle();
            //initialize all the game piles
            newGame();
            //draw game and return all created piles
            d.repaint();
            returnCardsFromGraphics();
            
            //Track mouse positiuon and click events
            f.addMouseMotionListener(this);
            f.addMouseListener(new MouseAdapter()
            {
                public void mousePressed(MouseEvent e)
		{
                    if(enabled == 0)
                        return;
                    xLoc = e.getX();
                    yLoc = e.getY();
                    
                    //if click is on deck pile
                    if ((xLoc >= DECKX && xLoc <= (DECKX + 71)) &&
                            (yLoc >= TOPROWY && yLoc <= (TOPROWY + 151)))
                    {
                        
                        traverseDeck();
                        d.repaint();
                    }
                    //if mouse click is on discard pile
                    if ((xLoc >= DISCARDX && xLoc <= (DISCARDX + 71)) &&
                            (yLoc >= TOPROWY && yLoc <= (TOPROWY + 151)))
                    {
                        giveDiscard();
                        
                        d.repaint();
                    }
                    //if mouse click is on first table pile
                    if ((xLoc >= TABLE1X && xLoc <= (TABLE1X + 71)) && yLoc >= TABLEY)
                    {
                        pile1 = addToTable(pile1, 1);
                        d.repaint();
                    }
                    //if mouse click is on second table pile
                    if ((xLoc >= TABLE2X && xLoc <= (TABLE2X + 71)) && yLoc >= TABLEY)
                    {
                        pile2 = addToTable(pile2, 2);
                        d.repaint();
                    }
                    //if mouse click is on third table pile
                    if ((xLoc >= TABLE3X && xLoc <= (TABLE3X + 71)) && yLoc >= TABLEY)
                    {
                        pile3 = addToTable(pile3, 3);
                        d.repaint();
                    }
                    //if mouse click is on fourth table pile
                    if ((xLoc >= TABLE4X && xLoc <= (TABLE4X + 71)) && yLoc >= TABLEY)
                    {
                        pile4 = addToTable(pile4, 4);
                        d.repaint();
                    }
                    //if mouse click is on fifth table pile
                    if ((xLoc >= TABLE5X && xLoc <= (TABLE5X + 71)) && yLoc >= TABLEY)
                    {
                        pile5 = addToTable(pile5, 5);
                        d.repaint();
                    }
                    //if mouse click is on sixth table pile
                    if ((xLoc >= TABLE6X && xLoc <= (TABLE6X + 71)) && yLoc >= TABLEY)
                    {
                        pile6 = addToTable(pile6, 6);
                        d.repaint();
                    }
                    //if mouse click is on seventh table pile
                    if ((xLoc >= TABLE7X && xLoc <= (TABLE7X + 71)) && yLoc >= TABLEY)
                    {
                        
                        pile7 = addToTable(pile7, 7);
                        d.repaint();
                        
                    }
                    
		}
            });
            
            //add elements to win page
            winPage.add(win);
            winPage.add(newGame);
            
            //add elements to home page
            homePage.add(title);
            homePage.add(start);
            
            //add drawings to game page
            big.add(d);
            
            //add pages to card layout
            master.add(homePage, "HOMEPAGE");
            master.add(big,"GAMEPAGE");
            master.add(winPage, "WINPAGE");
           
            //add elements to frame
            f.add(master);
            f.setJMenuBar(menuBar);
            f.setSize(FRAMEWIDTH, FRAMEHEIGHT);
            f.setResizable(false);
            f.setVisible(true);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
        
        
        /**
         * Function creates and initializes all the piles that are required for the solitaire game
         * these piles are:
         * Discard pile (pile that holds all cards shown from deck)
         * 4 suit piles (holding spade, heart, club, diamond cards from A-K respectively)
         * 7 table piles(these are the main piles a user will play on)
         */
        public void newGame()
        {
                       
            discard = new DiscardPile(DISCARDX, TOPROWY);
            int tempx = 100;
            for(int i = 1; i <= 7; i++ )
            {
                if (i == 1)
                {
                    pile1 = new TablePile(tempx, TABLEY);
                    TABLE1X = tempx;
                }
                if (i == 2)
                {
                    pile2 = new TablePile(tempx, TABLEY);
                    TABLE2X = tempx;
                }
                if (i == 3)
                {
                    pile3 = new TablePile(tempx, TABLEY);
                    TABLE3X = tempx;
                }
                if (i == 4)
                {
                    pile4 = new TablePile(tempx, TABLEY);
                    TABLE4X = tempx;
                }
                if (i == 5)
                {
                    pile5 = new TablePile(tempx, TABLEY);
                    TABLE5X = tempx;
                }
                if (i == 6)
                {
                    pile6 = new TablePile(tempx, TABLEY);
                    TABLE6X = tempx;
                }
                if (i == 7)
                {
                    pile7 = new TablePile(tempx, TABLEY);
                    TABLE7X = tempx;
                }
                tempx += 100;
                    
            }
            spade = new SuitPile(SUIT1X, TOPROWY, 0);
            heart = new SuitPile(SUIT2X, TOPROWY, 1);
            club = new SuitPile(SUIT3X, TOPROWY, 2);
            dimond = new SuitPile(SUIT4X, TOPROWY, 3);
            
            d.setDeck(deck);
            d.setDiscard(discard);
            d.setTable(1, pile1);
            d.setTable(2, pile2);
            d.setTable(3, pile3);
            d.setTable(4, pile4);
            d.setTable(5, pile5);
            d.setTable(6, pile6);
            d.setTable(7, pile7);
            d.setSuit(0, spade);
            d.setSuit(1, heart);
            d.setSuit(2, club);
            d.setSuit(3, dimond);
        }
        /**
         * When the game is drawn each table pile and deck is created
         * this function returns the initialized table piles back to main game for user to interact with
         */
        public void returnCardsFromGraphics()
        {
            deck = d.returnDeck();
            pile1 = d.returnTable(1);
            pile2 = d.returnTable(2);
            pile3 = d.returnTable(3);
            pile4 = d.returnTable(4);
            pile5 = d.returnTable(5);
            pile6 = d.returnTable(6);
            pile7 = d.returnTable(7);
        }
        /**
         * This function takes in a TablePile table and pile number i and returns an altered TablePile
         * pile number i represents a table 1 - 7 that user clicked on
         * if i = 0, then the player clicked on the discard pile
         * ind if found to be the index of the card in the pile that is being added to the table
         * if card or pile can be added to a different pile it is added and the altered pile is returned
         * if it cannot be added then the original pile is returned 
         * @param table
         * @param i
         * @return 
         */
        public TablePile addToTable(TablePile table, int i)
        {
            if (i == 0)
                ind = 0;
            else
                ind = table.inStack(xLoc, yLoc);
            if(ind != -1)
            {
                //retrieve card taht will be added to another pile
                card = table.showCard(ind);
                //retrieve stack being added
                temp = table.getSubStack(ind);
                //if only one card is to be added, check if it can be added to suit pile
                if(temp.size() == 1 && spade.canAdd(card))
                    spade = addToSuitPile(spade, card, 0);
                else if(temp.size() == 1 && heart.canAdd(card))
                    heart = addToSuitPile(heart, card, 1);
                else if(temp.size() == 1 && club.canAdd(card))
                    club = addToSuitPile(club, card, 2);
                else if (temp.size() == 1 && dimond.canAdd(card))
                    dimond = addToSuitPile(dimond, card, 3);
                //check to add to pile 1
                else if( i != 1 && pile1.canAdd(card))
                {
                    pile1.addStackToPile(temp);
                    d.setTable(1, pile1);
                }
                //check to add to pile 2
                else if( i != 2 && pile2.canAdd(card))
                {
                    pile2.addStackToPile(temp);
                    d.setTable(2, pile2);
                }
                //check to add to pile 3
                else if(i != 3 && pile3.canAdd(card))
                {
                    pile3.addStackToPile(temp);
                    d.setTable(3, pile3);
                }
                //check to add to pile 4
                else if(i!= 4 && pile4.canAdd(card))
                {
                    pile4.addStackToPile(temp);
                    d.setTable(4, pile4);
                }
                //check to add to pile 5
                else if(i != 5 && pile5.canAdd(card))
                {
                    pile5.addStackToPile(temp);
                    d.setTable(5, pile5);
                }
                //check to add to pile 6
                else if(i != 6 && pile6.canAdd(card))
                {
                    pile6.addStackToPile(temp);
                    d.setTable(6, pile6);
                }
                //check to add to pile 7
                else if(i != 7 && pile7.canAdd(card)) 
                {
                    pile7.addStackToPile(temp);
                    d.setTable(7, pile7);
                }
                //it cannot be added to pile so return TablePile to original state
                else
                    table.addStackToPile(temp);
                //make sure top card in table pile is facing up
                table.topCardFaceUp();
                //clear out temporary variables 
                temp.clear();
                card = null;
            }
            //check if game is won
            //if game won flip to winning page
            if((spade.completed() && heart.completed()) && 
                    (club.completed() && dimond.completed()))
            {
                d.addWin();
                enabled = 0;
                CardLayout c = (CardLayout) master.getLayout();
                c.show(master, "WINPAGE");
            }
            return table;
        }
        /**
         * Add card c to top of suitPile suit and reset suitPile for drawing
         * i indicates the suit pile card is being added to
         * @param suit
         * @param c
         * @param i
         * @return 
         */
        public SuitPile addToSuitPile(SuitPile suit, Card c, int i)
        {
            suit.addCardToPile(c);
            d.setSuit(i, suit);
            
            return suit;    
        }
        /**
         * When deck is empty function traverseDeck is called
         * this function returns all cards from the discard pile back to deck
         */
        public void traverseDeck()
        {
            if(deck.getSize() == 0)
            {
                int size = discard.getSize();
                for(int i = 0; i < size; i++)
                {
                    deck.addCardToPile(discard.popCard());
                }
            }
            discard.addCardToPile(deck.popCard());
            d.setDiscard(discard);
            d.setDeck(deck);
        }
        /**
         * fiveDiscard is used to send card from top of the discard pile to table piles
         * if card can not be added to table pile it is returned back to discard pile
         */
        public void giveDiscard()
        {
            TablePile temp = new TablePile(0,0);
            temp.addCardToPile(discard.popCard()); 
            temp = addToTable(temp, 0);
            if(temp.getSize() != 0)
            {
                discard.addCardToPile(temp.popCard());
            }
            d.setDiscard(discard);
        }
	// The MouseMotionListener is implemented here
	public void mouseMoved(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {}
}


class WriteGraphics extends JPanel
{
    //panen dimentions
    private int width;
    private int height; 
    
    //drawing status for new game or current game
    //if status = 0 a new game is drawn
    //if staus = 1 the current game is updated
    private int status;
    
    //image for discard pile
    private Image backImg;
    
    //x and y coordinates for drawing 
    private int xLoc;
    private int yLoc;
    
    //piles used for drawing
    private Card card;
    private Deck deck;
    private DiscardPile discard;
    private SuitPile spade;
    private SuitPile heart;
    private SuitPile club;
    private SuitPile dimond;
    private TablePile pile1;
    private TablePile pile2;
    private TablePile pile3;
    private TablePile pile4;
    private TablePile pile5;
    private TablePile pile6;
    private TablePile pile7;
    
    //win and loss tracker
    private int wins = 0;
    private int losses = 0;

    /**
     * initializes graphics width and height 
     * sets status to 0
     * @param w
     * @param h 
     */
	public WriteGraphics(int w, int h)
	{ 
            
            width = w;
            height = h;
            status = 0;
	}
        /**
         * changes status to either 0 or 1 depending on the current state
         * status = 0 means a new game is drawn
         * status = 1 means a current game is being updated
         */
        public void changeStatus()
        {
            status ^= 1;
        }
        /**
         * initializes deck to input d
         * @param d 
         */
        public void setDeck(Deck d)
        {
            deck = d;
        }
        /**
         * returns deck to game playing
         * @return 
         */
        public Deck returnDeck()
        {
            return deck;
        }
        /**
         * sets discard pile to draw to DiscardPile d
         * @param d 
         */
        public void setDiscard(DiscardPile d)
        {
            discard = d;
        }
        /**
         * returns discard pile for game play
         * @return 
         */
        public DiscardPile returnDiscard()
        {
            return discard;
        }
        /**
         * sets suit pile to s for drawing
         * indicator i is used to identify suit pile being added to
         * 0 = spade
         * 1 = heart
         * 2 = club
         * 3 = diamond
         * @param i
         * @param s 
         */
        public void setSuit(int i, SuitPile s)
        {
            if(i == 0)
                spade = s;
            if(i == 1)
                heart = s;
            if(i == 2)
                club = s;
            if(i == 3)
                dimond = s;
        }
        /**
         * using i to indicate SuitPile, return SuitPile to game page
         * @param i
         * @return 
         */
        public SuitPile returnSuit(int i)
        {
            if(i == 0)
                return spade;
            if(i == 1)
                return heart;
            if(i == 2)
                return club;
            if(i == 3)
                return dimond;
            return null;
        }
        /**
         * using indicator i as the table number sets TablePile t for drawing
         * @param i
         * @param t 
         */
        public void setTable(int i, TablePile t)
        {
            if(i == 1)
                pile1 = t;
            if(i == 2)
                pile2 = t;
            if(i == 3)
                pile3 = t;
            if(i == 4)
                pile4 = t;
            if(i == 5)
                pile5 = t;
            if(i == 6)
                pile6 = t;
            if(i == 7)
                pile7 = t;
        }
        /**
         * using indicator i, return TablePile for game play
         * @param i
         * @return 
         */
        public TablePile returnTable(int i)
        {
            if(i == 1)
                return pile1;
            if(i == 2)
                return pile2;
            if(i == 3)
                return pile3;
            if(i == 4)
                return pile4;
            if(i == 5)
                return pile5;
            if(i == 6)
                return pile6;
            if(i == 7)
                return pile7;
            return null;
        }
        /**
         * increase the win count
         */
        public void addWin()
        {
            wins++;
        }
        /**
         * increase the loss count
         */
        public void addLoss()
        {
            losses++;
        }
        /**
         * paint function
         * if status = 0 a new game is drawn and piles are created for game play
         * if status = 1 a game is updated and drawn to continue game play
         * @param g 
         */
	@Override
  	public void paint(Graphics g)  
   	{
            //set background to white
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);
            g.setColor(Color.gray);
            
            if (status == 0)
            {
                xLoc = 100;
                yLoc = 100;
                for(int i = 1; i <= 7; i++)
                {
                    //first loop for all seven piles
                    for (int j = i; j > 0; j--)
                    {
                        //second loop for cards in each pile
                        //get top card of deck
                        card = deck.popCard();
                        if(j == 1)
                        {
                            //if card will be top of the table stack show face
                            card.flipCard();
                        }
                        //draw card on board
                        g.drawImage(card.getImage(), xLoc, yLoc, this);
                        //initialze card to TablePile
                        card.setLoc(xLoc, yLoc); 
                        if(i == 1)
                            pile1.addCardToPile(card);
                        if(i == 2)
                            pile2.addCardToPile(card);
                        if(i == 3)
                            pile3.addCardToPile(card);
                        if(i == 4)
                            pile4.addCardToPile(card);
                        if(i == 5)
                            pile5.addCardToPile(card);
                        if(i == 6)
                            pile6.addCardToPile(card);
                        if(i == 7)
                            pile7.addCardToPile(card);

                        yLoc+=30;
                    }
                //set location for drawing
                xLoc += 100;
                yLoc = 100;
                
                }
                //initialize card back for discard pile and draw pile
                backImg = card.getBack();
                g.drawImage(backImg, 20, 0, this);
                //add win and loss count to board
                g.drawString("Wins: " + wins + " Losses: " + losses, 670, 600);
            }
            
            if(status == 1)
            {
                //draw discard pile
                if(discard.getSize() != 0)
                {
                    card = discard.showCard();
                    g.drawImage(card.getImage(), discard.getX(), discard.getY(), this);
                }
                
                
                //draw suit piles
                if(spade.getSize() != 0)
                {
                    card = spade.showCard();
                    g.drawImage(card.getImage(), spade.getX(), spade.getY(), this);
                }
                if(heart.getSize() != 0)
                {
                    card = heart.showCard();
                    g.drawImage(card.getImage(), heart.getX(), heart.getY(), this);
                }
                if(club.getSize() != 0)
                {
                    card = club.showCard();
                    g.drawImage(card.getImage(), club.getX(), club.getY(), this);
                }
                if(dimond.getSize() != 0)
                {
                    card = dimond.showCard();
                    g.drawImage(card.getImage(), dimond.getX(), dimond.getY(), this);
                }
                
                
                //drawing table piles
                for(int i = 0; i < pile1.getSize(); i++)
                {
                    card = pile1.showCard(i);
                    g.drawImage(card.getImage(), card.getX() - 10, card.getY() - 50, this);
                }
                for(int i = 0; i < pile2.getSize(); i++)
                {
                    card = pile2.showCard(i);
                    g.drawImage(card.getImage(), card.getX() - 10, card.getY() - 55, this);
                }
                for(int i = 0; i < pile3.getSize(); i++)
                {
                    card = pile3.showCard(i);
                    g.drawImage(card.getImage(), card.getX() - 10, card.getY() - 50, this);
                }
                for(int i = 0; i < pile4.getSize(); i++)
                {
                    card = pile4.showCard(i);
                    g.drawImage(card.getImage(), card.getX() - 10, card.getY() - 50, this);
                }
                for(int i = 0; i < pile5.getSize(); i++)
                {
                    card = pile5.showCard(i);
                    g.drawImage(card.getImage(), card.getX() - 10, card.getY() - 50, this);
                }
                for(int i = 0; i < pile6.getSize(); i++)
                {
                    card = pile6.showCard(i);
                    g.drawImage(card.getImage(), card.getX() - 10, card.getY() - 50, this);
                }
                for(int i = 0; i < pile7.getSize(); i++)
                {
                    card = pile7.showCard(i);
                    g.drawImage(card.getImage(), card.getX() - 10, card.getY() - 50, this);
                }
                
                
                //draw deck
                if(deck.getSize() != 0)
                    g.drawImage(backImg, 20, 0, this);
                
                g.drawString("Wins: " + wins + " Losses: " + losses, 670, 600);
            }
        //change status if new game is created
        if (status == 0)
            changeStatus();
    	}
        /**
         * used to update drawing
         * @param g 
         */
	@Override
	public void update(Graphics g)  
	{
		paint(g);
	}
}
