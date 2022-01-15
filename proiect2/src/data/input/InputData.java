package data.input;

import java.util.ArrayList;
import java.util.List;

public final class InputData {
    private Integer numberOfYears;
    private Double santaBudget;
    private InitialData initialData;
    private List<AnnualChange> annualChanges = new ArrayList<>();
    private final List<Integer> childrenOrder = new ArrayList<>();

    public List<Integer> getChildrenOrder() {
        return childrenOrder;
    }

    public Integer getNumberOfYears() {
        return numberOfYears;
    }

    public void setNumberOfYears(final Integer numberOfYears) {
        this.numberOfYears = numberOfYears;
    }

    public Double getSantaBudget() {
        return santaBudget;
    }

    public void setSantaBudget(final Double santaBudget) {
        this.santaBudget = santaBudget;
    }

    public InitialData getInitialData() {
        return initialData;
    }

    public void setInitialData(final InitialData initialData) {
        this.initialData = initialData;
    }

    public List<AnnualChange> getAnnualChanges() {
        return annualChanges;
    }

    public void setAnnualChanges(final List<AnnualChange> annualChanges) {
        this.annualChanges = annualChanges;
    }
}
