#include <stdlib.h>
#include <stdio.h>
#include "listLibrary.h"
#include "hashTableLibrary.h"
#include <time.h>

int main() {
	struct HashTable myTable = createHashTable(30000, &polynomialHash);
	char fileName[] = "C:\\Users\\Nick Fast\\Desktop\\garbage\\book.txt"; //forWhomTheBellTolls.txt
	FILE* fp = fopen(fileName, "r");
	char buffer[40]; //присвоить все нули 
	char word[40];
	clock_t start, end;
	start = clock();
	while (fscanf(fp, "%s", buffer) != EOF) {
		size_t i = 0;
		size_t j = 0;
		while (buffer[i] != '\0') {
			char c = buffer[i];
			if (('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z')) {
				word[j] = c;
				j++;
			}
			i++;
		}
		word[j] = '\0';
		char key[40];
		strcpy(key, word);
		insertElementExtended(&myTable, key, word, 1);
	}
	end = clock();
	double time = (double)(end - start) / CLOCKS_PER_SEC;
	fclose(fp);
	printHashTable(&myTable);
	struct Information tableInformation;
	getInformation(&myTable, &tableInformation);
	printf("table processing time %f\n", time);
	printf("number of elements %u\n", tableInformation.numberOfElements);
	printf("number of chains %u\n", tableInformation.numberOfChains);
	printf("min chain length %u\n", tableInformation.minChainLength);
	printf("max chain length %u\n", tableInformation.maxChainLength);
	printf("average chain length %u\n", tableInformation.averageChainLength);
	freeHashTable(&myTable);
}