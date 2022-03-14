import java.util.ArrayList;
import java.util.Random;

public class GraphSearchAgent {

    private ChessMat chessMat;
    private int startTurn;
    private State root;
    private int[][] actions;

    private ArrayList<int[]> DEPTH1_1OVER4;
    private ArrayList<int[]> DEPTH2_1OVER4;
    private ArrayList<int[]> ALPHA_BETA_05;
    private ArrayList<int[]> ALPHA_BETA_06;
    private ArrayList<int[]> ALPHA_BETA_07;
    private ArrayList<int[]> ALPHA_BETA_08;
    private ArrayList<int[]> RANDOM_ACTION;

    public GraphSearchAgent(ChessMat chessMat,int startTurn,int unknow){
        this.chessMat=new ChessMat(chessMat);
        this.startTurn=startTurn;
        this.root=new State(chessMat,-1,null,null);
        this.actions=new int[12][6];
        this.DEPTH1_1OVER4=new ArrayList<int[]>();
        this.DEPTH2_1OVER4=new ArrayList<int[]>();
        this.ALPHA_BETA_05=new ArrayList<int[]>();
        this.ALPHA_BETA_06=new ArrayList<int[]>();
        this.ALPHA_BETA_07=new ArrayList<int[]>();
        this.ALPHA_BETA_08=new ArrayList<int[]>();
        this.RANDOM_ACTION=new ArrayList<int[]>();
    }

