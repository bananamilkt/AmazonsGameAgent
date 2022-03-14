import java.util.ArrayList;
import java.util.Scanner;

public class GameManager{
    public static Scanner input;
    public static final boolean DISPLAY_DETAIL =    true;
    public static final boolean DISPLAY_NONE =      false;
    public static void main(String []args) {
        run(C.AMAZONS_CHESS_MAT, C.AMAZON_AGENT_PLAYER_X_, C.GRAPH_AGENT_PLAYER012,DISPLAY_DETAIL);
        close();
    }

    public static int run(int chessMatTYpe, int whitePlayerType, int blackPlayerType, boolean displayDetail){
        long startTime=System.nanoTime();
        ChessMat chessMat;
        int currentTurn = C.WHITE_MOVE; 
        int[] actionSequence = null;
        
        //Inital Game Board
        if(chessMatTYpe == C.SIMPLE_AMAZONS_CHESS_MAT){
            chessMat = new ChessMat(6,6);
            chessMat.initialSimpleAmazonChessMat();
        }else{
            chessMat = new ChessMat(10,10);
            chessMat.initialAmazonChessMat();
        }

        while(!chessMat.isTerminal(currentTurn)){
            if(displayDetail){
                System.out.println(chessMat);
            }
            if(currentTurn==C.WHITE_MOVE){
                if(whitePlayerType==C.HUMAN_PLAYER){
                    actionSequence=askHumanPlayerMove(chessMat,currentTurn,displayDetail);
                }else{
                    actionSequence=askAgentPlayerMove(chessMat,currentTurn,whitePlayerType,displayDetail);
                }
            }else{
                if(blackPlayerType==C.HUMAN_PLAYER){
                    actionSequence=askHumanPlayerMove(chessMat,currentTurn,displayDetail);
                }else{
                    actionSequence=askAgentPlayerMove(chessMat,currentTurn,blackPlayerType,displayDetail);
                }
            }   

                //ApplyActions
            applyAction(chessMat, actionSequence,currentTurn,displayDetail);

                //Move to next turn
            if(currentTurn == C.WHITE_MOVE){
                currentTurn = C.BLACK_MOVE;
            }else{
                currentTurn = C.WHITE_MOVE;
            }
        }
        //END
        return terminalResult(chessMat,currentTurn,whitePlayerType,blackPlayerType,startTime);
    }

