import gen.jythonParser;
import symbolTable.SymbolTable;
import symbolTable.SymbolTableEntity;
import symbolTable.SymbolTableMethodEntity;
import symbolTable.SymbolTableVarEntity;

import java.util.ArrayList;

public class ErrorHandler {
    private static ArrayList<ErrorEntity> errorList = new ArrayList<>();

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

    public static void redefinePrint(int line, SymbolTable symbolTable) {
        String text = "Error109: in line " + line + " , Definition of print as method of a class";
        errorList.add(new ErrorEntity(text, symbolTable, ErrorEntity.ErrorType.NOT_MATTER));
    }

    public static void printAll(SymbolTable root) {
        // check not define variable and method
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
                    if (symbolVarTableEntity instanceof SymbolTableVarEntity && symbolVarTableEntity.isValid()) {
                        if (!((SymbolTableVarEntity) symbolVarTableEntity).isLineOrder())
                            continue;
                    }
                    break;
            }
            System.err.println(errorEntity.text);
        }

        // check duplicate var definition after parse
        checkDuplicateVar(root);
    }

    private static void checkDuplicateVar(SymbolTable symbolTable){
        SymbolTable parent = symbolTable.getParent();
        if (parent != null) {
            symbolTable.getMap().forEach((s, symbolTableEntity) -> {
                if (parent.containsVarOrArray(s))
                    System.err.println("Error103: in line " + symbolTableEntity.getLineDefenitaion() + " , " + s + " has been defined already in current scope");

            });
        }

        for (SymbolTable child : symbolTable.getChilds()){
            checkDuplicateVar(child);
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
