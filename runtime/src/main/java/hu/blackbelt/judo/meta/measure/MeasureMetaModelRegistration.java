package hu.blackbelt.judo.meta.measure;

import hu.blackbelt.judo.meta.measure.util.MeasureResourceFactoryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

import java.util.Dictionary;
import java.util.Hashtable;

@Component(immediate = true, service = MeasureMetaModel.class)
public class MeasureMetaModelRegistration implements MeasureMetaModel {

    ServiceRegistration<Resource.Factory> measureFactoryRegistration;
    Resource.Factory factory;

    @Activate
    public void activate(ComponentContext componentContext) {
        Dictionary<String, Object> params = new Hashtable<>();
        params.put("meta", "measure");
        params.put("version", componentContext.getBundleContext().getBundle().getVersion());
        params.put("bundle", componentContext.getBundleContext().getBundle());

        factory = new MeasureResourceFactoryImpl();
        measureFactoryRegistration = componentContext.getBundleContext()
                .registerService(Resource.Factory.class, factory, params);
    }

    @Deactivate
    public void deactivate() {
        measureFactoryRegistration.unregister();
    }

    @Override
    public Resource.Factory getFactory() {
        return factory;
    }

    @Override
    public void registerMeasureMetamodel(ResourceSet resourceSet) {
        resourceSet.getPackageRegistry().put(MeasurePackage.eINSTANCE.getNsURI(), MeasurePackage.eINSTANCE);
    }

}
