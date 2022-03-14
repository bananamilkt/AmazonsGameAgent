import java.util.ArrayList;

public class ChessMat {
    private int[][] chess_mat;
    public int size_x;
    public int size_y;

    //CONSTRUTORS
    public ChessMat(int x, int y){
        this.size_x=x;
        this.size_y=y;
        chess_mat = new int[size_x][size_y];
        initialChessMat();
    }
    public ChessMat(ChessMat copyChessMat){
        this.size_x= copyChessMat.size_x;
        this.size_y= copyChessMat.size_y;
        chess_mat = new int[size_x][size_y];
        for(int x=0;x<size_x;x++){
            for(int y=0;y<size_y;y++){
                chess_mat[x][y]=copyChessMat.getChess(x,y);
            }
        }
    }
    private void initialChessMat(){
        //EMPTY THE CHESS_MAT
        for(int x=0;x<size_x;x++){
            for(int y=0;y<size_y;y++){
                chess_mat[x][y]=C.EMPTY_BLOCK;
            }
        }
    }
    public void initialAmazonChessMat(){
        initialChessMat();
        chess_mat[0][3] = C.BLACK_QUEEN;
        chess_mat[3][0] = C.BLACK_QUEEN;
        chess_mat[6][0] = C.BLACK_QUEEN;
        chess_mat[9][3] = C.BLACK_QUEEN;

        chess_mat[0][6] = C.WHITE_QUEEN;
        chess_mat[3][9] = C.WHITE_QUEEN;
        chess_mat[6][9] = C.WHITE_QUEEN;
        chess_mat[9][6] = C.WHITE_QUEEN;
    }
    public void initialSimpleAmazonChessMat(){
        initialChessMat();
        chess_mat[0][0] = C.BLACK_QUEEN;
        chess_mat[5][0] = C.BLACK_QUEEN;

        chess_mat[0][5] = C.WHITE_QUEEN;
        chess_mat[5][5] = C.WHITE_QUEEN;
    }
    //GETTERS
    public int getChess(int x, int y){
        return chess_mat[x][y];
    }
    public int getChess(Position position){
        return chess_mat[position.x][position.y];
    }
    public ArrayList<Position> getAllMoveableChessPositions(){
        ArrayList<Position> chessPositions = new ArrayList<Position>();
        for(int x=0;x<size_x;x++){
            for(int y=0;y<size_y;y++){
                if(chess_mat[x][y]>C.MOVEABLE_CHESS_LOWER_LIMIT && chess_mat[x][y]<C.MOVEABLE_CHESS_UPPER_LIMIT){
                    chessPositions.add(new Position(x,y));
                }
            }
        }
        return chessPositions;
    }
    public ArrayList<Position> getAllTypeChessPositions(int chessType){
        ArrayList<Position> chessPositions = new ArrayList<Position>();
        for(int x=0;x<size_x;x++){
            for(int y=0;y<size_y;y++){
                if(chess_mat[x][y]==chessType){
                    chessPositions.add(new Position(x,y));
                }
            }
        }
        return chessPositions;
    }
    public ArrayList<Position> getAllMoveablePositionsForChess(Position position){
        ArrayList<Position> moveablePositions = new ArrayList<Position>();
        //QUEEN
        if(chess_mat[position.x][position.y]==C.BLACK_QUEEN || chess_mat[position.x][position.y]==C.WHITE_QUEEN){
            //H check
            for(int x=position.x;x>=0;x--){
                if(x!=position.x){
                    if(chess_mat[x][position.y]==C.EMPTY_BLOCK){
                        moveablePositions.add(new Position(x,position.y));
                    }else{
                        break;
                    }
                }
            }
            for(int x=position.x;x<size_x;x++){
                if(x!=position.x){
                    if(chess_mat[x][position.y]==C.EMPTY_BLOCK){
                        moveablePositions.add(new Position(x,position.y));
                    }else{
                        break;
                    }
                }
            }
            //V check
            for(int y=position.y;y>=0;y--){
                if(y!=position.y){
                    if(chess_mat[position.x][y]==C.EMPTY_BLOCK){
                        moveablePositions.add(new Position(position.x,y));
                    }else{
                        break;
                    } 
                }
            }
            for(int y=position.y;y<size_y;y++){
                if(y!=position.y){
                    if(chess_mat[position.x][y]==C.EMPTY_BLOCK){
                        moveablePositions.add(new Position(position.x,y));
                    }else{
                        break;
                    }
                }
            }
            //D check
            int x = 0;
            int y = 0;
            x = position.x;
            y = position.y;
            while(x<size_x && y<size_y){
                if (y != position.y && x != position.x) {
                    if (chess_mat[x][y] == C.EMPTY_BLOCK) {
                        moveablePositions.add(new Position(x, y));
                    }else{
                        break;
                    }
                }
                x++;
                y++;
            }
            x = position.x;
            y = position.y;
            while(x>=0 && y>=0){
                if (y != position.y && x != position.x) {
                    if (chess_mat[x][y] == C.EMPTY_BLOCK) {
                        moveablePositions.add(new Position(x, y));
                    }else{
                        break;
                    }
                }
                x--;
                y--;
            }
            x = position.x;
            y = position.y;
            while(x<size_x && y>=0){
                if (y != position.y && x != position.x) {
                    if (chess_mat[x][y] == C.EMPTY_BLOCK) {
                        moveablePositions.add(new Position(x, y));
                    }else{
                        break;
                    }
                }
                x++;
                y--;
            }
            x = position.x;
            y = position.y;
            while(x>=0 && y<size_y){
                if (y != position.y && x != position.x) {
                    if (chess_mat[x][y] == C.EMPTY_BLOCK) {
                        moveablePositions.add(new Position(x, y));
                    }else{
                        break;
                    }
                }
                x--;
                y++;
            }
        }
        return moveablePositions;
    }
    public ArrayList<Position> getAllMoveablePositionsForSide(int gameStage){
        ArrayList<Position> allSelfChess = new ArrayList<Position>();
        ArrayList<Position> allSelfMoveablePosition = new ArrayList<Position>();
        if(gameStage==C.WHITE_MOVE || gameStage==C.WHITE_BLOC){
            allSelfChess = getAllTypeChessPositions(C.WHITE_QUEEN);
        }else{
            allSelfChess = getAllTypeChessPositions(C.BLACK_QUEEN);
        }
        for(Position chess:allSelfChess){
            allSelfMoveablePosition.addAll(getAllMoveablePositionsForChess(chess));
        }
        return allSelfMoveablePosition;
    }
    public ArrayList<ChessMat> getAllPossibleStates(int gameStage){
        ArrayList<ChessMat> possibleStates = new ArrayList<ChessMat>();
        ArrayList<Position> allMoveableChess = new ArrayList<Position>();
        if(gameStage==C.WHITE_MOVE || gameStage==C.WHITE_BLOC){
            allMoveableChess = getAllTypeChessPositions(C.WHITE_QUEEN);
        }else{
            allMoveableChess = getAllTypeChessPositions(C.BLACK_QUEEN);
        }
        ArrayList<Position> moveablePositions;
        for(Position chess : allMoveableChess){
            moveablePositions = getAllMoveablePositionsForChess(chess);
            for(Position moveablePosition : moveablePositions){
                ChessMat pasteChessMat = new ChessMat(this);
                if(gameStage==C.WHITE_BLOC || gameStage==C.BLACK_BLOC){
                    pasteChessMat.overWrite(C.AMAZON_BLOCK,moveablePosition);
                }else{
                    pasteChessMat.overWrite(getChess(chess),moveablePosition);
                    pasteChessMat.overWrite(C.EMPTY_BLOCK,chess);
                }
                possibleStates.add(pasteChessMat);
            }
        }
        return possibleStates;
    }
    public int[] getMoveableDirectionForChess(Position position){
        int[] moveableDirection = new int[8];
        for(int i=0; i<moveableDirection.length; i++){
            moveableDirection[i]=C.UNMOVEABLE;
        }
        //QUEEN
        if(chess_mat[position.x][position.y]==C.BLACK_QUEEN || chess_mat[position.x][position.y]==C.WHITE_QUEEN){
            //H check
            for(int x=position.x;x>=0;x--){
                if(x!=position.x){
                    if(chess_mat[x][position.y]==C.EMPTY_BLOCK){
                        moveableDirection[C.LEFT]=C.MOVEABLE;
                        break;
                    }else{
                        break;
                    }
                }
            }
            for(int x=position.x;x<size_x;x++){
                if(x!=position.x){
                    if(chess_mat[x][position.y]==C.EMPTY_BLOCK){
                        moveableDirection[C.RIGHT]=C.MOVEABLE;
                        break;
                    }else{
                        break;
                    }
                }
            }
            //V check
            for(int y=position.y;y>=0;y--){
                if(y!=position.y){
                    if(chess_mat[position.x][y]==C.EMPTY_BLOCK){
                        moveableDirection[C.DOWN]=C.MOVEABLE;
                        break;
                    }else{
                        break;
                    } 
                }
            }
            for(int y=position.y;y<size_y;y++){
                if(y!=position.y){
                    if(chess_mat[position.x][y]==C.EMPTY_BLOCK){
                        moveableDirection[C.UP]=C.MOVEABLE;
                        break;
                    }else{
                        break;
                    }
                }
            }
            //D check
            int x = 0;
            int y = 0;
            x = position.x;
            y = position.y;
            while(x<size_x && y<size_y){
                if (y != position.y && x != position.x) {
                    if (chess_mat[x][y] == C.EMPTY_BLOCK) {
                        moveableDirection[C.DOWN_RIGHT]=C.MOVEABLE;
                        break;
                    }else{
                        break;
                    }
                }
                x++;
                y++;
            }
            x = position.x;
            y = position.y;
            while(x>=0 && y>=0){
                if (y != position.y && x != position.x) {
                    if (chess_mat[x][y] == C.EMPTY_BLOCK) {
                        moveableDirection[C.UP_LEFT]=C.MOVEABLE;
                        break;
                    }else{
                        break;
                    }
                }
                x--;
                y--;
            }
            x = position.x;
            y = position.y;
            while(x<size_x && y>=0){
                if (y != position.y && x != position.x) {
                    if (chess_mat[x][y] == C.EMPTY_BLOCK) {
                        moveableDirection[C.UP_RIGHT]=C.MOVEABLE;
                        break;
                    }else{
                        break;
                    }
                }
                x++;
                y--;
            }
            x = position.x;
            y = position.y;
            while(x>=0 && y<size_y){
                if (y != position.y && x != position.x) {
                    if (chess_mat[x][y] == C.EMPTY_BLOCK) {
                        moveableDirection[C.DOWN_LEFT]=C.MOVEABLE;
                        break;
                    }else{
                        break;
                    }
                }
                x--;
                y++;
            }
        }
        return moveableDirection;
    }
    public int[][] getAllMoveableDirectionForPlayer(int gameStage){
        int[][] allMoveableDirections = new int[4][8];
        int counter = 0;
        ArrayList<Position> allMoveableChess = new ArrayList<Position>();
        if(gameStage==C.WHITE_MOVE || gameStage==C.WHITE_BLOC){
            allMoveableChess = getAllTypeChessPositions(C.WHITE_QUEEN);
        }else{
            allMoveableChess = getAllTypeChessPositions(C.BLACK_QUEEN);
        }
        for(Position chess : allMoveableChess){
            allMoveableDirections[counter] = getMoveableDirectionForChess(new Position(chess.x,chess.y));
            counter++;
        }
        return allMoveableDirections;
    }

