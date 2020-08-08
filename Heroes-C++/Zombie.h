#pragma once

#include "Creature.h"

class Zombie : public Creature {

public:
	Zombie() { name = "Zombie", price = 50, attackPower = 2, defense = 5, quantity = 0; }
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
