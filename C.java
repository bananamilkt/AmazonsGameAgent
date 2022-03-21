public class C{
    //CHESS_TYPE[00]
    public static final int CHESS_TYPE_ERROR =              0;
    public static final int EMPTY_BLOCK =                   1;
    public static final int MOVEABLE_CHESS_LOWER_LIMIT =    1;
    public static final int BLACK_KING =                    2;
    public static final int BLACK_QUEEN =                   3;
    public static final int BLACK_BISHOP =                  4;
    public static final int BLACK_ROOK =                    5;
    public static final int BLACK_PAWN =                    6;
    public static final int WHITE_KING =                    7;
    public static final int WHITE_QUEEN =                   8;
    public static final int WHITE_BISHOP =                  9;
    public static final int WHITE_ROOK =                    10;
    public static final int WHITE_PAWN =                    11;
    public static final int MOVEABLE_CHESS_UPPER_LIMIT =    12;
    public static final int AMAZON_BLOCK =                  12;
    public static final int AVAILABLE_ACTION_BLOCK =        13;
    public static final String[] STANDARD_CHESS_SYMBOL = {"[E]", " + ", "{K}", "{Q}", "{B}", "{O}", "{P}", "[K]", "[Q]", "[B]", "[O]", "[P]", "[ ]", "<+>"};

    //GAME_STAGE[50]
    public static final int WHITE_MOVE =                    50;
    public static final int WHITE_BLOC =                    51;
    public static final int BLACK_MOVE =                    52;
    public static final int BLACK_BLOC =                    53;

    //INITIAL_CHESS_MAT)TYPE[120]
    public static final int CHESS_MAT_ERROR =               120;
    public static final int AMAZONS_CHESS_MAT =             121;
    public static final int SIMPLE_AMAZONS_CHESS_MAT =      122;

    //PLAYER_TYPE[160]
    public static final String[] PLAYER_TYPE = 
    {"HUMAN_PLAYER",
    "AGENT_PLAYER001",
    "AGENT_PLAYER002",
    "AGENT_PLAYER003",
    "AGENT_PLAYER004",
    "AGENT_PLAYER005",
    "AGENT_PLAYER006",
    "AGENT_PLAYER007",
    "AGENT_PLAYER008",
    "AGENT_PLAYER009",
    "AGENT_PLAYER010",
    "AGENT_PLAYER011",
    "AGENT_PLAYER012",
    "AGENT_PLAYER013",
    "AGENT_PLAYER014",
    "AGENT_PLAYER015",
    "AGENT_PLAYER000",
    "AGENT_PLAYER000",
    "AGENT_PLAYER000",
    "AGENT_PLAYER000",
    "AGENT_PLAYER000",
    "AGENT_PLAYER000",
    "AGENT_PLAYER000",
    "AGENT_PLAYER000",
    "AGENT_PLAYER000",
    "AGENT_PLAYER000",
    "AGENT_PLAYER000",
    "AGENT_PLAYER000",
    "AGENT_PLAYER000",
    "AGENT_PLAYER000",
    "AGENT_PLAYER000",
    "AGENT_PLAYER000",
    "AGENT_PLAYER000",
    "AGENT_PLAYER000",
    "AGENT_PLAYER000",
    "AGENT_PLAYER000",
    "AGENT_PLAYER000",
    "AGENT_PLAYER[Y]",
    "AGENT_PLAYERMCT",
    "AGENT_PLAYER[X]",
    "RANDOM_AGENT",};

    public static final int HUMAN_PLAYER =                  160;
    
    public static final int MINMAX_AGENT_PLAYER001 =        161;    //FULL DEFENSING [STATES DEPENCE]
    public static final int MINMAX_AGENT_PLAYER002 =        162;    //BALANCE DEFENSING & ATTACKING [STATES DEPENCE]
    public static final int MINMAX_AGENT_PLAYER003 =        163;    //FULL ATTACKING [STATES DEPENCE]
    
    public static final int MINMAX_AGENT_PLAYER004 =        164;    //FULL DEFENSING [MOVEABLE DIRECTIONS DEPENCE]
    public static final int MINMAX_AGENT_PLAYER005 =        165;    //BALANCE DEFENSING & ATTACKING [MOVEABLE DIRECTIONS DEPENCE]
    public static final int MINMAX_AGENT_PLAYER006 =        166;    //FULL ATTACKING [MOVEABLE DIRECTIONS DEPENCE]
    
    public static final int MINMAX_AGENT_PLAYER007 =        167;    //FULL DEFENSING [MOVEABLE DIRECTIONS AND DEAD CHESS DEPENCE]
    public static final int MINMAX_AGENT_PLAYER008 =        168;    //BALANCE DEFENSING & ATTACKING [MOVEABLE DIRECTIONS AND DEAD CHESS DEPENCE]
    public static final int MINMAX_AGENT_PLAYER009 =        169;    //FULL ATTACKING [MOVEABLE DIRECTIONS AND DEAD CHESS DEPENCE]

    public static final int MINMAX_AGENT_PLAYER010  =       170;
    public static final int MINMAX_AGENT_PLAYER011  =       171;
    public static final int GRAPH_AGENT_PLAYER012 =         172;
    public static final int MINMAX_AGENT_PLAYER013 =        173;
    public static final int MINMAX_AGENT_PLAYER014 =        174;
    public static final int MINMAX_AGENT_PLAYER015 =        175;

    public static final int AMAZON_AGENT_PLAYER_Y_ =        197;
    public static final int MONTE_CARLO_PLAYER =            198;
    public static final int AMAZON_AGENT_PLAYER_X_ =        199;
    public static final int RANDOM_AGENT  =                 200;

    //STATE_ACTION_INDEX[00]
    public static final int FROM_X =                        0;
    public static final int FROM_Y =                        1;
    public static final int TO_X =                          2;
    public static final int TO_Y =                          3;
    public static final int BLOC_X =                        4;
    public static final int BLOC_Y =                        5;

    public static final int DEFAULT_F_VALUE =               -1;
    public static final int DEFAULT_MAX_F_VALUE =           Integer.MIN_VALUE;
    public static final int DEFAULT_MIN_F_VALUE =           Integer.MAX_VALUE;
    public static final int TERMINAL_STATE =                -99;

    //MOVEABLE DIRECTIONS
    public static final int UP =                            0;
    public static final int RIGHT =                         1;
    public static final int DOWN =                          2;
    public static final int LEFT =                          3;
    public static final int UP_RIGHT =                      4;
    public static final int UP_LEFT =                       5;
    public static final int DOWN_RIGHT =                    6;
    public static final int DOWN_LEFT =                     7;
    public static final int MOVEABLE =                      1;
    public static final int UNMOVEABLE =                    -1;

    //TERRITORY
    public static final int WHITE =                         0;
    public static final int BLACK =                         1;
    public static final int CONTROLED_BLOCKS =              2;


}

