package me.matrix;

import java.awt.Dimension;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.Iterator;
import java.util.List;
import java.util.PrimitiveIterator.OfInt;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Matrix implements Serializable {
	private static final long serialVersionUID = -2080962898540646931L;

	public static final Matrix EMPTY = new Matrix(0, 0);

	private transient String stringCache;

	private int matrix[][];

	private int rows;
	private int columns;

	public Matrix(Matrix matrix) {
		rows = matrix.rows;
		columns = matrix.columns;
		this.matrix = new int[rows][columns];
		System.arraycopy(matrix.matrix, 0, this.matrix, 0, matrix.matrix.length);
	}

	public Matrix(int[][] matrix) {
		setMatrix(matrix);
	}

	public Matrix(int[] matrix, boolean row) {
		rows = row ? 1 : matrix.length;
		columns = row ? matrix.length : 1;
		this.matrix = new int[rows][columns];

		if (row) {
			for (int i = 0; i < columns; i++) {
				this.matrix[0][i] = matrix[i];
			}
		} else {
			for (int i = 0; i < rows; i++) {
				this.matrix[i][0] = matrix[i];
			}
		}
	}

	public Matrix(int[] matrix) {
		this(matrix, true);
	}

	public Matrix(int rows, int columns) {
		matrix = new int[rows][columns];
		this.rows = rows;
		this.columns = columns;
	}

	public Matrix(int size) {
		this(size, size);
	}

	public Matrix(int rows, int columns, IntStream source) {
		this(rows, columns);
		fill(source);
	}

	public Matrix(int rows, int columns, int... values) {
		this(rows, columns, IntStream.of(values));
	}

	private int[][] grow(int rows, int columns) {
		int[][] newMatrix = new int[rows][columns];
		copyArray(matrix, newMatrix);
		return newMatrix;
	}

	public Matrix addRows(int amount) {
		return new Matrix(grow(rows + amount, columns));
	}

	public Matrix sumRows(int first, int second, int constant) {
		Matrix newMatrix = new Matrix(this);
		for (int i = 0; i < columns; i++) {
			newMatrix.matrix[second][i] += newMatrix.matrix[first][i] * constant;
		}
		return newMatrix;
	}

	public Matrix sumRows(int first, int second) {
		return sumRows(first, second, 1);
	}

	public Matrix multiplyRow(int row, int constant) {
		Matrix newMatrix = new Matrix(this);
		for (int i = 0; i < columns; i++) {
			newMatrix.matrix[row][i] *= constant;
		}
		return newMatrix;
	}

	public Matrix addColumns(int amount) {
		return new Matrix(grow(rows, columns + amount));
	}

	public Matrix sumColumns(int first, int second, int constant) {
		Matrix newMatrix = new Matrix(this);
		for (int i = 0; i < rows; i++) {
			newMatrix.matrix[i][second] += newMatrix.matrix[i][first] * constant;
		}
		return newMatrix;
	}

	public Matrix sumColumns(int first, int second) {
		return sumColumns(first, second, 1);
	}

	public Matrix multiplyColumn(int column, int constant) {
		Matrix newMatrix = new Matrix(this);
		for (int i = 0; i < rows; i++) {
			newMatrix.matrix[i][column] *= constant;
		}
		return newMatrix;
	}

	public Matrix swapRows(int first, int second) {
		Matrix newMatrix = new Matrix(this);
		int temp;
		for (int i = 0; i < columns; i++) {
			temp = newMatrix.matrix[first][i];
			newMatrix.matrix[first][i] = newMatrix.matrix[second][i];
			newMatrix.matrix[second][i] = temp;
		}
		return newMatrix;
	}

	public Matrix swapColumns(int first, int second) {
		Matrix newMatrix = new Matrix(this);
		int temp;
		for (int i = 0; i < columns; i++) {
			temp = newMatrix.matrix[i][first];
			newMatrix.matrix[i][first] = newMatrix.matrix[i][second];
			newMatrix.matrix[i][second] = temp;
		}
		return newMatrix;
	}

	public Matrix submatrix(int[] rows, int[] columns) {
		Matrix submatrix = new Matrix(this.rows - rows.length, this.columns - columns.length);
		
		int k = 0, l = 0, g = 0, h = 0;
		for (int i = 0; k < submatrix.rows && i < this.rows; i++) {
			if (g < rows.length && rows[g] == i) {
				g++;
				continue;
			}
			l = 0;
			h = 0;
			for (int j = 0; l < submatrix.columns && j < this.columns; j++) {
				if (h < columns.length && columns[h] == j) {
					h++;
					continue;
				}
				submatrix.matrix[k][l] = matrix[i][j];
				l++;
			}
			k++;
		}
		
		return submatrix;
	}
	
	public Matrix submatrix(String rows, String columns, String delimiter) {
		return submatrix(parseIntArray(rows, delimiter), parseIntArray(columns, delimiter));
	}
	
	public Matrix submatrix(int row, int column) {
		return submatrix(new int[] { row }, new int[] { column });
	}
	
	private static int computeDeterminant(Matrix m) {
		if (m.rows == 1) {
			return m.matrix[0][0];
		}
		
		int signum = 1;
		int sum = 0;
		for (int i = 0; i < m.columns; i++) {
			sum += m.matrix[0][i] * signum * computeDeterminant(m.submatrix(0, i));
			signum *= -1;
		}
		
		return sum;
	}
	
	public Integer getDeterminant() {
		if (!isSquareMatrix()) {
			return null;
		}
		return computeDeterminant(this);
	}
	
	public Integer getTrace() {
		if (!isSquareMatrix()) {
			return null;
		}
		
		int sum = 0;
		for (int i = 0; i < rows; i++) {
			sum += matrix[i][i];
		}
		
		return sum;
	}

	public Matrix trim() {
		boolean[] rows_ind = new boolean[rows];
		boolean[] columns_ind = new boolean[columns];
		int newRows = 0;
		int newColumns = 0;

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (matrix[i][j] != 0) {
					if (!rows_ind[i]) {
						rows_ind[i] = true;
						newRows++;
					}
					if (!columns_ind[j]) {
						columns_ind[j] = true;
						newColumns++;
					}
				}
			}
		}

		Matrix newMatrix = new Matrix(newRows, newColumns);

		int k = 0, l = 0;
		for (int i = 0; i < rows && k < newRows; i++) {
			if (!rows_ind[i])
				continue;

			l = 0;
			for (int j = 0; j < columns && l < newColumns; j++) {
				if (!columns_ind[j])
					continue;

				newMatrix.matrix[k][l] = matrix[i][j];
				l++;
			}
			k++;
		}

		return newMatrix;
	}

	public int getElementAt(int row, int column) {
		return matrix[row][column];
	}

	public boolean setElementAt(int row, int column, int value) {
		if (!isAppropriateIndex(row, column))
			return false;

		matrix[row][column] = value;
		return true;
	}

	public void fill(IntStream source) {
		OfInt iter = source.iterator();
		boolean done = false;
		for (int i = 0; i < rows && !done; i++) {
			for (int j = 0; j < columns && !done; j++) {
				if (iter.hasNext()) {
					matrix[i][j] = iter.nextInt();
				} else {
					done = true;
				}
			}
		}
	}

	public void fill(int... values) {
		fill(IntStream.of(values));
	}

	public boolean isEmpty() {
		return rows == 0 || columns == 0;
	}

	public boolean isTranspositionOf(Matrix other) {
		return this.equals(other.transpose());
	}

	public boolean isSquareMatrix() {
		return rows == columns;
	}

	public boolean isIdentityMatrix() {
		if (!isSquareMatrix()) {
			return false;
		}

		int count;
		Boolean flag = null;
		for (int i = 0; i < rows; i++) {
			count = 0;
			for (int j = 0; j < columns; j++) {
				if (matrix[i][j] == 1) {
					count++;
				} else if (matrix[i][j] != 0) {
					return false;
				}

				if (flag == null && matrix[j][j] != 1) {
					flag = Boolean.FALSE;
				}
			}
			if (flag == null) {
				flag = Boolean.TRUE;
			}

			if (count != 1) {
				return false;
			}
		}

		return flag;
	}

	public boolean isCompatibleWith(Matrix other) {
		return columns == other.rows;
	}

	private boolean isAppropriateIndex(int row, int column) {
		return row >= 0 && row <= rows && column >= 0 && column <= columns;
	}

	public int[][] getMatrixAsArray() {
		return matrix;
	}

	public void setMatrix(int[][] matrix) {
		rows = matrix.length;
		columns = 0;

		for (int[] row : matrix) {
			if (row.length > columns) {
				columns = row.length;
			}
		}

		this.matrix = new int[rows][columns];
		copyArray(matrix, this.matrix);
	}

	public Dimension size() {
		return new Dimension(rows, columns);
	}

	private String computeString() {
		StringBuilder sb = new StringBuilder();
		IntSummaryStatistics stats = Arrays.stream(matrix).flatMapToInt(r -> Arrays.stream(r)).summaryStatistics();
		String num = "%"
				.concat(String.valueOf(
						Math.max(String.valueOf(stats.getMax()).length(), String.valueOf(stats.getMin()).length())))
				.concat("d");
		for (int[] row : matrix) {
			for (int i = 0; i < row.length; i++) {
				sb.append(num);
				if (i != row.length - 1) {
					sb.append(", ");
				}
			}
			sb.append("\n");
		}

		return String.format(sb.toString(),
				Arrays.stream(matrix).flatMapToInt(r -> Arrays.stream(r)).boxed().toArray());
	}

	public String toString() {
		if (stringCache == null) {
			stringCache = computeString();
		}
		return stringCache;
	}

	public boolean equals(Object otherObject) {
		if (this == otherObject)
			return true;

		if (otherObject == null)
			return false;

		if (this.getClass() != otherObject.getClass())
			return false;

		Matrix other = (Matrix) otherObject;
		return Arrays.deepEquals(this.matrix, other.matrix);
	}

	public int hashCode() {
		return Arrays.deepHashCode(matrix) + rows * 2 + columns * 3;
	}

	public static Matrix copyOf(Matrix matrix, int rowsFrom, int rowsTo, int columnsFrom, int columnsTo) {
		Matrix copy = new Matrix(rowsTo - rowsFrom, columnsTo - columnsFrom);

		for (int i = rowsFrom; i < rowsTo; i++) {
			for (int j = columnsFrom; j < columnsTo; j++) {
				copy.matrix[i][j] = matrix.matrix[i][j];
			}
		}

		return copy;
	}

	public static Matrix copyOf(Matrix matrix) {
		return copyOf(matrix, 0, matrix.rows, 0, matrix.columns);
	}

	private Matrix simpleBiFunction(Matrix other, BiFunction<Integer, Integer, Integer> func) {
		if (!this.size().equals(other.size()))
			return EMPTY;

		Matrix result = new Matrix(rows, columns);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				result.matrix[i][j] = func.apply(matrix[i][j], other.matrix[i][j]);
			}
		}

		return result;
	}

	public Matrix add(Matrix other) {
		return simpleBiFunction(other, (a, b) -> a + b);
	}

	public Matrix subtract(Matrix other) {
		return simpleBiFunction(other, (a, b) -> a - b);
	}

	public Matrix multiplyBy(int scalar) {
		return simpleBiFunction(this, (a, b) -> a * scalar);
	}

	public Matrix multiplyBy(Matrix other) {
		if (!isCompatibleWith(other))
			return EMPTY;

		Matrix result = new Matrix(rows, other.columns);

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < other.columns; j++) {
				for (int k = 0; k < columns; k++) {
					result.matrix[i][j] += matrix[i][k] * other.matrix[k][j];
				}
			}
		}

		return result;
	}

	public Matrix parallelMultiplyBy(Matrix other) throws InterruptedException {
		if (!isCompatibleWith(other))
			return EMPTY;

		ExecutorService exec = Executors.newFixedThreadPool(5);
		Matrix result = new Matrix(rows, other.columns);

		for (int i = 0; i < rows; i++) {
			final int r = i;
			exec.execute(() -> {
				for (int j = 0; j < other.columns; j++) {
					for (int k = 0; k < columns; k++) {
						result.matrix[r][j] += matrix[r][k] * other.matrix[k][j];
					}
				}
			});
		}

		exec.shutdown();
		exec.awaitTermination(20, TimeUnit.SECONDS);

		return result;
	}

	public Matrix transpose() {
		Matrix newMatrix = new Matrix(columns, rows);
		for (int i = 0; i < columns; i++) {
			for (int j = 0; j < rows; j++) {
				newMatrix.matrix[i][j] = matrix[j][i];
			}
		}
		return newMatrix;
	}

	public static Matrix parse(String input, String rowDelimiter, String columnDelimiter) {
		if (input == null || input.length() == 0 || (rowDelimiter == null && columnDelimiter == null)
				|| (columnDelimiter != null && rowDelimiter != null && columnDelimiter.equals(rowDelimiter)))
			return EMPTY;

		if (rowDelimiter == null || columnDelimiter == null) {
			boolean row = rowDelimiter == null;
			return new Matrix(Arrays.stream(input.split(row ? columnDelimiter : rowDelimiter))
					.mapToInt(Integer::parseInt).toArray(), row);
		}

		String[] rows = input.split(rowDelimiter);
		int maxRowLength = 0;
		List<Integer> elements = new ArrayList<>();
		String[] arr;
		for (String row : rows) {
			arr = row.split(columnDelimiter);
			if (arr.length > maxRowLength) {
				maxRowLength = arr.length;
			}
			elements.addAll(Arrays.stream(arr).map(Integer::parseInt).collect(Collectors.toList()));
		}

		Matrix newMatrix = new Matrix(rows.length, maxRowLength);
		Iterator<Integer> iter = elements.iterator();
		for (int i = 0; i < newMatrix.rows; i++) {
			for (int j = 0; j < newMatrix.columns; j++) {
				newMatrix.matrix[i][j] = iter.next();
			}
		}

		return newMatrix;
	}

	private void copyArray(int[][] from, int[][] to) {
		for (int i = 0; i < from.length && i < to.length; i++) {
			for (int j = 0; j < from[0].length && j < to[0].length; j++) {
				to[i][j] = from[i][j];
			}
		}
	}

	private int[] parseIntArray(String s, String delimiter) {
		return Arrays.stream(s.split(delimiter)).mapToInt(Integer::parseInt).sorted().toArray();
	}
}
