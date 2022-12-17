import java.util.ArrayList;

class Variable {
    private String name;
    private String type;
    private int lower;
    private int upper;
    private double crispValue;
    private ArrayList<FuzzySet> fuzzySets = new ArrayList<>();

    public ArrayList<FuzzySet> getFuzzySets() {
        return fuzzySets;
    }

    public void addFuzzySet(FuzzySet fuzzySet) {
        this.fuzzySets.add(fuzzySet);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLower() {
        return lower;
    }

    public void setLower(int lower) {
        this.lower = lower;
    }

    public int getUpper() {
        return upper;
    }

    public void setUpper(int upper) {
        this.upper = upper;
    }

    public double getCrispValue() {
        return crispValue;
    }

    public void setCrispValue(int crispValue) {
        this.crispValue = crispValue;
    }
}