//                       INGROUP    RANDOM/24  
// AGENT_PLAYER006:      5          22          
// AGENT_PLAYER008:      5          24
// AGENT_PLAYER005:      3          24
// AGENT_PLAYER007:      1          24
// AGENT_PLAYER002:     -1          20
// AGENT_PLAYER001:     -3          4
// AGENT_PLAYER004:     -3          14
// AGENT_PLAYER009:     -3          18
// AGENT_PLAYER010:     -5         -18
// AGENT_PLAYER011:     -5          20
// AGENT_PLAYER003:     -9         -18 
// AGENT_PLAYER013:      X         -22
      
//                       RANDOM/8   RANDOM/10   RANDOM/36
// AGENT_PLAYER001:      6          8           28/36
// AGENT_PLAYER002:      6          8           26/36
// AGENT_PLAYER003:      X         -10         -28/36
// AGENT_PLAYER004:      8          8           34/36
// AGENT_PLAYER005:      8          10          36/36
// AGENT_PLAYER006:      8          10          34/36
// AGENT_PLAYER007:      8          8           32/36
// AGENT_PLAYER008:      6          10          36/36
// AGENT_PLAYER009:      8          8           30/36
// AGENT_PLAYER010:      X         -8          -28/36
// AGENT_PLAYER011:      8          10          26/36
// AGENT_PLAYER013:      X         -10         -30/36
// AGENT_PLAYER014:     -6          4            2/36
// AGENT_PLAYER015:      X          X          -36/36 