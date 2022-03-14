import java.util.ArrayList;

public class MarkovDecisionProcessor {
    private ChessMat chessMat;
    private int startTurn;
    private int agentPlaterType;

    public static final double TERMINAL_POS_REWARD = Integer.MAX_VALUE;
    public static final double TERMINAL_NEG_REWARD = Integer.MAX_VALUE;
    public static final double DISCOUNT_FACTOR = 1;


    public MarkovDecisionProcessor(ChessMat chessMat,int startTurn,int agentPlaterType){
        this.chessMat=new ChessMat(chessMat);
        this.startTurn=startTurn;
        this.agentPlaterType=agentPlaterType;
    }
    
    private double U(State state,int currentTurn){
        double maxU=0;
        ArrayList<State> possibleStates = state.getAllPossibleStates(currentTurn);
        for(State subState:possibleStates){
            if(maxU<(P(state,subState)*(R(state,subState,currentTurn)+DISCOUNT_FACTOR*U(subState,currentTurn))));
        }
        return -1;
    }

    private double P(State parentState,State subState){
        return -1;
    }

    private double R(State parentState,State subState,int currentTurn){
        double reward = -0.04;
        if(inFriendlyTurn(startTurn, currentTurn)){
            if(subState.chessMat.isTerminal(nextTurn(currentTurn))){
                reward=TERMINAL_POS_REWARD;
            }
        }else{
            if(subState.chessMat.isTerminal(nextTurn(currentTurn))){
                reward=TERMINAL_NEG_REWARD;
            }
        }
        return reward;
    }

    private boolean inFriendlyTurn(int startTurn, int currentTurn){
        if(currentTurn==C.WHITE_MOVE || currentTurn==C.WHITE_BLOC){
            if(startTurn==C.WHITE_MOVE || startTurn==C.WHITE_BLOC){
                return true;
            }
        }else{
            if(startTurn==C.BLACK_MOVE || startTurn==C.BLACK_BLOC){
                return true;
            }
        }
        return false;
    }
    private int nextTurn(int currentTurn){
        if(currentTurn==C.WHITE_MOVE){
            return C.BLACK_MOVE;
        }else if(currentTurn==C.BLACK_MOVE){
            return C.WHITE_MOVE;
        }else{
            return -1;
        }
    }
}
