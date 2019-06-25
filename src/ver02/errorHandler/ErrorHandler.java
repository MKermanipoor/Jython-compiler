package ver02.errorHandler;

import java.util.ArrayList;
import java.util.Hashtable;

public class ErrorHandler {
    private String nowFileName;
    private Hashtable<String, ArrayList<Error>> errorTable = new Hashtable<>();

    public void setNowFileName(String nowFileName) {
        this.nowFileName = nowFileName;
        if (errorTable.get(nowFileName) == null)
            errorTable.put(this.nowFileName, new ArrayList<>());
    }

    public void doubleImportClass(String className, int line) {
        addError(new DoubleImporrtClass(className, line));
    }

    public void doubleDefineClass(String className, int line) {
        addError(new DoubleClassDefine(className, line));
    }

    public void doubleDefineVarriable(String variableName, int line){
        addError(new DoubleVarDefine(variableName, line));
    }

    public void doubleDefineMethod(String methodName, int line){
        addError(new DoubleMethodDefine(methodName, line));
    }




    public void notFindClass(String className, int line){
        addError(new NotDefineClass(className, line));
    }

    public void notFindVar(String varName, int line){
        addError(new NotDefineVar(varName, line));
    }

    public void notFindMethod(String methodName, int line){
        addError(new NotDefineMethod(methodName, line));
    }

    public void notDefineOperation(int line){
        addError(new NotDefineOperation(line));
    }

    public void contConvert(String leftType, String rightType, int line){
        addError(new NotConvert(leftType, rightType, line));
    }

    public void conditionError(int line){
        addError(new ConditionNotBolean(line));
    }

    public void printError(int line){
        addError(new PrintError(line));
    }

    public void arraySizeError(int line){
        addError(new ArraySizeError(line));
    }

    private void addError(Error error){
        errorTable.get(nowFileName).add(error);
    }

    public void checkAndPrintAll() {
        errorTable.forEach((fileName, errors) -> {
            if (errors.size() < 1)
                return;
            System.err.println("in file " + fileName + ":");
            for (Error error : errors) {

                //print errors without check
                System.err.println(error);
            }
            System.err.println();
        });
    }
}
