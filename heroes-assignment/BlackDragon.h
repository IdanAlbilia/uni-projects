#pragma once

#include "Creature.h"

class BlackDragon : public Creature {

public:
	BlackDragon() { name = "BlackDragon", price = 200, attackPower = 9, defense = 10, quantity = 0; }
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
