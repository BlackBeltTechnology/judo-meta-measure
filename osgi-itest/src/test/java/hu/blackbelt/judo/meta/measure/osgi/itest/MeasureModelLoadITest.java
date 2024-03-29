package hu.blackbelt.judo.meta.measure.osgi.itest;

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

import org.slf4j.Logger;
import hu.blackbelt.epsilon.runtime.execution.impl.BufferedSlf4jLogger;
import hu.blackbelt.judo.meta.measure.runtime.MeasureModel;
import hu.blackbelt.judo.meta.measure.runtime.MeasureModel.MeasureValidationException;
import hu.blackbelt.judo.meta.measure.runtime.MeasureModel.SaveArguments;
import hu.blackbelt.osgi.utils.osgi.api.BundleTrackerManager;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.common.util.URI;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

import javax.inject.Inject;
import java.io.*;

import static hu.blackbelt.judo.meta.measure.osgi.itest.KarafFeatureProvider.karafConfig;
import static hu.blackbelt.judo.meta.measure.runtime.MeasureEpsilonValidator.calculateMeasureValidationScriptURI;
import static hu.blackbelt.judo.meta.measure.runtime.MeasureEpsilonValidator.validateMeasure;
import static org.ops4j.pax.exam.CoreOptions.*;
import static org.ops4j.pax.exam.OptionUtils.combine;
import static org.ops4j.pax.tinybundles.core.TinyBundles.bundle;
import static org.ops4j.pax.tinybundles.core.TinyBundles.withBnd;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
@Slf4j
public class MeasureModelLoadITest {

    private static final String DEMO = "northwind-measure";

    @Inject
    protected BundleTrackerManager bundleTrackerManager;

    @Inject
    BundleContext bundleContext;

    @Inject
    MeasureModel measureModel;

    @Configuration
    public Option[] config() throws IOException, MeasureValidationException {

        return combine(karafConfig(this.getClass()),
                mavenBundle(maven()
                        .groupId("hu.blackbelt.judo.meta")
                        .artifactId("hu.blackbelt.judo.meta.measure.osgi")
                        .versionAsInProject()),
                getProvisonModelBundle());
    }

    public Option getProvisonModelBundle() throws IOException, MeasureValidationException {
        return provision(
                getMeasureModelBundle()
        );
    }

    private InputStream getMeasureModelBundle() throws IOException, MeasureValidationException {
        MeasureModel measureModel = MeasureModel.buildMeasureModel()
                .name(DEMO)
                .uri(URI.createFileURI("test.model"))
                .build();

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        measureModel.saveMeasureModel(SaveArguments.measureSaveArgumentsBuilder().outputStream(os));

        return bundle()
                .add( "model/" + measureModel.getName() + "-measure.model",
                        new ByteArrayInputStream(os.toByteArray()))
                .set( Constants.BUNDLE_MANIFESTVERSION, "2")
                .set( Constants.BUNDLE_SYMBOLICNAME, measureModel.getName() + "-measure" )
                .set( "Measure-Models", "name=" + measureModel.getName() + ";file=model/" + measureModel.getName() + "-measure.model")
                .build( withBnd());
    }

    @Test
    public void testModelValidation() throws Exception {
        try (BufferedSlf4jLogger bufferedLog = new BufferedSlf4jLogger(log)) {
            validateMeasure(bufferedLog, measureModel, calculateMeasureValidationScriptURI());
        }
    }
}
