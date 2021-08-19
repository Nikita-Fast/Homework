package com.company.operations;

public abstract class AssociativeOperationWithNeutralElement<T> extends AssociativeOperation<T> {
    private T neutralElement;

    public void setNeutralElement(T neutralElement) {
        this.neutralElement = neutralElement;
    }

    public T getNeutralElement() {
        return neutralElement;
    }
}
