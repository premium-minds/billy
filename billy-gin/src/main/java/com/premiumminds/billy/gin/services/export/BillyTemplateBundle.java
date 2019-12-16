/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy GIN.
 *
 * billy GIN is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy GIN is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy GIN. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.gin.services.export;

import java.io.InputStream;

public interface BillyTemplateBundle {

    /**
     * returns the path to the image file to be used as a logo
     *
     * @return The path of the logo image file
     */
    public String getLogoImagePath();

    /**
     * returns the path to the xslt file to be used as the pdf template
     * generator
     *
     * @return The path of the xslt template file.
     */
    public InputStream getXSLTFileStream();

    public String getPaymentMechanismTranslation(Enum<?> pmc);

}
