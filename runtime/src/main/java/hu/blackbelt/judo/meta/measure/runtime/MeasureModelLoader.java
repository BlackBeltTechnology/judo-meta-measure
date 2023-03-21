package hu.blackbelt.judo.meta.measure.runtime;

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

import hu.blackbelt.judo.meta.measure.MeasurePackage;
import hu.blackbelt.judo.meta.measure.util.MeasureResourceFactoryImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIHandler;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.URIHandlerImpl;

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
                .resourceSet(resourceSet);

        if (checksum != null) {
            b.checksum(checksum);
        }

        if (acceptedMetaVersionRange != null)  {
            b.metaVersionRange(acceptedMetaVersionRange);
        }
        return b.build();
    }

    public static Map<Object, Object> getMeasureModelDefaultSaveOptions() {
        Map<Object, Object> saveOptions = new HashMap<>();
        saveOptions.put("DECLARE_XML", Boolean.TRUE);
        saveOptions.put("PROCESS_DANGLING_HREF", "DISCARD");
        saveOptions.put("URI_HANDLER", new URIHandlerImpl() {
            public URI deresolve(URI uri) {
                return uri.hasFragment() && uri.hasOpaquePart() && this.baseURI.hasOpaquePart() && uri.opaquePart().equals(this.baseURI.opaquePart()) ? URI.createURI("#" + uri.fragment()) : super.deresolve(uri);
            }
        });
        saveOptions.put("SCHEMA_LOCATION", Boolean.TRUE);
        saveOptions.put("DEFER_IDREF_RESOLUTION", Boolean.TRUE);
        saveOptions.put("SKIP_ESCAPE_URI", Boolean.FALSE);
        saveOptions.put("ENCODING", "UTF-8");
        return saveOptions;
    }

    public static void saveMeasuresModel(MeasureModel measureModel) throws IOException {
        measureModel.getResourceSet().getResource(measureModel.getUri(), false).save(getMeasureModelDefaultSaveOptions());
    }


}
