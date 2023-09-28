package com.hypeboy.hypeBoard.enums.tablesColumns;

import java.time.LocalDateTime;
import java.util.Arrays;

public enum TableColumnsUser {
    TABLE("USERS", String.class),
    ID("ID", String.class),
    NAME("NAME", String.class),
    PHONE("PHONE", String.class),
    EMAIL("EMAIL", String.class),
    PWD("PWD", String.class),
    NICKNAME("NICKNAME", String.class),
    CREATED_AT("CREATED_AT", LocalDateTime.class),
    UPDATED_AT("UPDATED_AT", LocalDateTime.class);
    private final String columnName;

    TableColumnsUser(String columnName, Class<?> columnType) {
        this.columnName = columnName;
    }

    public String getString() {
        return columnName;
    }

    public static boolean isValidColumn(String targetColumn) {
        return Arrays
                .stream(TableColumnsUser.values())
                .anyMatch((column) -> column.
                        getString()
                        .equalsIgnoreCase(targetColumn));

    }
}
