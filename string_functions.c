#include <stdio.h>

int strlen(char* pc) {
    int length = 0;
    int i = 0;
    while(*(pc + i) != '\0') {
        length ++;
        i ++;
    }
    return length;
}

void strcpy(char* dst, char* src) {
    int i = 0;
    while(*(src + i) != '\0') {
        *(dst + i) = *(src + i);
        i++;
    }
}

void strcat(char* dst, char* src) {
    while(*dst != '\0') {
        dst ++;
    }
    while(*src != '\0') {
        *dst = *src;
        dst ++;
        src ++;
    }
}

int strcmp(char* s1, char* s2) {
    while((*s1 == *s2) & (*s1 != '\0')) {
        s1 ++;
        s2 ++;
    }
    if(*s1 < *s2) {
        return -1;
    } else if(*s1 == *s2) {
        return 0;
    } else {
        return 1;
    }
}



