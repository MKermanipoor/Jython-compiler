package ver02.errorHandler;

class DoubleImporrtClass extends Error {
    final String className;

    public DoubleImporrtClass(String className, int line) {
        super(line, 301);
        this.className = className;
    }

    @Override
    public String getText() {
        return "Error" + code +
                ": in line " + line +
                ", " + className +
                " has been imported already in this file";
    }
}
