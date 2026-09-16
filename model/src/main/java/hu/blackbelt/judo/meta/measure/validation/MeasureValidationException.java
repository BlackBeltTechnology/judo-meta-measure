package hu.blackbelt.judo.meta.measure.validation;

/*-
 * #%L
 * Judo :: Measure :: Model
 * %%
 * Copyright (C) 2018 - 2024 BlackBelt Technology
 * %%
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License, v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is
 * available at https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 * #L%
 */

/**
 * Exception thrown when Measure model validation fails.
 */
public class MeasureValidationException extends Exception {

    private static final long serialVersionUID = 1L;

    public MeasureValidationException() {
        super("Measure model validation failed");
    }

    public MeasureValidationException(String message) {
        super(message);
    }

    public MeasureValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MeasureValidationException(Throwable cause) {
        super(cause);
    }
}
