#include "Thief.h"



void Thief::newTurn()
{
	Cooldown = false;
}



void Thief::TAbility(Hero *victim)
{
	if (!Cooldown)
	{
		Cooldown = true;
		int toSteal = victim->GetGold();
		if (toSteal < 70)
		{
			GainGold(victim->GetGold());
			victim->LoseGold(victim->GetGold());
		}
		else
		{
			GainGold(70);
			victim->LoseGold(70);
		}
	}
}

string Thief::GetClass()
{
	return "Thief";
}