package data.output;

import java.util.ArrayList;
import java.util.List;

public final class AnnualChildren {
    private final List<ChildrenList> annualChildren = new ArrayList<>();

    /**
     *
     * @param list add children list
     */
    public void addAnualChildList(final ChildrenList list) {
        this.annualChildren.add(list);
    }

    public List<ChildrenList> getAnnualChildren() {
        return annualChildren;
    }
}
