package ver02.listeners;

import gen.jythonParser;
import ver02.errorHandler.ErrorHandler;
import ver02.symbolTable.SymbolTable;
import ver02.symbolTable.subSybmolTable.SubClassSymbolTable;
import ver02.symbolTable.subSybmolTable.SubVarSymbolTable;

import java.util.ArrayList;
import java.util.Stack;

public class UseSymbolListener extends MainListener {

    Stack<SymbolTable> stack = new Stack<>();
    SymbolTable tempSymbolTable = null;


    ExpresionInfo rightExpresionInfo;
    ExpresionInfo leftExpresionInfo;
    Stack<ExpresionInfo> tempExpresionInfo = new Stack<>();
    ArrayList<ExpresionInfo> expresionList = new ArrayList<>();
    boolean leftExpresion = true;
    boolean isExpresionList = false;
    boolean correctConditionList = true;

    void setExpresionInfo(ExpresionInfo expresionInfo) {
        if (isExpresionList)
            expresionList.add(expresionInfo);
        else if (leftExpresion)
            leftExpresionInfo = expresionInfo;
        else
            rightExpresionInfo = expresionInfo;
    }

    ExpresionInfo getExpresionInfo() {
        if (leftExpresion)
            return leftExpresionInfo;
        else
            return rightExpresionInfo;
    }


    static class ExpresionInfo {
        enum ExpresionType {
            NUM, STRING, OBJECT, BOOLEAN;

            public static ExpresionType get(SubVarSymbolTable.VarEntity.VarType varType) {
                switch (varType) {
                    case OBJECT:
                        return OBJECT;
                    case INT:
                    case FLOAT:
                        return NUM;
                    case STRING:
                        return STRING;
                    case BOOLEAN:
                        return BOOLEAN;
                }
                return null;
            }
        }

        ExpresionType expresionType;
        private String className = "";

        ExpresionInfo(ExpresionType expresionType) {
            this.expresionType = expresionType;
        }

        ExpresionInfo(String className) {
            this.expresionType = ExpresionType.OBJECT;
            this.className = className;
        }

        ExpresionInfo div_mult_mod_add_sub(ExpresionInfo expresionInfo) {
            if (this.expresionType == ExpresionType.NUM && expresionInfo.expresionType == ExpresionType.NUM)
                return new ExpresionInfo(ExpresionType.NUM);

            return null;
        }

        ExpresionInfo equal_notEqual(ExpresionInfo expresionInfo) {
            if (this.expresionType == expresionInfo.expresionType)
                return new ExpresionInfo(ExpresionType.BOOLEAN);

            return null;
        }

        ExpresionInfo relation(ExpresionInfo expresionInfo) {
            if (this.expresionType == ExpresionType.NUM && expresionInfo.expresionType == ExpresionType.NUM)
                return new ExpresionInfo(ExpresionType.BOOLEAN);

            return null;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof ExpresionInfo)
                return this.expresionType == ((ExpresionInfo) obj).expresionType;

            return super.equals(obj);
        }
    }

    public UseSymbolListener(String fileName, ErrorHandler errorHandler, SymbolTable masterSymbolTable) {
        super(fileName, errorHandler, masterSymbolTable);
    }

    @Override
    public void exitPrint_statment(jythonParser.Print_statmentContext ctx) {
        int line = ctx.start.getLine();
        if (tempExpresionInfo.isEmpty() || tempExpresionInfo.pop().expresionType == ExpresionInfo.ExpresionType.OBJECT){
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
    public void enterLeftExp_self(jythonParser.LeftExp_selfContext ctx) {
        stack.push(symbolTable);
        symbolTable = masterSymbolTable.getChild(getClassHash(className));
    }

    @Override
    public void exitLeftExp_self(jythonParser.LeftExp_selfContext ctx) {
        symbolTable = stack.pop();
    }

    @Override
    public void enterMethod_call(jythonParser.Method_callContext ctx) {
        isExpresionList = true;
    }

    @Override
    public void exitMethod_call(jythonParser.Method_callContext ctx) {
        if (ctx.ID() == null)
            return;

        String methodName = ctx.ID().getText();
        int line = ctx.start.getLine();

        if (!symbolTable.findMethod(methodName).isEmpty()) {
            // TODO Masoud 6/11/2019: (Logic) after find return exp and check argument(s) type


        } else {
            errorHandler.notFindMethod(methodName, line);
        }

        isExpresionList = false;
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
            else
                tempExpresionInfo.push(resultInfo);
        } else if (ctx.eq_neq() != null) {
            ExpresionInfo resultInfo = tempExpresionInfo.pop().equal_notEqual(tempExpresionInfo.pop());
            if (resultInfo == null)
                errorHandler.notDefineOperation(line);
            else
                tempExpresionInfo.push(resultInfo);
        } else if (ctx.relation_operators() != null) {
            ExpresionInfo resultInfo = tempExpresionInfo.pop().relation(tempExpresionInfo.pop());
            if (resultInfo == null)
                errorHandler.notDefineOperation(line);
            else
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
                tempExpresionInfo.push(new ExpresionInfo(ExpresionInfo.ExpresionType.NUM));
            else if (ctx.STRING() != null)
                tempExpresionInfo.push(new ExpresionInfo(ExpresionInfo.ExpresionType.STRING));
            else if (ctx.FLOAT() != null)
                tempExpresionInfo.push(new ExpresionInfo(ExpresionInfo.ExpresionType.NUM));
        } else if (ctx.USER_TYPE() != null) {
            // TODO Masoud 6/12/2019: (Logic) check constructor
            String className = ctx.USER_TYPE().getText();

            if (findClassName(className))
                tempExpresionInfo.push(new ExpresionInfo(className));
            else
                errorHandler.notFindClass(className, line);
        }
    }
}
