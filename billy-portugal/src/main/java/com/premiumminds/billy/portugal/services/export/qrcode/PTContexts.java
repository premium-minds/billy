/*
 * Copyright (C) 2017 Premium Minds.
 *
 * This file is part of billy portugal (PT Pack).
 *
 * billy portugal (PT Pack) is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * billy portugal (PT Pack) is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with billy portugal (PT Pack). If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.billy.portugal.services.export.qrcode;

import com.premiumminds.billy.core.services.StringID;
import com.premiumminds.billy.core.services.entities.Context;

public class PTContexts {

    private final StringID<Context> portugalUID;
    private final StringID<Context> continentalUID;
    private final StringID<Context> azoresUID;
    private final StringID<Context> madeiraUID;

    public PTContexts(
        final StringID<Context> portugalUID,
        final StringID<Context> continentalUID,
        final StringID<Context> azoresUID,
        final StringID<Context> madeiraUID)
    {
        this.portugalUID = portugalUID;
        this.continentalUID = continentalUID;
        this.azoresUID = azoresUID;
        this.madeiraUID = madeiraUID;
    }

    public StringID<Context> getPortugalUID() {
        return portugalUID;
    }

    public StringID<Context> getContinentalUID() {
        return continentalUID;
    }

    public StringID<Context> getAzoresUID() {
        return azoresUID;
    }

    public StringID<Context> getMadeiraUID() {
        return madeiraUID;
    }
}
