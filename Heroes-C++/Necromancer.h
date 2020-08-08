#pragma once
#include "Hero.h"
using namespace std;


class Necromancer : public Hero {

private:
	bool Cooldown = false;

public:
	Necromancer(string name) { Name = name; Class = "Necromancer"; };
	string GetClass();
	void Ability();
	void newTurn();
	void TAbility(Hero *victim) {};
};