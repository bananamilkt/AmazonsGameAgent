import java.util.ArrayList;

public class Test {
    public static void main(String []args) {
        ChessMat chessMat = new ChessMat(10,10);
        chessMat.initialAmazonChessMat();
        chessMat.overWrite(C.AMAZON_BLOCK,new Position(4,9));
        //chessMat.overWrite(C.BLACK_QUEEN,new Position(3,8));
        chessMat.overWrite(C.AMAZON_BLOCK,new Position(4,8));
        chessMat.overWrite(C.AMAZON_BLOCK,new Position(4,7));
        chessMat.overWrite(C.AMAZON_BLOCK,new Position(3,7));
        chessMat.overWrite(C.AMAZON_BLOCK,new Position(2,7));
        chessMat.overWrite(C.AMAZON_BLOCK,new Position(2,8));
        chessMat.overWrite(C.AMAZON_BLOCK,new Position(2,9));

        int[] c=chessMat.getTerritory(new Position(3,9));
        System.out.println(c[C.CONTROLED_BLOCKS]);
        System.out.println(c[C.WHITE]);
        System.out.println(c[C.BLACK]);
        double b=chessMat.getTerritoryScore(new Position(3,9));
        int[] action=chessMat.getClosedAction(new Position(3,9));
        for(int i=0; i<action.length; i++){
            System.out.println(action[i]);
        }
        // State s = new State(chessMat,-1,null,null);
        // ArrayList<State> ss = s.getAllPossibleStates(C.WHITE_MOVE);
        // for(State i : ss){
        //     System.out.println(i);
        // }
        // System.out.println(ss.size());





    }
    private static void applyAction(ChessMat chessMat,int[] actionSequence){
        //MOVETO
        chessMat.overWrite(chessMat.getChess(actionSequence[0],actionSequence[1]),new Position(actionSequence[2],actionSequence[3]));
        //EMPTYFROM
        chessMat.overWrite(C.EMPTY_BLOCK,new Position(actionSequence[0],actionSequence[1]));
        //BLOC
        chessMat.overWrite(C.AMAZON_BLOCK,new Position(actionSequence[4],actionSequence[5]));
        System.out.println("[ACTION MADE FROM [" +actionSequence[0]+","+actionSequence[1]+"] TO ["+actionSequence[2]+","+actionSequence[3]+"] BLOC ["+actionSequence[4]+","+actionSequence[5]+"]");
    }
}
