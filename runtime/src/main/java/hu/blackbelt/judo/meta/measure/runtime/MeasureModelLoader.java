package hu.blackbelt.judo.meta.measure.runtime;

import hu.blackbelt.judo.meta.measure.MeasurePackage;
import hu.blackbelt.judo.meta.measure.util.MeasureResourceFactoryImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIHandler;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MeasureModelLoader {

    public static void registerMeasureMetamodel(ResourceSet resourceSet) {
        resourceSet.getPackageRegistry().put(MeasurePackage.eINSTANCE.getNsURI(), MeasurePackage.eINSTANCE);
    }


    public static Resource.Factory getMeasureFactory() {
        return new MeasureResourceFactoryImpl();
    }

    public static ResourceSet createMeasureResourceSet() {
        return createMeasureResourceSet(null);
    }

    public static ResourceSet createMeasureResourceSet(URIHandler uriHandler) {
        ResourceSet resourceSet = new ResourceSetImpl();
        registerMeasureMetamodel(resourceSet);
        resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(ResourceFactoryRegistryImpl.DEFAULT_EXTENSION, getMeasureFactory());
        if (uriHandler != null) {
            resourceSet.getURIConverter().getURIHandlers().add(0, uriHandler);
        }
        return resourceSet;
    }


    public static MeasureModel loadMeasureModel(URI uri, String name, String version) throws IOException {
        return loadMeasureModel(createMeasureResourceSet(), uri, name, version, null, null);
    }

    public static MeasureModel loadMeasureModel(ResourceSet resourceSet, URI uri, String name, String version) throws IOException {
        return loadMeasureModel(resourceSet, uri, name, version, null, null);
    }

    public static MeasureModel loadMeasureModel(ResourceSet resourceSet, URI uri, String name, String version, String checksum, String acceptedMetaVersionRange) throws IOException {
        registerMeasureMetamodel(resourceSet);
        Resource resource = resourceSet.createResource(uri);
        Map<Object, Object> loadOptions = new HashMap<>();
        //loadOptions.put(XMLResource.OPTION_RECORD_UNKNOWN_FEATURE, Boolean.TRUE);
        //loadOptions.put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
        loadOptions.put(XMLResource.OPTION_DEFER_IDREF_RESOLUTION, Boolean.TRUE);
        loadOptions.put(XMLResource.OPTION_LAX_FEATURE_PROCESSING, Boolean.TRUE);
        loadOptions.put(XMLResource.OPTION_PROCESS_DANGLING_HREF, XMLResource.OPTION_PROCESS_DANGLING_HREF_DISCARD);
        resource.load(loadOptions);

        MeasureModel.MeasureModelBuilder b = MeasureModel.buildMeasureModel();

        b.name(name)
                .version(version)
                .uri(uri)
                .checksum(checksum)
                .resource(resource);

        if (checksum != null) {
            b.checksum(checksum);
        }

        if (acceptedMetaVersionRange != null)  {
            b.metaVersionRange(acceptedMetaVersionRange);
        }
        return b.build();
    }

}
