/*
 * Copyright (C) 2017 ykonoclast
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.duckdns.spacedock.upengine.libupsystem;

import org.junit.Assert;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author ykonoclast
 */
public class IntegStatJetsTest
{

    @Test
    public void testJetTrait()
    {
	Perso persoRM3 = new Perso(3);
	assertTrue(IntegStatTestUtils.reussiteStatistiqueJetsTrait(persoRM3, 11, GroupeTraits.Trait.PRESENCE));
	assertFalse(IntegStatTestUtils.reussiteStatistiqueJetsTrait(persoRM3, 12, GroupeTraits.Trait.PRESENCE));

	assertTrue(IntegStatTestUtils.reussiteStatistiqueJetsTrait(persoRM3, 23, GroupeTraits.Trait.COORDINATION));
	assertFalse(IntegStatTestUtils.reussiteStatistiqueJetsTrait(persoRM3, 24, GroupeTraits.Trait.COORDINATION));
    }

    @Test
    public void testAttaquer()//sert aussi pour tester tout le système de jets de compétence : il se trouve qu'attaquer est la forme la plus complexe du jet de compétence, une fois tous les bonus et malus pris en compte
    {
	Perso persoRM1 = new Perso(1);
	Perso persoRM3 = new Perso(3);
	Perso persoRM5 = new Perso(5);

	//cas du physique minimal insuffisant avec une hache à deux mains et un physique de 2
	persoRM1.getInventaire().addArme(new ArmeCaC(9, Arme.QualiteArme.moyenne, Arme.EquilibrageArme.normal), Inventaire.Lateralisation.DROITE);
	Assert.assertTrue(IntegStatTestUtils.reussiteStatistiqueAttaque(persoRM1, 4, 0, 0));
	Assert.assertFalse(IntegStatTestUtils.reussiteStatistiqueAttaque(persoRM1, 5, 0, 0));

	//attaque à mains nues avec RM3
	Assert.assertTrue(IntegStatTestUtils.reussiteStatistiqueAttaque(persoRM3, 39, 0, 0));
	Assert.assertFalse(IntegStatTestUtils.reussiteStatistiqueAttaque(persoRM3, 40, 0, 0));

	//attaque en prenant en compte le malus à l'attaque du sabre et RM3
	persoRM3.getInventaire().addArme(new ArmeCaC(8, Arme.QualiteArme.inferieure, Arme.EquilibrageArme.bon), Inventaire.Lateralisation.DROITE);//la qualité ne devrait pas influer, l'équilibrage non plus, ni ici ni dans les autres tests de cette méthode
	Assert.assertTrue(IntegStatTestUtils.reussiteStatistiqueAttaque(persoRM3, 37, 0, 0));
	Assert.assertFalse(IntegStatTestUtils.reussiteStatistiqueAttaque(persoRM3, 38, 0, 0));

	//attaque à distance au fusil d'assaut coup par coup avec RM5 et portée courte
	persoRM5.getInventaire().addArme(new ArmeDist(44, Arme.QualiteArme.superieure, Arme.EquilibrageArme.bon), Inventaire.Lateralisation.DROITE);
	((ArmeDist) persoRM5.getInventaire().getArmeCourante()).recharger(30);
	Assert.assertTrue(IntegStatTestUtils.reussiteStatistiqueAttaque(persoRM5, 55, 300, 1));//pile la portée, donc courte
	Assert.assertFalse(IntegStatTestUtils.reussiteStatistiqueAttaque(persoRM5, 57, 300, 2));//deux balles ne devraient rien changer, on n'est pas au seuil de rafale courte

	//attaque à distance au fusil d'assaut en rafale courte avec RM3 et portée courte
	persoRM3.getInventaire().removeArme(Inventaire.Lateralisation.DROITE);
	persoRM3.getInventaire().addArme(new ArmeDist(44, Arme.QualiteArme.inferieure, Arme.EquilibrageArme.normal), Inventaire.Lateralisation.DROITE);
	((ArmeDist) persoRM3.getInventaire().getArmeCourante()).recharger(30);
	Assert.assertTrue(IntegStatTestUtils.reussiteStatistiqueAttaque(persoRM3, 34, 100, 3));
	Assert.assertFalse(IntegStatTestUtils.reussiteStatistiqueAttaque(persoRM3, 36, 20, 3));

	//attaque à distance au fusil d'assaut en rafale moyenne avec RM3 et portée longue
	persoRM3.getInventaire().removeArme(Inventaire.Lateralisation.DROITE);
	persoRM3.getInventaire().addArme(new ArmeDist(44, Arme.QualiteArme.maitre, Arme.EquilibrageArme.mauvais), Inventaire.Lateralisation.DROITE);
	((ArmeDist) persoRM3.getInventaire().getArmeCourante()).recharger(30);
	Assert.assertTrue(IntegStatTestUtils.reussiteStatistiqueAttaque(persoRM3, 41, 400, 8));//donc deux groupes entiers de trois balles
	Assert.assertFalse(IntegStatTestUtils.reussiteStatistiqueAttaque(persoRM3, 42, 540, 8));

	//attaque à distance au fusil d'assaut en rafale longue avec RM5 et portée courte
	persoRM5.getInventaire().removeArme(Inventaire.Lateralisation.DROITE);
	persoRM5.getInventaire().addArme(new ArmeDist(44, Arme.QualiteArme.moyenne, Arme.EquilibrageArme.mauvais), Inventaire.Lateralisation.DROITE);
	((ArmeDist) persoRM5.getInventaire().getArmeCourante()).recharger(30);
	Assert.assertTrue(IntegStatTestUtils.reussiteStatistiqueAttaque(persoRM5, 64, 60, 13));//deux groupes entiers de 5 balles
	Assert.assertFalse(IntegStatTestUtils.reussiteStatistiqueAttaque(persoRM5, 68, 20, 13));
    }
}