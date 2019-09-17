#include <iostream>
#include <string>

using namespace std;

int main()
{
    string new_string;
    getline(cin, new_string);
    int length = new_string.size();
    int open_bracket_counter{0};
    int close_bracket_counter{0};
    for(int i=0; i < length; i++)
    {
        if(new_string[i]=='(')
        {
            open_bracket_counter++;
        }
        if(new_string[i]==')')
        {
            close_bracket_counter++;
        }
        if(open_bracket_counter < close_bracket_counter)
        {
            break;
        }
    }
    if(open_bracket_counter == close_bracket_counter)
    {
        cout << "string is correct";
    }
    else
    {
        cout << "string is not correct";
    }
}
