package ver02.listeners;

import gen.jythonParser;
import ver02.errorHandler.ErrorHandler;
import ver02.symbolTable.SymbolTable;
import ver02.symbolTable.subSybmolTable.SubClassSymbolTable;
import ver02.symbolTable.subSybmolTable.SubVarSymbolTable;

import java.util.Stack;

public class UseSymbolListener extends MainListener {

    Stack<SymbolTable> stack = new Stack<>();
    SymbolTable tempSymbolTable = null;

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

        if (varEntity == null) {
            errorHandler.notFindVar(varName, line);
        } else if (varEntity.getVarType() == SubVarSymbolTable.VarEntity.VarType.OBJECT) {
            SubClassSymbolTable.ClassEntity classEntity = masterSymbolTable.findClass(varEntity.getClassName());
            if (classEntity != null) {
                tempSymbolTable = masterSymbolTable.getChild(classEntity.getClassHash());
            }
        }
    }

    @Override
    public void exitLeftExp_subVarName(jythonParser.LeftExp_subVarNameContext ctx) {
        String varName = ctx.ID().getText();
        int line = ctx.start.getLine();

        if (tempSymbolTable == null) {
            errorHandler.notFindVar(varName, line);
        } else {
            SubVarSymbolTable.VarEntity varEntity = tempSymbolTable.findVar(varName);
            if (varEntity == null) {
                errorHandler.notFindVar(varName, line);
            } else if (varEntity.getVarType() == SubVarSymbolTable.VarEntity.VarType.OBJECT) {
                SubClassSymbolTable.ClassEntity classEntity = masterSymbolTable.findClass(varEntity.getClassName());
                if (classEntity != null) {
                    tempSymbolTable = masterSymbolTable.getChild(classEntity.getClassHash());
                }
            }
        }
    }

    @Override
    public void enterLeftExp_self(jythonParser.LeftExp_selfContext ctx) {
        stack.push(symbolTable);
        symbolTable = masterSymbolTable.getChild(getClassHash(className));
    }

    @Override
    public void exitLeftExp_self(jythonParser.LeftExp_selfContext ctx) {
        symbolTable = stack.pop();
    }
}
