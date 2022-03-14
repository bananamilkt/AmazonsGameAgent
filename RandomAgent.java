import java.util.ArrayList;
import java.util.Random;

public class RandomAgent {
    private ChessMat chessMat;
    private int startTurn;

    private class Action{
        public State parentState;
        public State subState;
    }

    public RandomAgent(ChessMat chessMat,int startTurn,int agentPlaterType){
        this.chessMat=new ChessMat(chessMat);
        this.startTurn=startTurn;
    }

    public int[] getAction(){
        Random rand = new Random();
        State root = new State(chessMat,-1,null,null);
        ArrayList<State> PossibleStates = root.getAllPossibleStates(startTurn);
        int random_index=rand.nextInt(PossibleStates.size());
        return PossibleStates.get(random_index).action;
    }

}
