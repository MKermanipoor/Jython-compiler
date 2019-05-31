

import gen.jythonBaseListener;


import gen.jythonParser;
import symbolTable.*;

public class myListener extends jythonBaseListener {

    private SymbolTable symbolTable;
    private String currentClassName;
    private boolean lineOrder = false;
    private boolean haveMain=false;

    public myListener(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    @Override
    public void enterClassDec(jythonParser.ClassDecContext ctx) {
        String className = ctx.USER_TYPE(0).getText();
        SymbolTableCalssEntity entity;
        int line = ctx.start.getLine();
        currentClassName = className;
        SymbolTableEntity find = symbolTable.getSymbolTableEntity(className);
        if (find instanceof SymbolTableCalssEntity){
            ErrorHandler.doubleClassDefinition(line,className,symbolTable);
        }else {
            if (ctx.USER_TYPE().size() > 1) {
                entity = new SymbolTableCalssEntity(ctx.USER_TYPE(1).getText());
            } else {
                // TODO: 5/16/2019  parent name
                entity = new SymbolTableCalssEntity(line, "");
            }
            symbolTable.add(className, entity);
        }
        symbolTable = symbolTable.createChild();
    }

    @Override
    public void exitClassDec(jythonParser.ClassDecContext ctx) {
        symbolTable = symbolTable.getParent();
        currentClassName = "";
    }

    @Override
    public void exitVarDec(jythonParser.VarDecContext ctx) {
        String name = ctx.ID().getText();
        int line = ctx.start.getLine();
        SymbolTableVarEntity entity = null;

        if (symbolTable.containsVarOrArray(name)) {
            ErrorHandler.doubleVarDefinition(line, name, symbolTable);
            return;
        }

        if (ctx.type().USER_TYPE() != null) {
            entity = new SymbolTableVarEntity(VariableType.OBJECT, line, lineOrder);
        } else {
            switch (ctx.type().jythonType().getText()) {
                case "float":
                    entity = new SymbolTableVarEntity(VariableType.FLOAT, line, lineOrder);
                    break;
                case "int":
                    entity = new SymbolTableVarEntity(VariableType.INTEGER, line, lineOrder);
                    break;
                case "bool":
                    entity = new SymbolTableVarEntity(VariableType.BOOLEAN, line, lineOrder);
                    break;
                case "string":
                    entity = new SymbolTableVarEntity(VariableType.STRING, line, lineOrder);
                    break;
            }
        }
        symbolTable.add(name, entity);
    }

    @Override
    public void exitArrayDec(jythonParser.ArrayDecContext ctx) {
        String name = ctx.ID().getText();
        int line = ctx.start.getLine();

        if (symbolTable.containsVarOrArray(name)){
            ErrorHandler.doubleVarDefinition(line, name, symbolTable);
            return;
        }

        SymbolTableArrayEntity entity = null;

        if (ctx.type().USER_TYPE() != null) {
            entity = new SymbolTableArrayEntity(VariableType.OBJECT, line);
        } else {
            switch (ctx.type().jythonType().getText()) {
                case "float":
                    entity = new SymbolTableArrayEntity(VariableType.FLOAT, line);
                    break;
                case "int":
                    entity = new SymbolTableArrayEntity(VariableType.INTEGER, line);
                    break;
                case "bool":
                    entity = new SymbolTableArrayEntity(VariableType.BOOLEAN, line);
                    break;
                case "string":
                    entity = new SymbolTableArrayEntity(VariableType.STRING, line);
                    break;
            }
        }
        symbolTable.add(name, entity);
    }

    @Override
    public void enterWhile_statment(jythonParser.While_statmentContext ctx) {
        symbolTable = symbolTable.createChild();
    }

    @Override
    public void exitWhile_statment(jythonParser.While_statmentContext ctx) {
        symbolTable = symbolTable.getParent();
    }

    @Override
    public void enterFor_statment(jythonParser.For_statmentContext ctx) {
        symbolTable = symbolTable.createChild();
        int line = ctx.start.getLine();

        String name = ctx.ID().getText();

        boolean addSymbolTableResult;
        if (ctx.getChild(3).getText().equals("range")) {
            SymbolTableVarEntity symbolTableVarEntity = new SymbolTableVarEntity(VariableType.INTEGER, line, lineOrder);
            addSymbolTableResult = symbolTable.add(name, symbolTableVarEntity);

        } else {
            // TODO: skewfield 16 May 2019 (Logic) array bod ye kari bokone
            addSymbolTableResult = true;
        }

        if (!addSymbolTableResult) {
            ErrorHandler.doubleVarDefinition(line, name, symbolTable);
        }
    }

    @Override
    public void exitFor_statment(jythonParser.For_statmentContext ctx) {
        symbolTable = symbolTable.getParent();
    }

    @Override
    public void enterIf_else_statment(jythonParser.If_else_statmentContext ctx) {
        symbolTable = symbolTable.createChild();

    }

    @Override
    public void exitIf_else_statment(jythonParser.If_else_statmentContext ctx) {
        symbolTable = symbolTable.getParent();
    }

    @Override
    public void enterMethodDec(jythonParser.MethodDecContext ctx) {
        try {
            String name = ctx.ID().getText();
            int line = ctx.start.getLine();

            if (name.equals("print")) {
                ErrorHandler.redefinePrint(line, symbolTable);
                return;
            }
            if (name.equals("main")){
                haveMain=true;
            }

            VariableType returnType = null;
            if (ctx.getChild(1).getText().equals("void")) {
                returnType = VariableType.VOID;
            } else {
                if (ctx.getChild(2).getText().equals("[")) {
                    returnType = VariableType.ARRAY;
                } else {
                    switch (ctx.type().getText()) {
                        case "float":
                            returnType = VariableType.FLOAT;
                            break;
                        case "int":
                            returnType = VariableType.INTEGER;
                            break;
                        case "bool":
                            returnType = VariableType.BOOLEAN;
                            break;
                        case "string":
                            returnType = VariableType.STRING;
                            break;
                        default:
                            returnType = VariableType.OBJECT;
                            break;
                    }
                }
            }

            SymbolTableMethodEntity symbolTableMethodEntity = new SymbolTableMethodEntity(returnType, line);
            for (jythonParser.ParameterContext parameterContext : ctx.parameters().parameter()) {
                VariableType type = null;
                if (parameterContext.varDec() != null) {

                    if (parameterContext.varDec().type().USER_TYPE() != null) {
                        type = VariableType.OBJECT;
                    } else {
                        switch (parameterContext.varDec().type().jythonType().getText()) {
                            case "float":
                                type = VariableType.FLOAT;
                                break;
                            case "int":
                                type = VariableType.INTEGER;
                                break;
                            case "bool":
                                type = VariableType.BOOLEAN;
                                break;
                            case "string":
                                type = VariableType.STRING;
                                break;
                        }
                    }

                } else {
                    type = VariableType.ARRAY;
                }
                symbolTableMethodEntity.addParam(type);
            }
            if (!symbolTable.add(name, symbolTableMethodEntity)) {
                ErrorHandler.doubleMethodDefinition(line, name, currentClassName, symbolTable);
            }
        } finally {
            symbolTable = symbolTable.createChild();
            lineOrder = true;
        }


    }

    @Override
    public void exitMethodDec(jythonParser.MethodDecContext ctx) {
        symbolTable = symbolTable.getParent();
        lineOrder = false;
    }

    @Override
    public void enterConstructor(jythonParser.ConstructorContext ctx) {
        symbolTable = symbolTable.createChild();
        lineOrder = true;
    }

    @Override
    public void exitConstructor(jythonParser.ConstructorContext ctx) {
        symbolTable = symbolTable.getParent();
        lineOrder = false;
    }

    @Override
    public void exitLeftExp(jythonParser.LeftExpContext ctx) {
        if (ctx.getChildCount() == 1 && ctx.ID() != null) {
            String name = ctx.ID().getText();
            SymbolTableEntity symbolTableEntity = symbolTable.getSymbolTableEntity(name);
            if (!(symbolTableEntity instanceof SymbolTableVarEntity) && !(symbolTableEntity instanceof SymbolTableArrayEntity)) {

                ErrorHandler.notDefineVariable(ctx.start.getLine(), name, symbolTable);
            }
        }
    }

    @Override
    public void exitMethod_call(jythonParser.Method_callContext ctx) {
        if (ctx.getChildCount() == 2) {
            String name = ctx.ID().getText();
            if (!(symbolTable.getSymbolTableEntity(name) instanceof SymbolTableMethodEntity)) {

                ErrorHandler.notDefineMethod(ctx.start.getLine(), name, symbolTable, ctx);
            } else {
                SymbolTableMethodEntity symbolTableMethodEntity = (SymbolTableMethodEntity) symbolTable.getSymbolTableEntity(name);
                int passSize = ((ctx.args().explist() == null)) ? 0 : ctx.args().explist().expression().size();

                if (symbolTableMethodEntity.parameterSize() != passSize) {

                    ErrorHandler.notDefineMethod(ctx.start.getLine(), name, symbolTable, ctx);
                }


            }

        }
    }

    @Override
    public void exitProgram(jythonParser.ProgramContext ctx) {
        if(!haveMain)
            System.err.println("Error104: Can not find main method");
        ErrorHandler.printAll(symbolTable);
        ErrorHandler.reset();
    }


}
