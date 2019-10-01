#include <stdlib.h>

int bitAnd(int x, int y) {
    int result = ~(~x | ~y);
    return result;
}

int bitXor(int x, int y) {
    int result = ~(~(x & ~y) & ~(~x & y));
    return result;
}

int thirdBits(void) {
    int result = 36 << 8;
    result += 146;
    result = result << 8;
    result += 73;
    result = result << 8;
    result += 36;
    return result;
}

int fitsBits(int  x, int n) {
    int part_of_x = x >> (n + ~0);
    int result = !(part_of_x) | !(~part_of_x);
    return result;
}

int sign(int x) {
    return (x >> 31) | (!!x);
}

int getByte(int x, int n) {
    x >> (n << 3);
    x = x & 255;
    return x;
}

int logicalShift(int x, int n) {
    int mask = (1 << (33 + ~n)) + (~0);
    int result = (x >> n) & mask;
    return result;
}

int addOk(int x, int y) {
    int result = !((((x | y) ^ (x + y)) & (~(x ^ y))) >> 31);
    return result;
}

int bang(int x) {
    int result = (((x ^ (x + (~1 + 1))) >> 31) & 1) & (~(x >> 31));
    return result;
}

int conditional(int x, int y, int z) {
    int result = ((!x + (~0)) & y) | (~(!x + (~0)) & z);
    return result;
}

int isPower2(int x) {
    int result = !(x & (x + ~0)) & (~(x >> 31)) & (!!x);
    return result;
}







