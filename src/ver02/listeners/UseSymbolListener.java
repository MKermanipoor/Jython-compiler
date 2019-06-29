package ver02.listeners;

import gen.jythonParser;
import ver02.errorHandler.ErrorHandler;
import ver02.symbolTable.SymbolTable;
import ver02.symbolTable.subSybmolTable.SubClassSymbolTable;
import ver02.symbolTable.subSybmolTable.SubMethodSymbolTable;
import ver02.symbolTable.subSybmolTable.SubMethodSymbolTable.MethodEntity.InputInfo;
import ver02.symbolTable.subSybmolTable.SubVarSymbolTable;

import java.util.ArrayList;
import java.util.Stack;

public class UseSymbolListener extends MainListener {

    private Stack<SymbolTable> stack = new Stack<>();
    private SymbolTable tempSymbolTable = null;


    private ExpresionInfo rightExpresionInfo;
    private ExpresionInfo leftExpresionInfo;
    private Stack<ExpresionInfo> tempExpresionInfo = new Stack<>();
    private ArrayList<ExpresionInfo> expresionList = new ArrayList<>();
    private boolean leftExpresion = true;
    private boolean correctConditionList = true;

    private void setExpresionInfo(ExpresionInfo expresionInfo) {
        if (leftExpresion)
            leftExpresionInfo = expresionInfo;
        else
            rightExpresionInfo = expresionInfo;
    }

    private ExpresionInfo getExpresionInfo() {
        if (leftExpresion)
            return leftExpresionInfo;
        else
            return rightExpresionInfo;
    }

    public UseSymbolListener(String fileName, ErrorHandler errorHandler, SymbolTable masterSymbolTable) {
        super(fileName, errorHandler, masterSymbolTable);
    }

    @Override
    public void exitPrint_statment(jythonParser.Print_statmentContext ctx) {
        int line = ctx.start.getLine();
        if (tempExpresionInfo.isEmpty() || tempExpresionInfo.pop().expresionType == ExpresionInfo.ExpresionType.OBJECT) {
            errorHandler.printError(line);
        }
        tempExpresionInfo.clear();
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

        if (varEntity != null) {
            tempExpresionInfo.push(new ExpresionInfo(ExpresionInfo.ExpresionType.get(varEntity.getVarType())));
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

            if (varEntity != null) {
                tempExpresionInfo.push(new ExpresionInfo(ExpresionInfo.ExpresionType.get(varEntity.getVarType())));
            }
        }
    }

    @Override
    public void exitLeftExp_methodCall(jythonParser.LeftExp_methodCallContext ctx) {
        String methodName = ctx.ID().getText();
        int line = ctx.start.getLine();

        if (!symbolTable.findMethod(methodName).isEmpty()) {
            ArrayList<InputInfo> inputInfos = new ArrayList<>();
            for (ExpresionInfo exp : expresionList)
                inputInfos.add(new InputInfo(exp));

            SubMethodSymbolTable.MethodEntity entity = symbolTable.findMethodByHash(SubMethodSymbolTable.getMethodHash(methodName, inputInfos));
            if (entity == null) {
                errorHandler.notFindMethod(methodName, line);
            }else{
                if (entity.getReturnType() == SubMethodSymbolTable.MethodEntity.ReturnType.OBJECT) {
                    SubClassSymbolTable.ClassEntity classEntity = masterSymbolTable.findClass(entity.getClassName());
                    if (classEntity != null){
                        tempSymbolTable = masterSymbolTable.getChild(classEntity.getClassHash());
                    }
                }
                tempExpresionInfo.push(new ExpresionInfo(ExpresionInfo.ExpresionType.get(entity.getReturnType())));
            }

        } else {
            errorHandler.notFindMethod(methodName, line);
        }


        expresionList.clear();
    }

