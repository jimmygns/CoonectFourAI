import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;

/*
 * PSA Extra
 * class ConnectFourPlayer
 * jinye xu    jix029@ucsd.edu    A99076165
 * Date: 6/3/2014
 * 
 * ConnectFourPlayer creates an AI for ConnectFOur Game
 * it has three memeber variables 
 * char checker: the check of the AI
 * String tieBreakType
 * int ply: the intellegence level of the AI
 */

public class ConnectFourPlayer{
  
  private char checker;
  private String tieBreakType;
  private int ply;
  
  //Constructor
  public ConnectFourPlayer( char checkerIn, String tieBreakIn, int plyIn )
  {
    this.checker = checkerIn;
    this.tieBreakType = tieBreakIn;
    this.ply = plyIn; 
  }
  
  public String toString()
  {
    String info = new String();
    info += "Player for "+ checker+ "\n with tiebreak: "+tieBreakType+"\n and ply == "+ply;
    
    return info;
  }
  
  public char oppCh() 
  {
    if(checker == 'X')
      return 'O';
    else
      return 'X';
  }
  //Check the score of the Board
  public double scoreBoard(ConnectFourBoard b)
  {
    if(b.winsFor(checker))
      return 100.00;
    else if(b.winsFor(this.oppCh())){
      return 0.00;
    }
    else
      return 50.00;
  }
  //how to make a move when the scores are tied
  public int tiebreakMove(double[] scores)
  {
    Random num = new Random();
    double scoreMax = 0.0;
    ArrayList<Integer> columns = new ArrayList<Integer>();
    for(int i =0; i<scores.length; i++)
    {
      if(scores[i]>scoreMax)
        scoreMax = scores[i];
    }
    for(int j =0; j<scores.length; j++)
    {
      if(scores[j]==scoreMax)
        columns.add(j);
    }
    
    if(tieBreakType.equals("LEFT"))
      return columns.get(0).intValue();
    else if(tieBreakType.equals("RIGHT"))
      return columns.get(columns.size()-1).intValue();
    else
      return columns.get(num.nextInt(columns.size())).intValue();  
  }
  //find the maximum number in an array
  public double findMax(double[] scores)
  {
    double scoreMax = 0.0;
    for(int i =0; i<scores.length; i++)
    {
      if(scores[i]>scoreMax)
        scoreMax = scores[i];
    }
    return scoreMax;
  }
  
  //give the score of the move
  public double[] scoresFor(ConnectFourBoard b)
  {
    
    double[] array = new double[b.getNumColumns()];
    for(int i =0; i<array.length; i++)
    {
      if(!b.allowsMove(i))
        array[i]=-1.0;
      else if(b.winsFor('X')||b.winsFor('O'))
        array[i]=this.scoreBoard(b);
      else if(ply==0)
        array[i]=this.scoreBoard(b);
      else
      {        
        b.addMove(i,checker);        
        ConnectFourPlayer opp = new ConnectFourPlayer(this.oppCh(),tieBreakType,ply-1);  
        if(this.findMax(opp.scoresFor(b))==100.0)
        {
          array[i]=0;
          
        }
        else if(this.findMax(opp.scoresFor(b))==0.0)
          array[i]=100;
        else if(this.findMax(opp.scoresFor(b))==50.0)
          array[i]=50;
        b.delMove(i);
        
      }       
    } 
   
    return array;
  }
  
  public int nextMove(ConnectFourBoard b)
  {
    return this.tiebreakMove(this.scoresFor(b));
    
    
  }
  
  //for testing
  public static void main(String args[]){
    
    ConnectFourBoard b = new ConnectFourBoard( 7, 6 );
    //b.setBoard( "01020305" );
    b.setBoard( "1211244445" );
    // System.out.println((new ConnectFourPlayer('X', "LEFT", 0)).scoresFor(b));
    //System.out.println(b);
    //ConnectFourPlayer p = new ConnectFourPlayer('X', "LEFT", 1);
    // System.out.println(p.scoreBoard( b ));
    //double[] scores = {0, 0, 50, 0, 50, 50, 0};
    // ConnectFourPlayer p2 = new ConnectFourPlayer ('X', "RIGHT", 1);
    System.out.println((new ConnectFourPlayer('X', "RANDOM", 2)).nextMove(b));
    //System.out.println(p2.tiebreakMove(scores));

    
    //System.out.println(Arrays.toString((new ConnectFourPlayer('O', "LEFT", 3)).scoresFor(b)));
    //System.out.println(((new ConnectFourPlayer('X', "LEFT", 4 )).nextMove(b)));
    System.out.println(b);
  }
  
}