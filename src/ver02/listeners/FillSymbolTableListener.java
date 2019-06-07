package ver02.listeners;

import gen.jythonParser;
import ver02.errorHandler.ErrorHandler;
import ver02.symbolTable.SymbolTable;

public class FillSymbolTableListener extends MainListener {

    public FillSymbolTableListener(String fileName, ErrorHandler errorHandler, SymbolTable masterSymbolTable) {
        super(fileName, errorHandler, masterSymbolTable);
    }

    @Override
    public void enterImportclass(jythonParser.ImportclassContext ctx) {
        String className = ctx.USER_TYPE().getText();
        int line = ctx.start.getLine();
        if (importClass.contains(className)) {
            errorHandler.doubleImportClass(className, line);
        } else {
            importClass.add(className);
        }
    }

    @Override
    public void enterClassDec(jythonParser.ClassDecContext ctx) {
        String className = ctx.USER_TYPE(0).getText();
        int line = ctx.start.getLine();

        if (masterSymbolTable.findClass(className) != null) {
            errorHandler.doubleDefineClass(className, line);
        } else {
            symbolTable.addClassEntity(className, line);
        }

        super.enterClassDec(ctx);
    }
}
