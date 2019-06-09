package ver02.listeners;

import gen.jythonParser;
import ver02.errorHandler.ErrorHandler;
import ver02.symbolTable.SymbolTable;
import ver02.symbolTable.subSybmolTable.SubVarSymbolTable;

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
            symbolTable.addClassEntity(className, line, getClassHash(className));
        }

        super.enterClassDec(ctx);
    }

    @Override
    public void exitVarDec(jythonParser.VarDecContext ctx) {
        String name = ctx.ID().toString();
        int line = ctx.start.getLine();

        SubVarSymbolTable.VarEntity varEntity = symbolTable.findVar(name);
        // TODO Masoud 6/8/2019: (Logic) create array instance of now class

        if (isInMethod()) {
            if (varEntity != null && !varEntity.isAttribute()) {
                errorHandler.doubleDefineVarriable(name, line);
                if (ctx.type().jythonType() == null) {
                    String className = ctx.type().USER_TYPE().toString();
                    if (!importClass.contains(className) && !className.equals(this.className))
                        errorHandler.notFindClass(className, line);
                }
                return;
            }

            if (ctx.type().jythonType() == null) {
                String className = ctx.type().USER_TYPE().toString();
                if (!importClass.contains(className) && !className.equals(this.className)) {
                    errorHandler.notFindClass(className, line);
                    return;
                }
                symbolTable.addVarEntity(name, className, line, false);
            } else {
                SubVarSymbolTable.VarEntity.VarType type = SubVarSymbolTable.VarEntity.VarType.get(ctx.type().jythonType().getText());
                symbolTable.addVarEntity(name, type, line, false);
            }

        } else {
            if (varEntity != null) {
                errorHandler.doubleDefineVarriable(name, line);
                if (ctx.type().jythonType() == null) {
                    String className = ctx.type().USER_TYPE().toString();
                    if (!importClass.contains(className) && !className.equals(this.className))
                        errorHandler.notFindClass(className, line);
                }
                return;
            }

            if (ctx.type().jythonType() == null) {
                String className = ctx.type().USER_TYPE().toString();
                if (!importClass.contains(className) && !className.equals(this.className)) {
                    errorHandler.notFindClass(className, line);
                    return;
                }
                symbolTable.addAttributeEntity(name, className, line, false);
            } else {
                SubVarSymbolTable.VarEntity.VarType type = SubVarSymbolTable.VarEntity.VarType.get(ctx.type().jythonType().getText());
                symbolTable.addAttributeEntity(name, type, line, false);
            }

        }

    }

    @Override
    public void exitArrayDec(jythonParser.ArrayDecContext ctx) {
        String name = ctx.ID().toString();
        int line = ctx.start.getLine();

        SubVarSymbolTable.VarEntity varEntity = symbolTable.findVar(name);
        // TODO Masoud 6/8/2019: (Logic) create array instance of now class
        if (isInMethod()) {
            if (varEntity != null && !varEntity.isAttribute()) {
                errorHandler.doubleDefineVarriable(name, line);
                if (ctx.type().jythonType() == null) {
                    String className = ctx.type().USER_TYPE().toString();
                    if (!importClass.contains(className) && !className.equals(this.className))
                        errorHandler.notFindClass(className, line);
                }
                return;
            }

            if (ctx.type().jythonType() == null) {
                String className = ctx.type().USER_TYPE().toString();
                if (!importClass.contains(className) && !className.equals(this.className)) {
                    errorHandler.notFindClass(className, line);
                    return;
                }
                symbolTable.addVarEntity(name, className, line, true);
            } else {
                SubVarSymbolTable.VarEntity.VarType type = SubVarSymbolTable.VarEntity.VarType.get(ctx.type().jythonType().getText());
                symbolTable.addVarEntity(name, type, line, true);
            }

        } else {
            if (varEntity != null) {
                errorHandler.doubleDefineVarriable(name, line);
                if (ctx.type().jythonType() == null) {
                    String className = ctx.type().USER_TYPE().toString();
                    if (!importClass.contains(className) && !className.equals(this.className))
                        errorHandler.notFindClass(className, line);
                }
                return;
            }

            if (ctx.type().jythonType() == null) {
                String className = ctx.type().USER_TYPE().toString();
                if (!importClass.contains(className) && !className.equals(this.className)) {
                    errorHandler.notFindClass(className, line);
                    return;
                }
                symbolTable.addAttributeEntity(name, className, line, true);
            } else {
                SubVarSymbolTable.VarEntity.VarType type = SubVarSymbolTable.VarEntity.VarType.get(ctx.type().jythonType().getText());
                symbolTable.addAttributeEntity(name, type, line, true);
            }

        }
    }
}
