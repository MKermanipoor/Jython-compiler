public class ErrorHandler {
    public static void doubleVarDefinition(int line,String name){

        System.err.println("Error103: in line "+line+" , "+name+" has been defined already in current scope");
    }

}
