#pragma once
#include <iostream>
#include <string>
#include "Creature.h"
#include "Archer.h"
#include "BlackDragon.h"
#include "Mage.h"
#include "Vampire.h"
#include "Zombie.h"
using namespace std;

class Hero {

protected:
	string  Name;
	Creature *Army[5];
	string Class;
	int Gold;
	bool income;
	bool Alive;

public:
	Hero();
	void GainGold(int amount);
	void LoseGold(int amount);
	void Enlist();
	string GetName();
	void GetArmy();
	int GetArmySize();
	int GetGold();
	bool GetStatus();
	bool checkforCreature(string type);
	int getCreatureNum(string type);
	void Attack(Hero *Enemy);
	virtual string GetClass()=0;
	virtual void Ability()=0;
	virtual void TAbility(Hero *victim)=0;
	virtual void newTurn()=0;
	virtual ~Hero();
};