package ver02.listeners;

import ver02.symbolTable.subSybmolTable.SubVarSymbolTable.VarEntity.VarType;

public class ExpresionInfo {
    public enum ExpresionType {
        STRING, OBJECT, BOOLEAN, FLOAT, INT;

        public static ExpresionType get(VarType varType) {
            switch (varType) {
                case OBJECT:
                    return OBJECT;
                case INT:
                    return INT;
                case FLOAT:
                    return FLOAT;
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
    private boolean isArr = false;

    ExpresionInfo(ExpresionType expresionType) {
        this.expresionType = expresionType;
    }

    ExpresionInfo(String className) {
        this.expresionType = ExpresionType.OBJECT;
        this.className = className;
    }

    public ExpresionType getExpresionType() {
        return expresionType;
    }

    public String getClassName() {
        return className;
    }

    public boolean isArr() {
        return isArr;
    }

    ExpresionInfo div_mult_mod_add_sub(ExpresionInfo expresionInfo) {
        if (this.expresionType != ExpresionType.FLOAT && this.expresionType != ExpresionType.INT)
            return null;

        if (expresionInfo.expresionType != ExpresionType.FLOAT && expresionInfo.expresionType != ExpresionType.INT)
            return null;

        if (this.expresionType == ExpresionType.INT || expresionInfo.expresionType == ExpresionType.INT)
            return new ExpresionInfo(ExpresionType.INT);

        return new ExpresionInfo(ExpresionType.FLOAT);
    }

    ExpresionInfo equal_notEqual(ExpresionInfo expresionInfo) {
        if (this.expresionType == expresionInfo.expresionType)
            return new ExpresionInfo(ExpresionType.BOOLEAN);

        if (this.expresionType == ExpresionType.FLOAT && expresionInfo.expresionType == ExpresionType.INT)
            return new ExpresionInfo(ExpresionType.BOOLEAN);
        if (this.expresionType == ExpresionType.INT && expresionInfo.expresionType == ExpresionType.FLOAT)
            return new ExpresionInfo(ExpresionType.BOOLEAN);

        return null;
    }

    ExpresionInfo relation(ExpresionInfo expresionInfo) {
        if (this.expresionType == expresionInfo.expresionType)
            return new ExpresionInfo(ExpresionType.BOOLEAN);

        if (this.expresionType == ExpresionType.FLOAT && expresionInfo.expresionType == ExpresionType.INT)
            return new ExpresionInfo(ExpresionType.BOOLEAN);
        if (this.expresionType == ExpresionType.INT && expresionInfo.expresionType == ExpresionType.FLOAT)
            return new ExpresionInfo(ExpresionType.BOOLEAN);

        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ExpresionInfo) {
            if (this.expresionType != ExpresionType.FLOAT && this.expresionType != ExpresionType.INT)
                return this.expresionType == ((ExpresionInfo) obj).expresionType;

            return ((ExpresionInfo) obj).expresionType == ExpresionType.INT || ((ExpresionInfo) obj).expresionType == ExpresionType.FLOAT;
        }
        return super.equals(obj);
    }
}
