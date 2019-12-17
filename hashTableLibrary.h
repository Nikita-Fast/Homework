#include <stdlib.h>
#include "listLibrary.h"
#ifndef HASH_TABLE_LIBRARY INCLUDED
#define HASH_TABLE_LIBRARY INCLUDED

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

size_t constantHash(char* key, size_t size);
size_t symbolSumHash(char* key, size_t size);
size_t compressionMap(long long int hash, size_t size);
size_t polynomialHash(char* key, size_t size);
struct HashTable createHashTable(size_t size, size_t(*hashFunctionPtr)(char*, size_t));
void freeHashTable(struct HashTable* myHashTable);
void insertElement(struct HashTable* myHashTable, char* key, char* data, int value);
struct Node* findNode(struct List* list, char* data);
void insertElementExtended(struct HashTable* myHashTable, char* key, char* data, int value);
void deleteElement(struct HashTable* myHashTable, char* key);
void printHashTable(struct HashTable* myHashTable);
int numberOfChains(struct HashTable* myHashTable);
int numberOfElemnts(struct HashTable* myHashTable);
int minChainLength(struct HashTable* myHashTable);
int maxChainLength(struct HashTable* myHashTable);
int averageChainLength(struct HashTable* myHashTable);
void getInformation(struct HashTable* myHashTable, struct Information* myInf);

#endif HASH_TABLE_LIBRARY INCLUDED

