#pragma once
#include "Hero.h"
using namespace std;


class Thief : public Hero {

private:
	bool Cooldown = false;

public:
	Thief(string name) { Name = name; Class = "Thief"; };
	string GetClass();
	void Ability() {};
	void newTurn();
	void TAbility(Hero *victim);
};