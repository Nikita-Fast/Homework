#include <iostream>
#include <math.h>
using namespace std;

void prime_number(int x)
{
    int dividers_counter = 0;
    for(int j=2; j<=sqrt(x); j++)
    {
        if (x%j==0)
        {
            dividers_counter ++;
        }
    }
    if(dividers_counter==0)
    {
        cout << x <<" ";
    }
}

int main()
{
    int max_number;
    cin >> max_number;
    for(int i=2; i<=max_number; i++)
    {
        prime_number(i);
    }
}
