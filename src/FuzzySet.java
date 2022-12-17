import java.util.ArrayList;

class FuzzySet {
    private String name;
    private String type;
    private double membership;
    private ArrayList<Double> values = new ArrayList<>();

    public double getMembership() {
        return membership;
    }

    public void setMembership(double membership) {
        this.membership = membership;
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

    public ArrayList<Double> getValues() {
        return values;
    }

    public void setValues(String[] values) {
        for (String value:
                values) {
            if (value.equals(" ") || value.equals("")) continue;
            this.values.add(Double.parseDouble(value.trim()));
        }
    }

    @Override
    public String toString() {
        return "FuzzySet{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", values=" + values +
                '}';
    }
}