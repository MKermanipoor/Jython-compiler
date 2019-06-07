package ver02.errorHandler;

class DoubleClassDefine extends Error{
    final String className;

    public DoubleClassDefine(String className ,int line) {
        super(line, 101);
        this.className = className;
    }

    @Override
    public String getText() {
        return "Error" + code +
                ": in line " + line +
                ", " + className +
                " class has been defined already";
    }
}
