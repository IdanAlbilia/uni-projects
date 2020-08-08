#pragma once
#include <string>
#include <iostream>

using namespace std;

class Creature {

protected:
	int attackPower;
	int defense;
	int price;
	string name;
	int quantity;

public:
	Creature() { attackPower = 0, defense = 0, price = 0, quantity = 0; };
	virtual string getName() = 0;
	virtual int getQuantity() = 0;
	virtual int getPower() = 0;
	virtual int specialPower(Creature &c) = 0;
	virtual void attack(Creature &c) = 0;
	virtual void displayCreature() = 0;
	virtual ~Creature();
	virtual void hit(Creature &c, int damage) = 0;
	virtual void setQuantity(int q) = 0;
	virtual void addToArmy(int Add) = 0;


};
