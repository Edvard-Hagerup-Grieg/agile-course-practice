package ru.unn.agile.interpolationsearch.viewmodel;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import ru.unn.agile.interpolationsearch.model.InterpolationSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ViewModel {
    private final StringProperty number = new SimpleStringProperty();
    private final StringProperty valueToSearch = new SimpleStringProperty();

    private static final Pattern NUMBER_INPUT_ALLOWED_SYMBOLS =
            Pattern.compile("^[-+]?[0-9]+$");

    private static final Pattern VALUE_TO_SEARCH_INPUT_ALLOWED_SYMBOLS =
            Pattern.compile("^[-+]?[0-9]+$");

    private ListProperty<String> numbersListProperty = new SimpleListProperty<>();

    private final List<Integer> numbersList = new ArrayList<Integer>();

    private final StringProperty result = new SimpleStringProperty();

    public ListProperty<String> numbersProperty() {
        return numbersListProperty;
    }

    private ILogger logger;

    private boolean isNumberCorrect() {
        String n = number.get();
        return (NUMBER_INPUT_ALLOWED_SYMBOLS.matcher(n).matches());
    }

    private boolean isSearchValueCorrect() {
        String v = valueToSearch.get();
        return (VALUE_TO_SEARCH_INPUT_ALLOWED_SYMBOLS.matcher(v).matches());
    }

    private void init() {
        numbersListProperty.set(FXCollections.observableArrayList());
        clearFormInput();
    }

    public ViewModel() {
        init();
    }

    public ViewModel(final ILogger logger) {
        setLogger(logger);
        this.logger = logger;
        logger.log("Start");
        init();
    }

    public final void setLogger(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }
        this.logger = logger;
    }

    public final List<String> getLog() {
        return logger.getLogList();
    }

    public void addNumber() {
        if (!isNumberCorrect()) {
            result.set("Incorrect number");
            logger.log("Element is incorrect");
        } else {
            logger.log("Element was added");
            Integer n = Integer.parseInt(number.get());
            numbersList.add(n);
            numbersListProperty.add(n.toString());
            number.set("");
            result.set("");
        }
    }

    public void doSearch() {
        if (!isSearchValueCorrect()) {
            result.set("Incorrect value for search");
            logger.log("List is not sorted");
        } else if (numbersList.isEmpty()) {
            result.set("Empty list");
            logger.log("List is Empty");
        } else {
            try {
                Integer v = Integer.parseInt(valueToSearch.get());
                var arr = new Integer[numbersList.size()];
                numbersList.toArray(arr);
                int res = InterpolationSearch.find(arr, v);
                result.set(Integer.toString(res));
                logger.log("Element is found");
            } catch (Exception e) {
                result.setValue(e.getMessage());
                logger.log("Element not found");
            }
        }
    }

    private void clearFormInput() {
        number.set("");
        valueToSearch.set("");
    }

    public StringProperty numberProperty() {
        return number;
    }

    public StringProperty searchValueProperty() {
        return valueToSearch;
    }

    public StringProperty resultProperty() {
        return result;
    }

    public final List<Integer> getArray() {
        return numbersList;
    }

    public final String getResult() {
        return result.get();
    }
}
