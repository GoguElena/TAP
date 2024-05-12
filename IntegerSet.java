//IntegerSet.java
package org.example.tap_lab7;

import java.util.HashSet;
import java.util.Set;

public class IntegerSet {
    private Set<Integer> set;

    public IntegerSet() {
        set = new HashSet<>();
    }

    public void add(Integer element) {
        set.add(element);
    }

    public void remove(Integer element) {
        set.remove(element);
    }

    public IntegerSet union(IntegerSet otherSet) {
        IntegerSet resultSet = new IntegerSet();
        resultSet.set.addAll(this.set);
        resultSet.set.addAll(otherSet.set);
        return resultSet;
    }

    public IntegerSet intersection(IntegerSet otherSet) {
        IntegerSet resultSet = new IntegerSet();
        for (Integer element : this.set) {
            if (otherSet.contains(element)) {
                resultSet.add(element);
            }
        }
        return resultSet;
    }

    public boolean contains(Integer element) {
        return set.contains(element);
    }

    public String getStringRepresentation() {
        StringBuilder sb = new StringBuilder("[");
        for (Integer element : set) {
            sb.append(element).append(", ");
        }
        if (!set.isEmpty()) {
            sb.setLength(sb.length() - 2); // Eliminăm ultima virgulă și spațiu adăugate inutil
        }
        sb.append("]");
        return sb.toString();
    }
}
