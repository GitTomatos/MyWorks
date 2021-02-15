#include "source.cpp"

ostream& operator<<(ostream &os, MyBinary &myBin) {
	vector<int> BinNum;
	int intNumber = 0;
	int value = 1;
	int sign = 1;
	BinNum = myBin.getValue();
	if (BinNum[0] == 1) {
		sign = -1;
		BinNum[0] = 0;
	}
	for (int i = BinNum.size() - 1; i > 0; i--) {
		intNumber += BinNum[i] * value;
		value *= 2;
	}
	intNumber *= sign;
	os << intNumber;
	return os;
}

int main() {
	while (true) {
		cout << "What do u want?" << endl << "1. Add numbers" << endl
				<< "2. Substract numbers" << endl << "3. Multiplicate numbers" << endl
				<< "4. Divide numbers" << endl << "0 - Exit" << endl;
		cout << "Your choice: ";
		int command;
		cin >> command;
		cout << endl;
		switch (command) {
			case 0:
				cout << endl << "Okay. It's the end!";
				exit(0);
			case 1: {
				int number;
				cout << "Enter 1st number: ";
				cin >> number;
				MyBinary num1(number);

				cout << "Enter 2nd number: ";
				cin >> number;
				MyBinary num2(number);

				MyBinary result(MyBinary::addition(num1, num2));
				if (result.getValue()[0] == -1){
					cout << endl;
					perror ("Overfill!!!");
					cout << endl << endl;
				}
				else{
					cout << "Answer = " << result << endl;
					cout << "Binary = " << MyBinary::toStringValue(result.getValue()) << endl << endl;
				}
				break;
			}
			case 2: {
				int number;
				cout << "Enter 1st number: ";
				cin >> number;
				MyBinary num1(number);

				cout << "Enter 2nd number: ";
				cin >> number;
				cout << endl;
				MyBinary num2(number);

				MyBinary result(MyBinary::substraction(num1,num2));

				if (result.getValue()[0] == -1){
					cout << endl;
					perror ("Overfill!!!");
					cout << endl << endl;
				} else
					cout << "Answer = " << result << endl;
					cout << "Binary = " << MyBinary::toStringValue(result.getValue()) << endl << endl;
				break;
			}
			case 3: {
				int number;
				cout << "Enter 1st number: ";
				cin >> number;
				MyBinary num1(number);

				cout << "Enter 2nd number: ";
				cin >> number;
				cout << endl;
				MyBinary num2(number);

				MyBinary result(MyBinary::multiplication(num1, num2));

				if (result.getValue()[0] == -1){
					cout << endl;
					perror ("Overfill!!!");
					cout << endl << endl;
				} else{
					cout << "Answer = " << result << endl;
					cout << "Binary = " << MyBinary::toStringValue(result.getValue()) << endl;
					cout << endl; 
				}
				break;
			}
			case 4: {
				int number;
				cout << "Enter 1st number: ";
				cin >> number;
				MyBinary num1(number);

				cout << "Enter 2nd number: ";
				cin >> number;
				cout << endl;
				MyBinary num2(number);
				MyBinary result(MyBinary::diversity(num1, num2));
				
				if (result.getValue()[0] == -1){
					cout << endl;
					perror ("To div in zero is prohibited!");
					cout << endl << endl;
				} else{
					cout << "Answer = " << result << endl;
					cout << "Binary = " << MyBinary::toStringValue(result.getValue()) << endl << endl;
				}
				break;
			}
		}
	}
	return 0;
}
