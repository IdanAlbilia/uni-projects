#include "Necromancer.h"



void Necromancer::newTurn()
{
	Cooldown = false;
}

void Necromancer::Ability()
{
	Army[4]->addToArmy(1);
}



string Necromancer::GetClass()
{
	return "Necromancer";
}