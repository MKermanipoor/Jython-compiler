package ver02.errorHandler;

class DoubleVarDefine extends Error {
    final String variableName;

    public DoubleVarDefine(String variableName, int line) {
        super(line, 103);
        this.variableName = variableName;
    }

    @Override
    public String getText() {
        return "Error" + code +
                ": in line " + line +
                ", " + variableName +
                " has been defined already in current scope";
    }
}
