import java.util.Arrays;

class SortingTools {

	public static <T extends Comparable<? super T>> void mergeSort(T[] array) {
		T[] wArray = Arrays.copyOf(array, array.length);
		mergeSortSplit(wArray, 0, array.length, array);
	}
	
	private static <T extends Comparable<? super T>> void mergeSortSplit(T[] result, int start, int end, T[] array) {
		if (end - start < 2)
			return;
		
		int middle = (start + end) / 2;
		
		mergeSortSplit(array, start, middle, result);
		mergeSortSplit(array, middle, end, result);
		
		merge(result, start, middle, end, array);
	}
	
	private static <T extends Comparable<? super T>> void merge(T[] array, int start, int middle, int end, T[] result) {
		int i = start;
		int j = middle;
		
		for (int k = start; k < end; k++) {
			if (i < middle && (j >= end || array[i].compareTo(array[j]) <= 0)) {
				result[k] = array[i];
				i++;
			} else {
				result[k] = array[j];
				j++;
			}
		}
	}
	
	public static <T extends Comparable<? super T>> void bubbleSort(T[] array) {
		T temp;
		boolean isDone = false;
		int iterations = array.length;
		
		while (!isDone) {
			isDone = true;
			for (int i = 1; i < iterations; i++) {
				if (array[i].compareTo(array[i - 1]) < 0) {
					temp = array[i];
					array[i] = array[i - 1];
					array[i - 1] = temp;
					isDone = false;
				}
			}
			iterations--;
		}
	}
}

