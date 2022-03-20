import java.util.ArrayList;

public class MinMaxSearchAgent {
    private ChessMat chessMat;
    private int startTurn;
    private int limit;
    private int agentPlaterType;

    public static final int SEARCH_DEPTH_LIMIT = 2;
    public static final int LIMITED_SEARCH_DEPTH_LIMIT = 12;
    public static final int LIMITED_SEARCH_STATE_LIMIT = 100000;
    public static final int CONSTANT_STATE_LIMIT = 2;

    public MinMaxSearchAgent(ChessMat chessMat,int startTurn,int agentPlaterType){
        this.chessMat=new ChessMat(chessMat);
        this.startTurn=startTurn;
        this.limit=LIMITED_SEARCH_STATE_LIMIT;
        this.agentPlaterType=agentPlaterType;
    }

    public int[] getAction(){
        State root = new State(chessMat,-1,null,null);
        State state = root;
        //State state=MinMaxSearch(root, startTurn, startTurn, SEARCH_DEPTH_LIMIT);
        if(agentPlaterType==C.MINMAX_AGENT_PLAYER013){
            state=AlphaBetaSearch(root, startTurn, startTurn, 1, C.DEFAULT_MAX_F_VALUE, C.DEFAULT_MIN_F_VALUE);
        }else{
            state=AlphaBetaSearch(root, startTurn, startTurn, SEARCH_DEPTH_LIMIT, C.DEFAULT_MAX_F_VALUE, C.DEFAULT_MIN_F_VALUE);
        }

        //State state=StateLimitedSearch(root, startTurn, startTurn, C.DEFAULT_MAX_F_VALUE, C.DEFAULT_MIN_F_VALUE, SEARCH_STATE_LIMIT);
        //State state=DpethStateLimitedSearch(root, startTurn, startTurn, C.DEFAULT_MAX_F_VALUE, C.DEFAULT_MIN_F_VALUE, LIMITED_SEARCH_STATE_LIMIT, LIMITED_SEARCH_DEPTH_LIMIT);
        //State state=constantStateSearch(root, startTurn, startTurn, SEARCH_DEPTH_LIMIT, C.DEFAULT_MAX_F_VALUE, C.DEFAULT_MIN_F_VALUE, CONSTANT_STATE_LIMIT);
        //State state=SubjectiveSearch(root, startTurn, startTurn, SEARCH_DEPTH_LIMIT);
        while(state.parentState.action!=null){
            state=state.parentState;
        }
        return state.action;
    }

