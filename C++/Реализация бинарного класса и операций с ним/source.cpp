#include "head.h"


MyBinary::MyBinary(int number) {
    binNumber = MyBinary::createBinVec(number);
	if (number < 0)
        is_inversed = 1;
}

MyBinary::~MyBinary() {
}

vector<int> MyBinary::reverse(vector<int> initial){
	vector<int> reversed;
	for (int i = digitCapacity - 1; i >= 0; i--)
		reversed.push_back(initial[i]);
	
	return reversed;
}

//Иногда нужен только бинарный код, без остальных полей бинарного класса
vector<int> MyBinary::createBinVec(int number) {
	vector<int> binNum;
	int sign;
	if (number >= 0)
		sign = 0;
	else
		sign = 1;
	int length = pow(2, digitCapacity - 1);
	number = abs(number) % length;

	if (number == 0) {
		for (int i = 0; i < digitCapacity; i++)
			binNum.push_back(0);
	} else {
		while (number / 2 != 0) {
			binNum.push_back(number % 2);
			number /= 2;
		}
		binNum.push_back(1);

		int addZeroes = digitCapacity - binNum.size() - 1;
		for (int i = 0; i < addZeroes; i++) {
			binNum.push_back(0);
		}
		binNum.push_back(sign);
	}

	return reverse(binNum);
}

vector<int> MyBinary::toAdditCode(vector<int> neg_vec){
	if (neg_vec[0] == 1){
		neg_vec = MyBinary::inverseCode(neg_vec);
		neg_vec = MyBinary::vecAddition(neg_vec, MyBinary::createBinVec(1));
	}
	return neg_vec;
}

vector<int> MyBinary::toNormalCode(vector<int> result){
	vector<int> minus_one = createBinVec(-1);
	minus_one = toAdditCode(minus_one);
	result = vecAddition(result, minus_one);
	result = inverseCode(result);
	return result;
}


//Чтобы можно было складывать, не создавая бинарные объекты
vector<int> MyBinary::vecAddition(vector<int> binNum1, vector<int> binNum2) {
	vector<int> result;
	int addition = 0;

	for (int i = binNum1.size() - 1; i >= 0; i--) {
		string k;
		int sum;
		sum = binNum1[i] + binNum2[i] + addition;
		addition = sum / 2;
		result.push_back(sum % 2);
	}
	if (addition == 1)
		result.push_back(1);

	if (result.size() > MyBinary::digitCapacity) {
		int overDigitsAmount = result.size() - digitCapacity;
		for (int i = 0; i < overDigitsAmount; i++)
			result.pop_back();
	}
	return reverse(result);
}


//
MyBinary MyBinary::addition(MyBinary add1, MyBinary add2) {
	vector<int> result;

	vector<int> binNumber1 = add1.binNumber;
	vector<int> binNumber2 = add2.binNumber;

	binNumber1 = toAdditCode(binNumber1);
	binNumber2 = toAdditCode(binNumber2);

	result = MyBinary::vecAddition(binNumber1, binNumber2);

	if ((add1.is_inversed + add2.is_inversed) != 0)
		if (result[0] == 1){
			result = MyBinary::toNormalCode(result);
		}


	if ((add1.is_inversed == add2.is_inversed) && result[0] != add1.is_inversed){
		result[0] = -1;	
		MyBinary mb;
		mb.binNumber = result;
		return mb;
	}


	MyBinary mb;
	mb.binNumber = result;
	return mb;
}

MyBinary MyBinary::substraction(MyBinary substract1, MyBinary substract2) {
	
	if (substract2.binNumber[0] == 1){
		substract2.is_inversed = 0;
	} else {
		substract2.is_inversed = 1;
	}

	substract2.binNumber[0] =
			!substract2.binNumber[0];

	return MyBinary::addition(substract1, substract2);

}

