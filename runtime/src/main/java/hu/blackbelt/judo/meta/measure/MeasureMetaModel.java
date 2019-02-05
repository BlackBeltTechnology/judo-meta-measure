package hu.blackbelt.judo.meta.measure;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

public interface MeasureMetaModel {

    Resource.Factory getFactory();

    void registerMeasureMetamodel(ResourceSet resourceSet);
}
