package data.output;

import data.input.Child;

import java.util.List;

public final class ChildrenList {
    private final List<Child> childrenList;

    public ChildrenList(final List<Child> childrenList) {
        this.childrenList = childrenList;
    }

    public List<Child> getChildren() {
        return childrenList;
    }
}
