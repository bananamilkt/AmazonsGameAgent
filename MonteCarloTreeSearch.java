import java.util.ArrayList;
import java.util.Random;

public class MonteCarloTreeSearch {
    private ChessMat chessMat;
    private int startTurn;
    private int depth;
    private ArrayList<int[]> inputActions;

    public MonteCarloTreeSearch(ChessMat chessMat,int startTurn,int unkown){
        this.chessMat=new ChessMat(chessMat);
        this.startTurn=startTurn;
        this.depth=512;
    }

    public int[] getAction() throws InterruptedException{
        State root = new State(chessMat,-1,null,null);
        ArrayList<State> subStates = new ArrayList<State>();
        ArrayList<Thread> threads1 = new ArrayList<Thread>();
        ArrayList<Thread> threads2 = new ArrayList<Thread>();
        ArrayList<int[]> possibleActions = new ArrayList<int[]>();
        double max_winning=0;

        threads1.add(new Thread(){
            @Override
            public void run(){
                MinMaxSearchAgent agentPlayer = new MinMaxSearchAgent(chessMat,startTurn,C.MINMAX_AGENT_PLAYER005);
                possibleActions.add(agentPlayer.getAction());
            }
        });
        threads1.add(new Thread(){
            @Override
            public void run(){
                MinMaxSearchAgent agentPlayer = new MinMaxSearchAgent(chessMat,startTurn,C.MINMAX_AGENT_PLAYER006);
                possibleActions.add(agentPlayer.getAction());
            }
        });
        threads1.add(new Thread(){
            @Override
            public void run(){
                MinMaxSearchAgent agentPlayer = new MinMaxSearchAgent(chessMat,startTurn,C.MINMAX_AGENT_PLAYER007);
                possibleActions.add(agentPlayer.getAction());
            }
        });
        threads1.add(new Thread(){
            @Override
            public void run(){
                MinMaxSearchAgent agentPlayer = new MinMaxSearchAgent(chessMat,startTurn,C.MINMAX_AGENT_PLAYER008);
                possibleActions.add(agentPlayer.getAction());
            }
        });
        threads1.add(new Thread(){
            @Override
            public void run(){
                MinMaxSearchAgent agentPlayer = new MinMaxSearchAgent(chessMat,startTurn,C.MINMAX_AGENT_PLAYER009);
                possibleActions.add(agentPlayer.getAction());
            }
        });
        for(Thread t:threads1){
            t.start();
        }
        for(Thread t:threads1){
            try{t.join();}catch(InterruptedException e){e.printStackTrace();}
        }

        for(int[] action:possibleActions){
            subStates.add(State.applyAction(root, action));
        }
        State max_winning_state=subStates.get(0);
        int[] winning = new int[subStates.size()];

        for(State s:subStates){
            threads2.add(new Thread(){
                @Override
                public void run(){
                    winning[threads2.size()-1]=0;
                    for(int k=0; k<depth; k++){
                        winning[threads2.size()-1]=simulateSequence(s, nextTurn(nextTurn(startTurn)))+winning[threads2.size()-1];
                    }
                }
            });
        }
        for(Thread t:threads2){
            t.start();
        }
        for(Thread t:threads2){
            try{t.join();}catch(InterruptedException e){e.printStackTrace();}
        }

        for(int k=0; k<winning.length;k++){
            if(winning[k]>max_winning){
                max_winning=winning[k];
                max_winning_state=subStates.get(k);
            }
        }
   
        return max_winning_state.action;
    }
    public int[] getAction(ArrayList<int[]> possibleActions) throws InterruptedException{
        State root = new State(chessMat,-1,null,null);
        ArrayList<State> subStates = new ArrayList<State>();
        ArrayList<Thread> threads = new ArrayList<Thread>();
        inputActions = possibleActions;
        double max_winning=0;

        for(int[] action:possibleActions){
            subStates.add(State.applyAction(root, action));
        }
        State max_winning_state=subStates.get(0);
        int[] winning = new int[subStates.size()];

        for(State s:subStates){
            threads.add(new Thread(){
                @Override
                public void run(){
                    winning[threads.size()-1]=0;
                    for(int k=0; k<depth; k++){
                        winning[threads.size()-1]=simulateSequence(s, nextTurn(nextTurn(startTurn)))+winning[threads.size()-1];
                    }
                }
            });
        }
        for(Thread t:threads){
            t.start();
        }
        for(Thread t:threads){
            try{t.join();}catch(InterruptedException e){e.printStackTrace();}
        }

        for(int k=0; k<winning.length;k++){
            if(winning[k]>max_winning){
                max_winning=winning[k];
                max_winning_state=subStates.get(k);
            }
        }
   
        return max_winning_state.action;
    }

    private int simulateSequence(State state, int currentTurn){
        Random rand = new Random();
        if(state.chessMat.isTerminal(currentTurn)){
            if(startTurn==currentTurn){return 1;}else{return 0;}
        }else{
            ArrayList<State> subStates = state.getAllPossibleStates(currentTurn);
            int random_index=rand.nextInt(subStates.size());
            return simulateSequence(subStates.get(random_index), nextTurn(currentTurn));
        }
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
}
