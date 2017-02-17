import java.util.Map;

public class Reference implements Location {
    private String locname;

    public Reference(String locname) {
        if (!isValidReference(locname))
            throw new IllegalArgumentException("Location " + locname + " is not a valid name.");
        this.locname = locname;
    }

    public String getLocationName() {
        return locname;
    }

    @Override
    public int value(Map<String, Integer> varmap) {
        // in the case of branch opcode the labelmap should be passed instead of varmap
        if (varmap == null)
            throw new NullPointerException("value(): varmap is null");
        Integer valobj = varmap.get(this.locname);
        if (valobj == null)
            throw new IllegalArgumentException("Location " + this.locname + " does not exist");
        return valobj;
    }

    @Override
    public boolean isLiteral() {
        return false;
    }

    public static boolean isValidReference(String str) {
        if (str.length() < 1) return false;
        if (!Character.isLetter(str.codePointAt(0))) return false;
        for (int i = 1; i < str.length(); ++i) {
            if (!Character.isLetterOrDigit(str.codePointAt(i))) return false;
        }
        return true;
    }
}
