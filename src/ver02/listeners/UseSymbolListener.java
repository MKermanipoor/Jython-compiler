package ver02.listeners;

import gen.jythonParser;
import ver02.errorHandler.ErrorHandler;
import ver02.symbolTable.SymbolTable;
import ver02.symbolTable.subSybmolTable.SubVarSymbolTable;

public class UseSymbolListener extends MainListener {
    public UseSymbolListener(String fileName, ErrorHandler errorHandler, SymbolTable masterSymbolTable) {
        super(fileName, errorHandler, masterSymbolTable);
    }

    @Override
    public void enterImportclass(jythonParser.ImportclassContext ctx) {
        String className = ctx.USER_TYPE().getText();
        int line = ctx.start.getLine();
        if (masterSymbolTable.findClass(className) == null)
            errorHandler.notFindClass(className, line);
    }

    @Override
    public void exitLeftExp_varName(jythonParser.LeftExp_varNameContext ctx) {
        String varName = ctx.ID().getText();
        int line = ctx.start.getLine();

        SubVarSymbolTable.VarEntity varEntity = symbolTable.findVar(varName);

        if (varEntity == null)
            errorHandler.notFindVar(varName, line);
    }
}
