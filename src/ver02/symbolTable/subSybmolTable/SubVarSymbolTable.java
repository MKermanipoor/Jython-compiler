package ver02.symbolTable.subSybmolTable;

import ver02.symbolTable.SymbolTable;

public class SubVarSymbolTable extends SubSymbolTable<SubVarSymbolTable.VarEntity> {

    public SubVarSymbolTable(SymbolTable master) {
        super(master);
    }

    public void addVariable(String varName, VarEntity.VarType varType, int lineDefinition) {
        add(varName, new VarEntity(varType, lineDefinition, false));
    }

    public void addVariable(String varName, String className, int lineDefinition) {
        add(varName, new VarEntity(className, lineDefinition, false));
    }

    public void addAttribute(String varName, VarEntity.VarType varType, int lineDefinition) {
        add(varName, new VarEntity(varType, lineDefinition, true));
    }

    public void addAttribute(String varName, String className, int lineDefinition) {
        add(varName, new VarEntity(className, lineDefinition, true));
    }

    public static class VarEntity {

        public enum VarType {
            INT, FLOAT, BOOLEAN, STRING, OBJECT;

            public static VarType get(String type) {
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

        private final VarType varType;
        private final int lineDefinition;
        private final boolean attribute;
        private String className;

        public VarEntity(VarType varType, int lineDefinition, boolean attribute) {
            this.varType = varType;
            this.lineDefinition = lineDefinition;
            this.attribute = attribute;
        }

        public VarEntity(String className, int lineDefinition, boolean attribute) {
            this(VarType.OBJECT, lineDefinition, attribute);
            this.className = className;
        }

        public VarType getVarType() {
            return varType;
        }

        public int getLineDefinition() {
            return lineDefinition;
        }

        public boolean isAttribute() {
            return attribute;
        }

        public String getClassName() {
            return className;
        }
    }
}
