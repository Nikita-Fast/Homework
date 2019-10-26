#include <stdio.h>

size_t strlen(char* pc) {
    size_t length = 0;
    while (*(pc + length) != '\0') {
        length++;
    }
    return length;
}

void strcpy(char* dst, char* src) {
    while (*src != '\0') {
       *dst = *src;
        dst++;
        src++;
    }
}

void strcat(char* dst, char* src) {
    while (*dst != '\0') {
        dst++;
    }
    while (*src != '\0') {
       *dst = *src;
        dst++;
        src++;
    }
}

int strcmp(char* s1, char* s2) {
    while ((*s1 == *s2) && (*s1 != '\0')) {
        s1++;
        s2++;
    }
    if (*s1 < *s2) {
        return -1;
    } else if (*s1 == *s2) {
        return 0;
    } else {
        return 1;
    }
}