MyBinary MyBinary::multiplication(MyBinary mult1, MyBinary mult2) {
	vector<int> mn1 = mult1.binNumber;
	vector<int> mn2 = mult2.binNumber;

	int sign = (mn1[0] + mn2[0]) % 2;
	mn1[0] = 0;
	mn2[0] = 0;

	MyBinary mbResult;
	MyBinary mbNext;

	vector<int> subMultResult;
	for (int j = digitCapacity - 1; j >= 0; j--) {
		subMultResult.push_back(mn2[digitCapacity - 1] * mn1[j]);
	}
	mbResult.binNumber = reverse(subMultResult);

	for (int i = digitCapacity - 2; i >= 0; i--) {
		vector<int> subMultNext;
		for (int j = digitCapacity - 1; j >= 0; j--) {
			subMultNext.push_back(mn2[i] * mn1[j]);
		}
		mbNext.binNumber = reverse(subMultNext);
		subMultNext.clear();
		
		for (int k = 0; k < digitCapacity - i - 1; k++) {
			mbResult.binNumber.insert(mbResult.binNumber.begin(), 0);
			mbNext.binNumber.push_back(0);
		}
		mbResult = MyBinary::addition(mbResult, mbNext);

	}

	MyBinary zero(0);
	mbResult = MyBinary::addition(mbResult, zero);

	if (mbResult.binNumber[0] == -1)
		return mbResult;
	else
		mbResult.binNumber[0] = sign;


	return mbResult;
}

int MyBinary::compare(vector<int> num1, vector<int> num2){
	for (int i = 0; i <= digitCapacity - 1; i++){
		if (num1[i] > num2[i])
			return 1;
		else if (num1[i] < num2[i])
			return 2;
		else
			continue;
	}
	return 0;
}


MyBinary MyBinary::diversity(MyBinary div1, MyBinary div2) {
	vector<int> binNum = createBinVec();
	vector<int> divater = createBinVec();
	vector<int> res;

	for (int i = 0; i < digitCapacity; i++){
		binNum[i] = div1.binNumber[i];
		divater[i] = div2.binNumber[i];
	}

	int sign = (binNum[0] + divater[0]) % 2;
	binNum[0] = 0;
	divater[0] = 0;

	if (divater == createBinVec()){
		MyBinary res(0);
		res.binNumber[0] = -1;
		return res;
	}
	if (compare(binNum, divater) == 2){ 
		MyBinary res(0);
		return res;
	} else if (compare(binNum, divater) == 0){
		MyBinary res(1);
		return res;
	} 
	
	else{
		int mover = 0;
		while (divater[mover] == 0){
			mover++;
		}

		for (int i = 0; i < mover; i++){
			divater.push_back(0);
		}

		for (int i = 0; i < mover; i++){
			divater.erase(divater.begin());
		}

		
		for (int i = mover; i >= 0; i--){
			if (compare(binNum, divater) < 2){
				res.push_back(1);
				MyBinary mb1(0);
				MyBinary mb2(0);
				mb1.binNumber = binNum;
				mb2.binNumber = divater;
				binNum = substraction(mb1, mb2).binNumber;
			}
			else{
				res.push_back(0);
			}

			divater.insert(divater.begin(), 0);
			divater.pop_back();

		}
		vector<int> resultedResult = createBinVec();
		mover = digitCapacity - res.size();
		for (int i = mover; i < digitCapacity; i++)
			resultedResult[i] = res[i - mover];
		
		resultedResult[0] = sign;
		MyBinary result(0);
		result.binNumber = resultedResult;
		return result;
	}

}

vector<int> MyBinary::inverseCode(vector<int> code) {
	vector<int> newCode;
	if (code[0] == 1) {
		newCode.push_back(1);
		for (int i = 1; i <= code.size() - 1 ; i++){
            int num = (code[i]+1)%2;
            newCode.push_back(num);
        }
	} else 
		newCode = code;
	
	return newCode;
}

vector<int> MyBinary::getValue() {
	return binNumber;
}

string MyBinary::toStringReversedValue(vector<int> binNumber) {
	string str_value;
	for (int i = binNumber.size() - 1; i >= 0; i--)
		str_value += to_string(binNumber[i]);
	return str_value;
}

//
string MyBinary::toStringValue(vector<int> binNumber) {
	string str_value;
	for (int i = 0; i < binNumber.size(); i++)
		str_value += to_string(binNumber[i]);
	return str_value;
}



