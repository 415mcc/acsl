public class Line {
    private String label;
    private OpCode op;
    private Location loc;
    private String line;
    private int lnnum;

    public Line(String label, OpCode op, Location loc, int lnnum, String line) {
        this.label = label;
        this.op = op;
        this.loc = loc;
        this.line = line;
        this.lnnum = lnnum;
    }

    public boolean hasLabel() {
        return label != null;
    }

    public boolean hasLocation() {
        return loc == null;
    }

    public String getLabel() {
        return label;
    }

    public OpCode getOpCode() {
        return op;
    }

    public Location getLocation() {
        return loc;
    }

    public String getFullLine() {
        return line;
    }

    public int getLineNumber() {
        return lnnum;
    }
}
