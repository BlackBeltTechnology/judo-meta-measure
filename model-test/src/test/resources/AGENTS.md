# `model-test/src/test/resources` — Karaf provisioning for the model-test runs

| File | Purpose |
|---|---|
| `test-features.xml` | Karaf feature repository `judo-TEST` (schema `features/v1.5.0`) declaring one installable feature `test` v`1`: `wrap`, `shell`, `scr` as `prerequisite="true"`, then `osgi-utils`, `tinybundles`, `epsilon-runtime`, `cxf-jaxrs`, `cxf-jackson`, `cxf-rs-description-swagger2`. Every `<repository>` URL interpolates a POM property (`${cxf-version}`, `${epsilon-runtime-version}`, `${karaf-features-version}`), so the file only works resource-filtered. |
