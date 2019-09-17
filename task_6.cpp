#include<iostream>
using namespace std;

int main()
{
    int k{0};
    for(int i=100000;i<1000000;i++)
    {
        if((i%10) + ((i%100)/10) + ((i%1000)/100) == (i/100000) + ((i/10000)%10) + ((i/1000)%10))
        {
            k++;
        }
    }
    cout << k;
}
