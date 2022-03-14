import java.util.ArrayList;
import java.util.Random;

public class SearchAgentX {
    private ChessMat chessMat;
    private int startTurn;

    public SearchAgentX(ChessMat chessMat,int startTurn,int unknow){
        this.chessMat=new ChessMat(chessMat);
        this.startTurn=startTurn;
    }

    public int[] getAction(){
        State root = new State(chessMat,-1,null,null);
        int OPP_rootTerminal1_4=OPPTerminalCheck1_4(root);
        int SEL_rootTerminal1_4=SELTerminalCheck1_4(root);
        ArrayList<State> ThreateningStates=new ArrayList<State>();

        //[CHECKING THREATS] BY CHECKING SEL 1/4 TERMINAL
        //ArrayList<State> OPP_PossibleStates_depth1 = root.getAllPossibleStates(startTurn);
        //for(State opp_state_depth1:OPP_PossibleStates_depth1){
        //    if(SELTerminalCheck1_4(opp_state_depth1)>SEL_rootTerminal1_4){ThreateningStates.add(opp_state_depth1);}
        //}

        ArrayList<State> SEL_PossibleStates_depth1 = root.getAllPossibleStates(startTurn);

        for(State sel_state_depth1:SEL_PossibleStates_depth1){
            //[RETURN TERMINAL OPP ACTION]OPP 1/4 TERMINAL
            if(OPPTerminalCheck1_4(sel_state_depth1)>OPP_rootTerminal1_4){System.out.println("terminal");return sel_state_depth1.action;}
        }

            //[RETURN CLEANING THREATS ACTION]THREATS HANDING
            //if(!ThreateningStates.isEmpty()){
            //    for(State s:ThreateningStates){
            //        if(SELTerminalCheck1_4(State.applyAction(sel_state_depth1, s.action))<ThreateningStates.size()){return sel_state_depth1.action;}
            //    }
            //}
        for(State sel_state_depth1:SEL_PossibleStates_depth1){
            ArrayList<State> SEL_PossibleStates_depth2 = sel_state_depth1.getAllPossibleStates(startTurn);
            for(State sel_state_depth2:SEL_PossibleStates_depth2){
                //OPP 1/8 TERMINAL
                if(OPPTerminalCheck1_4(sel_state_depth2)>OPP_rootTerminal1_4 && !(OPPTerminalCheck1_4(sel_state_depth2)<OPP_rootTerminal1_4)){return sel_state_depth1.action;}
            }
        }
        return getRandomAction();
    }
    public int[] getRandomAction(){
        Random rand = new Random();
        State root = new State(chessMat,-1,null,null);
        ArrayList<State> PossibleStates = root.getAllPossibleStates(startTurn);
        int random_index=rand.nextInt(PossibleStates.size());
        return PossibleStates.get(random_index).action;
    }

    private int OPPTerminalCheck1_4(State state){
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
    private int SELTerminalCheck1_4(State state){
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

}
