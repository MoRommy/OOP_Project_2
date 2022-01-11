package data.output;

import data.input.ChildDisplay;

import java.util.List;

public final class ChildrenList {
    private final List<ChildDisplay> childrenList;

    public ChildrenList(final List<ChildDisplay> childrenList) {
        this.childrenList = childrenList;
    }

    public List<ChildDisplay> getChildren() {
        return childrenList;
    }
}
