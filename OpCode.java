public enum OpCode {
    LOAD  (true,  false, false, true,  false),
    STORE (false, false, false, true,  false),
    ADD   (true,  false, false, true,  false),
    SUB   (true,  false, false, true,  false),
    MULT  (true,  false, false, true,  false),
    DIV   (true,  false, false, true,  false),
    BE    (false, false, false, true,  false),
    BG    (false, false, false, true,  false),
    BL    (false, false, false, true,  false),
    BU    (false, false, false, true,  false),
    END   (false, false, false, false, false),
    READ  (false, false, false, true,  false),
    PRINT (true,  false, false, true,  false),
    DC    (true,  true,  true,  true,  true);
    private final boolean allowsLiteral;
    private final boolean requiresLabel;
    private final boolean requiresLiteral;
    private final boolean requiresLocation;
    private final boolean alternateLabelPurpose;
    OpCode(boolean allowsLiteral, boolean requiresLabel, boolean requiresLiteral, boolean requiresLocation, boolean alternateLabelPurpose) {
        this.allowsLiteral = allowsLiteral;
        this.requiresLabel = requiresLabel;
        this.requiresLiteral = requiresLiteral;
        this.requiresLocation = requiresLocation;
        this.alternateLabelPurpose = alternateLabelPurpose;
    }
    public boolean allowsLiteral() { return allowsLiteral; }
    public boolean requiresLabel() { return requiresLabel; }
    public boolean requiresLiteral() { return requiresLiteral; }
    public boolean requiresLocation() { return requiresLocation; }
    public boolean hasAlternateLabelPurpose() { return alternateLabelPurpose; }
}
