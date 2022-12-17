import java.util.ArrayList;

class Rule {
    String firstVariable;
    String secondVariable;

    ArrayList<Variable> variables;  //new
    String firstSet;
    String secondSet;
    String operator;
    ArrayList <String> operators;   //new

    ArrayList <String> memberships; //new

    String outVariable;
    String outSet;

    public String getFirstVariable() {
        return firstVariable;
    }

    public void setFirstVariable(String firstVariable) {
        this.firstVariable = firstVariable;
    }

    public String getSecondVariable() {
        return secondVariable;
    }

    public void setSecondVariable(String secondVariable) {
        this.secondVariable = secondVariable;
    }

    public String getFirstSet() {
        return firstSet;
    }

    public void setFirstSet(String firstSet) {
        this.firstSet = firstSet;
    }

    public String getSecondSet() {
        return secondSet;
    }

    public void setSecondSet(String secondSet) {
        this.secondSet = secondSet;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
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