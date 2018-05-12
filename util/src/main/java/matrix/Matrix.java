package matrix;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code Matrix} represent a matrix with lists.
 * @param <T> the type of the element what the {@code Matrix} will store.
 */
public class Matrix<T> {
    private List<List<T>> matrix;

    /**
     * Set the dimension of the matrix.
     * @param rows the count of rows in the matrix
     * @param cols the count of columns in the matrix
     */
    public void setDimension(int rows, int cols) {
        matrix = new ArrayList<>(rows);
        for (int i = 0; i < rows; i++) {
            ArrayList<T> row = new ArrayList<>(cols);
            for (int j = 0; j < cols; j++) {
                row.add(null);
            }
            matrix.add(row);
        }

    }

    public int getRows() {
        return matrix != null ? matrix.size() : 0;
    }

    public int getColumns() {
        return getRows() > 0 ? matrix.get(0).size() : 0;
    }

    public T getValue(int row, int col) {
        return matrix.get(row).get(col);
    }

    public T setValue(int row, int col, T value) {
        return matrix.get(row).set(col, value);
    }
}
