# eclipse-icofidec

## About

**Icofidec** (**Ico**n **Fi**le **Dec**orator) is a simple plugin for the [Eclipse IDE](https://www.eclipse.org).

Target audience are mainly developers whose Eclipse projects contain a lot of icons.
Installed and activated, **Icofidec** replaces the Eclipse standard file icon in the navigation views 
(e.g. *Package Explorer* or *Project Explorer*) for elements that represent an icon with the actual icon:

| no Icofidec | Icofidec activated |
| --- | --- |
| ![An example icons folder in the Eclipse Package Explorer](/bundles/de.vermat.icofidec.help/html/icofidec-disabled.png) | ![An example icons folder in the Eclipse Package Explorer with Icofidec activated](/bundles/de.vermat.icofidec.help/html/icofidec-enabled.png) |

The software mainly consists of the two Decorator classes 
[IconFileImageDecorator](/bundles/de.vermat.icofidec/src/de/vermat/icofidec/decorators/IconFileImageDecorator.java) 
and 
[IconFileInfoDecorator](/bundles/de.vermat.icofidec/src/de/vermat/icofidec/decorators/IconFileInfoDecorator.java)
as well as the class 
[IconFileDecoratorPreferencePage](/bundles/de.vermat.icofidec/src/de/vermat/icofidec/preferences/IconFileDecoratorPreferencePage.java)
for implementing an Eclipse PreferencePage, which allows the user to make some settings for the plugin.
The overly bloated project structure is due to the original author's attempt to try out different techniques in dealing with Eclipse RCP projects. 
The most notable of these are the provision of help content, internationalization and modularization.

Free use of the software and files available on this site is permitted under the guidelines and in accordance with the [MIT License](/LICENSE).

## Credits

* **Icofidec** plugin was inspired by a feature of [eclipaint](https://github.com/jabiercoding/eclipaint).
* The [Maven Tycho tutorial by vogella](https://www.vogella.com/tutorials/EclipseTycho/article.html)
  provided the essential input for the development of the **Maven/Tycho** build
  (I'd never had to work with Maven before and I don't understand most of it yet - but I got it working ...).
* Most of the translations from German into English were made with the help of [DeepL](https://www.deepl.com/translator).

## About the repository and the Eclipse project structure

The **ecipse-icofidec** repository consists of the following directories:

* [bundles](/bundles):
  The actual software with the Java sources and other resources.
  - [de.vermat.icofidec](/bundles/de.vermat.icofidec):
    The implementation of the plugin logic (the Java sources ...).
  - [de.vermat.icofidec.config](/bundles/de.vermat.icofidec.config):
    The configuration fragment of [de.vermat.icofidec](/bundles/de.vermat.icofidec)
    containing the plugin defaults for the user preferences.
  - [de.vermat.icofidec.nl1](/bundles/de.vermat.icofidec.nl1):
    The fragment containing the default translation of the text strings of 
    [de.vermat.icofidec](/bundles/de.vermat.icofidec), namely **English**.
  - [de.vermat.icofidec.nl1.de](/bundles/de.vermat.icofidec.nl1.de):
    The fragment containing the **German** translation of the text strings of 
    [de.vermat.icofidec](/bundles/de.vermat.icofidec).
  - [de.vermat.icofidec.help](/bundles/de.vermat.icofidec.help):
    The content of the context-sensitive help and the user guide (default translation **English**).
  - [de.vermat.icofidec.help.de](/bundles/de.vermat.icofidec.help.de):
    The **German** translation of the help content.
* [features](/features):
  The Eclipse RCP feature projects used for the project build.
  - [de.vermat.icofidec.feature](/features/de.vermat.icofidec.feature):
    Produces the version with the default translation, namely **English**.
  - [de.vermat.icofidec.nls.de.feature](/features/de.vermat.icofidec.nls.de.feature):
    Produces the version with the **German** translation.
* [releng](/releng):
  The release engineering setup, containing
  - the [update site](/releng/de.vermat.icofidec.update) and 
  - the [target platform](/target-platform) definitions.
* [download](/download):
  Provides the downloadable Eclipse update site of the latest "official" version of the plugin.

## Setup Eclipse workspace

As of the end of 2021, the project should be editable in any recent version of an [Eclipse](https://www.eclipse.org/downloads/) distribution.
Clone the repository `https://github.com/maglas/eclipse-icofidec.git` and import the contained Eclipse projects into your workspace.
In **Eclipse** version **2021-12** (**4.22**) or later (I don't know since when this function has been available) is one simple approach: 
**File > Import > Git > Projects from Git (with smart import)**.

## Install

Go to the [Releases](https://github.com/maglas/eclipse-icofidec-OLD/releases) page,
select the release you want to install and follow the instructions there.

You can also build the update site yourself.
Prerequisite is having [Maven](https://maven.apache.org/) installed:
Clone the repository and then call `mvn clean install` in the project's root directory.
If the build was successful, you will find the update site archive in `./releng/de.vermat.icofidec.update/target/`
(e.g. `de.vermat.icofidec.update.eclipse-repository-1.0.0-SNAPSHOT.zip`).

## To do

* Fix the issue: If **Icofidec** replaces an icon, existing overlay icons applied by other plugins are no longer displayed on the file.
  Example: a jpeg file under version control (e.g. Git) does no longer display the version control overlay.
  
  Since `ILightweightLabelDecorator.decorate(element,IDecoration.REPLACE)` doesn't seem to work (the original 
  image remains unchanged), I implemented [IconFileImageDecorator](/bundles/de.vermat.icofidec/src/de/vermat/icofidec/decorators/IconFileImageDecorator.java) 
  as a "normal" `ILabelDecorator`, 
  which leads to the described effect.
  There is even a very old [Eclipse Bugzilla Bug](https://bugs.eclipse.org/bugs/show_bug.cgi?id=385119) for this, but it has the status "CLOSED WONTFIX".
