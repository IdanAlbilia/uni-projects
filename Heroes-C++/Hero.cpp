#define _CRT_SECURE_NO_WARNINGS
#include "Hero.h"
#include "Creature.h"
#include "Archer.h"
#include "BlackDragon.h"
#include "Mage.h"
#include "Vampire.h"
#include "Zombie.h"
#include <cstring>


void Hero::GainGold(int amount)
{
	if (Gold + amount > 2500)
		Gold = 2500;
	else
		Gold = Gold + amount;
}

Hero::Hero()
{
	Gold = 750;
	Army[0] = new BlackDragon();
	Army[1] = new Mage();
	Army[2] = new Archer();
	Army[3] = new Vampire();
	Army[4] = new Zombie();
	Alive = true;
}

Hero::~Hero() {
	for (int j = 0; j < 5; j++)
	{
		delete(Army[j]);
	}
}

void Hero::LoseGold(int amount)
{
	if (Gold - amount < 0)
		Gold = 0;
	else
		Gold = Gold - amount;
}

string Hero::GetName() {
	return Name;
}

void Hero::GetArmy() {
	if (Army[0]->getQuantity() > 0)
		cout << Army[0]->getQuantity() << " Black_Dragon ";
	if (Army[1]->getQuantity() > 0)
		cout << Army[1]->getQuantity() << " Wizard ";
	if (Army[2]->getQuantity() > 0)
		cout << Army[2]->getQuantity() << " Archer ";
	if (Army[3]->getQuantity() > 0)
		cout << Army[3]->getQuantity() << " Vampire ";
	if (Army[4]->getQuantity() > 0)
		cout << Army[4]->getQuantity() << " Zombie";
	if (GetArmySize() > 0)
		cout << "\n";
}


int Hero::GetArmySize() {
	int size = Army[0]->getQuantity() + Army[1]->getQuantity() + Army[2]->getQuantity() + Army[3]->getQuantity() + Army[4]->getQuantity();
	return size;
}

bool Hero::checkforCreature(string type)
{
	if (type == "Black_Dragon")
	{
		if (Army[0]->getQuantity() > 0)
			return true;
		return false;
	}
	if (type == "Wizard")
	{
		if (Army[1]->getQuantity() > 0)
			return true;
		return false;
	}
	if (type == "Archer")
	{
		if (Army[2]->getQuantity() > 0)
			return true;
		return false;
	}
	if (type == "Vampire")
	{
		if (Army[3]->getQuantity() > 0)
			return true;
		return false;
	}
	if (type == "Zombie")
	{
		if (Army[4]->getQuantity() > 0)
			return true;
		return false;
	}
}

int Hero::getCreatureNum(string type)
{
	if (type == "Black_Dragon")
	{
		return 0;
	}
	if (type == "Wizard")
	{
		return 1;
	}
	if (type == "Archer")
	{
		return 2;
	}
	if (type == "Vampire")
	{
		return 3;
	}
	if (type == "Zombie")
	{
		return 4;
	}
}

void Hero::Attack(Hero *Enemy)
{
	int turn = 0;
	string attacker;
	string defender;
	while ((GetArmySize() > 0) && (Enemy->GetArmySize() > 0))
	{
		switch (turn)
		{
		case 0:
		{
			cout << GetName() << " " << GetClass() << "\n";
			GetArmy();
			cout << Enemy->GetName() << " " << Enemy->GetClass() << "\n";
			Enemy->GetArmy();
			cin >> attacker >> defender;
			if (checkforCreature(attacker) && Enemy->checkforCreature(defender))
			{
				Army[getCreatureNum(attacker)]->attack(*(Enemy->Army[Enemy->getCreatureNum(defender)]));
				turn = 1;
			}
			else
				cout << "Creature doesnt exist!\n";
			break;
		}
		case 1:
		{
			cout << Enemy->GetName() << " " << Enemy->GetClass() << "\n";
			Enemy->GetArmy();
			cout << GetName() << " " << GetClass() << "\n";
			GetArmy();
			cin >> attacker >> defender;
			if (checkforCreature(defender) && Enemy->checkforCreature(attacker))
			{
				Enemy->Army[getCreatureNum(attacker)]->attack(*(Army[getCreatureNum(defender)]));
				turn = 0;
			}
			else
				cout << "Creature doesnt exist!";
			break;
		}
		}

	}
	if (GetArmySize() == 0)
	{
		cout << "You have been perished\n";
		Alive = false;
	}
	if (Enemy->GetArmySize() == 0)
	{
		cout << "You have been victorious\n";
		Enemy->Alive = false;
	}

}

int Hero::GetGold() {
	return Gold;
}


bool Hero::GetStatus() {
	if (Alive)
		return true;
	return false;
}


void Hero::Enlist()
{
	int i;
	int count;
	cout << "1.Buy Zombies. \n";
	cout << "2.Buy Archers. \n";
	cout << "3.Buy Vampire. \n";
	cout << "4.Buy Wizard. \n";
	cout << "5.Buy Black Dragon. \n";
	cin >> i;
	switch (i)
	{
	case(1): {
		cout << "Attack level: 2, Defense level: 5 \n";
		cin >> count;
		if (50 * count > Gold)
			cout << "You only have " << GetGold() << " gold! \n";
		else
		{
			LoseGold(50 * count);
			Army[4]->addToArmy(count);
		}
		break;
	}
	case(2): {
		cout << "Attack level: 5, Defense level: 4 \n";
		cin >> count;
		if (90 * count > Gold)
			cout << "You only have " << GetGold() << " gold! \n";
		else
		{
			LoseGold(90 * count);
			Army[2]->addToArmy(count);
		}
		break;
	}
	case(3): {
		cout << "Attack level: 4, Defense level: 4 \n";
		cin >> count;
		if (80 * count > Gold)
			cout << "You only have " << GetGold() << " gold! \n";
		else
		{
			LoseGold(80 * count);
			Army[3]->addToArmy(count);
		}
		break;
	}
	case(4): {
		cout << "Attack level: 8, Defense level: 2 \n";
		cin >> count;
		if (150 * count > Gold)
			cout << "You only have " << GetGold() << " gold! \n";
		else
		{
			LoseGold(150 * count);
			Army[1]->addToArmy(count);
		}
		break;
	}
	case(5): {
		cout << "Attack level: 9, Defense level: 10 \n";
		cin >> count;
		if (200 * count > Gold)
			cout << "You only have " << GetGold() << " gold! \n";
		else
		{
			LoseGold(200 * count);
			Army[0]->addToArmy(count);
		}
		break;
	}
	}
}
