package ver02.symbolTable.subSybmolTable;

import ver02.symbolTable.SymbolTable;
import ver02.symbolTable.subSybmolTable.SubVarSymbolTable.VarEntity.VarType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class SubMethodSymbolTable extends SubSymbolTable<SubMethodSymbolTable.MethodEntity> {

    private HashMap<String, HashSet<String>> methodNameToEntity = new HashMap<>();

    public SubMethodSymbolTable(SymbolTable master) {
        super(master);
    }

    public void addMethodEntity(String methodName, MethodEntity.ReturnType returnType, boolean array, String className, ArrayList<MethodEntity.InputInfo> inputList, int lineDefinition) {
        String hash = getMethodHash(methodName, inputList);
        add(hash, new MethodEntity(returnType, array,className, inputList, lineDefinition));

        methodNameToEntity.computeIfAbsent(methodName, k -> new HashSet<>());
        methodNameToEntity.get(methodName).add(hash);
    }

    public static String getMethodHash(String methodName, ArrayList<MethodEntity.InputInfo> inputList) {
        StringBuilder result = new StringBuilder(methodName);
        for (MethodEntity.InputInfo varType : inputList)
            result.append(varType.isArray()).append(varType.varType).append(varType.className);

        return result.toString();
    }

    public HashSet<String> getMethodNameToEntity(String methodName) {
        return methodNameToEntity.getOrDefault(methodName, new HashSet<>());
    }

    public static class MethodEntity {
        public enum ReturnType {
            INT, FLOAT, BOOLEAN, STRING, OBJECT, VOID;

            public static ReturnType get(String type) {
                switch (type) {
                    case "float":
                        return FLOAT;
                    case "int":
                        return INT;
                    case "bool":
                        return BOOLEAN;
                    case "string":
                        return STRING;
                }
                throw new IllegalArgumentException("I don't know " + type);
            }
        }

        private final ReturnType returnType;
        private ArrayList<InputInfo> inputType = new ArrayList<>();
        private boolean array;
        private String className;
        private final int lineDefinition;

        public MethodEntity(ReturnType returnType, boolean array,String className,ArrayList<InputInfo> inputType, int lineDefinition) {
            this.returnType = returnType;
            this.inputType = inputType;
            this.array = array;
            this.className = className;
            this.lineDefinition = lineDefinition;
        }

        public ReturnType getReturnType() {
            return returnType;
        }

        public ArrayList<InputInfo> getInputType() {
            return inputType;
        }

        public boolean isArray() {
            return array;
        }

        public String getClassName() {
            return className;
        }

        public String getMethodHash(String methodName) {
            return SubMethodSymbolTable.getMethodHash(methodName, inputType);
        }

        public static class InputInfo{
            private final VarType varType;
            private String className = "";
            private boolean array = false;

            public InputInfo(VarType varType, String className) {
                this.varType = varType;
                this.className = className;
            }

            public void setArray(boolean array) {
                this.array = array;
            }

            public VarType getVarType() {
                return varType;
            }

            public String getClassName() {
                return className;
            }

            public boolean isArray() {
                return array;
            }
        }
    }
}
