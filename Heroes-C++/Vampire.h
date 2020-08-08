#pragma once

#include "Creature.h"

class Vampire : public Creature {

public:
	Vampire() { name = "Vampire", price = 80, attackPower = 4, defense = 4, quantity = 0; }
	string getName();
	int getQuantity();
	void addToArmy(int Add) { quantity = quantity + Add; }
	int specialPower(Creature &c);
	void displayCreature();
	void attack(Creature &c);
	void hit(Creature &c, int damage);
	int getPower();
	void setQuantity(int q);
};
