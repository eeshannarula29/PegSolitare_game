package peggame;
import java.util.ArrayList;
import java.util.Scanner;

public class PegSolitaire {
    public static void main(String[] args) {
        printIntro();
        Scanner in = new Scanner(System.in);

        char[][] board = createBoard(readValidint(in, "pls select the board type : ",1,4));
        displayBoard(board);

        boolean gameOver = false;

        while(!gameOver){
            int[] cordinatesAndDirection = readValidMove(in, board);
            board = performMove(board, cordinatesAndDirection[1], cordinatesAndDirection[0], cordinatesAndDirection[2]);
            displayBoard(board);

            if(countMovesAvailable(board) <= 0 || countPegsRemaining(board) <= 1){
                gameOver = true;
            }
        }

        if(countPegsRemaining(board) > 1 && countMovesAvailable(board) <= 0){
            System.out.println("Opps! you lost");
        }

        if(countPegsRemaining(board) <= 1){
            System.out.println("Congrats! you won");
        }

        System.out.println("                                         ");
        System.out.println("                                         ");
        System.out.println("=========================================");
        System.out.println("THANK YOU FOR PLAYING CS300 PEG SOLITAIRE");
    }
    private static void printIntro(){

        System.out.println("                               ");
        System.out.println("                               ");
        System.out.println("WELCOME TO CS300 PEG SOLITAIRE!");
        System.out.println("===============================");
        System.out.println("                               ");
        System.out.println("                               ");
        System.out.println("Board Style Menu               ");
        System.out.println("                               ");
        System.out.println("1) Cross                       ");
        System.out.println("2) Circle                      ");
        System.out.println("3) Triangle                    ");
        System.out.println("4) Simple_t                    ");
        System.out.println("                               ");
        System.out.println("                               ");

    }

    private static int readValidint(Scanner in,String prompt,int min,int max){
        System.out.print(prompt);
        String value = in.next();
        if(isInt(value)){
            int intval = Integer.parseInt(value);
            if(intval >= min && intval <= max){
                return intval;
            }
        }
        String refill_text = "pls fill your choice as an integer between " + min + " and " + max + " : ";
        return readValidint(in, refill_text, min, max);

    }

