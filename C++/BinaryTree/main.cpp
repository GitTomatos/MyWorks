#include "source.cpp"
#include <iostream>
#include <string>
#include <algorithm>
#include <cmath>
#include <typeinfo>
#include <vector>

using namespace std;


int main() {
    
    Heap heap;

    int num;

	
	while (true){
		cout << "What do u want?" << endl << "1 - Add element" << endl
				<< "2 - Delete Element" << endl << "3 - Find Id value" << endl
				<< "4 - Find Value's Id" << endl << "5 - Change Bound" << endl << "6 - Get current bound" << endl << "7 - Print Tree" << endl << "0 - Exit" << endl;
		cout << "Your choice: ";
		cin >> num;


		switch (num) {
			case 0:
				cout << endl << endl << "Okay. It's the end!";
				exit(0);
			case 1: {
				if (heap.checkBound() == 0)
					cout << endl << "Impossible. Bound. Max amount == " << heap.getSize() << endl << endl;
				else{
					cout << endl << "Enter value: ";
					cin >> num;
					heap.insert(num);
				}
				break;
			}
			case 2: {
				cout << "Enter value: ";
				cin >> num;
				int isOkay;
				isOkay = heap.remove(num);
				if (isOkay == -1){
					cout << endl << endl << "Tree is empty";
    				cout << endl << endl;
				}
				break;
			}
			case 3: {
				cout << endl << "Enter Id: ";
				cin >> num;
				int isOkay = heap.findIdValue(num);
				if (isOkay == -1)
					cout << endl << "There is no such id" << endl << endl;
                if (isOkay == -2)
					cout << endl << "Tree is empty" << endl << endl;
				break;
			}
			case 4: {
				cout << endl << "Enter Value: ";
				cin >> num;
				vector<int> ids = heap.findValueId(num);
				if (ids[0] == -1)
					cout << endl << "There is no such element" << endl << endl;
                if (ids[0] == -2)
					cout << endl << "Tree is empty" << endl << endl;
				break;
			}
			case 5: {
				cout << endl << "Enter Bound: ";
				cin >> num;
				int ans = heap.setBound(num); 
				if (ans == -1)
					cout << "Too small bound. Current size == " << heap.getSize() << endl << endl;
				else{
					cout << "Done!" << endl << endl;
				}
				break;
			}
            case 6: {
                cout << endl << "Current bound == " << heap.getBound() << endl << endl;
                break;
            }
			case 7: {
				cout << endl << "Tree:"; 
				heap.printTree();
				break;
			}
		}
	}




	return 0;
}
