package hu.blackbelt.judo.meta.measure.runtime;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.UniqueEList;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class MeasureUtils {

    private static final Logger log = LoggerFactory.getLogger(MeasureUtils.class);

    private boolean failOnError;

    private ResourceSet resourceSet;

    public MeasureUtils() {
    }


    public MeasureUtils(final ResourceSet resourceSet) {
        this(resourceSet, false);
    }

    public MeasureUtils(final ResourceSet resourceSet, final boolean failOnError) {
        this.resourceSet = resourceSet;
        this.failOnError = failOnError;

        // TODO: Processes here
    }

    public void setFailOnError(final boolean failOnError) {
        this.failOnError = failOnError;
    }
}
