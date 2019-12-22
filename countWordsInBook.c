#include <stdlib.h>
#include <stdio.h>
#include "listLibrary.h"
#include "hashTableLibrary.h"
#include <string.h>
#include <time.h>
#define TABLE_SIZE 25000

int main() {
	struct HashTable table = createHashTable(TABLE_SIZE, polynomialHash);
	char name[] = "C:\\Users\\Nick Fast\\Desktop\\garbage\\forWhomTheBellTolls.txt"; 
	FILE* fp = fopen(name, "r");
	char buffer[50];
	char word[50];
	clock_t begin, end;
	begin = clock();
	while (fscanf(fp, "%s", buffer) != EOF) {
		if (buffer[0] != '\0') {
			size_t i = 0;
			size_t j = 0;
			char c = buffer[i];
			while (c != '\0') {
				if (('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z')) {
					word[j] = c;
					j++;
				}
				i++;
				c = buffer[i];
			}
			word[j] = '\0';
			insertElementExtended(&table, word, 1);
		}
	}
	end = clock();
	double executionTime = (double)(end - begin) / CLOCKS_PER_SEC;
	fclose(fp);
	//printHashTable(&table);
	//printf("time = %f\n", executionTime);
	//struct Information myInf;
	//getInformation(&table, &myInf);
	//printf("%i %i %i %i %i", myInf.numberOfElements, myInf.numberOfChains, myInf.minChainLength, myInf.averageChainLength, myInf.maxChainLength);
	freeHashTable(&table);
}