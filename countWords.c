#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include "listLibrary.h"
#include "hashTableLibrary.h"
#include <stdlib.h>
#include <crtdbg.h>
#define TABLE_SIZE 10000

#ifdef _CRTDBG_MAP_ALLOC
inline void* __cdecl operator new(unsigned int s)
{
	return ::operator new(s, _NORMAL_BLOCK, __FILE__, __LINE__);
}
#endif

#define new new( _NORMAL_BLOCK, __FILE__, __LINE__)

void printHashTable(struct HashTable* table) {
	size_t size = table->size;
	for (size_t i = 0; i < size; i++) {
		printList(table->strings[i]);
	}
}

char* normalizeWord(char* buffer) {
	size_t i = 0;
	size_t j = 0;
	char word[50];
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
	return word;
}

int main() {
	_CrtSetReportMode(_CRT_WARN, _CRTDBG_MODE_FILE);
	_CrtSetReportFile(_CRT_WARN, _CRTDBG_FILE_STDOUT);
	struct HashTable* table = createHashTable(TABLE_SIZE, polynomialHash);
	char name[] = "C:\\Users\\Nick Fast\\Desktop\\garbage\\forWhomTheBellTolls.txt";
	FILE* fp = fopen(name, "r");
	if (fp == NULL) {
		printf("failed to open file\n");
		return 1;
	}
	char buffer[50];
	while (fscanf(fp, "%s", buffer) != EOF) {
		if (buffer[0] != '\0') {
			char word[50];
			strcpy(word, normalizeWord(buffer));
			int count = getValue(table, word);
			set(table, word, count + 1); 
		}
	}
	fclose(fp); 
	printHashTable(table);
	freeHashTable(table);
	_CrtDumpMemoryLeaks();
	return 0; 
}