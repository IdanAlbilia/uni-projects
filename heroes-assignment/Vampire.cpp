#include "Vampire.h"

string Vampire::getName() {
	return "Vampire";
}
int Vampire::getQuantity() {
	return quantity;
}
int Vampire::specialPower(Creature &Enemy) { return 0; }

void Vampire::displayCreature() {
	cout << "Attack level: " << this->attackPower << ", Defense level: " << this->defense << "\n";
}

void Vampire::attack(Creature &Enemy) {
	int damage;
	damage = quantity * (attackPower + specialPower(Enemy));
	Enemy.hit(*this, damage);
}

int Vampire::getPower() {
	return attackPower;
}
void Vampire::hit(Creature &Enemy, int damage) {
	setQuantity(this->quantity - (damage / this->defense));
	if (this->quantity < 0)
		this->quantity = 0;
}

void Vampire::setQuantity(int q) {
	quantity = q;
}
