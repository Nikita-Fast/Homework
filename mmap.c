#include <stdlib.h>
#include <stdio.h>
#include <io.h>
#include <fcntl.h>
#include <sys\types.h>
#include <sys\stat.h>
#include "mman.h"
#include <string.h>

int strCmp(char* str1, char* str2) {
	while (*str1 == *str2 && *str1 != '\n') {
		str1++;
		str2++;
	}
	if (*str1 == *str2) {
		return 0;
	}
	else {
		if (*str1 != '\n' && *str1 != '\n') {
			return *str1 - *str2;
		}
		else {
			if (*str1 == '\n' && *str2 != '\n') {
				return -1;
			}
			if (*str1 != '\n' && *str2 == '\n') {
				return 1;
			}
		}
	}
}

void qStringSort(char** pointers, size_t low, size_t high)
{
	size_t l = low, r = high;
	char* pivot = pointers[(l + r) / 2];
	while (l <= r)
	{
		while (strCmp(pointers[l], pivot) < 0) {
			l++;
		}
		while (strCmp(pointers[r], pivot) > 0) {
			r--;
		}
		if (l <= r) {
			char* copy = pointers[l];
			pointers[l++] = pointers[r];
			pointers[r--] = copy;
		}
	}
	if (low < r)
		qStringSort(pointers, low, r);
	if (high > l)
		qStringSort(pointers, l, high);
}

int main() {
	const char* inFileName = "C:\\Users\\Nick Fast\\Desktop\\garbage\\prokaryote_type_strain_report.txt";
	int inFileDes = _open(inFileName, _O_RDONLY, _S_IREAD);
	
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
		if (*mapPtrCopy == '\n') { //попробовать заменить символ новой строки на теринальный ноль
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
	//strCounter++;             //the last string with '\0'
	
	char** pointers = (char**)malloc(strCounter * sizeof(char*));
	if (pointers != NULL) {
		for (size_t i = 0; i < strCounter; i++) {
			pointers[i] = mapPtr;
			while (*mapPtr != '\n') {
				mapPtr++;
			}
			mapPtr++;
		}
		qStringSort(pointers, 0, strCounter - 1);
	
		FILE* file;
		char fileName[] = "C:\\Users\\Nick Fast\\Desktop\\garbage\\output.txt";
		file = fopen(fileName, "w");
		for (size_t i = 0; i < strCounter; i++) {
			while (*pointers[i] != '\n') { //*pointers[i] != '\n' &&
				fputc(*pointers[i]++, file);
			}
			fputc(*pointers[i], file);
		}
		fclose(file);
		free(pointers);
	}
	munmap(mapptr, length);
}