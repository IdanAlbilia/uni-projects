#include "Zombie.h"

string Zombie::getName() {
	return "Zombie";
}
int Zombie::getQuantity() {
	return quantity;
}
int Zombie::specialPower(Creature &Enemy) {
	if (Enemy.getName() == "Archer") {
		return attackPower;
	}
	else
	{
		return 0;
	}

}
void Zombie::displayCreature() {
	cout << "Attack level: " << this->attackPower << ", Defense level: " << this->defense << "\n";
}

void Zombie::attack(Creature &Enemy) {
	int damage;
	damage = quantity * (attackPower + specialPower(Enemy));
	Enemy.hit(*this, damage);
}

int Zombie::getPower() {
	return attackPower;
}
void Zombie::hit(Creature &Enemy, int damage) {
	setQuantity(this->quantity - (damage / this->defense));
	if (this->quantity < 0)
		this->quantity = 0;
}

void Zombie::setQuantity(int q) {
	quantity = q;
}