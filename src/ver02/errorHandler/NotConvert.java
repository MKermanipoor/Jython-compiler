package ver02.errorHandler;

class NotConvert extends Error {
    private final String leftType;
    private final String rightType;


    public NotConvert(String leftType, String rightType, int line) {
        super(line, 250);
        this.leftType = leftType;
        this.rightType = rightType;
    }

    @Override
    public String getText() {
        return "Error " + code +
                " : in line " + line +
                ", Incompatible types : " + leftType +
                " can not be converted to " + rightType +
                ".";
    }
}
