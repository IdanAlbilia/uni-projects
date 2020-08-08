#include "BlackDragon.h"

string BlackDragon::getName() {
	return "BlackDragon";
}
int BlackDragon::getQuantity() {
	return quantity;
}
int BlackDragon::specialPower(Creature &Enemy) { return 0; }

void BlackDragon::displayCreature() {
	cout << "Attack level: " << this->attackPower << ", Defense level: " << this->defense << "\n";
}

void BlackDragon::attack(Creature &Enemy) {
	int damage;
	damage = quantity * (attackPower + specialPower(Enemy));
	Enemy.hit(*this, damage);
}

int BlackDragon::getPower() {
	return attackPower;
}
void BlackDragon::hit(Creature &Enemy, int damage) {
	setQuantity(this->quantity - (damage / this->defense));
	if (this->quantity < 0)
		this->quantity = 0;
}

void BlackDragon::setQuantity(int q) {
	quantity = q;
}
