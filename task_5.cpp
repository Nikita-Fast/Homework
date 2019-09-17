#include <iostream>

using namespace std;

void array_replace(int a[],int x, int y)
{
    int copy;
    int j{0};
    for(int i=x; i <= (x+y)/2; i++)
    {
        copy = a[i];
        a[i] = a[y-j];
        a[y-j] = copy;
        j++;
    }
}

int main()
{
    int m,n;
    cin >> m >> n;
    int a[m+n];
    for(int i=0; i<m+n; i++)
    {
        cin >> a[i];
    }
    int min, max;
    if(m<=n) {min = m; max = n;}
    else {min = n; max = m;}
    int copy;
    for(int i=0; i<min; i++)
    {
        copy = a[i];
        a[i] = a[i+max];
        a[i+max] = copy;
    }
    array_replace(a,min,max-1);
    if(m<n)
    {
        array_replace(a,0,min-1);
        array_replace(a,0, max-1);
    }
    else
    {
        array_replace(a,max,m+n-1);
        array_replace(a,min, m+n-1);
    }
    for(int i=0; i<m+n; i++)
    {
        cout << a[i]<<" ";
    }
}
