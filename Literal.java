import java.util.Map;

public class Literal implements Location {
    private int value;

    public Literal(int value) {
        this.value = value;
    }

    public Literal(String str) {
        int val;
        try {
            val = Integer.parseInt(str);
        } catch (NumberFormatException ex) {
            throw new NumberFormatException("Unable to parse integer from location. Reminder: a reference must begin with an alphabetic character.");
        }
        this.value = val;
    }

    @Override
    public int value(Map<String, Integer> varmap) {
        return value;
    }

    @Override
    public boolean isLiteral() {
        return true;
    }
}