    public int[] getAction() throws InterruptedException{
        //int OPP_rootTerminal1_4=OPPTerminalCheck1_4(root);
        //int SEL_rootTerminal1_4=SELTerminalCheck1_4(root);
        //ArrayList<State> ThreateningStates=new ArrayList<State>();
        ArrayList<State> SEL_PossibleStates_depth1 = root.getAllPossibleStates(startTurn);

        //THREADS DECLARE
        Thread depth1_chess_terminal;
        Thread depth2_chess_terminal;
        Thread alpha_beta_search005_backup;
        Thread alpha_beta_search006_backup;
        Thread alpha_beta_search007_backup;
        Thread alpha_beta_search008_backup;
        Thread random_action_backup;
 
        depth1_chess_terminal = new Thread(){
            @Override
            public void run(){
                depth1_chess_terminal(SEL_PossibleStates_depth1);
                System.out.print(">");
            }        
        };
        depth2_chess_terminal = new Thread(){
            @Override
            public void run(){
                depth2_chess_terminal(SEL_PossibleStates_depth1);
                System.out.print(">");
            }        
        };
        alpha_beta_search005_backup = new Thread(){
            @Override
            public void run(){
                MinMaxSearchAgent agentPlayer = new MinMaxSearchAgent(chessMat,startTurn,C.MINMAX_AGENT_PLAYER005);
                ALPHA_BETA_05.add(agentPlayer.getAction());
                System.out.print(">");
            }        
        };
        alpha_beta_search006_backup = new Thread(){
            @Override
            public void run(){
                MinMaxSearchAgent agentPlayer = new MinMaxSearchAgent(chessMat,startTurn,C.MINMAX_AGENT_PLAYER006);
                ALPHA_BETA_06.add(agentPlayer.getAction());
                System.out.print(">");
            }        
        };
        alpha_beta_search007_backup = new Thread(){
            @Override
            public void run(){
                MinMaxSearchAgent agentPlayer = new MinMaxSearchAgent(chessMat,startTurn,C.MINMAX_AGENT_PLAYER007);
                ALPHA_BETA_07.add(agentPlayer.getAction());
                System.out.print(">");
            }        
        };
        alpha_beta_search008_backup = new Thread(){
            @Override
            public void run(){
                MinMaxSearchAgent agentPlayer = new MinMaxSearchAgent(chessMat,startTurn,C.MINMAX_AGENT_PLAYER008);
                ALPHA_BETA_08.add(agentPlayer.getAction());
                System.out.print(">");
            }        
        };
        random_action_backup  = new Thread(){
            @Override
            public void run(){
                RANDOM_ACTION.add(getRandomAction());
                System.out.print(">");
            }              
        };

        //THREADS START
        try{
        depth1_chess_terminal.start();
        depth2_chess_terminal.start();
        alpha_beta_search005_backup.start();
        alpha_beta_search006_backup.start();
        alpha_beta_search007_backup.start();
        alpha_beta_search008_backup.start();
        random_action_backup.start();
        }catch(RuntimeException e){
            e.printStackTrace();
        }

        //JOIN THREADS
        try{
            depth1_chess_terminal.join();
            depth2_chess_terminal.join();
            alpha_beta_search005_backup.join();
            alpha_beta_search006_backup.join();
            alpha_beta_search007_backup.join();
            alpha_beta_search008_backup.join();
            random_action_backup.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        return global_parliament();
    }

    private void depth1_chess_terminal(ArrayList<State> SEL_PossibleStates_depth1){
        int[] action=new int[6];
        int OPP_rootTerminal1_4=OPPTerminalCheck1_4(root);
        SEL_PossibleStates_depth1 = root.getAllPossibleStates(startTurn);

        for(State sel_state_depth1:SEL_PossibleStates_depth1){
            //[RETURN TERMINAL OPP ACTION]OPP 1/4 TERMINAL
            if(OPPTerminalCheck1_4(sel_state_depth1)>OPP_rootTerminal1_4){
                action=sel_state_depth1.action;
                DEPTH1_1OVER4.add(action);
            }
        }
    }
    private void depth2_chess_terminal(ArrayList<State> SEL_PossibleStates_depth1){
        int[] action = new int[6];
        int OPP_rootTerminal1_4=OPPTerminalCheck1_4(root);
        for(State sel_state_depth1:SEL_PossibleStates_depth1){
            ArrayList<State> SEL_PossibleStates_depth2 = sel_state_depth1.getAllPossibleStates(startTurn);
            for(State sel_state_depth2:SEL_PossibleStates_depth2){
                //OPP 1/8 TERMINAL
                if(OPPTerminalCheck1_4(sel_state_depth2)>OPP_rootTerminal1_4 && !(OPPTerminalCheck1_4(sel_state_depth2)<OPP_rootTerminal1_4)){
                    action=sel_state_depth1.action;
                    DEPTH2_1OVER4.add(action);
                }
            }
        }
    }
    public int[] getRandomAction(){
        Random rand = new Random();
        State root = new State(chessMat,-1,null,null);
        ArrayList<State> PossibleStates = root.getAllPossibleStates(startTurn);
        int random_index=rand.nextInt(PossibleStates.size());
        return PossibleStates.get(random_index).action;
    }

    private int[] global_parliament() throws InterruptedException{
        ArrayList<int[]> actionBundle = new ArrayList<int[]>();
        //THIS WILL HAVE REALLY MANY ACTIONS
        actionBundle.addAll(DEPTH1_1OVER4);
        //THIS WILL HAVE INFINIT ACTIONS
        //actionBundle.addAll(DEPTH2_1OVER4);
        actionBundle.addAll(ALPHA_BETA_05);
        actionBundle.addAll(ALPHA_BETA_06);
        actionBundle.addAll(ALPHA_BETA_07);
        actionBundle.addAll(ALPHA_BETA_08);
        MonteCarloTreeSearch agentPlayer = new MonteCarloTreeSearch(chessMat,startTurn,-1);
        return agentPlayer.getAction(actionBundle);
    }
    private int[] minmax_parliament(){
        int maxAgreed=1;
        int[] maxAgreedAction=ALPHA_BETA_08.get(0);

        ArrayList<int[]> MINMAXACTIONS = new ArrayList<int[]>();
        MINMAXACTIONS.add(ALPHA_BETA_08.get(0));
        MINMAXACTIONS.add(ALPHA_BETA_06.get(0));
        MINMAXACTIONS.add(ALPHA_BETA_05.get(0));
        MINMAXACTIONS.add(ALPHA_BETA_07.get(0));

        for(int i=0; i<=MINMAXACTIONS.size(); i++){
            int actionAgreed=0;
            for(int j=i; j<MINMAXACTIONS.size(); j++){
                if(isEqual(actions[i], actions[j])){actionAgreed++;}
            }
            if(actionAgreed>maxAgreed){
                maxAgreed=actionAgreed;
                maxAgreedAction=actions[i];
            }
        }
        if(!isThreatingAction(maxAgreedAction)){
            return maxAgreedAction;
        }else{
            return new int[6];
        }

    }
    public int OPPTerminalCheck1_4(State state){
        ArrayList<Position> chesses;
        int chessTerminal=0;
        if(startTurn==C.WHITE_MOVE){chesses=state.chessMat.getAllTypeChessPositions(C.BLACK_QUEEN);}else{chesses=state.chessMat.getAllTypeChessPositions(C.WHITE_QUEEN);}
        //CHECK NUMBER OF CHESS TERMINAL
        for(Position chess:chesses){
            if(state.chessMat.isChessTerminal(chess)){
                chessTerminal++;
            }
        }
        return chessTerminal;
    }
    public int SELTerminalCheck1_4(State state){
        ArrayList<Position> chesses;
        int chessTerminal=0;
        if(startTurn==C.WHITE_MOVE){chesses=state.chessMat.getAllTypeChessPositions(C.WHITE_QUEEN);}else{chesses=state.chessMat.getAllTypeChessPositions(C.BLACK_QUEEN);}
        //CHECK NUMBER OF CHESS TERMINAL
        for(Position chess:chesses){
            if(state.chessMat.isChessTerminal(chess)){
                chessTerminal++;
            }
        }
        return chessTerminal;
    }
    public boolean isThreatingAction(int[] action){
        if(SELTerminalCheck1_4(root)<SELTerminalCheck1_4(State.applyAction(root, action))){
            return true;
        }
        return false;
    }

    private boolean isEmpty(int[] action){
        for(int i=0; i<action.length; i++){
            if(action[i]!=0){return false;}
        }
        return true;
    }
    private boolean isEqual(int[] arrayA, int[] arrayB){
        if(arrayA.length!=arrayB.length){return false;}
        for(int i=0; i<arrayA.length; i++){
            if(arrayA[i]!=arrayB[i]){return false;}
        }
        return true;
    }
}
