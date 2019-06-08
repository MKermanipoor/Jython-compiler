package ver02.errorHandler;

class NotDefineVar extends Error {
    final String varName;

    public NotDefineVar(String varName, int line) {
        super(line, 108);
        this.varName = varName;
    }

    @Override
    public String getText() {
        return "Error" + code +
                ": in line " + line +
                ", Can not find Variable " + varName;
    }
}
