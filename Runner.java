import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Runner {
    private static final int MILLION = 1000000; // better off doing this than tracking down missing zero error
    private final Scanner ins;
    private final PrintStream outs;
    private final Line[] lines;
    private final Map<String, Integer> labelmap;
    private final Map<String, Integer> varmap = new HashMap<>();
    private int acc = 0; // whether ACC has a starting value is not mentioned in ACSL document

    public Runner(Parser parser, InputStream ins, PrintStream outs) {
        this.lines = toArray(parser.getLines());
        this.labelmap = parser.getLabelMap();
        this.ins = new Scanner(ins);
        this.outs = outs;
    }

    public Runner(Parser parser, InputStream ins) {
        this(parser, ins, System.out);
    }

    public Runner(Parser parser, PrintStream outs) {
        this(parser, System.in, outs);
    }

    public Runner(Parser parser) {
        this(parser, System.in, System.out);
    }

    public void run() {
        for (int lnindex = 0; lnindex < lines.length;) {
            boolean increment = true;
            Line line = lines[lnindex];
            OpCode op = line.getOpCode();
            Location loc = line.getLocation();
            String label = line.getLabel();
            switch (line.getOpCode()) {
            case LOAD:
                this.acc = loc.value(this.varmap);
                break;
            case STORE:
                this.varmap.put(safeRefCast(loc, op).getLocationName(), this.acc);
                break;
            case ADD:
                this.acc = (this.acc + loc.value(this.varmap)) % MILLION;
                break;
            case SUB:
                this.acc = (this.acc - loc.value(this.varmap)) % MILLION;
                break;
            case MULT:
                this.acc = (this.acc * loc.value(this.varmap)) % MILLION;
                break;
            case DIV:
                this.acc = this.acc / loc.value(this.varmap);
                break;
            case BE:
                if (this.acc == 0) {
                    lnindex = loc.value(this.labelmap);
                    increment = false;
                }
                break;
            case BG:
                if (this.acc > 0) {
                    lnindex = loc.value(this.labelmap);
                    increment = false;
                }
                break;
            case BL:
                if (this.acc < 0) {
                    lnindex = loc.value(this.labelmap);
                    increment = false;
                }
                break;
            case BU:
                lnindex = loc.value(this.labelmap);
                increment = false;
                break;
            case END:
                return;
            case READ:
                try {
                    this.varmap.put(safeRefCast(loc, op).getLocationName(), this.ins.nextInt());
                } catch (NumberFormatException ex) {
                    throw new NumberFormatException("READ did not receive a valid integer.");
                }
                break;
            case PRINT:
                this.outs.println(loc.value(this.varmap));
                break;
            case DC:
                this.varmap.put(label, loc.value(null)); // only takes literals so does not need varmap
                break;
            default:
                throw new UnsupportedOperationException("Opcode not implemented yet.");
            }
            if (increment)
                lnindex++;
        }
    }

    private static Reference safeRefCast(Location loc, OpCode op) {
        // for use with opcodes that cannot accept locations that are literals
        // Parser and OpCode should prevent this from ever throwing anything
        if (loc.isLiteral())
            throw new IllegalArgumentException(op.name() + " opcode cannot operate on a literal.");
        return (Reference) loc;
    }

    private static Line[] toArray(List<Line> linelist) {
        Object[] objarr = linelist.toArray();
        Line[] linearr = new Line[objarr.length];
        for (int i = 0; i < objarr.length; ++i) {
            linearr[i] = (Line) objarr[i];
        }
        return linearr;
    }
}
