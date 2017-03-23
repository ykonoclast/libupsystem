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

import org.duckdns.spacedock.commonutils.ErrorHandler;
import org.duckdns.spacedock.commonutils.PropertiesHandler;

/**
 * Classe représentant une arme et permettant de générer des dégâts avec
 * celle-ci.Elle est abstraite car l'on ne doit pouvoir instancier que ses
 * dérivées qui sont porteuses du code signifiant pour le CaC et le CaD
 *
 * @author ykonoclast
 */
public abstract class Arme
{

    /**
     * le nombre de mains nécessaire au maniement de l'arme
     */
    private final int m_NbMainsArme;
    /**
     * le bonus apporté à l'initiative totale
     */
    private final int m_bonusInit;
    /**
     * la catégorie d'arme (permet de définir la compétence à utiliser)
     */
    private final int m_categorie;
    /**
     * le nombre de dés gardés dans la VD
     */
    private final int m_desGardes;
    /**
     * le nombre de dés lancés dans la VD
     */
    private final int m_desLances;
    /**
     * le malus donné par l'arme à l'attaque
     */
    private final int m_malusAttaque;
    /**
     * le mode d'attaque de l'arme
     */
    private final int m_mode;
    /**
     * le nom de l'arme
     */
    private final String m_nom;
    /**
     * le physique minimal pour manier l'arme.
     */
    private final int m_physMin;
    /**
     * le type de l'arme : simple, perce-amure, pénétrante, perce-blindage ou
     * energétique, respectivement de 0 à 4
     */
    private final int m_typeArme;

    /**
     * constructeur d'arme de corps à corps à parti de la référence UP!
     *
     * @param p_indice
     * @param p_qualite la qualite de l'arme
     * @param p_equilibrage l'equilibrage de l'arme, ignoré si l'arme est de
     * maître
     */
    public Arme(int p_indice, QualiteArme p_qualite, EquilibrageArme p_equilibrage)
    {

	UPReferenceArmes referenceArm = UPReferenceArmes.getInstance();
	UPReferenceSysteme referenceSys = UPReferenceSysteme.getInstance();
	String nom = referenceArm.getLblArme(p_indice);
	nom = nom.concat(" ");
	int bonusLances = 0;
	int bonusGardes = 0;
	int bonusInitSup = 0;

	//récupération des éléments liés à la qualité et l'équilibrage de l'arme
	if (p_qualite == QualiteArme.maitre)//traitement spécial des armes de maître
	{
	    nom = nom.concat((String) referenceArm.getListQualiteArme().get(QualiteArme.maitre));
	    ++bonusLances;
	    ++bonusGardes;
	    ++bonusInitSup;
	}
	else
	{
	    nom = nom.concat(referenceSys.libelles.liaison);
	    nom = nom.concat(" ");
	    nom = nom.concat(referenceSys.libelles.qualite);
	    nom = nom.concat(" ");

	    switch (p_qualite)
	    {
		case inferieure:
		    --bonusLances;
		    nom = nom.concat((String) referenceArm.getListQualiteArme().get(QualiteArme.inferieure));
		    break;
		case moyenne:
		    nom = nom.concat((String) referenceArm.getListQualiteArme().get(QualiteArme.moyenne));
		    break;
		case superieure:
		    ++bonusLances;
		    nom = nom.concat((String) referenceArm.getListQualiteArme().get(QualiteArme.superieure));
		    break;
	    }

	    nom = nom.concat(" ");
	    nom = nom.concat(referenceSys.libelles.addition);
	    nom = nom.concat(" ");
	    nom = nom.concat(referenceSys.libelles.equilibrage);
	    nom = nom.concat(" ");

	    switch (p_equilibrage)
	    {
		case mauvais:
		    bonusInitSup = -1;
		    nom = nom.concat((String) referenceArm.getListEquilibrage().get(EquilibrageArme.mauvais));
		    break;
		case normal:
		    nom = nom.concat((String) referenceArm.getListEquilibrage().get(EquilibrageArme.normal));
		    break;
		case bon:
		    bonusInitSup = +1;
		    nom = nom.concat((String) referenceArm.getListEquilibrage().get(EquilibrageArme.bon));
		    break;
	    }
	}

	//récupération et construction des caractéristiques de l'arme
	m_desLances = referenceArm.getNbLancesArme(p_indice) + bonusLances;
	m_desGardes = referenceArm.getNbGardesArme(p_indice) + bonusGardes;
	m_bonusInit = referenceArm.getBonusInitArme(p_indice) + bonusInitSup;
	m_typeArme = referenceArm.getTypeArme(p_indice);
	m_malusAttaque = referenceArm.getMalusAttaqueArme(p_indice);
	m_physMin = referenceArm.getPhysMinArme(p_indice);
	m_nom = nom;
	m_categorie = referenceArm.getCategorieArme(p_indice);
	m_NbMainsArme = referenceArm.getNbMainsArme(p_indice);
	m_mode = referenceArm.getModArme(p_indice);
    }

    public int getBonusInit()
    {
	return m_bonusInit;
    }

    public int getCategorie()
    {
	return m_categorie;
    }

    public int getDesGardes()
    {
	return m_desGardes;
    }

    public int getDesLances()
    {
	return m_desLances;
    }

    public int getMalusAttaque()
    {
	return m_malusAttaque;
    }

    public int getMode()
    {
	return m_mode;
    }

    public int getNbMainsArme()
    {
	return m_NbMainsArme;
    }

    public int getTypeArme()
    {
	return m_typeArme;
    }

    public int getphysMin()
    {
	return m_physMin;
    }

    @Override
    public String toString()
    {
	return m_nom;
    }

    /**
     * classe utilisée pour encapsuler les résultats d'une attaque réussie ; des
     * dégâts mais aussi le type. On n'utilise pas de collections clé/valeur
     * comme une EnumMap car l'on veut juste un accès simple à des champs
     * définis : inutile de dégrader les performances avec toute la mécanique
     * des collections.
     */
    public static final class Degats
    {

	/**
	 * le total des dégâts infligés
	 */
	private int m_quantite;
	/**
	 * le type d'arme employé
	 */
	private int m_typeArme;

	/**
	 * constructeur de dégâts
	 *
	 * @param p_quantite
	 * @param p_typeArme
	 */
	public Degats(int p_quantite, int p_typeArme)
	{
	    if (p_quantite >= 0 && p_typeArme >= 0)
	    {
		m_quantite = p_quantite;
		m_typeArme = p_typeArme;
	    }
	    else
	    {
		ErrorHandler.paramAberrant(PropertiesHandler.getInstance("libupsystem").getString("degats") + ":" + p_quantite + " " + PropertiesHandler.getInstance("libupsystem").getString("type") + ":" + p_typeArme);
	    }
	}

	/**
	 * @return the m_quantite
	 */
	public int getQuantite()
	{
	    return m_quantite;
	}

	/**
	 * @return the m_typeArme
	 */
	public int getTypeArme()
	{
	    return m_typeArme;
	}
    }

    /**
     * Enum contenant les niveaux de qualite des armes
     */
    public enum QualiteArme
    {
	inferieure, moyenne, superieure, maitre
    };

    /**
     * Enum contenant les niveaux d'équilibrage
     */
    public enum EquilibrageArme
    {
	mauvais, normal, bon
    };
}
