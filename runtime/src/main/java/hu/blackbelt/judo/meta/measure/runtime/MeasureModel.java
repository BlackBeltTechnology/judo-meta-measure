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

import lombok.*;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import java.util.Dictionary;
import java.util.Hashtable;

@AllArgsConstructor
@ToString
@Getter
@Builder(builderMethodName = "buildMeasureModel")
public class MeasureModel {

    public static final String NAME = "name";
    public static final String VERSION = "version";
    public static final String CHECKSUM = "checksum";
    public static final String META_VERSION_RANGE = "meta-version-range";
    public static final String URI = "uri";
    public static final String RESOURCESET = "resourceset";

    @NonNull
    String name;
    @NonNull
    String version;
    @NonNull
    URI uri;
    @Builder.Default
    String checksum = "NonDefined";

    String metaVersionRange;
    @NonNull
    ResourceSet resourceSet;

    public Dictionary<String, Object> toDictionary() {
        Dictionary<String, Object> ret = new Hashtable<>();
        ret.put(NAME, name);
        ret.put(VERSION, version);
        ret.put(URI, uri);
        ret.put(CHECKSUM, checksum);
        ret.put(META_VERSION_RANGE, metaVersionRange);
        ret.put(RESOURCESET, resourceSet);
        return ret;
    }

}
