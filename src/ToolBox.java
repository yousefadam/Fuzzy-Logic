import java.util.ArrayList;

public class ToolBox {
    ArrayList<Variable> variables = new ArrayList<>();
    ArrayList<Rule> rules = new ArrayList<>();
    ArrayList<Double> centroids = new ArrayList<>();
    ArrayList<Double> outputMemberships;
    ArrayList<String> outputSets;

    public ToolBox(ArrayList<Variable> variables, ArrayList<Rule> rules) {
        this.variables = variables;
        this.rules = rules;
    }

    Variable getVariable(String name) {
        for (Variable variable : variables) {
            if (variable.getName().equals(name))
                return variable;
        }
        return null;
    }
    
    public void simulate(){
        System.out.println("Running the simulation...");
        fuzzify();
        infer();
        deffuzify();
    }

    private void deffuzify() {
        double numerator = 0, denominator = 0, result = 0;
        for (int i=0 ; i<outputMemberships.size() ; i++) {
            numerator += outputMemberships.get(i) * centroids.get(i);
            denominator += outputMemberships.get(i);
        }
        result = numerator / denominator;

        System.out.println("Defuzzification => done");

    }

    private void infer() {
        for (Rule rule: rules) {
            double membership = 0;

            if(rule.operators.size() == 0){
                Variable variable = rule.variables.get(0);
                membership = variable.getMembership(rule.memberships.get(0));
            }

            for (int i = 0; i < rule.operators.size(); i++) {
                Variable variable1 = getVariable(rule.variables.get(i).getName());
                Variable variable2 = getVariable(rule.variables.get(i + 1).getName());

                if(rule.operators.get(i).equals("or")){
                    membership = Math.max(variable1.getMembership(rule.memberships.get(i)),
                            variable2.getMembership(rule.memberships.get(i+1)));
                }
                else if(rule.operators.get(i).equals("and")){
                    membership = Math.min(variable1.getMembership(rule.memberships.get(i)),
                            variable2.getMembership(rule.memberships.get(i+1)));
                }
                else if(rule.operators.get(i).equals("or_not")){
                    membership = Math.max(variable1.getMembership(rule.memberships.get(i)),
                            1 - variable2.getMembership(rule.memberships.get(i+1)));
                }
                else if (rule.operators.get(i).equals("and_not")){
                    membership = Math.min(variable1.getMembership(rule.memberships.get(i)),
                           1 - variable2.getMembership(rule.memberships.get(i+1)));
                }
            }

            Variable outputVariable = getVariable(rule.getOutVariable());
            for (FuzzySet set: outputVariable.getFuzzySets()) {
                if(set.getName().equals(rule.getOutSet())){
                    centroids.add(set.getCentroid());
                }
            }

            outputMemberships.add(membership);
            outputSets.add(rule.outVariable);



        }
        System.out.println("Inference => done");

    }

    private void fuzzify() {

        for (Variable variable: variables) {
            double crisp = variable.getCrispValue();

            for (FuzzySet set: variable.getFuzzySets()) {

                //to check whether the corresponding value is either 0 or 1, without calculating the slope
                int pos = set.getValues().indexOf(crisp);//returns -1 if not found

                if(pos != -1){
                    set.setMembership(getYValue(set.getType(), pos));
                }else{
                    double x1 = -1, y1 = -1, x2 = -1, y2 = -1;

                    ArrayList<Double> values = set.getValues();
                    for (int i = 0; i < values.size()-1; i++) {

                        //check if the crisp value is in range between any two values
                        if (crisp > values.get(i) && crisp < values.get(i+1)){

                            //get XY coordinates of the points to calculate slope
                            x1 = values.get(i);
                            y1 = getYValue(set.getType(), i);
                            x2 = values.get(i+1);
                            y2 = getYValue(set.getType(), i+1);
                        }

                        //if the crisp value doesn't fall in the set, then its membership equals 0
                        if (x1 == -1 && x2 == -1 && y1 == -1 && y2 == -1) set.setMembership(0);
                        else set.setMembership(calculateMembership(x1, y1, x2, y2, crisp));

                    }
                }

            }

        }

        System.out.println("Fuzzification => done");

    }

    private double calculateMembership(double x1, double y1, double x2, double y2, double crisp) {
        //y = mx + c
        double slope = (y2 - y1)/(x2 - x1);
        double c = y1 - (slope * crisp);
        return (slope * crisp) + c;
    }

    private int getYValue(String type, int pos) {
        //if trap, the middle two values correspond directly to 1, else 0
        if(type.equals("TRAP")){
            return pos == 1 || pos == 2 ?  1 : 0;
        }
        //if tri, the middle value correspond directly to 1, else 0
        else return pos == 1 ? 1 : 0;
    }
}
