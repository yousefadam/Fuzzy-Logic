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
                2- Quit
                """);
        toolBoxInput = scanner.nextInt();
        switch (toolBoxInput) {
            case 1 -> {
                System.out.println("""
                        Enter the system’s name and a brief description:
                        ------------------------------------------------
                        """);
                scanner.next();
                systemName = scanner.nextLine();
                systemDescription = scanner.nextLine();
                MainMenu();
            }
            case 2 -> System.out.println("Goodbye!");
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
                Enter the variable’s name:
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
            newRule.setFirstVariable(firstVariable);
            newRule.setFirstSet(scanner.next());
            newRule.setOperator(scanner.next());
            newRule.setSecondVariable(scanner.next());
            newRule.setSecondSet(scanner.next());
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
            System.out.print(variable.getName() + ": ");
            variable.setCrispValue(scanner.nextInt());
        }
        System.out.println("Running the simulation…");

    }

    private static void AddVariables() {
        System.out.println("""
                Enter the variable’s name, type (IN/OUT) and range ([lower, upper]):
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
