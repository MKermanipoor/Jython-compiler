

import gen.jythonBaseListener;


import gen.jythonParser;
import symbolTable.SymbolTable;
import symbolTable.SymbolTableVarEntity;
import symbolTable.SympolTableCalssEntity;
import symbolTable.VariableType;

public class myListener extends jythonBaseListener {

    private SymbolTable symbolTable = new SymbolTable();

    @Override
    public void enterClassDec(jythonParser.ClassDecContext ctx) {
        String className = ctx.USER_TYPE(0).getText();
        SympolTableCalssEntity entity;
        if (ctx.USER_TYPE().size() > 1){
            entity = new SympolTableCalssEntity(ctx.USER_TYPE(1).getText());
        }else{
            entity = new SympolTableCalssEntity();
        }
        symbolTable.add(className, entity);

        symbolTable = symbolTable.createChild();
    }

    @Override
    public void exitClassDec(jythonParser.ClassDecContext ctx) {
        symbolTable = symbolTable.getParent();

    }

    @Override
    public void enterVarDec(jythonParser.VarDecContext ctx) {
        String name = ctx.ID().getText();
        SymbolTableVarEntity entity = null;
        if (ctx.type().USER_TYPE() != null){
            entity = new SymbolTableVarEntity(VariableType.OBJECT);
        }else{
            switch (ctx.type().jythonType().getText()){
                case "float":
                    entity = new SymbolTableVarEntity(VariableType.FLOAT);
                    break;
                case "int":
                    entity = new SymbolTableVarEntity(VariableType.INTEGER);
                    break;
                case "bool":
                    entity = new SymbolTableVarEntity(VariableType.BOOLEAN);
                    break;
                case "string":
                    entity = new SymbolTableVarEntity(VariableType.STRING);
                    break;
            }
        }
        symbolTable.add(name, entity);

    }
}
