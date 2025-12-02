import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
public class ListMakerII {
    private static ArrayList<String> list = new ArrayList<>();
    private static String currentFile = null;
    private static boolean needsToBeSaved = false;

    public static void main(String[] args){
        boolean done = false;

        do {
            displayMenu();
            String choice = SafeInput.getRegExString("Enter choice [AaDdIiPpQqMmOoSsCcVv]: ", "[AaDdIiPpQqMmOoSsCcVv]");

            switch(choice.toUpperCase()){
                case "A":
                    addItem();
                    break;
                case "D":
                    deleteItem();
                    break;
                case "I":
                    insertItem();
                    break;
                case "P":
                    printItem();
                    break;
                case "M":
                    done = moveItem();
                    break;
                case "C":
                    clearList();
                    break;
                case "O":
                    openFile();
                    break;
                case "S":
                    saveFile();
                    break;
                case "V":
                    viewList();
                    break;
                case "Q":
                    done = quitProgram();
                    break;
            }
        }while(!done);
        System.out.println("Program terminated. Goodbye!");
    }
    private static void displayMenu(){
        System.out.println("\n=== List Maker Menu ===");
        System.out.println("A – Add an item");
        System.out.println("D – Delete an item");
        System.out.println("I – Insert an item");
        System.out.println("M – Move an item");
        System.out.println("C – Clear the list");
        System.out.println("O – Open a list file");
        System.out.println("S – Save the list file");
        System.out.println("V – View the list");
        System.out.println("Q – Quit");
        System.out.println("=======================");
    }

    private static void addItem(){
        String item = SafeInput.getNonZeroLenString("Enter item to add: ");
        list.add(item);
        System.out.println("Added item: " + item);
    }
    private static void deleteItem(){
        if(list.isEmpty()){
            System.out.println("List is empty. Nothing to Delete.");
            return;
        }
        printNumberedlist();
        int choice = SafeInput.getRangedInt("Enter Item number to delete: ", 1, list.size());
        list.remove(choice-1);
        System.out.println("Deleted item: " + list.get(choice-1));
    }
    private static void insertItem(){
        String item = SafeInput.getNonZeroLenString("Enter item to insert: ");
        int position = SafeInput.getRangedInt("Enter position (1 to " + (list.size() + 1) + "): ", 1, list.size() + 1);
        list.add(position - 1, item);
        System.out.println("Item inserted.");
    }
    private static void printItem(){
        if (list.isEmpty()){
            System.out.println("List is empty.");
        }else{
            System.out.println("\nCurrent List: ");
            for(String item : list){
                System.out.println("- " + item);
            }
        }
    }
    private static void printNumberedlist(){
        System.out.println("\nNumber of Items in the List: ");
        for(int i = 0; i < list.size(); i++){
            System.out.printf("%d: %s%n" , i+1, list.get(i));
        }
    }
    private static boolean quitProgram(){
        return SafeInput.getYNConfirm("Are you Sure you want to Quit? ");
    }
    private static boolean moveItem(){
        if(list.isEmpty()){
            System.out.println("List is empty. Nothing to move.");
        }
        printNumberedlist();
        int from = SafeInput.getRangedInt("Enter item number to move: ", 1, list.size());
        int to = SafeInput.getRangedInt("Enter new position (1 to " + list.size() + "): ", 1, list.size());
        String item = list.remove(from-1);
        list.add(to-1, item);
        needsToBeSaved = true;
        System.out.println("Moved item to position " + to);
        return false;
    }
    private static void clearList(){
        if(list.isEmpty()){
            System.out.println("List is already empty.");
            return;
        }
        boolean confirm = SafeInput.getYNConfirm("Are you sure you want to clear the list?");
        if(confirm){
            list.clear();
            needsToBeSaved = true;   // mark dirty
            System.out.println("List cleared.");
        }
        return;
    }
    private static void viewList(){
        if(list.isEmpty()){
            System.out.println("List is empty.");
        }else{
            System.out.println("\nCurrent List: ");
            for(int i = 0; i < list.size(); i++){
                System.out.println();
            }
        }
        return;
    }
    private static void openFile() {
        if (needsToBeSaved) {
            boolean save = SafeInput.getYNConfirm("Unsaved changes exist. Save before opening?");
            if (save) {
                saveFile();
            }
        }
        String baseName = SafeInput.getNonZeroLenString("Enter file name to open (without .txt): ").trim();
        if (baseName.isEmpty()) {
            System.out.println("Filename cannot be empty.");
            return;
        }
        String filename = baseName + ".txt";
        Path path = Path.of(filename);
        System.out.println("Looking for file at: " + path.toAbsolutePath());
        try {
            list.clear();
            list.addAll(Files.readAllLines(path));
            currentFile = filename;
            needsToBeSaved = false;
            System.out.println("File loaded: " + filename);
        } catch (IOException e) {
            System.out.println("Error opening file: " + e.getMessage());
        }
    }


    private static void saveFile(){
        if(currentFile == null){
            currentFile = SafeInput.getNonZeroLenString("Enter filename to save (without .txt): ") + ".txt";
        }
        try{
            Files.write(Path.of(currentFile), list);
            needsToBeSaved = false;
            System.out.println("File saved: " + currentFile);
        }catch(IOException e){
            System.out.println("Error saving file: " + e.getMessage());
        }
        return;
    }
}