    public boolean isTerminal(int gameStage){
        ArrayList<Position> allMoveableChess = new ArrayList<Position>();
        if(gameStage==C.WHITE_MOVE || gameStage==C.WHITE_BLOC){
            allMoveableChess = getAllTypeChessPositions(C.WHITE_QUEEN);
        }else{
            allMoveableChess = getAllTypeChessPositions(C.BLACK_QUEEN);
        }
        ArrayList<Position> moveablePositions;
        for(Position chess : allMoveableChess){
            moveablePositions = getAllMoveablePositionsForChess(chess);
            if(moveablePositions.size()!=0){
                return false;
            }
        }
        return true;
    }
    public boolean isEnvironmentClosed(int gameStage){
        ArrayList<Position> moveablePosition_self = getAllMoveablePositionsForSide(gameStage);
        if(gameStage==C.WHITE_MOVE){gameStage=C.BLACK_MOVE;}else{gameStage=C.WHITE_MOVE;}
        ArrayList<Position> moveablePosition_opp = getAllMoveablePositionsForSide(gameStage);
        for(Position s:moveablePosition_self){
            for(Position o:moveablePosition_opp){
                if(s.x==o.x && s.y==o.y){
                    return false;
                }
            }
        }
        return true;
    }
    public boolean isChessTerminal(Position chess){
        ArrayList<Position> chessPositions=getAllMoveablePositionsForChess(chess);
        if(chessPositions.size()==0){return true;}else{return false;}
    }

