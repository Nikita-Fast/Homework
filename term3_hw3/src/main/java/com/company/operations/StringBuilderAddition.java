package com.company.operations;

public class StringBuilderAddition extends AssociativeOperation<StringBuilder>{
    @Override
    public StringBuilder apply(StringBuilder x, StringBuilder y) {
        return new StringBuilder(x.append(y));
    }
}
