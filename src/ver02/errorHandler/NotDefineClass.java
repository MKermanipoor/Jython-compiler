package ver02.errorHandler;

class NotDefineClass extends Error {
    final String className;

    public NotDefineClass(String className, int line) {
        super(line, 302);
        this.className = className;
    }

    @Override
    public String getText() {
        return "Error" + code +
                ": in line " + line +
                ", Can not find Class " + className ;
    }
}
