## Eclipse Sirius
Sirius is based on Eclipse Modeling Framework, and enables the specification of a modeling workbench in terms of graphical, table or tree editors with validation rules and actions using declarative descriptions. Sirius is founded by Eclipse foundation, Samsung add features as needed.

Modification Base on:  v4.1.3
https://git.eclipse.org/c/sirius/org.eclipse.sirius.git/tag/?h=v4.1.3

tag name	v4.1.3 (293d1b35141f7e1cd215b24b3f3c19cabd072a4f)

tag date	2017-01-31 03:37:12 -0500

tagged by	Pierre-Charles David


# -- Original Sirius Information -- 
[![Build Status](https://travis-ci.org/pcdavid/org.eclipse.sirius.svg?branch=master)](https://travis-ci.org/pcdavid/org.eclipse.sirius)

Sirius enables the specification of a modeling workbench in terms of graphical, table or tree editors with validation rules and actions using declarative descriptions.

For more details see [the project page](http://www.eclipse.org/sirius) and [the main wiki page](http://wiki.eclipse.org/Sirius).

### Building

The build uses [Tycho](http://www.eclipse.org/tycho/). To launch a complete build, issue

```
mvn clean package
```

from the top-level directory. The resulting update-site (p2 repository) can be found in `packaging/org.eclipse.sirius.update/target/repository`.

By default the build uses a Neon-based target platform. You can specify a different platform like this:

```
mvn clean package -Dplatform-version-name=name
```

where `name` can be any of the following values:
* `mars` (Eclipse 4.5)
* `neon` (Eclipse 4.6, the default and reference target platform)
* `canary` (uses nightly builds of all our dependencies, only used for testing)

The corresponding target platform definitions can be found in `releng/org.eclipse.sirius.targets`.
