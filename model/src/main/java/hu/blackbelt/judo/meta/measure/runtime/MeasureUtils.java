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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class MeasureUtils {

    private static final Logger log = LoggerFactory.getLogger(MeasureUtils.class);

    private ResourceSet resourceSet;

    public MeasureUtils() {
        resourceSet = null;
    }

    public MeasureUtils(final ResourceSet resourceSet) {
        this.resourceSet = resourceSet;
    }

    public void setResourceSet(ResourceSet resourceSet) {
        this.resourceSet = resourceSet;
    }

    /**
     * Get id of {@link EObject} in XML if it has a resource
     *
     * @param eObject {@link EObject} with id
     * @return <i>eObject's</i> id if it has a resource, null otherwise
     */
    public static String getId(EObject eObject) {
        XMLResource xmlResource = (XMLResource) eObject.eResource();
        return xmlResource == null
               ? null
               : xmlResource.getID(eObject);
    }

    /**
     * Set id of {@link EObject} in XML if it has a resource
     *
     * @param eObject {@link EObject} with id
     * @param id      new id
     * @throws IllegalStateException if <i>eObject</i> does not have a resource
     */
    public static void setId(EObject eObject, String id) {
        XMLResource xmlResource = (XMLResource) eObject.eResource();
        if (xmlResource == null) {
            throw new IllegalStateException("Id " + id + " cannot be set: target object " + eObject + " does not have a resource");
        }
        xmlResource.setID(eObject, id);
    }

    /**
     * Check if all {@link EObject}s' xmiid-s are unique
     *
     * @throws IllegalStateException if model's ResourceSet is unknown (null) or duplicates were found
     * @see MeasureUtils#MeasureUtils(ResourceSet)
     * @see MeasureUtils#setResourceSet(ResourceSet)
     */
    public void validateUniqueXmiids() {
        if (resourceSet == null) {
            throw new IllegalStateException("Model's ResourceSet is unknown (null)");
        }

        log.debug("Xmiid validation started...");
        final List<String> ids = all(resourceSet)
                .filter(o -> o instanceof EObject)
                .map(o -> getId((EObject) o))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        final Set<String> nonUniqueXmiids = ids.stream()
                .filter(id -> {
                    log.debug("Checking id: " + id);
                    return ids.stream().filter(id::equals).count() > 1;
                })
                .collect(Collectors.toSet());

        if (nonUniqueXmiids.size() != 0) {
            final StringBuilder builder = new StringBuilder();
            nonUniqueXmiids.forEach(id -> builder.append("Xmiid ").append(id).append(" must be unique\n"));
            throw new IllegalStateException("There are non-unique xmiid-s\n" + builder.toString());
        }
    }

    /**
     * Get stream of source iterator.
     *
     * @param sourceIterator source iterator
     * @param parallel       flag controlling returned stream (serial or parallel)
     * @param <T>            type of source iterator
     * @return return serial (parallel = <code>false</code>) or parallel (parallel = <code>true</code>) stream
     */
    static <T> Stream<T> asStream(Iterator<T> sourceIterator, boolean parallel) {
        Iterable<T> iterable = () -> sourceIterator;
        return StreamSupport.stream(iterable.spliterator(), parallel);
    }

    /**
     * Get all model elements.
     *
     * @param <T> generic type of model elements
     * @return model elements
     */
    <T> Stream<T> all(final ResourceSet resourceSet) {
        return asStream((Iterator<T>) resourceSet.getAllContents(), false);
    }

    /**
     * Get model elements with specific type
     *
     * @param clazz class of model element types
     * @param <T>   specific type
     * @return all elements with clazz type
     */
    public <T> Stream<T> all(final ResourceSet resourceSet, final Class<T> clazz) {
        return all(resourceSet).filter(e -> clazz.isAssignableFrom(e.getClass())).map(e -> (T) e);
    }

}
