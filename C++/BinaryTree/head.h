#pragma once

#include <iostream>
#include <string>
#include <algorithm>
#include <cmath>
#include <typeinfo>
#include <vector>

using namespace std;


class Node {
public:
    Node(int setData, Node *parentNode);
    ~Node();

    Node *parent;
    Node *leftBranch;
    Node *rightBranch;

    int data;
};

class Heap {
public:
    Heap();
    ~Heap();

    void insert(int setData);
    void remove();
    void printTree();
    void sortTree();
    
    vector<int> addAlgorithm(int number);
    vector<int> goThrough();
    vector<int> findValueId(int value);

    int remove (int remData);
    int setBound(int newBound);
    int checkBound();
    int findIdValue(int id);
    int getSize();
    int getBound();

    int size;
    int bound;

    Node *root;
    Node *last;
};



