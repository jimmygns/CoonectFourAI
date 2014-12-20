import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * /*
 * PSA Extra
 * class ConnectFour
 * jinye xu    jix029@ucsd.edu    A99076165
 * Date: 6/3/2014
 * 
 * the Connect Game is hold here 
 */
public class ConnectFour extends JFrame {
  
  /** The underlying board that will hold the state of the game */
  private ConnectFourBoard theBoard;

  /** Whose turn it is.  We use 'X' and 'O', but we will translate 'X' into 
    * the color blue, and 'O' into the color red for the display. */
  private char turn;
  
  /** The status message at the top of the window */
  private JLabel status;
  
  private ConnectFourPlayer opponent = new ConnectFourPlayer('O',"RANDOM",5);
  
  /** The default constructor.  Constructs a board with 6 rows and 7 columns */
  public ConnectFour()
  {
    this( 7, 6 );
  }

  /** Create a new ConnectFour object */
  public ConnectFour( int width, int height )
  {
    // X starts
    turn = 'X';
    
    // Make a new underlying board.
    theBoard = new ConnectFourBoard(width, height);
    
    // The reset button.  It doesn't do anything yet.
    JButton jbtReset = new JButton( "New Game" );
    jbtReset.addActionListener( new ActionListener() {
      public void actionPerformed( ActionEvent e ) {
        theBoard.clear();
        status.setText( "Welcome to Connect 4.  Blue's turn" );
        turn = 'X';
        repaint();
      }
    }
    );
    status = new JLabel( "Welcome to Connect 4!  Blue's turn" );
    JPanel displayBoard = createBoard( width, height );
    
    setLayout( new BorderLayout() );
    add( status, BorderLayout.NORTH );
    add(displayBoard, BorderLayout.CENTER );
    add( jbtReset, BorderLayout.SOUTH );
    pack();
    setVisible( true );
    
  }
      
  // Helper method to create the visual board.
  private JPanel createBoard( int width, int height )
  {
    JPanel displayBoard = new JPanel();
    displayBoard.setLayout( new GridLayout( height, width ) );
    
    for ( int r = 0; r < height; r++ ) 
    {
      for ( int c = 0; c < width; c++ ) 
      {        
        BoardCell cell = new BoardCell( r, c );
        displayBoard.add( cell );
      }
    }
    return displayBoard;
  }
  
  // helper method that is called when the user clicks on a column
  private void makeMove( int col )
  {
    if ( theBoard.winsFor( 'X' ) || theBoard.winsFor( 'O' ) || theBoard.isFull() )
    {
      return;  // Don't make a move if the game is over.
    }
    if ( theBoard.allowsMove( col ) )
    {
      theBoard.addMove( col, turn );
      turn = switchTurn( turn );
    }
    String color = "";
    if ( turn == 'X' )
      color = "Blue";
    else
      color = "Red";
    
    if ( theBoard.winsFor( 'X' ) )
      status.setText( "Game over. Blue wins!" );
    else if ( theBoard.winsFor( 'O' ) )
      status.setText( "Game over.  Red wins!" );
    else if ( theBoard.isFull() )
      status.setText( "Game over.  Tie game!" );
    else 
      status.setText( color + "'s turn.  Click a column to play." );
    
    repaint();
  }
  
  // helper method that will change whose turn it is.
  private char switchTurn( char turn )
  {
    if ( turn == 'X' )
      return 'O';
    else
      return 'X';
  }


  /** The visual display of the cells in the connect 4 board */
  class BoardCell extends JPanel
  {
    private int row;
    private int column;
    /** Construct a new BoardCell at row r and column c 
      * @param r The row that this BoardCell exists at
      * @param c The column that this BoardCell exists at 
      * */
    BoardCell( int r, int c )
    {
      row = r;
      column = c;
      addMouseListener( new PlayListener() );
    }
  
    /** Returns the preferred size of the board */
    public Dimension getPreferredSize()
    {
      return new Dimension( 50, 50 );
    }
  
    /** Paints the board cell using the underlying connect four board */
    protected void paintComponent( Graphics g )
    { 
      super.paintComponent( g );
      char contents = theBoard.getContents(row, column);
      g.setColor( Color.black );
      g.fillRect( 0, 0, getWidth(), getHeight() );
      if ( contents == ' ' )
      {
        g.setColor( Color.white );
      }
      else if ( contents == 'X' )
      {
        g.setColor( Color.blue );
      }
      else {
        g.setColor( Color.red );
      }
      g.fillOval( (int)(0.1*getWidth()), (int)(0.1*getHeight()),
                 (int)(0.9*getWidth()), (int)(0.9*getHeight()) );
    }
    
    /** The listener for mouse clicks on the board that makes the moves */
    class PlayListener implements MouseListener
    {
      /** make a move in the corresponding column */
      public void mouseClicked( MouseEvent e ) 
      {
        // We just need to tell the board to play a checker in the appropriate
        // column.
        makeMove( column );
        makeMove( opponent.nextMove(theBoard) );
      }
      
  
      public void mousePressed( MouseEvent e ) {}
      public void mouseReleased( MouseEvent e ) {}
      public void mouseEntered( MouseEvent e ) {}
      public void mouseExited( MouseEvent e ) {}
    }
  }
  
   public static void main( String[] args )
  {
    if ( args.length > 1 ) {
      int w = Integer.parseInt( args[0] );
      int h = Integer.parseInt( args[1] );
      new ConnectFour(w, h);
    }
    else
      new ConnectFour();
      
  }

}
