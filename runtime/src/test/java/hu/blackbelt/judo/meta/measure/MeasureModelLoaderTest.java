package hu.blackbelt.judo.meta.measure;

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