    private static int[] askHumanPlayerMove(ChessMat chessMat, int currentTurn, boolean displayDetail){
        long startTime=System.nanoTime();
        int[] actionList = new int[6];
        int from_x = -1;
        int from_y = -1;
        int to_x = -1;
        int to_y = -1;
        int bloc_x = -1;
        int bloc_y = -1;
        boolean valid = false;
        if(currentTurn==C.WHITE_MOVE || currentTurn==C.WHITE_BLOC){
            System.out.println("[WHITE_MOVE_TURN]");
        }else{
            System.out.println("[BLACK_MOVE_TURN]");
        }
        //Chess on selected
        while(!valid){
            System.out.println("[FROM INDEX X]");
            from_x = askInput(0, 9);
            System.out.println("[FROM INDEX Y]");
            from_y = askInput(0, 9);
            valid = checkChessOnPosition(chessMat,from_x,from_y,currentTurn);
        }
        actionList[0]=from_x;
        actionList[1]=from_y;
        valid = false;
        //Chess to move
        while(!valid){
            System.out.println("[TO INDEX   X]");
            to_x = askInput(0, 9);
            System.out.println("[TO INDEX   Y]");
            to_y = askInput(0, 9);
            valid = checkMoveActionValid(chessMat,from_x,from_y,to_x,to_y,currentTurn);
        }
        actionList[2]=to_x;
        actionList[3]=to_y;  
        valid = false;     
        //Chess to bloc
        while(!valid){
            System.out.println("[TO BLOCK   X]");
            bloc_x = askInput(0, 9);
            System.out.println("[TO BLOCK   Y]");
            bloc_y = askInput(0, 9);
            valid = checkBlocActionValid(chessMat,from_x,from_y,to_x,to_y,bloc_x,bloc_y,currentTurn);
        }    
        actionList[4]=bloc_x;
        actionList[5]=bloc_y;
        long stopTime=System.nanoTime();
        if(displayDetail==DISPLAY_DETAIL){
            if(stopTime-startTime>1000000000){
                System.out.print("[ "+(stopTime-startTime)/1000000000+"s ]");
            }else{
                System.out.print("[ "+(stopTime-startTime)/1000000+"ms ]");
            }
        }
        return actionList;
    }
    private static int[] askAgentPlayerMove(ChessMat chessMat, int currentTurn,int agentPlaterType, boolean displayDetail){
        long startTime=System.nanoTime();
        boolean valid = false;
        int[] actionSequence=new int[6];
        while(!valid){
            if(agentPlaterType>=161 && agentPlaterType<=171){
                MinMaxSearchAgent agentPlayer = new MinMaxSearchAgent(chessMat,currentTurn,agentPlaterType);
                actionSequence=agentPlayer.getAction();
            }else if(agentPlaterType==200){
                RandomAgent agentPlayer = new RandomAgent(chessMat,currentTurn,agentPlaterType);
                actionSequence=agentPlayer.getAction();
            }else if(agentPlaterType==172){
                GraphSearchAgent agentPlayer = new GraphSearchAgent(chessMat,currentTurn,agentPlaterType);
                try{actionSequence=agentPlayer.getAction();}catch(InterruptedException e){e.printStackTrace();}
            }else if(agentPlaterType==198){
                MonteCarloTreeSearch agentPlayer = new MonteCarloTreeSearch(chessMat,currentTurn,agentPlaterType);
                try{actionSequence=agentPlayer.getAction();}catch(InterruptedException e){e.printStackTrace();}
            }else if(agentPlaterType==199){
                SearchAgentX agentPlayer = new SearchAgentX(chessMat,currentTurn,agentPlaterType);
                actionSequence=agentPlayer.getAction();
            }else{
                MinMaxSearchAgent agentPlayer = new MinMaxSearchAgent(chessMat,currentTurn,agentPlaterType);
                actionSequence=agentPlayer.getAction();
            }    

            valid=checkChessOnPosition(chessMat,actionSequence[C.FROM_X],actionSequence[C.FROM_Y],currentTurn);
            if(valid==false){
                System.out.println("[SEARCH AGENT ERROR: EMPTY BLOCK ON SELECTED]");
                break;
            }
            valid=checkMoveActionValid(chessMat,actionSequence[C.FROM_X],actionSequence[C.FROM_Y],actionSequence[C.TO_X],actionSequence[C.TO_Y],currentTurn);
            if(valid==false){
                System.out.println("[SEARCH AGENT ERROR: INVALID POSITION TO MOVE]");
                break;
            }
            valid=checkBlocActionValid(chessMat,actionSequence[C.FROM_X],actionSequence[C.FROM_Y],actionSequence[C.TO_X],actionSequence[C.TO_Y],actionSequence[C.BLOC_X],actionSequence[C.BLOC_Y],currentTurn);
            if(valid==false){
                System.out.println("[SEARCH AGENT ERROR: INVALID POSITION TO BLOC]");
                break;
            }
        }

        long stopTime=System.nanoTime();
        if(displayDetail==DISPLAY_DETAIL){
            if(stopTime-startTime>1000000000){
                System.out.print("[ "+(stopTime-startTime)/1000000000+"s ]");
            }else{
                System.out.print("[ "+(stopTime-startTime)/1000000+"ms ]");
            }
        }
        return actionSequence;
    }

