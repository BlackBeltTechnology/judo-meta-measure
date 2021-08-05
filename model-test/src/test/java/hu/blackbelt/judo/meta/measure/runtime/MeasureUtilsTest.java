package hu.blackbelt.judo.meta.measure.runtime;

import hu.blackbelt.judo.meta.measure.BaseMeasure;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.common.util.URI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static hu.blackbelt.judo.meta.measure.runtime.MeasureUtils.getId;
import static hu.blackbelt.judo.meta.measure.runtime.MeasureUtils.setId;
import static hu.blackbelt.judo.meta.measure.util.builder.MeasureBuilders.newBaseMeasureBuilder;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class MeasureUtilsTest {

    private final String createdSourceModelName = "urn:Measure.model";

    @Test
    @DisplayName("Test ValidateUniqueXmiids")
    public void testValidateUniqueXmiids() {
        MeasureModel measureModel = MeasureModel.buildMeasureModel()
                .name("M")
                .uri(URI.createURI(createdSourceModelName))
                .build();

        BaseMeasure baseMeasure = newBaseMeasureBuilder().withNamespace("MN").withName("BM").build();
        BaseMeasure baseMeasure2 = newBaseMeasureBuilder().withNamespace("MN").withName("BM2").build();
        measureModel.addContent(baseMeasure);
        measureModel.addContent(baseMeasure2);

        MeasureUtils measureUtils = new MeasureUtils();

        // #1 unknown resourceSet
        IllegalStateException e = assertThrows(IllegalStateException.class, measureUtils::validateUniqueXmiids);
        assertEquals("Model's ResourceSet is unknown (null)", e.getMessage());

        // #2 non-unique id-s
        measureUtils.setResourceSet(measureModel.getResourceSet());
        setId(baseMeasure, "ID");
        setId(baseMeasure2, "ID");

        IllegalStateException e2 = assertThrows(IllegalStateException.class, measureUtils::validateUniqueXmiids);
        assertEquals("There are non-unique xmiid-s\nXmiid " + getId(baseMeasure) + " must be unique\n", e2.getMessage());

        // #3 unique id-s
        setId(baseMeasure2, "ID2");
        assertDoesNotThrow(measureUtils::validateUniqueXmiids);
    }

}
