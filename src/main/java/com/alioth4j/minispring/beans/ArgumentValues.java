package com.alioth4j.minispring.beans;

import java.util.*;

public class ArgumentValues {

    private final Map<Integer, ArgumentValue> indexedArgumentValues = new HashMap<>(0);
    private final List<ArgumentValue> genericArgumentValues = new LinkedList<>();

    public ArgumentValues() {
    }

    public void addArgumentValue(Integer key, ArgumentValue newValue) {
        this.indexedArgumentValues.put(key, newValue);
    }

    public boolean hasIndexedArgumentValue(int index) {
        return this.indexedArgumentValues.containsKey(index);
    }

    public ArgumentValue getIndexedArgumentValue(int index) {
        return this.indexedArgumentValues.get(index);
    }

    public void addGenericArgumentValue(String type, Object value) {
        this.genericArgumentValues.add(new ArgumentValue(type, value));
    }

    public void addGenericArgumentValue(ArgumentValue newArgumentValue) {
        if (newArgumentValue.getName() != null) {
            for (Iterator<ArgumentValue> it = this.genericArgumentValues.iterator(); it.hasNext(); ) {
                ArgumentValue currentArgumentValue = it.next();
                if (newArgumentValue.getName().equals(currentArgumentValue.getName())) {
                    it.remove();
                }
            }
        }
        this.genericArgumentValues.add(newArgumentValue);
    }

    public ArgumentValue getGenericArgumentValue(String requiredName) {
        for (ArgumentValue argumentValueHolder : this.genericArgumentValues) {
            if (argumentValueHolder.getName() != null && (requiredName == null) || !argumentValueHolder.getName().equals(requiredName)) {
                continue;
            }
            return argumentValueHolder;
        }
        return null;
    }

    public int getArgumentCount() {
        return this.genericArgumentValues.size();
    }

    public boolean isEmpty() {
        return this.genericArgumentValues.isEmpty();
    }

}
