#include "Warrior.h"



void Warrior::newTurn()
{
	Cooldown = false;
}

void Warrior::Ability()
{
	if (!Cooldown)
	{
		GainGold(50);
		cout << "Gold added successfully\n";
		Cooldown = true;
	}
	else
	{
		cout << "Already used this turn\n";
	}
}



string Warrior::GetClass()
{
	return "Warrior";
}