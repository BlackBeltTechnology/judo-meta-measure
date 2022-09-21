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

import hu.blackbelt.epsilon.runtime.execution.api.Log;
import hu.blackbelt.epsilon.runtime.execution.impl.Slf4jLog;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.BigDecimal;

import static hu.blackbelt.judo.meta.measure.runtime.MeasureModelLoader.createMeasureResourceSet;
import static hu.blackbelt.judo.meta.measure.util.builder.MeasureBuilders.newBaseMeasureBuilder;
import static hu.blackbelt.judo.meta.measure.util.builder.MeasureBuilders.newUnitBuilder;

class ExecutionContextTest {

    @Test
    void testReflectiveCreated() throws Exception {


        String createdSourceModelName = "urn:psm.judo-meta-measure";

        ResourceSet executionResourceSet = createMeasureResourceSet();
        Resource measureResource = executionResourceSet.createResource(
                URI.createURI(createdSourceModelName));

        Log log = new Slf4jLog();


        measureResource.getContents().add(newBaseMeasureBuilder()
                .withName("Name1")
                .withNamespace("ns")
                .withSymbol("sym")
                .withUnits(newUnitBuilder()
                        .withName("UName1")
                        .withRateDividend(new BigDecimal("10.0"))
                        .withSymbol("UNT").build())
                .build());

        /*

        // Executrion context
        ExecutionContext executionContext = executionContextBuilder()
                .log(log)
                .resourceSet(executionResourceSet)
                .metaModels(ImmutableList.of())
                .modelContexts(ImmutableList.of(
                        wrappedEmfModelContextBuilder()
                                .log(log)
                                .name("JUDOPSM")
                                .resourceSet(measureResource)
                                .build()))
                .sourceDirectory(scriptDir())
                .build();

        // run the model / metadata loading
        executionContext.load();

        // Transformation script
        executionContext.executeProgram(
                evlExecutionContextBuilder()
                        .source("validations/judopsm.evl")
                        .build());



        executionContext.commit();
        executionContext.close();
        */

    }

    public File scriptDir(){
        String relPath = getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
        File targetDir = new File(relPath+"../../src/main");
        if(!targetDir.exists()) {
            targetDir.mkdir();
        }
        return targetDir;
    }

}
