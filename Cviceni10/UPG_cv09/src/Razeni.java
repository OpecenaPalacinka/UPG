/*
 * Mereni doby behu ruznych radicich algoritmu.
 * Implementovane algoritmy:
 *   Bubble Sort
 *   Insertion Sort
 *   Selection Sort
 *   Quick Sort (lepsi implementace)
 *   Quick Sort (horsi implementace)
 *   Merge Sort
 *   Shell Sort
 *   
 *   Idealne se spousti z prikazove radky s presmerovanim vystupu do souboru
 *     java Razeni > casy.txt
 *   Program na konzoli (prostrednictvim stderr) hlasi, jak je daleko.
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class Razeni {

	/* 
	 * zdrojaky prevzaty z:
	 * http://thilinasameera.wordpress.com/2011/06/01/sorting-algorithms-sample-codes-on-java-c-and-matlab/ 
	 */

	// Selection sort
	public static int[] selectionSort(int[] data) {
		int lenD = data.length;
		int j = 0;
		int tmp = 0;
		for (int i = 0; i < lenD; i++) {
			j = i;
			for (int k = i; k < lenD; k++) {
				if (data[j] > data[k]) {
					j = k;
				}
			}
			tmp = data[i];
			data[i] = data[j];
			data[j] = tmp;
		}
		return data;
	}

	
	// Insertion Sort
	public static int[] insertionSort(int[] data) {
		int len = data.length;
		int key = 0;
		int i = 0;
		for (int j = 1; j < len; j++) {
			key = data[j];
			i = j - 1;
			while (i >= 0 && data[i] > key) {
				data[i + 1] = data[i];
				i = i - 1;
				data[i + 1] = key;
			}
		}
		return data;
	}
	
	
	// Bubble Sort
	public static int[] bubbleSort(int[] data) {
		int lenD = data.length;
		int tmp = 0;
		for (int i = 0; i < lenD; i++) {
			for (int j = (lenD - 1); j >= (i + 1); j--) {
				if (data[j] < data[j - 1]) {
					tmp = data[j];
					data[j] = data[j - 1];
					data[j - 1] = tmp;
				}
			}
		}
		return data;
	}
	
	
	// Quick Sort
	// jednoducha implementace, pouziva doplnkova pole
	public static int[] quickSort_horsi(int[] data) {
		int lenD = data.length;
		int pivot = 0;
		int ind = lenD / 2;
		int i, j = 0, k = 0;

		if (lenD < 2) {
			return data;
		} else {
			int[] L = new int[lenD];
			int[] R = new int[lenD];
			int[] sorted = new int[lenD];
			pivot = data[ind];
			for (i = 0; i < lenD; i++) {
				if (i != ind) {
					if (data[i] < pivot) {
						L[j] = data[i];
						j++;
					} else {
						R[k] = data[i];
						k++;
					}
				}
			}
			int[] sortedL = new int[j];
			int[] sortedR = new int[k];
			System.arraycopy(L, 0, sortedL, 0, j);
			System.arraycopy(R, 0, sortedR, 0, k);
			sortedL = quickSort_horsi(sortedL);
			sortedR = quickSort_horsi(sortedR);
			System.arraycopy(sortedL, 0, sorted, 0, j);
			sorted[j] = pivot;
			System.arraycopy(sortedR, 0, sorted, j + 1, k);
			return sorted;
		}
	}

	
	
	// Merge Sort
	// implementace vyzaduje pomocnou metodu mergeSort_merge
	public static int[] mergeSort(int[] data) {
		int lenD = data.length;
		if (lenD <= 1) {
			return data;
		} else {
			int[] sorted = new int[lenD];
			int middle = lenD / 2;
			int rem = lenD - middle;
			int[] L = new int[middle];
			int[] R = new int[rem];
			System.arraycopy(data, 0, L, 0, middle);
			System.arraycopy(data, middle, R, 0, rem);
			L = mergeSort(L);
			R = mergeSort(R);
			sorted = mergeSort_merge(L, R);
			return sorted;
		}
	}
		 
	public static int[] mergeSort_merge(int[] L, int[] R) {
		int lenL = L.length;
		int lenR = R.length;
		int[] merged = new int[lenL + lenR];
		int i = 0;
		int j = 0;
		while (i < lenL || j < lenR) {
			if (i < lenL & j < lenR) {
				if (L[i] <= R[j]) {
					merged[i + j] = L[i];
					i++;
				} else {
					merged[i + j] = R[j];
					j++;
				}
			} else if (i < lenL) {
				merged[i + j] = L[i];
				i++;
			} else if (j < lenR) {
				merged[i + j] = R[j];
				j++;
			}
		}
		return merged;
	}
	
	
	
	// Shell Sort
	public static int[] shellSort(int[] data) {
		int lenD = data.length;
		int inc = lenD / 2;
		while (inc > 0) {
			for (int i = inc; i < lenD; i++) {
				int tmp = data[i];
				int j = i;
				while (j >= inc && data[j - inc] > tmp) {
					data[j] = data[j - inc];
					j = j - inc;
				}
				data[j] = tmp;
			}
			inc = (inc / 2);
		}
		return data;
	}	

	// Quick Sort
	// Implementace in-place (zadne doplnkove pole nejsou zapotrebi)
	// Implementace prevzata z KIV/PPA2, prednaska 4 (skolni rok 2017/2018)
	// Cinnost je rozlozena do metod quickSort_better_part(), quickSort_better_split
	public static int[] quickSort_better(int[] data) {
		quickSort_better_part(data, 0, data.length - 1);
		return data;
	}

	static void quickSort_better_part(int[] data, int start, int end) {
		if (end <= start)
			return;
		int i = quickSort_better_split(data, start, end);
		quickSort_better_part(data, start, i - 1);
		quickSort_better_part(data, i + 1, end);
	}

	static int quickSort_better_split(int[] data, int left, int right) {
		int pivot = data[right];
		while (true) {
			while ((data[left] < pivot) && (left < right)) {
				left++;
			}

			if (left < right) {
				data[right] = data[left];
				right--;
			} else {
				break;
			}

			while ((data[right] > pivot) && (left < right)) {
				right--;
			}

			if (left < right) {
				data[left] = data[right];
				left++;
			} else {
				break;
			}
		}

		data[left] = pivot;
		return (left);
	}

	static Random randomGenerator = new Random();
	
	public static int[] generateRandomArray(int arrayLength, double randomness) {
		int[] arr = new int[arrayLength];
		for (int i = 0; i < arrayLength; i++) {
			arr[i] = i;
		}
		
		int swaps = (int) Math.round(randomness*arrayLength);
		int ia, ib, a, b;
		for (int i = 0; i < swaps; i++) {
			ia = randomGenerator.nextInt(arrayLength);
			ib = randomGenerator.nextInt(arrayLength);
			a = arr[ia];
			b = arr[ib];
			arr[ia] = b;
			arr[ib] = a;
		}
		return arr;
	}
	
	
	//
	// MERENI
	//
	public static void main(String[] args) {
		// Budou se radit pole delek v nasledujicim rozmezi
		int startLength, endLength;

		// Delka razeneho pole narusta podle nasledujicho schematu:
		// for (...; ...; arrayLength = (int)(arrayLength*incrementMul + incrementAdd)) { ... }
		double incrementMul;
		int incrementAdd;
		
		// Pro zvyseni presnosti se pro jednu delku pole dela razeni pro
		// nasledujici pocet nahodnych poli.
		int randomArrays;
		
		// Pole muze byt serazene (randomness = 0)
		// nebo v nem muze byt provedeno az arrayLength zamen nahodnych prvku (randomness = 1);
		double randomness;
		
		// Pro dalsi zvyseni presnosti se jedno konkretni pole radi tolikrat:
		int repeatedMeasurements;
		
		// Budeme merit dobu behu radicich algoritmu
		// (bubblesort, insertsort, selectsort, 2x quicksort, mergesort, shellsort, Arrays.sort)
		// i dobu nutnou na kopirovani obsahu pole
		int methodsCount = 0;
		ArrayList<String> methodName = new ArrayList<String>(10);
		final int M_BUBBLESORT        = methodsCount; methodName.add("BubbleSort"); methodsCount++;
		final int M_INSERTSORT        = methodsCount; methodName.add("InsertionSort"); methodsCount++;
		final int M_SELECTSORT        = methodsCount; methodName.add("SelectionSort"); methodsCount++;
		final int M_QUICKSORT_BETTER  = methodsCount; methodName.add("QuickSort_lepsi"); methodsCount++;
		final int M_QUICKSORT_WORSE   = methodsCount; methodName.add("QuickSort_horsi"); methodsCount++;
		final int M_MERGESORT         = methodsCount; methodName.add("MergeSort"); methodsCount++;
		final int M_SHELLSORT         = methodsCount; methodName.add("ShellSort"); methodsCount++;
		final int M_JAVASORT          = methodsCount; methodName.add("JavaSort"); methodsCount++;
		
		// Pro kratka pole je vhodne zkouset mnoho ruznych poli
		// a kazde nekolikrat seradit. 
		randomArrays = 50;
		repeatedMeasurements = 20;
		startLength = 3;
		endLength = 50; 
		incrementMul = 1;
		incrementAdd = 1;
		randomness = 0.1;

		/*
		// Pro dlouha pole staci zkusit par nahodnych poli
		// a kazde nekolikrat seradit.
		randomArrays = 10;
		repeatedMeasurements = 10;
		startLength = 100;
		endLength = 4000;
		incrementMul = 1.1;
		incrementAdd = 0;
		randomness = 1;
		*/
		
		// Vypiseme zahlavi tabulky
		// (oddeleni tabulatory je kvuli snadnemu importu do tabulkoveho procesoru)
		System.out.print("arrayLength\t");
		for (int i = 0; i < methodsCount; i++) {
			System.out.print(methodName.get(i) + "\t");
		}
		System.out.print("sorts:, " + randomArrays*repeatedMeasurements + 
				         ", (," + randomArrays + ", randomArrays x ," +
				         repeatedMeasurements + ", measurements), " +
				         "randomness " + randomness);
		System.out.println("");

		// Radime pole ruznych delek
		for (int arrayLength = startLength; 
		     arrayLength <= endLength; 
			 arrayLength = (int)(arrayLength*incrementMul + incrementAdd))
		{
			int[] unsorted = new int[arrayLength];
			int[] toSort = new int[arrayLength];
			int[] sorted = new int[arrayLength];

			// Pomocne promenne pro mereni casu
			long startTime;
			long endTime;
			long[] elapsedTime = new long[methodsCount]; // automaticka inicializace nulami
			
			// Pro kazdou delku zkusime nekolik nahodnych poli
			for (int arrayNo = 1; arrayNo <= randomArrays; arrayNo++) {
				unsorted = generateRandomArray(arrayLength, randomness);
				
				// Kazde konkretni pole seradime nekolikrat
				for (int measurement = 1; measurement <= repeatedMeasurements; measurement++) {
					// Nasledujici bloky se de facto opakuji, meni se jen metoda,
					// jejiz doba behu se meri.
					
					// Bubble sort
					for (int i = 0; i < repeatedMeasurements; i++) {
						System.arraycopy(unsorted, 0, toSort, 0, arrayLength);
						startTime = System.nanoTime();
						sorted = bubbleSort(toSort);
						endTime = System.nanoTime();
						elapsedTime[M_BUBBLESORT] += endTime - startTime;
					}
					
					
					// Insertion Sort
					for (int i = 0; i < repeatedMeasurements; i++) {
						System.arraycopy(unsorted, 0, toSort, 0, arrayLength);
						startTime = System.nanoTime();
						sorted = insertionSort(toSort);
						endTime = System.nanoTime();
						elapsedTime[M_INSERTSORT] += endTime - startTime;
					}
		
					
					// Selection Sort
					for (int i = 0; i < repeatedMeasurements; i++) {
						System.arraycopy(unsorted, 0, toSort, 0, arrayLength);
						startTime = System.nanoTime();
						sorted = selectionSort(toSort);
						endTime = System.nanoTime();
						elapsedTime[M_SELECTSORT] += endTime - startTime;
					}
		
		
					// Quick Sort (lepsi implementace)
					for (int i = 0; i < repeatedMeasurements; i++) {
						System.arraycopy(unsorted, 0, toSort, 0, arrayLength);
						startTime = System.nanoTime();
						sorted = quickSort_better(toSort);
						endTime = System.nanoTime();
						elapsedTime[M_QUICKSORT_BETTER] += endTime - startTime;
					}

					
					// Quick Sort (horsi implementace)
					for (int i = 0; i < repeatedMeasurements; i++) {
						System.arraycopy(unsorted, 0, toSort, 0, arrayLength);
						startTime = System.nanoTime();
						sorted = quickSort_horsi(toSort);
						endTime = System.nanoTime();
						elapsedTime[M_QUICKSORT_WORSE] += endTime - startTime;
					}
					
					
					// Merge Sort
					for (int i = 0; i < repeatedMeasurements; i++) {
						System.arraycopy(unsorted, 0, toSort, 0, arrayLength);
						startTime = System.nanoTime();
						sorted = mergeSort(toSort);
						endTime = System.nanoTime();
						elapsedTime[M_MERGESORT] += endTime - startTime;
					}
		
					
					// Shell Sort
					for (int i = 0; i < repeatedMeasurements; i++) {
						System.arraycopy(unsorted, 0, toSort, 0, arrayLength);
						startTime = System.nanoTime();
						sorted = shellSort(toSort);
						endTime = System.nanoTime();
						elapsedTime[M_SHELLSORT] += endTime - startTime;
					}
					
					
					// Java default sort
					for (int i = 0; i < repeatedMeasurements; i++) {
						System.arraycopy(unsorted, 0, toSort, 0, arrayLength);
						startTime = System.nanoTime();
						Arrays.sort(toSort);
						endTime = System.nanoTime();
						elapsedTime[M_JAVASORT] += endTime - startTime;
					}
				}
			}

			// Oznameni na konzoli
			System.err.print(arrayLength + " ");

			// Vytisknout dobu behu jednotlivych metod (vhodne pro presmerovani do souboru)
			System.out.print(arrayLength);
			for (int i = 0; i < elapsedTime.length; i++) {
				System.out.print("\t" + elapsedTime[i]);
			}
			System.out.println("");
		}
	}
}
