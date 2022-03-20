import java.util.ArrayList;

public class State {
    public ChessMat chessMat;
    public int f_value;
    public State parentState;
    public int[] action;

    public State(ChessMat chessMat,int f_value,State parentState,int[] action){
        this.chessMat=new ChessMat(chessMat);
        this.f_value=f_value;
        this.parentState=parentState;
        if(action!=null){
            this.action=new int[action.length];
            for(int i=0; i<action.length; i++){
                this.action[i]=action[i];
            }
        }else{
            action=null;
        }
    }
    public ArrayList<State> getAllPossibleStates(int gameStage){
        ArrayList<State> possibleStates = new ArrayList<State>();
        ArrayList<Position> allMoveableChess = new ArrayList<Position>();
        int[] action = new int[6];
        if(gameStage==C.WHITE_MOVE){
            allMoveableChess = this.chessMat.getAllTypeChessPositions(C.WHITE_QUEEN);
        }else{
            allMoveableChess = this.chessMat.getAllTypeChessPositions(C.BLACK_QUEEN);
        }
        ArrayList<Position> moveablePositions;
        for(Position chess : allMoveableChess){
            moveablePositions = this.chessMat.getAllMoveablePositionsForChess(chess);
            for(Position moveablePosition : moveablePositions){
                ChessMat pasteChessMat = new ChessMat(this.chessMat);
                pasteChessMat.overWrite(this.chessMat.getChess(chess),moveablePosition);
                pasteChessMat.overWrite(C.EMPTY_BLOCK,chess);
                ArrayList<Position> blocablePositions;
                blocablePositions = pasteChessMat.getAllMoveablePositionsForChess(moveablePosition);
                for(Position blocablePosition : blocablePositions){
                    ChessMat pasteBlocChessMat = new ChessMat(pasteChessMat);
                    pasteBlocChessMat.overWrite(C.AMAZON_BLOCK,blocablePosition);
                    action[C.FROM_X]=chess.x;
                    action[C.FROM_Y]=chess.y;
                    action[C.TO_X]=moveablePosition.x;
                    action[C.TO_Y]=moveablePosition.y;
                    action[C.BLOC_X]=blocablePosition.x;
                    action[C.BLOC_Y]=blocablePosition.y;
                    possibleStates.add(new State(pasteBlocChessMat,C.DEFAULT_F_VALUE,this,action));
                }
            }
        }

        return possibleStates;
    }
    public ArrayList<State> getAllPossibleStates(int gameStage, boolean ignoreClosed){
        ArrayList<State> possibleStates = new ArrayList<State>();
        ArrayList<Position> allMoveableChess = new ArrayList<Position>();
        int[] action = new int[6];
        if(gameStage==C.WHITE_MOVE){
            allMoveableChess = this.chessMat.getAllTypeChessPositions(C.WHITE_QUEEN);
            for(int i=allMoveableChess.size()-1; i>=0; i--){
                if(this.chessMat.isClosed(allMoveableChess.get(i))){
                    allMoveableChess.remove(i);
                }
            }
        }else{
            allMoveableChess = this.chessMat.getAllTypeChessPositions(C.BLACK_QUEEN);
            for(int i=allMoveableChess.size()-1; i>=0; i--){
                if(this.chessMat.isClosed(allMoveableChess.get(i))){
                    allMoveableChess.remove(i);
                }
            }
        }
        
        if(allMoveableChess.isEmpty()){return possibleStates;}

        ArrayList<Position> moveablePositions;
        for(Position chess : allMoveableChess){
            moveablePositions = this.chessMat.getAllMoveablePositionsForChess(chess);
            for(Position moveablePosition : moveablePositions){
                ChessMat pasteChessMat = new ChessMat(this.chessMat);
                pasteChessMat.overWrite(this.chessMat.getChess(chess),moveablePosition);
                pasteChessMat.overWrite(C.EMPTY_BLOCK,chess);
                ArrayList<Position> blocablePositions;
                blocablePositions = pasteChessMat.getAllMoveablePositionsForChess(moveablePosition);
                for(Position blocablePosition : blocablePositions){
                    ChessMat pasteBlocChessMat = new ChessMat(pasteChessMat);
                    pasteBlocChessMat.overWrite(C.AMAZON_BLOCK,blocablePosition);
                    action[C.FROM_X]=chess.x;
                    action[C.FROM_Y]=chess.y;
                    action[C.TO_X]=moveablePosition.x;
                    action[C.TO_Y]=moveablePosition.y;
                    action[C.BLOC_X]=blocablePosition.x;
                    action[C.BLOC_Y]=blocablePosition.y;
                    possibleStates.add(new State(pasteBlocChessMat,C.DEFAULT_F_VALUE,this,action));
                }
            }
        }

        return possibleStates;
    }
    public ArrayList<State> getAllPossibleStates(int gameStage,int limit){
        ArrayList<State> possibleStates = new ArrayList<State>();
        ArrayList<Position> allMoveableChess = new ArrayList<Position>();
        int[] action = new int[6];
        if(gameStage==C.WHITE_MOVE){
            allMoveableChess = this.chessMat.getAllTypeChessPositions(C.WHITE_QUEEN);
        }else{
            allMoveableChess = this.chessMat.getAllTypeChessPositions(C.BLACK_QUEEN);
        }
        ArrayList<Position> moveablePositions;
        for(Position chess : allMoveableChess){
            moveablePositions = this.chessMat.getAllMoveablePositionsForChess(chess);
            for(Position moveablePosition : moveablePositions){
                ChessMat pasteChessMat = new ChessMat(this.chessMat);
                pasteChessMat.overWrite(this.chessMat.getChess(chess),moveablePosition);
                pasteChessMat.overWrite(C.EMPTY_BLOCK,chess);
                ArrayList<Position> blocablePositions;
                blocablePositions = pasteChessMat.getAllMoveablePositionsForChess(moveablePosition);
                for(Position blocablePosition : blocablePositions){
                    ChessMat pasteBlocChessMat = new ChessMat(pasteChessMat);
                    pasteBlocChessMat.overWrite(C.AMAZON_BLOCK,blocablePosition);
                    action[C.FROM_X]=chess.x;
                    action[C.FROM_Y]=chess.y;
                    action[C.TO_X]=moveablePosition.x;
                    action[C.TO_Y]=moveablePosition.y;
                    action[C.BLOC_X]=blocablePosition.x;
                    action[C.BLOC_Y]=blocablePosition.y;
                    possibleStates.add(new State(pasteBlocChessMat,C.DEFAULT_F_VALUE,this,action));
                    limit--;
                    if(limit<=0){
                        return possibleStates;
                    }
                }
            }
        }

        return possibleStates;
    }
    public ArrayList<State> getAllPossibleStatesForChess(int gameStage, Position chess){
        ArrayList<State> possibleStates = new ArrayList<State>();
        int[] action = new int[6];
        ArrayList<Position> moveablePositions;
        moveablePositions = this.chessMat.getAllMoveablePositionsForChess(chess);
        for(Position moveablePosition : moveablePositions){
            ChessMat pasteChessMat = new ChessMat(this.chessMat);
            pasteChessMat.overWrite(this.chessMat.getChess(chess),moveablePosition);
            pasteChessMat.overWrite(C.EMPTY_BLOCK,chess);
            ArrayList<Position> blocablePositions;
            blocablePositions = pasteChessMat.getAllMoveablePositionsForChess(moveablePosition);
            for(Position blocablePosition : blocablePositions){
                ChessMat pasteBlocChessMat = new ChessMat(pasteChessMat);
                pasteBlocChessMat.overWrite(C.AMAZON_BLOCK,blocablePosition);
                action[C.FROM_X]=chess.x;
                action[C.FROM_Y]=chess.y;
                action[C.TO_X]=moveablePosition.x;
                action[C.TO_Y]=moveablePosition.y;
                action[C.BLOC_X]=blocablePosition.x;
                action[C.BLOC_Y]=blocablePosition.y;
                possibleStates.add(new State(pasteBlocChessMat,C.DEFAULT_F_VALUE,this,action));
            }
        }

        return possibleStates;
    }
    public static State applyAction(State state, int[] action){
        ChessMat old_chessMat = state.chessMat;
        ChessMat new_chessMat = new ChessMat(old_chessMat);
        new_chessMat.overWrite(old_chessMat.getChess(action[0], action[1]), new Position(action[2], action[3]));
        new_chessMat.overWrite(C.EMPTY_BLOCK, new Position(action[0], action[1]));
        new_chessMat.overWrite(C.AMAZON_BLOCK, new Position(action[4], action[5]));
        return new State(new_chessMat,C.DEFAULT_F_VALUE,state,action);
    }
    public String toString(){
        return this.chessMat.toString();
    }
}