    private static boolean isInt(String str){
        try{
            int value = Integer.parseInt(str);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }

    private static char[][] createBoard(int BoardType){

        char[][] cross = new char[][] {
                {'#', '#', '#', '@', '@', '@', '#', '#', '#'},
                {'#', '#', '#', '@', '@', '@', '#', '#', '#'},
                {'@', '@', '@', '@', '@', '@', '@', '@', '@'},
                {'@', '@', '@', '@', '_', '@', '@', '@', '@'},
                {'@', '@', '@', '@', '@', '@', '@', '@', '@'},
                {'#', '#', '#', '@', '@', '@', '#', '#', '#'},
                {'#', '#', '#', '@', '@', '@', '#', '#', '#'}
        };

        char[][] circle = new char[][] {
                {'#', '_', '@', '@', '_', '#'},
                {'_', '@', '@', '@', '@', '_'},
                {'@', '@', '@', '@', '@', '@'},
                {'@', '@', '@', '@', '@', '@'},
                {'_', '@', '@', '@', '@', '_'},
                {'#', '_', '@', '@', '_', '#'}
        };

        char[][] triangle = new char[][] {
                {'#', '#', '#', '_', '@', '_', '#', '#', '#'},
                {'#', '#', '_', '@', '@', '@', '_', '#', '#'},
                {'#', '_', '@', '@', '_', '@', '@', '_', '#'},
                {'_', '@', '@', '@', '@', '@', '@', '@', '_'}
        };

        char[][] simple_t = new char[][] {
                { '_', '_', '_', '_', '_'},
                { '_', '@', '@', '@', '_'},
                { '_', '_', '@', '_', '_'},
                { '_', '_', '@', '_', '_'},
                { '_', '_', '_', '_', '_'}
        };

        ArrayList<char[][]> Boards = new ArrayList();
        Boards.add(cross);Boards.add(circle);Boards.add(triangle);Boards.add(simple_t);

        return Boards.get(BoardType - 1);
    }

    private static void displayBoard(char[][] board){
        ArrayList<String> VisualBoard = new ArrayList<>();
        VisualBoard.add(stringRange(board[0].length));

        int rowcount = 1;

        for(char[] row : board){
            StringBuffer str = new StringBuffer();

            str.append(rowcount);
            for(char i : row){
                str.append(" " + i + " ");
            }

            VisualBoard.add(str.toString());
            rowcount++;
        }

        for(String str : VisualBoard) System.out.println(str);

    }

    private static String stringRange(int len){
        StringBuffer str = new StringBuffer();

        str.append("0 ");
        for(int i = 1; i < len + 1; i++){
            str.append(i);
            str.append("  ");
        }

        return str.toString();
    }

    private static int[] readValidMove(Scanner in,char[][] board){
        int col = readValidint(in,"pls enter the column of the peg : ",0,board[0].length);
        int row = readValidint(in, "pls enter the row of the peg : ", 0, board.length);
        int direction = readValidint(in, "pls choose direction 1) UP 2) DOWN 3) LEFT 4) RIGHT : ", 1, 4);
        int[] array = new int[]{col - 1, row - 1, direction - 1};


        if(isValidMove(board, row - 1, col - 1, direction - 1)){
            return array;
        }
        else {
            System.out.println("the move you choose to make is not valid");
        }
        return readValidMove(in, board);
    }

    private static boolean isValidMove(char[][] board, int row, int col, int direction){
        int[][][] cordinates = new int[][][]{
                {{row - 1, col},{row - 2, col}},
                {{row + 1, col},{row + 2, col}},
                {{row, col - 1},{row, col - 2}},
                {{row, col + 1},{row, col + 2}}
        };

        if(board[row][col] == '@'){
            int[] toRem = cordinates[direction][0];
            int[] toJump = cordinates[direction][1];
            if(board[toRem[0]][toRem[1]] == '@' && board[toJump[0]][toJump[1]] == '_'){
                return true;
            }
        }
        return false;
    }

    private static char[][] performMove(char[][] board,int row,int col,int direction){
        int[][][] cordinates = new int[][][]{
                {{row - 1, col},{row - 2, col}},
                {{row + 1, col},{row + 2, col}},
                {{row, col - 1},{row, col - 2}},
                {{row, col + 1},{row, col + 2}}
        };


        char[][] newBoard = board;
        newBoard[row][col] = '_';

        int[] toRem = cordinates[direction][0];
        int[] toJump = cordinates[direction][1];

        newBoard[toRem[0]][toRem[1]] = '_';
        newBoard[toJump[0]][toJump[1]] = '@';

        return newBoard;
    }

    private static int countPegsRemaining(char[][] board){
        int pegcount = 0;
        for(char[] row : board){
            for(char c : row){
                if(c == '@'){
                    pegcount++;
                }
            }
        }
        return pegcount;
    }

    private static int countMovesAvailable(char[][] board){
        int moves = 0;
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                if(board[i][j] == '@'){
                    int[][][] cordinates = new int[][][]{
                            {{i - 1, j},{i - 2, j}},
                            {{i + 1, j},{i + 2, j}},
                            {{i, j - 1},{i, j - 2}},
                            {{i, j + 1},{i, j + 2}}
                    };
                    for(int[][] cord : cordinates){
                        if(cord[0][0] > 0 && cord[0][0] < board.length && cord[1][0] > 0 && cord[1][0] < board.length){
                            if(cord[0][1] > 0 && cord[0][1] < board[0].length && cord[1][1] > 0 && cord[1][1] < board[0].length){
                                if(board[cord[0][0]][cord[0][1]] == '@' && board[cord[1][0]][cord[1][1]] == '_'){
                                    moves++;
                                }
                            }
                        }
                    }
                }
            }
        }
        return moves;
    }

}
