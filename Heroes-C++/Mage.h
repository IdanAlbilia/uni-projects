#pragma once

#include "Creature.h"

class Mage : public Creature {

public:
	Mage() { name = "Mage", price = 150, attackPower = 8, defense = 2, quantity = 0; }
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
