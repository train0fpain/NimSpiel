import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class NimSpiel {

    ArrayList<Integer> stacks = new ArrayList<>();

    public NimSpiel() {
        welcomeText();

        prepareGame();

        gameLoop();
    }

    private void welcomeText(){
        System.out.println("Welcome to Nim");
    }

    private void prepareGame(){
        String input = "";
        while (true){
            try {
                input = readInput("Define amount in next stack: ");
                addToStack(Integer.parseInt(input));
            }catch (IOException ioe){
                System.out.println("Input is not valid.");
            }catch (NumberFormatException nfe){
                if (input.equalsIgnoreCase("end")){
                    break;
                }
                System.out.println("Input was not an Integer. Try again.");
            }
        }
    }

    private void gameLoop(){
        while (true){
            printCurrentSituation();
            playerTurn();
            if (checkWinCondition()){
                System.out.println("You won! Congratulations!");
                break;
            }
            computerTurn();
            if (checkWinCondition()){
                System.out.println("You lost!");
                break;
            }
        }
    }

    private void addToStack(int amount){
        stacks.add(amount);
    }

    private boolean removeFromStack(int stack, int amount){
        if (stacks.size() > stack){
            stacks.set(stack, stacks.get(stack)-amount);
            if (stacks.get(stack) <= 0){
                stacks.remove(stack);
            }
            return true;
        } else {
            System.out.println("Stack does not exist");
            return false;
        }
    }

    private boolean checkWinCondition(){
        return stacks.size() == 0;
    }

    private String readInput(String message) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print(message);
        return br.readLine();
    }

    private void playerTurn(){
        boolean turnFinished = false;
        while (!turnFinished) {
            try {
                String stackString = readInput("Enter target stack index: ");
                String amountString = readInput("Enter amount to remove from stack: ");
                removeFromStack(Integer.parseInt(stackString), Integer.parseInt(amountString));
                turnFinished = true;
            } catch (IOException ioe) {
                System.out.println("Input was not valid");
            } catch (NumberFormatException nfe) {
                System.out.println("You need to enter numbers. Try again.");
            }
        }
        printCurrentSituation();
    }

    private void computerTurn() {
        System.out.println("Computer turn: ");
        List<Integer> nums = new ArrayList<>();
        int biggestInt = getBiggestInt(stacks);
        for (int i = 1; i <= biggestInt; i++) {
            nums.add(0);
            for (int k = 0; k < stacks.size(); k++) {
                if (stacks.get(k) >= i) {
                    nums.set(i - 1, nums.get(i - 1) + 1);
                }
            }
        }

        int binaryValue = 0;
        for (int j = 0; j < nums.size(); j++) {
            binaryValue = binaryValue ^ nums.get(j);
        }

        if (stacks.size() == 1) {
            removeFromStack(0, biggestInt);
        } else {
            if (binaryValue == 0) {
                // random since there is no "good" solution in this situation
                int randomStack = (int) (Math.random() * (stacks.size() - 1));
                removeFromStack(randomStack, 1);
            }else if (binaryValue <= biggestInt)
                for (int i = 0; i < stacks.size(); i++) {
                    if (stacks.get(i) >= binaryValue) {
                        removeFromStack(i, binaryValue);
                        break;
                    }
                }
            else {
                // random since there is no "good" solution in this situation
                int randomStack = (int) (Math.random() * (stacks.size() - 1));
                removeFromStack(randomStack, 1);
            }
        }
    }

    private int getBiggestInt(List<Integer> in){
        int tmp = 0;
        for (int bla:in){
            if (bla > tmp){
                tmp=bla;
            }
        }
        return tmp;
    }

    private void printCurrentSituation(){
        for (int i=0; i<stacks.size();i++){
            System.out.println("Stack: "+i+" Amount: "+stacks.get(i));
        }
    }
}
