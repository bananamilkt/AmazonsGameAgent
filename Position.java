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
}