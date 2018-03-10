# Delphi Build Plugin
A Gradle Plugin to build Delphi Projects.

## Features

* Update all Project-Files (*.dproj) with the project version
* Assign the project version to a constant
* Assign the current git commit hash to a constant
* Compile the project (Win32, Win64, Android, iOS)
* Execute Unit-Tests
* Create a ZIP-Archive for delivery

## Project Structure

The plugin is designed to work with the following project structure:

```
.git
\bin
\src
build.gradle
```

Althoug this is not the default Delphi project structure I recommend this to clearly seperate source files from generated files. The second thing is that the plugin's `clean` and `assemble` tasks need a single directory that contains all generated files. To accomplish this you need to update the project settings with this output dir: `..\..\bin\$(Platform)\$(Config)` (instead of `.\$(Platform)\$(Config)`)

## Tasks

### Build
* assemble
* brand - Brand version and commit to source files
* build
* clean - Delete binary files
* compile
* test

### Util
* writeCommit - Write commit id to project files
* writeProjectVersion - Write version to project files
* writeVersionConstant - Write version to a constant in source code

### Info
* check - Check project configuration
* listCompilers - Display list of Delphi instalations
* readCommit - Display commit id from source-file
* readVersion - Display version from source-file

## Configuration

Here's an example for a simple *build.gradle* file.

```
buildscript {
	repositories {
		mavenLocal()
	}
	
	dependencies {
		classpath 'de.heinrichmarkus.gradle:dbp:0.1.7'
	}
}

apply plugin: 'de.heinrichmarkus.gradle.delphi'

project {
	bin = 'bin/'
	bds = '19.0'
	compiler.add('src/Project/PGradle_Client.dproj', 'Release', 'Win32')
	compiler.add('src/Project/PGradle_Test.dproj', 'Release', 'Win32')
	test.add('bin/Win32/Release/PGradle_Test.exe', '-exit:continue')
	name = 'GradleBuildTest'
	version = '0.0.1'
	versionCode = 19
	versionConstantFile = 'src/Units/Constants.pas'
	versionConstantName = 'PROGRAM_VERSION'
	commitConstantFile = versionConstantFile
	commitConstantName = 'COMMIT_HASH'
	noBrand = false
	noVersionDate = false
	assembly.add('bin/Win32/Release/PGradle_Client.exe')
}

```
