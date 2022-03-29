package stage2.demo;

import static java.lang.String.format;

public final class PrintTable {

    private static final char PLUS = '+';
    private static final char MINUS = '-';
    private static final char VERTICAL = '|';

    public PrintTable() {

    }

    public void print(String[][] table) {
        if ( table == null ) {
            throw new IllegalArgumentException("No tabular data provided");
        }
        if ( table.length == 0 ) {
            return;
        }
        final int[] widths = new int[getMaxColumns(table)];
        addColumn(table, widths);
        printPreparedTable(table, widths, getHorizontalBorder(widths));
    }

    private void printPreparedTable(String[][] table, int[] widths, String horizontalBorder) {
        final int lineLength = horizontalBorder.length();
        System.out.println(horizontalBorder);
        for ( final String[] row : table ) {
            if ( row != null ) {
                System.out.println(getRow(row, widths, lineLength));
                System.out.println(horizontalBorder);
            }
        }
    }

    private String getRow(String[] row, int[] widths, int lineLength) {
        final StringBuilder builder = new StringBuilder(lineLength).append(VERTICAL);
        final int maxWidths = widths.length;
        for ( int i = 0; i < maxWidths; i++ ) {
            builder.append(strFormat(row[i], widths[i])).append(VERTICAL);
        }
        return builder.toString();
    }

    private String getHorizontalBorder(int[] widths) {
        final StringBuilder builder = new StringBuilder(256);
        builder.append(PLUS);
        for ( final int w : widths ) {
            builder.append(String.valueOf(MINUS).repeat(w));
            builder.append(PLUS);
        }
        return builder.toString();
    }

    private int getMaxColumns(String[][] rows) {
        int max = 0;
        for ( final String[] row : rows ) {
            if ( row != null && row.length > max ) {
                max = row.length;
            }
        }
        return max;
    }

    private void addColumn(String[][] rows, int[] widths) throws NullPointerException {
        for ( final String[] row : rows ) {
            if ( row != null ) {
                for ( int c = 0; c < widths.length; c++ ) {
                    final String cv = row[c];
                    final int l = cv.length();
                    if ( widths[c] < l ) {
                        widths[c] = l;
                    }
                }
            }
        }
    }

    private static String strFormat(String s, int n) {
        return format("%1$-" + n + "s", s);
    }
}