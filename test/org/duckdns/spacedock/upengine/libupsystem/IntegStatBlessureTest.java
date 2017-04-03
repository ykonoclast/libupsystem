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
import org.junit.Test;

/**
 *
 * @author ykonoclast
 */
public class IntegStatBlessureTest
{

    @Test
    public void testDegatsLegers()
    {
	int nbGraves = IntegStatTestUtils.nbBlessuresGravesStatistique(17, 2);
	Assert.assertEquals(1, nbGraves);
    }

    @Test
    public void testDegatsMoyens()
    {
	int nbGraves = IntegStatTestUtils.nbBlessuresGravesStatistique(33, 3);
	Assert.assertEquals(2, nbGraves);
    }

    @Test
    public void testDegatsLourds()
    {
	int nbGraves = IntegStatTestUtils.nbBlessuresGravesStatistique(55, 5);
	Assert.assertEquals(3, nbGraves);
    }
}
