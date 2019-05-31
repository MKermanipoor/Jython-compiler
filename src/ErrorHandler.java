import gen.jythonParser;
import symbolTable.SymbolTable;
import symbolTable.SymbolTableEntity;
import symbolTable.SymbolTableMethodEntity;
import symbolTable.SymbolTableVarEntity;

import java.util.ArrayList;

public class ErrorHandler {
    private static ArrayList<ErrorEntity> errorList = new ArrayList<>();

    public static void doubleClassDefinition(int line, String name, SymbolTable symbolTable) {
        String text = "Error101: in line " + line + " , " + name + " has been defined already in current scope";
        errorList.add(new ErrorEntity(text, symbolTable, ErrorEntity.ErrorType.NOT_MATTER));
    }
    public static void notFindClass(int line, String name, SymbolTable symbolTable) {
        String text = "Error106: in line " + line + " , cannot find class " + name ;
        errorList.add(new ErrorEntity(text, symbolTable, ErrorEntity.ErrorType.NOT_MATTER));
    }
    public static void doubleImportDefinition(int line, String name, SymbolTable symbolTable) {
        String text = "Error110: in line " + line + " , " + name + " has been imported already";
        errorList.add(new ErrorEntity(text, symbolTable, ErrorEntity.ErrorType.NOT_MATTER));
    }

    public static void doubleVarDefinition(int line, String name, SymbolTable symbolTable) {
        String text = "Error103: in line " + line + " , " + name + " has been defined already in current scope";
        errorList.add(new ErrorEntity(text, symbolTable, ErrorEntity.ErrorType.NOT_MATTER));
    }

    public static void doubleMethodDefinition(int line, String name, String className, SymbolTable symbolTable) {

        String text = "Error102: in line " + line + " , " + name + " has been defined already in " + className;
        errorList.add(new ErrorEntity(text, symbolTable, ErrorEntity.ErrorType.NOT_MATTER));
    }

    public static void notDefineVariable(int line, String name, SymbolTable symbolTable) {
        String text = "Error108: in line " + line + " , Can not find Variable " + name;
        errorList.add(new ErrorEntity(text, name, symbolTable, ErrorEntity.ErrorType.NOT_DEFINE_VARIABLE));

    }

    public static void notDefineMethod(int line, String name, SymbolTable symbolTable, jythonParser.Method_callContext ctx) {

        String text = "Error105: in line " + line + " , cannot find method " + name;
        errorList.add(new ErrorEntity(text, name, symbolTable, ErrorEntity.ErrorType.NOT_DEFINE_METHOD, ctx));

    }
    public static void notDefineMainMethod() {

        String text = "Error104: Can not find main method";
        errorList.add(new ErrorEntity(text, null, null, ErrorEntity.ErrorType.NOT_MATTER));

    }

    public static void redefinePrint(int line, SymbolTable symbolTable) {
        String text = "Error109: in line " + line + " , Definition of print as method of a class";
        errorList.add(new ErrorEntity(text, symbolTable, ErrorEntity.ErrorType.NOT_MATTER));
    }

    public static void printAll(SymbolTable root, String fileName) {
        // check not define variable and method
        StringBuilder stringBuilder = new StringBuilder();
        main_for:
        for (ErrorEntity errorEntity : errorList) {
            SymbolTable s = errorEntity.symbolTable;

            switch (errorEntity.errorType) {
                case NOT_DEFINE_METHOD:
                    SymbolTableEntity symbolTableEntity = s.getSymbolTableEntity(errorEntity.name);
                    if (symbolTableEntity instanceof SymbolTableMethodEntity && symbolTableEntity.isValid()) {
                        jythonParser.Method_callContext ctx = errorEntity.ctx;
                        int passSize = ((ctx.args().explist() == null)) ? 0 : ctx.args().explist().expression().size();

                        if (((SymbolTableMethodEntity) symbolTableEntity).parameterSize() == passSize)
                            continue;
                    }
                    break;
                case NOT_DEFINE_VARIABLE:
                    SymbolTableEntity symbolVarTableEntity = s.getSymbolTableEntity(errorEntity.name);
                    SymbolTable temp = s;
                    while ((symbolVarTableEntity instanceof SymbolTableVarEntity && symbolVarTableEntity.isValid())) {
                        if (!((SymbolTableVarEntity) symbolVarTableEntity).isLineOrder())
                            continue main_for;
                        temp = temp.getParent();
                        if (temp == null)
                            break;

                        symbolVarTableEntity = temp.getSymbolTableEntity(errorEntity.name);
                    }
                    break;
            }
            stringBuilder.append("\t").append(errorEntity.text).append("\n");
        }

        // check duplicate var definition after parse
        checkDuplicateVar(root, stringBuilder);

        if (!stringBuilder.toString().isEmpty()){
            System.err.println("Error(s) in file " + fileName + ":");
            System.err.println(stringBuilder.toString());
        }
    }

    private static void checkDuplicateVar(SymbolTable symbolTable, StringBuilder stringBuilder){
        SymbolTable parent = symbolTable.getParent();
        if (parent != null) {
            symbolTable.getMap().forEach((s, symbolTableEntity) -> {
                if (parent.containsVarOrArray(s))
                    stringBuilder.append("\t").append("Error103: in line ").append(symbolTableEntity.getLineDefenitaion()).append(" , ").append(s).append(" has been defined already in current scope").append("\n");

            });
        }

        for (SymbolTable child : symbolTable.getChilds()){
            checkDuplicateVar(child, stringBuilder);
        }
    }

    static class ErrorEntity {
        enum ErrorType {
            NOT_MATTER, NOT_DEFINE_VARIABLE, NOT_DEFINE_METHOD
        }

        String text;
        String name;
        SymbolTable symbolTable;
        ErrorType errorType;
        jythonParser.Method_callContext ctx;

        public ErrorEntity(String text, SymbolTable symbolTable, ErrorType errorType) {
            this.text = text;
            this.symbolTable = symbolTable;
            this.errorType = errorType;
        }

        public ErrorEntity(String text, String name, SymbolTable symbolTable, ErrorType errorType) {
            this.text = text;
            this.name = name;
            this.symbolTable = symbolTable;
            this.errorType = errorType;
        }

        public ErrorEntity(String text, String name, SymbolTable symbolTable, ErrorType errorType, jythonParser.Method_callContext ctx) {
            this.text = text;
            this.name = name;
            this.symbolTable = symbolTable;
            this.errorType = errorType;
            this.ctx = ctx;
        }
    }

    public static void reset(){
        errorList.clear();
    }
}
