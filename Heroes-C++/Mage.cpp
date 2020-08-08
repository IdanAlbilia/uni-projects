#include "Mage.h"

string Mage::getName() {
	return "Mage";
}
int Mage::getQuantity() {
	return quantity;
}
int Mage::specialPower(Creature &Enemy) {
	if (Enemy.getName() == "BlackDragon") {
		return defense;
	}
	else
		return 0;
}

void Mage::displayCreature() {
	cout << "Attack level: " << this->attackPower << ", Defense level: " << this->defense << "\n";
}

void Mage::attack(Creature &Enemy) {
	int damage;
	damage = quantity * (attackPower);
	Enemy.hit(*this, damage);
}

int Mage::getPower() {
	return attackPower;
}
void Mage::hit(Creature &Enemy, int damage) {
	int shield;
	shield = defense + (specialPower(Enemy));
	setQuantity(this->quantity - (damage / shield));
	if (this->quantity < 0)
		this->quantity = 0;
}

void Mage::setQuantity(int q) {
	quantity = q;
}
