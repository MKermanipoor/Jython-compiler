

import gen.jythonBaseListener;


import gen.jythonParser;
import symbolTable.*;

public class myListener extends jythonBaseListener {

    private SymbolTable symbolTable = new SymbolTable();
    private String currentClassName;
    @Override
    public void enterClassDec(jythonParser.ClassDecContext ctx) {
        String className = ctx.USER_TYPE(0).getText();
        SympolTableCalssEntity entity;
        int line=ctx.start.getLine();
        currentClassName=className;
        if (ctx.USER_TYPE().size() > 1){
            entity = new SympolTableCalssEntity(ctx.USER_TYPE(1).getText());
        }else{
            // TODO: 5/16/2019  parent name
            entity = new SympolTableCalssEntity(line,"");
        }
        symbolTable.add(className, entity);

        symbolTable = symbolTable.createChild();
    }

    @Override
    public void exitClassDec(jythonParser.ClassDecContext ctx) {
        symbolTable = symbolTable.getParent();
        currentClassName="";
    }

    @Override
    public void exitVarDec(jythonParser.VarDecContext ctx) {
        String name = ctx.ID().getText();
        int line=ctx.start.getLine();
        SymbolTableVarEntity entity = null;
        if (ctx.type().USER_TYPE() != null){
            entity = new SymbolTableVarEntity(VariableType.OBJECT,line);
        }else{
            switch (ctx.type().jythonType().getText()){
                case "float":
                    entity = new SymbolTableVarEntity(VariableType.FLOAT,line);
                    break;
                case "int":
                    entity = new SymbolTableVarEntity(VariableType.INTEGER,line);
                    break;
                case "bool":
                    entity = new SymbolTableVarEntity(VariableType.BOOLEAN,line);
                    break;
                case "string":
                    entity = new SymbolTableVarEntity(VariableType.STRING,line);
                    break;
            }
        }
        symbolTable.add(name, entity);

    }

    @Override
    public void enterWhile_statment(jythonParser.While_statmentContext ctx) {
        symbolTable=symbolTable.createChild();
    }

    @Override
    public void exitWhile_statment(jythonParser.While_statmentContext ctx) {
        symbolTable=symbolTable.getParent();
    }

    @Override
    public void enterFor_statment(jythonParser.For_statmentContext ctx) {
        symbolTable=symbolTable.createChild();
        int line=ctx.start.getLine();
        
        String name=ctx.ID().getText();
        
        boolean addSymbolTableResult;
        if(ctx.getChild(3).getText().equals("range")){
            SymbolTableVarEntity symbolTableVarEntity=new SymbolTableVarEntity(VariableType.INTEGER,line);
            addSymbolTableResult=symbolTable.add(name,symbolTableVarEntity);
            
        }
        else {
            // TODO: skewfield 16 May 2019 (Logic) array bod ye kari bokone
            addSymbolTableResult=false;
            
        }

        if(!addSymbolTableResult){
                ErrorHandler.doubleVarDefinition(line,name);
        }
    }

    @Override
    public void exitFor_statment(jythonParser.For_statmentContext ctx) {
        symbolTable=symbolTable.getParent();
    }

    @Override
    public void enterIf_else_statment(jythonParser.If_else_statmentContext ctx) {
        symbolTable=symbolTable.createChild();

    }

    @Override
    public void exitIf_else_statment(jythonParser.If_else_statmentContext ctx) {
        symbolTable=symbolTable.getParent();
    }

    @Override
    public void enterMethodDec(jythonParser.MethodDecContext ctx) {
        String name=ctx.ID().getText();
        int line=ctx.start.getLine();
        VariableType returnType = null;
        if(ctx.getChild(1).getText().equals("void")){
            returnType =VariableType.VOID;
        }
        else {
            if(ctx.getChild(2).getText().equals("[")){
                returnType=VariableType.ARRAY;
            }
            else {
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
                        returnType=VariableType.OBJECT;
                        break;
                }
            }
        }

        SymbolTableMethodEntity symbolTableMethodEntity=new SymbolTableMethodEntity(returnType,line);
        for (jythonParser.ParameterContext parameterContext:ctx.parameters().parameter()) {
            VariableType type = null;
            if (parameterContext.varDec()!=null){

                if (parameterContext.varDec().type().USER_TYPE() != null){
                  type=VariableType.OBJECT;
                }else{
                    switch (parameterContext.varDec().type().jythonType().getText()){
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
                
            }
            else {
                type=VariableType.ARRAY;
            }
            symbolTableMethodEntity.addParam(type);
        }
        if(!symbolTable.add(name, symbolTableMethodEntity)){
            ErrorHandler.doubleMethodDefinition(line,name,currentClassName);
        }
        symbolTable=symbolTable.createChild();

    }

    @Override
    public void exitMethodDec(jythonParser.MethodDecContext ctx) {
        symbolTable=symbolTable.getParent();

    }

    @Override
    public void enterConstructor(jythonParser.ConstructorContext ctx) {
        symbolTable=symbolTable.createChild();
    }

    @Override
    public void exitConstructor(jythonParser.ConstructorContext ctx) {
        symbolTable=symbolTable.getParent();
    }
}
