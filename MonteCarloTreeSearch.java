import java.util.ArrayList;
import java.util.Random;

public class MonteCarloTreeSearch {
    private ChessMat chessMat;
    private int startTurn;
    private int depth;

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
    private int calculate_F_value(ChessMat chessMat, int currentTurn,int agentPlaterType){
        int self_f_value;
        int opp_f_value;
        ArrayList<Position> allMoveableChess = new ArrayList<Position>();
        ArrayList<Position> allOppMoveableChess = new ArrayList<Position>();
        ArrayList<Position> moveablePositions = new ArrayList<Position>();
        ArrayList<Position> OppMoveablePositions = new ArrayList<Position>();
        int[][] moveableDirection;
        int[][] oppMoveableDirection;
        if(chessMat.isTerminal(nextTurn(currentTurn))){
            if(inFriendlyTurn(startTurn, currentTurn)){
                return C.TERMINAL_STATE;
            }else{
                return C.TERMINAL_STATE;
            }
        }
//MINMAX_AGENT_PLAYER001
        if(agentPlaterType==C.MINMAX_AGENT_PLAYER001){
            if(currentTurn==C.WHITE_MOVE || currentTurn==C.WHITE_BLOC){
                allMoveableChess = chessMat.getAllTypeChessPositions(C.WHITE_QUEEN);
            }else{
                allMoveableChess = chessMat.getAllTypeChessPositions(C.BLACK_QUEEN);
            }
            for(Position chess : allMoveableChess){
                moveablePositions = chessMat.getAllMoveablePositionsForChess(chess);
            }
            return moveablePositions.size();
        }
//MINMAX_AGENT_PLAYER002        
        if(agentPlaterType==C.MINMAX_AGENT_PLAYER002){
            if(currentTurn==C.WHITE_MOVE || currentTurn==C.WHITE_BLOC){
                allMoveableChess = chessMat.getAllTypeChessPositions(C.WHITE_QUEEN);
                allOppMoveableChess = chessMat.getAllTypeChessPositions(C.BLACK_QUEEN);
            }else{
                allMoveableChess = chessMat.getAllTypeChessPositions(C.BLACK_QUEEN);
                allOppMoveableChess = chessMat.getAllTypeChessPositions(C.WHITE_QUEEN);
            }
            for(Position chess : allMoveableChess){
                moveablePositions = chessMat.getAllMoveablePositionsForChess(chess);
            }
            self_f_value = moveablePositions.size();
            for(Position chess : allOppMoveableChess){
                OppMoveablePositions = chessMat.getAllMoveablePositionsForChess(chess);
            }
            opp_f_value = OppMoveablePositions.size();
            return self_f_value-opp_f_value;
        }
//MINMAX_AGENT_PLAYER003
        if(agentPlaterType==C.MINMAX_AGENT_PLAYER003){
            if(currentTurn==C.WHITE_MOVE || currentTurn==C.WHITE_BLOC){
                allOppMoveableChess = chessMat.getAllTypeChessPositions(C.BLACK_QUEEN);
            }else{
                allOppMoveableChess = chessMat.getAllTypeChessPositions(C.WHITE_QUEEN);
            }
            for(Position chess : allOppMoveableChess){
                OppMoveablePositions = chessMat.getAllMoveablePositionsForChess(chess);
            }
            return -OppMoveablePositions.size();
        }
//MINMAX_AGENT_PLAYER004
        if(agentPlaterType==C.MINMAX_AGENT_PLAYER004){
            self_f_value=0;
            if(currentTurn==C.WHITE_MOVE || currentTurn==C.WHITE_BLOC){
                moveableDirection = chessMat.getAllMoveableDirectionForPlayer(C.WHITE_MOVE);
            }else{
                moveableDirection = chessMat.getAllMoveableDirectionForPlayer(C.BLACK_MOVE);
            }
            for(int i=0; i<moveableDirection.length; i++){
                for(int j=0; j<moveableDirection[i].length; j++){
                    if(moveableDirection[i][j]==C.MOVEABLE){
                        self_f_value++;
                    }else{
                        self_f_value--;
                    }
                }
            }
            return self_f_value;
        }
//MINMAX_AGENT_PLAYER005
        if(agentPlaterType==C.MINMAX_AGENT_PLAYER005){
            self_f_value=0;
            opp_f_value=0;
            if(currentTurn==C.WHITE_MOVE || currentTurn==C.WHITE_BLOC){
                moveableDirection = chessMat.getAllMoveableDirectionForPlayer(C.WHITE_MOVE);
                oppMoveableDirection = chessMat.getAllMoveableDirectionForPlayer(C.BLACK_MOVE);
            }else{
                moveableDirection = chessMat.getAllMoveableDirectionForPlayer(C.BLACK_MOVE);
                oppMoveableDirection = chessMat.getAllMoveableDirectionForPlayer(C.WHITE_MOVE);
            }
            for(int i=0; i<moveableDirection.length; i++){
                for(int j=0; j<moveableDirection[i].length; j++){
                    if(moveableDirection[i][j]==C.MOVEABLE){
                        self_f_value++;
                    }else{
                        self_f_value--;
                    }
                }
            }
            for(int i=0; i<oppMoveableDirection.length; i++){
                for(int j=0; j<oppMoveableDirection[i].length; j++){
                    if(oppMoveableDirection[i][j]==C.MOVEABLE){
                        opp_f_value++;
                    }else{
                        opp_f_value--;
                    }
                }
            }
            return self_f_value-opp_f_value;
        }
//MINMAX_AGENT_PLAYER006        
        if(agentPlaterType==C.MINMAX_AGENT_PLAYER006){
            opp_f_value=0;
            if(currentTurn==C.WHITE_MOVE || currentTurn==C.WHITE_BLOC){
                oppMoveableDirection = chessMat.getAllMoveableDirectionForPlayer(C.BLACK_MOVE);
            }else{
                oppMoveableDirection = chessMat.getAllMoveableDirectionForPlayer(C.WHITE_MOVE);
            }
            for(int i=0; i<oppMoveableDirection.length; i++){
                for(int j=0; j<oppMoveableDirection[i].length; j++){
                    if(oppMoveableDirection[i][j]==C.MOVEABLE){
                        opp_f_value++;
                    }else{
                        opp_f_value--;
                    }
                }
            }
            return 0-opp_f_value;
        }
//MINMAX_AGENT_PLAYER007
        if(agentPlaterType==C.MINMAX_AGENT_PLAYER007){
            self_f_value=0;
            boolean notDirectionAvaliable;
            if(currentTurn==C.WHITE_MOVE || currentTurn==C.WHITE_BLOC){
                moveableDirection = chessMat.getAllMoveableDirectionForPlayer(C.WHITE_MOVE);
            }else{
                moveableDirection = chessMat.getAllMoveableDirectionForPlayer(C.BLACK_MOVE);
            }
            for(int i=0; i<moveableDirection.length; i++){
                notDirectionAvaliable=true;
                for(int j=0; j<moveableDirection[i].length; j++){
                    if(moveableDirection[i][j]==C.MOVEABLE){
                        notDirectionAvaliable=false;
                        self_f_value++;
                    }
                }
                if(notDirectionAvaliable){
                    self_f_value=self_f_value-10;
                }
            }
            return self_f_value;
        }
//MINMAX_AGENT_PLAYER008
        if(agentPlaterType==C.MINMAX_AGENT_PLAYER008){
            self_f_value=0;
            opp_f_value=0;
            boolean notDirectionAvaliable;
            if(currentTurn==C.WHITE_MOVE || currentTurn==C.WHITE_BLOC){
                moveableDirection = chessMat.getAllMoveableDirectionForPlayer(C.WHITE_MOVE);
                oppMoveableDirection = chessMat.getAllMoveableDirectionForPlayer(C.BLACK_MOVE);
            }else{
                moveableDirection = chessMat.getAllMoveableDirectionForPlayer(C.BLACK_MOVE);
                oppMoveableDirection = chessMat.getAllMoveableDirectionForPlayer(C.WHITE_MOVE);
            }
            for(int i=0; i<moveableDirection.length; i++){
                notDirectionAvaliable=true;
                for(int j=0; j<moveableDirection[i].length; j++){
                    if(moveableDirection[i][j]==C.MOVEABLE){
                        notDirectionAvaliable=false;
                        self_f_value++;
                    }
                }
                if(notDirectionAvaliable){
                    self_f_value=self_f_value-10;
                }
            }
            for(int i=0; i<oppMoveableDirection.length; i++){
                notDirectionAvaliable=true;
                for(int j=0; j<oppMoveableDirection[i].length; j++){
                    if(oppMoveableDirection[i][j]==C.MOVEABLE){
                        notDirectionAvaliable=false;
                        opp_f_value++;
                    }
                }
                if(notDirectionAvaliable){
                    opp_f_value=opp_f_value-10;
                }
            }
            return self_f_value-opp_f_value;
        }
//MINMAX_AGENT_PLAYER009        
        if(agentPlaterType==C.MINMAX_AGENT_PLAYER009){
            opp_f_value=0;
            boolean notDirectionAvaliable;
            if(currentTurn==C.WHITE_MOVE || currentTurn==C.WHITE_BLOC){
                oppMoveableDirection = chessMat.getAllMoveableDirectionForPlayer(C.BLACK_MOVE);
            }else{
                oppMoveableDirection = chessMat.getAllMoveableDirectionForPlayer(C.WHITE_MOVE);
            }
            for(int i=0; i<oppMoveableDirection.length; i++){
                notDirectionAvaliable=true;
                for(int j=0; j<oppMoveableDirection[i].length; j++){
                    if(oppMoveableDirection[i][j]==C.MOVEABLE){
                        notDirectionAvaliable=false;
                        opp_f_value++;
                    }
                    if(notDirectionAvaliable){
                        opp_f_value=opp_f_value-10;
                    }
                }
            }
            return 0-opp_f_value;
        }    
//MINMAX_AGENT_PLAYER010        
        if(agentPlaterType==C.MINMAX_AGENT_PLAYER010){
            opp_f_value=0;
            if(currentTurn==C.WHITE_MOVE || currentTurn==C.WHITE_BLOC){
                moveableDirection = chessMat.getAllMoveableDirectionForPlayer(C.WHITE_MOVE);
                oppMoveableDirection = chessMat.getAllMoveableDirectionForPlayer(C.BLACK_MOVE);
            }else{
                moveableDirection = chessMat.getAllMoveableDirectionForPlayer(C.BLACK_MOVE);
                oppMoveableDirection = chessMat.getAllMoveableDirectionForPlayer(C.WHITE_MOVE);
            }
            if(chessMat.isEnvironmentClosed(currentTurn)){
                for(int i=0; i<oppMoveableDirection.length; i++){
                    for(int j=0; j<oppMoveableDirection[i].length; j++){
                        if(oppMoveableDirection[i][j]==C.MOVEABLE){opp_f_value++;}else{}
                    }
                }
                return 0-opp_f_value;
            }else{
                for(Position chess : allMoveableChess){
                    moveablePositions = chessMat.getAllMoveablePositionsForChess(chess);
                }
                return moveablePositions.size();
            }
        }  
//MINMAX_AGENT_PLAYER011        
        if(agentPlaterType==C.MINMAX_AGENT_PLAYER011){
            opp_f_value=0;
            if(currentTurn==C.WHITE_MOVE || currentTurn==C.WHITE_BLOC){
                moveableDirection = chessMat.getAllMoveableDirectionForPlayer(C.WHITE_MOVE);
                oppMoveableDirection = chessMat.getAllMoveableDirectionForPlayer(C.BLACK_MOVE);
            }else{
                moveableDirection = chessMat.getAllMoveableDirectionForPlayer(C.BLACK_MOVE);
                oppMoveableDirection = chessMat.getAllMoveableDirectionForPlayer(C.WHITE_MOVE);
            }
            if(chessMat.isEnvironmentClosed(currentTurn)){
                for(Position chess : allMoveableChess){
                    moveablePositions = chessMat.getAllMoveablePositionsForChess(chess);
                }
                self_f_value = moveablePositions.size();
                for(Position chess : allOppMoveableChess){
                    OppMoveablePositions = chessMat.getAllMoveablePositionsForChess(chess);
                }
                return self_f_value-opp_f_value;
            }else{
                for(int i=0; i<oppMoveableDirection.length; i++){
                    for(int j=0; j<oppMoveableDirection[i].length; j++){
                        if(oppMoveableDirection[i][j]==C.MOVEABLE){
                            opp_f_value++;
                        }else{
                            opp_f_value--;
                        }
                    }
                }
                return 0-opp_f_value;
            }
        }       
        return C.DEFAULT_F_VALUE;
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
