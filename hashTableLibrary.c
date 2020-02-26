#include <stdio.h>
#include <stdlib.h>
#include <malloc.h>
#include <string.h>
#include <limits.h>
#include <time.h>
#include "listLibrary.h"
#include "hashTableLibrary.h"

size_t compressionMap(long long int hash, size_t size) {
	size_t index = 0;
	int a = 71317;
	int b = 43981;
	index = abs(a * hash + b) % size;
	return index;
}

size_t polynomialHash(char* key, size_t size) {
	long long int hash = 0;
	int coefficient = 31;
	int multiplier = 1;
	size_t i = 0;
	while (key[i]) {
		hash += (key[i] - 'a' + 1) * coefficient * multiplier;
		multiplier *= coefficient;
		i++;
	}
	return compressionMap(hash, size);
}

size_t constantHash(char* key, size_t size) {
	long long int hash = 56872901378337;
	return compressionMap(hash, size);
}

size_t symbolSumHash(char* key, size_t size) {
	long long int hash = 0;
	while (*key) {
		hash += *key;
		key++;
	}
	compressionMap(hash, size);
}

struct HashTable* createHashTable(size_t size, size_t(*hashFunction)(char* key)) {
	struct HashTable* table = (struct HashTable*)malloc(sizeof(struct HashTable));
	if (table == NULL) {
		printf("failed to create hash table\n");
		exit(1);
	}
	table->strings = (struct List*)malloc(size * sizeof(struct List*));
	for (size_t i = 0; i < size; i++)
	{
		table->strings[i] = createList();
	}
	table->size = size;
	table->hashFunction = hashFunction;
	return table;
}

void freeHashTable(struct HashTable* table) {
	for (size_t i = 0; i < table->size; i++) {
		freeList(table->strings[i]);
	}
	free(table->strings);
	free(table);
}

void insertElementToTable(struct HashTable* table, char* key) {
	size_t index = table->hashFunction(key, table->size);
	insertToEnd(table->strings[index], 1, key);
}

struct Node* findElement(struct HashTable* table, char* key) {
	size_t index = table->hashFunction(key, table->size);
	struct Node* node = findNode(table->strings[index], key);
	return node;
}

void deleteElementFromTable(struct HashTable* table, char* key) {
	struct Node* node = findElement(table, key);
	if (node != NULL) {
		size_t index = table->hashFunction(key, table->size);
		deleteNode(table->strings[index], key);
	}
}

int getValue(struct HashTable* table, char* key) {
	struct Node* node = findElement(table, key);
	if (node != NULL) {
		return node->value;
	}
	else {
		return 0;
	}
}

void set(struct HashTable* table, char* key, int value) {//передать value который на 1 больше 
	struct Node* node = findElement(table, key);
	if (node != NULL) {
		node->value = value;
	}
	else {
		size_t index = table->hashFunction(key, table->size);
		insertToBegin(table->strings[index], value, key);
	}
}

size_t numberOfChains(struct HashTable* table) {
	size_t size = table->size;
	size_t count = 0;
	for (size_t i = 0; i < size; i++) {
		if (table->strings[i]->length > 0) {
			count++;
		}
	}
	return count;
}

size_t numberOfElemnts(struct HashTable* table) {
	size_t size = table->size;
	size_t number = 0;
	for (size_t i = 0; i < size; i++) {
		number += table->strings[i]->length;
	}
	return number;
}

size_t minChainLength(struct HashTable* table) {
	size_t size = table->size;
	size_t min = _UI32_MAX;
	for (size_t i = 0; i < size; i++) {
		if (table->strings[i]->length < min && table->strings[i]->length > 0) {
			min = table->strings[i]->length;
		}
	}
	return min;
}

size_t maxChainLength(struct HashTable* table) {
	size_t size = table->size;
	size_t max = table->strings[0]->length;
	for (size_t i = 0; i < size; i++) {
		if (table->strings[i]->length > max) {
			max = table->strings[i]->length;
		}
	}
	return max;
}

size_t averageChainLength(struct HashTable* table) {
	size_t size = table->size;
	size_t sumLength = 0;
	size_t numberOfChains = 0;
	for (size_t i = 0; i < size; i++) {
		if (table->strings[i]->length > 0) {
			sumLength += table->strings[i]->length;
			numberOfChains++;
		}
	}
	return sumLength / numberOfChains;
}

void getInformation(struct HashTable* myHashTable, struct Information* myInf) {
	myInf->numberOfElements = numberOfElemnts(myHashTable);
	myInf->numberOfChains = numberOfChains(myHashTable);
	myInf->minChainLength = minChainLength(myHashTable);
	myInf->maxChainLength = maxChainLength(myHashTable);
	myInf->averageChainLength = averageChainLength(myHashTable);
}