    //OVERWRITE ON CHESSMAT
    public void overWrite(int chessType,Position position){
        chess_mat[position.x][position.y]=chessType;
    }

    //ISEMPTY
    public boolean isEmpty(Position position){
        if(chess_mat[position.x][position.y]==C.EMPTY_BLOCK){
            return true;
        }else{
            return false;
        }
    }

    //TOSTRING
    public String toString(){
        String result = " Y \n";
        for(int y = 0; y < size_y; y++){
            result = result + " " + y + "|";
            for(int x = 0; x < size_x; x++){
                result = result + C.STANDARD_CHESS_SYMBOL[chess_mat[x][y]];
            }
            result = result + "\n";
        }
        result = result + "   ";
        for(int i = 0; i < size_x; i++){
            result = result + "---";
        }
        result = result + "\n   ";
        for(int i = 0; i < size_x; i++){
            result = result + " " + i + " ";
        }
        result = result + " X ";
        return result;
    }
    public String toString(Position position){
        ArrayList<Position> moveablePositions = getAllMoveablePositionsForChess(position);
        String result = " Y \n";
        String index = "";
        for(int y = 0; y < size_y; y++){
            result = result + " " + y + "|";
            for(int x = 0; x < size_x; x++){
                index =  C.STANDARD_CHESS_SYMBOL[chess_mat[x][y]];
                for(Position p : moveablePositions){
                    if(p.x==x && p.y==y){
                        index = C.STANDARD_CHESS_SYMBOL[C.AVAILABLE_ACTION_BLOCK];
                        moveablePositions.remove(p);
                        break;
                    }
                }
                result = result + index;
            }
            result = result + "\n";
        }
        result = result + "   ";
        for(int i = 0; i < size_x; i++){
            result = result + "---";
        }
        result = result + "\n   ";
        for(int i = 0; i < size_x; i++){
            result = result + " " + i + " ";
        }
        result = result + " X ";
        return result;
    }
}