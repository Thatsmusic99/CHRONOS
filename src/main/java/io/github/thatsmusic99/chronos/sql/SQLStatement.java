package io.github.thatsmusic99.chronos.sql;

import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;

public class SQLStatement {

    private StringBuilder strStatement;
    private List<String> args;

    public SQLStatement() {

    }

    /**
     * Appends a select statement.
     *
     * This should be the first
     * @param args
     * @return
     */
    public SQLStatement select(String... args) {
        this.args.addAll(Arrays.asList(args));
        strStatement.append("SELECT ");
        appendParameters(args.length);
        return this;
    }

    public SQLStatement from(String... args) {
        this.args.addAll(Arrays.asList(args));
        strStatement.append("FROM ");
        appendParameters(args.length);
        return this;
    }

    public static class SQLComparison {

        public SQLComparison(String compValue1, String compValue2, ) {

        }

        public enum SQLComparisonOperators {
            EQUAL("="),
            NOT_EQUAL("<>"),
            BIGGER(">"),
            SMALLER("<"),
            BIGGER_EQUAL(">="),
            SMALLER_EQUAL("<="),
            AND("AND"),
            NOT("NOT"),
            OR("OR");

            private String name;

            SQLComparisonOperators(String name) {
                this.name = name;
            }
        }
    }

    private void appendParameters(int amount) {
        for (int i = 0; i < amount; i++) {
            strStatement.append("? ");
            if (i < amount - 1) {
                strStatement.append(",");
            }
        }
    }
}
