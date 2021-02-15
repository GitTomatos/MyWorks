#include "source.cpp"

// ostream& operator<<(ostream &os, MyBinary &myBin) {
// 	vector<int> BinNum;
// 	int intNumber = 0;
// 	int value = 1;
// 	int sign = 1;
// 	BinNum = myBin.getValue();
// 	if (BinNum[0] == 1) {
// 		sign = -1;
// 		BinNum[0] = 0;
// 	}
// 	for (int i = BinNum.size() - 1; i > 0; i--) {
// 		intNumber += BinNum[i] * value;
// 		value *= 2;
// 	}
// 	intNumber *= sign;
// 	os << intNumber;
// 	return os;
// }

int main() {
	vector<int> nums = {24, 31, 15, 20, 52, 6, 43, 11};
	Heap h1(nums);
	return 0;
}
