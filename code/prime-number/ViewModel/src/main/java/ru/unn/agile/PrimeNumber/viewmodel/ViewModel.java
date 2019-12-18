package ru.unn.agile.PrimeNumber.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import ru.unn.agile.primenumber.model.PrimeNumberFinder;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class ViewModel {
    private final StringProperty startElement = new SimpleStringProperty();
    private final StringProperty endElement = new SimpleStringProperty();
    private final StringProperty outputField = new SimpleStringProperty();

    public BooleanProperty calculationDisabledProperty() {
        return calculationDisabled;
    }

    private final BooleanProperty calculationDisabled = new SimpleBooleanProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final List<ValueChangeListener> valueChangedListeners = new ArrayList<>();

    public StringProperty startElemProperty() {
        return startElement;
    }

    public StringProperty endElemProperty() {
        return endElement;
    }

    public StringProperty outputProperty() {
        return outputField;
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void findPrimaryNums() {
        if (calculationDisabled.get()) {
            return;
        }

        try {
            PrimeNumberFinder setOfPrimeNums = new PrimeNumberFinder(
                    Integer.parseInt(startElement.get()), Integer.parseInt(endElement.get()));
            List<Integer> primeNumsList = setOfPrimeNums.findNumbers();
            StringJoiner stringJoiner = new StringJoiner(",");
            for (Integer elem : primeNumsList) {
                stringJoiner.add(String.valueOf(elem));
            }
            outputField.set(stringJoiner.toString());
            status.set(Status.SUCCESS.toString());
        } catch (IllegalArgumentException e) {
            outputField.set(e.getMessage());
        }
    }

    public ViewModel() {
        startElement.set("");
        endElement.set("");
        outputField.set("");
        status.set(Status.WAITING.toString());

        BooleanBinding couldCalculate = new BooleanBinding() {
            {
                super.bind(startElement, endElement);
            }

            @Override
            protected boolean computeValue() {
                return getInputStatus() == Status.READY;
            }
        };
        calculationDisabled.bind(couldCalculate.not());

        // Add listeners to the input text fields
        final List<StringProperty> fields = new ArrayList<>() {
            {
                add(startElement);
                add(endElement);
            }
        };

        for (StringProperty field : fields) {
            final ValueChangeListener listener = new ValueChangeListener();
            field.addListener(listener);
            valueChangedListeners.add(listener);
        }
    }

    private Status getInputStatus() {
        Status status = Status.READY;
        if (startElement.get().isEmpty() || endElement.get().isEmpty()) {
            status = Status.WAITING;
        }
        try {
            if (!startElement.get().isEmpty()) {
                Integer.parseInt(startElement.get());
            }
            if (!endElement.get().isEmpty()) {
                Integer.parseInt(endElement.get());
            }
        } catch (NumberFormatException e) {
            status = Status.BAD_FORMAT;
        }
        return status;
    }

    private class ValueChangeListener implements ChangeListener<String> {
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            Status currentStatus = getInputStatus();
            status.set(currentStatus.toString());
            if (!currentStatus.equals(Status.SUCCESS)) {
                outputField.set(status.get());
            }
        }
    }
}

enum Status {
    WAITING("Please provide input data"),
    READY("Press 'Find' or Enter"),
    BAD_FORMAT("Bad format"),
    SUCCESS("Success");

    private final String name;

    Status(final String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
