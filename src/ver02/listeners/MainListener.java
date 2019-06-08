package ver02.listeners;

import gen.jythonBaseListener;
import gen.jythonParser;
import ver02.errorHandler.ErrorHandler;
import ver02.symbolTable.SymbolTable;

import java.util.HashSet;
import java.util.Stack;

public class MainListener extends jythonBaseListener {
    final String fileName;
    final ErrorHandler errorHandler;
    final SymbolTable masterSymbolTable;
    String className;

    private boolean inMethod = false;

    SymbolTable symbolTable;
    Stack<SymbolTable> stack = new Stack<>();

    HashSet<String> importClass = new HashSet<>();

    public MainListener(String fileName, ErrorHandler errorHandler, SymbolTable masterSymbolTable) {
        this.fileName = fileName;
        this.errorHandler = errorHandler;
        this.errorHandler.setNowFileName(this.fileName);
        this.masterSymbolTable = masterSymbolTable;
        this.symbolTable = this.masterSymbolTable;
    }

    public boolean isInMethod() {
        return inMethod;
    }

    @Override
    public void enterClassDec(jythonParser.ClassDecContext ctx) {
        className = ctx.USER_TYPE(0).getText();
        symbolTable = symbolTable.createChild(getClassHash());
    }

    String getClassHash(){
        return fileName+className;
    }

    @Override
    public void exitClassDec(jythonParser.ClassDecContext ctx) {
        symbolTable = symbolTable.getParent();
    }

    @Override
    public void enterMethodDec(jythonParser.MethodDecContext ctx) {
        int line = ctx.start.getLine();
        symbolTable = symbolTable.createChild(fileName + " method " +  line);
        inMethod = true;
    }

    @Override
    public void exitMethodDec(jythonParser.MethodDecContext ctx) {
        symbolTable = symbolTable.getParent();
        inMethod = false;
    }

    @Override
    public void enterFor_statment(jythonParser.For_statmentContext ctx) {
        int line = ctx.start.getLine();
        symbolTable = symbolTable.createChild(fileName + " for " + line);
    }

    @Override
    public void exitFor_statment(jythonParser.For_statmentContext ctx) {
        symbolTable = symbolTable.getParent();
    }

    @Override
    public void enterWhile_statment(jythonParser.While_statmentContext ctx) {
        int line = ctx.start.getLine();
        symbolTable = symbolTable.createChild(fileName + " while " + line);
    }

    @Override
    public void exitWhile_statment(jythonParser.While_statmentContext ctx) {
        symbolTable = symbolTable.getParent();
    }

    @Override
    public void enterIf_else_statment(jythonParser.If_else_statmentContext ctx) {
        int line = ctx.start.getLine();
        symbolTable = symbolTable.createChild(fileName + " if " + line);
    }

    @Override
    public void exitIf_else_statment(jythonParser.If_else_statmentContext ctx) {
        symbolTable = symbolTable.getParent();
    }

    @Override
    public void enterLeftExp_self(jythonParser.LeftExp_selfContext ctx) {
        stack.push(symbolTable);
        symbolTable = masterSymbolTable.getChild(getClassHash());
    }

    @Override
    public void exitLeftExp_self(jythonParser.LeftExp_selfContext ctx) {
        symbolTable = stack.pop();
    }


}
