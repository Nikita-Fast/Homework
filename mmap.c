#include <stdlib.h>
#include <stdio.h>
#include <io.h>
#include <fcntl.h>
#include <sys\stat.h>
#include "mman.h"
#include <string.h>

int comparator(const void* a1, const void* a2) {
	char* str1 = *(char**)a1;
	char* str2 = *(char**)a2;
	size_t i = 0;
	while (str1[i] == str2[i] && str1[i] != '\n') {
		i++;
	}
	return str1[i] - str2[i];
}

int main() {
	const char* inFileName = "C:\\Users\\Nick Fast\\Desktop\\garbage\\forWhomTheBellTolls.txt";
	int inFileDes = _open(inFileName, _O_RDONLY, _S_IREAD);
	if (inFileDes == -1) {
		printf("failed to open input file\n");
		return 1;
	}
	struct stat properties;
	fstat(inFileDes, &properties);
	size_t length = properties.st_size;

	char* mapPtr = (char*)mmap(0, length, PROT_READ, MAP_SHARED, inFileDes, 0);
	if (mapPtr == MAP_FAILED) {
		printf("failed to map file to memory\n");
		return 1;
	}

	char* mapPtrCopy = mapPtr;
	char* mapptr = mapPtr;
	size_t strCounter = 0;
	size_t maxStrLength = 0;
	size_t strLength = 0;
	while (*mapPtrCopy != '\0') {
		if (*mapPtrCopy == '\n') { 
			strCounter++;
			if (strLength > maxStrLength) {
				maxStrLength = strLength;
				strLength = 0;
			}
		}
		else {
			strLength++;
		}
		mapPtrCopy++;
	}

	char** pointers = (char**)malloc(strCounter * sizeof(char*));
	if (pointers == NULL) {
		printf("failed to allocate memory\n");
		return 1;
	}
	for (size_t i = 0; i < strCounter; i++) {
		pointers[i] = mapPtr;
		while (*mapPtr != '\n') {
			mapPtr++;
		}
		mapPtr++;
	}
	qsort(pointers, strCounter, sizeof(pointers[0]), comparator);
	
	FILE* file;
	char fileName[] = "C:\\Users\\Nick Fast\\Desktop\\garbage\\bestOutputEver.txt";
	file = fopen(fileName, "w");
	if (file == NULL) {
		printf("failed to open output file\n");
		return 1;
	}
	for (size_t i = 0; i < strCounter; i++) {
		while (*pointers[i] != '\n') { 
			fputc(*pointers[i]++, file);
		}
		fputc(*pointers[i], file);
	}
	fclose(file);
	_close(inFileDes);
	free(pointers);
	munmap(mapptr, length);
}



