#pragma once
#include "Hero.h"
using namespace std;


class Warrior : public Hero {

private:
	bool Cooldown = false;

public:
	Warrior(string name) { Name = name; Class = "Warrior"; };
	string GetClass();
	void Ability();
	void newTurn();
	void TAbility(Hero *victim) {};
};