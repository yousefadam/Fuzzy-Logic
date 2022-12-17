import java.util.ArrayList;

class Rule {
    //String firstVariable;
    //String secondVariable;
    //String firstSet;
    //String secondSet;
    //String operator;

    ArrayList<Variable> variables = new ArrayList<>();  //new
    ArrayList <String> operators = new ArrayList<>();   //new
    ArrayList <String> memberships = new ArrayList<>(); //new
    String outVariable;
    String outSet;


    public ArrayList<Variable> getVariables() {
        return variables;
    }

    public void addVariable(Variable variable) {
        this.variables.add(variable);
    }

    public ArrayList<String> getOperators() {
        return operators;
    }

    public void addOperator(String operator) {
        this.operators.add(operator);
    }

    public ArrayList<String> getMemberships() {
        return memberships;
    }

    public void addMembership(String membership) {
        this.memberships.add(membership);
    }

    public String getOutVariable() {
        return outVariable;
    }

    public void setOutVariable(String outVariable) {
        this.outVariable = outVariable;
    }

    public String getOutSet() {
        return outSet;
    }

    public void setOutSet(String outSet) {
        this.outSet = outSet;
    }
}