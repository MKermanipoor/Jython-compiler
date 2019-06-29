package ver02.listeners;

import gen.jythonParser;
import ver02.errorHandler.ErrorHandler;
import ver02.symbolTable.SymbolTable;
import ver02.symbolTable.subSybmolTable.SubMethodSymbolTable;
import ver02.symbolTable.subSybmolTable.SubMethodSymbolTable.MethodEntity.ReturnType;
import ver02.symbolTable.subSybmolTable.SubVarSymbolTable;
import ver02.symbolTable.subSybmolTable.SubVarSymbolTable.VarEntity.VarType;

import java.util.ArrayList;

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
        String parentName = "";
        if (ctx.USER_TYPE().size() > 1){
            parentName = ctx.USER_TYPE(1).getText();
        }

        if (masterSymbolTable.findClass(className) != null) {
            errorHandler.doubleDefineClass(className, line);
        } else if (parentName.isEmpty()){
            symbolTable.addClassEntity(className, line, getClassHash(className));
        } else{
            if (importClass.contains(parentName)){
                symbolTable.addClassEntity(className, line, getClassHash(className), parentName);
            }else{
                errorHandler.notFindClass(parentName, line);
            }
        }

        super.enterClassDec(ctx);
    }

    @Override
    public void exitVarDec(jythonParser.VarDecContext ctx) {
        String name = ctx.ID().toString();
        int line = ctx.start.getLine();

        SubVarSymbolTable.VarEntity varEntity = symbolTable.findVar(name);

        if (isInMethod()) {
            if (varEntity != null && !varEntity.isAttribute()) {
                errorHandler.doubleDefineVarriable(name, line);
                if (ctx.type().jythonType() == null) {
                    String className = ctx.type().USER_TYPE().toString();
                    if (!findClassName(className))
                        errorHandler.notFindClass(className, line);
                }
                return;
            }

            if (ctx.type().jythonType() == null) {
                String className = ctx.type().USER_TYPE().toString();
                if (!findClassName(className)) {
                    errorHandler.notFindClass(className, line);
                    return;
                }
                symbolTable.addVarEntity(name, className, line, false);
            } else {
                VarType type = VarType.get(ctx.type().jythonType().getText());
                symbolTable.addVarEntity(name, type, line, false);
            }

        } else {
            if (varEntity != null) {
                errorHandler.doubleDefineVarriable(name, line);
                if (ctx.type().jythonType() == null) {
                    String className = ctx.type().USER_TYPE().toString();
                    if (!findClassName(className))
                        errorHandler.notFindClass(className, line);
                }
                return;
            }

            if (ctx.type().jythonType() == null) {
                String className = ctx.type().USER_TYPE().toString();
                if (!findClassName(className)) {
                    errorHandler.notFindClass(className, line);
                    return;
                }
                symbolTable.addAttributeEntity(name, className, line, false);
            } else {
                VarType type = VarType.get(ctx.type().jythonType().getText());
                symbolTable.addAttributeEntity(name, type, line, false);
            }

        }

    }

    @Override
    public void exitArrayDec(jythonParser.ArrayDecContext ctx) {
        String name = ctx.ID().toString();
        int line = ctx.start.getLine();

        SubVarSymbolTable.VarEntity varEntity = symbolTable.findVar(name);

        if (isInMethod()) {
            if (varEntity != null && !varEntity.isAttribute()) {
                errorHandler.doubleDefineVarriable(name, line);
                if (ctx.type().jythonType() == null) {
                    String className = ctx.type().USER_TYPE().toString();
                    if (findClassName(className))
                        errorHandler.notFindClass(className, line);
                }
                return;
            }

            if (ctx.type().jythonType() == null) {
                String className = ctx.type().USER_TYPE().toString();
                if (findClassName(className)) {
                    errorHandler.notFindClass(className, line);
                    return;
                }
                symbolTable.addVarEntity(name, className, line, true);
            } else {
                VarType type = VarType.get(ctx.type().jythonType().getText());
                symbolTable.addVarEntity(name, type, line, true);
            }

        } else {
            if (varEntity != null) {
                errorHandler.doubleDefineVarriable(name, line);
                if (ctx.type().jythonType() == null) {
                    String className = ctx.type().USER_TYPE().toString();
                    if (findClassName(className))
                        errorHandler.notFindClass(className, line);
                }
                return;
            }

            if (ctx.type().jythonType() == null) {
                String className = ctx.type().USER_TYPE().toString();
                if (findClassName(className)) {
                    errorHandler.notFindClass(className, line);
                    return;
                }
                symbolTable.addAttributeEntity(name, className, line, true);
            } else {
                VarType type = VarType.get(ctx.type().jythonType().getText());
                symbolTable.addAttributeEntity(name, type, line, true);
            }

        }
    }

    @Override
    public void enterMethodDec(jythonParser.MethodDecContext ctx) {

        // create method entity info
        String methodName = ctx.ID().getText();
        int line = ctx.start.getLine();
        String className = "";
        ReturnType returnType;
        boolean arrayReturn;
        ArrayList<SubMethodSymbolTable.MethodEntity.InputInfo> inputType = new ArrayList<>();

        if (ctx.type() == null)
            returnType = ReturnType.VOID;
        else if (ctx.type().USER_TYPE() == null)
            returnType = ReturnType.get(ctx.type().jythonType().getText());
        else {
            returnType = ReturnType.OBJECT;
            className = ctx.type().USER_TYPE().getText();
        }

        arrayReturn = ctx.getChild(2).getText().startsWith("[");

        if (ctx.parameters() != null) {
            for (jythonParser.ParameterContext parameter : ctx.parameters().parameter()) {
                boolean array = false;
                String inputClassName = "";
                VarType varType;
                if (parameter.varDec() != null){
                    if(parameter.varDec().type().jythonType() != null){
                        varType = VarType.get(parameter.varDec().type().jythonType() .getText());
                    }else{
                        varType = VarType.OBJECT;
                        className = parameter.varDec().type().USER_TYPE().getText();
                    }
                }else{
                    array = true;
                    if(parameter.arrayDec().type().jythonType() != null){
                        varType = VarType.get(parameter.arrayDec().type().jythonType() .getText());
                    }else{
                        varType = VarType.OBJECT;
                        className = parameter.arrayDec().type().USER_TYPE().getText();
                    }
                }
                SubMethodSymbolTable.MethodEntity.InputInfo info = new SubMethodSymbolTable.MethodEntity.InputInfo(varType, inputClassName);
                info.setArray(array);
                inputType.add(info);
            }
        }

        if (symbolTable.findMethodByHash(SubMethodSymbolTable.getMethodHash(methodName, inputType)) == null)
            symbolTable.addMethod(methodName, returnType,className, arrayReturn, inputType, line);
        else
            errorHandler.doubleDefineMethod(methodName, line);

        super.enterMethodDec(ctx);
    }
}
