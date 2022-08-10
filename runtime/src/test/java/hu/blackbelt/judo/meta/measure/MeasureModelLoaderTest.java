package hu.blackbelt.judo.meta.measure;

/*-
 * #%L
 * Judo :: Measure :: Model
 * %%
 * Copyright (C) 2018 - 2022 BlackBelt Technology
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

import hu.blackbelt.judo.meta.measure.runtime.MeasureModel;
import hu.blackbelt.judo.meta.measure.runtime.MeasureModelLoader;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

@Slf4j
class MeasureModelLoaderTest {

    @Test
    void loadMeasureModel() throws IOException {
        MeasureModel measureModel = MeasureModelLoader.loadMeasureModel(
                URI.createURI(new File(srcDir(), "test/models/northwind-measure.model").getAbsolutePath()),
                "test",
                "1.0.0");

        for (Iterator<EObject> i = measureModel.getResourceSet().getResource(measureModel.getUri(), false).getAllContents(); i.hasNext(); ) {
            log.debug(i.next().toString());
        }

        final long numberOfBaseMeasures = measureModel.getResourceSet().getResource(measureModel.getUri(), false).getContents().stream()
                .filter(c -> c instanceof BaseMeasure)
                .count();
        Assertions.assertEquals(9L, numberOfBaseMeasures);

        final long numberOfDerivedMeasures = measureModel.getResourceSet().getResource(measureModel.getUri(), false).getContents().stream()
                .filter(c -> c instanceof DerivedMeasure)
                .count();
        Assertions.assertEquals(20L, numberOfDerivedMeasures);
    }

    public File srcDir() {
        String relPath = getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
        File targetDir = new File(relPath + "../../src");
        if (!targetDir.exists()) {
            targetDir.mkdir();
        }
        return targetDir;
    }


}
