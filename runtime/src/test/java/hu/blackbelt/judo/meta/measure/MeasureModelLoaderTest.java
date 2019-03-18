package hu.blackbelt.judo.meta.measure;

import hu.blackbelt.judo.meta.measure.runtime.MeasureModel;
import hu.blackbelt.judo.meta.measure.runtime.MeasureModelLoader;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

@Slf4j
class MeasureModelLoaderTest {

    @Test
    void loadMeasureModel() throws IOException {
        MeasureModel psmModel = MeasureModelLoader.loadMeasureModel(
                URI.createURI(new File(srcDir(), "test/models/northwind-measure.model").getAbsolutePath()),
                "test",
                "1.0.0");

        for (Iterator<EObject> i = psmModel.getResource().getAllContents(); i.hasNext(); ) {
            log.info(i.next().toString());
        }
    }

    public File srcDir(){
        String relPath = getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
        File targetDir = new File(relPath+"../../src");
        if(!targetDir.exists()) {
            targetDir.mkdir();
        }
        return targetDir;
    }


}