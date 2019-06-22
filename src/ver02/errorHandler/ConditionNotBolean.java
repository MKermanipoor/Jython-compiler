package ver02.errorHandler;

class ConditionNotBolean extends Error {
    public ConditionNotBolean(int line) {
        super(line, 220);
    }

    @Override
    public String getText() {
        return "Error " + code +
                ": in line " + line +
                ", Condition type must be Boolean.";
    }
}
