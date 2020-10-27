/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.kingdomino.model;
import ca.mcgill.ecse223.kingdomino.controller.*;
import java.util.*;
import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.*;
import ca.mcgill.ecse223.kingdomino.model.Domino.*;
import ca.mcgill.ecse223.kingdomino.view.Game_4_Players;
import java.io.IOException;

// line 3 "../../../../../Gameplay.ump"
public class Gameplay
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Gameplay Attributes
  private Game game;
  private int selectingOrder;
  private int placingOrder;
  private int total_No_Draft;

  //Gameplay State Machines
  public enum Gamestatus { Initializing, ProceedingToNextTurn, Evaluation }
  public enum GamestatusInitializing { Null, CreatingFirstDrafts, SelectingFirstDomino }
  public enum GamestatusProceedingToNextTurn { Null, CreatingNextDraft, proceedingToPlaceDomino }
  public enum GamestatusProceedingToNextTurnProceedingToPlaceDomino { Null, placingDomino, choosingNextDomino }
  public enum GamestatusEvaluation { Null, CalculatingPlayersScore, ResolvingTieBreak, CalculatingRanking }
  private Gamestatus gamestatus;
  private GamestatusInitializing gamestatusInitializing;
  private GamestatusProceedingToNextTurn gamestatusProceedingToNextTurn;
  private GamestatusProceedingToNextTurnProceedingToPlaceDomino gamestatusProceedingToNextTurnProceedingToPlaceDomino;
  private GamestatusEvaluation gamestatusEvaluation;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Gameplay()
  {
    game = KingdominoApplication.getKingdomino().getCurrentGame();
    selectingOrder = 0;
    placingOrder = 0;
    total_No_Draft = 0;
    setGamestatusInitializing(GamestatusInitializing.Null);
    setGamestatusProceedingToNextTurn(GamestatusProceedingToNextTurn.Null);
    setGamestatusProceedingToNextTurnProceedingToPlaceDomino(GamestatusProceedingToNextTurnProceedingToPlaceDomino.Null);
    setGamestatusEvaluation(GamestatusEvaluation.Null);
    setGamestatus(Gamestatus.Initializing);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    game = aGame;
    wasSet = true;
    return wasSet;
  }

  public boolean setSelectingOrder(int aSelectingOrder)
  {
    boolean wasSet = false;
    selectingOrder = aSelectingOrder;
    wasSet = true;
    return wasSet;
  }

  public boolean setPlacingOrder(int aPlacingOrder)
  {
    boolean wasSet = false;
    placingOrder = aPlacingOrder;
    wasSet = true;
    return wasSet;
  }

  public boolean setTotal_No_Draft(int aTotal_No_Draft)
  {
    boolean wasSet = false;
    total_No_Draft = aTotal_No_Draft;
    wasSet = true;
    return wasSet;
  }

  public Game getGame()
  {
    return game;
  }

  public int getSelectingOrder()
  {
    return selectingOrder;
  }

  public int getPlacingOrder()
  {
    return placingOrder;
  }

  public int getTotal_No_Draft()
  {
    return total_No_Draft;
  }

  public String getGamestatusFullName()
  {
    String answer = gamestatus.toString();
    if (gamestatusInitializing != GamestatusInitializing.Null) { answer += "." + gamestatusInitializing.toString(); }
    if (gamestatusProceedingToNextTurn != GamestatusProceedingToNextTurn.Null) { answer += "." + gamestatusProceedingToNextTurn.toString(); }
    if (gamestatusProceedingToNextTurnProceedingToPlaceDomino != GamestatusProceedingToNextTurnProceedingToPlaceDomino.Null) { answer += "." + gamestatusProceedingToNextTurnProceedingToPlaceDomino.toString(); }
    if (gamestatusEvaluation != GamestatusEvaluation.Null) { answer += "." + gamestatusEvaluation.toString(); }
    return answer;
  }

  public Gamestatus getGamestatus()
  {
    return gamestatus;
  }

  public GamestatusInitializing getGamestatusInitializing()
  {
    return gamestatusInitializing;
  }

  public GamestatusProceedingToNextTurn getGamestatusProceedingToNextTurn()
  {
    return gamestatusProceedingToNextTurn;
  }

  public GamestatusProceedingToNextTurnProceedingToPlaceDomino getGamestatusProceedingToNextTurnProceedingToPlaceDomino()
  {
    return gamestatusProceedingToNextTurnProceedingToPlaceDomino;
  }

  public GamestatusEvaluation getGamestatusEvaluation()
  {
    return gamestatusEvaluation;
  }

  public boolean draftsReady()
  {
    boolean wasEventProcessed = false;
    
    GamestatusInitializing aGamestatusInitializing = gamestatusInitializing;
    switch (aGamestatusInitializing)
    {
      case CreatingFirstDrafts:
        exitGamestatusInitializing();
        // line 55 "../../../../../Gameplay.ump"
        try{
                    revealFirstDraft();
                 	 }catch (InvalidInputException e) {
  					e.printStackTrace();
  					throw new java.lang.IllegalArgumentException(
  					"Error occured while trying to revealing draft: " + e.getMessage()); }				
  					
                	generateInitialPlayerOrder();
        setGamestatusInitializing(GamestatusInitializing.SelectingFirstDomino);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean selectingFirstDraftDomino(int domino)
  {
    boolean wasEventProcessed = false;
    
    GamestatusInitializing aGamestatusInitializing = gamestatusInitializing;
    switch (aGamestatusInitializing)
    {
      case SelectingFirstDomino:
        if (DominoSelectable(domino)&&getSelectingOrder()<3)
        {
          exitGamestatusInitializing();
        // line 76 "../../../../../Gameplay.ump"
          selectingFirst(domino);
	   			    	setPlayerOrderNextDraft(domino);
	                	selectingOrder = (selectingOrder+1)%4;
          setGamestatusInitializing(GamestatusInitializing.SelectingFirstDomino);
          wasEventProcessed = true;
          break;
        }
        if (DominoSelectable(domino)&&getSelectingOrder()>=3)
        {
          exitGamestatus();
        // line 82 "../../../../../Gameplay.ump"
          selectingFirst(domino);	                     	
                		setPlayerOrderNextDraft(domino);
                		selectingOrder = (selectingOrder+1)%4; 
                		upDatePlayerOrder();
          setGamestatusProceedingToNextTurn(GamestatusProceedingToNextTurn.CreatingNextDraft);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean hasMoreDraft()
  {
    boolean wasEventProcessed = false;
    
    GamestatusProceedingToNextTurn aGamestatusProceedingToNextTurn = gamestatusProceedingToNextTurn;
    switch (aGamestatusProceedingToNextTurn)
    {
      case CreatingNextDraft:
        if (getTotal_No_Draft()<12)
        {
          exitGamestatusProceedingToNextTurn();
        // line 96 "../../../../../Gameplay.ump"
          System.out.println("hasMoreDraft");
              	 
              	 		
              	 		try{
                	    	createNextDraft();
                		}catch (InvalidInputException e) {
  							e.printStackTrace();
  							throw new java.lang.IllegalArgumentException(
  							"Error occured while trying to create draft: " + e.getMessage()); }  
               		 
               		 	try{
                  	 		orderNextDraft();
                        } catch (InvalidInputException e) {
    				    	e.printStackTrace();
    						throw new java.lang.IllegalArgumentException(
    						"Error occured while trying to sort draft: " + e.getMessage()); }
    					try{
                  	    	revealNextDraft();
                 	    }catch (InvalidInputException e) {
  					    	e.printStackTrace();
  					    	throw new java.lang.IllegalArgumentException(
  					    	"Error occured while trying to revealing draft: " + e.getMessage());}
          setGamestatusProceedingToNextTurn(GamestatusProceedingToNextTurn.proceedingToPlaceDomino);
          wasEventProcessed = true;
          break;
        }
        if (getTotal_No_Draft()>=12)
        {
          exitGamestatusProceedingToNextTurn();
          setGamestatusProceedingToNextTurn(GamestatusProceedingToNextTurn.proceedingToPlaceDomino);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean moveDominoToKingdom(int x,int y,String dir)
  {
    boolean wasEventProcessed = false;
    
    GamestatusProceedingToNextTurnProceedingToPlaceDomino aGamestatusProceedingToNextTurnProceedingToPlaceDomino = gamestatusProceedingToNextTurnProceedingToPlaceDomino;
    switch (aGamestatusProceedingToNextTurnProceedingToPlaceDomino)
    {
      case placingDomino:
        exitGamestatusProceedingToNextTurnProceedingToPlaceDomino();
        // line 133 "../../../../../Gameplay.ump"
        createDominoInKingdom(x,y, dir);
        setGamestatusProceedingToNextTurnProceedingToPlaceDomino(GamestatusProceedingToNextTurnProceedingToPlaceDomino.placingDomino);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean moveCurrentDomino(DirectionKind move)
  {
    boolean wasEventProcessed = false;
    
    GamestatusProceedingToNextTurnProceedingToPlaceDomino aGamestatusProceedingToNextTurnProceedingToPlaceDomino = gamestatusProceedingToNextTurnProceedingToPlaceDomino;
    switch (aGamestatusProceedingToNextTurnProceedingToPlaceDomino)
    {
      case placingDomino:
        if (moveable())
        {
          exitGamestatusProceedingToNextTurnProceedingToPlaceDomino();
        // line 135 "../../../../../Gameplay.ump"
          moveDominoInKingdom(move);
          setGamestatusProceedingToNextTurnProceedingToPlaceDomino(GamestatusProceedingToNextTurnProceedingToPlaceDomino.placingDomino);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean rotateCurrentDomino(String rotation)
  {
    boolean wasEventProcessed = false;
    
    GamestatusProceedingToNextTurnProceedingToPlaceDomino aGamestatusProceedingToNextTurnProceedingToPlaceDomino = gamestatusProceedingToNextTurnProceedingToPlaceDomino;
    switch (aGamestatusProceedingToNextTurnProceedingToPlaceDomino)
    {
      case placingDomino:
        exitGamestatusProceedingToNextTurnProceedingToPlaceDomino();
        // line 137 "../../../../../Gameplay.ump"
        rotate(rotation);
        setGamestatusProceedingToNextTurnProceedingToPlaceDomino(GamestatusProceedingToNextTurnProceedingToPlaceDomino.placingDomino);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean discard()
  {
    boolean wasEventProcessed = false;
    
    GamestatusProceedingToNextTurnProceedingToPlaceDomino aGamestatusProceedingToNextTurnProceedingToPlaceDomino = gamestatusProceedingToNextTurnProceedingToPlaceDomino;
    switch (aGamestatusProceedingToNextTurnProceedingToPlaceDomino)
    {
      case placingDomino:
        if (!(shouldDiscardDomino()))
        {
          exitGamestatusProceedingToNextTurnProceedingToPlaceDomino();
        // line 139 "../../../../../Gameplay.ump"
          System.out.println(placingOrder); WarningInDiscarding();
          setGamestatusProceedingToNextTurnProceedingToPlaceDomino(GamestatusProceedingToNextTurnProceedingToPlaceDomino.placingDomino);
          wasEventProcessed = true;
          break;
        }
        if (shouldDiscardDomino()&&(getTotal_No_Draft()<12))
        {
          exitGamestatusProceedingToNextTurnProceedingToPlaceDomino();
        // line 141 "../../../../../Gameplay.ump"
          
          setGamestatusProceedingToNextTurnProceedingToPlaceDomino(GamestatusProceedingToNextTurnProceedingToPlaceDomino.choosingNextDomino);
          wasEventProcessed = true;
          break;
        }
        if (shouldDiscardDomino()&&(getTotal_No_Draft()>=12)&&(getPlacingOrder()<3))
        {
          exitGamestatusProceedingToNextTurnProceedingToPlaceDomino();
        // line 144 "../../../../../Gameplay.ump"
          
          setGamestatusProceedingToNextTurnProceedingToPlaceDomino(GamestatusProceedingToNextTurnProceedingToPlaceDomino.placingDomino);
          wasEventProcessed = true;
          break;
        }
        if (shouldDiscardDomino()&&(getTotal_No_Draft()>=12)&&getPlacingOrder()>=3)
        {
          exitGamestatus();
        // line 149 "../../../../../Gameplay.ump"
          System.out.println(placingOrder);
          setGamestatus(Gamestatus.Evaluation);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean confirmPlacement()
  {
    boolean wasEventProcessed = false;
    
    GamestatusProceedingToNextTurnProceedingToPlaceDomino aGamestatusProceedingToNextTurnProceedingToPlaceDomino = gamestatusProceedingToNextTurnProceedingToPlaceDomino;
    switch (aGamestatusProceedingToNextTurnProceedingToPlaceDomino)
    {
      case placingDomino:
        if (!(preplaceDomino()))
        {
          exitGamestatusProceedingToNextTurnProceedingToPlaceDomino();
        // line 154 "../../../../../Gameplay.ump"
          choose_Other_Placement();
          setGamestatusProceedingToNextTurnProceedingToPlaceDomino(GamestatusProceedingToNextTurnProceedingToPlaceDomino.placingDomino);
          wasEventProcessed = true;
          break;
        }
        if (preplaceDomino()&&(getTotal_No_Draft()<12))
        {
          exitGamestatusProceedingToNextTurnProceedingToPlaceDomino();
        // line 156 "../../../../../Gameplay.ump"
          verifyPlacement();placingOrder=(placingOrder+1)%4;
          setGamestatusProceedingToNextTurnProceedingToPlaceDomino(GamestatusProceedingToNextTurnProceedingToPlaceDomino.choosingNextDomino);
          wasEventProcessed = true;
          break;
        }
        if (preplaceDomino()&&(getTotal_No_Draft()>=12)&&(getPlacingOrder()<3))
        {
          exitGamestatusProceedingToNextTurnProceedingToPlaceDomino();
        // line 158 "../../../../../Gameplay.ump"
          verifyPlacement();placingOrder=(placingOrder+1)%4;
          setGamestatusProceedingToNextTurnProceedingToPlaceDomino(GamestatusProceedingToNextTurnProceedingToPlaceDomino.placingDomino);
          wasEventProcessed = true;
          break;
        }
        if (preplaceDomino()&&(getTotal_No_Draft()>=12)&&(getPlacingOrder()>=3))
        {
          exitGamestatus();
          setGamestatus(Gamestatus.Evaluation);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean selectingDomino(int domino)
  {
    boolean wasEventProcessed = false;
    
    GamestatusProceedingToNextTurnProceedingToPlaceDomino aGamestatusProceedingToNextTurnProceedingToPlaceDomino = gamestatusProceedingToNextTurnProceedingToPlaceDomino;
    switch (aGamestatusProceedingToNextTurnProceedingToPlaceDomino)
    {
      case choosingNextDomino:
        if (getSelectingOrder()<3)
        {
          exitGamestatusProceedingToNextTurnProceedingToPlaceDomino();
        // line 168 "../../../../../Gameplay.ump"
          selectingNext(domino);
                    setPlayerOrderNextDraft(domino);
                    selectingOrder = (selectingOrder+1)%4;
          setGamestatusProceedingToNextTurnProceedingToPlaceDomino(GamestatusProceedingToNextTurnProceedingToPlaceDomino.placingDomino);
          wasEventProcessed = true;
          break;
        }
        if (getSelectingOrder()>=3)
        {
          exitGamestatus();
        // line 174 "../../../../../Gameplay.ump"
          selectingNext(domino);                                                     
                    setPlayerOrderNextDraft(domino);
                    selectingOrder = (selectingOrder+1)%4;
                    upDatePlayerOrder();
                    updateTwoDraftPostion();
          setGamestatus(Gamestatus.ProceedingToNextTurn);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean scoresAreCalculated()
  {
    boolean wasEventProcessed = false;
    
    GamestatusEvaluation aGamestatusEvaluation = gamestatusEvaluation;
    switch (aGamestatusEvaluation)
    {
      case CalculatingPlayersScore:
        if (!(isThereATie()))
        {
          exitGamestatusEvaluation();
          setGamestatusEvaluation(GamestatusEvaluation.CalculatingRanking);
          wasEventProcessed = true;
          break;
        }
        if (isThereATie())
        {
          exitGamestatusEvaluation();
          setGamestatusEvaluation(GamestatusEvaluation.ResolvingTieBreak);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void exitGamestatus()
  {
    switch(gamestatus)
    {
      case Initializing:
        exitGamestatusInitializing();
        break;
      case ProceedingToNextTurn:
        exitGamestatusProceedingToNextTurn();
        break;
      case Evaluation:
        exitGamestatusEvaluation();
        break;
    }
  }

  private void setGamestatus(Gamestatus aGamestatus)
  {
    gamestatus = aGamestatus;

    // entry actions and do activities
    switch(gamestatus)
    {
      case Initializing:
        if (gamestatusInitializing == GamestatusInitializing.Null) { setGamestatusInitializing(GamestatusInitializing.CreatingFirstDrafts); }
        break;
      case ProceedingToNextTurn:
        if (gamestatusProceedingToNextTurn == GamestatusProceedingToNextTurn.Null) { setGamestatusProceedingToNextTurn(GamestatusProceedingToNextTurn.CreatingNextDraft); }
        break;
      case Evaluation:
        if (gamestatusEvaluation == GamestatusEvaluation.Null) { setGamestatusEvaluation(GamestatusEvaluation.CalculatingPlayersScore); }
        break;
    }
  }

  private void exitGamestatusInitializing()
  {
    switch(gamestatusInitializing)
    {
      case CreatingFirstDrafts:
        setGamestatusInitializing(GamestatusInitializing.Null);
        break;
      case SelectingFirstDomino:
        setGamestatusInitializing(GamestatusInitializing.Null);
        break;
    }
  }

  private void setGamestatusInitializing(GamestatusInitializing aGamestatusInitializing)
  {
    gamestatusInitializing = aGamestatusInitializing;
    if (gamestatus != Gamestatus.Initializing && aGamestatusInitializing != GamestatusInitializing.Null) { setGamestatus(Gamestatus.Initializing); }

    // entry actions and do activities
    switch(gamestatusInitializing)
    {
      case CreatingFirstDrafts:
        // line 45 "../../../../../Gameplay.ump"
        shuffleDominoPile(); try{
                    createAndSortFirstDraft();
                }catch (InvalidInputException e) {
  					e.printStackTrace();
  				throw new java.lang.IllegalArgumentException(
  					"Error occured while trying to create draft: " + e.getMessage());
                }
        break;
    }
  }

  private void exitGamestatusProceedingToNextTurn()
  {
    switch(gamestatusProceedingToNextTurn)
    {
      case CreatingNextDraft:
        setGamestatusProceedingToNextTurn(GamestatusProceedingToNextTurn.Null);
        break;
      case proceedingToPlaceDomino:
        exitGamestatusProceedingToNextTurnProceedingToPlaceDomino();
        setGamestatusProceedingToNextTurn(GamestatusProceedingToNextTurn.Null);
        break;
    }
  }

  private void setGamestatusProceedingToNextTurn(GamestatusProceedingToNextTurn aGamestatusProceedingToNextTurn)
  {
    gamestatusProceedingToNextTurn = aGamestatusProceedingToNextTurn;
    if (gamestatus != Gamestatus.ProceedingToNextTurn && aGamestatusProceedingToNextTurn != GamestatusProceedingToNextTurn.Null) { setGamestatus(Gamestatus.ProceedingToNextTurn); }

    // entry actions and do activities
    switch(gamestatusProceedingToNextTurn)
    {
      case proceedingToPlaceDomino:
        if (gamestatusProceedingToNextTurnProceedingToPlaceDomino == GamestatusProceedingToNextTurnProceedingToPlaceDomino.Null) { setGamestatusProceedingToNextTurnProceedingToPlaceDomino(GamestatusProceedingToNextTurnProceedingToPlaceDomino.placingDomino); }
        break;
    }
  }

  private void exitGamestatusProceedingToNextTurnProceedingToPlaceDomino()
  {
    switch(gamestatusProceedingToNextTurnProceedingToPlaceDomino)
    {
      case placingDomino:
        setGamestatusProceedingToNextTurnProceedingToPlaceDomino(GamestatusProceedingToNextTurnProceedingToPlaceDomino.Null);
        break;
      case choosingNextDomino:
        setGamestatusProceedingToNextTurnProceedingToPlaceDomino(GamestatusProceedingToNextTurnProceedingToPlaceDomino.Null);
        break;
    }
  }

  private void setGamestatusProceedingToNextTurnProceedingToPlaceDomino(GamestatusProceedingToNextTurnProceedingToPlaceDomino aGamestatusProceedingToNextTurnProceedingToPlaceDomino)
  {
    gamestatusProceedingToNextTurnProceedingToPlaceDomino = aGamestatusProceedingToNextTurnProceedingToPlaceDomino;
    if (gamestatusProceedingToNextTurn != GamestatusProceedingToNextTurn.proceedingToPlaceDomino && aGamestatusProceedingToNextTurnProceedingToPlaceDomino != GamestatusProceedingToNextTurnProceedingToPlaceDomino.Null) { setGamestatusProceedingToNextTurn(GamestatusProceedingToNextTurn.proceedingToPlaceDomino); }
  }

  private void exitGamestatusEvaluation()
  {
    switch(gamestatusEvaluation)
    {
      case CalculatingPlayersScore:
        setGamestatusEvaluation(GamestatusEvaluation.Null);
        break;
      case ResolvingTieBreak:
        setGamestatusEvaluation(GamestatusEvaluation.Null);
        break;
      case CalculatingRanking:
        setGamestatusEvaluation(GamestatusEvaluation.Null);
        break;
    }
  }

  private void setGamestatusEvaluation(GamestatusEvaluation aGamestatusEvaluation)
  {
    gamestatusEvaluation = aGamestatusEvaluation;
    if (gamestatus != Gamestatus.Evaluation && aGamestatusEvaluation != GamestatusEvaluation.Null) { setGamestatus(Gamestatus.Evaluation); }

    // entry actions and do activities
    switch(gamestatusEvaluation)
    {
      case CalculatingPlayersScore:
        // line 196 "../../../../../Gameplay.ump"
        calculateAllPlayersScores();
        break;
      case ResolvingTieBreak:
        // line 204 "../../../../../Gameplay.ump"
        resolveTieBreak();
        break;
    }
  }

  public void delete()
  {}


  /**
   * ///////////////////////////////////////////////////////////////////////////////////
   * ///////////////////////////////////////////////////////////////////////////////////
   * ///////////////////////////////////////////////////////////////////////////////////
   * //////////////////Actions and Guards below/////////////////////////////////////////
   * ///////////////////////////////////////////////////////////////////////////////////
   * ///////////////////////////////////////////////////////////////////////////////////
   * ///////////////////////////////////////////////////////////////////////////////////
   * ///////////////////////////////////////////////////////////////////////////////////
   * ///////////////////////////////////////////////////////////////////////////////////
   * 
   * @author chenkua
   * 
   * Setter for test setup
   */
  // line 241 "../../../../../Gameplay.ump"
   public void setGamestatus(String status){
    switch (status) {
       	case "CreatingFirstDrafts":
       	    gamestatus = Gamestatus.Initializing;
       	    gamestatusInitializing = GamestatusInitializing.CreatingFirstDrafts;
       	    break;
       	case"ProceedingToNextTurn":
       		gamestatus = Gamestatus.ProceedingToNextTurn;
       		gamestatusProceedingToNextTurn= GamestatusProceedingToNextTurn.CreatingNextDraft;
       		break;
       		
       	case"Evaluation": 
       		gamestatus = Gamestatus.Evaluation;
       		gamestatusEvaluation = GamestatusEvaluation.CalculatingPlayersScore;
       	    break;
       	default:
       	    throw new RuntimeException("Invalid gamestatus string was provided: " + status);
       	}
  }

  // line 261 "../../../../../Gameplay.ump"
   public void setProceedingToNextTurn(String status){
    switch (status) {
  		 case"CreatingNextDraft":
  		 	 gamestatus = Gamestatus.ProceedingToNextTurn;
  		 	 gamestatusProceedingToNextTurn = GamestatusProceedingToNextTurn.CreatingNextDraft;		 
  	 		break;
  		 case"proceedingToPlaceDomino":
  			 gamestatus = Gamestatus.ProceedingToNextTurn;
  			 gamestatusProceedingToNextTurn = GamestatusProceedingToNextTurn.proceedingToPlaceDomino;
  			 gamestatusProceedingToNextTurnProceedingToPlaceDomino = GamestatusProceedingToNextTurnProceedingToPlaceDomino.placingDomino;	
  		 	break;		 
  		 
  		 default:
       	    throw new RuntimeException("Invalid gamestatus string was provided: " + status);
  		 }
  }

  // line 278 "../../../../../Gameplay.ump"
   public void setproceedingToPlaceDomino(String status){
    switch (status) {
  		 case"placingDomino":
  		 	 gamestatus = Gamestatus.ProceedingToNextTurn;
  		 	 gamestatusProceedingToNextTurn = GamestatusProceedingToNextTurn.proceedingToPlaceDomino;
  		 	 gamestatusProceedingToNextTurnProceedingToPlaceDomino	=  GamestatusProceedingToNextTurnProceedingToPlaceDomino.placingDomino;
  	 		break;
  		 case"choosingNextDomino":
  			 gamestatus = Gamestatus.ProceedingToNextTurn;
  			 gamestatusProceedingToNextTurn = GamestatusProceedingToNextTurn.proceedingToPlaceDomino;	
  		 	 gamestatusProceedingToNextTurnProceedingToPlaceDomino = GamestatusProceedingToNextTurnProceedingToPlaceDomino.choosingNextDomino;
  		 	break;		 
  		 
  		 default:
       	    throw new RuntimeException("Invalid gamestatus string was provided: " + status);
  		 }
  }


  /**
   * This will clear the draft column 2 and update column 1.
   * @author chenkua
   * line 302 "../../../../../Gameplay.ump"
   */
  // line 303 "../../../../../Gameplay.ump"
   public void updateTwoDraftPostion(){
    Game_4_Players.refreshSelectioBeforeColumn1();
	Game_4_Players.RefreshDrafts_1();
	Game_4_Players.clearColumn_2();
  }


  /**
   * @author Angelina
   */
  // line 314 "../../../../../Gameplay.ump"
   public void selectingFirst(int domino){
    try {
     chooseNextDomino(game.getCurrentOrder(selectingOrder), domino);
	} catch (InvalidInputException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    for (DominoSelection s : game.getCurrentDraft().getSelections()) {
			System.out.print(s.getDomino().getId()+" ");
		}
    	System.out.println(" ");
    	System.out.println("Domino is chosen");
  }

  // line 330 "../../../../../Gameplay.ump"
   public void selectingNext(int domino){
    try {
		chooseNextDomino(game.getCurrentOrder(selectingOrder), domino);
	} catch (InvalidInputException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    for (DominoSelection s : game.getNextDraft().getSelections()) {
			System.out.print(s.getDomino().getId()+" ");
		}
    	System.out.println(" ");
    	System.out.println("Domino is chosen");
  }


  /**
   * You may need to add more guards here
   * 
   * @author chenkua
   */
  // line 351 "../../../../../Gameplay.ump"
   public boolean isThereATie(){
    boolean has =KingDominoController.HasTieBreak(game);
   		System.out.println("hasTie"+ has);
	  return has;
  }


  /**
   * Actions
   * 
   * @author: Amani
   */
  // line 366 "../../../../../Gameplay.ump"
   public void shuffleDominoPile(){
    KingDominoController.shuffleDominos(this.game);
    	game.setTopDominoInPile(game.getAllDomino(0));
    	for(Domino d: game.getAllDominos()) {
    		System.out.print(d.getId()+"  ");
    	}
    	System.out.println(" ");
    	System.out.println("Domino is shuffled");
  }


  /**
   * 
   * 
   * @author: chenkua
   * line 199 "../../../../../Gameplay.ump"
   */
  // line 381 "../../../../../Gameplay.ump"
   public void generateInitialPlayerOrder(){
    System.out.println("Player order originally");
  	for(Player p:game.getPlayers()){
  		System.out.print(p.getColor().toString()+" ");
  	}
  	System.out.println(" ");
    List<Player> tmp = game.getPlayers();
    List<String> playercolor = new ArrayList<>();
    
       for(Player a : tmp) {
    	   playercolor.add(a.getColor().toString().toLowerCase());
    	   
       }
       Collections.shuffle(playercolor);
       
      List<Player> players = new ArrayList<>();  
      for(String a:playercolor) {
    	  players.add(getPlayerFromColor(a));
      } 
      
       
       game.setCurrentOrder(players);
       game.setNextOrder(players);
       
       for(Player p:game.getCurrentOrder()){
      		System.out.print(p.getColor().toString()+" ");
      	}
		System.out.println("   generateInitialPlayerOrder");
  }


  /**
   * 
   * @author Ellina
   * 
   */
  // line 415 "../../../../../Gameplay.ump"
   public void createNextDraft() throws InvalidInputException{
    total_No_Draft = total_No_Draft+1;
 	   	if(game.getNextDraft()!=null) game.setNextDraft(null);   	
		KingDominoController.createNextDraft(game);
		System.out.println("CreateNextDraft" + total_No_Draft);
  }


  /**
   * 
   * @author Ellina
   * 
   */
  // line 427 "../../../../../Gameplay.ump"
   public void orderNextDraft() throws InvalidInputException{
    Game game = KingdominoApplication.getKingdomino().getCurrentGame();
       KingDominoController.sortNextDraft(game); 
       System.out.println("OrderNextDraft");
  }


  /**
   * After reveal the next draft, the next draft become the current draft
   * @author Ellina
   * 
   */
  // line 439 "../../../../../Gameplay.ump"
   public void revealNextDraft() throws InvalidInputException{
    Game game = KingdominoApplication.getKingdomino().getCurrentGame();
        KingDominoController.revealNextDraft(game);
        System.out.println("revealNextDraft");
  }


  /**
   * 
   * @author Ellina
   * 
   */
  // line 450 "../../../../../Gameplay.ump"
   public void createAndSortFirstDraft() throws InvalidInputException{
    total_No_Draft = total_No_Draft+1;
    	Game game = KingdominoApplication.getKingdomino().getCurrentGame();
    	KingDominoController.createAndSortFirstDraft(game);
    	System.out.println("createAndSortFirstDraft");
  }


  /**
   * 
   * @author Ellina
   * 
   */
  // line 461 "../../../../../Gameplay.ump"
   public void revealFirstDraft() throws InvalidInputException{
    Game game = KingdominoApplication.getKingdomino().getCurrentGame();
    	KingDominoController.revealFirstDraft(game);
    	 for(Domino d: game.getCurrentDraft().getIdSortedDominos()) {
        	System.out.print(d.getId()+" ");
        }
    	System.out.println("revealFirstDraft");
    	for(Domino d : game.getCurrentDraft().getIdSortedDominos()) {
    		System.out.print(d.getId()+ " ");
    	}
    	System.out.println("  ");
  }


  /**
   * You may need to add more actions here
   * NOTE: (Amani) new actions:
   * 
   * @author: Amani 
   * @param territory
   * @param direction
   */
  // line 492 "../../../../../Gameplay.ump"
   public void placeDominoInKingdom(KingdomTerritory territory, DirectionKind direction) throws InvalidInputException{
    Game game = KingdominoApplication.getKingdomino().getCurrentGame();

	   Player currentPlayer = game.getNextPlayer();
	   Domino domino = currentPlayer.getDominoSelection().getDomino();
	   Kingdom playersKingdom = currentPlayer.getKingdom();
	   int x = territory.getX();
	   int y = territory.getY();
	   DominoInKingdom domInKingdom = new DominoInKingdom(x, y, playersKingdom, domino);
	   domInKingdom.setDirection(direction);
	   KingDominoController.placeDominoInKingdom(domInKingdom);
	   domino.setStatus(DominoStatus.PlacedInKingdom);
	   List<Domino> dominosInDraft = game.getCurrentDraft().getIdSortedDominos();
	   for(Domino currentDomino : dominosInDraft) {
		   if(currentDomino.equals(domino)) {
			   game.getCurrentDraft().removeIdSortedDomino(domino);
			   game.setTopDominoInPile(dominosInDraft.get(0));
		   }
	   }
  }


  /**
   * 
   * @author: Amani
   */
  // line 519 "../../../../../Gameplay.ump"
   public void goToNextPlayer(){
    Game game = KingdominoApplication.getKingdomino().getCurrentGame();

	   Domino topDomino = game.getTopDominoInPile();
	   game.setNextPlayer(topDomino.getDominoSelection().getPlayer());
        // TODO: implement this
  }

  // line 527 "../../../../../Gameplay.ump"
   public void calculateAllPlayersScores(){
    KingDominoController.calculatePlayerScore1(game.getPlayer(0));
    	 KingDominoController.calculatePlayerScore1(game.getPlayer(1));
   		 KingDominoController.calculatePlayerScore1(game.getPlayer(2));
   		 KingDominoController.calculatePlayerScore1(game.getPlayer(3));
  }

  // line 535 "../../../../../Gameplay.ump"
   public void resolveTieBreak(){
    KingDominoController.rankPlayers(game);
  }

  // line 541 "../../../../../Gameplay.ump"
   public void displayRanking(){
    // TODO: implement this
  }

  // line 549 "../../../../../Gameplay.ump"
   public void choose_Other_Placement(){
    Game_4_Players.warningInPlacing();
  }


  /**
   * 
   * @author chenkua
   * @param domino
   */
  // line 557 "../../../../../Gameplay.ump"
   public void setPlayerOrderNextDraft(int domino){
    List<Player> tmp = game.getNextOrder();  	
     
    List<Player> newOrder = new ArrayList<Player>();
    List<String> playerColor = new ArrayList<String>();
    
    for(int i=0;i<game.getCurrentDraft().getIdSortedDominos().size();i++) {
    	
    	if(game.getCurrentDraft().getIdSortedDominos().get(i).getId()==domino) {
    		newOrder.add(game.getCurrentOrder(selectingOrder));
    	}else {
    		newOrder.add(tmp.get(i));
    	}
    }
    
    game.setNextOrder(newOrder);
    for(Player p: game.getNextOrder()){
    	System.out.print(p.getColor().toString()+" ");
    }
    System.out.println(" ");
  }


  /**
   * 
   * 
   * @author chenkua
   * 
   */
  // line 587 "../../../../../Gameplay.ump"
   public boolean preplaceDomino(){
    Player aPlayer  = game.getCurrentOrder(placingOrder);
    
    int size = aPlayer.getKingdom().getTerritories().size();
    if(size==1) {	
    	Game_4_Players.warningInPreplacing();
    	return false;
    }   
    DominoInKingdom tmp = (DominoInKingdom) aPlayer.getKingdom().getTerritories().get(size-1);
    Domino domino = tmp.getDomino();
    if (KingDominoController.verifyGridSize(tmp.getKingdom())
			&& (KingDominoController.verifyCastleAdjacency(tmp) || (KingDominoController.verifyNeighborAdjacency(tmp).size() != 0))
			&& !KingDominoController.verifyNoOverlapping(tmp)) {
		domino.setStatus(DominoStatus.CorrectlyPreplaced);
		return true;
		
	} else {
		domino.setStatus(DominoStatus.ErroneouslyPreplaced);
		Game_4_Players.warningInPlacing();
		return false;
	}
  }


  /**
   * Find the DominoInKingdom which is added most recently, which also means this dominoInKingdom are being processed.
   * @author chenkua
   * 
   * line 612 "../../../../../Gameplay.ump"
   */
  // line 617 "../../../../../Gameplay.ump"
   public DominoInKingdom findDominoInProcessing(){
    Player aPlayer  = game.getCurrentOrder(placingOrder);
	    DominoInKingdom tmp = null;
		int size =  aPlayer.getKingdom().getTerritories().size();
		
		tmp = (DominoInKingdom) aPlayer.getKingdom().getTerritory(size-1);
		  
		return tmp;
  }


  /**
   * After current players have all placed their dominos into kingdom and all have selected dominos in next draft,
   * the nextOrder becomes currentOrder
   * @author chenkua
   */
  // line 631 "../../../../../Gameplay.ump"
   public void upDatePlayerOrder(){
    Game_4_Players.setCurrentOrder();
  }


  /**
   * Check whether one domino should be discarded
   * @author chenkua 
   * This method is mostly copied from the similar one in controller but changed returned resutl as Boolean
   */
  // line 639 "../../../../../Gameplay.ump"
   public boolean shouldDiscardDomino(){
    DominoInKingdom tmp = findDominoInProcessing();
    int initialX = tmp.getX();
	int initialY = tmp.getY();
	DirectionKind initialDirection = tmp.getDirection();
	  		
		for (int x=-4; x<=4; x++) { // needs to be from the start of the kingdom, not from -4, -4
			for (int y=-4; y<=4; y++) {
				for (DirectionKind direction : DirectionKind.values()) {
					tmp.setX(x);
					tmp.setY(y);
					tmp.setDirection(direction);
					
					KingDominoController.verifyDominoPreplacement(tmp);
					if (tmp.getDomino().getStatus() == DominoStatus.CorrectlyPreplaced) {
						KingDominoController.verifyDominoPreplacement(tmp); 
						tmp.setX(initialX);
						tmp.setY(initialY);
						tmp.setDirection(initialDirection);						
						return false;
					}
				}
			}
		}
		
		tmp.getDomino().setStatus(DominoStatus.Discarded);
		tmp.delete();
		Game_4_Players.SuccessDiscarding();
		return true;
  }


  /**
   * @author chenkua
   * line 431 "../../../../../Gameplay.ump"
   */
  // line 676 "../../../../../Gameplay.ump"
   public boolean DominoSelectable(int i){
    Player current = game.getCurrentOrder(selectingOrder);
	   
	   for(Player p : game.getCurrentOrder()) {
		   if(!p.equals(current) && p.getDominoSelection()!=null && p.getDominoSelection().getDomino().getId()==i) {
			  System.out.println("DominoSelectable: false");
			  Game_4_Players.warningInSelecting();
			   return false;
		   }
		   
	   }
	   System.out.println("DominoSelectable: true"); 
	   return true;
  }

  // line 691 "../../../../../Gameplay.ump"
   private static  Player getPlayerFromColor(String string){
    Player temp = null;
		
		if(string.equals(KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(0).getColor().toString().toLowerCase())) {
			
			temp = KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(0);	
			
		}else if(string.equals(KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(1).getColor().toString().toLowerCase())) {
			
			temp = KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(1);
			
		}else if(string.equals(KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(2).getColor().toString().toLowerCase())) {
			
			temp = KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(2);
			
		}else if(string.equals(KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(3).getColor().toString().toLowerCase())) {
			
			temp = KingdominoApplication.getKingdomino().getCurrentGame().getPlayer(3);
			
		}
		return temp;
  }

  // line 714 "../../../../../Gameplay.ump"
   public Draft chooseNextDomino(Player player, int chosenDominoID) throws InvalidInputException{
    Draft nextDraft =game.getCurrentDraft();
		for (Domino domino : nextDraft.getIdSortedDominos()) {
			if (domino.getId() == chosenDominoID) {
				Domino chosenDomino = domino;

				for (DominoSelection dominoSelection : nextDraft.getSelections()) {
					if (dominoSelection.getDomino() == chosenDomino) {
						throw new InvalidInputException("chosen domino is already occupied.");
					}
				}

				nextDraft.addSelection(player, chosenDomino);
				return nextDraft;
			}
		}
		throw new InvalidInputException("chosen domino was not in next draft.");
  }


  /**
   * This method will create DominoInKingdom to the current player who are placing the domino.
   * @author chenkua
   * @param x
   * @param y
   * @param dir
   * line 673 "../../../../../Gameplay.ump"
   */
  // line 739 "../../../../../Gameplay.ump"
   public void createDominoInKingdom(int x, int y, String dir){
    Player curplayer = game.getCurrentOrder(placingOrder);
		Kingdom k = curplayer.getKingdom();
		Domino d = null;
		//check for dupilcate adding domino to kingdom
		if(curplayer.getDominoSelection()!=null) {
			 d = curplayer.getDominoSelection().getDomino();
		}else {
			System.out.println("Domino is already in your kingdom");
			return;
		}
		DominoInKingdom dk = new DominoInKingdom(x, y, k, d);
		DirectionKind direction = util.directionKindFromString(dir); 
		
		dk.setDirection(direction);
		KingDominoController.verifyDominoPreplacement(dk);
		
		//this delete() is very important! @Kua
		curplayer.getDominoSelection().delete();
		System.out.println(d.getId()+" is added the kingdom of "+ curplayer.getColor().toString());
  }


  /**
   * @author chenkua
   * 
   */
  // line 765 "../../../../../Gameplay.ump"
   public void moveDominoInKingdom(DirectionKind move){
    int size = game.getCurrentOrder(placingOrder).getKingdom().getTerritories().size();
	  	
	   if(size>1) {
	   		
		   DominoInKingdom DIK = (DominoInKingdom) game.getCurrentOrder(placingOrder).getKingdom().getTerritory(size-1);
		  
		   try {
			moveDominoInKingdom(DIK, move);
			System.out.println(DIK.getX()+" " +DIK.getY());
		   } catch (InvalidInputException e) {
			Game_4_Players.warningInMoving();
			e.printStackTrace();
		}
	   }else {
		   System.out.println("No DominoInKingdom");
	   }
  }


  /**
   * moveDominoInKingdom has some small problem here, but should not directly change what is in controller otherwies
   * Iteration2 may get affected.
   * 
   * @author Elias; chenkua
   * @param dik
   * @param move
   * @throws InvalidInputException
   */
  // line 794 "../../../../../Gameplay.ump"
   public void moveDominoInKingdom(DominoInKingdom dik, DirectionKind move) throws InvalidInputException{
    int x = dik.getX();
		int y = dik.getY();
		int initX = x;
		int initY =y;
		DominoStatus s = dik.getDomino().getStatus();

		if (move == DirectionKind.Up) {
			y += 1;
		} else if (move == DirectionKind.Down) {
			y -= 1;
		} else if (move == DirectionKind.Left) {
			x -= 1;
		} else if (move == DirectionKind.Right) {
			x += 1;
		}
		
		/*When setX and setY, it will return fasle when x and y is out of bonudary and will not really set the new X and new Y
		 *
		 */
		if(dik.setX(x) &&dik.setY(y)) {	
			if(!KingDominoController.verifyKingdomTerritoryInBoard(dik)) {
				dik.setX(initX);
				dik.setY(initY);
				dik.getDomino().setStatus(s);
				throw new InvalidInputException("movement would place one of the domino tiles outside the board.");
			}
		}else {
			throw new InvalidInputException("movement would place one of the domino tiles outside the board.");
		}
  }


  /**
   * This method will set domino status if verified successfully
   * @author chenkua
   */
  // line 829 "../../../../../Gameplay.ump"
   public void verifyPlacement(){
    int size = game.getCurrentOrder(placingOrder).getKingdom().getTerritories().size();
	   DominoInKingdom tmp = (DominoInKingdom) game.getCurrentOrder(placingOrder).getKingdom().getTerritory(size-1);   
	   try {
		KingDominoController.placeDominoInKingdom(tmp);
	} catch (InvalidInputException e) {
		System.out.print(e);
	}
  }


  /**
   * 
   * @author chenkua Elias
   * @param dir
   */
  // line 843 "../../../../../Gameplay.ump"
   public void rotate(String dir){
    int size = game.getCurrentOrder(placingOrder).getKingdom().getTerritories().size();
	   DominoInKingdom tmp = (DominoInKingdom) game.getCurrentOrder(placingOrder).getKingdom().getTerritory(size-1);    
	  	try {
			KingDominoController.rotateDominoInKingdom(tmp, dir);
		} catch (InvalidInputException e) {
			shouldNotRotate();
			e.printStackTrace();
		}
  }


  /**
   * 
   * @author chenkua
   */
  // line 858 "../../../../../Gameplay.ump"
   public void shouldNotRotate(){
    Game_4_Players.shouldNorRotate();
  }

  // line 862 "../../../../../Gameplay.ump"
   public void WarningInDiscarding(){
    Game_4_Players.warningInDiscarding();
  }


  /**
   * 
   * @author chenkua
   */
  // line 869 "../../../../../Gameplay.ump"
   public boolean moveable(){
    Player aPlayre = game.getCurrentOrder(placingOrder);
	   DominoInKingdom DIK= findDominoInProcessing();
	 
	   if(DIK.getDomino().getStatus()==DominoStatus.PlacedInKingdom) {
		  Game_4_Players.warningAfterMoving();
		  return false; 
	   }
	   
	   return true;
  }


  public String toString()
  {
    return super.toString() + "["+
            "selectingOrder" + ":" + getSelectingOrder()+ "," +
            "placingOrder" + ":" + getPlacingOrder()+ "," +
            "total_No_Draft" + ":" + getTotal_No_Draft()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "game" + "=" + (getGame() != null ? !getGame().equals(this)  ? getGame().toString().replaceAll("  ","    ") : "this" : "null");
  }
}