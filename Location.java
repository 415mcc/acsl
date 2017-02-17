import java.util.Map;

public interface Location {
    public int value(Map<String, Integer> varmap);
    public boolean isLiteral(); // prefer this over instanceof
}
