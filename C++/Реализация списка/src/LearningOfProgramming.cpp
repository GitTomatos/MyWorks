#include <iostream>
#include <string>
#include "MyList.cpp"
#include <vector>
using namespace std;

int main() {
	int command;
	int s;
	while (true) {
		cout << "Input size(0 for infinite size) --> ";
		cin >> s;
		if (s < 0) {
			perror("Incorrect length of List. It have to be 1 and more.");
			continue;
		}

		cout << endl;
		break;
	}
	MyList<int> lst(s);

	while (true) {
		cout << "Input the command:" << endl << "1 - add element to the end"
				<< endl << "2 - remove first element" << endl
				<< "3 - delete all elements of List" << endl
				<< "4 - find element" << endl << "5 - remove element by index"
				<< endl << "6 - print List" << endl << "7 - print Size of List"
				<< endl << "8 - increase Size of List" << endl
				<< "9 - print max Size of List" << endl
				<< "10 - delete all similar elements" << endl
				<< "11 - find all similar elements" << endl << "0 - exit"
				<< endl;
		cin >> command;
		switch (command) {
		case 0:
			cout << endl << "Okay. It's the end!";
			exit(0);
		case 1:{
			cout << "input the element you want to add" << endl;
			int el;
			cin >> el;
			int fail;
			fail = lst.addToTheEnd(el);
			if (fail == -1)
				perror("List is full");
			else
				cout << "Added" << endl << endl;
			break;
		}
		case 2:{
			int fail;
			fail = lst.removeFirst();
			if (fail == -1)
				perror("The List is empty. You can't delete first element.");
			else
				cout << "First element was removed" << endl << endl;
			break;
		}
		case 3:
			lst.removeAll();
			break;
		case 4:{
			int fail;
			int el;
			cout << "Input element to find" << endl;
			cin >> el;
			fail = lst.findIndexOfElement(el);
			if (fail == -1)
				perror("The List is empty.");
			else if (fail == -2)
				perror("There is no such element.");
			else
				cout << "Index of " << el << " is " << fail << endl;
			break;
		}
		case 5:{
			int fail;
			cout << "Input index to remove" << endl;
			int ind;
			cin >> ind;
			fail = lst.removeByIndex(ind);
			if (fail == -1)
				perror("The List is empty. You can't delete elements.");
			else
				cout << "The element was removed" << endl;
			break;
		}
		case 6:{
			int fail;
			fail = lst.print();
			if (fail == -1)
				cout << "List is Empty" << endl;
			break;
		}
		case 7:
			cout << "Size o List == " << lst.GetSize() << endl;
			break;
		case 8:{
			int fail;
			int incSize;
			cout << "Input size(0 for infinite size) --> ";
			cin >> incSize;
			cout << endl;
			fail = lst.increaseSize(incSize);
			if (fail == -1)
				perror("This List is infinite. You can't increase its size");
			else if (fail == -2)
				perror("Your number should be >= 0");
			else
				cout << "Size was increased" << endl;
			break;
		}
		case 9:{
			int maxSize;
			maxSize = lst.GetMaxSize();
			if (maxSize == 0) {
				cout << "The list is infinite" << endl << endl;
			} else {
				cout << "max Size o List == " << maxSize << endl << endl;
			}
			break;
		}
		case 10:{
			int el;
			cout << "Input element to find" << endl;
			cin >> el;
			lst.removeAllByElement(el);
			break;
		}
		case 11: {
			int el;
			cout << "Input element to find" << endl;
			cin >> el;
			vector<int> indexes;
			indexes = lst.findAllIndexesOfElement(el);
			if (indexes.empty())
				cout << "There is no such elements" << endl;
			else if (indexes[0] == -1)
				cout << "There is no such elements, because the list is empty"
						<< endl;
			else {
				cout << "Indexes of " << el << " are: ";
				for (int i = 0; i < indexes.size(); i++)
					cout << indexes[i] << " ";
				cout << endl << endl;
			}
			break;
		}
		default:
			cout << "There is no such command. Try Again." << endl;
			break;
		}
	}
}
