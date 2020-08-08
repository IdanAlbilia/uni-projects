#include "Archer.h"

string Archer::getName() {
	return "Archer";
}
int Archer::getQuantity() {
	return quantity;
}
int Archer::specialPower(Creature &Enemy) {
	if (Enemy.getName() == "BlackDragon") {
		return attackPower * 0.2;
	}
	else
		return 0;
}

void Archer::displayCreature() {
	cout << "Attack level: " << this->attackPower << ", Defense level: " << this->defense << "\n";
}

void Archer::attack(Creature &Enemy) {
	int damage;
	damage = quantity * (attackPower + specialPower(Enemy));
	Enemy.hit(*this, damage);
}

int Archer::getPower() {
	return attackPower;
}
void Archer::hit(Creature &Enemy, int damage) {
	setQuantity(this->quantity - (damage / this->defense));
	if (this->quantity < 0)
		this->quantity = 0;
}

void Archer::setQuantity(int q) {
	quantity = q;
}
