package kitchenpos.table.ui.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class TableStatusRequest {

    private final boolean empty;

    @JsonCreator
    public TableStatusRequest(@JsonProperty("empty") boolean empty) {
        this.empty = empty;
    }

    public boolean isEmpty() {
        return empty;
    }
}