    //Action checkers
    private static boolean checkChessOnPosition(ChessMat chessMat, int from_x,int from_y,int currentTurn){
        int chessType = chessMat.getChess(from_x,from_y);
        if(chessType == C.WHITE_QUEEN && currentTurn == C.WHITE_MOVE){
            return true;
        }else if(chessType == C.BLACK_QUEEN && currentTurn == C.BLACK_MOVE){
            return true;
        }else{
            return false;
        }
    }
    private static boolean checkMoveActionValid(ChessMat chessMat,int from_x,int from_y,int to_x,int to_y,int currentTurn){
        ArrayList<Position> positions = chessMat.getAllMoveablePositionsForChess(new Position(from_x,from_y));
        for(int i=0; i<positions.size(); i++){
            if(positions.get(i).x==to_x && positions.get(i).y==to_y){
                return true;
            }
        }
        return false;
    }
    private static boolean checkBlocActionValid(ChessMat chessMat, int from_x,int from_y, int to_x,int to_y,int bloc_x, int bloc_y, int currentTurn){
        ChessMat new_chessMat = new ChessMat(chessMat);
        new_chessMat.overWrite(new_chessMat.getChess(from_x,from_y),new Position(to_x,to_y));
        new_chessMat.overWrite(C.EMPTY_BLOCK,new Position(from_x,from_y));
        ArrayList<Position> positions = new_chessMat.getAllMoveablePositionsForChess(new Position(to_x,to_y));
        for(int i=0; i<positions.size(); i++){
            if(positions.get(i).x==bloc_x && positions.get(i).y==bloc_y){
                return true;
            }
        }
        return false;
    }

    private static void applyAction(ChessMat chessMat,int[] actionSequence,int currentTurn,boolean diplayDetail){
        //MOVETO
        chessMat.overWrite(chessMat.getChess(actionSequence[0],actionSequence[1]),new Position(actionSequence[2],actionSequence[3]));
        //EMPTYFROM
        chessMat.overWrite(C.EMPTY_BLOCK,new Position(actionSequence[0],actionSequence[1]));
        //BLOC
        chessMat.overWrite(C.AMAZON_BLOCK,new Position(actionSequence[4],actionSequence[5]));
        if(diplayDetail){
            if(currentTurn==C.WHITE_MOVE){
                System.out.println("[WHITE TURN: ACTION MADE FROM [" +actionSequence[0]+","+actionSequence[1]+"] TO ["+actionSequence[2]+","+actionSequence[3]+"] BLOC ["+actionSequence[4]+","+actionSequence[5]+"]");
            }else{
                System.out.println("[BLACK TURN: ACTION MADE FROM [" +actionSequence[0]+","+actionSequence[1]+"] TO ["+actionSequence[2]+","+actionSequence[3]+"] BLOC ["+actionSequence[4]+","+actionSequence[5]+"]");
            }
        }else{
            System.out.print(">");
        }
    }

    private static int askInput(int min, int max){
        input = new Scanner(System.in);
        int userInput = -999;
        while(userInput<min || userInput>max){
            userInput = input.nextInt();
        }
        return userInput;
    }
    private static void close(){
        input.close();
        System.out.println("[SYSTEM CLOSE");
    }
    private static int terminalResult(ChessMat chessMat,int currentTurn,int whitePlayerType,int blackPlayerType,long startTime){
        System.out.println("\n"+chessMat);
        long stopTime=System.nanoTime();
        if(currentTurn == C.WHITE_MOVE){
            System.out.print("[ "+(stopTime-startTime)/1000000000+"s ]");
            System.out.println("["+C.PLAYER_TYPE[blackPlayerType-160]+"_WIN && "+C.PLAYER_TYPE[whitePlayerType-160]+"_LOSE]");
            return -1;
        }else{
            System.out.print("[ "+(stopTime-startTime)/1000000000+"s ]");
            System.out.println("["+C.PLAYER_TYPE[whitePlayerType-160]+"_WIN && "+C.PLAYER_TYPE[blackPlayerType-160]+"_LOSE]");
            return 1;
        }
    }
}