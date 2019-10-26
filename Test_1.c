#include <stdio.h>
#include <stdlib.h>
#include <string.h >

int fibbonachiNumber(int n) {
    int result = 1;
    int x = 1;
    int y = 1;
    for (int i = 0; i < n-1; i++) {
        result = x + y;
        y = x;
        x = result;
    }
    return result;
}

int stringToNumber(char string[]) {
    int check = 0;
    int result = 0;
    size_t len = strlen(string);
    for (size_t i = 0; i < len; i ++) {

        switch (string[i]) {
            case '0': result +=0;
                break;
            case '1': result +=1;
                break;
            case '2': result +=2;
                break;
            case '3': result +=3;
                break;
            case '4': result +=4;
                break;
            case '5': result +=5;
                break;
            case '6': result +=6;
                break;
            case '7': result +=7;
                break;
            case '8': result +=8;
                break;
            case '9': result +=9;
                break;
            case ' ': result /= 10;
                break;
            default: check = 1; result /= 10;
                break;
        }
        if (check) {
            printf("the string contains a non-digit character, the answer is not complete\n");
            break;
        } else if (i != len-1) {
                result *= 10;
        }
    }
    return result;
}

void numbers_order(void) {
    printf("enter the number\n");
    int n = 0;
    scanf("%i", &n);
    printf("enter the numbers in range 0 to 255\n");
    int *p = malloc(n * sizeof(int));

    for (size_t i = 0; i < n; i++) {
        p[i] = -1;
    }
    int a = 0;
    size_t j = 0;
    int check = 0;
    for (size_t i=0; i < n; i++) {
        scanf("%i", &a);
        for(size_t k=0; k < j; k++) {
            if (p[k] == a) {
                check = 1;
            }
        }
        if (!check) {
            p[j] = a;
            j++;
        }
        check = 0;
    }
    while (*p != -1) {
        printf("%i ", *p);
        p++;
    }
}




