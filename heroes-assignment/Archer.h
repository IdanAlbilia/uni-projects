#pragma once

#include "Creature.h"

class Archer : public Creature {

public:
	Archer() { name = "Archer", price = 90, attackPower = 5, defense = 4, quantity = 0; }
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
