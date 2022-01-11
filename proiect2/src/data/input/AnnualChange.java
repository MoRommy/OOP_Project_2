package data.input;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public final class AnnualChange {
    private final Double newSantaBudget;
    private final List<Gift> newGifts;
    private final List<Child> newChildren;
    private final List<ChildUpdate> childrenUpdates;

    public AnnualChange(@JsonProperty("newSantaBudget") final Double newSantaBudget,
                        @JsonProperty("newGifts") final List<Gift> newGifts,
                        @JsonProperty("newChildren") final List<Child> newChildren,
                        @JsonProperty("childrenUpdates") final List<ChildUpdate> childrenUpdates) {
        this.newSantaBudget = newSantaBudget;
        this.newGifts = newGifts;
        this.newChildren = newChildren;
        this.childrenUpdates = childrenUpdates;
    }

    public Double getNewSantaBudget() {
        return newSantaBudget;
    }

    public List<Gift> getNewGifts() {
        return newGifts;
    }

    public List<Child> getNewChildren() {
        return newChildren;
    }

    public List<ChildUpdate> getChildrenUpdates() {
        return childrenUpdates;
    }

}
