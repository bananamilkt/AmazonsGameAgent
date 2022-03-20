import java.util.ArrayList;

public class Position{
    public int x;
    public int y;

    public Position(int x, int y){
        this.x=x;
        this.y=y;
    }
    public String toString(){
        String result="[ x:"+x+",y:"+y+" ]\n";
        return result;
    }
    public boolean equals(Position position){
        if(this.x==position.x && this.y==position.y){return true;}else{return false;}
    }
    public boolean includedBy(ArrayList<Position> positions){
        for(Position p:positions){
            if(this.equals(p)){return true;}
        }
        return false;
    }
}