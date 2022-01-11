package data.input;

import java.util.ArrayList;
import java.util.List;

public final class InitialData {
    private List<Child> children = new ArrayList<>();
    private List<Gift> santaGiftsList = new ArrayList<>();

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(final List<Child> children) {
        this.children = children;
    }

    public List<Gift> getSantaGiftsList() {
        return santaGiftsList;
    }

    public void setSantaGiftsList(final List<Gift> santaGiftsList) {
        this.santaGiftsList = santaGiftsList;
    }

}
