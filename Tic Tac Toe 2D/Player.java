import java.util.*;

public class Player {

    private int initial_depth = 2;
    private int player = Constants.CELL_X;
    private int opponent = Constants.CELL_O;


    public GameState play(final GameState gameState, final Deadline deadline) {

        Vector<GameState> nextStates = new Vector<GameState>();
        gameState.findPossibleMoves(nextStates);


        if (nextStates.size() == 0) {
            return new GameState(gameState, new Move());
        }

       return bestMove(gameState, nextStates);

    }


    private GameState bestMove(GameState state, Vector<GameState> moves){


    GameState best_state = moves.get(0);


    int x = minimax(moves.get(0), initial_depth, Integer.MIN_VALUE, Integer.MAX_VALUE,  true);
    
    for (int i = 1; i < moves.size(); i++) {
        
        int y = minimax(moves.get(i), initial_depth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        
        if (y > x){
            best_state = moves.get(i);
            x = y; 
        }
        

        
    }


    return best_state;

    }


    int scoreMultiplier(int x, int plr, boolean block){
        if (block) return 0;
        int[] multiplier = {0, 10, 100, 1000, 10000};
        int score = 0;
        if (plr == player) score = multiplier[x];
        else if (plr == opponent) score = score - multiplier[x];
        return score;
    }



    int countDiag2(GameState state, int plr) {
        int counter=0;
        boolean block = false;
        for ( int i = 0 ; i < state.BOARD_SIZE ; i++ ) {
            if (state.at(i, 3-i) == plr) counter++;
            if (state.at(i, 3-i) == Constants.CELL_EMPTY) continue;
            else
            {block = true;  } 
        }
        return scoreMultiplier(counter, plr, block);
    }

    int countDiag1(GameState state, int plr){
        int counter=0;
        boolean block = false;
        for ( int i = 0 ; i < state.BOARD_SIZE ; i++ ) {
            if (state.at(i,i) == plr ) counter++;
            else if (state.at(i, i) == Constants.CELL_EMPTY) continue;
            else
            {block = true;  } 
            
        }
        return scoreMultiplier(counter, plr, block);
    }

    int countRow(GameState state, int row, int plr){
        int counter = 0;
        boolean block = false;
        for (int col = 0; col < state.BOARD_SIZE; col++){
            if (state.at(row ,col) == plr) counter++;
            else if (state.at(row, col) == Constants.CELL_EMPTY) continue;
            else
            {block = true;  } 
        }
        return scoreMultiplier(counter, plr, block);
    }

    int countColoumn(GameState state, int column, int plr) {
        int counter=0;
        boolean block = false;
        for (int row = 0; row < state.BOARD_SIZE ; row++ ) {
            if (state.at(row, column) == plr) counter++;
             else if (state.at(row, column) == Constants.CELL_EMPTY) continue;
             else
            {block = true;} 
    
        }
        return scoreMultiplier(counter, plr, block);
    }


    int heuristicFunction(GameState state){

    int score = 0;
    int [] weight = {5, 2, 2, 5, 2, 10 ,10 ,2, 2, 10 ,10 ,2, 5, 2, 2, 5};


    for (int i = 0; i <16 ;i++ ) {
        if (state.at(i) == Constants.CELL_X) score += weight[i]; 
        if (state.at(i) == Constants.CELL_O) score -= weight[i]; 
    }


    for (int i = 0; i < 4;i++) {
        score += countRow(state, i, Constants.CELL_X);
        score += countRow(state, i, Constants.CELL_O);
        score += countColoumn(state, i, Constants.CELL_O);
        score += countColoumn(state, i, Constants.CELL_X);    
    }
    score += countDiag1(state,  Constants.CELL_X);
    score += countDiag2(state, Constants.CELL_X);
    score += countDiag1(state, Constants.CELL_O);
    score += countDiag2(state, Constants.CELL_O);





    return score;
    }


    int minimax(GameState state, int depth, int alpha, int beta, boolean maximizingPlayer){

    Vector<GameState> nextStates = new Vector<GameState>();
    state.findPossibleMoves(nextStates);    
    

    if (depth == 0 || nextStates.size() == 0){
        return heuristicFunction(state); 
        }


    if (maximizingPlayer){  
         int bestValue = Integer.MIN_VALUE;
         for (int i = 0; i < nextStates.size(); i++){
            GameState child_state = nextStates.get(i);
            int val = minimax(child_state, depth - 1, alpha, beta, false);
            bestValue = Math.max(bestValue, val);
            alpha = Math.max(alpha, bestValue);
            if (alpha >= beta) break;
            }

        return bestValue;
    }

    else{
         int bestValue = Integer.MAX_VALUE;
         for (int i = 0; i < nextStates.size(); i++){
            GameState child_state = nextStates.get(i);
            int val = minimax(child_state, depth - 1, alpha, beta,  true);
            bestValue = Math.min(bestValue, val);
            beta = Math.min(bestValue, beta);
            if(beta <= alpha) break;
            }

         return bestValue;
         }
    }





}
