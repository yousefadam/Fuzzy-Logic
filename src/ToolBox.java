import java.util.ArrayList;

public class ToolBox {
    ArrayList<Variable> variables = new ArrayList<>();
    ArrayList<Rule> rules = new ArrayList<>();

    public ToolBox(ArrayList<Variable> variables, ArrayList<Rule> rules) {
        this.variables = variables;
        this.rules = rules;
    }
    
    public void simulate(){
        fuzzify();
        infer();
        deffuzify();
    }

    private void deffuzify() {
    }

    private void infer() {
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
