import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static int toolBoxInput = 0;
    static int mainMenuInput = 0;
    static String systemName = "";
    static String systemDescription = "";
    static ArrayList<Variable> variables = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Rule> rules = new ArrayList<>();

    public static void ToolBoxMenu(){

        System.out.println("""
                Fuzzy Logic Toolbox
                ===================
                1- Create a new fuzzy system
                2- Read System from File
                3- Quit
                """);
        toolBoxInput = scanner.nextInt();
        switch (toolBoxInput) {
            case 1 -> {
                System.out.println("""
                        Enter the system's name and a brief description:
                        ------------------------------------------------
                        """);
                scanner.next();
                systemName = scanner.nextLine();
                systemDescription = scanner.nextLine();
                MainMenu();
            }
            case 2 -> ReadSystemFromFile();
            case 3 -> System.out.println("Goodbye!");
        }
    }

    private static void ReadSystemFromFile() {
        try {
            File input = new File("input.txt");
            Scanner fileReader = new Scanner(input);
            while (fileReader.hasNextLine()) {
                /** Read System Name & Description */
                systemName = fileReader.nextLine();
                while (true){
                    String line = fileReader.nextLine();
                    if (line.equals("x")) break;
                    else systemDescription+= " " + line;
                }

                /** Read Variables */
                while (true){
                    String line = fileReader.nextLine();
                    if (line.equals("x")) break;
                    else {
                        String[] variable = line.split(" ");
                        Variable newVariable = new Variable();
                        newVariable.setName(variable[0]);
                        newVariable.setType(variable[1]);
                        newVariable.setLower(Integer.parseInt(variable[2].substring(1, variable[2].length() - 1)));
                        newVariable.setUpper(Integer.parseInt(variable[3].substring(0, variable[3].length()-1)));
                        variables.add(newVariable);

                    }
                }
                /** Read Fuzzy Sets */
                while (true){
                    ArrayList<String> lines = new ArrayList<>();
                    String line = fileReader.nextLine();
                    if (line.equals("x")) break;
                    while (!line.equals("x")){
                        lines.add(line);
                        line = fileReader.nextLine();
                    }
                    ReadFuzzySetsFromFile(lines);
                }
                /** Read Rules */
                while (true){
                    String line = fileReader.nextLine();
                    if (line.equals("x")) break;
                    else {
                        String[] rule = line.split(" ");
                        Rule newRule = new Rule();
                        newRule.addVariable(GetVariableByName(rule[0]));
                        newRule.addMembership(rule[1]);
                        newRule.addOperator(rule[2]);
                        newRule.addVariable(GetVariableByName(rule[3]));
                        newRule.addMembership(rule[4]);
                        newRule.setOutVariable(rule[6]);
                        newRule.setOutSet(rule[7]);
                        rules.add(newRule);
                    }
                }
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        MainMenu();
    }

    private static void ReadFuzzySetsFromFile(ArrayList<String> lines) {
        Variable variable = GetVariableByName(lines.get(0));
        lines.remove(0);
        for (String line:
             lines) {
            String[] set = line.split(" ");
            FuzzySet newFuzzySet = new FuzzySet();
            newFuzzySet.setName(set[0]);
            newFuzzySet.setType(set[1]);
            int numberOfValues = newFuzzySet.getType().equals("TRI") ? 3 : 4;
            String[] values = new String[numberOfValues];
            System.arraycopy(set, 2, values, 0, numberOfValues);
            newFuzzySet.setValues(values);
            assert variable != null;
            variable.addFuzzySet(newFuzzySet);
        }
    }

    private static void MainMenu() {

        System.out.println("""
                Main Menu:
                ==========
                1- Add variables.
                2- Add fuzzy sets to an existing variable.
                3- Add rules.
                4- Run the simulation on crisp values.
                5- Close.""");
        mainMenuInput = scanner.nextInt();
        switch (mainMenuInput) {
            case 1 -> AddVariables();
            case 2 -> AddFuzzySetsToExistingVariable();
            case 3 -> AddRules();
            case 4 -> RunSimulation();
            case 5 -> ToolBoxMenu();
        }
    }

    private static void AddFuzzySetsToExistingVariable() {
        System.out.println("""
                Enter the variable's name:
                --------------------------""");
        Variable currentVariable = GetVariableByName(scanner.next());
        if (currentVariable == null) {
            System.out.println("No Such Variable Found");
            MainMenu();
        }
        else {
            System.out.println("""
                    Enter the fuzzy set name, type (TRI/TRAP) and values: (Press x to finish)
                    -----------------------------------------------------""");
            while (true){
                String name = scanner.next();
                if (name.equals("x")) break;
                FuzzySet newFuzzySet = new FuzzySet();
                newFuzzySet.setName(name);
                newFuzzySet.setType(scanner.next());
                HandleFuzzySetValuesInput(newFuzzySet, scanner.nextLine());
                currentVariable.addFuzzySet(newFuzzySet);
            }
        }
        MainMenu();
    }

    private static void HandleFuzzySetValuesInput(FuzzySet newFuzzySet, String input) {
        String[] values = input.split(" ");
        newFuzzySet.setValues(values);
        /*for (int value:
                newFuzzySet.getValues()) {
            System.out.println(value);
        }*/
    }

    private static Variable GetVariableByName(String name) {
        for (Variable variable:
                variables) {
            if (variable.getName().equals(name)) return variable;
        }
        return null;
    }

    private static void AddRules() {
        System.out.println("""
                Enter the rules in this format: (Press x to finish)
                IN_variable set operator IN_variable set => OUT_variable set
                ------------------------------------------------------------""");
        while (true){
            String firstVariable = scanner.next();
            if (firstVariable.equals("x")) break;
            Rule newRule = new Rule();
            newRule.addVariable(GetVariableByName(firstVariable));
            newRule.addMembership(scanner.next());
            newRule.addOperator(scanner.next());
            newRule.addVariable(GetVariableByName(scanner.next()));
            newRule.addMembership(scanner.next());
            scanner.next();
            newRule.setOutVariable(scanner.next());
            newRule.setOutSet(scanner.next());
            rules.add(newRule);
        }
        /*for (Rule rule:
             rules) {
            System.out.println(rule.getFirstVariable() + " " + rule.getFirstSet() + " " + rule.getOperator() + " " + rule.getSecondVariable() + " " + rule.getSecondSet() + " => " + rule.getOutVariable() + " " + rule.getOutSet());
        }*/
        MainMenu();
    }

    private static void RunSimulation() {
        System.out.println("""
                Enter the crisp values:
                -----------------------""");
        for (Variable variable:
                variables) {
            if (variable.getType().equals("IN")){
                System.out.print(variable.getName() + ": ");
                variable.setCrispValue(scanner.nextInt());
            }
        }
        ToolBox toolBox = new ToolBox(variables, rules);
        toolBox.simulate();
    }

    private static void AddVariables() {
        System.out.println("""
                Enter the variable's name, type (IN/OUT) and range ([lower, upper]):
                (Press x to finish)
                --------------------------------------------------------------------""");
        while (true){
            String name = scanner.next();
            if (name.equals("x")) break;
            Variable newVariable = new Variable();
            newVariable.setName(name);
            newVariable.setType(scanner.next());
            HandleRangeInput(newVariable, scanner.nextLine());
            variables.add(newVariable);
        }
        /*for (Variable var:
             variables) {
            System.out.println(var.getName() + "\n" + var.getType() + "\n" + var.getLower() + "\n" + var.getUpper() + "\n");
        }*/
        MainMenu();
    }

    private static void HandleRangeInput(Variable newVariable, String input) {
        newVariable.setLower(Integer.parseInt(input.substring(2, input.indexOf(","))));
        newVariable.setUpper(Integer.parseInt(input.substring(input.indexOf(",") + 2, input.length() - 1)));
    }

    public static void main(String[] args) {
        ToolBoxMenu();
        scanner.close();
    }



    
}
