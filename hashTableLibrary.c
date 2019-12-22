#include <stdlib.h>
#include <stdio.h>
#include "listLibrary.h"
#include <string.h>
#include <limits.h>

struct HashTable {
	struct List* arrayOfLists;
	size_t size;
	size_t(*hashFunction)(char*, size_t);
};

struct Information {
	size_t numberOfElements;
	size_t numberOfChains;
	size_t minChainLength;
	size_t maxChainLength;
	size_t averageChainLength;
};

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

struct HashTable createHashTable(size_t size, size_t(*hashFunctionPtr)(char*, size_t)) {
	struct HashTable myHashTable;
	myHashTable.arrayOfLists = (struct List*)malloc(size * sizeof(struct List));
	if (myHashTable.arrayOfLists == NULL) {
		exit(1);
	}
	for (size_t i = 0; i < size; i++) {
		myHashTable.arrayOfLists[i] = *createList();   //is it good idea?
	}
	myHashTable.hashFunction = hashFunctionPtr;
	myHashTable.size = size;
	return myHashTable;
}

void freeHashTable(struct HashTable* myHashTable) {
	size_t length = myHashTable->size;
	for (size_t i = 0; i < length; i++) {
		clearList(&(myHashTable->arrayOfLists[i]));
	}
	free(myHashTable->arrayOfLists);
}

void insertElement(struct HashTable* myHashTable, char* key, int value) {
	size_t index = myHashTable->hashFunction(key, myHashTable->size);
	insertToEnd(&(myHashTable->arrayOfLists[index]), createNode(value, key));
}

struct Node* findNode(struct HashTable* table, char* key) { 
	size_t index = table->hashFunction(key, table->size);
	if (table->arrayOfLists[index].length == 0) {
		return NULL;
	}
	else {
		struct List* list = &(table->arrayOfLists[index]);
		struct Node* node = list->head;
		while (node != NULL) {
			if (strcmp(node->key, key) == 0) {
				return node;
			}
			node = node->next;
		}
		return NULL;
	}
}

void insertElementExtended(struct HashTable* table, char* key, int value) { //for the job about counting words in the text
	size_t index = table->hashFunction(key, table->size);
	struct Node* node = findNode(table, key);
	if (node == NULL) {
		insertElement(table, key, value);
	}
	else {
		node->val++;
	}
}

void deleteElement(struct HashTable* table, char* key) {
	struct Node* node = findNode(table, key);
	if (node != NULL) {
		size_t index = table->hashFunction(key, table->size);
		removeNode(&(table->arrayOfLists[index]), node);
	}
}

void printHashTable(struct HashTable* myHashTable) {
	size_t size = myHashTable->size;
	struct Node* currentNode = NULL;
	for (size_t i = 0; i < size; i++) {
		if (myHashTable->arrayOfLists[i].length > 0) {
			printList(&myHashTable->arrayOfLists[i]);
		}
	}
}

int numberOfChains(struct HashTable* myHashTable) {
	size_t size = myHashTable->size;
	size_t count = 0;
	for (size_t i = 0; i < size; i++) {
		if (myHashTable->arrayOfLists[i].length > 0) {
			count++;
		}
	}
	return count;
}

int numberOfElemnts(struct HashTable* myHashTable) {
	size_t size = myHashTable->size;
	size_t number = 0;
	for (size_t i = 0; i < size; i++) {
		number += myHashTable->arrayOfLists[i].length;
	}
	return number;
}

int minChainLength(struct HashTable* myHashTable) {
	size_t size = myHashTable->size;
	size_t min = _UI32_MAX;
	for (size_t i = 0; i < size; i++) {
		if (myHashTable->arrayOfLists[i].length < min && myHashTable->arrayOfLists[i].length > 0) {
			min = myHashTable->arrayOfLists[i].length;
		}
	}
	return min;
}

int maxChainLength(struct HashTable* myHashTable) {
	size_t size = myHashTable->size;
	size_t max = myHashTable->arrayOfLists->length;
	for (size_t i = 0; i < size; i++) {
		if (myHashTable->arrayOfLists[i].length > max) {
			max = myHashTable->arrayOfLists[i].length;
		}
	}
	return max;
}

int averageChainLength(struct HashTable* myHashTable) {
	size_t size = myHashTable->size;
	size_t sumLength = 0;
	size_t numberOfChains = 0;
	for (size_t i = 0; i < size; i++) {
		if (myHashTable->arrayOfLists[i].length > 0) {
			sumLength += myHashTable->arrayOfLists[i].length;
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