    //MinMaxSearch
    private State MinMaxSearch(State state, int startTurn,int currentTurn, int depth){
        if(inFriendlyTurn(startTurn, currentTurn)){
            State subState;
            State max_subState=null;
            int max_f_value=C.DEFAULT_MAX_F_VALUE;
            if(depth!=0 && !state.chessMat.isTerminal(currentTurn)){
                ArrayList<State> PossibleStates = state.getAllPossibleStates(currentTurn);
                for(State s : PossibleStates){
                    subState=MinMaxSearch(s, startTurn, nextTurn(currentTurn), depth-1);
                    if(subState.f_value>max_f_value){
                        max_f_value = subState.f_value;
                        max_subState = subState;
                    }
                }
                return max_subState;
            }else{
                state.f_value = calculate_F_value(state.chessMat, currentTurn);
                return state;
            }
        }else{
            State subState;
            State min_subState=null;
            int min_f_value=C.DEFAULT_MIN_F_VALUE;
            if(depth!=0 && !state.chessMat.isTerminal(currentTurn)){
                
                ArrayList<State> PossibleStates =  state.getAllPossibleStates(currentTurn);
                for(State s : PossibleStates){
                    subState=MinMaxSearch(s, startTurn, nextTurn(currentTurn), depth-1);
                    if(subState.f_value<min_f_value){
                        min_f_value = subState.f_value;
                        min_subState = subState;
                    }
                }
                return min_subState;
            }else{
                state.f_value = calculate_F_value(state.chessMat, currentTurn);
                return state;
            }
        }
    }
    //AlphaBetaSearch
    private State AlphaBetaSearch(State state, int startTurn,int currentTurn, int depth, int alpha, int beta){
        if(inFriendlyTurn(startTurn, currentTurn)){
            State subState;
            State max_subState=null;
            int max_f_value=C.DEFAULT_MAX_F_VALUE;
            if(depth!=0 && !state.chessMat.isTerminal(currentTurn)){
                ArrayList<State> PossibleStates = state.getAllPossibleStates(currentTurn);
                for(State s : PossibleStates){
                    subState=AlphaBetaSearch(s, startTurn, nextTurn(currentTurn), depth-1, alpha, beta);
                    if(subState.f_value>max_f_value){
                        max_f_value = subState.f_value;
                        max_subState = subState;
                    }
                    alpha=Math.max(alpha,subState.f_value);
                    if(beta<=alpha){break;}
                }
                return max_subState;
            }else{
                state.f_value = calculate_F_value(state.chessMat, currentTurn);
                return state;
            }
        }else{
            State subState;
            State min_subState=null;
            int min_f_value=C.DEFAULT_MIN_F_VALUE;
            if(depth!=0 && !state.chessMat.isTerminal(currentTurn)){
                
                ArrayList<State> PossibleStates =  state.getAllPossibleStates(currentTurn);
                for(State s : PossibleStates){
                    subState=AlphaBetaSearch(s, startTurn, nextTurn(currentTurn), depth-1, alpha, beta);
                    if(subState.f_value<min_f_value){
                        min_f_value = subState.f_value;
                        min_subState = subState;
                    }
                    beta=Math.min(beta,subState.f_value);
                    if(beta<=alpha){break;}
                }
                return min_subState;
            }else{
                state.f_value = calculate_F_value(state.chessMat, currentTurn);
                return state;
            }
        }
    }
    //StateLimitedSearch ONLY FOR TESTING
    private State StateLimitedSearch(State root,int startTurn,int currentTurn,int alpha,int beta,int search_limit){
        limit=search_limit;
        State state=StateLimitedSearch(root, startTurn, startTurn, C.DEFAULT_MAX_F_VALUE, C.DEFAULT_MIN_F_VALUE);
        return state;
    }
    private State StateLimitedSearch(State state,int startTurn,int currentTurn,int alpha,int beta){
        if(inFriendlyTurn(startTurn, currentTurn)){
            State subState;
            int max_f_value=C.DEFAULT_MAX_F_VALUE;
            State max_subState=null;
            if(limit>0 && !state.chessMat.isTerminal(currentTurn)){
                ArrayList<State> PossibleStates = state.getAllPossibleStates(currentTurn,limit);
                limit=limit-PossibleStates.size();
                for(State s : PossibleStates){
                    subState=StateLimitedSearch(s, startTurn, nextTurn(currentTurn), alpha, beta);
                    if(subState.f_value>max_f_value){
                        max_f_value = subState.f_value;
                        max_subState = subState;
                    }
                    alpha=Math.max(alpha,subState.f_value);
                    if(beta<=alpha){break;}
                    if(limit<=0){break;}
                }
                return max_subState;
            }else{
                state.f_value = calculate_F_value(state.chessMat, currentTurn);
                return state;
            }
        }else{
            State subState;
            int min_f_value=C.DEFAULT_MIN_F_VALUE;
            State min_subState=null;
            if(limit>0 && !state.chessMat.isTerminal(currentTurn)){
                ArrayList<State> PossibleStates = state.getAllPossibleStates(currentTurn,limit);
                limit=limit-PossibleStates.size();
                for(State s : PossibleStates){
                    subState=StateLimitedSearch(s, startTurn, nextTurn(currentTurn), alpha, beta);
                    if(subState.f_value<min_f_value){
                        min_f_value = subState.f_value;
                        min_subState = subState;
                    }
                    beta=Math.min(beta,subState.f_value);
                    if(beta<=alpha){break;}
                    if(limit<=0){break;}
                }
                return min_subState;
            }else{
                state.f_value = calculate_F_value(state.chessMat, currentTurn);
                return state;
            }
        }
    }
    //DepthStateLimitedSearch
    private State DpethStateLimitedSearch(State state,int startTurn,int currentTurn,int alpha,int beta,int limit,int depth){
        if(inFriendlyTurn(startTurn, currentTurn)){
            State subState;
            int max_f_value=C.DEFAULT_MAX_F_VALUE;
            State max_subState=null;
            if(depth==0 || limit<=0 || state.chessMat.isTerminal(currentTurn)){
                state.f_value = calculate_F_value(state.chessMat, currentTurn);
                return state;
            }else{
                ArrayList<State> PossibleStates;
                if(depth!=0){PossibleStates = state.getAllPossibleStates(currentTurn);}else{PossibleStates = state.getAllPossibleStates(currentTurn,limit);}
                limit=limit-PossibleStates.size();
                for(State s : PossibleStates){
                    subState=DpethStateLimitedSearch(s, startTurn, nextTurn(currentTurn), alpha, beta, limit/PossibleStates.size(), depth-1);
                    if(subState.f_value>max_f_value){
                        max_f_value = subState.f_value;
                        max_subState = subState;
                    }
                    alpha=Math.max(alpha,subState.f_value);
                    if(beta<=alpha){break;}
                }
                return max_subState;
            }
        }else{
            State subState;
            int min_f_value=C.DEFAULT_MIN_F_VALUE;
            State min_subState=null;
            if(depth==0 || limit<=0 || state.chessMat.isTerminal(currentTurn)){
                state.f_value = calculate_F_value(state.chessMat, currentTurn);
                return state;
            }else{
                ArrayList<State> PossibleStates;
                if(depth!=0){PossibleStates = state.getAllPossibleStates(currentTurn);}else{PossibleStates = state.getAllPossibleStates(currentTurn,limit);}
                limit=limit-PossibleStates.size();
                for(State s : PossibleStates){
                    subState=DpethStateLimitedSearch(s, startTurn, nextTurn(currentTurn), alpha, beta, limit/PossibleStates.size(), depth-1);
                    if(subState.f_value<min_f_value){
                        min_f_value = subState.f_value;
                        min_subState = subState;
                    }
                    beta=Math.min(beta,subState.f_value);
                    if(beta<=alpha){break;}
                }
                return min_subState;
            }
        }
    }
    //ConstantStateSearch
    private State constantStateSearch(State state, int startTurn,int currentTurn, int depth, int alpha, int beta, int constant){
        if(inFriendlyTurn(startTurn, currentTurn)){
            State subState;
            State max_subState=null;
            int max_f_value=C.DEFAULT_MAX_F_VALUE;
            if(depth!=0 && !state.chessMat.isTerminal(currentTurn)){
                ArrayList<State> PossibleStates = state.getAllPossibleStates(currentTurn);
                PossibleStates = cutArrayByConstantlength(currentTurn,constant,PossibleStates);
                for(State s : PossibleStates){
                    subState=AlphaBetaSearch(s, startTurn, nextTurn(currentTurn), depth-1, alpha, beta);
                    if(subState.f_value>max_f_value){
                        max_f_value = subState.f_value;
                        max_subState = subState;
                    }
                    alpha=Math.max(alpha,subState.f_value);
                    if(beta<=alpha){break;}
                }
                return max_subState;
            }else{
                state.f_value = calculate_F_value(state.chessMat, currentTurn);
                return state;
            }
        }else{
            State subState;
            State min_subState=null;
            int min_f_value=C.DEFAULT_MIN_F_VALUE;
            if(depth!=0 && !state.chessMat.isTerminal(currentTurn)){
                ArrayList<State> PossibleStates =  state.getAllPossibleStates(currentTurn);
                PossibleStates = cutArrayByConstantlength(currentTurn,constant,PossibleStates);
                for(State s : PossibleStates){
                    subState=AlphaBetaSearch(s, startTurn, nextTurn(currentTurn), depth-1, alpha, beta);
                    if(subState.f_value<min_f_value){
                        min_f_value = subState.f_value;
                        min_subState = subState;
                    }
                    beta=Math.min(beta,subState.f_value);
                    if(beta<=alpha){break;}
                }
                return min_subState;
            }else{
                state.f_value = calculate_F_value(state.chessMat, currentTurn);
                return state;
            }
        }
    }
    private ArrayList<State> cutArrayByConstantlength(int currentTurn, int constant, ArrayList<State> PossibleStates){
        ArrayList<State> returnStates = new ArrayList<State>();
        for(int i=0; i<PossibleStates.size(); i++){
            PossibleStates.get(i).f_value=calculate_F_value(PossibleStates.get(i).chessMat,currentTurn);
            if(returnStates.size()<constant){
                returnStates.add(PossibleStates.get(i));
            }else{
                for(int j=0; j<returnStates.size(); j++){
                    if(inFriendlyTurn(startTurn, currentTurn)){
                        if(PossibleStates.get(i).f_value>returnStates.get(j).f_value){
                            returnStates.remove(j);
                            returnStates.add(j,PossibleStates.get(i));
                            break;
                        }
                    }else{
                        if(PossibleStates.get(i).f_value<returnStates.get(j).f_value){
                            returnStates.remove(j);
                            returnStates.add(j,PossibleStates.get(i));
                            break;
                        }
                    }
                }
            }
        }

        return returnStates;
    }
    //SubjectiveSearch
    private State SubjectiveSearch(State state, int startTurn,int currentTurn, int depth){
        if(inFriendlyTurn(startTurn, currentTurn)){
            State max_subState=null;
            int max_f_value=C.DEFAULT_MAX_F_VALUE;
            if(depth!=0 && !state.chessMat.isTerminal(currentTurn)){
                ArrayList<State> PossibleStates = state.getAllPossibleStates(currentTurn);
                for(State s : PossibleStates){
                    s.f_value=calculate_F_value(state.chessMat, currentTurn);
                    if(s.f_value>max_f_value){
                        max_f_value = s.f_value;
                        max_subState = s;
                    }
                }
                return SubjectiveSearch(max_subState, startTurn, nextTurn(currentTurn), depth-1);
            }else{
                state.f_value = calculate_F_value(state.chessMat, currentTurn);
                return state;
            }
        }else{
            if(depth!=0 && !state.chessMat.isTerminal(currentTurn)){
                ArrayList<State> PossibleStates = state.getAllPossibleStates(currentTurn);
                for(State s : PossibleStates){
                    return SubjectiveSearch(s, startTurn, nextTurn(currentTurn), depth-1);
                }
            }else{
                state.f_value = calculate_F_value(state.chessMat, currentTurn);
                return state;
            }
        }
        return null;
    }

    private int calculate_F_value(ChessMat chessMat, int currentTurn){
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
//MINMAX_AGENT_PLAYER013      
        if(agentPlaterType==C.MINMAX_AGENT_PLAYER013){
            ArrayList<Position> selchesses=new ArrayList<Position>();
            ArrayList<Position> oppchesses=new ArrayList<Position>();
            if(currentTurn==C.WHITE_MOVE || currentTurn==C.WHITE_BLOC){
                selchesses = chessMat.getAllTypeChessPositions(C.WHITE_QUEEN);
                oppchesses = chessMat.getAllTypeChessPositions(C.BLACK_QUEEN);
            }else{
                selchesses = chessMat.getAllTypeChessPositions(C.BLACK_QUEEN);
                oppchesses = chessMat.getAllTypeChessPositions(C.WHITE_QUEEN);
            }
            double T=-999;
            for(Position c:selchesses){T=T+chessMat.getTerritoryScore(c);}
            for(Position c:oppchesses){T=T-chessMat.getTerritoryScore(c);}
            return (int)T;
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
