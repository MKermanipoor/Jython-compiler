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
        errorTable.get(nowFileName).add(new DoubleImporrtClass(className, line));
    }

    public void doubleDefineClass(String className, int line) {
        errorTable.get(nowFileName).add(new DoubleClassDefine(className, line));
    }

    public void doubleDefineVarriable(String variableName, int line){
        errorTable.get(nowFileName).add(new DoubleVarDefine(variableName, line));
    }

    public void doubleDefineMethod(String methodName, int line){
        errorTable.get(nowFileName).add(new DoubleMethodDefine(methodName, line));
    }




    public void notFindClass(String className, int line){
        errorTable.get(nowFileName).add(new NotDefineClass(className, line));
    }

    public void notFindVar(String varName, int line){
        errorTable.get(nowFileName).add(new NotDefineVar(varName, line));
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