    @Override
    public void exitLeftExp_subMethodCall(jythonParser.LeftExp_subMethodCallContext ctx) {
        String methodName = ctx.ID().getText();
        int line = ctx.start.getLine();

        if (tempSymbolTable == null) {
            errorHandler.notFindMethod(methodName, line);
        }else{
            if (!symbolTable.findMethod(methodName).isEmpty()) {
                ArrayList<InputInfo> inputInfos = new ArrayList<>();
                for (ExpresionInfo exp : expresionList)
                    inputInfos.add(new InputInfo(exp));

                SubMethodSymbolTable.MethodEntity entity = symbolTable.findMethodByHash(SubMethodSymbolTable.getMethodHash(methodName, inputInfos));
                if (entity == null) {
                    errorHandler.notFindMethod(methodName, line);
                }else{
                    if (entity.getReturnType() == SubMethodSymbolTable.MethodEntity.ReturnType.OBJECT) {
                        SubClassSymbolTable.ClassEntity classEntity = masterSymbolTable.findClass(entity.getClassName());
                        if (classEntity != null){
                            tempSymbolTable = masterSymbolTable.getChild(classEntity.getClassHash());
                        }
                    }
                    tempExpresionInfo.push(new ExpresionInfo(ExpresionInfo.ExpresionType.get(entity.getReturnType())));
                }
            }else{
                    errorHandler.notFindMethod(methodName, line);
            }
        }

        expresionList.clear();
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

    @Override
    public void exitMethod_call(jythonParser.Method_callContext ctx) {
        if (ctx.ID() == null)
            return;

        String methodName = ctx.ID().getText();
        int line = ctx.start.getLine();

        if (!symbolTable.findMethod(methodName).isEmpty()) {
            ArrayList<InputInfo> inputInfos = new ArrayList<>();
            for (ExpresionInfo exp : expresionList)
                inputInfos.add(new InputInfo(exp));

            if (symbolTable.findMethodByHash(SubMethodSymbolTable.getMethodHash(methodName, inputInfos)) == null)
                errorHandler.notFindMethod(methodName, line);

        } else {
            errorHandler.notFindMethod(methodName, line);
        }


        expresionList.clear();
    }


    @Override
    public void exitBoolean_expression(jythonParser.Boolean_expressionContext ctx) {
        int line = ctx.start.getLine();

        if (tempExpresionInfo.empty()) {
            correctConditionList = false;
            errorHandler.conditionError(line);
            return;
        }

        if (tempExpresionInfo.pop().expresionType != ExpresionInfo.ExpresionType.BOOLEAN) {
            if (!correctConditionList)
                errorHandler.notDefineOperation(line);
            else
                errorHandler.conditionError(line);

            correctConditionList = false;
        }

        tempExpresionInfo.clear();
    }

    @Override
    public void exitCondition_list(jythonParser.Condition_listContext ctx) {
        correctConditionList = true;
    }

    @Override
    public void exitExpression(jythonParser.ExpressionContext ctx) {
        int line = ctx.start.getLine();

        if (ctx.rightExp() != null)
            return;

        if (tempExpresionInfo.size() < 2)
            return;

        if (ctx.mult_mod_div() != null || ctx.add_sub() != null) {
            ExpresionInfo resultInfo = tempExpresionInfo.pop().div_mult_mod_add_sub(tempExpresionInfo.pop());
            if (resultInfo == null)
                errorHandler.notDefineOperation(line);

            tempExpresionInfo.push(resultInfo);
        } else if (ctx.eq_neq() != null) {
            ExpresionInfo resultInfo = tempExpresionInfo.pop().equal_notEqual(tempExpresionInfo.pop());
            if (resultInfo == null)
                errorHandler.notDefineOperation(line);

            tempExpresionInfo.push(resultInfo);
        } else if (ctx.relation_operators() != null) {
            ExpresionInfo resultInfo = tempExpresionInfo.pop().relation(tempExpresionInfo.pop());
            if (resultInfo == null)
                errorHandler.notDefineOperation(line);

            tempExpresionInfo.push(resultInfo);
        }
    }

    @Override
    public void exitAssignment(jythonParser.AssignmentContext ctx) {
        int line = ctx.start.getLine();
        setExpresionInfo(tempExpresionInfo.pop());

        if (!leftExpresionInfo.equals(rightExpresionInfo)) {
            errorHandler.contConvert(leftExpresionInfo.expresionType.toString(), rightExpresionInfo.expresionType.toString(), line);
        }


        leftExpresion = true;
        leftExpresionInfo = null;
        rightExpresionInfo = null;
        tempExpresionInfo.clear();
    }

    @Override
    public void exitAssignment_operators(jythonParser.Assignment_operatorsContext ctx) {
        if (getExpresionInfo() == null)
            setExpresionInfo(tempExpresionInfo.pop());

        tempExpresionInfo.clear();
        leftExpresion = false;

    }

    @Override
    public void exitRightExp(jythonParser.RightExpContext ctx) {
        int line = ctx.start.getLine();

        if (ctx.args() == null && ctx.leftExp() == null) {
            if (ctx.BOOL() != null)
                tempExpresionInfo.push(new ExpresionInfo(ExpresionInfo.ExpresionType.BOOLEAN));
            else if (ctx.INTEGER() != null)
                tempExpresionInfo.push(new ExpresionInfo(ExpresionInfo.ExpresionType.INT));
            else if (ctx.STRING() != null)
                tempExpresionInfo.push(new ExpresionInfo(ExpresionInfo.ExpresionType.STRING));
            else if (ctx.FLOAT() != null)
                tempExpresionInfo.push(new ExpresionInfo(ExpresionInfo.ExpresionType.FLOAT));
        } else if (ctx.USER_TYPE() != null) {
            // TODO Masoud 6/12/2019: (Logic) check constructor
            String className = ctx.USER_TYPE().getText();

            if (findClassName(className))
                tempExpresionInfo.push(new ExpresionInfo(className));
            else
                errorHandler.notFindClass(className, line);
        }
    }


    @Override
    public void exitArrayDec(jythonParser.ArrayDecContext ctx) {
        int line = ctx.start.getLine();

        if (tempExpresionInfo.isEmpty() || tempExpresionInfo.peek().expresionType != ExpresionInfo.ExpresionType.INT) {
            errorHandler.arraySizeError(line);
        }

        tempExpresionInfo.clear();
    }

    @Override
    public void exitArg(jythonParser.ArgContext ctx) {
        if (tempExpresionInfo.isEmpty())
            expresionList.add(null);
        else
            expresionList.add(tempExpresionInfo.pop());
    }
}
