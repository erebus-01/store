package io.store.steam.repository.specification;

public enum SearchOperation {

    LIKE,
    EQUALS,
    LESS,
    GREATER,
    NEGATION,
    START_WITH,
    END_WITH,
    CONTAINS;

    public static final String[] OPERATION_SET = {"!", ":", ">", "<", "~"};
    public static final String OR_PREDICATE_FLAG = "'";
    public static final String ZERO_OR_MORE_REGEX = "*";
    public static final String OR_OPERATOR = "OR";
    public static final String AND_OPERATION = "AND";
    public static final String LEFT_PARENTHESIS = "(";
    public static final String RIGHT_PARENTHESIS = ")";

    public static SearchOperation getSimpleOperation(final char input) {
        return switch (input) {
            case ':' -> EQUALS;
            case '!' -> NEGATION;
            case '>' -> GREATER;
            case '<' -> LESS;
            case '~' -> LIKE;
            default -> null;
        };
    }



}
