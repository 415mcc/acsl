import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Locale;

public class Parser {
    private static final Pattern splitp = Pattern.compile("\\s+");
    private final List<Line> lines = new ArrayList<>();
    private final Map<String, Integer> labelmap = new HashMap<>();

    public Parser(InputStream is) throws IOException {
        this(new InputStreamReader(is));
    }

    public Parser(Reader r) throws IOException {
        BufferedReader br = new BufferedReader(r);
        String whlline = null;
        for (int lnnum = 1, lnindex = 0; (whlline = br.readLine()) != null; ++lnnum) {
            Line nln = newLine(lnnum, whlline);
            if (nln == null)
                continue;
            lines.add(nln);
            adjustLabelMap(nln, lnindex);
            lnindex++;
        }
    }

    public List<Line> getLines() {
        return lines;
    }

    public Map<String, Integer> getLabelMap() {
        return labelmap;
    }

    private void adjustLabelMap(Line line, int lnindex) {
        if (!line.hasLabel() || line.getOpCode().hasAlternateLabelPurpose())
            return;
        if (labelmap.containsKey(line.getLabel()))
            throw new IllegalArgumentException("Duplicate labels.");
        labelmap.put(line.getLabel(), lnindex);
    }

    private static Line newLine(int lnnum, String line) {
        if (line.trim().length() == 0) return null;
        String[] parts = splitp.split(line, 4);
        if (parts.length < 2)
            throw new RuntimeException("Every line must have at least an empty label and an opcode.");
        OpCode op = parseOpCode(parts[1]);
        String label = parts[0].length() == 0 ? null : parts[0];
        if (label == null) {
            if (op.requiresLabel())
                throw new IllegalArgumentException(op.name() + " requires a non-empty label.");
        } else {
            if (!Reference.isValidReference(label))
                throw new IllegalArgumentException(label + " is not a valid label name.");
        }
        Location loc = null;
        if (parts.length < 3) { // no location
            if (op.requiresLocation()) throw new IllegalArgumentException(op.name() + " requires a location.");
        } else {
            loc = parseLocation(op, parts[2]);
        }
        return new Line(label, op, loc, lnnum, line);
    }

    private static Location parseLocation(OpCode op, String locstr) {
        // assume locstr has len of at least 1
        if (Character.isLetter(locstr.codePointAt(0))) {
            if (op.requiresLiteral())
                throw new IllegalArgumentException(op.name() + " requires a literal.");
            return new Reference(locstr);
            // Reference constructor will throw rt exception if invalid name
        } else {
            if (!op.allowsLiteral()) {
                throw new IllegalArgumentException(op.name() + " does not accept literals.");
            }
            if (locstr.charAt(0) == '=') return new Literal(locstr.substring(1));
            else return new Literal(locstr);
        }
    }

    private static OpCode parseOpCode(String opstr) {
        try {
            return OpCode.valueOf(opstr.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Unknown opcode encountered.");
        }
    }
}
