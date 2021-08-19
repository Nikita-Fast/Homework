#include <stdlib.h>
#include <stdio.h>
#include <time.h>

void countingSort(int data[], size_t length) {
	int min = data[0];
	int max = data[0];
	for (size_t i = 0; i < length; i++) {
		if (data[i] < min) {
			min = data[i];
		}
		if (data[i] > max) {
			max = data[i];
		}
	}
	size_t size = max - min + 1;
	int* arr = (int*)malloc(size * sizeof(data[0]));
	if (arr != NULL) {
		for (size_t i = 0; i < size; i++) {
			arr[i] = 0;
		}
		for (size_t i = 0; i < length; i++) {
			arr[data[i] - min]++;
		}
		size_t i = 0;
		size_t j = 0;
		while (i < size) {
			if (arr[i] != 0) {
				data[j] = i + min;
				j++;
				arr[i]--;
			}
			else {
				i++;
			}
		}
		free(arr);
	}
}

void insertionSort(int data[], size_t length) {
	for (size_t i = 1; i < length; i++) {
		int element = data[i];
		size_t j = i - 1;
		while (j >= 0 && element < data[j]) {
			data[j + 1] = data[j];
			j--;
		}
		data[j + 1] = element;
	}
}

void qSort(int data[], size_t low, size_t high)
{
	size_t l = low, r = high;
	int pivot = data[(l + r) / 2];
	while (l <= r)
	{
		while (data[l] < pivot) {
			l++;
		}
		while (data[r] > pivot) {
			r--;
		}
		if (l <= r) {
			int copy = data[l];
			data[l++] = data[r];
			data[r--] = copy;
		}
	}
	if (low < r)
		qSort(data, low, r);
	if (high > l)
		qSort(data, l, high);
}
/*
int main() {
	clock_t time;
	size_t length = 100000;
	srand(2);
	int* data = (int*)malloc(length * sizeof(int));
	if (data) {
		for (size_t i = 0; i < length; i++) {
			data[i] = rand();
		}
		time = clock();
		//countingSort(data, length);
		//insertionSort(data, length);
		qSort(data, 0, length - 1);
		time = clock() - time;
		printf("%i elements %Lf seconds\n", length, (double)(time) / CLK_TCK);
	}
	//printArray(data, length);
	free(data);
}
*/
