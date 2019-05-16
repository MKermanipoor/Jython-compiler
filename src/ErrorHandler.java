public class ErrorHandler {
    public static void doubleVarDefinition(int line,String name){

        System.err.println("Error103: in line "+line+" , "+name+" has been defined already in current scope");
    }

    public static void doubleMethodDefinition(int line,String name,String className){

        System.err.println("Error102: in line "+line+" , "+name+" has been defined already in "+className);
    }
}
