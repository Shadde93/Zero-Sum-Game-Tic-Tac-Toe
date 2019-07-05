import java.util.*;

public class Player {

    private int initial_depth = 0;
    private int player;
    private int opponent;
    

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
        if (plr == Constants.CELL_X) score = multiplier[x];
        else if (plr == Constants.CELL_O) score = score - multiplier[x];
        return score;
    }
	
	int countDiagZY1(GameState state,int x,  int player) {
        int counter=0;
        boolean block = false;
        for ( int i = 0 ; i < state.BOARD_SIZE ; i++ ) {
            if (state.at(x,i,i) == player) counter++;
            else if (state.at(x, i, i) == Constants.CELL_EMPTY) continue;
            else
        {block = true; } 
            
        }
        return scoreMultiplier(counter, player, block);
    }

	int countDiagZY2(GameState state,int x,  int player) {
        int counter=0;
        boolean block = false;
        for ( int i = 0 ; i < state.BOARD_SIZE ; i++ ) {
            if (state.at(x,3-i,i) == player) counter++;
        else if (state.at(x,3-i, i) == Constants.CELL_EMPTY) continue;
            else
        {block = true; } 
            
        }
        return scoreMultiplier(counter, player, block);
    }

	int countDiagZX1(GameState state,int y,  int player) {
        int counter=0;
        boolean block = false;
        for ( int i = 0 ; i < state.BOARD_SIZE ; i++ ) {
            if (state.at(i,y,i) == player) counter++;
        else if (state.at(i,y,i) == Constants.CELL_EMPTY) continue;
            else
        {block = true; } 
            
        }
        return scoreMultiplier(counter, player, block);
    }

	int countDiagZX2(GameState state,int y,  int player) {
        int counter=0;
        boolean block = false;
        for ( int i = 0 ; i < state.BOARD_SIZE ; i++ ) {
            if (state.at(3-i,y,i) == player) counter++;
            else if (state.at(3-i, y, i) == Constants.CELL_EMPTY) continue;
            else
        {block = true; } 
            
        }
        return scoreMultiplier(counter, player, block);
    }

	int countDiagXY1(GameState state,int z,  int player) {
        int counter=0;
        boolean block = false;
        for ( int i = 0 ; i < state.BOARD_SIZE ; i++ ) {
            if (state.at(i,i,z) == player) counter++;
        else if (state.at(i, i, z) == Constants.CELL_EMPTY) continue;
            else
        {block = true; } 
            
        }
        return scoreMultiplier(counter, player, block);
    }

	int countDiagXY2(GameState state,int z,  int player) {
        int counter=0;
        boolean block = false;
        for ( int i = 0 ; i < state.BOARD_SIZE ; i++ ) {
            if (state.at(3-i,i,z) == player) counter++;
        else if (state.at(3-i, i,z) == Constants.CELL_EMPTY) continue;
            else
        {block = true; } 
            
        }
        return scoreMultiplier(counter, player, block);
    }

    int countRymdDiag4(GameState state, int player) {
        int counter=0;
        boolean block = false;
        for ( int i = 0 ; i < state.BOARD_SIZE ; i++ ) {
            if (state.at(i,i,3-i) == player) counter++;
            else if (state.at(i, i,3-i) == Constants.CELL_EMPTY) continue;
            else
        {block = true; } 
            
        }
        return scoreMultiplier(counter, player, block);
    }

    int countRymdDiag3(GameState state, int player) {
        int counter=0;
        boolean block = false;
        for ( int i = 0 ; i < state.BOARD_SIZE ; i++ ) {
            if (state.at(3-i,i,i) == player) counter++;
        else if (state.at(3-i, i,i) == Constants.CELL_EMPTY) continue;
            else
        {block = true; } 
            
        }
        return scoreMultiplier(counter, player, block);
    }


    int countRymdDiag2(GameState state, int player) {
        int counter=0;
        boolean block = false;
        for ( int i = 0 ; i < state.BOARD_SIZE ; i++ ) {
            if (state.at(i,3-i,i) == player) counter++;
        else if (state.at(i, 3-i,i) == Constants.CELL_EMPTY) continue;
            else
        {block = true; } 
            
        }
        return scoreMultiplier(counter, player, block);
    }

    int countRymdDiag1(GameState state, int player){
        int counter=0;
        boolean block = false;
        for ( int i = 0 ; i < state.BOARD_SIZE ; i++ ) {
            if ( state.at(i,i,i) == player ) counter++;
        else if (state.at(i, i,i) == Constants.CELL_EMPTY) continue;
            else
        {block = true; } 
            
        }
        return scoreMultiplier(counter, player, block);
    }

    int countRow(GameState state,int z, int y, int player){
        int counter = 0;
        boolean block = false;
        for (int x = 0; x < state.BOARD_SIZE; x++){
            if (state.at(x ,y, z) == player) counter++;
        else if (state.at(x, y,z) == Constants.CELL_EMPTY) continue;
            else
        {block = true; } 
        
	    }
        return scoreMultiplier(counter, player, block);
    }
    
    int countRow1(GameState state,int z, int x, int player){
        int counter = 0;
        boolean block = false;
        for (int y = 0; y < state.BOARD_SIZE; y++){
            if (state.at(x ,y, z) == player) counter++;
            else if (state.at(x, y,z) == Constants.CELL_EMPTY) continue;
            else
        {block = true; } 
        
	    }
        return scoreMultiplier(counter, player, block);
    }
    

    int countColoumn(GameState state, int x,int y, int player) {
        int counter=0;
        boolean block = false;
        for (int z = 0; z < state.BOARD_SIZE ; z++ ) {
            if (state.at(x,y,z) == player) counter++;
        else if (state.at(x, y,z) == Constants.CELL_EMPTY) continue;
            else
        {block = true; } 
    	
        }
        return scoreMultiplier(counter, player, block);
    }
    

    int heuristicFunction(GameState state){

    int score = 0;
    
    for (int i = 0; i < state.BOARD_SIZE;i++) {
	
    	for (int j = 0; j < state.BOARD_SIZE;j++) {

        	score += countRow(state, i,j, Constants.CELL_X);
        	score += countRow(state,i,j, Constants.CELL_O);
        	score += countColoumn(state,i,j, Constants.CELL_X);
        	score += countColoumn(state,i,j, Constants.CELL_O);
        	score += countRow1(state, i,j, Constants.CELL_X);
        	score += countRow1(state, i,j, Constants.CELL_O);

    	}
    }

    for (int k = 0; k < state.BOARD_SIZE;k++) {
        score += countDiagXY1(state, k, Constants.CELL_X);
        score += countDiagXY1(state,k, Constants.CELL_O);
        
        score += countDiagXY2(state, k, Constants.CELL_X);
        score += countDiagXY2(state,k, Constants.CELL_O);
        
        score += countDiagZX1(state,k, Constants.CELL_X);
        score += countDiagZX1(state,k, Constants.CELL_O);
        
        score += countDiagZX2(state,k, Constants.CELL_X);
        score += countDiagZX2(state,k, Constants.CELL_O);
        
        score += countDiagZY1(state,k, Constants.CELL_X);
        score += countDiagZY1(state,k, Constants.CELL_O);
        
        score += countDiagZY2(state,k, Constants.CELL_X);
        score += countDiagZY2(state,k, Constants.CELL_O);
    }



    score += countRymdDiag1(state, Constants.CELL_X);
    score += countRymdDiag1(state, Constants.CELL_O);
    
    score += countRymdDiag2(state, Constants.CELL_X);
    score += countRymdDiag2(state, Constants.CELL_O);
    
    score += countRymdDiag3(state, Constants.CELL_X);
    score += countRymdDiag3(state, Constants.CELL_O);
    
    score += countRymdDiag4(state, Constants.CELL_X);
    score += countRymdDiag4(state, Constants.CELL_O);


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

